package com.example.doan

import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager


class LoginFragment : Fragment() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var checkBoxShowPassword: CheckBox
    private lateinit var buttonLogin: Button
    private lateinit var buttonForgotPassword: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        checkBoxShowPassword = view.findViewById(R.id.checkBoxShowPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        buttonForgotPassword = view.findViewById(R.id.buttonForgotPassword)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Lấy mật khẩu từ SharedPreferences
            val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val savedPassword = sharedPreferences.getString("password", null)

            if (username == "quanly" && password == savedPassword) {
                navigateToHomeFragment()
            }

            else {
                Toast.makeText(requireContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
        }
        buttonForgotPassword.setOnClickListener {
            resetPassword()
        }

        checkBoxShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Ẩn password
                editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        return view
    }

    private fun navigateToHomeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomePageFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun resetPassword() {
        // Đặt lại mật khẩu về giá trị mặc định
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("password", "password") // Giá trị mặc định là "password"
            apply()
        }
        Toast.makeText(requireContext(), "Mật khẩu đã được đặt lại về mặc định", Toast.LENGTH_SHORT).show()
    }
}
