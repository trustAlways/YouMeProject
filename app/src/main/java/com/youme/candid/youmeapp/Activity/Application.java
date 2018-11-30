package com.youme.candid.youmeapp.Activity;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.youme.candid.youmeapp.Activity.utils.MediaLoader;

import java.util.Locale;

public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;

            Album.initialize(AlbumConfig.newBuilder(this)
                    .setAlbumLoader(new MediaLoader())
                    .setLocale(Locale.getDefault())
                    .build()
            );
        }
    }

    public static Application getInstance() {
        return instance;
    }
}
