package binar.academy.kelompok6.tripie_buyer.view.home.searchfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.model.PlaneClass
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentListPlaneClassBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PlaneClassAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.PlaneClassViewModel

class ListPlaneClassFragment : Fragment(), PlaneClassAdapter.PlaneClassInterface {
    private var _binding : FragmentListPlaneClassBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : PlaneClassAdapter
    private val vmPlaneClass : PlaneClassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPlaneClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setPlaneClass()
    }

    private fun setPlaneClass() {
        adapter = PlaneClassAdapter(this)

        vmPlaneClass.getPlaneClass()
        vmPlaneClass.planeClassList.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        binding.apply {
            rvListPlaneClass.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvListPlaneClass.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(planeClass: PlaneClass) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "planeClassName",
            planeClass.kelasPesawat
        )
        findNavController().navigateUp()
    }
}