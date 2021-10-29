package com.juraganpisang.sinaujowo.Adapters;

import static com.juraganpisang.sinaujowo.DBQuery.ANSWERED;
import static com.juraganpisang.sinaujowo.DBQuery.NOT_VISITED;
import static com.juraganpisang.sinaujowo.DBQuery.REVIEW;
import static com.juraganpisang.sinaujowo.DBQuery.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.juraganpisang.sinaujowo.DBQuery;
import com.juraganpisang.sinaujowo.QuestionsActivity;
import com.juraganpisang.sinaujowo.R;

public class QuestionGridAdapter extends BaseAdapter {

    private int numOfQuest;
    private Context context;

    public QuestionGridAdapter(Context context, int numOfQuest) {
        this.context = context;
        this.numOfQuest = numOfQuest;
    }

    @Override
    public int getCount() {
        return numOfQuest;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myView;

        if(view == null){
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quest_grid_item, viewGroup, false);
        }else{
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof QuestionsActivity)
                    ((QuestionsActivity) context).goToQuestion(i);
            }
        });

        TextView questTv = myView.findViewById(R.id.quest_num);
        questTv.setText(String.valueOf(i+1));

        switch (DBQuery.g_questList.get(i).getStatus()){
            case NOT_VISITED :
                questTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.grey)));
                break;
            case UNANSWERED :
                questTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.red)));
                break;
            case ANSWERED:
                questTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.green)));
                break;
            case REVIEW:
                questTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.purple)));
                break;
            default:
                break;
        }
        return myView;
    }
}
