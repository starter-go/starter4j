package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Color;
import java.awt.Font;

public class BoxStyle {

    // color

    private Color backgroundColor; // 'background-color'
    private Color foregroundColor; // 'foreground-color'

    // border

    private Color borderTopColor; // .......... 'border-top-color'
    private LineStyle borderTopStyle; // ...... 'border-top-style'
    private int borderTopWidth; // ............ 'border-top-width' , <0 表示没有有效值

    private Color borderLeftColor; // ......... 'border-left-color'
    private LineStyle borderLeftStyle; // ..... 'border-left-style'
    private int borderLeftWidth; // ........... 'border-left-width' , <0 表示没有有效值

    private Color borderRightColor; // ........ 'border-right-color'
    private LineStyle borderRightStyle; // .... 'border-right-style'
    private int borderRightWidth; // .......... 'border-right-width' , <0 表示没有有效值

    private Color borderBottomColor; // ....... 'border-bottom-color'
    private LineStyle borderBottomStyle; // ... 'border-bottom-style'
    private int borderBottomWidth; // ......... 'border-bottom-width' , <0 表示没有有效值

    private Color borderColor; // ............. 'border-color'
    private LineStyle borderStyle; // ......... 'border-style'
    private int borderWidth; // ............... 'border-width' , <0 表示没有有效值

    // margin

    private int margin; // .............. 'margin' , <0 表示没有有效值
    private int marginTop; // ........... 'margin-top'
    private int marginLeft; // .......... 'margin-left'
    private int marginRight; // ......... 'margin-right'
    private int marginBottom; // ........ 'margin-bottom'

    // padding

    private int padding; // .............. 'padding' , <0 表示没有有效值
    private int paddingTop; // ........... 'padding-top'
    private int paddingLeft; // .......... 'padding-left'
    private int paddingRight; // ......... 'padding-right'
    private int paddingBottom; // ........ 'padding-bottom'

    // other

    private Font font; // ..................... 'font'
    private AlignEnum textAlign; // ........... 'text-align'
    private VisibilityEnum visibility; // ..... 'visibility'

    public BoxStyle() {
        this.innerInitAsDefault();
    }

    private void innerInitAsDefault() {

        this.backgroundColor = Color.WHITE;
        this.foregroundColor = Color.BLACK;

        // this.borderTopColor = Color.GRAY;
        // this.borderTopStyle = LineStyle.SOLID;
        // this.borderTopWidth = 1;

        // this.borderLeftColor = Color.GRAY;
        // this.borderLeftStyle = LineStyle.SOLID;
        // this.borderLeftWidth = 1;

        // this.borderRightColor = Color.GRAY;
        // this.borderRightStyle = LineStyle.SOLID;
        // this.borderRightWidth = 1;

        // this.borderBottomColor = Color.GRAY;
        // this.borderBottomStyle = LineStyle.SOLID;
        // this.borderBottomWidth = 1;

        this.borderWidth = -1;
        this.borderTopWidth = -1;
        this.borderLeftWidth = -1;
        this.borderRightWidth = -1;
        this.borderBottomWidth = -1;

        this.paddingTop = -1;
        this.paddingLeft = -1;
        this.paddingRight = -1;
        this.paddingBottom = -1;

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
        return Getters.getFirstAvailableValue(this.borderTopColor, this.borderColor);
    }

    public void setBorderTopColor(Color borderTopColor) {
        this.borderTopColor = borderTopColor;
    }

    public LineStyle getBorderTopStyle() {
        return Getters.getFirstAvailableValue(this.borderTopStyle, this.borderStyle);
    }

    public void setBorderTopStyle(LineStyle borderTopStyle) {
        this.borderTopStyle = borderTopStyle;
    }

    public int getBorderTopWidth() {
        return Getters.getFirstAvailableWidth(this.borderTopWidth, this.borderWidth);
    }

    public void setBorderTopWidth(int borderTopWidth) {
        this.borderTopWidth = borderTopWidth;
    }

    public Color getBorderLeftColor() {
        return Getters.getFirstAvailableValue(this.borderLeftColor, this.borderColor);
    }

    public void setBorderLeftColor(Color borderLeftColor) {
        this.borderLeftColor = borderLeftColor;
    }

    public LineStyle getBorderLeftStyle() {
        return Getters.getFirstAvailableValue(this.borderLeftStyle, this.borderStyle);
    }

    public void setBorderLeftStyle(LineStyle borderLeftStyle) {
        this.borderLeftStyle = borderLeftStyle;
    }

    public int getBorderLeftWidth() {
        return Getters.getFirstAvailableWidth(this.borderLeftWidth, this.borderWidth);
    }

    public void setBorderLeftWidth(int borderLeftWidth) {
        this.borderLeftWidth = borderLeftWidth;
    }

    public Color getBorderRightColor() {
        return Getters.getFirstAvailableValue(this.borderRightColor, this.borderColor);
    }

    public void setBorderRightColor(Color borderRightColor) {
        this.borderRightColor = borderRightColor;
    }

    public LineStyle getBorderRightStyle() {
        return Getters.getFirstAvailableValue(this.borderRightStyle, this.borderStyle);
    }

    public void setBorderRightStyle(LineStyle borderRightStyle) {
        this.borderRightStyle = borderRightStyle;
    }

    public int getBorderRightWidth() {
        return Getters.getFirstAvailableWidth(this.borderRightWidth, this.borderWidth);
    }

    public void setBorderRightWidth(int borderRightWidth) {
        this.borderRightWidth = borderRightWidth;
    }

    public Color getBorderBottomColor() {
        return Getters.getFirstAvailableValue(this.borderBottomColor, this.borderColor);
    }

    public void setBorderBottomColor(Color borderBottomColor) {
        this.borderBottomColor = borderBottomColor;
    }

    public LineStyle getBorderBottomStyle() {
        return Getters.getFirstAvailableValue(this.borderBottomStyle, this.borderStyle);
    }

    public void setBorderBottomStyle(LineStyle borderBottomStyle) {
        this.borderBottomStyle = borderBottomStyle;
    }

    public int getBorderBottomWidth() {
        return Getters.getFirstAvailableWidth(this.borderBottomWidth, this.borderWidth);
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

    public VisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public LineStyle getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(LineStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPaddingTop() {
        // return paddingTop;
        return Getters.getFirstAvailableWidth(this.paddingTop, this.padding);
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingLeft() {
        // return paddingLeft;
        return Getters.getFirstAvailableWidth(this.paddingLeft, this.padding);
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        // return paddingRight;
        return Getters.getFirstAvailableWidth(this.paddingRight, this.padding);
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingBottom() {
        // return paddingBottom;
        return Getters.getFirstAvailableWidth(this.paddingBottom, this.padding);
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

}
