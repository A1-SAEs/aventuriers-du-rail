package fr.umontpellier.iut.rails;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class JeuTest {

    List<CouleurWagon> pileCarteWagon= new ArrayList<>();

    @BeforeEach
    void setUp() {
        for(int i=0;i<14;i++){
            pileCarteWagon.add(CouleurWagon.LOCOMOTIVE);
        }
        for(int i=0;i<12;i++){
            pileCarteWagon.addAll(CouleurWagon.getCouleursSimples());
        }
    }

    @Test
    public void test_defausser_carte_wagon_ajouter_carte_dans_defausse(){

    }


}