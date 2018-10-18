<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript"
	src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>
<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript">
	$(document).ready(function() {
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button2").hover(function() {
			$(".button2 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button3").hover(function() {
			$(".button3 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	 
</script>



<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$("select").select2();
	});
</script>


<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/lodash@4.17.4/lodash.min.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

th {
	cursor: pointer;
}

.customers { 
			font-family:  Arial, Helvetica, sans-serif; 
			font-size: 11px; 
			border-collapse: collapse; 
			width: 100%; 
		} 
		 
		.customers td, .customers th { 
			/*border: 1px solid #ddd;*/ 
			 
		} 
		 
		.customers tr:nth-child(even){background-color: #f2f2f2;} 
		 
		 .customers tr:nth-child(odd){background-color: #ffffff;} 
		 
		.customers tr:hover {background-color: #ddd;} 
		 
		.customers th { 
			
			text-align: center; 
			background-color: #80CB9B; 
			color: #222222; 
}

.panel, .flip {
    padding: 5px;
    text-align: center;
    
    border: solid 1px #c3c3c3;
    width: 100%;
}

.panel{
	background-color: #ffffff;
	width: 200px;
}

.flip{
	background-color: #80CB9B;
}

.panel1{
	background-color: #ffffff;
}



.panel1, .flip1 {
    padding: 5px;
    text-align: center;
    border: solid 1px #c3c3c3;
    width: 100%;
}

.flip1{
	background-color: #bdc3c7;
	width: 400px;
	text-align: left;
}

.important{
	color:red;
	font-weight: bold;
}


.flip:hover {
  background-color: white;
  cursor: pointer;
}

.flip1:hover {
  background-color: white;
  cursor: pointer;
}



.panel {
    padding: 0px;
    display: none;
   	width: 99%;
}

.flip{
	
	width: 200px;
}


.panel1 {
    padding: 0px;
    display: none;
   	width: 99%;
}

.custom{
	font-family:  Arial, Helvetica, sans-serif; 
	font-size: 11px;
	font-weight: bold;
}


.active{
	background-color : #80CB9B
} 

</style>

<script type="text/javascript">

	function _isNullOrNumeric(e){
		console.log(e);
		if(e == null) return 0;
		if(e == "") return 0;
		if (e == NaN) return 0;
		if(e == Infinity) return 0;
		return parseFloat(e.replace(/,/g, ""));
	}
	
	function DinhDangNSoLe(e, n){
		const v = _isNullOrNumeric($(e).val());
		n = parseInt(n);
		$(e).val(v.toFixed(n).replace(/\d(?=(\d{3})+\.)/g, '$&,'))
	}
	
	function DinhDangN(e, n){
		const v = e;
		n = parseInt(n);
		return v.toFixed(n).replace(/\d(?=(\d{3})+\.)/g, '$&,');
	}
	
	function isNullOrEmpty(s){
		 if(s == null) return 0;
		 if(s == "" ) return 0;
		 return s;
	 }
	
	 function increaseDate(date, inc){
		 var a = new Date(date);
		 inc = parseInt(isNullOrEmpty(inc));
		 var b = new Date(a.getTime() + inc * 24 * 60 * 60 * 1000);
		 return b.toISOString().split('T')[0];
		 
	 }
</script>


