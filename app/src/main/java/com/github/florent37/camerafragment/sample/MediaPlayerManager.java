package com.github.florent37.camerafragment.sample;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.widget.MediaController;

import static com.github.florent37.camerafragment.internal.utils.CameraHelper.TAG;

/**
 * Created by android on 3/7/17.
 */

public class MediaPlayerManager {

    //    private static MediaController mediaController;
    private static MediaPlayer mediaPlayer;
    private MediaController mediaController;


    public static MediaPlayerManager getInstance() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        return new MediaPlayerManager(mediaPlayer);
    }

    public MediaPlayerManager(MediaPlayer player) {
        mediaPlayer = player;
    }

    public synchronized void playMediaPlayer(final ScalableTextureView surfaceTextureView, Surface holder, final MediaController mediaController) {
        try {
            this.mediaController = mediaController;
            if (mediaPlayer == null) {
                return;
            }

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.release();
            }

            Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.sample_360);
            mediaPlayer.setDataSource(App.getInstance(), uri);
            mediaPlayer.setSurface(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mediaController.setAnchorView(surfaceTextureView);
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

                    mediaPlayer.start();
//                    mediaPlayer.seekTo(currentPlaybackPosition);

//                    if (!isVideoPlaying)
//                        mediaPlayer.pause();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "Error media player playing video.");
        }
    }


    public int getCurrentPosition() {
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentPosition();
    }


    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (this.mediaController == null) {
            return;
        }

        mediaController.hide();
        mediaController = null;
    }


    public boolean isVideoPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.isPlaying();
    }

}
