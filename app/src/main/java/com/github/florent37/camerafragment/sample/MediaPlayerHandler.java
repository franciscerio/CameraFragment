package com.github.florent37.camerafragment.sample;

import android.os.AsyncTask;

/**
 * Created by android on 3/17/17.
 */

public class MediaPlayerHandler extends AsyncTask<Void, Void, Void> {

    private MediaPlayerListeners mListener;


    public MediaPlayerHandler(MediaPlayerListeners mListener) {
        this.mListener = mListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (mListener != null) {
            mListener.onBackground();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mListener != null) {
            mListener.onPreExecute();
        }


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (mListener != null) {
            mListener.onPostExecute();
        }
    }

    public interface MediaPlayerListeners {
        void onBackground();

        void onPreExecute();

        void onPostExecute();
    }
}
