package com.example.asus.konusarakogrenprojesi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.konusarakogrenprojesi.Interface.ItemClickListener;
import com.example.asus.konusarakogrenprojesi.PageActivity;
import com.example.asus.konusarakogrenprojesi.R;
import com.example.asus.konusarakogrenprojesi.model.RSSObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Asus on 12.09.2017.
 */

class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView txtTitle,txtPubDate,txtContent;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);
        txtTitle =(TextView) itemView.findViewById(R.id.txtTitle);
        txtPubDate =(TextView) itemView.findViewById(R.id.txtPubDate);
        txtContent =(TextView) itemView.findViewById(R.id.txtContent);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
public class FeedAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    private RSSObject rssObject;
    private Context nContext;
    private LayoutInflater inflater;


    public FeedAdapter(RSSObject rssObject, Context nContext) {
        this.rssObject = rssObject;
        this.nContext = nContext;
        inflater= LayoutInflater.from(nContext);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.row,parent,false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, int position) {
        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){


                    CharSequence getUrl=rssObject.getItems().get(position).getLink();
                    CharSequence getContent=rssObject.getItems().get(position).getContent();
                    Intent intent = new Intent(nContext.getApplicationContext(), PageActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("getUrl",getUrl);

                    nContext.getApplicationContext().startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return 5;
        //return rssObject.items.size();

    }
}
