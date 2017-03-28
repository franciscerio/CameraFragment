package com.github.florent37.camerafragment.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class MyVideoPlayer extends JCVideoPlayerStandard {


    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
//        widthRatio = 17;
//        heightRatio = 9;

        backButton.setVisibility(View.GONE);
        thumbImageView.setVisibility(View.GONE);
        tinyBackImageView.setVisibility(View.GONE);
        fullscreenButton.setVisibility(View.GONE);
        currentScreen = SCREEN_WINDOW_FULLSCREEN;
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        titleTextView.setText(objects[0].toString());
        backButton.setVisibility(View.GONE);
        thumbImageView.setVisibility(View.GONE);
        tinyBackImageView.setVisibility(View.GONE);
        fullscreenButton.setVisibility(View.GONE);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            fullscreenButton.setImageResource(fm.jiecao.jcvideoplayer_lib.R.drawable.jc_shrink);
            changeStartButtonSize((int) getResources().getDimension(fm.jiecao.jcvideoplayer_lib.R.dimen.jc_start_button_w_h_fullscreen));
        }
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);

    }


}
