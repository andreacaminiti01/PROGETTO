/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.colorModel;

import it.unipv.po.printer.colorModel.Colore;


public class RGBColor implements Comparable<RGBColor> {

    private int r, g, b;
    public final static int MAXCOLOR = 256;

    public RGBColor() {
        this.r = (int)(Math.random()*MAXCOLOR);
        this.g = (int)(Math.random()*MAXCOLOR);
        this.b = (int)(Math.random()*MAXCOLOR);
    }

    public RGBColor(int r, int g, int b){
        this.r = r % MAXCOLOR;
        this.g = g % MAXCOLOR;
        this.b = b % MAXCOLOR;
    }

    private int getR() {
        return r;
    }

    private int getG() {
        return g;
    }

    private int getB() {
        return b;
    }

    public int getColor(Colore c) {
        switch (c) {
            case RED:
                return r;
            case GREEN:
                return g;
            case BLUE:
                return b;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return  "("+String.format("%02x",r) + "," + String.format("%02x",g) + "," + String.format("%02x",b) + ')';
    }

    @Override
    public int compareTo(RGBColor o) {
        if (o.getR() != this.r) {
            return r - o.getR();
        } else if (o.getG() != this.g) {
            return g - o.getG();
        } else {
            return b - o.getB();
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RGBColor other = (RGBColor) obj;
        if (this.r != other.r) {
            return false;
        }
        if (this.g != other.g) {
            return false;
        }
        if (this.b != other.b) {
            return false;
        }
        return true;
    }
    
    

}
