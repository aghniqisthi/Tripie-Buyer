package binar.academy.kelompok6.tripie_buyer.view.authentication.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.request.GoogleAuthRequest
import binar.academy.kelompok6.tripie_buyer.data.model.request.LoginRequest
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentLoginBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant.Companion.LOGIN_SUCCESSFUL
import binar.academy.kelompok6.tripie_buyer.view.authentication.viewmodel.AuthenticationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPref
    private val auth : AuthenticationViewModel by viewModels()
//    private lateinit var firebaseAuth : FirebaseAuth
//    private lateinit var mGoogleSignInClient : GoogleSignInClient
//    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
//        savedStateHandle[LOGIN_SUCCESSFUL] = false

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .requestProfile()
//            .build()

//        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnMasuk.setOnClickListener {
            if (binding.editTextEmail.text.toString().isEmpty() || binding.editTextPass.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                reqLogin()
            }
        }

        binding.btnBuatAkun.setOnClickListener {
            //masuk fragment register
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

//        binding.btnLoginWithGoogle.setOnClickListener {
//            loginWithGoogleAcc()
//        }
    }

//    private fun loginWithGoogleAcc() {
//        val intent = mGoogleSignInClient.signInIntent
//        resultLauncher.launch(intent)
//    }
//
//    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        if (it.resultCode == Activity.RESULT_OK){
//            val task : Task<GoogleSignInAccount> =
//                GoogleSignIn.getSignedInAccountFromIntent(it.data)
//            handleResult(task)
//        }
//    }
//
//    private fun handleResult(task: Task<GoogleSignInAccount>) {
//        try {
//            val account = task.getResult(ApiException::class.java)
//            if (account != null){
//                updateUI(account)
//            }
//        }catch (e : ApiException){
//            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun updateUI(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful){
////                saveToken(account.idToken.toString(), account.id.toString(),
////                    account.displayName.toString(), true, account.photoUrl.toString(), account.email.toString())
//                authGoogle(account)
//                Log.d("Token Google", account.idToken.toString())
//                Log.d("Token Google", account.displayName.toString())
//                Log.d("Token Google", account.email.toString())
//                Log.d("Token Google", account.id.toString())
//            }else{
//                Toast.makeText(requireContext(), "Login Gagal!!", Toast.LENGTH_SHORT).show()
//                Log.d("Error", it.result.toString())
//            }
//        }
//    }
//
//    private fun authGoogle(account: GoogleSignInAccount) {
//        auth.loginGoogle(GoogleAuthRequest(account.idToken.toString(), account.email.toString(), account.displayName.toString()))
//        auth.goggleAuthObserver().observe(viewLifecycleOwner){ response ->
//            when(response){
//                is ApiResponse.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//                is ApiResponse.Success -> {
//                    binding.progressBar.visibility = View.GONE
//                    response.data?.let { getGoogleUser(it.accessToken) }
//                }
//                is ApiResponse.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    private fun getGoogleUser(accessToken: String) {
//        auth.getGoogleUser(accessToken)
//        auth.getGoogleUserObserver().observe(viewLifecycleOwner){ response ->
//            when(response){
//                is ApiResponse.Loading -> {
//                    Log.d("Loading", response.toString())
//                }
//                is ApiResponse.Success -> {
//                    response.data?.data?.let {
//                        saveToken(accessToken, it.id.toString(), it.name)
//                    }
//                    Toast.makeText(requireContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show()
//                    Log.d("Success", response.toString())
//                    savedStateHandle[LOGIN_SUCCESSFUL] = true
//                    findNavController().popBackStack()
//                }
//                is ApiResponse.Error -> {
//                    Log.d("Error", response.msg.toString())
//                }
//            }
//        }
//    }

    private fun reqLogin() {
        auth.loginUser(LoginRequest(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPass.text.toString()
        ))
        auth.ambilLiveDataUser().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("Loading", it.toString())
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    saveToken(it.data!!.token,it.data.id.toString(), it.data.name)
                    Toast.makeText(requireContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    Log.d("Success", it.toString())
//                    savedStateHandle[LOGIN_SUCCESSFUL] = true

                    if (findNavController().currentDestination?.id != R.id.profileFragment) {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToProfileFragment()
                        )
                    }
//                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
//                    savedStateHandle[LOGIN_SUCCESSFUL] = false
                    Log.d("Error", it.msg.toString())
                }
            }
        }
    }

    private fun saveToken(token:String, idUser:String, username : String) {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.saveToken(token, idUser, username)
        }
    }

}
