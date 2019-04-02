package com.custom.controller;


import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Genres;

import java.io.Serializable;

public class Cancion implements Serializable {
    private double precio;
    private String URL;
    public ID3v id3v;

    public class ID3v implements Serializable{
        public String nombre;
        public String album;
        public String genre;
        public String artist;

        public ID3v(String nombre,String album, String artist, String genre) {
            this.nombre = nombre;
            this.album = album;
            this.genre = genre;
            this.artist = artist;
        }
    }

    public Cancion(double precio, String URL, ID3v1 id3v1){
        this.precio = precio;
        this.URL = URL;
        this.id3v = new ID3v(id3v1.getTitle(),id3v1.getAlbum(),id3v1.getArtist(), ID3v1Genres.GENRES[id3v1.getGenre()]);
    }

    public double getPrecio(){
        return this.precio;
    }

    public String getURL(){
        return this.URL;
    }


}
