package interfaces;

/**
 * Interface for DBFacade to provide all necessary database function.
 *
 */
import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.RewardsData;
import datatypes.StarterData;
import datatypes.SupporterData;
import dbadapter.FundingRequest;

public interface IFundingRequest {
	
	public ArrayList<FundingRequest> getAvailableFundingRequests(String status);
	public FundingRequest getProject(int id);
	
	public void storeNewFundingRequest(StarterData starterinformation , String projectName,
			String description, int fundingLimit, Timestamp endDate, RewardsData listOfRewards);
	
	public boolean addDonation(int id, SupporterData supporterInformation , int donationAmount);
	
	public boolean setSuccess();
	
	public boolean setFailure();
	
	
}
