package com.juraganpisang.sinaujowo;

import static com.juraganpisang.sinaujowo.DBQuery.g_userList;
import static com.juraganpisang.sinaujowo.DBQuery.g_usersCount;
import static com.juraganpisang.sinaujowo.DBQuery.myPerfomance;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juraganpisang.sinaujowo.Adapters.RankAdapter;

public class LeaderBoardFragment extends Fragment {

    private TextView totalUsersTV, myImgTextTV, myScoreTV, myRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private Dialog progressDialog;
    private TextView dialogText;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Rangking");

        initViews(view);

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Proses...");

        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);

        adapter = new RankAdapter(DBQuery.g_userList);

        usersView.setAdapter(adapter);

        DBQuery.getTopUsers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();

                if(myPerfomance.getScore() != 0){

                    if(! DBQuery.isMeOnTopList){
                        calculateRank();
                    }

                    myScoreTV.setText("Skor : "+ myPerfomance.getScore());
                    myRankTV.setText("Rangking : "+ myPerfomance.getRank());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {

                Toast.makeText(getContext(), "Ngapunten, Enten sing Mboten Beres",
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        totalUsersTV.setText("Total Pengguna : "+ DBQuery.g_usersCount);
        myImgTextTV.setText(myPerfomance.getName().toUpperCase().substring(0,1));

        return view;
    }


    private void initViews(View view) {
        totalUsersTV = view.findViewById(R.id.total_users);
        myImgTextTV = view.findViewById(R.id.img_text);
        myScoreTV = view.findViewById(R.id.total_score);
        myRankTV = view.findViewById(R.id.rank);
        usersView = view.findViewById(R.id.users_view);

    }

    private void calculateRank() {
        int lowTopScore = g_userList.get(g_userList.size() - 1).getScore();

        int remaining_slots = g_usersCount - 20;

        int mySlot = (myPerfomance.getScore()*remaining_slots) / lowTopScore;

        int rank;
        if(lowTopScore != myPerfomance.getScore()){
            rank = g_usersCount - mySlot;
        }else{
            rank = 21;
        }

        myPerfomance.setRank(rank);
    }
}