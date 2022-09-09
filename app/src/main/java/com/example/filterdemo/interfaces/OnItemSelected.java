package com.example.filterdemo.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface OnItemSelected
{
    void onSingleItemSelected(String id,Object object) ;
    void onMultipleItemSelected(List<String> ids, Object object) ;
}
