/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.imageModel;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.colorModel.RGBColor;
import it.unipv.po.printer.dataModel.Forma;
import it.unipv.po.printer.dataModel.Printable;
import it.unipv.po.printer.dataModel.forme.Quadrato;
import it.unipv.po.printer.imageModel.exception.VectorComponentOutOfLimit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ImgVett implements Comparable<ImgVett>, Printable{

    private static final double CONSUMO = 0.1;
	public static final int MAXCOMP = 3;
    private List<Forma> elementi;

    public ImgVett() {
        elementi = new ArrayList<>();
    }

    public void addComponente(Forma f) throws VectorComponentOutOfLimit{
    		if((elementi.size()+1)>MAXCOMP)
    			throw new VectorComponentOutOfLimit();
            elementi.add(f);
    }

    public double getSommaAree() {
        double s = 0;
        for (Forma m : elementi) {
            if (m != null) {
                s += m.getArea();
            }
        }
        return s;
    }

    public double getSommaAree(Colore c) {
        double s = 0;
        for (Forma m : elementi) {
                if (m != null) {
                    s += m.getArea()*(m.getColore().getColor(c)/(double)RGBColor.MAXCOLOR);
            }
        }
        return s;
    }
    
    @Override
    public double getNeededInk(Colore c) {
    	
        return getSommaAree(c)*CONSUMO;
    }

    @Override
    public String toString() {
        /* prima della stampa si ordinano i componenti grafici per colore */
        Collections.sort(elementi);
        String s = "";
        for (Forma f : elementi) {
            if (f != null) {
                s += f;
            } 
        }
        s += "TotalInk: "+Colore.RED.name()+" "+getNeededInk(Colore.RED)+" "+Colore.GREEN.name()+" "+getNeededInk(Colore.GREEN)+" "+Colore.BLUE.name()+" "+getNeededInk(Colore.BLUE)+"\n";
        return s;
    }

    @Override
    public int compareTo(ImgVett o) {
        if (this.getSommaAree() < o.getSommaAree()) {
            return -1;
        } else if (this.getSommaAree() > o.getSommaAree()) {
            return +1;
        }
        return 0;
    }
    public static void main(String[] args) {
        ImgVett img =  new ImgVett();
        try {
			img.addComponente(new Quadrato(2, new RGBColor(0, 0, 255)));
		} catch (VectorComponentOutOfLimit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("img = " + img);
        img =  new ImgVett();
        try {
			img.addComponente(new Quadrato(2, new RGBColor(0, 255, 0)));
		} catch (VectorComponentOutOfLimit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("img = " + img);
        img =  new ImgVett();
        try {
			img.addComponente(new Quadrato(2, new RGBColor(255,0, 0)));
		} catch (VectorComponentOutOfLimit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("img = " + img);
    }
}
