package szeptunm.corner.dataaccess.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Single
import szeptunm.corner.dataaccess.database.entity.Team

@Dao
interface TeamDao {

    @Delete
    fun delete(vararg team: Team)

    @Update
    fun update(vararg team: Team)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTeams(teamList: List<Team>)

    @Query("SELECT * FROM team")
    fun getAllTeams(): Single<List<Team>>

    @Query("SELECT * FROM team WHERE id LIKE :id")
    fun getTeamById(id: Int): Single<Team>

    @Query("SELECT * FROM team WHERE name LIKE:club")
    fun getTeamByName(club: String): Single<Team>
}