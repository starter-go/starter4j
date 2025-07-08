package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Color;
import java.awt.Font;

public class BoxStyle {

    private Color backgroundColor; // 'background-color'
    private Color foregroundColor; // 'foreground-color'

    private Font font;

    private Color borderTopColor; // ....... 'border-top-color'
    private LineStyle borderTopStyle; // ... 'border-top-style'
    private int borderTopWidth; // ......... 'border-top-width'

    private Color borderLeftColor; // ....... 'border-left-color'
    private LineStyle borderLeftStyle; // ... 'border-left-style'
    private int borderLeftWidth; // ......... 'border-left-width'

    private Color borderRightColor; // ....... 'border-right-color'
    private LineStyle borderRightStyle; // ... 'border-right-style'
    private int borderRightWidth; // ......... 'border-right-width'

    private Color borderBottomColor; // ....... 'border-bottom-color'
    private LineStyle borderBottomStyle; // ... 'border-bottom-style'
    private int borderBottomWidth; // ......... 'border-bottom-width'

    private AlignEnum textAlign; // 'text-align'

    public BoxStyle() {
        this.innerInitAsDefault();
    }

    private void innerInitAsDefault() {

        this.backgroundColor = Color.WHITE;
        this.foregroundColor = Color.BLACK;

        this.borderTopColor = Color.GRAY;
        this.borderTopStyle = LineStyle.SOLID;
        this.borderTopWidth = 1;

        this.borderLeftColor = Color.GRAY;
        this.borderLeftStyle = LineStyle.SOLID;
        this.borderLeftWidth = 1;

        this.borderRightColor = Color.GRAY;
        this.borderRightStyle = LineStyle.SOLID;
        this.borderRightWidth = 1;

        this.borderBottomColor = Color.GRAY;
        this.borderBottomStyle = LineStyle.SOLID;
        this.borderBottomWidth = 1;

    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public Color getBorderTopColor() {
        return borderTopColor;
    }

    public void setBorderTopColor(Color borderTopColor) {
        this.borderTopColor = borderTopColor;
    }

    public LineStyle getBorderTopStyle() {
        return borderTopStyle;
    }

    public void setBorderTopStyle(LineStyle borderTopStyle) {
        this.borderTopStyle = borderTopStyle;
    }

    public int getBorderTopWidth() {
        return borderTopWidth;
    }

    public void setBorderTopWidth(int borderTopWidth) {
        this.borderTopWidth = borderTopWidth;
    }

    public Color getBorderLeftColor() {
        return borderLeftColor;
    }

    public void setBorderLeftColor(Color borderLeftColor) {
        this.borderLeftColor = borderLeftColor;
    }

    public LineStyle getBorderLeftStyle() {
        return borderLeftStyle;
    }

    public void setBorderLeftStyle(LineStyle borderLeftStyle) {
        this.borderLeftStyle = borderLeftStyle;
    }

    public int getBorderLeftWidth() {
        return borderLeftWidth;
    }

    public void setBorderLeftWidth(int borderLeftWidth) {
        this.borderLeftWidth = borderLeftWidth;
    }

    public Color getBorderRightColor() {
        return borderRightColor;
    }

    public void setBorderRightColor(Color borderRightColor) {
        this.borderRightColor = borderRightColor;
    }

    public LineStyle getBorderRightStyle() {
        return borderRightStyle;
    }

    public void setBorderRightStyle(LineStyle borderRightStyle) {
        this.borderRightStyle = borderRightStyle;
    }

    public int getBorderRightWidth() {
        return borderRightWidth;
    }

    public void setBorderRightWidth(int borderRightWidth) {
        this.borderRightWidth = borderRightWidth;
    }

    public Color getBorderBottomColor() {
        return borderBottomColor;
    }

    public void setBorderBottomColor(Color borderBottomColor) {
        this.borderBottomColor = borderBottomColor;
    }

    public LineStyle getBorderBottomStyle() {
        return borderBottomStyle;
    }

    public void setBorderBottomStyle(LineStyle borderBottomStyle) {
        this.borderBottomStyle = borderBottomStyle;
    }

    public int getBorderBottomWidth() {
        return borderBottomWidth;
    }

    public void setBorderBottomWidth(int borderBottomWidth) {
        this.borderBottomWidth = borderBottomWidth;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public AlignEnum getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(AlignEnum textAlign) {
        this.textAlign = textAlign;
    }

}
