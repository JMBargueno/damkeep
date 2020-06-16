package com.jmbargueno.damkeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.damkeepapp.client.request.CreateNoteRequest
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.model.Note
import com.jmbargueno.damkeepapp.viewmodel.NoteViewModel
import javax.inject.Inject

class NoteDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var noteViewModel: NoteViewModel

    @BindView(R.id.textViewDetailTitle)
    lateinit var textViewDetailTitle: TextView

    @BindView(R.id.textViewDetailContent)
    lateinit var textViewDetailContent: TextView

    @BindView(R.id.imageButtonEdit)
    lateinit var imageButtonEdit: ImageButton

    @BindView(R.id.imageButtonDelete)
    lateinit var imageButtonDelete: ImageButton

    @BindView(R.id.editTextTitle)
    lateinit var editTextTitle: EditText

    @BindView(R.id.editTextContent)
    lateinit var editTextContent: EditText

    @BindView(R.id.buttonSave)
    lateinit var buttonSave: Button


    lateinit var id: String
    lateinit var note: Note
    var isEditable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        id = intent.getStringExtra("id")


        noteViewModel.getNote(id).observe(this, Observer {
            if (it != null) {
                note = it
                Log.d("TITLE", note.title)
                textViewDetailTitle.text = note.title
                editTextTitle.setText(note.title)
                Log.d("BODY", note.content)
                textViewDetailContent.text = note.content
                editTextContent.setText(note.content)
            }
        })
        setListeners()

    }

    fun setListeners() {

        imageButtonEdit.setOnClickListener(View.OnClickListener {
            isEditable = !isEditable

            if (isEditable) {
                textViewDetailTitle.visibility = View.GONE
                textViewDetailContent.visibility = View.GONE
                editTextTitle.visibility = View.VISIBLE
                editTextContent.visibility = View.VISIBLE
                buttonSave.visibility = View.VISIBLE
            } else {
                textViewDetailTitle.visibility = View.VISIBLE
                textViewDetailContent.visibility = View.VISIBLE
                editTextTitle.visibility = View.GONE
                editTextContent.visibility = View.GONE
                buttonSave.visibility = View.GONE
            }
        })

        imageButtonDelete.setOnClickListener(View.OnClickListener {
            noteViewModel.removeNote(note.id.toString())
            val main: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(main)
            finish()
        })

        buttonSave.setOnClickListener(View.OnClickListener {
            noteViewModel.editNote(
                note.id.toString(),
                CreateNoteRequest(editTextTitle.text.toString(), editTextContent.text.toString())
            ).observe(this, Observer {
                if (it == null) {
                } else {
                    val toMain: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(toMain)
                    finish()
                }
            })
        })
    }
}