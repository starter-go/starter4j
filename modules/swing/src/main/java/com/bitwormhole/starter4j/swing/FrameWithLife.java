package com.bitwormhole.starter4j.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.Life;

public class FrameWithLife extends FrameWithLifeAbs {

    static final Logger logger = LoggerFactory.getLogger(FrameWithLife.class);

    public FrameWithLife() {
        SwingFrameLifeAdapter.createAdapter(this);
    }

    @Override
    public Life life() {
        Life l = new Life();
        l.onCreate = () -> this.onCreate();
        l.onStart = () -> this.onStart();
        l.onResume = () -> this.onResume();
        l.onPause = () -> this.onPause();
        l.onStop = () -> this.onStop();
        l.onDestroy = () -> this.onDestroy();
        return l;
    }

    @Override
    protected void onCreate() {
        this.log_life_fn("onCreate");
    }

    @Override
    protected void onStart() {
        this.log_life_fn("onStart");
    }

    @Override
    protected void onStop() {
        this.log_life_fn("onStop");
    }

    @Override
    protected void onDestroy() {
        this.log_life_fn("onDestroy");
    }

    @Override
    protected void onResume() {
        this.log_life_fn("onResume");
    }

    @Override
    protected void onPause() {
        this.log_life_fn("onPause");
    }

    private final void log_life_fn(String fn) {
        String cn = this.getClass().getSimpleName();
        logger.info(cn + "." + fn + "()");
    }

}
