package com.example.zhangjinlu.automatictest;

import java.io.Serializable;

/**
 * Created by zhangjinlu on 2016/1/11.
 */
public class AutotestItem implements Serializable{
    public int id;
    public String content;
    public String title;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}

