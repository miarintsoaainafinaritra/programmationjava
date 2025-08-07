import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombattantTest {
    @Test
    void testCombattantCreation() {
        Combattant c = new Combattant("1", "Doe", "John", "The Destroyer", 85.5);
        assertEquals("The Destroyer", c.getNomDeCombattant());
        assertEquals(0, c.getVictoires());
        assertEquals(0, c.getDefaites());
        assertEquals(0, c.getEgalites());
        assertTrue(c.getTitres().isEmpty());
    }

    @Test
    void testMatchAmical() {
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);

        MatchAmical match = new MatchAmical("m1", "2023-01-01", "Las Vegas", c1, c2);

        match.ajouterPointsCombattant1(10);
        match.ajouterPointsCombattant2(8);
        match.terminer();

        assertEquals(1, c1.getVictoires());
        assertEquals(1, c2.getDefaites());
        assertFalse(c1.getTitres().contains("Champion"));
    }

    @Test
    void testMatchOfficiel() {
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);

        MatchOfficiel match = new MatchOfficiel("m2", "2023-01-02", "New York", c1, c2);

        match.ajouterPointsCombattant1(9);
        match.ajouterPointsCombattant2(10);
        match.terminer();

        assertEquals(1, c2.getVictoires());
        assertEquals(1, c1.getDefaites());
        assertTrue(c2.getTitres().isEmpty()); 
    }

    @Test
    void testMatchPourTitre() {
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);

        MatchPourTitre match = new MatchPourTitre("m3", "2023-01-03", "Paris", c1, c2, "Champion Poids Moyen");

        match.ajouterPointsCombattant1(10);
        match.ajouterPointsCombattant2(9);
        match.terminer();

        assertEquals(1, c1.getVictoires());
        assertEquals(1, c2.getDefaites());
        assertTrue(c1.getTitres().contains("Champion Poids Moyen"));
        assertEquals(0, c2.getTitres().size());
    }

    @Test
    void testEgaliteMatchPourTitre() {
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);

        MatchPourTitre match = new MatchPourTitre("m4", "2023-01-04", "Tokyo", c1, c2, "Champion Poids Moyen");

        match.ajouterPointsCombattant1(10);
        match.ajouterPointsCombattant2(10);
        match.terminer();

        assertEquals(1, c1.getEgalites());
        assertEquals(1, c2.getEgalites());
        assertEquals(0, c1.getTitres().size()); 
        assertEquals(0, c2.getTitres().size());
    }

    @Test
    void testLigueGetMatchsParCombattant() {
        LigueDeCombat ufc = new LigueDeCombat("UFC");

        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);
        Combattant c3 = new Combattant("3", "Johnson", "Mike", "Fighter3", 77.0);

        ufc.ajouterCombattant(c1);
        ufc.ajouterCombattant(c2);
        ufc.ajouterCombattant(c3);

        Match m1 = new MatchAmical("m1", "2023-01-01", "Las Vegas", c1, c2);
        Match m2 = new MatchOfficiel("m2", "2023-01-02", "New York", c1, c3);
        Match m3 = new MatchPourTitre("m3", "2023-01-03", "Paris", c2, c3, "Champion");

        ufc.ajouterMatch(m1);
        ufc.ajouterMatch(m2);
        ufc.ajouterMatch(m3);

        List<Match> matchsC1 = ufc.getMatchsParCombattant("1");
        assertEquals(2, matchsC1.size());

        List<Match> matchsC2 = ufc.getMatchsParCombattant("2");
        assertEquals(2, matchsC2.size());

        List<Match> matchsC3 = ufc.getMatchsParCombattant("3");
        assertEquals(2, matchsC3.size());
    }

    @Test
    void testAjouterPointsApresMatchTermine() {
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Jane", "Fighter2", 77.0);

        Match match = new MatchOfficiel("m1", "2023-01-01", "Las Vegas", c1, c2);

        match.ajouterPointsCombattant1(5);
        match.terminer();
        match.ajouterPointsCombattant1(5); 

        assertEquals(5, match.getPointsCombattant1());
    }
}
