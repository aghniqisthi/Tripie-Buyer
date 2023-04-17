package binar.academy.kelompok6.tripie_buyer.view.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentProfileBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val navController = findNavController()
//
//        val currentBackStackEntry = navController.currentBackStackEntry!!
//        val savedStateHandle = currentBackStackEntry.savedStateHandle
//        savedStateHandle.getLiveData<Boolean>(Constant.LOGIN_SUCCESSFUL)
//            .observe(currentBackStackEntry){ success ->
//                if (!success){
//                    val startDestination = navController.graph.startDestinationId
//                    val navOptions = NavOptions.Builder()
//                        .setPopUpTo(startDestination, false, saveState = true)
//                        .build()
//                    navController.navigate(startDestination, null, navOptions)
//                }
//            }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .requestProfile()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.navigateToNotificaiton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
        }

        binding.btnLogout.setOnClickListener{
            clearData()
        }

        binding.navigateToProfile.setOnClickListener {
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ id ->
                viewModel.getProfile(id.toInt())
            }
            viewModel.getLiveDataProfile().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading", it.toString())
                    }
                    is ApiResponse.Success -> {
                        response.data?.data?.let{ user ->
                            val bundle = Bundle()
                            bundle.putInt("id", user.id)
                            bundle.putString("name", user.name)
                            bundle.putString("email", user.email)
                            bundle.putString("phone", user.phoneNumber)
                            bundle.putString("gambar", user.foto)
                            bundle.putString("address", user.address)
                            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
                        }
                    }
                    is ApiResponse.Error -> {
                        Log.d("Error", response.msg.toString())
                    }
                }
            }
        }
        checkUser()
    }

    private fun clearData(){
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.removeToken()

//            if (GoogleSignIn.getLastSignedInAccount(requireContext()) != null){
//                mGoogleSignInClient.signOut()
//            }
        }
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun showDataUser(){
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ idUser ->
            viewModel.getProfile(idUser.toInt())
        }

        viewModel.getLiveDataProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading", response.toString())
                }
                is ApiResponse.Success -> {
                    response.data?.data?.let{ user ->
                        Glide.with(this)
                            .load(user.foto)
                            .placeholder(R.drawable.shape_round_imageview)
                            .into(binding.circleImageView2)
                        binding.tvNamaUser.text = user.name
                        binding.tvEmailUser.text = user.email
                    }
                }
                is ApiResponse.Error -> {
                    Log.d("Error", response.msg.toString())
                }
            }
        }
    }

    private fun checkUser() {
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner) { token ->
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) { idUser ->
                if (token == "Undefined" || idUser == "Undefined") {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
//                Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
                else {
                    showDataUser()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}