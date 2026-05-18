package com.firstbank.creditcard.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.firstbank.creditcard.ejb.session.CreditCardApplicationHome;
import com.firstbank.creditcard.ejb.session.CreditCardApplicationRemote;
import com.firstbank.creditcard.form.FinancialInfoForm;
import com.firstbank.creditcard.vo.ApplicationVO;
import com.firstbank.creditcard.vo.CustomerVO;

/**
 * FinancialInfoAction - Struts Action for handling Step 2 form submission.
 * Processes financial and employment information, submits the complete
 * application via EJB, and redirects to the confirmation page.
 *
 * Mapped URL: /financialInfo.do
 *
 * Forwards:
 *   success  -> /pages/confirmation.jsp  (application submitted)
 *   failure  -> /pages/financial_info.jsp (re-display with errors)
 *   session  -> /pages/session_expired.jsp (session lost)
 *   error    -> /pages/error.jsp          (system error)
 *
 * @author  FirstBank IT Department - Web Team
 * @version 1.0  2001-06-15
 * @since   Struts 1.1
 */
public class FinancialInfoAction extends Action {

    private static final Logger logger = Logger.getLogger(FinancialInfoAction.class.getName());

    private static final String EJB_JNDI_NAME      = "java:global/creditcard/CreditCardApplicationBean!com.firstbank.creditcard.ejb.session.CreditCardApplicationHome";
    static final String         SESSION_CUSTOMER_VO = "customerVO";
    static final String         SESSION_APPLICATION_ID = "applicationId";

    public ActionForward execute(
            ActionMapping       mapping,
            ActionForm          form,
            HttpServletRequest  request,
            HttpServletResponse response) {

        logger.info("FinancialInfoAction.execute() called");

        FinancialInfoForm finForm = (FinancialInfoForm) form;
        HttpSession       session = request.getSession(false);
        ActionErrors      errors  = new ActionErrors();

        // Guard: session must have CustomerVO from step 1
        if (session == null || session.getAttribute(SESSION_CUSTOMER_VO) == null) {
            logger.warning("Session expired or missing CustomerVO. Redirecting to session page.");
            return mapping.findForward("session");
        }

        CustomerVO customerVO = (CustomerVO) session.getAttribute(SESSION_CUSTOMER_VO);

        try {
            // Build ApplicationVO from form data
            ApplicationVO appVO = new ApplicationVO();
            appVO.setCardTypeId(Integer.parseInt(finForm.getCardTypeId()));

            // Requested limit (optional)
            if (finForm.getRequestedLimit() != null && finForm.getRequestedLimit().trim().length() > 0) {
                appVO.setRequestedLimit(parseDouble(finForm.getRequestedLimit()));
            }

            // Financial fields
            appVO.setAnnualIncome(parseDouble(finForm.getAnnualIncome()));
            appVO.setOtherIncome(parseDoubleOrZero(finForm.getOtherIncome()));
            appVO.setMonthlyRent(parseDoubleOrZero(finForm.getMonthlyRent()));
            appVO.setMortgageBalance(parseDoubleOrZero(finForm.getMortgageBalance()));
            appVO.setTotalDebt(parseDoubleOrZero(finForm.getTotalDebt()));
            appVO.setCheckingBalance(parseDoubleOrZero(finForm.getCheckingBalance()));
            appVO.setSavingsBalance(parseDoubleOrZero(finForm.getSavingsBalance()));
            appVO.setHomeOwnership(finForm.getHomeOwnership());
            appVO.setBankruptcyFlag(
                "Y".equalsIgnoreCase(finForm.getBankruptcyFlag()) ? "Y" : "N");

            // Employment fields
            appVO.setEmploymentType(finForm.getEmploymentType());
            appVO.setEmployerName(finForm.getEmployerName());
            appVO.setEmployerPhone(finForm.getEmployerPhone());
            appVO.setJobTitle(finForm.getJobTitle());
            appVO.setYearsEmployed(parseIntOrZero(finForm.getYearsEmployed()));

            // Capture client IP address for audit
            appVO.setIpAddress(request.getRemoteAddr());

            // Determine the session user identifier
            String sessionUserId = "WEB_" + session.getId().substring(0, 8).toUpperCase();

            // ---- EJB CALL ----
            InitialContext ctx = new InitialContext();
            CreditCardApplicationHome home =
                (CreditCardApplicationHome) javax.rmi.PortableRemoteObject.narrow(
                    ctx.lookup(EJB_JNDI_NAME), CreditCardApplicationHome.class);
            CreditCardApplicationRemote ejb = home.create();

            long applicationId = ejb.processApplication(customerVO, appVO, sessionUserId);
            logger.info("Application submitted successfully. ID: " + applicationId);

            // Store application ID in session for confirmation page
            session.setAttribute(SESSION_APPLICATION_ID, new Long(applicationId));

            // Clean up step 1 data from session
            session.removeAttribute(SESSION_CUSTOMER_VO);

            return mapping.findForward("success");

        } catch (NumberFormatException nfe) {
            logger.log(Level.WARNING, "Number format error in financial form", nfe);
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.financial.numberFormat"));
            saveErrors(request, errors);
            return mapping.findForward("failure");

        } catch (javax.ejb.EJBException ejbEx) {
            logger.log(Level.SEVERE, "EJB error submitting application", ejbEx);
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.application.submission", ejbEx.getMessage()));
            saveErrors(request, errors);
            return mapping.findForward("failure");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in FinancialInfoAction", e);
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.system.unexpected"));
            saveErrors(request, errors);
            return mapping.findForward("error");
        }
    }

    // ----------------------------------------------------------
    // Private Helpers
    // ----------------------------------------------------------

    private double parseDouble(String value) throws NumberFormatException {
        if (value == null || value.trim().length() == 0) return 0.0;
        return Double.parseDouble(value.trim().replaceAll(",", ""));
    }

    private double parseDoubleOrZero(String value) {
        if (value == null || value.trim().length() == 0) return 0.0;
        try {
            return Double.parseDouble(value.trim().replaceAll(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseIntOrZero(String value) {
        if (value == null || value.trim().length() == 0) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
