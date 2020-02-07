<#include "header.ftl">

<b>Welcome to our little demonstration on the FP Webapp</b>

<table id="availableHOs">
	<tr>
		<th><a href="viewDetails.ftl">#</th>
		<th>Name</th>
		<th>Description</th>
		<th>Funding limit</th>
		<th>End date</th>
	</tr>
	<#list result as ho>
	<tr>
	<td><a href="supportergui?action=selectProjectDetails&amp;hid=${ho.id}" id="Make Donation" title="Make Donation">${ho.id}</a></td>
		<td>${ho.projectName}</td>
		<td>${ho.description}</td>
		<td>${ho.fundingLimit}</td>
		<td>${ho.endDate}</td>
	</tr>
	</#list>
	
</table>
<#include "footer.ftl">