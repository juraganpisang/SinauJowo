package com.juraganpisang.sinaujowo;

import static com.juraganpisang.sinaujowo.DBQuery.g_categoryModelList;
import static com.juraganpisang.sinaujowo.DBQuery.g_questList;
import static com.juraganpisang.sinaujowo.DBQuery.g_selected_cat_index;
import static com.juraganpisang.sinaujowo.DBQuery.g_selected_test_index;
import static com.juraganpisang.sinaujowo.DBQuery.g_testModelList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private TextView tvQuestID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuestB, nextQuestB, closeDrawerB;
    private ImageView questListB;
    private int questID;
    private QuestionsAdapter questionsAdapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_list_layout);

        init();

        questionsAdapter = new QuestionsAdapter(g_questList);
        questionsView.setAdapter(questionsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        setSnapHelper();

        setClickListeners();

        startTimer();
    }

    private void init(){
        questionsView = findViewById(R.id.questionsRv);
        tvQuestID = findViewById(R.id.tv_questID);
        timerTV = findViewById(R.id.tv_timer);
        catNameTV = findViewById(R.id.qa_catName);
        submitB = findViewById(R.id.btn_submit);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevQuestB = findViewById(R.id.prev_questB);
        nextQuestB = findViewById(R.id.next_questB);
        questListB = findViewById(R.id.quest_list_gridB);
        drawerLayout = findViewById(R.id.drawer_layout);
        closeDrawerB = findViewById(R.id.drawerCloseB);

        questID = 0;

        tvQuestID.setText("1/" +String.valueOf(g_questList.size()));
        catNameTV.setText(g_categoryModelList.get(g_selected_cat_index).getName());
    }

    private void setSnapHelper(){
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                questID = recyclerView.getLayoutManager().getPosition(view);

                tvQuestID.setText(String.valueOf(questID + 1) + "/"+ String.valueOf(g_questList.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners(){
     prevQuestB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(questID > 0){
                 questionsView.smoothScrollToPosition(questID - 1);
             }
         }
     });

     nextQuestB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             if(questID < g_questList.size() - 1){
                 questionsView.smoothScrollToPosition(questID + 1);
             }
         }
     });

     clearSelB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             g_questList.get(questID).setSelectedAns(-1);

             questionsAdapter.notifyDataSetChanged();
         }
     });

     questListB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(!drawerLayout.isDrawerOpen(GravityCompat.END)){
                 drawerLayout.openDrawer(GravityCompat.END);
             }
         }
     });

     closeDrawerB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                 drawerLayout.closeDrawer(GravityCompat.END);
             }
         }
     });
    }

    private void startTimer(){
        long totalTime = g_testModelList.get(g_selected_test_index).getTime()*60*1000;

        CountDownTimer timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remaining) {
                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remaining),
                        TimeUnit.MILLISECONDS.toSeconds(remaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaining))
                        );

                timerTV.setText(time);
            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();
    }
}