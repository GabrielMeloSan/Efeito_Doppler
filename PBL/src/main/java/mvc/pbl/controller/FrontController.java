package mvc.pbl.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mvc.pbl.FrontApp;
import mvc.pbl.model.DadosFisica;

import java.io.IOException;

public class FrontController {
    public ImageView buttonCriar, buttonOuvir, buttonVoltar, buttonEnviarDados, buttonExit;
    public TextField tfFreq, tfVel, tfDist, tfPot, tfNom, tfTempo, valorY;
    public Slider sliderTempo;

    @FXML
    public void appCriar() throws IOException {
        Stage stage = (Stage) buttonCriar.getScene().getWindow();
        FrontApp.criarAudio(stage);
    }

    @FXML
    public void appEnviarDados() throws IOException {
        boolean exceptionCaught;
        double[] temp = new double[4];
        String tNome = "";
        do {
            exceptionCaught = false;
            try {
                temp[0] = Double.parseDouble(tfFreq.getText().replaceAll(",", "."));
                temp[1] = Double.parseDouble(tfDist.getText().replaceAll(",", "."));
                temp[2] = Double.parseDouble(tfVel.getText().replaceAll(",", "."));
                temp[3] = Double.parseDouble(tfPot.getText().replaceAll(",", "."));
                tNome = tfNom.getText();
                System.out.println(temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3] + " " + tNome);
            } catch (NumberFormatException nfe) {
                exceptionCaught = true;
            }
        } while (exceptionCaught);
        InsercaodeDados.CalcularFisica(temp[0],temp[1],temp[2],temp[3],tNome);
        Stage stage = (Stage) buttonEnviarDados.getScene().getWindow();
        FrontApp.dadosAudio(stage);
    }

    @FXML
    public void appOuvir() throws IOException{
        Stage stage = (Stage) buttonOuvir.getScene().getWindow();
        FrontApp.ouvirAudio(stage);
    }

    @FXML
    public void appVoltar() throws IOException {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        FrontApp.menuPrincipal(stage);
    }

    @FXML
    public void appExit() {
        Platform.exit();
    }

    @FXML
    public void mudaValor() {
        double tempo = DadosFisica.getTempo();
        String temp = "";
        sliderTempo.setMax(tempo);
        Bindings.bindBidirectional(tfTempo.textProperty(), sliderTempo.valueProperty(), new javafx.util.StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return number.toString();
            }

            @Override
            public Number fromString(String string) {
                try {
                    return Double.valueOf(string);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });
        valorY.setText(temp);
    }
}