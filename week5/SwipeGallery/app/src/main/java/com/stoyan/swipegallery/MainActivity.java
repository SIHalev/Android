package com.stoyan.swipegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ArrayList<Bitmap> pictures;
    private File[] pictureFiles;
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictures = getAllPictures();

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), pictures);
        pager.setAdapter(adapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Bitmap> pictures;
        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Bitmap> pictures) {
            super(fm);
            this.pictures = pictures;
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment(pictures.get(position));
        }

        @Override
        public int getCount() {

            return pictures.size();
        }
    }

    private ArrayList<Bitmap> getAllPictures() {
        File files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        pictureFiles = files.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return new File(dir, filename).isDirectory() == false;
            }
        });

        pictures = new ArrayList<Bitmap>();
        for (int i = 0; i < pictureFiles.length; i++) {
            pictures.add(BitmapFactory.decodeFile(pictureFiles[i].getAbsolutePath()));
        }

        return pictures;
    }
}
