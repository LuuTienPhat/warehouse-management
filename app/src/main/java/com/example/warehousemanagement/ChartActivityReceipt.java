package com.example.warehousemanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.ReceiptDao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartActivityReceipt extends AppCompatActivity {
        PieChart pieChart2;
        ReceiptDao receiptDao;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chart_receipt);

            pieChart2 = findViewById(R.id.piechart2);
            receiptDao = new ReceiptDao(DatabaseHelper.getInstance(this));
            setupPieChart2();
            loadPieChart2Data();
        }


        private void setupPieChart2() {
            pieChart2.setDrawHoleEnabled(true);
            pieChart2.setUsePercentValues(true);
            pieChart2.setEntryLabelTextSize(13);
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
            HashMap<String, Integer> hashMap = receiptDao.getDataTKPN();
            int sum = 0;
            for(int quantity : hashMap.values()){
                sum += quantity;
            }
            ArrayList<PieEntry> entries = new ArrayList<>();
            for(String receiptID: hashMap.keySet()){
                entries.add(new PieEntry(hashMap.get(receiptID)*1f/sum, receiptID));
            }
//            ArrayList<PieEntry> entries = new ArrayList<>();
//            entries.add(new PieEntry(0.3f, "Phiếu nhập 1"));
//            entries.add(new PieEntry(0.1f, "Phiếu nhập 2"));
//            entries.add(new PieEntry(0.2f, "Phiếu nhập 3"));
//            entries.add(new PieEntry(0.2f, "Phiếu nhập 4"));
//            entries.add(new PieEntry(0.2f, "Phiếu nhập 5"));

            ArrayList<Integer> colors = new ArrayList<>();
            for (int color : ColorTemplate.MATERIAL_COLORS) {
                colors.add(color);
            }

            for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(color);
            }
            Description description = new Description();
            description.setText("\nThống kê phiếu nhập dựa trên số lượng vật tư");
            description.setTextSize(20);
            pieChart2.setDescription(description);
            PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter(pieChart2));
            data.setValueTextSize(15f);
            data.setValueTextColor(Color.BLACK);

            pieChart2.setData(data);
            pieChart2.invalidate();

            pieChart2.animateY(1400, Easing.EaseInOutQuad);
        }
    }

