package com.xz.sm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xz.sm.entity.KdList;

import java.util.ArrayList;
import java.util.List;

public class OddAdapter extends RecyclerView.Adapter<OddAdapter.ViewHolder> {

    private Context mContext;
    private List<KdList> mList;

    public OddAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    /**
     * 刷新数据
     * @param lists
     */
    public void refresh(List<KdList> lists){
        mList.addAll(lists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
