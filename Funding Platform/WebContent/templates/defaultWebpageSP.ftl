<#include "header.ftl">

<b>Welcome to our little demonstration on the FP Webapp</b><br><br>

<form  method="POST" action="supportergui?action=browseProjects">
	<fieldset id="browseAllProjects">
		<legend>Select Project Status</legend>
		
		<div>
			<br>Select the status of the Project</br>
			<select name="Status">
			<option value="Open">Open</option>
			<option value="Successful">Successful</option>
			<option value="Failed">Failed</option>
			</select>
	    </div>

	</fieldset>
	
	<button type="Submit" id="Submit" name="Submit" value="Submit">Submit</button>
	
</form>
<#include "footer.ftl">

