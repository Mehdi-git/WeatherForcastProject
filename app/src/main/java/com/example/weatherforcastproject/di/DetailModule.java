package com.example.weatherforcastproject.di;

import com.example.weatherforcastproject.feature.detail.Contract;
import com.example.weatherforcastproject.feature.detail.DetailPresenter;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class DetailModule {

    private final Contract.View view;

    public DetailModule(Contract.View view) {
        this.view = view;
    }

    @Singleton @Provides
    public Contract.View provideDetailView(){
        return view;
    }

    @Singleton @Provides
    public Contract.Presenter provideDetailPresenter(Contract.View view){
        return new DetailPresenter(view);
    }
}
