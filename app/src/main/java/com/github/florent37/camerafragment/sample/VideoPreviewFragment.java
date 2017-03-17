package com.github.florent37.camerafragment.sample;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
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
    private static final String TAG_MEDIA = "media";

    private String videoName;
    private String url;
    private MediaPlayer mediaPlayer;
    private boolean isPreparing;
    private ScalableTextureView textureView;
    private MediaPlayerHandler mHandler;

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
    private boolean isVideoComplete = false;


    @Bind(R.id.relativeContainer)
    RelativeLayout mRelativeContainer;

    @Bind(R.id.name)
    protected TextView name;

    private MediaController mediaController;

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

//        mediaController = new MediaController(getActivity());
//        mediaPlayer = new MediaPlayer();

        if (savedInstanceState != null) {
            loadVideoParams(savedInstanceState);
        }


        if (!TextUtils.isEmpty(videoName)) {
            setName(videoName);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.e(TAG, "PLAY playVideo onActivityCreated ");
//        setupMediaPlayer();
    }

    private void setupMediaPlayer() {
        Log.e(TAG, "PLAY VIDEO setupMediaPlayer ");

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                Log.e(TAG, "PLAY VIDEO onSurfaceTextureAvailable " + videoName);
                final Surface surface = new Surface(surfaceTexture);
                showVideoPreview(surface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
//                Log.e(TAG, "PLAY VIDEO onSurfaceTextureSizeChanged " + videoName);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                surfaceTexture.release();
                pause();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        });
    }

    public synchronized int getMediaCurrentPosition() {
        if (mediaPlayer == null) {
            return 0;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        return mediaPlayer.getCurrentPosition();
    }

    public void hideController() {
        if (mediaPlayer == null) {
            return;
        }

        if (mediaController == null) {
            return;
        }

        mediaController.hide();
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
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
//        Log.e(TAG, "PLAY playVideo onResume = " + textureView.isAvailable() + " videname = " + videoName);

        playVideo(currentPlaybackPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    private synchronized void playVideo(int currentPlaybackPosition) {
//        Log.e(TAG, "PLAY playVideo textureView " + videoName);
        Log.e(TAG, "PLAY VIDEO isPreparing = " + isPreparing + " getUserVisibleHint() = " + getUserVisibleHint() + " mediaplayer = " + mediaPlayer);
        if (mediaPlayer == null) {
//            mHandler = new MediaPlayerHandler(new MediaPlayerHandler.MediaPlayerListeners() {
//                @Override
//                public void onBackground() {
//                }
//
//                @Override
//                public void onPreExecute() {
//                    mediaPlayer = new MediaPlayer();
//                    mediaController = new MediaController(getActivity());
//                    setUpTextureView();
//                }
//
//                @Override
//                public void onPostExecute() {
//                    setupMediaPlayer();
//                }
//            });
//            mHandler.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            mediaPlayer = new MediaPlayer();
            mediaController = new MediaController(getActivity());
            setUpTextureView();
            setupMediaPlayer();

        } else {
            if (getUserVisibleHint() && !isPreparing) {
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying()) {
//                        mediaPlayer.start();
//                        mediaPlayer.seekTo(currentPlaybackPosition);

                    }
                }

            }
        }

    }

    private void pause() {
        if (mediaPlayer == null) {
            return;
        }

        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();

        mediaPlayer = null;
        mediaController = null;
        isPreparing = true;

        if (textureView != null && mRelativeContainer != null) {
            textureView.removeCallbacks(null);
            mRelativeContainer.removeView(textureView);

            textureView = null;
        }


//        mHandler = new MediaPlayerHandler(new MediaPlayerHandler.MediaPlayerListeners() {
//            @Override
//            public void onBackground() {
//                if (mediaPlayer == null) {
//                    return;
//                }
//                mediaPlayer.release();
//            }
//
//            @Override
//            public void onPreExecute() {
//                if (mediaPlayer == null) {
//                    return;
//                }
//                mediaPlayer.stop();
//                mediaPlayer.reset();
//            }
//
//            @Override
//            public void onPostExecute() {
//                mediaPlayer = null;
//                mediaController = null;
//                isPreparing = true;
//
//            }
//        });
//        mHandler.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//
//        if (textureView != null && mRelativeContainer != null) {
//            textureView.removeCallbacks(null);
//            mRelativeContainer.removeView(textureView);
//        }
//
//        textureView = null;
    }


    private synchronized void pauseVideo() {
        Log.e(TAG, "PLAY VIDEO PAUSE " + videoName);

        pause();

        if (mediaController == null) {
            return;
        }
        mediaController.hide();

    }

    private void loadVideoParams(Bundle savedInstanceState) {
//        currentPlaybackPosition = savedInstanceState.getInt(VIDEO_POSITION_ARG, 0);
//        isVideoPlaying = savedInstanceState.getBoolean(VIDEO_IS_PLAYED_ARG, true);
    }

    private void showVideoPreview(Surface holder) {
        try {
//            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(url));
            mediaPlayer.setDataSource(getActivity(), Uri.parse(url));
            mediaPlayer.setSurface(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e(TAG, "PREPARE " + videoName);
//                    mediaController = new MediaController(getActivity());
                    mediaController.setAnchorView(textureView);
                    mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
                        @Override
                        public void start() {
                            if (mediaPlayer == null) {
                                return;
                            }
                            isVideoComplete = false;
                            mediaPlayer.start();
                            mediaPlayer.seekTo(currentPlaybackPosition);
                        }

                        @Override
                        public void pause() {
                            if (mediaPlayer == null) {
                                return;
                            }
                            mediaPlayer.pause();
                        }



                        @Override
                        public int getDuration() {
                            if (mediaPlayer == null) {
                                return 0;
                            }
                            return mediaPlayer.getDuration();
                        }

                        @Override
                        public int getCurrentPosition() {
                            if (mediaPlayer == null) {
                                return 0;
                            }

                            return mediaPlayer.getCurrentPosition();
                        }

                        @Override
                        public void seekTo(int pos) {
                            if (mediaPlayer == null) {
                                return;
                            }
                            mediaPlayer.seekTo(pos);
                        }

                        @Override
                        public boolean isPlaying() {
                            if (mediaPlayer == null) {
                                return false;
                            }
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
                            if (mediaPlayer == null) {
                                return 0;
                            }

                            return mediaPlayer.getAudioSessionId();
                        }
                    });
                    isPreparing = false;
                    Log.e(TAG, "START " + videoName);
                    playVideo(currentPlaybackPosition);

                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e(TAG, "INFO");
                    return true;
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    currentPlaybackPosition = 0;
                    isVideoComplete = true;
                    mediaController.hide();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "ERROR");
                    playVideo(currentPlaybackPosition);
                    isPreparing = true;
                    return false;
                }
            });

            isPreparing = true;
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            isPreparing = true;
            Log.e(TAG, "Error media player playing video.");
            getActivity().finish();
        }
    }

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
        if (mediaPlayer != null) {
            outState.putInt(VIDEO_POSITION_ARG, mediaPlayer.getCurrentPosition());
            outState.putBoolean(VIDEO_IS_PLAYED_ARG, mediaPlayer.isPlaying());
        }
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

    private void setUpTextureView() {
        Log.e(TAG, "PLAY VIDEO setUpTextureView");
        textureView = new ScalableTextureView(getActivity());

        textureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaController == null)
                    return false;
                if (mediaController.isShowing()) {
                    mediaController.hide();
                } else {
                    mediaController.show();
                }
                return false;
            }
        });

        textureView.setScaleType(ScalableTextureView.ScaleType.FILL);
        mRelativeContainer.addView(textureView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        sendViewToBack(textureView);
    }

    public void setName(String name) {
        if (this.name == null) {
            return;
        }

        this.name.setText(name);
    }

    public void setCurrentPlaybackPosition(int currentPlaybackPosition) {
        this.currentPlaybackPosition = currentPlaybackPosition;
    }

    public boolean isVideoComplete() {
        return isVideoComplete;
    }

    public void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup) child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

}
