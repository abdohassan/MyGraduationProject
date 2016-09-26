package com.example.engab.edumeapp;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.engab.edumeapp.Lesson.Lecture;

import java.util.List;

/**
 * Created by engab on 09-May-16.
 */
public class LessonAdapter  extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Lesson1> lessons;

    public LessonAdapter(Context context, List<Lesson1> lessons)
    {
        this.context=context;
        this.lessons = lessons;
    }
    @Override
    public int getCount() {
        return lessons.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int location) {
        return lessons.get(location);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.lesson_item, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.v_name);
        TextView descip = (TextView) convertView.findViewById(R.id.v_url);
        Lesson1 m = lessons.get(position);
        name.setText(m.getCourse_name());

            descip.setText(Html.fromHtml("<a href=https://www.youtube.com/watch?v=" + m.getUrl() + "\">"
                    + m.getUrl() + "</a> "));

            // Making url clickable
            descip.setMovementMethod(LinkMovementMethod.getInstance());
            descip.setVisibility(View.VISIBLE);




        return convertView;

    }
}
