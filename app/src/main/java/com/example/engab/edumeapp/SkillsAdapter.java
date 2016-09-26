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
public class SkillsAdapter  extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Skills> skill;

    public SkillsAdapter(Context context, List<Skills> skill)
    {
        super();
        this.skill = skill;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Skills skills = skill.get(position);
        holder.name.setText(skills.getSkill());

    }

    @Override
    public int getItemCount() {
        return skill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.skill_item);



        }

    }
}
