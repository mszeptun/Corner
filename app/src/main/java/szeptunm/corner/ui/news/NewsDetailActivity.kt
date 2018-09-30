package szeptunm.corner.ui.news

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import szeptunm.corner.R
import szeptunm.corner.R.layout
import szeptunm.corner.databinding.NewsDetailsBinding
import szeptunm.corner.entity.News
import szeptunm.corner.ui.BaseActivity
import szeptunm.corner.ui.news.NewsViewHolder.Companion.KEY_NEWS

class NewsDetailActivity : BaseActivity() {
    override val layoutResource: Int
        get() = R.layout.news_details

    lateinit var binding: NewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.news_details)
        binding = DataBindingUtil.setContentView(this, R.layout.news_details)
        val news = intent.getParcelableExtra(KEY_NEWS) as News
        binding.newsDetailsDescription.text = news.description
        setToolbar()
        setImage(news)
    }

    private fun setImage(news: News) {
        val requestOptions = RequestOptions()
                .fitCenter()
        Glide.with(binding.root).load(news.photoUrl).apply(requestOptions).into(binding.newsDetailsPhoto)
    }

    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null;
        toolbar.setNavigationIcon(R.drawable.ic_close_white)
        toolbar.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}