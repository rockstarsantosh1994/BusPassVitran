package com.rockstar.buspassvitran.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.model.BusPassData;

import java.util.List;

/**
 * Created by rockstar on 2/2/19.
 */

public class BusPassAdapter extends RecyclerView.Adapter<BusPassAdapter.BusViewHolder>{

    Context context;
    List<BusPassData> busList;



    public BusPassAdapter(Context context, List<BusPassData> bus_list) {
        this.context=context;
        this.busList=bus_list;
        //this.communication=fragmentCommunication;
    }

    @Override
    public BusPassAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bus_pass_adapter,parent,false);
        return new BusViewHolder(view);
    }



    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        BusPassData busData= busList.get(position);
        holder.tv_busstand.setText(busData.getBus_Stand());
        holder.tv_stdcode.setText(busData.getSTD_Code());
        holder.tv_telephone.setText(busData.getTelephone());

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder{

        TextView tv_busstand,tv_stdcode,tv_telephone;



        public BusViewHolder(View itemView) {
            super(itemView);

            // this.fragmentCommunication=fragmentCommunication;
            tv_busstand= (TextView)itemView.findViewById(R.id.tv_busstand);
            tv_stdcode= (TextView)itemView.findViewById(R.id.tv_stdcode);
            tv_telephone= (TextView)itemView.findViewById(R.id.tv_telephone);
        }
    }

}

