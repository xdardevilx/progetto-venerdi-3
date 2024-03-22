package Valerio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Magazine extends CatalogElement {
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public Magazine() {
        super();
    }

    public Magazine(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicity periodicity) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicity = periodicity;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + getId() +
                ", codiceISBN='" + getCodiceISBN() + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPubblicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", periodicity=" + periodicity +
                '}';
    }
}
