package dbadapter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
 * Class which acts as the connector between application and database. Creates
 * Java objects from SQL returns. Exceptions thrown in this class will be
 * catched with a 500 error page.
 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import datatypes.RewardsData;
import datatypes.StarterData;
import datatypes.SupporterData;
import interfaces.IFundingRequest;

public class DBFacade implements IFundingRequest{
	
	private static DBFacade instance;
	
	/**
	 * Contructor which loads the corresponding driver for the chosen database
	 * type
	 */
	private DBFacade() {
		try {
			Class.forName("com." + Configuration.getType() + ".jdbc.Driver")
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return
	 */
	public static DBFacade getInstance() {
		if (instance == null) {
			instance = new DBFacade();
		}

		return instance;
	}

	/**
	 * Function that returns all appropriate funding requests from the database.
	 * 
	 * @param status
	 * 
	 * @return Arraylist of all funding requests objects.
	 */

	@Override
	public ArrayList<FundingRequest> getAvailableFundingRequests(String status) {
		ArrayList<FundingRequest> result = new ArrayList<FundingRequest>();

		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM FundingRequest WHERE status = ?";
//email	iban	projectname	description	fundinglimit	endDate	amount	reward	totaldonation	status

		// Query all offers that fits to the given criteria.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlSelect)) {
				ps.setString(1, status.toUpperCase());
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						StarterData std = new StarterData(rs.getString("email"), rs.getString("iban"));
						RewardsData ls = new RewardsData(rs.getInt("amount"), rs.getString("reward"));
								
						FundingRequest temp = new FundingRequest(rs.getInt("id"), std , 
								rs.getString("projectname"), rs.getString("description"), rs.getInt("fundinglimit"),
								rs.getTimestamp("endDate"), ls , rs.getInt("totaldonation"), rs.getString("status") );
									result.add(temp);					

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
		
	
	
	/**
	 * Function that add a new funding request to the database
	 * @return 
	 */
		
	@Override
	public void storeNewFundingRequest(StarterData starterinformation, String projectName, String description,
			int fundingLimit, Timestamp endDate, RewardsData listOfRewards) {
		
		// Declare SQL query to insert offer.
				String sqlInsert = "INSERT INTO FundingRequest (id,email,iban,projectname,"
						+ "description,fundinglimit,endDate,amount,reward,totaldonation,status) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				// Insert request into database.
				try (Connection connection = DriverManager.getConnection(
						"jdbc:" + Configuration.getType() + "://"
								+ Configuration.getServer() + ":"
								+ Configuration.getPort() + "/"
								+ Configuration.getDatabase(), Configuration.getUser(),
						Configuration.getPassword())) {

					try (PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
						ps.setInt(1,getLastID()+1);
						ps.setString(2, starterinformation.getEmail());
						ps.setString(3, starterinformation.getIban());
						ps.setString(4, projectName);
						ps.setString(5, description);
						ps.setInt(6, fundingLimit);
						ps.setTimestamp(7, endDate);
						ps.setInt(8,listOfRewards.getAmount());
						ps.setString(9, listOfRewards.getReward());
						ps.setInt(10, 0);
						ps.setString(11,"OPEN");																		
						ps.executeUpdate();
					
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

	}
//	email	iban	donationamount	donationID	frid
	@Override
	public boolean addDonation(int id, SupporterData supporterInformation, int donationAmount) {
		// Declare SQL query to add a donation.
		FundingRequest fr = getProject(id);
		if(fr.getStatus().equalsIgnoreCase("open")) {
			String sqlInsert = "INSERT INTO Donation (email,iban,donationamount,"
					+ "donationID,frid) VALUES (?,?,?,?,?)";
			String sqlUpdate = "UPDATE FundingRequest SET totaldonation = ? WHERE id = ?";
			
			
			// Insert donation into database.
			try (Connection connection = DriverManager.getConnection(
					"jdbc:" + Configuration.getType() + "://"
							+ Configuration.getServer() + ":"
							+ Configuration.getPort() + "/"
							+ Configuration.getDatabase(), Configuration.getUser(),
					Configuration.getPassword())) {

				try (PreparedStatement ps = connection.prepareStatement(sqlInsert);
						PreparedStatement ps2 = connection.prepareStatement(sqlUpdate)) {
					ps.setString(1,supporterInformation.getEmail());
					ps.setString(2,supporterInformation.getIban());
					ps.setInt(3, donationAmount);
					ps.setInt(4, getLastDonationID()+1);
					ps.setInt(5, id);												
					ps.executeUpdate();
					int totalDonationNew;
					totalDonationNew = fr.getTotalDonation() + donationAmount;
					ps2.setInt(1, (totalDonationNew));
					ps2.setInt(2, id);
					ps2.executeUpdate();
					return true;
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
		
		}

	// do we need to return an arraylist?? 
	@Override
	public boolean setSuccess() {
		ArrayList<FundingRequest> result = new ArrayList<FundingRequest>();
		ArrayList<String> supporters = new ArrayList<String>();
		ArrayList<String> starters=new ArrayList<String>();

		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM FundingRequest WHERE (status = 'OPEN' AND(SELECT CURRENT_DATE)>endDate AND totaldonation>=fundinglimit)";

		String sqlSelectB ="SELECT * FROM donation WHERE(frid = ?)" ;		
		String sqlUpdate = "UPDATE FundingRequest SET status = 'SUCCESSFUL' WHERE ((SELECT CURRENT_DATE)>endDate AND totaldonation>=fundinglimit)";
		// Query all funding requests that fits to the given criteria.
		starters.add("Starters:");
		supporters.add("supporters:");
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (Statement ps = (Statement) connection.createStatement();
					PreparedStatement psSelectB = connection.prepareStatement(sqlSelectB);
					Statement s = (Statement) connection.createStatement()) {

				try (ResultSet rs = ps.executeQuery(sqlSelect)) {
				
					while (rs.next()) {
						StarterData std = new StarterData(rs.getString("email"), rs.getString("iban"));
						RewardsData ls = new RewardsData(rs.getInt("amount"), rs.getString("reward"));
						FundingRequest temp = new FundingRequest(rs.getInt("id"), std , 
							rs.getString("projectname"), rs.getString("description"), rs.getInt("fundinglimit"),
								rs.getTimestamp("endDate"), ls , rs.getInt("totaldonation"), rs.getString("status") );
						result.add(temp);
						
						//get all supporters of this funding request
						psSelectB.setInt(1, rs.getInt("id"));
						starters.add(rs.getString("email")+" ,"+rs.getString("iban"));
						starters.add("The following project has succeeded:" + rs.getString("projectname"));
						try (ResultSet brs = psSelectB.executeQuery()) {
							
							while (brs.next()) {
								supporters.add(brs.getString("email") + ", " + brs.getString("iban"));
								supporters.add(" The following project has succeeded:" + rs.getString("projectname"));
								
							}
						
							
						}
						
					}sendSuccessEmail(starters,supporters);
				}
				try 
				{
					
					s.executeUpdate(sqlUpdate);
					
					return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false; 
	}

	
	// do we need to return an arraylist?? 
	@Override
	public boolean setFailure() {
		
		ArrayList<FundingRequest> result = new ArrayList<FundingRequest>();
		ArrayList<String> supporters = new ArrayList<String>();
		ArrayList<String> starters=new ArrayList<String>();
		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM FundingRequest WHERE (status = 'OPEN' AND(SELECT CURRENT_DATE)>endDate AND totaldonation<fundinglimit)";

		String sqlSelectB ="SELECT * FROM donation WHERE(frid = ?)" ;		
		String sqlUpdate = "UPDATE FundingRequest SET status = 'FAILED' WHERE ((SELECT CURRENT_DATE)>endDate AND totaldonation<fundinglimit)";
		// Query all funding requests that fits to the given criteria.
		starters.add("Starters:");
		supporters.add("supporters:");
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (Statement ps = (Statement) connection.createStatement();
					PreparedStatement psSelectB = connection.prepareStatement(sqlSelectB);
					Statement s = (Statement) connection.createStatement()) {

				try (ResultSet rs = ps.executeQuery(sqlSelect)) {
					while (rs.next()) {
						StarterData std = new StarterData(rs.getString("email"), rs.getString("iban"));
						RewardsData ls = new RewardsData(rs.getInt("amount"), rs.getString("reward"));
						FundingRequest temp = new FundingRequest(rs.getInt("id"), std , 
							rs.getString("projectname"), rs.getString("description"), rs.getInt("fundinglimit"),
								rs.getTimestamp("endDate"), ls , rs.getInt("totaldonation"), rs.getString("status") );
						result.add(temp);
						
						//get all supporters of this funding request
						psSelectB.setInt(1, rs.getInt("id"));
						
						starters.add(rs.getString("email")+" ,"+rs.getString("iban"));
						starters.add("The following project has failed:" + rs.getString("projectname"));
						try (ResultSet brs = psSelectB.executeQuery()) {
							 
							while (brs.next()) {
								supporters.add(brs.getString("email") + ", " + brs.getString("iban"));
								supporters.add(" The following project has failed:" + rs.getString("projectname"));
							}
							
						}
						
					}sendFailureEmail(starters,supporters);
				}
				try 
				{
					s.executeUpdate(sqlUpdate);
					return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false; 
	}

	public int getLastID() {
		String sqlQuery = "select max(id) as max from FundingRequest";
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {
			Statement st = (Statement) connection.createStatement();
			
		    ResultSet rs = st.executeQuery(sqlQuery);
					
		     if (rs.next()) {
					int lastID=rs.getInt("max");
					return lastID;
					
				}
		     return 0;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int getLastDonationID() {
		String sqlQuery = "select max(donationID) as maxID from Donation";
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {
			Statement st = (Statement) connection.createStatement();
			
		    ResultSet rs = st.executeQuery(sqlQuery);
					
		     if (rs.next()) {
					int lastID=rs.getInt("maxID");
					return lastID;
					
				}
		     return 0;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public FundingRequest getProject(int id) {
		FundingRequest result = null;

		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM FundingRequest WHERE id = ?";


		// Query all offers that fits to the given criteria.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlSelect)) {
				ps.setInt(1, id);
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						StarterData std = new StarterData(rs.getString("email"), rs.getString("iban"));
						RewardsData ls = new RewardsData(rs.getInt("amount"), rs.getString("reward"));
								
						 result = new FundingRequest(rs.getInt("id"), std , 
								rs.getString("projectname"), rs.getString("description"), rs.getInt("fundinglimit"),
								rs.getTimestamp("endDate"), ls , rs.getInt("totaldonation"), rs.getString("status") );
							
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public void sendFailureEmail(ArrayList<String>starters, ArrayList<String> supporters) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Fail1.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String line : starters) {
			writer.println(line);
		}
	    for (String line : supporters) {
	        writer.println(line);
	    }
	    writer.close();
				
	}
	
	public void sendSuccessEmail(ArrayList<String>starters, ArrayList<String> supporters) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Success1.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String line : starters) {
			writer.println(line);
		}
	    for (String line : supporters) {
	        writer.println(line);
	    }
	    writer.close();
		
		
	}
	
}