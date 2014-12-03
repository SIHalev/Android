package com.stoyan.listalldevices;
import android.bluetooth.BluetoothAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<String> bluetoothList = new ArrayList<String>();

    public void add(String name) {
        bluetoothList.add(name);
        notifyDataSetChanged();
    }

    private final static class ViewHolder {
        public TextView bluetoothName;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {

        LinearLayout layout = null;

        if (convertView != null) {
            layout = (LinearLayout) convertView;
        } else {
            layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linear_layout, null, false);

            TextView name = (TextView) layout.findViewById(R.id.bluetoothName);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.bluetoothName = name;

            layout.setTag(viewHolder);
        }

        final String value = bluetoothList.get(position);
        ViewHolder holder = (ViewHolder) layout.getTag();
        holder.bluetoothName.setText(value);

        return layout;
    }

    @Override
    public int getCount() {
        return bluetoothList.size();
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
