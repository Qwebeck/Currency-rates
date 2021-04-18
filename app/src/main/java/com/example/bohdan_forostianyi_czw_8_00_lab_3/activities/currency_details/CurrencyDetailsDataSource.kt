package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.RequestQueueProvider
import org.json.JSONObject

class CurrencyDetailsDataSource(val context: Context,
                                val notifyRatesLoaded: ((currencyDetails: CurrencyDetails, rates: List<CurrencyHistoryRecord>) -> Unit)? = null,
                                var notifyError: () -> Unit = {}): ViewModel() {

    val requestQueue: RequestQueueProvider;
    init {
        requestQueue = RequestQueueProvider.getInstance(context);
    }

    fun getData(currencyCode: String, currencyTable: String, historyPeriod: Int) {
        val url = "${context.getString(R.string.api_url)}/exchangerates/rates/${currencyTable}/${currencyCode}/last/${historyPeriod}/";

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                val currencyDetails = parseCurrencyDetails(response);
                val historyRates = parseHistoryRates(response);
                notifyRatesLoaded?.let{ it(currencyDetails, historyRates) };

            },
            Response.ErrorListener {
                notifyError();
            }
        );
        requestQueue.addToRequestQueue(jsonRequest);
    }

    private fun parseHistoryRates(response: JSONObject): List<CurrencyHistoryRecord> {
        val rates = response.getJSONArray("rates")
        val historyRates = mutableListOf<CurrencyHistoryRecord>();
        for(i in 0 until rates.length()) {
            val rate = rates.getJSONObject(i);
            historyRates.add(
                CurrencyHistoryRecord(
                     date = rate.getString("effectiveDate"),
                     price = rate.getDouble("mid")
                )
            );
        }
        return historyRates;
    }

    private fun parseCurrencyDetails(response: JSONObject): CurrencyDetails {
        val newCurrencyDetails =
            CurrencyDetails(
                code = response.getString("code"),
                name = response.getString("currency"),
                containingTableName = response.getString("table")
            );
        return newCurrencyDetails;
    }
}