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
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Projects> project;

    public ProjectAdapter(Context context, List<Projects> project)
    {
        super();
        this.project = project;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Projects projects=project.get(position);
        holder.title.setText(projects.getTitle());
        holder.des.setText(projects.getDescription());

    }

    @Override
    public int getItemCount() {
        return project.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title,des;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.textView15);
            des = (TextView) itemView.findViewById(R.id.textView16);



        }
    }
}
