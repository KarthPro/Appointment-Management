package base;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Trigger {
	
	public static Boolean nb_rdz_j(Connection connection,Persistance p){		
		String strsql = "CREATE TRIGGER before_insert_seance BEFORE INSERT ON seance FOR EACH ROW" + 
		" DECLARE nb_seance_psy INT DEFAULT nb_seance_patient INT DEFAULT BEGIN SELECT SUM(*) INTO nb_seance_psy" + 
				" FROM seance WHERE ( ( date BETWEEN TRUNC(NEW.date, DATE) AND TRUNC(NEW.date+1, DATE)" + 
				" SELECT SUM(*) INTO nb_seance_patient  FROM seance WHERE ( ( date BETWEEN TRUNC(NEW.date, DATE)"+ 
				" AND TRUNC(NEW.date+1, DATE) ) AND id_Personne = NEW.id_Personne IF (nb_seance_psy >= 20)" + 
				" THEN RAISE_APPLICATION_ERROR(-20501, 'Insertion impossible : la psy travaille deja 10h ce jour ')" + 
				" ELSEIF (nb_seance_patient >= 3) THEN RAISE_APPLICATION_ERROR" + 
				"(-20501, 'Insertion impossible : le patient a plus de 3 seance le même jour ') END IF END ";
		try (Statement statement = connection.createStatement();){
				statement.execute(strsql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
