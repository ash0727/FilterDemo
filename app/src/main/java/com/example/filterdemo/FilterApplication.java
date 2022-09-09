package com.example.filterdemo;

import android.app.Application;
import android.content.Context;

import com.example.filterdemo.repository.FilterRepository;

import java.util.logging.Filter;

public class FilterApplication extends Application {

    private FilterRepository repository;
    private static FilterApplication instance;
    public static Context appLevelContext;
    public static FilterApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appLevelContext = getApplicationContext();
        repository = new FilterRepository(getApplicationContext());
    }

    public FilterRepository getFilterRepository(){
        return repository;
    }
}
