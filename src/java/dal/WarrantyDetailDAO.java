package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.WarrantyDetails;
import models.Warranties;

public class WarrantyDetailDAO extends DBContext {

    public List<WarrantyDetails> getWarrantyDetailsByCategoryId(int categoryId) {
        List<WarrantyDetails> list = new ArrayList<>();
        String sql = "SELECT wd.WarrantyDetailID, wd.WarrantyID, wd.BrandComID, wd.price, wd.status "
                + "FROM WarrantyDetails wd "
                + "JOIN BrandComs bc ON wd.BrandComID = bc.BrandComID "
                + "JOIN Categories c ON bc.BrandComID = c.BrandComID "
                + "WHERE c.CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            WarrantyDAO warrantyDAO = new WarrantyDAO();
            while (rs.next()) {
                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                int warrantyId = rs.getInt("WarrantyID");
                wd.setWarrantyID(warrantyId);
                wd.setBrandComID(rs.getInt("BrandComID"));
                wd.setPrice(rs.getInt("price"));
                wd.setStatus(rs.getInt("status"));
                Warranties warranty = warrantyDAO.getWarrantyById(warrantyId);
                wd.setWarranty(warranty);
                list.add(wd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public WarrantyDetails getWarrantyDetailById(int id) {
        String sql = "SELECT * FROM WarrantyDetails WHERE WarrantyDetailID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                wd.setWarrantyID(rs.getInt("WarrantyID"));
                wd.setBrandComID(rs.getInt("BrandComID"));
                wd.setPrice(rs.getInt("Price"));
                wd.setStatus(rs.getInt("Status"));

                WarrantyDAO wdao = new WarrantyDAO();
                wd.setWarranty(wdao.getWarrantyById(rs.getInt("WarrantyID")));
                return wd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
