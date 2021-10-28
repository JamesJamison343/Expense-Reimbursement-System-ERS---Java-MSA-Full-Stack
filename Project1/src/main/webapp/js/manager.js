
let ticketStore;
let currentManager;

window.onload = function() {
	document.getElementById("manaTicketBtn").addEventListener('click', getAllTickets);
	getSessUser();
};

///get session user
function getSessUser() {
	console.log("in sess user");
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let user = JSON.parse(xhttp.responseText);
			currentManager = user;
			console.log(currentManager);
			document.getElementById("welcomeHeading").innerText = `Welcome, ${user.firstName}`;
		}
	}
	xhttp.open('GET', "http://localhost:8080/Project1/getsessionuser.json");
	xhttp.send();
};

//get all tickets 
function getAllTickets() {
	console.log("in get all tickets");
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let tickets = JSON.parse(xhttp.responseText);
			ticketStore = tickets;
			console.log(ticketStore);
			displayTickets(tickets);
		}
	}
	xhttp.open('GET', "http://localhost:8080/Project1/getalltickets.json");
	xhttp.send();
};

//display all tickets
function displayTickets(tickets) {
	let filter = document.getElementById("statusSelect").value;
	console.log(filter);
	document.getElementById("manaViewTickets").innerHTML = "";
	tickets.forEach(t => {
		if(filter === "none" || t.status.status === filter) {
			document.getElementById("manaViewTickets").innerHTML += 
				`<li id="${t.reimbursementId}" class="list-group-item d-flex justify-content-between"><span><strong>Ticket Id:</strong> ${t.reimbursementId} |  
					<strong>Amount:</strong> $${t.ammount} | <strong>Type:</strong> ${t.type.type} | 
					<strong>Description:</strong> ${t.description}<br>
					<strong>Status:</strong> ${t.status.status}<br>
					<strong>Submitted:</strong> ${new Date(t.submitted)}<br>
					<strong>Author ID:</strong> ${t.author}<br>
					<strong>Resolved:</strong> ${t.resolved === null ? '' : new Date(t.resolved)}<br> 
					<strong>Resolver ID:</strong> ${t.resolver === 0 ? '' : t.resolver}</span>
					<span><button onclick="approveTicket(${t.reimbursementId})" id="approveBtn" type="button" class="btn btn-primary">Approve</button>
					<button onclick="denyTicket(${t.reimbursementId})" type="button" class="btn btn-primary">Deny</button></span>
				</li>`;
		}
	})
};

//approve ticket
function approveTicket(ticketID) {
	console.log("in approve ticket");
	let xhttp = new XMLHttpRequest();
	let ticket;
	for(key in ticketStore) {
		if(ticketStore[key].reimbursementId === ticketID) {
			ticket = ticketStore[key];
		}
	}
	
	//ticket.status.status = "Approved";
	ticket.resolver = currentManager.userId;
	console.log(ticket);
	let ticketString = JSON.stringify(ticket);

	console.log(ticketString);
	
	xhttp.open('POST', "http://localhost:8080/Project1/approveTicket.json");
	
	xhttp.setRequestHeader('Content-type', 'application/json');
	
	xhttp.onreadystatechange = function() {
    	if(xhttp.readyState == 4 && xhttp.status == 200) {
			getAllTickets();
        	alert(xhttp.responseText + " " + xhttp.status);
    	}
	}	
	
	xhttp.send(ticketString);
};

//deny ticket
function denyTicket(ticketID) {
	console.log("in deny ticket");
	let xhttp = new XMLHttpRequest();
	let ticket;
	for(key in ticketStore) {
		if(ticketStore[key].reimbursementId === ticketID) {
			ticket = ticketStore[key];
		}
	}
	
	//ticket.status.status = "Denied";
	ticket.resolver = currentManager.userId;
	console.log(ticket);
	let ticketString = JSON.stringify(ticket);

	console.log(ticketString);
	
	xhttp.open('POST', "http://localhost:8080/Project1/denyTicket.json");
	
	xhttp.setRequestHeader('Content-type', 'application/json');
	
	xhttp.onreadystatechange = function() {
    	if(xhttp.readyState == 4 && xhttp.status == 200) {
			getAllTickets();
        	alert(xhttp.responseText + " " + xhttp.status);
    	}
	}	
	
	xhttp.send(ticketString);
};







