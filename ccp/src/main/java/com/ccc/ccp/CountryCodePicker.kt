package com.ccc.ccp

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ccc.ccp.utils.DimensionUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class CountryCodePicker : LinearLayout {

    private lateinit var mFlagImageView: ImageView
    private lateinit var mPhoneCodeTextView: TextView

    private var mPhoneCodeSize: Float =
        DimensionUtils.getDimensionWithScaledDensity(context, R.dimen.sp_14)
    private var mPhoneCodeStyle: Int = Typeface.NORMAL
    private var mPhoneCodeColor: Int = ContextCompat.getColor(context, android.R.color.black)
    private var mLanguageCode: String = "vi"
    private var mIsShowPhoneCode: Boolean = true
    private var mIsShowFlag: Boolean = true
    private var mFlagHeight: Float = DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24)
    private var mFlagWidth: Float = DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24)
    private var mCountries = ArrayList<Country>()
    private var mCountryPicked: Country? = null
    private var mOnLoadDataComplete: OnLoadDataCompleteListener? = null
    private var mOnRefreshDataComplete: OnRefreshDataCompleteListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0)
        try {
            mPhoneCodeColor = typeArray.getColor(
                R.styleable.CountryCodePicker_ccp_phoneCodeColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
            mPhoneCodeSize =
                typeArray.getDimension(
                    R.styleable.CountryCodePicker_ccp_phoneCodeSize,
                    DimensionUtils.getDimensionWithScaledDensity(context, R.dimen.sp_14)
                )
            mFlagHeight =
                typeArray.getDimension(
                    R.styleable.CountryCodePicker_ccp_flagHeight,
                    DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24)
                )
            mFlagWidth =
                typeArray.getDimension(
                    R.styleable.CountryCodePicker_ccp_flagWidth,
                    DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24)
                )
            mPhoneCodeStyle =
                typeArray.getInt(R.styleable.CountryCodePicker_ccp_phoneCodeStyle, Typeface.BOLD)
            mIsShowPhoneCode =
                typeArray.getBoolean(R.styleable.CountryCodePicker_ccp_showPhoneCode, true)
            mIsShowFlag =
                typeArray.getBoolean(R.styleable.CountryCodePicker_ccp_showFlag, true)
            mLanguageCode = typeArray.getString(R.styleable.CountryCodePicker_ccp_langCode) ?: "vi"
        } finally {
            typeArray.recycle()
        }
        initData()
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    fun setOnLoadDataCompleteListener(onLoadDataCompleteListener: OnLoadDataCompleteListener?) {
        mOnLoadDataComplete = onLoadDataCompleteListener
    }

    fun setOnRefreshDataCompleteListener(onRefreshDataCompleteListener: OnRefreshDataCompleteListener?) {
        mOnRefreshDataComplete = onRefreshDataCompleteListener
    }

    fun setLanguageCode(languageCode: String) {
        mLanguageCode = languageCode
    }

    fun refreshDefaultData() {
        mCountries.clear()
        Single.create<List<Country>> {
            it.onSuccess(
                Country.loadCountryDataFromXML(context, mLanguageCode)
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(countries: List<Country>) {
                    mCountries.addAll(countries)
                    mOnRefreshDataComplete?.onRefreshDataComplete()
                }

                override fun onError(e: Throwable) {
                    Log.e(this::class.java.simpleName, "error", e)
                }
            })
    }

    fun updateData(countries: List<Country>) {
        mCountries.clear()
        mCountries.addAll(countries)
    }

    // Default country is Viet Nam
    fun getDefaultCountryPicked(): Country {
        mCountries.find { it.nameCode == "vn" }?.let {
            return it
        }
        if (mLanguageCode == "vi") {
            return Country("vn", "+84", "Việt Nam")
        }
        return Country("vn", "+84", "VietNam")
    }

    fun getCountry(nameCode: String): Country {
        return mCountries.find { it.nameCode == nameCode.toLowerCase(Locale.getDefault()) }
            ?: getDefaultCountryPicked()
    }

    fun getCountryPicked(): Country? {
        return mCountryPicked
    }

    fun setCountryPicked(countryPicked: Country) {
        mCountryPicked = countryPicked
        val country = mCountries.find { it.nameCode == countryPicked.nameCode } ?: return
        mFlagImageView.setImageResource(Country.getFlagMasterResID(country))
        mPhoneCodeTextView.text = country.phoneCode
    }

    private fun initData() {
        Single.create<List<Country>> {
            it.onSuccess(
                Country.loadCountryDataFromXML(context, mLanguageCode)
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(countries: List<Country>) {
                    mCountries.addAll(countries)
                    mOnLoadDataComplete?.onLoadDataComplete()
                }

                override fun onError(e: Throwable) {
                    Log.e(this::class.java.simpleName, "error", e)
                }
            })
    }

    private fun initViews() {
        mFlagImageView = ImageView(context).apply {
            val layoutParams = ViewGroup.LayoutParams(mFlagWidth.toInt(), mFlagHeight.toInt())
            this.layoutParams = layoutParams
        }
        val polygonView = ImageView(context).apply {
            val layoutParams = ViewGroup.LayoutParams(resources.getDimensionPixelOffset(R.dimen.dp_8), resources.getDimensionPixelOffset(R.dimen.dp_5))
            this.layoutParams = layoutParams
            setImageResource(R.drawable.ic_polygon)
        }
        mPhoneCodeTextView = TextView(context).apply {
            textSize = mPhoneCodeSize
            setTextColor(mPhoneCodeColor)
            setTypeface(Typeface.DEFAULT, mPhoneCodeStyle)
            val layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(
                DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt(),
                0,
                DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt(),
                0
            )
            this.layoutParams = layoutParams
        }
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        if (mIsShowFlag) {
            addView(mFlagImageView)
        }
        if (mIsShowPhoneCode) {
            addView(mPhoneCodeTextView)
        }
        addView(polygonView)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }

    interface OnLoadDataCompleteListener {
        fun onLoadDataComplete()
    }

    interface OnRefreshDataCompleteListener {
        fun onRefreshDataComplete()
    }
}