package com.bitwormhole.starter4j.swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.application.tasks.Promise;
import com.bitwormhole.starter4j.application.tasks.PromiseContext;
import com.bitwormhole.starter4j.application.tasks.Result;

public final class ExampleHomeFrame extends FrameWithLife {

    private static final Logger logger = LoggerFactory.getLogger(Example9LabelsFrame.class);

    public static ExampleHomeFrame create(Goal goal) {
        ApplicationContext ac = goal.getContext();
        ExampleHomeFrame inst = new ExampleHomeFrame(ac);
        inst.setSize(640, 480);
        inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        // inst.onCreate();
        return inst;
    }

    public static FrameRegistration registration() {
        FrameRegistration fr = new FrameRegistration();
        fr.setFactory((goal) -> create(goal));
        fr.setName(ExampleHomeFrame.class.getName());
        fr.setType(ExampleHomeFrame.class);
        fr.setSingleton(false);
        return fr;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final ApplicationContext mAC;

    private ExampleHomeFrame(ApplicationContext ac) {
        this.mAC = ac;
    }

    private void setupButton(String text, ActionListener li) {
        JButton btn = new JButton(text);
        btn.addActionListener(li);
        this.add(btn);
    }

    protected void onCreate() {
        this.onCreateLayout();
        this.onCreateSetupButtons();
    }

    private void onCreateLayout() {
        GridLayout layout = new GridLayout(5, 1);
        this.setLayout(layout);
    }

    private void onCreateSetupButtons() {
        this.setupButton("Try Promise", (ae) -> this.onClickButton1_try_promise(ae));
        this.setupButton("Show Canvas Frame", (ae) -> this.onClickButton2_show_canvas_frame(ae));
        this.setupButton("Show 9-Labels", (ae) -> this.onClickButton3_show_9_labels(ae));
        this.setupButton("Show Scrolling", (ae) -> this.onClickButton4_show_scrolling(ae));
    }

    private void onClickButton1_try_promise(ActionEvent event) {

        PromiseContext ctx = SwingPromiseContext.getInstance();

        Promise.init(ctx, ExampleHomeFrame.class).Try(() -> {
            logger.debug("this = " + this.getClass());
            ExampleHomeFrame ef = ExampleHomeFrame.this;
            return new Result<>(ef);
        }).Then((x) -> {
            logger.info("this = " + this.getClass());
            return x;
        }).Catch((x) -> {
            return x;
        }).Finally((x) -> {
            return x;
        }).start();
    }

    private void onClickButton2_show_canvas_frame(ActionEvent event) {
        ApplicationContext ac = this.mAC;
        ComponentSelector cs = ComponentSelector.getInstance();
        String sel = cs.ID(FrameManager.class);
        final FrameManager fm = ac.selectComponent(sel, FrameManager.class);
        final Goal goal = new Goal();
        goal.setContext(ac);
        goal.setFrameClass(ExampleCanvasFrame.class);
        fm.show(goal);
    }

    private void onClickButton4_show_scrolling(ActionEvent event) {

        ApplicationContext ac = this.mAC;
        ComponentSelector cs = ComponentSelector.getInstance();
        String sel = cs.ID(FrameManager.class);
        final FrameManager fm = ac.selectComponent(sel, FrameManager.class);
        final Goal goal = new Goal();
        goal.setContext(ac);
        goal.setFrameClass(ExampleScrollingFrame.class);
        fm.show(goal);

    }

    private void onClickButton3_show_9_labels(ActionEvent event) {

        ApplicationContext ac = this.mAC;
        ComponentSelector cs = ComponentSelector.getInstance();
        String sel = cs.ID(FrameManager.class);
        final FrameManager fm = ac.selectComponent(sel, FrameManager.class);
        final Goal goal = new Goal();
        goal.setContext(ac);
        goal.setFrameClass(Example9LabelsFrame.class);
        fm.show(goal);

    }

}
