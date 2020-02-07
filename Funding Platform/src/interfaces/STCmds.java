package interfaces;
/**
 * Interface that provides all method to interact with a starter.
 */

import java.sql.Timestamp;
import datatypes.RewardsData;
import datatypes.StarterData;



public interface STCmds {

	public boolean makeFundingRequest(StarterData starterinformation , String projectName, String description,
			int fundingLimit, Timestamp endDate, RewardsData listOfRewards);

}
