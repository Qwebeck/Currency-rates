package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details.CurrencyDetails
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.RequestQueueProvider
import org.json.JSONArray

class CurrencyListDataSource(val context: Context,
                             val notifyRatesLoaded: () -> Unit = {},
                             val notifyError: () -> Unit = {}): ViewModel() {
    val currencies = mutableListOf<CurrencyDetails>()
    val requestQueue: RequestQueueProvider;

    private val tableContents = hashMapOf<String, List<CurrencyDetails>?>(
       "A" to null,
        "B" to null
    )

    init {
        requestQueue = RequestQueueProvider.getInstance(context);
    }

    fun getData() {
        clearData()
        tableContents.keys.forEach {
            getDataFromTable(it)
        }
    }

    fun clearData() {
        currencies.clear();
        tableContents.keys.forEach { key -> tableContents[key] = null }
    }

    fun getDataFromTable(tableName: String) {
        val url = "${context.getString(R.string.api_url)}/exchangerates/tables/${tableName}/last/2";
        val jsonRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                parseTableData(response, tableName);
            },
            Response.ErrorListener {
                notifyError();
            }
        );
        requestQueue.addToRequestQueue(jsonRequest);
    }

    private fun parseTableData(response: JSONArray, tableName: String) {
        val todayRates = response.getJSONObject(0)
            .getJSONArray("rates");
        val previousDayRates = response.getJSONObject(1)
            .getJSONArray("rates");

        val tableCurrencies = mutableListOf<CurrencyDetails>();
        for(i in 0 until todayRates.length()) {
            val rate = todayRates.getJSONObject(i);
            tableCurrencies.add(
                CurrencyDetails(
                    code = rate.getString("code"),
                    course = rate.getDouble("mid"),
                    name = rate.getString("currency"),
                    containingTableName = tableName,
                    lastDayCourse = previousDayRates.getJSONObject(i).getDouble("mid")
                )
            )
        }
        tableContents[tableName] = tableCurrencies;
        notifyIfNeeded()
    }

    private fun notifyIfNeeded() {
        if (tableContents.values.all { it !== null }) {
            currencies.addAll(tableContents.values.filterNotNull().flatten());
            notifyRatesLoaded()
        }

    }



}