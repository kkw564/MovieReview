package com.example.moviereview;

import android.widget.ImageView;
import android.widget.RatingBar;

public class CommentItem {
    String id;
    String time;
    String comment;
    String recommendationCount;
    RatingBar ratingBar;
    ImageView profileImage;

    public CommentItem(String id, String time, String recommendCount, RatingBar ratingBar, ImageView profileImage) {
        this.id = id;
        this.time = time;
        this.recommendationCount = recommendCount;
        this.ratingBar = ratingBar;
        this.profileImage = profileImage;
    }

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

    public String getRecommendationCount() {
        return recommendationCount;
    }

    public void setRecommendationCount(String recommendationCount) {
        this.recommendationCount = recommendationCount;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
