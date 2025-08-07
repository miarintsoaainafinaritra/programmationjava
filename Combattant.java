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
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nomDeCombattant = nomDeCombattant;
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
    public List<String> getTitres() { return titres; }
    public int getVictoires() { return victoires; }
    public int getDefaites() { return defaites; }
    public int getEgalites() { return egalites; }

    public void ajouterVictoire() { victoires++; }
    public void ajouterDefaite() { defaites++; }
    public void ajouterEgalite() { egalites++; }
    public void ajouterTitre(String titre) { titres.add(titre); }

    @Override
    public String toString() {
        return nomDeCombattant + " (" + prenom + " " + nom + ")";
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
        if (!termine) pointsCombattant1 += points;
    }

    public void ajouterPointsCombattant2(int points) {
        if (!termine) pointsCombattant2 += points;
    }


    public abstract void terminer();


    protected void determinerResultat() {
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


class MatchAmical extends Match {
    public MatchAmical(String id, String date, String endroit, Combattant combattant1, Combattant combattant2) {
        super(id, date, endroit, combattant1, combattant2);
    }

    @Override
    public void terminer() {

        super.determinerResultat();
    }
}

class MatchOfficiel extends Match {
    public MatchOfficiel(String id, String date, String endroit, Combattant combattant1, Combattant combattant2) {
        super(id, date, endroit, combattant1, combattant2);
    }

    @Override
    public void terminer() {

        super.determinerResultat();
    }
}

class MatchPourTitre extends Match {
    private String titre;

    public MatchPourTitre(String id, String date, String endroit, Combattant combattant1, Combattant combattant2, String titre) {
        super(id, date, endroit, combattant1, combattant2);
        this.titre = titre;
    }

    @Override
    public void terminer() {
        super.determinerResultat();


        if (getPointsCombattant1() > getPointsCombattant2()) {
            getCombattant1().ajouterTitre(titre);
        } else if (getPointsCombattant2() > getPointsCombattant1()) {
            getCombattant2().ajouterTitre(titre);
        }

    }
}


class LigueDeCombat {
    private String nom;
    private List<Combattant> combattants;
    private List<Match> matchs;

    public LigueDeCombat(String nom) {
        this.nom = nom;
        this.combattants = new ArrayList<>();
        this.matchs = new ArrayList<>();
    }


    public String getNom() { return nom; }
    public List<Combattant> getCombattants() { return combattants; }
    public List<Match> getMatchs() { return matchs; }


    public void ajouterCombattant(Combattant combattant) {
        combattants.add(combattant);
    }

    public void ajouterMatch(Match match) {
        matchs.add(match);
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
        ArrayList<Combattant> combattants = new ArrayList<>();
        Combattant c1 = new Combattant("1", "Doe", "John", "Fighter1", 77.0);

    }
}