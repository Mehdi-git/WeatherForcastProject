package com.example.weatherforcastproject.di;

import com.example.weatherforcastproject.feature.detail.DetailView;
import com.example.weatherforcastproject.feature.search.SearchView;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {SearchModule.class})


    public interface SearchComponent {
        void injectSearchView(SearchView activity);

    }



