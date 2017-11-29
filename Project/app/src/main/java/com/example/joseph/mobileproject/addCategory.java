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
 * Created by Joseph on 11/18/17.
 */

public class addCategory  extends AppCompatActivity {

    DBHelper myDB;
    EditText category;
    Button submit;
    String temp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_activity);


        category = (EditText) findViewById(R.id.categoryInput);
        submit = (Button) findViewById(R.id.SubmitCategory);

        myDB = new DBHelper(this);

        if ((getIntent().getExtras().getString("NAME")).equals("null1")) {

            category.setText("");
        }
        else {
            category.setText(getIntent().getExtras().getString("NAME"));
            temp=getIntent().getExtras().getString("NAME");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean yes = false;
                if(getIntent().getExtras().getString("NAME").equals("null1"))
                {
                    yes = myDB.addCategory(category.getText().toString());
                }
                else
                {
                    yes = myDB.editCategory(temp,category.getText().toString());
                }
                //boolean yes = myDB.editExpense("1b", "1a", "12");

                if (yes)
                {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    int pos = 2;
                    i.putExtra("pos", pos );
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data incomplete", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
