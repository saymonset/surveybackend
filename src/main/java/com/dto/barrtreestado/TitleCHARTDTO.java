package com.dto.barrtreestado;

/**
 * Created by simon on 6/10/2019.
 */
public class TitleCHARTDTO {
    private String text = "Stacked column char";

    public TitleCHARTDTO() {
        this.text = text;
    }

    public TitleCHARTDTO(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
