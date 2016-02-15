package com.example.zhangjinlu.automatictest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zhangjinlu.automatictest.AutotestActivities.ManualModeMainActivity;

import java.util.List;

public class AutotestModeActivity extends Activity implements View.OnClickListener{

    private List<AutotestItem> items= null;
    private int itemCounts = 0;

    private void findButtonById(){

        //find button and initial onclick listener
        Button B_ATEST = (Button) findViewById(R.id.button4);
        Button B_HTEST = (Button) findViewById(R.id.button5);

        B_ATEST.setOnClickListener(this);
        B_HTEST.setOnClickListener(this);

    }

    private void findTextViewById(){

        //find TextView and update info

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText("总共 : "+itemCounts+" 项");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autotest_mode);

        //getintent extras info
        items = (List<AutotestItem>)getIntent().getSerializableExtra("serinfo");

        itemCounts = items.size();

        findTextViewById();

        findButtonById();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button4:
                while(itemCounts > 0){
                    AutotestItem item = items.get(itemCounts - 1);
                    Intent intent = new Intent(this,AutotestItemDetailActivity.class);
                    intent.putExtra("id",item.getId());
                    intent.putExtra("content",item.getContent());
                    intent.putExtra("title",item.getTitle());
                    itemCounts--;
                    startActivity(intent);
            }
                itemCounts = items.size();
                break;
            case R.id.button5:
                Intent intent = new Intent(this, ManualModeMainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
