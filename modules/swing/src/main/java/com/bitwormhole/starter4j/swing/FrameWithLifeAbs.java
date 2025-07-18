package com.bitwormhole.starter4j.swing;

import javax.swing.JFrame;

import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.LifeManager;

public abstract class FrameWithLifeAbs extends JFrame implements LifeCycle {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public LifeManager getLifeManager() {
        return lifeManager;
    }

    public void setLifeManager(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
    }

    public IFrameLifeAdapter getLifeAdapter() {
        return lifeAdapter;
    }

    public void setLifeAdapter(IFrameLifeAdapter lifeAdapter) {
        this.lifeAdapter = lifeAdapter;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    protected abstract void onCreate();

    protected abstract void onStart();

    protected abstract void onResume();

    protected abstract void onPause();

    protected abstract void onStop();

    protected abstract void onDestroy();

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private LifeManager lifeManager;
    private IFrameLifeAdapter lifeAdapter;

}
