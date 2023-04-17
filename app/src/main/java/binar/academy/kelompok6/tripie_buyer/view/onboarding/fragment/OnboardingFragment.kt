package binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.view.onboarding.adapter.OnboardingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.viewPager.adapter = OnboardingAdapter(this)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.btnNextFirst.setOnClickListener {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                                      }, 2000)

                if (binding.viewPager.currentItem == 2) {
                    binding.btnNextFirst.text = "Mulai"
                    binding.btnNextFirst.setOnClickListener{
                        statusTrue()
                        Navigation.findNavController(view).navigate(R.id.action_onboardingFragment2_to_homeFragment2)
                    }
                }
                else{
                    binding.btnNextFirst.text = "Selanjutnya"
                }
            }

        })
    }

    private fun statusTrue() {
        CoroutineScope(Dispatchers.IO).launch{
            sharedPref.saveStatus(true)
        }
    }

}