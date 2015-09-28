package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemDAO itemDao;
    private List<String> items;
    private ListView listView;
    private static final int REQUEST_CODE_EDIT_ITEM = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemDao = new ItemDAO(getFilesDir());

        listView = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>(itemDao.getItems());
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));

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
        ((ArrayAdapter) listView.getAdapter()).add(text);
        itemDao.saveItems(items);
        editText.setText("");
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Utils.hideKeyboard(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_ITEM) {
            Item itemData = (Item) data.getSerializableExtra("item");
            if(itemData == null) {
                Log.e(this.getClass().getSimpleName(), "Item data is missing from edit activity");
                return;
            }
            if(itemData.getPosition() > -1 && !itemData.getText().equals(items.get(itemData.getPosition()))) {
                items.set(itemData.getPosition(), itemData.getText());
                ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                itemDao.saveItems(items);
            }
        }
    }

        private void addItemListener(final ListView listView, final List<String> listItems) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = ((ArrayAdapter<String>)listView.getAdapter());
                listItems.remove(position);
                adapter.notifyDataSetChanged();
                itemDao.saveItems(listItems);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", new Item().setText(listItems.get(position)).setPosition(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
            }
        });
    }
}
