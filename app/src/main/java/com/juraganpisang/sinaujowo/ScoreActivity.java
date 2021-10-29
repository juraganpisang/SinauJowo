package com.juraganpisang.sinaujowo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTv, timeTv, totalQTv, correctQTv, wrongQTv, unattemptedQtv;
    private Button leaderB, reAttemptB, viewAnsB;
    private long timeTaken;
    private Toolbar toolbar;
    private Dialog progressDialog;
    private TextView dialogText;
    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Hasil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Proses...");

        progressDialog.show();

        init();

        loadData();

        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reAttempt();
            }
        });

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        saveResult();

    }

    private void init(){
        scoreTv = findViewById(R.id.scoreQ);
        timeTv = findViewById(R.id.timeQ);
        totalQTv = findViewById(R.id.totalQ);
        correctQTv = findViewById(R.id.correctQ);
        wrongQTv = findViewById(R.id.wrongQ);
        unattemptedQtv = findViewById(R.id.un_attemptedQ);
        leaderB = findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answeredB);
    }

    private void loadData(){
        int correctQ = 0, wrongQ = 0, unattemptQ = 0;

        for(int i = 0; i < DBQuery.g_questList.size(); i++){
            if(DBQuery.g_questList.get(i).getSelectedAns() == -1){
                unattemptQ++;
            }else{
                if(DBQuery.g_questList.get(i).getSelectedAns() == DBQuery.g_questList.get(i).getCorrectAns()){
                    correctQ++;
                }else{
                    wrongQ++;
                }
            }
        }

        correctQTv.setText(String.valueOf(correctQ));
        wrongQTv.setText(String.valueOf(wrongQ));
        unattemptedQtv.setText(String.valueOf(unattemptQ));

        totalQTv.setText(String.valueOf(DBQuery.g_questList.size()));

        finalScore = (correctQ * 100)/DBQuery.g_questList.size();
        scoreTv.setText(String.valueOf(finalScore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN",0);

        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
        );

        timeTv.setText(time);
    }

    private void reAttempt(){
        for(int i = 0; i < DBQuery.g_questList.size(); i++){
            DBQuery.g_questList.get(i).setSelectedAns(-1);
            DBQuery.g_questList.get(i).setStatus(DBQuery.NOT_VISITED);
        }

        Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveResult(){
        DBQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ScoreActivity.this, "Enten sing mboten beres", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}