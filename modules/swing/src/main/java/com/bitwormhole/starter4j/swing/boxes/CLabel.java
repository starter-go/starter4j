package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.bitwormhole.starter4j.swing.canvases.AlignEnum;
import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxEntity;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.Getters;
import com.bitwormhole.starter4j.swing.canvases.RenderContext;

/********************************
 * CLabel：Canvas-Label
 */
public class CLabel extends BoxEntity {

    private String text;

    public CLabel() {
        this.text = "";
    }

    public CLabel(String txt) {
        this.text = txt;
    }

    @Override
    protected void onPaintBackground(RenderContext rc) {
        super.onPaintBackground(rc);
    }

    @Override
    protected void onPaintForeground(RenderContext rc) {

        BoxStyle style1 = this.getStyle();
        prepareStyle(style1);

        Graphics g = rc.getGraphics();
        String str = this.text;
        Color fgColor = style1.getForegroundColor();
        Font font = style1.getFont();

        TextLocationComputer tlc = new TextLocationComputer();
        tlc.init(this, str, style1);
        Point pt1 = tlc.location();
        Point pt2 = local2canvas(pt1);

        g.setFont(font);
        g.setColor(fgColor);
        g.drawString(str, pt2.x, pt2.y);

        super.onPaintForeground(rc);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    ////////////////////////////////////////////////////
    /// private
    ///

    private Point local2canvas(Point pt1) {
        Point off = this.getPositionAtCanvas();
        return new Point(off.x + pt1.x, off.y + pt1.y);
    }

    private static void prepareStyle(BoxStyle st) {

        // font
        Font font = st.getFont();
        if (font == null) {
            font = Getters.notNull(font);
            st.setFont(font);
        }

        // fg-color
        Color fg = st.getForegroundColor();
        if (fg == null) {
            fg = Getters.notNull(fg);
            st.setForegroundColor(fg);
        }

        // align
        AlignEnum align = st.getTextAlign();
        if (align == null) {
            align = Getters.notNull(align);
            st.setTextAlign(align);
        }
    }

    private static class TextLocationComputer {

        // String text;

        double text_w, text_h, box_w, box_h;
        AlignEnum align;
        BoxStyle style;

        void init(Box box, String txt, BoxStyle _style) {

            Dimension box_size = box.getSize();
            Font font = Getters.notNull(_style.getFont());

            AffineTransform at = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(at, true, true);
            Rectangle2D txt_rect = font.getStringBounds(txt, frc);

            this.box_h = box_size.getHeight();
            this.box_w = box_size.getWidth();
            this.text_h = txt_rect.getHeight();
            this.text_w = txt_rect.getWidth();
            this.align = _style.getTextAlign();
            this.style = _style;
        }

        Point location() {
            switch (align) {
                case LEFT:
                    return this.computeLocationLeft();
                case RIGHT:
                    return this.computeLocationRight();
                case TOP:
                    return this.computeLocationTop();
                case BOTTOM:
                    return this.computeLocationBottom();
                case TOP_LEFT:
                    return this.computeLocationTopLeft();
                case TOP_RIGHT:
                    return this.computeLocationTopRight();
                case BOTTOM_LEFT:
                    return this.computeLocationBottomLeft();
                case BOTTOM_RIGHT:
                    return this.computeLocationBottomRight();
                default: // CENTER
                    break;
            }
            return this.computeLocationCenter();
        }

        Point computeLocationTopLeft() {
            final int p_top = this.style.getPaddingTop();
            final int p_left = this.style.getPaddingLeft();
            double x = 0 + p_left;
            double y = text_h + p_top;
            return new Point((int) x, (int) y);
        }

        Point computeLocationTopRight() {
            final int p_top = this.style.getPaddingTop();
            final int p_right = this.style.getPaddingRight();
            double x = (box_w - text_w) - p_right;
            double y = text_h + p_top;
            return new Point((int) x, (int) y);
        }

        Point computeLocationBottomLeft() {
            final int p_bottom = this.style.getPaddingBottom();
            final int p_left = this.style.getPaddingLeft();
            double x = 0 + p_left;
            double y = box_h - p_bottom;
            return new Point((int) x, (int) y);
        }

        Point computeLocationBottomRight() {
            final int p_bottom = this.style.getPaddingBottom();
            final int p_right = this.style.getPaddingRight();
            double x = (box_w - text_w) - p_right;
            double y = box_h - p_bottom;
            return new Point((int) x, (int) y);
        }

        Point computeLocationLeft() {
            final int p_left = this.style.getPaddingLeft();
            double x = 0 + p_left;
            double y = (box_h / 2) + (text_h / 4);
            return new Point((int) x, (int) y);
        }

        Point computeLocationRight() {
            final int p_right = this.style.getPaddingRight();
            double x = (box_w - text_w) - p_right;
            double y = (box_h / 2) + (text_h / 4);
            return new Point((int) x, (int) y);
        }

        Point computeLocationBottom() {
            final int p_bottom = this.style.getPaddingBottom();
            double x = (box_w - text_w) / 2;
            double y = box_h - p_bottom;
            return new Point((int) x, (int) y);
        }

        Point computeLocationTop() {
            final int p_top = this.style.getPaddingTop();
            double x = (box_w - text_w) / 2;
            double y = text_h + p_top;
            return new Point((int) x, (int) y);
        }

        Point computeLocationCenter() {
            double x = (box_w - text_w) / 2;
            double y = (box_h / 2) + (text_h / 4);
            return new Point((int) x, (int) y);
        }
    }
}
