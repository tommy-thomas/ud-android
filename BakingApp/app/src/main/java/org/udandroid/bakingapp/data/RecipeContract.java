package org.udandroid.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tommy-thomas on 4/9/18.
 */

public class RecipeContract {


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "org.udandroid.bakingapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_INGREDIENT_BUNDLE = "ingredient_bundle";

    }
}
