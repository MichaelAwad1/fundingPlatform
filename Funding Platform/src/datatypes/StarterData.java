package datatypes;

/**
 * Contains informations about a starter.
 * 
 */

public class StarterData {
	
	private String email;
	private String iban;
	
	
	public StarterData(String email, String iban) {
		super();
		this.email = email;
		this.iban = iban;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
	}
	
	
	

}
