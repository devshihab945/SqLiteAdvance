package com.technetia.sqliteadvance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvBalance, tvExpenseBalance, tvIncomeBalance,
            btnAddExpense, btnShowExpense, btnAddIncome, btnShowIncome;

    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        tvBalance = findViewById(R.id.tvBalance);
        tvExpenseBalance = findViewById(R.id.tvExpenseBalance);
        tvIncomeBalance = findViewById(R.id.tvIncomeBalance);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnShowExpense = findViewById(R.id.btnShowExpense);
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnShowIncome = findViewById(R.id.btnShowIncome);

        databaseHelper = new DatabaseHelper(this);

        // Set button click listeners
        btnAddExpense.setOnClickListener(v -> {
            AddData.EXPENSE = true;
            startActivity(new Intent(this, AddData.class));
        });

        btnAddIncome.setOnClickListener(v -> {
            AddData.EXPENSE = false;
            startActivity(new Intent(this, AddData.class));
        });

        btnShowExpense.setOnClickListener(v -> {

            ShowData.EXPENSE = true;
            startActivity(new Intent(this, ShowData.class));

        });

        btnShowIncome.setOnClickListener(v -> {

            ShowData.EXPENSE = false;
            startActivity(new Intent(this, ShowData.class));

        });

    }

    @SuppressLint("SetTextI18n")
    private void updateBalance() {
        double totalExpense = databaseHelper.calculateTotalExpense();
        double totalIncome = databaseHelper.calculateTotalIncome();
        tvExpenseBalance.setText("BDT " + totalExpense);
        tvIncomeBalance.setText("BDT " + totalIncome);
        tvBalance.setText("BDT " + (totalIncome - totalExpense));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateBalance();
    }
}
