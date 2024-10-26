CREATE TABLE `Customer` (
  `CustomerID` INT PRIMARY KEY AUTO_INCREMENT,
  `Name` VARCHAR(50),
  `Phone` VARCHAR(20),
  `Address` VARCHAR(255)
);

CREATE TABLE `Product` (
  `ProductID` INT PRIMARY KEY AUTO_INCREMENT,
  `ProductName` VARCHAR(100),
  `Price` DECIMAL(10,2),
  `Unit` INT,
  `SupplierID` INT,
  `StockQuantity` INT
);

CREATE TABLE `Order` (
  `OrderID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderDate` DATE,
  `CustomerID` INT,
  `ShipperID` INT,
  `TotalAmount` DECIMAL(10,2),
  `PaymentStatus` VARCHAR(20)
);

CREATE TABLE `OrderDetails` (
  `OrderDetailID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderID` INT,
  `ProductID` INT,
  `Quantity` INT
);

CREATE TABLE `Invoice` (
  `InvoiceID` INT PRIMARY KEY AUTO_INCREMENT,
  `CustomerID` INT,
  `OrderID` INT,
  `InvoiceDate` DATE,
  `DueDate` DATE,
  `TotalAmount` DECIMAL(10,2),
  `PaymentStatus` VARCHAR(20)
);

CREATE TABLE `Supplier` (
  `SupplierID` INT PRIMARY KEY AUTO_INCREMENT,
  `SupplierName` VARCHAR(100),
  `ProductCategory` VARCHAR(50),
  `ContactPerson` VARCHAR(100),
  `Address` VARCHAR(255),
  `Phone` VARCHAR(20),
  `PaymentDueDate` VARCHAR(100),
  `DeliveryTime` VARCHAR(50)
);

CREATE TABLE `SupplierOrder` (
  `SupplierOrderID` INT PRIMARY KEY AUTO_INCREMENT,
  `SupplierID` INT,
  `OrderDate` DATE,
  `TotalAmount` DECIMAL(10,2)
);

CREATE TABLE `SupplierOrderDetails` (
  `SupplierOrderDetailID` INT PRIMARY KEY AUTO_INCREMENT,
  `SupplierOrderID` INT,
  `ProductID` INT,
  `Quantity` INT
);

CREATE TABLE `Shipper` (
  `ShipperID` INT PRIMARY KEY AUTO_INCREMENT,
  `ShipperServiceID` INT,
  `ShipperName` VARCHAR(100),
  `ContactPhone` VARCHAR(20)
);

CREATE TABLE `ShipperService` (
  `ShipperServiceID` INT PRIMARY KEY AUTO_INCREMENT,
  `ShippingServiceName` VARCHAR(50),
  `ShippingFeePerKM` INT,
  `ShippingRange` VARCHAR(50)
);

CREATE TABLE `InventoryTransaction` (
  `TransactionID` INT PRIMARY KEY AUTO_INCREMENT,
  `ProductID` INT,
  `QuantityChange` INT,
  `TransactionType` VARCHAR(50),
  `TransactionDate` DATE,
  `Notes` VARCHAR(255)
);

CREATE TABLE `Payment` (
  `PaymentID` INT PRIMARY KEY AUTO_INCREMENT,
  `InvoiceID` INT,
  `PaymentDate` DATE,
  `PaymentAmount` DECIMAL(10,2),
  `PaymentMethod` VARCHAR(50),
  `PaymentStatus` VARCHAR(20)
);

CREATE TABLE `UserAccount` (
  `UserID` INT PRIMARY KEY AUTO_INCREMENT,
  `Username` VARCHAR(50) UNIQUE,
  `PasswordHash` VARCHAR(255),
  `RoleID` INT,
  `CreatedAt` DATE,
  `LastLogin` DATE
);

CREATE TABLE `UserRole` (
  `RoleID` INT PRIMARY KEY AUTO_INCREMENT,
  `RoleName` VARCHAR(50)
);

ALTER TABLE `Product` ADD FOREIGN KEY (`SupplierID`) REFERENCES `Supplier` (`SupplierID`);

ALTER TABLE `Order` ADD FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`);

ALTER TABLE `Order` ADD FOREIGN KEY (`ShipperID`) REFERENCES `Shipper` (`ShipperID`);

ALTER TABLE `OrderDetails` ADD FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`);

ALTER TABLE `OrderDetails` ADD FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`);

ALTER TABLE `Invoice` ADD FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`);

ALTER TABLE `Invoice` ADD FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`);

ALTER TABLE `SupplierOrder` ADD FOREIGN KEY (`SupplierID`) REFERENCES `Supplier` (`SupplierID`);

ALTER TABLE `SupplierOrderDetails` ADD FOREIGN KEY (`SupplierOrderID`) REFERENCES `SupplierOrder` (`SupplierOrderID`);

ALTER TABLE `SupplierOrderDetails` ADD FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`);

ALTER TABLE `Shipper` ADD FOREIGN KEY (`ShipperServiceID`) REFERENCES `ShipperService` (`ShipperServiceID`);

ALTER TABLE `InventoryTransaction` ADD FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`);

ALTER TABLE `Payment` ADD FOREIGN KEY (`InvoiceID`) REFERENCES `Invoice` (`InvoiceID`);

ALTER TABLE `UserAccount` ADD FOREIGN KEY (`RoleID`) REFERENCES `UserRole` (`RoleID`);


-- The dummy data for the tables was created by ChatGPT

-- Insert dummy data into Customer table
INSERT INTO Customer (Name, Phone, Address) VALUES
('John Doe', '1234567890', '123 Elm St, Springfield'),
('Jane Smith', '0987654321', '456 Oak St, Springfield'),
('Alice Johnson', '5551234567', '789 Pine St, Springfield');

-- Insert dummy data into Supplier table
INSERT INTO Supplier (SupplierName, ProductCategory, ContactPerson, Address, Phone, PaymentDueDate, DeliveryTime) VALUES
('ABC Supplies', 'Electronics', 'Mike Brown', '101 Maple St, Springfield', '1231231234', '2024-11-01', '2 days'),
('XYZ Traders', 'Groceries', 'Sara White', '202 Birch St, Springfield', '4321432143', '2024-11-05', '1 day');

-- Insert dummy data into Product table
INSERT INTO Product (ProductName, Price, Unit, SupplierID, StockQuantity) VALUES
('Laptop', 999.99, 1, 1, 50),
('Smartphone', 599.99, 1, 1, 100),
('Coffee', 19.99, 1, 2, 75),
('Noodles', 2.99, 1, 2, 200),
('Cereal', 29.99, 1, 2, 75),
('Desk Chair', 89.99, 1, 1, 30);

-- Insert dummy data into ShipperService table
INSERT INTO ShipperService (ShippingServiceName, ShippingFeePerKM, ShippingRange) VALUES
('Ahamove', 10, 'Local'),
('GHTK', 20, 'Regional');

-- Insert dummy data into Shipper table
INSERT INTO Shipper (ShipperServiceID, ShipperName, ContactPhone) VALUES
(1, 'Minh', '1112223333'),
(2, 'Thang', '4445556666');

-- Insert dummy data into Order table
INSERT INTO `Order` (OrderDate, CustomerID, ShipperID, TotalAmount, PaymentStatus) VALUES
('2024-10-01', 1, 1, 1599.98, 'Completed'),
('2024-10-02', 2, 2, 639.98, 'Pending');

-- Insert dummy data into OrderDetails table
INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 2);

-- Insert dummy data into Invoice table
INSERT INTO Invoice (CustomerID, OrderID, InvoiceDate, DueDate, TotalAmount, PaymentStatus) VALUES
(1, 1, '2024-10-01', '2024-10-15', 1599.98, 'Completed'),
(2, 2, '2024-10-02', '2024-10-16', 639.98, 'Pending');

-- Insert dummy data into SupplierOrder table
INSERT INTO SupplierOrder (SupplierID, OrderDate, TotalAmount) VALUES
(1, '2024-10-05', 5000.00),
(2, '2024-10-10', 1500.00);

-- Insert dummy data into SupplierOrderDetails table
INSERT INTO SupplierOrderDetails (SupplierOrderID, ProductID, Quantity) VALUES
(1, 1, 10),
(1, 2, 20),
(2, 3, 15);

-- Insert dummy data into InventoryTransaction table
INSERT INTO InventoryTransaction (ProductID, QuantityChange, TransactionType, TransactionDate, Notes) VALUES
(1, 10, 'Restock', '2024-10-05', 'Restocked 10 Laptops'),
(2, -5, 'Sale', '2024-10-02', 'Sold 5 Smartphones');

-- Insert dummy data into Payment table
INSERT INTO Payment (InvoiceID, PaymentDate, PaymentAmount, PaymentMethod, PaymentStatus) VALUES
(1, '2024-10-10', 1599.98, 'Credit Card', 'Completed'),
(2, '2024-10-12', 639.98, 'Cash', 'Pending');

-- Insert dummy data into UserRole table
INSERT INTO UserRole (RoleName) VALUES
('Customer'),
('Employee'),
('Manager');

-- Insert dummy data into UserAccount table
INSERT INTO UserAccount (Username, PasswordHash, RoleID, CreatedAt, LastLogin) VALUES
('jdoe', 'password', 1, '2024-09-01', '2024-10-10'),
('jsmith', 'password', 2, '2024-09-05', '2024-10-11'),
('admin', 'password', 3, '2024-09-10', '2024-10-12');