package com.example.moviereview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


public class CommentItemView extends LinearLayout {
    TextView userId;
    TextView time;
    RatingBar ratingBar;
    TextView comment;
    TextView recommendationCount;
    ImageView profileImage;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item, this, true);

        userId = (TextView) findViewById(R.id.comment_user_id);
        time = (TextView) findViewById(R.id.comment_time);
        ratingBar = (RatingBar) findViewById(R.id.comment_rating_bar);
        comment = (TextView) findViewById(R.id.comment);
        recommendationCount = (TextView) findViewById(R.id.comment_recommendation_count);
    }

    public void setUserId(String userId){
        this.userId.setText(userId);
    }
    public void setTime(Long time){
        this.time.setText(Long.toString(time));
    }
    public void setRatingBar(Float ratingScore){
        this.ratingBar.setRating(ratingScore);
    }
    public void setComment(String comment){
        this.comment.setText(comment);
    }
    public void setRecommendationCount(int recommendationCount){
        this.recommendationCount.setText(Integer.toString(recommendationCount));
    }
    public void setProfileImage(ImageView profileImage){
        this.profileImage = profileImage;
    }
}
