package com.ondarm.android.newsreaders.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.ondarm.android.newsreaders.listeners.OnNewsClickListener
import com.ondarm.android.newsreaders.R
import com.ondarm.android.newsreaders.data.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.view.*


class NewsListAdapter(
    private val mContext: Context,
    var items: List<News>
): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private lateinit var mOnNewsClickListener: OnNewsClickListener

    fun setOnNewsClickListener(listener: OnNewsClickListener) {
        this.mOnNewsClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.item_news, parent, false)

        // 뉴스 아이템 높이 설정
        val itemHeight = 0.5 // 1 = 100%
        val layoutParams = itemView.layoutParams
        layoutParams.height = (parent.height * itemHeight).toInt()
        itemView.layoutParams = layoutParams

//        itemView.news_image.alpha = 0.7f

        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]

        holder.containerView.setOnClickListener {
            mOnNewsClickListener.onNewsClick(position)
        }
        holder.containerView.news_title.text = item.title
        Glide
            .with(mContext)
            .load(item.image)
            .error(R.drawable.fakenews)
            .into(holder.containerView.news_image)
        holder.containerView.news_description.text = item.description
    }

    class NewsViewHolder(
        override val containerView: View
    ): RecyclerView.ViewHolder(containerView), LayoutContainer



}


