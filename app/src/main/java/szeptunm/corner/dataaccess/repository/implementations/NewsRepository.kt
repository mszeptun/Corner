package szeptunm.corner.dataaccess.repository.implementations

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import szeptunm.corner.BuildConfig
import szeptunm.corner.dataaccess.api.model.NewsResponse
import szeptunm.corner.dataaccess.api.service.NewsService
import szeptunm.corner.dataaccess.database.DatabaseTransaction
import szeptunm.corner.dataaccess.database.dao.NewsDao
import szeptunm.corner.dataaccess.database.entity.NewsEntity
import szeptunm.corner.entity.News
import javax.inject.Inject

class NewsRepository @Inject constructor(var newsDao: NewsDao, var newsService: NewsService,
        var databaseTransaction: DatabaseTransaction) {

    private fun newsTransformer(): SingleTransformer<List<NewsEntity>, List<News>> {
        return SingleTransformer { upstream ->
            upstream.flattenAsObservable { list -> list }
                    .map { News(it) }
                    .toList()
        }
    }

    fun getAllNews(): Single<List<News>> {
        return newsDao.getAllNews().compose(newsTransformer())
    }

    private fun getAllNewsFromApi(): Single<NewsResponse> {
        return newsService.getAllNews(BuildConfig.NEWS_API_HTTP_URL, BuildConfig.NEWS_KEY)
    }


    private fun mapResponseToEntity(newsResponse: NewsResponse): List<NewsEntity> {
        val newsList: MutableList<NewsEntity> = ArrayList()
        for (i in 0..newsResponse.items.size) {
            newsResponse.items[i].let {
                newsList.add(NewsEntity(it.title, it.description, it.pubDate, it.enclosure.imageURL, it.link, 0))
            }
        }
        return newsList
    }

    private fun saveToDatabase(newsList: List<NewsEntity>): Completable {
        return databaseTransaction.runTransaction(
                Runnable { newsDao.insertAllNews(newsList) })
    }

    fun updateNewsDatabase(): Completable {
        return getAllNewsFromApi()
                .map(::mapResponseToEntity)
                .flatMapCompletable(::saveToDatabase)
    }

}
