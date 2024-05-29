package src.MVC.View;
//arquivo de testes


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import src.MVC.DAO.Conexao;
import src.MVC.DAO.SimulacaoDAO;
import src.MVC.Model.DadosFisica;
import src.MVC.Model.GeraSom;
import src.MVC.Model.SenoECosseno;

public class main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite a sua opção:\n 1- Modulo de calculo \n 2- Grafico \n 3- Gerar som \n 4- TESTE DE FÍSICA \n 5- Listar todas as simulações \n 6- Recuperar áudio de uma simulação \n 7- Deletar uma simulação");
        int opcao = leitor.nextInt();

        switch (opcao) {
            case 1:
                SenoeCosseno();;
                break;
            case 2:
                System.out.println("A opção não funciona mais");
                break;
            case 3:
                System.out.println("A opção não funciona mais");
                break;
            case 4:
                TesteFisica();
                break;
            case 5:                
                //Listar Simulações
                ListarTudo();
                break;
            case 6:
                //Recuperar áudio
                SalvarAudio();
                break;
            case 7:
                //Apagar simulação
                DeletarSimulacao();
                break;
            default:
                System.out.println("Erro");
                break;
        }
        
    }

    static void TesteFisica(){

        //valores a serem inseridos: frequencia da fonte, velocidade relativa, distancia inicial entre a fonte e observador, tempo da simulação, nome do audio e potência
        Scanner scn = new Scanner(System.in);

        System.out.println("Insira a frequencia da fonte (hz): ");
        double frequencia_inicial = scn.nextDouble();
        
        System.out.println("Insira a Velocidade relativa entre a fonte e o observador (m/s): ");
        double velocidade_relativa = scn.nextDouble();

        double valor_minimo = 5 * velocidade_relativa;

        System.out.println("Insira a Distancia inicial entre a fonte e o observador (m): " + "[valor mínimo: " + Double.toString(velocidade_relativa) + "]" );
        double distancia_inicial = scn.nextDouble();

        System.out.println("Insira o nome do audio: ");
        String nome_audio = scn.next();

        System.out.println("Insira a potência da fonte(W): ");
        double potencia = scn.nextDouble();

        DadosFisica dados = new DadosFisica(frequencia_inicial, distancia_inicial, velocidade_relativa, potencia, nome_audio);

        System.out.println("\n\n\n\nFrequencia aprox: " + Double.toString(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial)));

        System.out.println("Frequencia afast: " + Double.toString(dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial)));

        dados.CalculaTempoSimulacao();
        System.out.println("Tempo: " + Double.toString(dados.getTempo()) + "s");

        dados.CalculaIntensidade();
        System.out.println("Intensidade inicial: " + Double.toString(dados.getIntensidade()) + "W/m²");

        GeraSom gerar = new GeraSom();

        gerar.CriaAudio(dados.CalculaFrequenciaAprox(velocidade_relativa, frequencia_inicial), dados.CalculaFrequenciaAfast(velocidade_relativa, frequencia_inicial), dados.getTempo(), dados.getNome_do_audio());

        System.out.println("Audio gerado!");
        
        //parte do grafico
        System.out.println("Grafico:");

        System.out.println("1ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " * sin(2pi * " + Double.toString(dados.getFreqPercebidaAprox()) + "t)" );
        System.out.println("2ª Função: y(t) = " + Double.toString(dados.CalculaAmplitude(potencia, distancia_inicial)) + " * sin(2pi * " + Double.toString(dados.getFreqPercebidaAfast()) + "t)" );

        try{
            SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
            dao.execProcedureInsertEmissor(dados.getFrequenciaInicial(), dados.getVelocidadeRelativa(), dados.getPotencia(), 340);
            dao.execProcedureInsertSimulacoes(dados.getDistanciaInicial(), dados.getTempo(), dados.getIntensidade(), dados.getFreqPercebidaAfast(), dados.getFreqPercebidaAprox(), dados.getNome_do_audio()+".wav");
            System.out.println("Dados resgitrados no banco.");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        
    }
    
    static void ListarTudo(){
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        try{
            ResultSet result = dao.execSelectAll();
            while (result.next()) {
                System.out.print("ID: " + result.getInt(1) + " | ");
                System.out.print("Frequência da fonte: " + result.getDouble(2) + " | ");
                System.out.print("Vel. relativa: " + result.getDouble(3) + " | ");
                System.out.print("Potencia: " + result.getDouble(4) + " | ");
                System.out.print("Vel. da onda: " + result.getDouble(5) + " | ");
                System.out.print("Distância inicial: " + result.getDouble(6) + " | ");
                System.out.print("Tempo: " + result.getDouble(7) + " | ");
                System.out.print("Intensidade: " + result.getDouble(8) + " | ");
                System.out.print("Freq. aproximação: " + result.getDouble(9) + " | ");
                System.out.print("Freq. afastamento: " + result.getDouble(10) + " | ");
                System.out.print("Nome do Audio: " + result.getString(11) + "\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    static void SalvarAudio(){
        Scanner leitor = new Scanner(System.in);
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        System.out.print("Digite o id da simulação: ");
        try{
            dao.execSelectAudio(leitor.nextInt());
            System.out.println("Arquivo salvo com sucesso");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    static void DeletarSimulacao(){
        Scanner leitor = new Scanner(System.in);
        SimulacaoDAO dao = new SimulacaoDAO(Conexao.getConnection());
        System.out.print("Digite o id da simulação: ");
        try{
            dao.execDelete(leitor.nextInt());
            System.out.println("Simulação deletada");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    static void SenoeCosseno(){
        SenoECosseno calc = new SenoECosseno();

        System.out.println("Inserir valor de seno e cosseno em rad: ");

        Scanner scn = new Scanner(System.in);

        double x = scn.nextDouble();

        //precisao arbitraria de 0.0001
        System.out.println("Seno: " + Double.toString(calc.CalculaSeno(x)));
    }

}
