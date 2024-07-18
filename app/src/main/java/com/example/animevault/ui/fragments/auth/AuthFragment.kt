package com.example.animevault.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.animevault.R
import com.example.animevault.databinding.FragmentAuthBinding
import com.example.animevault.ui.fragments.feature.AnimeFragment
import com.example.animevault.ui.fragments.feature.FavoriteFragment
import com.example.animevault.ui.fragments.feature.SharedViewModel
import com.example.animevault.ui.fragments.feature.HomeFragment

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBottomNavigation()
        binding.bottomNav.selectedItemId = R.id.nav_home

        sharedViewModel.selectedNavItemId.observe(viewLifecycleOwner) { itemId ->
            if (binding.bottomNav.selectedItemId != itemId) {
                binding.bottomNav.selectedItemId = itemId
            }
        }
    }

    fun  setBottomNavSelectedItem(itemId: Int) {
        binding.bottomNav.selectedItemId = itemId
    }

    fun setFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun handleBottomNavigation() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setFragment(HomeFragment())
                    true
                }
                R.id.nav_list -> {
                    setFragment(AnimeFragment())
                    true
                }
                R.id.nav_favorite -> {
                    setFragment(FavoriteFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
