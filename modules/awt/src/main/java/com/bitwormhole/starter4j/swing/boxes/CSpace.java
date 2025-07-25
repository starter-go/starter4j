package com.bitwormhole.starter4j.swing.boxes;

import com.bitwormhole.starter4j.swing.canvases.BoxEntity;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;

public class CSpace extends BoxEntity {

    public CSpace() {
        this.onCreate();
    }

    private void onCreate() {
        BoxStyle st = new BoxStyle();

        st.setBackgroundColor(null);
        st.setForegroundColor(null);

        st.setBorderStyle(LineStyle.NONE);
        st.setBorderColor(null);
        st.setBorderWidth(0);

        this.setStyle(st);
    }

}
