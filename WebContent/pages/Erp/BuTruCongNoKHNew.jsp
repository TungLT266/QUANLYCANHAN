<%@page import="geso.traphaco.erp.beans.butrucongno.IButrucongno"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String)session.getAttribute("userId");
String userTen=(String)session.getAttribute("userTen");

   IButrucongno nvgnBean = (IButrucongno)session.getAttribute("nvgnBean");
   ResultSet hoadonList = nvgnBean.getHoadonList() ;
   ResultSet khRs = nvgnBean.getKhList();
   ResultSet tienteList = nvgnBean.getTienteList();
   ResultSet KHChuyenNoList =  nvgnBean.getKHChuyenNoList();
   ResultSet KHNhanNoList = nvgnBean.getKHNhanNoList();
      
   String[] hdId = nvgnBean.getHdId();
   String[] hdNgayhd = nvgnBean.getHdNgayhd();
   String[] hdLoai = nvgnBean.getHdLoai();
   String[] hdSohd = nvgnBean.getHdSohd();
   String[] hdSotiengoc = nvgnBean.getHdSotiengoc();
   String[] hdSotienphaixoa= nvgnBean.getHdSotienphaixoa();
   String[] hdSotienxoa= nvgnBean.getHdSotienxoa();
   String[] hdSotienconlai= nvgnBean.getHdSotienconlai();
   String[] hdChon = nvgnBean.getHdChon();
   String[] hdTigia = nvgnBean.getHdTigia();
   
   NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Bù trừ công nợ</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: 400px; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {re
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="javascript" type="text/javascript">
	function replaces()
	{
		console.log("********!!!!!!!!!!!************");
		var tenKHChuyenNo = document.getElementById("tenKHChuyenNo");
		var khchuyennoId = document.getElementById("khchuyennoId");
		var isNPPChuyenNo = document.getElementById("isNPPChuyenNo");
		var isCommit = false;
		if (tenKHChuyenNo.value != "" )
		{
			var sp = tenKHChuyenNo.value;
			var pos = parseInt(sp.indexOf("--"));
			if(pos > 0)
			{
				isCommit = true;
				var idt = sp.substring(0, pos);
				var post = parseInt(sp.indexOf(","));
				
				isNPPChuyenNo.value = idt.substring(0, post);
				khchuyennoId.value = idt.substring(post + 1, idt.length);
				
				tenKHChuyenNo.value = sp.substring(pos + 2, sp.length);
			}
		}
		else
		{
			khchuyennoId.value = "";
			tenKHChuyenNo.value = "";
		}
		
		var tenKHNhanNo = document.getElementById("tenKHNhanNo");
		var khnhannoId = document.getElementById("khnhannoId");
		var isNPPNhanNo = document.getElementById("isNPPNhanNo");
		
		if (tenKHNhanNo.value != "" )
		{
			var sp = tenKHNhanNo.value;
			var pos = parseInt(sp.indexOf("--"));
			if(pos > 0)
			{
				var idt = sp.substring(0, pos);
				var post = parseInt(sp.indexOf(","));
				
				isNPPNhanNo.value = idt.substring(0, post);
				khnhannoId.value = idt.substring(post + 1, idt.length);
				
				tenKHNhanNo.value = sp.substring(pos + 2, sp.length);
			}
		}
		else
		{
			khnhannoId.value = "";
			tenKHNhanNo.value = "";
		}
		
		setTimeout(replaces, 500);
		if (isCommit == true)
			submitform();
	}
	
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	}
	
	function searchform()
	{
		document.nvgnForm.action.value= 'search';
		document.forms['nvgnForm'].submit();
	}
	
	function submitform()
	{
		if (document.forms["nvgnForm"]["khchuyennoId"].value.length == 0) {
			setErrors("Vui lòng chọn khách hàng chuyển nợ!" + document.forms["nvgnForm"]["khchuyennoId"].value);					
			return;
		}
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("dataerror");
		errors.value = errorMsg;
	}
	
	function saveform()
	{	
		if (document.forms["nvgnForm"]["ngaychungtu"].value.length == 0) {
			setErrors("Vui lòng chọn ngày chứng từ!");					
			return;
		}
		
		if (document.forms["nvgnForm"]["khnhannoId"].value.length == 0) {
			setErrors("Vui lòng chọn khách hàng nhận nợ!");					
			return;
		}
		
		if (document.forms["nvgnForm"]["khchuyennoId"].value.length == 0) {
			setErrors("Vui lòng chọn khách hàng chuyển nợ!");					
			return;
		}
		
		if (document.forms["nvgnForm"]["sochungtu"].value.length == 0) {
			setErrors("Vui lòng nhập số chứng từ!");					
			return;
		}
		
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms['nvgnForm'].action.value='save';
	     document.forms['nvgnForm'].submit();
	}
	
	function keypress(e) //Hàm dùng ngăn người dùng nhập ký tự khác ký tự số vào Textbox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
		{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function CheckAll()//hàm check hết 
	{
		var selectAll = document.getElementById("selectAll");		
		var hdId = document.getElementsByName("hdId");		
		var hdduno = document.getElementsByName("hdduno");
		var hdghico1 = document.getElementsByName("hdghico1"); 
		
		var hdduco = document.getElementsByName("hdduco");
		var hdghino1 = document.getElementsByName("hdghino1");
		
		var ckKh = document.getElementById("ckKh");
		var khId = document.getElementsByName("khId");
		
		var tc_gn = 0;
		var tc_gc = 0;
		
		if(selectAll.checked)//khi check hết
		{
			if(!ckKh.checked)
			{
				for(i = 0; i < hdId.length; i++)
				{	
					document.getElementById(hdId.item(i).value).checked= true;								
					hdghico1.item(i).value = hdduno.item(i).value;
					hdghino1.item(i).value=hdduco.item(i).value;
					hdghino1.item(i).readOnly=false;
					hdghico1.item(i).readOnly=false;
					if(hdduco.item(i).value!='')
						tc_gn += parseFloat(hdduco.item(i).value.replace(/,/g, "") );
					if(hdduno.item(i).value!='')
						tc_gc +=parseFloat(hdduno.item(i).value.replace(/,/g, ""));
				}
			}
			
			if(ckKh.checked)
			{
				for(i = 0; i < khId.length; i++)
				{	
					document.getElementById(khId.item(i).value).checked= true;								
					hdghico1.item(i).value = hdduno.item(i).value;
					hdghino1.item(i).value=hdduco.item(i).value;
					hdghino1.item(i).readOnly=false;
					hdghico1.item(i).readOnly=false;
					if(hdduco.item(i).value!='')
						tc_gn += parseFloat(hdduco.item(i).value.replace(/,/g, "") );
					if(hdduno.item(i).value!='')
						tc_gc +=parseFloat(hdduno.item(i).value.replace(/,/g, ""));
				}
			}
			
			document.getElementById("tc_gn").value=DinhDangTien(tc_gn);
			document.getElementById("tc_gc").value=DinhDangTien(tc_gc);
		}
		else //khi bỏ check
		{
			if(ckKh.checked)
			{
				for(i = 0; i < khId.length; i++)
				{	
					document.getElementById(khId.item(i).value).checked = false;				
					hdghico1.item(i).value ="";
					hdghino1.item(i).value="";
					hdghino1.item(i).readOnly=true;
					hdghico1.item(i).readOnly=true;
				}
			}
			else
			{
				for(i = 0; i < hdId.length; i++)
				{	
					document.getElementById(hdId.item(i).value).checked = false;				
					hdghico1.item(i).value ="";
					hdghino1.item(i).value="";
					hdghino1.item(i).readOnly=true;
					hdghico1.item(i).readOnly=true;
				}
			}
			document.getElementById("tc_gn").value=DinhDangTien(tc_gn);
			document.getElementById("tc_gc").value=DinhDangTien(tc_gc);
		}
	}
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("selectAll");
		selectAll.checked = false;
	}
	
	function Change_NO_CO(stt)  //khi check từng cái
	{		
		var hdId = document.getElementsByName("hdId");
		var hdloai = document.getElementsByName("hdloai");
		
		var trahet = document.getElementsByName("chon");	
		
		var avat = document.getElementsByName("hdsotienphaixoano");		
		var thanhtoan = document.getElementsByName("hdsotienxoa");		
		var conlai = document.getElementsByName("hdsotienconlai");
		
		var tienteId = document.getElementsByName("tienteId");
		//var tigia = document.getElementsByName("tigia");
		//var tg = tigia.item(0).value.replace(/,/g,"");
				
		var tongtienVND = 0;
		var tongtienNT = 0;
		
// VÒNG LẶP CHO TẤT CẢ HÓA ĐƠN
	 	for(i = 0; i < trahet.length; i++)
	 	{
			if(trahet.item(i).checked ) // CHỌN TRẢ HẾT HÓA ĐƠN
			{
// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN				
				if(stt == 100){ 
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
					}else{
						tt = 0;
					}												

// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN + THANH TOAN HOA DON TIEN VNĐ					
// CHỈ ĐƯỢC THANH TOÁN TỐI ĐA BẰNG SỐ TIỀN HÓA ĐƠN
					if(tienteId.item(0).value == "100000"){ 
						if( Math.abs(parseFloat(avat.item(i).value.replace(/,/g,""))) - Math.abs(parseFloat(tt)) > 0){
							conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
				
							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
							trahet.item(i).checked = false;
						
						}else{ 
							thanhtoan.item(i).value = avat.item(i).value;
							conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
						
							trahet.item(i).checked = true;
						}

// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN + THANH TOAN HOA DON NGOẠI TỆ						
// CHỈ ĐƯỢC THANH TOÁN TỐI ĐA BẰNG SỐ TIỀN HÓA ĐƠN
					}else{ 
						var sotienNT = document.getElementsByName("sotienNT");
						if(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt) > 0){
							conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));

							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tigiaHD.item(i).value.replace(/,/g,""));
									
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
							trahet.item(i).checked = false;
						
						}else{
							thanhtoan.item(i).value = sotienNT.item(i).value;
							conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");
							
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
							
							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tigiaHD.item(i).value.replace(/,/g,""));
							
							trahet.item(i).checked = true;
						}						
					}
// CHỌN TRẢ HẾT HÓA ĐƠN + KHÔNG THAY ĐỔI SỐ TIỀN 
				}else{ 
// CHỌN TRẢ HẾT HÓA ĐƠN + KHÔNG THAY ĐỔI SỐ TIỀN
// THANH TOÁN CHO HÓA ĐƠN VNĐ
					if(tienteId.item(0).value == "100000"){ 
						//alert("Da vao day r");
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value)),0);
					
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					}else{
						var sotienNT = document.getElementsByName("sotienNT");
						thanhtoan.item(i).value = sotienNT.item(i).value;
						conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
					
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						
						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);							
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tigiaHD.item(i).value.replace(/,/g,""));
					}					
				}
				
// CLICK BỎ CHỌN TRẢ HẾT HÓA ĐƠN
			}else if(i == stt){ 
				thanhtoan.item(i).value = "0";				
				
				if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
					conlai.item(i).value = avat.item(i).value;
				}else{
					sotienNT = document.getElementsByName("sotienNT");
					conlai.item(i).value = sotienNT.item(i).value;
				}

// KHÔNG CHỌN TRẢ HẾT HÓA ĐƠN  				
			}else{ 
				var tt;
				if(thanhtoan.item(i).value != ''){
					tt = thanhtoan.item(i).value.replace(/,/g,"");
				}else{
					tt = 0;
				}					
				
// KHÔNG CHỌN TRẢ HẾT HÓA ĐƠN + THANH TOÁN HÓA ĐƠN VNĐ				
				if(tienteId.item(0).value == "100000"){
					if( Math.abs(parseFloat(avat.item(i).value.replace(/,/g,""))) - Math.abs(parseFloat(tt)) > 0){
						conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));
				
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);

						thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
					}else{
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
						trahet.item(i).checked = true;
					}

// KHÔNG CHỌN TRẢ HẾT HÓA ĐƠN + THANH TOÁN HÓA ĐƠN NGOẠI TỆ						
				}else{ 
					var sotienNT = document.getElementsByName("sotienNT");

					if(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt) >= 0){
						conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));
				
						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tigiaHD.item(i).value.replace(/,/g,""));
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
					}else{
						thanhtoan.item(i).value = sotienNT.item(i).value;
						conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						
						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tigiaHD.item(i).value.replace(/,/g,""));
						
						trahet.item(i).checked = true;
					}
				}				
			}
	 	}
	 	
// THANH TOÁN CHO HÓA ĐƠN VNĐ	 	
	 	if(tienteId.item(0).value == "100000"){ 

			 document.getElementById("tongchuyenno").value = DinhDangTien(roundNumber(tongtienVND, 0));
			 document.getElementById("tongnhanno").value = DinhDangTien(roundNumber(tongtienVND, 0));

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ	 	
	 	}else{ 
	 		
			var tongttVND = 0;
	 		var tongttNT = 0;
	 		
	 		document.getElementById("sotienHDNT").value = DinhDangDonGia((tongtienNT).toFixed(2));
	 		document.getElementById("sotienHDVND").value = DinhDangTien(tongtienVND, 0);
			
 			document.getElementById("tigia").value = DinhDangTien(document.getElementById("tigia").value);
	 	}
	}
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' || element.value== '0')
		{
			element.value= '';
		}
	}
	
	function ThanhToan(stt)
	{
		var hdId = document.getElementsByName("hdId");
		var chon = "";
		var phaixoano = document.getElementsByName("hdsotienphaixoano").item(stt);
		var xoano = document.getElementsByName("hdsotienxoa").item(stt);
		var conlai = document.getElementsByName("hdsotienconlai").item(stt);
		var tongchuyeno = document.getElementById("tongchuyenno");
		var tongnhanno = document.getElementById("tongnhanno");
		var hdphaixoano = 0;
		var hdconlai = 0;
		var hdxoano = 0;
		var totaltongchuyeno = 0;
		
		for(i =0; i< hdId.length; i++){
			
			chon = document.getElementById(hdId.item(i).value);
			hdphaixoano = parseFloat(phaixoano.item(i).value.replace(/,/g, ""));
			hdxoano = parseFloat(xoano.item(i).value.replace(/,/g, ""));
			
			if(hdxoano>hdphaixoano){
				hdxoano = hdphaixoano;
			}
			
			if(chon.checked)
	 		{
				hdxoano =  hdphaixoano;
			}
			else{
				hdxoano = 0;
			}
			
			hdconlai = hdphaixoano - hdxoano;
			totaltongchuyeno = parseFloat(tongchuyeno.value.replace(/,/g, "")) + hdxoano;
		}
		
		/* if(chon.checked)
			{ 
		hdphaixoano = parseFloat(phaixoano.item(stt).value.replace(/,/g, ""));
		hdxoano = parseFloat(xoano.item(stt).value.replace(/,/g, ""));
		
		if(hdxoano>hdphaixoano){
			hdxoano = hdphaixoano;
		}
		hdconlai = hdphaixoano - hdxoano;
		totaltongchuyeno += hdxoano;
			 }  */
		
		document.getElementById("tongchuyenno").value = DinhDangTien(totaltongchuyeno);
		document.getElementById("tongnhanno").value = DinhDangTien(totaltongchuyeno);
	}
	
	function goBack()
	{
	  	window.history.back();
	}
</SCRIPT>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nvgnForm" method="post" action="../../ButrucongnoUpdateSvl" >
<input type="hidden" name="nppId" id= "nppId" value='<%= nvgnBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<input name="userTen" type="hidden" value='<%= userTen %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý công nợ &gt; Công nợ phải thu &gt; Bù trừ công nợ KH &gt; Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= userTen %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href = "javascript: goBack();" >		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()">
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
        	</label>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" id="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold;" style="width: 100%" readonly="readonly" rows="1"><%= nvgnBean.getMessage() %></textarea>
		         <% nvgnBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Khách hàng chuyển nợ </legend>       	
        <div style="float:none; width:100%" align="left">
            <table width="100%" cellspacing="0" cellpadding="6px">                
                <TR>
                    <TD class="plainlabel">Ngày</TD>
                    <TD  class="plainlabel" colspan="3">
                        <INPUT type="text" name="ngaychungtu"  class = "days" size="60" value="<%= nvgnBean.getNgaychungtu()%>">
                    </TD>
				</TR>
				
				<TR>
                     <TD class="plainlabel">Số chứng từ</TD>
                     <TD  class="plainlabel" colspan="3">
                        <INPUT type="text" name="sochungtu"  value="<%= nvgnBean.getSochungtu() %>"></TD>
                </TR>
                <TR>
                     <TD class="plainlabel">Diễn giải chứng từ</TD>
                     <TD  class="plainlabel" colspan="3">
                        <INPUT type="text" name="dienGiaiCT" id="dienGiaiCT"  value="<%= nvgnBean.getDienGiaiCT() %>"></TD>
                </TR>
                
                <TR>
					<TD class="plainlabel">Tiền tệ</TD>
					<TD class="plainlabel" style = "width:100px" colspan = "3">

        				<SELECT name="tienteId" id="tienteId" style = "width:200px" onChange = "submitform();">
   						<option value = "" >&nbsp;</option>
   					<%
   					if(tienteList != null)
   					{
   						try
   						{
   							while(tienteList.next())
   							{  
   								if( tienteList.getString("pk_seq").equals(nvgnBean.getTienteId())){ %>
   							<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
   								<%}else { %>
   							<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
   		 						<%} 
   							} 
   							tienteList.close();
   						} 
   						catch(java.sql.SQLException ex){}
   					}
   					%>
   					</SELECT>
						</TD> 
				</TR>
										
                 <TR>
					<TD class="plainlabel">Khách hàng chuyển nợ</TD>
					<TD class="plainlabel" style = "width:100px" >
						<input type="text" name="tenKHChuyenNo" id="tenKHChuyenNo" value='<%= nvgnBean.getTenKHChuyenNo() %>' style="width:400px;"/>
						<input type="hidden" name="khchuyennoId" id="khchuyennoId"  value='<%= nvgnBean.getKHChuyenNoId()%>'/>
						<input type="hidden" name="isNPPChuyenNo" id="isNPPChuyenNo"  value='<%= nvgnBean.getIsNPPChuyenNo()%>'/>
						<script type="text/javascript">
							jQuery(function()
							{		
								$("#tenKHChuyenNo").autocomplete("KhachHang_BTCN.jsp");
							});	
						</script>
						
					</TD>
					
					<TD class="plainlabel" valign="middle">Tổng </TD> 
                    <TD class="plainlabel" valign="middle">
						<input type="text" id="tongchuyenno" name="tongchuyenno" value="<%=nvgnBean.getTongchuyenno() %>"  readonly="readonly" > 													 
					</TD>	
									 
				</TR>
				
             </table>
             </div>
             <div id="xoanokh" align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
	             <table width="100%" border="0" cellspacing="1" cellpadding="1">
	                    <tr class="tbheader">	                    	
	                    	<th align="center" width="5%">STT</th>
							<th align="center" width="12%">Loại hóa đơn</th>
							<th align="center" width="10%">Ngày hóa đơn</th>
							<th align="center" width="10%">Số hóa đơn</th>
							<th align="center" width="13%">Số tiền gốc</th>
							<th align="center" width="13%">Phải xóa nợ</th>
							<th align="center" width="13%">Xóa nợ</th>
							<th align="center" width="13%">Còn lại</th>	                       
	                        <th style="width:5% " align="center" >Chọn</th>	                       
  	                    </tr>
	              
	                    <%
	                    	int n = 0;
	                    	int stt = 1;
	                    	if(hdId != null )
	                    	{	
	                    		int k =  hdId.length;
								for(int i = 0; i < hdId.length ; i++){ 	
									
									String loaihd = "";
										if(hdLoai[i].equals("0")){ 
											loaihd= "Hóa đơn tài chính";
										}else if(hdLoai[i].equals("1")){
											loaihd= "Hóa đơn khác";
										}else if(hdLoai[i].equals("2")){
											loaihd = "Giảm/Tăng giá hàng bán";
										}else if(hdLoai[i].equals("3")){
											loaihd =  "Khách hàng trả trước";
										}else if(hdLoai[i].equals("7")){
											loaihd = "Hóa đơn hàng trả về";
										}										
										else loaihd = "";
						%>
						<TR>
							<TD align="center">
								<input type="text" readonly="readonly" name="STT" style="width: 100%" value="<%= stt%>" > 						
							</TD>							
							<TD align="center">
								<input type="hidden" readonly="readonly" name="hdId" style="width: 100%; text-align: right;" value="<%= hdId[i]%>" > 
								<input type="hidden" readonly="readonly" name="hdloai" style="width: 100%; text-align: right;" value="<%= hdLoai[i] %>" >
								<input type="hidden" readonly="readonly" name="hdtigia" style="width: 100%; text-align: right;" value="<%= hdTigia[i]%>" > 	 	
								<input type="text" readonly="readonly" name="hdten" style="width: 100%; text-align: right;" value="<%= loaihd %>" > 						
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdNgay" style="width: 100%; " value="<%= hdNgayhd[i]%>" > 
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsohoadon" style="width: 100%; " value="<%= hdSohd[i] %>" > 
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsotiengoc" style="width: 100%; text-align: right;" value="<%= formatter.format( Double.parseDouble(hdSotiengoc[i]))  %> " >
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsotienphaixoano" style="width: 100%; text-align: right;" value="<%= formatter.format( Double.parseDouble(hdSotienphaixoa[i])) %> " >
							</TD>																												
							<TD align="center">
								<input type="text"  name="hdsotienxoa" style="width: 100%; text-align: right;" value="<%=formatter.format( Double.parseDouble( hdSotienxoa[i]))  %> " onChange="Change_NO_CO(100);" onkeypress="return keypress(event);" > 
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsotienconlai" style="width: 100%; text-align: right;" value="<%= formatter.format( Double.parseDouble(hdSotienconlai[i])) %>" > 
							</TD>
							<TD align="center">
							<% if (hdChon[i].equals(hdId[i] )){ %>
								<input name="chon" id="chon"<%=stt %>  value="<%= hdId[i]%><%= hdLoai[i]%>" type="checkbox"  checked  onChange="Change_NO_CO( <%= i  %> );" >
							<%}else{ %>											
								<input name="chon" id="chon"<%=stt %> type="checkbox" value="<%= hdId[i]%><%= hdLoai[i]%>"  onChange="Change_NO_CO( <%= i  %> );" >
							<%} %>
						</TD> 
	                    </TR> <%
	                    stt++; }} %>
	             </table> 
             </div>  
          </fieldset>	
          
          <FIELDSET>
			<LEGEND class="legendtitle" style="color: black">Khách hàng nhận nợ</LEGEND>       
              <table width="100%" cellspacing="0" cellpadding="6px">
             	<TR>
					<TD class="plainlabel">Khách hàng nhận nợ</TD>
					<TD class="plainlabel" style = "width:100px">
						
						<input type="text" name="tenKHNhanNo" id="tenKHNhanNo" value="<%= nvgnBean.getTenKHNhanNo() %>" style="width:400px;"/>
						<input type="hidden" name="khnhannoId" id="khnhannoId" value='<%= nvgnBean.getKHNhanNoId()%>'/>
						<input type="hidden" name="isNPPNhanNo" id="isNPPNhanNo" value='<%= nvgnBean.getIsNPPNhanNo()%>'/>
						<script type="text/javascript">
							jQuery(function()
							{		
								$("#tenKHNhanNo").autocomplete("KhachHang_BTCN.jsp");
							});	
						</script>
						
					</TD>
					
					<TD class="plainlabel" valign="middle">Tổng </TD> 
                    <TD class="plainlabel" valign="middle">
						<input type="text" id="tongnhanno" name="tongnhanno" value="<%=nvgnBean.getTongnhanno() %>"  readonly="readonly" > 													 
					</TD>
					 
				</TR>
             </table>   
    </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">
	replaces();
</script>

</body>
</html>