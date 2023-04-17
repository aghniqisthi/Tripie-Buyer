package binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentThirdOnboardingBinding

class ThirdOnboardingFragment : Fragment() {
    private lateinit var binding : FragmentThirdOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentThirdOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.line3.max = 5000
        val currentProgress = 5000
        ObjectAnimator.ofInt(binding.line3, "progress", currentProgress).setDuration(5000).start()

//        binding.line3.setProgress(5000)
//        if(binding.line3.progress >= 5000){
//            Navigation.findNavController(view).navigate(R.id.action_thirdOnboardingFragment_to_homeFragment)
//        }

//        binding.btnGetStarted.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_onboardingFragment2_to_homeFragment2)
//        }
    }
}