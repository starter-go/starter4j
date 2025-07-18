package com.bitwormhole.starter4j.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.SwingUtilities;

import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.LifeManager;

public final class SwingFrameLifeAdapter implements IFrameLifeAdapter {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public LifeManager getManager() {
        return manager;
    }

    @Override
    public LifeManager getLifeManager() {
        return this.manager;
    }

    @Override
    public LifeCycle getLifeCycle() {
        return this.frame;
    }

    @Override
    public FrameWithLife getFrame() {
        return frame;
    }

    public static IFrameLifeAdapter createAdapter(FrameWithLife f) {

        if (f == null) {
            return null;
        }

        IFrameLifeAdapter ada1 = f.getLifeAdapter();
        if (ada1 != null) {
            return ada1;
        }

        SwingFrameLifeAdapter ada2 = new SwingFrameLifeAdapter(f);
        f.setLifeAdapter(ada2);
        f.setLifeManager(ada2.getLifeManager());

        SwingUtilities.invokeLater(() -> ada2.activate());
        return ada2;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final FrameWithLife frame;
    private final LifeManager manager;
    private boolean activated;
    private Life life;

    private SwingFrameLifeAdapter(FrameWithLife f) {
        this.manager = SwingFrameLifeManager.getInstance();
        this.frame = f;
    }

    private class MyWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // create
            run(life.onCreate);
        }

        @Override
        public void windowClosing(WindowEvent e) {
            // destroy
            run(life.onDestroy);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // destroy
            // run(life.onDestroy);
            run(null);
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // start
            run(life.onStart);
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // stop
            run(life.onStop);
        }
    }

    private class MyWindowStateListener implements WindowStateListener {

        @Override
        public void windowStateChanged(WindowEvent e) {
        }
    }

    private class MyWindowFocusListener implements WindowFocusListener {

        @Override
        public void windowGainedFocus(WindowEvent e) {
            // resume
            run(life.onResume);
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            // pause
            run(life.onPause);
        }
    }

    private static void run(Life.OnLifecycleFunction fn) {
        if (fn == null) {
            return;
        }
        try {
            fn.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activate() {

        if (this.activated) {
            return;
        }

        this.activated = true;
        this.life = this.frame.life();

        this.frame.addWindowListener(new MyWindowListener());
        this.frame.addWindowStateListener(new MyWindowStateListener());
        this.frame.addWindowFocusListener(new MyWindowFocusListener());
    }
}
