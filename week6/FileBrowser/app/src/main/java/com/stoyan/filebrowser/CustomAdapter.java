package com.stoyan.filebrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private String ROOT_PATH;
    private String RELATIVE_PATH = "";
    private List<File> fileList = new ArrayList<File>();

    public CustomAdapter() {
        ROOT_PATH = Environment.getExternalStorageDirectory().getPath();

        File sd = Environment.getExternalStorageDirectory();
        File[] sdDirList = sd.listFiles();
        fileList = Arrays.asList(sdDirList);
    }

    public void add(File file) {
        fileList.add(file);
        notifyDataSetChanged();
    }

    public void setSource(Activity activity, int position) {
        File selectedFile = fileList.get(position);

        if(selectedFile.isDirectory()) {
            RELATIVE_PATH += File.separator + selectedFile.getName();
            viewAllFiles(RELATIVE_PATH);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            String ext = MimeTypeMap.getFileExtensionFromUrl(selectedFile.getName()); // Working
            String type = map.getMimeTypeFromExtension(ext);

            if (type == null) {
                type = "*/*";
            }

            //Uri data = Uri.fromFile(fileThatUserClickedOn); ----------------
            Uri data = Uri.fromFile(selectedFile);
            //Uri data = Uri.fromFile(Environment.getExternalStoragePublicDirectory(RELATIVE_PATH));
            intent.setDataAndType(data, type);
            activity.startActivity(intent);
        }
    }

    private void viewAllFiles(String path) {
        File sd = Environment.getExternalStoragePublicDirectory(path);
        File[] sdDirList = sd.listFiles();
        fileList = Arrays.asList(sdDirList);
        notifyDataSetChanged();
    }

    public boolean canGoPrevFolder() {
       if(RELATIVE_PATH.length() == 0) {
           return false;
       }

        //TODO: make this separate method, maybe.
        RELATIVE_PATH = RELATIVE_PATH.substring(0, RELATIVE_PATH.lastIndexOf(File.separator));
        viewAllFiles(RELATIVE_PATH);

        return true;
    }

    private final static class ViewHolder {
        public TextView fileItem;
        //public boolean isDir;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {

        LinearLayout layout = null;

        if (convertView != null) {
            layout = (LinearLayout) convertView;
        } else {
            layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linear_layout, null, false);

            TextView fileItem = (TextView) layout.findViewById(R.id.fileItem);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fileItem = fileItem;

            layout.setTag(viewHolder);
        }

        final File file = fileList.get(position);
        ViewHolder holder = (ViewHolder) layout.getTag();
        holder.fileItem.setText(file.getName());

        if(file.isDirectory()) {
            holder.fileItem.setTextColor(Color.YELLOW);
        } else {
            holder.fileItem.setTextColor(Color.WHITE);
        }

        return layout;
    }

    @Override
    public int getCount() {
        return fileList.size();
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
