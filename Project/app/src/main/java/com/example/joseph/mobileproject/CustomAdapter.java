package com.example.joseph.mobileproject;

/**
 * Created by Joseph on 11/17/17.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nihal on 1/24/2017.
 */

public class CustomAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Expenses> expenses;
    private ArrayList<String> category;
    public Util util;
    int x;

    public CustomAdapter(Context context, int x, ArrayList<Expenses> objects) {
        super(context,x, objects);

        this.context = context;
        this.expenses = objects;
        this.x = x;

        util = new Util();


    }

    private class ViewHolder
    {
        TextView exName;
        TextView exAmount;
        TextView exCategory;
        TextView exDate;

        public ViewHolder(View v)
        {

        }
        public TextView getExAmount() {
            return exAmount;
        }

        public TextView getExCategory() {
            return exCategory;
        }

        public TextView getExName() {
            return exName;
        }
        public TextView getExDate() {
            return exDate;
        }

        public void setExDate(TextView exDate) {
            this.exDate = exDate;
        }

        public void setExName(TextView exName) {
            this.exName = exName;
        }

        public void setExAmount(TextView exAmount) {
            this.exAmount = exAmount;
        }

        public void setExCategory(TextView exCategory) {
            this.exCategory = exCategory;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_format, null);

            holder = new ViewHolder(convertView);
            holder.setExName((TextView) convertView.findViewById(R.id.expense_name_text_view));
            holder.setExCategory( (TextView) convertView.findViewById(R.id.expense_category_name_text_view));
            holder.setExAmount((TextView)convertView.findViewById(R.id.expense_value_text_view));
            holder.setExDate((TextView)convertView.findViewById(R.id.textView4));
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expenses expensesarray= expenses.get(position);
        holder.getExCategory().setText(expensesarray.getCategory());
        holder.getExName().setText(expensesarray.getName());
        holder.getExAmount().setText(util.rupiah(expensesarray.getAmount()));
        holder.getExDate().setText(expensesarray.getDate());
        return convertView;

    }
}

