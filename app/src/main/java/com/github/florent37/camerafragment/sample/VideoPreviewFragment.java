package com.github.florent37.camerafragment.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by android on 3/7/17.
 */

public class VideoPreviewFragment extends Fragment {

    public static final String TAG = VideoPreviewFragment.class.getCanonicalName();
    private static final String TAG_NAME = "name";
    private static final String TAG_URL = "url";

    private String videoName;
    private String url;

    public static VideoPreviewFragment newInstance(String name, String url) {
        VideoPreviewFragment fragment = new VideoPreviewFragment();
        Bundle args = new Bundle();
        args.putString(TAG_NAME, name);
        args.putString(TAG_URL, url);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        } else {
            onPause();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            videoName = bundle.getString(TAG_NAME, "");
            url = bundle.getString(TAG_URL, "");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        if (mJcVideoPlayerStandard == null) {
            return;
        }
        Log.e(TAG, "url = " + url);
        Log.e(TAG, "videoName = " + videoName);
        mJcVideoPlayerStandard.setUp(url
                , JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, videoName);
//        JCVideoPlayerStandard.startFullscreen(getActivity(), JCVideoPlayerStandard.class, url, videoName);
        mJcVideoPlayerStandard.startVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_preview, container, false);

        mJcVideoPlayerStandard = (MyVideoPlayer) view.findViewById(R.id.videoplayer);
        return view;
    }

    private MyVideoPlayer mJcVideoPlayerStandard;


}

