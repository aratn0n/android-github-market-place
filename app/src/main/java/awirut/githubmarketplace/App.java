package awirut.githubmarketplace;

import android.app.Application;

import awirut.githubmarketplace.di.AppComponent;
import awirut.githubmarketplace.di.AppModule;
import awirut.githubmarketplace.di.DaggerAppComponent;
import timber.log.Timber;

public class App extends Application {
    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        createAppComponent();
        plantTimber();
    }

    public static App getInstance()
    {
        return instance;
    }

    private void createAppComponent()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void plantTimber()
    {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }
}
