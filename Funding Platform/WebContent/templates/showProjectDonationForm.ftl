<#include "header.ftl">

<b>Welcome to our demonstration on the FP Webapp</b><br><br>

<form method="POST" action="donate?action=enterDonation">
	<fieldset id="browseAllProjects">
		<legend>Required Information</legend>
		
		<div>
			<label>Email</label>
			<input type="email" name="email" required>
	    </div>
	    <div>
	    	<label>IBAN</label>
	    	<input type="text" name="iban" required>
	    </div>
	    <div>
	    	<label>Donation Amount</label>
	    	<input type="number" min="1" name="amount" required>
	    </div>
	    
	</fieldset>
	
	<button type="submit" name="submit" id="submit">Submit</button>
</form>
<#include "footer.ftl">