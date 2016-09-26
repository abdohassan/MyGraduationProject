package com.example.engab.edumeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by engab on 16-May-16.
 */
public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Interests> interest;

    public InterestAdapter(Context context, List<Interests> interest)
    {
        super();
        this.interest = interest;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Interests interests = interest.get(position);
        holder.name.setText(interests.getInterest());

    }

    @Override
    public int getItemCount() {
        return interest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textView10);



        }

    }
}
