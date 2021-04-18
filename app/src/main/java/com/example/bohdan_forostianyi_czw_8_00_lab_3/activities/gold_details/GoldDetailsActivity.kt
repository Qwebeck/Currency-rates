package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.gold_details

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details.MONTH_DAY_COUNT
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.ChartHelper
import com.github.mikephil.charting.charts.LineChart

class GoldDetailsActivity : AppCompatActivity() {
    private lateinit var dataSource: GoldRatesDataSource;

    private lateinit var container: LinearLayout;
    private lateinit var errorMessage: TextView;

    private lateinit var monthRatesChart: LineChart;

    private lateinit var todayRate: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_details);
        bindViews();
        configureCharts();
        configureDatasoure();
        getData();
    }

    private fun bindViews() {
        todayRate = findViewById(R.id.today_gold_rate);
        monthRatesChart = findViewById(R.id.gold_monthly_rates);
        container = findViewById(R.id.gold_details_container);
        errorMessage = findViewById(R.id.gold_error);
    }
    private fun configureDatasoure() {
        dataSource = GoldRatesDataSource(this,
            notifyRatesLoaded = {
                    historyRates ->  updateView(historyRates)
            },
            notifyError = { showError() }
        )
    }


    private fun showError() {
        container.visibility = View.GONE;
        errorMessage.visibility = View.VISIBLE;
    }
    private fun showContent() {
        container.visibility = View.VISIBLE;
        errorMessage.visibility = View.GONE;
    }
    private fun getData() {
        dataSource.getData(MONTH_DAY_COUNT);
    }

    private fun configureCharts() {
        ChartHelper.configureCharts(monthRatesChart);
    }

    private fun updateView(historyRecords: List<GoldRate>) {
        showContent();
        todayRate.text = String.format(getString(R.string.today_rates), historyRecords[0].price.toString());
        ChartHelper.setData(monthRatesChart, historyRecords, { item -> item.price.toFloat() }, { item -> item.date} );
    }
}