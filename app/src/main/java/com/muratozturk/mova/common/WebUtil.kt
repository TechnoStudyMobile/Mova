package com.muratozturk.mova.common
import android.content.Context
import android.content.Intent
import android.net.Uri


object WebUtil {
    fun openWebPage(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}