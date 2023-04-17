package binar.academy.kelompok6.tripie_buyer.view.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentEditProfileBinding
import binar.academy.kelompok6.tripie_buyer.view.profile.adapter.UriToFile
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    lateinit var sharedPref: SharedPref
    private val viewModel : ViewModelProfile by viewModels()
    companion object {
        const val REQUEST_CODE_PERMISSIONS = 101

        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var getFile: File? = null
    private val imagePic =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri : Uri? ->
            if (uri != null) {
                val img = UriToFile(requireContext()).getImageBody(uri)
                getFile = img
                binding.ivProfile.setImageURI(uri)
            }
        }

    private var permissionRequestCount: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        // Make sure the app has correct permissions to run
        requestPermissionsIfNecessary()

        // When activity is reloaded after configuration change
        savedInstanceState?.let {
            // Restore the permission request count
            permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val argsName = arguments?.getString("name")
        val argsEmail = arguments?.getString("email")
        val argsPhone= arguments?.getString("phone")
        val argsGambar = arguments?.getString("gambar")
        val argsAddress = arguments?.getString("address")

        binding.editTextNama.setText(argsName)
        binding.editTextEmail.setText(argsEmail)
        binding.editTextNotelp.setText(argsPhone)
        binding.editTextAlamat.setText(argsAddress)

        if (argsGambar != null){
            Glide.with(this)
                .load(argsGambar)
                .placeholder(R.drawable.shape_round_imageview)
                .into(binding.ivProfile)
        }

        binding.btnUbahData.setOnClickListener{
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) {
                if (it != "Undefined"){
                    val name = binding.editTextNama.text.toString()
                    val email = binding.editTextEmail.text.toString()
                    val phone = binding.editTextNotelp.text.toString()
                    val address = binding.editTextAlamat.text.toString()
                    val password = binding.editTextPassword.text.toString()
                    val confirmPassword = binding.editTextConfirmPassword.text.toString()

                    if (password != confirmPassword){
                        Toast.makeText(requireContext(), "Password tidak sama!", Toast.LENGTH_SHORT).show()
                    }else{
                        if (getFile != null) {
                            val requestFile = getFile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                                "Foto",
                                getFile!!.name,
                                requestFile
                            )

                            viewModel.updateProfile(
                                it.toInt(),
                                name.toRequestBody("multipart/form-data".toMediaType()),
                                email.toRequestBody("multipart/form-data".toMediaType()),
                                confirmPassword.toRequestBody("multipart/form-data".toMediaType()),
                                imageMultipart,
                                address.toRequestBody("multipart/form-data".toMediaType()),
                                phone.toRequestBody("multipart/form-data".toMediaType())
                            )
                            viewModel.updateLiveDataProfile().observe(viewLifecycleOwner) { response ->
                                when (response) {
                                    is ApiResponse.Success -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(
                                            requireContext(),
                                            "Berhasil Update Profile",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d("Success", response.toString())
                                        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                                    }
                                    is ApiResponse.Error -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(
                                            requireContext(),
                                            "Gagal Update Profile",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d("Error", "${response.msg}")
                                    }
                                    is ApiResponse.Loading -> {
                                        binding.progressBar.visibility = View.VISIBLE
                                        Log.d("Loading", response.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.ivProfile.setOnClickListener {
            requireActivity().intent.type = "image/*"
            imagePic.launch("image/*")
        }
    }

    //Request Permissions
    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                // When the number of permission request retried is less than the max limit set
                permissionRequestCount += 1 // Increment the number of permission requests done
                // Request the required permissions for external storage access
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissions,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                // Disable the "Select Image" button when access is denied by the user
                binding.ivProfile.isEnabled = false
                // When the number of permission request retried exceeds the max limit set
                // Show a toast about how to update the permission for storage access
                Toast.makeText(
                    context,
                    "Go to Settings -> Apps and Notifications -> Flight Go Admin -> App Permissions and grant access to Storage.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        // Boolean state to indicate all permissions are granted
        var hasPermissions = true
        // Verify all permissions are granted
        for (permission in permissions) {
            hasPermissions = hasPermissions && isPermissionGranted(permission)
        }
        // Return the state of all permissions granted
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(requireContext(), permission) ==
            PackageManager.PERMISSION_GRANTED
}