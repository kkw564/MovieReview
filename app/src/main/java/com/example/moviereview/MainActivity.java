package com.example.moviereview;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
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

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final static int WRITE_COMMENT_REQUEST = 100;
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
        //adapter.addItem(new Sing);
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


        /*
        ibThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upCount.setText(Integer.parseInt(upCount.getText().toString()) + 1 + "");
            }
        });
        ibThumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downCount.setText(Integer.parseInt(downCount.getText().toString()) + 1 + "");
            }
        });
        */

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == WRITE_COMMENT_REQUEST){
            if(resultCode == RESULT_OK){
                // TODO : set real time

                String id = idList[rd.nextInt(5)];
                Long time = System.currentTimeMillis();
                String comment = data.getStringExtra("comment");
                Float ratingScore = data.getFloatExtra("rating_score",0) * 2;
                int recommendationCount = 0;
                ImageView profileImage = new ImageView(this);
                profileImage.setImageResource(R.drawable.user1);
                adapter.addItem(new CommentItem(id, time, comment, recommendationCount, ratingScore, profileImage));
                Toast.makeText(getApplicationContext(), "comment : " + comment + " rating : " + ratingScore,Toast.LENGTH_LONG).show();
                /*
                String id = data.getStringExtra("id");
                String time = data.getStringExtra("time");
                byte[] imageViewArr = data.getByteArrayExtra("profileImage");
                    Bitmap image = BitmapFactory.decodeByteArray(imageViewArr, 0, imageViewArr.length);
                    ImageView profileImage = (ImageView)findViewById(R.id.comment_user_profile_image);
                    profileImage.setImageBitmap(image);
                */

                // TODO : Throw comment from this to list view
            }
        }
    }

    class CommentAdapter extends BaseAdapter{
        ArrayList<CommentItem> items = new ArrayList<>();

        public void addItem(CommentItem item){
            items.add(item);
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentItemView view = new CommentItemView(getApplicationContext());

            CommentItem item = items.get(position);
            view.setUserId(item.getId());
            view.setComment(item.getComment());
            view.setProfileImage(item.getProfileImage());
            view.setRatingBar(item.getRatingScore());
            view.setRecommendationCount(item.getRecommendationCount());
            view.setTime(item.getTime());
            return view;
        }
    }
}
