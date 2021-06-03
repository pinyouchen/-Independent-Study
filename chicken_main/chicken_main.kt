package com.example.login.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.login.*
import kotlinx.android.synthetic.main.fragment_chicken_main.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class chicken_main : Fragment() {


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_chicken_main, container, false)
        root.button_chicken3.setOnClickListener{
            val intent = Intent()
            this.activity?.applicationContext?.let { it1 -> intent.setClass(it1, food_detail::class.java) }
            startActivity(intent);
        }

        root.button_chicken4.setOnClickListener{
            val intent = Intent()
            this.activity?.applicationContext?.let { it1 -> intent.setClass(it1, text::class.java) }
            startActivity(intent);
        }


        root.floatingActionButton_chicken.setOnClickListener{
            val intent = Intent()
            this.activity?.applicationContext?.let { it1 -> intent.setClass(it1, new_diary::class.java) }
            startActivity(intent);
        }

//        val bHeart = findViewById<View>(R.id.heart_chicken) as ShineButton
        root.heart_chicken.init(root.context as Activity?)

//    val textView: TextView = root.findViewById(R.id.text_home)
//    homeViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })
        return root

    }



}




