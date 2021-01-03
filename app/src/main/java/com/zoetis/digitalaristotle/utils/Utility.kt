package com.zoetis.digitalaristotle.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class Utility(context: Context) {

    companion object {
        private var utility: Utility? = null

        fun getInstance(mContext: Context): Utility? {
            if (utility == null) {
                utility = Utility(mContext)
            }
            return utility
        }
    }

    fun convertSecondsToHMmSs(seconds: Long): String? {
        val s = seconds % 60
        val m = seconds / 60 % 60
        return String.format("%02d:%02d", m, s)
    }

    public fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    public fun getImageFromBase64(image64: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(image64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun setCustomFont(htmlString: String, path: String): String {
        return "<html>" +
                "<head>" +
                "<style type=\"text/css\">" +
                "@font-face {" +
                "font-family: MyFont;" +
                "src: url(\"" + path + "\")" +
                "}" +
                "body {" +
                "font-family: MyFont;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" + htmlString +
                "</body>" +
                "</html>"
    }

}