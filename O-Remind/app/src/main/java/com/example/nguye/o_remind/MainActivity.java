package com.example.nguye.o_remind;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements IStopCountTime {
    final Handler handler = new Handler();
    static CountDownTimer count1, count2;
    Runnable myRunnable1, myRunnable2;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    LinearLayout layout1, layout2, layout3, layout4;
    ImageButton btnWorking, btnRelax, btnSkip, btnPause, btnStop, btnWorking2, btnRelax2, btnStop2, btnStop3;
    TextView tvMinutes;
    ImageView ImgMilis;
    static int MinutesWorking = 2;
    static int MinutesRelax = 1;
    static int seconds = 0;
    static int checkWork = 0;

    int ck = 0;
    IStopCountTime iStopCountTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.iconapp);
        actionBar.setTitle(" " + getResources().getString(R.string.app_name));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        InitViews();
        tvMinutes.setText("00:00");
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        layout2.setVisibility(LinearLayout.GONE);
        layout3.setVisibility(LinearLayout.GONE);
        layout4.setVisibility(LinearLayout.GONE);
        layout1.setVisibility(LinearLayout.VISIBLE);
        //final Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
        btnWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
                ck=0;
               // btnWorking.setVisibility(View.INVISIBLE);
                layout1.setVisibility(LinearLayout.GONE);
                layout3.setVisibility(LinearLayout.GONE);
                layout4.setVisibility(LinearLayout.GONE);
               // btnStop.setVisibility(View.VISIBLE);
               // btnPause.setVisibility(View.VISIBLE);
               // btnSkip.setVisibility(View.VISIBLE);
                layout2.setVisibility(LinearLayout.VISIBLE);
                CountTimeWorking();
                checkWork = 1;

//                Calendar calendar = Calendar.getInstance();
//                int gio = calendar.get(Calendar.HOUR_OF_DAY);
//                int phut = calendar.get(Calendar.MINUTE);
//                calendar.set(Calendar.HOUR_OF_DAY, gio);
//                calendar.set(Calendar.MINUTE, phut + 1);
//                intent.putExtra("extra", "on");
//                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });
        btnWorking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              //  btnWorking.setVisibility(View.INVISIBLE);
//                layout1.setVisibility(LinearLayout.GONE);
//               // btnStop.setVisibility(View.VISIBLE);
//               // btnPause.setVisibility(View.VISIBLE);
//               // btnSkip.setVisibility(View.VISIBLE);
//                layout2.setVisibility(LinearLayout.VISIBLE);
//                CountTimeWorking();
//                checkWork = 1;

//                Calendar calendar = Calendar.getInstance();
//                int gio = calendar.get(Calendar.HOUR_OF_DAY);
//                int phut = calendar.get(Calendar.MINUTE);
//                calendar.set(Calendar.HOUR_OF_DAY, gio);
//                calendar.set(Calendar.MINUTE, phut + 1);
//                intent.putExtra("extra", "on");
//                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });
        btnRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
                ck=0;
              //  btnRelax.setVisibility(View.INVISIBLE);
                layout1.setVisibility(LinearLayout.GONE);
                layout3.setVisibility(LinearLayout.GONE);
                layout4.setVisibility(LinearLayout.GONE);
             //   btnStop.setVisibility(View.VISIBLE);
              //  btnPause.setVisibility(View.VISIBLE);
             //   btnSkip.setVisibility(View.VISIBLE);
                layout2.setVisibility(LinearLayout.VISIBLE);
                CountTimeRelax();
                checkWork = 2;
            }
        });
        btnRelax2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               // btnRelax.setVisibility(View.INVISIBLE);
//                layout1.setVisibility(LinearLayout.GONE);
//               // btnStop.setVisibility(View.VISIBLE);
//              //  btnPause.setVisibility(View.VISIBLE);
//               // btnSkip.setVisibility(View.VISIBLE);
//                layout2.setVisibility(LinearLayout.VISIBLE);
//                CountTimeRelax();
//                checkWork = 2;
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkWork == 1) {
                    count1.cancel();
                    MinutesRelax = 1;
                    seconds = 0;
                    CountTimeRelax();
                    checkWork = 2;
                    ck=0;
                } else if (checkWork == 2) {
                    count2.cancel();
                    MinutesWorking = 2;
                    seconds = 0;
                    CountTimeWorking();
                    checkWork = 1;
                    ck=0;
                }
                btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ck==0){
                    btnPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else if(ck==1){
                    btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
                }
                if (checkWork == 1 && ck==0) {
                    count1.cancel();
                    ck = 1;
                } else {
                    if (checkWork == 1 && ck == 1) {
                        count1.start();
                        ck = 0;
                    } else if (checkWork == 2 && ck == 1) {
                        count2.start();
                        ck = 0;
                    } else if (checkWork == 2 && ck == 0) {
                        count2.cancel();
                        ck = 1;
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogView();
            }
        });
        btnStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogView();
            }
        });
        btnStop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogView();
            }
        });
    }

    private void DialogView() {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("bạn có muốn dừng không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(count1!=null){
                   count1.cancel();
               }
               if(count2!=null){
                   count2.cancel();
               }
                MinutesWorking = 2;
                MinutesRelax =1;
                seconds = 0;
                tvMinutes.setText("00:00");
                ImgMilis.setImageResource(android.R.color.transparent);
              //  btnRelax.setVisibility(View.VISIBLE);
             //   btnWorking.setVisibility(View.VISIBLE);
                layout1.setVisibility(LinearLayout.VISIBLE);
                layout2.setVisibility(LinearLayout.GONE);
                layout3.setVisibility(LinearLayout.GONE);
                layout4.setVisibility(LinearLayout.GONE);

            }
        });
        dialogXoa.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }

    private void InitViews() {
        layout1 = (LinearLayout) this.findViewById(R.id.Option1);
        layout2 = (LinearLayout) this.findViewById(R.id.Option2);
        btnWorking = (ImageButton) findViewById(R.id.btnWorking);
        btnRelax = (ImageButton) findViewById(R.id.btnRelax);
        tvMinutes = (TextView) findViewById(R.id.tvMinutes);
        btnSkip = (ImageButton) findViewById(R.id.btnSkip);
        ImgMilis = (ImageView) findViewById(R.id.ImgMilis);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnWorking2 = (ImageButton) findViewById(R.id.btnWorking2);
        btnRelax2 =(ImageButton) findViewById(R.id.btnRelax2);
        btnStop2 = (ImageButton) findViewById(R.id.btnStop2);
        btnStop3 = (ImageButton) findViewById(R.id.btnStop3);
        layout3 = (LinearLayout) findViewById(R.id.Option3);
        layout4 = (LinearLayout) findViewById(R.id.Option4);
    }

    private void CountTimeWorking() {
        ImgMilis.setImageResource(R.drawable.ic_app);
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, MinutesWorking);
        calendar.set(Calendar.SECOND, seconds);
        final SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        tvMinutes.setText(dinhdang.format(calendar.getTime()));
        myRunnable1 = new Runnable() {
            @Override
            public void run() {
                count1 = new CountDownTimer(MinutesWorking * 1000 * 60 + 1000, 1000) {
                    int check = 0;

                    @Override
                    public void onTick(long l) {
                        if (MinutesWorking > 0 && seconds == 0) {
                            MinutesWorking--;
                        }
                        seconds--;
                        if (seconds == -1) {
                            seconds = 59;
                        }
                        calendar.set(Calendar.MINUTE, MinutesWorking);
                        calendar.set(Calendar.SECOND, seconds);
                        tvMinutes.setText(dinhdang.format(calendar.getTime()));
                    }

                    @Override
                    public void onFinish() {
                        MinutesWorking = 2;
                        MinutesRelax =1;
                        seconds = 0;
                        tvMinutes.setText("00:00");
                        ImgMilis.setImageResource(android.R.color.transparent);
//                       int a[] = new int[2];
//                        btnWorking.getLocationOnScreen(a);
//                        btnRelax.setX(250);
//                        Toast.makeText(MainActivity.this, a[0] + " : " + a[1], Toast.LENGTH_SHORT).show();
//                        btnRelax.setY(0);
                        layout3.setVisibility(LinearLayout.GONE);
                        layout2.setVisibility(LinearLayout.GONE);
                        layout1.setVisibility(LinearLayout.GONE);
                        layout4.setVisibility(LinearLayout.VISIBLE);
//                        btnPause.setVisibility(View.INVISIBLE);
//                        btnSkip.setVisibility(View.INVISIBLE);
//                        btnWorking.setVisibility(View.INVISIBLE);
//                        btnRelax.setVisibility(View.VISIBLE);
//                        btnStop.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                    }
                }.start();
            }
        };
        handler.postDelayed(myRunnable1, 1000);
//        Intent in = getIntent();
//        try{
//            int cc = in.getExtras().getInt("ccc");
//            if(cc==1){
//                handler.removeCallbacks(myRunnable);
//                cc=0;
//            }
//        }catch (Exception e){
//            Toast.makeText(this, "méo có Dl", Toast.LENGTH_SHORT).show();
//        }
    }

    private void CountTimeRelax() {
        ImgMilis.setImageResource(R.drawable.cf);
        final Handler handler = new Handler();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, MinutesRelax);
        calendar.set(Calendar.SECOND, seconds);
        final SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        tvMinutes.setText(dinhdang.format(calendar.getTime()));
        myRunnable2 = new Runnable() {
            @Override
            public void run() {
                count2 = new CountDownTimer(MinutesRelax * 1000 * 60 + 1000, 1000) {
                    int check = 0;

                    @Override
                    public void onTick(long l) {
                        if (MinutesRelax > 0 && seconds == 0) {
                            MinutesRelax--;
                        }
                        seconds--;
                        if (seconds == -1) {
                            seconds = 59;
                        }
                        calendar.set(Calendar.MINUTE, MinutesRelax);
                        calendar.set(Calendar.SECOND, seconds);
                        tvMinutes.setText(dinhdang.format(calendar.getTime()));
                    }

                    @Override
                    public void onFinish() {
                        MinutesWorking = 2;
                        MinutesRelax =1;
                        seconds = 0;
                        tvMinutes.setText("00:00");
                        ImgMilis.setImageResource(android.R.color.transparent);
                        layout2.setVisibility(LinearLayout.GONE);
                        layout1.setVisibility(LinearLayout.GONE);
                        layout4.setVisibility(LinearLayout.GONE);
                        layout3.setVisibility(LinearLayout.VISIBLE);
//                        btnPause.setVisibility(View.INVISIBLE);
//                        btnSkip.setVisibility(View.INVISIBLE);
//                        btnRelax.setVisibility(View.INVISIBLE);
//                        btnWorking.setVisibility(View.VISIBLE);
//                        btnStop.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                    }
                }.start();
            }
        };
        handler.postDelayed(myRunnable2, 1000);
//        Intent in = getIntent();
//        try{
//            int cc = in.getExtras().getInt("ccc");
//            if(cc==1){
//                handler.removeCallbacks(myRunnable);
//                cc=0;
//            }
//        }catch (Exception e){
//            Toast.makeText(this, "méo có Dl", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void Stop(int id) {
        if (id == 1) {
//            xx = 1;
//            Intent intent = new Intent(MainActivity.this, ReceiverStop.class);
//            intent.putExtra("abc", xx);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            xx = 0;
            Toast.makeText(this, "cc", Toast.LENGTH_SHORT).show();
        }
    }
    //get location

}
