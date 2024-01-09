package com.example.kotlinnotes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
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
        val view: View = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.btnEdit?.setOnClickListener { listeners!!.change(holder.adapterPosition) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun sortAbc() {
        list.sortBy { list. }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView? = null
        var title: TextView? = null
        var desc: TextView? = null
        var date: TextView? = null
        var btnEdit: ImageView? = null

        init {
            img = itemView.findViewById(R.id.item_img)
            title = itemView.findViewById(R.id.item_tv_title)
            desc = itemView.findViewById(R.id.item_tv_des)
            date = itemView.findViewById(R.id.item_tv_date)
            btnEdit = itemView.findViewById(R.id.item_btn_edit)
        }

        fun onBind(note: Note) {
            title?.text = note.title
            desc?.text = note.description
            date?.text = note.date
        }
    }

    interface ListenersBtn {
        fun change(position: Int)
    }
}