package com.custom.utils;

import com.custom.controller.Cancion;
import com.mpatric.mp3agic.*;

import java.io.IOException;

public class SongUtils {
    public static ID3v1 transformId3v2toId3v1(ID3v2 Tag) {
        ID3v1 newTag = new ID3v1Tag();
        if (Tag.getAlbum() != null) newTag.setAlbum(Tag.getAlbum());
        else newTag.setAlbum("Unknown");
        if (Tag.getArtist() != null) newTag.setArtist(Tag.getArtist());
        else newTag.setArtist("Unknown");
        if (Tag.getTitle() != null) newTag.setTitle(Tag.getTitle());
        else newTag.setTitle("Unknown");
        if (Tag.getGenre() >= 0) newTag.setGenre(Tag.getGenre());
        else newTag.setGenre(12);
        return newTag;
    }

    public static ID3v1 unknowTag(){
        ID3v1 tag = new ID3v1Tag();
        tag.setGenre(12);
        tag.setTitle("Unknown");
        tag.setArtist("Unknown");
        tag.setAlbum("Unknown");
        return tag;
    }

    public static Cancion convertSong(String path) {
        try {
            Mp3File song = new Mp3File(path);
            ID3v1 tag = null;
            if(song.hasId3v1Tag()) tag = song.getId3v1Tag();
            else if(song.hasId3v2Tag())tag = transformId3v2toId3v1(song.getId3v2Tag());
            if(tag==null) tag = unknowTag();
            return  new Cancion(0,path,tag);
        } catch (InvalidDataException | IOException | UnsupportedTagException e) {
            e.printStackTrace();
            return null;
        }
    }
}
