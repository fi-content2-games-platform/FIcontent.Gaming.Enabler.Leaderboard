function setStatusOk(ok, str) {
	document.getElementById("status").style.backgroundColor = (ok?"green":"yellow");
	document.getElementById("status").style.color = "#000";
	document.getElementById("status").innerHTML = "Message:" + str;
}

// var url = "";
//
// $.ajax({
// 	url: "config.json",
// 	type: "GET",
// 	dataType : 'json',
// 	success: function (data) {
// 		url = data.serverUrlAndPort; // get value from config.json
// 	},
// 	error: function (err) {
// 		setStatusOk(false, "An error happened receiving the config.json");
// 	}
// });

var url = "http://localhost:4567/lb/";
// var url = "http://162.243.91.80:4567/lb/";

function utf8_to_b64(str) {
	return window.btoa(encodeURIComponent( escape( str )));
}

function b64_to_utf8(str) {
	if (!str) return "";
	return unescape(decodeURIComponent(window.atob( str )));
}

// $(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
// 	document.getElementById("status").style.backgroundColor = ("red");
// 	document.getElementById("status").innerHTML = "error message: " + thrownError + "<br>response:" + jqXHR.responseText;
// });

function submitScore(gameID, playerID, score) {
	var pstr = url + gameID + "/" + playerID + "/score";
	var userDataBase64 = utf8_to_b64(document.getElementById('userData').value);

	setStatusOk(false, pstr);

	$.ajax({
		url: pstr,
		data: JSON.stringify({
			scoreEntries: [
				{name:"highscore", value:score}
			],
			//authentication: ...,
			userData: userDataBase64 // base64 encoded, e.g. "VGhpcyBpcyBhIHRlc3QgY29tbWVudC4="
		}),
		processData: false,
		type: 'POST',
		contentType: 'text/plain', //'application/json',  cross site posting an application/json requires to implement the CORS preflight with OPTIONS, so this is a simple hacky workaround as the leaderboard server doesn't check the content-type
	}).done(function(data) {
		setStatusOk(true, "ok");
	})
	.fail(function() {
		setStatusOk(false, "fail");
	});
}

function getRankingPos(gameID, playerID) {
	var rstr = url + gameID + "/" + playerID + "/rankingposition";
	setStatusOk(false, rstr);

	$.get(rstr, {
		//
	},
	null,
	"jsonp").done(function(data) {
		var txt = data.position;
		document.getElementById("highestScore").innerHTML = txt;
	}).done(function(data) {
		setStatusOk(true, "ok");
		document.getElementById("position-container").style.backgroundColor = "#3C763D";
	})
	.fail(function() {
		setStatusOk(false, "fail");
		document.getElementById("position-container").style.backgroundColor = "#8A6D3B";
	});
}

function getScore(gameID, playerID) {
	var rstr = url + gameID + "/" + playerID + "/score";
	setStatusOk(false, rstr);

	$.get(rstr, {
		//
	},
	null,
	"jsonp").done(function(data) {
		var txt = data.score;
		document.getElementById("highestScorePoints").innerHTML = txt;
	}).done(function(data) {
		setStatusOk(true, "ok");
		document.getElementById("score-container").style.backgroundColor = "#3C763D";
	})
	.fail(function() {
		setStatusOk(false, "fail");
		document.getElementById("score-container").style.backgroundColor = "#8A6D3B";
	});
}


function getRankedList(gameID) {
	var rstr = url + gameID + "/rankedlist";
	setStatusOk(false, rstr);

	$.get(rstr, {}, null, "jsonp")
	.done(function(data) {
		var txt = "<table>";
		for (var i=0; i<data.length; i++) {
			txt += "<tr>";
			txt += "<td>" + (i+1) + ".</td><td>" + data[i].highscore + " points </td><td>by: " + data[i].playerID + "</td>";
			if (data[i].imgURL) {
				txt += "<td><img src='" + data[i].imgURL + "' width='32'></td>";
			}
			txt += "<td>userData: <pre>"+b64_to_utf8(data[i].userData)+"</pre></td>";
			txt += "</tr>";
		}
		txt += "</table>";
		document.getElementById("rankedlist").innerHTML = txt;
		//$('#rankedlist').html(txt);
		setStatusOk(true, "ok");
	})
	.fail(function() {
		setStatusOk(false, "fail");
	});
}

function createGame(gameID, maxEntries) {
	var pstr = url + gameID;
	var onlyKeepBestEntry = "false";
	var addUpScore = "false";
	if ($('#myOnlyKeepBestEntry').is(':checked'))
		onlyKeepBestEntry = "true";
	if ($('#myAddUpScore').is(':checked'))
		addUpScore = "true";
	var asn = document.getElementById("additionalScoreNames").value.split(",");
	var snetw = document.getElementById("socialnetwork").value;
	setStatusOk(false, pstr);

	$.ajax({
		url: pstr,
		data: JSON.stringify({
			maxEntries: maxEntries,
			onlyKeepBestEntry : onlyKeepBestEntry,
			addUpScore : addUpScore,
			highScoreNames: asn,
			socialnetwork: snetw
		}),
		processData: false,
		type: "PUT",
		crossDomain: true,
		//dataType: "jsonp",
		contentType: 'text/plain', //'application/json',  cross site posting an application/json requires to implement the CORS preflight with OPTIONS, so this is a simple hacky workaround as the leaderboard server doesn't check the content-type
		success: function() {
			console.log("success");
			setStatusOk(true, "ok");
		},
		error: function(jqXHR) {
			console.log(jqXHR);
			setStatusOk(false, "fail");
		}
	});
}

function deleteGame(gameID) {
	var pstr = url + gameID;
	setStatusOk(false, pstr);

	$.ajax({
		url: pstr,
		data: "",
		processData: false,
		type: "DELETE",
		contentType: 'text/plain', //'application/json',  cross site posting an application/json requires to implement the CORS preflight with OPTIONS, so this is a simple hacky workaround as the leaderboard server doesn't check the content-type
		success: function() {
			setStatusOk(true, "ok");
		},
		error: function() {
			setStatusOk(false, "fail");
		}
	});
}
