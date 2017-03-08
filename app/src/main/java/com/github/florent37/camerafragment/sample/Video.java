package com.github.florent37.camerafragment.sample;

import android.support.v4.app.Fragment;

/**
 * Created by android on 3/7/17.
 */

public class Video {

    private String name;
    private String path;
    private Fragment fragment;

    public Video(Fragment fragment, String name, String path) {
        this.name = name;
        this.path = path;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
