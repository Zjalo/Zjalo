package com.example.zhangjinlu.automatictest;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button B_YD_TEST = null ,B_LT_TEST = null ,B_DX_TEST = null;

    private Intent intent = null;

    //find button and set click listener
    private  void findButtonById(){
        B_YD_TEST = (Button) findViewById(R.id.button);
        B_LT_TEST = (Button) findViewById(R.id.button2);
        B_DX_TEST = (Button) findViewById(R.id.button3);

        B_YD_TEST.setOnClickListener(this);
        B_LT_TEST.setOnClickListener(this);
        B_DX_TEST.setOnClickListener(this);
    }

    //read Autotest items info and trans these info to next activity to use
    private  List<AutotestItem> readItemsInfo(MainActivity activity,int xmlid)  throws Exception{

        List<AutotestItem> items = null;

        AutotestItem item = null;

        Resources res = activity.getResources();

        XmlResourceParser xmlparser = res.getXml(xmlid);

        int event = xmlparser.getEventType();

        while(event != XmlPullParser.END_DOCUMENT){

            switch (event){

                case XmlPullParser.START_DOCUMENT :

                    items = new ArrayList<AutotestItem>();
                    break;

                case XmlPullParser.START_TAG :

                    if("testitem".equals(xmlparser.getName())){
                        int id = Integer.valueOf((xmlparser.getAttributeValue(0)));
                        item = new AutotestItem();
                        item.setId(id);
                    }
                    if("title".equals(xmlparser.getName())){
                        String title = xmlparser.nextText();
                        item.setTitle(title);
                    }
                    if("content".equals(xmlparser.getName())){
                        String content = xmlparser.nextText();
                        item.setContent(content);
                    }
                    break;

                case XmlPullParser.END_TAG :

                    if("testitem".equals(xmlparser.getName())){
                        items.add(item);
                        item = null;
                    }
                    break;

            }

            event = xmlparser.next();
        }

        return items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button initial
        findButtonById();

        //copy settings.db
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                List<AutotestItem> items;
                try {
                    items = readItemsInfo(this,R.xml.items);

                    //start activity to test mode with items info
                    intent = new Intent(this,AutotestModeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("serinfo", (Serializable) items);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                Toast.makeText(MainActivity.this, "暂时不支持联通项目", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(MainActivity.this, "暂时不支持电信项目", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
