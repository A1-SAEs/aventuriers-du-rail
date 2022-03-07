package fr.umontpellier.iut.rails;

public class Tunnel extends Route {
    //////Initialisation d'un tunnel//////
    public Tunnel(Ville ville1, Ville ville2, int longueur, CouleurWagon couleur) {
        super(ville1, ville2, longueur, couleur);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }

}
