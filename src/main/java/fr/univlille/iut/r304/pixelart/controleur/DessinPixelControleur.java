package fr.univlille.iut.r304.pixelart.controleur;

import fr.univlille.iut.r304.pixelart.modele.CouleurPixel;
import fr.univlille.iut.r304.pixelart.modele.DessinPixel;

public class DessinPixelControleur {

    private DessinPixel model;

    public DessinPixelControleur(DessinPixel model) {
        this.model = model;
    }

    public void clicCouleur(CouleurPixel couleur) {
        model.choixCouleur(couleur);
    }

    public void clicBoutonToutPeindre() {
        model.colorierTout();
    }
    
    public void clicDessin(int ligne, int colonne) {
        model.colorier(ligne, colonne);
    }


    public void clicBoutonRedimensionner(String nbLignes, String nbCols) {
        int nbL;
        int nbC;
        try {
            nbL = Integer.parseInt(nbLignes);
            nbC = Integer.parseInt(nbCols);
            model.redimensionner(nbL, nbC);
        } catch (NumberFormatException ignored) {  }
    }

    public void clicPng() {
        model.exportPng();
    }

}