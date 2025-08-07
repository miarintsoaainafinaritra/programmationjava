import java.util.ArrayList;
import java.util.List;

class Combattant {
    private String id;
    private String nom;
    private String prenom;
    private String nomDeCombattant;
    private double poids;
    private List<String> titres;
    private int victoires;
    private int defaites;
    private int egalites;

    public Combattant(String id, String nom, String prenom, String nomDeCombattant, double poids) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("ID ne peut pas être vide");
        if (nom == null || nom.isEmpty()) throw new IllegalArgumentException("Nom ne peut pas être vide");
        if (prenom == null || prenom.isEmpty()) throw new IllegalArgumentException("Prénom ne peut pas être vide");
        if (poids <= 0) throw new IllegalArgumentException("Poids doit être positif");

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nomDeCombattant = (nomDeCombattant != null && !nomDeCombattant.isEmpty()) ?
                nomDeCombattant : prenom + " " + nom;
        this.poids = poids;
        this.titres = new ArrayList<>();
        this.victoires = 0;
        this.defaites = 0;
        this.egalites = 0;
    }


    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNomDeCombattant() { return nomDeCombattant; }
    public double getPoids() { return poids; }
    public List<String> getTitres() { return new ArrayList<>(titres); }
    public int getVictoires() { return victoires; }
    public int getDefaites() { return defaites; }
    public int getEgalites() { return egalites; }


    public void ajouterVictoire() { victoires++; }
    public void ajouterDefaite() { defaites++; }
    public void ajouterEgalite() { egalites++; }

    public void ajouterTitre(String titre) {
        if (titre != null && !titre.isEmpty()) {
            titres.add(titre);
        }
    }

    @Override
    public String toString() {
        return nomDeCombattant + " (" + prenom + " " + nom + ") - Poids: " + poids + "kg";
    }
}

abstract class Match {
    private String id;
    private String date;
    private String endroit;
    private Combattant combattant1;
    private Combattant combattant2;
    private int pointsCombattant1;
    private int pointsCombattant2;
    private boolean termine;

    public Match(String id, String date, String endroit, Combattant combattant1, Combattant combattant2) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("ID ne peut pas être vide");
        if (combattant1 == null || combattant2 == null) throw new IllegalArgumentException("Combattants ne peuvent pas être null");
        if (combattant1.equals(combattant2)) throw new IllegalArgumentException("Un combattant ne peut pas se battre contre lui-même");

        this.id = id;
        this.date = date;
        this.endroit = endroit;
        this.combattant1 = combattant1;
        this.combattant2 = combattant2;
        this.pointsCombattant1 = 0;
        this.pointsCombattant2 = 0;
        this.termine = false;
    }


    public String getId() { return id; }
    public String getDate() { return date; }
    public String getEndroit() { return endroit; }
    public Combattant getCombattant1() { return combattant1; }
    public Combattant getCombattant2() { return combattant2; }
    public int getPointsCombattant1() { return pointsCombattant1; }
    public int getPointsCombattant2() { return pointsCombattant2; }
    public boolean isTermine() { return termine; }

    public void ajouterPointsCombattant1(int points) {
        if (!termine && points > 0) {
            pointsCombattant1 += points;
        }
    }

    public void ajouterPointsCombattant2(int points) {
        if (!termine && points > 0) {
            pointsCombattant2 += points;
        }
    }

    public abstract void terminer();

    protected void determinerResultat() {
        if (!termine) {
            if (pointsCombattant1 > pointsCombattant2) {
                combattant1.ajouterVictoire();
                combattant2.ajouterDefaite();
            } else if (pointsCombattant2 > pointsCombattant1) {
                combattant2.ajouterVictoire();
                combattant1.ajouterDefaite();
            } else {
                combattant1.ajouterEgalite();
                combattant2.ajouterEgalite();
            }
            termine = true;
        }
    }
}

class MatchAmical extends Match {
    public MatchAmical(String id, String date, String endroit, Combattant combattant1, Combattant combattant2) {
        super(id, date, endroit, combattant1, combattant2);
    }

    @Override
    public void terminer() {
        if (!isTermine()) {
            determinerResultat();
            System.out.println("Match amical terminé!");
        }
    }
}

class MatchOfficiel extends Match {
    public MatchOfficiel(String id, String date, String endroit, Combattant combattant1, Combattant combattant2) {
        super(id, date, endroit, combattant1, combattant2);
    }

    @Override
    public void terminer() {
        if (!isTermine()) {
            determinerResultat();
            System.out.println("Match officiel terminé! Les statistiques ont été mises à jour.");
        }
    }
}

class MatchPourTitre extends Match {
    private String titre;

    public MatchPourTitre(String id, String date, String endroit, Combattant combattant1, Combattant combattant2, String titre) {
        super(id, date, endroit, combattant1, combattant2);
        if (titre == null || titre.isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        this.titre = titre;
    }

    @Override
    public void terminer() {
        if (!isTermine()) {
            determinerResultat();
            if (getPointsCombattant1() > getPointsCombattant2()) {
                getCombattant1().ajouterTitre(titre);
            } else if (getPointsCombattant2() > getPointsCombattant1()) {
                getCombattant2().ajouterTitre(titre);
            }
            System.out.println("Match pour le titre " + titre + " terminé!");
        }
    }
}

class LigueDeCombat {
    private String nom;
    private List<Combattant> combattants;
    private List<Match> matchs;

    public LigueDeCombat(String nom) {
        if (nom == null || nom.isEmpty()) throw new IllegalArgumentException("Le nom de la ligue ne peut pas être vide");
        this.nom = nom;
        this.combattants = new ArrayList<>();
        this.matchs = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public List<Combattant> getCombattants() { return new ArrayList<>(combattants); }
    public List<Match> getMatchs() { return new ArrayList<>(matchs); }

    public void ajouterCombattant(Combattant combattant) {
        if (combattant != null && !combattants.contains(combattant)) {
            combattants.add(combattant);
        }
    }

    public void ajouterMatch(Match match) {
        if (match != null && !matchs.contains(match)) {
            matchs.add(match);
        }
    }

    public List<Match> getMatchsParCombattant(String combattantId) {
        List<Match> resultats = new ArrayList<>();
        for (Match match : matchs) {
            if (match.getCombattant1().getId().equals(combattantId) ||
                    match.getCombattant2().getId().equals(combattantId)) {
                resultats.add(match);
            }
        }
        return resultats;
    }

    public static void main(String[] args) {
        LigueDeCombat ligue = new LigueDeCombat("Ultimate Fighting Championship");

        Combattant c1 = new Combattant("1", "Doe", "John", "The Destroyer", 77.0);
        Combattant c2 = new Combattant("2", "Smith", "Mike", "Iron Mike", 80.5);

        ligue.ajouterCombattant(c1);
        ligue.ajouterCombattant(c2);

        MatchAmical matchAmical = new MatchAmical("M1", "2023-05-15", "Las Vegas", c1, c2);
        matchAmical.ajouterPointsCombattant1(10);
        matchAmical.ajouterPointsCombattant2(8);
        matchAmical.terminer();
        ligue.ajouterMatch(matchAmical);

        System.out.println("Statistiques après match:");
        System.out.println(c1.getNomDeCombattant() + " - Victoires: " + c1.getVictoires());
        System.out.println(c2.getNomDeCombattant() + " - Défaites: " + c2.getDefaites());
    }
}
