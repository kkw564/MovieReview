package com.example.moviereview;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    final static int WRITE_COMMENT_REQUEST = 100;
    final static int FULL_SCREEN_COMMENT_REQUEST = 101;

    final static String[] idList = {"kkw***","cro***","abs***","hellowo***","na***"};
    Random rd = new Random();

    TextView upCount;
    TextView downCount;
    int thumbState; // 0 : none 1 : up 2 : down

    ImageButton ibThumbUp;
    ImageButton ibThumbDown;

    RatingBar ratingBar;
    TextView ratingScore;

    ListView commentListView;

    Button commentWrite;
    CommentAdapter adapter;

    Button commentSeeAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        upCount = (TextView)findViewById(R.id.tv_up_count);
        downCount = (TextView)findViewById(R.id.tv_down_count);

        ibThumbUp = (ImageButton)findViewById(R.id.btn_thumb_up);
        ibThumbDown = (ImageButton)findViewById(R.id.btn_thumb_down);

        commentListView = (ListView) findViewById(R.id.lv_comment_view);

        adapter = new CommentAdapter();
        commentListView.setAdapter(adapter);
        thumbState = 0;

        ibThumbUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(thumbState == 0 || thumbState == 2){
                    ibThumbUp.setImageResource(R.drawable.ic_thumb_up_selected);
                    upCount.setText(Integer.parseInt(upCount.getText().toString()) + 1 + "");
                    if(thumbState == 2){
                        ibThumbDown.setImageResource(R.drawable.ic_thumb_down);
                        downCount.setText(Integer.parseInt(downCount.getText().toString()) - 1 + "");
                    }
                    thumbState = 1;
                } else if(thumbState == 1){
                    ibThumbUp.setImageResource(R.drawable.ic_thumb_up);
                    upCount.setText(Integer.parseInt(upCount.getText().toString()) - 1 + "");
                    thumbState = 0;
                }
            }
        });

        ibThumbDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(thumbState == 0 || thumbState == 1){
                    ibThumbDown.setImageResource(R.drawable.ic_thumb_down_selected);
                    downCount.setText(Integer.parseInt(downCount.getText().toString()) + 1 + "");
                    if(thumbState == 1){
                        ibThumbUp.setImageResource(R.drawable.ic_thumb_up);
                        upCount.setText(Integer.parseInt(upCount.getText().toString()) - 1 + "");
                    }
                    thumbState = 2;
                } else if(thumbState == 2){
                    ibThumbDown.setImageResource(R.drawable.ic_thumb_down);
                    downCount.setText(Integer.parseInt(downCount.getText().toString()) - 1 + "");
                    thumbState = 0;
                }
            }
        });

        ratingBar = (RatingBar)findViewById(R.id.rb_rating_bar);
        ratingScore = (TextView)findViewById(R.id.tv_rating_score_text_view);
        /**
         * Make a float value for rating default value in dimen.xml
         */
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.rating_default_value, typedValue, true);
        float ratingDefaultValue = typedValue.getFloat();
        ratingBar.setRating(ratingDefaultValue);
        ratingScore.setText(String.format("%.1f", ratingDefaultValue * 2));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float floatRating, boolean fromUser) {
                String strRating = String.format("%.1f", floatRating * 2);
                ratingScore.setText(strRating);
            }
        });

        commentWrite = (Button)findViewById(R.id.btn_write_comment);
        commentWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), WriteCommentView.class);
                startActivityForResult(intent, WRITE_COMMENT_REQUEST);
            }
        });

        commentSeeAll = (Button)findViewById(R.id.btn_comment_see_all);
        commentSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDataList list = new CommentDataList();
                for(int i = 0 ; i < adapter.size(); i++){
                    CommentData data = new CommentData();
                    data.id = adapter.getItem(i).id;
                    data.time = adapter.getItem(i).time;
                    data.comment = adapter.getItem(i).comment;
                    data.recommendationCount = adapter.getItem(i).recommendationCount;
                    data.ratingScore = adapter.getItem(i).ratingScore;
                    //data.profileImage = adapter.getItem(i).profileImage;
                    list.addItem(data);
                }

                Intent intent = new Intent(getApplicationContext(), FullScreenCommentView.class);
                intent.putParcelableArrayListExtra("commentListData", list);
                startActivityForResult(intent, FULL_SCREEN_COMMENT_REQUEST);
            }
        });
    }

    private String setElapsedTime(Long commentTime){
        Long currentTime = System.currentTimeMillis();
        long diffTime = (currentTime - commentTime) / 1000;
        if(diffTime == 0) {
            return "방금";
        } else if(diffTime < TIME_TABLE.SEC) {
            return diffTime + "초 전";
        } else if((diffTime /= TIME_TABLE.SEC) < TIME_TABLE.MIN){
            return diffTime + "분 전";
        } else if((diffTime /= TIME_TABLE.MIN) < TIME_TABLE.HOUR){
            return diffTime + "시간 전";
        } else if((diffTime /= TIME_TABLE.HOUR) < TIME_TABLE.DAY){
            return diffTime + "일 전";
        } else if((diffTime /= TIME_TABLE.DAY) < TIME_TABLE.MONTH){
            return diffTime + "달 전";
        } else {
            return diffTime + "년 전";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case WRITE_COMMENT_REQUEST:
                if(resultCode == RESULT_OK){
                    String id = idList[rd.nextInt(5)];
                    Long time = System.currentTimeMillis();
                    String comment = data.getStringExtra("comment");
                    Float ratingScore = data.getFloatExtra("rating_score",0);
                    int recommendationCount = 0;
                    ImageView profileImage = new ImageView(this);
                    /*switch(rd.nextInt(3)){
                        case 0:
                            profileImage.setImageResource(R.drawable.user1);
                            break;
                        case 1:
                            profileImage.setImageResource(R.drawable.user2);
                            break;
                        case 2:
                            profileImage.setImageResource(R.drawable.user3);
                            break;
                    }*/

                    adapter.addItem(new CommentItem(id, time, comment, recommendationCount, ratingScore, profileImage));
                    adapter.notifyDataSetChanged();
                    /*
                    byte[] imageViewArr = data.getByteArrayExtra("profileImage");
                        Bitmap image = BitmapFactory.decodeByteArray(imageViewArr, 0, imageViewArr.length);
                        ImageView profileImage = (ImageView)findViewById(R.id.comment_user_profile_image);
                        profileImage.setImageBitmap(image);
                    */
                }
                break;
            case FULL_SCREEN_COMMENT_REQUEST:
                if(resultCode == RESULT_OK){
                    ArrayList<CommentData> items = data.getParcelableExtra("returnCommentListData");
                    adapter.clear();

                    for(int i = 0; i < items.size(); i ++) {
                        CommentItem item = new CommentItem();
                        item.setId(items.get(i).id);
                        item.setTime(items.get(i).time);
                        item.setComment(items.get(i).comment);
                        item.setRecommendationCount(items.get(i).recommendationCount);
                        item.setRatingScore(items.get(i).ratingScore);
                        //item.setProfileImage(items.get(i).profileImage);
                        adapter.addItem(item);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    class CommentAdapter extends BaseAdapter {
        private ArrayList<CommentItem> items = new ArrayList<>();

        public int size(){ return items.size(); }

        public void addItem(CommentItem item){
            items.add(item);
        }

        public void clear() {
            items.clear();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CommentItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final CommentItemView view = new CommentItemView(getApplicationContext());
            final CommentItem item = items.get(position);
            view.setUserId(item.getId());
            view.setComment(item.getComment());
            view.setProfileImage(item.getProfileImage());
            view.setRatingBar(item.getRatingScore());
            view.setRecommendationCount(item.getRecommendationCount());
            view.setTime(setElapsedTime(item.getTime()));
            view.findViewById(R.id.tv_comment_recommend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setRecommendationCount(item.getRecommendationCount() + 1);
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }
    private static final class TIME_TABLE {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
}
