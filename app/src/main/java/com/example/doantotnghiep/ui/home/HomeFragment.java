package com.example.doantotnghiep.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.doantotnghiep.HomeActivity;
import com.example.doantotnghiep.MainActivity;
import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.PagerAdapter;
import com.example.doantotnghiep.ui.fragment.ThongKeTheoNgayFragment;
import com.example.doantotnghiep.ui.fragment.ThongKeTheoThangFragment;
import com.example.doantotnghiep.ui.fragment.ThongKeTheoTuanFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        adapter.addFragment(new ThongKeTheoNgayFragment(), "Ngày");
        adapter.addFragment(new ThongKeTheoTuanFragment(), "Tuần");
        adapter.addFragment(new ThongKeTheoThangFragment(), "Tháng");

        viewPager.setAdapter(adapter);

    }
}