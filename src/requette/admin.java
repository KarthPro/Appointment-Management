package requette;

import java.sql.Connection;
import java.util.Scanner;

import base.Persistance;

public class admin {

	public admin(Connection connection, Persistance p, int id) {
		Boolean loop = true;
		Scanner sc = new Scanner(System.in);
		while (loop){
			System.out.println("Que voulez vous faire : ");
			System.out.println("- 1) Consulter planing");
			System.out.println("- 2) Créer patient ");
			System.out.println("- 3) Créer séance ");
			System.out.println("- 4) Modifier séance ");
			System.out.println("- 5) Supprimer séance ");
			System.out.println("- 6) Se deconnecter ");
			
			String aws = sc.nextLine();
			
			switch (aws){
			case "1":
				System.out.println("Donné l'id du patient dont vous souhaitez voir les séances:");
				id = sc.nextInt();
				Requettes.afficher_seance_patient(connection, p, id);
				break;
			case "2":
				Requettes.personne_inscription(connection, p);
				break;
			case "3":
				Requettes.seance_inscription(connection, p);
				break;
			case "4":
				Requettes.seance_modification(connection, p);
				break;
			case "5":
				Requettes.seance_suppression(connection, p);
				break;
			case "6":
				loop = false;
				sc.close();
				break;
			default:
				System.out.println("Veulliez entrer 1, 2 ou 3.");
				break;
			}
		}
		sc.close();
	}
}
