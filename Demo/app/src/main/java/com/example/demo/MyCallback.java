package com.example.demo;


import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface MyCallback {
    void onCallBack1(ArrayList<DateObject> value);
    void onCallBack2(List<FoodInfo> value) throws Exception;
}
