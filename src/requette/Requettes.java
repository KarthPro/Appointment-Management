package requette;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import base.Persistance;

public class Requettes {
	public static Boolean personne_inscription(Connection connection,Persistance p){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Nom de la personne:");
		String nom = sc.nextLine();
		System.out.println("Prenom de la personne:");
		String prenom = sc.nextLine();
		System.out.println("E-mail de la personne:");
		String email = sc.nextLine();
		System.out.println("Mode de passe de la personne:");
		String mdp = sc.nextLine();
		System.out.println("Est ce que le patientest un admin (Vrai/Faux):");
		String isAdmin_s = sc.nextLine();
		int isAdmin = 0; // par défaut pas un admin
		if (isAdmin_s.equalsIgnoreCase("Vrai"))
			isAdmin = 1;
		System.out.println("Categorie d'age de la personne:");
		int id_CategorieAge = sc.nextInt();
		System.out.println("Moyen connue de la personne:");
		int id_MoyenConnu = sc.nextInt();
		sc.close();
		
		return p.insert_Personne(p.getPersonne()+1, nom, prenom, email, mdp, isAdmin, id_CategorieAge, id_MoyenConnu);
	}

	public static Boolean seance_inscription(Connection connection,Persistance p){
		Scanner sc = new Scanner(System.in);
 
		int id = p.getSeance()+1;
		System.out.println("A quelle date aura lieux la seance (yyyy-[m]m-[d]d):");
		Date date = Date.valueOf(sc.next());
		System.out.println("Quel est le prix de la seance:");
		float prix = sc.nextFloat();
		System.out.println("Est ce que le patient etait en retard (Vrai/Faux):");
		String retard_s = sc.next();
		int retard = 0; // par défaut pas un admin
		if (retard_s.equalsIgnoreCase("Faux"))
			retard = 1;
		System.out.println("Quel patient attendra la seance:");
		int id_Personne = sc.nextInt();
		System.out.println("De quelle maniére sera payé la seance:");
		int id_TypePaiement = sc.nextInt();
		
		sc.close();
		
		return p.insert_seance(id, date, prix, retard, id_Personne, id_TypePaiement);
	}
	public static Boolean seance_modification(Connection connection,Persistance p){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Donné l'ID de la séance que vous souhaitez modifier:");
		int id_c = sc.nextInt();
		
		int id = p.getSeance()+1;
		System.out.println("Quelle est la nouvelle date de la seance:");
		Date date = Date.valueOf(sc.next());
		System.out.println("Quel est le nouveau prix de la seance:");
		float prix = sc.nextFloat();
		System.out.println("Est ce que le patient est en retard (Vrai/Faux):");
		String retard_s = sc.next();
		int retard = 0; // par défaut pas un admin
		if (retard_s.equalsIgnoreCase("Faux"))
			retard = 1;
		System.out.println("Quel patient attendra la seance:");
		int id_Personne = sc.nextInt();
		System.out.println("De quelle maniére sera payé la seance:");
		int id_TypePaiement = sc.nextInt();
		
		sc.close();
		
		String strsql = "UPDATE seance SET date= "+date+", prix= "+prix+", retard= "+retard+", id_Personne= "+id_Personne+",id_TypePaiement= "+id_TypePaiement+") WHERE id="+id_c;
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		try(FileWriter copy = new FileWriter("Model\\persistant\\seance_copy.txt");Scanner global = new Scanner(new File("Model\\persistant\\seance.txt"))){
			while (global.hasNextLine()){
				copy.write(global.next());
			}
			try(Scanner copyScanner = new Scanner(new File("Model\\persistant\\seance_copy.txt"));FileWriter original = new FileWriter("Model\\persistant\\seance.txt")){
				while (copyScanner.hasNextLine()){
					String line = copyScanner.nextLine();
					try (Scanner token1 = new Scanner(line); Scanner token = token1.useDelimiter(";")){
						int id_copy = token.nextInt();
						if (id_copy == id_c){
							original.write(id+";"+date+";"+prix+";"+retard+";"+id_Personne+";"+id_TypePaiement+";");
						}else{
							original.write(line);
						}
						token1.close();
					}
				}
			}catch (IOException e) {
				System.out.println("Erreur, impossible d'ouvrir 'seance.txt' ou 'seance_copy.txt'");
				e.printStackTrace();
				return false;
			}
		} catch (IOException e1) {
			System.out.println("Erreur, impossible d'ecrire dans 'seance.txt' ou 'seance_copy.txt''");
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public static Boolean seance_suppression(Connection connection,Persistance p){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Donné l'ID de la séance que vous souhaitez modifier:");
		int id_c = sc.nextInt();
		sc.close();
		
		String strsql = "CREATE OR REPLACE VIEW delete_patient (IdPatient) AS DELETE FROM seance WHERE id=" + id_c;
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		try(FileWriter copy = new FileWriter("Model\\persistant\\seance_copy.txt");Scanner global = new Scanner(new File("Model\\persistant\\seance.txt"))){
			while (global.hasNextLine()){
				copy.write(global.next());
			}
			try(Scanner copyScanner = new Scanner(new File("Model\\persistant\\seance_copy.txt"));FileWriter original = new FileWriter("Model\\persistant\\seance.txt")){
				int nb_supr = 0;
				while (copyScanner.hasNextLine()){
					String line = copyScanner.nextLine();
					try (Scanner token1 = new Scanner(line); Scanner token = token1.useDelimiter(";")){
						int id_copy = token.nextInt();
						if (id_copy == id_c){
							break;
						}else{
							id_copy -= nb_supr;
							Date date = Date.valueOf(token.next());
							Float prix = token.nextFloat();
							Integer retard = token.nextInt();
							int id_Personne = token.nextInt();
							int id_TypePaiement = token.nextInt();
							original.write(id_copy+";"+date+";"+prix+";"+retard+";"+id_Personne+";"+id_TypePaiement+";");
							original.write(line);
						}
						token1.close();
					}
				}
			}catch (IOException e) {
				System.out.println("Erreur, impossible d'ouvrir 'seance.txt' ou 'seance_copy.txt'");
				e.printStackTrace();
				return false;
			}
		} catch (IOException e1) {
			System.out.println("Erreur, impossible d'ecrire dans 'seance.txt' ou 'seance_copy.txt''");
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean aficher_seance_date(Connection connection,Persistance p){
		Scanner sc = new Scanner(System.in);
		System.out.println("De quelle date voulez le planning:");
		Date date = Date.valueOf(sc.next());
		sc.close();
		
		String strsql = "SELECT * FROM seance WHERE date=" + date;
		try (Statement statement = connection.createStatement();ResultSet resultset = statement.executeQuery(strsql)){
			while(resultset.next()){
				int id = resultset.getInt("id");
				String name = resultset.getString("date");
				float prix = resultset.getFloat("prix");
				Boolean retard = resultset.getBoolean("retard");
				int id_Personne = resultset.getInt("id_Personne");
				int id_TypePaiement = resultset.getInt("id_TypePaiement");
				System.out.println("id: " +id + ", nom: " + name + ", prix: " + prix + ", retard: " + retard + ", id patient: " + id_Personne + ", id type de payment: " + id_TypePaiement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static Boolean afficher_seance_patient(Connection connection,Persistance p, int IdPatient){
		String strsql = "SELECT * FROM seance WHERE id_Personne=" + IdPatient;
		try (Statement statement = connection.createStatement();ResultSet resultset = statement.executeQuery(strsql)){
			while(resultset.next()){
				int id = resultset.getInt("id");
				String name = resultset.getString("date");
				float prix = resultset.getFloat("prix");
				Boolean retard = resultset.getBoolean("retard");
				int id_Personne = resultset.getInt("id_Personne");
				int id_TypePaiement = resultset.getInt("id_TypePaiement");
				System.out.println("id: " +id + ", nom: " + name + ", prix: " + prix + ", retard: " + retard + ", id patient: " + id_Personne + ", id type de payment: " + id_TypePaiement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


}
