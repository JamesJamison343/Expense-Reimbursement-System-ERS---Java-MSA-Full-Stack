window.onload = function() {
	let pokemonId = Math.ceil(Math.random() * 150);
	console.log(pokemonId);
	getPokemon(pokemonId);
}

function getPokemon(pokemonId) { 
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		 if(xhttp.readyState == 4 && xhttp.status == 200) {
			let pokemon = JSON.parse(xhttp.responseText);
			displayPokemon(pokemon);
			console.log(pokemon);
		}
	}
	xhttp.open('GET', `https://pokeapi.co/api/v2/pokemon/${pokemonId}`);
	xhttp.send();
}

function displayPokemon(pokemon) { 
	document.getElementById('pokeName').innerText = ` ${pokemon.name.toUpperCase()}`;
	document.getElementById('pokeImg').setAttribute("src", pokemon.sprites.front_default);
}
