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
import com.example.animevault.databinding.FragmentAnimeBinding
import com.example.animevault.ui.adapter.AnimeAdapter
import com.example.animevault.ui.adapter.AnimeAdapterListener
import com.example.animevault.ui.fragments.auth.AuthFragment
import com.example.animevault.ui.viewholder.FragmentType
import com.example.domain.model.Anime
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeFragment : Fragment(), AnimeAdapterListener {
    private lateinit var binding: FragmentAnimeBinding
    private val animeAdapter = AnimeAdapter(this,  FragmentType.ANIME )
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel by viewModel<AnimeFragmentViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        sharedViewModel.setSelectedNavItemId(R.id.nav_list)

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
                    R.id.action_account ->{
                        findNavController().navigate(R.id.action_authFragment_to_profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.error.observe(viewLifecycleOwner){error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }

        setupAnimeRV()

        viewModel.retrieveAvailableAnimes()

        viewModel.animes.observe(viewLifecycleOwner){ animes ->
            animeAdapter.submitList(animes)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }



    }

    private fun setupAnimeRV(){
        binding.rvAnimeList.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setFragment(fragment: Fragment) {
        (parentFragment as? AuthFragment)?.setFragment(fragment)
    }

    override fun onClickFavButton(data: Anime) {
        viewModel.loadAnimeFromFavorite(data.id)
        viewModel.storeToFavorite(
            id = data.id,
            image = data.image,
            title = data.title,
            synopsis = data.synopsis,
            year = data.year,
            episode = data.episode,
            rate = data.rate
        )
        setFragment(FavoriteFragment())
    }

//    override fun onClickSearchButton(data: AnimeHome) {
//        searchAnime(data)
//    }
//
//    private fun searchAnime(data: AnimeHome) {
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setData(Uri.parse("https://www.google.com/search?q=${data.title}"))
//        }
//        startActivity(intent)
//    }


}