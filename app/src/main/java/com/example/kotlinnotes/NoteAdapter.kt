package com.example.kotlinnotes

import android.annotation.SuppressLint
import android.content.Context
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotes.databinding.ActivityMainBinding
import com.example.kotlinnotes.databinding.FragmentSecondBinding
import com.example.kotlinnotes.databinding.ItemNoteBinding


class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private lateinit var binding: ItemNoteBinding
    private var inflater: LayoutInflater
    private var list: MutableList<Note> = ArrayList<Note>()
    private var listeners: ListenersBtn? = null
    private val nameComparator: Comparator<Note> = compareBy { it.title }


    constructor(context: Context?, listeners: ListenersBtn?) {
        inflater = LayoutInflater.from(context)
        this.listeners = listeners
    }

    constructor(context: Context?) {
        inflater = LayoutInflater.from(context)
    }

    fun addNote(list: MutableList<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun changeNote(note: Note, position: Int) {
        list[position] = note
        notifyItemChanged(position)
    }

    fun getList(): List<Note> {
        return list
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortAbc(list : List<Note>) {
       this.list = list.sortedWith(nameComparator).toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val Binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        binding = Binding
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.binding.itemBtnEdit.setOnClickListener { listeners!!.change(holder.adapterPosition) }
        holder.binding.itemBtnDelete.setOnClickListener{
            listeners?.delete(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun sortAbc() {
        list.sortBy { list.size }
    }

    fun listDelete(position: Int){
        list.remove(list[position])
        notifyItemRemoved(position)
    }

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(note: Note) {
            binding.itemTvTitle.text = note.title
            binding.itemTvDes.text = note.description
            binding.itemTvDate.text = note.date
        }
    }

    interface ListenersBtn {
        fun change(position: Int)
        fun delete(position : Int)
    }
}