package com.adolf.pcmaster.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolf.pcmaster.R;
import com.adolf.pcmaster.model.TrainingItemBean;
import com.adolf.pcmaster.ui.TrainingActivity;
import com.adolf.pcmaster.view.ZoomCircleView;

import java.util.List;

/**
 * @program: PCMaster
 * @description:
 * @author: Adolf
 * @create: 2020-08-19 17:29
 **/
public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private Context mContext;
    private TrainingActivity mActivity;
    private List<TrainingItemBean> trainings;

    public TrainingAdapter(List<TrainingItemBean> trainings, Activity activity) {

        this.trainings = trainings;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        mActivity = (TrainingActivity) mContext;
        View view = LayoutInflater.from(mContext).inflate(R.layout.training_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemZoomCircle.setVisibility(View.GONE);
        holder.itemBg.setVisibility(View.VISIBLE);

        TrainingItemBean training = trainings.get(position);
        holder.itemName.setText(training.getName());
        holder.itemZoomCircle.setModelAndLoop(training.getModel(), training.getLoop());
        holder.itemZoomCircle.setFinishListener(new ZoomCircleView.OnFinishListener() {
            @Override
            public void showInfo() {
                holder.itemZoomCircle.setVisibility(View.GONE);
                holder.itemBg.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView itemBg;
        private final TextView itemName;
        private final ZoomCircleView itemZoomCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBg = itemView.findViewById(R.id.item_iv_bg);
            itemName = itemView.findViewById(R.id.item_tv_name);
            itemZoomCircle = itemView.findViewById(R.id.item_zcv);
            itemZoomCircle.setFinishListener(() -> {
                mActivity.setItemFinish(true);
                Log.d("jq", "ViewHolder: listener");
                Toast.makeText(mActivity, "finish", Toast.LENGTH_SHORT).show();
                mActivity.setCurPosition(-1);
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            });

            itemView.setOnClickListener(v -> {
                if (mActivity.isItemFinish()) {
                    itemBg.setVisibility(View.GONE);
                    itemZoomCircle.setVisibility(View.VISIBLE);
                    mActivity.setCurPosition(getAdapterPosition());
                    mActivity.setItemFinish(false);
                }
            });
        }
    }
}
