package com.example.moviereview;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView upCount;
    TextView downCount;
    int thumbState; // 0 : none 1 : up 2 : down

    ImageButton thumbUp;
    ImageButton thumbDown;

    RatingBar ratingBar;
    TextView ratingScore;

    ListView commentListView;

    Button commentWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        upCount = (TextView)findViewById(R.id.up_count);
        downCount = (TextView)findViewById(R.id.down_count);

        thumbUp = (ImageButton)findViewById(R.id.btn_thumb_up);
        thumbDown = (ImageButton)findViewById(R.id.btn_thumb_down);

        commentListView = (ListView) findViewById(R.id.comment_list_view);

        CommentAdapter adapter = new CommentAdapter();
        commentListView.setAdapter(adapter);
        //adapter.addItem(new Sing);
        thumbState = 0;

        thumbUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(thumbState == 0 || thumbState == 2){
                    thumbUp.setImageResource(R.drawable.ic_thumb_up_selected);
                    upCount.setText(Integer.parseInt(upCount.getText().toString()) + 1 + "");
                    if(thumbState == 2){
                        thumbDown.setImageResource(R.drawable.ic_thumb_down);
                        downCount.setText(Integer.parseInt(downCount.getText().toString()) - 1 + "");
                    }
                    thumbState = 1;
                } else if(thumbState == 1){
                    thumbUp.setImageResource(R.drawable.ic_thumb_up);
                    upCount.setText(Integer.parseInt(upCount.getText().toString()) - 1 + "");
                    thumbState = 0;
                }
            }
        });

        thumbDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(thumbState == 0 || thumbState == 1){
                    thumbDown.setImageResource(R.drawable.ic_thumb_down_selected);
                    downCount.setText(Integer.parseInt(downCount.getText().toString()) + 1 + "");
                    if(thumbState == 1){
                        thumbUp.setImageResource(R.drawable.ic_thumb_up);
                        upCount.setText(Integer.parseInt(upCount.getText().toString()) - 1 + "");
                    }
                    thumbState = 2;
                } else if(thumbState == 2){
                    thumbDown.setImageResource(R.drawable.ic_thumb_down);
                    downCount.setText(Integer.parseInt(downCount.getText().toString()) - 1 + "");
                    thumbState = 0;
                }
            }
        });


        /*
        thumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upCount.setText(Integer.parseInt(upCount.getText().toString()) + 1 + "");
            }
        });
        thumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downCount.setText(Integer.parseInt(downCount.getText().toString()) + 1 + "");
            }
        });
        */

        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        ratingScore = (TextView)findViewById(R.id.rating_score_text_view);
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


        commentWrite = (Button)findViewById(R.id.comment_write_button);

        commentWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showMessage();
            }
        });
    }
    public void showMessage(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "확인 감사합니다";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "취소 감사합니다.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size.x - 50;
        int height = size.y - 50;

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(width, height);
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
            view.setRatingBar(item.getRatingBar());
            view.setRecommendationCount(item.getRecommendationCount());
            view.setTime(item.getTime());
            return view;
        }
    }
}
