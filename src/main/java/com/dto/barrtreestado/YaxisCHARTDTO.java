package com.dto.barrtreestado;

/**
 * Created by simon on 6/10/2019.
 */
public class YaxisCHARTDTO {
    private int min = 0;
    private TitleCHARTDTO title = new TitleCHARTDTO("Total fruit consumption");

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public TitleCHARTDTO getTitle() {
        return title;
    }

    public void setTitle(TitleCHARTDTO title) {
        this.title = title;
    }
}
