package dbadapter;

import datatypes.SupporterData;

/**
 * Class representing a donation
 * 
 */
public class Donation {
	
	private SupporterData supporterInformation;
	private int donationAmount;
	private int donationID;
	private int frid;
	
	public Donation(SupporterData supporterInformation, int donationAmount, int donationID, int frid) {
		super();
		this.supporterInformation = supporterInformation;
		this.donationAmount = donationAmount;
		this.donationID = donationID;
		this.frid = frid;
	}

	public SupporterData getSupporterInformation() {
		return supporterInformation;
	}

	public void setSupporterInformation(SupporterData supporterInformation) {
		this.supporterInformation = supporterInformation;
	}

	public int getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(int donationAmount) {
		this.donationAmount = donationAmount;
	}

	public int getDonationID() {
		return donationID;
	}

	public void setDonationID(int donationID) {
		this.donationID = donationID;
	}

	public int getFrid() {
		return frid;
	}

	public void setFrid(int frid) {
		this.frid = frid;
	}
	
	

}
