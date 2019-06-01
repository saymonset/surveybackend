package com.dto;

/**
 * Created by simon on 5/31/2019.
 */
public class Serie2 <A, B> implements Serie1<A, B> {
    private A value1;
    private B value2;

    public A getValue1() {
        return value1;
    }

    public void setValue1(A value1) {
        this.value1 = value1;
    }

    public B getValue2() {
        return value2;
    }

    public void setValue2(B value2) {
        this.value2 = value2;
    }
}
