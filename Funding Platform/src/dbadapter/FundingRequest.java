package dbadapter;

import java.sql.Timestamp;
import datatypes.RewardsData;
import datatypes.StarterData;

/**
 * Class representing a funding request
 * 
 */

public class FundingRequest {
	
	private int id;
	private StarterData starterInformation;
	private String projectName;
	private String description;
	private int fundingLimit;
	private Timestamp endDate;
	private RewardsData listOfRewards;
	private int totalDonation;
	private String status;
	
	public FundingRequest(int id, StarterData starterInformation, String projectName, String description,
			int fundingLimit, Timestamp endDate, RewardsData listOfRewards, int totalDonation, String status) {
		super();
		this.id = id;
		this.starterInformation = starterInformation;
		this.projectName = projectName;
		this.description = description;
		this.fundingLimit = fundingLimit;
		this.endDate = endDate;
		this.listOfRewards = listOfRewards;
		this.totalDonation = totalDonation;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StarterData getStarterInformation() {
		return starterInformation;
	}

	public void setStarterInformation(StarterData starterInformation) {
		this.starterInformation = starterInformation;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFundingLimit() {
		return fundingLimit;
	}

	public void setFundingLimit(int fundingLimit) {
		this.fundingLimit = fundingLimit;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public RewardsData getListOfRewards() {
		return listOfRewards;
	}

	public void setListOfRewards(RewardsData listOfRewards) {
		this.listOfRewards = listOfRewards;
	}

	public int getTotalDonation() {
		return totalDonation;
	}

	public void setTotalDonation(int totalDonation) {
		this.totalDonation = totalDonation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
