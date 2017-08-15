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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
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
    ImageView ImgProgress1,ImgProgress2,ImgProgress3,ImgProgress4,ImgProgress5,ImgProgress6;
    static int checkAnimation = 1;
    Animation animationalpha ;
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
        ImgProgress1 = (ImageView) findViewById(R.id.ImgProgress1);
        ImgProgress2 = (ImageView) findViewById(R.id.ImgProgress2);;
        ImgProgress3 = (ImageView) findViewById(R.id.ImgProgress3);;
        ImgProgress4 = (ImageView) findViewById(R.id.ImgProgress4);;
        ImgProgress5 = (ImageView) findViewById(R.id.ImgProgress5);;
        ImgProgress6 = (ImageView) findViewById(R.id.ImgProgress6);;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        imgAlpha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(x==1){
//                    view.startAnimation(animationalpha);
//                    x=0;
//                }
//                else if(x==0){
//                    view.clearAnimation();
//                    x=1;
//                }
//
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.iconapp);
        actionBar.setTitle(" " + getResources().getString(R.string.app_name));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        InitViews();
        animationalpha = AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_alpha);
        tvMinutes.setText("00:00");
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        layout2.setVisibility(LinearLayout.GONE);
        layout3.setVisibility(LinearLayout.GONE);
        layout4.setVisibility(LinearLayout.GONE);
        layout1.setVisibility(LinearLayout.VISIBLE);
        btnWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Working();
                StartAnimation();
            }
        });
        btnWorking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Working();
                StartAnimation();
            }
        });

        btnRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Relax();
            }
        });
        btnRelax2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Relax();
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
                    CancelAnimation();
//                    switch (checkAnimation){
//                        case 1 :
//                            ImgProgress1.clearAnimation();checkAnimation++;break;
//                        case 2 :
//                            ImgProgress2.clearAnimation();checkAnimation++;break;
//                        case 3 :
//                            ImgProgress3.clearAnimation();checkAnimation++;break;
//                        case 4 :
//                            ImgProgress4.clearAnimation();checkAnimation++;break;
//                        case 5 :
//                            ImgProgress5.clearAnimation();checkAnimation++;break;
//                        case 6 :
//                            ImgProgress6.clearAnimation();checkAnimation++;break;
//                    }

                } else if (checkWork == 2) {
                    count2.cancel();
                    MinutesWorking = 2;
                    seconds = 0;
                    CountTimeWorking();
                    checkWork = 1;
                    ck=0;
                    StartAnimation();
//                    switch (checkAnimation){
//                        case 1 :
//                            ImgProgress1.setImageResource(R.drawable.ic_brain);
//                            ImgProgress1.startAnimation(animationalpha);break;
//                        case 2 :
//                            ImgProgress2.setImageResource(R.drawable.ic_brain);
//                            ImgProgress2.startAnimation(animationalpha);break;
//                        case 3 :
//                            ImgProgress3.setImageResource(R.drawable.ic_brain);
//                            ImgProgress3.startAnimation(animationalpha);break;
//                        case 4 :
//                            ImgProgress4.setImageResource(R.drawable.ic_brain);
//                            ImgProgress4.startAnimation(animationalpha);break;
//                        case 5 :
//                            ImgProgress5.setImageResource(R.drawable.ic_brain);
//                            ImgProgress5.startAnimation(animationalpha);break;
//                        case 6 :
//                            ImgProgress6.setImageResource(R.drawable.ic_brain);
//                            ImgProgress6.startAnimation(animationalpha);break;
//                        case 7 :  ImgProgress1.setImageResource(R.drawable.ic_brain);
//                            ImgProgress2.setImageResource(R.drawable.ic_brain2);
//                            ImgProgress3.setImageResource(R.drawable.ic_brain2);
//                            ImgProgress4.setImageResource(R.drawable.ic_brain2);
//                            ImgProgress5.setImageResource(R.drawable.ic_brain2);
//                            ImgProgress6.setImageResource(R.drawable.ic_brain2);
//                            ImgProgress1.startAnimation(animationalpha);break;
//                    }
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
              DialogView();
            }
        });
        btnStop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogView();
            }
        });
    }


    //method working
    private void Working(){
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        ck=0;
        layout1.setVisibility(LinearLayout.GONE);
        layout3.setVisibility(LinearLayout.GONE);
        layout4.setVisibility(LinearLayout.GONE);
        layout2.setVisibility(LinearLayout.VISIBLE);
        CountTimeWorking();
        checkWork = 1;
//        switch (checkAnimation){
//            case 1 :
//                ImgProgress1.setImageResource(R.drawable.ic_brain);
//                ImgProgress1.startAnimation(animationalpha);break;
//            case 2 :
//                ImgProgress2.setImageResource(R.drawable.ic_brain);
//                ImgProgress2.startAnimation(animationalpha);break;
//            case 3 :
//                ImgProgress3.setImageResource(R.drawable.ic_brain);
//                ImgProgress3.startAnimation(animationalpha);break;
//            case 4 :
//                ImgProgress4.setImageResource(R.drawable.ic_brain);
//                ImgProgress4.startAnimation(animationalpha);break;
//            case 5 :
//                ImgProgress5.setImageResource(R.drawable.ic_brain);
//                ImgProgress5.startAnimation(animationalpha);break;
//            case 6 :
//                ImgProgress6.setImageResource(R.drawable.ic_brain);
//                ImgProgress6.startAnimation(animationalpha);break;
//            case 7 :  ImgProgress1.setImageResource(R.drawable.ic_brain);
//                ImgProgress2.setImageResource(R.drawable.ic_brain2);
//                ImgProgress3.setImageResource(R.drawable.ic_brain2);
//                ImgProgress4.setImageResource(R.drawable.ic_brain2);
//                ImgProgress5.setImageResource(R.drawable.ic_brain2);
//                ImgProgress6.setImageResource(R.drawable.ic_brain2);
//                checkAnimation =1;ImgProgress1.startAnimation(animationalpha);break;
//        }
    }

    //method relax
    private void Relax(){
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        ck=0;
        layout1.setVisibility(LinearLayout.GONE);
        layout3.setVisibility(LinearLayout.GONE);
        layout4.setVisibility(LinearLayout.GONE);
        layout2.setVisibility(LinearLayout.VISIBLE);
        CountTimeRelax();
        checkWork = 2;
//        switch (checkAnimation){
//            case 1 :
//                ImgProgress1.clearAnimation();checkAnimation++;break;
//            case 2 :
//                ImgProgress2.clearAnimation();checkAnimation++;break;
//            case 3 :
//                ImgProgress3.clearAnimation();checkAnimation++;break;
//            case 4 :
//                ImgProgress4.clearAnimation();checkAnimation++;break;
//            case 5 :
//                ImgProgress5.clearAnimation();checkAnimation++;break;
//            case 6 :
//                ImgProgress6.clearAnimation();checkAnimation++;break;//       }
    }

    //start animation
    private void StartAnimation(){
        switch (checkAnimation){
            case 1 :
                ImgProgress1.setImageResource(R.drawable.iconapp);
                ImgProgress1.startAnimation(animationalpha);break;
            case 2 :
                ImgProgress2.setImageResource(R.drawable.iconapp);
                ImgProgress2.startAnimation(animationalpha);break;
            case 3 :
                ImgProgress3.setImageResource(R.drawable.iconapp);
                ImgProgress3.startAnimation(animationalpha);break;
            case 4 :
                ImgProgress4.setImageResource(R.drawable.iconapp);
                ImgProgress4.startAnimation(animationalpha);break;
            case 5 :
                ImgProgress5.setImageResource(R.drawable.iconapp);
                ImgProgress5.startAnimation(animationalpha);break;
            case 6 :
                ImgProgress6.setImageResource(R.drawable.iconapp);
                ImgProgress6.startAnimation(animationalpha);break;
            case 7 :
                ImgProgress1.setImageResource(R.drawable.iconapp);
                ImgProgress2.setImageResource(R.drawable.ic_brain2);
                ImgProgress3.setImageResource(R.drawable.ic_brain2);
                ImgProgress4.setImageResource(R.drawable.ic_brain2);
                ImgProgress5.setImageResource(R.drawable.ic_brain2);
                ImgProgress6.setImageResource(R.drawable.ic_brain2);
                ImgProgress1.startAnimation(animationalpha);
                checkAnimation = 0;
                break;

        }
    }

    //Cancel animation
    private void CancelAnimation(){
                switch (checkAnimation) {
                    case 0:
                        checkAnimation++;
                        break;
                    case 1:
                        ImgProgress1.clearAnimation();
                        checkAnimation++;
                        break;
                    case 2:
                        ImgProgress2.clearAnimation();
                        checkAnimation++;
                        break;
                    case 3:
                        ImgProgress3.clearAnimation();
                        checkAnimation++;
                        break;
                    case 4:
                        ImgProgress4.clearAnimation();
                        checkAnimation++;
                        break;
                    case 5:
                        ImgProgress5.clearAnimation();
                        checkAnimation++;
                        break;
                    case 6:
                        ImgProgress6.clearAnimation();
                        checkAnimation++;
                        break;
                }
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
//                        switch (checkAnimation){
//                            case 1 :
//                                ImgProgress1.clearAnimation();checkAnimation++;break;
//                            case 2 :
//                                ImgProgress2.clearAnimation();checkAnimation++;break;
//                            case 3 :
//                                ImgProgress3.clearAnimation();checkAnimation++;break;
//                            case 4 :
//                                ImgProgress4.clearAnimation();checkAnimation++;break;
//                            case 5 :
//                                ImgProgress5.clearAnimation();checkAnimation++;break;
//                            case 6 :
//                                ImgProgress6.clearAnimation();checkAnimation++;break;
//                        }
                        CancelAnimation();

                        MinutesWorking = 2;
                        MinutesRelax =1;
                        seconds = 0;
                        tvMinutes.setText("00:00");
                        ImgMilis.setImageResource(android.R.color.transparent);
                        layout3.setVisibility(LinearLayout.GONE);
                        layout2.setVisibility(LinearLayout.GONE);
                        layout1.setVisibility(LinearLayout.GONE);
                        layout4.setVisibility(LinearLayout.VISIBLE);
                    }
                }.start();
            }
        };
        handler.postDelayed(myRunnable1, 1000);
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
                    }
                }.start();
            }
        };
        handler.postDelayed(myRunnable2, 1000);
    }
}
