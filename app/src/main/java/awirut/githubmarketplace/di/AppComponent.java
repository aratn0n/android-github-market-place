package awirut.githubmarketplace.di;

import awirut.githubmarketplace.home.presenter.HomePresenter;
import dagger.Component;

@Component(modules = { AppModule.class })
@DaggerScope.ApplicationScope
public interface AppComponent {
    HomePresenter homePresenter();
}
