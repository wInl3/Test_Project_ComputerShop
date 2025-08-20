<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shipper Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <style>
            .card-hover:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
            }
            .gradient-bg {
                background: linear-gradient(135deg, #A84B5E 0%, #E98EA7 100%);
            }
        </style>
    </head>
    <body class="bg-gray-100 font-sans">
        <div class="container mx-auto p-6">
            <!-- Header -->
            <div class="gradient-bg text-white p-4 rounded-lg mb-6 shadow-lg">
                <h1 class="text-3xl font-bold text-center">Shipper Dashboard</h1>
            </div>

            <!-- Shipment List -->
            <c:forEach var="shipment" items="${shipments}" varStatus="loop">
                <div class="card-hover bg-white p-6 rounded-lg shadow-lg mb-6">
                    <div class="flex justify-between items-start">
                        <div>
                            <h2 class="text-2xl font-semibold text-gray-800">Order #${shipment.orderID}</h2>
                            <p class="text-gray-600 mt-2"><strong>Date:</strong> ${shipment.orderDate}</p>
                            <p class="text-gray-600"><strong>Address:</strong> ${shipment.address}</p>
                            <p class="text-gray-600"><strong>Total Amount:</strong> ${shipment.totalAmount} VNĐ</p>
                            <p class="text-yellow-600 font-medium"><strong>Status:</strong> ${shipment.shippingStatus}</p>
                            <p class="text-gray-600"><strong>Feedback:</strong> ${shipment.feedback}</p>
                        </div>
                        <div class="w-1/3">
                            <div class="bg-gray-50 p-4 rounded-lg">
                                <h3 class="text-lg font-medium text-gray-700 mb-2">Product Details</h3>
                                <div class="flex items-center">
                                    <img src="${shipment.imageURL}" alt="${shipment.categoryName}" class="w-20 h-20 object-cover rounded mr-4">
                                    <div>
                                        <p class="text-gray-800 font-medium">${shipment.categoryName}</p>
                                        <p class="text-gray-600">Quantity: ${shipment.quantity}</p>
                                        <p class="text-green-600 font-semibold">Price: ${shipment.price} VNĐ</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="my-4 border-gray-200">
                    <form action="ShippingController" method="post" class="space-y-4">
                        <input type="hidden" name="service" value="update">
                        <input type="hidden" name="shippingID" value="${shipment.shippingID}">
                        <div>
                            <label for="status" class="block text-sm font-medium text-gray-700">Update Status:</label>
                            <select name="status" id="status" class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200">
                                <option value="Pending" <c:if test="${shipment.shippingStatus == 'Pending'}">selected</c:if>>Pending</option>
                                <option value="Shipped" <c:if test="${shipment.shippingStatus == 'Shipped'}">selected</c:if>>Shipped</option>
                                <option value="Delivered" <c:if test="${shipment.shippingStatus == 'Delivered'}">selected</c:if>>Delivered</option>
                            </select>
                        </div>
                        <div>
                            <label for="feedback" class="block text-sm font-medium text-gray-700">Feedback:</label>
                            <input type="text" name="feedback" value="${shipment.feedback}" class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200">
                        </div>
                        <div class="text-right">
                            <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition duration-200">Update</button>
                            <a href="ShippingController?service=list" class="ml-4 text-blue-500 hover:text-blue-700 font-medium">Cancel</a>
                        </div>
                    </form>
                </div>
            </c:forEach>
        </div>
    </body>
</html>