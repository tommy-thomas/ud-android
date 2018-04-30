package org.udandroid.bakingapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.udandroid.bakingapp.model.Recipe;

/**
 * Created by tommy-thomas on 4/14/18.
 */
@Dao
public interface RecipeDAO {

    @Query("SELECT distinct * FROM recipes order by id asc")
    Recipe[] getAll();

    @Query("SELECT * FROM recipes where name = :name limit 1")
    Recipe findByName(String name);

    @Query("SELECT COUNT(*) from recipes")
    int countRecipes();

    @Query("SELECT * from recipes order by date desc limit 1")
    Recipe getRecent();

    @Query("DELETE from recipes")
    void clearRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);

    @Update
    void update(Recipe recipe);

}
