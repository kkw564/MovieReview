package com.example.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

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
        initToolbar();

        commentListView = findViewById(R.id.lv_comment_view);

        adapter = new CommentAdapter();
        commentListView.setAdapter(adapter);

        commentWrite = findViewById(R.id.btn_write_comment);
        commentWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), WriteCommentView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, WRITE_COMMENT_REQUEST);
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.tb_custom_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("한줄평 목록");

        // Make a back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                setIntentForResult();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void processIntent(Intent intent){
        ArrayList<CommentData> items = intent.getParcelableExtra("commentListData");
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
    private void setIntentForResult(){
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

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("returnCommentListData", list);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN ){
            if( keyCode == KeyEvent.KEYCODE_BACK ){
                setIntentForResult();
            }
        }
        return super.onKeyDown( keyCode, event );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == WRITE_COMMENT_REQUEST){
            if(resultCode == RESULT_OK){
                String id = idList[rd.nextInt(5)];
                Long time = System.currentTimeMillis();
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
            }
        }
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

    private static final class TIME_TABLE {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
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
        public int getCount() { return items.size(); }

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
}
