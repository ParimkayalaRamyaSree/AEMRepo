$(document).ready(function() {
  
$('body').hide().fadeIn(5000);
         
$('#submit').click(function() {
    var failure = function(err) {
             alert("Unable to retrive data "+err);
   };
  
    //Get the user-defined values
    var fName= $('#fname').val() ; 
    var lName= $('#lname').val() ; 
    var gender= $('#gender').val() ; 

    //Use JQuery AJAX request to post data to a Sling Servlet
    $.ajax({
         type: 'POST',    
         url:'/bin/mySubmitFormServlet',
         data:'firstName='+ fName+'&lastName='+ lName+'&gender='+ gender,
         success: function(msg){
 
           var json = jQuery.parseJSON(msg); 
            var lastName = json.lastname;
            var firstName = json.firstname;
			var mygender = json.mygender;
            $('#frm').val("Details submitted are " + firstName + " " + lastName+ " " + mygender);   
         }
     });
  });
     
}); // end ready