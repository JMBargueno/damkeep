package com.jmbargueno.damkeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.damkeepapp.client.request.CreateNoteRequest
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.viewmodel.NoteViewModel
import javax.inject.Inject

class AddNoteActivity : AppCompatActivity() {

    @Inject
    lateinit var noteViewModel: NoteViewModel

    @BindView(R.id.editTextSaveNote)
    lateinit var editTextSaveNote: EditText

    @BindView(R.id.editTextMultiLineSaveContent)
    lateinit var editTextMultiLineSaveContent: EditText

    @BindView(R.id.buttonNewNoteSave)
    lateinit var buttonNewNoteSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)

        setListeners()
    }

    fun setListeners() {
        buttonNewNoteSave.setOnClickListener(View.OnClickListener {
            noteViewModel.createNote(
                CreateNoteRequest(
                    editTextSaveNote.text.toString(),
                    editTextMultiLineSaveContent.text.toString()
                )
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