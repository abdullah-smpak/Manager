package com.hellodoc.abdullah.hellodocserver.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hellodoc.abdullah.hellodocserver.App_Status;
import com.hellodoc.abdullah.hellodocserver.Model.Request;
import com.hellodoc.abdullah.hellodocserver.R;

import java.util.Random;

public class ListenApp extends Service implements ChildEventListener {

    FirebaseDatabase db ;
    DatabaseReference app;


    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        app = db.getReference("Request");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        app.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public ListenApp() {
    }

    @Override
    public IBinder onBind(Intent intent) {
return  null;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Request request = dataSnapshot.getValue(Request.class);
        if(request.getStatus().equals("0"))
        {
            showNotification(dataSnapshot.getKey(),request );
        }

    }

    private void showNotification(String key, Request request) {

        Intent intent = new Intent(getBaseContext(), App_Status.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(getBaseContext(),0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("Hello Doctor")
                .setContentInfo("New Appoinment")
                .setContentTitle("You have new Appointment Of" + key)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int rand = new Random().nextInt(9999-1)+1;
        manager.notify(rand,builder.build());





    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
