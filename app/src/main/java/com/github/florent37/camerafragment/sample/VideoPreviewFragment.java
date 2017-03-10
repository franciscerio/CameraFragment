package com.github.florent37.camerafragment.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private final static String VIDEO_POSITION_ARG = "current_video_position";
    private final static String VIDEO_IS_PLAYED_ARG = "is_played";

    private int currentPlaybackPosition = 0;
    //    private boolean isVideoPlaying = true;
    @Bind(R.id.video_preview)
    ScalableTextureView textureView;

    @Bind(R.id.name)
    protected TextView name;

    private MediaController mediaController = null;
    //    private MediaPlayer mediaPlayer = null;
    private OnVideoPlayedListener mListener;

    public interface OnVideoPlayedListener {
        void onVideoPlayingListener();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_preview, container, false);
        ButterKnife.bind(this, view);

        Log.e(TAG, "PLAY VIDEO onCreateView " + videoName);

        mediaController = new MediaController(getActivity());
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

        if (!TextUtils.isEmpty(videoName)) {
            setName(videoName);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "PLAY playVideo onActivityCreated " + videoName);

//        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//            @Override
//            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
//                Log.e(TAG, "PLAY VIDEO onSurfaceTextureAvailable");
//                final Surface surface = new Surface(surfaceTexture);
//                MediaPlayerManager.getInstance().initializeMediaPlayer(textureView, , surface, mediaController);
//            }
//
//            @Override
//            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
//                Log.e(TAG, "PLAY VIDEO onSurfaceTextureSizeChanged");
//            }
//
//            @Override
//            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
//                surfaceTexture.release();
//                Log.e(TAG, "PLAY VIDEO onSurfaceTextureDestroyed");
//                return false;
//            }
//
//            @Override
//            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//            }
//        });
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
        Log.e(TAG, "PLAY playVideo textureView " + videoName);
        if (textureView == null) {
            return;
        }

        Log.e(TAG, "PLAY mediaPlayer textureView " + videoName);
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//            return;
//        }
//        new Ahm().execute();

        MediaPlayerManager.getInstance().start();

        Log.e(TAG, "PLAY VIDEO " + videoName);

    }


    private void pauseVideo() {
        Log.e(TAG, "PLAY VIDEO PAUSE " + videoName);
//        if (mediaPlayer == null) {
//            return;
//        }
//
//        mediaPlayer.pause();

        if (mediaController == null) {
            return;
        }

        MediaPlayerManager.getInstance().pause();

        mediaController.hide();
        if (textureView == null) {
            return;
        }

    }

    private void loadVideoParams(Bundle savedInstanceState) {
        currentPlaybackPosition = savedInstanceState.getInt(VIDEO_POSITION_ARG, 0);
//        isVideoPlaying = savedInstanceState.getBoolean(VIDEO_IS_PLAYED_ARG, true);
    }

//    private MediaPlayer showVideoPreview(Surface holder) {
//        try {
//            MediaPlayer mediaPlayer = new MediaPlayer();
//
//            Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.sample_360);
//            mediaPlayer.setDataSource(getActivity(), uri);
//            mediaPlayer.setSurface(holder);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setScreenOnWhilePlaying(true);
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    Log.e(TAG, "PREPARE");
//                    mediaController = new MediaController(getActivity());
//                    mediaController.setAnchorView(textureView);
//                    mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
//                        @Override
//                        public void start() {
//                            mediaPlayer.start();
//                        }
//
//                        @Override
//                        public void pause() {
//                            mediaPlayer.pause();
//                        }
//
//                        @Override
//                        public int getDuration() {
//                            return mediaPlayer.getDuration();
//                        }
//
//                        @Override
//                        public int getCurrentPosition() {
//                            return mediaPlayer.getCurrentPosition();
//                        }
//
//                        @Override
//                        public void seekTo(int pos) {
//                            mediaPlayer.seekTo(pos);
//                        }
//
//                        @Override
//                        public boolean isPlaying() {
//                            return mediaPlayer.isPlaying();
//                        }
//
//                        @Override
//                        public int getBufferPercentage() {
//                            return 0;
//                        }
//
//                        @Override
//                        public boolean canPause() {
//                            return true;
//                        }
//
//                        @Override
//                        public boolean canSeekBackward() {
//                            return true;
//                        }
//
//                        @Override
//                        public boolean canSeekForward() {
//                            return true;
//                        }
//
//                        @Override
//                        public int getAudioSessionId() {
//                            return mediaPlayer.getAudioSessionId();
//                        }
//                    });
//
//                    Log.e(TAG, "START");
////                    if (!isVideoPlaying)
////                        mediaPlayer.pause();
//                }
//            });
//
//            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                @Override
//                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//                    Log.e(TAG, "INFO");
//                    return true;
//                }
//            });
//            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    Log.e(TAG, "ERROR");
//                    getActivity().finish();
//                    return true;
//                }
//            });
//            mediaPlayer.prepareAsync();
//        } catch (Exception e) {
//            Log.e(TAG, "Error media player playing video.");
//            getActivity().finish();
//        }
//    }


//    private MediaPlayer showVideoPreview(Surface holder) {
//        try {
//            final MediaPlayer mediaPlayer = new MediaPlayer();
//
//            Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.sample_360);
//            mediaPlayer.setDataSource(getActivity(), uri);
//            mediaPlayer.setSurface(holder);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setScreenOnWhilePlaying(true);
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    Log.e(TAG, "PREPARE");
//                    mediaController = new MediaController(getActivity());
//                    mediaController.setAnchorView(textureView);
//                    mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
//                        @Override
//                        public void start() {
//                            mediaPlayer.start();
//                        }
//
//                        @Override
//                        public void pause() {
//                            mediaPlayer.pause();
//                        }
//
//                        @Override
//                        public int getDuration() {
//                            return mediaPlayer.getDuration();
//                        }
//
//                        @Override
//                        public int getCurrentPosition() {
//                            return mediaPlayer.getCurrentPosition();
//                        }
//
//                        @Override
//                        public void seekTo(int pos) {
//                            mediaPlayer.seekTo(pos);
//                        }
//
//                        @Override
//                        public boolean isPlaying() {
//                            return mediaPlayer.isPlaying();
//                        }
//
//                        @Override
//                        public int getBufferPercentage() {
//                            return 0;
//                        }
//
//                        @Override
//                        public boolean canPause() {
//                            return true;
//                        }
//
//                        @Override
//                        public boolean canSeekBackward() {
//                            return true;
//                        }
//
//                        @Override
//                        public boolean canSeekForward() {
//                            return true;
//                        }
//
//                        @Override
//                        public int getAudioSessionId() {
//                            return mediaPlayer.getAudioSessionId();
//                        }
//                    });
//
//                    Log.e(TAG, "START");
////                    if (!isVideoPlaying)
////                        mediaPlayer.pause();
//                }
//            });
//
//            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                @Override
//                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//                    Log.e(TAG, "INFO");
//                    return true;
//                }
//            });
//            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    Log.e(TAG, "ERROR");
//                    getActivity().finish();
//                    return true;
//                }
//            });
//            mediaPlayer.prepareAsync();
//
//
//            return mediaPlayer;
//        } catch (Exception e) {
//            Log.e(TAG, "Error media player playing video.");
//            getActivity().finish();
//        }
//
//        return null;
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveVideoParams(outState);
    }

    private void saveVideoParams(Bundle outState) {
//        if (mediaPlayer != null) {
//            outState.putInt(VIDEO_POSITION_ARG, mediaPlayer.getCurrentPosition());
//            outState.putBoolean(VIDEO_IS_PLAYED_ARG, mediaPlayer.isPlaying());
//        }
    }


    @Override
    public void onDestroyView() {
//        ButterKnife.unbind(this);
        super.onDestroyView();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//        if (mediaController != null) {
//            mediaController.hide();
//            mediaController = null;
//        }
    }

    public void setName(String name) {
        if (this.name == null) {
            return;
        }

        this.name.setText(name);
    }

}
