package connection;

import java.sql.*;
import sun.security.krb5.Config;

/**
 *
 * @author Diego
 */
public class MySqlConnect {

	private static String status = "unconnected";
	private ConnectionConfig config;
	private Connection connection;
	private static String installation_file = "/opt/lampp/bin/";

	public MySqlConnect() {
		config = new ConnectionConfig("localhost", "konnen-db-0-1-0", "", "");
	}

	public MySqlConnect(String serverName, String database, String username, String password) {
		config = new ConnectionConfig(serverName, serverName, serverName, serverName);
	}

	public String getStatus() {
		return status;
	}

	public Connection connect() {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			connection = DriverManager.getConnection(config.getUrl(), config.username, config.password);

			if (connection != null) {
				status = "conected";
			} else {
				status = "error";
			}

			return connection;
		} catch (ClassNotFoundException e) {
			System.out.println("driver not loaded");
			return null;
		} catch (SQLException e) {
			System.out.println("database not found");
			return null;
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}

	public boolean close() {
		try {
			this.connect().close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Connection reset() {
		this.close();
		return this.connect();
	}

	public ResultSet exec(String query) {
		Statement statement;

		try {
			statement = connection.createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	public int update(String query) {
		Statement statement;

		try {
			statement = connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return 0;
		}
	}

	public void executeSqlFile(String path) {
		try {
			Runtime rt = Runtime.getRuntime();
			String executeSqlCommand = this.installation_file + "mysql --user="+ config.username +" --password=" + config.password + " -h " + config.serverName + " " + config.database + " < " + path;
			Process pr = rt.exec(executeSqlCommand);
			int exitVal = pr.waitFor();
			
			if(exitVal != 0){
				System.out.println("Exited with error code " + exitVal + ", commanda: " + executeSqlCommand);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	protected class ConnectionConfig {

		public String serverName, database, username, password;
		//caminho do servidor do BD, nome do BD, nome do usuário, senha do usuário.

		public String getUrl() {
			return "jdbc:mysql://" + serverName + "/" + database;
		}

		public ConnectionConfig(String serverName, String database, String username, String password) {
			this.serverName = serverName;
			this.database = database;
			this.username = username;
			this.password = password;
		}
	}
}
