//deletado a parte "package PBL;"


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String URL = "jdbc:sqlserver://localhost:1433;databaseName=master;encrypt=false;trustServerCertificate=true";
    private String user = "sa";
    private String senha = "123456";

    public Connection getConnection(){
        Connection conexao = null;
        try{
            Class.forName(driver);
            conexao = DriverManager.getConnection(URL, user, senha);
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida!");
                SimulacaoDAO sml = new SimulacaoDAO(conexao);
                sml.criaObjetosBD();
            } else {
                System.out.println("Não foi possível conectar ao banco de dados.");
            }
        } catch(ClassNotFoundException e){
            System.out.println("Driver JDBC não encontrado. \nERRO: " + e.getMessage());
            e.printStackTrace();
        } catch(SQLException e){
            System.out.println("Não foi possível conectar ao servidor. \nERRO: " + e.getMessage());
            e.printStackTrace();
        }
        return conexao;
    }
}
