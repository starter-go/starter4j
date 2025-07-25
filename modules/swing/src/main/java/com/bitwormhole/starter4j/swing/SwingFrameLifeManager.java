package com.bitwormhole.starter4j.swing;

import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeManager;

public final class SwingFrameLifeManager implements LifeManager {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public static SwingFrameLifeManager getInstance() {
        return theInst;
    }

    @Override
    public void add(Life l) {
    }

    @Override
    public Life getMaster() {
        return this.mMaster;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private static final SwingFrameLifeManager theInst;

    private final Life mMaster;

    private SwingFrameLifeManager() {
        this.mMaster = new Life();
    }

    static {
        theInst = new SwingFrameLifeManager();
    }

}
