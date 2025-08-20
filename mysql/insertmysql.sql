
-- Insert data into Roles
INSERT INTO Roles (RoleName) VALUES 
('Admin'),
('Sale'),
('Customer'),
('Shipper');

-- Insert data into OrderStatus
INSERT INTO OrderStatus (StatusID, StatusName) VALUES
(0,  'Rejected'),
(1,  'Pending'),
(2,  'On-progress'),
(3,  'Waiting for ship'),
(4,  'On Ship'),
(5,  'Completed'),
(6,  'Pending Warranty'),
(7,  'On Warranty'),
(8,  'Pending Build PC');

INSERT INTO Users (RoleID, FullName, Email, PhoneNumber, PasswordHash, CreatedAt, Status) VALUES
(1, 'Alice Nguyen', 'alice@domain.com', '0912345678', 'b9ff6b991cdc84277a42cacc41493d5a9dc867445a33999401f50efe8052a022', NOW(), 1), -- hashed 'hashedpassword123'
(2, 'Bob Tran', 'bob@domain.com', '0987654321', '9878d344400c00f8bab1a4ba1a3488b3ace88aea983e3d94ba1c781e09ba32bb', NOW(), 1), -- hashed 'securepass456'
(4, 'Charlie Pham', 'charlie@domain.com', '0909090909', '51f334a658e9bf4ce073412c5948c01083cbb64497cf913347fa106dd962e9b0', NOW(), 1), -- hashed 'pass789secure'
(4, 'Charlie Pham2', 'charlie@doma.com', '0909090908', '51f334a658e9bf4ce073412c5948c01083cbb64497cf913347fa106dd962e9b0', NOW(), 1), -- hashed 'pass789secure'
(3, 'LinhNV', 'customer@example.com', '0912345687', 'ef688fb1a087af8b9a7f22eb141fd9e8707160954e8c855a45abd848e35516e5', NOW(), 1), -- hashed 'hashedpassword3'
(3, 'Cus1', 'customer1@example.com', '0912345673', 'ef688fb1a087af8b9a7f22eb141fd9e8707160954e8c855a45abd848e35516e5', NOW(), 1), -- hashed 'hashedpassword3'
(3, 'Cus2', 'customer2@example.com', '0912345679', 'ef688fb1a087af8b9a7f22eb141fd9e8707160954e8c855a45abd848e35516e5', NOW(), 1), -- hashed 'hashedpassword3'
(3, 'Cus3', 'customer3@example.com', '0912345610', 'ef688fb1a087af8b9a7f22eb141fd9e8707160954e8c855a45abd848e35516e5', NOW(), 1), -- hashed 'hashedpassword3'
(4, 'Ship1', 'ship1@example.com', '0912345611', 'ef688fb1a087af8b9a7f22eb141fd9e8707160954e8c855a45abd848e35516e5', NOW(), 1), -- hashed 'hashedpassword3'
(1, 'DXT', 'taidoyasuovn@gmail.com', '0912345671', 'b9ff6b991cdc84277a42cacc41493d5a9dc867445a33999401f50efe8052a022', NOW(), 1); -- hashed 'hashedpassword123'


INSERT INTO CustomerInfo (UserID, Address) VALUES
(3, '789 Nguyen Trai, DN'),
(5, '123 Nguyen Trai, HN');

INSERT INTO StaffInfo (UserID, StartedDate, EndDate) VALUES
(2, '2023-01-01', '2025-12-31'),
(4, '2023-01-01', '2025-12-31');

INSERT INTO Components (ComponentName, Quantity, Status) VALUES
('PC', 50, 1),
('MainBoard', 120, 1),
('CPU', 100, 1),
('GPU', 80, 1),
('RAM', 150, 1),
('SSD', 90, 1),
('Case', 70, 1);

-- Insert data into Brands
INSERT INTO Brands (BrandName, Quantity, Status) VALUES
('ASUS', 0, 1),
('MSI', 0, 1),
('GIGABYTE', 0, 1),
('Intel', 0, 1),
('AMD', 0, 1),
('NVIDIA', 0, 1),
('Corsair', 0, 1),
('Kingston', 0, 1),
('Samsung', 0, 1),
('DeepCool', 0, 1),
('WesternDigital', 0, 1);

INSERT INTO BrandComs (ComponentID, BrandID, Quantity) VALUES
(1, 1, 0),   -- 1
(1, 2, 0),
(2, 1, 0),
(2, 2, 0),   -- 4
(2, 3, 0),
(3, 4, 0),
(3, 5, 0),
(4, 5, 0),   -- 8
(4, 6, 0),
(5, 8, 0),   -- 10
(5, 9, 0),
(6, 8, 0),   -- 12
(6, 11, 0),
(7, 1, 0),   -- 14
(7, 2, 0),
(7, 10, 0);

-- Insert data into Categories

INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC Mini Asus NUC 13TH Arena Canyon', 1, 16, 1582000, N'I3-1315U/DDR4/WF+BT/NON OS/BLACK', 0, N'Screenshot 2025-07-22 041056.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC Mini Pc Asus NUC 14 Pro Revel Canyon U7', 1, 24, 222000, N'U7-155H/DDR5/WF+BT/NON OS/BLACK', 2, N'8854-mini-pc-asus-nuc-14th-revel-canyon-i7--1-.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC Mini Asus ROG NUC 14TH Scorpion Canyon U9', 1, 39, 338000, N'U9-185H/32GB/1TB PCIE/VGA RTX4070/DDR5/WF+BT/WIN11/BLACK NEW', 2, N'frame_283_1__3.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC Mini ASUS NUC 14 Essential RNUC14MNK9700000', 1, 30, 2609000, N'INTEL CORE N97 DDR5/WIFI6/NO-OS', 1, N'51177_b____mini_pc_asus_nuc_14_essential_rnuc14mnk97__5_.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC Mini Asus NUC 14 Pro Revel Canyon U3', 1, 6, 161000, N'C3-100U/DDR5/WF+BT/NON OS/BLACK', 2, N'gaming_001_1_8.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC CPS X MSI Gaming Intel i5 Gen 12', 2, 27, 2966000, N'MSI G255F 25 inch/Intel i5 12400F/GeForce RTX3060/RGB 16GB(1x16GB) 3200MHz DDR4', 2, N'Screenshot 2025-07-22 041202.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC CPS X ASUS Gaming Intel i3 Gen 12', 1, 24, 1255000, N'ASUS G255F 25 inch/Intel i3 12100F/GeForce RTX3060/RGB 16GB(1x16GB) 3200MHz DDR4', 1, N'Screenshot 2025-07-22 041310.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'PC CPS Asus Gaming Intel I7 Gen 12', 1, 6, 1052000, N'ASUS G255F 25 inch/i7 12700F/16GB DDR4/RTX 4060/SSD 500GB', 2, N'Screenshot 2025-07-22 041357.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard Asus PRIME H610M-K D4', 3, 28, 2566000, N'micro-ATX size, DDR4, PCIe 4.0, M.2 slot, Realtek 1 Gb Ethernet, HDMI/D-Sub, USB 3.2 Gen 1 ports', 1, N'81hw9NOhNNL.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard Asus TUF Gaming B760M-PLUS Wifi D4', 3, 25, 918000, N'two M.2 slots, 12+1 DrMOS, DDR4, Realtek 2.5Gb Ethernet, Intel® Wi-Fi 6, HDMI®, DisplayPort,', 1, N'69817_12646_12646_asus_tuf_gaming_b760m_plus_wifi_d4_df.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard ASUS ROG Strix Z790-F Gaming Wifi II DDR5', 3, 33, 1126000, N'Intel® Z790 LGA 1700 ATX, 16+1+2 power stages, DDR5, five M.2 slots, PCIe® 5.0, WiFi 7', 2, N'25849-rog-strix-z790-f-gaming-wifi-ii-01.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard MSI PRO B760M-A WIFI DDR4', 4, 10, 2966000, N'mATX size. Socket 1700 supports Intel CPU 12 & 13, 4 DDR4 RAM slots, M2 SSD slot, SATA.', 0, N'250_43784_mainboard_msi_pro_z790_p_wifi_ddr5_anphat88.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard MSI Pro H610M-S DDR4', 4, 24, 896000, N'LGA 1700 socket, Intel H610 chipset to support a wide range of Intel Core, Intel Celeron and Pentium Gold CPUs.', 1, N'55972_mainboard_msi_pro_h610m_s_ddr4_4.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard MSI Z790 Gaming Plus WF DDR5', 4, 40, 1936000, N'LGA 1700 socket · Supports DDR5 memory, up to 7200+(OC) MHz', 1, N'Screenshot 2025-07-22 020027.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard Gigabyte B760M Gaming Plus Wifi D4', 5, 16, 1205000, N'Socket, LGA 1700 - Support for the 14th, 13th, and 12th Generation Intel® Core™, Pentium® Gold', 1, N'mainboard-gigabyte-b760m-gaming-plus-wifi-ddr4.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard Gigabyte H510M-H', 5, 6, 1265000, N'Supports 11th and 10th Gen Intel® Core™ Series Processors · Dual Channel Non-ECC Unbuffered DDR4, 2 DIMMs · Ultra-Fast M.', 1, N'Mainboard-May-tinh-ban-Gigabyte-H510M-H-V2_5.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Mainboard Gigabyte B850 A Elite WF7', 5, 36, 2780000, N'14*+2+2 Twin Digital VRM Design. 6-Layer PCB; Mid-Loss PCB · Socket AM5 Supports AMD Ryzen™ Series Processors', 2, N'Mainboard-Gigabyte-B850-A-ELITE-WF7-(1).jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i3 10th', 6, 31, 461000, N'6M cache, up to 4.30 GHz', 1, N'52615_i3_10100_1000.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i3 11th', 6, 38, 151000, N'Number of Cores. 2 ; Total Threads. 4 ; Max Turbo Frequency. 4.10 GHz ; Cache. 6 MB Intel® Smart Cache ; Bus Speed. 4 GT/s.', 2, N'2022_10_17_638015921933458968_intel-core-i3-1115g4-1.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i3 12th', 6, 25, 2175000, N'LGA 1700. Cores. 4. Threads. 8. Clock Speed. 3.30 GHz up to 4.30 GHz.', 1, N'unnamed.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i5 10th', 6, 40, 1566000, N'6 cores 12 threads, default clock speed 2.9GHz and can go up to 4.3GHz', 0, N'Screenshot 2025-07-22 031127.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i5 11th', 6, 14, 1409000, N'Number of Cores. 4 ; Total Threads. 8 ; Max Turbo Frequency. 4.20 GHz ; Cache. 8 MB Intel® Smart Cache ; Bus Speed. 4 GT/s.', 0, N'i5-11th-Gen-with-Graphics-1.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i5 12th', 6, 40, 721000, N'18M Cache, up to 4.40 GHz', 0, N'504x_d9dd85d5-d230-48cc-8dd6-bee8048fdce9.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i7 10th', 6, 50, 365000, N'Number of cores. 8 ; Total threads. 16 ; Max turbo frequency. 5.10 GHz ; Intel® Turbo Boost Max Technology Frequency 3.0 · 5.10 GHz', 1, N'Intel_Core_i7-10700K_LGA1200_Unlocked_Desktop_Processor_From_The_Peripheral_Store_01.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i7 11th', 6, 23, 1413000, N'Number of cores:8. Number of Threads, 16. Processor base/max frequency, 2.5GHz turbo 4.9GHz.', 2, N'71idAEIDhvL._UF894,1000_QL80_.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i7 12th', 6, 10, 1490000, N'3.6GHz turbo up to 4.9Ghz, 12 cores 20 threads, 25MB Cache, 65W', 2, N'250_41141_cpu_intel___core____i7_12700_anphat88.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i9 10th', 6, 7, 2359000, N'Number of Cores. 10 ; Total Threads. 20 ; Max Turbo Frequency. 5.30 GHz ; Intel® Thermal Velocity Boost Frequency. 5.30 GHz', 0, N'images.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i9 11th', 6, 31, 524000, N'3.50GHz Turbo Up To 5.30GHz, 8 Cores 16 Threads, 20M Cache, Rocket Lake', 2, N'1_f353c9fa-f1bb-4949-9d42-1312cb97401e.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Intel Core i9 12th', 6, 36, 2410000, N'3.2GHZ TURBO UP TO 5.2GHZ, 16 CORES 24 THREADS, 30MB Cache, 125W', 2, N'CPU-Intel-Core-i9-12900.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU Intel Core Ultra 5 245K', 6, 25, 603000, N'24M Cache, up to 5.20 GHz', 0, N'cpu-intel-core-ultra-5-245k-up-to-5-2ghz-14-cores-14-threads-24mb-02.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU Intel Pentium Gold G6405', 6, 12, 2423000, N'Total Threads 4, Processor Base Frequency 4.10 GHz, Cache 4 MB Intel® Smart Cache, Bus Speed 8 GT/s, TDP 58 W.', 2, N'1_f198a79307fd40c2b352427c1fbba037_master.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU AMD Ryzen 5 5500', 7, 43, 295000, N'Cores6; Threads12; Max ClockUp to 4.2GHz; Base Clock3.6GHz', 1, N'cpu-amd-ryzen-5-5500-16m-phong-vu-kinh-bac.png2-min.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU AMD Ryzen 7 7800X3D', 7, 34, 625000, N'4.2GHz Boost 5.0GHz / 8 cores 16 threads', 0, N'ryzen-7-7800x3d-600x600_30d6f05d43524a6c950830a366e4f4eb_2fb2daf9ef7d4faf92f0b1ed1612b1a0.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU AMD Ryzen 7 5700X', 7, 21, 652000, N'3.4GHz Boost 4.6GHz / 8 cores 16 threads / 32MB / AM4; Number of processing threads: 16; Processing speed: Base clock 3.4GHz, maximum clock 4.6GHz.', 0, N'Ryzen-7-5700X-1670491457.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU AMD Ryzen 9 9950X', 7, 46, 2904000, N'Up to 5.7 GHz, 16 Cores 32 Threads, 64MB Cache, AM5', 1, N'9950x_6455e44e2494442aabb35c74b75b1215_master.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'CPU AMD Ryzen Threadripper PRO 5995WX', 7, 36, 168000000, N'2.7GHz Boost 4.5GHz / 64 cores 128 threads / 292MB / sWRX8.', 2, N'cpu-amd-ryzen-threadripper-pro-5995wx-27-ghz-upto-42.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'RX6600 CLD 8G', 8, 50, 2515000, N'Stream Processors : 1792; Process : 7 nm; Memory : 8GB GDDR6; Card Bus : PCI-E 4.0 x 8', 0, N'z5550778520192_c2f66c5bd1d0ab68feb901ccf6453223_3e8d67a93c374e06b30377fe74ec4a21.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SAPPHIRE Radeon RX 7800XT PURE Gaming OC 16GB GDDR6', 8, 23, 1042000, N'GPU: Game Clock: Up to 2169 MHz; Memory: 16GB/256-bit DDR6. 19.5 Gbps Efficiency; Stream Processors: 3840', 0, N'vga-sapphire-radeon-rx-7800xt-pure-gaming-oc-16gb_6_.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SAPPHIRE Radeon RX 7700XT PURE Gaming OC 12GB GDDR6', 8, 34, 464000, N'Connection standard, PCI-Express 4.0 x16 ; Memory clock, 18 Gbps Effective ; Memory, 12GB/192 bit GDDR6', 1, N'sapphire-pure-radeon-rx-7700-xt-gaming-oc-12gb-gddr6_01_600x600_crop_center.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA Gigabyte Radeon Pro W7900 AI Top 48G', 8, 36, 129000000, N'Capacity. 48GB. Memory type. GDDR6. Memory interface. 384-bit. GPU type. Radeon PRO W7900. Interface PCI.', 0, N'vga-gigabyte-radeon-pro-w7900-ai-top-48g_1_.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA Asus ROG Astral Geforce RTX 5090', 9, 16, 110000000, N'Memory: 32GB GDDR7 (512-bit) - OC mode: 2610 MHz. Default mode: 2580 MHz(Boost clock)', 1, N'250-11614-tnc-store-card-man-hinh-asus-rog-astral-lc-geforce-rtx-5090-32gb-gddr7--1-.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA MSI GeForce RTX 3050 VENTUS 2X 6G OC', 9, 37, 1387000, N'Capacity. 6GB. Memory type. GDDR6. Memory interface. 96-bit.', 2, N'vga-msi-rtx-3050-ventus-2x-e-6gb-oc-0.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA Asus Tuf Gaming Geforce RTX 5080 OC 16GB TUF-RTX5080-O16G-GAMING', 9, 43, 45000000, N'With 16GB of 30 Gbps GDDR7 memory and a 256-bit memory interface,', 1, N'Frame-30-180-600x600.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA MSI GeForce RTX 4060 VENTUS 2X Black 8G OC', 9, 43, 1545000, N'Cuda Core, 3072 Units ; Memory Speed, 17 Gbps ; Memory, 8GB GDDR6 ; Memory Bus, 128-bit', 0, N'title.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'VGA Asus Prime Geforce RTX 5060 Ti 16GB OC PRIME-RTX5060TI-O16G', 9, 33, 355000, N'CUDA Cores: 4608 · AI Performance: 772 TOPs · Clock Speed: 2647MHz (OC mode) · Connection Standard: PCI Express 5.0', 2, N'card-man-hinh-asus-prime-geforce-rtx-5060-ti-16gb-gddr7-oc-edition-8.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'NVIDIA RTX 6000 ADA', 9, 42, 300000000, N'18,176 CUDA Cores, 568 Tensor Cores, 142 RT Cores, 48GB GDDR6 Memory with ECC, Memory Bandwidth: 960 GB/s', 1, N't16_c6c33f7876b2457e9663b00d3218a8ee_1024x1024.jpg');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'RAM Laptop Kingston 1.2V 8GB 3200MHz KVR32S22S8/8', 10, 47, 895000, N'RAM Type. DDR4. Capacity. 8GB. Bus. 3200Mhz. Latency. CL22', 1, N'kvr32s22s8-8.webp');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'RAM Laptop Kingston Sodimm 1.2V 16GB 3200MHz CL22', 10, 23, 2865000, N'RAM Bus Speed: 3200 MHz · Voltage: 1.2V.', 1, N'Screenshot 2025-07-22 005048.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Ram PC Kingston Fury Beast RGB DDR5 5600MHz 64GB (2*32GB)', 10, 5, 574000, N'Capacity. 64GB (2x32GB). Bus. 5600Mhz', 0, N'Screenshot 2025-07-22 005235.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'RAM Kingston DDR5 5600MT/s 32GB KVR56S46BD8-32', 10, 23, 941000, N'Bus Speed: 5600MT/s', 1, N'Screenshot 2025-07-22 005329.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Ram Laptop Samsung 8GB 5600MHZ DDR5', 11, 12, 1153000, N'Bus: 5600MHz', 2, N'Screenshot 2025-07-22 005520.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD Kingston NV3 PCIe 4.0 NVMe 1TB', 12, 21, 1735000, N'Read/write speeds up to 6,000/4,000 MB/s', 2, N'Screenshot 2025-07-22 005651.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD Kingston SKC3000 M2 PCIe NVME Gen 4 2TB', 12, 49, 2976000, N'For read/write speeds up to 7,000MB/s', 1, N'Screenshot 2025-07-22 005730.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD Kingston SKC3000 M2 PCIe NVME Gen 4 1TB', 12, 6, 2754000, N'Read speed (SSD), 7000MB/s ; Write speed (SSD), 3900MB/s', 1, N'Screenshot 2025-07-22 005803.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD Kingston SKC3000 M2 PCIe NVME Gen 4 512GB', 12, 32, 409000, N'Read speed (SSD), 7000MB/s ; Write speed (SSD), 3900MB/s', 1, N'Screenshot 2025-07-22 005905.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD Kingston NV2 M.2 PCIe Gen4 NVMe 250GB', 12, 34, 107000, N'For read/write speeds up to 3000MB/s', 2, N'Screenshot 2025-07-22 010024.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN850X NVME PCIe Gen4 x4 M.2 2280 4TB', 13, 15, 1981000, N'read speed 7300MB/s, write 6600MB/s', 1, N'Screenshot 2025-07-22 010107.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN850X NVME PCIe Gen4 x4 M.2 2280 8TB', 13, 34, 457000, N'read speed 7200MB/s, write 6600MB/s', 1, N'Screenshot 2025-07-22 041528.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN850X NVME PCIe Gen4 x4 M.2 2280 1TB', 13, 35, 1070000, N'read speed 7300MB/s, write 6600MB/s', 0, N'Screenshot 2025-07-22 010230.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN850X NVME PCIe Gen4 x4 M.2 2280 2TB', 13, 25, 301000, N'read speed 7,300MB/s and write 6,900MB/s', 2, N'Screenshot 2025-07-22 010318.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN7100 NVME PCIe Gen4 x4 M.2 2280 2TB', 13, 41, 2980000, N'read speed 7,250MB/s and write 6,900MB/s', 2, N'Screenshot 2025-07-22 010407.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN7100 NVME PCIe Gen4 x4 M.2 2280 4TB', 13, 43, 2120000, N'read speed 7,000MB/s and write 6,900MB/s', 0, N'Screenshot 2025-07-22 010440.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN7100 NVME PCIe Gen4 x4 M.2 2280 1TB', 13, 20, 1489000, N'read speed 7,250MB/s and write 6,900MB/s', 1, N'Screenshot 2025-07-22 010537.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'SSD WD Black SN7100 NVME PCIe Gen4 x4 M.2 2280 500GB', 13, 47, 1742000, N'read speed 7,000MB/s and write 6,900MB/s', 1, N'Screenshot 2025-07-22 041836.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case ASUS TUF Gaming GT302 ARGB', 14, 13, 424000, N'4*140mm PWM ARGB fans', 0, N'Screenshot 2025-07-22 011304.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case ASUS Prime AP201 M-ATX', 14, 32, 2476000, N'Dimensions: 350 x 205 x 460 mm; GPU length: Maximum 338mm; CPU cooler height: 170mm; Included fan: 1 * 120mm fan', 0, N'Screenshot 2025-07-22 035857.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case Asus ROG Hyperion GR701 BTF Edition', 14, 8, 1130000, N'Support dual 420mm radiator, four 140mm fans', 0, N'Screenshot 2025-07-22 040045.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case ASUS ROG Hyperion GR701 E-ATX', 14, 32, 1825000, N'Support dual 420mm radiator, four 140mm fans', 0, N'Screenshot 2025-07-22 011457.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case MSI MAG FORGE 120A AIRFLOW', 15, 9, 223000, N'Support AIO water cooling', 1, N'Screenshot 2025-07-22 011532.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case MSI MAG FORGE 320R AIRFLOW', 15, 9, 2943000, N'Support AIO water cooling', 2, N'Screenshot 2025-07-22 011634.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case DeepCool Macube 110', 16, 32, 1681000, N'Equipped with up to 6 fans', 0, N'Screenshot 2025-07-22 011726.png');
INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) VALUES (N'Case DeepCool CK560 3F', 16, 42, 1806000, N'Fan support. Front: 3×120mm / 2×140mm.', 2, N'Screenshot 2025-07-22 011757.png');

INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM001', 1, '2024-11-06 05:30:02', 6, 662748);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM002', 2, '2024-11-06 05:40:02', 8, 957967);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM003', 3, '2024-11-06 05:50:02', 9, 797293);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM004', 4, '2024-11-06 06:00:02', 8, 795500);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM005', 5, '2024-11-06 06:10:02', 6, 218325);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM006', 6, '2024-11-06 06:20:02', 7, 360385);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM007', 7, '2024-11-06 06:30:02', 6, 316018);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM008', 8, '2024-11-06 06:40:02', 10, 320662);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM009', 9, '2024-11-06 06:50:02', 10, 903258);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM010', 10, '2024-11-06 07:00:02', 8, 732916);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM011', 11, '2024-11-06 07:10:02', 10, 749327);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM012', 12, '2024-11-06 07:20:02', 5, 261966);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM013', 13, '2024-11-06 07:30:02', 10, 511404);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM014', 14, '2024-11-06 07:40:02', 8, 352065);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM015', 15, '2024-11-06 07:50:02', 5, 989605);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM016', 16, '2024-11-06 08:00:02', 7, 698176);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM017', 17, '2024-11-06 08:10:02', 8, 311648);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM018', 18, '2024-11-06 08:20:02', 5, 946489);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM019', 19, '2024-11-06 08:30:02', 10, 539625);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM020', 20, '2024-11-06 08:40:02', 9, 556928);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM021', 21, '2024-11-06 08:50:02', 10, 360845);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM022', 22, '2024-11-06 09:00:02', 9, 592170);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM023', 23, '2024-11-06 09:10:02', 7, 645538);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM024', 24, '2024-11-06 09:20:02', 5, 958361);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM025', 25, '2024-11-06 09:30:02', 9, 574694);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM026', 26, '2024-11-06 09:40:02', 10, 280715);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM027', 27, '2024-11-06 09:50:02', 6, 554309);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM028', 28, '2024-11-06 10:00:02', 5, 622160);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM029', 29, '2024-11-06 10:10:02', 8, 289868);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM030', 30, '2024-11-06 10:20:02', 8, 212044);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM031', 31, '2024-11-06 10:30:02', 8, 315208);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM032', 32, '2024-11-06 10:40:02', 9, 280593);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM033', 33, '2024-11-06 10:50:02', 5, 441714);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM034', 34, '2024-11-06 11:00:02', 5, 328368);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM035', 35, '2024-11-06 11:10:02', 6, 518752);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM036', 36, '2024-11-06 11:20:02', 9, 896036);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM037', 37, '2024-11-06 11:30:02', 5, 809525);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM038', 38, '2024-11-06 11:40:02', 10, 360973);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM039', 39, '2024-11-06 11:50:02', 8, 828036);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM040', 40, '2024-11-06 12:00:02', 8, 372511);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM041', 41, '2024-11-06 12:10:02', 9, 591666);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM042', 42, '2024-11-06 12:20:02', 9, 577314);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM043', 43, '2024-11-06 12:30:02', 8, 479609);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM044', 44, '2024-11-06 12:40:02', 8, 731840);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM045', 45, '2024-11-06 12:50:02', 7, 561562);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM046', 46, '2024-11-06 13:00:02', 9, 928457);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM047', 47, '2024-11-06 13:10:02', 8, 450961);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM048', 48, '2024-11-06 13:20:02', 5, 304824);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM049', 49, '2024-11-06 13:30:02', 10, 376246);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM050', 50, '2024-11-06 13:40:02', 6, 489571);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM051', 51, '2024-11-06 13:50:02', 7, 695350);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM052', 52, '2024-11-06 14:00:02', 8, 645130);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM053', 53, '2024-11-06 14:10:02', 6, 475127);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM054', 54, '2024-11-06 14:20:02', 6, 880141);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM055', 55, '2024-11-06 14:30:02', 7, 953564);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM056', 56, '2024-11-06 14:40:02', 5, 766049);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM057', 57, '2024-11-06 14:50:02', 9, 346849);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM058', 58, '2024-11-06 15:00:02', 8, 600728);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM059', 59, '2024-11-06 15:10:02', 9, 719953);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM060', 60, '2024-11-06 15:20:02', 9, 923194);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM061', 61, '2024-11-06 15:30:02', 5, 733086);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM062', 62, '2024-11-06 15:40:02', 10, 958232);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM063', 63, '2024-11-06 15:50:02', 6, 950239);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM064', 64, '2024-11-06 16:00:02', 10, 855384);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM065', 65, '2024-11-06 16:10:02', 10, 927476);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM066', 66, '2024-11-06 16:20:02', 10, 440872);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM067', 67, '2024-11-06 16:30:02', 6, 706477);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM068', 68, '2024-11-06 16:40:02', 10, 464196);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM069', 69, '2024-11-06 16:50:02', 5, 289763);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM070', 70, '2024-11-06 17:00:02', 5, 819774);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM071', 71, '2024-11-06 17:10:02', 5, 204320);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM072', 72, '2024-11-06 17:20:02', 7, 760465);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM073', 28, '2024-11-06 17:30:02', 10, 417890);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM074', 52, '2024-11-06 17:40:02', 8, 511434);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM075', 7, '2024-11-06 17:50:02', 8, 934187);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM076', 7, '2024-11-06 18:00:02', 9, 621106);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM077', 10, '2024-11-06 18:10:02', 6, 765874);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM078', 35, '2024-11-06 18:20:02', 9, 239098);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM079', 36, '2024-11-06 18:30:02', 7, 221451);
INSERT INTO Imports (ImportCode, CategoryID, CreatedAt, Quantity, Price) VALUES ('IM080', 65, '2024-11-06 18:40:02', 8, 994924);

-- Insert data into Products
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0100', 0, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0101', 1, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0102', 1, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0103', 1, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0104', 1, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0105', 1, 1);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0106', 0, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0107', 0, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0108', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0109', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0110', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0111', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0112', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0113', 1, 2);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0114', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0115', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0116', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0117', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0118', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0119', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0120', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0121', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0122', 1, 3);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0123', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0124', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0125', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0126', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0127', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0128', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0129', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0130', 1, 4);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0131', 0, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0132', 1, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0133', 1, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0134', 1, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0135', 1, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0136', 1, 5);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0137', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0138', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0139', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0140', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0141', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0142', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0143', 1, 6);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0144', 0, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0145', 1, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0146', 1, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0147', 1, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0148', 1, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0149', 1, 7);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0150', 0, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0151', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0152', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0153', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0154', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0155', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0156', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0157', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0158', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0159', 1, 8);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0160', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0161', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0162', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0163', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0164', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0165', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0166', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0167', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0168', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0169', 1, 9);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0170', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0171', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0172', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0173', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0174', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0175', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0176', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0177', 1, 10);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0178', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0179', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0180', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0181', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0182', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0183', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0184', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0185', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0186', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0187', 1, 11);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0188', 1, 12);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0189', 1, 12);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0190', 1, 12);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0191', 1, 12);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0192', 1, 12);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0193', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0194', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0195', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0196', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0197', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0198', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0199', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0200', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0201', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0202', 1, 13);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0203', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0204', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0205', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0206', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0207', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0208', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0209', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0210', 1, 14);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0211', 1, 15);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0212', 1, 15);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0213', 1, 15);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0214', 1, 15);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0215', 1, 15);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0216', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0217', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0218', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0219', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0220', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0221', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0222', 1, 16);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0223', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0224', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0225', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0226', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0227', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0228', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0229', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0230', 1, 17);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0231', 1, 18);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0232', 1, 18);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0233', 0, 18);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0234', 1, 18);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0235', 1, 18);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0236', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0237', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0238', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0239', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0240', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0241', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0242', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0243', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0244', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0245', 1, 19);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0246', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0247', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0248', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0249', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0250', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0251', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0252', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0253', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0254', 1, 20);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0255', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0256', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0257', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0258', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0259', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0260', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0261', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0262', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0263', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0264', 1, 21);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0265', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0266', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0267', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0268', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0269', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0270', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0271', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0272', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0273', 1, 22);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0274', 0, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0275', 0, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0276', 1, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0277', 1, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0278', 1, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0279', 1, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0280', 1, 23);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0281', 1, 24);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0282', 1, 24);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0283', 1, 24);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0284', 1, 24);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0285', 1, 24);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0286', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0287', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0288', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0289', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0290', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0291', 1, 25);	
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0292', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0293', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0294', 1, 25);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0295', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0296', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0297', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0298', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0299', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0300', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0301', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0302', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0303', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0304', 1, 26);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0305', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0306', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0307', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0308', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0309', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0310', 1, 27);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0311', 1, 28);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0312', 1, 28);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0313', 1, 28);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0314', 1, 28);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0315', 1, 28);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0316', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0317', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0318', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0319', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0320', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0321', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0322', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0323', 1, 29);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0324', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0325', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0326', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0327', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0328', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0329', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0330', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0331', 1, 30);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0332', 0, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0333', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0334', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0335', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0336', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0337', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0338', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0339', 1, 31);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0340', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0341', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0342', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0343', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0344', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0345', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0346', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0347', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0348', 1, 32);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0349', 1, 33);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0350', 1, 33);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0351', 1, 33);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0352', 1, 33);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0353', 1, 33);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0354', 1, 34);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0355', 1, 34);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0356', 1, 34);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0357', 1, 34);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0358', 1, 34);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0359', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0360', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0361', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0362', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0363', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0364', 1, 35);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0365', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0366', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0367', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0368', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0369', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0370', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0371', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0372', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0373', 1, 36);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0374', 1, 37);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0375', 1, 37);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0376', 1, 37);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0377', 1, 37);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0378', 1, 37);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0379', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0380', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0381', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0382', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0383', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0384', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0385', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0386', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0387', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0388', 1, 38);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0389', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0390', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0391', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0392', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0393', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0394', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0395', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0396', 1, 39);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0397', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0398', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0399', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0400', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0401', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0402', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0403', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0404', 1, 40);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0405', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0406', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0407', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0408', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0409', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0410', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0411', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0412', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0413', 1, 41);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0414', 0, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0415', 0, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0416', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0417', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0418', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0419', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0420', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0421', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0422', 1, 42);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0423', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0424', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0425', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0426', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0427', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0428', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0429', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0430', 1, 43);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0431', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0432', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0433', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0434', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0435', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0436', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0437', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0438', 1, 44);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0439', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0440', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0441', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0442', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0443', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0444', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0445', 1, 45);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0446', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0447', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0448', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0449', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0450', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0451', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0452', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0453', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0454', 1, 46);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0455', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0456', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0457', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0458', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0459', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0460', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0461', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0462', 1, 47);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0463', 1, 48);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0464', 1, 48);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0465', 1, 48);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0466', 1, 48);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0467', 1, 48);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0468', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0469', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0470', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0471', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0472', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0473', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0474', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0475', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0476', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0477', 1, 49);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0478', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0479', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0480', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0481', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0482', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0483', 1, 50);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0484', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0485', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0486', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0487', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0488', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0489', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0490', 1, 51);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0491', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0492', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0493', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0494', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0495', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0496', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0497', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0498', 1, 52);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0499', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0500', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0501', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0502', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0503', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0504', 1, 53);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0505', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0506', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0507', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0508', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0509', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0510', 1, 54);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0511', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0512', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0513', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0514', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0515', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0516', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0517', 1, 55);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0518', 0, 56);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0519', 1, 56);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0520', 1, 56);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0521', 1, 56);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0522', 1, 56);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0523', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0524', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0525', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0526', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0527', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0528', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0529', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0530', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0531', 1, 57);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0532', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0533', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0534', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0535', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0536', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0537', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0538', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0539', 1, 58);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0540', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0541', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0542', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0543', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0544', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0545', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0546', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0547', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0548', 1, 59);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0549', 0, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0550', 0, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0551', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0552', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0553', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0554', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0555', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0556', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0557', 1, 60);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0558', 1, 61);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0559', 1, 61);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0560', 1, 61);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0561', 1, 61);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0562', 1, 61);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0563', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0564', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0565', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0566', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0567', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0568', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0569', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0570', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0571', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0572', 1, 62);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0573', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0574', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0575', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0576', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0577', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0578', 1, 63);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0579', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0580', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0581', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0582', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0583', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0584', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0585', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0586', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0587', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0588', 1, 64);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0589', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0590', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0591', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0592', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0593', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0594', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0595', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0596', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0597', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0598', 1, 65);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0599', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0600', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0601', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0602', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0603', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0604', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0605', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0606', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0607', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0608', 1, 66);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0609', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0610', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0611', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0612', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0613', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0614', 1, 67);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0615', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0616', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0617', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0618', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0619', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0620', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0621', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0622', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0623', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0624', 1, 68);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0625', 1, 69);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0626', 1, 69);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0627', 1, 69);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0628', 1, 69);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0629', 1, 69);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0630', 1, 70);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0631', 1, 70);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0632', 1, 70);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0633', 1, 70);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0634', 1, 70);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0635', 1, 71);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0636', 1, 71);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0637', 1, 71);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0638', 1, 71);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0639', 1, 71);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0640', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0641', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0642', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0643', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0644', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0645', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0646', 1, 72);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0647', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0648', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0649', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0650', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0651', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0652', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0653', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0654', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0655', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0656', 1, 73);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0657', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0658', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0659', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0660', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0661', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0662', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0663', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0664', 1, 74);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0665', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0666', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0667', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0668', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0669', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0670', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0671', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0672', 1, 75);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0673', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0674', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0675', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0676', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0677', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0678', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0679', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0680', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0681', 1, 76);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0682', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0683', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0684', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0685', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0686', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0687', 1, 77);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0688', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0689', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0690', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0691', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0692', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0693', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0694', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0695', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0696', 1, 78);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0697', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0698', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0699', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0700', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0701', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0702', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0703', 1, 79);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0704', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0705', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0706', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0707', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0708', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0709', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0710', 1, 80);
INSERT INTO Products (ProductCode, Status, ImportID) VALUES ('PRD0711', 1, 80);
INSERT INTO Warranties (WarrantyPeriod, Description) VALUES
(3, '3-month warranty'),
(6, '6-month warranty'),
(12, '12-month warranty'),
(24, '24-month warranty');

INSERT INTO WarrantyDetails (WarrantyID, BrandComID, Price, Status) VALUES
(4, 1, 156820, 1),
(3, 1, 89027, 1),
(1, 1, 97200, 1),
(2, 2, 115342, 1),
(3, 2, 374962, 1),
(4, 2, 53829, 1),
(3, 3, 496339, 1),
(4, 4, 165743, 1),
(3, 4, 155628, 1),
(2, 4, 450361, 1),
(2, 5, 255019, 1),
(2, 6, 374823, 1),
(1, 6, 428570, 1),
(3, 6, 147971, 1),
(1, 7, 479335, 1),
(3, 7, 289827, 1),
(2, 7, 334421, 1),
(1, 8, 339192, 1),
(3, 9, 338276, 1),
(4, 10, 394175, 1),
(4, 11, 287302, 1),
(1, 12, 425416, 1),
(4, 12, 249685, 1),
(2, 12, 383527, 1),
(1, 13, 103964, 1),
(3, 13, 427141, 1),
(4, 14, 168675, 1),
(1, 14, 112847, 1),
(2, 14, 192019, 1),
(3, 15, 488503, 1),
(2, 15, 479436, 1),
(4, 15, 229079, 1);



INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012345', 0, 5, '2022-04-27 05:34:02', 'Customer 2 Address #49', 'Nguyen Van A', '0123456789', 7632869, 3, 1);

INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012346', 0, 5, '2025-04-27 05:34:02', 'Customer 2 Address #49', 'Nguyen Van A', '0123456789', 7632869, 3, 1);

INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012347', 1,5, '2024-12-11 05:34:02', 'Customer 2 Address #50', 'Le Thi B', '0987654321', 9077684, 3, 2);

INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012348', 0, 6, '2024-12-11 05:34:02', 'Customer 2 Address #50', 'Pham Van C', '0367890123', 9077684, 5, 2);



INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012349', 0, 6, '2024-01-11 05:34:02', 'Customer 2 Address #50', 'Pham Van C', '0367890123', 9077684, 0, 1);

INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012340', 0, 6, '2024-12-11 05:34:02', 'Customer 2 Address #50', 'Pham Van C', '0367890123', 9077684, 1, 2);

INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, FullName, PhoneNumber, TotalAmount, Status, PaymentStatusID)
VALUES ('012351', 0, 6, '2024-12-11 05:34:02', 'Customer 2 Address #50', 'Pham Van C', '0367890123', 9077684, 2, 2);

-- Insert data into CartItems
INSERT INTO CartItems (UserID, CategoryID, WarrantyDetailID, Quantity, Status) VALUES (5, 16, 6, 1, 1);
INSERT INTO CartItems (UserID, CategoryID, WarrantyDetailID, Quantity, Status) VALUES (5, 12, 5, 1, 1);
INSERT INTO CartItems (UserID, CategoryID, WarrantyDetailID, Quantity, Status) VALUES (5, 71, 12, 1, 1);
INSERT INTO CartItems (UserID, CategoryID, WarrantyDetailID, Quantity, Status) VALUES (5, 12, 1, 1, 0);


-- Insert data into OrderItems
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (1, 1, 1, 1574055);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (1, 8, 1, 2870431);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (1, 2, 2, 555857);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (1, 5, 1, 533325);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (1, 7, 1, 2312204);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (2, 23, 2, 2846069);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (2, 42, 2, 1228975);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (4, 31, 1, 2619488);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (4, 56, 1, 1030626);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (4, 60, 2, 1049969);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (5, 60, 2, 1049969);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (6, 60, 2, 1049969);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (6, 1, 2, 1049969);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (7, 60, 2, 1049969);
INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price) VALUES (7, 1, 2, 1049969);

-- Insert valid OrderDetails data
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status, Start, End) VALUES (1, 1, 1, 7388968, 160277, 1,'2022-04-27', '2024-04-27' );
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (2, 51, 4, 960190, 368646, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (3, 7, 4, 6064089, 152252, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (3, 8, 5, 4274287, 171350, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (4, 32, 3, 1768543, 131820, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (5, 45, 2, 2703833, 150941, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (6, 175, 11, 5893887, 267216, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (6, 176, 12, 863208, 50716, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (7, 315, 19, 5176824, 329730, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (7, 316, 19, 6234664, 266637, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (8, 233, 13, 1477091, 354040, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (9, 419, 21, 5565144, 282424, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (10, 450, 23, 1807914, 253927, 1);
INSERT INTO OrderDetails (OrderItemID, ProductID, WarrantyDetailID, UnitPrice, WarrantyPrice, Status) VALUES (10, 451, 24, 7407705, 255069, 1);

INSERT INTO Feedbacks (UserID, Content, OrderItemID, CreatedAt, Rate, Status) VALUES 
(5, 'Feedback #1 from user 2 on order item 91.', 1, '2025-06-03 05:46:35', 2, 1),
(5, 'Feedback #2 from user 2 on order item 95.', 2, '2025-03-11 05:46:35', 4, 1),
(5, 'Feedback #3 from user 2 on order item 16.', 3, '2025-03-11 05:46:35', 4, 1),
(5, 'Feedback #4 from user 2 on order item 29.', 4, '2025-06-03 05:46:35', 2, 1),
(5, 'Feedback #5 from user 2 on order item 75.', 5, '2025-03-11 05:46:35', 3, 1),
(5, 'Feedback #6 from user 2 on order item 41.', 6, '2025-05-02 05:46:35', 1, 1),
(5, 'Feedback #7 from user 2 on order item 92.', 7, '2025-04-04 05:46:35', 2, 1),
(6, 'Feedback #8 from user 2 on order item 65.', 8, '2025-05-02 05:46:35', 4, 1),
(6, 'Feedback #9 from user 2 on order item 91.', 9, '2025-02-28 05:46:35', 5, 1),
(6, 'Feedback #10 from user 2 on order item 91.', 10, '2025-05-10 05:46:35', 3, 1);

INSERT INTO Blogs_category (Bc_id, Bc_name)
VALUES
(1, 'Technology News'),
(2, 'User Guides'),
(3, 'Product Reviews'),
(4, 'Promotions & Events');
	
-- Dữ liệu mẫu chèn vào bảng Post
INSERT INTO Post (Post_id, Title, Author, Updated_date, Content, Bc_id, Thumbnail, Brief, Add_id) VALUES
(1, 'Introduction to Laptop Components', 'ThuongTN', NOW(),
'Laptops have become an essential part of modern life, used for work, education, entertainment, and communication.

To make informed decisions about purchasing, upgrading, or maintaining a laptop, it is important to understand its internal components and how they function together.

1. Central Processing Unit (CPU)
2. Graphics Processing Unit (GPU)
3. Random Access Memory (RAM)
4. Storage Drive (HDD or SSD)
5. Motherboard
6. Cooling System
7. Display
8. Battery
9. Input Devices
10. Connectivity and Ports
11. Audio and Webcam

Understanding these components empowers users to troubleshoot, upgrade, and optimize their laptops.', 
1, 'https://platform.labdoo.org/sites/default/files/content/laptop-wiki/laptop2.png', 
'Overview of key laptop components for beginners.', 1),

(2, 'Proper Laptop Cleaning and Maintenance Guide', 'ThuongTN', NOW(),
'Laptops collect dust and bacteria over time. This guide includes:
- Why maintenance matters
- Tools needed
- Cleaning steps for screen, keyboard, internals
- Maintenance schedule and hygiene tips', 
2, 'https://i.pcmag.com/imagery/articles/05uthAxgkcAOKdZzRzXfMvf-11..v1617741381.jpg', 
'Simple and effective laptop cleaning guide.', 2),

(3, 'Review of ASUS ROG Swift 27-inch Gaming Monitor', 'NinhTT', NOW(),
'Compare OLED and IPS gaming monitors in ASUS ROG lineup: PG27AQDM, PG27UQR, PG279Q, PG27UQ. Analyze refresh rate, HDR, G-Sync, contrast, and use cases.', 
3, 'https://i.rtings.com/assets/products/1kK1PmWU/asus-rog-swift-oled-pg27aqdm/design-medium.jpg?format=auto', 
'Detailed review of ASUS ROG Swift 27-inch gaming monitor.', 1),

(4, 'Optimizing Cooling Systems for Gaming PCs', 'ThuongTN', NOW(),
'Cooling tips:
- Improve airflow
- Apply thermal paste
- Adjust fan curves
- Clean regularly
- Monitor temperatures

Ideal temps: CPU < 85°C, GPU < 80°C.', 
2, 'https://www.intel.com/content/dam/www/central-libraries/us/en/images/opened-neon-lighted-computer-case-rwd.jpg', 
'Tips to optimize cooling systems for gaming PCs.', 1),

(5, 'PC Component Promotions for May 2025', 'NinhTT', NOW(),
'Deals on SSDs, CPUs, RAM, monitors, and prebuilt PCs from Dell, Newegg, Amazon. Highlights include Ryzen 7 7800X3D and Samsung 990 EVO Plus.', 
4, 'https://i0.wp.com/www.onthespotrepairs.com/wp-content/uploads/2024/05/Custom-Build-PC-Part-List-Late-2024.png?fit=1200%2C544&ssl=1', 
'Information on PC component promotions in May 2025.', 1),

(6, 'Step-by-Step RAM Installation Guide for Desktop PCs', 'NinhTT', NOW(),
'A safe and complete guide to install or upgrade RAM:
- Power off and ground yourself
- Open case
- Insert RAM properly
- Verify in BIOS and Windows', 
2, 'https://www.offtek.co.uk/images/pages/desktop-ram-installation.jpg', 
'Detailed guide on installing RAM for desktop PCs.', 2),

(7, 'SSD SATA vs NVMe: Which One Should You Choose', 'NinhTT', NOW(),
'Compare SATA and NVMe SSDs in terms of speed, compatibility, cost, and best use cases for gaming, content creation, and upgrading old PCs.', 
1, 'https://vstl.info/wp-content/uploads/2024/04/Purple-Minimalist-Develop-Your-Mobile-App-With-Us-Medium-Banner-1920-x-1080-px-1.jpg', 
'Comparing SATA and NVMe SSDs for faster computers.', 1),

(8, 'NVIDIA RTX 50 Series Graphics Card Launch Event', 'ThuongTN', NOW(),
'Highlights:
- RTX 5090, 5080, 5070 Ti launch
- Blackwell architecture
- DLSS 4 and Multi Frame Generation
- Laptop versions and stock issues', 
4, 'https://www.nvidia.com/content/dam/en-zz/Solutions/geforce/news/rtx-50-series-graphics-cards-gpu-laptop-announcements/geforce-rtx-5090-key-visual.jpg', 
'Highlights from the NVIDIA RTX 50 series graphics card launch event.', 1);

INSERT INTO OrderPreparements (UserID, OrderID, PrepareTime) VALUES 
(2, 1, CURDATE()),  
(2, 2, CURDATE()),  
(2, 3, CURDATE()),
(2, 4, CURDATE()),
(2, 5, CURDATE()),
(2, 7, CURDATE());

INSERT INTO Shipping (OrderID, ShipperID, ShippingStatus, ShipTime) VALUES 
(1, 4, 1, '2022-04-27'),
(2, 4, 1, CURDATE()),
(3, 4, 1, CURDATE()),
(4, 4, 1, CURDATE());



INSERT INTO Comments (Post_id, UserID, CommentText) VALUES 
(1, 3, 'Very informative post, thank you!');

-- Notification
INSERT INTO Notifications (UserID, SenderID, Title, Message, IsRead, CreatedAt) VALUES
(1, 1, 'Đơn hàng mới', 'Bạn có đơn hàng mới cần xử lý.', 0, '2025-06-03 08:00:00'),
(1, 1, 'Phản hồi khách hàng', 'Khách hàng vừa gửi feedback mới.', 0, '2025-06-03 09:00:00'),
(1, 1, 'Báo cáo doanh thu', 'Báo cáo doanh thu tháng 6 đã sẵn sàng.', 0, '2025-06-03 10:00:00'),
(1, 1, 'Kho hàng', 'Một số sản phẩm sắp hết hàng.', 0, '2025-06-03 11:00:00'),
(1, 1, 'Đăng ký mới', 'Có người dùng mới đăng ký tài khoản.', 0, '2025-06-03 12:00:00'),
(1, 1, 'Đơn hàng bị hủy', 'Một đơn hàng vừa bị hủy.', 0, '2025-06-03 13:00:00'),
(1, 1, 'Bảo trì hệ thống', 'Hệ thống sẽ bảo trì vào 23:00 hôm nay.', 0, '2025-06-03 14:00:00'),
(1, 1, 'Khuyến mãi mới', 'Chương trình khuyến mãi mới đã bắt đầu.', 0, '2025-06-03 15:00:00'),
(1, 1, 'Phản hồi đã trả lời', 'Bạn vừa trả lời một feedback của khách.', 0, '2025-06-03 16:00:00'),
(1, 1, 'Đơn hàng giao thành công', 'Một đơn hàng đã giao thành công.', 0, '2025-06-03 17:00:00');




