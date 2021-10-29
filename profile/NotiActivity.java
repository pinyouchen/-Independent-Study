package com.example.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class NotiActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pi;
    NotificationManager notiManager;
    NotificationChannel channel;
    Switch noti1, noti2, noti3, noti4, noti5;
    Context context = this;
    ImageButton setting;
    int set_hour,set_min;
    LinearLayout setting_view;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);

        setting_view = findViewById(R.id.setting_view);
        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(NotiActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(NotiActivity.this, 0, alarmIntent, 0);

        setting = findViewById(R.id.setting_btn);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        boolean status = false;
        boolean status2 = false;
        boolean status3 = false;
        boolean status4 = false;
        boolean status5 = false;

        createNotificationChannel();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(NotiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        set_hour= hourOfDay;
                        set_min= minute;
                        startDaily();
                    }
                }, hour, minute, true).show();
            }
        });

        noti1 = (Switch) findViewById(R.id.noti1);
        noti2 = (Switch) findViewById(R.id.noti2);
        noti3 = (Switch) findViewById(R.id.noti3);
        noti4 = (Switch) findViewById(R.id.noti4);
        noti5 = (Switch) findViewById(R.id.noti5);

        noti1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean status) {
                if (status) {
                    triggerNotification1();
                    Toast.makeText(NotiActivity.this, "你開啟了所有通知", Toast.LENGTH_LONG).show();
                    noti2.setChecked(true);
                    noti3.setChecked(true);
                    noti4.setChecked(true);
                    noti5.setChecked(true);

                }else{
                    Toast.makeText(NotiActivity.this,"關閉後所有推播都不會再打擾您",Toast.LENGTH_LONG).show();
                    noti2.setChecked(false);
                    noti3.setChecked(false);
                    noti4.setChecked(false);
                    noti5.setChecked(false);
                }
            }
        });
        noti2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean status2) {
                if (status2) {
                    openLight();
                    if(noti1.isChecked()==false){
                        noti1.setChecked(true);
                    }
                }else{
                    closeLight();
                }
            }
        });
        noti3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean status3) {
                if (status3) {
                    setting_view.setVisibility(View.VISIBLE);
//                    Intent intent = new Intent(NotiActivity.this,NotiAlarm.class);
//                    intent.setAction("VIDEO_TIMER");
//                    // PendingIntent这个类用于处理即将发生的事情
//                    PendingIntent sender = PendingIntent.getBroadcast(NotiActivity.this, 0, intent, 0);
//                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
//                    // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
//                    am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                            SystemClock.elapsedRealtime(), 10 * 1000, sender);
                }else{
                    setting_view.setVisibility(View.GONE);
                    cancel();
                }
            }
        });
        noti4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    triggerNotification4();
                }
            }
        });
        noti5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    triggerNotification5();
                }
            }
        });

    }

    private void triggerNotification1() {

        Intent intent = new Intent(this, NotificationDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentTitle("YO煮廚1")
                .setContentText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId),builder.build());
    }
    private void triggerNotification2() {

        Intent intent = new Intent(this, NotificationDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.email_small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic1))
                .setContentTitle("Noti1")
                .setContentText("如果有人收藏了您在YO煮廚發布的食譜，我們會第一時間通知您")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("HIHIHIHI"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId),builder.build());
    }
    private void triggerNotification3() {

        Intent intent = new Intent(this, NotificationDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.email_small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic1))
                .setContentTitle("Noti1")
                .setContentText("您已開啟日記填寫提醒通知，YO煮廚會在固定時間傳送通知提醒您該寫日記了喔")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("HIHIHIHI"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId),builder.build());
    }
    private void triggerNotification4() {

        Intent intent = new Intent(this, NotificationDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.email_small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic1))
                .setContentTitle("Noti1")
                .setContentText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId),builder.build());
    }
    private void triggerNotification5() {

        Intent intent = new Intent(this, NotificationDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.email_small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic1))
                .setContentTitle("Noti1")
                .setContentText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("您已開啟推播通知，YO煮廚有任何新消息都會第一時間通知您"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId),builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.NEWS_CHANNEL_ID), getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void openLight(){
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toast.makeText(NotiActivity.this, "clicked 2.", Toast.LENGTH_LONG).show();

    }
    private void closeLight(){
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toast.makeText(NotiActivity.this, "channel 2.", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "按下左上角返回鍵", Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public void startDaily() {
        Toast.makeText(this,"wait...",Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//we are using alarm manager for the notification

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);//this intent will be called when taping the notification
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);//this pendingIntent will be called by the broadcast receiver

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//getting calender instance

        cal.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        cal.set(Calendar.HOUR_OF_DAY, set_hour); // cal.set NOT cal.add
        cal.set(Calendar.MINUTE, set_min);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);//alarm manager will repeat the notification each day at the set time
    }

    public void startAt1800() {
        Toast.makeText(this,"wait...",Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//we are using alarm manager for the notification

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);//this intent will be called when taping the notification
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);//this pendingIntent will be called by the broadcast receiver

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//getting calender instance

        cal.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        cal.set(Calendar.HOUR_OF_DAY, 18); // cal.set NOT cal.add
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);//alarm manager will repeat the notification each day at the set time
    }

    public void startAt1130() {
        Toast.makeText(this,"wait...",Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//we are using alarm manager for the notification

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);//this intent will be called when taping the notification
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);//this pendingIntent will be called by the broadcast receiver

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//getting calender instance

        cal.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        cal.set(Calendar.HOUR_OF_DAY, 11); // cal.set NOT cal.add
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);//alarm manager will repeat the notification each day at the set time
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }


}
