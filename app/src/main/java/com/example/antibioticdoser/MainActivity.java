package com.example.antibioticdoser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private static DoseCalculator c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Move to the next screen upon pressing "submit"
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText weight = (EditText) findViewById(R.id.weightPounds);
                EditText heightFeet = (EditText) findViewById(R.id.heightFeet);
                EditText heightInch = (EditText) findViewById(R.id.heightInches);
                Spinner sexChoice = (Spinner) findViewById(R.id.MorF);
                EditText ageYears = (EditText) findViewById(R.id.ageYears);
                EditText SCrMgL = (EditText) findViewById(R.id.SCrMilligramsPerLiter);
                EditText doseMilligrams = (EditText) findViewById(R.id.doseMilligrams);
                EditText infTime = (EditText) findViewById(R.id.infusionTime);

                Spinner substance = (Spinner) findViewById(R.id.substance);


                //default values
                double pBodyLiquid = 0.7;
                double weightPounds = 145;
                double heightInches = 67;
                char sex = 'M';
                int age = 64;
                double SCr = .9; //(mg/dL)
                boolean LD = false;
                double targetPeak = 200; //(mg/L)
                double dose = 1000; //(mg)
                double infusionTime = 1;

                /*
                try {
                    weightPounds = Double.parseDouble(weight.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid weight entered.");
                }
                try {
                    heightInches = Double.parseDouble(heightFeet.getText().toString()) * 12 //feet
                            + Integer.valueOf(heightInch.getText().toString()); //inches
                } catch (Exception e) {
                    System.out.println("No valid height entered.");
                }
                try {
                    sex = sexChoice.getSelectedItem().toString().charAt(0);
                } catch (Exception e) {
                    System.out.println("No valid sex selected.");
                }
                try {
                    age = Integer.valueOf(ageYears.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid age entered.");
                }
                try {
                    SCr = Double.parseDouble(SCrMgL.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid SCr entered.");
                }
                try {
                    SCr = Double.parseDouble(SCrMgL.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid SCr entered.");
                }
                try {
                    dose = Double.parseDouble(doseMilligrams.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid dose entered.");
                }
                try {
                    infusionTime = Double.parseDouble(infTime.getText().toString());
                } catch (Exception e) {
                    System.out.println("No valid infusion time entered.");
                }
                */
                c = new DoseCalculator(pBodyLiquid, weightPounds, heightInches, sex,
                        age, SCr, LD, targetPeak, dose, infusionTime);

                openPlots();

            }
        });
    }

    public static DoseCalculator getDoseCalculator() {
        return c;
    }

    public void openPlots() {
        Intent intent = new Intent(this, Plots.class);
        startActivity(intent);
    }
}