package com.codepath.simpletodo;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by dmaskev on 9/27/15.
 */
public class ItemDAO {

    private File root;
    private File dataFile;
    public ItemDAO(File root) {
        this.root = root;
        this.dataFile = new File(root, "todo.txt");
        if(!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), "Failed to initialize data file " + dataFile, e);
            }
        }
    }

    public List<String> getItems() {
        List<String> res = Collections.emptyList();
        try {
            res = FileUtils.readLines(dataFile);
        } catch(IOException e) {
            Log.e(this.getClass().getSimpleName(), "Failed to load items from file " + dataFile, e);
        }
        return res;
    }

    public void saveItems(List<String> items) {
        try {
            FileUtils.writeLines(dataFile, items);
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Failed to save items to file " + dataFile, e);
        }
    }
}
