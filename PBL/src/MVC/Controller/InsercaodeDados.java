package src.MVC.Controller;

import src.MVC.Model.DadosFisica;
import src.MVC.Model.GeraSom;

public class InsercaodeDados {
    public static void CalcularFisica(double frequencia_inicial, double distancia_inicial, double velocidade_relativa, double potencia, String nome_audio){
        
        //Insere os dados de física
        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);
        
        //Gera o áudio
        GeraSom.CriaAudio(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial), dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial), dados.getTempo(), dados.getNome_do_audio());

        //Parte de BD vai aqui abaixo:
        
    }
}
