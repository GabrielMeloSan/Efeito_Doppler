package mvc.pbl.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import mvc.pbl.FrontApp;
import mvc.pbl.dao.Conexao;
import mvc.pbl.dao.SimulacaoDAO;
import mvc.pbl.model.DadosFisica;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FrontController {
    public ImageView buttonCriar, buttonOuvir, buttonVoltar, buttonEnviarDados, buttonExit,
                     buttonPlay, buttonBaixar, buttonApagar;
    public TextField tfFreq, tfVel, tfDist, tfPot, tfNom, tfTempo, valorY, func1, func2, ID;
    public Slider sliderTempo, sliderVolume;
    public ProgressBar tempoAudio;
    public ListView<String> listaAudio;
    private Clip clip;
    private boolean playing;
    private ScheduledExecutorService scheduler;
    private final DoubleProperty valorTempo = new SimpleDoubleProperty();

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
        FrontApp.dadosAudio(stage, tNome);
    }

    @FXML
    public void appOuvir() throws IOException{
        Stage stage = (Stage) buttonOuvir.getScene().getWindow();
        FrontApp.ouvirAudio(stage);
    }

    @FXML
    private void iniciarAudio() {
        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (clip != null) {
                volume(newValue.doubleValue());
            }
        });
    }

    private void volume(double vol) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float dB = (float) (Math.log10(vol == 0.0 ? 0.0001 : vol) * 20.0);

            if (dB < min) dB = min;
            if (dB > max) dB = max;

            gainControl.setValue(dB);
        }
    }

    private void atualizaTempo() {
        if (clip != null && clip.isRunning()) {
            double progress = (double) clip.getMicrosecondPosition() / clip.getMicrosecondLength();
            Platform.runLater(() -> tempoAudio.setProgress(progress));
        } else if (clip != null && !clip.isRunning() && playing) {
            //audio finalizado
            Platform.runLater(() -> {
                tempoAudio.setProgress(1.0);
                buttonPlay.getStyleClass().clear();
                buttonPlay.getStyleClass().add("button4");
                playing = false;
                if (scheduler != null && !scheduler.isShutdown()) {
                    scheduler.shutdown();
                }
            });
        }
    }

    @FXML
    public void playAudio() throws IOException {
        try {
            File audioFile = new File(InsercaodeDados.nomAud + ".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            //volume padrão
            volume(sliderVolume.getValue());

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && playing) {
                    Platform.runLater(() -> {
                        buttonPlay.getStyleClass().clear();
                        buttonPlay.getStyleClass().add("button4");
                        tempoAudio.setProgress(0);
                        playing = false;
                        clip.close();
                    });
                    if (scheduler != null && !scheduler.isShutdown()) {
                        scheduler.shutdown();
                    }
                }
            });

            clip.start();
            playing = true;
            buttonPlay.getStyleClass().clear();
            buttonPlay.getStyleClass().add("button5");

            //atualiza barra de progresso
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(this::atualizaTempo, 0, 100, TimeUnit.MILLISECONDS);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void playPause() throws IOException {
        if (playing) {
            clip.stop();
            playing = false;
            buttonPlay.getStyleClass().clear();
            buttonPlay.getStyleClass().add("button4");
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } else {
            playAudio();
        }
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
    public void setText() {
        func1.setText("1: y(t) = " + String.format("%.3f", InsercaodeDados.amplitude) + " sin(2π" + String.format("%.3f", InsercaodeDados.freqaprox) + "t)");
        func2.setText("2: y(t) = " + String.format("%.3f", InsercaodeDados.amplitude) + " sin(2π" + String.format("%.3f", InsercaodeDados.freqafast) + "t)");
        double tempo = InsercaodeDados.time;
        sliderTempo.setMax(tempo);
        sliderTempo.setValue(tempo/2);
        tfTempo.setText(String.format("%.2f", tempo/2));
    }

    @FXML
    public void mudaValor() {
        sliderTempo.valueProperty().bindBidirectional(valorTempo);
        tfTempo.textProperty().bindBidirectional(valorTempo, new javafx.util.converter.NumberStringConverter());
        sliderTempo.valueProperty().addListener((observable, oldValue, newValue) -> {
            tfTempo.setText(String.valueOf(newValue.doubleValue()));
            atualizaY();
        });
    }

    private void atualizaY() {
        double resultado = InsercaodeDados.CalcFuncYt(sliderTempo.getValue());
        valorY.setText(String.valueOf(resultado));
    }

    @FXML
    public void populaLista() {
        List<String> data = getColunasBD();

        //converte lista em lista observavel
        ObservableList<String> observableList = FXCollections.observableArrayList(data);
        listaAudio.setItems(observableList);
    }

    private static List<String> getColunasBD() {
        List<String> data = new ArrayList<>();
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        try{
            ResultSet result = dao.execSelectAll();
            ResultSetMetaData resultmd = result.getMetaData();
            int colunas = resultmd.getColumnCount();

            while (result.next()) {
                StringBuilder linha = new StringBuilder();
                for (int i = 1; i <= colunas; i++) {
                    linha.append(resultmd.getColumnName(i)).append(": ").append(result.getString(i)).append("  ");
                }
                data.add(linha.toString());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    public void apagaArquivo() {
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        try{
            dao.execDelete(Integer.parseInt(ID.getText()));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void baixaArquivo() throws IOException {
        double[] temp = new double[4];
        String tNome = "";
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        try {
            ResultSet result = dao.execSelectID(Integer.parseInt(ID.getText()));
            dao.execSelectAudio(Integer.parseInt(ID.getText()));
            result.next();
            temp[0] = result.getDouble(2);
            temp[1] = result.getDouble(6);
            temp[2] = result.getDouble(3);
            temp[3] = result.getDouble(4);
            tNome = result.getString(11);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InsercaodeDados.CalcularFisicaDados(temp[0],temp[1],temp[2],temp[3],tNome);
        Stage stage = (Stage) buttonBaixar.getScene().getWindow();
        FrontApp.dadosAudio(stage, tNome);
    }
}