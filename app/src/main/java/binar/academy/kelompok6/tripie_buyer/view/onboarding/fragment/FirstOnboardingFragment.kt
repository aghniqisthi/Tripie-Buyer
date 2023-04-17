package binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentFirstOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstOnboardingFragment : Fragment() {

    private lateinit var binding : FragmentFirstOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFirstOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.line1.progress = 0
        binding.line1.max = 5000
        val currentProgress = 5000
        ObjectAnimator.ofInt(binding.line1, "progress", currentProgress).setDuration(5000).start()

//        Handler().postDelayed({
//            ViewPager.PageTransformer({view, position -> 1}).transformPage(view,2F)
//                              //navigasi ke next fragment dalam viewpager
//        }, 5000)

        binding.txtSkipFirst.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_onboardingFragment2_to_homeFragment2)
        }

//        binding.btnNextFirst.setOnClickListener {
//            OnboardingAdapter(requireParentFragment()).createFragment(1).requireView()
//        }
    }
}
