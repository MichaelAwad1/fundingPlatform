<#include "header.ftl">

<b>Welcome to our little demonstration on the FP Webapp</b><br><br>

<form method="POST" action="startergui?action=enterRequest">
	<fieldset id="insertrequest">
		<legend>Required Information</legend>
		<div>
			<label>Project Name</label>
			<input type="text" name="projectName" required>
	    </div>

		<div>
			<label>Description</label>
			<input type="text" name="description">
	    </div>

		<div>
			<label>Funding limit</label>
			<input type="number" name="fundingLimit" min="1" required>
	    </div>
	    
	   <div>
			<label>End Date</label>
			<input type="text" name="endDate" id="datepicker1">
	    </div>


		<div>
			<label>Amount</label>
			<input type="text" name="amount">
	    </div>
		<div>
			<label>Reward</label>
			<input type="text" name="reward">
	    </div>

		<div>
			<label>E-Mail</label>
			<input type="email" name="email" required>
	    </div>
	    
	    <div>
			<label>IBAN</label>
			<input type="text" name="IBAN"  minlength="15" maxlength="32" required>
	    </div>
	    
	</fieldset>
	<button type="submit" id="submit">Submit</button>
</form>
<#include "footer.ftl">