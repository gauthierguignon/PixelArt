package fr.univlille.iut.r304.pixelart.modele;

import fr.univlille.iut.r304.pixelart.vue.DessinPixelObservateur;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class DessinPixel {

    private int nbLignes;
    private int nbColonnes;
    private CouleurPixel couleurPinceau;
    private List<DessinPixelObservateur> observers;

    public DessinPixel(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.observers = new ArrayList<>();
        couleurPinceau = CouleurPixel.BLANC;
    }

    public CouleurPixel getCouleurPinceau() {
        return couleurPinceau;
    }

    /**
     * Ajoute un abonné au modèle
     * @param o: l'observateur qui s'abonne au modèle"
     */
    public void add(DessinPixelObservateur o) {
        observers.add(o);
    }

    /**
     * Retire un abonné du modèle
     * @param o: l'observateur qui se désabonne du modèle
     */
    public void remove(DessinPixelObservateur o) {
        observers.remove(o);
    }

    public int getNbLignes(){
        return this.nbLignes;
    }

    public int getNbCols() {
        return this.nbColonnes;
    }

    /**
     * Change la couleur utilisée pour colorier
     * @param couleur la couleur à utiliser pour colorier
     */
    public void choixCouleur(CouleurPixel couleur) {
        couleurPinceau = couleur;
        for(DessinPixelObservateur obs : observers) {
            obs.update(this.couleurPinceau);
        }
    }

    /**
     * Retourne la couleur d'un pixel
     * @param ligne: ligne du pixel
     * @param col: colonne du pixel
     * @return la couleur du pixel
     */
    public CouleurPixel getCouleur(int ligne, int col) {
            return CouleurPixel.values()[ligne * 3 + col];
    }

    /**
     * Colore toute la grille d'une couleur avec celle actuelle
     */
    public void colorierTout() {
        for(DessinPixelObservateur obs : observers) {
            obs.update(this);
        }
    }

    /**
     * Change la couleur d'un pixel par celle actuelle
     * @param ligne: ligne du pixel
     * @param col: colonne du pixel
     */
    public void colorier(int ligne, int col) {
        for(DessinPixelObservateur obs : observers) {
            obs.update(ligne,col, this.couleurPinceau);
        }
    }


    /**
     * On redimensionne la grille à la taille passée en paramètre
     * Si la taille augmente, les pixels ajoutés sont blancs
     * Les couleurs des pixels de l'ancienne grille sont conservées
     * @param nbLigne: nouvelle hauteur de la grille
     * @param nbColonne: nouvelle largeur de la grille
     */
    public void redimensionner(int nbLigne, int nbColonne) {
        this.nbLignes = nbLigne;
        this.nbColonnes = nbColonne;
        for(DessinPixelObservateur obs : observers) {
            obs.redimensionner(this);
        }
    }

    public void exportPng() {
        for(DessinPixelObservateur obs : observers) {
            obs.exportPng();
        }
    }

}