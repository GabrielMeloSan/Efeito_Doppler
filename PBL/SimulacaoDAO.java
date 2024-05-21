import java.sql.*;

public class SimulacaoDAO {
    
    private final Connection conexao;
    
    // Construtor recebe como parâmetro a conexão criada
    public SimulacaoDAO(Connection conexao){
        this.conexao = conexao;
    }
    
    
    // Criação do Banco de Dados
    public void criaBanco() throws SQLException{
        String createDB = "CREATE DATABASE Simulacoes"; // comando para criação do banco
        
        try(PreparedStatement stm = conexao.prepareStatement(createDB)){ 
            stm.executeUpdate(); // executa o statement
            System.out.println("Banco criado"); // mensagem de sucesso
        } catch(SQLException e){} // captura a exceção caso o banco já exista
        
        try(PreparedStatement stm = conexao.prepareStatement("USE Simulacoes")){
            stm.executeUpdate(); // Conexão com o banco
        }
    }
    
    
    // Criação das tabelas
    
    //Tabela de Emissores
    public void criaTabelaEmissor(){
        // Comando para criação da tabela
        String createTable = "CREATE TABLE Emissor(" + 
                             "nm_emissor int identity(1,1) primary key," +
                             "Frequencia float not null," +
                             "Velocidade_Emissor float," +
                             "Potencia float not null," +
                             "Velocidade_Onda float not null)";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
            stm.executeUpdate();
         
        } catch (SQLException e){} // caso a tabela já exista
    }
    // Tabela de Ouvintes
    public void criaTabelaOuvinte(){
        String createTable = "CREATE TABLE Ouvinte("
                            + "nm_ouvinte int identity(1,1) primary key,"
                            + "Velocidade_ouvinte)";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
            stm.executeUpdate();
            
        } catch (SQLException e){} // caso de a tabela já existir
    }
    // Tabela de Simulações
    public void criaTabelaSimulacao(){
        String createTable = "CREATE TABLE Simulacao("
                            + "Fk_Emissor_nm_emissor int not null foreign key references Emissor,"
                            + "Fk_Ouvinte_nm_ouvinte int not null foreign key references Ouvinte,"
                            + "Distancia float not null,"
                            + "Frequencia_final float not null,"
                            + "Frequencia_inicial float not null,"
                            + "primary key (Fk_Emissor_nm_emissor, Fk_Ouvinte_nm_ouvinte))";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
            stm.executeUpdate();
            
        } catch (SQLException e){} // caso de a tabela já existir
    }
    
    // Criação das triggers
    public void criaTriggerEmissor(){
        String createTrigger = "create or alter trigger tgr_Insert_Emissor on Emissor " +
                                "for insert as " +
                                "begin " +
                                "	declare @frequencia float = (select Frequencia from inserted) " +
                                "	declare @velocidadeEm float = (select Velocidade_Emissor from inserted) " +
                                "	declare @potencia float = (select Potencia from inserted) " +
                                "	declare @velocidadeOn float = (select Velocidade_Onda from inserted) " +
                                "if (@frequencia < 0) " +
                                "begin " +
                                "	raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "	Rollback tran " +
                                "	return " +
                                "end " +
                                "if (@velocidadeEm < 0) " +
                                "begin " +
                                "	raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "	Rollback tran " +
                                "	return " +
                                "end " +
                                "if (@potencia < 0) " +
                                "begin " +
                                "	raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "	Rollback tran " +
                                "	return " +
                                "end " +
                                "if (@velocidadeOn < 0) " +
                                "begin " +
                                "	raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "	Rollback tran " +
                                "	return " +
                                "end " +
                                "end";
         try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
            stm.executeUpdate();
            
        } catch (SQLException e){} // caso de a trigger já existir
        
    }
}
