/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import dal.OrderDAO;
import java.sql.Connection;
import java.sql.SQLException;
import models.OrderCate;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import java.sql.DriverManager;

public class OrderDAOTest {

    private static final Logger logger = Logger.getLogger(OrderDAOTest.class);
    private OrderDAO orderDAO;
    private Connection testConnection;

    @Before
    public void setUp() throws SQLException {
        // Use H2 in-memory database for testing
        testConnection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        
        // Create the Orders table
        testConnection.createStatement().execute(
            "CREATE TABLE Orders (" +
            "OrderID INT AUTO_INCREMENT PRIMARY KEY, " +
            "OrderCode VARCHAR(50), " +
            "Product_Type VARCHAR(50), " +
            "CustomerID INT, " +
            "Address VARCHAR(255), " +
            "FullName VARCHAR(100), " +
            "PhoneNumber VARCHAR(20), " +
            "TotalAmount INT, " +
            "PaymentStatusID INT, " +
            "Status INT, " +
            "Note VARCHAR(255))"
        );

        // Initialize OrderDAO with the test connection
        orderDAO = new OrderDAO() {
            protected Connection getConnection() {
                return testConnection;
            }
        };
    }

    @After
    public void tearDown() throws SQLException {
        if (testConnection != null) {
            testConnection.createStatement().execute("DROP TABLE Orders");
            testConnection.close();
        }
    }

    @Test
    public void testCreateOrderAndReturnId_Success() throws SQLException {
        // Arrange
        OrderCate order = new OrderCate();
        order.setOrderCode("ORD123");
        order.setProduct_Type(1);
        order.setCustomerID(1);
        order.setAddress("123 Test St");
        order.setFullName("John Doe");
        order.setPhoneNumber("1234567890");
        order.setTotalAmount(1000);
        order.setPaymentStatusID(1);
        order.setStatus(1);
        order.setNote("Test order");

        // Act
        int orderId = orderDAO.createOrderAndReturnId(order);

        // Assert
        assertTrue(orderId > 0);
        logger.info("testCreateOrderAndReturnId_Success: Order ID returned: " + orderId);

        // Verify the order was inserted
        var rs = testConnection.createStatement().executeQuery(
            "SELECT * FROM Orders WHERE OrderCode = 'ORD123'"
        );
        assertTrue(rs.next());
        assertEquals("Electronics", rs.getString("Product_Type"));
        assertEquals(1, rs.getInt("CustomerID"));
assertEquals("123 Test St", rs.getString("Address"));
        assertEquals("John Doe", rs.getString("FullName"));
        assertEquals("1234567890", rs.getString("PhoneNumber"));
        assertEquals(1000, rs.getInt("TotalAmount"));
        assertEquals(1, rs.getInt("PaymentStatusID"));
        assertEquals(1, rs.getInt("Status"));
        assertEquals("Test order", rs.getString("Note"));
    }

    @Test
    public void testCreateOrderAndReturnId_Failure() throws SQLException {
        // Arrange: Use invalid data to simulate failure (e.g., null OrderCode if it's required)
        OrderCate order = new OrderCate();
        order.setOrderCode(null); // Assuming OrderCode is required
        order.setProduct_Type(1);
        order.setCustomerID(1);
        order.setAddress("123 Test St");
        order.setFullName("John Doe");
        order.setPhoneNumber("1234567890");
        order.setTotalAmount(1000);
        order.setPaymentStatusID(1);
        order.setStatus(1);
        order.setNote("Test order");

        // Act
        int orderId = orderDAO.createOrderAndReturnId(order);

        // Assert
        assertEquals(-1, orderId);
        logger.info("testCreateOrderAndReturnId_Failure: Returned -1 as expected for invalid data");
    }
}
