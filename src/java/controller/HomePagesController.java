package controller;

import dal.Blog_CateDAO;
import dal.CategoriesDAO;
import models.BrandByComponentName;
import models.Brands;
import models.Categories;
import models.Components;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import models.SaleEvents;

@WebServlet(name = "HomePagesController", urlPatterns = {"/HomePages"})
public class HomePagesController extends HttpServlet {

    private static final int PAGE_SIZE = 6;
    private static final int COMPONENT_PC = 1;
    private static final int COMPONENT_LAPTOP = 2;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        CategoriesDAO dao = new CategoriesDAO();

        // parameter
        int pagePC = parsePage(request.getParameter("pagePC"));
        int pageLaptop = parsePage(request.getParameter("pageLaptop"));

        int startPC = (pagePC - 1) * PAGE_SIZE;
        int startLaptop = (pageLaptop - 1) * PAGE_SIZE;

        //  pc list
        List<Categories> pcProducts = dao.getCategoriesByComponent(COMPONENT_PC, startPC, PAGE_SIZE);
        int totalPC = dao.countTotalProducts(COMPONENT_PC);
        int totalPagesPC = (int) Math.ceil(totalPC * 1.0 / PAGE_SIZE);
        // laptop list
        List<Categories> laptopProducts = dao.getCategoriesByComponent(COMPONENT_LAPTOP, startLaptop, PAGE_SIZE);
        int totalLaptop = dao.countTotalProducts(COMPONENT_LAPTOP);
        int totalPagesLaptop = (int) Math.ceil(totalLaptop * 1.0 / PAGE_SIZE);
        Blog_CateDAO daos = new Blog_CateDAO();
        List<SaleEvents> sale = daos.getAllSaleEvents();
        request.setAttribute("sale", sale);

        // slide bar
        request.setAttribute("Components", dao.getAllComponents());
        request.setAttribute("BrandWithComponent", dao.getBrandsGroupedByComponent());
        request.setAttribute("listBrand", dao.getAllBrands());

        // pc data
        request.setAttribute("pcProducts", pcProducts);
        request.setAttribute("totalPagesPC", totalPagesPC);
        request.setAttribute("currentPagePC", pagePC);

        // laptop data
        request.setAttribute("laptopProducts", laptopProducts);
        request.setAttribute("totalPagesLaptop", totalPagesLaptop);
        request.setAttribute("currentPageLaptop", pageLaptop);

        request.getRequestDispatcher("/ShopPages/Pages/homepages.jsp").forward(request, response);
    }

    private int parsePage(String param) {
        try {
            return (param != null && !param.isEmpty()) ? Integer.parseInt(param) : 1;
        } catch (NumberFormatException e) {
            return 1;
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
        return "Controller for homepage product display by component with pagination.";
    }
}
