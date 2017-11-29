package com.example.joseph.mobileproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Joseph on 11/17/17.
 */

public class addMoneyExpenses extends AppCompatActivity {

    DBHelper myDB;
    EditText amount, name, date;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        amount = (EditText) findViewById(R.id.expense_value_edit_text);
        name = (EditText) findViewById(R.id.name_edit_text);
        s = (Spinner) findViewById(R.id.spinner);


        myDB = new DBHelper(this);

        ArrayList<String> thelist = new ArrayList<String>();
        Cursor data = myDB.getListCategory();

        if (data.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Please add category", Toast.LENGTH_LONG).show();
        }
        else {
            while (data.moveToNext()) {
                thelist.add(data.getString(0));
                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thelist);
                s.setAdapter(listAdapter);
            }
        }

        Button back = (Button) findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = s.getSelectedItem().toString();
                boolean yes = myDB.addData(name.getText().toString(), Integer.parseInt(amount.getText().toString()), text);
                //boolean yes = myDB.editExpense("1b", "1a", "12");


                if (yes)
                {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data incomplete", Toast.LENGTH_LONG).show();
                }

            }
        });
        /*

        Button noSubmit = (Button) findViewById(R.id.button2);
        noSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        */
    }
}
