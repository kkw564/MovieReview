package com.example.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class FullScreenCommentView extends AppCompatActivity {
    final static int WRITE_COMMENT_REQUEST = 100;

    final static String[] idList = {"kkw***","cro***","abs***","hellowo***","na***"};
    Random rd = new Random();

    ImageButton arrowBackButton;
    ListView commentListView;
    Button commentWrite;
    CommentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_comnent_list);

        getSupportActionBar().hide();

        arrowBackButton = (ImageButton)findViewById(R.id.ib_arrow_back);
        arrowBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO :: send data to main for recommendation or something
                finish();
            }
        });
        commentListView = (ListView) findViewById(R.id.lv_comment_view);

        adapter = new CommentAdapter();
        commentListView.setAdapter(adapter);

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
                SimpleDateFormat timeFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
                String time = timeFormatter.format(System.currentTimeMillis());
                String comment = data.getStringExtra("comment");
                Float ratingScore = data.getFloatExtra("rating_score",0);
                int recommendationCount = 0;
                ImageView profileImage = new ImageView(this);
                profileImage.setImageResource(R.drawable.user1);
                adapter.addItem(new CommentItem(id, time, comment, recommendationCount, ratingScore, profileImage));
                adapter.notifyDataSetChanged();
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
    class CommentAdapter extends BaseAdapter {
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
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final CommentItemView view = new CommentItemView(getApplicationContext());
            final CommentItem item = items.get(position);
            view.setUserId(item.getId());
            view.setComment(item.getComment());
            view.setProfileImage(item.getProfileImage());
            view.setRatingBar(item.getRatingScore());
            view.setRecommendationCount(item.getRecommendationCount());
            view.setTime(item.getTime());
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
}
