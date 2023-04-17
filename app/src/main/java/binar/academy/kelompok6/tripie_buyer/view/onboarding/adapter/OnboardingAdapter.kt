package binar.academy.kelompok6.tripie_buyer.view.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.FirstOnboardingFragment
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.SecondOnboardingFragment
import binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment.ThirdOnboardingFragment

class OnboardingAdapter(var fragment:Fragment) : FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int): Fragment {
                return when(position){
                        0 -> FirstOnboardingFragment()
                        1 -> SecondOnboardingFragment()
                        2 -> ThirdOnboardingFragment()
                        else -> FirstOnboardingFragment()
                }
        }

        override fun getItemCount(): Int = 3

}