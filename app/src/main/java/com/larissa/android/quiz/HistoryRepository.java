package com.larissa.android.quiz;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private static HistoryRepository repository;
    public List<EandR> history;

    private HistoryRepository(Context context){
        history=new ArrayList<>();
    }

    public static HistoryRepository getInstance(){
        if(repository==null){
            throw new IllegalStateException("HistoryRepository must be initialized");
        }
        return repository;
    }

    public static void initialize(Context context){
        if(repository==null)
            repository= new HistoryRepository(context);
    }
}
