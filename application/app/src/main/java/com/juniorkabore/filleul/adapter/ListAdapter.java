package com.juniorkabore.filleul.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private String[] mDateset;

    public ListAdapter(String[] dateset) {
        mDateset = dateset;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mDateset[i]);
    }

    @Override
    public int getItemCount() {
        return mDateset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
