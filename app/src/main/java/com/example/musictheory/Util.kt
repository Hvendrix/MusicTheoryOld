package com.example.musictheory

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.musictheory.database.Answer

fun formatAnswers(answers: List<Answer>, resources: Resources): Spanned {
    val sb = StringBuilder()

    sb.apply{
        append("<h3>Hello</h3>")
        answers.forEach{
            append("<br>")
            append("id is: \t${it.ansId.toString()}<br>")
            append("${it.quality.toString()}<br>")
            append("${it.stroka}<br>")
//            it.errorList.forEach {
//                append("\t${it}")
//            }


        }
    }


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}