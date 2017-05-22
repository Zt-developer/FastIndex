package com.zhangtian.myapplication;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Fast on 2017/5/19.
 */

public class Item implements Comparable<Item>{
    public String name;
    public String pinyin;

    public Item(String name) {
        this.name = name;
        pinyin = PinYinUtils.getPinYin(name);
    }

    @Override
    public int compareTo(@NonNull Item o) {
        return this.pinyin.compareTo(o.pinyin);
    }
}
