package fr.umontpellier.iut.rails;

import java.util.ArrayList;
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
    public boolean peutPrendreRoute(Joueur joueur, Jeu jeu){
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        List<Route> listeRoutes = jeu.getRoutes();

        if(this.getProprietaire() == null||this.getProprietaire()!=joueur) {
            //Cas normal -> Route grise -> Assez de carte de la même couleur (avec ou sans loco)
            if (this.getCouleur().equals(CouleurWagon.GRIS)) {
                for (CouleurWagon couleurWagon : listeCouleurWagons) {
                    if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur()) {
                        return true;
                    }
                }
            }
            //Cas couleur -> Route couleur -> Assez de carte de la même couleur (avec ou sans loco)
            else {
                if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(this.getCouleur()) >= this.getLongueur()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void prendreRoute(Joueur joueur, Jeu jeu){
        int prixRoute =0;
        CouleurWagon couleurChoisie = null;
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        ArrayList<String> choixCartesPossibles = new ArrayList<>();


        CouleurWagon carteBonus = null;
        int prixEnPlus = 0;/* Danger : boucle infini
        for(int i =0; i<3;i++){
            carteBonus=jeu.piocherCarteWagon();
            if(carteBonus.equals(couleurChoisie)||carteBonus.equals(CouleurWagon.LOCOMOTIVE)){
                prixEnPlus++;
            }
            jeu.defausserCarteWagon(carteBonus);
        }
        */

        if(this.getCouleur() == CouleurWagon.GRIS){
            for(CouleurWagon couleurWagon : listeCouleurWagons){
                if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur()){
                    choixCartesPossibles.add(couleurWagon.name());
                }
            }
        }
        else{
            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(this.getCouleur()) >= this.getLongueur()){
                choixCartesPossibles.add(this.getCouleur().name());
                couleurChoisie = this.getCouleur();
            }
        }

        choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());

        while(prixRoute < this.getLongueur()+prixEnPlus){
            String choixJoueur = joueur.choisir("Choisissez " + prixRoute + " carte(s) à défausser", choixCartesPossibles, new ArrayList<>(), false);

            if(choixJoueur.equals(CouleurWagon.LOCOMOTIVE.name())){
                joueur.getCartesWagon().remove((CouleurWagon.LOCOMOTIVE));
                jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
                prixRoute++;
            }

            if(couleurChoisie==null){
                for(CouleurWagon couleurWagon : listeCouleurWagons){
                    if(choixJoueur.equals(couleurWagon.name())){
                        couleurChoisie = couleurWagon;
                        joueur.getCartesWagon().remove(couleurChoisie);
                        jeu.defausserCarteWagon(couleurChoisie);
                        prixRoute++;
                    }
                }
            }

            else if(choixJoueur.equals(couleurChoisie.name())){
                joueur.getCartesWagon().remove(couleurChoisie);
                jeu.defausserCarteWagon(couleurChoisie);
                prixRoute++;
            }
        }

        this.setProprietaire(joueur);
        joueur.setNbWagons(joueur.getNbWagons()-this.getLongueur());
    }
}
