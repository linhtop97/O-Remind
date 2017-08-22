package com.example.nguye.o_remind;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //Declare variable
    Intent intentFinished;
    SharedPreferences sharedPreferences;
    private final Handler handler = new Handler();
    private Runnable RunnableWorking, RunnableRelax;
    private LinearLayout LayoutMain, LayoutPlaying, LayoutWorkingContinue, LayoutRelaxContinue;
    private ImageButton btnWorking, btnRelax, btnSkip, btnPause, btnStop, btnWorking2, btnRelax2, btnStop2, btnStop3;
    private TextView tvMinutes;
    private ImageView ImgMilis;
    private static CountDownTimer WorkingCountDown, RelaxCountDown;
    static int MinutesWorking;
    static int MinutesRelax;
    static int MinutesRelax2;
    static int seconds = 0;
    static boolean chkLongEnable;
    static int checkWork = 2;

    int ckeckIsPlay = 0;
    ImageView ImgProgress1,ImgProgress2,ImgProgress3,ImgProgress4,ImgProgress5,ImgProgress6;
    static int checkAnimation = 1;
    Animation animationalpha ;
    private void InitViews() {
        LayoutMain = (LinearLayout) this.findViewById(R.id.Option1);
        LayoutPlaying = (LinearLayout) this.findViewById(R.id.Option2);
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
        LayoutWorkingContinue = (LinearLayout) findViewById(R.id.Option3);
        LayoutRelaxContinue = (LinearLayout) findViewById(R.id.Option4);
        ImgProgress1 = (ImageView) findViewById(R.id.ImgProgress1);
        ImgProgress2 = (ImageView) findViewById(R.id.ImgProgress2);
        ImgProgress3 = (ImageView) findViewById(R.id.ImgProgress3);
        ImgProgress4 = (ImageView) findViewById(R.id.ImgProgress4);
        ImgProgress5 = (ImageView) findViewById(R.id.ImgProgress5);
        ImgProgress6 = (ImageView) findViewById(R.id.ImgProgress6);
        animationalpha = AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_alpha);
        tvMinutes.setText("00:00");
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        LayoutPlaying.setVisibility(LinearLayout.GONE);
        LayoutWorkingContinue.setVisibility(LinearLayout.GONE);
        LayoutRelaxContinue.setVisibility(LinearLayout.GONE);
        LayoutMain.setVisibility(LinearLayout.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.iconapp);
        actionBar.setTitle(" " + getResources().getString(R.string.app_name));
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        sharedPreferences = getSharedPreferences("Data",MODE_PRIVATE);
        MinutesWorking = sharedPreferences.getInt("iWorkDuration",25);
        MinutesRelax = sharedPreferences.getInt("iBreakDuration",5);
        MinutesRelax2 = sharedPreferences.getInt("iLongBreakDuration",20);
        chkLongEnable = sharedPreferences.getBoolean("bLongBreakEnable",true);
        intentFinished = new Intent(MainActivity.this,AlarmReceiver.class);
        // iWorkSessions = sharedPreferences.getInt("iWorkSessions",6);
        InitViews();
        AddEvents();
    }

private void AddEvents(){
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
            if (checkWork == 2 && checkAnimation!=7) {
                Working();
                StartAnimation();
            }
        }
    });

    btnRelax.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Relax();
            if(checkAnimation!=1){
                CancelAnimation();
            }

        }
    });
    btnRelax2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkWork == 1 && checkAnimation!=6){
                CancelAnimation();
                Relax();
            }else if(checkWork == 1 && checkAnimation==6){
                CancelAnimation();
                StartAnimation();
                boolean b = sharedPreferences.getBoolean("bLongBreakEnable",true);
                if(b) {
                    MinutesRelax = sharedPreferences.getInt("iLongBreakDuration", 20);
                }else{
                    MinutesRelax = sharedPreferences.getInt("iBreakDuration", 5);
                }
                seconds = 0;
                Relax();
                checkAnimation=1;
                ckeckIsPlay=0;
            }

        }
    });


    btnSkip.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (checkWork == 1 && checkAnimation!=6) {
                WorkingCountDown.cancel();
                CancelAnimation();
                MinutesRelax = sharedPreferences.getInt("iBreakDuration",5);;
                seconds = 0;
                CountTimeRelax();
                checkWork = 2;
                ckeckIsPlay=0;

            } else if (checkWork == 2 && checkAnimation!=7) {
                RelaxCountDown.cancel();
                MinutesWorking =  sharedPreferences.getInt("iWorkDuration",25);;
                seconds = 0;
                CountTimeWorking();
                checkWork = 1;
                ckeckIsPlay=0;
                StartAnimation();
            }else if ( checkAnimation==6 && checkWork==1) {
                ImgProgress1.setImageResource(R.drawable.ic_brain2);
                ImgProgress2.setImageResource(R.drawable.ic_brain2);
                ImgProgress3.setImageResource(R.drawable.ic_brain2);
                ImgProgress4.setImageResource(R.drawable.ic_brain2);
                ImgProgress5.setImageResource(R.drawable.ic_brain2);
                ImgProgress6.setImageResource(R.drawable.ic_brain2);
                WorkingCountDown.cancel();
                CancelAnimation();
                boolean b = sharedPreferences.getBoolean("bLongBreakEnable",true);
                if(b) {
                    MinutesRelax = sharedPreferences.getInt("iLongBreakDuration", 20);
                }else{
                    MinutesRelax = sharedPreferences.getInt("iBreakDuration", 5);
                }
                seconds = 0;
                CountTimeRelax();
                checkWork = 2;
                checkAnimation=1;
                ckeckIsPlay=0;
            }
            btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        }
    });


    btnPause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(ckeckIsPlay==0){
                btnPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }else if(ckeckIsPlay==1){
                btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
            }
            if (checkWork == 1 && ckeckIsPlay==0) {
                WorkingCountDown.cancel();
                ckeckIsPlay = 1;
            } else {
                if (checkWork == 1 && ckeckIsPlay == 1) {
                    WorkingCountDown.start();
                    ckeckIsPlay = 0;
                } else if (checkWork == 2 && ckeckIsPlay == 1) {
                    RelaxCountDown.start();
                    ckeckIsPlay = 0;
                } else if (checkWork == 2 && ckeckIsPlay == 0) {
                    RelaxCountDown.cancel();
                    ckeckIsPlay = 1;
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
        ckeckIsPlay=0;
        LayoutMain.setVisibility(LinearLayout.GONE);
        LayoutWorkingContinue.setVisibility(LinearLayout.GONE);
        LayoutRelaxContinue.setVisibility(LinearLayout.GONE);
        LayoutPlaying.setVisibility(LinearLayout.VISIBLE);
        CountTimeWorking();
        checkWork = 1;
    }

    //method relax
    private void Relax(){
        btnPause.setImageResource(R.drawable.ic_pause_black_24dp);
        ckeckIsPlay=0;
        LayoutMain.setVisibility(LinearLayout.GONE);
        LayoutWorkingContinue.setVisibility(LinearLayout.GONE);
        LayoutRelaxContinue.setVisibility(LinearLayout.GONE);
        LayoutPlaying.setVisibility(LinearLayout.VISIBLE);
        CountTimeRelax();
        checkWork = 2;
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
                ImgProgress1.setImageResource(R.drawable.ic_brain2);
                ImgProgress2.setImageResource(R.drawable.ic_brain2);
                ImgProgress3.setImageResource(R.drawable.ic_brain2);
                ImgProgress4.setImageResource(R.drawable.ic_brain2);
                ImgProgress5.setImageResource(R.drawable.ic_brain2);
                ImgProgress6.setImageResource(R.drawable.ic_brain2);
                break;

        }
    }

    //Cancel animation
    private void CancelAnimation(){
                switch (checkAnimation) {
                    case 1:
                        ImgProgress1.clearAnimation();
                        break;
                    case 2:
                        ImgProgress2.clearAnimation();
                        break;
                    case 3:
                        ImgProgress3.clearAnimation();
                        break;
                    case 4:
                        ImgProgress4.clearAnimation();
                        break;
                    case 5:
                        ImgProgress5.clearAnimation();
                        break;
                    case 6:
                        ImgProgress6.clearAnimation();
                        break;
                }
        checkAnimation++;
    }
    private void DialogView() {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("bạn có muốn dừng không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(WorkingCountDown!=null){
                   WorkingCountDown.cancel();
               }
               if(RelaxCountDown!=null){
                   RelaxCountDown.cancel();
               }
                ResetAll();
                ImgProgress1.clearAnimation();
                ImgProgress2.clearAnimation();
                ImgProgress3.clearAnimation();
                ImgProgress4.clearAnimation();
                ImgProgress5.clearAnimation();
                ImgProgress6.clearAnimation();
                tvMinutes.setText("00:00");
                ImgMilis.setImageResource(android.R.color.transparent);
                LayoutMain.setVisibility(LinearLayout.VISIBLE);
                LayoutPlaying.setVisibility(LinearLayout.GONE);
                LayoutWorkingContinue.setVisibility(LinearLayout.GONE);
                LayoutRelaxContinue.setVisibility(LinearLayout.GONE);

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
        RunnableWorking = new Runnable() {
            @Override
            public void run() {
                WorkingCountDown = new CountDownTimer(MinutesWorking * 1000 * 60 + (MinutesWorking+1)*1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        // Decreate Minutes Work
                        if (MinutesWorking > 0 && seconds == 0) {
                            MinutesWorking--;
                        }
                        // Decreate Second Work
                        if(MinutesWorking>=0 && seconds>-1) {
                            seconds--;
                        }
                        if (seconds == -1) {
                            seconds = 59;
                        }
                        //Update time text on Screen
                        calendar.set(Calendar.MINUTE, MinutesWorking);
                        calendar.set(Calendar.SECOND, seconds);
                        tvMinutes.setText(dinhdang.format(calendar.getTime()));
                    }

                    @Override
                    public void onFinish() {
                        MinutesWorking =sharedPreferences.getInt("iWorkDuration",25);
                        MinutesRelax = sharedPreferences.getInt("iBreakDuration",5);
                        seconds = 0;
                        tvMinutes.setText("00:00");
                        //Delete icon show above time text
                        ImgMilis.setImageResource(android.R.color.transparent);
                        LayoutPlaying.setVisibility(LinearLayout.GONE);
                        LayoutRelaxContinue.setVisibility(LinearLayout.VISIBLE);
                        intentFinished.putExtra("extra", "WokringFinished");
                        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intentFinished, PendingIntent.FLAG_UPDATE_CURRENT);
                     //   sendBroadcast(intentFinished);
                    }
                }.start();
            }
        };
        handler.postDelayed(RunnableWorking, 1000);
    }

    private void CountTimeRelax() {
        ImgMilis.setImageResource(R.drawable.cf);
        final Handler handler = new Handler();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, MinutesRelax);
        calendar.set(Calendar.SECOND, seconds);
        final SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        tvMinutes.setText(dinhdang.format(calendar.getTime()));
        RunnableRelax = new Runnable() {
            @Override
            public void run() {
                RelaxCountDown = new CountDownTimer(MinutesRelax * 1000 * 60 + 1000, 1000) {
                    int check = 0;

                    @Override
                    public void onTick(long l) {
                        if (MinutesRelax > 0 && seconds == 0) {
                            MinutesRelax--;
                        }
                        if(MinutesRelax>=0 && seconds>-1) {
                            seconds--;
                        }
                        if (seconds == -1) {
                            seconds = 59;
                        }
                        calendar.set(Calendar.MINUTE, MinutesRelax);
                        calendar.set(Calendar.SECOND, seconds);
                        tvMinutes.setText(dinhdang.format(calendar.getTime()));
                    }

                    @Override
                    public void onFinish() {
                        MinutesWorking =sharedPreferences.getInt("iWorkDuration",25);
                        MinutesRelax = sharedPreferences.getInt("iBreakDuration",5);
                        seconds = 0;
                        tvMinutes.setText("00:00");
                        ImgMilis.setImageResource(android.R.color.transparent);
                        LayoutPlaying.setVisibility(LinearLayout.GONE);
                        LayoutWorkingContinue.setVisibility(LinearLayout.VISIBLE);
                        intentFinished.putExtra("extra", "RelaxFinished");
                        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intentFinished, PendingIntent.FLAG_UPDATE_CURRENT);
                      //  sendBroadcast(intentFinished);
                    }
                }.start();
            }
        };
        handler.postDelayed(RunnableRelax, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufirst,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menusearch:
                Toast.makeText(this, "Bạn chọn search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menushare:
                Toast.makeText(this, "Bạn chọn share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuEmail:
                Toast.makeText(this, "Bạn chọn Email", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuExit:
                Toast.makeText(this, "Bạn chọn exit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuPhone:
                Toast.makeText(this, "Bạn chọn Phone", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menusetting:
                startActivity(new Intent(MainActivity.this,SetupActivity.class));

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void ResetAll(){
        MinutesWorking =sharedPreferences.getInt("iWorkDuration",25);
        MinutesRelax = sharedPreferences.getInt("iBreakDuration",5);
        seconds = 0;
        checkAnimation=1;
        checkWork =2;
        ImgProgress1.setImageResource(R.drawable.ic_brain2);
        ImgProgress2.setImageResource(R.drawable.ic_brain2);
        ImgProgress3.setImageResource(R.drawable.ic_brain2);
        ImgProgress4.setImageResource(R.drawable.ic_brain2);
        ImgProgress5.setImageResource(R.drawable.ic_brain2);
        ImgProgress6.setImageResource(R.drawable.ic_brain2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        try{
            Intent intent =getIntent();
            if(intent.getExtras().getString("datachange1").equals("ok1")){
                MinutesWorking = sharedPreferences.getInt("iWorkDuration",25);
            }
            if(intent.getExtras().getString("datachange2").equals("ok2")) {
                MinutesRelax = sharedPreferences.getInt("iBreakDuration", 5);
            }
            if(intent.getExtras().getString("datachange3").equals("ok3")) {
                MinutesRelax2 = sharedPreferences.getInt("iLongBreakDuration", 20);
            }
            seconds = 0;
        }catch (Exception e){}

        super.onRestart();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
