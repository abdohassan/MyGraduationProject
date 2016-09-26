package com.example.engab.edumeapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.engab.edumeapp.Lesson.Introdction;
import com.example.engab.edumeapp.Lesson.Lecture;
import com.example.engab.edumeapp.profile.CV;
import com.example.engab.edumeapp.profile.Courses;
import com.example.engab.edumeapp.profile.Timeline;

import java.util.ArrayList;
import java.util.List;

public class Lesson_Screen extends AppCompatActivity {
    private TabLayout tabLayout1;
    private ViewPager viewPager1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson__screen);
        getSupportActionBar().setElevation(0);
        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        setupViewPager(viewPager1);
        tabLayout1 = (TabLayout) findViewById(R.id.lesson_tabs);
        tabLayout1.setupWithViewPager(viewPager1);
    }

    private void setupViewPager(ViewPager viewPager1) {
        LessonAdapter adapter = new LessonAdapter(getSupportFragmentManager());
        adapter.addFragment(new Introdction(), "Introdction");
        adapter.addFragment(new Lecture(), "Lecture");
        viewPager1.setAdapter(adapter);
    }

    class LessonAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public LessonAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Lesson_Screen.this,Course_Screen.class);
        startActivity(intent);

    }
}