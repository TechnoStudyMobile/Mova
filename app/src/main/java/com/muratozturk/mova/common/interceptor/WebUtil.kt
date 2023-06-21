package com.muratozturk.mova.common.interceptor

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.airbnb.lottie.animation.content.Content

object WebUtil {
    fun openWebPage(context: Context, url: String){
        val intent= Intent(Intent.ACTION_VIEW)
        intent.data= Uri.parse(url)
        context.startActivity(intent)
    }
}