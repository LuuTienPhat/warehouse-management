package com.example.warehousemanagement;
import  androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
public class ChartActivityWarehouse extends AppCompatActivity{
    PieChart pieChart1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_warehouse);

        pieChart1 = findViewById(R.id.piechart1);

        setupPieChart1();
        loadPieChart1Data();
    }



    private void setupPieChart1() {
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setUsePercentValues(true);
        pieChart1.setEntryLabelTextSize(12);
        pieChart1.setEntryLabelColor(Color.BLACK);
        pieChart1.setCenterText("WarehouseChart");
        pieChart1.setCenterTextSize(24);
        pieChart1.getDescription().setEnabled(false);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChart1Data() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.35f, "Kho1"));
        entries.add(new PieEntry(0.15f, "Kho2"));
        entries.add(new PieEntry(0.2f, "Kho3"));
        entries.add(new PieEntry(0.3f, "Kho4"));


        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart1));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart1.setData(data);
        pieChart1.invalidate();

        pieChart1.animateY(1400, Easing.EaseInOutQuad);
    }
}
