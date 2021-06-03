package com.example.login.ui.dashboard

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
import kotlinx.android.synthetic.main.fragment_chicken_list.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class chicken_list : Fragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chicken_list, container, false)
        val Balls = arrayListOf("土雞雞腿肉","乾香菇","水","鹽","薑片","米酒")
        val engNames = arrayListOf( "250g"," 5朵","淹過食材","1茶匙","6片","適量")
        val items = ArrayList<Map<String, Any>>()
        for (i in Balls.indices) {
            val item = HashMap<String, Any>()
            item["textView4"] = Balls[i]
            item["textView5"] = engNames[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(this.activity?.applicationContext,
            items, R.layout.list_layout, arrayOf("textView4","textView5"),
            intArrayOf(R.id.textView4, R.id.textView5))
        root.listview_chicken.adapter = adapter
//    val textView: TextView = root.findViewById(R.id.text_dashboard)
//    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })
        return root
    }
}
