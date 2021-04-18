package com.example.bohdan_forostianyi_czw_8_00_lab_3.lib

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class RequestQueueProvider(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: RequestQueueProvider? = null;
        fun getInstance(context: Context) = INSTANCE
                ?: synchronized(this) {
                    INSTANCE ?: RequestQueueProvider(context).also {
                        INSTANCE = it;
                    }
                }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext);
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req);
    }
}