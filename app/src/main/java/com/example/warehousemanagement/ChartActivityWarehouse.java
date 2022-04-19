package com.example.warehousemanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        pieChart1.setEntryLabelTextSize(13);
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
        entries.add(new PieEntry(0.6f, "Kho Bình Chánh"));
        entries.add(new PieEntry(0.2f, "Kho Tân Phú"));
        entries.add(new PieEntry(0.2f, "Kho Thủ Đức"));




        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        Description description = new Description();
        description.setText("\nThống kê nhà kho dựa trên số phiếu nhập");
        description.setTextSize(20);
        pieChart1.setDescription(description);
        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart1));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart1.setData(data);
        pieChart1.invalidate();

        pieChart1.animateY(1400, Easing.EaseInOutQuad);
    }
}
