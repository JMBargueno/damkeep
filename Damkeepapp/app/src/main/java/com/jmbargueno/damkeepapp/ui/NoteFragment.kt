package com.jmbargueno.damkeepapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jmbargueno.damkeepapp.R
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.model.Note
import com.jmbargueno.damkeepapp.viewmodel.AppUserViewModel
import com.jmbargueno.damkeepapp.viewmodel.NoteViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class NoteFragment : Fragment() {
    @Inject
    lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: MyNoteRecyclerViewAdapter
    private var notes: List<Note> = ArrayList()


    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).getAppCompontent().inject(this)
        Log.d("NOTE", "NOTE FRAGMENT")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NOTE", "ON CREATE VIEW")
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        noteAdapter = MyNoteRecyclerViewAdapter()
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = noteAdapter
            }
        }

        noteViewModel.getAll().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                notes = it
                noteAdapter.setData(notes)
            }else{
            }
        })

        return view
    }


}