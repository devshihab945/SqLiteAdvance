package com.technetia.sqliteadvance;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowData extends AppCompatActivity {

    public static boolean EXPENSE = true;

    ListView listView;
    TextView tvShowData;

    DatabaseHelper databaseHelper;

    ArrayList<HashMap<String, String>> arrayList;
    HashMap<String, String> hashMap;

    Cursor cursor;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        listView = findViewById(R.id.listView);
        tvShowData = findViewById(R.id.tvShowData);

        databaseHelper = new DatabaseHelper(this);


       loadData();


    }

    private void loadData() {
        arrayList = new ArrayList<>(); // Initialize the arrayList once before the loop

        if (EXPENSE) {
            tvShowData.setText("Expense Data");
            cursor = databaseHelper.showAllExpense();
        } else {
            tvShowData.setText("Income Data");
            cursor = databaseHelper.showAllIncome();
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                double time = cursor.getDouble(3);

                hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("amount", String.valueOf(amount));
                hashMap.put("reason", reason);
                hashMap.put("time", String.valueOf(time));
                arrayList.add(hashMap); // Add to the same arrayList
            } while (cursor.moveToNext());

            DataAdapter dataAdapter = new DataAdapter();
            listView.setAdapter(dataAdapter);
        } else {
            tvShowData.setText("No Data Found");
        }

        if (cursor != null) cursor.close(); // Ensure the cursor is closed
    }



    public class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_item, null);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView tvReason = view.findViewById(R.id.tvReason);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView tvBalance = view.findViewById(R.id.tvBalance);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView tvTime = view.findViewById(R.id.tvTime);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView btnDeleteData = view.findViewById(R.id.btnDeleteData);

            tvReason.setText(arrayList.get(position).get("reason"));
            tvBalance.setText("BDT " + arrayList.get(position).get("amount"));
            tvTime.setText(arrayList.get(position).get("time"));

            btnDeleteData.setOnClickListener(v -> {

                if (EXPENSE){
                    databaseHelper.deleteExpense(Integer.parseInt(arrayList.get(position).get("id")));
                    arrayList.remove(position);
                    notifyDataSetChanged();
                    loadData();
                } else {
                    databaseHelper.deleteIncome(Integer.parseInt(arrayList.get(position).get("id")));
                    arrayList.remove(position);
                    notifyDataSetChanged();
                    loadData();
                }
            });

            return view;
        }
    }
}