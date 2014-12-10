package com.stoyan.expenselistsqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    //TODO: make database operations in separate thread. Maybe AsyncTasks.

    private List<Expense> expenseList = new ArrayList<Expense>();
    private SQLiteDatabase database;

    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_EXPENSE_NAME,
            MySQLiteHelper.COLUMN_EXPENSE_PRICE
    };

//    public CustomAdapter() { }
//
//    public CustomAdapter(List<Expense> defaultExpenses) {
//        this.expenseList = defaultExpenses;
//    }

    public CustomAdapter(SQLiteDatabase database) {
        this.database = database;

        fillExpenseList();
    }

    private void fillExpenseList() {

        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Expense expense = cursorToExpense(cursor);
            expenseList.add(expense);
            cursor.moveToNext();
        }

        cursor.close();
    }

    private Expense cursorToExpense(Cursor cursor) {
        Expense expense = new Expense();
        expense.setId(cursor.getLong(0));
        expense.setName(cursor.getString(1));
        expense.setPrice(cursor.getString(2));

        return expense;
    }

    public void add(String name, String price) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXPENSE_NAME, name);
        values.put(MySQLiteHelper.COLUMN_EXPENSE_PRICE, price);
        long insertId = database.insert(MySQLiteHelper.TABLE_EXPENSES, null, values);

        Cursor cursor =
                database.query(
                        MySQLiteHelper.TABLE_EXPENSES,
                        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId
                        , null,null, null, null);

        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();

        expenseList.add(newExpense);
        notifyDataSetChanged();
    }

    public void update(Activity activity, int position, String label, String price) {
        Expense currExpense = expenseList.get(position);

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXPENSE_NAME, label);
        values.put(MySQLiteHelper.COLUMN_EXPENSE_PRICE, price);

        String filter = "_id=" + currExpense.getId();
        database.update(MySQLiteHelper.TABLE_EXPENSES, values, filter, null);

        currExpense.setName(label);
        currExpense.setPrice(price);
        notifyDataSetChanged();
    }

    private final static class ViewHolder {
        public TextView label;
        public TextView price;
    }

    public void remove(long id) {
        database.delete(MySQLiteHelper.TABLE_EXPENSES, MySQLiteHelper.COLUMN_ID + " = " + id, null);

        for (Expense expense : expenseList) {
            if(expense.getId() == id) {
                expenseList.remove(expense);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {

        LinearLayout layout = null;

        if (convertView != null) {
            layout = (LinearLayout) convertView;
        } else {
            layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linear_layout, null, false);

            TextView label = (TextView) layout.findViewById(R.id.label);
            TextView price = (TextView) layout.findViewById(R.id.price);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.label = label;
            viewHolder.price = price;

            layout.setTag(viewHolder);
        }

        final Expense contact = expenseList.get(position);
        ImageView deleteButton = (ImageView) layout.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());

                AlertDialog dialog = builder.setTitle("Warning").setMessage(R.string.warning_message)
                        .setPositiveButton(R.string.positive_answer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(contact.getId());
                    }
                }).setNegativeButton(R.string.negative_answer, null).create();

                dialog.show();
            }
        });

        ViewHolder holder = (ViewHolder) layout.getTag();
        holder.label.setText(contact.getName());
        holder.price.setText(contact.getPrice());

        return layout;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
