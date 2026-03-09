package it.unipv.po.printer.dataModel;


import it.unipv.po.printer.colorModel.RGBColor;
import it.unipv.po.printer.inkJetprinter.Util;

public abstract class Forma implements Misurabile, Comparable<Forma> {

    private RGBColor coloreRGB;

    public Forma(RGBColor col) {
        this.coloreRGB = col;
    }

    public Forma() {
        this.coloreRGB = new RGBColor();
    }
    public RGBColor getColore() {
        return coloreRGB;
    }

    @Override
    public int compareTo(Forma o) {
        return this.coloreRGB.compareTo(o.coloreRGB);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +" " + this.getColore()+ " area="+Util.format(this.getArea());
    }

}
