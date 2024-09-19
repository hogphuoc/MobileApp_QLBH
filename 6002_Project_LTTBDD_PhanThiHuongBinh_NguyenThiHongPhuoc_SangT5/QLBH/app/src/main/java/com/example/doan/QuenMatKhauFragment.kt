package com.example.doan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class QuenMatKhauFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quenmatkhau_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextNewPassword = view.findViewById<EditText>(R.id.editTextNewPassword)
        val buttonSavePassword = view.findViewById<Button>(R.id.buttonSavePassword)
        val buttonexit = view.findViewById<Button>(R.id.btnexit)

        buttonSavePassword.setOnClickListener {
            val newPassword = editTextNewPassword.text.toString()
            if (newPassword.isNotEmpty()) {
                // Lưu mật khẩu mới vào SharedPreferences
                val sharedPreferences =
                    requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("password", newPassword)
                    apply()
                }
                Toast.makeText(requireContext(), "Mật khẩu đã được lưu", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonexit.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}