package binar.academy.kelompok6.tripie_buyer.view.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding : FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        startSplashScreen()
    }

    private fun startSplashScreen() {
        sharedPref.getStatus.asLiveData().observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                if (it == false){
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_onboardingFragment2)
                }else{
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_homeFragment)
                }
            }, 1000)
        }
    }

//    private fun checkUser(){
//        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){
//            Handler(Looper.getMainLooper()).postDelayed({
//                if(it == "Undefined"){
//                    startSplashScreen()
//                }else{
//                    Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_homeFragment)
//                }
//            }, 1000)
//        }
//    }
}