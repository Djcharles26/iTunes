package com.custom.controller;


import com.custom.Database.DBManager;

public class Tienda {
    public void purchaseSong(Cancion cancion){
        if(Usuario.getSharedInstance().checkIfPurchased(cancion)) {
            if (Usuario.getSharedInstance().getSaldo() - cancion.getPrecio() >= 0)
                Usuario.getSharedInstance().addPurchasedSong(cancion);
        }
    }

    public void depositar(double deposito){
        Usuario.getSharedInstance().depositar(deposito);
    }




}
