package com.stoyan.expenselist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Article> articleList = new ArrayList<Article>();

    public CustomAdapter() { }

    public CustomAdapter(List<Article> defaultArticles) {
        this.articleList = defaultArticles;
    }

    public void add(Article article) {
        articleList.add(article);
        notifyDataSetChanged();
    }

    public void remove(long id) {
        for (Article article : articleList) {
            if(article.getId() == id) {
                articleList.remove(article);
                break; // unique id.
            }
        }
        notifyDataSetChanged();
    }

    private final static class ViewHolder {
        public TextView label;
        public TextView price;
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

        final Article contact = articleList.get(position);
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
        holder.label.setText(contact.getLabel());
        holder.price.setText(contact.getPrice());

        return layout;
    }

    @Override
    public int getCount() {
        return articleList.size();
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
