package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details.CurrencyDetails
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R

class CurrencyListAdapter(val dataSet: List<CurrencyDetails>,
                          private val onClick: (currency: CurrencyDetails) -> Unit = {}): RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    val currencies = World.getAllCurrencies();

    val customCurrencyFlags = hashMapOf(
            "EUR" to R.drawable.eu,
            "HKD" to R.drawable.hk,
            "USD" to R.drawable.us
    )
    val specialCurrencyCodes = hashMapOf(
            "GHS" to "GH",
            "GIP" to "GIB",
            "BYN" to "BLR",
            "IDR" to "MCO",
            "INR" to "IN",
            "BYN" to "BLR",
            "ZWL" to "ZWE",
            "STN" to "ST",
            "XPF" to "CH",
            "GIP" to "GI"
    )

    class ViewHolder(view: View, private val onClick: (currency: CurrencyDetails) -> Unit): RecyclerView.ViewHolder(view) {

        val currencyCode: TextView;
        val currencyIcon: ImageView;
        val currencyStatus: ImageView;
        val currencyRate: TextView;
        var currentItem: CurrencyDetails? = null;
        init{
            currencyCode = view.findViewById(R.id.currency_code);
            currencyIcon = view.findViewById(R.id.currency_flag);
            currencyRate = view.findViewById(R.id.currency_rate);
            currencyStatus = view.findViewById(R.id.currency_status);
            view.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position];
        viewHolder.currencyIcon.setImageResource(getCurrencyFlag(item));
        viewHolder.currencyRate.text = item.course.toString();
        viewHolder.currencyCode.text = item.code;
        viewHolder.currencyStatus.setImageResource(getCurrencyStatusIcon(item));
        viewHolder.currentItem = item;
    }

    private fun getCurrencyStatusIcon(item: CurrencyDetails): Int =
        if (compareValues(item.course, item.lastDayCourse) > 0) R.drawable.arrow_green else R.drawable.arrow_red

    private fun getCurrencyFlag(currency: CurrencyDetails): Int {
        return when {
            customCurrencyFlags.containsKey(currency.code) -> customCurrencyFlags[currency.code] ?: 0
            specialCurrencyCodes.containsKey(currency.code) -> customCurrencyFlags[currency.code] ?: 0
            else -> {
                val currencyMeta = currencies.find {
                    curr -> curr.code.equals(currency.code)
                }
                World.getFlagOf(World.getCountryFrom(currencyMeta?.country ?: "").id);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.currency_row, parent, false);
        return ViewHolder(
            view,
            onClick
        );
    }

    override fun getItemCount() = dataSet.size;

}
