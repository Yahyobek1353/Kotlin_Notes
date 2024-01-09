package com.example.kotlinnotes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotes.databinding.FragmentMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment(), NoteAdapter.ListenersBtn {
    private var adapter: NoteAdapter? = null
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val noteBinding = FragmentMainBinding.inflate(inflater , container , false)
        binding = noteBinding
        return noteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteAdapter(requireContext(), this)
        binding.rvNotes.setAdapter(adapter)
        adapter!!.addNote(MainActivity.getList)
        binding.btnSort.setOnClickListener(View.OnClickListener { view1: View? -> adapter!!.sortAbc() })

        //Инициализация обработчика нажатия на кнопку и перехода на SecondFragment
        binding.btnAdd.setOnClickListener(View.OnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, SecondFragment())
            transaction.addToBackStack("SecondFragment")
            transaction.commit()
        })


        //Инициализация добавления новых заметок
        /*   requireActivity().getSupportFragmentManager().setFragmentResultListener("new_note", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("note");
                adapter.addNote(note);
            }
        });*/requireActivity().supportFragmentManager.setFragmentResultListener(
            "change_note", this
        ) { requestKey, result ->
            val note: Note? = result.getSerializable("edit_note") as Note?
            adapter!!.changeNote(note!! , result.getInt("position"))
        }
    }

    override fun change(position: Int) {
        val note: Note = adapter!!.getList()[position]
        val bundle = Bundle()
        bundle.putString("changeTitle", note.title)
        bundle.putString("changeDesc", note.description)
        bundle.putString("changeDate", note.date)
        bundle.putInt("position", position)
        val secondFragment = SecondFragment()
        secondFragment.setArguments(bundle)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, secondFragment)
        transaction.addToBackStack("SecondFragment")
        transaction.commit()
    }
}
