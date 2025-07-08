package com.bitwormhole.starter4j.swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.application.tasks.Promise;
import com.bitwormhole.starter4j.application.tasks.PromiseContext;
import com.bitwormhole.starter4j.application.tasks.Result;

public final class ExampleFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(ExampleFrame.class);

    private ExampleFrame() {
    }

    public static ExampleFrame create(Goal goal) {
        ExampleFrame inst = new ExampleFrame();
        inst.setSize(640, 480);
        inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        inst.initLayout(goal.getContext());
        return inst;
    }

    private void initLayout(ApplicationContext ac) {

        GridLayout layout = new GridLayout(5, 1);
        this.setLayout(layout);

        this.setupButton1();
        this.setupButton2(ac);
    }

    private void setupButton1() {

        Action action = new MyAction();
        JButton btn = new JButton("run task");
        btn.addActionListener((action_event) -> {
            // System.out.println("" + action_event);
            onClickButton1();
        });
        btn.setAction(action);
        this.add(btn);
    }

    private void setupButton2(ApplicationContext ac) {

        ComponentSelector cs = ComponentSelector.getInstance();
        String sel = cs.ID(FrameManager.class);

        final FrameManager fm = ac.selectComponent(sel, FrameManager.class);
        final Goal goal = new Goal();
        goal.setContext(ac);
        goal.setFrameClass(ExampleCanvasFrame.class);

        JButton btn = new JButton("show canvas frame");
        btn.addActionListener((ae) -> {
            fm.show(goal);
        });
        this.add(btn);
    }

    private void onClickButton1() {

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

    // private final static class MyFactory implements FrameFactory {
    // @Override
    // public JFrame createFrame(Goal goal) {

    // // JFrame f = new ExampleFrame();
    // // f.setSize(640, 480);
    // // f.setVisible(true);
    // // f.setTitle(ExampleFrame.class.getName());
    // // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // return create();
    // }
    // }

    public static FrameRegistration registration() {
        // MyFactory factory = new MyFactory();
        FrameRegistration fr = new FrameRegistration();
        fr.setFactory((goal) -> create(goal));
        fr.setName(ExampleFrame.class.getName());
        fr.setType(ExampleFrame.class);
        fr.setSingleton(false);
        return fr;
    }

}
