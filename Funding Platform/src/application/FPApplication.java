package application;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import datatypes.RewardsData;
import datatypes.StarterData;
import datatypes.SupporterData;
import dbadapter.DBFacade;
import dbadapter.FundingRequest;
import interfaces.STCmds;
import interfaces.Itimer;
import interfaces.SPCmds;

// This class contains the FPApplication which acts as the interface between all
// components.

public class FPApplication implements STCmds, SPCmds{
	
	private static FPApplication instance;

	
	 
	//Implementation of the Singleton pattern.
	  
	 
	 
	public static FPApplication getInstance() {
		if (instance == null) {
			instance = new FPApplication();
		}

		return instance;
	}

	/**
	 * Calls DBFacace method to make new funding request
	 * 
	 */

	@Override
	public boolean makeFundingRequest(StarterData starterinformation, String projectName, String description,
			int fundingLimit, Timestamp endDate, RewardsData listOfRewards) {
		
		try {
		DBFacade.getInstance().storeNewFundingRequest(starterinformation, projectName, description,
				fundingLimit, endDate, listOfRewards);
		
		return true;
		}	catch (Exception e) {
					e.printStackTrace();
				}
		return false;
	}

	/**
	 * Calls DBFacace method to search for all funding rquest having specific status
	 * 
	 */

	@Override
	public ArrayList<FundingRequest> forwardSearch(String status) {
		ArrayList<FundingRequest> result = new ArrayList<FundingRequest>();
		result = DBFacade.getInstance().getAvailableFundingRequests(status);
		return result ;
	}


	@Override
	public boolean forwardEnterDonation(int id, SupporterData supporterInformation, int donationAmount) {
	return DBFacade.getInstance().addDonation(id, supporterInformation, donationAmount);
		
	}

	@Override
	public FundingRequest forwardGetProject(int id) {
		FundingRequest result = DBFacade.getInstance().getProject(id);
		return result;
		
	}

	public void setSucccessAndFail() {
		DBFacade.getInstance().setSuccess() ; 
		DBFacade.getInstance().setFailure(); 
		
			
			
			
		
		
	}
}
