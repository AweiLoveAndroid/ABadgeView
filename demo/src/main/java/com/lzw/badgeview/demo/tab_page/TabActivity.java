package com.lzw.badgeview.demo.tab_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.demo.tab_page.FragmentOne;
import com.lzw.badgeview.demo.tab_page.FragmentThree;
import com.lzw.badgeview.demo.tab_page.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        FragmentOne fragmentOne = new FragmentOne();
        FragmentTwo fragmentTwo = new FragmentTwo();
        FragmentThree fragmentThree = new FragmentThree();

        tabLayout.setupWithViewPager(viewPager);

        //create viewpager adapter
        //here we will create inner class for adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        //add fragments and set the adapter
        viewPagerAdapter.addFragment(fragmentOne,"页面1");
        viewPagerAdapter.addFragment(fragmentTwo,"页面2");
        viewPagerAdapter.addFragment(fragmentThree,"页面3");
        viewPager.setAdapter(viewPagerAdapter);

        //set the icons
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications_black_24dp);

        //set the badge
        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setBadgeText("5");

        BadgeDrawable badgeDrawable2 = tabLayout.getTabAt(1).getOrCreateBadge();
        badgeDrawable2.setVisible(true);
        badgeDrawable2.setBadgeText("20");

        BadgeDrawable badgeDrawable3 = tabLayout.getTabAt(2).getOrCreateBadge();
        badgeDrawable3.setVisible(true);
        badgeDrawable3.setBadgeText("测试");

//        tabLayout.getTabAt(0).setCustomView(R.layout.layout_custom_tab_xml);
//        tabLayout.getTabAt(1).setCustomView(R.layout.layout_custom_tab_xml);
//        tabLayout.getTabAt(2).setCustomView(R.layout.layout_custom_tab_xml);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        //add fragment to the viewpager
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        //to setup title of the tab layout
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

}