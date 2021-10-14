package com.example.star_average;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBarYours;
    private RatingBar ratingBarAll;

    private Button buttonSubmit;

    private TextView textViewYourCurrentRating;
    private TextView textViewRatingCount;
    private TextView textViewRatingCount2; //
    private TextView textViewSumAllRating;
    private TextView textViewSumAllRating2;  //
    private TextView textViewAverageAllRating;

    private List<Float> allRatings = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buttonSubmit = (Button) this.findViewById(R.id.button_submit);
        this.ratingBarYours = (RatingBar) this.findViewById(R.id.ratingBar_yours);
        this.ratingBarAll = (RatingBar) this.findViewById(R.id.ratingBar_all);

        this.textViewYourCurrentRating = (TextView) this.findViewById(R.id.textView_yourCurrentRating);
        this.textViewRatingCount= (TextView) this.findViewById(R.id.textView_ratingCount);

        this.textViewRatingCount2= (TextView) this.findViewById(R.id.textView_ratingCount2); //

        this.textViewSumAllRating= (TextView) this.findViewById(R.id.textView_sumAllRating);
        this.textViewSumAllRating2= (TextView) this.findViewById(R.id.textView_sumAllRating2); //
        this.textViewAverageAllRating= (TextView) this.findViewById(R.id.textView_averageAllRating);

        this.ratingBarYours.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textViewYourCurrentRating.setText("Your current Rating: " + rating);
            }
        });
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSubmit();
            }
        });
    }

    private void doSubmit()  {
        float rating = this.ratingBarYours.getRating();
        this.allRatings.add(rating);



        double me =Double.parseDouble(textViewRatingCount2.getText().toString()); //定義
        float ratingSum = 0f;
        int ratingCount = (int) (this.allRatings.size()+me); //抓取資料庫評分次數

        double sum =Double.parseDouble(textViewSumAllRating2.getText().toString()); //總和3
        for(Float r: this.allRatings)  {
                ratingSum += r; //資料庫抓取評過的數字
        }


        float finalcount = (float) (ratingSum+sum); //
        float averageRating = finalcount / ratingCount; //


        this.textViewRatingCount.setText("評分次數: " + ratingCount);
        this.textViewSumAllRating.setText("總和星星: " + finalcount);
        this.textViewAverageAllRating.setText("平均數: " + averageRating);

        float ratingAll = this.ratingBarAll.getNumStars() * averageRating / this.ratingBarYours.getNumStars() ;
        this.ratingBarAll.setRating(ratingAll);
    }

}
