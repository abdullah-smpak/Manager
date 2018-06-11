package com.hellodoc.abdullah.hellodocserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.hellodoc.abdullah.hellodocserver.Interface.ItemClickListner;
import com.hellodoc.abdullah.hellodocserver.R;

public class AppViewHolder  extends RecyclerView.ViewHolder implements   View.OnClickListener, View.OnCreateContextMenuListener
{
    public TextView txtdrnam,txtpatname,txtpatphone,txtappdate, txtapptime,txtstatus;

    private ItemClickListner itemClicklistener;

    public AppViewHolder(View itemView) {
        super(itemView);

        txtdrnam=(TextView)itemView.findViewById(R.id.dr_name);
        txtpatname = itemView.findViewById(R.id.pat_name);
        txtpatphone=(TextView)itemView.findViewById(R.id.pat_phone);
        txtappdate=(TextView)itemView.findViewById(R.id.app_date);
        txtapptime=(TextView)itemView.findViewById(R.id.app_time);
        txtstatus=(TextView)itemView.findViewById(R.id.app_status);


        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);


    }

    public void setItemClicklistener(ItemClickListner itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    @Override
    public void onClick(View view) {

        itemClicklistener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        contextMenu.setHeaderTitle("Select an Action");
        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,1,getAdapterPosition(),"Delete");

    }
}
