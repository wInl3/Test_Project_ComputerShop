Use master;

-- Xóa database nếu đã tồn tại
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'ComputerOnlineShop')
BEGIN
    ALTER DATABASE ComputerOnlineShop SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE ComputerOnlineShop;
END;

-- Tạo database mới
CREATE DATABASE ComputerOnlineShop;
GO

-- Sử dụng database vừa tạo
USE ComputerOnlineShop;
GO

-- 1. Roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    RoleName VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE PaymentStatus (
	StatusID INT Primary key identity(1,1),
	Status VARCHAR(20) NOT NULL
)

-- 2. Users
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    RoleID INT NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE() NOT NULL,
    Status int DEFAULT 1 NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- 3. Customer Info
CREATE TABLE CustomerInfo (
	CustomerInfoID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL,
    Address TEXT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

-- 4. Staff Info 
CREATE TABLE StaffInfo (
	StaffInfoID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL,
    StartedDate Date NOT NULL,
    EndDate Date,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

-- 5. Components luu san pham: PC, Laptop, Ban Phim
CREATE TABLE Components (
    ComponentID INT PRIMARY KEY IDENTITY(1,1),
	ComponentName VARCHAR(100) UNIQUE NOT NULL,
    Quantity INT NOT NULL,
	Status INT
);

--6. Brands
Create table Brands(
	BrandID int primary key identity(1,1),
	BrandName Varchar(100)  NOT NULL,
	Quantity INT NOT NULL,
	Status INT NOT NULL
);

-- 7. Brand components
Create table BrandComs (
	BrandComID INT PRIMARY KEY IDENTITY(1,1),
	ComponentID INT NOT NULL,
	BrandID INT NOT NULL,
	Quantity INT NOT NULL,
	FOREIGN KEY (ComponentID) REFERENCES Components(ComponentID),
	FOREIGN KEY (BrandID) REFERENCES Brands(BrandID)
);

--8. Category luu san pham cu the: Acer Nitro 5/ Des: RTX...
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
	CategoryName NVARCHAR(100) UNIQUE NOT NULL,
	BrandComID INT NOT NULL,
    Quantity INT NOT NULL,
	Price int not null,
	inventory int default 0 not null,
	Description TEXT NOT NULL,
    Status int DEFAULT 1 NOT NULL,
	ImageURL TEXT,
	FOREIGN KEY (BrandComID) REFERENCES BrandComs(BrandComID)
);

-- 9. Imports
Create TABLE Imports(
	ImportID INT PRIMARY KEY IDENTITY(1,1),
	ImportCode Varchar(100),
	CategoryID INT NOT NULL ,
	CreatedAt DATETIME DEFAULT GETDATE() NOT NULL,
	Quantity INT,
	Price INT,
	FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- 10. Products
CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
	ProductCode Varchar(100) NOT null,
    Status int DEFAULT 1 NOT NULL,
	ImportID INT NOT NULL,
	Note text default null,
    FOREIGN KEY (ImportID) REFERENCES Imports(ImportID)
);

--11. Bang nay luu thong tin cua bao hanh
Create table Warranties(
	WarrantyID INT PRIMARY KEY IDENTITY(1,1),
	WarrantyPeriod INT ,
	Status int DEFAULT 1 ,
	Description TEXT 
);

--12. WarrantyDetails
Create table WarrantyDetails(
	WarrantyDetailID INT PRIMARY KEY IDENTITY(1,1),
	WarrantyID INT,
	BrandComID INT,
	Price INT,
	Status int DEFAULT 1 NOT NULL,
    FOREIGN KEY (WarrantyID) REFERENCES Warranties(WarrantyID),
    FOREIGN KEY (BrandComID) REFERENCES BrandComs(BrandComID)
);

-- 13. Orders
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY(1,1),
	OrderCode int default null,
	Product_Type int default null, -- 0 là cate, 1 là build PC
    CustomerID INT NOT NULL,
    OrderDate DATETIME DEFAULT GETDATE() NOT NULL,
    Address TEXT NOT NULL,
	Fullname Varchar(50) NUll, 
	PhoneNumber Varchar(11) NUll, 
    TotalAmount INT NOT NULL,
	PaymentStatusID INT DEFAULT 1 NOT NULL, -- 1: COD, 2: WAITING_FOR_PAYMENT, 3: PAID, 4: : CANCEL
    Status int DEFAULT 1 NOT NULL, --  1: PENDING, 2: PROCESSING, 3: SHIPPING, 4: DELIVERED, 5: CANCEL,
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID),
    FOREIGN KEY (PaymentStatusID) REFERENCES PaymentStatus(StatusID)
);

-- 14. OrderItems
CREATE TABLE OrderItems (
	OrderItemID INT PRIMARY KEY IDENTITY(1,1),
	OrderID INT NOT NULL,
	CategoryID INT NOT NULL,
	Quantity INT NOT NULL,
	Price INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
)

-- 15. OrderDetails
CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY IDENTITY(1,1),
    OrderItemID INT NOT NULL,
    ProductID INT NOT NULL,
	WarrantyDetailID Int not null,
    UnitPrice int NOT NULL,
    WarrantyPrice int NOT NULL,
	Status int DEFAULT 1 NOT NULL,
    FOREIGN KEY (OrderItemID) REFERENCES OrderItems(OrderItemID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
	FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
);

-- 16.CartItems
CREATE TABLE CartItems (
	CartItemID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL,
	CategoryID INT NOT NULL,
	WarrantyDetailID INT NOT NULL,
	Quantity INT NOT NULL,
	Status INT NOT NULL DEFAULT 1,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
)

-- 17. Shipping
CREATE TABLE Shipping (
    ShippingID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL,
    ShipperID INT default 0 NOT NULL,
    ShippingStatus VARCHAR(50) NOT NULL,
    ShipTime DATE NOT NULL,
	Note text default null,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ShipperID) REFERENCES Users(UserID),
);

-- 18. Feedbacks
CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    Content TEXT NOT NULL,
	OrderItemID int not null,
    CreatedAt DATETIME DEFAULT GETDATE() NOT NULL,
    Rate INT NOT NULL,
	Reply TEXT NULL,
	Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
	FOREIGN KEY (OrderItemID) REFERENCES OrderItems(OrderItemID)
);

-- 19. Reports
CREATE TABLE Reports (
    ReportID INT PRIMARY KEY IDENTITY(1,1),
    ManagerID INT NOT NULL,
    Title VARCHAR(255) NOT NULL,
    Content TEXT NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE() NOT NULL,
    FOREIGN KEY (ManagerID) REFERENCES Users(UserID)
);

-- 20. Payments
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL,
    PaidAmount DECIMAL(10,2) NOT NULL,
    PaymentDate DATETIME DEFAULT GETDATE() NOT NULL,
    PaymentStatus VARCHAR(50) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

-- 21: Blogs_category
CREATE TABLE Blogs_category (
    Bc_id INT PRIMARY KEY,
    Bc_name VARCHAR(255) NOT NULL
);

-- 22: Post
CREATE TABLE Post (
    Post_id INT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255),
    Updated_date DATETIME,
    Content TEXT,
    Bc_id INT,
    Thumbnail VARCHAR(255),
    Brief TEXT,
    Add_id INT NOT NULL,
	Status INT NOT NULL DEFAULT 1,
    FOREIGN KEY (Bc_id) REFERENCES Blogs_category(Bc_id),
    FOREIGN KEY (Add_id) REFERENCES Users(UserID)
);

-- 23. Sale Events
CREATE TABLE SaleEvents (
    EventID INT PRIMARY KEY IDENTITY(1,1),
	CategoryID INT NOT NULL,
	Post_id INT NOT NULL,
	StartDate Date NOT NULL,
	EndDate Date NOT NULL,
	DiscountPercent DECIMAL(5,2) NOT NULL, 
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (Post_id) REFERENCES Post(Post_id)
);

-- 24 Order Preparements
CREATE TABLE OrderPreparements (
	UserID INT NOT NULL,
	OrderID INT NOT NULL,
	PrepareTime Date NOT NULL,
	Primary key(UserID, OrderID),
	Foreign key(UserID) references Users(UserID),
	Foreign key(OrderID) references Orders(OrderID),
)

-- 25 OTP
CREATE TABLE OTP (
    OTP_ID INT PRIMARY KEY IDENTITY(1,1),         -- Mã định danh riêng cho mỗi OTP
    Email VARCHAR(100) NOT NULL,                  -- Hoặc có thể dùng PhoneNumber nếu xác thực qua SMS
    OTP_Code VARCHAR(10) NOT NULL,                -- Mã OTP (thường 6 ký tự)
    ExpirationTime DATETIME NOT NULL,             -- Thời điểm mã hết hạn (ví dụ: tạo + 5 phút)
    CreatedAt DATETIME DEFAULT GETDATE(),         -- Thời điểm tạo
    IsUsed BIT DEFAULT 0,                          -- Đánh dấu mã đã sử dụng hay chưa (0 = chưa, 1 = đã dùng)
	Foreign key(Email) References Users(Email)
);

-- 25. Build PC
CREATE TABLE Build_PC (
    BuildPCID INT PRIMARY KEY IDENTITY(1,1),
	Price int not null,
	Status INT NOT NULL DEFAULT 1,
	UserID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- 26. Build PC Item

CREATE TABLE Build_PC_Items (
    BuildPCItemID INT PRIMARY KEY IDENTITY(1,1),
	BuildPCID INT not null,
	CategoryID int not null, 
	price int not null,
	WarrantyDetailID INT NULL,
	Status INT NOT NULL DEFAULT 1,
	FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
	FOREIGN KEY (BuildPCID) REFERENCES Build_PC(BuildPCID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
);

-- 27. Cart Build PC
CREATE TABLE Cart_Build_PC (
	CartBuildPCID INT PRIMARY KEY IDENTITY(1,1),
	UserID INT NOT NULL,
	-- giá bán cả case PC 
	Price int not null, 
	Status INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- 28. Cart Build PC Item
CREATE TABLE Cart_Build_PC_Items (
	CartBuildPCItemID INT PRIMARY KEY IDENTITY(1,1),
	CartBuildPCID INT not null,
	CategoryID int not null,
	WarrantyDetailID INT NULL,
	-- giá bán của item 
	price int not null,
	Status INT NOT NULL,
	FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
	FOREIGN KEY (CartBuildPCID) REFERENCES Cart_Build_PC(CartBuildPCID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
);

-- 29. Comments
CREATE TABLE Comments (
    CommentID INT IDENTITY(1,1) PRIMARY KEY,
    Post_id INT NOT NULL,            
    UserID INT NOT NULL,              
    CommentText TEXT NOT NULL,        
    CreatedAt DATETIME DEFAULT GETDATE(),  
    ParentCommentID INT NULL,         

    FOREIGN KEY (Post_id) REFERENCES Post(Post_id),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ParentCommentID) REFERENCES Comments(CommentID)
);

-- 30. bảng này Order của Build PC
CREATE TABLE Order_BuildPCItems (
    OrderBuildPCItemID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL,
    BuildPCID INT NOT NULL,
    Price INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (BuildPCID) REFERENCES Build_PC(BuildPCID)
);
--31
CREATE TABLE Order_BuildPCDetails (
    OrderBuildPCDetailID INT PRIMARY KEY IDENTITY(1,1),
    OrderBuildPCItemID INT NOT NULL,
    CategoryID INT NOT NULL,
    WarrantyDetailID INT NOT NULL,
    Price INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (OrderBuildPCItemID) REFERENCES Order_BuildPCItems(OrderBuildPCItemID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
);
--32
CREATE TABLE Order_BuildPC_Products (
    OrderBuildPCProductID INT PRIMARY KEY IDENTITY(1,1),
    OrderBuildPCDetailID INT NOT NULL,
    ProductID INT NOT NULL,
    FOREIGN KEY (OrderBuildPCDetailID) REFERENCES Order_BuildPCDetails(OrderBuildPCDetailID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

--33 Notification
CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,         -- Người nhận thông báo (admin hoặc user)
    SenderID INT NOT NULL,       -- Người gửi thông báo (admin hoặc user)
    Title NVARCHAR(255) NOT NULL,
    Message NVARCHAR(MAX) NOT NULL,
    IsRead BIT DEFAULT 0,        -- 0: chưa đọc, 1: đã đọc
    CreatedAt DATETIME DEFAULT GETDATE() NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (SenderID) REFERENCES Users(UserID)
);
