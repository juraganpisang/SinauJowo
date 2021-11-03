package com.juraganpisang.sinaujowo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.juraganpisang.sinaujowo.Adapters.AnswerAdapter;

public class AnswerActivity extends AppCompatActivity {

    private RecyclerView answerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        toolbar = findViewById(R.id.aa_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("Jawaban");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        answerView = findViewById(R.id.aa_testRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        answerView.setLayoutManager(layoutManager);

        AnswerAdapter adapter = new AnswerAdapter(DBQuery.g_questList);
        answerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            AnswerActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}