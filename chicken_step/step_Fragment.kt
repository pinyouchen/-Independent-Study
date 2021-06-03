package com.example.login.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login.R
import kotlinx.android.synthetic.main.fragment_chicken_step.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class chicken_step : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chicken_step, container, false)
        val imgText = arrayListOf("將雞肉與泡過水的香菇、香菇水一起倒入鍋中",
            "再加水約八分滿，蓋上鍋蓋加熱煮滾後轉小火續煮3分鐘即可熄火",
                "打開鍋蓋會聞到很香的雞湯味道，簡單加點鹽巴調味就可以端上桌了",
                "就是這麼簡單，一鍋香噴噴又美味極了的香菇雞湯大成功!!")
        val imgId = arrayListOf(R.drawable.ck_step1, R.drawable.ck_step2, R.drawable.ck_step3, R.drawable.ck_step4)
        val items = ArrayList<Map<String, Any>>()
        for (i in imgId.indices) {
            val item = HashMap<String, Any>()
            item["stepimage"] = imgId[i]
            item["steptext"] = imgText[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(this.activity?.applicationContext,
            items, R.layout.grid_layout, arrayOf("stepimage", "steptext"),
            intArrayOf(R.id.stepimage, R.id.steptext))
        root.gridView_ch.adapter=adapter
//    val textView: TextView = root.findViewById(R.id.text_notifications)
//    notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })
        return root
    }
}
