package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details.CurrencyDetails
import com.example.bohdan_forostianyi_czw_8_00_lab_3.R
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details.CurrencyDetailsActivity
import java.util.*
import kotlin.concurrent.schedule

const val CURRENCY_CODE = "currency details";
const val CURRENCY_TABLE = "currency table";

class CurrencyRatesActivity : AppCompatActivity() {
    lateinit var listAdapter: CurrencyListAdapter;
    lateinit var recyclerView: RecyclerView;
    lateinit var errorMessage: TextView;
    private lateinit var currencyDataHolder: CurrencyListDataSource;
    private lateinit var progressBar: ProgressBar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        configureDataSource();
        World.init(this);
        setContentView(R.layout.currency_list);
        bindViews();
        loadRatesView();
    }

    private fun configureDataSource() {
        currencyDataHolder = CurrencyListDataSource(this, {
            showContent();
            listAdapter.notifyDataSetChanged()
        }, { showError() }
        );
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.currency_recycler_view);
        errorMessage = findViewById(R.id.currency_list_error);
        progressBar = findViewById(R.id.progress_bar);
    }

    fun loadRatesView() {
        showProgressBar();
        Timer("Show one spinner rotation", false).schedule(300) {
            currencyDataHolder.getData();
        }
        listAdapter = CurrencyListAdapter(currencyDataHolder.currencies, { item -> onCurrencyItemClicked(item)});
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = listAdapter;
    }

    private fun showProgressBar() {
        recyclerView.visibility = View.GONE;
        errorMessage.visibility = View.GONE;
        progressBar.visibility = View.VISIBLE;
    }

    fun onCurrencyItemClicked(clickedItem: CurrencyDetails) {
        val intent = Intent(this, CurrencyDetailsActivity::class.java)
        intent.putExtra(CURRENCY_CODE, clickedItem.code);
        intent.putExtra(CURRENCY_TABLE, clickedItem.containingTableName);
        startActivity(intent);
    }

    private fun showError() {
        recyclerView.visibility = View.GONE;
        errorMessage.visibility = View.VISIBLE;
        progressBar.visibility = View.GONE;
    }

    private fun showContent() {
        recyclerView.visibility = View.VISIBLE;
        errorMessage.visibility = View.GONE;
        progressBar.visibility = View.GONE;
    }
}