package controller.cart;

import dal.CartItemDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.OrderItemDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import models.CartItem;
import models.OrderCate;
import models.OrderDetail;
import models.OrderItems;
import models.User;
import utils.Validator;

public class ConfirmOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrderDAO dao = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        CartItemDAO cartDao = new CartItemDAO();

        ArrayList<CartItem> cartItems = new ArrayList<>();

        // Get parameters
        String[] cartItemIds = request.getParameterValues("cartItemIds");
        String receiverName = request.getParameter("receiverName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");
        String subTotal = request.getParameter("subTotal");
        String message = request.getParameter("message");

        // Validate cart item ids
        if (cartItemIds == null || cartItemIds.length == 0) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toast", "Proceed to checkout failed!");
            response.sendRedirect("Cart");
            return;
        }

        // Get Cart Items
        for (String idStr : cartItemIds) {
            int id = Integer.parseInt(idStr);
            CartItem cartItem = cartDao.getCartItemById(id);
            cartItems.add(cartItem);
        }

        // Validate parameters
        String error = null;
        if (receiverName.trim().isEmpty()) {
            error = "Full name is required!";
        } else if (phoneNumber.trim().isEmpty()) {
            error = "Phone number is required!";
        } else if (!Validator.isValidPhoneNumber(phoneNumber)) {
            error = "Phone number is invalid!";
        } else if (address.trim().isEmpty()) {
            error = "Address is required!";
        }

        if (error != null) {
            forwardBackToCheckout(request, response, cartItems, receiverName, phoneNumber, address, message, subTotal, error);
            return;
        }

        try {
            // Tạo OrderCate
            String newCode = dao.generateRandomOrderCode();
            OrderCate order = new OrderCate();
            if ("COD".equals(paymentMethod)) {
                order.setOrderCode(newCode);
                order.setProduct_Type(0);
                order.setCustomerID(user.getUserId());
                order.setAddress(address);
                order.setFullName(receiverName);
                order.setPhoneNumber(phoneNumber);
                order.setTotalAmount(Integer.parseInt(subTotal));
                order.setPaymentStatusID(1);
                order.setStatus(1);
                order.setNote(message);
            } else {
                order.setOrderCode(newCode);
                order.setProduct_Type(0);
                order.setCustomerID(user.getUserId());
                order.setAddress(address);
                order.setFullName(receiverName);
                order.setPhoneNumber(phoneNumber);
                order.setTotalAmount(Integer.parseInt(subTotal));
                order.setPaymentStatusID(2);
                order.setStatus(1);
                order.setNote(message);
            }

            int orderId = dao.createOrderAndReturnId(order);

            if (orderId != -1) {
                boolean allInserted = true;
                for (CartItem cartItem : cartItems) {
                    int orderItemID = orderItemDAO.checkOrderItemExist(orderId, cartItem.getCategory().getCategoryID());
                    if (orderItemID == -1) {
                        OrderItems newOrderItem = new OrderItems();
                        newOrderItem.setOrderID(orderId);
                        newOrderItem.setCategoryID(cartItem.getCategory().getCategoryID());
                        newOrderItem.setPrice(cartItem.getCategory().getPrice());
                        newOrderItem.setQuantity(cartItem.getQuantity());
                        int newId = orderItemDAO.insertOrderItem(newOrderItem);
                        if (newId == -1) {
                            allInserted = false;
                            break;
                        } else {
                            for (int i = 0; i < cartItem.getQuantity(); i++) {
                                OrderDetail newOrderDetail = new OrderDetail();
                                newOrderDetail.setOrderItemID(newId);
                                newOrderDetail.setProductID(0);
                                newOrderDetail.setStatus(1);
                                newOrderDetail.setUnitPrice(cartItem.getCategory().getPrice());
                                newOrderDetail.setWarrantyDetailID(cartItem.getWarranty().getWarrantyDetailID());
                                newOrderDetail.setWarrantyPrice(cartItem.getWarranty().getPrice());
                                orderDetailDAO.insertOrderDetail(newOrderDetail);
                            }
                        }
                    } else {
                        orderItemDAO.addQuantity(orderId, cartItem.getCategory().getCategoryID(), cartItem.getQuantity());
                        for (int i = 0; i < cartItem.getQuantity(); i++) {
                            OrderDetail newOrderDetail = new OrderDetail();
                            newOrderDetail.setOrderItemID(orderItemID);
                            newOrderDetail.setProductID(0);
                            newOrderDetail.setStatus(1);
                            newOrderDetail.setUnitPrice(cartItem.getCategory().getPrice());
                            newOrderDetail.setWarrantyDetailID(cartItem.getWarranty().getWarrantyDetailID());
                            newOrderDetail.setWarrantyPrice(cartItem.getWarranty().getPrice());
                            orderDetailDAO.insertOrderDetail(newOrderDetail);
                        }
                    }
                }

                if (allInserted) {
                    if ("COD".equals(paymentMethod)) {
                        response.sendRedirect("Order?action=viewOrder&orderID=" + orderId);
                    } else {
                        response.sendRedirect("Order?action=viewOrder&orderID=" + orderId);
                    }
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toast", "Create order items failed!");
                    forwardBackToCheckout(request, response, cartItems, receiverName, phoneNumber, address, message, subTotal, null);
                }
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toast", "Create order failed!");
                forwardBackToCheckout(request, response, cartItems, receiverName, phoneNumber, address, message, subTotal, null);
            }
            for (CartItem cartItem : cartItems) {
                cartDao.deleteCartItem(cartItem.getCartItemID());
            }

        } catch (Exception e) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toast", "Unexpected error: " + e.getMessage());
            forwardBackToCheckout(request, response, cartItems, receiverName, phoneNumber, address, message, subTotal, null);
        }
    }

    /**
     * Hàm gói chung logic forward back khi lỗi hoặc sai dữ liệu
     */
    private void forwardBackToCheckout(HttpServletRequest request, HttpServletResponse response,
            ArrayList<CartItem> cartItems, String receiverName, String phoneNumber, String address,
            String message, String subTotal, String error) throws ServletException, IOException {
        request.setAttribute("receiverName", receiverName);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("address", address);
        request.setAttribute("message", message);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subTotal", subTotal);
        if (error != null) {
            request.setAttribute("error", error);
        }
        RequestDispatcher rd = request.getRequestDispatcher("ShopPages/Pages/checkout.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles order confirmation";
    }
}
