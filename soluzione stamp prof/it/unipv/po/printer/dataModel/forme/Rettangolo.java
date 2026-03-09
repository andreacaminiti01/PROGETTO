/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.dataModel.forme;

import it.unipv.po.printer.colorModel.RGBColor;
import it.unipv.po.printer.dataModel.Forma;


public class Rettangolo extends Forma{

    private double altezza;
     private double base;

    public Rettangolo( double base, double altezza) {
        super();
        this.base = base;
        this.altezza = altezza;
    }
    public Rettangolo(RGBColor col, double base, double altezza) {
        super(col);
        this.base = base;
        this.altezza = altezza;
    }
    @Override
    public double getArea() {
        return base*altezza;
    }

    @Override
    public double getPerimetro() {
        return 2*(base+altezza);
    }

    @Override
    public String toString() {
        return super.toString()+ " (altezza=" + altezza + ", base=" + base + ")\n";
    }
    
}
