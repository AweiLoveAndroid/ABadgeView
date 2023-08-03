package com.lzw.badgeview.demo.tab_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lzw.badgeview.demo.databinding.FragmentOneBinding;
import com.lzw.badgeview.demo.databinding.FragmentThreeBinding;

public class FragmentThree extends Fragment {

    private FragmentThreeBinding binding;

    public static final String TAG = FragmentThree.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentThreeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}