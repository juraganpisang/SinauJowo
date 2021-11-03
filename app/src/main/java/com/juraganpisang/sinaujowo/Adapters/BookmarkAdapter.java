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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private List<QuestionModel> questList;

    public BookmarkAdapter(List<QuestionModel> questList) {
        this.questList = questList;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);

        return new BookmarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {

        String quest = questList.get(position).getQuestion();
        String a = questList.get(position).getOptionA();
        String b = questList.get(position).getOptionB();
        String c = questList.get(position).getOptionC();
        String d = questList.get(position).getOptionD();
        int answer = questList.get(position).getCorrectAns();

        holder.setData(position, quest, a, b, c, d, answer);
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

        private void setData(int pos, String quest, String a, String b, String c, String d, int correctAns){

            questNo.setText("Pitakon No, "+String.valueOf(pos+1));
            question.setText(quest);
            optionA.setText("A. "+a);
            optionB.setText("B. "+b);
            optionC.setText("C. "+c);
            optionD.setText("D. "+d);

            if(correctAns == 1){
                result.setText("JAWABAN : "+a);
            }else if(correctAns == 2){
                result.setText("JAWABAN : "+b);
            }else if(correctAns == 3){
                result.setText("JAWABAN : "+c);
            }else if(correctAns == 4){
                result.setText("JAWABAN : "+d);
            }

        }
    }
}
