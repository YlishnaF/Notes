package com.example.notes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.example.notes.R
import com.example.notes.data.model.Color
import com.example.notes.data.model.Note
import com.example.notes.databinding.ActivityNoteBinding
import com.example.notes.extensions.MyLog
import com.example.notes.ui.common.BaseActivity
import com.example.notes.ui.note.NoteViewModel
import com.example.notes.ui.note.NoteViewState
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {

        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun getStartIntent(context: Context, noteId: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    private lateinit var ui: ActivityNoteBinding
    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //not implemented
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //not implemented
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityNoteBinding.inflate(layoutInflater)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let {
            viewModel.loadNote(it)
        }

        if (noteId == null) supportActionBar?.title = getString(R.string.new_note_title)
        initView()
        ui.titleEt.addTextChangedListener(textChangeListener)
        ui.bodyEt.addTextChangedListener(textChangeListener)
    }

    private fun initView() {
        Log.d(MyLog, "fun init in NoteActivity")
        ui.titleEt.setText(note?.title ?: "")
        ui.bodyEt.setText(note?.note ?: "")
        val color = when (note?.color) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yellow
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
            else -> R.color.color_white
        }

        ui.toolbar.setBackgroundColor(resources.getColor(color))
        ui.titleEt.addTextChangedListener(textChangeListener)
        ui.bodyEt.addTextChangedListener(textChangeListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun triggerSaveNote() {
        Log.d(MyLog, "fun triggerSaveNote")
        if (ui.titleEt.text == null || ui.titleEt.text!!.length < 3) return

        Handler(Looper.getMainLooper()).postDelayed({
            note = note?.copy(
                title = ui.titleEt.text.toString(),
                note = ui.bodyEt.text.toString(),
                lastChanged = Date()
            ) ?: createNewNote()

            if (note != null) viewModel.saveChanges(note!!)
        }, SAVE_DELAY)
    }

    private fun createNewNote(): Note = Note(
        viewModel.newId(),
        ui.titleEt.text.toString(),
        ui.bodyEt.text.toString()
    )

    override fun renderData(data: Note?) {
        this.note = data
        initView()
    }
}