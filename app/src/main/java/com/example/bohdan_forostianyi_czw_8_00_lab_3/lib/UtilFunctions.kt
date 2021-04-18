package com.example.bohdan_forostianyi_czw_8_00_lab_3.lib

inline fun <T:Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it !== null }) {
        closure(elements.filterNotNull());
    }
}
