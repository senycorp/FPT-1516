package fpt.com.component.database;

import fpt.com.core.component.database.BaseConnector;
import fpt.com.model.Product;

import java.sql.*;

/**
 * JDBC-Connector
 */
public class JDBCConnector extends BaseConnector {
    /**
     * Singleton-Holder
     */
    private static JDBCConnector instance = null;

    /**
     * Database-Connection
     */
    private Connection connection = null;

    /**
     * PreparedStatement
     */
    private PreparedStatement pst = null;

    /**
     * Resultset
     */
    private ResultSet rls = null;

    /**
     * Constructor
     */
    private JDBCConnector() {
        // Nothing to do
    }

    /**
     * Get singleton instance
     *
     * @return
     */
    public static JDBCConnector getInstance() {
        // Check for existing instance
        if (instance == null) {
            // Get database driver for postgresSQL
            try {
                // Load the driver
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Create and save the instance
            instance = new JDBCConnector();
        }

        return instance;
    }

    /**
     * Connect to Database
     *
     * @return
     */
    public boolean connect() {
        // TODO: Try-Ressource making some problems check it later!!!!
        try {
            this.connection = DriverManager.getConnection(
                    this.getUrl(),
                    this.getUsername(),
                    this.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();

            this.close();
            return false;
        }

        return true;
    }

    /**
     * Get connection
     *
     * @return
     */
    public Connection getConnection() {
        if (!this.isConnected()) {
            this.connect();
        }

        return this.connection;
    }


    @Override
    public Connection getJDBCConnection() {
        return this.getConnection();
    }

    @Override
    public long insert(String name, double price, int quantity) {
        long lastInsertedID = -1;

        try (PreparedStatement pstmt = this.getConnection().prepareStatement(
                "INSERT INTO products(name,price,quantity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                lastInsertedID = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastInsertedID;
    }

    @Override
    public void insert(fpt.com.Product product) {
        long id = this.insert(product.getName(), product.getPrice(), product.getQuantity());
        product.setId(id);
    }

    @Override
    public Product read(long id) {
        try (PreparedStatement stmt = this.getConnection().prepareStatement("SELECT id,name,price,quantity FROM products WHERE id=?");
             ResultSet rs = stmt.executeQuery()) {

            stmt.setLong(1, id);

            if (rs.next()) {
                return new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"));
            }

            stmt.close();
            rs.close();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product read() {
        if (this.pst == null) {
            try {
                this.pst = this.getConnection().prepareStatement(
                        "SELECT id,name,price,quantity FROM products WHERE TRUE");

                this.rls = this.pst.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                this.close();
            }
        }

        try {
            if (this.rls.next()) {
                return new Product(this.rls.getLong("id"), this.rls.getString("name"), this.rls.getDouble("price"), this.rls.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
        return null;
    }

    /**
     * Close all Database ressources
     */
    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (this.pst != null) {
                this.pst.close();
                this.pst = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (this.rls != null) {
                this.rls.close();
                this.rls = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if connection is active
     *
     * @return
     */
    public boolean isConnected() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get Url to database
     *
     * @return
     */
    public String getUrl() {
        return "jdbc:postgresql://java.is.uni-due.de/ws1011";
    }

    /**
     * Get username
     *
     * @return
     */
    public String getUsername() {
        return "ws1011";
    }

    /**
     * Get password
     *
     * @return
     */
    public String getPassword() {
        return "ftpw10";
    }
}
