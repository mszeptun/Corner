package szeptunm.corner.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import szeptunm.corner.dataaccess.database.entity.NewsEntity

@Parcelize
data class News(var id: Int = 0,
        var title: String = "",
        var description: String = "",
        var date: String = "",
        var photoUrl: String = "",
        var teamId: Int = 0) : Parcelable {

    constructor(newsEntity: NewsEntity) : this() {
        this.id = newsEntity.id
        this.title = newsEntity.title
        this.description = newsEntity.description
        this.date = newsEntity.date
        this.photoUrl = newsEntity.photoUrl
        this.teamId = newsEntity.teamId
    }
}