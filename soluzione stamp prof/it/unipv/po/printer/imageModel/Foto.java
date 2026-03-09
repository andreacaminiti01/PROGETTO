/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.imageModel;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.colorModel.RGBColor;
import it.unipv.po.printer.dataModel.Printable;

/**
 *
 *
 * I'm assuming only square (1:1) aspect ratio fotos are allowed in this implementation
 */
public class Foto implements Printable{
    private static final double CONSUMO = 0.01;
    private RGBColor[][] pixels;
    
    public Foto(int n, RGBColor c) {
        /* creates a nxn image with all the pixels set to color c*/
        this.pixels = new RGBColor[n][n];
        for (int i=0; i<n;i++) {
            for (int j=0; j<n;j++) {
                pixels[i][j] = c;
            }    
        }
    }

     public Foto(int n) {
        /* creates a nxn image with all the pixels set to color c*/
        this.pixels = new RGBColor[n][n];
        for (int i=0; i<n;i++) {
            for (int j=0; j<n;j++) {
                pixels[i][j] = new RGBColor();
            }    
        }
    }
      public Foto(int r, int c) {
        /* creates a nxn image with all the pixels set to color c*/
        this.pixels = new RGBColor[r][c];
        for (int i=0; i<r;i++) {
            for (int j=0; j<c;j++) {
                pixels[i][j] = new RGBColor();
            }    
        }
    }
    public Foto(RGBColor[][] pixels) {
        this.pixels = pixels;
    }

    @Override
    public double getNeededInk(Colore c) {
        double s = 0;
        for (RGBColor[] pixelrow : pixels) {
            for (RGBColor pixelunit : pixelrow) {
                if (pixelunit != null ) {
                    s += pixelunit.getColor(c); 
                }
            }    
        }
        return s*CONSUMO;
    }

    @Override
    public String toString() {
        String result =  "Foto " +pixels.length+" x " +pixels[0].length+"\n";
            for (RGBColor[] pixelrow : pixels) {
                for (RGBColor pixelunit : pixelrow) {
                    result += pixelunit+"\t";
                } 
                result += "\n";
        }
        result += "\n";
        result += "TotalInk: "+Colore.RED.name()+" "+getNeededInk(Colore.RED)+" "+Colore.GREEN.name()+" "+getNeededInk(Colore.GREEN)+" "+Colore.BLUE.name()+" "+getNeededInk(Colore.BLUE)+"\n";
        return result;
    }
    
    public static void main(String[] args) {
        Foto f = new Foto(3, new RGBColor(0,0,255));
        System.out.println(f);
        f = new Foto(3, new RGBColor(0,255,0));
        System.out.println(f);
        f = new Foto(3, new RGBColor(255,0,0));
        System.out.println(f);
    }
}
