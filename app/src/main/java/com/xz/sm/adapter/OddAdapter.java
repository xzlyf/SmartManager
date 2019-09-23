package com.xz.sm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xz.sm.R;
import com.xz.sm.entity.KdAccept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OddAdapter extends RecyclerView.Adapter<OddAdapter.ViewHolder> {

    private Context mContext;
    private List<KdAccept> mList;


    public OddAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    /**
     * 刷新数据
     *
     * @param lists
     */
    public void refresh(List<KdAccept> lists) {
        Collections.reverse(lists);//反转,时间前后排序
        mList.addAll(lists);
        notifyDataSetChanged();
    }

    public void cleanAll(){
        mList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_traces, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.acceptTime.setText(mList.get(i).getAcceptTime());
        viewHolder.acceptStation.setText(mList.get(i).getAcceptStation());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView acceptTime;
        TextView acceptStation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            acceptTime = itemView.findViewById(R.id.accept_time);
            acceptStation = itemView.findViewById(R.id.accept_station);
        }
    }
}
