package es.webandroid.marvel.presentation.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val BLOCK_SIZE = 13

class HeroListScrollListener(
    private val heroListScrollerCallback: HeroListScrollerCallback
) : RecyclerView.OnScrollListener() {

    private var loading = false
    private var offset = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!loading && shouldLoadMoreHero(recyclerView)) {
            moreHeroes()
            loading = true
        }
    }

    private fun shouldLoadMoreHero(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter?.itemCount == 0) return false
        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition()
        return (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter?.itemCount?.minus(1))
    }

    private fun moreHeroes() {
        offset = if (offset == 0) BLOCK_SIZE else offset + BLOCK_SIZE
        heroListScrollerCallback.loadMoreHeroes(offset)
    }

    fun loadingCompleted() {
        loading = false
    }

    fun loadingStatus() = loading
}
