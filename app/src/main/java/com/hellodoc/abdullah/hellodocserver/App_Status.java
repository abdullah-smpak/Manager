package com.hellodoc.abdullah.hellodocserver;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hellodoc.abdullah.hellodocserver.Common.Common;
import com.hellodoc.abdullah.hellodocserver.Interface.ItemClickListner;
import com.hellodoc.abdullah.hellodocserver.Model.Appointment;
import com.hellodoc.abdullah.hellodocserver.Model.Request;
import com.hellodoc.abdullah.hellodocserver.Model.User;
import com.hellodoc.abdullah.hellodocserver.ViewHolder.AppViewHolder;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class App_Status extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Appointment,AppViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference requests;

    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__status);


        //Firebase
        db= FirebaseDatabase.getInstance();
        requests = db.getReference("Request");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        loadApps(); //load all orders
    }

    private void loadApps() {
        adapter = new FirebaseRecyclerAdapter<Appointment, AppViewHolder>(
                Appointment.class,
                R.layout.app_layout,
                AppViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(AppViewHolder viewHolder, Appointment model, int position) {





                viewHolder.txtdrnam.setText(model.getDrName());
                viewHolder.txtpatname.setText(model.getPatient_Name());
                viewHolder.txtpatphone.setText(adapter.getRef(position).getKey());
                viewHolder.txtappdate.setText(model.getAppointment_Date_());
                viewHolder.txtapptime.setText(model.getAppointment_Time());
                viewHolder.txtstatus.setText(Common.convertCodeToStatus(model.getStatus()));



                viewHolder.setItemClicklistener(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongclick) {

                    }
                });


            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else  if(item.getTitle().equals(Common.DELETE))
            deleteOrder (adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);

    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();

    }

    private void showUpdateDialog(String key, final Appointment item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(App_Status.this);
        alertDialog.setTitle("Update Appointment");
        alertDialog.setMessage("Please choose status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View  view = inflater.inflate(R.layout.update_app_layout,null );

        spinner = (MaterialSpinner) view.findViewById(R.id.statusSpinner);
        spinner.setItems("Pending", "Appointed", "Completed");


        alertDialog.setView(view);
        final String localkey =key;


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));


                requests.child(localkey).setValue(item);




            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface , int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();
    }


}
