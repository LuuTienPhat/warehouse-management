package com.example.warehousemanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

    public class ChartActivityReceipt extends AppCompatActivity {
        PieChart pieChart2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chart_receipt);

            pieChart2 = findViewById(R.id.piechart2);

            setupPieChart2();
            loadPieChart2Data();
        }


        private void setupPieChart2() {
            pieChart2.setDrawHoleEnabled(true);
            pieChart2.setUsePercentValues(true);
            pieChart2.setEntryLabelTextSize(12);
            pieChart2.setEntryLabelColor(Color.BLACK);
            pieChart2.setCenterText("ReceiptChart");
            pieChart2.setCenterTextSize(24);
            pieChart2.getDescription().setEnabled(false);

            Legend l = pieChart2.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setEnabled(true);
        }

        private void loadPieChart2Data() {
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(0.35f, "PhieuNhap1"));
            entries.add(new PieEntry(0.15f, "PhieuNhap2"));
            entries.add(new PieEntry(0.2f, "PhieuNhap3"));
            entries.add(new PieEntry(0.15f, "PhieuNhap4"));
            entries.add(new PieEntry(0.15f, "PhieuNhap5"));

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
            data.setValueFormatter(new PercentFormatter(pieChart2));
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.BLACK);

            pieChart2.setData(data);
            pieChart2.invalidate();

            pieChart2.animateY(1400, Easing.EaseInOutQuad);
        }
    }

