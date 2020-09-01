package com.example.simpletodo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnadd;
    EditText etItem;
    RecyclerView rvitems;
    ItemsAdapter ItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd = findViewById(R.id.btnadd);
        etItem = findViewById(R.id.etItem);
        rvitems = findViewById(R.id.rvitems);


       loadItems();


                ItemsAdapter.OnLongClickListener onLongClickListener =  new ItemsAdapter.OnLongClickListener() {
                @Override
                public void OnItemLongClicked(int position) {
                    // delete the item from the model
                    items.remove(position);
                    //Notify the adapter
                    ItemsAdapter.notifyItemRemoved(position);
                    Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                    saveItems();


                }

            };


        ItemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvitems.setAdapter(ItemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        btnadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String todoitem = etItem.getText().toString();
                //add item to the model
                items.add(todoitem);
                //Notify adapter that an item is inserted
                ItemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }

        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
            }
        }
        private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        }catch (IOException e){
            Log.e("MainActivity", "Error reading items", e);
        }
        }

}