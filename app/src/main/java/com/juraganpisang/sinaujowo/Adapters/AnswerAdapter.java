package com.juraganpisang.sinaujowo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juraganpisang.sinaujowo.Models.QuestionModel;
import com.juraganpisang.sinaujowo.R;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<QuestionModel> questList;

    public AnswerAdapter(List<QuestionModel> questList) {
        this.questList = questList;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);

        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {

        String quest = questList.get(position).getQuestion();
        String a = questList.get(position).getOptionA();
        String b = questList.get(position).getOptionB();
        String c = questList.get(position).getOptionC();
        String d = questList.get(position).getOptionD();
        int selected = questList.get(position).getSelectedAns();
        int answer = questList.get(position).getCorrectAns();

        holder.setData(position, quest, a, b, c, d, selected, answer);
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questNo, question, optionA, optionB, optionC, optionD, result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questNo = itemView.findViewById(R.id.questNo);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            result = itemView.findViewById(R.id.result);
        }

        private void setData(int pos, String quest, String a, String b, String c, String d, int selected, int correctAns){

            questNo.setText("Pitakon No. "+String.valueOf(pos+1));
            question.setText(quest);
            optionA.setText("A. "+a);
            optionB.setText("B. "+b);
            optionC.setText("C. "+c);
            optionD.setText("D. "+d);

            if(selected == -1){
                result.setText("MBOTEN DIJAWAB");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                setOptionColor(selected, R.color.text_normal);
            }else{
                if(selected == correctAns){
                    result.setText("BENER");;
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                    setOptionColor(selected, R.color.green);
                }else{
                    result.setText("SALAH");;
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                    setOptionColor(selected, R.color.red);
                }
            }
        }

        private void setOptionColor(int selected, int color){

            if(selected == 1){
                optionA.setTextColor(itemView.getContext().getResources().getColor(color));
            }else{
                optionA.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }


            if(selected == 2){
                optionB.setTextColor(itemView.getContext().getResources().getColor(color));
            }else{
                optionB.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }


            if(selected == 3){
                optionC.setTextColor(itemView.getContext().getResources().getColor(color));
            }else{
                optionC.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }


            if(selected == 4){
                optionD.setTextColor(itemView.getContext().getResources().getColor(color));
            }else{
                optionD.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }
        }
    }
}
