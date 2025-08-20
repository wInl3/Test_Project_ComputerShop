<% String ctx = request.getContextPath(); %>
<% String activeMenu = request.getParameter("activeMenu"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${param.ctx}/AdminLTE/AdminPages/dist/img/ProdileIcon.png"  class="img-circle" alt="User"/>
            </div>
            <div class="pull-left info">
                <p>${user.fullname}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                    <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                    </button>
                </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <c:if test="${user.role.roleID == 1}">
            
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview <% if ("user".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-users"></i> <span>User</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("user".equals(activeMenu)) { %>style="display:block;"<% } %>>                          
                    <li><a href="${param.ctx}/Admin/user?type=customer" class="<% if ("user".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Customers</a></li>   
                    <li><a href="${param.ctx}/Admin/user?type=sale"><i class="fa fa-circle-o"></i>View Sales</a></li>   
                    <li><a href="${param.ctx}/Admin/user?type=shipper"><i class="fa fa-circle-o"></i>View Shippers</a></li>   
                    <li><a href="${param.ctx}/Admin/user/add"><i class="fa fa-circle-o"></i>Create new user</a></li>
                </ul>
            </li>  
            <li class="treeview <% if ("brand".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Brand</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("brand".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/BrandAdmin"><i class="fa fa-circle-o"></i>View Brand</a></li>
                    <li><a href="${param.ctx}/BrandAdmin?service=insert"><i class="fa fa-circle-o"></i>Insert new Brand</a></li>  
                </ul>
            </li>
            <li class="treeview <% if ("component".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Component</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("component".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/ComAdmin" class="<% if ("component".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Component</a></li>
                    <li><a href="${param.ctx}/ComAdmin?service=insert"><i class="fa fa-circle-o"></i>Insert new Component</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("bracom".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>BraCom</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("bracom".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/BrandComAdmin" class="<% if ("bracom".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Bra-Com</a></li>   
                    <li><a href="${param.ctx}/BrandComAdmin?service=insert"><i class="fa fa-circle-o"></i>Insert new Brand-Com</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("category".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Category</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("category".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/CateAdmin" class="<% if ("category".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Category</a></li>
                    <li><a href="${param.ctx}/CateAdmin?service=insert"><i class="fa fa-circle-o"></i>Insert new Category</a></li>
                </ul>
            </li>           
            <li class="treeview <% if ("product".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Product</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("product".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/ProductAdmin" class="<% if ("product".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Product</a></li> 
                </ul>
            </li>
            <li class="treeview <% if ("warranty".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Warranty</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("warranty".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/WarrantyAdmin" class="<% if ("warranty".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Warranty</a></li> 
                    <li><a href="${param.ctx}/WarrantyAdmin?service=insert"><i class="fa fa-circle-o"></i>Insert Warranty</a></li> 
                </ul>
            </li>
            <li class="treeview <% if ("warrantydetail".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Warranty Detail</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("warrantydetail".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/WDA" class="<% if ("warrantydetail".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Warranty Detail</a></li> 
                    <li><a href="${param.ctx}/WDA?service=insert"><i class="fa fa-circle-o"></i>Insert Warranty Detail</a></li> 
                </ul>
            </li>
            <li class="treeview <% if ("ordercate".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Category</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("ordercate".equals(activeMenu)) { %>style="display:block;"<% } %>> 
                    <li><a href="${param.ctx}/OrderAdminCate?service=listRejected" class="<% if ("ordercate".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPending"><i class="fa fa-circle-o"></i>Pending Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listProcessing"><i class="fa fa-circle-o"></i>In Process Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWaitShip"><i class="fa fa-circle-o"></i>Waiting Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <!-- don thay the neu phai doi hang cho khach --> 
                </ul>
            </li>
            <li class="treeview <% if ("orderbuildpc".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Build PC</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("orderbuildpc".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/OrderBuildPC?service=listRejected" class="<% if ("orderbuildpc".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listInProcess"><i class="fa fa-circle-o"></i>In Process PC Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWaitingShipping"><i class="fa fa-circle-o"></i>Waiting Ship</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("feedback".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Feedback</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("feedback".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/FeedBackAdmin" class="<% if ("feedback".equals(activeMenu)) { %>active<% } %>"><i ass="fa fa-circle-o"></i>View Feedback</a></li> 
                </ul>
            </li>
            <li class="treeview <% if ("blog".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Blogs</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("blog".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/bloga" class="<% if ("blog".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Blog</a></li>
                    <li><a href="${param.ctx}/blogadd"><i class="fa fa-circle-o"></i>Insert new Blog</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("saleevents".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Sale Events</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("saleevents".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/saleevents" class="<% if ("blog".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Sale Events</a></li>
                    <li><a href="${param.ctx}/addsale"><i class="fa fa-circle-o"></i>Insert Sale Events</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("buildpc".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Build PC</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("buildpc2".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${pageContext.request.contextPath}/BuildPC_ListCate?service=list" class="<% if ("buildpc".equals(activeMenu)) { %>active<% } %>">
                            <i class="fa fa-circle-o"></i> View PC</a>
                    </li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWaitingConfirm"><i class="fa fa-circle-o"></i>Waiting PC-Pending</a></li>
                    <li><a href="${param.ctx}/AdminLTE/AdminPages/pages/forms/BuildPCAdmin.html"><i class="fa fa-circle-o"></i>Creative new PC </a></li>
                </ul>
            </li>
<!--            <li>
                <a href="${param.ctx}/NotificationServlet?service=showSendForm">
                    <i class="fa fa-bell"></i> <span>Send Notification</span>
                </a>
            </li>-->
        </ul>
        </c:if>
        <c:if test="${user.role.roleID == 2}">
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview <% if ("ordercate".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Category</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("ordercate".equals(activeMenu)) { %>style="display:block;"<% } %>> 
                    <li><a href="${param.ctx}/OrderAdminCate?service=listRejected" class="<% if ("ordercate".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPending"><i class="fa fa-circle-o"></i>Pending Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listProcessing"><i class="fa fa-circle-o"></i>In Process Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWaitShip"><i class="fa fa-circle-o"></i>Waiting Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <!-- don thay the neu phai doi hang cho khach --> 
                </ul>
            </li>
            <li class="treeview <% if ("orderbuildpc".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Build PC</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("orderbuildpc".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/OrderBuildPC?service=listRejected" class="<% if ("orderbuildpc".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listInProcess"><i class="fa fa-circle-o"></i>In Process PC Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWaitingShipping"><i class="fa fa-circle-o"></i>Waiting Ship</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                </ul>
            </li>
            <li class="treeview <% if ("feedback".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Feedback</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("feedback".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/FeedBackAdmin" class="<% if ("feedback".equals(activeMenu)) { %>active<% } %>"><i ass="fa fa-circle-o"></i>View Feedback</a></li> 
                </ul>
            </li>
            <li class="treeview <% if ("blog".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Blogs</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("blog".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/bloga" class="<% if ("blog".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>View Blog</a></li>
                    <li><a href="${param.ctx}/blogadd"><i class="fa fa-circle-o"></i>Insert new Blog</a></li>
                </ul>
            </li>
        </ul>
        </c:if>
        <c:if test="${user.role.roleID == 4}">
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview <% if ("ordercate".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Category</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("ordercate".equals(activeMenu)) { %>style="display:block;"<% } %>> 
                    <li><a href="${param.ctx}/OrderAdminCate?service=listRejected" class="<% if ("ordercate".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPending"><i class="fa fa-circle-o"></i>Pending Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listProcessing"><i class="fa fa-circle-o"></i>In Process Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWaitShip"><i class="fa fa-circle-o"></i>Waiting Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderAdminCate?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <!-- don thay the neu phai doi hang cho khach --> 
                </ul>
            </li>
            <li class="treeview <% if ("orderbuildpc".equals(activeMenu)) { %>menu-open active<% } %>">
                <a href="#">
                    <i class="fa fa-laptop"></i> <span>Order Build PC</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" <% if ("orderbuildpc".equals(activeMenu)) { %>style="display:block;"<% } %>>                               
                    <li><a href="${param.ctx}/OrderBuildPC?service=listRejected" class="<% if ("orderbuildpc".equals(activeMenu)) { %>active<% } %>"><i class="fa fa-circle-o"></i>Reject</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listInProcess"><i class="fa fa-circle-o"></i>In Process PC Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWaitingShipping"><i class="fa fa-circle-o"></i>Waiting Ship</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listOnShipping"><i class="fa fa-circle-o"></i>On Shipping Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listCompleted"><i class="fa fa-circle-o"></i>Success Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listPendingWarranty"><i class="fa fa-circle-o"></i>Pending Warranty Order</a></li>
                    <li><a href="${param.ctx}/OrderBuildPC?service=listWarranty"><i class="fa fa-circle-o"></i>Warranty Order</a></li>
                </ul>
            </li>
        </ul>
        </c:if>
    </section>
    <!-- /.sidebar -->
</aside>