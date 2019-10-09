package com.ccc.ccp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccc.ccp.utils.DimensionUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_country_code_picker.view.*
import java.util.*

class CountryCodePickerFragment : DialogFragment(), CountryCodeAdapter.OnItemClickListener {

    private lateinit var mView: View
    private lateinit var mAdapter: CountryCodeAdapter

    private var mOnCountryPicked: OnCountryPickedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (mOnCountryPicked == null && context is OnCountryPickedListener) {
            mOnCountryPicked = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mOnCountryPicked == null && parentFragment is OnCountryPickedListener) {
            mOnCountryPicked = parentFragment as? OnCountryPickedListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_country_code_picker, container, false)

        initRecyclerView()
        initData()
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = context ?: return
        Single.create<List<Country>> {
            it.onSuccess(
                Country.loadCountryDataFromXML(
                    context,
                    arguments?.getString("LanguageCode") ?: Locale.getDefault().language
                )
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(countries: List<Country>) {
                    mAdapter.updateData(countries)
                }

                override fun onError(e: Throwable) {
                    Log.e(this::class.java.simpleName, "error", e)
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val context = context ?: return
        val params = dialog!!.window!!.attributes
        params.width = (DimensionUtils.getDisplayWidth(context) / 1.15).toInt()
        params.height = (DimensionUtils.getDisplayHeight(context) / 1.7).toInt()
        dialog?.window?.attributes = params as android.view.WindowManager.LayoutParams
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
