package com.android.llc.proringer.pro.proringerpro.fragmnets.drawerNav;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.llc.proringer.pro.proringerpro.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by su on 2/20/18.
 */

public class AccountAnalyticsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transact_analytics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] barColorArray1 = new int[12];
        BarChart barChart = (BarChart)view.findViewById(R.id.barchart);
        float barWidth =  0.45f;
        barChart.setTouchEnabled(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(false);

        barChart.getLegend().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
//        YAxis yAxis = barChart.getAxisLeft(); // Show left y-axis line
//        yAxis.setDrawGridLines(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(40f, 0));
        entries.add(new BarEntry(25f, 1));
        entries.add(new BarEntry(60f, 2));
        entries.add(new BarEntry(10f, 3));
        entries.add(new BarEntry(35f, 4));
        entries.add(new BarEntry(50f, 5));
        entries.add(new BarEntry(19f, 6));
        entries.add(new BarEntry(25f, 7));
        entries.add(new BarEntry(37f, 8));
        entries.add(new BarEntry(46f, 9));
        entries.add(new BarEntry(60f, 10));
        entries.add(new BarEntry(75f, 11));
        BarDataSet bardataset = new BarDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");
        labels.add(" ");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);

        barChart.getXAxis().setDrawGridLines(false); // disable grid lines for the XAxis
        barChart.getAxisLeft().setDrawGridLines(false); // disable grid lines for the left YAxis
        barChart.getAxisRight().setDrawGridLines(false);
        bardataset.setBarSpacePercent(0);

        barChart.setDescription("");
         // set the descripti


        barChart.animateY(1000);


        for (int i=0;i<entries.size();i++)
        {
            if( entries.get(i).getVal()>=19&& entries.get(i).getVal() <= 60)
            {
                Log.d("jshdhshdjs","skdhshdj");
                barColorArray1[i] = Color.rgb(241,89,42);
                bardataset.setColors(barColorArray1);
            }
            else if(entries.get(i).getVal()<= 19)
            {
                barColorArray1[i] = Color.DKGRAY;
                bardataset.setColors(barColorArray1);
            }
            else if(entries.get(i).getVal() > 60 )
            {
                barColorArray1[i] = Color.rgb(109,181,236);
                bardataset.setColors(barColorArray1);
            }
            bardataset.setColors(barColorArray1);
        }

    }
}
