package es.webandroid.marvel.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.webandroid.marvel.core.extensions.loadFromUrl
import es.webandroid.marvel.databinding.HeroRowBinding
import es.webandroid.marvel.domain.entities.Hero
import es.webandroid.marvel.domain.entities.PortraitType

class HeroListAdapter(val onClickListener: (heroId: Long) -> Unit) : ListAdapter<Hero, HeroListAdapter.ViewHolder>(HeroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HeroRowBinding = HeroRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = getItem(position)
        holder.bindTo(hero)
        holder.itemView.setOnClickListener { onClickListener(hero.id) }
    }

    class HeroDiffCallback : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Hero, newItem: Hero) = oldItem == newItem
    }

    inner class ViewHolder(private val binding: HeroRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(hero: Hero) {
            hero.apply {
                binding.tvTitle.text = name
                binding.ivImage.loadFromUrl(getPhoto(PortraitType.UNCANNY))
            }
        }
    }
}
