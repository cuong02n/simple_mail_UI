package com.cuong02n.simple_mail_ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.widget.ConstraintLayout
import com.cuong02n.simple_mail_ui.ui.theme.Simple_mail_UITheme
import java.lang.Integer.max
import java.lang.Integer.min

class MainActivity : ComponentActivity() {
    data class Info(
        val subject: String, val sender: String, val message: String, val time: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val csvData = """
                        "Chủ đề 1", "cuong@example.com", "Anh yêu em", "2023-11-01 08:30"
                        "Chủ đề 2", "an@example.com", "Bạn đẹp quá", "2023-11-01 10:45"
                        "Chủ đề 3", "thao@example.com", "Hôm nay trời đẹp", "2023-11-01 12:15"
                        "Chủ đề 4", "huy@example.com", "Đi chơi đi", "2023-11-01 14:20"
                        "Chủ đề 5", "minh@example.com", "Tôi đang rảnh", "2023-11-01 16:50"
                        "Chủ đề 6", "quynh@example.com", "Cảm ơn", "2023-11-01 18:25"
                        "Chủ đề 7", "tung@example.com", "Mình hẹn hò nhé", "2023-11-01 20:30"
                        "Chủ đề 8", "phuong@example.com", "Học bài đi", "2023-11-01 22:45"
                        "Chủ đề 9", "thanh@example.com", "Tớ nhớ cậu", "2023-11-01 23:55"
                        "Chủ đề 10", "linh@example.com", "Cùng nhau đi", "2023-11-02 01:10"
                    """.trimIndent()

        val emails = csvData.split("\n").map { line ->
            val parts = line.split(",").map { it.trim('"', ' ') }
            if (parts.size == 4) {
                Info(parts[0], parts[1], parts[2], parts[3])
            } else {
                null
            }
        }.filterNotNull()
        val listview = findViewById<ListView>(R.id.list_mail)
        val adapter = CustomListAdapter(this,emails)
        listview.adapter = adapter


    }
    class CustomListAdapter(context: Context, private val data: List<Info>) : BaseAdapter() {
        private val mContext: Context = context
        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val info = getItem(position) as Info
            return (mContext as MainActivity).createEmailItemLayout(
                mContext,
                info.subject,
                info.sender,
                info.message,
                info.time
            )
        }


    }

    fun createEmailItemLayout(
        context: Context, subject: String, sender: String, content: String, time: String
    ): RelativeLayout {
        val relativeLayout = RelativeLayout(context)
        relativeLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT

            )

        val emailIcon = Button(context)
        emailIcon.id = View.generateViewId()
        emailIcon.text = sender[0].toString()
        emailIcon.textSize = 24f
        emailIcon.setTextColor(context.resources.getColor(android.R.color.white))
        emailIcon.setBackgroundResource(R.drawable.my_shape)
        val emailIconParams = RelativeLayout.LayoutParams(48.dpToPx(context), 48.dpToPx(context))
        emailIconParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        emailIconParams.addRule(RelativeLayout.CENTER_VERTICAL)
        emailIcon.layoutParams = emailIconParams
        relativeLayout.addView(emailIcon)

        val emailSubject = TextView(context)
        emailSubject.id = View.generateViewId()
        emailSubject.text = subject
        emailSubject.textSize = 16f
        emailSubject.setTextColor(context.resources.getColor(android.R.color.black))
        val emailSubjectParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        emailSubjectParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        emailSubjectParams.addRule(RelativeLayout.END_OF, emailIcon.id)
        emailSubjectParams.leftMargin = 20.dpToPx(context)
        emailSubject.layoutParams = emailSubjectParams
        relativeLayout.addView(emailSubject)

        val emailTimestamp = TextView(context)
        emailTimestamp.text = time
        emailTimestamp.textSize = 12f
        emailTimestamp.setTextColor(context.resources.getColor(android.R.color.darker_gray))
        val emailTimestampParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        emailTimestampParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        emailTimestampParams.addRule(RelativeLayout.CENTER_VERTICAL)
        emailTimestamp.layoutParams = emailTimestampParams
        relativeLayout.addView(emailTimestamp)

        val emailSender = TextView(context)
        emailSender.id = View.generateViewId()
        emailSender.text = sender
        emailSender.textSize = 14f
        emailSender.setTextColor(context.resources.getColor(R.color.black))
        val emailSenderParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        emailSenderParams.addRule(RelativeLayout.BELOW, emailSubject.id)
        emailSenderParams.addRule(RelativeLayout.END_OF, emailIcon.id)
        emailSenderParams.leftMargin = 20.dpToPx(context)

        emailSender.layoutParams = emailSenderParams
        relativeLayout.addView(emailSender)

        val emailContent = TextView(context)
        emailContent.text = content
        emailContent.textSize = 20f
        emailContent.setTextColor(context.resources.getColor(R.color.black))
        val emailContentParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        emailContentParams.addRule(RelativeLayout.BELOW, emailSender.id)
        emailContentParams.addRule(RelativeLayout.END_OF, emailIcon.id)
        emailContentParams.leftMargin = 20.dpToPx(context)

        emailContent.layoutParams = emailContentParams
        relativeLayout.addView(emailContent)

        return relativeLayout
    }

    private fun Int.dpToPx(context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
