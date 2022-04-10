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

    public boolean peutPrendreRoute(Joueur joueur, Jeu jeu){
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        List<Route> listeRoutes = jeu.getRoutes();

        if(this.getProprietaire() == null || this.getProprietaire()!=joueur) {
            //Cas normal -> Route grise -> Assez de carte de la même couleur (avec ou sans loco)
            for (CouleurWagon couleurWagon : listeCouleurWagons) {
                if (joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur() && joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE)>=this.nbLocomotives) {
                    return true;
                }
            }

        }
        return false;
    }

    public void prendreRoute(Joueur joueur, Jeu jeu){
        int nombre = 0;
        CouleurWagon couleurChoisie = null;
        List<CouleurWagon> listeCouleurWagons = CouleurWagon.getCouleursSimples();
        ArrayList<String> choixCartesPossibles = new ArrayList<>();

        choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());

        while(nombre<nbLocomotives){
            joueur.getCartesWagon().remove((CouleurWagon.LOCOMOTIVE));
            jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
            nombre++;
        }

        choixCartesPossibles.clear();

        for(CouleurWagon couleurWagon : listeCouleurWagons){
            if(joueur.nombreCouleurWagonJoueur(CouleurWagon.LOCOMOTIVE) + joueur.nombreCouleurWagonJoueur(couleurWagon) >= this.getLongueur()-nbLocomotives){
                choixCartesPossibles.add(couleurWagon.name());
            }
        }

        choixCartesPossibles.add(CouleurWagon.LOCOMOTIVE.name());

        while(nombre <this.getLongueur()){
            String choixJoueur = joueur.choisir("Choississez une carte à défausser", choixCartesPossibles, new ArrayList<>(), false);

            if(choixJoueur.equals(CouleurWagon.LOCOMOTIVE.name())){
                joueur.getCartesWagon().remove((CouleurWagon.LOCOMOTIVE));
                jeu.defausserCarteWagon(CouleurWagon.LOCOMOTIVE);
                nombre++;
            }

            if(couleurChoisie==null){
                for(CouleurWagon couleurWagon : listeCouleurWagons){
                    if(choixJoueur.equals(couleurWagon.name())){
                        couleurChoisie = couleurWagon;
                        joueur.getCartesWagon().remove(couleurChoisie);
                        jeu.defausserCarteWagon(couleurChoisie);
                        nombre++;
                    }
                }
            }

            else if(choixJoueur.equals(couleurChoisie.name())){
                joueur.getCartesWagon().remove(couleurChoisie);
                jeu.defausserCarteWagon(couleurChoisie);
                nombre++;
            }
        }
        this.setProprietaire(joueur);
        joueur.setNbWagons(joueur.getNbWagons()-this.getLongueur());
    }

}
