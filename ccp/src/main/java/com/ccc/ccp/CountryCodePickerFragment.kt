package com.ccc.ccp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccc.ccp.utils.DimensionUtils
import kotlinx.android.synthetic.main.fragment_country_code_picker.view.*
import java.util.*

class CountryCodePickerFragment : DialogFragment(), CountryCodeAdapter.OnItemClickListener {

    private lateinit var mView: View
    private lateinit var mAdapter: CountryCodeAdapter

    private var mOnCountryPicked: OnCountryPickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_country_code_picker, container, false)

        initRecyclerView()
        initData()
        return mView
    }

    override fun onResume() {
        super.onResume()
        val context = context ?: return
        val params = dialog!!.window!!.attributes
        params.width = (DimensionUtils.getDisplayWidth(context) / 1.15).toInt()
        params.height = (DimensionUtils.getDisplayHeight(context) / 1.7).toInt()
        dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }

    override fun onDestroy() {
        mAdapter.setOnItemClickListener(null)
        super.onDestroy()
    }

    override fun onItemClick(country: Country) {
        mOnCountryPicked?.onCountryPicked(country)
        dismiss()
    }

    fun setOnCountryPickedListener(onCountryPicked: OnCountryPickedListener?) {
        mOnCountryPicked = onCountryPicked
    }

    private fun initData() {
        val country = arguments?.getParcelable<Country>("Country") ?: return
        updateUI(country)
        mAdapter.deleteCountry(country)
    }

    private fun updateUI(country: Country) {
        mView.flagImageView.setImageResource(Country.getFlagMasterResID(country))
        mView.nameTextView.text = country.name
        mView.phoneCodeTextView.text = country.phoneCode
    }

    private fun initRecyclerView() {
        val context = context ?: return
        mAdapter = CountryCodeAdapter()
        val manager = LinearLayoutManager(context)
        mView.recyclerView.layoutManager = manager
        mView.recyclerView.adapter = mAdapter
        mAdapter.updateData(Country.loadCountryDataFromXML(context, arguments?.getString("LanguageCode")?: Locale.getDefault().language))
        mAdapter.setOnItemClickListener(this)
    }

    companion object {
        var TAG: String = this::class.java.simpleName

        fun getInstance(country: Country, langCode: String): CountryCodePickerFragment {
            val fragment = CountryCodePickerFragment()
            val bundle = Bundle()
            bundle.putParcelable("Country", country)
            bundle.putString("LanguageCode", langCode)
            fragment.arguments = bundle
            return fragment
        }
    }
}
