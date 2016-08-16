package com.example.hwhong.notificationexplore;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    //every notification has its own unique ID
    private int NOTIFICATION_ID;
    //allows us to build a custom layout which can then be used to combine with notification
    private RemoteViews remoteViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification);

        //broadcast receiver will only be listening to intents that have the name tagged "btnClicked"
        Intent intent = new Intent("btnClicked");
        NOTIFICATION_ID = (int) System.currentTimeMillis();
        intent.putExtra("id", NOTIFICATION_ID);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 999, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notiIntent = new Intent(getApplicationContext(), MainActivity.class);
                //when user clicks anywhere on the notification the activity will be launched
                PendingIntent pendingIntent1 = PendingIntent.getActivity(getApplicationContext(), 0, notiIntent, 0);
                builder  = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.uber)
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews)
                        .setContentIntent(pendingIntent1);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        });
    }
}
