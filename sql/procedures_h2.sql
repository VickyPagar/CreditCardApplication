-- ============================================================
-- FirstBank Credit Card Application System
-- H2 Database Schema and Procedures (converted from Oracle)
-- ============================================================

-- Create schema to simulate Oracle package
CREATE SCHEMA IF NOT EXISTS PKG_CREDIT_CARD_APP;

-- ========================================================
-- SP_CREATE_CUSTOMER
-- Creates or finds existing customer by SSN
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_CREATE_CUSTOMER FOR 
"com.firstbank.creditcard.dao.H2Procedures.spCreateCustomer";

-- ========================================================
-- SP_SUBMIT_APPLICATION  
-- Submits new credit card application
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_SUBMIT_APPLICATION FOR
"com.firstbank.creditcard.dao.H2Procedures.spSubmitApplication";

-- ========================================================
-- SP_EVALUATE_APPLICATION
-- Auto-decision engine for application evaluation
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_EVALUATE_APPLICATION FOR
"com.firstbank.creditcard.dao.H2Procedures.spEvaluateApplication";

-- ========================================================
-- SP_GET_APPLICATION_STATUS
-- Retrieves application status
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_GET_APPLICATION_STATUS FOR
"com.firstbank.creditcard.dao.H2Procedures.spGetApplicationStatus";

-- ========================================================
-- SP_UPDATE_APPLICATION_STATUS
-- Updates application status
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_UPDATE_APPLICATION_STATUS FOR
"com.firstbank.creditcard.dao.H2Procedures.spUpdateApplicationStatus";

-- ========================================================
-- SP_ISSUE_CREDIT_CARD
-- Issues credit card for approved application
-- ========================================================
CREATE ALIAS IF NOT EXISTS PKG_CREDIT_CARD_APP.SP_ISSUE_CREDIT_CARD FOR
"com.firstbank.creditcard.dao.H2Procedures.spIssueCreditCard";

COMMIT;

-- Made with Bob
