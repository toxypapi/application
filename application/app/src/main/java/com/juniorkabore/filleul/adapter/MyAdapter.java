package com.juniorkabore.filleul.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.juniorkabore.filleul.FileNews;
import com.juniorkabore.filleul.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juniorkabore on 28/10/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //private String[] mDataset;
    private List<FileNews> profilePerso = new ArrayList<FileNews>();



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ProfilePictureView profilePictureView;
        public TextView Name;
        public TextView textUniversiter;
        public TextView textStatut;
        public TextView addInfo;


        public ViewHolder(View v) {
            super(v);
            profilePictureView = (ProfilePictureView) v.findViewById(R.id.profilePicture);
            Name = (TextView) v.findViewById(R.id.Name);
            textUniversiter = (TextView) v.findViewById(R.id.textUniversiter);
            textStatut = (TextView) v.findViewById(R.id.textStatut);
            addInfo = (TextView) v.findViewById(R.id.addInfo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<FileNews> profilePerso) {
        this.profilePerso = profilePerso;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_file_news, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        FileNews fn = profilePerso.get(position);
        holder.Name.setText(fn.textName);
        holder.textStatut.setText(fn.tStatut);
        holder.textUniversiter.setText(fn.tUniversiter);
        holder.addInfo.setText( fn.textaddInfo);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return profilePerso.size();
    }
}