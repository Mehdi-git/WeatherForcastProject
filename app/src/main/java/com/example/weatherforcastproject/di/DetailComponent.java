package com.example.weatherforcastproject.di;

import com.example.weatherforcastproject.feature.detail.DetailView;
import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = {DetailModule.class})

public interface DetailComponent {
    void injectDetailView(DetailView activity);

}
