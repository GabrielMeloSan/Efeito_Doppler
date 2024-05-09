package sqlconectar;

import java.sql.*;
import java.util.Scanner;

public class SQLConectar {
    
    static Scanner leitor = new Scanner(System.in);
    
    // Criação do banco
    static void criaBanco(Connection conexao){
        String createDB = "CREATE DATABASE Banco "; // comando da criação do banco
        
        // try() inicia o objeto PreparedStatement como os respectivos comandos e fecha-o após a execução do bloco 
        try(PreparedStatement stm = conexao.prepareStatement(createDB)){ 
            stm.executeUpdate(); // executa o statement
            System.out.println("Banco criado"); // mensagem de sucesso
        } catch(SQLException e){} // caso de o banco já existir
    }
    
    // Criação da tabela
    static void criaTabela(Connection conexao){
        String createTabela = "USE Banco " // comando da criação da tabela
                    + "CREATE TABLE Pessoa("
                    + "id int not null primary key,"
                    + "nome varchar(40),"
                    + "idade int) ";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTabela)){
            stm.executeUpdate();
            System.out.println("Tabela criada");
        } catch (SQLException e){} // caso de a tabela já existir
    }
    
    // insere registros na tabela Pessoa
    static void inserirDados(Connection conexao, int id, String nome, int idade) throws SQLException{
        String insertDados = "INSERT INTO Pessoa VALUES (?,?,?)"; // comando insert
        try (PreparedStatement stm = conexao.prepareStatement(insertDados)){
            // Inclusão dos dados nos campos
            stm.setInt(1, id); 
            stm.setString(2,nome);
            stm.setInt(3, idade); 
            stm.executeUpdate(); // Execução
        }
    }
    
    // Seleciona os dados da tabela Pessoa em um resultset
    static ResultSet selectDados(Connection conexao) throws SQLException{
        ResultSet rst;
        String selectTabela = "SELECT * FROM Pessoa"; // comando select
        
        try(PreparedStatement stm = conexao.prepareStatement(selectTabela)){
            rst = stm.executeQuery(); // execução e armazenamento dos resultados
        }
        return rst;
    }
    

    public static void main(String[] args) throws SQLException{
        
        Conexao conectar = new Conexao(); // instancia o objeto de conexão
        Connection conexao = conectar.getConnection(); // estabelece uma conexão com o servidor
        
        criaBanco(conexao); // cria o banco e a tabela caso não existam
        criaTabela(conexao);
        
        System.out.println("Escolha uma opção: \n[1] Cadastrar pessoa; \n[2] Exibir cadastros");
        String opcao = leitor.next();
        if (opcao.equals("1")){
            System.out.print("ID: ");
            int id = leitor.nextInt();
            leitor.nextLine();
            System.out.print("Nome: ");
            String nome = leitor.nextLine();
            System.out.print("Idade: ");
            int idade = leitor.nextInt();
            
            try{
                inserirDados(conexao, id, nome, idade);
            } catch (SQLException e){
                System.out.println("ERRO: " + e.getMessage());
            }
        } else if(opcao.endsWith("2")){
            // trabalharemos nisso
        }
                
        conexao.close();
        System.out.println("Conexão encerrada");
    }
}
