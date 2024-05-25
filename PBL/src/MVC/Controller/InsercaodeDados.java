package src.MVC.Controller;

import java.sql.SQLException;
import java.io.IOException;

import src.MVC.Model.DadosFisica;
import src.MVC.Model.GeraSom;
import src.MVC.DAO.Conexao;
import src.MVC.DAO.SimulacaoDAO;

public class InsercaodeDados {
    public static void CalcularFisica(double frequencia_inicial, double distancia_inicial, double velocidade_relativa, double potencia, String nome_audio) throws SQLException, IOException{
        
        //Insere os dados de física
        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);
        
        //Gera o áudio
        GeraSom.CriaAudio(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial), dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial), dados.getTempo(), dados.getNome_do_audio());

        //Parte de BD vai aqui abaixo:
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        dao.execProcedureInsertEmissor(dados.getFrequenciaInicial(), dados.getVelocidadeRelativa(), dados.getPotencia(), 340);
        dao.execProcedureInsertSimulacoes(dados.getDistanciaInicial(), dados.getTempo(), dados.getIntensidade(), dados.getFreqPercebidaAfast(), dados.getFreqPercebidaAprox(), dados.getNome_do_audio());    
    }
}
