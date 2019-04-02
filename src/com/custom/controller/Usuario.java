package com.custom.controller;

import com.custom.Database.DBManager;
import sun.security.pkcs11.Secmod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
    private String name;
    private String email;
    private String password;
    private double saldo;
    private List<Cancion> cancionesCompradas, cancionesLocales;
    private static Usuario sharedInstance;

    public static Usuario getSharedInstance(){
        if(sharedInstance==null) sharedInstance = new Usuario();
        return sharedInstance;
    }

    private Usuario() {
        this.cancionesCompradas = new ArrayList<>();
        this.cancionesLocales = new ArrayList<>();
    }

    public void createUser(String email,String name, double saldo, String password){
        if(sharedInstance==null) sharedInstance = new Usuario();
        sharedInstance.email = email;
        sharedInstance.name = name;
        sharedInstance.password = password;
        sharedInstance.saldo = saldo;
    }

    public boolean checkEmail(String usuario){
        Pattern VALID_EMAIL_REGEX =
            Pattern.compile(
                    "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_REGEX.matcher(usuario);
        return matcher.find();
    }


    public boolean checkIfPurchased(Cancion cancion){
        for(Cancion song:cancionesCompradas){
            if(song.equals(cancion)) return true;
        }
        return false;
    }

    public void addPurchasedSong(Cancion cancion){
        DBManager.getSharedInstance().write(cancion);
        this.cancionesCompradas.add(cancion);
    }

    public void addLocalSong(Cancion cancion){
        DBManager.getSharedInstance().write(cancion);
        this.cancionesLocales.add(cancion);
    }

    public boolean setPassword(String password){
        if(password.length()>8) sharedInstance.password = password;
        else return false;
        return true;
    }

    public boolean checkPassword(String password){

        return sharedInstance.password.equals(password);
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void depositar(double deposito){
        this.saldo += deposito;
    }
}
