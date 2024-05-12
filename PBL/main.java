//arquivo de testes


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite a sua opção:\n 1- Modulo de calculo \n 2- Modulo de BD");
        int opcao = leitor.nextInt();

        switch (opcao) {
            case 1:
                SenoeCosseno();
                break;
            case 2:
                BD();
            default:
                System.out.println("Erro");
                break;
        }
        
    }

    static void SenoeCosseno(){
        SenoECosseno calc = new SenoECosseno();

        System.out.println("Inserir valor de seno e cosseno em rad: ");

        Scanner scn = new Scanner(System.in);

        double x = scn.nextDouble();

        //precisao arbitraria de 0.0001
        System.out.println("Seno: " + Double.toString(calc.CalculaSeno(x, 0.0001)));
        System.out.println("Coseno: " + Double.toString(calc.CalculaCosseno(x, 0.0001)));
    }

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
    static void selectDados(Connection conexao) throws SQLException{
        ResultSet rst;
        String selectTabela = "SELECT id,nome,idade FROM Pessoa"; // comando select
        
        try(PreparedStatement stm = conexao.prepareStatement(selectTabela)){
            rst = stm.executeQuery(); // execução e armazenamento dos resultados
            while (rst.next()){       // o ResultSet só pode ser usado enquanto seu statement não for fechado 
                System.out.print("ID: " + rst.getInt("id"));
                System.out.print("| Nome: " + rst.getString("nome"));
                System.out.print("| Idade: " + rst.getInt("idade") + "\n");
            }
        }
    }

    static void BD(){
        Scanner leitor = new Scanner(System.in);

        Conexao conectar = new Conexao(); // instancia o objeto de conexão
        Connection conexao = conectar.getConnection(); // estabelece uma conexão com o servidor
        
        criaBanco(conexao); // cria o banco e a tabela caso não existam
        criaTabela(conexao);
        
        while(true){
            System.out.println("Escolha uma opção: \n[1] Cadastrar pessoa; \n[2] Exibir cadastros; \n[0] Sair");
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
            } else if(opcao.equals("2")){
                try{
                    selectDados(conexao);
                } catch (SQLException e) {
                    System.out.println("ERRO: " + e.getMessage());
                }
            } else if(opcao.equals("0")){
                try{
                    conexao.close();
                    System.out.println("Conexão encerrada");
                } catch (SQLException e){
                    System.out.println("ERRO: " + e.getMessage());
                }
                break;
            } else System.out.println("Digite uma opcao válida.");
        }
                
        
    }
}
