package testing;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dbadapter.Configuration;
import dbadapter.DBFacade;
import dbadapter.FundingRequest;
import datatypes.StarterData;
import datatypes.RewardsData;
import datatypes.SupporterData;

/**
 * Testing our DBFacade.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBFacade.class)
public class DBFacadeTest {

	private Connection stubCon;
	private String sqlSelect;
	private String sqlUpdate;
	private String sqlUpdateB;
	//private String sqlSelectB;
	private PreparedStatement ps;
	private PreparedStatement ps1;
	private PreparedStatement ps2;
	private ResultSet rs;
	//private ResultSet drs;

	/**
	 * Preparing classes with static methods
	 */
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DriverManager.class);

		// Declare necessary SQL queries
		sqlSelect = "SELECT * From FundingRequest WHERE status=? ";
		sqlUpdate="INSERT INTO FundingRequest (id,email,iban,projectname,"
				+ "description,fundinglimit,endDate,amount,reward,totaldonation,status) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		sqlUpdateB="INSERT INTO Donation (email,iban,donationamount,"
				+ "donationID,frid) VALUES (?,?,?,?,?)";
		// Mock return values
		ps = mock(PreparedStatement.class);
		ps1 = mock(PreparedStatement.class);
		ps2 = mock(PreparedStatement.class);
		rs = mock(ResultSet.class);
		//brs = mock(ResultSet.class);

		try {
			// Setting up return values for connection and statements
			stubCon = mock(Connection.class);
			PowerMockito.when(DriverManager.getConnection(
					"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
							+ Configuration.getPort() + "/" + Configuration.getDatabase(),
					Configuration.getUser(), Configuration.getPassword())).thenReturn(stubCon);

			when(stubCon.prepareStatement(sqlSelect)).thenReturn(ps);
			when(stubCon.prepareStatement(sqlUpdate)).thenReturn(ps1);
			when(stubCon.prepareStatement(sqlUpdateB)).thenReturn(ps1);
			when(ps.executeQuery()).thenReturn(rs);
			when(ps1.executeUpdate()).thenReturn(1);
			when(ps2.executeUpdate()).thenReturn(1);

			// Setting up return values for methods
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getInt(1)).thenReturn(0);
			when(rs.getString(2)).thenReturn("sfsd@gmail.com");
			when(rs.getString(3)).thenReturn("12345678901234A");
			//when(rs.getTimestamp(3)).thenReturn(Timestamp.valueOf("2015-12-30 00:00:00"));
			when(rs.getString(4)).thenReturn("second Project Name");
			when(rs.getString(5)).thenReturn("second Project description");
			when(rs.getInt(6)).thenReturn(1000);
			when(rs.getTimestamp(7)).thenReturn(Timestamp.valueOf("2020-01-19 19:16:33"));
			when(rs.getInt(7)).thenReturn(1000);
			when(rs.getString(8)).thenReturn("second reward");
			when(rs.getInt(9)).thenReturn(0);
			when(rs.getString(10)).thenReturn("offen");

			// Setting up return values for corresponding bo
			//when(brs.next()).thenReturn(true).thenReturn(false);
//			when(brs.getInt(1)).thenReturn(0);
//			when(brs.getTimestamp(2)).thenReturn(Timestamp.valueOf("2015-12-01 00:00:00"));
//			when(brs.getTimestamp(3)).thenReturn(Timestamp.valueOf("2015-12-01 00:00:00"));
//			when(brs.getTimestamp(4)).thenReturn(Timestamp.valueOf("2015-12-04 00:00:00"));
//			when(brs.getBoolean(5)).thenReturn(true);
//			when(brs.getString(6)).thenReturn("Peter Schulze");
//			when(brs.getString(7)).thenReturn("peter@uni-due.de");
//			when(brs.getDouble(8)).thenReturn(9.0);
//			when(brs.getInt(9)).thenReturn(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Testing getAvailableFundingRequests with non-empty results.
	 */
	@Test
	public void testGetAvailableFundingRequests() {

		// Select a time where no booking exists
		//Timestamp endDate = Timestamp.valueOf("2020-12-05 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");

		ArrayList<FundingRequest> Frs = DBFacade.getInstance().getAvailableFundingRequests("offen");

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps, times(1)).executeQuery();
			//verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(Frs.size() == 1);
		assertTrue(Frs.get(0).getId() == 0);
//		assertTrue(hos.get(0).getBookings().size() == 1);
//		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}

	/**
	 * Testing getAvailableHolidayOffer with empty result.
	 */
	@Test
	public void testGetAvailableHolidayOffersEmpty() {

		// Select a time where already a booking exists
//		Timestamp enDate = Timestamp.valueOf("2020-12-02 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-03 00:00:00");

		ArrayList<FundingRequest> Frs = DBFacade.getInstance().getAvailableFundingRequests("offen");

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps, times(1)).executeQuery();
//			verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values

		assertTrue(Frs.size() == 0);

	}
	
	
	public void testStoreNewFundingRequest() throws SQLException {

		// Select a time where no booking exists
		Timestamp endDate = Timestamp.valueOf("2020-12-05 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");
	StarterData starter =new StarterData("abc@xyz.com", "1263597642163586");
	RewardsData rewards= new RewardsData(100,"a reward");
		//	int row;
		DBFacade.getInstance().storeNewFundingRequest(starter, "Test project", "Test description", 1000, endDate, rewards);
		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlUpdate);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps1, times(1)).executeUpdate();
			//verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(ps1.executeUpdate(sqlUpdate) >0);
		//assertTrue(Frs.get(0).getId() == 0);
//		assertTrue(hos.get(0).getBookings().size() == 1);
//		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}
	
	public void testStoreNewFundingRequestFail() throws SQLException {

		// Select a time where no booking exists
		Timestamp endDate = Timestamp.valueOf("2020-12-05 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");
	StarterData starter =new StarterData("abc@xyz.com", "1263597642163586");
	RewardsData rewards= new RewardsData(100,"a reward");
		//	int row;
		DBFacade.getInstance().storeNewFundingRequest(starter,"", "Test description", 1000, endDate, rewards);
		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlUpdate);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps1, times(1)).executeUpdate();
			//verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(ps1.executeUpdate(sqlUpdate) ==0);
		//assertTrue(Frs.get(0).getId() == 0);
//		assertTrue(hos.get(0).getBookings().size() == 1);
//		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}
	
	
	public void testAddDonation() throws SQLException {

		// Select a time where no booking exists
		//Timestamp endDate = Timestamp.valueOf("2020-12-05 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");
	SupporterData supporter =new SupporterData("abc@xyz.com", "1263597642163586");
	//RewardsData rewards= new RewardsData(100,"a reward");
		//	int row;
		DBFacade.getInstance().addDonation(16, supporter, 200);
		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlUpdateB);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps2, times(1)).executeUpdate();
			//verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(ps2.executeUpdate(sqlUpdateB) >0);
		//assertTrue(Frs.get(0).getId() == 0);
//		assertTrue(hos.get(0).getBookings().size() == 1);
//		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}
	
	public void testAddDonationFail() throws SQLException {

		// Select a time where no booking exists
		//Timestamp endDate = Timestamp.valueOf("2020-12-05 00:00:00");
//		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");
	SupporterData supporter =new SupporterData("", "1263597642163586");
	//RewardsData rewards= new RewardsData(100,"a reward");
		//	int row;
		DBFacade.getInstance().addDonation(16, supporter, 200);
		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlUpdateB);
			//verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps2, times(1)).executeUpdate();
			//verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(ps2.executeUpdate(sqlUpdateB) ==0);
		//assertTrue(Frs.get(0).getId() == 0);
//		assertTrue(hos.get(0).getBookings().size() == 1);
//		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}
}
