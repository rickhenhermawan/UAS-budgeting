package com.example.joseph.mobileproject.fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mobileproject.CustomAdapter;
import com.example.joseph.mobileproject.DBHelper;
import com.example.joseph.mobileproject.Expenses;
import com.example.joseph.mobileproject.R;
import com.example.joseph.mobileproject.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joseph on 11/18/17.
 */

public class ReportFragment extends Fragment {

    DBHelper myDB;
    ArrayList<Expenses> expensesArray;
    TextView total, tx;
    Cursor temp;
    EditText editText;
    Integer sum;
    Util util;
    ListView lv;

    CustomAdapter listAdapter = null;


    public ReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        lv = (ListView) rootView.findViewById(R.id.expenses_list_view);

        registerForContextMenu(lv);
        myDB = new DBHelper(getActivity());
        setHasOptionsMenu(true);
        util = new Util();

        tx = (TextView) rootView.findViewById(R.id.total_expense_sum_text_view);
        sum = myDB.sumAll();
        tx.setText(util.rupiah(sum.toString()));

        expensesArray = new ArrayList<Expenses>();

        Cursor data = myDB.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "The database is empty", Toast.LENGTH_LONG).show();

        } else {
            while (data.moveToNext()) {
                Expenses temp = new Expenses(data.getString(1), data.getString(2), data.getString(3), data.getString(4));
                expensesArray.add(temp);
                listAdapter = new CustomAdapter(getActivity(), R.layout.listview_format, expensesArray);
                lv.setAdapter(listAdapter);
                
            }
        }

       // EditText datepick = (EditText) rootView.findViewById(R.id.dateEditText);
        Button datebutton = (Button) rootView.findViewById(R.id.dateButton);
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum = myDB.sumAll(editText.getText().toString());
                tx.setText(util.rupiah(sum.toString()));


                expensesArray = new ArrayList<Expenses>();

                Cursor data = myDB.getListContents(editText.getText().toString());
                listAdapter.clear();
                if (data.getCount() == 0) {
                    Toast.makeText(getActivity(), "The database is empty", Toast.LENGTH_LONG).show();

                } else {
                    while (data.moveToNext()) {
                        Expenses temp = new Expenses(data.getString(1), data.getString(2), data.getString(3), data.getString(4));
                        expensesArray.add(temp);
                        listAdapter = new CustomAdapter(getActivity(), R.layout.listview_format, expensesArray);
                        lv.setAdapter(listAdapter);
                    }
                }

            }
        });

        myCalendar = Calendar.getInstance();

        editText= (EditText) rootView.findViewById(R.id.dateEditText);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;

    }

    private void updateLabel(EditText edittext) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sCertDate = dateFormat.format(myCalendar.getTime());
        edittext.setText(sCertDate);


    }

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }
}
