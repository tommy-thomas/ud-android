package org.udandroid.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_ID;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_INGREDIENT_BUNDLE;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.TABLE_NAME;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry._ID;

/**
 * Created by tommy-thomas on 4/9/18.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    RecipeDBHelper(Context context){ super(context, DATABASE_NAME, null, VERSION);}

    private static final String DATABASE_NAME = "recipes.db";

    private static final int VERSION = 2;

    //Add alter statement with schema update.
    private static final String DATABASE_ALTER = "";

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create favorites table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ID + " TEXT NOT NULL, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_INGREDIENT_BUNDLE + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion > oldVersion){
            db.execSQL(DATABASE_ALTER);
        }
    }
}
