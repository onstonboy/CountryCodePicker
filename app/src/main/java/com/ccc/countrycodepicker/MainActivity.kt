package com.ccc.countrycodepicker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ccc.ccp.Country
import com.ccc.ccp.CountryCodePicker
import com.ccc.ccp.OnCountryPickedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnCountryPickedListener,
    CountryCodePicker.OnRefreshDataCompleteListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryCodePicker.setLanguageCode(Locale.getDefault().language)
        countryCodePicker.refreshDefaultData()
        countryCodePicker.setOnRefreshDataCompleteListener(this)
    }

    override fun onRefreshDataComplete() {
        countryCodePicker.setCountryPicked(countryCodePicker.getCountry(Locale.getDefault().country))
    }

    override fun onCountryPicked(country: Country) {
        countryCodePicker.setCountryPicked(country)
    }

    fun onPickerClick(view: View) {
        val country = countryCodePicker.getCountryPicked() ?: return
        val fm = supportFragmentManager
        val newFragment =
            com.ccc.ccp.CountryCodePickerFragment.getInstance(country, Locale.getDefault().language, false)
        newFragment.show(fm, com.ccc.ccp.CountryCodePickerFragment.TAG)
    }
}
