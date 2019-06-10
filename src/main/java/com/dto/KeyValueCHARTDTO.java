package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class KeyValueCHARTDTO {
        private String name;
        private float y;


    private String color; //: '#0A0A0A',



    public void KeyValueCHARTDTO(){}

    public KeyValueCHARTDTO(String name, float y, String color) {
        this.name = name;
        this.y = y;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
