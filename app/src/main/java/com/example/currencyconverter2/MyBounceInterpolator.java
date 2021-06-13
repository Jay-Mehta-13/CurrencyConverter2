package com.example.currencyconverter2;

import android.view.animation.Interpolator;

public class MyBounceInterpolator implements Interpolator {

    private double myAmp = 10;
    private double myfreq = 10;

    MyBounceInterpolator(double amp, double freq){
        myAmp=amp;
        myfreq=freq;
    }
    @Override
    public float getInterpolation(float time) {
        return (float)(-1*Math.pow(Math.E, -time/myAmp)*Math.sin(myfreq*time)+1);
    }
}
