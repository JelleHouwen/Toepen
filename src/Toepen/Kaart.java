package Toepen;

import Enums.Soort;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Created by Jelle on 7-6-2017.
 */
public class Kaart implements Remote, Serializable {

    private int waarde;
    private Soort soort;
    private boolean visible;
    private Rectangle rectangleKaart;
    private Label stringValue;
    private boolean selectedKaart;

    public Kaart(int waarde, Soort soort)
    {
        this.waarde = waarde;
        this.soort = soort;
    }
    public boolean getSelectedKaart()
    {
        return this.selectedKaart;
    }

    public void setSelectedKaart(boolean selectedKaart)
    {


            this.selectedKaart = selectedKaart;

    }


    public Label getStringValue() {
        return stringValue;
    }

    public void setStringValue(Label stringValue) {
        this.stringValue = stringValue;
    }

    public Rectangle getRectangleKaart() {
        return rectangleKaart;
    }

    public void setRectangleKaart(Rectangle rectangleKaart) {
        this.rectangleKaart = rectangleKaart;
    }

    public int getWaarde() {
        return waarde;
    }

    public Image getKaartImage()
    {
        return new Image( "resources/toepenfoto.jpg");
    }


    public void setWaarde(int waarde) {
        this.waarde = waarde;
    }

    public Soort getSoort() {
        return soort;
    }

    public void setSoort(Soort soort) {
        this.soort = soort;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString()
    {
        String s ="";
        String waarde ="";

        if(this.soort == Soort.Harten)
        {
            s ="❤";
        }
        if(this.soort == Soort.Ruiten)
        {
            s ="♦";
        }
        if(this.soort == Soort.Klaveren)
        {
            s ="♣";
        }
        if(this.soort == Soort.Schoppen)
        {
            s ="♠";
        }
        if(this.waarde ==3)
        {
            waarde="B";
        }
        if(this.waarde ==4)
        {
            waarde="Q";
        }
        if(this.waarde ==5)
        {
            waarde="K";
        }
        if(this.waarde ==6)
        {
            waarde="A";
        }
        if(this.waarde ==7)
        {
            waarde="7";
        }
        if(this.waarde ==8)
        {
            waarde="8";
        }
        if(this.waarde ==9)
        {
            waarde="9";
        }
        if(this.waarde ==10)
        {
            waarde="10";
        }

        return s + " " + waarde;
    }
}
