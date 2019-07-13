package com.example.moviereview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView upCount;
    TextView downCount;
    int thumbState; // 0 : none 1 : up 2 : down

    ImageButton thumbUp;
    ImageButton thumbDown;

    RatingBar ratingBar;
    TextView ratingScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        upCount = (TextView)findViewById(R.id.up_count);
        downCount = (TextView)findViewById(R.id.down_count);

        thumbUp = (ImageButton)findViewById(R.id.btn_thumb_up);
        thumbDown = (ImageButton)findViewById(R.id.btn_thumb_down);

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
    }
}
