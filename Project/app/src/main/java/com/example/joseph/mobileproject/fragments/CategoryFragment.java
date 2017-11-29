package com.example.joseph.mobileproject.fragments;

/**
 * Created by Joseph on 11/17/17.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joseph.mobileproject.DBHelper;
import com.example.joseph.mobileproject.R;
import com.example.joseph.mobileproject.addCategory;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    DBHelper myDB;
    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category_list, container, false);

        ListView lv = (ListView) rootView.findViewById(R.id.category_view_list);
        registerForContextMenu(lv);
        myDB = new DBHelper(getActivity() );

        FloatingActionButton fabC = (FloatingActionButton) rootView.findViewById(R.id.fabC);
        fabC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getActivity(), addCategory.class);
                i2.putExtra("NAME", "null1" );
                startActivity(i2);
            }
        });

        Cursor data = myDB.getListCategory();
        data.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!data.isAfterLast()) {
            names.add(data.getString(data.getColumnIndex("C_Name")));
            data.moveToNext();
        }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(), R.layout.list_view_item_row, R.id.textViewName, names);
                lv.setAdapter(adapter);




        return rootView;
    }


}