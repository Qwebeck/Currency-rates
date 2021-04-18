package com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_details

data class CurrencyDetails(val code: String,
                           val course: Double? = null,
                           val name: String,
                           val containingTableName: String,
                           val lastDayCourse: Double? = null);
