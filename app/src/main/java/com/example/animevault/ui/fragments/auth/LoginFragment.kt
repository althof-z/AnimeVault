package com.example.animevault.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.animevault.R
import com.example.animevault.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private lateinit var viewBinding: FragmentLoginBinding
    private val loginViewModel by viewModel<LoginViewModel>()
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentLoginBinding.inflate(layoutInflater, container, false).also {
            viewBinding = it
        }.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnLogin.setOnClickListener {
            if (viewBinding.edtEmailLogin.text.isNullOrEmpty()) {
                viewBinding.edtEmailLogin.error = "email tidak boleh kosong"
            } else if (viewBinding.edtPwLogin.text.isNullOrEmpty()) {
                viewBinding.edtPwLogin.error = "password tidak boleh kosong"
            } else {
                viewBinding.edtEmailLogin.error = null
                viewBinding.edtPwLogin.error = null
                loginViewModel.login(
                    username = viewBinding.edtEmailLogin.text.toString(),
                    password = viewBinding.edtPwLogin.text.toString(),
                )
            }
        }

//        viewModel.loading.observe(this) { isLoading ->
//            if (isLoading) {
//                viewBinding.flipperButtonLogin.displayedChild = 1
//            } else {
//                viewBinding.flipperButtonLogin.displayedChild = 0
//            }
//        }
//
        loginViewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_authFragment)
            }
        }
//
        loginViewModel.error.observe(viewLifecycleOwner) { throwable ->
            Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
        }

    }


}