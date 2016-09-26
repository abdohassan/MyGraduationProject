package com.example.engab.edumeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by engab on 15-May-16.
 */
public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.ViewHolder>
{
    private Context context;
    private List<MyCourse> pcourses;
    private ImageLoader imageLoader,imageLoader1;
    CustomItemClickListener listener;

    public MyCourseAdapter(Context context,List<MyCourse> pcourses )
           {
                super();
                this.pcourses=pcourses;
                this.context=context;


           }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcourse_item,parent,false);
                final ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                MyCourse course = pcourses.get(position);
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                imageLoader.get(course.getThumbnailUrl(),ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
                holder.imageView.setImageUrl(course.getThumbnailUrl(),imageLoader);
                holder.cName.setText(course.getTitle());

            }

            @Override
            public int getItemCount() {
                return pcourses.size();
            }


            public class ViewHolder extends RecyclerView.ViewHolder {
                public NetworkImageView imageView;
                public TextView cName;
                public ViewHolder(View itemView) {
                    super(itemView);
                    imageView = (NetworkImageView) itemView.findViewById(R.id.image1);
                    cName = (TextView) itemView.findViewById(R.id.c_name);


                }
            }
        }