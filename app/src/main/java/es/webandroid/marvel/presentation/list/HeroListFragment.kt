package es.webandroid.marvel.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.marvel.core.platform.BaseFragment
import es.webandroid.marvel.core.platform.ConsumingObserver
import es.webandroid.marvel.databinding.FragmentHeroListBinding
import es.webandroid.marvel.presentation.list.HeroListViewModel.Event

@AndroidEntryPoint
class HeroListFragment : BaseFragment(), HeroListScrollerCallback {

    private val viewModel by viewModels<HeroListViewModel>()

    private lateinit var adapter: HeroListAdapter
    private val heroListScrollListener by lazy { HeroListScrollListener(this) }

    private var _binding: FragmentHeroListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHeroListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null && viewModel.listState == null) {
            viewModel.getHeroes()
        } else {
            viewModel.reloadLocalData()
        }

        configureView()
        viewModel.event.observe(viewLifecycleOwner, ConsumingObserver(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun configureView() {
        adapter = HeroListAdapter(viewModel::onHeroClicked)
        binding.recycler.adapter = adapter
        binding.recycler.addOnScrollListener(heroListScrollListener)
    }

    private fun updateUi(event: Event) {
        binding.progress.visibility = if (event is Event.Loading) View.VISIBLE else View.GONE

        when (event) {
            is Event.Content -> {
                adapter.submitList(event.heroes)
                if (heroListScrollListener.loadingStatus()) {
                    adapter.notifyDataSetChanged()
                    heroListScrollListener.loadingCompleted()
                }
                onRestoreStateRecycler()
            }
            is Event.Detail -> {
                val action = HeroListFragmentDirections.navigationToHeroDetailFragment(event.heroId)
                view?.findNavController()?.navigate(action)
            }
        }
    }

    private fun onRestoreStateRecycler() {
        if (viewModel.listState != null) {
            binding.recycler.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        }
    }

    override fun loadMoreHeroes(offset: Int) = viewModel.getHeroes(offset)

    override fun onStop() {
        super.onStop()
        binding.recycler.removeOnScrollListener(heroListScrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.listState = binding.recycler.layoutManager?.onSaveInstanceState()
        _binding = null
    }
}
