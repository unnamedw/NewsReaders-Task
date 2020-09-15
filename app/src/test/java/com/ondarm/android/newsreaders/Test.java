package com.ondarm.android.newsreaders;

import java.util.ArrayList;

public class Test {
    public void test() {
        ArrayList<Sample> sampleList = new ArrayList<Sample>();
        sampleList.add(new Sample("Sam"));
        System.out.println("Hello world!");
    }

    class Sample {
        String name;
        Sample(String name) {
            this.name = name;
        }
    }
}

