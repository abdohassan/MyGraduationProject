package com.example.engab.edumeapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by engab on 25-Jun-16.
 */
public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.ViewHolder>{
    private List<coment> com;
    private Activity activity;
    CustomItemClickListener listener;

    public Comment_Adapter(List<coment> com, Activity activity ) {
        super();
        this.com = com;
        this.activity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        coment coment = com.get(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(coment.getThumbnailUrl(),ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.imageView.setImageUrl(coment.getThumbnailUrl(),imageLoader);
        holder.name.setText(coment.getName());
        holder.comment.setText(coment.getContent());

    }

    @Override
    public int getItemCount() {
        return com.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView comment,name;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.proPic);
            comment = (TextView) itemView.findViewById(R.id.comment);
            name =(TextView)itemView.findViewById(R.id.name);


        }
    }
}