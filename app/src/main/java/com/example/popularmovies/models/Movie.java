package com.example.popularmovies.models;

/**
 * Created by claudioguerra on 8/20/17.
 */

public class Movie {
    private String mName;
    public Movie(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        if(name != null && !name.equals(""))
            mName = name;
    }
}
