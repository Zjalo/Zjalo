package com.example.zhangjinlu.automatictest;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import android.text.format.Formatter;

public class AutotestItemDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button B_Success = null,B_Fail = null;

    private TextView textView = null,textView2 = null;

    public int id;
    public String content = "" , title = "";

    public boolean flag = false;//flag to set button color

    String value = null; //query result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autotest_item_detail);

        //get intent info

        id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");
        content = getIntent().getExtras().getString("content");

        findTextViewById();


        findButtonById();

        doCheckItem();

        setButtonColor();
    }

    private void setButtonColor() {
        if(flag == true){
            B_Success.setBackgroundColor(Color.GREEN);
        }else{
            B_Fail.setBackgroundColor(Color.RED);
        }
    }

    private void doCheckItem() {
        switch(id){
            case 1:
                String loggerState = getprop("persist.service.logr.enable","");
                String adbdState = getprop("init.svc.adbd","");
                if(((loggerState != "" && "0".equals(loggerState)))
                        && (adbdState != "" && "running".equals(adbdState))) {
                    flag = true;
                }else{
                    flag = false;
                }
                break;
            case 2:
                TelephonyManager TELEPHONY_SERVICE = (TelephonyManager) getSystemService(
                        Service.TELEPHONY_SERVICE);

                int simState = TELEPHONY_SERVICE.getSimState();

                value = android.provider.Settings.System.getString(getContentResolver(),Settings.Secure.ADB_ENABLED);

                if((simState == TelephonyManager.SIM_STATE_ABSENT) &&
                        (value != null && "1".equals(value))){
                    flag = true;
                }
                else{
                    flag = false;
                }
                break;
            case 3:
                File Path = Environment.getExternalStorageDirectory();

                StatFs statFs = new StatFs(Path.getPath());

                long blockSize = statFs.getBlockSizeLong();

                long blockAvaliable = statFs.getAvailableBlocksLong();

                String avaliableSize = Formatter.formatFileSize(this, blockSize * blockAvaliable);

                long totalSize = getSectorTotalSize();

                Toast.makeText(this,"总存储空间 ："+totalSize +"G"+"可用存储空间 ："+avaliableSize,Toast.LENGTH_SHORT).show();

                break;
            case 4:
                Toast.makeText(AutotestItemDetailActivity.this, "请手动查看", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                MySQLiteOperator operator = new MySQLiteOperator();
                Cursor cursor = operator.launcherQuery();

                PackageManager pm = getPackageManager();
                List<PackageInfo> piList= pm.getInstalledPackages(0);

                if(cursor != null && cursor.getCount() > 0){
                    while(!cursor.isLast()){

                        value = cursor.getString(0);
                        int start = value.indexOf("component=");
                        int end = value.indexOf("/",start);
                        value = value.substring(start+10,end);

                        boolean findme = findme(value,piList);
                        if(findme == false){
                            flag = false;
                            break;
                        }else{
                            flag = true;
                        }
                        cursor.moveToNext();
                    }
                }
                break;
            case 6:
                value = android.provider.Settings.System.getString(getContentResolver(),Settings.Secure.DEFAULT_INPUT_METHOD);

                if(value != null && value.equals("com.iflytek.inputmethod/.FlyIME")){
                    flag = true;
                }else{
                    flag = false;
                }
                break;
            case 7:

                Toast.makeText(this,"受Sim卡空间限制，复制话机联系人到Sim卡上仅保存联系人名称和号码",Toast.LENGTH_SHORT).show();
                break;
            case 8:
                TELEPHONY_SERVICE = (TelephonyManager) getSystemService(
                        Service.TELEPHONY_SERVICE);
                simState = TELEPHONY_SERVICE.getSimState();
                if(simState == 5) {
                    Uri uri = Uri.parse("content://icc/adn");
                    ContentValues values = new ContentValues();

                    values.put("_id",10);
                    values.put("index",11);
                    values.put("name", " A SimCard Test Contacts");
                    values.put("number", "10086");
                    values.put("email", "test@163.com");
                    getContentResolver().insert(uri, values);

                    Toast.makeText(getApplicationContext(),"手动重启后查看联系人是否添加成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"请插入Sim卡进行测试",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean findme(String value, List<PackageInfo> piList) {

        boolean find = false;

        for(PackageInfo packageInfo :piList){
            if(value.equals(packageInfo.packageName)){
                find = true;
                break;
            }
        }
        return find;
    }

    private long getSectorTotalSize() {
        double totalSecSize = 0.00;

        BufferedReader br = null;

        try {

            File totalNode = new File("/sys/bus/mmc/devices/mmc0:0001/block/mmcblk0/size");

            br = new BufferedReader(new FileReader(totalNode));

            String total = br.readLine();

            totalSecSize = Double.parseDouble(total);

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            try {

                if(br != null){

                    br.close();

                }

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

        }

        double totalMemory = totalSecSize/2.0/1024.0/1024.0/0.91;

        long totalMemorySize = Math.round(totalMemory); //si she wu ru

        if (totalMemorySize >= 15 && totalMemorySize <= 16) {

            totalMemorySize = 16;

        }

        return totalMemorySize;
    }

    private static String getprop(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            value = (String)(   get.invoke(c, key, "unknown" ));
        } catch (Exception e) {
        }
        return value;
    }

    private void findButtonById() {
        B_Success = (Button) findViewById(R.id.button6);
        B_Fail = (Button) findViewById(R.id.button7);

        B_Success.setOnClickListener(this);
        B_Fail.setOnClickListener(this);
    }

    private void findTextViewById() {
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        textView.setText("测试第 "+id+" 项");
        textView2.setText(title+" : "+content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button6:
                break;
            case R.id.button7:
                break;
        }

        this.finish();
    }
}
