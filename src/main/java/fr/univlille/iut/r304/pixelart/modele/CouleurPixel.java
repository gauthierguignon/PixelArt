package fr.univlille.iut.r304.pixelart.modele;

public enum CouleurPixel {
    NOIR(0, 0, 0),
    BLANC(255, 255, 255),
    ROUGE(255, 0, 0),
    VERT(0, 255, 0),
    BLEU(0, 0, 255),
    JAUNE(255, 255, 0),
    CYAN(0, 255, 255),
    MAGENTA(255, 0, 255),
    GRIS(128, 128, 128);

    public final int rouge;
    public final int vert;
    public final int bleu;

    CouleurPixel(int rouge, int vert, int bleu) {
        this.rouge = rouge;
        this.vert = vert;
        this.bleu = bleu;
    }

}