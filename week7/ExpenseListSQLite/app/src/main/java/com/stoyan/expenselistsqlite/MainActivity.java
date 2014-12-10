package com.stoyan.expenselistsqlite;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private CustomAdapter adapter;
    private  List<Article> defaultArticles;
    private MainActivity mainIntent = this;

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///
        dbHelper = new MySQLiteHelper(this);
        database = dbHelper.getWritableDatabase();


        /*
        // TODO: make Total cost last item.
        defaultArticles = new ArrayList<Article>() {{
            add(new Article("pizza", "10.99", 0));
            add(new Article("internet", "30.0", 1));
            add(new Article("beer", "7.68", 2));
            add(new Article("vodka", "33.99", 3));
        }};

        //adapter = new CustomAdapter();
        adapter = new CustomAdapter(defaultArticles);
        */

        adapter = new CustomAdapter(database);

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final EditText labelText = (EditText) findViewById(R.id.label_text);
        final EditText priceText = (EditText) findViewById(R.id.price_text);

        final Button addButton = (Button) findViewById(R.id.add_button);


        // TODO: remove the duplicated code.
        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = labelText.getText().toString();
                String price = priceText.getText().toString();

                if(validateInput(label, price)) {
                   // adapter.add(new Article(labelText.getText().toString(), priceText.getText().toString(), adapter.getCount()));
                    adapter.add(labelText.getText().toString(), priceText.getText().toString());
                    labelText.setText("");
                    priceText.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String label = labelText.getText().toString();
                String price = priceText.getText().toString();
                // On click change the curr expense
                if(validateInput(label, price)) {
                    adapter.update(mainIntent, position, label, price);
                    labelText.setText("");
                    priceText.setText("");
                }
            }
        });
    }

    private boolean validateInput(String label, String price) { // No real validation atm.
        if(label.length() == 0 || price.length() == 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.close();
    }
}
