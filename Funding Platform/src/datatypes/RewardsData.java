package datatypes;

/**
 * Contains rewards data information about a funding request.
 * 
 */

public class RewardsData {

	private int amount;
	private String reward;
	
	public RewardsData(int amount, String reward) {
		super();
		this.amount = amount;
		this.reward = reward;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
	
	
	
}
