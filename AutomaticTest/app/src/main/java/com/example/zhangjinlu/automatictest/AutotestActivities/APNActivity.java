package com.example.zhangjinlu.automatictest.AutotestActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangjinlu.automatictest.R;


/**
 * Created by zhangjinlu on 2016/2/14.
 */
public class APNActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_success,button_fail;

    private TextView textView3;

    public Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apn);
        findButtonById();
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

        intent.putExtra("oriclass","ApnActivity");

        setResult(-1,intent);

        finish();
    }
}
