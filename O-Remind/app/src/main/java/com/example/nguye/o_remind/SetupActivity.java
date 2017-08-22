package com.example.nguye.o_remind;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView tvWorkDurationNumber, tvBreakDurationNumber, tvLongBreakDurationNumber, tvWorkSessonsNumber;
    Switch stwLongEnable;
    static int check = 0;
    int TimeMax = 60;
    int TimeMin = 1;
    int SessionMax = 8;
    int SessionMin = 1;
    static int iWorkDuration, iBreakDuration, iLongBreakDuration, iWorkSessions;
    static boolean chkLongEnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" " + getResources().getString(R.string.Setup));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        sharedPreferences = getSharedPreferences("Data",MODE_PRIVATE);
        //lấy giá trị
        iWorkDuration = sharedPreferences.getInt("iWorkDuration",25);
        iBreakDuration = sharedPreferences.getInt("iBreakDuration",5);
        iLongBreakDuration = sharedPreferences.getInt("iLongBreakDuration",20);
        iWorkSessions = sharedPreferences.getInt("iWorkSessions",6);
        chkLongEnable = sharedPreferences.getBoolean("bLongBreakEnable",true);
        InitView();
        AddEvent();
    }

    private void AddEvent() {
        tvWorkDurationNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=1;
                int a = check;
                DialogGetTime(getResources().getString(R.string.Work_Duration),check);
                check =0;
            }
        });
        tvBreakDurationNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check =2;
                DialogGetTime(getResources().getString(R.string.Break_Duration),check);
                check =0;
            }
        });
        tvLongBreakDurationNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check =3;
                DialogGetTime(getResources().getString(R.string.Long_Break_Duration),check);
                check =0;
            }
        });
        tvWorkSessonsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=4;
                DialogGetTime(getResources().getString(R.string.Work_sessions),check);
                check = 0;
            }
        });
      //setOnckeckedchangeLisstener : if true => toast..., switch.settext....
        stwLongEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("bLongBreakEnable");
                    editor.putBoolean("bLongBreakEnable", b);
                    editor.commit();
            }
        });
    }

    private void InitView() {
        tvWorkDurationNumber = (TextView) findViewById(R.id.tvWorkDurationNumber);
        tvBreakDurationNumber = (TextView) findViewById(R.id.tvBreakDurationNumber);
        tvLongBreakDurationNumber = (TextView) findViewById(R.id.tvLongBreakDurationNumber);
        tvWorkSessonsNumber = (TextView) findViewById(R.id.tvWorkSessionsNumber);
        stwLongEnable = (Switch) findViewById(R.id.swtLongBreak);

        tvWorkDurationNumber.setText(String.valueOf(iWorkDuration));
        tvBreakDurationNumber.setText(String.valueOf(iBreakDuration));
        tvLongBreakDurationNumber.setText(String.valueOf(iLongBreakDuration));
        tvWorkSessonsNumber.setText(String.valueOf(iWorkSessions));
        stwLongEnable.setChecked(chkLongEnable);
    }

    public void DialogGetTime(String s, final int checkone){
        final Dialog dialog = new Dialog(this);
        final Intent intent = new Intent(SetupActivity.this,MainActivity.class);
        //xóa cái phía trên
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogcustom);
        //ko cho clcik ra ngoài
        dialog.setCanceledOnTouchOutside(false);
        //ánh xạ
        final TextView tvShow = (TextView) dialog.findViewById(R.id.tvShow);
        tvShow.setText(s);
        final EditText edtTime = (EditText)dialog.findViewById(R.id.edtTime);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Time = 0;
                if (edtTime.getText().toString().trim().equals("")) {
                    Toast.makeText(SetupActivity.this, "không được bỏ trống trường này", Toast.LENGTH_SHORT).show();
                }else {
                    String str = edtTime.getText().toString().trim();
                    char[] a = edtTime.getText().toString().toCharArray();
                    int x = 0;
                    for (int i = 0; i < a.length; i++) {
                        if (Integer.parseInt(String.valueOf(a[i])) > 0) {
                            x = i;
                            break;
                        }
                    }
                    Time = Integer.parseInt(str.substring(x, a.length));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (checkone){
                        case 1:
                            if (Time > TimeMax) {
                                Toast.makeText(SetupActivity.this, "Số phút không được lớn hơn " + TimeMax + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else if (Time < TimeMin) {
                                Toast.makeText(SetupActivity.this, "Số phút không được nhỏ hơn " + TimeMin + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else {

                                editor.remove("iWorkDuration");
                                editor.putInt("iWorkDuration", Time);
                                editor.commit();
                                tvWorkDurationNumber.setText(String.valueOf(Time));
                                Toast.makeText(SetupActivity.this, "Work Duration: "+ String.valueOf(Time) + "Phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                                dialog.dismiss();
                                intent.putExtra("datachange1","ok1");
                            }
                            break;
                        case 2:
                            if (Time > TimeMax) {
                                Toast.makeText(SetupActivity.this, "Số phút không được lớn hơn " + TimeMax + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else if (Time < TimeMin) {
                                Toast.makeText(SetupActivity.this, "Số phút không được nhỏ hơn " + TimeMin + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else {

                                editor.remove("iBreakDuration");
                                editor.putInt("iBreakDuration", Time);
                                editor.commit();
                                tvBreakDurationNumber.setText(String.valueOf(Time));
                                Toast.makeText(SetupActivity.this, "Break Duration: "+ String.valueOf(Time) + "Phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                                dialog.dismiss();
                                intent.putExtra("datachange2","ok2");
                            }
                            break;
                        case 3:
                            if (Time > TimeMax) {
                                Toast.makeText(SetupActivity.this, "Số phút không được lớn hơn " + TimeMax + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else if (Time < TimeMin) {
                                Toast.makeText(SetupActivity.this, "Số phút không được nhỏ hơn " + TimeMin + " phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else {
                                editor.remove("iLongBreakDuration");
                                editor.putInt("iLongBreakDuration", Time);
                                editor.commit();
                                tvLongBreakDurationNumber.setText(String.valueOf(Time));
                                Toast.makeText(SetupActivity.this, "Long Break Duration: "+ String.valueOf(Time) + "Phút", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                                dialog.dismiss();
                                intent.putExtra("datachange3","ok3");
                            }
                            break;
                        case 4:
                            if (Time > SessionMax) {
                                Toast.makeText(SetupActivity.this, "Phiên làm việc không được lớn hơn " + SessionMax + " phiên", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else if (Time < SessionMin) {
                                Toast.makeText(SetupActivity.this, "Phiên làm việc không được nhỏ hơn " + SessionMin + " phiên", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                            } else {
                                editor.remove("iWorkSessions");
                                editor.putInt("iWorkSessions", Time);
                                editor.commit();
                                tvWorkSessonsNumber.setText(String.valueOf(Time));
                                Toast.makeText(SetupActivity.this, String.valueOf(Time) + " phiên", Toast.LENGTH_SHORT).show();
                                edtTime.setText("");
                                dialog.dismiss();
                                intent.putExtra("datachange4","ok4");
                            }
                            break;
                    }
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
