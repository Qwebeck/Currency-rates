package com.example.bohdan_forostianyi_czw_8_00_lab_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.currency_rates.CurrencyRatesActivity
import com.example.bohdan_forostianyi_czw_8_00_lab_3.activities.gold_details.GoldDetailsActivity

/**
 * Bohdan Forostianyi czwartek 8:00
 *
 *
 * > 1. Proszę pamiętać o odpowiednimprzygotowaniu layoutu (constraints etc.)
 * > oraz o obsłudze sytuacji awaryjnych (brak sieci, „słaby”internet etc.).
 * W przypadku słabego intenetu użytkownik zobaczy spinner przy wyjściu na listę walut.
 * W przypadku problemów z połączeniem, użytkownik zobaczy napisa "Wystąpił problem sieciowy"
 * > 2. Kursy walut/złoto/przelicznik.
 * Przelicznik nie został zaimplementowany
 *
 * > 3.  Obsługa obu tabel NBP (tabela A + tabela B)
 * Dodana
 *
 * > 4. Kursy walut:
 * > 4.1 Lista lub siatka walut (według Państwa upodobań). Każdy wiersz zawiera kod waluty (PLN, EUR etc.) i aktualny kurs (proszę użyć recylerView)
 * Zaimplementowane
 * > 4.2 W wierszach dodatkowoznajdująsięsymbole poszczególnych walut(flagi państw, można wykorzystać bibliotekę worldCountryDatalub umieścić flagi w postaci znaków unicodew pliku strings.xml).
 * Zaimplementowane
 * > 4.3 Obsługa „błędów” w flagach. Biblioteka worldCountryDatanie pozwala pobrać flagi dla danej waluty, a jedynie dla konkretnego państwa. Z tego powodu flagi symbolizująceniektórewaluty(m.in. USD, GBP, EUR, CHF, HKD) mogą być niewłaściwe. Punkt zostanie przyznany za poprawęflag przy tych walutach (USD –flaga USA, GBP –flaga Wielkiej Brytanii, EUR –flaga Unii Europejskiej, HKD –flaga Hong Kongu, CHF –flaga Szwajcarii).
 * Dodana. Obsługa znajduje się w klasie CurrencyListAdapter
 * > 4.4  Wybór pozycji z listy powoduje przejście do nowego activity, które zawieradwa ostatnie kursy(dzisiejszy i wczorajszy)oraz wykresy zmiany kursu w ostatnimtygodniui miesiącu (odpowiednio 7lub 30 ostatnich kursów).Do stworzenia wykresów można wykorzystać na przykład bibliotekę MPAndroidChart. Proszę pamiętać o dostosowaniu wyglądu wykresów
 *  Zaimplementowane. Obsługę konfiguracji wykresów można zobaczyć w klasie ChartHelper
 *
 * > 4.5 Lista walut zawiera czerwoną/zielonąstrzałkę symbolizującą spadek/wzrost kursu w stosunku do poprzedniego
 * Zaimplementowane
 *
 * > 5.  Złoto
 * Zaimplementowane
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    fun openCurrencyRatesActivity(view: View) {
        val intent = Intent(this, CurrencyRatesActivity:: class.java);
        startActivity(intent);
    }


    fun openGoldStatsActivity(view: View) {
        val intent = Intent(this, GoldDetailsActivity::class.java);
        startActivity(intent);
    }
}