package com.bitwormhole.starter4j.stopper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.afs.AFS;
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
        l.onCreate = this::onCreate;
        l.onLoop = this::runLoop;
        return l;
    }

    private void onCreate() {
        mControlFile = AFS.resolve(mControlFile);
        String action = this.mAction;
        if ("start".equalsIgnoreCase(action)) {
            this.handleActionStart();
        } else if ("stop".equalsIgnoreCase(action)) {
            this.handleActionStop();
        }
    }

    private void handleActionStart() {
        Path file = this.mControlFile;
        if (Files.exists(file)) {
            return;
        }
        Path dir = file.getParent();
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            byte[] buffer = new byte[0];
            Files.write(file, buffer, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            logger.error("cannot create file ", e);
        }
    }

    private void handleActionStop() {
        Path file = this.mControlFile;
        if (Files.exists(file) && Files.isRegularFile(file)) {
            try {
                long size = Files.size(file);
                if (size == 0) {
                    Files.delete(file);
                } else {
                    String path = file.toString();
                    logger.error("stopper: size of control-file is not 0; path={}", path);
                }
            } catch (IOException e) {
                logger.error("cannot delete file ", e);
            }
        }
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
