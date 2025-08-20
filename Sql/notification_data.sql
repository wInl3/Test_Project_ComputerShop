-- Insert sample notification data for admin (UserID = 1)
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Đơn hàng mới', N'Bạn có đơn hàng mới cần xử lý.', 0, '2025-06-03 08:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Phản hồi khách hàng', N'Khách hàng vừa gửi feedback mới.', 0, '2025-06-03 09:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Báo cáo doanh thu', N'Báo cáo doanh thu tháng 6 đã sẵn sàng.', 0, '2025-06-03 10:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Kho hàng', N'Một số sản phẩm sắp hết hàng.', 0, '2025-06-03 11:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Đăng ký mới', N'Có người dùng mới đăng ký tài khoản.', 0, '2025-06-03 12:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Đơn hàng bị hủy', N'Một đơn hàng vừa bị hủy.', 0, '2025-06-03 13:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Bảo trì hệ thống', N'Hệ thống sẽ bảo trì vào 23:00 hôm nay.', 0, '2025-06-03 14:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Khuyến mãi mới', N'Chương trình khuyến mãi mới đã bắt đầu.', 0, '2025-06-03 15:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Phản hồi đã trả lời', N'Bạn vừa trả lời một feedback của khách.', 0, '2025-06-03 16:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Đơn hàng giao thành công', N'Một đơn hàng đã giao thành công.', 0, '2025-06-03 17:00:00');

-- Insert some read notifications
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Thông báo cũ', N'Đây là thông báo đã đọc.', 1, '2025-06-02 10:00:00');
INSERT INTO Notifications (UserID, Title, Message, IsRead, CreatedAt) VALUES (1, N'Thông báo cũ 2', N'Đây là thông báo đã đọc thứ 2.', 1, '2025-06-02 11:00:00'); 