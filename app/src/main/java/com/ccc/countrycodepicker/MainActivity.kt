package com.ccc.countrycodepicker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnCountryPickedListener {

    private var mCountry: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCountry = Country("vn", "+84", "Viet Nam")
        val country = mCountry ?: return
        countryCodePicker.setCountry(country.nameCode)
    }

    override fun onCountryPicked(country: Country) {
        mCountry = country
        countryCodePicker.setCountry(country.nameCode)
    }

    fun onPickerClick(view: View) {
        val country = mCountry ?: return
        val fm = supportFragmentManager
        val newFragment = CountryCodePickerFragment.getInstance(country)
        newFragment.setOnCountryPickedListener(this)
        newFragment.show(fm, CountryCodePickerFragment.TAG)
    }
}
