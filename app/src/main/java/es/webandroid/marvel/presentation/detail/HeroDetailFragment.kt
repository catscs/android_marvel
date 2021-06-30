package es.webandroid.marvel.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.marvel.core.extensions.loadFromUrl
import es.webandroid.marvel.core.platform.BaseFragment
import es.webandroid.marvel.core.platform.MainActivity
import es.webandroid.marvel.databinding.FragmentHeroDetailBinding
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.entities.LandscapeType
import es.webandroid.marvel.presentation.detail.HeroDetailViewModel.Event

@AndroidEntryPoint
class HeroDetailFragment : BaseFragment() {

    private val viewModel by viewModels<HeroDetailViewModel>()

    private var _binding: FragmentHeroDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<HeroDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getHeroDetail(args.heroId)
        }

        viewModel.event.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun updateUi(event: Event) {
        binding.progress.visibility = if (event is Event.Loading) View.VISIBLE else View.GONE
        if (event is Event.Content) updateDetail(event.hero)
    }

    private fun updateDetail(hero: Hero) {
        hero.apply {
            (activity as MainActivity).supportActionBar?.title = name
            binding.ivImage.loadFromUrl(getPhoto(LandscapeType.INCREDIBLE))
            binding.tvDescription.text = description
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
