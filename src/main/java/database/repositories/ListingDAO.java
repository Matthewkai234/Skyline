package database.repositories;

import model.Listing;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import static util.HibernateUtil.getSessionFactory;

public class ListingDAO {

    public void updateListing(Listing listing) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // Begin transaction
            transaction = session.beginTransaction();

            // Ensure the listing exists
            Listing existingListing = session.get(Listing.class, listing.getId());
            if (existingListing == null) {
                System.out.println("No listing found with ID: " + listing.getId());
                return;
            }

            // Use session.merge to update the entity
            session.merge(listing);

            // Commit the transaction
            transaction.commit();
            System.out.println("Listing updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction in case of an error
            }
            e.printStackTrace();
        }
    }

    // Method to fetch a listing by its ID
    public Listing getListingById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            // Log session state for debugging
            System.out.println("Session opened: " + session.isOpen());

            Listing listing = session.get(Listing.class, id); // Retrieve the listing with the given ID
            if (listing == null) {
                System.out.println("Listing not found with ID: " + id);
            }
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to check if a listing exists
    public boolean isListingExists(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(Listing.class, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
