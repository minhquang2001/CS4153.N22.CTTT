package com.minhquang.sensor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.minhquang.sensor.Fragment.HomeFragment;
import com.minhquang.sensor.Fragment.LinearFragment;
import com.minhquang.sensor.Fragment.SystemLogsFragment;
import com.minhquang.sensor.Fragment.TemperatureFragment;
import com.minhquang.sensor.Fragment.VisualizeDataFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 5;

    public ViewPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TemperatureFragment();
            case 2:
                return new LinearFragment();
            case 3:
                return new VisualizeDataFragment();
            case 4:
                return new SystemLogsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
