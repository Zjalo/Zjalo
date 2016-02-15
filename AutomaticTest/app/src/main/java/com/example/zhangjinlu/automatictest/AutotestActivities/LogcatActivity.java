package com.example.zhangjinlu.automatictest.AutotestActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangjinlu.automatictest.AutotestUtils.ItemTestMethod;
import com.example.zhangjinlu.automatictest.R;


/**
 * Created by zhangjinlu on 2016/2/14.
 */
public class LogcatActivity extends AppCompatActivity {

    public Boolean flag = false;

    public TextView textView3;

    private boolean isExit = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if( isExit == true){
                    finish();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);

        textView3 = (TextView) findViewById(R.id.textView3);

        ItemTestMethod itemTestMethod = new ItemTestMethod();

        flag = itemTestMethod.logcat_test();

        if(flag == true){
            textView3.setText("测试结果："+"离线日志不启动，adb服务启动");
            Toast.makeText(LogcatActivity.this, "测试成功", Toast.LENGTH_SHORT).show();
        }else{
            textView3.setText("测试结果："+"离线日志启动，adb服务未启动");
            Toast.makeText(LogcatActivity.this, "测试失败", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent();

        intent.putExtra("oriclass","LogcatActivity");

        intent.putExtra("flag",flag);

        setResult(-1,intent);


        if(!isExit){
            isExit = true;
            handler.sendEmptyMessageDelayed(0,2000);
        }else{
            finish();
        }

    }
}
