package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> items;
    private ListView listView;
    private static final int REQUEST_CODE_EDIT_ITEM = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvItems);
        items = Item.getAll();
        listView.setAdapter(new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items));

        addItemListener(listView, items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText editText = (EditText) findViewById(R.id.etNewItem);
        String text = editText.getText().toString();
        Item item = new Item(text);
        ((ArrayAdapter) listView.getAdapter()).add(item);
        item.save();
        editText.setText("");
        Utils.hideKeyboard(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_ITEM) {
            Item item = (Item) data.getSerializableExtra("item");
            if(item == null) {
                Log.e(this.getClass().getSimpleName(), "Item data is missing from edit activity");
                return;
            }
            if(item.position > -1 && item.text != null
                    && !item.text.equals(items.get(item.position))) {
                //after deserialization item doesn't have the id, so we can't just save this instance to db.
                //instead, we copy fields from deserialized item to our existing item from items list.
                Item existingItem = items.get(item.position);
                existingItem.text = item.text;
                //this items.set() is redundant
                items.set(item.position, existingItem);
                ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                existingItem.save();
            }
        }
    }

    private void addItemListener(final ListView listView, final List<Item> listItems) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = ((ArrayAdapter<String>) listView.getAdapter());
                Item itemForRemoval = listItems.get(position);
                listItems.remove(position);
                adapter.notifyDataSetChanged();
                itemForRemoval.delete();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                Log.d(this.getClass().getSimpleName(), "item " + listItems.get(position).getId());
                intent.putExtra("item", listItems.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
            }
        });
    }
}
