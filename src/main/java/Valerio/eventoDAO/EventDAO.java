package Valerio.eventoDAO;

import Valerio.entities.Book;
import Valerio.entities.CatalogElement;
import Valerio.entities.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

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


}
