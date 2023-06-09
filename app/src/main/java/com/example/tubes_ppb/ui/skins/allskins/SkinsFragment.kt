package com.example.tubes_ppb.ui.skins.allskins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubes_ppb.databinding.FragmentSkinsBinding
import com.example.tubes_ppb.ui.skins.adapter.SkinAdapter
import com.example.tubes_ppb.data.Result

class SkinsFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentSkinsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSkinsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: SkinsViewModel by viewModels {
            factory
        }

        val newsAdapter = SkinAdapter { skins ->
            if (skins.isBookmarked){
                viewModel.deleteSkins(skins)
            } else {
                viewModel.saveSkins(skins)
            }
        }

        if (tabName == TAB_SKINS) {
            viewModel.getHeadlineSkins().observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        } else if (tabName == TAB_BOOKMARK) {
            viewModel.getBookmarkedSkins().observe(viewLifecycleOwner, { bookmarkedSkins ->
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(bookmarkedSkins)
            })
        }

        binding?.recyclerViewSkin?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_SKINS = "skins"
        const val TAB_BOOKMARK = "bookmark"
    }
}