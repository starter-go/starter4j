package com.bitwormhole.starter4j.afs;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFSTest {

    final static Logger logger = LoggerFactory.getLogger(AFSTest.class);

    @Test
    public void testPathNormalize() {

        List<String> list = new ArrayList<>();

        list.add("g:/a/b///c/d");
        list.add("h:/a/./b/c");
        list.add("i:/a/../b/c");
        list.add("j:/a/~/b/c");
        list.add("~/.a/b/c");
        list.add("/.a/b/c");

        for (String path : list) {
            Path p1 = Paths.get(path);
            Path p2 = AFS.resolve(p1);
            logger.info("AFS.resolve({}) : result.path({})", p1, p2);
        }
    }
}
