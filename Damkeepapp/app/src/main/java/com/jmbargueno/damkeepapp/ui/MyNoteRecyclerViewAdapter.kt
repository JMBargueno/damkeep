package com.jmbargueno.damkeepapp.ui

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmbargueno.damkeepapp.NoteDetailActivity
import com.jmbargueno.damkeepapp.R
import com.jmbargueno.damkeepapp.common.MyApp

import com.jmbargueno.damkeepapp.model.Note

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyNoteRecyclerViewAdapter() : RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Note> = ArrayList()
    private val mOnClickListener: View.OnClickListener
    var id: String = ""

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Note
            id = item.id.toString()
            var detail: Intent = Intent(MyApp.instance, NoteDetailActivity::class.java).apply {
                putExtra("id", id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mView: View = view
        val title: TextView = view.findViewById(R.id.textViewNoteFragmentTitle)


    }

    fun setData(orders: List<Note>?) {
        this.values = orders!!
        notifyDataSetChanged()
    }
}