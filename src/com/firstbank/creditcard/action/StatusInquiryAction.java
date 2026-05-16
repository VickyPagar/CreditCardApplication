package com.firstbank.creditcard.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.firstbank.creditcard.ejb.session.CreditCardApplicationHome;
import com.firstbank.creditcard.ejb.session.CreditCardApplicationRemote;
import com.firstbank.creditcard.vo.ApplicationVO;

/**
 * StatusInquiryAction - Struts Action for application status inquiry.
 * Looks up the EJB with the provided application ID and forwards
 * result to the status display page.
 *
 * Mapped URL: /statusInquiry.do
 *
 * @author  FirstBank IT Department - Web Team
 * @version 1.0  2001-06-15
 * @since   Struts 1.1
 */
public class StatusInquiryAction extends Action {

    private static final Logger logger = Logger.getLogger(StatusInquiryAction.class.getName());
    private static final String EJB_JNDI_NAME = "ejb/CreditCardApplicationBean";

    public ActionForward execute(
            ActionMapping       mapping,
            ActionForm          form,
            HttpServletRequest  request,
            HttpServletResponse response) {

        logger.info("StatusInquiryAction.execute() called");

        DynaActionForm statusForm = (DynaActionForm) form;
        ActionErrors   errors     = new ActionErrors();

        String applicationIdStr = (String) statusForm.get("applicationId");

        if (applicationIdStr == null || applicationIdStr.trim().length() == 0) {
            errors.add("applicationId", new ActionError("error.applicationId.required"));
            saveErrors(request, errors);
            return mapping.findForward("error");
        }

        long applicationId;
        try {
            applicationId = Long.parseLong(applicationIdStr.trim());
        } catch (NumberFormatException nfe) {
            errors.add("applicationId", new ActionError("error.applicationId.format"));
            saveErrors(request, errors);
            return mapping.findForward("error");
        }

        try {
            InitialContext ctx = new InitialContext();
            CreditCardApplicationHome home =
                (CreditCardApplicationHome) javax.rmi.PortableRemoteObject.narrow(
                    ctx.lookup(EJB_JNDI_NAME), CreditCardApplicationHome.class);
            CreditCardApplicationRemote ejb = home.create();
            ApplicationVO appVO = ejb.getApplicationStatus(applicationId);

            if (appVO == null) {
                return mapping.findForward("notFound");
            }

            request.setAttribute("applicationVO", appVO);
            return mapping.findForward("success");

        } catch (javax.ejb.EJBException ejbEx) {
            logger.log(Level.WARNING, "Application not found or EJB error: " + applicationId, ejbEx);
            return mapping.findForward("notFound");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in StatusInquiryAction", e);
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.system.unexpected"));
            saveErrors(request, errors);
            return mapping.findForward("error");
        }
    }
}
