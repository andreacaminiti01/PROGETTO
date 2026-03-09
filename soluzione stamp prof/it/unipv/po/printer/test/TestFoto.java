/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.test;

import it.unipv.po.printer.imageModel.Foto;


public class TestFoto {

    public static void main(String[] args) {

        Foto fotos[] = new Foto[3];
        fotos[0] = new Foto(5);
        fotos[1] = new Foto(2, 3);
        fotos[2] = new Foto(4,5);
        
        System.out.println("Immagini");
        for (Foto foto : fotos) {
            System.out.println(foto);
        }        
    }
}
