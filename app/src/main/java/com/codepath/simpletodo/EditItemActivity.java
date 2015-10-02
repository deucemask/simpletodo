package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText itemEditText;
    private ItemContext itemContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemContext = (ItemContext)getIntent().getSerializableExtra("item");

        itemEditText = (EditText)findViewById(R.id.etEditItem);
        itemEditText.setText(itemContext.item.text);
        itemEditText.setSelection(itemContext.item.text.length());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onSave(View v) {
        Intent intent = new Intent();
        itemContext.item.text = itemEditText.getText().toString();
        intent.putExtra("item", itemContext);
        setResult(RESULT_OK, intent);
        Utils.hideKeyboard(this);
        finish();
    }
}
