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
           
        } catch(SQLException e){} // captura a exceção caso o banco já exista
        
        try(PreparedStatement stm = conexao.prepareStatement("USE Simulacoes")){
            stm.executeUpdate(); // Conexão com o banco
        }
    }
    
    
    
    // Criação das tabelas
    
    //Tabela de Emissores
    public void criaTabelaEmissor() throws SQLException{
        // Comando para criação da tabela
        String createTable = "create table Emissor( " +
                            "	nm_emissor int identity(1,1) primary key,  " +
                            "	Frequencia float not null, " +
                            "	Velocidade_Relativa float, " +
                            "	Potencia float not null, " +
                            "	Velocidade_Onda float not null )";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
            stm.executeUpdate();
         
        } catch (SQLException e){} // caso a tabela já exista
    }
    // Tabela de Ouvintes (Descontinuada)
//    public void criaTabelaOuvinte(){
//        String createTable = "CREATE TABLE Ouvinte("
//                            + "nm_ouvinte int identity(1,1) primary key,"
//                            + "Velocidade_ouvinte)";
//        
//        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
//            stm.executeUpdate();
//            
//        } catch (SQLException e){} // caso de a tabela já existir
//    }
    // Tabela de Simulações
    public void criaTabelaSimulacao() throws SQLException{
        String createTable = "create table Simulacao ( " +
                            "	Fk_Emissor_nm_emissor int not null " +
                            "		foreign key references Emissor, " +
                            "	Distancia float not null, " +
                            "   Tempo float not null, " +
                            "	Intensidade float not null, " +
                            "	Frequencia_final float not null, " +
                            "	Frequencia_inicial float not null, " +
                            "   NomeAudio varchar, " +
                            "	Audio varbinary(max), " +
                            "	primary key (Fk_Emissor_nm_emissor) )";
        
        try(PreparedStatement stm = conexao.prepareStatement(createTable)){
            stm.executeUpdate();
            
        } catch (SQLException e){} // caso de a tabela já existir
    }
    
    
    // Criação das triggers
    // Trigger de inserção de emissor
    public void criaInsertTriggerEmissor() throws SQLException{
        String createTrigger = "create or alter trigger tgr_Insert_Emissor on Emissor " +
                                "for insert as " +
                                "begin " +
                                "	declare @frequencia float = (select Frequencia from inserted) " +
                                "	declare @velocidadeRel float = (select Velocidade_Relativa from inserted) " +
                                "	declare @potencia float = (select Potencia from inserted) " +
                                "	declare @velocidadeOn float = (select Velocidade_Onda from inserted) " +
                                "	if (@frequencia < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@velocidadeRel < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@potencia < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@velocidadeOn < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "end";
         try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
            stm.executeUpdate();
        }   
    }
    // Trigger de inseção de simulação
    public void criaInsertTriggerSimulacao() throws SQLException{
        String createTrigger = "create or alter trigger tgr_Insert_Simulacao on Simulacao" +
                                "for insert as " +
                                "begin " +
                                "	declare @distancia float = (select Distancia from inserted) " +
                                "	declare @tempo float = (select Tempo from inserted) " +
                                "	declare @intensidade float = (select Intensidade from inserted) " +
                                "	declare @frequenciaFi float = (select Frequencia_final from inserted) " +
                                "	declare @frequenciaini float = (select Frequencia_inicial from inserted) " +
                                "	if (@distancia < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@tempo < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@intensidade < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@frequenciaFi < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "	if (@frequenciaini < 0) " +
                                "	begin " +
                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
                                "		Rollback tran " +
                                "		return " +
                                "	end " +
                                "end";
        try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
            stm.executeUpdate();   
        }
        
    }
//    public void criaInsertTriggerOuvinte() throws SQLException{
//        String createTrigger = "create or alter trigger tgr_Insert_Ouvinte on Ouvinte " +
//                                "for insert as " +
//                                "begin " +
//
//                                "	declare @VelocidadeOu float = (select Velocidade_ouvinte from inserted) " +
//
//                                "	if (@VelocidadeOu < 0) " +
//                                "	begin " +
//                                "		raiserror('Não é possível armazernar um valor negativo!', 1, 16) " +
//                                "		Rollback tran " +
//                                "		return " +
//                                "	end " +
//
//                                "end ";
//        try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
//            stm.executeUpdate();
//        }
//    }
    
    public void criaTriggerDeleteAll() throws SQLException{
        String createTrigger = "create or alter trigger tgr_Delete_All on Emissor " +
                                "instead of delete as " +
                                "begin " +
                                "	declare @nm int = (select nm_emissor from deleted) " +
                                "	delete from Simulacao where @nm = Fk_Emissor_nm_emissor " +
                                "	delete from Emissor where @nm = nm_emissor " +
                                "end ";
        try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
            stm.executeUpdate();
        }
    }
    
    public void criaInsertSpEmissor()throws SQLException{
        String createSP = "create or alter procedure sp_insert_Emissor (@frequencia float, @VelocidadeRel float, " +
                                "					     @potencia float, @VelocidadeOn float) as " +
                                "begin " +
                                "	insert into Emissor values (@frequencia, @VelocidadeRel, @potencia, @VelocidadeOn) " +
                                "end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }
    
//    public void criaInsertSpOuvinte() throws SQLException{
//        String createTrigger = "create or alter procedure sp_insert_Simulacao (@velocidadeOu float) as    " +
//                                "begin    " +
//                                "	insert into Ouvinte values (@velocidadeOu)    " +
//                                "end ";
//        try(PreparedStatement stm = conexao.prepareStatement(createTrigger)){
//            stm.executeUpdate();
//        }
//    }
    
     public void criaInsertSpSimulacao() throws SQLException{
        String createSP = "create or alter procedure sp_insert_Simulacao (@Distancia float, @Tempo float, @Intensidade float, @Frequenciafi float, @Frequenciaini float, @nome varchar, @audio varbinary(max)) as " +
                            "begin " +
                            "	declare @nm int  " +
                            "	set @nm = (select isnull(max(nm_emissor),1) from Emissor) " +
                            "	insert into Simulacao values (@nm, /*@nm,*/ @Distancia, @Tempo, @Intensidade, @Frequenciafi, @Frequenciaini, @nome, @audio) " +
                            "end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate(); 
        }
    }
    
    public void criaDeleteAllSp() throws SQLException{
        String createSP = "create or alter procedure sp_delete_all (@nm int) as begin delete from Emissor where nm_emissor = @nm end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }
    
    public void criaObjetosBD(){
        try{
            criaBanco();
            criaTabelaEmissor();
            criaTabelaSimulacao();
            criaInsertTriggerEmissor();
            criaInsertTriggerSimulacao();
            criaTriggerDeleteAll();
            criaInsertSpEmissor();
            criaInsertSpSimulacao();
            criaDeleteAllSp();
        } catch (SQLException e){System.out.print("ERRO: " + e.getMessage());}
        
    }
}
