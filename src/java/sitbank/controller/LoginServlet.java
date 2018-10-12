/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitbank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import sitbank.jpa.controller.AccountJpaController;
import sitbank.jpa.models.Account;

/**
 *
 * @author bankcom
 */
public class LoginServlet extends HttpServlet {

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

        //รับ พารามิเตอร์เข้ามาสองตัว
        String inAccountID = request.getParameter("inAccountID");
        String inPin = request.getParameter("inPin");

        //ถ้ามัน parameter ไม่ว้าง
        if (inAccountID != null && inPin != null) {

            //แปลงค่าเป็น Int เพื่อที่จะนำไปตรวจสอบต่อไป
            int AccountID = parseInt(inAccountID);
            int AccountPIN = parseInt(inPin);

            //โหลด Controller เข้ามาและส่งค่าของ Persistanc เข้าไป
            AccountJpaController accountCtrl = new AccountJpaController(utx, emf);

            //นำ Model Account เข้ามา จากนั้นนำไปค้นหาว่ามีผู้ใช้นี้หรือไม่
            Account SearchAccount = accountCtrl.findAccount(AccountID);

            //กรณีมีผู้ใช้ แสดงว่า SearchAccount มันไม่เท้ากับ Null
            if (SearchAccount != null) {

                //ดึง พิน จาก SearchAccount  และ มันเท่ากับ พารามิเตอร์ที่รับเข้ามา
                if (SearchAccount.getPin()== AccountPIN) {

                    //สร้าง Session ใหม่ ที่ชื่อว่า LoggedIn จากนั้นนำ SearchAccount เข้าไปใน Session
                    request.getSession(false).setAttribute("LoggedIn", SearchAccount);
                    //เปลี่ยนหน้าโดยใช้  Response จะทำการเปลี่ยนทั้ง URL
                    response.sendRedirect("MyAccount");
                    return;

                }
                
                //กรณีไม่เข้า if ใดๆ กลับหน้าเดิม
                request.setAttribute("msg", " Account or Pin Invalid");
                getServletContext().getRequestDispatcher("/LoginView.jsp").forward(request, response);

            }

        }

        //กรณีไม่มี Parameter ใดๆ
        getServletContext().getRequestDispatcher("/LoginView.jsp").forward(request, response);

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
