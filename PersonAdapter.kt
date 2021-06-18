package com.example.louis.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.louis.Interface.IPersonClickListener
import com.example.louis.Model.Person
import com.example.louis.R
import kotlinx.android.synthetic.main.person_layout.view.*

class PersonAdapter(internal var context : Context, internal var personList:List<Person>) : RecyclerView.Adapter<PersonAdapter.MyViewHolder>()
{
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{

//        fun bindItem(model : Person){
//            itemView.text_name.text = model.name
//            itemView.text_email.text = model.email
//            itemView.text_phone.text = model.phone
//        }

        internal var name: TextView
        internal var email: TextView
        internal var phone: TextView

        private lateinit var personClickListener:IPersonClickListener

        fun setClick(personClickListener: IPersonClickListener)
        {
            this.personClickListener = personClickListener;
        }

        init{
            name = itemView.findViewById(R.id.text_name) as TextView
            email = itemView.findViewById(R.id.text_email) as TextView
            phone = itemView.findViewById(R.id.text_phone) as TextView
        }

        override fun onClick(p0: View?) {
            personClickListener.onPersonClick(p0!!,adapterPosition)
        }


    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.person_layout,p0,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.name.text = personList[p1].name
        p0.email.text = personList[p1].email
        p0.phone.text = personList[p1].phone

        if(p1 % 2 == 0)
            //p0.aaa.setCardBackgroundColor(Color.parseColor("#123456"))

        p0.setClick(object : IPersonClickListener{
            override fun onPersonClick(view: View, position: Int) {
                Toast.makeText(context,""+personList[position].name,Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}