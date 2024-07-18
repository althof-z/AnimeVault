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
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animevault.R
import com.example.animevault.databinding.FragmentHomeBinding
import com.example.animevault.ui.fragments.auth.AuthFragment
import com.example.animevault.ui.adapter.AnimeAdapterListener
import com.example.animevault.ui.adapter.AnimeHomeAdapter
import com.example.domain.model.Anime
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), AnimeAdapterListener {
    private lateinit var binding: FragmentHomeBinding
    private val animeHomeAdapter = AnimeHomeAdapter(this)

    private val viewModel by viewModel<HomeFragmentViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            animeHomeAdapter.submitList(animes)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.ivHighlightAnime.load("https://wallpapers.com/images/hd/fullmetal-alchemist-brotherhood-action-poster-0tvvnvos36llm4yx.webp")
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.btnSeeAll.setOnClickListener {
            setFragment(AnimeFragment())
        }
        binding.btnCrash.setOnClickListener{
            triggerCrash()
        }
    }

    private fun setFragment(fragment: Fragment) {
        (parentFragment as? AuthFragment)?.setFragment(fragment)
    }


    private fun setupAnimeRV(){
        binding.rvAnimeHome.apply {
            adapter = animeHomeAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onClickFavButton(data: Anime) {
    }

    private fun triggerCrash() {
        throw RuntimeException("test crashlytics")
    }

}