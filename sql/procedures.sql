-- ============================================================
-- FirstBank Credit Card Application System
-- Oracle PL/SQL Stored Procedures Package
-- Created: 2001-06-15  Author: DBA Team - FirstBank IT Dept
-- ============================================================

-- Create Package Specification
CREATE OR REPLACE PACKAGE PKG_CREDIT_CARD_APP AS

    -- Create or find existing customer
    PROCEDURE SP_CREATE_CUSTOMER(
        p_first_name      IN  VARCHAR2,
        p_middle_name     IN  VARCHAR2,
        p_last_name       IN  VARCHAR2,
        p_date_of_birth   IN  DATE,
        p_gender          IN  CHAR,
        p_ssn             IN  VARCHAR2,
        p_email           IN  VARCHAR2,
        p_phone_home      IN  VARCHAR2,
        p_phone_work      IN  VARCHAR2,
        p_address_line1   IN  VARCHAR2,
        p_address_line2   IN  VARCHAR2,
        p_city            IN  VARCHAR2,
        p_state_code      IN  CHAR,
        p_zip_code        IN  VARCHAR2,
        p_created_by      IN  VARCHAR2,
        p_customer_id     OUT NUMBER,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    );

    -- Submit new application
    PROCEDURE SP_SUBMIT_APPLICATION(
        p_customer_id     IN  NUMBER,
        p_card_type_id    IN  NUMBER,
        p_requested_limit IN  NUMBER,
        p_ip_address      IN  VARCHAR2,
        p_annual_income   IN  NUMBER,
        p_other_income    IN  NUMBER,
        p_monthly_rent    IN  NUMBER,
        p_total_debt      IN  NUMBER,
        p_home_ownership  IN  VARCHAR2,
        p_bankruptcy_flag IN  CHAR,
        p_employment_type IN  VARCHAR2,
        p_employer_name   IN  VARCHAR2,
        p_employer_phone  IN  VARCHAR2,
        p_job_title       IN  VARCHAR2,
        p_years_employed  IN  NUMBER,
        p_created_by      IN  VARCHAR2,
        p_application_id  OUT NUMBER,
        p_return_code     OUT NUMBER
    );

    -- Evaluate application (auto-decision engine)
    PROCEDURE SP_EVALUATE_APPLICATION(
        p_application_id  IN  NUMBER,
        p_credit_score    IN  NUMBER,
        p_analyst_id      IN  VARCHAR2,
        p_decision        OUT VARCHAR2,
        p_approved_limit  OUT NUMBER,
        p_rejection_reason OUT VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    );

    -- Get application status
    PROCEDURE SP_GET_APPLICATION_STATUS(
        p_application_id  IN  NUMBER,
        p_status_code     OUT VARCHAR2,
        p_status_desc     OUT VARCHAR2,
        p_approved_limit  OUT NUMBER,
        p_rejection_reason OUT VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    );

    -- Update application status
    PROCEDURE SP_UPDATE_APPLICATION_STATUS(
        p_application_id  IN  NUMBER,
        p_new_status      IN  VARCHAR2,
        p_approved_limit  IN  NUMBER,
        p_rejection_reason IN VARCHAR2,
        p_analyst_id      IN  VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    );

    -- Issue credit card
    PROCEDURE SP_ISSUE_CREDIT_CARD(
        p_application_id  IN  NUMBER,
        p_card_number     IN  VARCHAR2,
        p_cvv_hash        IN  VARCHAR2,
        p_card_id         OUT NUMBER,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    );

END PKG_CREDIT_CARD_APP;
/

-- Create Package Body
CREATE OR REPLACE PACKAGE BODY PKG_CREDIT_CARD_APP AS

    -- ========================================================
    -- SP_CREATE_CUSTOMER
    -- ========================================================
    PROCEDURE SP_CREATE_CUSTOMER(
        p_first_name      IN  VARCHAR2,
        p_middle_name     IN  VARCHAR2,
        p_last_name       IN  VARCHAR2,
        p_date_of_birth   IN  DATE,
        p_gender          IN  CHAR,
        p_ssn             IN  VARCHAR2,
        p_email           IN  VARCHAR2,
        p_phone_home      IN  VARCHAR2,
        p_phone_work      IN  VARCHAR2,
        p_address_line1   IN  VARCHAR2,
        p_address_line2   IN  VARCHAR2,
        p_city            IN  VARCHAR2,
        p_state_code      IN  CHAR,
        p_zip_code        IN  VARCHAR2,
        p_created_by      IN  VARCHAR2,
        p_customer_id     OUT NUMBER,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    ) AS
        v_existing_id NUMBER;
    BEGIN
        -- Check if customer exists by SSN
        BEGIN
            SELECT CUSTOMER_ID INTO v_existing_id
            FROM CC_CUSTOMER
            WHERE SSN = p_ssn;
            
            p_customer_id := v_existing_id;
            p_return_code := 0;
            p_return_message := 'Existing customer found';
            RETURN;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                NULL; -- Continue to create new customer
        END;

        -- Create new customer
        SELECT SEQ_CUSTOMER_ID.NEXTVAL INTO p_customer_id FROM DUAL;

        INSERT INTO CC_CUSTOMER (
            CUSTOMER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME,
            DATE_OF_BIRTH, GENDER, SSN, EMAIL_ADDRESS,
            PHONE_HOME, PHONE_WORK, ADDRESS_LINE1, ADDRESS_LINE2,
            CITY, STATE_CODE, ZIP_CODE, CREATED_BY
        ) VALUES (
            p_customer_id, p_first_name, p_middle_name, p_last_name,
            p_date_of_birth, p_gender, p_ssn, p_email,
            p_phone_home, p_phone_work, p_address_line1, p_address_line2,
            p_city, p_state_code, p_zip_code, p_created_by
        );

        p_return_code := 0;
        p_return_message := 'Customer created successfully';
        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            p_return_code := -1;
            p_return_message := 'Error: ' || SQLERRM;
    END SP_CREATE_CUSTOMER;

    -- ========================================================
    -- SP_SUBMIT_APPLICATION
    -- ========================================================
    PROCEDURE SP_SUBMIT_APPLICATION(
        p_customer_id     IN  NUMBER,
        p_card_type_id    IN  NUMBER,
        p_requested_limit IN  NUMBER,
        p_ip_address      IN  VARCHAR2,
        p_annual_income   IN  NUMBER,
        p_other_income    IN  NUMBER,
        p_monthly_rent    IN  NUMBER,
        p_total_debt      IN  NUMBER,
        p_home_ownership  IN  VARCHAR2,
        p_bankruptcy_flag IN  CHAR,
        p_employment_type IN  VARCHAR2,
        p_employer_name   IN  VARCHAR2,
        p_employer_phone  IN  VARCHAR2,
        p_job_title       IN  VARCHAR2,
        p_years_employed  IN  NUMBER,
        p_created_by      IN  VARCHAR2,
        p_application_id  OUT NUMBER,
        p_return_code     OUT NUMBER
    ) AS
        v_financial_id NUMBER;
        v_employment_id NUMBER;
    BEGIN
        -- Generate application ID
        SELECT SEQ_APPLICATION_ID.NEXTVAL INTO p_application_id FROM DUAL;

        -- Insert application
        INSERT INTO CC_APPLICATION (
            APPLICATION_ID, CUSTOMER_ID, CARD_TYPE_ID,
            REQUESTED_LIMIT, IP_ADDRESS, STATUS_CODE, CREATED_BY
        ) VALUES (
            p_application_id, p_customer_id, p_card_type_id,
            p_requested_limit, p_ip_address, 'PENDING', p_created_by
        );

        -- Insert financial info
        SELECT SEQ_APPLICATION_ID.NEXTVAL INTO v_financial_id FROM DUAL;
        INSERT INTO CC_APPLICANT_FINANCIAL (
            FINANCIAL_ID, APPLICATION_ID, ANNUAL_INCOME, OTHER_INCOME,
            MONTHLY_RENT, TOTAL_DEBT, HOME_OWNERSHIP, BANKRUPTCY_FLAG
        ) VALUES (
            v_financial_id, p_application_id, p_annual_income, p_other_income,
            p_monthly_rent, p_total_debt, p_home_ownership, p_bankruptcy_flag
        );

        -- Insert employment info
        SELECT SEQ_APPLICATION_ID.NEXTVAL INTO v_employment_id FROM DUAL;
        INSERT INTO CC_APPLICANT_EMPLOYMENT (
            EMPLOYMENT_ID, APPLICATION_ID, EMPLOYMENT_TYPE,
            EMPLOYER_NAME, EMPLOYER_PHONE, JOB_TITLE, YEARS_EMPLOYED
        ) VALUES (
            v_employment_id, p_application_id, p_employment_type,
            p_employer_name, p_employer_phone, p_job_title, p_years_employed
        );

        -- Audit log
        INSERT INTO CC_APPLICATION_AUDIT (
            AUDIT_ID, APPLICATION_ID, ACTION_CODE, NEW_STATUS,
            ACTION_BY, ACTION_NOTES, IP_ADDRESS
        ) VALUES (
            SEQ_AUDIT_ID.NEXTVAL, p_application_id, 'SUBMIT', 'PENDING',
            p_created_by, 'Application submitted', p_ip_address
        );

        p_return_code := 0;
        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            p_return_code := -1;
    END SP_SUBMIT_APPLICATION;

    -- ========================================================
    -- SP_EVALUATE_APPLICATION
    -- ========================================================
    PROCEDURE SP_EVALUATE_APPLICATION(
        p_application_id  IN  NUMBER,
        p_credit_score    IN  NUMBER,
        p_analyst_id      IN  VARCHAR2,
        p_decision        OUT VARCHAR2,
        p_approved_limit  OUT NUMBER,
        p_rejection_reason OUT VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    ) AS
        v_annual_income NUMBER;
        v_total_debt NUMBER;
        v_card_min_income NUMBER;
        v_card_max_limit NUMBER;
        v_debt_ratio NUMBER;
    BEGIN
        -- Get financial info
        SELECT f.ANNUAL_INCOME, f.TOTAL_DEBT, c.MIN_INCOME_REQ, c.CREDIT_LIMIT_MAX
        INTO v_annual_income, v_total_debt, v_card_min_income, v_card_max_limit
        FROM CC_APPLICATION a
        JOIN CC_APPLICANT_FINANCIAL f ON a.APPLICATION_ID = f.APPLICATION_ID
        JOIN CC_CARD_TYPE c ON a.CARD_TYPE_ID = c.CARD_TYPE_ID
        WHERE a.APPLICATION_ID = p_application_id;

        -- Calculate debt-to-income ratio
        v_debt_ratio := (v_total_debt / v_annual_income) * 100;

        -- Decision logic
        IF p_credit_score < 620 THEN
            p_decision := 'REJECTED';
            p_rejection_reason := 'LOW_SCORE';
            p_approved_limit := 0;
        ELSIF v_annual_income < v_card_min_income THEN
            p_decision := 'REJECTED';
            p_rejection_reason := 'LOW_INCOME';
            p_approved_limit := 0;
        ELSIF v_debt_ratio > 43 THEN
            p_decision := 'REJECTED';
            p_rejection_reason := 'HIGH_DEBT';
            p_approved_limit := 0;
        ELSIF p_credit_score >= 750 THEN
            p_decision := 'APPROVED';
            p_approved_limit := LEAST(v_card_max_limit, v_annual_income * 0.3);
        ELSIF p_credit_score >= 620 THEN
            p_decision := 'APPROVED';
            p_approved_limit := LEAST(v_card_max_limit * 0.5, v_annual_income * 0.2);
        ELSE
            p_decision := 'REVIEW';
            p_approved_limit := 0;
        END IF;

        -- Update application
        UPDATE CC_APPLICATION
        SET STATUS_CODE = p_decision,
            CREDIT_SCORE = p_credit_score,
            APPROVED_LIMIT = p_approved_limit,
            REJECTION_REASON = p_rejection_reason,
            ANALYST_ID = p_analyst_id,
            REVIEW_DATE = SYSDATE
        WHERE APPLICATION_ID = p_application_id;

        p_return_code := 0;
        p_return_message := 'Evaluation completed';
        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            p_return_code := -1;
            p_return_message := 'Error: ' || SQLERRM;
    END SP_EVALUATE_APPLICATION;

    -- ========================================================
    -- SP_GET_APPLICATION_STATUS
    -- ========================================================
    PROCEDURE SP_GET_APPLICATION_STATUS(
        p_application_id  IN  NUMBER,
        p_status_code     OUT VARCHAR2,
        p_status_desc     OUT VARCHAR2,
        p_approved_limit  OUT NUMBER,
        p_rejection_reason OUT VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    ) AS
    BEGIN
        SELECT a.STATUS_CODE, s.STATUS_DESC, a.APPROVED_LIMIT,
               r.REASON_DESC
        INTO p_status_code, p_status_desc, p_approved_limit, p_rejection_reason
        FROM CC_APPLICATION a
        JOIN CC_STATUS_CODE s ON a.STATUS_CODE = s.STATUS_CODE
        LEFT JOIN CC_REJECTION_REASON r ON a.REJECTION_REASON = r.REASON_CODE
        WHERE a.APPLICATION_ID = p_application_id;

        p_return_code := 0;
        p_return_message := 'Status retrieved';

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_return_code := -1;
            p_return_message := 'Application not found';
        WHEN OTHERS THEN
            p_return_code := -1;
            p_return_message := 'Error: ' || SQLERRM;
    END SP_GET_APPLICATION_STATUS;

    -- ========================================================
    -- SP_UPDATE_APPLICATION_STATUS
    -- ========================================================
    PROCEDURE SP_UPDATE_APPLICATION_STATUS(
        p_application_id  IN  NUMBER,
        p_new_status      IN  VARCHAR2,
        p_approved_limit  IN  NUMBER,
        p_rejection_reason IN VARCHAR2,
        p_analyst_id      IN  VARCHAR2,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    ) AS
        v_old_status VARCHAR2(10);
    BEGIN
        SELECT STATUS_CODE INTO v_old_status
        FROM CC_APPLICATION
        WHERE APPLICATION_ID = p_application_id;

        UPDATE CC_APPLICATION
        SET STATUS_CODE = p_new_status,
            APPROVED_LIMIT = p_approved_limit,
            REJECTION_REASON = p_rejection_reason,
            ANALYST_ID = p_analyst_id,
            REVIEW_DATE = SYSDATE
        WHERE APPLICATION_ID = p_application_id;

        INSERT INTO CC_APPLICATION_AUDIT (
            AUDIT_ID, APPLICATION_ID, ACTION_CODE,
            OLD_STATUS, NEW_STATUS, ACTION_BY
        ) VALUES (
            SEQ_AUDIT_ID.NEXTVAL, p_application_id, 'STATUS_UPDATE',
            v_old_status, p_new_status, p_analyst_id
        );

        p_return_code := 0;
        p_return_message := 'Status updated';
        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            p_return_code := -1;
            p_return_message := 'Error: ' || SQLERRM;
    END SP_UPDATE_APPLICATION_STATUS;

    -- ========================================================
    -- SP_ISSUE_CREDIT_CARD
    -- ========================================================
    PROCEDURE SP_ISSUE_CREDIT_CARD(
        p_application_id  IN  NUMBER,
        p_card_number     IN  VARCHAR2,
        p_cvv_hash        IN  VARCHAR2,
        p_card_id         OUT NUMBER,
        p_return_code     OUT NUMBER,
        p_return_message  OUT VARCHAR2
    ) AS
        v_customer_id NUMBER;
        v_card_type_id NUMBER;
        v_approved_limit NUMBER;
        v_card_holder_name VARCHAR2(100);
    BEGIN
        -- Get application details
        SELECT a.CUSTOMER_ID, a.CARD_TYPE_ID, a.APPROVED_LIMIT,
               c.FIRST_NAME || ' ' || c.LAST_NAME
        INTO v_customer_id, v_card_type_id, v_approved_limit, v_card_holder_name
        FROM CC_APPLICATION a
        JOIN CC_CUSTOMER c ON a.CUSTOMER_ID = c.CUSTOMER_ID
        WHERE a.APPLICATION_ID = p_application_id
        AND a.STATUS_CODE = 'APPROVED';

        -- Generate card ID
        SELECT SEQ_CARD_ID.NEXTVAL INTO p_card_id FROM DUAL;

        -- Insert card
        INSERT INTO CC_CREDIT_CARD (
            CARD_ID, APPLICATION_ID, CUSTOMER_ID, CARD_TYPE_ID,
            CARD_NUMBER, CARD_HOLDER_NAME, EXPIRY_MONTH, EXPIRY_YEAR,
            CVV_HASH, CREDIT_LIMIT, AVAILABLE_CREDIT, CARD_STATUS
        ) VALUES (
            p_card_id, p_application_id, v_customer_id, v_card_type_id,
            p_card_number, v_card_holder_name,
            EXTRACT(MONTH FROM ADD_MONTHS(SYSDATE, 36)),
            EXTRACT(YEAR FROM ADD_MONTHS(SYSDATE, 36)),
            p_cvv_hash, v_approved_limit, v_approved_limit, 'INACTIVE'
        );

        p_return_code := 0;
        p_return_message := 'Card issued successfully';
        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            p_return_code := -1;
            p_return_message := 'Error: ' || SQLERRM;
    END SP_ISSUE_CREDIT_CARD;

END PKG_CREDIT_CARD_APP;
/

-- Grant execute permissions
-- GRANT EXECUTE ON PKG_CREDIT_CARD_APP TO ccapp_user;

COMMIT;

-- Made with Bob
