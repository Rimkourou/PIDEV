package entitie.hazem.share;

public class FilmTest {
    private int id;
    private String nom;

    public FilmTest() {
    }

    public FilmTest(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "FilmTest{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
