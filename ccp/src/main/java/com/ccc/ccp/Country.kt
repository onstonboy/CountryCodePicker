package com.ccc.ccp

import android.content.Context
import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException

@Parcelize
data class Country(
    var nameCode: String,
    var phoneCode: String,
    var name: String
) : Parcelable {
    companion object {
        private val TAG = this::class.java.simpleName

        fun loadCountryDataFromXML(context: Context, languageCode: String): List<Country> {
            val countries = ArrayList<Country>()
            val defaultLanguage = if (languageCode == "vi" || languageCode == "en") {
                languageCode
            } else {
                "en"
            }
            try {
                val xmlFactoryObject = XmlPullParserFactory.newInstance()
                val xmlPullParser = xmlFactoryObject.newPullParser()
                val ins = context.resources.openRawResource(
                    context.resources
                        .getIdentifier(
                            "ccp_$defaultLanguage",
                            "raw", context.packageName
                        )
                )
                xmlPullParser.setInput(ins, "UTF-8")
                var event = xmlPullParser.eventType
                while (event != XmlPullParser.END_DOCUMENT) {
                    val nameParser = xmlPullParser.name
                    when (event) {
                        XmlPullParser.END_TAG -> {
                            if (nameParser == "country") {
                                val nameCode = xmlPullParser.getAttributeValue(null, "name_code")
                                val phoneCode = xmlPullParser.getAttributeValue(null, "phone_code")
                                val name = xmlPullParser.getAttributeValue(null, "name")
                                val countryCode =
                                    Country(
                                        nameCode = nameCode,
                                        phoneCode = "+$phoneCode",
                                        name = name
                                    )
                                countries.add(countryCode)
                            }
                        }
                    }
                    event = xmlPullParser.next()
                }
            } catch (e: XmlPullParserException) {
                Log.e(TAG, "error", e)
            } catch (e: IOException) {
                Log.e(TAG, "error", e)
            } catch (e: Exception) {
                Log.e(TAG, "error", e)
            }
            return countries.sortedBy { it.name.removeAccent() }
        }

        fun getFlagMasterResID(countryCode: Country): Int {
            when (countryCode.nameCode.toLowerCase()) {
                //this should be sorted based on country name code.
                "ad" //andorra
                -> return R.drawable.flag_andorra
                "ae" //united arab emirates
                -> return R.drawable.flag_uae
                "af" //afghanistan
                -> return R.drawable.flag_afghanistan
                "ag" //antigua & barbuda
                -> return R.drawable.flag_antigua_and_barbuda
                "ai" //anguilla // Caribbean Islands
                -> return R.drawable.flag_anguilla
                "al" //albania
                -> return R.drawable.flag_albania
                "am" //armenia
                -> return R.drawable.flag_armenia
                "ao" //angola
                -> return R.drawable.flag_angola
                "aq" //antarctica // custom
                -> return R.drawable.flag_antarctica
                "ar" //argentina
                -> return R.drawable.flag_argentina
                "as" //American Samoa
                -> return R.drawable.flag_american_samoa
                "at" //austria
                -> return R.drawable.flag_austria
                "au" //australia
                -> return R.drawable.flag_australia
                "aw" //aruba
                -> return R.drawable.flag_aruba
                "ax" //alan islands
                -> return R.drawable.flag_aland
                "az" //azerbaijan
                -> return R.drawable.flag_azerbaijan
                "ba" //bosnia and herzegovina
                -> return R.drawable.flag_bosnia
                "bb" //barbados
                -> return R.drawable.flag_barbados
                "bd" //bangladesh
                -> return R.drawable.flag_bangladesh
                "be" //belgium
                -> return R.drawable.flag_belgium
                "bf" //burkina faso
                -> return R.drawable.flag_burkina_faso
                "bg" //bulgaria
                -> return R.drawable.flag_bulgaria
                "bh" //bahrain
                -> return R.drawable.flag_bahrain
                "bi" //burundi
                -> return R.drawable.flag_burundi
                "bj" //benin
                -> return R.drawable.flag_benin
                "bl" //saint barthélemy
                -> return R.drawable.flag_saint_barthelemy// custom
                "bm" //bermuda
                -> return R.drawable.flag_bermuda
                "bn" //brunei darussalam // custom
                -> return R.drawable.flag_brunei
                "bo" //bolivia, plurinational state of
                -> return R.drawable.flag_bolivia
                "br" //brazil
                -> return R.drawable.flag_brazil
                "bs" //bahamas
                -> return R.drawable.flag_bahamas
                "bt" //bhutan
                -> return R.drawable.flag_bhutan
                "bw" //botswana
                -> return R.drawable.flag_botswana
                "by" //belarus
                -> return R.drawable.flag_belarus
                "bz" //belize
                -> return R.drawable.flag_belize
                "ca" //canada
                -> return R.drawable.flag_canada
                "cc" //cocos (keeling) islands
                -> return R.drawable.flag_cocos// custom
                "cd" //congo, the democratic republic of the
                -> return R.drawable.flag_democratic_republic_of_the_congo
                "cf" //central african republic
                -> return R.drawable.flag_central_african_republic
                "cg" //congo
                -> return R.drawable.flag_republic_of_the_congo
                "ch" //switzerland
                -> return R.drawable.flag_switzerland
                "ci" //côte d\'ivoire
                -> return R.drawable.flag_cote_divoire
                "ck" //cook islands
                -> return R.drawable.flag_cook_islands
                "cl" //chile
                -> return R.drawable.flag_chile
                "cm" //cameroon
                -> return R.drawable.flag_cameroon
                "cn" //china
                -> return R.drawable.flag_china
                "co" //colombia
                -> return R.drawable.flag_colombia
                "cr" //costa rica
                -> return R.drawable.flag_costa_rica
                "cu" //cuba
                -> return R.drawable.flag_cuba
                "cv" //cape verde
                -> return R.drawable.flag_cape_verde
                "cw" //curaçao
                -> return R.drawable.flag_curacao
                "cx" //christmas island
                -> return R.drawable.flag_christmas_island
                "cy" //cyprus
                -> return R.drawable.flag_cyprus
                "cz" //czech republic
                -> return R.drawable.flag_czech_republic
                "de" //germany
                -> return R.drawable.flag_germany
                "dj" //djibouti
                -> return R.drawable.flag_djibouti
                "dk" //denmark
                -> return R.drawable.flag_denmark
                "dm" //dominica
                -> return R.drawable.flag_dominica
                "do" //dominican republic
                -> return R.drawable.flag_dominican_republic
                "dz" //algeria
                -> return R.drawable.flag_algeria
                "ec" //ecuador
                -> return R.drawable.flag_ecuador
                "ee" //estonia
                -> return R.drawable.flag_estonia
                "eg" //egypt
                -> return R.drawable.flag_egypt
                "er" //eritrea
                -> return R.drawable.flag_eritrea
                "es" //spain
                -> return R.drawable.flag_spain
                "et" //ethiopia
                -> return R.drawable.flag_ethiopia
                "fi" //finland
                -> return R.drawable.flag_finland
                "fj" //fiji
                -> return R.drawable.flag_fiji
                "fk" //falkland islands (malvinas)
                -> return R.drawable.flag_falkland_islands
                "fm" //micronesia, federated states of
                -> return R.drawable.flag_micronesia
                "fo" //faroe islands
                -> return R.drawable.flag_faroe_islands
                "fr" //france
                -> return R.drawable.flag_france
                "ga" //gabon
                -> return R.drawable.flag_gabon
                "gb" //united kingdom
                -> return R.drawable.flag_united_kingdom
                "gd" //grenada
                -> return R.drawable.flag_grenada
                "ge" //georgia
                -> return R.drawable.flag_georgia
                "gf" //guyane
                -> return R.drawable.flag_guyane
                "gg" //Guernsey
                -> return R.drawable.flag_guernsey
                "gh" //ghana
                -> return R.drawable.flag_ghana
                "gi" //gibraltar
                -> return R.drawable.flag_gibraltar
                "gl" //greenland
                -> return R.drawable.flag_greenland
                "gm" //gambia
                -> return R.drawable.flag_gambia
                "gn" //guinea
                -> return R.drawable.flag_guinea
                "gp" //guadeloupe
                -> return R.drawable.flag_guadeloupe
                "gq" //equatorial guinea
                -> return R.drawable.flag_equatorial_guinea
                "gr" //greece
                -> return R.drawable.flag_greece
                "gt" //guatemala
                -> return R.drawable.flag_guatemala
                "gu" //Guam
                -> return R.drawable.flag_guam
                "gw" //guinea-bissau
                -> return R.drawable.flag_guinea_bissau
                "gy" //guyana
                -> return R.drawable.flag_guyana
                "hk" //hong kong
                -> return R.drawable.flag_hong_kong
                "hn" //honduras
                -> return R.drawable.flag_honduras
                "hr" //croatia
                -> return R.drawable.flag_croatia
                "ht" //haiti
                -> return R.drawable.flag_haiti
                "hu" //hungary
                -> return R.drawable.flag_hungary
                "id" //indonesia
                -> return R.drawable.flag_indonesia
                "ie" //ireland
                -> return R.drawable.flag_ireland
                "il" //israel
                -> return R.drawable.flag_israel
                "im" //isle of man
                -> return R.drawable.flag_isleof_man // custom
                "is" //Iceland
                -> return R.drawable.flag_iceland
                "in" //india
                -> return R.drawable.flag_india
                "io" //British indian ocean territory
                -> return R.drawable.flag_british_indian_ocean_territory
                "iq" //iraq
                -> return R.drawable.flag_iraq_new
                "ir" //iran, islamic republic of
                -> return R.drawable.flag_iran
                "it" //italy
                -> return R.drawable.flag_italy
                "je" //Jersey
                -> return R.drawable.flag_jersey
                "jm" //jamaica
                -> return R.drawable.flag_jamaica
                "jo" //jordan
                -> return R.drawable.flag_jordan
                "jp" //japan
                -> return R.drawable.flag_japan
                "ke" //kenya
                -> return R.drawable.flag_kenya
                "kg" //kyrgyzstan
                -> return R.drawable.flag_kyrgyzstan
                "kh" //cambodia
                -> return R.drawable.flag_cambodia
                "ki" //kiribati
                -> return R.drawable.flag_kiribati
                "km" //comoros
                -> return R.drawable.flag_comoros
                "kn" //st kitts & nevis
                -> return R.drawable.flag_saint_kitts_and_nevis
                "kp" //north korea
                -> return R.drawable.flag_north_korea
                "kr" //south korea
                -> return R.drawable.flag_south_korea
                "kw" //kuwait
                -> return R.drawable.flag_kuwait
                "ky" //Cayman_Islands
                -> return R.drawable.flag_cayman_islands
                "kz" //kazakhstan
                -> return R.drawable.flag_kazakhstan
                "la" //lao people\'s democratic republic
                -> return R.drawable.flag_laos
                "lb" //lebanon
                -> return R.drawable.flag_lebanon
                "lc" //st lucia
                -> return R.drawable.flag_saint_lucia
                "li" //liechtenstein
                -> return R.drawable.flag_liechtenstein
                "lk" //sri lanka
                -> return R.drawable.flag_sri_lanka
                "lr" //liberia
                -> return R.drawable.flag_liberia
                "ls" //lesotho
                -> return R.drawable.flag_lesotho
                "lt" //lithuania
                -> return R.drawable.flag_lithuania
                "lu" //luxembourg
                -> return R.drawable.flag_luxembourg
                "lv" //latvia
                -> return R.drawable.flag_latvia
                "ly" //libya
                -> return R.drawable.flag_libya
                "ma" //morocco
                -> return R.drawable.flag_morocco
                "mc" //monaco
                -> return R.drawable.flag_monaco
                "md" //moldova, republic of
                -> return R.drawable.flag_moldova
                "me" //montenegro
                -> return R.drawable.flag_of_montenegro// custom
                "mf" -> return R.drawable.flag_saint_martin
                "mg" //madagascar
                -> return R.drawable.flag_madagascar
                "mh" //marshall islands
                -> return R.drawable.flag_marshall_islands
                "mk" //macedonia, the former yugoslav republic of
                -> return R.drawable.flag_macedonia
                "ml" //mali
                -> return R.drawable.flag_mali
                "mm" //myanmar
                -> return R.drawable.flag_myanmar
                "mn" //mongolia
                -> return R.drawable.flag_mongolia
                "mo" //macao
                -> return R.drawable.flag_macao
                "mp" // Northern mariana islands
                -> return R.drawable.flag_northern_mariana_islands
                "mq" //martinique
                -> return R.drawable.flag_martinique
                "mr" //mauritania
                -> return R.drawable.flag_mauritania
                "ms" //montserrat
                -> return R.drawable.flag_montserrat
                "mt" //malta
                -> return R.drawable.flag_malta
                "mu" //mauritius
                -> return R.drawable.flag_mauritius
                "mv" //maldives
                -> return R.drawable.flag_maldives
                "mw" //malawi
                -> return R.drawable.flag_malawi
                "mx" //mexico
                -> return R.drawable.flag_mexico
                "my" //malaysia
                -> return R.drawable.flag_malaysia
                "mz" //mozambique
                -> return R.drawable.flag_mozambique
                "na" //namibia
                -> return R.drawable.flag_namibia
                "nc" //new caledonia
                -> return R.drawable.flag_new_caledonia// custom
                "ne" //niger
                -> return R.drawable.flag_niger
                "nf" //Norfolk
                -> return R.drawable.flag_norfolk_island
                "ng" //nigeria
                -> return R.drawable.flag_nigeria
                "ni" //nicaragua
                -> return R.drawable.flag_nicaragua
                "nl" //netherlands
                -> return R.drawable.flag_netherlands
                "no" //norway
                -> return R.drawable.flag_norway
                "np" //nepal
                -> return R.drawable.flag_nepal
                "nr" //nauru
                -> return R.drawable.flag_nauru
                "nu" //niue
                -> return R.drawable.flag_niue
                "nz" //new zealand
                -> return R.drawable.flag_new_zealand
                "om" //oman
                -> return R.drawable.flag_oman
                "pa" //panama
                -> return R.drawable.flag_panama
                "pe" //peru
                -> return R.drawable.flag_peru
                "pf" //french polynesia
                -> return R.drawable.flag_french_polynesia
                "pg" //papua new guinea
                -> return R.drawable.flag_papua_new_guinea
                "ph" //philippines
                -> return R.drawable.flag_philippines
                "pk" //pakistan
                -> return R.drawable.flag_pakistan
                "pl" //poland
                -> return R.drawable.flag_poland
                "pm" //saint pierre and miquelon
                -> return R.drawable.flag_saint_pierre
                "pn" //pitcairn
                -> return R.drawable.flag_pitcairn_islands
                "pr" //puerto rico
                -> return R.drawable.flag_puerto_rico
                "ps" //palestine
                -> return R.drawable.flag_palestine
                "pt" //portugal
                -> return R.drawable.flag_portugal
                "pw" //palau
                -> return R.drawable.flag_palau
                "py" //paraguay
                -> return R.drawable.flag_paraguay
                "qa" //qatar
                -> return R.drawable.flag_qatar
                "re" //la reunion
                -> return R.drawable.flag_martinique // no exact flag found
                "ro" //romania
                -> return R.drawable.flag_romania
                "rs" //serbia
                -> return R.drawable.flag_serbia // custom
                "ru" //russian federation
                -> return R.drawable.flag_russian_federation
                "rw" //rwanda
                -> return R.drawable.flag_rwanda
                "sa" //saudi arabia
                -> return R.drawable.flag_saudi_arabia
                "sb" //solomon islands
                -> return R.drawable.flag_soloman_islands
                "sc" //seychelles
                -> return R.drawable.flag_seychelles
                "sd" //sudan
                -> return R.drawable.flag_sudan
                "se" //sweden
                -> return R.drawable.flag_sweden
                "sg" //singapore
                -> return R.drawable.flag_singapore
                "sh" //saint helena, ascension and tristan da cunha
                -> return R.drawable.flag_saint_helena // custom
                "si" //slovenia
                -> return R.drawable.flag_slovenia
                "sk" //slovakia
                -> return R.drawable.flag_slovakia
                "sl" //sierra leone
                -> return R.drawable.flag_sierra_leone
                "sm" //san marino
                -> return R.drawable.flag_san_marino
                "sn" //senegal
                -> return R.drawable.flag_senegal
                "so" //somalia
                -> return R.drawable.flag_somalia
                "sr" //suriname
                -> return R.drawable.flag_suriname
                "ss" //south sudan
                -> return R.drawable.flag_south_sudan
                "st" //sao tome and principe
                -> return R.drawable.flag_sao_tome_and_principe
                "sv" //el salvador
                -> return R.drawable.flag_el_salvador
                "sx" //sint maarten
                -> return R.drawable.flag_sint_maarten
                "sy" //syrian arab republic
                -> return R.drawable.flag_syria
                "sz" //swaziland
                -> return R.drawable.flag_swaziland
                "tc" //turks & caicos islands
                -> return R.drawable.flag_turks_and_caicos_islands
                "td" //chad
                -> return R.drawable.flag_chad
                "tg" //togo
                -> return R.drawable.flag_togo
                "th" //thailand
                -> return R.drawable.flag_thailand
                "tj" //tajikistan
                -> return R.drawable.flag_tajikistan
                "tk" //tokelau
                -> return R.drawable.flag_tokelau // custom
                "tl" //timor-leste
                -> return R.drawable.flag_timor_leste
                "tm" //turkmenistan
                -> return R.drawable.flag_turkmenistan
                "tn" //tunisia
                -> return R.drawable.flag_tunisia
                "to" //tonga
                -> return R.drawable.flag_tonga
                "tr" //turkey
                -> return R.drawable.flag_turkey
                "tt" //trinidad & tobago
                -> return R.drawable.flag_trinidad_and_tobago
                "tv" //tuvalu
                -> return R.drawable.flag_tuvalu
                "tw" //taiwan, province of china
                -> return R.drawable.flag_taiwan
                "tz" //tanzania, united republic of
                -> return R.drawable.flag_tanzania
                "ua" //ukraine
                -> return R.drawable.flag_ukraine
                "ug" //uganda
                -> return R.drawable.flag_uganda
                "us" //united states
                -> return R.drawable.flag_united_states_of_america
                "uy" //uruguay
                -> return R.drawable.flag_uruguay
                "uz" //uzbekistan
                -> return R.drawable.flag_uzbekistan
                "va" //holy see (vatican city state)
                -> return R.drawable.flag_vatican_city
                "vc" //st vincent & the grenadines
                -> return R.drawable.flag_saint_vicent_and_the_grenadines
                "ve" //venezuela, bolivarian republic of
                -> return R.drawable.flag_venezuela
                "vg" //british virgin islands
                -> return R.drawable.flag_british_virgin_islands
                "vi" //us virgin islands
                -> return R.drawable.flag_us_virgin_islands
                "vn" //vietnam
                -> return R.drawable.flag_vietnam
                "vu" //vanuatu
                -> return R.drawable.flag_vanuatu
                "wf" //wallis and futuna
                -> return R.drawable.flag_wallis_and_futuna
                "ws" //samoa
                -> return R.drawable.flag_samoa
                "xk" //kosovo
                -> return R.drawable.flag_kosovo
                "ye" //yemen
                -> return R.drawable.flag_yemen
                "yt" //mayotte
                -> return R.drawable.flag_martinique // no exact flag found
                "za" //south africa
                -> return R.drawable.flag_south_africa
                "zm" //zambia
                -> return R.drawable.flag_zambia
                "zw" //zimbabwe
                -> return R.drawable.flag_zimbabwe
                else -> return R.drawable.flag_transparent
            }
        }
    }
}
