package mvc.controller;

import java.io.IOException;
import java.sql.SQLException;

import mvc.dao.Conexao;
import mvc.dao.SimulacaoDAO;
import mvc.model.DadosFisica;
import mvc.model.GeraSom;
import mvc.model.SenoECosseno;

public class InsercaodeDados {
    public static double time;
    public static double amplitude;
    public static double freqaprox;
    public static double freqafast;
    public static String nomAud;
    public static void CalcularFisica(double frequencia_inicial, double distancia_inicial, double velocidade_relativa, double potencia, String nome_audio){
        
        //Insere os dados de física
        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);
        dados.CalculaTempoSimulacao();
        dados.CalculaIntensidade();
        dados.Calculafuncao(dados.getTempo());

        //Gera o áudio
        GeraSom.CriaAudio(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial), dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial), dados.getTempo(), dados.getNome_do_audio());

        time = dados.getTempo();
        amplitude = dados.CalculaAmplitude(potencia, distancia_inicial);
        freqaprox = dados.getFreqPercebidaAprox();
        freqafast = dados.getFreqPercebidaAfast();
        nomAud = dados.getNome_do_audio();

        // y(t) = A sin (2pift)
        System.out.println("1ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " sin(2π" + Double.toString(dados.getFreqPercebidaAprox()) + "t)" );
        System.out.println("2ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " sin(2π" + Double.toString(dados.getFreqPercebidaAfast()) + "t)" );

        //Parte de BD vai aqui abaixo:
        try{
            SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
            dao.execProcedureInsertEmissor(dados.getFrequenciaInicial(), dados.getVelocidadeRelativa(), dados.getPotencia(), 340);
            dao.execProcedureInsertSimulacoes(dados.getDistanciaInicial(), dados.getTempo(), dados.getIntensidade(), dados.getFreqPercebidaAfast(), dados.getFreqPercebidaAprox(), dados.getNome_do_audio());
        } catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	// Este método calcula os dados de fisica sem gerar um novo áudio ou gravar no BD; Usado ao baixar uma simulação armazenada no BD (FrontController)
    public static void CalcularFisicaDados(double frequencia_inicial, double distancia_inicial, double velocidade_relativa, double potencia, String nome_audio){

        //Insere os dados de física
        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);
        dados.CalculaTempoSimulacao();
        dados.CalculaIntensidade();
        dados.Calculafuncao(dados.getTempo());

        time = dados.getTempo();
        amplitude = dados.CalculaAmplitude(potencia, distancia_inicial);
        freqaprox = dados.getFreqPercebidaAprox();
        freqafast = dados.getFreqPercebidaAfast();
        nomAud = dados.getNome_do_audio();

        // y(t) = A sin (2pift)
        System.out.println("1ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " sin(2π" + Double.toString(dados.getFreqPercebidaAprox()) + "t)" );
        System.out.println("2ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " sin(2π" + Double.toString(dados.getFreqPercebidaAfast()) + "t)" );

    }
    public static double CalcFuncYt(double t){
        double y;
        SenoECosseno sen = new SenoECosseno();

        if(t>(time/2)){
            y = (amplitude  * (sen.CalculaSeno((2*Math.PI) * freqafast * t)));
        }else if (t<(time/2)){
            y = (amplitude  * (sen.CalculaSeno((2*Math.PI) * freqaprox * t)));
        }else{
            y = 0;
        }
        return y;
    }
}
