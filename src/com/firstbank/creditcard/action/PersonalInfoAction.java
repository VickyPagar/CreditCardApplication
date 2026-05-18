package com.firstbank.creditcard.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.firstbank.creditcard.form.PersonalInfoForm;
import com.firstbank.creditcard.vo.CustomerVO;

/**
 * PersonalInfoAction - Struts Action for handling Step 1 form submission.
 * Validates and processes applicant personal/contact information.
 * Stores validated CustomerVO in the HTTP session for use by subsequent steps.
 *
 * Mapped URL: /personalInfo.do
 *
 * Forwards:
 *   success  -> /pages/financial_info.jsp  (proceed to step 2)
 *   failure  -> /pages/personal_info.jsp   (re-display with errors)
 *   error    -> /pages/error.jsp           (system error)
 *
 * @author  FirstBank IT Department - Web Team
 * @version 1.0  2001-06-15
 * @since   Struts 1.1
 */
public class PersonalInfoAction extends Action {

    private static final Logger logger = Logger.getLogger(PersonalInfoAction.class.getName());

    /** JNDI name of the EJB Home interface */
    private static final String EJB_JNDI_NAME = "java:global/creditcard/CreditCardApplicationBean!com.firstbank.creditcard.ejb.session.CreditCardApplicationHome";

    /** Session attribute key for CustomerVO */
    static final String SESSION_CUSTOMER_VO = "customerVO";

    /** Session attribute key for card types list */
    static final String SESSION_CARD_TYPES = "cardTypes";

    /** Date format used in the form */
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    // ----------------------------------------------------------
    // Struts Action execute method
    // ----------------------------------------------------------

    public ActionForward execute(
            ActionMapping  mapping,
            ActionForm     form,
            HttpServletRequest  request,
            HttpServletResponse response) {

        logger.info("PersonalInfoAction.execute() called");

        PersonalInfoForm personalForm = (PersonalInfoForm) form;
        HttpSession      session      = request.getSession(true);
        ActionErrors     errors       = new ActionErrors();

        try {
            // Parse date of birth
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date dob;
            try {
                dob = sdf.parse(personalForm.getDateOfBirth());
            } catch (ParseException pe) {
                errors.add("dateOfBirth", new ActionError("error.dateOfBirth.format"));
                saveErrors(request, errors);
                return mapping.findForward("failure");
            }

            // Build CustomerVO from form data
            CustomerVO customerVO = new CustomerVO();
            customerVO.setFirstName(personalForm.getFirstName().trim());
            customerVO.setMiddleName(personalForm.getMiddleName() != null ?
                                     personalForm.getMiddleName().trim() : null);
            customerVO.setLastName(personalForm.getLastName().trim());
            customerVO.setDateOfBirth(dob);
            customerVO.setGender(personalForm.getGender());
            customerVO.setSsn(personalForm.getSsn().trim());
            customerVO.setEmailAddress(personalForm.getEmailAddress().trim());
            customerVO.setPhoneHome(personalForm.getPhoneHome());
            customerVO.setPhoneWork(personalForm.getPhoneWork());
            customerVO.setPhoneMobile(personalForm.getPhoneMobile());
            customerVO.setAddressLine1(personalForm.getAddressLine1().trim());
            customerVO.setAddressLine2(personalForm.getAddressLine2() != null ?
                                       personalForm.getAddressLine2().trim() : null);
            customerVO.setCity(personalForm.getCity().trim());
            customerVO.setStateCode(personalForm.getStateCode());
            customerVO.setZipCode(personalForm.getZipCode().trim());
            customerVO.setCountryCode("US");

            // Store in session for next step
            session.setAttribute(SESSION_CUSTOMER_VO, customerVO);
            logger.info("CustomerVO stored in session for: " + customerVO.getFullName());

            // Look up EJB and pre-load card types for step 2 dropdown
            try {
                InitialContext ctx = new InitialContext();
                CreditCardApplicationHome home =
                    (CreditCardApplicationHome) javax.rmi.PortableRemoteObject.narrow(
                        ctx.lookup(EJB_JNDI_NAME), CreditCardApplicationHome.class);
                CreditCardApplicationRemote ejb = home.create();
                List cardTypes = ejb.getAvailableCardTypes();
                session.setAttribute(SESSION_CARD_TYPES, cardTypes);
                logger.info("Card types loaded: " + cardTypes.size() + " types available.");
            } catch (Exception e) {
                // Non-fatal: card types can still be loaded on step 2
                logger.log(Level.WARNING, "Could not pre-load card types from EJB", e);
            }

            return mapping.findForward("success");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in PersonalInfoAction", e);
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.system.unexpected"));
            saveErrors(request, errors);
            return mapping.findForward("error");
        }
    }
}
