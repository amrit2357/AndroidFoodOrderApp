package com.example.orderfoodnow.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.orderfoodnow.Common.Common;
import com.example.orderfoodnow.Home;
import com.example.orderfoodnow.Model.Request;
import com.example.orderfoodnow.R;
import com.example.orderfoodnow.orderStatus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference requests;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("requests");

        Log.d("OnCreateListen","oncraete");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requests.addChildEventListener(this);
        Log.d("OnStart","onStarte");
        return super.onStartCommand( intent, flags, startId );
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d("OnChildAdded","onAdded");
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        // Trigger here
        Log.d("OnChildChanges","onChnages");
        Request request = dataSnapshot.getValue(Request.class);
        showNotifications(dataSnapshot.getKey(),request);


    }

    private void showNotifications(String key, Request request) {
        Intent intent = new Intent( getBaseContext() , orderStatus.class);
        intent.putExtra( "userPhone",request.getPhone());
        PendingIntent content = (PendingIntent) PendingIntent.getActivity( getBaseContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder builder = new NotificationCompat.Builder( getBaseContext() );
        builder.setAutoCancel( true )
                .setDefaults( Notification.DEFAULT_ALL )
                .setWhen( System.currentTimeMillis() )
                .setTicker( "Amrit" )
                .setContentInfo( "your order was updated" )
                .setContentText( "Order #" + key + "Was updated status to "+ Common.convertCodeToStatus( request.getStatus() ) )
                .setContentIntent( content )
                .setSmallIcon( R.mipmap.ic_launcher );
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify( 1, builder.build() );
    }


    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        Log.d( "Removes","rmoved" ) ;
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
