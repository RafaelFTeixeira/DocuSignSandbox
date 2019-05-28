
function scroll_to_class(chosen_class) {
	var nav_height = $('nav').outerHeight();
	var scroll_to = $(chosen_class).offset().top - nav_height;

	if($(window).scrollTop() != scroll_to) {
		$('html, body').stop().animate({scrollTop: scroll_to}, 1000);
	}
}

function sendForm(){
	$.ajax({
		url: "http://localhost:8080",
		type: "POST",
		crossDomain: true,
		data: $("#form").serializeArray(),
		dataType: "json",
		headers: {
			"accept": "application/json",
			"Access-Control-Allow-Origin":"*"
		},
		success: function (response) {
			console.log(response.status);
			if(response.status == "sent")
				alert("Proposta gerada!");
			$("#btnSubmit").prop("disabled", true);
		},
		error: function (xhr, status) {
			alert("error");
		}
	});
}

$("#form").submit((e) => {
	e.preventDefault();
	sendForm();
});

jQuery(document).ready(function() {

	/*
	    Fullscreen background
	*/
	/*$.backstretch("assets/img/backgrounds/1.jpg");*/

	/*
	    Multi Step Form
	*/
	$('.msf-form form fieldset:first-child').fadeIn('slow');
	
	// next step
	$('.msf-form form .btn-next').on('click', function() {
		$(this).parents('fieldset').fadeOut(400, function() {
	    	$(this).next().fadeIn();
			scroll_to_class('.msf-form');
	    });
	});
	
	// previous step
	$('.msf-form form .btn-previous').on('click', function() {
		$(this).parents('fieldset').fadeOut(400, function() {
			$(this).prev().fadeIn();
			scroll_to_class('.msf-form');
		});
	});
	
	
});
