$(document).ready(function() {
    
    $('body').hide().fadeIn(5000);
           
$('#submit').click(function() {
    var failure = function(err) {
             alert("Unable to retrive data "+err);
   };

  
   var claimId = 0; 
    
    //Use JQuery AJAX request to post data to a Sling Servlet
    $.ajax({
         type: 'GET',    
        url:'/content/wknd/en/testservletnode',
         success: function(msg){
   
           var myMsg = msg; 
  
  
            alert(myMsg); 

         }
     });
  });
       
}); // end ready
