package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Persistance {
	private Connection connection;
	private static int Bilan_PostSeance = 0;
	private static int CategorieAge = 0;
	private static int MoyenConnu = 0;
	private static int Personne = 0;
	private static int profession = 0;
	private static int seance = 0;
	private static int travaille = 0;
	private static int TypePaiement = 0;
	
	
	
	public Persistance(Connection connection){
		this.connection = connection;
		fill_Bilan_PostSeance();
		fill_CategorieAge();
		fill_MoyenConnu();
		fill_Personne();
		fill_profession();
		fill_seance();
		fill_travaille();
		fill_TypePaiement();
	}
	
	public Boolean insert_Bilan_PostSeance(int id, String expression, String posture, String comportement, int id_seance){
		String strsql = "INSERT INTO Bilan_PostSeance(id, expression, posture, comportement, id_seance) VALUES ("+id+",\""+expression+"\",\""+posture+"\",\""+comportement+"\",\""+id_seance +"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_Bilan_PostSeance( id, expression, posture, comportement, id_seance);
		return true;
	}
	public Boolean insert_CategorieAge(int id, String nomCategorie){
		String strsql = "INSERT INTO CategorieAge(id, nomCategorie) VALUES ("+id+",\""+nomCategorie+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_CategorieAge( id, nomCategorie);
		return true;
	}
	public Boolean insert_MoyenConnu(int id, String intitule){
		String strsql = "INSERT INTO MoyenConnu(id, intitule) VALUES ("+id+",\""+intitule+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_MoyenConnu( id, intitule);
		return true;
	}
	public Boolean insert_Personne(int id, String nom, String prenom, String email, String mdp, int isAdmin, int id_CategorieAge, int id_MoyenConnu){
		String strsql = "INSERT INTO Personne(id, nom, prenom,email, mdp, isAdmin, id_CategorieAge, id_MoyenConnu) VALUES ("+id+",\""+nom+"\",\""+prenom+"\",\""+email+"\",\""+mdp+"\",\""+isAdmin+"\",\""+id_CategorieAge+"\",\""+id_MoyenConnu+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_Personne( id, nom, prenom, email, mdp, isAdmin, id_CategorieAge, id_MoyenConnu);
		return true;
	}
	public Boolean insert_profession(int id, String nomProfession){
		String strsql = "INSERT INTO profession( id, nomProfession) VALUES ("+id+",\""+nomProfession+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_profession( id, nomProfession);
		return true;
	}
	public Boolean insert_seance(int id, Date date, Float prix, Integer retard, int id_Personne, int id_TypePaiement){
		String strsql = "INSERT INTO seance( id, date, prix, retard, id_Personne, id_TypePaiement) VALUES ("+id+",\""+date+"\",\""+prix+"\",\""+retard+"\",\""+id_Personne+"\",\""+id_TypePaiement+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_seance( id, date, prix, retard, id_Personne, id_TypePaiement);
		return true;
	}
	public Boolean insert_travaille(int id, int id_Profession, Date date){
		String strsql = "INSERT INTO travaille( id, id_Profession, date) VALUES ("+id+"\",\""+id_Profession+"\",\""+date+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_travaille( id, id_Profession, date);
		return true;
	}
	public Boolean insert_TypePaiement(int id, String type){
		String strsql = "INSERT INTO TypePaiement( id, type) VALUES ("+id+",\""+type+"\")";
		try (Statement statement = connection.createStatement()){
			statement.executeUpdate(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		add_TypePaiement( id, type);
		return true;
	}

	public Boolean add_Bilan_PostSeance(int id, String expression, String posture, String comportement, int id_seance){
		try (FileWriter fw = new FileWriter("Model\\persistant\\Bilan_PostSeance.txt", true)){
			fw.write(id+";"+expression+";"+posture+";"+comportement+";"+id_seance+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_CategorieAge(int id, String nomCategorie){
		try (FileWriter fw = new FileWriter("Model\\persistant\\CategorieAge.txt", true)){
			fw.write(id+";"+nomCategorie+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_MoyenConnu(int id, String intitule){
		try (FileWriter fw = new FileWriter("Model\\persistant\\MoyenConnu.txt", true)){
			fw.write(id+";"+intitule+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_Personne(int id, String nom, String prenom, String email, String mdp, int isAdmin, int id_CategorieAge, int id_MoyenConnu){
		try (FileWriter fw = new FileWriter("Model\\persistant\\Personne.txt", true)){
			fw.write(id+";"+nom+";"+prenom+";"+email+";"+mdp+";"+isAdmin+";"+id_CategorieAge+";"+id_MoyenConnu+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_profession(int id, String nomProfession){
		try (FileWriter fw = new FileWriter("Model\\persistant\\profession.txt", true)){
			fw.write(id+";"+nomProfession+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_seance(int id, Date date, Float prix, Integer retard, int id_Personne, int id_TypePaiement){
		try (FileWriter fw = new FileWriter("Model\\persistant\\seance.txt", true)){
			fw.write(id+";"+date+";"+prix+";"+retard+";"+id_Personne+";"+id_TypePaiement+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_travaille(int id, int id_Profession, Date date){
		try (FileWriter fw = new FileWriter("Model\\persistant\\travaille.txt", true)){
			fw.write(id+";"+id_Profession+";"+date+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean add_TypePaiement(int id, String type){
		try (FileWriter fw = new FileWriter("Model\\persistant\\TypePaiement.txt", true)){
			fw.write(id+";"+type+";");
			fw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean fill_Bilan_PostSeance(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\Bilan_PostSeance.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String expression = token.next();
					String posture = token.next();
					String comportement = token.next();
					int id_seance = token.nextInt();
					insert_Bilan_PostSeance( id, expression, posture, comportement, id_seance);
					Bilan_PostSeance++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_CategorieAge(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\CategorieAge.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String nomCategorie = token.next();
					insert_CategorieAge( id, nomCategorie);
					CategorieAge++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_MoyenConnu(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\MoyenConnu.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String intitule = token.next();
					insert_MoyenConnu( id, intitule);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_Personne(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\Personne.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String nom = token.next();
					String prenom = token.next();
					String email = token.next();
					String mdp = token.next();
					int isAdmin = token.nextInt();
					int id_CategorieAge = token.nextInt();
					int id_MoyenConnu = token.nextInt();
					insert_Personne( id, nom, prenom, email, mdp, isAdmin, id_CategorieAge, id_MoyenConnu);
					Personne++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_profession(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\profession.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String nomProfession = token.next();
					insert_profession( id, nomProfession);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_seance(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\seance.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					Date date = Date.valueOf(token.next());
					Float prix = Float.valueOf(token.next());
					Integer retard = token.nextInt();
					int id_Personne = token.nextInt();
					int id_TypePaiement = token.nextInt();
					insert_seance( id, date, prix, retard, id_Personne, id_TypePaiement);
					seance++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_travaille(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\travaille.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					int id_Profession = token.nextInt();
					Date date = Date.valueOf(token.next());
					insert_travaille( id, id_Profession, date);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Boolean fill_TypePaiement(){
		try(Scanner global = new Scanner(new File("Model\\persistant\\TypePaiement.txt"))){
			while (global.hasNextLine()){
				try(Scanner token = new Scanner(global.nextLine()).useDelimiter(";")){
					int id = token.nextInt();
					String type = token.next();
					insert_TypePaiement( id, type);
					TypePaiement++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public int getBilan_PostSeance() {
		return Bilan_PostSeance;
	}

	public void setBilan_PostSeance(int bilan_PostSeance) {
		Bilan_PostSeance = bilan_PostSeance;
	}

	
	public int getCategorieAge() {
		return CategorieAge;
	}

	public void setCategorieAge(int categorieAge) {
		CategorieAge = categorieAge;
	}

	
	public int getMoyenConnu() {
		return MoyenConnu;
	}

	public void setMoyenConnu(int moyenConnu) {
		MoyenConnu = moyenConnu;
	}

	
	public int getPersonne() {
		return Personne;
	}

	public void setPersonne(int personne) {
		Personne = personne;
	}

	
	public int getProfession() {
		return profession;
	}

	public void setProfession(int profession) {
		Persistance.profession = profession;
	}

	
	public int getSeance() {
		return seance;
	}

	public void setSeance(int seance) {
		Persistance.seance = seance;
	}

	
	public int getTravaille() {
		return travaille;
	}

	public void setTravaille(int travaille) {
		Persistance.travaille = travaille;
	}

	
	public int getTypePaiement() {
		return TypePaiement;
	}

	public void setTypePaiement(int typePaiement) {
		TypePaiement = typePaiement;
	}
	
	
}
