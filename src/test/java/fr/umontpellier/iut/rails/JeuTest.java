package fr.umontpellier.iut.rails;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JeuTest {
    Jeu Partie = new Jeu(new String[]{"Lolo","Stella"});
    List<CouleurWagon> pileCartesWagon= new ArrayList<>();
    List<CouleurWagon>defausseCarteWagon = new ArrayList<>();
    List<CouleurWagon>cartesWagonVisibles = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Mise en place de la pile : 110 cartes
        for(int i=0;i<14;i++){
            pileCartesWagon.add(CouleurWagon.LOCOMOTIVE);
        }
        for(int i=0;i<12;i++){
            pileCartesWagon.addAll(CouleurWagon.getCouleursSimples());
        }
        Collections.shuffle(pileCartesWagon);
    }

    @Test
    public void test_piocher_carte_wagon_pile_et_defausse_remplies(){
        System.out.println(pileCartesWagon.toString());
        System.out.println(defausseCarteWagon.toString());
        Partie.piocherCarteWagon();
        System.out.println(pileCartesWagon.toString());
        System.out.println(defausseCarteWagon.toString());
        assertEquals(109, pileCartesWagon.size());
    }

    @Test
    public void test_defausser_carte_wagon_ajouter_carte_dans_defausse_vide(){
        Partie.defausserCarteWagon(CouleurWagon.ROUGE);
        assertEquals(1, defausseCarteWagon.size());
    }

    @Disabled
    @Test
    public void test_defausser_carte_wagon_ajouter_carte_dans_cartes_visibles() {

    }


}