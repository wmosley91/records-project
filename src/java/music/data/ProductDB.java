package music.data;

import java.sql.*;
import java.util.*;

import music.business.*;

public class ProductDB {

    //This method returns null if a product isn't found.
    public static Product selectProduct(String productCode) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product "
                + "WHERE ProductCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("ProductID"));
                p.setCode(rs.getString("ProductCode"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static Product selectProduct(long productID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product "
                + "WHERE ProductID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("ProductID"));
                p.setCode(rs.getString("ProductCode"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static List<Product> selectProducts() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setCode(rs.getString("ProductCode"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    //This method updates the Available column on the Product Table, DAVE STARTING HERE IS WHERE I ADDED THE CODE
    public static void updateAvailabe(Invoice inv)
    {
        List<LineItem> lineItems = inv.getLineItems();
        for (LineItem item : lineItems) {
                int quantity = item.getQuantity();
                Product p = item.getProduct();
                Long productID = p.getId();
                int availableQuantity = getAvailable(productID);
                
                int newAvailable = availableQuantity - quantity;
                
                ConnectionPool pool = ConnectionPool.getInstance();
                Connection connection = pool.getConnection();
                PreparedStatement ps = null;

                String query = "UPDATE product SET "
                                + "Available = ? "
                                + "WHERE ProductID = ?";

                try
                {
                    ps = connection.prepareStatement(query);
                    ps.setInt(1, newAvailable);
                    ps.setLong(2, productID);
                    ps.executeUpdate();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                finally
                {
                    DBUtil.closePreparedStatement(ps);
                    pool.freeConnection(connection);
                }
            }
    }
    
    public static int getAvailable(Long productID)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int availQuantity = 0;

        String query = "SELECT Available FROM Product "
                + "WHERE ProductID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                availQuantity = rs.getInt("Available");
                return availQuantity;
            } else {
                return availQuantity;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return availQuantity;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}