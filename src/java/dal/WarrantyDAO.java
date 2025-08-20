package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Warranties;

public class WarrantyDAO extends DBContext {
    public List<Warranties> getAllWarranties() {
        List<Warranties> list = new ArrayList<>();
        String sql = "SELECT * FROM Warranties";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Warranties w = new Warranties();
                w.setWarrantyID(rs.getInt("WarrantyID"));
                w.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                w.setDescription(rs.getString("Description"));
                w.setStatus(rs.getInt("Status"));
                list.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Warranties getWarrantyById(int warrantyId) {
        String sql = "SELECT * FROM Warranties WHERE WarrantyID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Warranties w = new Warranties();
                w.setWarrantyID(rs.getInt("WarrantyID"));
                w.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                w.setDescription(rs.getString("Description"));
                w.setStatus(rs.getInt("Status"));
                return w;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
