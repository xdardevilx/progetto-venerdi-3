package Valerio.eventoDAO;

import Valerio.entities.Book;
import Valerio.entities.CatalogElement;
import Valerio.entities.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

public class EventDAO {
    private EntityManager em;

    public EventDAO(EntityManager em) {
        this.em = em;
    }

    public void save(CatalogElement element) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(element);
            tx.commit();
            System.out.println("Salvataggio avvenuto con successo");

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("errore nel salvataggio dell'elemento" + " " + e.getMessage());
            }
        }
    }

    public void deleteByISBN(String isbn) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CatalogElement element = em.createQuery("SELECT e FROM CatalogElement e WHERE e.codiceISBN = :isbn", CatalogElement.class).setParameter("isbn", isbn).getSingleResult();
            if (element != null) {
                em.remove(element);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("errore nell'eliminazione dell'elemento" + " " + e.getMessage());
            }
        }
    }

    public Book findBookByISBN(String codiceISBN) {
        try {
            Book book = em.createQuery("SELECT b FROM Book b WHERE b.codiceISBN = :isbn", Book.class)
                    .setParameter("isbn", codiceISBN)
                    .getSingleResult();
            System.out.println("Ricerca del libro tramite ISBN avvenuta con successo");
            return book;
        } catch (NoResultException e) {
            System.out.println("Nessun libro trovato con l'ISBN specificato");
            return null;
        }
    }

    public Magazine findMagazineByISBN(String codiceISBN) {
        try {
            Magazine magazine = em.createQuery("SELECT m FROM Magazine m WHERE m.codiceISBN = :isbn", Magazine.class)
                    .setParameter("isbn", codiceISBN)
                    .getSingleResult();
            System.out.println("Ricerca della rivista tramite ISBN avvenuta con successo");
            return magazine;
        } catch (NoResultException e) {
            System.out.println("Nessuna rivista trovata con l'ISBN specificato");
            return null;
        }
    }


    public List<Book> findBooksByPublicationYear(int year) {
        try {
            List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.annoPubblicazione = :year", Book.class)
                    .setParameter("year", year)
                    .getResultList();

            if (!books.isEmpty()) {
                System.out.println("Ricerca dei libri per anno di pubblicazione avvenuta con successo");
                return books;
            } else {
                System.out.println("--------------------------------");
                System.out.println("Nessun libro trovato per l'anno di pubblicazione specificato");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca dei libri per anno di pubblicazione: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Magazine> findMagazinesByPublicationYear(int year) {
        try {
            List<Magazine> magazines = em.createQuery("SELECT m FROM Magazine m WHERE m.annoPubblicazione = :year", Magazine.class)
                    .setParameter("year", year)
                    .getResultList();

            if (!magazines.isEmpty()) {
                System.out.println("Ricerca delle riviste per anno di pubblicazione avvenuta con successo");
                return magazines;
            } else {
                System.out.println("Nessuna rivista trovata per l'anno di pubblicazione specificato");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.out.println("Errore durante la ricerca delle riviste per anno di pubblicazione: " + e.getMessage());
            return Collections.emptyList();
        }
    }


}
