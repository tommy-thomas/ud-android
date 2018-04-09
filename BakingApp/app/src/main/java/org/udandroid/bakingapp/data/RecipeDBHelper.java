package org.udandroid.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        final String CREATE_TABLE = "CREATE TABLE "  + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY, " +
                RecipeContract.RecipeEntry.COLUMN_ID + " TEXT NOT NULL, " +
                RecipeContract.RecipeEntry.COLUMN_INGREDIENT_BUNDLE + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion > oldVersion){
            db.execSQL(DATABASE_ALTER);
        }
    }
}
