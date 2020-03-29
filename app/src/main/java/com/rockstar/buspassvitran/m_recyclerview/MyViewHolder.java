package com.rockstar.buspassvitran.m_recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockstar.buspassvitran.R;


/**
 * Created by Oclemy on 7/29/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;
    ImageView img;

    public MyViewHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        img= (ImageView) itemView.findViewById(R.id.spacecraftImg);

    }
}
