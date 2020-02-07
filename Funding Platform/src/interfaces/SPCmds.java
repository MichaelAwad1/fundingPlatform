package interfaces;

/**
 * Interface that provides all method to interact with a supporter.
 */
import java.util.ArrayList;
import datatypes.SupporterData;
import dbadapter.FundingRequest;


public interface SPCmds {
	

	public ArrayList<FundingRequest> forwardSearch(String status);

	public boolean forwardEnterDonation(int id, SupporterData supporterInformation , int donationAmount);
	public FundingRequest forwardGetProject(int id);
}
