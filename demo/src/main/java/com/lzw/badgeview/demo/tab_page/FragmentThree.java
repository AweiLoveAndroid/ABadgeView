package com.lzw.badgeview.demo.tab_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lzw.badgeview.demo.R;

public class FragmentThree extends Fragment {

    public static final String TAG = FragmentThree.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_three, container, false);
        return root;
    }

}