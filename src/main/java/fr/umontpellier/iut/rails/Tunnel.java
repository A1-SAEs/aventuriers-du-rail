package fr.umontpellier.iut.rails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tunnel extends Route {

    //////Initialisation d'un tunnel//////
    public Tunnel(Ville ville1, Ville ville2, int longueur, CouleurWagon couleur) {
        super(ville1, ville2, longueur, couleur);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }

    @Override
    public void prendreRoute(Joueur joueur, Jeu jeu){
        int prixRoute = this.getLongueur();
        CouleurWagon couleurChoisie = null;
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        List<String> choixCartesPossibles = new ArrayList<>();
        List<String> choixCartesSupplementairesPossibles = new ArrayList<>();
        if(this.getCouleur() == CouleurWagon.GRIS){
            for(CouleurWagon couleurWagon : listeCouleurWagons){
                if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= prixRoute){
                    if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) > 0){
                        choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());
                    }
                    if(joueur.nombreCouleurWagonJoueur(couleurWagon) > 0) {
                        choixCartesPossibles.add(couleurWagon.name());
                    }
                }
            }
        }
        else{
            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(this.getCouleur()) >= prixRoute){
                if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) > 0){
                    choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());
                }
                if(joueur.nombreCouleurWagonJoueur(this.getCouleur()) > 0) {
                    choixCartesPossibles.add(this.getCouleur().name());
                }
                couleurChoisie = this.getCouleur();
            }
        }


        while(prixRoute != 0){
            String choixJoueur = joueur.choisir("Choisissez " + prixRoute + " carte(s) à défausser", choixCartesPossibles, new ArrayList<>(), false);

            if(choixJoueur.equals(CouleurWagon.LOCOMOTIVE.name())){
                joueur.getCartesWagon().remove((CouleurWagon.LOCOMOTIVE));
                joueur.setCartesWagonPosees(CouleurWagon.LOCOMOTIVE);
                prixRoute--;
            }

            if(couleurChoisie==null){
                for(CouleurWagon couleurWagon : listeCouleurWagons){
                    if(choixJoueur.equals(couleurWagon.name())){
                        couleurChoisie = couleurWagon;
                        joueur.getCartesWagon().remove(couleurChoisie);
                        joueur.setCartesWagonPosees(couleurChoisie);
                        prixRoute--;
                    }
                }
            }

            else if(choixJoueur.equals(couleurChoisie.name())){
                joueur.getCartesWagon().remove(couleurChoisie);
                joueur.setCartesWagonPosees(couleurChoisie);
                prixRoute--;
            }

            if(couleurChoisie != null && joueur.nombreCouleurWagonJoueur(couleurChoisie) < 0){
                choixCartesPossibles.remove(couleurChoisie.name());
            }

            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) < 0){
                choixCartesPossibles.remove(CouleurWagon.LOCOMOTIVE.name());
            }
        }

        if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) == joueur.getCartesWagonPosees().size()){
            couleurChoisie = null;
        }

        int nbCartesAPayerEnPlus = 0;
        for(int i=0; i<3; i++){
            CouleurWagon cartePiochee = jeu.piocherCarteWagon();
            if(cartePiochee.equals(couleurChoisie) || cartePiochee.equals(CouleurWagon.LOCOMOTIVE)){
                nbCartesAPayerEnPlus++;
            }

            jeu.defausserCarteWagon(cartePiochee);
        }

        if(nbCartesAPayerEnPlus == 0){
            for(CouleurWagon cartePosee : joueur.getCartesWagonPosees()){
                jeu.defausserCarteWagon(cartePosee);
            }
            this.setProprietaire(joueur);
            joueur.setNbWagons(joueur.getNbWagons()-this.getLongueur());
        }

        else {
            if (couleurChoisie == null) {
                if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) >= nbCartesAPayerEnPlus) {
                    choixCartesSupplementairesPossibles.add(CouleurWagon.LOCOMOTIVE.name());
                }
            } else if (joueur.nombreCouleurWagonJoueur(couleurChoisie) + joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) >= nbCartesAPayerEnPlus) {
                if (joueur.nombreCouleurWagonJoueur(couleurChoisie) > 0) {
                    choixCartesSupplementairesPossibles.add(couleurChoisie.name());
                }
                if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) > 0) {
                    choixCartesSupplementairesPossibles.add(CouleurWagon.LOCOMOTIVE.name());
                }
            }

            String choixJoueur = joueur.choisir("Choisissez " + nbCartesAPayerEnPlus + " carte(s) à défausser supplémentaires ou passez", choixCartesSupplementairesPossibles, new ArrayList<>(), true);
            if(choixJoueur.equals(CouleurWagon.LOCOMOTIVE.name())){
                jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
                joueur.getCartesWagon().remove(CouleurWagon.LOCOMOTIVE);
                nbCartesAPayerEnPlus--;
            }
            else if(couleurChoisie != null && choixJoueur.equals(couleurChoisie.name())){
                jeu.defausserCarteWagon(couleurChoisie);
                joueur.getCartesWagon().remove(couleurChoisie);
                nbCartesAPayerEnPlus--;
            }
            else if(choixJoueur.equals("")){
                for(CouleurWagon cartesPosee : joueur.getCartesWagonPosees()){
                    joueur.setCartesWagon(cartesPosee);
                }
                return;
            }

            while(nbCartesAPayerEnPlus != 0){
                choixJoueur = joueur.choisir("Choisissez " + nbCartesAPayerEnPlus + " carte(s) à défausser supplémentaires", choixCartesPossibles, new ArrayList<>(), false);
                if(choixJoueur.equals(CouleurWagon.LOCOMOTIVE.name())){
                    jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
                    joueur.getCartesWagon().remove(CouleurWagon.LOCOMOTIVE);
                    nbCartesAPayerEnPlus--;
                }
                else if(couleurChoisie != null && choixJoueur.equals(couleurChoisie.name())){
                    jeu.defausserCarteWagon(couleurChoisie);
                    joueur.getCartesWagon().remove(couleurChoisie);
                    nbCartesAPayerEnPlus--;
                }
            }

            for(CouleurWagon cartePosee : joueur.getCartesWagonPosees()){
                jeu.defausserCarteWagon(cartePosee);
            }
            this.setProprietaire(joueur);
            joueur.setNbWagons(joueur.getNbWagons()-this.getLongueur());
        }
    }
}
