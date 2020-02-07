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

/**
 * Servlet implementation class StarterGui
 */
@WebServlet("/StarterGui")
public class StarterGui extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StarterGui() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set pagetitle und navtype
				request.setAttribute("navtype", "starter");
				request.setAttribute("pagetitle", "Enter Request");

				// Dispatch request to template engine
				try {
					request.getRequestDispatcher("/templates/defaultWebpageST.ftl").forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("navtype", "starter");

		// Check wether the call is insertOffer or not
		if (request.getParameter("action").equals("enterRequest") ) {
			
			

			// Append parameter of request
			String email = request.getParameter("email");
			String iban = (String) request.getParameter("IBAN");
			String amount = (String) request.getParameter("amount");
			String endDate = (String) request.getParameter("endDate");
			String reward = (String) request.getParameter("reward");
			String fundinglimit = (String) request.getParameter("fundingLimit");
			String projectName = (String) request.getParameter("projectName");
			String description = (String) request.getParameter("description");
			
			try {
				
//				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//				Date date = dateFormat.parse(arrivalTime);
//				long time = date.getTime();
//				Timestamp arrivalTimeSQL = new Timestamp(time);
				
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				
				date = dateFormat.parse(endDate);
				long time = date.getTime();
				Timestamp endTimeSQL = new Timestamp(time);
				if(!(amount.equals("")) || !(reward.equals("")))
				new FPApplication().makeFundingRequest(new StarterData(email,iban), projectName, description,
						Integer.parseInt(fundinglimit), endTimeSQL, new RewardsData(Integer.parseInt(amount), reward));
				else
					new FPApplication().makeFundingRequest(new StarterData(email,iban), projectName, description,
							Integer.parseInt(fundinglimit), endTimeSQL, new RewardsData(0, ""));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					


			// Call application to insert offer
			
			
			// Dispatch message to template engine
			try {
				request.setAttribute("pagetitle", "Enter Request");
				request.setAttribute("message", "New Funding Request successfully stored in the database.");
				request.getRequestDispatcher("/templates/showConfirmMake.ftl").forward(request, response);

			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			// Call doGet if request is not equal to insertOffer
		} else
			doGet(request, response);

	}
	}


