package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.FPApplication;
import datatypes.RewardsData;
import datatypes.StarterData;
import datatypes.SupporterData;
import dbadapter.DBFacade;

/**
 * Servlet implementation class DonationServlet
 */
@WebServlet("/DonationServlet")
public class DonationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   private int id=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set pagetitle und navtype
		//id = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("navtype", "supporter");

		request.setAttribute("pagetitle", "Donate");
		id=Integer.parseInt(request.getParameter("hid"));
		//id = Integer.parseInt(sid);
		System.out.println(id);

		// Dispatch request to template engine
		try {
			request.getRequestDispatcher("/templates/showProjectDonationForm.ftl").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action").equals("enterDonation") ) {			
			request.setAttribute("navtype", "supporter");

			// Append parameter of request
			
			
			String email = (String) request.getParameter("email");
			String iban = (String) request.getParameter("iban");
			String amount = (String) request.getParameter("amount");
			System.out.println("HEREE");
			//id = Integer.parseInt(request.getParameter("hid"));
			
			//System.out.println(id + " HEEEE");
			
			int donationAmount = Integer.parseInt(amount);
			if(FPApplication.getInstance().forwardEnterDonation(id, new SupporterData(email, iban), donationAmount) == true) {
			System.out.println("inside if");
		
				try {
					
					request.setAttribute("pagetitle", "Enter Donation");
					request.setAttribute("message", "New Donation successful stored in the database.");
					request.getRequestDispatcher("/templates/showConfirmMake.ftl").forward(request, response);

				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
				// Call doGet if request is not equal to insertOffer
			} else
				doGet(request, response);
			}
			
				


						
	}
	}


