package com.example.zhangjinlu.automatictest.AutotestActivities;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangjinlu.automatictest.AutotestUtils.ItemTestMethod;
import com.example.zhangjinlu.automatictest.R;


/**
 * Created by zhangjinlu on 2016/2/14.
 */
public class UsbActivity extends AppCompatActivity {

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

    public boolean usb_test(){
        TelephonyManager TELEPHONY_SERVICE = (TelephonyManager) getSystemService(
                Service.TELEPHONY_SERVICE);

        int simState = TELEPHONY_SERVICE.getSimState();

        String value = android.provider.Settings.System.getString(getContentResolver(), Settings.Secure.ADB_ENABLED);

        if((simState == TelephonyManager.SIM_STATE_ABSENT) &&
                (value != null && "1".equals(value))){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        textView3 = (TextView) findViewById(R.id.textView3);

        flag = usb_test();

        if(flag == true){
            textView3.setText("测试结果："+"不插入sim卡，USB调试不打开");
            Toast.makeText(UsbActivity.this, "测试成功", Toast.LENGTH_LONG).show();
        }else{
            textView3.setText("测试结果："+"失败");
            Toast.makeText(UsbActivity.this, "测试失败", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();

        intent.putExtra("oriclass","UsbActivity");

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
