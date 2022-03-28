package fr.umontpellier.iut.rails;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.beans.DesignMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JeuTest {
    Jeu Partie = new Jeu(new String[]{"Lolo", "Stella"});
    List<CouleurWagon> pileCartesWagon = Partie.getPileCartesWagon();
    List<CouleurWagon> defausseCartesWagon = Partie.getDefausseCartesWagon();
    List<CouleurWagon> cartesWagonVisibles = Partie.getCartesWagonVisibles();
    List<Destination> pileDestinations = Partie.getPileDestinations();

    @BeforeEach
    void setUp() {
        //Mise en place de la pile des wagons : 110 cartes
        for (int i = 0; i < 14; i++) {
            pileCartesWagon.add(CouleurWagon.LOCOMOTIVE);
        }
        for (int i = 0; i < 12; i++) {
            pileCartesWagon.addAll(CouleurWagon.getCouleursSimples());
        }
        Collections.shuffle(pileCartesWagon);

        //Mise en place de la pile des destinations : 46 cartes
        pileDestinations.addAll(Destination.makeDestinationsEurope());
    }

    /////defausserCarteWagon/////
    @Disabled //Fonctionne
    @Test
    public void test_ajouter_carte_dans_defausse_vide() {
        Partie.defausserCarteWagon(CouleurWagon.ROUGE);
        System.out.println(defausseCartesWagon);
        assertEquals(1, defausseCartesWagon.size());
    }

    @Disabled //Fonctionne
    @Test
    public void test_ajouter_carte_dans_cartes_visibles() {
        pileCartesWagon.clear();
        defausseCartesWagon.clear();
        cartesWagonVisibles.add(CouleurWagon.ROSE);
        Partie.defausserCarteWagon(CouleurWagon.ROUGE);
        System.out.println(cartesWagonVisibles);
        assertEquals(2, cartesWagonVisibles.size());
    }

    @Disabled //Fonctionne
    @Test
    public void test_ne_pas_ajouter_carte_dans_cartes_visibles_car_egale_a_5_malgre_pile_et_defausse_vides() {
        pileCartesWagon.clear();
        defausseCartesWagon.clear();
        for (int i = 0; i < 5; i++) {
            cartesWagonVisibles.add(CouleurWagon.ROSE);
        }
        Partie.defausserCarteWagon(CouleurWagon.ROUGE);
        System.out.println(cartesWagonVisibles);
        System.out.println(defausseCartesWagon);
        assertEquals(1, defausseCartesWagon.size());
    }

    /////piocherCarteWagon/////
    @Disabled //Fonctionne
    @Test
    public void test_pile_et_defausse_remplies() {
        Partie.piocherCarteWagon();
        System.out.println(pileCartesWagon);
        assertEquals(109, pileCartesWagon.size());
    }

    @Disabled //Fonctionne
    @Test
    public void test_pile_vide_et_defausse_remplie() {
        defausseCartesWagon.addAll(pileCartesWagon);
        pileCartesWagon.clear();
        Partie.piocherCarteWagon();
        assertEquals(109, pileCartesWagon.size());
    }

    @Disabled //Fonctionne
    @Test
    public void test_pile_et_defausse_vides() {
        pileCartesWagon.clear();
        defausseCartesWagon.clear();
        assertNull(Partie.piocherCarteWagon());
    }

    /////retirerCarteWagonVisible/////
    @Disabled //Fonctionne
    @Test
    public void test_piocher_carte_wagon_visible() {
        for (int i = 0; i < 5; i++) {
            cartesWagonVisibles.add(CouleurWagon.ROUGE);
        }
        Partie.retirerCarteWagonVisible(CouleurWagon.ROUGE);
        System.out.println(cartesWagonVisibles);
        assertEquals(5, cartesWagonVisibles.size());
    }

    //A refaire/revoir
    @Test
    public void test_3_locomotives() {
        cartesWagonVisibles.clear();
        for (int i = 0; i < 3; i++) {
            cartesWagonVisibles.add(CouleurWagon.LOCOMOTIVE);
        }
        for (int i = 0; i < 2; i++) {
            cartesWagonVisibles.add(CouleurWagon.ROUGE);
        }
        System.out.println(cartesWagonVisibles);
        Partie.retirerCarteWagonVisible(CouleurWagon.ROUGE);
        System.out.println(cartesWagonVisibles);
    }

    /////piocherDestination/////
    @Disabled //Fonctionne
    @Test
    public void test_pile_destination_remplie() {
        Partie.piocherDestination();
        assertEquals(45, pileDestinations.size());
    }

    @Disabled //Fonctionne
    @Test
    public void test_pile_destination_vide() {
        pileDestinations.clear();
        assertNull(Partie.piocherDestination());
    }


}