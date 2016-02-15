package com.example.zhangjinlu.automatictest.AutotestActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangjinlu.automatictest.AutotestUtils.ItemTestMethod;
import com.example.zhangjinlu.automatictest.R;

import org.w3c.dom.Text;

import java.io.File;

public class SettingStorageActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_success,button_fail;

    private TextView textView3;

    public Boolean flag = true;

    public String avaliableSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_storage);

        findButtonById();

        ItemTestMethod mItemTestMethod = new ItemTestMethod();

        long totalSize = mItemTestMethod.getSectorTotalSize();

        avaliableSize = storage_test();

        textView3.setText("测试结果："+"总存储容量 "+totalSize+" GB , 剩余存储容量 "+avaliableSize);
    }

    private void findButtonById() {
        button_success = (Button) findViewById(R.id.button7);
        button_success.setOnClickListener(this);
        button_fail = (Button) findViewById(R.id.button6);
        button_fail.setOnClickListener(this);
        textView3 = (TextView) findViewById(R.id.textView3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button6:
                flag = false;
                break;
            case R.id.button7:
                flag = true;
                break;
        }
        Intent intent = new Intent();

        intent.putExtra("flag",flag);

        intent.putExtra("oriclass","SettingStorageActivity");

        setResult(-1,intent);

        finish();
    }
    public String storage_test(){
        File Path = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(Path.getPath());

        long blockSize = statFs.getBlockSizeLong();

        long blockAvaliable = statFs.getAvailableBlocksLong();

        avaliableSize = Formatter.formatFileSize(this, blockSize * blockAvaliable);

        return avaliableSize;
    }
}
