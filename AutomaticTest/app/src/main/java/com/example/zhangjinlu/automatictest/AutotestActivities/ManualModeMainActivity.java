package com.example.zhangjinlu.automatictest.AutotestActivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhangjinlu.automatictest.R;

public class ManualModeMainActivity extends Activity implements View.OnClickListener{

    public Button SettingStorage,Logcat,Usb,APN,Back;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);
        findButtonById();
    }

    private void findButtonById() {
        SettingStorage = (Button) findViewById(R.id.button10);
        Logcat = (Button) findViewById(R.id.button8);
        Usb = (Button) findViewById(R.id.button9);
        APN = (Button) findViewById(R.id.button11);
        Back = (Button) findViewById(R.id.button16);


        SettingStorage.setOnClickListener(this);
        Logcat.setOnClickListener(this);
        Back.setOnClickListener(this);
        Usb.setOnClickListener(this);
        APN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button8 :
                intent = new Intent(this,LogcatActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.button9 :
                intent = new Intent(this,UsbActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.button10 :
                intent = new Intent(this,SettingStorageActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.button11 :
                intent = new Intent(this,APNActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.button16 :
                this.finish();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Boolean flag = data.getBooleanExtra("flag",false);

            String oriclass = data.getStringExtra("oriclass");

            switch(oriclass){
                case "SettingStorageActivity" :
                    if(flag == true){
                        SettingStorage.setBackgroundColor(Color.GREEN);
                    }else{
                        SettingStorage.setBackgroundColor(Color.RED);
                    }
                    break;
                case "UsbActivity" :
                    if(flag == true){
                        Usb.setBackgroundColor(Color.GREEN);
                    }else{
                        Usb.setBackgroundColor(Color.RED);
                    }
                    break;
                case "LogcatActivity" :
                    if(flag == true){
                        Logcat.setBackgroundColor(Color.GREEN);
                    }else{
                        Logcat.setBackgroundColor(Color.RED);
                    }
                    break;
                case "ApnActivity" :
                    if(flag == true){
                        APN.setBackgroundColor(Color.GREEN);
                    }else{
                        APN.setBackgroundColor(Color.RED);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
