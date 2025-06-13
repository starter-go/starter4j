package com.bitwormhole.starter4j.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.tasks.Promise;
import com.bitwormhole.starter4j.application.tasks.PromiseContext;
import com.bitwormhole.starter4j.application.tasks.Result;

public final class ExampleFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(ExampleFrame.class);

    private ExampleFrame() {
    }

    public static ExampleFrame create() {
        ExampleFrame inst = new ExampleFrame();
        inst.setSize(640, 480);
        inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        inst.initLayout();
        return inst;
    }

    private void initLayout() {

        Action action = new MyAction();
        JButton btn = new JButton("run task");
        btn.addActionListener((action_event) -> {
            // System.out.println("" + action_event);
            onClickButton();
        });
        btn.setAction(action);
        this.add(btn);
    }

    private void onClickButton() {

        PromiseContext ctx = SwingPromiseContext.getInstance();

        Promise.init(ctx, ExampleFrame.class).Try(() -> {
            logger.debug("this = " + this.getClass());
            ExampleFrame ef = ExampleFrame.this;
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

    public static class MyAction extends AbstractAction {

        MyAction() {
            putValue(Action.NAME, "(name)");
            putValue(Action.SHORT_DESCRIPTION, "(desc)");
            // putValue(Action.SMALL_ICON, ACCELERATOR_KEY);
            // putValue("color", ACCELERATOR_KEY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
