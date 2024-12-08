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
  `TotalAmount` DECIMAL(10,2)
);

CREATE TABLE `OrderDetails` (
  `OrderDetailID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderID` INT,
  `ProductID` INT,
  `Quantity` INT
);

CREATE TABLE `Supplier` (
  `SupplierID` INT PRIMARY KEY AUTO_INCREMENT,
  `SupplierName` VARCHAR(100),
  `ProductCategory` VARCHAR(50),
  `ContactPerson` VARCHAR(100),
  `Address` VARCHAR(255),
  `Phone` VARCHAR(20)
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

ALTER TABLE `SupplierOrder` ADD FOREIGN KEY (`SupplierID`) REFERENCES `Supplier` (`SupplierID`);

ALTER TABLE `SupplierOrderDetails` ADD FOREIGN KEY (`SupplierOrderID`) REFERENCES `SupplierOrder` (`SupplierOrderID`);

ALTER TABLE `SupplierOrderDetails` ADD FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`);

ALTER TABLE `Shipper` ADD FOREIGN KEY (`ShipperServiceID`) REFERENCES `ShipperService` (`ShipperServiceID`);

ALTER TABLE `UserAccount` ADD FOREIGN KEY (`RoleID`) REFERENCES `UserRole` (`RoleID`);

-- Dummy data was created by ChatGPT
-- Insert dummy data into Customer table
INSERT INTO Customer (Name, Phone, Address) VALUES
('John Doe', '1234567890', '123 Elm St, Springfield'),
('Jane Smith', '0987654321', '456 Oak St, Springfield'),
('Alice Johnson', '5551234567', '789 Pine St, Springfield');

-- Insert dummy data into Supplier table
INSERT INTO Supplier (SupplierName, ProductCategory, ContactPerson, Address, Phone) VALUES
('ABC Supplies', 'Electronics', 'Mike Brown', '101 Maple St, Springfield', '1231231234'),
('XYZ Traders', 'Groceries', 'Sara White', '202 Birch St, Springfield', '4321432143');

-- Insert dummy data into ShipperService table
INSERT INTO ShipperService (ShippingServiceName, ShippingFeePerKM, ShippingRange) VALUES
('Express Shipping', 10, 'Local'),
('Standard Shipping', 5, 'National');

-- Insert dummy data into Shipper table
INSERT INTO Shipper (ShipperServiceID, ShipperName, ContactPhone) VALUES
(NULL, NULL, NULL),
(1, 'Fast Delivery', '1112223333'),
(2, 'Quick Ship', '4445556666');

-- Insert dummy data into Product table
INSERT INTO Product (ProductName, Price, Unit, SupplierID, StockQuantity) VALUES
('Tablet', 299.99, 1, 1, 120),
('Headphones', 49.99, 1, 1, 200),
('Wireless Mouse', 19.99, 1, 1, 150),
('Bluetooth Speaker', 79.99, 1, 1, 80),
('Electric Kettle', 29.99, 1, 2, 90),
('Blender', 39.99, 1, 2, 60),
('Air Fryer', 99.99, 1, 2, 70),
('Vacuum Cleaner', 149.99, 1, 2, 50),
('Monitor', 129.99, 1, 1, 100),
('USB-C Cable', 9.99, 1, 1, 500);


-- Insert dummy data into Order table
INSERT INTO `Order` (OrderDate, CustomerID, ShipperID, TotalAmount) VALUES
('2024-10-01', 1, 1, 1599.98),
('2024-10-02', 2, 2, 639.98);

-- Insert dummy data into OrderDetails table
INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 2);

-- Insert dummy data into SupplierOrder table
INSERT INTO SupplierOrder (SupplierID, OrderDate, TotalAmount) VALUES
(1, '2024-10-05', 5000.00),
(2, '2024-10-10', 1500.00);

-- Insert dummy data into SupplierOrderDetails table
INSERT INTO SupplierOrderDetails (SupplierOrderID, ProductID, Quantity) VALUES
(1, 1, 10),
(1, 2, 20),
(2, 3, 15);

-- Insert dummy data into UserRole table
INSERT INTO UserRole (RoleName) VALUES
('Employee'),
('Manager'),
('Customer');

-- Insert dummy data into UserAccount table
INSERT INTO UserAccount (Username, PasswordHash, RoleID, CreatedAt, LastLogin) VALUES
('jsmith', 'password', 1, '2024-09-05', '2024-10-11'),
('admin', 'password', 2, '2024-09-10', '2024-10-12');


