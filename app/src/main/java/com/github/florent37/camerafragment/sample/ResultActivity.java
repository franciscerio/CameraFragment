package com.github.florent37.camerafragment.sample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
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

    private int mLastPage = 0;

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

        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager(), media.getVideos());
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //pause and play
                VideoPreviewFragment fragment = (VideoPreviewFragment) mPagerAdapter.getItem(position);
                VideoPreviewFragment fragmentMedia = (VideoPreviewFragment) mPagerAdapter.getItem(mLastPage);
                fragmentMedia.hideController();

                fragment.setUserVisibleHint(true);
                int currentPosition = fragmentMedia.getMediaCurrentPosition();

                //get lastpage mediaplayer seek length
                Log.e(TAG, "position = " + position);
                Log.e(TAG, "mLastPage = " + mLastPage);
                Log.e(TAG, "currentPosition = " + currentPosition);
                fragment.setCurrentPlaybackPosition(fragmentMedia.isVideoComplete() ? 0 : currentPosition);
                Log.e(TAG, "===========================================================================================");
                Log.e(TAG, "===========================================================================================");
//                Log.e(TAG, "fragment position getUserVisibleHint = " + fragment.getUserVisibleHint() + " position = " + position);
//                Log.e(TAG, "fragment position isMenuVisible = " + fragment.isMenuVisible() + " position = " + position);
//                Log.e(TAG, "fragment position isResumed = " + fragment.isResumed() + " position = " + position);
                mLastPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        VideoPreviewFragment fragment = (VideoPreviewFragment) mPagerAdapter.getItem(0);
//        Video video = urlPath.get(0);
//        fragment.setName(video.getName());

    }

    private void setData() {
        Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.video1);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video1.mp4";
        File file = new File(path);

        boolean isExist = file.exists();
        String videoPath = file.getAbsolutePath();

        media = new Media();
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", uri.toString()), "video 1", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", uri.toString()), "video 2", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", uri.toString()), "video 3", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", uri.toString()), "video 4", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", uri.toString()), "video 5", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", uri.toString()), "video 6", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", uri.toString()), "video 7", uri.toString()));

        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", uri.toString())));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 8", uri.toString())));

//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", videoPath)));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 8", videoPath)));


        media.setVideos(urlPath);
    }

    private Media media;

}
