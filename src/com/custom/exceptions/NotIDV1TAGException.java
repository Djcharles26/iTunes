package com.custom.exceptions;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;

public class NotIDV1TAGException extends Exception{

    public ID3v1 createFalseId3v1Tag(){
        ID3v1 tag = new ID3v1Tag();
        tag.setAlbum("Unknown");
        tag.setArtist("Unknown");
        tag.setTitle("Unknown");
        tag.setGenre(12);
        return tag;
    }
}
