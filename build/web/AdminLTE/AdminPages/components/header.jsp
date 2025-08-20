<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header class="main-header">
    <!-- Logo -->
    <c:if test="${user.role.roleID == 1}">
        <a href="${ctx}/AdminDashbordServlet" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>A</b>LT</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Admin</b>LTE</span>
        </a>
    </c:if>
    <c:if test="${user.role.roleID != 1}">
        <a href="#" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>A</b>LT</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Admin</b>LTE</span>
        </a>
    </c:if>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <button class="sidebar-toggle" data-toggle="offcanvas" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
        </button>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${ctx}/HomePages"><i class="fa fa-home" aria-hidden="true"></i>  Shop Pages</a>
                </li>
                <!-- Notifications: style can be found in dropdown.less -->
                <c:if test="${not empty sessionScope.user}">
                    <!--                    <li class="dropdown notifications-menu">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                                <i class="fa fa-bell-o"></i>
                                                <span class="label label-warning" id="notification-count">0</span>
                                            </a>
                                            <ul class="dropdown-menu" id="notification-dropdown">
                                                <li class="header">You have <span id="notification-count-header">0</span> notifications</li>
                                                <li>
                                                     inner menu: contains the actual data 
                                                    <ul class="menu" id="notification-list">
                                                        <li><a href="#"><i class="fa fa-info-circle text-aqua"></i> No notifications</a></li>
                                                    </ul>
                                                </li>
                                                <li class="footer"><a href="${ctx}/NotificationServlet?service=list">View All</a></li>
                                            </ul>
                                        </li>-->
                </c:if>

                <c:if test="${user != null}">
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!--<img src="${ctx}/AdminLTE/AdminPages/dist/img/user2-160x160.jpg" class="user-image" alt="User">-->
                            <span class="hidden-xs"><i class="fa fa-user" aria-hidden="true"></i> Profile</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="${ctx}/AdminLTE/AdminPages/dist/img/ProdileIcon.png" class="img-circle" alt="User">
                                <p>
                                    ${user.fullname}
                                    <small>${user.role.roleName}</small>
                                </p>
                            </li>
                            <!-- Menu Bodya -->
                            <!--                            <li class="user-body">
                                                            <div class="row">
                                                                <div class="col-xs-4 text-center">
                                                                    <a href="#">Followers</a>
                                                                </div>
                                                                <div class="col-xs-4 text-center">
                                                                    <a href="#">Sales</a>
                                                                </div>
                                                                <div class="col-xs-4 text-center">
                                                                    <a href="#">Friends</a>
                                                                </div>
                                                            </div>
                                                             /.row 
                                                        </li>-->
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="${ctx}/AdminProfile" class="btn btn-default btn-flat"><i  class="fa fa-cog"></i> Settings</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${ctx}/Logout" class="btn btn-default btn-flat"><i  class="fa fa-sign-in"></i>  Logout</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${user == null}">
                    <li>
                        <a href="${ctx}/Login"><i  class="fa fa-sign-in"></i> Login</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <script>
            $(document).ready(function () {
                $.ajax({
                    url: "${ctx}/NotificationServlet?service=getUnreadCount",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        $("#notification-count").text(data.count);
                        $("#notification-count-header").text(data.count);
                    }
                });

                $('.notifications-menu > a').on('click', function (e) {
                    $.ajax({
                        url: "${ctx}/NotificationServlet?service=ajaxList",
                        type: "GET",
                        dataType: "json",
                        success: function (data) {
                            var html = '';
                            if (data.notifications.length === 0) {
                                html = '<li><a href="#"><i class="fa fa-info-circle text-aqua"></i> No notifications</a></li>';
                            } else {
                                data.notifications.forEach(function (noti) {
                                    html += '<li><a href="${ctx}/NotificationServlet?service=detail&id=' + noti.id + '">'
                                            + '<i class="fa fa-info-circle text-aqua"></i> <b>' + noti.title + '</b>'
                                            + '<br><small>' + noti.message + '</small>'
                                            + '<br><small>' + noti.createdAt + '</small>'
                                            + '</a></li>';
                                });
                            }
                            // Hi?n th? t?ng s? ch?a ??c
                            $('.dropdown-menu .header').html('You have <span id="notification-count-header">' + data.totalUnread + '</span> notifications');
                            $('#notification-list').html(html);
                            // ??ng b? l?i s? trên icon chuông n?u c?n
                            $('#notification-count').text(data.totalUnread);
                        }
                    });
                });
            });
        </script>
    </c:when>
    <c:otherwise>
        <script>$(function () {
                $("#notification-count").text(0);
            });</script>
    </c:otherwise>
</c:choose>