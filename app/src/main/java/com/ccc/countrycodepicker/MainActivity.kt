package com.ccc.countrycodepicker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ccc.ccp.Country
import com.ccc.ccp.OnCountryPickedListener
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
    val newFragment = com.ccc.ccp.CountryCodePickerFragment.getInstance(country)
    newFragment.setOnCountryPickedListener(this)
    newFragment.show(fm, com.ccc.ccp.CountryCodePickerFragment.TAG)
  }
}
