	-- Xóa database nếu đã tồn tại
DROP DATABASE IF EXISTS ComputerOnlineShop;

-- Tạo database mới
CREATE DATABASE ComputerOnlineShop;
USE ComputerOnlineShop;

-- 1. Roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleName VARCHAR(50) UNIQUE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Order Status
CREATE TABLE OrderStatus (
	StatusID INT PRIMARY KEY,
    StatusName varchar(20) Unique not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Users
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    RoleID INT NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) UNIQUE NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Customer Info
CREATE TABLE CustomerInfo (
    CustomerInfoID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    Address TEXT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Staff Info 
CREATE TABLE StaffInfo (
    StaffInfoID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    StartedDate DATE NOT NULL,
    EndDate DATE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Components
CREATE TABLE Components (
    ComponentID INT PRIMARY KEY AUTO_INCREMENT,
    ComponentName VARCHAR(100) UNIQUE NOT NULL,
    Quantity INT NOT NULL,
    Status INT
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Brands
CREATE TABLE Brands (
    BrandID INT PRIMARY KEY AUTO_INCREMENT,
    BrandName VARCHAR(100) NOT NULL,
    Quantity INT NOT NULL,
    Status INT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Brand Components
CREATE TABLE BrandComs (
    BrandComID INT PRIMARY KEY AUTO_INCREMENT,
    ComponentID INT NOT NULL,
    BrandID INT NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY (ComponentID) REFERENCES Components(ComponentID),
    FOREIGN KEY (BrandID) REFERENCES Brands(BrandID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Categories
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(100) UNIQUE NOT NULL,
    BrandComID INT NOT NULL,
    Quantity INT NOT NULL, -- tổng số hàng đã nhập
    Price INT NOT NULL,
    Inventory INT DEFAULT 0 NOT NULL, -- Số hàng tồn kho(Product Status = 1)
    Queue int default 0 not null, -- Số hàng chờ dùng để trường hợp không đủ hàng tạo đơn.
    Description TEXT NOT NULL,
    Status INT DEFAULT 1 NOT NULL, -- 0 là hết hàng nhưng có nhập thêm, 1 bán, 2 on sale, 3 dừng nhập hàng
    ImageURL TEXT,
    FOREIGN KEY (BrandComID) REFERENCES BrandComs(BrandComID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Imports
CREATE TABLE Imports (
    ImportID INT PRIMARY KEY AUTO_INCREMENT,
    ImportCode VARCHAR(100),
    CategoryID INT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    Quantity INT,
    Price INT,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Products
CREATE TABLE Products (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductCode VARCHAR(100) NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    ImportID INT NOT NULL,
    Note TEXT DEFAULT NULL,
    FOREIGN KEY (ImportID) REFERENCES Imports(ImportID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Warranties
CREATE TABLE Warranties (
    WarrantyID INT PRIMARY KEY AUTO_INCREMENT,
    WarrantyPeriod INT,
    Status INT DEFAULT 1,
    Description TEXT
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. Warranty Details
CREATE TABLE WarrantyDetails (
    WarrantyDetailID INT PRIMARY KEY AUTO_INCREMENT,
    WarrantyID INT,
    BrandComID INT,
    Price INT,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (WarrantyID) REFERENCES Warranties(WarrantyID),
    FOREIGN KEY (BrandComID) REFERENCES BrandComs(BrandComID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 13. Orders
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    OrderCode VARCHAR(100) DEFAULT NULL,
    Product_Type INT DEFAULT NULL, -- 0 là Items, 1 là Build PC
    CustomerID INT NOT NULL,
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    Address TEXT NOT NULL,
    PhoneNumber TEXT NOT NULL,
    Fullname TEXT NOT NULL,
    Note TEXT NULL,
	PaymentStatusID INT NOT NULL DEFAULT 1,
    TotalAmount INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID),
    FOREIGN KEY (Status) REFERENCES OrderStatus(StatusID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 14. Order Items
CREATE TABLE OrderItems (
    OrderItemID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    CategoryID INT NOT NULL,
    Quantity INT NOT NULL,
    Price INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 15. Order Details
CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderItemID INT NOT NULL,
    ProductID INT,
    WarrantyDetailID INT NOT NULL,
    UnitPrice INT NOT NULL,
    WarrantyPrice INT NOT NULL,
    Start Date default null,
    End Date default null,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (OrderItemID) REFERENCES OrderItems(OrderItemID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 16. Cart Items
CREATE TABLE CartItems (
    CartItemID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    CategoryID INT NOT NULL,
    WarrantyDetailID INT NOT NULL,
    Quantity INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 17. Shipping
CREATE TABLE Shipping (
    ShippingID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    ShipperID INT NOT NULL,
    ShippingStatus int default 0 NOT NULL,
    ShipTime DATE default NULL,
    Note TEXT DEFAULT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ShipperID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 18. Feedbacks
CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    Content TEXT NOT NULL,
    OrderItemID INT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    Rate INT NOT NULL,
    Reply TEXT DEFAULT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (OrderItemID) REFERENCES OrderItems(OrderItemID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 19. Reports
CREATE TABLE Reports (
    ReportID INT PRIMARY KEY AUTO_INCREMENT,
    ManagerID INT NOT NULL,
    Title VARCHAR(255) NOT NULL,
    Content TEXT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ManagerID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 20. Payments
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL,
    PaidAmount DECIMAL(10,2) NOT NULL,
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PaymentStatus VARCHAR(50) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 21. Blog Categories
CREATE TABLE Blogs_category (
    Bc_id INT PRIMARY KEY AUTO_INCREMENT,
    Bc_name VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 22. Posts
CREATE TABLE Post (
    Post_id INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255),
    Updated_date DATETIME,
    Content TEXT,
    Bc_id INT,
    Thumbnail VARCHAR(255),
    Brief TEXT,
    Add_id INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (Bc_id) REFERENCES Blogs_category(Bc_id),
    FOREIGN KEY (Add_id) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 23. Sale Events
CREATE TABLE SaleEvents (
    EventID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryID INT NOT NULL,
    Post_id INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    DiscountPercent DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (Post_id) REFERENCES Post(Post_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 24. Order Preparements
CREATE TABLE OrderPreparements (
    UserID INT NOT NULL,
    OrderID INT NOT NULL,
    PrepareTime DATE NOT NULL,
    PRIMARY KEY (UserID, OrderID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 25. OTP
CREATE TABLE OTP (
    OTP_ID INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(100) NOT NULL,
    OTP_Code VARCHAR(10) NOT NULL,
    ExpirationTime DATETIME NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsUsed BOOLEAN DEFAULT 0,
    FOREIGN KEY (Email) REFERENCES Users(Email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 26. Build PC
CREATE TABLE Build_PC (
    BuildPCID INT PRIMARY KEY AUTO_INCREMENT,
    Price INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    UserID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 27. Build PC Items
CREATE TABLE Build_PC_Items (
    BuildPCItemID INT PRIMARY KEY AUTO_INCREMENT,
    BuildPCID INT NOT NULL,
    CategoryID INT NOT NULL,
    Price INT NOT NULL,
    WarrantyDetailID INT DEFAULT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (BuildPCID) REFERENCES Build_PC(BuildPCID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 28. Cart Build PC
CREATE TABLE Cart_Build_PC (
    CartBuildPCID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    Price INT NOT NULL,
    Status INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 29. Cart Build PC Items
CREATE TABLE Cart_Build_PC_Items (
    CartBuildPCItemID INT PRIMARY KEY AUTO_INCREMENT,
    CartBuildPCID INT NOT NULL,
    CategoryID INT NOT NULL,
    WarrantyDetailID INT DEFAULT NULL,
    Price INT NOT NULL,
    Status INT NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
    FOREIGN KEY (CartBuildPCID) REFERENCES Cart_Build_PC(CartBuildPCID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 30. Comments
CREATE TABLE Comments (
    CommentID INT PRIMARY KEY AUTO_INCREMENT,
    Post_id INT NOT NULL,
    UserID INT NOT NULL,
    CommentText TEXT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    ParentCommentID INT DEFAULT NULL,
    FOREIGN KEY (Post_id) REFERENCES Post(Post_id),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ParentCommentID) REFERENCES Comments(CommentID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 31. Order Build PC Items
CREATE TABLE Order_BuildPCItems (
    OrderBuildPCItemID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    BuildPCID INT NOT NULL,
    Price INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (BuildPCID) REFERENCES Build_PC(BuildPCID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 32. Order Build PC Details
CREATE TABLE Order_BuildPCDetails (
    OrderBuildPCDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderBuildPCItemID INT NOT NULL,
    CategoryID INT NOT NULL,
    Price INT NOT NULL,
    Status INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (OrderBuildPCItemID) REFERENCES Order_BuildPCItems(OrderBuildPCItemID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 33. Order Build PC Products
CREATE TABLE Order_BuildPC_Products (
    OrderBuildPCProductID INT PRIMARY KEY AUTO_INCREMENT,
    OrderBuildPCDetailID INT NOT NULL,
    ProductID INT DEFAULT NULL,
    WarrantyDetailID INT DEFAULT NULL,
    StartDate DATE DEFAULT NULL,
    EndDate DATE DEFAULT NULL,
    FOREIGN KEY (OrderBuildPCDetailID) REFERENCES Order_BuildPCDetails(OrderBuildPCDetailID),
    FOREIGN KEY (WarrantyDetailID) REFERENCES WarrantyDetails(WarrantyDetailID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 34. Notifications
CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    SenderID INT NOT NULL,
    Title VARCHAR(255) NOT NULL,
    Message TEXT NOT NULL,
    IsRead BOOLEAN DEFAULT 0,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (SenderID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 35. WarrantyAssignments
CREATE TABLE WarrantyAssignments (
    OrderID INT NOT NULL,
    UserID INT NOT NULL,
    AssignedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    Note Text default null,
    PRIMARY KEY (OrderID, UserID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
