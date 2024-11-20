package com.bitwormhole.starter4j.afs;

import java.nio.file.Path;
import java.nio.file.Paths;

/****
 * 对于 java 版的 AFS， 直接使用 java.nio.file.Path 作为抽象的文件路径接口
 */
public final class AFS {

    private AFS() {
    }

    public static Path resolve(Path p) {
        if (p == null) {
            return null;
        }
        String str = p.toString();
        if (UserHomePathResolver.needResolve(str)) {
            str = UserHomePathResolver.resolve(str);
            p = Paths.get(str);
        }
        return p.normalize();
    }
}
