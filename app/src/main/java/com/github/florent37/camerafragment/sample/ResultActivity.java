package com.github.florent37.camerafragment.sample;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 3/7/17.
 */

public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.pager)
    ViewPager mPager;
    ArrayList<Video> urlPath;
    VideoPagerAdapter mPagerAdapter;

    private static final String TAG = ResultActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.github.florent37.camerafragment.R.layout.activity_pager);
        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        urlPath = new ArrayList<>();
        setData();

        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager(), urlPath);
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                VideoPreviewFragment fragment = (VideoPreviewFragment) mPagerAdapter.getItem(position);
                Video video = urlPath.get(position);
                fragment.setName(video.getName());
                fragment.setUserVisibleHint(true);


                Log.e(TAG, "fragment position getUserVisibleHint = " + fragment.getUserVisibleHint() + " position = " + position);
                Log.e(TAG, "fragment position isMenuVisible = " + fragment.isMenuVisible() + " position = " + position);
                Log.e(TAG, "fragment position isResumed = " + fragment.isResumed() + " position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        VideoPreviewFragment fragment = (VideoPreviewFragment) mPagerAdapter.getItem(0);
        Video video = urlPath.get(0);
        fragment.setName(video.getName());

    }


    private void setData() {
        Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.sample_360);
        urlPath.add(new Video(VideoPreviewFragment.newInstance(), "video 1", uri.toString()));
        urlPath.add(new Video(VideoPreviewFragment.newInstance(), "video 2", uri.toString()));
        urlPath.add(new Video(VideoPreviewFragment.newInstance(), "video 3", uri.toString()));
    }


}
