package com.example.engab.edumeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by engab on 16-May-16.
 */
public class ExperiencesAdapter extends RecyclerView.Adapter<ExperiencesAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Experiences> exp;

    public ExperiencesAdapter(Context context, List<Experiences> exp)
    {
        super();
        this.exp = exp;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Experiences experiences = exp.get(position);
        holder.c_name.setText(experiences.getCompany_name());
        holder.title.setText(experiences.getTitle());
        holder.des.setText(experiences.getDiscription());

    }

    @Override
    public int getItemCount() {
        return exp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView c_name,title, des;

        public ViewHolder(View itemView)
        {
            super(itemView);

            c_name = (TextView) itemView.findViewById(R.id.textView19);
            title = (TextView) itemView.findViewById(R.id.textView20);
            des = (TextView) itemView.findViewById(R.id.textView21);



        }
    }
}
