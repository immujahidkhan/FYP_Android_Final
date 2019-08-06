package com.fyp.Utils;


import android.app.Application;

import com.fyp.ModelsClass.OrdersList;

import java.util.ArrayList;

public class MyApplication extends Application {
    public ArrayList<OrdersList> orderArrayList = null;

    public MyApplication() {
        orderArrayList = new ArrayList<>();
    }
}