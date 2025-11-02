# Inventory Management System - Data Model

This document outlines the data model for an Inventory Management System, detailing the entities involved and their attributes.

## Define Attributes (Details for Each Entity)

Entity Attributes (Columns in Table)

| Entity      | Attributes (Columns)                                                       |
| ----------- | -------------------------------------------------------------------------- |
| ProductType | id, name, description                                                      |
| Product     | product_id, name, category, unit_price, unit (kg / ton / bag), description |
| Vendor      | vendor_id, name, contact_no, address                                       |
| Customer    | customer_id, name, contact_no, address                                     |
| Purchase    | purchase_id, vendor_id, purchase_date, total_cost                          |
| Sale        | sale_id, product_id, customer_id, quantity, total_amount                   |
| Inventory   | product_id, available_qty, reorder_level                                   |
