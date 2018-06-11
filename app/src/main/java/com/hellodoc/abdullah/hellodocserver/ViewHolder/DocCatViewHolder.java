package com.hellodoc.abdullah.hellodocserver.ViewHolder;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellodoc.abdullah.hellodocserver.Common.Common;
import com.hellodoc.abdullah.hellodocserver.Interface.ItemClickListner;
import com.hellodoc.abdullah.hellodocserver.R;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DocCatViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txtDocName;
    public ImageView imageView;

    private ItemClickListner itemClicklistener;

    public DocCatViewHolder(View itemView) {
        super(itemView);

        txtDocName = (TextView)itemView.findViewById(R.id.doc_name);

        imageView=(ImageView) itemView.findViewById(R.id.doc_pic);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);



    }

    public void setItemClicklistener(ItemClickListner itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    @Override
    public void onClick(View view) {
        itemClicklistener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select The Action");
        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);



    }
}
