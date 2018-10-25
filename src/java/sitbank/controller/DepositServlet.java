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
public class DepositServlet extends HttpServlet {

    
    //สร้าง Persistance ปกติ
    @PersistenceUnit(unitName = "SITBankPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;
    
    // ด้านบนนี่จำเป้นต้องจำให้ได้

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

        //โหลด session ขึ้นมา
        HttpSession session = request.getSession(false);
        
        // นำ sesion ที่ชื่อว่า LoggedIn นำมายัดไว้ใน object AccountLoggedIn โดย แคสให้มันเป็น Account
        Account AccountLoggedIn = (Account) session.getAttribute("LoggedIn");

        
        //ถ้ามีการส่ง parameter ที่ชื่อว่า DepositAm มา แสดงว่ามันไม่เท่ากับ null
        if (request.getParameter("DepositAm") != null) {

            
            //แปลงค่าที่รับมาเป็น int
            int AmountDeposit = parseInt(request.getParameter("DepositAm"));

            //โหลด jpa controller ขึ้นมาชื่อว่า accountCtrl
            AccountJpaController accountCtrl = new AccountJpaController(utx, emf);

            //นำ account ไอดีที่มาจาก session ไปค้นหาใน database
            Account depositAccount = accountCtrl.findAccount(AccountLoggedIn.getAccountid());

            
            //ถ้ามันไม่เท่ากับ null
            if (depositAccount != null) {

                //นำเงินเก่ามาบวกกับเงินใหม่
                depositAccount.setBalance(AmountDeposit + depositAccount.getBalance());

                
                //แก้ปัญหา Exception
                try {
                    //นำข้อมูลที่แก้ไขไปเมื่อแก้นำไปแก้ใน db
                    accountCtrl.edit(depositAccount);

                } catch (RollbackFailureException rbf) {
                    System.out.println(rbf);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                //Add เข้าสู่ History
                HistoryJpaController hisCtrl = new HistoryJpaController(utx, emf);

                History his = new History(hisCtrl.getHistoryCount() + 1);

                his.setTime(new Date());
                his.setAmount(AmountDeposit);
                his.setBalance(AmountDeposit + depositAccount.getBalance());
                his.setAccountid(AccountLoggedIn);
                his.setMethod("Deposit");

                try {

                    hisCtrl.create(his);

                } catch (RollbackFailureException rbf) {
                    System.out.println(rbf);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                session.setAttribute("finish", "Deposit Complate");
                session.setAttribute("LoggedIn", depositAccount);
                response.sendRedirect("MyAccount");
                return;

            }

        }

        getServletContext().getRequestDispatcher("/DepositView.jsp").forward(request, response);
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
