/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.rakeshrav.magicmovies.utility;

/**
 * Created by rao.
 */

public final class AppConstants {

    public static final String DB_NAME = "magicMovies";
    public static final String SEED_DATABASE_OPTIONS = "seedDataBaseOptions";

    public static final String PREF_NAME = "magicMovies";

    public static final String GENRE_ID_DRAMA = "18";
    public static final String GENRE_ID_HORROR = "27";


    public static final long NULL_INDEX = -1L;

    public static final String TIMESTAMP_FORMAT = "dd MMM yyyy";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
