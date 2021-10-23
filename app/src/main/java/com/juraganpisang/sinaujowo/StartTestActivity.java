package com.juraganpisang.sinaujowo;

import static com.juraganpisang.sinaujowo.DBQuery.g_categoryModelList;
import static com.juraganpisang.sinaujowo.DBQuery.loadQuestions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartTestActivity extends AppCompatActivity {

    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);


        init();

        progressDialog = new Dialog(StartTestActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Proses...");

        progressDialog.show();

        loadQuestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                setData();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {

                progressDialog.dismiss();
                // If sign in fails, display a message to the user.
                Toast.makeText(StartTestActivity.this, "Ngapunten, Enten sing Salah",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        catName = findViewById(R.id.st_cat_nameTv);
        testNo = findViewById(R.id.st_test_noTv);
        totalQ = findViewById(R.id.st_total_quesTv);
        bestScore = findViewById(R.id.st_bestTv);
        time = findViewById(R.id.st_timeTv);
        startTestB = findViewById(R.id.startTestBtn);
        backB = findViewById(R.id.st_backBtn);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTestActivity.this.finish();
            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartTestActivity.this, QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData(){
        catName.setText(g_categoryModelList.get(DBQuery.g_selected_cat_index).getName());
        testNo.setText("Test No. "+ String.valueOf(DBQuery.g_selected_test_index + 1));
        totalQ.setText(String.valueOf(DBQuery.g_questList.size()));
        bestScore.setText(String.valueOf(DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTopScore()));
        time.setText(String.valueOf(DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTime()) + " menit");
    }
}