package com.example.joseph.mobileproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joseph.mobileproject.fragments.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    int count = 0;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        myDB = new DBHelper(this);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[3];

        drawerItem[0] = new DataModel(R.drawable.calendar, "Today's Expense");
        drawerItem[2] = new DataModel(R.drawable.category, "Category");
        drawerItem[1] = new DataModel(R.drawable.progress_report, "Expense Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        if(getIntent().getExtras()!=null)
            count=getIntent().getExtras().getInt("pos");
        else count =0;
        if(count==0)
        {
            selectItem(0);
        }
        else
            selectItem(count);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new ExpenseFragment();
                break;
            case 1:
                fragment = new ReportFragment();
                break;
            case 2:
                fragment = new CategoryFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.expenses_list_view)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_listview, menu);
        }
        else if (v.getId()==R.id.category_view_list)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_listview_c, menu);
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.edit:
                Expenses e = getListViewInformation(info.position);
                Intent i = new Intent(getApplicationContext(), editMoneyExpenses.class);
                i.putExtra("NAME", e.getName());
                i.putExtra("AMOUNT", e.getAmount());
                i.putExtra("CATEGORY", e.getCategory());
                startActivity(i);
                return true;
            case R.id.delete:
                Expenses e2 = getListViewInformation(info.position);
                String id = myDB.getExpenseId(e2.getName(),e2.getAmount(),e2.getCategory());
                myDB.deleteExpense(id);
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                i2.putExtra("pos",0);
                startActivity(i2);
                return true;
            case R.id.editC:
                Intent i3 = new Intent(getApplicationContext(),addCategory.class);
                String cname = getListViewInfoCategory(info.position).toString();
                i3.putExtra("pos",2);
                i3.putExtra("NAME", cname );
                startActivity(i3);
                return true;
            case R.id.deleteC:
                String category = getListViewInfoCategory(info.position).toString();;
                myDB.deleteCategory(category);
                Intent i4 = new Intent(getApplicationContext(), MainActivity.class);
                i4.putExtra("pos",2);
                startActivity(i4);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    Cursor temp;
    ArrayList<Expenses> expensesArray;
    ArrayList<String> categoryArray;


    private Expenses getListViewInformation(int position) {
        expensesArray = new ArrayList<Expenses>();
        temp = myDB.getListContents();

        if (temp.getCount() == 0) {
        } else {
            while (temp.moveToNext()) {
                Expenses tempex = new Expenses(temp.getString(1), temp.getString(2), temp.getString(3), temp.getString(4));
                expensesArray.add(tempex);
            }
        }
        return expensesArray.get(position);
    }

    private String getListViewInfoCategory(int position)
    {
        String cat = null;
        categoryArray = new ArrayList<String>();
        temp = myDB.getListCategory();
            while (temp.moveToNext()) {
               cat = temp.getString(0);
               categoryArray.add(cat);
            }
        return categoryArray.get(position);
    }
}