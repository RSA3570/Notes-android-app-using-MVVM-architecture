package com.rsa.myfirstnoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var inputText: EditText
    lateinit var addButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.input)
        addButton = findViewById(R.id.addButton)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRvAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }

        })


    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        showToast("${note.text} Deleted!")
    }

    fun submitData(view: View) {
        val noteText = inputText.text.toString()
        if (noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            showToast("$noteText Inserted!")
        }
    }
    fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

}