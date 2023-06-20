package com.muratozturk.mova.common

import android.content.Context
import android.content.Intent
import android.net.Uri

object WebUtil {
    fun openBrowserUrl(context:Context, url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}