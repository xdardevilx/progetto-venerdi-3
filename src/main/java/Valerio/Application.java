package Valerio;

import Valerio.entities.Book;
import Valerio.entities.Magazine;
import Valerio.entities.Periodicity;
import Valerio.eventoDAO.EventDAO;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalelibreria");
        EntityManager em = emf.createEntityManager();
        EventDAO dao = new EventDAO(em);

// -----------SALVO DEI LIBRI RANDOM NEL DB-------------

        for (int i = 0; i < 5; i++) {
            try {
                Faker faker = new Faker();
                String codiceISBN = faker.code().isbn13();
                String titolo = faker.book().title();
                int annoPubblicazione = faker.number().numberBetween(1900, 2023); // Da 1900 a 2022 incluso
                int numeroPagine = faker.number().numberBetween(50, 1000);
                String autore = faker.book().author();
                String genere = faker.book().genre();
                Book book = new Book(codiceISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);

//                dao.save(book);
            } catch (Exception e) {
                System.out.println("errore nel salvataggio" + " " + e.getMessage());
            }
        }

//        ----------SALVO DEI MAGAZINE RANDOM NEL DB-------------


        for (int i = 0; i < 5; i++) {

            try {
                Faker faker1 = new Faker();

                String codiceISBN = faker1.code().isbn13();
                String titolo = faker1.book().title();
                int annoPubblicazione = faker1.number().numberBetween(1900, 2023); // Da 1900 a 2022 incluso
                int numeroPagine = faker1.number().numberBetween(50, 200);
                Periodicity periodicita = Periodicity.values()[faker1.random().nextInt(Periodicity.values().length)];

                Magazine magazine = new Magazine(codiceISBN, titolo, annoPubblicazione, numeroPagine, periodicita);

//                dao.save(magazine);
            } catch (Exception e) {
                System.out.println("errore nel salvataggio" + " " + e.getMessage());
            }
        }


//        --------------EFFETTUO LA RICERCA TRAMITE ISBN MAGAZINE--------------
        try {
            Magazine magazine = dao.findMagazineByISBN("9780595279746");
            System.out.println(magazine.toString());
        } catch (Exception e) {
            System.out.println("errore nella ricerca" + " " + e.getMessage());
        }

//        --------------EFFETTUO LA RICERCA TRAMITE ISBN MAGAZINE--------------

        try {
            Book book = dao.findBookByISBN("9790960711504");
            System.out.println(book.toString());
        } catch (Exception e) {
            System.out.println("errore nella ricerca" + " " + e.getMessage());
        }

        
//        try {
//        dao.deleteByISBN("9781687582447");
//            System.out.println("eliminazione tramite ISBN avvenuta con successo");
//        } catch (Exception e) {
//            System.out.println("errore nella cancellazione" + " " + e.getMessage());
//
//        }


        em.close();


    }
}