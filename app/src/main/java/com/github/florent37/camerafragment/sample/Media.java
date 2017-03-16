package com.github.florent37.camerafragment.sample;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by android on 3/14/17.
 */

public class Media implements Parcelable {

    private int currentPosition;
    private ArrayList<Video> videos;

    public Media() {
        currentPosition = 0;
        this.videos = new ArrayList<>();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.currentPosition);
        dest.writeList(this.videos);
    }

    protected Media(Parcel in) {
        this.currentPosition = in.readInt();
        this.videos = new ArrayList<Video>();
        in.readList(this.videos, Video.class.getClassLoader());
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
