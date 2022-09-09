package com.example.filterdemo.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filterdemo.models.FilterDatum;
import com.example.filterdemo.models.FilterResponse;
import com.example.filterdemo.utils.Resource;
import com.example.filterdemo.utils.Variables;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FilterRepository {

    Context context;

    public FilterRepository(Context context) {
        this.context = context;
    }

    public List<FilterDatum> loadFilterFromAsset(){
        String json = null;
        List<FilterDatum> _filterList = new ArrayList<>();
        try {
            InputStream inputStream = null;
            inputStream = context.getAssets().open("testjson.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
           FilterResponse filterResponse = gson.fromJson(json, FilterResponse.class);

           if(filterResponse.getStatus().equalsIgnoreCase(Variables.RESPONSE_SUCCESS))
           {
               _filterList.addAll(filterResponse.getFilterData());
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return _filterList;
    }
}
