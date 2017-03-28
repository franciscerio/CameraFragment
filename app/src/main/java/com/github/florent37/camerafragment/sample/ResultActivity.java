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
//        //4k 17:9, 12fps   // NOT WORKING
        String name1 = "4k-17|9 12fps.mp4";
        String name2 = name1;
        String name3 = name1;


//        //4k 15fps      // WORKING
//        String name1= "4k15fps.mp4";
//        String name2= name1;
//        String name3= name1;

//        //2.7k 17:9 24fps  // WORKING
//        String name1 = "2|7k-17|9-24fps.mp4";
//        String name2 = name1;
//        String name3 = name1;

//        //2.7k 30fps         // WORKING
//        String name1 = "2|7k-30fps.mp4";
//        String name2 = name1;
//        String name3 = name1;

        //1440 24fps        // WORKING
//        String name1 = "1440-24fps.mp4";
//        String name2 = name1;
//        String name3 = name1;

        //1440 30fps        // WORKING
//        String name1 = "1440-30fps.mp4";
//        String name2 = name1;
//        String name3 = name1;

        //1440 48fps        // WORKING
//        String name1 = "1440-48fps.mp4";
//        String name2 = name1;
//        String name3 = name1;

        //1080 Super 24fps to 60fps   //WORKING
//        String name1 = "1080Super-24fps.mp4";
//        String name2 = name1;
//        String name3 = name1;
//        String name4 = "name1";

//        //1080 24fps to 60fps       //WORKING
//        String name1 = "1080-24fps.mp4";
//        String name2 = "1080-30fps.mp4";
//        String name3 = "1080-48fps.mp4";
//        String name4 = "1080-60fps.mp4";

        //960 48fps to 100fps       //WORKING
//        String name1 = "960-48fps.mp4";
//        String name2 = "960-60fps.mp4";
//        String name3 = "960-100fps.mp4";
//        String name4 = "1080-60fps.mp4";

        //720 Super 48fps to 100fps       //WORKING
//        String name1 = "720Super-48fps.mp4";
//        String name2 = "720Super-60fps.mp4";
//        String name3 = "720Super-100fps.mp4";

        //720 60fps to 100fps       //WORKING
//        String name1 = "720-60fps.mp4";
//        String name2 = "720-100fps.mp4";
//        String name3 = "WVGA-240fps.mp4";


        String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + name1;
        String path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + name2;
        String path3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + name3;
        String path4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + name1;
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

        urlPath.add(new Video(VideoPreviewFragment.newInstance("front", path1)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("right", path2)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("back", path3)));
        urlPath.add(new Video(VideoPreviewFragment.newInstance("left", path4)));

        media.setVideos(urlPath);
    }

    private Media media;

}
