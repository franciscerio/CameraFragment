package com.github.florent37.camerafragment.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VideoPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Video> videoFragments = new ArrayList<>();

    public VideoPagerAdapter(FragmentManager fm, ArrayList<Video> videoFragments) {
        super(fm);
        this.videoFragments = videoFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return videoFragments.get(position).getFragment();
    }



    @Override
    public int getCount() {
        return videoFragments.size();
    }
}
