package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Wrapper around Item entity. Needed to include id field into serialization as a work-around for
 * dealing with activeandroid.Model.mId private field
 *
 * Created by dmaskev on 10/1/15.
 */
public class ItemContext implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public Item item;
    public int position;

    public ItemContext(Item item) {
        this.id = item.getId();
        this.item = item;
    }

    public ItemContext() {}

}
