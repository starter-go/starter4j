package com.bitwormhole.starter4j.application.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerLoader {

    final static Logger logger = LoggerFactory.getLogger(BannerLoader.class);

    public void load(AppContextCore core) {
        try {
            String text = core.resources.readText("res:/banner.txt");
            core.banner = text;
        } catch (Exception e) {
            core.banner = "(no banner.txt)";
        }
    }

    public void loadAndLog(AppContextCore core) {
        this.load(core);
        logger.info("banner:\n" + core.banner);
        logger.info("application.profiles.active: " + core.profile);
    }

}
