/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.BrandAdminDAO;
import dalAdmin.BrandComAdminDAO;
import dalAdmin.ComponentAdminDAO;
import dalAdmin.WarrantyAdminDAO;
import dalAdmin.WarrantyDetailAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.User;
import models.WarrantyDetails;

/**
 *
 * @author Admin
 */
public class WarrantyDetailAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        /* TODO output your page here. You may use following sample code. */
        String service = request.getParameter("service");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || currentUser.getRole().getRoleID() != 1) {
            if (session != null) {
                session.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("error", "You do not have permission to access this task.");
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        if (service == null) {
            service = "list";
        }

        WarrantyDetailAdminDAO dao = new WarrantyDetailAdminDAO();

        if (service.equals("list")) {
            List<WarrantyDetails> list = dao.getAllWarrantyDetails();
            request.setAttribute("warrantyDetails", list);
            request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewWarrantyDetail.jsp")
                    .forward(request, response);

        } else if (service.equals("insert")) {
            String submit = request.getParameter("submit");

            if (submit == null) {
                // Load dữ liệu để hiển thị dropdown
                WarrantyAdminDAO warrantyDAO = new WarrantyAdminDAO();
                BrandAdminDAO brandDAO = new BrandAdminDAO();
                ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                request.setAttribute("warranties", warrantyDAO.getAllWarranties());
                request.setAttribute("brands", brandDAO.getAllBrands());
                request.setAttribute("components", componentDAO.getAllComponent());

                request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertWarrantyDetail.jsp").forward(request, response);
            } else {
                List<String> errors = new ArrayList<>();

                String warrantyIDRaw = request.getParameter("warrantyID");
                String brandIDRaw = request.getParameter("brandID");
                String componentIDRaw = request.getParameter("componentID");
                String priceRaw = request.getParameter("price");
                String statusRaw = request.getParameter("status");

                int warrantyID = 0, brandID = 0, componentID = 0, price = 0, status = 1;

                if (warrantyIDRaw == null || warrantyIDRaw.trim().isEmpty()) {
                    errors.add("Warranty must be selected.");
                }

                if (brandIDRaw == null || brandIDRaw.trim().isEmpty()) {
                    errors.add("Brand must be selected.");
                }

                if (componentIDRaw == null || componentIDRaw.trim().isEmpty()) {
                    errors.add("Component must be selected.");
                }

                if (priceRaw == null || priceRaw.trim().isEmpty()) {
                    errors.add("Price must not be empty.");
                } else if (!priceRaw.matches("^\\d{6,10}$")) {
                    errors.add("Price must be a number between 6 and 10 digits.");
                }

                if (statusRaw == null || statusRaw.trim().isEmpty()) {
                    errors.add("Status must be selected.");
                } else if (!(statusRaw.equals("0") || statusRaw.equals("1"))) {
                    errors.add("Invalid status value.");
                }

                if (warrantyIDRaw == null || warrantyIDRaw.trim().isEmpty()) {
                    errors.add("Warranty must be selected.");
                } else {
                    try {
                        warrantyID = Integer.parseInt(warrantyIDRaw);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid warranty ID.");
                    }
                }

                if (brandIDRaw == null || brandIDRaw.trim().isEmpty()) {
                    errors.add("Brand must be selected.");
                } else {
                    try {
                        brandID = Integer.parseInt(brandIDRaw);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid brand ID.");
                    }
                }

                if (componentIDRaw == null || componentIDRaw.trim().isEmpty()) {
                    errors.add("Component must be selected.");
                } else {
                    try {
                        componentID = Integer.parseInt(componentIDRaw);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid component ID.");
                    }
                }

                if (priceRaw == null || !priceRaw.matches("^\\d{6,10}$")) {
                    errors.add("Price must be a number between 6 and 10 digits.");
                } else {
                    try {
                        price = Integer.parseInt(priceRaw);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid price format.");
                    }
                }

                if (statusRaw != null && (statusRaw.equals("0") || statusRaw.equals("1"))) {
                    status = Integer.parseInt(statusRaw);
                } else {
                    errors.add("Invalid status value.");
                }

                int brandComID = 0;
                if (errors.isEmpty()) {
                    brandComID = new BrandComAdminDAO().getBrandComID(brandID, componentID);
                    if (brandComID == 0) {
                        errors.add("No BrandCom found for selected Brand and Component.");
                    }
                }

                if (!errors.isEmpty()) {
                    // Trả lại form và dữ liệu
                    WarrantyAdminDAO warrantyDAO = new WarrantyAdminDAO();
                    BrandAdminDAO brandDAO = new BrandAdminDAO();
                    ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                    request.setAttribute("errors", errors);
                    request.setAttribute("warranties", warrantyDAO.getAllWarranties());
                    request.setAttribute("brands", brandDAO.getAllBrands());
                    request.setAttribute("components", componentDAO.getAllComponent());

                    request.setAttribute("warrantyID", warrantyIDRaw);
                    request.setAttribute("brandID", brandIDRaw);
                    request.setAttribute("componentID", componentIDRaw);
                    request.setAttribute("price", priceRaw);
                    request.setAttribute("status", statusRaw);

                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertWarrantyDetail.jsp").forward(request, response);
                    return;
                }

                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyID(warrantyID);
                wd.setBrandComID(brandComID);
                wd.setPrice(price);
                wd.setStatus(status);

                new WarrantyDetailAdminDAO().insertWarrantyDetail(wd);
                response.sendRedirect("WDA");
            }
        } else if (service.equals("update")) {
            int id = Integer.parseInt(request.getParameter("warrantyDetailID"));
            String submit = request.getParameter("submit");

            if (submit == null) {
                // Load dữ liệu để hiển thị form update
                WarrantyAdminDAO warrantyDAO = new WarrantyAdminDAO();
                BrandAdminDAO brandDAO = new BrandAdminDAO();
                ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                WarrantyDetails wd = dao.getWarrantyDetailByID(id);

                request.setAttribute("warranties", warrantyDAO.getAllWarranties());
                request.setAttribute("brands", brandDAO.getAllBrands());
                request.setAttribute("components", componentDAO.getAllComponent());
                request.setAttribute("detail", wd);

                int price = wd.getPrice();
                request.setAttribute("detail", wd);
                request.setAttribute("price", price);

                request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/updateWarrantyDetail.jsp").forward(request, response);
            } else {
                List<String> errors = new ArrayList<>();

                String priceRaw = request.getParameter("price");
                String statusRaw = request.getParameter("status");

                int price = 0, status = 1;

                if (priceRaw == null || !priceRaw.matches("^\\d{6,10}$")) {
                    errors.add("Price must be a number between 6 and 10 digits.");
                } else {
                    try {
                        price = Integer.parseInt(priceRaw);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid price format.");
                    }
                }

                if (statusRaw != null && (statusRaw.equals("0") || statusRaw.equals("1"))) {
                    status = Integer.parseInt(statusRaw);
                } else {
                    errors.add("Invalid status value.");
                }

                if (!errors.isEmpty()) {
                    WarrantyAdminDAO warrantyDAO = new WarrantyAdminDAO();
                    BrandAdminDAO brandDAO = new BrandAdminDAO();
                    ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                    WarrantyDetails wd = dao.getWarrantyDetailByID(id);

                    request.setAttribute("errors", errors);
                    request.setAttribute("warranties", warrantyDAO.getAllWarranties());
                    request.setAttribute("brands", brandDAO.getAllBrands());
                    request.setAttribute("components", componentDAO.getAllComponent());
                    request.setAttribute("detail", wd);
                    request.setAttribute("price", priceRaw);
                    request.setAttribute("status", statusRaw);

                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/updateWarrantyDetail.jsp").forward(request, response);
                    return;
                }

                // Gọi cập nhật price và status bằng warrantyDetailID
                dao.updatePriceAndStatusByID(id, price, status);

                response.sendRedirect("WDA");
            }
        } else if (service.equals("listbybrandcomid")) {
            String brandComIDRaw = request.getParameter("brandComID");
            try {
                int brandComID = Integer.parseInt(brandComIDRaw);
                List<WarrantyDetails> list = dao.getAllWarrantyDetailsByBrandComID(brandComID);

                request.setAttribute("warrantyDetails", list);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewWarrantyDetail.jsp")
                        .forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid brandComID.");
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewWarrantyDetail.jsp")
                        .forward(request, response);
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
