-- Create enum type for project status
CREATE TYPE project_status AS ENUM ('in_progress', 'complete', 'suspended');

-- Create client table
CREATE TABLE client (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        address VARCHAR(255),
                        phone VARCHAR(15),
                        is_professional BOOLEAN NOT NULL
);

-- Create project table
CREATE TABLE project (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         profit_margin DOUBLE PRECISION,
                         total_cost DOUBLE PRECISION,
                         project_status project_status,
                         client_id INTEGER REFERENCES client(id)
);

-- Create component table (base table for material and labor)
CREATE TABLE component (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           component_type VARCHAR(50) NOT NULL,
                           vat_rate DOUBLE PRECISION,
                           project_id INTEGER REFERENCES project(id)
);

-- Create material table (inherits from component)
CREATE TABLE material (
                          unit_cost DOUBLE PRECISION,
                          quantity DOUBLE PRECISION,
                          transport_cost DOUBLE PRECISION,
                          quality_coefficient DOUBLE PRECISION
) INHERITS (component);

-- Create labor table (inherits from component)
CREATE TABLE labor (
                       hourly_rate DOUBLE PRECISION,
                       hours_worked DOUBLE PRECISION,
                       worker_productivity DOUBLE PRECISION
) INHERITS (component);

-- Create quote table
CREATE TABLE quote (
                       id SERIAL PRIMARY KEY,
                       estimated_amount DOUBLE PRECISION,
                       issue_date DATE,
                       validity_date DATE,
                       accepted BOOLEAN,
                       project_id INTEGER REFERENCES project(id)
);

-- Add indexes
CREATE INDEX idx_client_name ON client(name);
CREATE INDEX idx_project_name ON project(name);
CREATE INDEX idx_component_name ON component(name);
CREATE INDEX idx_component_project_id ON component(project_id);
CREATE INDEX idx_quote_project_id ON quote(project_id);

-- Add constraints
ALTER TABLE material ADD CONSTRAINT chk_material_type CHECK (component_type = 'Material');
ALTER TABLE labor ADD CONSTRAINT chk_labor_type CHECK (component_type = 'Labor');

-- Add cascade delete for related components and quotes when a project is deleted
ALTER TABLE component
    ADD CONSTRAINT fk_component_project
        FOREIGN KEY (project_id)
            REFERENCES project(id)
            ON DELETE CASCADE;

ALTER TABLE quote
    ADD CONSTRAINT fk_quote_project
        FOREIGN KEY (project_id)
            REFERENCES project(id)
            ON DELETE CASCADE;