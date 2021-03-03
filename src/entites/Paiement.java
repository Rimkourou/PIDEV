package entites;

import java.util.Date;

public class Paiement {
    private int id;
    private String numCarte;
    private int code;
    private Date date;
    private String codePostale;

    public Paiement() {
    }

    public Paiement(int id) {
        this.id = id;
    }

    public Paiement(int id, String numCarte, int code, Date date, String codePostale) {
        this.id = id;
        this.numCarte = numCarte;
        this.code = code;
        this.date = date;
        this.codePostale = codePostale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", numCarte='" + numCarte + '\'' +
                ", code=" + code +
                ", date=" + date +
                ", codePostale='" + codePostale + '\'' +
                '}';
    }
}
