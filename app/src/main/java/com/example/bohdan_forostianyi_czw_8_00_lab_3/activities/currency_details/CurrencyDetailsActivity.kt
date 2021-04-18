package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates.CURRENCY_CODE
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates.CURRENCY_TABLE
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.ChartHelper
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.ifLet
import com.github.mikephil.charting.charts.LineChart
import org.w3c.dom.Text

val WEEK_DAYS_COUNT = 7;
val MONTH_DAY_COUNT = 30; // :)

class CurrencyDetailsActivity: AppCompatActivity() {
    private var currencyCode: String? = null;
    private var currencyTable: String? = null;
    private lateinit var dataSource: CurrencyDetailsDataSource;

    private lateinit var mainContent: LinearLayout;
    private lateinit var errorMessage: TextView;

    private lateinit var weekRatesChart: LineChart;
    private lateinit var monthRatesChart: LineChart;

    private lateinit var currencyName: TextView;
    private lateinit var lastDayRate: TextView;
    private lateinit var todayRate: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);
        bindViews();
        configureCharts();
        configureDatasoure();
        getData();
    }

    private fun bindViews() {
        mainContent = findViewById(R.id.currency_details_content);
        currencyName = findViewById(R.id.currency_details_currency_name);
        lastDayRate = findViewById(R.id.last_day_rate);
        todayRate = findViewById(R.id.today_rate);
        weekRatesChart = findViewById(R.id.currency_weekly_rates);
        monthRatesChart = findViewById(R.id.currency_monthly_rates);
        errorMessage = findViewById(R.id.currency_error);
    }

    private fun configureDatasoure() {
        dataSource = CurrencyDetailsDataSource(this,
            notifyRatesLoaded = {
                    currencyDetails, rates ->  updateView(currencyDetails, rates)
            },
            notifyError = { showError() }
        )
    }

    private fun showError() {
        mainContent.visibility = View.GONE;
        errorMessage.visibility = View.VISIBLE;
    }



    private fun getData() {
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            currencyCode = bundle.getString(CURRENCY_CODE);
            currencyTable = bundle.getString(CURRENCY_TABLE);
        }
        ifLet(currencyCode, currencyTable) { (itCode, itTable) ->
            dataSource.getData(itCode, itTable, MONTH_DAY_COUNT)
        }
    }

    private fun configureCharts() {
        ChartHelper.configureCharts(monthRatesChart, weekRatesChart);
    }

    private fun updateView(currencyDetails: CurrencyDetails, historyRecords: List<CurrencyHistoryRecord>) {
        showContent();
        currencyName.text = currencyDetails.name;
        todayRate.text = String.format(getString(R.string.today_rates), historyRecords[0].price.toString());
        lastDayRate.text =String.format(getString(R.string.last_day_rates), historyRecords[1].price.toString());

        val monthData = historyRecords.subList(0, MONTH_DAY_COUNT);
        val weekData = historyRecords.subList(0, WEEK_DAYS_COUNT);

        (listOf(monthRatesChart, weekRatesChart) zip listOf(monthData, weekData)).forEach {

            val (chart, data) = it;

            ChartHelper.setData(chart, data, { item -> item.price.toFloat() }, { item -> item.date} );
        }
    }

    private fun showContent() {
        mainContent.visibility = View.VISIBLE;
        errorMessage.visibility = View.GONE;
    }
}