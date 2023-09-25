# Store Management Backend Documentation

## Overview

The Store Management backend is a Java-based application built on the Spring Boot framework. It is designed to manage various aspects of a retail store, including cashiers, goods, receipts, and shops.

## Table of Contents

1. [Controllers](#controllers)
2. [Models](#models)
3. [Repositories](#repositories)
4. [Services](#services)

---

## Controllers

### CashierController

Responsible for handling cashier-related operations.

#### Methods:

- `getAllCashiers()`: Fetches all cashiers.
- `getCashierById()`: Fetches a cashier by ID.
- `addCashier()`: Adds a new cashier.
- `updateCashier()`: Updates an existing cashier.
- `deleteCashier()`: Deletes a cashier.

### CashierWorkerController

Manages cashier workers.

#### Methods:

- `getAllCashierWorkers()`: Fetches all cashier workers.
- `getCashierWorkerById()`: Fetches a cashier worker by ID.
- `addCashierWorker()`: Adds a new cashier worker.
- `updateCashierWorker()`: Updates an existing cashier worker.
- `deleteCashierWorker()`: Deletes a cashier worker.

### GoodsController

Handles goods-related operations.

#### Methods:

- `getAllGoods()`: Fetches all goods.
- `getGoodsById()`: Fetches goods by ID.
- `addGoods()`: Adds new goods.
- `updateGoods()`: Updates existing goods.
- `deleteGoods()`: Deletes goods.

### ReceiptController

Manages receipts.

#### Methods:

- `getAllReceipts()`: Fetches all receipts.
- `getReceiptById()`: Fetches a receipt by ID.
- `addReceipt()`: Adds a new receipt.
- `updateReceipt()`: Updates an existing receipt.
- `deleteReceipt()`: Deletes a receipt.

### ShopController

Handles shop-related operations.

#### Methods:

- `getAllShops()`: Fetches all shops.
- `getShopById()`: Fetches a shop by ID.
- `addShop()`: Adds a new shop.
- `updateShop()`: Updates an existing shop.
- `deleteShop()`: Deletes a shop.

---

## Models

- **Cashier**: Represents a cashier in the store.
- **CashierWorker**: Represents a cashier worker.
- **Category**: Represents a category of goods.
- **Goods**: Represents goods in the store.
- **Receipt**: Represents a receipt.
- **Shop**: Represents a shop.

---

## Repositories

- **CashierRepository**: Repository for `Cashier` model.
- **CashierWorkerRepository**: Repository for `CashierWorker` model.
- **GoodsRepository**: Repository for `Goods` model.
- **ReceiptRepository**: Repository for `Receipt` model.
- **ShopRepository**: Repository for `Shop` model.

---

## Services

### CashierService

#### Methods:

- `getAllCashiers()`: Fetches all cashiers.
- `getCashierById()`: Fetches a cashier by ID.
- `addCashier()`: Adds a new cashier.
- `updateCashier()`: Updates an existing cashier.
- `deleteCashier()`: Deletes a cashier.

### CashierWorkerService

#### Methods:

- `getAllCashierWorkers()`: Fetches all cashier workers.
- `getCashierWorkerById()`: Fetches a cashier worker by ID.
- `addCashierWorker()`: Adds a new cashier worker.
- `updateCashierWorker()`: Updates an existing cashier worker.
- `deleteCashierWorker()`: Deletes a cashier worker.

## Test Coverage

The project includes a comprehensive set of tests to ensure the functionality and reliability of each component. Below is an overview of the test classes and what they cover:

### Controller Tests

1. **CashierControllerTest**: Validates the functionalities related to the Cashier Controller.
2. **CashierWorkerControllerTest**: Ensures the correct behavior of the Cashier Worker Controller.
3. **GoodsControllerTest**: Tests the functionalities of the Goods Controller.
4. **ReceiptControllerTest**: Validates the Receipt Controller's operations.
5. **ShopControllerTest**: Tests the Shop Controller's functionalities.

### Service Tests

1. **CashierServiceTest**: Validates the functionalities related to the Cashier Service.
2. **CashierWorkerServiceTest**: Ensures the correct behavior of the Cashier Worker Service.
3. **GoodsServiceTest**: Tests the functionalities of the Goods Service.
4. **PriceCalculationServiceTest**: Validates the Price Calculation Service's operations.
5. **ReceiptServiceTest**: Tests the Receipt Service's functionalities.
6. **ShopServiceTest**: Validates the functionalities related to the Shop Service.
