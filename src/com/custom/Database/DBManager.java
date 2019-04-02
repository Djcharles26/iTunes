package com.custom.Database;


import com.custom.controller.Cancion;
import com.custom.controller.Usuario;
import com.custom.exceptions.NotIDV1TAGException;
import com.mpatric.mp3agic.*;


import java.io.*;
import java.util.*;

import static com.custom.utils.SongUtils.transformId3v2toId3v1;


public class DBManager {
    FileInputStream in = null;
    FileOutputStream out = null;
    ObjectOutputStream oS = null;
    ObjectInputStream oI = null;

    private static DBManager  sharedInstance = null;

    public static DBManager getSharedInstance(){
        if(sharedInstance==null) sharedInstance = new DBManager();
        return sharedInstance;
    }

    public void write(Cancion cancion){
        try {
            out = new FileOutputStream("songs.txt");
            oS = new ObjectOutputStream(out);
            oS.writeObject(cancion);
            oS.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeSongs(List<Cancion> canciones){
        try {
            out = new FileOutputStream("songs.txt");
            oS = new ObjectOutputStream(out);
            for(Cancion cancion:canciones){
                oS.writeObject(cancion);
            }
            oS.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //TODO: Drag and drop manager, when the song is dragged, it must be
    //Converted to Cancion type

    public List<Cancion> read(){
        try{
            in = new FileInputStream("songs.txt");
            oI = new ObjectInputStream(in);
            List<Cancion> canciones = new ArrayList<>();
            while(in.available()>0){
                canciones.add((Cancion) oI.readObject());
            }
            if(canciones.size()<1) throw new IOException();
            oI.close();
            in.close();
            return canciones;
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();

            return null;
        }
    }

    public void folderWriter(String URL){
        Mp3File song;
        File folder = new File(URL);
        File[] listOfFiles = folder.listFiles();
        List<Cancion> canciones = new ArrayList<>();
        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    try {
                        song = new Mp3File(listOfFiles[i].getPath());
                        ID3v1 id3v1Tag = null;
                        ID3v2 id3v2Tag = null;
                        if(song.hasId3v1Tag()) id3v1Tag = song.getId3v1Tag();
                        else if(song.hasId3v2Tag()){
                            id3v2Tag = song.getId3v2Tag();
                            id3v1Tag = transformId3v2toId3v1(id3v2Tag);
                        }
                        try {
                            if (id3v1Tag == null) throw new NotIDV1TAGException();
                        }catch(NotIDV1TAGException e){
                            id3v1Tag = e.createFalseId3v1Tag();
                        }
                        canciones.add(
                                new Cancion(0, listOfFiles[i].getPath(), id3v1Tag));
                    }catch(InvalidDataException e){
                        System.out.println("Not a valid type");
                    }
                }
            }
            if(canciones.size()>0) writeSongs(canciones);
            else throw new IOException();
        }catch (NullPointerException | IOException |UnsupportedTagException e) {
            e.printStackTrace();
        }

    }

    public List<Usuario> readUsers(){
        try{
            in = new FileInputStream("users.txt");
            oI = new ObjectInputStream(in);
            List<Usuario> usuarios = new ArrayList<>();
            while(in.available()>0){
                usuarios.add((Usuario)oI.readObject());
            }
            in.close();
            oI.close();
            return usuarios;
        }catch(IOException |ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }


    }

    public void writeUser(Usuario usuario){
        try{
            out = new FileOutputStream("users.txt");
            oS = new ObjectOutputStream(out);
            oS.writeObject(usuario);
            out.close();
            oS.close();
        }catch(IOException   e ){
            e.printStackTrace();
        }
    }
}
