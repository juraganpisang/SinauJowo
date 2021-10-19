package com.juraganpisang.sinaujowo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestModel> testModelList;

    public TestAdapter(List<TestModel> testModelList) {
        this.testModelList = testModelList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {

        int progress = testModelList.get(position).getTopScore();
        holder.setData(position, progress);
    }

    @Override
    public int getItemCount() {
        return testModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testNo, topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testNo = itemView.findViewById(R.id.testNoTv);
            topScore = itemView.findViewById(R.id.scoreTv);
            progressBar = itemView.findViewById(R.id.testProgressBar);
        }

        private void setData(int pos, int progress){
            testNo.setText("Test No : "+ String.valueOf(pos+1));
            topScore.setText(String.valueOf(progress) + "%");

            progressBar.setProgress(progress);
        }
    }
}
