package com.juniorkabore.filleul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.List;

/**
 * Created by juniorkabore on 16/03/2016.
 */
public class messagedesignAdapter extends ArrayAdapter<messagedesign> {



    public messagedesignAdapter(Context context, List<messagedesign> messagedesigns) {
        super(context, 0, messagedesigns);
    }

    private class messagedesignViewHolder{
        public ProfilePictureView profilePictureView;
        public TextView userName;
        public TextView derniermessages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messagedesign,parent, false);
        }

        messagedesignViewHolder viewHolder = (messagedesignViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new messagedesignViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.derniermessages = (TextView) convertView.findViewById(R.id.derniermessages);
            viewHolder.profilePictureView = (ProfilePictureView) convertView.findViewById(R.id.profilePicture);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<messagedesign> messagedesigns
        messagedesign messagedesign = getItem(position);
        viewHolder.userName.setText(messagedesign.getUserName());
        viewHolder.derniermessages.setText(messagedesign.getDerniermessages());
        //viewHolder.avatar.setImageDrawable(new ColorDrawable(tweet.getColor()));



        return convertView;
    }
}
