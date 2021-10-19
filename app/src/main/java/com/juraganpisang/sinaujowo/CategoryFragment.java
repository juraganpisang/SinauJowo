package com.juraganpisang.sinaujowo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    private GridView catView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        catView = view.findViewById(R.id.category_GV);

//        loadCategories();

        CategoryAdapter adapter = new CategoryAdapter(DBQuery.g_categoryModelList);
        catView.setAdapter(adapter);

        return view;
    }

}