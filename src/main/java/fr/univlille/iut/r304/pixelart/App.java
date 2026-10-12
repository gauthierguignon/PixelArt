package fr.univlille.iut.r304.pixelart;

import fr.univlille.iut.r304.pixelart.modele.DessinPixel;
import fr.univlille.iut.r304.pixelart.vue.DessinPixelVue;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        DessinPixel modele = new DessinPixel(10,10);
        DessinPixelVue vue = new DessinPixelVue(modele);
    }
}
