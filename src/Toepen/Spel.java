package Toepen;

import Components.GameServer;
import Enums.Soort;
import Interfaces.ISpel;
import Interfaces.ISpeler;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jelle on 7-6-2017.
 */
public class Spel extends UnicastRemoteObject implements ISpel {
    private ArrayList<ISpeler> spelers;
    private int ronde;
    private int id;
    private RemotePublisher publisher;
    private Kaart eertseKaart=null;
    private ArrayList<Kaart> alleKaarten;
    private int aantalKloppen;
    private GameServer server;
    private boolean gameStarted;
    private boolean end;
    public static final int maxStrafpunten =1;

    public Spel(GameServer server, ArrayList<ISpeler> spelers) throws RemoteException {
        this.server = server;
        this.spelers = spelers;
        this.id = id;
        this.gameStarted = false;
        this.alleKaarten = maxKaarten();
        end =false;

    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public ArrayList<Kaart> getAlleKaarten() {
        return alleKaarten;
    }

    public void setAlleKaarten(ArrayList<Kaart> alleKaarten) {
        this.alleKaarten = alleKaarten;
    }

    public ArrayList<ISpeler> getSpelers() {
        return spelers;
    }

    public void setSpelers(ArrayList<ISpeler> spelers) {
        this.spelers = spelers;
    }

    public int getRonde() {
        return ronde;
    }

    public void setRonde(int ronde) {
        this.ronde = ronde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kaart getEertseKaart() {
        return eertseKaart;
    }

    public void setEertseKaart(Kaart eertseKaart) {
        this.eertseKaart = eertseKaart;
    }

    public void removeKaart(Kaart k) {
        this.alleKaarten.remove(k);
    }


    public int getAantalKloppen() {
        return aantalKloppen;
    }

    public void setAantalKloppen(int aantalKloppen) {
        this.aantalKloppen = aantalKloppen;
    }

    public ISpeler checkAanZet(ArrayList<ISpeler> spelers) throws RemoteException {
        ISpeler isAanDeBeurt = null;
        for (ISpeler s : this.spelers) {
            if (s.isAanDeBeurt()) {
                isAanDeBeurt = s;
            }

        }
        return isAanDeBeurt;
    }

    public void nieuweRonde()throws RemoteException{
        if(!gameStarted){
            for(ISpeler s :this.spelers)
            {
                s.setLaatsteKaart(null);

            }
        getSpelers().get(0).setAanDeBeurt(true);
        deelKaarten(getSpelers());
        this.ronde = 1;
        gameStarted = true;
        }
    }

    public void deelKaarten(ArrayList<ISpeler> spelers) throws RemoteException {
        Random rng = new Random();
        int n = 0;
        Kaart k = null;
            for (ISpeler s : this.spelers) {
                for(int i =0;i<4;i++) {
                    n = rng.nextInt(alleKaarten.size());

                    k = alleKaarten.get(n);

                    s.addKaart(k);
                    this.alleKaarten.remove(k);
                }
            }
    }

    public void speelKaart(ISpeler s,Kaart k) throws RemoteException {
        for(ISpeler speler : this.spelers) {
            if (s.getGebruikersnaam().equals(speler.getGebruikersnaam())) {
                if(checkKaartBekennen(s,k)) {
                    int i = spelers.indexOf(speler);

                    speler.kaartSpelen(k);

                    if (eertseKaart == null) {
                        eertseKaart = speler.getLaatsteKaart();
                    }
                    speler.setAanDeBeurt(false);
                    if (i < spelers.size() - 1) {
                        spelers.get(i + 1).setAanDeBeurt(true);
                    } else spelers.get(0).setAanDeBeurt(true);
                }
            }
        }

        if(allCardsOnTable()){
            checkWinnaarRonde();
        }
    }

    public boolean checkKaartBekennen(ISpeler s,Kaart k) throws RemoteException {
        boolean kaartBekennen = true;

        for(Kaart kaart : s.getHand())
        {
            if(eertseKaart!= null&& eertseKaart.getSoort().equals(kaart.getSoort())&&!kaart.getSoort().equals(k.getSoort())&&kaart.getWaarde()!=k.getWaarde())
            {
                kaartBekennen = false;
            }

        }
        return kaartBekennen;
    }


    public void checkWinnaarRonde() throws RemoteException {
        ISpeler winnaar = null;
        ArrayList<Kaart> kaarten = new ArrayList<>();
        for (ISpeler s : this.spelers) {
            if(s.getLaatsteKaart()!=null) {
                kaarten.add(s.getLaatsteKaart());
            }
        }
        Kaart highestCard = calculateHighestCard(kaarten);

        for (ISpeler s : this.spelers) {
            if (s.getLaatsteKaart().getWaarde() == highestCard.getWaarde() && s.getLaatsteKaart().getSoort().equals(highestCard.getSoort())) {
                winnaar = s;
            }
        }
        for(ISpeler s : this.spelers)
        {
            if(winnaar.getGebruikersnaam().equals(s.getGebruikersnaam()))
            {
                s.setAanDeBeurt(true);
            }
            else{
                s.setAanDeBeurt(false);
            }
        }
        if(nextRound())
        {
            for(ISpeler speler : this.spelers)
            {
                speler.calculateAantalStrafPunten(winnaar);
            }
            gameStarted = false;
        }

    }

    public Kaart calculateHighestCard(ArrayList<Kaart> kaarten) {
        Kaart highestKaart = eertseKaart;
        for (Kaart k1 : kaarten) {
            if (k1.getSoort().equals(highestKaart.getSoort()) && k1.getWaarde() > highestKaart.getWaarde()) {
                System.out.println("nieuwe hoogste kaart = "+ k1);
                highestKaart = k1;
            }
        }
        System.out.println(highestKaart);
        return highestKaart;


    }



    public void addKlop() {
        this.aantalKloppen = +1;
    }

    public ArrayList<Kaart> maxKaarten() {
        ArrayList<Kaart> alleKaarten = new ArrayList<>();

        for (int i = 3; i < 11; i++) {
            alleKaarten.add(new Kaart(i, Soort.Harten));
            alleKaarten.add(new Kaart(i, Soort.Ruiten));
            alleKaarten.add(new Kaart(i, Soort.Schoppen));
            alleKaarten.add(new Kaart(i, Soort.Klaveren));
        }

        return alleKaarten;
    }

    public boolean nextRound() throws RemoteException {
        boolean end = false;
        if(ronde+1 ==5)
        {
            end =true;
        }
        else
        System.out.println("volgende ronde");
            this.eertseKaart = null;
            ronde++;
            return end;
    }


    public void resetRound() throws RemoteException {
        this.alleKaarten = maxKaarten();
        this.ronde = 0;
        this.deelKaarten(spelers);

        checkAanZet(spelers);
    }

    public boolean allCardsOnTable() throws RemoteException {
        boolean allCardsOnTable = true;
        int kaartenPerRonde=0;
        if(ronde==1)
        {
            kaartenPerRonde =3;
        }
        if(ronde==2)
        {
            kaartenPerRonde =2;
        }
        if(ronde==3)
        {
            kaartenPerRonde =1;
        }
        if(ronde==4)
        {
            kaartenPerRonde =0;
        }
        for (ISpeler s1 : this.spelers) {
            for (ISpeler s2 : this.spelers) {
                if (s1.getHand().size() != s2.getHand().size()||s1.getHand().size()!=kaartenPerRonde||s2.getHand().size()!=kaartenPerRonde) {
                    allCardsOnTable = false;
                }
            }
        }

        return allCardsOnTable;
    }
    public boolean checkEnd() throws RemoteException {
        boolean b = false;
        for(ISpeler s :this.spelers)
        {
            if(s.getStrafPunten()>=maxStrafpunten)
            {

                end = true;
                b = true;
            }
        }
        return b;
    }

}
