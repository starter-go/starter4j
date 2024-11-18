package com.bitwormhole.starter4j.stopper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.base.Time;

public class Stopper implements LifeCycle {

    final static Logger logger = LoggerFactory.getLogger(Stopper.class);

    private int mLoopInterval;
    private String mAction;
    private String mScope;
    private boolean mEnabled;
    private Path mControlFile;

    public Stopper() {
    }

    public static Module module() {
        return ThisModule.module();
    }

    public void setControlFile(String path) {
        if (path == null) {
            return;
        }
        path = path.trim();
        if (path.length() < 1) {
            return;
        }
        this.mControlFile = Paths.get(path);
    }

    public void setLoopInterval(int interval) {
        this.mLoopInterval = interval;
    }

    public void setScope(String scope) {
        this.mScope = scope;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    @Override
    public Life life() {
        Life l = new Life();
        if (!this.mEnabled) {
            return l;
        }
        l.order = 9999;
        l.onLoop = this::runLoop;
        return l;
    }

    private void runLoop() {

        final int min_interval = 10;
        int interval = this.mLoopInterval;
        Path file = this.mControlFile;

        if (interval < min_interval) {
            interval = min_interval;
        }

        if (file == null) {
            file = this.getDefaultFile();
        }

        this.logFile(file);

        for (;;) {
            if (!this.isFileExist(file)) {
                break;
            }
            Time.sleep(interval);
        }
    }

    private Path getDefaultFile() {
        Path file = Paths.get("~/.starter/default.stopper");
        file = file.toAbsolutePath();
        return file;
    }

    private void logFile(Path file) {
        String path = file.toString();
        logger.info("make stopper listener file @(" + path + ")");
    }

    private boolean isFileExist(Path file) {
        return Files.exists(file);
    }
}
