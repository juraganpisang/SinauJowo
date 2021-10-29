package com.juraganpisang.sinaujowo;

import static com.juraganpisang.sinaujowo.DBQuery.ANSWERED;
import static com.juraganpisang.sinaujowo.DBQuery.NOT_VISITED;
import static com.juraganpisang.sinaujowo.DBQuery.REVIEW;
import static com.juraganpisang.sinaujowo.DBQuery.UNANSWERED;
import static com.juraganpisang.sinaujowo.DBQuery.g_categoryModelList;
import static com.juraganpisang.sinaujowo.DBQuery.g_questList;
import static com.juraganpisang.sinaujowo.DBQuery.g_selected_cat_index;
import static com.juraganpisang.sinaujowo.DBQuery.g_selected_test_index;
import static com.juraganpisang.sinaujowo.DBQuery.g_testModelList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.juraganpisang.sinaujowo.Adapters.QuestionGridAdapter;
import com.juraganpisang.sinaujowo.Adapters.QuestionsAdapter;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private TextView tvQuestID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuestB, nextQuestB, closeDrawerB;
    private ImageView questListB, markImage;
    private int questID;
    private QuestionsAdapter questionsAdapter;
    private DrawerLayout drawerLayout;
    private GridView questListGV;
    private QuestionGridAdapter gridAdapter;
    private CountDownTimer timer;
    private long timeLeft;

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

        gridAdapter = new QuestionGridAdapter(this, g_questList.size());
        questListGV.setAdapter(gridAdapter);

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
        markImage = findViewById(R.id.mark_image);
        questListGV = findViewById(R.id.quest_list_gv);

        questID = 0;

        tvQuestID.setText("1/" +String.valueOf(g_questList.size()));
        catNameTV.setText(g_categoryModelList.get(g_selected_cat_index).getName());

        g_questList.get(0).setStatus(UNANSWERED);
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

                if(g_questList.get(questID).getStatus() == NOT_VISITED){
                    g_questList.get(questID).setStatus(UNANSWERED);
                }

                if(g_questList.get(questID).getStatus() == REVIEW){
                    markImage.setVisibility(View.GONE);
                }else{
                    markImage.setVisibility(View.GONE);
                }

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
             g_questList.get(questID).setStatus(UNANSWERED);
             markImage.setVisibility(View.GONE);
             questionsAdapter.notifyDataSetChanged();
         }
     });

     questListB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(!drawerLayout.isDrawerOpen(GravityCompat.END)){
                 gridAdapter.notifyDataSetChanged();
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

     markB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(markImage.getVisibility() != View.VISIBLE){
                 markImage.setVisibility(View.VISIBLE);

                 g_questList.get(questID).setStatus(REVIEW);
             }else{
                 markImage.setVisibility(View.GONE);

                 if(g_questList.get(questID).getSelectedAns() != -1){
                     g_questList.get(questID).setStatus(ANSWERED);
                 }else{
                     g_questList.get(questID).setStatus(UNANSWERED);
                 }
             }
         }
     });

     submitB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             submitTest();
         }
     });
    }

    private void submitTest(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);

        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB = view.findViewById(R.id.confirmB);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                alertDialog.dismiss();

                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = g_testModelList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });

        alertDialog.show();
    }

    public void goToQuestion(int position){
        questionsView.smoothScrollToPosition(position);

        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void startTimer(){
        long totalTime = g_testModelList.get(g_selected_test_index).getTime()*60*1000;

        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remaining) {

                timeLeft = remaining;

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remaining),
                        TimeUnit.MILLISECONDS.toSeconds(remaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaining))
                        );

                timerTV.setText(time);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                long totalTime = g_testModelList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME_TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        };

        timer.start();
    }
}