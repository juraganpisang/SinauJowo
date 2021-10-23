package com.juraganpisang.sinaujowo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionModel> questionModelList;

    public QuestionsAdapter(List<QuestionModel> questionModelList) {
        this.questionModelList = questionModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.setData(i);

    }

    @Override
    public int getItemCount() {
        return questionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quest;
        private Button optionA, optionB, optionC, optionD, prevSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quest = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

            prevSelectedB = null;
        }

        private void setData(int pos){
            quest.setText(questionModelList.get(pos).getQuestion());
            optionA.setText(questionModelList.get(pos).getOptionA());
            optionB.setText(questionModelList.get(pos).getOptionB());
            optionC.setText(questionModelList.get(pos).getOptionC());
            optionD.setText(questionModelList.get(pos).getOptionD());

            setOption(optionA, 1, pos);
            setOption(optionB, 2, pos);
            setOption(optionC, 3, pos);
            setOption(optionD, 4, pos);

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionA, 1, pos);
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionB, 2, pos);
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionC, 3, pos);
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionD, 4, pos);
                }
            });
        }

        private void selectOption(Button btn, int option_num,int questID){
            if(prevSelectedB == null){
                btn.setBackgroundResource(R.drawable.selected_btn);
                DBQuery.g_questList.get(questID).setSelectedAns(option_num);

                prevSelectedB = btn;
            }else{
                if(prevSelectedB.getId() == btn.getId()){
                    btn.setBackgroundResource(R.drawable.selected_btn);
                    DBQuery.g_questList.get(questID).setSelectedAns(-1);

                    prevSelectedB = null;
                }else{
                    prevSelectedB.setBackgroundResource(R.drawable.unselected_btn);
                    btn.setBackgroundResource(R.drawable.selected_btn);

                    DBQuery.g_questList.get(questID).setSelectedAns(option_num);

                    prevSelectedB = btn;
                }
            }
        }

        private void setOption(Button btn, int option_num,int questID){
            if(DBQuery.g_questList.get(questID).getSelectedAns() == option_num){
                btn.setBackgroundResource(R.drawable.selected_btn);
            }else{
                btn.setBackgroundResource(R.drawable.unselected_btn);
            }
        }
    }
}
