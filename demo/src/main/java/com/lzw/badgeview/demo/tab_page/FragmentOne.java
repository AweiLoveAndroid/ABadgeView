package com.lzw.badgeview.demo.tab_page;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lzw.badgeview.ABadgeImageView;
import com.lzw.badgeview.IABadge;
import com.lzw.badgeview.OnBadgeDragStateChangedListener;
import com.lzw.badgeview.annotation.ABadgeDragState;
import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.demo.databinding.FragmentDashboardBinding;
import com.lzw.badgeview.demo.databinding.FragmentOneBinding;

public class FragmentOne extends Fragment {

    private FragmentOneBinding binding;

    public static final String TAG = FragmentOne.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOneBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}