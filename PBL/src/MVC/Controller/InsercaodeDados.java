package src.MVC.Controller;

import java.io.IOException;
import java.sql.SQLException;

import src.MVC.DAO.Conexao;
import src.MVC.DAO.SimulacaoDAO;
import src.MVC.Model.DadosFisica;
import src.MVC.Model.GeraSom;

public class InsercaodeDados {
    public static void CalcularFisica(double frequencia_inicial, double distancia_inicial, double velocidade_relativa, double potencia, String nome_audio){
        
        //Insere os dados de física
        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);
        
        //Gera o áudio
        GeraSom.CriaAudio(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial), dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial), dados.getTempo(), dados.getNome_do_audio());

        //Joga os valores de y(t) para um array de 1 em 1:
        dados.Calculafuncao(dados.getTempo());
        double [] yt = new double[dados.getFuncaodografico().length];
        yt = dados.getFuncaodografico();

        //ATENÇÃO MATEUS:
        //a partir da função acima deve-se printar os valores com um for no javaFX, por exemplo
        // y(t) = 0; x = 0
        // y(t) = 1.7654; x = 1
        // y(t) = 5.5676; x = 2
        // y(t) = 1.7867; x = 3
        // y(t) = 0; x = 4

        

        //Parte de BD vai aqui abaixo:
        try{
            SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
            dao.execProcedureInsertEmissor(dados.getFrequenciaInicial(), dados.getVelocidadeRelativa(), dados.getPotencia(), 340);
            dao.execProcedureInsertSimulacoes(dados.getDistanciaInicial(), dados.getTempo(), dados.getIntensidade(), dados.getFreqPercebidaAfast(), dados.getFreqPercebidaAprox(), dados.getNome_do_audio()+".wav"); 
        } catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
