package fr.univlille.iut.r304.pixelart.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PixelArtTest {

    //Exemples de test pour rappel, peuvent être supprimés
    @Test
    public void testSomme(){
        assertEquals(0.8,0.7+0.1,0.0001);
    }

    @Test
    public void testCharAt() {
        String text = "Hello World !";
        assertThrows(RuntimeException.class,
                () -> text.charAt(text.length()));
    }

    //Écrire les tests de fonctionnalité couvrant les méthodes du modèle implémentées sur le tour
}
