package fr.umontpellier.iut.rails;

import java.util.ArrayList;
import java.util.List;

public class Ferry extends Route {

    // Nombre de locomotives qu'un joueur doit payer pour capturer le ferry
    private int nbLocomotives;

    //////Initialisation d'un ferry//////
    public Ferry(Ville ville1, Ville ville2, int longueur, CouleurWagon couleur, int nbLocomotives) {
        super(ville1, ville2, longueur, couleur);
        this.nbLocomotives = nbLocomotives;
    }

    @Override
    public String toString() {
        return String.format("[%s - %s (%d, %s, %d)]", getVille1(), getVille2(), getLongueur(), getCouleur(),
                nbLocomotives);
    }

    @Override
    public boolean peutPrendreRoute(Joueur joueur){
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();

        if(this.getProprietaire() == null) {
            //Cas normal -> Route grise -> Assez de carte de la même couleur (avec ou sans loco)
            for (CouleurWagon couleurWagon : listeCouleurWagons) {
                if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur() && joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE)>=this.nbLocomotives) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void prendreRoute(Joueur joueur, Jeu jeu){
        int prixRoute = 0;
        CouleurWagon couleurChoisie = null;
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        ArrayList<String> choixCartesPossibles = new ArrayList<>();

        choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());

        while(prixRoute<nbLocomotives){
            joueur.getCartesWagon().remove((CouleurWagon.LOCOMOTIVE));
            jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
            prixRoute++;
        }

        choixCartesPossibles.clear();

        for(CouleurWagon couleurWagon : listeCouleurWagons){
            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur()-nbLocomotives){
                if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) > 0){
                    choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());
                }
                if(joueur.nombreCouleurWagonJoueur(couleurWagon) > 0) {
                    choixCartesPossibles.add(couleurWagon.name());
                }
            }
        }

        while(prixRoute <this.getLongueur()){
            String choixJoueur = joueur.choisir("Choisissez " + prixRoute + " carte(s) à défausser (hors locomotives obligatoires demandées par le ferry)", choixCartesPossibles, new ArrayList<>(), false);

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

            if(couleurChoisie != null && joueur.nombreCouleurWagonJoueur(couleurChoisie) < 0){
                choixCartesPossibles.remove(couleurChoisie.name());
            }

            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) < 0){
                choixCartesPossibles.remove(CouleurWagon.LOCOMOTIVE.name());
            }
        }
        this.setProprietaire(joueur);
        joueur.setNbWagons(joueur.getNbWagons()-this.getLongueur());
    }

}
