package com.example.antibioticdoser;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Plots extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private static DoseCalculator c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);

        double y;
        double x;

        c = MainActivity.getDoseCalculator();

         double Cp = c.getPeak();
         double Ct = c.getTrough();
         double K = c.getK();
         double Co = Cp;
         if(c.getLD()) {
             Co = c.getLoadingDose() / c.getVD();
         }

         double ta = c.getSOI()*c.getVD()*K*(1-Math.exp(-1*K*c.getTBD()));


        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();

        //Line to get starting doses (starts at 0, ends at Co)
        double interval = Co/99;
        for (int i = 0; i < 100; i++) {
            x  = i*interval;
            y = Math.log(1-((ta*x)/c.getDose())) / (-1*K);
            series.appendData(new DataPoint(x, y), true, 500);
        }


        //Initial exponential plot (starts at LD, ends at Ct)
        interval = (Co-Ct)/99;
        for (double i = 0; i < 100; i++){
            x = Ct + i*interval;
            y = Math.log(x/Co)/(-1*K)+c.getSOI();
            series.appendData(new DataPoint(x, y), true, 500);
        }
/*
        //Line to get to Cp after Ct is hit
        interval = (Cp-Ct)/99;
        for (double i = 0; i < 100; i++){
            x = Ct + i*interval;
            y = Math.log(1-(ta*x)/c.getDose());
            series.appendData(new DataPoint(x, y), true, 100);
            //Next exponential decay curve after time between doses passes
            y = Math.log(x/Cp)/(-1*K)+(c.getTBD()+c.getSOI());
            series.appendData(new DataPoint(x, y), true, 100);
        }
        */

        graph.addSeries(series);

        //graph.getViewport().setScalable(true);
    }
}
