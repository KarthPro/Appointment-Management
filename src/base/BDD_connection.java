package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import requette.admin;
import requette.regular;

public class BDD_connection {

	public static void main(String[] args){
		
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("Model//conf.properties")){
			props.load(fis);
		} catch (IOException e) {
			System.out.println("Il y a un probléme avec le fichier conf.properties, la base de donnée ne peut-etre lancé.");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			Class.forName(props.getProperty("jdbc.driver.class"));
		} catch (ClassNotFoundException e1) {
			System.out.println("Il y a un probléme avec jdbc.driver.class, la base de donnée ne peut-etre lancé.");
			e1.printStackTrace();
			System.exit(2);
		}

		// Changé pour un systeme de mot de passe
		String url = props.getProperty("jdbc.url");
		String login = props.getProperty("jdbc.login");
		String password = props.getProperty("jdbc.password");
		
		
		try (Connection connection = DriverManager.getConnection(url,login,password)){
			System.out.println("Connection avec la base de données établie."); //espace de travail
			Persistance p = new Persistance(connection);
			System.out.println("Table remplis."); //Remplit les tables et crée une interface permetant de stocké de maniére durable les données
			
			Scanner sc = new Scanner(System.in);
			Boolean loop = true;
			String username;
			String mdp;
			while(loop){
				System.out.println("Que voulez vous faire : ");
				System.out.println("- 1) Se connecter ");
				System.out.println("- 2) Quitter ");
				String aws = sc.nextLine();
				
				switch (aws){
				case "1":
					System.out.println("E-mail de l'utilisateur :");
					username = sc.nextLine();
					System.out.println("Mot de passe de l'utilisateur :");
					mdp = sc.nextLine();

					String strsql = "SELECT * FROM Personne WHERE email = \"" + username + "\" and mdp = \"" + mdp +"\"";
					try (Statement statement = connection.createStatement();ResultSet resultset = statement.executeQuery(strsql)){
						if (resultset.next()){
							if (resultset.getBoolean("isAdmin")){
								@SuppressWarnings("unused")
								admin a = new admin(connection,p,resultset.getInt("id"));
							}else{
								@SuppressWarnings("unused")
								regular r = new regular(connection,p,resultset.getInt("id"));
							}
						}else{
							System.out.println("Email utilisateur ou mot de passe incorrect");
						}
					}
					break;
				case"2":
					System.out.println("Etes-vous sur de vouloir quiter (oui/non):");
					if (sc.nextLine().equals("oui")){
						System.out.println("Aurevoir");
						System.exit(0);
					}
					break;
				default:
					System.out.println("Veulliez entrer 1 ou 2.");
					break;
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}