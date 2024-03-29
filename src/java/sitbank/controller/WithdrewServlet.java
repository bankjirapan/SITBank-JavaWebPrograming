/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitbank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import sitbank.jpa.controller.AccountJpaController;
import sitbank.jpa.controller.HistoryJpaController;
import sitbank.jpa.controller.exceptions.RollbackFailureException;
import sitbank.jpa.models.Account;
import sitbank.jpa.models.History;

/**
 *
 * @author bankcom
 */
public class WithdrewServlet extends HttpServlet {

    
    
    @PersistenceUnit(unitName = "SITBankPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        
        HttpSession session = request.getSession(false);
        Account AccountLoggedIn = (Account) session.getAttribute("LoggedIn");

        if (request.getParameter("WithDrewAm") != null) {

            int AmountWithdrew = parseInt(request.getParameter("WithDrewAm"));

            AccountJpaController accountCtrl = new AccountJpaController(utx, emf);

            Account withdrewAccount = accountCtrl.findAccount(AccountLoggedIn.getAccountid());

            if (withdrewAccount != null) {

                withdrewAccount.setBalance(withdrewAccount.getBalance() - AmountWithdrew);

                try {

                    accountCtrl.edit(withdrewAccount);

                } catch (RollbackFailureException rbf) {
                    System.out.println(rbf);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                //Add เข้าสู่ History
                HistoryJpaController hisCtrl = new HistoryJpaController(utx, emf);

                History his = new History(hisCtrl.getHistoryCount() + 1);

                his.setTime(new Date());
                his.setAmount(AmountWithdrew);
                his.setBalance(withdrewAccount.getBalance() - AmountWithdrew);
                his.setAccountid(AccountLoggedIn);
                his.setMethod("WirhDrew");

                try {

                    hisCtrl.create(his);

                } catch (RollbackFailureException rbf) {
                    System.out.println(rbf);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                session.setAttribute("finish", "Withdrew Complate");
                session.setAttribute("LoggedIn", withdrewAccount);
                response.sendRedirect("MyAccount");
                return;

            }

        }

        getServletContext().getRequestDispatcher("/WithdrewView.jsp").forward(request, response);
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
