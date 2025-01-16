# Pepcap

This outlines the schemas and tables designed for various applications. Each application has a dedicated schema with relevant tables to ensure modularity and maintainability.

---

## **1. Admin Panel Schema**

### **Schema: `admin_panel`**

#### **Tables:**

1. **`users`**
    - Stores user details for the admin panel.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing user ID.
        - `username`: Unique username.
        - `password`: Encrypted password.
        - `email`: Unique email address.
        - `role`: Role of the user.
        - `first_name`, `last_name`: Personal details.
        - `is_active`: Account activation status.
        - `is_email_confirmed`: Email confirmation status.
        - `phone_number`: Optional contact number.
        - `created_at`, `updated_at`: Timestamps for creation and updates.

2. **`categories`**
    - Defines product categories.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing category ID.
        - `name`: Unique category name.
        - `description`: Description of the category.
        - `created_at`, `updated_at`: Timestamps for creation and updates.

3. **`products`**
    - Stores product details.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing product ID.
        - `name`, `description`, `price`, `stock`: Product details.
        - `category_id`: References `admin_panel.categories(id)`.
        - `created_at`, `updated_at`: Timestamps for creation and updates.

---

## **2. E-Commerce Schema**

### **Schema: `e_commerce`**

#### **Tables:**

1. **`users`**
    - Similar to `admin_panel.users`, stores e-commerce-specific user details.

2. **`orders`**
    - Stores order details.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing order ID.
        - `user_id`: References `e_commerce.users(id)`.
        - `total_amount`: Total order amount.
        - `status`: Order status (e.g., 'Pending', 'Shipped').
        - `created_at`: Timestamp for creation.

3. **`order_items`**
    - Tracks items in an order.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing order item ID.
        - `order_id`: References `e_commerce.orders(id)`.
        - `product_id`: References `admin_panel.products(id)`.
        - `quantity`, `price`: Product details in the order.

---

## **3. Inventory Management Schema**

### **Schema: `inventory_management`**

#### **Tables:**

1. **`suppliers`**
    - Stores supplier details.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing supplier ID.
        - `name`: Supplier name.
        - `contact_info`: Contact details.
        - `created_at`: Timestamp for creation.

2. **`inventory_transactions`**
    - Tracks inventory movements.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing transaction ID.
        - `product_id`: References `admin_panel.products(id)`.
        - `quantity`: Quantity involved in the transaction.
        - `transaction_type`: Type of transaction ('IN', 'OUT').
        - `created_at`: Timestamp for creation.

---

## **4. Task Management Schema**

### **Schema: `task_management`**

#### **Tables:**

1. **`projects`**
    - Stores project details.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing project ID.
        - `name`, `description`, `start_date`, `end_date`, `status`: Project details.
        - `created_at`: Timestamp for creation.

2. **`tasks`**
    - Tracks tasks under projects.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing task ID.
        - `project_id`: References `task_management.projects(id)`.
        - `name`, `description`: Task details.
        - `assignee_id`: References `admin_panel.users(id)`.
        - `status`, `due_date`: Task status and deadline.
        - `created_at`: Timestamp for creation.

---

## **5. Employee Management Schema**

### **Schema: `employee_management`**

#### **Tables:**

1. **`employees`**
    - Stores employee details.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing employee ID.
        - `name`, `email`: Personal details.
        - `position`, `department`: Work details.
        - `created_at`: Timestamp for creation.

2. **`attendance`**
    - Tracks employee attendance.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing attendance ID.
        - `employee_id`: References `employee_management.employees(id)`.
        - `date`, `status`: Attendance details.

---

## **6. Feedback System Schema**

### **Schema: `feedback_system`**

#### **Tables:**

1. **`feedback`**
    - Tracks user feedback.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing feedback ID.
        - `user_id`: References `admin_panel.users(id)`.
        - `message`, `rating`: Feedback details.
        - `created_at`: Timestamp for creation.

---

## **7. Ticketing System Schema**

### **Schema: `ticketing_system`**

#### **Tables:**

1. **`tickets`**
    - Tracks support tickets.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing ticket ID.
        - `user_id`: References `admin_panel.users(id)`.
        - `subject`, `description`: Ticket details.
        - `status`: Ticket status ('Open', 'Resolved').
        - `created_at`: Timestamp for creation.

---

## **8. Analytics Dashboard Schema**

### **Schema: `analytics_dashboard`**

#### **Tables:**

1. **`analytics`**
    - Tracks analytics data.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing analytics ID.
        - `metric_name`, `metric_value`: Metric details.
        - `timestamp`: Time of data entry.

---

## **9. Time Tracking Schema**

### **Schema: `time_tracking`**

#### **Tables:**

1. **`timesheets`**
    - Tracks time spent on tasks.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing timesheet ID.
        - `user_id`: References `admin_panel.users(id)`.
        - `date`, `hours_worked`: Work details.
        - `task_id`: References `task_management.tasks(id)`.
        - `created_at`: Timestamp for creation.

---

## **10. Event Management Schema**

### **Schema: `event_management`**

#### **Tables:**

1. **`events`**
    - Tracks events.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing event ID.
        - `name`, `date`, `location`: Event details.
        - `organizer_id`: References `admin_panel.users(id)`.
        - `created_at`: Timestamp for creation.

2. **`event_registrations`**
    - Tracks event registrations.
    - **Columns:**
        - `id` (Primary Key): Auto-incrementing registration ID.
        - `event_id`: References `event_management.events(id)`.
        - `user_id`: References `admin_panel.users(id)`.
        - `registration_date`: Timestamp for registration.

---

### **Inter-Schema Relationships:**
- `admin_panel.users` is referenced in multiple schemas for consistency in user-related operations.
- `admin_panel.products` is linked to e-commerce and inventory management schemas for unified product data.

This modular schema design ensures scalability, reusability, and efficient data management across applications.

