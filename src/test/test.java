package test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class test {

	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("Model//conf.properties")){
			props.load(fis);
		}
		Class.forName(props.getProperty("jdbc.driver.class"));
		
		String url = props.getProperty("jdbc.url");
		String login = props.getProperty("jdbc.login");
		String password = props.getProperty("jdbc.password");
		try (Connection connection = DriverManager.getConnection(url,login,password)){
			String StrSQL2 = "SELECT * FROM countries WHERE REGION_ID = 3";
			try (Statement statement = connection.createStatement();ResultSet resultset = statement.executeQuery(StrSQL2)){
				while(resultset.next()){
					String id = resultset.getString("COUNTRY_ID");
					String name = resultset.getString("COUNTRY_NAME");
					int re_id = resultset.getInt(3);
					System.out.println(id + " " + name + " " + re_id);
				}
			}
		}
	}
}