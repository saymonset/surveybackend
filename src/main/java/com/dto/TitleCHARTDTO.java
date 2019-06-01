package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class TitleCHARTDTO {
   private String  text= "NPS - Net Promoter Score";
    private String  align ="center";
    private String verticalAlign = "middle";
    private int y =  40;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(String verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
