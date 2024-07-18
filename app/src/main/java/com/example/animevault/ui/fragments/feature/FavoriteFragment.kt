package com.example.animevault.ui.fragments.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animevault.R
import com.example.animevault.databinding.FragmentFavoriteBinding
import com.example.animevault.ui.adapter.AnimeAdapter
import com.example.animevault.ui.adapter.AnimeAdapterListener
import com.example.animevault.ui.viewholder.FragmentType
import com.example.domain.model.Anime
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), AnimeAdapterListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val animeAdapter = AnimeAdapter(this,  FragmentType.FAVORITE)
    private val viewModel by viewModel<FavoriteFragmentViewModel>()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupAnimeRV()

        // Update BottomNavigationView selected item
        sharedViewModel.setSelectedNavItemId(R.id.nav_favorite)

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_logout -> {
                        viewModel.logout()
                        activity?.finish()
                        true
                    }
                    R.id.action_account -> {
                        findNavController().navigate(R.id.action_authFragment_to_profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupAnimeRV() {
        binding.rvAnimeList.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getMovieFromLocal()

        viewModel.animes.observe(viewLifecycleOwner) { animes ->
            animeAdapter.submitList(animes)
        }
    }

    override fun onClickFavButton(data: Anime) {
        viewModel.loadAnimeFromFavorite(data.id)
        viewModel.deleteAnimeFromFavorite(data)
        viewModel.getMovieFromLocal()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
