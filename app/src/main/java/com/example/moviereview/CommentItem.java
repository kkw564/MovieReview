package com.example.moviereview;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

public class CommentItem {
    String id;
    String time;
    String comment;
    int recommendationCount;
    Float ratingScore;
    ImageView profileImage;

    public CommentItem(String id, String time, String comment, int recommendCount, Float ratingScore, ImageView profileImage) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.recommendationCount = recommendCount;
        this.ratingScore = ratingScore;
        this.profileImage = profileImage;
    }

    public CommentItem() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRecommendationCount() {
        return recommendationCount;
    }

    public void setRecommendationCount(int recommendationCount) {
        this.recommendationCount = recommendationCount;
    }

    public Float getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Float ratingScore) {
        this.ratingScore = ratingScore;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
