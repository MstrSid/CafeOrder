package com.company.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String drink;
    private String name;
    private String password;
    private StringBuilder builderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        drink = getString(R.string.tea_text);
        String additions = String.format(getString(R.string.additions_text), drink);
        textViewHello = findViewById(R.id.textViewHello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        builderAdditions = new StringBuilder();
        textViewAdditions.setText(additions);
        Intent intent = getIntent();
        if(intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else{
            Intent main = new Intent(this, LoginActivity.class);
            startActivity(main);
            finish();
        }

        String hello = String.format(getString(R.string.hello_text), name);
        textViewHello.setText(hello);

    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id == R.id.radioButtonTea){
            drink = getString(R.string.tea_text);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else if(id == R.id.radioButtonCoffee){
            drink = getString(R.string.coffee_text);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setChecked(false);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions_text), drink);
        textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {
        String optionOfDrink, additions, order, fullOrder;
        builderAdditions.setLength(0);
        if(checkBoxMilk.isChecked()){
            builderAdditions.append(getString(R.string.milk_text)).append(" ");
        }
        if(checkBoxSugar.isChecked()){
            builderAdditions.append(getString(R.string.sugar_text)).append(" ");
        }
        if(checkBoxLemon.isChecked()){
            builderAdditions.append(getString(R.string.lemon_text)).append(" ");
        }
        if(drink.equals(getString(R.string.tea_text))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else{
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        order = String.format(getString(R.string.order_string),
                name, password, drink, optionOfDrink);
        if(builderAdditions.length()>0){
            additions = getString(R.string.need_additions_text)+" "+builderAdditions.toString();
        } else{
            additions="";
        }
        fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}