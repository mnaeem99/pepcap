
CREATE SCHEMA admin_panel;

CREATE TABLE admin_panel.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_email_confirmed BOOLEAN,
    phone_number VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admin_panel.categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admin_panel.products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id INT REFERENCES admin_panel.categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE SCHEMA e_commerce;

CREATE TABLE e_commerce.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_email_confirmed BOOLEAN,
    phone_number VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE e_commerce.categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE e_commerce.products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id INT REFERENCES e_commerce.categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE e_commerce.orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES e_commerce.users(id),
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending', -- 'Pending', 'Shipped', 'Delivered'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE e_commerce.order_items (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES e_commerce.orders(id),
    product_id INT REFERENCES e_commerce.products(id), -- Linking to Admin Panel Products
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE SCHEMA inventory_management;

CREATE TABLE inventory_management.suppliers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_info TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE inventory_management.categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory_management.products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category_id INT REFERENCES inventory_management.categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE inventory_management.inventory_transactions (
    id SERIAL PRIMARY KEY,
    product_id INT REFERENCES inventory_management.products(id), -- Linking to Admin Panel Products
    quantity INT NOT NULL,
    transaction_type VARCHAR(50) NOT NULL, -- 'IN', 'OUT'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA task_management;

CREATE TABLE task_management.projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'Active', -- 'Active', 'Completed', 'On-Hold'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task_management.tasks (
    id SERIAL PRIMARY KEY,
    project_id INT REFERENCES task_management.projects(id),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    assignee_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    status VARCHAR(50) DEFAULT 'To Do', -- 'To Do', 'In Progress', 'Done'
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA employee_management;

CREATE TABLE employee_management.employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    position VARCHAR(100),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee_management.attendance (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employee_management.employees(id),
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL -- 'Present', 'Absent', 'Leave'
);

CREATE SCHEMA feedback_system;

CREATE TABLE feedback_system.feedback (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    message TEXT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA ticketing_system;

CREATE TABLE ticketing_system.tickets (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    subject VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'Open', -- 'Open', 'In Progress', 'Resolved', 'Closed'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA analytics_dashboard;

CREATE TABLE analytics_dashboard.analytics (
    id SERIAL PRIMARY KEY,
    metric_name VARCHAR(100) NOT NULL,
    metric_value NUMERIC NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA time_tracking;

CREATE TABLE time_tracking.timesheets (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    date DATE NOT NULL,
    hours_worked DECIMAL(5, 2) NOT NULL,
    task_id INT REFERENCES task_management.tasks(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SCHEMA event_management;

CREATE TABLE event_management.events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    location TEXT,
    organizer_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE event_management.event_registrations (
    id SERIAL PRIMARY KEY,
    event_id INT REFERENCES event_management.events(id),
    user_id INT REFERENCES admin_panel.users(id), -- Linking to Admin Panel Users
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
