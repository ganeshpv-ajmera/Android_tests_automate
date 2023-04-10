package com.aop.interplay.ui.splash

import android.os.Bundle
import com.aop.interplay.utils.AppConstants

class WebViewBundle {
    companion object{
        fun getPrivacyPolicyBundle() : Bundle {
            val bundle = Bundle()
            bundle.putString(AppConstants.TITLE_KEY,AppConstants.PRIVACY_POLICY_TITLE)
            bundle.putString(AppConstants.URL_KEY,AppConstants.PRIVACY_POLICY_WEB_URL)
            return bundle
        }

        fun getTermsOfConditionsBundle() : Bundle {
            val bundle = Bundle()
            bundle.putString(AppConstants.TITLE_KEY,AppConstants.TERMS_OF_SERVICE_TITLE)
            bundle.putString(AppConstants.URL_KEY,AppConstants.TERMS_OF_SERVICE_WEB_URL)
            return bundle
        }
    }
}