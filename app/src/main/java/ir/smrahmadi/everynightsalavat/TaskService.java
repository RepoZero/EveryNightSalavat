package ir.smrahmadi.everynightsalavat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class TaskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer timer = new Timer();
    int UPDATE_INTERVAL = 5000;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Log.i("Task Service","Enable");


        timer.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {


                Calendar c = Calendar.getInstance();

                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minutes = c.get(Calendar.MINUTE) ;
                int Second = c.get(Calendar.SECOND);

                Log.i("Clock",Hour+":"+Minutes+":"+Second);

                if (Hour==21){

                    boolean hour_a=SharedPreferencesConnector.exist(TaskService.this,"A");
                    if(hour_a==false){
                        SharedPreferencesConnector.writeBoolean(TaskService.this,"A",true);
                        //write

                        sendNotification();

                    }
                }
              else if(Hour==24){
                    SharedPreferencesConnector.delete(TaskService.this,"A");
                }


            }
        }, 0, UPDATE_INTERVAL);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Task Service","Disable");
        timer.cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification() {

        Intent intent = new Intent(this, TaskService.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)

                .setContentTitle("برَسم هر شب 5 صلوات برای سلامتی و ظهور آقا امام زمان (عج)")
                .setContentText(" \uD83E\uDD8B الّلهُمَّ صَلِّ عَلَی مُحَمَّدٍ و َآلِ مُحَمَّد ٍوَ عَجِّلْ فَرَجَهُمْ\uD83E\uDD8B")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
