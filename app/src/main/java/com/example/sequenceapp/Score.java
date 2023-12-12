package com.example.sequenceapp;

public class Score {
    int _id;
    String _name, _final_score;

    public Score(){}
    public Score(int id, String name, String _final_score){
        this._id = id;
        this._name = name;
        this._final_score = _final_score;
    }
    public Score(String name, String _final_score){
        this._name=name;
        this._final_score = _final_score;
    }
    public int getId(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name = name;
    }

    public String getFinalScore(){
        return this._final_score;
    }

    public void setFinalScore(String _final_score){
        this._final_score = _final_score;
    }

    @Override
    public String toString(){
        return "Name: " + getName() + " Score: " + getFinalScore();
    }
}

