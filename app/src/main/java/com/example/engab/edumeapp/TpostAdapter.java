package com.example.engab.edumeapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by engab on 10-May-16.
 */
public class TpostAdapter extends RecyclerView.Adapter<TpostAdapter.ViewHolder2> {
    private Context context;
    private LayoutInflater inflater;
    private List<Tpost> post;
    CustomItemClickListener listener;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TpostAdapter(Context context, List<Tpost> post) {
        super();
        this.post = post;
        this.context = context;


    }


    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        final ViewHolder2 viewHolder = new ViewHolder2(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder2 holder, int position) {
        Tpost tpost = post.get(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(tpost.getProfilepicture(),ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.imageView.setImageUrl(tpost.getProfilepicture(),imageLoader);
        holder.name.setText(tpost.getName());
        holder.content.setText(tpost.getContent());



    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView name,content;

        public ViewHolder2(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.profilePic);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.txtStatusMsg);


        }
    }
}