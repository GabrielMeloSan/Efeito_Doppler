package mvc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            System.out.println("Banco Criado");
        } catch(SQLException e){} // captura a exceção caso o banco já exista
        
        try(PreparedStatement stm = conexao.prepareStatement("USE Simulacoes")){
            stm.executeUpdate(); // Começa a usar o banco
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
                            "   NomeAudio varchar(40), " +
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
        String createTrigger = "create or alter trigger tgr_Insert_Simulacao on Simulacao " +
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
    
    // Trigger que deleta a simulacao correspondente ao emissor deletado
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
    
    
    // Criação das Stored Procedures
    // SP para inserir na tabela emissor
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
    
    // SP para inserir na tabela simulacao 
    public void criaInsertSpSimulacao() throws SQLException{
        String createSP = "create or alter procedure sp_insert_Simulacao (@Distancia float, @Tempo float, @Intensidade float, @Frequenciafi float, @Frequenciaini float, @nomeA varchar(40), @audio varbinary(max)) as " +
                            "begin " +
                            "	declare @nm int  " +
                            "	set @nm = (select isnull(max(nm_emissor),1) from Emissor) " +
                            "	insert into Simulacao values (@nm, /*@nm,*/ @Distancia, @Tempo, @Intensidade, @Frequenciafi, @Frequenciaini, @nomeA, @audio) " +
                            "end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate(); 
        }
    }
    
    // SP para apagar um registro de emissor; trigger apaga simulacao correspondente
    public void criaDeleteAllSp() throws SQLException{
        String createSP = "create or alter procedure sp_delete_all (@nm int) as begin delete from Emissor where nm_emissor = @nm end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }
    
    // SP para selecionar todas as simulações registradas
    public void criaSelectAllSp() throws SQLException{
        String createSP = "create or alter procedure sp_Select_All as begin " +
                            "	select e.*, s.Distancia, s.Tempo, s.Intensidade, s.Frequencia_inicial, s.Frequencia_final, s.NomeAudio from Emissor e " +
                            "	left join Simulacao s on s.Fk_Emissor_nm_emissor = e.nm_emissor " +
                            "end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }

    public void criaSelectIDSp() throws SQLException{
        String createSP = "create or alter procedure sp_Select_ID (@id int) as begin " +
                "select e.*, s.Distancia, s.Tempo, s.Intensidade, s.Frequencia_inicial, s.Frequencia_final, s.NomeAudio from Emissor e " +
                "left join Simulacao s on s.Fk_Emissor_nm_emissor = e.nm_emissor " +
                "where e.nm_emissor = @id " +
                "end ";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }

    // SP para selecionar um áudio e seu nome
    public void criaSelectAudio() throws SQLException{
        String createSP = "create or alter procedure sp_Select_Audio (@id int) as begin " +
                            "	select NomeAudio, Audio from Simulacao " +
                            "	where Fk_Emissor_nm_emissor = @id " +
                            "end";
        try(PreparedStatement stm = conexao.prepareStatement(createSP)){
            stm.executeUpdate();
        }
    }
    
    
    // Inicialização dos componentes do BD
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
            criaSelectAllSp();
            criaSelectIDSp();
            criaSelectAudio();
        } catch (SQLException e){System.out.print("ERRO: " + e.getMessage());}
    }
    
    // Insere um emissor na tabela emissor
    public void execProcedureInsertEmissor(double frequencia, double velocidadeRel, double potencia, double velocidadeOn)throws SQLException
    {
        String execSP = "exec sp_insert_Emissor ?, ?, ?, ? ";
        
        // inclusão dos dados no statement
        try (PreparedStatement stm = conexao.prepareStatement(execSP)){
            stm.setDouble(1, frequencia);
            stm.setDouble(2, velocidadeRel);
            stm.setDouble(3, potencia);
            stm.setDouble(4, velocidadeOn);
            stm.executeUpdate();
        }
        
    }
    
    // Insere uma simulacao na tabela simulacao
    public void execProcedureInsertSimulacoes(double distancia, double tempo, double intensidade, double Frequenciafi, double Frequenciaini, String nomeA) throws SQLException, IOException{
        String execSP = "exec sp_insert_Simulacao  ?, ?, ?, ?, ?, ?, ? ";

        FileInputStream arquivo = new FileInputStream(nomeA + ".wav"); // Cria um inputstream do arquivo de audio
        
        try (PreparedStatement stm = conexao.prepareStatement(execSP)){
            stm.setDouble(1, distancia);
            stm.setDouble(2, tempo);
            stm.setDouble(3, intensidade);
            stm.setDouble(4, Frequenciafi);
            stm.setDouble(5, Frequenciaini);
            stm.setString(6, nomeA);
            stm.setBinaryStream(7, arquivo);
            
            stm.executeUpdate();
        }
        
    }
    // Deleta o registro das tabelas com o registro informado
    public void execDelete (int numeroSimulacao) throws SQLException{
        String execDelete = "exec sp_delete_all ?";
        try(PreparedStatement stm = conexao.prepareStatement(execDelete)){
           stm.setInt(1, numeroSimulacao);
           stm.executeUpdate();
        }
    }
    
    // Imprime os dados de todas as simulações
    public ResultSet execSelectAll() throws SQLException{
        String execSP = "exec sp_Select_All";
        PreparedStatement stm = conexao.prepareStatement(execSP);
        ResultSet result = stm.executeQuery();        
        return result;
    }

    public ResultSet execSelectID(int id) throws SQLException{
        String execSP = "exec sp_Select_ID ?";
        PreparedStatement stm = conexao.prepareStatement(execSP);
        stm.setInt(1,id);
        ResultSet result = stm.executeQuery();
        return result;
    }
            
    // Busca e salva o áudio de uma simulaçao no PC
    public void execSelectAudio(int id) throws SQLException, IOException{
        String execSP = "exec sp_Select_Audio ?";
        ResultSet resultado;
        try(PreparedStatement stm = conexao.prepareStatement(execSP)){
            stm.setInt(1, id);
            resultado = stm.executeQuery();
            resultado.next();
            String nomeArq = resultado.getString(1);
            byte[] audio = resultado.getBytes(2);
            OutputStream arquivo = new FileOutputStream(nomeArq + ".wav");
            arquivo.write(audio);
        }
    }


 
}