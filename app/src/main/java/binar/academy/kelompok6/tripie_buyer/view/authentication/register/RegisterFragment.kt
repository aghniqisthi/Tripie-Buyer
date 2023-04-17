package binar.academy.kelompok6.tripie_buyer.view.authentication.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.model.request.RegisterRequest
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentRegisterBinding
import binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val auth : AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDaftar.setOnClickListener {
            postRegister()
        }

        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun postRegister(){
        if (binding.etNamaLengkap.text.toString().isEmpty() || binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty() || binding.etKonfirPass.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            if (binding.etPassword.text.toString() == binding.etKonfirPass.text.toString()) {
                auth.registerUser(
                    RegisterRequest(
                    name = binding.etNamaLengkap.text.toString(),
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
                )
                auth.postDataRegister().observe(viewLifecycleOwner){
                    when(it){
                        is ApiResponse.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is ApiResponse.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_registerSuccessFragment)
                        }
                        is ApiResponse.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
        }
    }
}