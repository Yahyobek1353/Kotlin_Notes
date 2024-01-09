package com.example.kotlinnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kotlinnotes.databinding.FragmentMainBinding
import com.example.kotlinnotes.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SecondFragment : Fragment() {
   private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val noteBinding = FragmentSecondBinding.inflate(inflater , container , false)
        binding = noteBinding
        return noteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        if (arguments != null) {
            binding.etTitle.setText(requireArguments().getString("changeTitle"))
            binding.etDes.setText(requireArguments().getString("changeDesc"))
            binding.etDate.setText(requireArguments().getString("changeDate"))
            //Инициализация изменения заметок
            binding.btnSave.setOnClickListener(View.OnClickListener {
                val changeTitle = binding.etTitle.text.toString()
                val changeDesck = binding.etDes.text.toString()
                val position = requireArguments().getInt("position")
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val changeDate = sdf.format(Date())
                val note = Note(changeTitle, changeDesck, changeDate, 1)
                bundle.putSerializable("edit_note", note)
                bundle.putInt("position", position)
                requireActivity().supportFragmentManager.setFragmentResult("change_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            })
        } else {
            //Инициализация сохранения новых заметок
            binding.btnSave.setOnClickListener(View.OnClickListener {
                val title = binding.etTitle.text.toString()
                val desc = binding.etDes.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val date = sdf.format(Date())
                val note = Note(title, desc, date, 1)
                bundle.putSerializable("note", note)
                requireActivity().supportFragmentManager.setFragmentResult("new_note", bundle)
                requireActivity().supportFragmentManager.popBackStack()
            })
        }
    }
}