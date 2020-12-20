package requette;

import java.sql.Connection;
import java.util.Scanner;

import base.Persistance;

public class regular {
	public regular(Connection connection, Persistance p, int id){
		Boolean loop = true;
		Scanner sc = new Scanner(System.in);
		while (loop){
			System.out.println("Que voulez vous faire : ");
			System.out.println("- 1) Consulter planing ");
			System.out.println("- 2) Se déconnecter ");
			
			String aws = sc.nextLine();
			
			switch (aws){
			case "1":
				Requettes.afficher_seance_patient(connection, p, id);
				break;
			case "2":
				loop = false;
				sc.close();
				break;
			default:
				System.out.println("Veulliez entrer 1, 2 ou 3.");
				break;
			}
		}
	}
}
