package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.FPApplication;

import dbadapter.FundingRequest;

/**
 * Servlet implementation class SupporterGui
 */
@WebServlet("/SupporterGui")
public class SupporterGui extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupporterGui() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set navtype
				request.setAttribute("navtype", "supporter");

				// Catch error if there is no page contained in the request
				String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");

				// Case: Request booking form
				if (action.equals("selectProjectDetails")) {
					// Set request attributes
					request.setAttribute("pagetitle", "Project Details");
					request.setAttribute("hid", request.getParameter("hid"));
					FundingRequest fr = FPApplication.getInstance().
								forwardGetProject(Integer.parseInt(request.getParameter("hid")));
					request.setAttribute("project", fr);
					request.setAttribute("reward", fr.getListOfRewards().getReward());
					request.setAttribute("amount", fr.getListOfRewards().getAmount());
					request.setAttribute("email", fr.getStarterInformation().getEmail());
					request.setAttribute("IBAN", fr.getStarterInformation().getIban());
					

					
					// Dispatch request to template engine
					try {
						request.getRequestDispatcher("/templates/viewDetails.ftl").forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Otherwise show search form
				} 
				else {

					// Set request attributes
					request.setAttribute("pagetitle", "Search Projects");

					// Dispatch request to template engine
					try {
						request.getRequestDispatcher("/templates/defaultWebpageSP.ftl").forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("navtype", "supporter");
		request.setAttribute("pagetitle", "Search results");

		String status = request.getParameter("Status");
		ArrayList<FundingRequest> result = new ArrayList<FundingRequest>();
		//FPApplication.getInstance().setSucccessAndFail();
		result = new FPApplication().forwardSearch(status);
		// Call application to insert offer
		

		// Dispatch message to template engine
		try {
			request.setAttribute("result", result);
			request.getRequestDispatcher("/templates/projectsRepresentation.ftl").forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
		doGet(request, response);
	}

}
