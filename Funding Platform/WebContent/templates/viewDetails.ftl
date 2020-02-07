<#include "header.ftl">

<form method="POST" action="supportergui?action=selectProjectDetails">
<h1> Selected Project Details </h1>

<ul id>
<li>Project Name: ${project.projectName} </li>
<li>Description: ${project.description}</li>
<li>Funding Limit: ${project.fundingLimit}</li>
<li>End Date: ${project.endDate}</li>
<li>For this amount: ${amount}</li>
<li>Reward is: ${reward}</li>
<li>Total donations: ${project.totalDonation}</li>
<li>Email: ${email} </li>
<li>IBAN: ${IBAN}</li>
</ul>
</form>
<div>         
<h1> Click on the button if you wish to donate </h1>

<form action="donate">
       
<button type="button"  id="viewDetails"><a href="/FP/donate?hid=${project.id}"  id="viewDetails"> Donate </button> 
</form>

<#include "footer.ftl">