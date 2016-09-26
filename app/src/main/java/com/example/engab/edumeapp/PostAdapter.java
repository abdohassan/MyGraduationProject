package com.example.engab.edumeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by engab on 09-May-16.
 */
public class PostAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Post> post;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public PostAdapter(Activity activity, List<Post> post) {
        this.activity = activity;
        this.post = post;
        imageLoader = AppController.getInstance().getImageLoader();
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return post.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int location) {
        return post.get(location);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.post_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Post m = post.get(position);
        holder.setDetails(m);

        return convertView;
    }

    private class ViewHolder implements View.OnClickListener {

        NetworkImageView thumbNail;
        TextView name;
        TextView content;
        Button b1, b2;
        private Post post;

        public ViewHolder(View itemView) {

            thumbNail = (NetworkImageView) itemView.findViewById(R.id.profilePic);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            b1 = (Button) itemView.findViewById(R.id.button);
            b1.setOnClickListener(this);
            b1.setTag(R.string.app_name, false);
            b2 = (Button) itemView.findViewById(R.id.button1);
            b2.setOnClickListener(this);
        }

        public void setDetails(Post m) {
            post = m;
            thumbNail.setImageUrl(m.getProfilepicture(), imageLoader);
            name.setText(m.getName());
            content.setText(m.getContent());
        }

        @Override
        public void onClick(View v) {
            if (v == b1) {
                boolean liked = (boolean) b1.getTag(R.string.app_name);
                if (liked) {
                    b1.setBackgroundResource(R.drawable.like);
                    b1.setTag(R.string.app_name, false);
                } else {
                    b1.setBackgroundResource(R.drawable.liked_orange);
                    b1.setTag(R.string.app_name, true);
                }
            } else if (v == b2) {
                Intent intent = new Intent(activity, Comment.class);
                intent.putExtra("id",post.getId());
                intent.putExtra("content",post.getContent());
                intent.putExtra("name",post.getName());
                intent.putExtra("pic",post.getProfilepicture());
                activity.startActivity(intent);
            }
        }
    }
}
