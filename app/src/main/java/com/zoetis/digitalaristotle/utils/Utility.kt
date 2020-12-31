package com.zoetis.digitalaristotle.utils

import android.content.Context

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

    fun setCustomFont(htmlString: String, path: String): String? {
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