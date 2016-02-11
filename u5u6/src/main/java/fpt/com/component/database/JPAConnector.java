package fpt.com.component.database;

import fpt.com.core.component.database.BaseConnector;
import fpt.com.model.Product;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Connection;
import java.util.List;

/**
 * Aufgabe 3d:
 *
 * Eine Konfigurationsdatei hat den Nachteil, dass alle Informationen lesbar abgelegt werden. Dies birgt
 * ein hohes Sicherheitsrisiko, da auch das Passwort hier abgelegt wird.
 *
 * Aufgabe 3e:
 *
 * TODO: Do not forget this here
 *
 */

/**
 * OpenJPAConnector
 */
public class JPAConnector extends BaseConnector {

    /**
     * Singleton-Holder
     */
    private static JPAConnector instance = null;

    /**
     * EntityManagerFactory
     */
    private EntityManagerFactory entityManagerFactory = null;

    /**
     * EntitiyManager
     */
    private EntityManager entityManager = null;

    /**
     * ResultSet for products
     */
    private List<Product> productsResult = null;

    /**
     * Iterator counter
     */
    private int productsCounter = 0;

    /**
     * Constructor
     */
    private JPAConnector() {

    }

    /**
     * Get singleton instance
     *
     * @return
     */
    public static JPAConnector getInstance() {
        // Check for existing instance
        if (instance == null) {
            // Create and save the instance
            instance = new JPAConnector();
        }

        return instance;
    }

    /**
     * Connect to Database
     *
     * @return
     */
    public boolean connect() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        this.entityManager = this.entityManagerFactory.createEntityManager();
        //this.entityManager.getTransaction().begin();

        return true;
    }

    /**
     * Get connection
     *
     * @return
     */
    public EntityManager getConnection() {
        if (!this.isConnected()) {
            this.connect();
        }

        return this.entityManager;
    }

    @Override
    public Connection getJDBCConnection() {
        this.getConnection();
//        this.entityManager.getTransaction().begin();
        Connection c = (Connection) OpenJPAPersistence.cast(this.entityManager).getConnection();
        return c;
    }

    public long insert(String name, double price, int quantity) {
        int lastInsertedID = -1;

        Product product = new Product(0, name, price, quantity);
        this.getConnection().getTransaction().begin();

        this.getConnection().persist(product);

        this.getConnection().getTransaction().commit();

        this.close();

        System.out.print(product.getId() +" "+product.getIdDB());
        return  product.getId();
    }

    public void insert(fpt.com.Product product) {
        long id = this.insert(product.getName(), product.getPrice(), product.getQuantity());
        product.setId(id);
    }

    public Product read(long id) {

        Query q = this.getConnection().createQuery("SELECT c FROM Product c WHERE  c.id = "+id);
        List<Product> products = (List<Product>) q.getResultList();

        if (products.size() > 0)
            return products.get(0);

        this.close();

        return null;
    }

    public Product read() {
        if (productsResult == null) {
            Query q = this.getConnection().createQuery("SELECT c FROM Product c ORDER BY c.idDB DESC ", Product.class);
            q.setMaxResults(10);
            this.productsResult = (List<Product>) q.getResultList();
        }

        if (this.productsResult.size() > this.productsCounter) {
            System.out.println(this.productsResult.get(this.productsCounter).getIdDB());
            return this.productsResult.get(this.productsCounter++);
        } else {
            this.productsResult = null;
            this.productsCounter = 0;
            this.close();

            return null;
        }
    }

    /**
     * Check if connection is active
     *
     * @return
     */
    public boolean isConnected() {
        if (this.entityManager == null || this.entityManagerFactory == null || !this.entityManager.isOpen() )
            return false;

        return true;
    }

    @Override
    public void close() {
        if (this.entityManager != null) {
            this.entityManager.close();
        }

        if (this.entityManagerFactory != null) {
            this.entityManagerFactory.close();
        }
    }
}
