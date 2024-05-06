package com.marco.ensominaearser.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marco.ensominaearser.R
import com.marco.ensominaearser.databinding.FragmentSplashBinding

import com.marco.ensominaearser.utilites.Constants
import com.marco.ensominaearser.utilites.PreferenceManager
import com.marco.ensominaearser.viewmodel.AuthViewModel
import com.marco.ensominaearser.viewmodel.ViewModelFactory


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var preference:PreferenceManager
    private val authViewModel by lazy {
        val homeViewModelProvider = ViewModelFactory(requireActivity().application)
        ViewModelProvider(this,homeViewModelProvider)[AuthViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSplashBinding.inflate(layoutInflater)
        preference = PreferenceManager(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            setAnimation()
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        return binding.root
    }
    private fun checkOnBoardingState(){
        Log.d("state","checking")
        Handler().postDelayed({
            if (preference.getBoolean(Constants.ONBOARDING_STATE)&&authViewModel.getCurrentUser()!=null)
            {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
            else if (preference.getBoolean(Constants.ONBOARDING_STATE) &&authViewModel.getCurrentUser()==null){
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
            else
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        },3000)


    }

    private fun setAnimation() {
        val appName: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.app_name)
        val description: Animation = AnimationUtils.loadAnimation(requireContext(),
            R.anim.description
        )
        binding.descTv.startAnimation(description)
        binding.appNameTv.startAnimation(appName)
    }

}