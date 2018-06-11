package com.hellodoc.abdullah.hellodocserver;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hellodoc.abdullah.hellodocserver.Common.Common;
import com.hellodoc.abdullah.hellodocserver.Interface.ItemClickListner;
import com.hellodoc.abdullah.hellodocserver.Model.Category;
import com.hellodoc.abdullah.hellodocserver.Model.Doctor;
import com.hellodoc.abdullah.hellodocserver.ViewHolder.DocViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import info.hoang8f.widget.FButton;

public class DocList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private static final int REQ_PERMISSION = 120;
    FloatingActionButton fab;

    FirebaseDatabase db;
    DatabaseReference doclist;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId ="";
    Doctor newDoc;
    RelativeLayout rootlayout;
    Uri saveuri;
FirebaseRecyclerAdapter<Doctor, DocViewHolder> adapter;


MaterialEditText docnam,docadd,docqua,docday,doctime;
FButton btnSelect,btnupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list);

        db=FirebaseDatabase.getInstance();
        doclist = db.getReference("Doctor");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        recyclerView = findViewById(R.id.recycler_doc);
        rootlayout = findViewById(R.id.rootlayout);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqper();
                showAddDocDialog();

            }
        });

        if(getIntent()!=null)
        {
            categoryId =getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty())
        {

            loadlistdoctor(categoryId);
        }

    }

    private void reqper() {
        int reqEx = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (reqEx != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);
        }
    }

    private void showAddDocDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DocList.this);
        alertDialog.setTitle("Add New Doctor");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View add_doc_layout = layoutInflater.inflate(R.layout.add_new_doc, null);


        docnam = add_doc_layout.findViewById(R.id.edtDocName);
        docadd = add_doc_layout.findViewById(R.id.edtDocAdd);
        docqua = add_doc_layout.findViewById(R.id.edtDocEdu);
        docday = add_doc_layout.findViewById(R.id.edtDocDays);
        doctime = add_doc_layout.findViewById(R.id.edtDoctime);

        btnSelect = add_doc_layout.findViewById(R.id.btnSelect);
        btnupload = add_doc_layout.findViewById(R.id.btnupload);


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });


        alertDialog.setView(add_doc_layout);
        alertDialog.setIcon(R.drawable.ic_add_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();

                if (newDoc != null) {
                    doclist.push().setValue(newDoc);
                    Snackbar.make(rootlayout,"New Doctor "+ newDoc.getName()+" was added",Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }


    private void uploadImage() {
        final ProgressDialog mdialog = new ProgressDialog(this);
        try{

        mdialog.setMessage("Uploading...");
        mdialog.show();

        String imagename = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/" + imagename);
        imageFolder.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mdialog.dismiss();
                Toast.makeText(DocList.this, "Uploaded !!!", Toast.LENGTH_SHORT).show();
                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newDoc = new Doctor();
                        newDoc.setName(docnam.getText().toString());
                        newDoc.setAddress(docadd.getText().toString());
                        newDoc.setQualification(docqua.getText().toString());
                        newDoc.setDays(docday.getText().toString());
                        newDoc.setTimings(doctime.getText().toString());
                        newDoc.setDoctor_Id(categoryId);
                        newDoc.setImage(uri.toString());
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mdialog.dismiss();
                        Toast.makeText(DocList.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double prog = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mdialog.setMessage("Uploaded " + prog + "%");
                    }
                });

    }catch (Exception ex)
        {
            mdialog.dismiss();
            Toast.makeText(this, "Please Select Image First", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Common.PICK_IMAGE_REQUEST);
    }
    private void loadlistdoctor(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Doctor, DocViewHolder>(
                Doctor.class,
                R.layout.doc_item,
                DocViewHolder.class,
                doclist.orderByChild("doctor_Id").equalTo(categoryId)

        ) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void populateViewHolder(DocViewHolder viewHolder, Doctor model, int position) {
                    viewHolder.txtDocName.setText(model.getName());
                Picasso.get().load(model.getImage())
                        .into(viewHolder.docimg);

                viewHolder.setItemClicklistener(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });


            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            saveuri = data.getData();
            btnSelect.setText("Image Selected !");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if(item.getTitle().equals(Common.DELETE))
        {
            deletFood(adapter.getRef(item.getOrder()).getKey());
        }

        return super.onContextItemSelected(item);
    }

    private void deletFood(String key) {
        doclist.child(key).removeValue();
    }

    private void showUpdateFoodDialog(final String key, final Doctor item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DocList.this);
        alertDialog.setTitle("Edit Doctor");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View add_doc_layout = layoutInflater.inflate(R.layout.add_new_doc, null);


        docnam = add_doc_layout.findViewById(R.id.edtDocName);
        docadd = add_doc_layout.findViewById(R.id.edtDocAdd);
        docqua = add_doc_layout.findViewById(R.id.edtDocEdu);
        docday = add_doc_layout.findViewById(R.id.edtDocDays);
        doctime = add_doc_layout.findViewById(R.id.edtDoctime);

        btnSelect = add_doc_layout.findViewById(R.id.btnSelect);
        btnupload = add_doc_layout.findViewById(R.id.btnupload);

        docnam.setText(item.getName());
        docadd.setText(item.getAddress());
        docqua.setText(item.getQualification());
        docday.setText(item.getDays());
        doctime.setText(item.getTimings());


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(item);
            }
        });


        alertDialog.setView(add_doc_layout);
        alertDialog.setIcon(R.drawable.ic_add_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();





                    item.setName(docnam.getText().toString());
                    item.setAddress(docadd.toString());
                    item.setDays(docday.toString());
                    item.setTimings(doctime.toString());
                    item.setQualification(docqua.toString());
                    doclist.push().setValue(newDoc);

                    doclist.child(key).setValue(item);

                    Snackbar.make(rootlayout,"Doctor "+ item.getName()+" was Edited",Snackbar.LENGTH_SHORT).show();


            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void changeImage(final Doctor item) {
        final ProgressDialog mdialog = new ProgressDialog(this);
        mdialog.setMessage("Uploading...");
        mdialog.show();

        String imagename = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/"+imagename);
        imageFolder.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mdialog.dismiss();
                Toast.makeText(DocList.this, "Uploaded !!!", Toast.LENGTH_SHORT).show();
                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        item.setImage(uri.toString());
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mdialog.dismiss();
                        Toast.makeText(DocList.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double prog = (100.0* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        mdialog.setMessage("Uploaded "+ prog+ "%");
                    }
                });

    }
}
