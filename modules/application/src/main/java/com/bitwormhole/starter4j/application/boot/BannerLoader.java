package com.bitwormhole.starter4j.application.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.Module;

public class BannerLoader {

    final static Logger logger = LoggerFactory.getLogger(BannerLoader.class);

    public void load(AppContextCore core) {
        try {
            String text = core.resources.readText("res:/banner.txt");
            String ver = this.getFrameworkVersion(core);
            core.banner = this.injectVersionInfo(text, ver);
        } catch (Exception e) {
            core.banner = "(no banner.txt)";
        }
    }

    public void loadAndLog(AppContextCore core) {
        this.load(core);
        logger.info("banner:\n" + core.banner);
        logger.info("application.profiles.active: " + core.profile);
    }

    private String injectVersionInfo(String text, String ver) {
        if (text == null) {
            text = "";
        }
        if (ver == null) {
            return text;
        }
        final String token = "{{starter.framework.version}}";
        return text.replace(token, ver);
    }

    private String getFrameworkVersion(AppContextCore core) {
        final String want = "com.bitwormhole.starter4j.Starter";
        for (Module m : core.modules) {
            String name = m.name();
            if (want.equals(name)) {
                String ver = m.version();
                int rev = m.revision();
                return "v" + ver + "-r" + rev;
            }
        }
        return "v0.0.0";
    }
}
