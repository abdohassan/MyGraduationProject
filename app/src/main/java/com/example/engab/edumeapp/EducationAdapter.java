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
public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Education> edu;

    public EducationAdapter(Context context, List<Education> edu)
    {
        super();
        this.edu = edu;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edu_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Education education =edu.get(position);
        holder.name.setText(education.getS_name());
        holder.des.setText(education.getDescription());

    }

    @Override
    public int getItemCount() {
        return edu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView name, des;

        public ViewHolder(View itemView)
        {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.textView17);
        des = (TextView) itemView.findViewById(R.id.textView18);


    }
    }
}
