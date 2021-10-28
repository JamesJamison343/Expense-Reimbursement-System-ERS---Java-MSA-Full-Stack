window.onload = function() {
	getSessUser();
	document.getElementById("newTicketBtn").addEventListener('click', ()=> alert('Submitting Ticket'));
	document.getElementById("emplTicketBtn").addEventListener('click', getPastTickets);
};

///get session user
function getSessUser() {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let user = JSON.parse(xhttp.responseText);
			document.getElementById("welcomeHeading").innerText = `Welcome, ${user.firstName}`;
		}
	}
	xhttp.open('GET', "http://localhost:8080/Project1/getsessionuser.json");
	xhttp.send();
};

//get past tickets 
function getPastTickets() {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let tickets = JSON.parse(xhttp.responseText);
			console.log(tickets);
			displayTickets(tickets);
		}
	}
	xhttp.open('GET', "http://localhost:8080/Project1/getemployeetickets.json");
	xhttp.send();
};

//add tickets to modal
function displayTickets(tickets) {
	tickets.forEach(t => {
		document.getElementById("empViewTickets").innerHTML += 
			`<li class="list-group-item"><strong>Amount:</strong> $${t.ammount}<br><strong>Type:</strong> ${t.type.type}<br>
			<strong>Description:</strong> ${t.description}<br><strong>Status:</strong> ${t.status.status}<br>
			<strong>Submitted:</strong> ${new Date(t.submitted)}<br>
			<strong>Resolved:</strong> ${t.resolved === null ? '' : new Date(t.resolved)}<br>
			<strong>Resolver ID:</strong> ${t.resolver === 0 ? '' : t.resolver}
			</li>`;
	})
};















