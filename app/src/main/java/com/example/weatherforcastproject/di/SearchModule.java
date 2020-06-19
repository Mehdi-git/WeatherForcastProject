package com.example.weatherforcastproject.di;
import com.example.weatherforcastproject.feature.search.Contract;
import com.example.weatherforcastproject.feature.search.SearchPresenter;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class SearchModule {

        private final Contract.View view;

        public SearchModule(Contract.View view) {
            this.view = view;

        }

        @Singleton @Provides
        public Contract.View provideSearchView(){
            return view;
        }


        @Singleton @Provides
        public Contract.Presenter provideSearchPresenter(Contract.View view){
            return new SearchPresenter(view);
        }
    }


