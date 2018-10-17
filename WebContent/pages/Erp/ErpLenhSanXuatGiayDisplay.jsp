 <%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.lenhsanxuatgiay.*"%>
<%@ page import="geso.traphaco.erp.beans.lenhsanxuatgiay.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@page import="geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP"%>

<%
	IErpLenhsanxuat lsxBean = (IErpLenhsanxuat) session.getAttribute("lsxBean");
%>
<%
	ResultSet spRs = lsxBean.getSpRs();
%>
<%
	ResultSet kbsxRs = lsxBean.getKbsxRs();
%>
<%
	ResultSet nhamayRs = lsxBean.getNhamayRs();
%>
<%
	ResultSet rsdvkd = lsxBean.getRsDvkd();
%>
<%
	ResultSet rskho = lsxBean.getRsKhoSX();
%>
  
<%
	List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>) lsxBean.getListDanhMuc();
%>
<%
	String tensp = "";
	String PK_SEQ_SP = "";
	boolean uutien = false;
	NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
%>

<%
	String userId = (String) session.getAttribute("userId");
%>
<%
	String userTen = (String) session.getAttribute("userTen");
	System.out.println("");
	List<ILenhSXCongDoan> ListLsxcongdoan = lsxBean.getListCongDoan();
	
	ResultSet rscongdoan = lsxBean.getRsCongDoan();
	List<IErpBom> listbom = lsxBean.getBomList();
	System.out.println("Lôi : ");
	NumberFormat formatter = new DecimalFormat("#,###,###.######");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript"
	src="../scripts/ajax_erplenhsanxuat_giay.js"></script>
<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});   
		$(".titleTab a").click(function () { 

	        var activeTab = $(this).attr("href"); 
				//var activeTab =
	        $(".titleTab a").removeClass("active"); 
	        $(this).addClass("active");
	        $(".tabContents").hide();
	        $(activeTab).fadeIn();

	    });
		
	});
	
</script>
<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
        $(document).ready(function() { $("#spid").select2(); });
      
    </script>


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
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

table[name="table_noidungnhap"] {
	margin-top: 0px;
}

input[name^="_soluong"] {
	width: 70px;
	text-align: right;
}
</style>


<script language="javascript" type="text/javascript">

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	function Replace_spsx(){
		var spsxidsp=document.getElementsByName("spsxidsp");
		var spsxmasp = document.getElementsByName("spsxmasp");
		var spsxtensp = document.getElementsByName("spsxtensp");
		var spsxkhorong = document.getElementsByName("spsxkhorong");  
		var spsxdonvi= document.getElementsByName("spsxdonvi");  
		var spsxtrongluongqd= document.getElementsByName("spsxtrongluongqd");  
		for(i=0; i < spsxmasp.length; i++)
		{
			if(spsxmasp.item(i).value != "")
			{
				var vt = spsxmasp.item(i).value;
				var pos = parseInt(vt.indexOf("--"));
				if(pos > 0)
				{
					spsxmasp.item(i).value = vt.substring(0, pos);
					vt = vt.substr(parseInt(vt.indexOf("--")) + 3);
					spsxtensp.item(i).value = vt.substring(0, parseInt(vt.indexOf(" [")));
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					spsxdonvi.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					 
					 vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					 spsxidsp.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					 
					 vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					 spsxkhorong.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					 vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					 spsxtrongluongqd.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
						
					 ajaxOption("spsxbomid"+ i,spsxidsp.item(i).value, spsxtrongluongqd.item(i).value);
					 
					 
				}	
			}else{
				 spsxtensp.item(i).value ="";
				 spsxdonvi.item(i).value ="";
				 spsxidsp.item(i).value ="";
				 spsxkhorong.item(i).value ="";
				 spsxtrongluongqd.item(i).value ="";
			}
		}
	}
	function ajaxOption(id, str,trongluong)
	{
		var xmlhttp;
		if (str == "")
		{
		   document.getElementById(id).innerHTML = "";
		   return;
		}
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
		      document.getElementById(id).innerHTML = xmlhttp.responseText;
		   }
		}
		xmlhttp.open("POST","../../LenhsanxuatAjaxSvl?spid=" + str + "&id=" + id+"&trongluong="+trongluong,true);
		xmlhttp.send();
	}
	function Replace_khachhang()
	{
 
		var tenkh=document.getElementsByName("tenkhachhang");
		var idkh = document.getElementsByName("idkhachhang");
		 
		for(i=0; i <tenkh.length; i++)
		{
			if(tenkh.item(i).value != "")
			{
				if(tenkh.item(i).value.indexOf(" -- ") > 0 )
				{
					var str = tenkh.item(i).value;
					idkh.item(i).value = str.substring(0, tenkh.item(i).value.indexOf(" -- "));
					tenkh.item(i).value  =str.substring(str.indexOf(" -- ") + 4);
				}	
			}
		}
	}
	function replaces()
	{ 
		
		 Replace_spsx();
		 Replace_khachhang();
		var vtId=document.getElementsByName("vattuthemvattuid");
		var mavt = document.getElementsByName("vattuthemvattuma");
		var tenvt = document.getElementsByName("vattuthemvattuten");
		var donvitinhvt = document.getElementsByName("vattuthemdvt");  
		var soluong = document.getElementsByName("vattuthemsoluong");
		var soluongton= document.getElementsByName("vattuthemsoluongton");
		
		var vattuthemkhoid= document.getElementsByName("vattuthemkhoid");
		var vattuthemkhoten= document.getElementsByName("vattuthemkhoten");
			
		var i;
		for(i=0; i < mavt.length; i++)
		{
			if(mavt.item(i).value != "")
			{
				var vt = mavt.item(i).value;
				var pos = parseInt(vt.indexOf("--"));
				if(pos > 0)
				{
					mavt.item(i).value = vt.substring(0, pos);
					vt = vt.substr(parseInt(vt.indexOf("--")) + 3);
					tenvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" [")));
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					donvitinhvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					vtId.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					soluongton.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					vattuthemkhoid.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					vattuthemkhoten.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
					
				}	
				
			}
			else
			{
				vtId.item(i).value = "";
				mavt.item(i).value = "";
				tenvt.item(i).value = "";
				donvitinhvt.item(i).value = "";		
				soluong.item(i).value = "";
				soluongton.item(i).value="";
				vattuthemkhoid.item(i).value = "";
				vattuthemkhoten.item(i).value="";
			}
		}
		setTimeout(replaces, 300);
	}
	function TinhTrongLuong(){
		var soluong = document.getElementsByName("spsxsoluong");
		var idsp = document.getElementsByName("spsxidsp");
		var trongluong = document.getElementsByName("spsxtrongluongqd");
		var tongtrongluong =0; 
		for(i=0; i < idsp.length; i++)
		{
			
			if(soluong.item(i).value!='' ){
				
				
				tongtrongluong=tongtrongluong + parseFloat(soluong.item(i).value) * parseFloat(trongluong.item(i).value);
			}

		}
	}
	
	 function DinhDangTien(num) 
	 {
		 num = num.toString().replace(/\$|\,/g,'');
		 
		 num = (Math.round( num * 1000 ) / 1000).toString();
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100+0.50000000001);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}

	 
	 
	 
	
	 function PrintLsxNangcao(idcd)
	 {
		 document.forms['hctmhForm'].action.value = 'PrintLsxNangcao';
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit(); 
	 }
	 function PrintThongtinSX(idcd)
	 {
		 document.forms['hctmhForm'].action.value = 'PrintThongtinSX';
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit(); 
	 }
	 
	 function KiemTra_CongDoan()
	 {
		 var kiemtra = KiemTra_CongDoanNhapXuat();
	 }
	 
	 function KiemTra_CongDoanNhapXuat(){
		 
		 var kiemtra = true;
		 
		
		 
		 var soCongDoan= document.forms['hctmhForm'].soCongDoan.value  ;//document.getElementsByName("soCongDoan").value;
		 
		 console.log("so cong doan: "+soCongDoan);
		 
		// var soCongDoan = $('input[name = "soCongDoan"]').val();
		 
		 
		 for(var i = 0 ; i < soCongDoan ; i++){
			 var tab = i+1;
			 console.log("Clicked on tab " + tab);
			 
			var name_soluongnhap = document.getElementsByName("_soluongNhap_tab_"+i);  //"input[name = '_soluongNhap_tab_"+i + "']";
			var name_soluongxuat = document.getElementsByName("_soluongXuat_tab_"+i);// "input[name = '_soluongXuat_tab_"+i + "']";
			var name_soluongdupham =document.getElementsByName("_soluongDuPham_tab_"+i);// "input[name = '_soluongDuPham_tab_"+i + "']";
			var name_soluongphepham =document.getElementsByName("_soluongPhePham_tab_"+i);// "input[name = '_soluongPhePham_tab_"+i + "']";
			
			//console.log("SOluongnhap "+name_soluongnhap);
			
		/* 	var soluongNhap_elements = $(name_soluongnhap);
	    	var soluongXuat_elements = $(name_soluongxuat);
	    	var soluongDuPham_elements = $(name_soluongdupham);
	    	var soluongPhePham_elements = $(name_soluongphepham);
	    	 */
	    	var soluongNhap = [];
	    	var soluongXuat = [];
	    	var soluongDuPham = [];
	    	var soluongPhePham = [];
	    	
	    	/* $(soluongNhap_elements).each(function(index){
	    		//console.log("element soluong nhap ->index - value " + index + " - " + $(this).val());
	    		soluongNhap.push($(this).val());
	    	});*/
	    	  for(var index = 0 ; index < name_soluongnhap.length ; index++ ){
	    		soluongNhap.push(name_soluongnhap.item(index).value);
	    		soluongXuat.push(name_soluongxuat.item(index).value);
	    		soluongDuPham.push(name_soluongdupham.item(index).value);
	    		soluongPhePham.push(name_soluongphepham.item(index).value);
	    	}
	    	
	    	
	    	var n = soluongNhap.length;
	    	 console.log("So luong nhap length : "+ n);
	    	 var total_tongnhap=0;
	    	 var total_tongxuat=0;
	    	for(var j = 0 ; j < n ; j++){
	    		var lan = j + 1;
	    		var soluongnhap = parseFloat(soluongNhap[j]);
	    		var soluongxuat = parseFloat(soluongXuat[j]);
	    		var soluongdupham = parseFloat(soluongDuPham[j]);
	    		var soluongphepham = parseFloat(soluongPhePham[j]);
	    		
	    		total_tongnhap+=soluongnhap;
	    		
	    		var tong = soluongxuat + soluongdupham + soluongphepham;
	    		total_tongxuat=total_tongxuat+ tong;
	    		
	    		console.log(" soluongnhap : "+soluongnhap);
	    		console.log(" tong : "+tong);
	    		
	    	}  
	    	
	     	if(total_tongnhap < total_tongxuat){
				kiemtra = false;
				var notify = "Số lượng nhập ở Công đoạn " + tab + " lần " + lan
				+ " bé hơn số lượng xuất + dư phẩm + phế phẩm (" 
				+ soluongnhap + " < " + tong + ")";
				console.log(notify);
				alert(notify);
				
				//set color red for error input 
				for(var j = 0 ; j < n ; j++){
			  		 name_soluongnhap.item(j).style.setProperty('background-color', 'red');
				}				
				break;
				
			} else{
				for(var j = 0 ; j < n ; j++){
				 	 name_soluongnhap.item(j).style.setProperty('background-color', 'white');
				}	
			}   
	    	
	    	
		}
		  return kiemtra;
	 }
	 
	 
	 function TinhTongSoLuong(){
			
			var soluong = document.getElementsByName("spsxsoluong");
			var idsp = document.getElementsByName("spsxidsp");
			var dvkdid =document.forms['hctmhForm'].dvkdid.value;
			var trongluong = document.getElementsByName("spsxtrongluongqd");
			var bomid = document.getElementsByName("spsxbomid"); 
			var tongsoluong=0;
		
			var tongtrongluong =0; 
		
			for(i=0; i < idsp.length; i++)
			{
				
				if(soluong.item(i).value!='' && ( bomid.item(i).value!='' || dvkdid=="100005")){
					
					tongsoluong=tongsoluong+ parseFloat(soluong.item(i).value);
					tongtrongluong=tongtrongluong + parseFloat(soluong.item(i).value) * parseFloat(trongluong.item(i).value);
				}
				
				
			}
		
			 document.forms['hctmhForm'].soluongsx.value = tongsoluong;
			 
			 
			 var soluong1 = document.getElementsByName("lsxcdsoluong");
				for(i=0; i < soluong1.length; i++){
					soluong1.item(i).value=0;
				}
	 }
	 function GanLaiSoluong(){	
		var sl= document.forms['hctmhForm'].soluongsx.value;
			var soluong = document.getElementsByName("spsxsoluong");
				soluong.item(0).value=sl;
				
				var soluong1 = document.getElementsByName("lsxcdsoluong");
				for(i=0; i < soluong1.length; i++){
					soluong1.item(i).value=0;
				}
			
	 }
	 function YeuCauNguyenLieu(){
		 document.forms['hctmhForm'].action.value = 'yeucaunguyenlieu';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
	 }
	 
	 
	 
	 function ChonlaiBOM(){
		 
			document.forms["hctmhForm"].action.value = "ChonlaiBOM";
		    document.forms["hctmhForm"].submit();
	 }
	 
 	 function xoavattuhienthi1(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		  document.getElementById("hienthisanpham").deleteRow(i);
		  document.getElementById("khonghienthisanpham").deleteRow(i);
		 
	 }
 	 function xoavattuhienthi_loi(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		  document.getElementById("vattudenghi").deleteRow(i);
	 }
 	
 	
 	 function xoavattuhienthi_vattuthem(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		 document.getElementById("hienthisanpham").deleteRow(i);
	 }
 	
 	 function xoavattuhienthi2(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		 document.getElementById("bangvattuthieu").deleteRow(i);
	 }
 	 
	 function viewBom1()
	 {
		
		 document.forms['hctmhForm'].action.value = 'kiemtranguyenlieu';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
		 
	 }
	 function Kiemtranguyenlieusauthaythe(){
		 document.forms['hctmhForm'].action.value = 'kiemtranguyenlieusautt';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
	 }
	 function nhansanpham(idcd){
		 document.forms['hctmhForm'].action.value = 'nhapkho';
		 document.forms["hctmhForm"].ctidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit();
	 }
	 function CapNhatSoLuong_1(value,chuoi,stt){
			 
			 
			 var pk_seq1 = document.getElementsByName(chuoi+'.pk_seq1');
			 var tonkho1 = document.getElementsByName(chuoi+".tonkho1");
			 
			 var soluong1 = document.getElementsByName(chuoi+'.soluong1');
			 var chon1 = document.getElementsByName(chuoi+'.chon1');
			 var tong= document.getElementsByName(chuoi+'.tongcong');
			 var sotong=0;
			 var soluongngoai=0;
			 var dadung=0;
				for(i=0; i < chon1.length; i++)
				{
					soluongngoai=tong.item(i).value;
					if(soluong1.item(i).value==""){
						
					}else{
						/* if( parseFloat(soluong1.item(i).value) > parseFloat(tonkho1.item(i).value)){
							soluong1.item(i).value=0;
							alert("Không được nhập vượt quá tồn kho");
							return;
						} */
					 	sotong=  parseFloat(sotong) + parseFloat(soluong1.item(i).value);
					}
				}
				/* if(parseFloat(sotong) >parseFloat(soluongngoai) ) {
					soluong1.item(stt).value=0;
					alert("Không được nhập vượt quá số lượng tổng đề nghị");
					return;
				} */
			 
	 }
	 function CapNhatSoLuong(value,chuoi,stt){
		 
		 
		 var pk_seq1 = document.getElementsByName(chuoi+'.pk_seq1');
		 var tonkho1 = document.getElementsByName(chuoi+".tonkho1");
		 
		 var soluong1 = document.getElementsByName(chuoi+'.soluong1');
		 var chon1 = document.getElementsByName(chuoi+'.chon1');
		 var tong= document.getElementsByName(chuoi+'.tongcong');
		 var sotong=0;
		 var dadung=0;
		 
		 chuoistr='';
		 for(i=0; i < chon1.length; i++)
			{
			  sotong=tong.item(i).value;
			  
			  if(chon1.item(i).checked ==true){
				 chuoistr=chuoistr+','+ chon1.item(i).value;
				 dadung= parseFloat(dadung) + parseFloat(soluong1.item(i).value);
				 
			  }
			  else{
				  soluong1.item(i).value=0;
			  }
			  
			}
	
		var conlai=parseFloat(sotong) -parseFloat(dadung) ;
		for(i=0; i < chon1.length; i++)
		{ 
			 if(chon1.item(i).checked ==true){
				 if(parseFloat(soluong1.item(i).value) ==0){
					if( parseFloat(tonkho1.item(i).value) > conlai){
						soluong1.item(i).value=conlai;
						conlai=0;
						break;
					}else{
						conlai=parseFloat(conlai)-parseFloat(tonkho1.item(i).value);
						soluong1.item(i).value=tonkho1.item(i).value;
					}
				 }
			 }
		}
	 }
	 function Tintong_CongDoanNhapXuat(){
		 
		 var kiemtra = true;
		 
		
		 
		 var soCongDoan= document.forms['hctmhForm'].soCongDoan.value  ;//document.getElementsByName("soCongDoan").value;
		 
		 console.log("so cong doan: "+soCongDoan);
		 
		// var soCongDoan = $('input[name = "soCongDoan"]').val();
		 
		 
		 for(var i = 0 ; i < soCongDoan ; i++){
			 var tab = i+1;
			 console.log("Clicked on tab " + tab);
			 
			var name_soluongnhap = document.getElementsByName("_soluongNhap_tab_"+i);  //"input[name = '_soluongNhap_tab_"+i + "']";
			var name_soluongxuat = document.getElementsByName("_soluongXuat_tab_"+i);// "input[name = '_soluongXuat_tab_"+i + "']";
			var name_soluongdupham =document.getElementsByName("_soluongDuPham_tab_"+i);// "input[name = '_soluongDuPham_tab_"+i + "']";
			var name_soluongphepham =document.getElementsByName("_soluongPhePham_tab_"+i);// "input[name = '_soluongPhePham_tab_"+i + "']";
			var nama_soluongtongdoc =document.getElementsByName("_soluongtongdoc_tab_"+i); 
			//console.log("SOluongnhap "+name_soluongnhap);
	 
	    	var soluongNhap = [];
	    	var soluongXuat = [];
	    	var soluongDuPham = [];
	    	var soluongPhePham = [];
	    	
	    	 
	    	  for(var index = 0 ; index < name_soluongnhap.length ; index++ ){
	    		soluongNhap.push(name_soluongnhap.item(index).value);
	    		soluongXuat.push(name_soluongxuat.item(index).value);
	    		soluongDuPham.push(name_soluongdupham.item(index).value);
	    		soluongPhePham.push(name_soluongphepham.item(index).value);
	    	}
	    	
	    	
	    	var n = soluongNhap.length;
	    	 console.log("So luong nhap length : "+ n);
	    	 var total_tongnhap=0;
	    	 var total_tongxuat=0;
	    	 
	    	 var total_xuat=0;
	    	 var total_chuyendupham=0;
	    	 var total_chuyenphepham=0;
	    	 
	    	for(var j = 0 ; j < n ; j++){
	    		var lan = j + 1;
	    		var soluongnhap = parseFloat(soluongNhap[j]);
	    		var soluongxuat = parseFloat(soluongXuat[j]);
	    		var soluongdupham = parseFloat(soluongDuPham[j]);
	    		var soluongphepham = parseFloat(soluongPhePham[j]);
	    		
	    		total_tongnhap+=soluongnhap;
	    		total_xuat+=soluongxuat;
	    		total_chuyendupham+=soluongdupham;
	    		total_chuyenphepham+=soluongphepham;
	    		
	    		var tong = soluongxuat + soluongdupham + soluongphepham;
	    		total_tongxuat=total_tongxuat+ tong;
	    		
	    		console.log(" soluongnhap : "+soluongnhap);
	    		console.log(" tong : "+tong);
	    		nama_soluongtongdoc.item(j).value=soluongnhap -tong;
	    	}  
	    	
	    	
	    	document.getElementsByName("_totalsoluongNhap_tab_"+i).item(0).value=total_tongnhap;
	    	document.getElementsByName("_totalsoluongXuat_tab_"+i).item(0).value=total_xuat;
	    	document.getElementsByName("_totalsoluongDuPham_tab_"+i).item(0).value=total_chuyendupham;
	    	document.getElementsByName("_totalsoluongPhepham_tab_"+i).item(0).value=total_chuyenphepham;
	    	 
	    	document.getElementsByName("_totalsoluong_tab_"+i).item(0).value=total_tongnhap -total_tongxuat;
	     
	    	if(total_tongnhap < total_tongxuat){
				  
				//set color red for error input 
				for(var j = 0 ; j < n ; j++){
			  		 name_soluongnhap.item(j).style.setProperty('background-color', 'red');
				}				
				break;
				
			} else{
				for(var j = 0 ; j < n ; j++){
				 	 name_soluongnhap.item(j).style.setProperty('background-color', 'white');
				}	
			}   
	    	
	    	
	    	
		}
		 
	 }
	 
	 
	 function KiemTra_CongDoanNhapXuat(){
		 
		 var kiemtra = true;
		 
		
		 
		 var soCongDoan= document.forms['hctmhForm'].soCongDoan.value  ;//document.getElementsByName("soCongDoan").value;
		 
		 console.log("so cong doan: "+soCongDoan);
		 
		// var soCongDoan = $('input[name = "soCongDoan"]').val();
		 
		 
		 for(var i = 0 ; i < soCongDoan ; i++){
			 var tab = i+1;
			 console.log("Clicked on tab " + tab);
			 
			var name_soluongnhap = document.getElementsByName("_soluongNhap_tab_"+i);  //"input[name = '_soluongNhap_tab_"+i + "']";
			var name_soluongxuat = document.getElementsByName("_soluongXuat_tab_"+i);// "input[name = '_soluongXuat_tab_"+i + "']";
			var name_soluongdupham =document.getElementsByName("_soluongDuPham_tab_"+i);// "input[name = '_soluongDuPham_tab_"+i + "']";
			var name_soluongphepham =document.getElementsByName("_soluongPhePham_tab_"+i);// "input[name = '_soluongPhePham_tab_"+i + "']";
			var nama_soluongtongdoc =document.getElementsByName("_soluongtongdoc_tab_"+i); 
			//console.log("SOluongnhap "+name_soluongnhap);
			
		/* 	var soluongNhap_elements = $(name_soluongnhap);
	    	var soluongXuat_elements = $(name_soluongxuat);
	    	var soluongDuPham_elements = $(name_soluongdupham);
	    	var soluongPhePham_elements = $(name_soluongphepham);
	    	 */
	    	var soluongNhap = [];
	    	var soluongXuat = [];
	    	var soluongDuPham = [];
	    	var soluongPhePham = [];
	    	
	    	/* $(soluongNhap_elements).each(function(index){
	    		//console.log("element soluong nhap ->index - value " + index + " - " + $(this).val());
	    		soluongNhap.push($(this).val());
	    	});*/
	    	  for(var index = 0 ; index < name_soluongnhap.length ; index++ ){
	    		soluongNhap.push(name_soluongnhap.item(index).value);
	    		soluongXuat.push(name_soluongxuat.item(index).value);
	    		soluongDuPham.push(name_soluongdupham.item(index).value);
	    		soluongPhePham.push(name_soluongphepham.item(index).value);
	    	}
	    	
	    	
	    	var n = soluongNhap.length;
	    	 console.log("So luong nhap length : "+ n);
	    	 var total_tongnhap=0;
	    	 var total_tongxuat=0;
	    	 
	    	 var total_xuat=0;
	    	 var total_chuyendupham=0;
	    	 var total_chuyenphepham=0;
	    	 
	    	for(var j = 0 ; j < n ; j++){
	    		var lan = j + 1;
	    		var soluongnhap = parseFloat(soluongNhap[j]);
	    		var soluongxuat = parseFloat(soluongXuat[j]);
	    		var soluongdupham = parseFloat(soluongDuPham[j]);
	    		var soluongphepham = parseFloat(soluongPhePham[j]);
	    		
	    		total_tongnhap+=soluongnhap;
	    		total_xuat+=soluongxuat;
	    		total_chuyendupham+=soluongdupham;
	    		total_chuyenphepham+=soluongphepham;
	    		
	    		var tong = soluongxuat + soluongdupham + soluongphepham;
	    		total_tongxuat=total_tongxuat+ tong;
	    		
	    		console.log(" soluongnhap : "+soluongnhap);
	    		console.log(" tong : "+tong);
	    		nama_soluongtongdoc.item(j).value=soluongnhap -tong;
	    	}  
	    	
	    	
	    	document.getElementsByName("_totalsoluongNhap_tab_"+i).item(0).value=total_tongnhap;
	    	document.getElementsByName("_totalsoluongXuat_tab_"+i).item(0).value=total_xuat;
	    	document.getElementsByName("_totalsoluongDuPham_tab_"+i).item(0).value=total_chuyendupham;
	    	document.getElementsByName("_totalsoluongPhepham_tab_"+i).item(0).value=total_chuyenphepham;
	    	
	    	
	    	document.getElementsByName("_totalsoluong_tab_"+i).item(0).value=total_tongnhap -total_tongxuat;
	    	
	    	
	     	if(total_tongnhap < total_tongxuat){
				kiemtra = false;
				var notify = "Số lượng nhập ở Công đoạn " + tab + " lần " + lan
				+ " bé hơn số lượng xuất + dư phẩm + phế phẩm (" 
				+ soluongnhap + " < " + tong + ")";
				console.log(notify);
				alert(notify);
				
				//set color red for error input 
				for(var j = 0 ; j < n ; j++){
			  		 name_soluongnhap.item(j).style.setProperty('background-color', 'red');
				}				
				break;
				
			} else{
				for(var j = 0 ; j < n ; j++){
				 	 name_soluongnhap.item(j).style.setProperty('background-color', 'white');
				}	
			}   
	    	
	    	
		}
		  return kiemtra;
	 }
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="hctmhForm" method="post"
		action="../../ErpLenhsanxuatgiayUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'>  
			<input
			type="hidden" name="id" value='<%=lsxBean.getId()%>'>
			<input
			type="hidden" name="trangthai" value='<%=lsxBean.getTrangthai()%>'>
			<input type="hidden"
			name="viewBom" value='<%=lsxBean.getViewBom()%>'>  
			<input type="hidden"
			name="cdidcurrent" value='<%=lsxBean.getCongDoanCurrent()%>'>
			
			<input type='hidden' name="soCongDoan"
			value='<%=ListLsxcongdoan.size()%>' />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					 
					class="tbnavigation">Quản lý cung ứng &gt; Sản xuất &gt; Lệnh
					sản xuất &gt; Hiển thị
					 
					 
					</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../ErpLenhsanxuatgiaySvl?userId=<%=userId%>"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				 
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="background-color: white; color: red; width: 100%"><%=lsxBean.getMsg()%></textarea>
					<%
						lsxBean.setMsg("");
					%>
				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Lệnh sản xuất </legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<tr class="plainlabel">
								<td>Ghi chú</td>
								<td><input type="text" value="<%=lsxBean.getghichu()%>" name="ghichu"></td>
								
								<td>Số LSX</td>
								<td><input type="text" <%=(lsxBean.getTrangthai().trim().length() <= 0 || lsxBean.getTrangthai().equals("0")) ? "" : "readonly" %> value="<%=lsxBean.getSolsxNew()%>" name="solsxnew"></td>
							</tr>

							<tr class="plainlabel">
								<td>Chọn ngày bắt đầu</td>
								<td colspan="1"><input type="text"
									value="<%=lsxBean.getNgaytao()%>" id="ngaybatdau"
									name="ngaybatdau" <%=(lsxBean.getTrangthai().trim().length() <= 0 || lsxBean.getTrangthai().equals("0")) ? "class=\"days\"" : "readonly" %>></td>
								<td>Chọn ngày kết thúc</td>
								<td colspan="1"><input type="text"
									value="<%=lsxBean.getNgaydukien()%>" id="ngayketthuc"
									name="ngayketthuc" <%=(lsxBean.getTrangthai().trim().length() <= 0 || lsxBean.getTrangthai().equals("0")) ? "class=\"days\"" : "readonly" %>></td>
							</tr>
							<tr class="plainlabel">
								 
								<TD width="14%" class="plainlabel" valign="top">Sản phẩm</TD>
								<TD   class="plainlabel" valign="top"><select
									style="width: 300px"  id="spid" name="spid"
									onchange="submitform()">
										<option value=""></option>
										<%
											if (spRs != null) {
												try {
													while (spRs.next()) {
														if (spRs.getString("pk_seq").equals(lsxBean.getSpId())) {
										%>
										<option value="<%=spRs.getString("pk_seq")%>"
											selected="selected"><%=spRs.getString("ma") + "--" + spRs.getString("ten")%></option>
										<%
										 		 
														} else {
										%>
										<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ma") + "--" + spRs.getString("ten")%></option>
										<%
											}
													}
													spRs.close();
												} catch (SQLException ex) {
												}
											}
										%>
								</select>  
  						</TD>
  						
  									<td>Số mẻ</td>
								<td colspan="1"><input type="text"
									value="<%=lsxBean.getSome()%>" id="soluongme"
									name="soluongme" <%=(lsxBean.getTrangthai().trim().length() <= 0 || lsxBean.getTrangthai().equals("0")) ? "" : "readonly" %> ></td>
									
									
							</tr>
						 

							<TR>
								<TD class="plainlabel" valign="top">Kịch bản sản xuất</TD>
								<TD colspan="1" class="plainlabel" valign="top"><select
									style="width: 300px" name="kbsxIds" onchange="changeBOM();">
										<option value=""></option>
										<%
											if (kbsxRs != null) {
												try {
													while (kbsxRs.next()) {
														if (kbsxRs.getString("pk_seq").equals(lsxBean.getKbsxId())) {
										%>
										<option value="<%=kbsxRs.getString("pk_seq")%>"
											selected="selected"><%=kbsxRs.getString("diengiai")%></option>
										<%
											} else {
										%>
										<option value="<%=kbsxRs.getString("pk_seq")%>"><%=kbsxRs.getString("diengiai")%></option>
										<%
											}
													}
													kbsxRs.close();
												} catch (SQLException ex) {
												}
											}
										%>
								</select></TD>
								 
								 

								
								<TD colspan="4" class="plainlabel" valign="top" >
									<a class="button"
										href=" javascript:PrintThongtinSX(<%=lsxBean.getCongDoanCurrent()%>)">
										<img style="top: 2px !important; left: 3px" src="../images/Printer20.png"
										alt="">In Thông Tin Chung SX </a>
								</TD>

							</TR>

							<TR>
								<TD class="plainlabel" valign="top">Số lượng sản xuất</TD>
								<TD class="plainlabel" valign="top"><input type="hidden"
									name="kichbanspid" id="kichbanspid"
									value="<%=lsxBean.getSpId()%>" /> <input type="text"
									id="soluongsx" name="soluongsx"
									value="<%=lsxBean.getSoluong()%>" style="text-align: right;"
									onchange="javascript:submitform();"
									onkeypress="return keypress(event);" <%=(lsxBean.getTrangthai().trim().length() <= 0 || lsxBean.getTrangthai().equals("0")) ? "" : "readonly" %> /></TD>
								<TD class="plainlabel" valign="top"> </TD>
								<TD class="plainlabel" valign="top" colspan="2"> </TD>
							</TR>
 


						</TABLE>
					</div>
				</fieldset>
				<fieldset>
					<legend class="legendtitle"></legend>
					<table width="100%">
						<tr>
							<td>
							  
							</td>
						</tr>

						<TR>

							<td colspan="3">

								<ul class="tabs" id="tabnew">
									<%
										for (int i = 0; i < ListLsxcongdoan.size(); i++) {
											ILenhSXCongDoan lsxcd = ListLsxcongdoan.get(i);
									 if (lsxcd.getCongDoanId().equals(lsxBean.getCongDoanCurrent())) {
									%>
	
									<li class="current"><a href="#tab<%=i%>">Công đoạn <%=(i + 1)%></a></li>
									<%
										} else {
									%>
									<li><a class="" href="#tab<%=i%>">Công đoạn <%=(i + 1)%></a></li>
									<%
										}
										}
									%>
								</ul> <%
 					for (int i = 0; i < ListLsxcongdoan.size(); i++) {
				 		ILenhSXCongDoan lsxcd = ListLsxcongdoan.get(i);
				 		// PhatNguyen thêm công đoạn nhập xuất
				 		ArrayList<String> soluongNhap = lsxcd.getSoluongNhap();
				 		ArrayList<String> soluongXuat = lsxcd.getSoluongXuat();
				 		ArrayList<String> soluongDuPham = lsxcd.getSoluongDuPham();
				 		ArrayList<String> soluongPhePham = lsxcd.getSoluongPhePham();
				 		System.out.println("soluongnhap size jsp: " + soluongNhap.size());
 		
 		
 									if (lsxcd.getCongDoanId().equals(lsxBean.getCongDoanCurrent())) {
 %>
								 	 <div id="tab<%=i%>" class="tab_content" style="display: block;">
									<%
										} else {
									%>
									<div id="tab<%=i%>" class="tab_content" style="display: none;">

										<%
											}
										%>
										<table width="100%" class="plainlabel">
												<tr>
														<td>
														<table class="plainlabel" align="left" width="100%"
														cellpadding="4" cellspacing="0">
														<tr>
															<td width="15%"  >Phân xưởng</td>
															<td><input type="hidden" id="lsxcdthutu"
																name="lsxcdthutu" value="<%=lsxcd.getThutu()%>" /> <input
																type="hidden" id="lsxcdspid" name="lsxcdspid"
																value="<%=lsxcd.getSpid()%>" /> <input type="hidden"
																id="lsxcdspmasp" name="lsxcdspmasp"
																value="<%=lsxcd.getMaSp()%>" /> <input type="hidden"
																id="lsxcdbomid" name="lsxcdbomid"
																value="<%=lsxcd.getBomId()%>" /> <input type="hidden"
																id="lsxcdnhamayid" name="lsxcdnhamayid"
																value="<%=lsxcd.getNhaMayId()%>" /> <input
																type="hidden" id="lsxcdkiemdinhcl"
																name="lsxcdkiemdinhcl"
																value="<%=lsxcd.getKiemDinhCL()%>" /> <input
																type="hidden" id="lsxcdid" name="lsxcdid"
																value="<%=lsxcd.getCongDoanId()%>" /> <input
																readonly="readonly" style="width: 300px" type="text"
																id="lsxcdphanxuong" name="lsxcdphanxuong"
																value="<%=lsxcd.getPhanXuong()%>"
																style="text-align: right;" />
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																 </td>
														</tr>
														<tr>
															<td>Diễn giải</td>
															<td><input readonly="readonly" style="width: 300px"
																type="text" id="lsxcddiengiai" name="lsxcddiengiai"
																value="<%=lsxcd.getDiengiai()%>"
																style="text-align: right;" /></td>

														</tr>
														<tr>
															<td>Sản phẩm</td>
															<td><input readonly="readonly" style="width: 300px"
																type="text" id="lsxcdsanpham" name="lsxcdsanpham"
																value="<%=lsxcd.getSanPham()%>"
																style="text-align: right;" /></td>
														</tr>
														<tr>
															<td>Số lượng</td>
															<td><input type="text" id="lsxcdsoluong"
																name="lsxcdsoluong" value="<%=lsxcd.getSoLuong()%>"
																readonly="readonly" style="text-align: right;" /></td>
														</tr>
														 
														<tr>
															<td>BOM </td>
															<td><input type="hidden" id="lsxcdBomId"
																name="lsxcdBomId" value="<%=lsxcd.getBomId()%>"
																readonly="readonly" style="text-align: right;" />
																<input type="text" id="lsxcdBomTen"
																name="lsxcdBomTen" value="<%=lsxcd.getBomTen()%>"
																readonly="readonly" style="text-align: right;" />
																</td>
														</tr>
														 <tr>
														 <TD class="plainlabel">Chọn kho sản xuất </TD>
																<TD class="plainlabel" colspan="1" > 
																<select
																	style="width: 300px" id="lsxcdKhosxId" name="lsxcdKhosxId">	
																		<option value=""></option>
																		<%
																		rskho=lsxcd.getKhosxRs();
																			if (rskho !=null ) {
																				try {
																		 
																					while (rskho.next()) {
																						if (rskho.getString("pk_seq").equals(lsxcd.getKhoSXId())) {
																							%>
																							<option value="<%=rskho.getString("pk_seq")%>"
																								selected="selected"><%=  rskho.getString("ten")%></option>
																							<% 		 
																							 	} else {
																							%>
																							<option value="<%=rskho.getString("pk_seq")%>"><%=  rskho.getString("ten")%></option>
																							<%
																							}
																					}
																				} catch (SQLException ex) {
																				}
																			}
																		%>
																</select>  
																 </TD>
														 </tr>
														 <%
														 double trangthai=0;
															try{
															trangthai= Double.parseDouble(lsxBean.getTrangthai());
															}catch(Exception er){
																
															} 
															if(trangthai>=2){
																%><tr><%
															}else{
															 %>
															 <tr  style="display: none">
															 <%} %>
															<td>Ngày yêu cầu NL thêm</td>
															<td> 
																<input type="text" id="lsxcdngayyeucauthem" onchange=" Reload_NLCongDoan(<%=lsxcd.getCongDoanId()%>)"
																name="lsxcdngayyeucauthem" value="<%=lsxcd.getNGayYcThem()%>"
																readonly="readonly"   class="days" />
																</td>
														</tr>
													
													</table>


												</td>
														<td style="display: none;">
											 
															<table name='table_noidungnhap' width="100%" class="plainlabel">
														<tr>
															<th width="30%"></th>
															<th width="12%">Lần 1</th>
															<th width="12%">Lần 2</th>
															<th width="12%">Lần 3</th>
															<th width="12%">Lần 4</th>
															<th width="12%">Lần 5</th>
															<th width="12%" >Tổng cộng</th>
														</tr>
														<tbody>
															<tr>
																<td width="30%" >Nhập</td>
																<%
																	for (int index = 0; index < soluongNhap.size(); index++) {
																%>
																<td><input type='text' onchange="Tintong_CongDoanNhapXuat()"
																	name='_soluongNhap_tab_<%=i%>'
																	value='<%=soluongNhap.get(index)%>' /></td>
																<%
																	}
																%>
																<td><input type='text' readonly="readonly" style="width:100px"
																	name='_totalsoluongNhap_tab_<%=i%>'
																	value='' /></td>
																
															</tr>
															<tr>
																<td >Xuất</td>
																<%
																	for (int index = 0; index < soluongXuat.size(); index++) {
																%>
																<td><input type='text' onchange="Tintong_CongDoanNhapXuat()"
																	name='_soluongXuat_tab_<%=i%>'
																	value='<%=soluongXuat.get(index)%>' /></td>
																<%
																	}
																%>
																	<td><input type='text' readonly="readonly"  style="width:100px"
																	name='_totalsoluongXuat_tab_<%=i%>'
																	value='' /></td>
																	
															</tr>
															<tr>
																<td>Chuyển Dư Phẩm</td>
																<%
																	for (int index = 0; index < soluongDuPham.size(); index++) {
																%>
																<td><input type='text' onchange="Tintong_CongDoanNhapXuat()"
																	name='_soluongDuPham_tab_<%=i%>'
																	value='<%=soluongDuPham.get(index)%>' /></td>
																<%
																	}
																%>
																	<td><input type='text' readonly="readonly"  style="width:100px"
																	name='_totalsoluongDuPham_tab_<%=i%>'
																	value='' /></td>
																	
															</tr>
															<tr>
																<td>Chuyển Phế Phẩm</td>
																<%
																	for (int index = 0; index < soluongPhePham.size(); index++) {
																%>
																<td><input type='text' onchange="Tintong_CongDoanNhapXuat()"
																	name='_soluongPhePham_tab_<%=i%>'
																	value='<%=soluongPhePham.get(index)%>' /></td>
																<%
																	}
																%>
																<td><input type='text' readonly="readonly"  style="width:100px"
																	name='_totalsoluongPhepham_tab_<%=i%>'
																	value='' /></td>
																	
															</tr>
<tr>
																<td>Tổng cộng</td>
																<%
																 	for (int index = 0; index < soluongPhePham.size(); index++) {
																%>
																<td><input type='text' readonly="readonly"
																	name='_soluongtongdoc_tab_<%=i%>'
																	value='' /></td>
																<%
																	}
																%>
																
																<td><input type='text' readonly="readonly"  style="width:100px"
																	name='_totalsoluong_tab_<%=i%>'
																	value='' /></td>
																	
															</tr>
															
															<tr>
														
																<td colspan="4">
																	<!-- <button type="button" id='checknhap'
																		onclick="KiemTra_CongDoanNhapXuat()">Check</button> -->
																		
																		
																		<a class="button"
																		href=" javascript: KiemTra_CongDoan()">
																		<img style="top: -4px;" src="../images/button.png"
																		alt="">Kiểm tra</a>
																		
																		 <!-- <a class="button"
																			href=" javascript: LuuCongDoanNhapXuat()">
																		<img style="top: -4px;" src="../images/button.png"
																		alt="">Lưu -->
																 
																</td>
																
															</tr>
														 </table>
														 </td>
												 </tr>
												 
												 <tr>
															 
															
																<td colspan="3" align="left">
																
														  
																 <a class="button"
																href=" javascript:PrintLsxNangcao(<%=lsxcd.getCongDoanId()%>)">
																<img style="top: -4px;" src="../images/printer20.png"
																alt="">In công đoạn </a>
																</td>
																
																
														 
								<div class="tabDetails">
									<div id="tab_1" class="tabContents"  >
										<table width="100%" align="center" id="vattudenghi">
											<tr class="plainlabel">
												<th width="15%" style="display: none">Công đoạn</th>
												<th width="15%">Mã</th>
												<th width="23%">Tên</th>
												<th width="10%">DVT</th>
												<th width="10%">Hàm ẩm</th>
												<th width="10%">Hàm lượng</th>
												<th width="7%">SL chuẩn</th>
												<th width="7%">SL đã YC</th>
												<th width="7%">Yêu cầu </th>
											  
											</tr>
											<%
												int j = 0;
											vattuList =lsxcd.getListDanhMuc();
											
												if (vattuList != null)
													while (j < vattuList.size()) {
														IDanhmucvattu_SP vt = vattuList.get(j);

														String syle = "";
														if (!vt.getChon().equals("1")) {
															syle = "background-color: red;";
														}
											%>
											<tr class="plainlabel">
												<td  style="display: none"> 
													<input id="congdoanid1<%=j%>" style="width: 100%"
													type="hidden" name="congdoanid1_<%=i%>"
													value="<%=vt.getCongDoanId()%>"> 
													 <input	style="width: 100%" type="hidden" name="bomid1_<%=i%>"
													value="<%=vt.getBomId()%>"> <input
													style="width: 100%" type="text" name="congdoanten1_<%=i%>"
													value="<%=vt.getTenCongDoan()%>"> <input
													style="width: 100%" type="hidden" name="idvt1_<%=i%>"
													value="<%=vt.getIdVT()%>"> <input
													style="width: 100%" type="hidden" name="isTINHHAMAM_<%=i%>"
													value="<%=vt.getIsTinhHA()%>"> <input
													style="width: 100%" type="hidden" name="isTINHHAMLUONG_<%=i%>"
													value="<%=vt.getIsTinhHL()%>"> <input
													style="width: 100%" type="hidden" name="kho1_<%=i%>"
													value="<%=vt.getkhoid()%>"> <input
													style="width: 100%" type="hidden" name="IsNLTieuHao_<%=i%>"
													value="<%=vt.getIsNLTieuHao()%>"> <input
													style="width: 100%" type="hidden" name="chon__<%=i%>"
													value="<%=vt.getChon()%>"></td>
												<td><input style="width: 100%" type="text"
													readonly="readonly" name="mavattu1_<%=i%>"
													value="<%=vt.getMaVatTu()%>"></td>
												<td><input style="width: 100%" type="text"
													readonly="readonly" name="tenvattu1_<%=i%>"
													value="<%=vt.getTenVatTu()%>"></td>
												<td><input style="width: 100%" type="text"
													name="donvitinh1_<%=i%>" value="<%=vt.getDvtVT()%>"></td>
												<td><input style="width: 100%" type="text"
													readonly="readonly" name="hamam_<%=i%>" value="<%=vt.getHamam()%>"></td>
												<td><input style="width: 100%" type="text"
													readonly="readonly" name="hamluong_<%=i%>"
													value="<%=vt.getHamluong()%>"></td>
												<td><input style="width: 100%"  type="text"
													readonly="readonly" name="soluongchuan_<%=i%>"
													value="<%=formatter.format(Double.parseDouble(vt.getSoLuongChuan().replaceAll(",", "")))%>"></td>
												<td><input style="width: 60%" type="text" 
													readonly="readonly" name="soluongdayc_<%=i%>"
													value="<%=formatter.format(Double.parseDouble(vt.getSoluongDaYC().replaceAll(",", "")))%>"> 
												 
												 
 													<a href="" id="cd<%=i+"tt"+j%>pubup_dayeucau"
													rel="cd<%=i%>_subcontendayeucau<%=j%>"> <img
														alt="Số lô - Vị trí hàng hóa xuất"
														src="../images/vitriluu.png"></a>
													<DIV id="cd<%=i%>_subcontendayeucau<%=j%>"
														style="position: absolute; visibility: hidden; border: 1px solid #80CB9B; background-color: white; max-height: 300px; overflow: auto; width: 1120px; padding: 1px;">
														<table width="100%" align="center" class="tabledetail">
															<tr class="plainlabel">

																<th width="140px">Tên kho</th>
																<th width="120px">MARQUETTE</th>
																<th width="150px">Mã SP</th>
																<th width="200px">Số lô</th>
																<th width="100px">Phiếu kiểm nghiệm</th>
																<th width="100px">Phiếu định tính</th>
																<th width="100px">Phiếu EO</th>
																<th width="50px">Mã thùng</th>
																<th width="50px">Mã mẻ</th>
																<th width="130px">Ngày hết hạn</th>
																<th width="130px">Ngày nhập kho</th>
																<th width="130px">Mã NSX</th>
																<th style="display: none;" width="100px">Khu vực</th>
																<th width="100px">Vị trí</th>
																<th width="50px">Hàm ẩm</th>
																<th width="50px">Hàm lượng</th>
																<th width="100px">Số lượng YC</th>
															</tr>
															<%
																List<ISpSanxuatChitiet> list_ct = vt.getListCT_DaYCCK();

																if (list_ct != null)
																	for (int t = 0; t < list_ct.size(); t++) {

																		ISpSanxuatChitiet sp = list_ct.get(t);

																		if (sp.getIdSpThayThe().length() > 0) {
																		%>
																		<tr class='tbdarkrow'>
																			<%
																				} else {
																			%>
																		
																		<tr class='tblightrow'>
																			<%
																				}
																			%>
																<td><input type="hidden" size="200px"
																	style="font-size: 12px" name="<%=j%>_khoid_dayc"
																	value="<%=sp.getKhoId()%>" readonly="readonly" /> <input
																	type="hidden" size="200px" style="font-size: 12px"
																	name="<%=j%>_binid_dayc" value="<%=sp.getBinId()%>"
																	readonly="readonly" /> <input type="hidden"
																	size="100px" style="font-size: 12px"
																	name="<%=j%>_spId_dayc" value="<%=sp.getIdSp()%>"
																	readonly="readonly" /> <input type="hidden"
																	size="100px" name="<%=j%>_spIdThayThe_dayc"
																	value="<%=sp.getIdSpThayThe()%>" readonly="readonly" />
																	<input type="hidden" size="100px"
																	name="<%=j%>_KhuvucId_dayc"
																	value="<%=sp.getkhuvuckhoId()%>" readonly="readonly" />

																	<input type="hidden" size="100px"
																	style="font-size: 12px" name="<%=j%>_MARQUETTE_FK_dayc"
																	value="<%=sp.getMARQUETTE_FK()%>" readonly="readonly" />

																	<input type="text" size="100px" style="font-size: 12px"
																	name="<%=j%>_khoten_dayc" value="<%=sp.getKhoTen()%>"
																	readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_MARQUETTE_dayc"
																	value="<%=sp.getMARQUETTE()%>" readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px;background-color: <%=(!vt.getIdVT().equals(sp.getIdSp()) ? "blue" : "")%>"
																	name="<%=j%>_MASP_dayc" value="<%=sp.getMaSp()%>"
																	readonly="readonly" /></td>
																<td><input type="text" size="200px"
																	style="font-size: 12px" name="<%=j%>_SOLO_dayc"
																	value="<%=sp.getSolo()%>" readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_MAPHIEU_dayc"
																	value="<%=sp.getMAPHIEU()%>" readonly="readonly" /></td>


																<td><input type="text" size="100px"
																	style="font-size: 12px"
																	name="<%=j%>_MAPHIEU_TINHTINH_dayc"
																	value="<%=sp.getMAPHIEU_TINHTINH()%>"
																	readonly="readonly" /></td>


																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_MAPHIEU_EO_dayc"
																	value="<%=sp.getMAPHIEU_EO()%>" readonly="readonly" /></td>


																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_MATHUNG_dayc"
																	value="<%=sp.getMATHUNG()%>" readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_MAME_dayc"
																	value="<%=sp.getMAME()%>" readonly="readonly" /></td>

																<td><input type="text" size="150px"
																	style="font-size: 12px" name="<%=j%>_NGAYHETHAN_dayc"
																	value="<%=sp.getNGAYHETHAN()%>" readonly="readonly" />
																</td>
																<td><input type="text" size="150px"
																	style="font-size: 12px" name="<%=j%>_NGAYNHAPKHO_dayc"
																	value="<%=sp.getNGAYNHAPKHO()%>" readonly="readonly" />
																</td>
																<td>
							                            		<input   style="<%=syle%>"  type="text" size="150px" name="<%=j%>_MANSX_dayc" value="<%=sp.getMANSX() %>" readonly="readonly" />
							                            		</td>
							                            	
																<td style="display: none;"><input type="text"
																	size="100px" style="font-size: 12px"
																	name="<%=j%>_khuvuckhoTen_dayc"
																	value="<%=sp.getkhuvuckhoTen()%>" readonly="readonly" />
																</td>
																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_bin_dayc"
																	value="<%=sp.getBin()%>" readonly="readonly" /></td>

																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_HAMAM_dayc"
																	value="<%=sp.getHAMAM()%>" readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px" name="<%=j%>_HALUONG_dayc"
																	value="<%=sp.getHAMLUONG()%>" readonly="readonly" /></td>

																<td><input type="text" size="100px"
																	name="<%=j%>_Soluongdexuat_dayc"
																	value="<%=sp.getSoluong()%>" /></td>


															</tr>
															<%
																}
															%>
														</table>
														<div align="right">
															<a
																href="javascript:dropdowncontent.hidediv('cd<%=i%>_subcontendayeucau<%=j%>');">Hoàn
																tất</a>
														</div>
													</DIV> 
													</td>


												 
												<td> 
												<input style="width: 60%;<%=syle%>" type="text"
													readonly="readonly" name="soluong_<%=i%>"
													value="<%=formatter.format(Double.parseDouble(vt.getSoLuong().replaceAll(",", "")))%>">  
													
													 
												<a href="" id="cd<%=i+"tt"+j%>pubup_ton"
													rel="cd<%=i%>_subcontenta<%=j%>"> <img
														alt="Số lô - Vị trí hàng hóa xuất"
														src="../images/vitriluu.png"></a>
														
														
													<DIV id="cd<%=i%>_subcontenta<%=j%>"
														style="position: absolute; visibility: hidden; border: 1px solid #80CB9B; background-color: white; max-height: 300px; overflow: auto; width: 1120px; padding: 1px;">
														<table width="100%" align="center" class="tabledetail">
															<tr class="plainlabel">

																<th width="140px">Tên kho</th>
																<th width="100px">MARQUETTE</th>
																<th width="200px">Mã SP</th>
																<th width="200px">Số lô</th>
																<th width="100px">Phiếu kiểm nghiệm</th>
																<th width="100px">Phiếu định tính</th>
																<th width="100px">Phiếu EO</th>

																<th width="50px">Mã thùng</th>
																<th width="50px">Mã mẻ</th>
																<th width="130px">Ngày hết hạn</th>
																<th width="130px">Ngày nhập kho</th>
																<th width="130px">Mã NSX</th>
																
																<th style="display: none;" width="100px">Khu vực</th>
																<th width="100px">Vị trí</th>
																<th width="50px">Hàm ẩm</th>
																<th width="50px">Hàm lượng</th>
																<th width="100px">Số lượng tồn</th>
																<th width="100px">Số lượng tồn quy đổi</th>
																<th width="100px">Số lượng đề xuất</th>
															</tr>
															<%
																	  list_ct = vt.getListCTkho();
														if(list_ct!=null){
														for (int t = 0; t < list_ct.size(); t++) {

															ISpSanxuatChitiet sp = list_ct.get(t);

															if (sp.getIdSpThayThe().length() > 0) {
															%>
															<tr class='tbdarkrow'>
																<%
																	} else {
																%>
															
															<tr class='tblightrow'>
																<%
																	}
																%>
																<td><input type="hidden" size="200px"
																	name="<%=i+"_"+j%>_khoid" value="<%=sp.getKhoId()%>"
																	readonly="readonly" /> <input type="hidden"
																	size="200px" name="<%=i+"_"+j%>_binid"
																	value="<%=sp.getBinId()%>" readonly="readonly" />
																	 	<input type="hidden" size="100px" name="<%=i+"_"+j%>_doituongid"  value="<%=sp.getDoituongId()%>" readonly="readonly" />
																	 	
																	 	  
																	<input
																	type="hidden" size="100px" name="<%=i+"_"+j%>_spId"
																	value="<%=sp.getIdSp()%>" readonly="readonly" /> <input
																	type="hidden" size="100px" name="<%=i+"_"+j%>_spIdThayThe"
																	value="<%=sp.getIdSpThayThe()%>" readonly="readonly" />
																	<input type="hidden" size="100px"
																	name="<%=i+"_"+j%>_KhuvucId" value="<%=sp.getkhuvuckhoId()%>"
																	readonly="readonly" />
																	
																	<input type="hidden" size="100px" name="<%=i+"_"+j%>_nsx_fk"  value="<%=sp.getNSX_FK()%>" readonly="readonly" />
																		 
																	 <input type="hidden"
																	size="100px" name="<%=i+"_"+j%>_MARQUETTE_FK"
																	value="<%=sp.getMARQUETTE_FK()%>" readonly="readonly" />

																	<input style="font-size: 12px" type="text" size="100px"
																	name="<%=i+"_"+j%>_khoten" value="<%=sp.getKhoTen()%>"
																	readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MARQUETTE"
																	value="<%=sp.getMARQUETTE()%>" readonly="readonly" /></td>
																<td><input type="text" size="100px"
																	style="font-size: 12px;background-color: <%=(!sp.getIdSpThayThe().equals(sp.getIdSp()) ? "blue" : "")%> "
																	name="<%=i+"_"+j%>_MASP" value="<%=sp.getMaSp()%>"
																	readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="200px" name="<%=i+"_"+j%>_SOLO"
																	value="<%=sp.getSolo()%>" readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MAPHIEU"
																	value="<%=sp.getMAPHIEU()%>" readonly="readonly" /></td>


																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MAPHIEU_TINHTINH"
																	value="<%=sp.getMAPHIEU_TINHTINH()%>"
																	readonly="readonly" /></td>


																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MAPHIEU_EO"
																	value="<%=sp.getMAPHIEU_EO()%>" readonly="readonly" /></td>


																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MATHUNG"
																	value="<%=sp.getMATHUNG()%>" readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_MAME"
																	value="<%=sp.getMAME()%>" readonly="readonly" /></td>

																<td><input style="font-size: 12px" type="text"
																	size="150px" name="<%=i+"_"+j%>_NGAYHETHAN"
																	value="<%=sp.getNGAYHETHAN()%>" readonly="readonly" />
																</td>
																<td><input style="font-size: 12px" type="text"
																	size="150px" name="<%=i+"_"+j%>_NGAYNHAPKHO"
																	value="<%=sp.getNGAYNHAPKHO()%>" readonly="readonly" />
																</td>
																<td>
							                            				<input   style="<%=syle%>"  type="text" size="150px" name="<%=i+"_"+j%>_MANSX" value="<%=sp.getMANSX() %>" readonly="readonly" />
							                            	</td>

																<td style="display: none;"><input type="text"
																	size="100px" name="<%=i+"_"+j%>_khuvuckhoTen"
																	value="<%=sp.getkhuvuckhoTen()%>" readonly="readonly" />
																</td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_bin" value="<%=sp.getBin()%>"
																	readonly="readonly" /></td>

																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_HAMAM"
																	value="<%=sp.getHAMAM()%>" readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_HALUONG"
																	value="<%=sp.getHAMLUONG()%>" readonly="readonly" /></td>

																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_Soluongton"
																	value="<%=sp.getSoluongton()%>" readonly="readonly" />
																</td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_Soluongtonthute"
																	value="<%=sp.getSoluongtonthute()%>"
																	readonly="readonly" /></td>
																<td><input style="font-size: 12px" type="text"
																	size="100px" name="<%=i+"_"+j%>_Soluongdexuat"
																	value="<%=sp.getSoluong()%>" /></td>


															</tr>
															<%
																}
															}
															%>
														</table>
														<div align="right">
															<a
																href="javascript:dropdowncontent.hidediv('cd<%=i%>_subcontenta<%=j%>');">Hoàn
																tất</a>
														</div>
													</DIV></td>

											</tr>

											<%
												j++;
													}
											%>
										</TABLE>
									</div>
									 
								</div>
								 
															</td>
															</tr>
															
														</tbody>
														<tfoot class="planlabel"></tfoot>
													</table>
												
									</div>
									<%
										}
									%>
								</div>
							</td>
						</TR>
					</TABLE>
				</fieldset>
			</div>
		</div>

		<script type="text/javascript">
	dropdowncontent.init('spInit', "center", 500, "click");
	dropdowncontent.init('spInit1', "center", 900, "click");
	dropdowncontent.init('tonkhotp', "left", 900, "click");
	
	<%
	for (int i = 0; i < ListLsxcongdoan.size(); i++) {
		vattuList= ListLsxcongdoan.get(i).getListDanhMuc();
		
	for (int j = 0; j < vattuList.size(); j++) {%>
	
	
		dropdowncontent.init("cd<%=i+"tt"+j%>pubup_ton", "left-top", 900, "click");
		dropdowncontent.init("cd<%=i+"tt"+j%>pubup_dayeucau", "left-top", 900, "click");
		
	<%} 
	}%>
	replaces();
	TinhTrongLuong();
<%-- 	<%if(uutien){%>
	$(':radio').click(function(){
	    return false;
	});
	
	<%}%> --%>
</script>





		<%
			try {

				if (ListLsxcongdoan != null) {
					ListLsxcongdoan.clear();
				}
				if (nhamayRs != null) {
					nhamayRs.close();
				}
				if (rskho != null) {
					rskho.close();
				}

				if (vattuList != null) {
					vattuList.clear();
				}
				if (rscongdoan != null) {
					rscongdoan.close();
				}
				if (spRs != null) {
					spRs.close();
				}
				if (kbsxRs != null) {
					kbsxRs.close();
				}
				if (rsdvkd != null) {
					rsdvkd.close();
				}
			 

			} catch (Exception er) {

			} finally {
				lsxBean.DBclose();
			}
			session.setAttribute("lsxBean", null);
		%>
	</form>
</BODY>
</html>