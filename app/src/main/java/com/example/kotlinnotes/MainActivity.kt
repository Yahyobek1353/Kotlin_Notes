package com.example.kotlinnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, MainFragment())
        transaction.addToBackStack("MainFragment")
        transaction.commit()
        val adapter = NoteAdapter(this)
        supportFragmentManager.setFragmentResultListener(
            "new_note", this
        ) { _: String?, result: Bundle ->
            val note = result.getSerializable("note") as Note?
            mainList.add(note)
            adapter.addNote(mainList)
        }
    }


}

    fun NoteAdapter.addNote(list: List<Note?>) {
     val mainList: MutableList<Note?> = ArrayList()
    fun getList(): List<Note?>? {
        return mainList
    }
}
