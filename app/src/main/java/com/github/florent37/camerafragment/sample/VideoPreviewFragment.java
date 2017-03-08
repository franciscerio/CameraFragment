package com.github.florent37.camerafragment.sample;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by android on 3/7/17.
 */

public class VideoPreviewFragment extends Fragment {

    public static final String TAG = VideoPreviewFragment.class.getCanonicalName();

    public static VideoPreviewFragment newInstance() {
        return new VideoPreviewFragment();
    }

    private final static String VIDEO_POSITION_ARG = "current_video_position";
    private final static String VIDEO_IS_PLAYED_ARG = "is_played";

    private int currentPlaybackPosition = 0;
    //    private boolean isVideoPlaying = true;
    @Bind(R.id.video_preview)
    ScalableTextureView textureView;

    @Bind(R.id.name)
    protected TextView name;

    private MediaController mediaController = null;
    private MediaPlayer mediaPlayer = null;
    private OnVideoPlayedListener mListener;

    public interface OnVideoPlayedListener {
        void onVideoPlayingListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_preview, container, false);
        ButterKnife.bind(this, view);

        Log.e(TAG, "PLAY VIDEO onCreateView");


        textureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaController == null) return false;
                if (mediaController.isShowing()) {
                    mediaController.hide();
                } else {
                    mediaController.show();
                }
                return false;
            }
        });

        if (savedInstanceState != null) {
            loadVideoParams(savedInstanceState);
        }
        textureView.setScaleType(ScalableTextureView.ScaleType.FILL);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                Log.e(TAG, "PLAY VIDEO onSurfaceTextureAvailable");
                final Surface surface = new Surface(surfaceTexture);
                showVideoPreview(surface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                Log.e(TAG, "PLAY VIDEO onSurfaceTextureSizeChanged");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                surfaceTexture.release();
                Log.e(TAG, "PLAY VIDEO onSurfaceTextureDestroyed");
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        });

        return view;
    }

    public void setOnVideoPlayingListener(OnVideoPlayedListener playingListener) {
        this.mListener = playingListener;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        } else {
            pauseVideo();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        playVideo();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void playVideo() {
        if (textureView == null) {
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.start();
            return;
        }


    }

    private void pauseVideo() {
        Log.e(TAG, "PLAY VIDEO PAUSE");
        if (mediaPlayer == null) {
            return;
        }

        mediaPlayer.release();
        mediaPlayer = null;
        if (mediaController == null) {
            return;
        }

        mediaController.hide();
        mediaController = null;
    }

    private void loadVideoParams(Bundle savedInstanceState) {
        currentPlaybackPosition = savedInstanceState.getInt(VIDEO_POSITION_ARG, 0);
//        isVideoPlaying = savedInstanceState.getBoolean(VIDEO_IS_PLAYED_ARG, true);
    }

    private void showVideoPreview(Surface holder) {
        try {
            mediaPlayer = new MediaPlayer();
            Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.sample_360);
            mediaPlayer.setDataSource(getActivity(), uri);
            mediaPlayer.setSurface(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e(TAG, "PREPARE");
                    mediaController = new MediaController(getActivity());
                    mediaController.setAnchorView(textureView);
                    mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
                        @Override
                        public void start() {
                            mediaPlayer.start();
                        }

                        @Override
                        public void pause() {
                            mediaPlayer.pause();
                        }

                        @Override
                        public int getDuration() {
                            return mediaPlayer.getDuration();
                        }

                        @Override
                        public int getCurrentPosition() {
                            return mediaPlayer.getCurrentPosition();
                        }

                        @Override
                        public void seekTo(int pos) {
                            mediaPlayer.seekTo(pos);
                        }

                        @Override
                        public boolean isPlaying() {
                            return mediaPlayer.isPlaying();
                        }

                        @Override
                        public int getBufferPercentage() {
                            return 0;
                        }

                        @Override
                        public boolean canPause() {
                            return true;
                        }

                        @Override
                        public boolean canSeekBackward() {
                            return true;
                        }

                        @Override
                        public boolean canSeekForward() {
                            return true;
                        }

                        @Override
                        public int getAudioSessionId() {
                            return mediaPlayer.getAudioSessionId();
                        }
                    });

                    Log.e(TAG, "START");
//                    if (!isVideoPlaying)
//                        mediaPlayer.pause();
                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e(TAG, "INFO");
                    return true;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "ERROR");
                    getActivity().finish();
                    return true;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "Error media player playing video.");
            getActivity().finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveVideoParams(outState);
    }

    private void saveVideoParams(Bundle outState) {
        if (mediaPlayer != null) {
            outState.putInt(VIDEO_POSITION_ARG, mediaPlayer.getCurrentPosition());
            outState.putBoolean(VIDEO_IS_PLAYED_ARG, mediaPlayer.isPlaying());
        }
    }


    @Override
    public void onDestroyView() {
//        ButterKnife.unbind(this);
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaController != null) {
            mediaController.hide();
            mediaController = null;
        }
    }

    public void setName(String name) {
        if (this.name == null) {
            return;
        }

        this.name.setText(name);
    }

}
