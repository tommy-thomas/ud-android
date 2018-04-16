package org.udandroid.bakingapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tommy-thomas on 4/14/18.
 */
@Entity(tableName = "selected-recipe")
public class SelectedRecipeDAO {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    protected int id;


}
