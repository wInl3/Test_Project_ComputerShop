package controllerAdmin;

import dalAdmin.CategoryAdminDAO;
import dalAdmin.ImportDAO;
import dalAdmin.ProductAdminDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Categories;
import models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig
public class ImportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
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
        ImportDAO im = new ImportDAO();
        im.updateImportQuantitiesFromProducts();

        String submit = request.getParameter("submit");

        if (submit == null) {
            // Load category được chọn để hiển thị trên form
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            CategoryAdminDAO cateDAO = new CategoryAdminDAO();
            Categories selectedCategory = cateDAO.getCategoryByID(categoryID);

            request.setAttribute("selectedCategory", selectedCategory);
            request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
        } else {
            // Xử lý form submit
            String importCode = request.getParameter("importCode");
            String categoryRaw = request.getParameter("categoryID");
            String priceRaw = request.getParameter("price");
            String error = null;

            try {
                int categoryID = Integer.parseInt(categoryRaw);
                int price = 0;
                ImportDAO imdao = new ImportDAO();

                // Validate ImportCode
                if (importCode == null || importCode.trim().isEmpty()) {
                    error = "Import code cannot be empty.";
                } else if (imdao.isImportCodeExists(importCode)) {
                    error = "Import code already exists.";
                }

                // Validate Price
                if (priceRaw == null || !priceRaw.matches("^\\d+$")) {
                    error = "Price must be a positive number.";
                } else {
                    price = Integer.parseInt(priceRaw);
                }

                if (error != null) {
                    // Nếu lỗi, load lại category hiện tại và thông báo lỗi
                    CategoryAdminDAO cateDAO = new CategoryAdminDAO();
                    Categories selectedCategory = cateDAO.getCategoryByID(categoryID);

                    request.setAttribute("selectedCategory", selectedCategory);
                    request.setAttribute("error", error);
                    request.setAttribute("importCode", importCode);
                    request.setAttribute("categoryID", categoryID);
                    request.setAttribute("price", price);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
                    return;
                }

                // Thêm import vào DB
                int importID = imdao.insertImportAndGetID(importCode, categoryID, price);
                if (importID == -1) {
                    error = "Failed to insert import (check import code or DB).";
                    CategoryAdminDAO cateDAO = new CategoryAdminDAO();
                    Categories selectedCategory = cateDAO.getCategoryByID(categoryID);

                    request.setAttribute("selectedCategory", selectedCategory);
                    request.setAttribute("error", error);
                    request.setAttribute("importCode", importCode);
                    request.setAttribute("categoryID", categoryID);
                    request.setAttribute("price", price);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
                    return;
                }

                // Đọc file Excel sản phẩm
                Part filePart = request.getPart("productExcel");
                if (filePart != null && filePart.getSize() > 0) {
                    List<String> productCodes = new ArrayList<>();
                    ProductAdminDAO pdao = new ProductAdminDAO();

                    try (InputStream is = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
                        Sheet sheet = workbook.getSheetAt(0);
                        for (Row row : sheet) {
                            int rowNum = row.getRowNum() + 1;
                            Cell cell = row.getCell(0);
                            if (cell != null && cell.getCellType() == CellType.STRING) {
                                String code = cell.getStringCellValue().trim();
                                if (code.isEmpty()) {
                                    continue;
                                }

                                // Validate từng product code
                                if (code.length() > 30 || !code.matches("^[a-zA-Z0-9_-]+$")) {
                                    imdao.deleteImportByID(importID);
                                    error = "Invalid ProductCode '" + code + "' at row " + rowNum;
                                    break;
                                }

                                if (pdao.isProductCodeExists(code)) {
                                    imdao.deleteImportByID(importID);
                                    error = "Duplicate ProductCode '" + code + "' at row " + rowNum;
                                    break;
                                }

                                productCodes.add(code);
                            }
                        }

                        // Nếu có lỗi trong file Excel
                        if (error != null) {
                            CategoryAdminDAO cateDAO = new CategoryAdminDAO();
                            Categories selectedCategory = cateDAO.getCategoryByID(categoryID);

                            request.setAttribute("selectedCategory", selectedCategory);
                            request.setAttribute("error", error);
                            request.setAttribute("importCode", importCode);
                            request.setAttribute("categoryID", categoryID);
                            request.setAttribute("price", price);
                            request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
                            return;
                        }

                        // Insert sản phẩm sau khi pass validate
                        pdao.insertProductsFromExcel(productCodes, importID);
                    } catch (Exception e) {
                        imdao.deleteImportByID(importID);
                        e.printStackTrace();
                        CategoryAdminDAO cateDAO = new CategoryAdminDAO();
                        Categories selectedCategory = cateDAO.getCategoryByID(categoryID);

                        request.setAttribute("selectedCategory", selectedCategory);
                        request.setAttribute("error", "Error reading Excel file: " + e.getMessage());
                        request.setAttribute("importCode", importCode);
                        request.setAttribute("categoryID", categoryID);
                        request.setAttribute("price", price);
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
                        return;
                    }
                }

                response.sendRedirect("AdminDashbordServlet");

            } catch (Exception e) {
                e.printStackTrace();
                CategoryAdminDAO cateDAO = new CategoryAdminDAO();
                Categories selectedCategory = cateDAO.getCategoryByID(Integer.parseInt(request.getParameter("categoryID")));

                request.setAttribute("selectedCategory", selectedCategory);
                request.setAttribute("error", "Unexpected Error: " + e.getMessage());
                request.setAttribute("importCode", request.getParameter("importCode"));
                request.setAttribute("categoryID", request.getParameter("categoryID"));
                request.setAttribute("price", request.getParameter("price"));
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertImport.jsp").forward(request, response);
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
        return "Handles import of products by category";
    }
}
