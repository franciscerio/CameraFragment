package com.github.florent37.camerafragment.sample;

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                VideoPreviewFragment fragmentCurrentPosition = (VideoPreviewFragment) mPagerAdapter.getItem(position);
                VideoPreviewFragment fragmentLastPosition = (VideoPreviewFragment) mPagerAdapter.getItem(mLastPage);
//                fragment.onResume();
//                fragmentMedia.onPause();
//                fragmentMedia.hideController();

//                fragment.setUserVisibleHint(true);
                int currentPosition = fragmentLastPosition.getCurrentPositionWhenPlaying();
                fragmentCurrentPosition.setCurrentPositionWhenPlaying(currentPosition);
                //get lastpage mediaplayer seek length
//                Log.e(TAG, "position = " + position);
//                Log.e(TAG, "mLastPage = " + mLastPage);
//                Log.e(TAG, "currentPosition = " + currentPosition);
//                fragment.setCurrentPlaybackPosition(fragmentMedia.isVideoComplete() ? 0 : currentPosition);
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
//        Uri uri = Uri.parse("file:///android_asset/video1.mp4");
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video1.mp4";
//        String path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Advanced Android Espresso.mp4";
//        String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Advanced Android Espresso (Big Android BBQ 2015).mp4";
        String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video1.mp4";
        String path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video2.mp4";
        String path3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video3.mp4";
        String path4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video4.mp4";
        String path5 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "video5.mp4";
//        File file = new File(uri.toString());
//        boolean isExist = file.exists();
//        String videoPath = file.getAbsolutePath();
//        String videoPath = uri.toString();

        media = new Media();
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", uri.toString()), "video 1", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", uri.toString()), "video 2", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", uri.toString()), "video 3", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", uri.toString()), "video 4", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", uri.toString()), "video 5", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", uri.toString()), "video 6", uri.toString()));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", uri.toString()), "video 7", uri.toString()));

//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", uri.toString())));
//        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 8", uri.toString())));

        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 1", path1)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 2", path2)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 3", path3)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 4", path4)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 5", path5)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 6", path1)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 7", path2)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("video 8", path3)));


        media.setVideos(urlPath);
    }

    private Media media;

}
