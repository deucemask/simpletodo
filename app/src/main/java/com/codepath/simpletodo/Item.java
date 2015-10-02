package com.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dmaskev on 10/1/15.
 */
@Table(name = "tasks")
public class Item extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    public String text;

    public Item() {
    }

    public Item(String text) {
        this.text = text;
    }

    public static List<Item> getAll() {
        return new Select().from(Item.class).execute();
    }


    public String toString() {
        return text;
    }

}
