package com.example.moviereview;

import android.widget.ImageView;
import android.widget.RatingBar;

public class CommentItem {
    String id;
    Long time;
    String comment;
    int recommendationCount;
    Float ratingScore;
    ImageView profileImage;

    public CommentItem(String id, Long time, String comment, int recommendCount, Float ratingScore, ImageView profileImage) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.recommendationCount = recommendCount;
        this.ratingScore = ratingScore;
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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
