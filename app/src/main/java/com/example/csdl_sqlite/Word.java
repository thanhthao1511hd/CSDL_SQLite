package com.example.csdl_sqlite;

public class Word {
    int id;
    String word , mean;

    public Word(int id, String word, String mean) {
        this.id = id;
        this.word = word;
        this.mean = mean;
    }

    public Word() {
    }

    @Override
    public String toString() {
        return
                "id=" + id +'\n'+
                "word=" + word + '\n' +
                "mean=" + mean + '\n' ;
    }

    public Word(String book, String s) {
        this.word = book;
        this.mean = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
