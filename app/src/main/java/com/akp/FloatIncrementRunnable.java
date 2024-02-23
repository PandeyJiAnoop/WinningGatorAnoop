package com.akp;

import android.os.Handler;
import android.widget.TextView;

//public class FloatIncrementRunnable implements Runnable {
//    private final Handler handler;
//    private final TextView textView;
//    private final double startValue;
//    private final double endValue;
//    private final double increment;
//    private double value;
//    private boolean started;
//    private int speed;
//
//    public FloatIncrementRunnable(Handler handler, TextView textView, double startValue, double endValue, double increment,int speed) {
//        this.handler = handler;
//        this.textView = textView;
//        this.startValue = startValue;
//        this.endValue = endValue;
//        this.increment = increment;
//        this.value = startValue;
//        this.speed = speed;
//    }
//
//    @Override
//    public void run() {
//        if (started) {
//            textView.setText(String.format("%.1f", value));
//            if (value < endValue) {
//                value += increment;
//                handler.postDelayed(this, speed);
//            }
//        }
//    }
//
//    public void start() {
//        if (!started) {
//            started = true;
//            handler.post(this);
//        }
//    }
//
//    public void stop() {
//        started = false;
//        handler.removeCallbacks(this);
//    }
//}