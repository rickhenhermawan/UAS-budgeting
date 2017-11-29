package com.example.joseph.mobileproject.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mobileproject.CustomAdapter;
import com.example.joseph.mobileproject.DBHelper;
import com.example.joseph.mobileproject.Expenses;
import com.example.joseph.mobileproject.MainActivity;
import com.example.joseph.mobileproject.R;
import com.example.joseph.mobileproject.Util;
import com.example.joseph.mobileproject.addMoneyExpenses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Joseph on 11/17/17.
 */

public class ExpenseFragment extends Fragment {

    DBHelper myDB;
    ArrayList<Expenses> expensesArray;
    TextView total;
    Cursor temp;

    public ExpenseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.expenses_list_view);
        registerForContextMenu(lv);
        myDB = new DBHelper(getActivity());
        setHasOptionsMenu(true);
        Util util = new Util();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), addMoneyExpenses.class);
                startActivity(i);
            }
        });

        TextView tx = (TextView) rootView.findViewById(R.id.total_expense_sum_text_view);
        Integer sum = myDB.sumAllToday();
        tx.setText(util.rupiah(sum.toString()));

        expensesArray = new ArrayList<Expenses>();
        Cursor data = myDB.getTodayListContents();


        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "The database is empty", Toast.LENGTH_LONG).show();

        } else {
            while (data.moveToNext()) {
                Expenses temp = new Expenses(data.getString(1), data.getString(2), data.getString(3), data.getString(4));
                expensesArray.add(temp);
                CustomAdapter listAdapter = new CustomAdapter(getActivity(), R.layout.listview_format, expensesArray);
                lv.setAdapter(listAdapter);
            }
        }

        return rootView;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }


}
