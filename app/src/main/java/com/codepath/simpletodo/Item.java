package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Created by dmaskev on 9/27/15.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private int position;

    public String getText() {
        return text;
    }

    public Item setText(String text) {
        this.text = text;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public Item setPosition(int position) {
        this.position = position;
        return this;
    }


}
