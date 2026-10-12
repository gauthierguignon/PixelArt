package fr.univlille.iut.r304.pixelart.vue;

import fr.univlille.iut.r304.pixelart.modele.CouleurPixel;
import fr.univlille.iut.r304.pixelart.modele.DessinPixel;

public interface DessinPixelObservateur {
    void update(DessinPixel observable);
    void update(int ligne, int col, CouleurPixel c);
    void update(CouleurPixel c);
    void redimensionner(DessinPixel observable);
    void exportPng();
}
