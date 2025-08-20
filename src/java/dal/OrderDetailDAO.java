package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.OrderDetail;
import models.Products;
import models.WarrantyDetails;

public class OrderDetailDAO extends DBContext {

    public ArrayList<OrderDetail> getOrderDetailsByOrderItemID(int orderItemID) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT od.*, "
                + "p.ProductID, p.ProductCode, p.Status AS ProductStatus, "
                + "wd.WarrantyDetailID, wd.WarrantyID, wd.BrandComID, wd.Price, wd.Status AS WarrantyDetailStatus, "
                + "w.WarrantyPeriod, w.Description "
                + "FROM OrderDetails od "
                + "LEFT JOIN Products p ON od.ProductID = p.ProductID "
                + "LEFT JOIN WarrantyDetails wd ON od.WarrantyDetailID = wd.WarrantyDetailID "
                + "LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID "
                + "WHERE od.OrderItemID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt("OrderDetailID"));
                od.setOrderItemID(rs.getInt("OrderItemID"));
                od.setProductID(rs.getInt("ProductID"));
                od.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                od.setUnitPrice(rs.getInt("UnitPrice"));
                od.setWarrantyPrice(rs.getInt("WarrantyPrice"));
                od.setStatus(rs.getInt("Status"));

                // SET PRODUCT
                if (rs.getInt("ProductID") != 0) {
                    Products product = new Products();
                    product.setProductID(rs.getInt("ProductID"));
                    product.setProductCode(rs.getString("ProductCode"));
                    product.setStatus(rs.getInt("ProductStatus"));
                    od.setProduct(product);
                }

                // SET WARRANTY
                WarrantyDetails warranty = new WarrantyDetails();
                warranty.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                warranty.setWarrantyID(rs.getInt("WarrantyID"));
                warranty.setBrandComID(rs.getInt("BrandComID"));
                warranty.setPrice(rs.getInt("Price"));
                warranty.setStatus(rs.getInt("WarrantyDetailStatus"));
                warranty.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                warranty.setDescription(rs.getString("Description"));
                od.setWarranty(warranty);

                list.add(od);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderDetail.getOrderItemID());

            if (orderDetail.getProductID() != 0) {
                ps.setInt(2, orderDetail.getProductID());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setInt(3, orderDetail.getWarrantyDetailID());
            ps.setInt(4, orderDetail.getUnitPrice());
            ps.setInt(5, orderDetail.getWarrantyPrice());
            ps.setInt(6, orderDetail.getStatus());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
