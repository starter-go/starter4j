package com.bitwormhole.starter4j.swing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentTemplate;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.ComponentTemplate.RegistrationT;
import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.base.StarterException;

public class SwingFrameManager implements FrameManager, LifeCycle {

    private static final Logger logger = LoggerFactory.getLogger(SwingFrameManager.class);

    private ApplicationContext context;
    private Class<?> mainFrameClass;
    private MyCache cache;

    public SwingFrameManager() {
    }

    private void innerShowFR(FrameRegistration fr, Goal goal) {
        JFrame frame = fr.getHolder().getFrame(goal);
        frame.setVisible(true);
    }

    private MyCache getCache() {
        MyCache c = this.cache;
        if (c == null) {
            c = this.loadCache();
            this.cache = c;
        }
        return c;
    }

    private MyCache loadCache() {
        MyCache c = new MyCache();
        List<Object> src = new ArrayList<>();
        src = this.context.selectComponents(".jframe", src);
        List<FrameRegistration> tmp = new ArrayList<>();
        for (Object obj : src) {
            if (obj instanceof FrameRegistry) {
                FrameRegistry fr1 = (FrameRegistry) obj;
                tmp = fr1.listRegistrations(tmp);
            }
        }
        for (FrameRegistration fr2 : tmp) {
            this.loadFrameToCache(c, fr2);
        }
        return c;
    }

    private void loadFrameToCache(MyCache c, FrameRegistration fr) {

        fr = this.loadFrameComplete(fr);

        String name = fr.getName();
        Class<?> clazz = fr.getType();

        c.table.put(MyCache.keyOf(name), fr);
        c.table.put(MyCache.keyOf(clazz), fr);

        logger.info("load [frame name:" + name + " class:" + clazz + "]");
    }

    private FrameRegistration loadFrameComplete(FrameRegistration fr) {

        fr = new FrameRegistration(fr);

        Class<?> type = fr.getType(); // required
        FrameFactory factory = fr.getFactory(); // required
        FrameHolder holder = fr.getHolder(); // optional
        String name = fr.getName();// optional

        if (type == null) {
            throw new StarterException("frame class (type) is nil");
        }
        if (factory == null) {
            throw new StarterException("FrameFactory is nil");
        }
        if (holder == null) {
            holder = new DefaultFrameHolder(fr);
            fr.setHolder(holder);
        }
        if (name == null) {
            name = type.getSimpleName();
            fr.setName(name);
        }
        return fr;
    }

    @Override
    public void show(Class<?> frameClass) {
        Goal goal = new Goal();
        FrameRegistration fr = this.find(frameClass);
        goal.setType(frameClass);
        this.innerShowFR(fr, goal);
    }

    @Override
    public void show(String frameName) {
        Goal goal = new Goal();
        FrameRegistration fr = this.find(frameName);
        goal.setName(frameName);
        this.innerShowFR(fr, goal);
    }

    @Override
    public FrameRegistration find(Class<?> frameClass) {
        return this.getCache().find(frameClass);
    }

    @Override
    public FrameRegistration find(String frameName) {
        return this.getCache().find(frameName);
    }

    @Override
    public String[] listNames() {
        MyCache c = this.getCache();
        String[] keys = c.keys();
        Set<String> dst = new HashSet<>();
        for (String key : keys) {
            FrameRegistration fr = c.findByKey(key);
            String name = fr.getName();
            dst.add(name);
        }
        return dst.toArray(new String[0]);
    }

    @Override
    public Life life() {
        Life l = new Life();
        l.onLoop = () -> this.runLoop1();
        return l;
    }

    private void runLoop1() {
        try {
            SwingUtilities.invokeAndWait(() -> this.runLoop2());
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runLoop2() {
        this.show(this.mainFrameClass);
    }

    private static final class MyCache {

        private final Map<String, FrameRegistration> table;

        public MyCache() {
            Map<String, FrameRegistration> t = new HashMap<>();
            this.table = Collections.synchronizedMap(t);
        }

        static String keyOf(String name) {
            return "frame:name:" + name;
        }

        static String keyOf(Class<?> clazz) {
            return "frame:class:" + clazz.getName();
        }

        FrameRegistration find(String name) {
            String key = keyOf(name);
            return this.findByKey(key);
        }

        FrameRegistration find(Class<?> clazz) {
            String key = keyOf(clazz);
            return this.findByKey(key);
        }

        FrameRegistration findByKey(String key) {
            FrameRegistration fr = this.table.get(key);
            if (fr == null) {
                throw new RuntimeException("no frame with key [" + key + "]");
            }
            return fr;
        }

        String[] keys() {
            List<String> dst = new ArrayList<>(this.table.keySet());
            return dst.toArray(new String[0]);
        }
    }

    private static final class MyComponentRegistryFunc implements ComponentRegistryFunc {

        @Override
        public void invoke(ComponentRegistry cr) throws StarterException {

            ComponentTemplate ct = new ComponentTemplate(cr);
            RegistrationT<SwingFrameManager> rt = ct.component(SwingFrameManager.class);
            rt.setId(SwingFrameManager.class);
            rt.onNew(() -> {
                return new SwingFrameManager();
            });
            rt.onInject((ie, o) -> {
                // final ComponentSelector cs = ComponentSelector.getInstance();

                // o.enabled = ie.getBoolean(cs.PROPERTY("debug.enabled"));
                // o.en_log__args = ie.getBoolean(cs.PROPERTY("debug.log-arguments"));
                // o.en_log___env = ie.getBoolean(cs.PROPERTY("debug.log-environment"));
                // o.en_log_props = ie.getBoolean(cs.PROPERTY("debug.log-properties"));

                // ie.get

                ApplicationContext ctx = ie.getContext();
                Class<?> mfc = (Class<?>) ctx.getAttributes().getAttr(SwingConst.MAIN_FRAME_CLASS, true);

                o.context = ctx;
                o.mainFrameClass = mfc;
            });
            rt.register();
        }
    }

    public static ComponentRegistryFunc registry() {
        return new MyComponentRegistryFunc();
    }

    @Override
    public void show(Goal goal) {

        FrameRegistration fr;
        Class<?> type = goal.getType();
        String name = goal.getName();

        if (type != null) {
            fr = this.find(type);
            this.innerShowFR(fr, goal);
            return;
        }

        if (name != null) {
            fr = this.find(name);
            this.innerShowFR(fr, goal);
            return;
        }

        throw new RuntimeException("bad goal: " + goal);
    }

}
