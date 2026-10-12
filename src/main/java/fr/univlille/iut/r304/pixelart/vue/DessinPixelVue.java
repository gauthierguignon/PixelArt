package fr.univlille.iut.r304.pixelart.vue;

import fr.univlille.iut.r304.pixelart.controleur.DessinPixelControleur;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import fr.univlille.iut.r304.pixelart.modele.CouleurPixel;
import fr.univlille.iut.r304.pixelart.modele.DessinPixel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DessinPixelVue extends Stage implements DessinPixelObservateur {

    private static int counter = 0;

    protected static final int TAILLE_PIXEL = 20;
    protected BorderPane conteneurPrincipal;
    protected GridPane dessin;
    protected GridPane palette;
    protected Rectangle couleurPinceau;
    protected DessinPixelControleur controleur;
    protected DessinPixel modele;
    private final int number;

    public DessinPixelVue(DessinPixel modele) {
        this.number = ++counter;

        this.modele = modele;

        // la vue s'abonne au controleur
        modele.add(this);
        // on instancie le controleur en lui passant le modele
        controleur = new DessinPixelControleur(modele);

        this.palette = creerPalette();
        this.couleurPinceau = new Rectangle(TAILLE_PIXEL, TAILLE_PIXEL);

        CouleurPixel tmp = CouleurPixel.BLANC;
        this.couleurPinceau.setFill(Color.rgb(tmp.rouge, tmp.vert, tmp.bleu));

        this.dessin = creerDessin(modele);

        conteneurPrincipal = new BorderPane();
        this.setScene(new Scene(conteneurPrincipal));
        this.setTitle("Pixel Art");
        VBox outils = creerBoiteOutils();
        outils.setSpacing(5.0);
        conteneurPrincipal.setLeft(dessin);
        conteneurPrincipal.setRight(outils);
        this.setWidth(380);
        this.setHeight(320);

        this.show();
    }

    private VBox creerBoiteOutils() {
        Button boutonTout = new Button("Tout !");

        // recolorier tout le dessin
        boutonTout.setOnAction(e -> {
            controleur.clicBoutonToutPeindre();
        });

        Button redimensionner = new Button("Redimensionner !");
        TextField saisieHauteur = new TextField();
        saisieHauteur.setPromptText("Hauteur");
        TextField saisieLargeur = new TextField();
        saisieLargeur.setPromptText("Largeur");

        redimensionner.setOnAction( e -> {
            controleur.clicBoutonRedimensionner(saisieHauteur.getText(), saisieLargeur.getText());
        });

        Button nouvelleFenetre = new Button("Nouvelle fenêtre");

        nouvelleFenetre.setOnAction( e -> {
            new DessinPixelVue(modele);
        });

        Button exporterPng = new Button("Exporter en png");

        exporterPng.setOnAction( e -> {
            controleur.clicPng();
        });

        return new VBox(palette, boutonTout, couleurPinceau, saisieHauteur, saisieLargeur, redimensionner,nouvelleFenetre, exporterPng);
    }

    public void setControleur(DessinPixelControleur controleur) {
        this.controleur = controleur;
    }

    protected GridPane creerPalette() {
        GridPane gridPane = new GridPane();
        for (int ligne = 0; ligne < 3; ligne++) {
            for (int col = 0; col < 3; col++) {
                Rectangle carre = new Rectangle(TAILLE_PIXEL, TAILLE_PIXEL);
                gridPane.add(carre, col, ligne);
                CouleurPixel c = CouleurPixel.values()[ligne * 3 + col];
                carre.setFill(Color.rgb(c.rouge, c.vert, c.bleu));

                // Appelle le controleur quand on clique sur un carré
                carre.setOnMouseClicked(e -> {
                    controleur.clicCouleur(c);
                });

            }
        }
        return gridPane;
    }

    protected GridPane creerDessin(DessinPixel modele) {
        GridPane gridPane = new GridPane();
        for(int i = 0; i < modele.getNbLignes(); i++) {
           for( int j = 0; j < modele.getNbCols(); j ++) {
               Rectangle carre = new Rectangle(TAILLE_PIXEL, TAILLE_PIXEL);
               carre.setFill(Color.rgb(255, 255, 255));
               gridPane.add(carre,j,i);

               int ligne = i;
               int col = j;

               carre.setOnDragDetected(e -> {
                   carre.startFullDrag();
               });

               carre.setOnMouseDragEntered(e -> {
                   controleur.clicDessin(ligne, col);
               });

           }
        }
        return gridPane;
    }

    @Override
    public void update(CouleurPixel c) {
        this.couleurPinceau.setFill(Color.rgb(c.rouge, c.vert, c.bleu));
    }

    @Override
    public void update(DessinPixel modele) {
        CouleurPixel c = modele.getCouleurPinceau();
        for(int i = 0; i < modele.getNbLignes(); i++) {
            for( int j = 0; j < modele.getNbCols(); j ++) {
                Rectangle r = (Rectangle) dessin.getChildren().get(i * modele.getNbCols() + j);
                r.setFill(Color.rgb(c.rouge, c.vert, c.bleu));
            }
        }
    }

    @Override
    public void update(int ligne, int col, CouleurPixel c) {
            Rectangle r = (Rectangle) dessin.getChildren().get(ligne * dessin.getColumnCount() + col);
            r.setFill(Color.rgb(c.rouge, c.vert, c.bleu));
    }

    @Override
    public void redimensionner(DessinPixel model){
        // stockage pour performance
        CouleurPixel c = model.getCouleurPinceau();
        int dessinRowCount = dessin.getColumnCount();
        int dessinColCount = dessin.getColumnCount();

        // on crée une grille vide avec la couleur en cours
        GridPane newGrid = creerDessin(model);
        // on parcourt toute la nouvelle grille
        for(int i = 0; i < model.getNbLignes(); i++) {
            for( int j = 0; j < model.getNbCols(); j ++) {
                // on récupère les carrés dans le gridPane
                Rectangle nouveauCarre = (Rectangle) newGrid.getChildren().get(i * model.getNbCols() + j);
                // si on est dans les dimensions de l'ancienne grille
                if(i < dessinColCount && j < dessinRowCount) {
                    Rectangle ancienCarre = (Rectangle) dessin.getChildren().get(i * dessinColCount + j);
                    nouveauCarre.setFill(ancienCarre.getFill());
                } else {
                    nouveauCarre.setFill(Color.rgb(c.rouge, c.vert, c.bleu));
                }
            }
        }
        this.dessin = newGrid;
        conteneurPrincipal.setLeft(dessin);
    }

    public void exportPng() {
        WritableImage image = dessin.snapshot(null, null);
        File file = new File(System.getProperty("user.home"),"Téléchargements/dessin" + number +".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}