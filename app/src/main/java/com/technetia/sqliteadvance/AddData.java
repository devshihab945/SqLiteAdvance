package com.technetia.sqliteadvance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddData extends AppCompatActivity {

    TextInputEditText edAmount, edReason;
    Button btnInsertData;
    TextView tvTittle;

    DatabaseHelper databaseHelper;

    public static boolean EXPENSE = true;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        edAmount = findViewById(R.id.edAmount);
        edReason = findViewById(R.id.edReason);
        tvTittle = findViewById(R.id.tvTittle);
        btnInsertData = findViewById(R.id.btnInsertData);

        databaseHelper = new DatabaseHelper(this);

        tvTittle.setText(EXPENSE ? "Add Expense" : "Add Income");

        btnInsertData.setOnClickListener(v -> {
            String sAmount = Objects.requireNonNull(edAmount.getText()).toString().trim();
            String reason = Objects.requireNonNull(edReason.getText()).toString().trim();

            if (sAmount.isEmpty() || reason.isEmpty()) {
                tvTittle.setText("Invalid Input");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(sAmount);
            } catch (NumberFormatException e) {
                tvTittle.setText("Invalid Amount");
                return;
            }

            if (EXPENSE) {
                databaseHelper.addExpense(amount, reason);
            } else {
                databaseHelper.addIncome(amount, reason);
            }

            tvTittle.setText("Data Inserted");
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
