-- === Departments ===
CREATE TABLE IF NOT EXISTS departments (
                                           id            BIGSERIAL PRIMARY KEY,
                                           code          VARCHAR(32)  NOT NULL UNIQUE,
    name          VARCHAR(128) NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
    );

-- === Employees ===
CREATE TABLE IF NOT EXISTS employees (
                                         id               BIGSERIAL PRIMARY KEY,
                                         external_code    VARCHAR(64),
    first_name       VARCHAR(80) NOT NULL,
    last_name        VARCHAR(80) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    hire_date        DATE NOT NULL,
    department_id    BIGINT NOT NULL REFERENCES departments(id) ON DELETE RESTRICT,
    salary_monthly   NUMERIC(12,2) NOT NULL DEFAULT 0,
    status           VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',  -- ACTIVE | INACTIVE
    customer_id      VARCHAR(64),
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE INDEX IF NOT EXISTS idx_employees_department ON employees(department_id);
CREATE INDEX IF NOT EXISTS idx_employees_status     ON employees(status);

-- === Leaves ===
CREATE TABLE IF NOT EXISTS leaves (
                                      id          BIGSERIAL PRIMARY KEY,
                                      employee_id BIGINT NOT NULL REFERENCES employees(id) ON DELETE CASCADE,
    type        VARCHAR(16) NOT NULL,  -- ANNUAL | SICK | UNPAID
    start_date  DATE NOT NULL,
    end_date    DATE NOT NULL,
    status      VARCHAR(16) NOT NULL DEFAULT 'REQUESTED', -- REQUESTED | APPROVED | REJECTED
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE INDEX IF NOT EXISTS idx_leaves_employee ON leaves(employee_id);
CREATE INDEX IF NOT EXISTS idx_leaves_status   ON leaves(status);
CREATE INDEX IF NOT EXISTS idx_leaves_range    ON leaves(start_date, end_date);
ALTER TABLE leaves ADD CONSTRAINT ck_leaves_dates CHECK (start_date <= end_date);

-- === Payroll ===
CREATE TABLE IF NOT EXISTS payroll_runs (
                                            id           BIGSERIAL PRIMARY KEY,
                                            period_year  INT  NOT NULL,
                                            period_month INT  NOT NULL CHECK (period_month BETWEEN 1 AND 12),
    run_date     DATE NOT NULL DEFAULT CURRENT_DATE,
    status       VARCHAR(16) NOT NULL DEFAULT 'OPEN',  -- OPEN | CLOSED
    CONSTRAINT uq_payroll_period UNIQUE (period_year, period_month)
    );

CREATE TABLE IF NOT EXISTS payroll_items (
                                             id              BIGSERIAL PRIMARY KEY,
                                             payroll_run_id  BIGINT NOT NULL REFERENCES payroll_runs(id) ON DELETE CASCADE,
    employee_id     BIGINT NOT NULL REFERENCES employees(id) ON DELETE RESTRICT,
    gross           NUMERIC(12,2) NOT NULL DEFAULT 0,
    deductions      NUMERIC(12,2) NOT NULL DEFAULT 0,
    net             NUMERIC(12,2) NOT NULL DEFAULT 0
    );

CREATE INDEX IF NOT EXISTS idx_payroll_items_run ON payroll_items(payroll_run_id);
CREATE INDEX IF NOT EXISTS idx_payroll_items_emp ON payroll_items(employee_id);

-- Seeds opcionales
INSERT INTO departments(code, name) VALUES ('ENG','Engineering')
    ON CONFLICT (code) DO NOTHING;
