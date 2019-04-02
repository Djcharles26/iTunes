package com.custom.controller;

import com.custom.Database.DBManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        DBManager.getSharedInstance().folderWriter("/Volumes/BACKK/musica/pop/pop 2");

        for (Cancion cancion : DBManager.getSharedInstance().read()) {
            System.out.println("NOMBRE: " + cancion.id3v.nombre);
            System.out.println("ALBUM: " + cancion.id3v.album);
            System.out.println("GENERO: " + cancion.id3v.genre);
            System.out.println("ARTIST: " + cancion.id3v.artist);
        }


    }
}
