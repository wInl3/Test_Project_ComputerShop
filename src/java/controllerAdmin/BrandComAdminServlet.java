package controllerAdmin;

import dalAdmin.BrandComAdminDAO;
import dalAdmin.BrandAdminDAO;
import dalAdmin.ComponentAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.BrandComs;
import models.User;

public class BrandComAdminServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
            BrandComAdminDAO dao = new BrandComAdminDAO();
            dao.updateBrandComQuantitiesFromCategories();
            if (service.equals("list")) {
                List<BrandComs> braComList = dao.getAllBrandComs();
                request.setAttribute("braCom", braComList);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewBrandCom.jsp").forward(request, response);
            } else if (service.equals("listbybrand")) {
                int id = Integer.parseInt(request.getParameter("brandID"));
                List<BrandComs> braComList = dao.getBrandComsByBrandID(id);
                request.setAttribute("braCom", braComList);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewBrandCom.jsp").forward(request, response);
            } else if (service.equals("listbycom")) {
                int id = Integer.parseInt(request.getParameter("componentID"));
                List<BrandComs> braComList = dao.getBrandComsByComponentID(id);
                request.setAttribute("braCom", braComList);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewBrandCom.jsp").forward(request, response);
            } else if (service.equals("insert")) {
                String submit = request.getParameter("submit");

                if (submit == null) {
                    BrandAdminDAO brandDAO = new BrandAdminDAO();
                    ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                    request.setAttribute("brands", brandDAO.getAllBrands());
                    request.setAttribute("components", componentDAO.getAllComponent());

                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrandCom.jsp").forward(request, response);
                } else {
                    String brandRaw = request.getParameter("brandID");
                    String componentRaw = request.getParameter("componentID");

                    String error = null;

                    try {
                        int brandID = Integer.parseInt(brandRaw);
                        int componentID = Integer.parseInt(componentRaw);
                        int quantity = 0;

                        if (dao.existsBrandComponentPair(brandID, componentID)) {
                            error = "This brand-component combination already exists.";
                        }

                        if (error != null) {
                            BrandAdminDAO brandDAO = new BrandAdminDAO();
                            ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                            request.setAttribute("brands", brandDAO.getAllBrands());
                            request.setAttribute("components", componentDAO.getAllComponent());
                            request.setAttribute("error", error);
                            request.setAttribute("brandID", brandRaw);
                            request.setAttribute("componentID", componentRaw);
                            request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrandCom.jsp").forward(request, response);
                            return;
                        }

                        // Nếu hợp lệ thì insert
                        BrandComs newBrandCom = new BrandComs(brandID, componentID, quantity);
                        dao.insertBrandCom(newBrandCom);
                        response.sendRedirect("BrandComAdmin?service=list");

                    } catch (NumberFormatException e) {
                        error = "Invalid selection.";
                        BrandAdminDAO brandDAO = new BrandAdminDAO();
                        ComponentAdminDAO componentDAO = new ComponentAdminDAO();

                        request.setAttribute("brands", brandDAO.getAllBrands());
                        request.setAttribute("components", componentDAO.getAllComponent());
                        request.setAttribute("error", error);
                        request.setAttribute("brandID", brandRaw);
                        request.setAttribute("componentID", componentRaw);
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrandCom.jsp").forward(request, response);
                    }
                }
            }

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BrandCom Management Servlet";
    }
}
