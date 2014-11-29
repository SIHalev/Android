package com.stoyan.expenselist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private CustomAdapter adapter;
    private  List<Article> defaultArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: make Total cost last item.
        defaultArticles = new ArrayList<Article>() {{
            add(new Article("pizza", "10.99", 0));
            add(new Article("internet", "30.0", 1));
            add(new Article("beer", "7.68", 2));
            add(new Article("vodka", "33.99", 3));
        }};

        //adapter = new CustomAdapter();
        adapter = new CustomAdapter(defaultArticles);

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final EditText labelText = (EditText) findViewById(R.id.label_text);
        final EditText priceText = (EditText) findViewById(R.id.price_text);
        priceText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                System.currentTimeMillis();
                final View viewReference = v;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int height = viewReference.getHeight();
                        System.currentTimeMillis();
                    }
                });
            }
        });
        priceText.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                System.currentTimeMillis();
            }
        });

        final Button addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = labelText.getText().toString();
                String price = priceText.getText().toString();

                if(validateInput(label, price)) {
                    adapter.add(new Article(labelText.getText().toString(), priceText.getText().toString(), adapter.getCount()));
                }
            }
        });
    }

    private boolean validateInput(String label, String price) { // No real validation atm.
        if(label == null || price == null) {
            return false;
        }
        return true;
    }
}
