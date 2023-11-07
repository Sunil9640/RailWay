function togglebar ()  {
	
	if($('.sidebar').is(":visible"))
	{
		
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
		
		
		
	}else{
		
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
		
		
	}
	
	
};


const PaymentMethod = () =>{
	console.log("payment started");

let amount=$("#donate_money").val();
 console.log(amount);

 if(amount==""|| amount==null){
	alert("required minimum 1 Rupee to process")
	return amount;
 }

};



$.ajax(
	{
	URL:'/user/process_order',
	data:JSON.stringify({amount:amount,info:'order_request'}),
	contentType: "application/json",
	type:'POST',
	dataType:'json',
	success:function(response){
           // invoked when it's is successed

		console.log(response);

	},

	error:function(error){
		// invoked when its is failed
		console.log(error);
		alert("something went wrong");
	}



 });


