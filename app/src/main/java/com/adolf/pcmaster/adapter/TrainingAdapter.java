package com.adolf.pcmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolf.pcmaster.R;
import com.adolf.pcmaster.model.TrainingItemBean;

import java.util.List;

/**
 * @program: PCMaster
 * @description:
 * @author: Adolf
 * @create: 2020-08-19 17:29
 **/
public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private Context mContext;
    private List<TrainingItemBean> trainings;

    public TrainingAdapter(List<TrainingItemBean> trainings) {
        this.trainings = trainings;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.training_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainingItemBean training = trainings.get(position);
        holder.itemName.setText(training.getName());
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView itemBg;
        private final TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBg = itemView.findViewById(R.id.item_iv_bg);
            itemName = itemView.findViewById(R.id.item_tv_name);
        }
    }
}
