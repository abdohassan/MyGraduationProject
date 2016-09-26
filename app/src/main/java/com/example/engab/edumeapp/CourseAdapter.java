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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by engab on 05-May-16.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
     private Context context;
    private List<Course> courses;
    private ImageLoader imageLoader,imageLoader1;
    CustomItemClickListener listener;


    public CourseAdapter(List<Course> courses, CustomItemClickListener  listener ) {
        super();
        this.courses = courses;
        this.listener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item,parent,false);
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
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Course course = courses.get(position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(course.getThumbnailUrl(),ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.imageView.setImageUrl(course.getThumbnailUrl(),imageLoader);
        ImageLoader imageLoader1 = AppController.getInstance().getImageLoader();
        imageLoader.get(course.getThumbnailUrl(),ImageLoader.getImageListener(holder.PInstractor, R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.PInstractor.setImageUrl(course.getpInstractor(),imageLoader);
        holder.cName.setText(course.getTitle());
        holder.dicribtion.setText(course.getDescription());
        holder.NInstractor.setText(course.getnInstractor());




    }
    @Override
    public int getItemCount()
    {
        return courses.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView,PInstractor;
        public TextView cName,dicribtion,NInstractor;



        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.card_image);
            PInstractor=(NetworkImageView)itemView.findViewById(R.id.pinst);
            cName = (TextView) itemView.findViewById(R.id.card_text);
            dicribtion= (TextView) itemView.findViewById(R.id.card);
            NInstractor=(TextView)itemView.findViewById(R.id.ninst);



        }
    }


}
