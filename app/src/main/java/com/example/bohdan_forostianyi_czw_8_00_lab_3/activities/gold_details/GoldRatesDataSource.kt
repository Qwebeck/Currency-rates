package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.gold_details

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.lib.RequestQueueProvider
import org.json.JSONArray

class GoldRatesDataSource (val context: Context,
                           val notifyRatesLoaded: ((goldDetails: List<GoldRate>) -> Unit)? = null,
                           val notifyError: () -> Unit = {}): ViewModel() {

    val requestQueue: RequestQueueProvider;
    init {
        requestQueue = RequestQueueProvider.getInstance(context);
    }

    fun getData(historyPeriod: Int) {
        val url = "${context.getString(R.string.api_url)}/cenyzlota/last/${historyPeriod}/";

        val jsonRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                val goldDetails = parseGoldHistoryRates(response);
                notifyRatesLoaded?.let{ it(goldDetails) };

            },
            Response.ErrorListener { error ->
                notifyError();
            }
        );
        requestQueue.addToRequestQueue(jsonRequest);
    }


    private fun parseGoldHistoryRates(response: JSONArray): List<GoldRate> {
        val historyRates = mutableListOf<GoldRate>();
        for(i in 0 until response.length()) {
            val rate = response.getJSONObject(i);
            historyRates.add(
                GoldRate(
                    date = rate.getString("data"),
                    price = rate.getDouble("cena")
                )
            );
        }
        return historyRates;
        }
    }