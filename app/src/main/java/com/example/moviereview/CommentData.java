package com.example.moviereview;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class CommentData implements Parcelable {
    String id;
    long time;
    String comment;
    int recommendationCount;
    Float ratingScore;
    ImageView profileImage;

    public CommentData(String id, Long time, String comment, int recommendationCount, Float ratingScore) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.recommendationCount = recommendationCount;
        this.ratingScore = ratingScore;
        //this.profileImage = profileImage;
    }

    public CommentData(Parcel src) {
        id = src.readString();
        time = src.readLong();
        comment = src.readString();
        recommendationCount = src.readInt();
        ratingScore = src.readFloat();
       // profileImage = src.readImageView();
    }

    public static final Creator<CommentData> CREATOR = new Creator<CommentData>() {
        @Override
        public CommentData createFromParcel(Parcel in) {
            return new CommentData(in);
        }

        @Override
        public CommentData[] newArray(int size) {
            return new CommentData[size];
        }
    };

    public CommentData() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(time);
        dest.writeString(comment);
        dest.writeInt(recommendationCount);
        dest.writeFloat(ratingScore);
        // dest.writeImageview(profileImage);
    }
}
