<%@page import="geso.traphaco.erp.beans.doiquycach.ISpDoiquycach"%>
<%@page import="geso.traphaco.erp.beans.phieuxuatkho.ISpDetail"%>
<%@page import="geso.traphaco.erp.beans.doiquycach.IDoiquycach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="java.sql.SQLException"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
  <%
  	IDoiquycach obj = (IDoiquycach)session.getAttribute("obj") ;
 	List<ISpDoiquycach> spDoiquycachlist =obj.getSpDoiquycachlist();
  	ResultSet khoRs = obj.getKhoRs();
  	ResultSet khuvuc_lo = obj.getKhuvuc_Lo();
  	String[] spTen2 = obj.getSpTen2();
  	String[] dvt2=obj.getDVT2();
  	String[] spMa2 = obj.getSpMa2();
  	String[] spId2 = obj.getSpId2();
  	String[] soluong2 = obj.getSoluong2();
  	String[] dongia2 = obj.getDongia2();
  	String[] tonggiatri2 = obj.getTonggiatri2();
  	String[] phanbo2 = obj.getPhanbo2();
  	
  %>
<%  NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%  NumberFormat formatter1 = new DecimalFormat("###.###"); %> 
 
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>	
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_spdqc.js"></script>
 <style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
		width: 600px;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
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
        }); 		
		
</script>
	

 <SCRIPT language="javascript" type="text/javascript">
						
	function saveform()
	{ 	
		
		phanbo_dongia_sp_cuoicung(); 
	/* 	 tinhtong_saudoiquycach(); */
		
	 	document.forms['dqcForm'].action.value= 'save';
		document.forms['dqcForm'].submit();
	}
	function reload_sp()
	{ 	
		document.forms['dqcForm'].action.value= 'reload_sp';
		document.forms['dqcForm'].submit();
	}
	
	function submitform()
	{ 	
		document.forms['dqcForm'].submit();
	}
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
			return false;}
			
	}	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
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
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	 }

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }
	 
	 function replaces()
	 {
		 
		 var spma = document.getElementsByName("spMa");
		 var spten1 = document.getElementsByName("spTen1");
		 var spId = document.getElementsByName("spId");
		 var dvt1 = document.getElementsByName("dvt1");
		 var soluong1 = document.getElementsByName("soluong");
		 var isreload = document.getElementsByName("isreload");
		 var giatri1 = document.getElementsByName("giatri1");
		 
			for(i=0; i < spma.length; i++)
			{
				
				 var tem=spma.item(i).value;
				 var pos=parseInt(tem.indexOf(" -- "));
				 if(pos  > 0){
					 	spma.item(i).value = tem.substring(0, parseInt(tem.indexOf(" -- ")));
						tem = tem.substr(parseInt(tem.indexOf(" -- ")) +3);
						 
						spten1.item(i).value = tem.substring(0, tem.indexOf("[") );
						
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						 
						dvt1.item(i).value = tem.substring(0, tem.indexOf("]") );
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						
						spId.item(i).value =tem.substring(0, tem.indexOf("]") );
						isreload.item(i).value='1';
						
						document.forms['dqcForm'].action.value= 'reload_sp';
						document.forms['dqcForm'].submit();
				 }
		 
			 if(spma.item(i).value==""){
				 spten1.item(i).value="";
				 dvt1.item(i).value="";
				 soluong1.item(i).value="";
				 spId.item(i).value="";
				 giatri1.item(i).value="";
				 
			 }
			}
		 
		 for(var i = 2; i <= 8; i++) {
		 
			 var spid2 = document.getElementById("spid" + i);
			 var spma2 = document.getElementById("spma" + i);
			 var spTen2 = document.getElementById("spTen" + i);
			 var dvt2 = document.getElementById("dvt" + i);
			 var soluong2 = document.getElementById("soluong" + i);
			 
			  var	tem=spma2.value;
			 
			   pos=parseInt(tem.indexOf(" -- "));
			 if(pos  > 0){
				 	spma2.value = tem.substring(0, parseInt(tem.indexOf(" -- ")));
					tem = tem.substr(parseInt(tem.indexOf(" -- ")) +3);
					 
					spTen2.value = tem.substring(0, tem.indexOf("[") );
					
					tem = tem.substr(parseInt(tem.indexOf("["))+1 );
					 
					dvt2.value = tem.substring(0, tem.indexOf("]") );
					tem = tem.substr(parseInt(tem.indexOf("["))+1 );
					tem = tem.substr(parseInt(tem.indexOf("["))+1 );
					spid2.value =tem.substring(0, tem.indexOf("]") );
			 }
			 
			 if(spma2.value==""){
				 spTen2.value ="";
				 dvt2.value = "";
				// soluong2.value="";
				 spid2.value="";
			 }
				
		 }
		
		 tinhtong_truocdoiquycach();
		 tinhtong_saudoiquycach();
		 setTimeout(replaces, 250);
	 }
	 
	/*  /* 
	 function Tinhphanbo(){
		
		 var tongtienphanra=0;
		 
		 var giatri1 = document.getElementsByName("giatri1");
		 
			for( i=0; i < giatri1.length; i++)
			{
				
				if(giatri1.item(i).value!=''){
				 try{
					 tongtienphanra=tongtienphanra+ parseFloat(giatri1.item[i].value.replace(/,/g,""));
				 }catch(err ){}
				}
			}
			
		 
		 var chiphi1_tmp=0;
		 try{
			 chiphi1_tmp=parseFloat(document.getElementById("chiphi1").value.replace(/,/g,""));
		 }catch(err ){}
		 
		 var chiphikho_tmp=0;
		 try{
			 chiphikho_tmp=parseFloat(document.getElementById("chiphikho").value.replace(/,/g,""));
		 }catch(err ){}
		 
		 
		 tongtienphanra =tongtienphanra+ chiphi1_tmp +chiphikho_tmp;
		 
		var thantien_truocphanbo=0; 
		var totalsoluong=0;
		var bientam=0;
		 for(var i = 2; i <= 8; i++) {
			 
			 var spid2 = document.getElementById("spid" + i);
			 var spma2 = document.getElementById("spma" + i);
			 var spTen2 = document.getElementById("spTen" + i);
			 var dvt2 = document.getElementById("dvt" + i);
			 var soluong2 = document.getElementById("soluong" + i);
			 var dongia2 = document.getElementById("dongia" + i);
			 
			   var soluongtmp=0;
			   var dongia2tmp=0;
			   
				try{
					soluongtmp=parseFloat(soluong2.value.replace(/,/g,""));
				}catch(err){}
			
				try{
					dongia2tmp=parseFloat(dongia2.value.replace(/,/g,""));
					
				}catch(err){}
				
			 	soluong2.value= DinhDangDonGia(soluongtmp);
				dongia2.value= DinhDangDonGia(dongia2tmp);
				
				thantien_truocphanbo += Math.round(soluongtmp* dongia2tmp);
				
				if(soluongtmp >0){
					bientam=i;
				}
				totalsoluong+= soluongtmp;
		 }
			
		 var tienchenhlech=tongtienphanra-Math.round(thantien_truocphanbo);
		 var totaldaphanbo=0;
		 for(var i = 2; i <= 8; i++) {
			 
			 var spid2 = document.getElementById("spid" + i);
			 var spma2 = document.getElementById("spma" + i);
			 var spTen2 = document.getElementById("spTen" + i);
			 var dvt2 = document.getElementById("dvt" + i);
			 var soluong2 = document.getElementById("soluong" + i);
			 var dongia2 = document.getElementById("dongia" + i);
			 var tonggiatri2=document.getElementById("tonggiatri" + i);
			 var phanbo2 = document.getElementById("phanbo" + i);
			 
			   var soluongtmp=0;
			   var dongia2tmp=0;
			   
			   
				try{
					soluongtmp=parseFloat(soluong2.value.replace(/,/g,""));
				}catch(err){}
			
				try{
					dongia2tmp=parseFloat(dongia2.value.replace(/,/g,""));
					
				}catch(err){}
				
			 	soluong2.value= DinhDangDonGia(soluongtmp);
				dongia2.value= DinhDangDonGia(dongia2tmp);
				
				var thanhtientmp =  Math.round(soluongtmp* dongia2tmp);
				var phanbotmp=0;
				if(i== bientam){
					 phanbotmp=tienchenhlech-totaldaphanbo;
				}else{
					if(thantien_truocphanbo>0){
					  phanbotmp=Math.round((thanhtientmp/ thantien_truocphanbo) *tienchenhlech);
					}
				}
				totaldaphanbo+= phanbotmp;
				phanbo2.value= DinhDangDonGia(phanbotmp);
				tonggiatri2.value=DinhDangDonGia(phanbotmp + thanhtientmp);
				
				if(soluongtmp >0){
					bientam=i;
				}
				 
		 }
		 document.getElementById("tongsoluongdqc").value = DinhDangDonGia(totalsoluong);;
		 document.getElementById("thanhtiendqc").value =DinhDangDonGia(tongtienphanra); ;
		 
		 
	 } */ 
	 
	 function onchangeSoluong(item)
	 {
		 var val = item.value;
		 var lo = document.getElementsByName("soluong1");
		 for(var i=0;i<lo.length;i++)
			 lo[i].value = 0;
		 item.value = val;
	 }
	 
	 
	 
	 function tinhtong_truocdoiquycach(){
	
		 var soluong= document.getElementsByName("soluong");
		 var giatri =  document.getElementsByName("giatri1");
		 var chiphi =  document.getElementsByName("chiphi1");
		 var chiphikho =  document.getElementsByName("chiphikho");

		 var tong_soluong=0;
		 var tong_giatri=0;
		 
		 
		 for(i =0; i< soluong.length ; i++){
			 
			 var a= soluong.item(i).value.replace(/,/g,"");
			 if(isNaN(a) || a==null || a==""){
				 a=0;
			 }
			 
			 var b = giatri.item(i).value.replace(/,/g,"");
			 if(isNaN(b) || b==null || b==""){
				 b=0;
			 }
			 
			 tong_soluong +=  parseFloat(a);
			 tong_giatri +=  parseFloat(b);	
		 }		
		 
		 document.getElementsByName("total_giatri_truocdoiquycach").item(0).value=  DinhDangDonGia( tong_giatri );
		 document.getElementsByName("total_soluong_truocdoiquycach").item(0).value= DinhDangDonGia(tong_soluong );
		 
		 
		 
		 //----- cộng thêm chi phí & chí phí lưu kho  (nếu có)
		 
		 var c = chiphi.item(0).value.replace(/,/g,"");
		 if(isNaN(c) || c==null || c==""){
			 c=0;
		 }
		 
		 var d = chiphikho.item(0).value.replace(/,/g,"");
		 if(isNaN(d) || d==null || d==""){
			 d=0;
		 }
		 
		 tong_giatri += parseFloat(c) + parseFloat(d);   ;
		 
		 document.getElementsByName("tong_truocdoiquycach").item(0).value= DinhDangDonGia( tong_giatri);
		
	 }
	 
	 
	 
	 function phanbo_dongia(){

		 var tongthanhtien =0;
		 var tongsoluong=0;
		 for(i=1; i < 8 ; i ++){
			 var tmp = document.getElementsByName("spma"+(i+1));
			 if(tmp.item(0).value.length > 0 ){ 
				
				 var dongia = document.getElementsByName("dongia"+(i+1));
				 
				 a= dongia.item(0).value.replace(/,/g,"");
				
				 if(isNaN(a) || a=="" || a==null){
					 a=0;
					 document.getElementsByName("dongia"+(i+1)).item(0).value = DinhDangDonGia(a) ;
					 
				 }
				 
					 
				var soluong =  document.getElementsByName("soluong"+(i+1));
				var b = soluong.item(0).value.replace(/,/g,"");
				if(isNaN(b) || b==null || b==""){
					b=0;
				
				}
				var thanhtien = parseFloat(b)* parseFloat(a);
				tongthanhtien += parseFloat(thanhtien);
				tongsoluong += parseFloat(b);
			

				document.getElementsByName("phanbo"+(i+1)).item(0).value = DinhDangDonGia(a);
				document.getElementsByName("tonggiatri"+(i+1)).item(0).value = DinhDangDonGia(thanhtien);
				
			 }
		 } 
		
			
		 document.getElementsByName("tongsoluongdqc").item(0).value = DinhDangDonGia(tongsoluong);
		 document.getElementsByName("thanhtiendqc").item(0).value = DinhDangDonGia(tongthanhtien);
	 }
	 
	 
	 function phanbo_dongia_sp_cuoicung(){
		 
		 var sosp_doiquycach=0;
		 var tonggiatri=0;
		 var vitri=-1;
		 var error =0;
		 
		 var tong =  document.getElementsByName("tong_truocdoiquycach");
		 
		 for(i=1; i < 8 ; i ++){
			 var tmp = document.getElementsByName("spma"+(i+1));
			 
			 
			 if(tmp.item(0).value.length > 0 ){ 
				 
				 if(parseFloat(error) >1) {
					alert('Có hơn 2 sản phẩm đổi quy cách có giá trị bằng 0. Vui lòng kiểm tra lại !!! ');
					return false ;
					
				 }
				
				 
				 var soluong = document.getElementsByName("soluong"+(i+1));
				 var dongia = document.getElementsByName("dongia"+(i+1));
				 var phanbo = document.getElementsByName("phanbo"+(i+1));
				 
				 var a= dongia.item(0).value.replace(/,/g,"");
				 var b = soluong.item(0).value.replace(/,/g,"");
				 if(a==""){
					 a="0";
				 }
				 if(b==""){
					 b="0";
				 }
				 
				 var giatri =    parseFloat(b)* parseFloat(a);
				 
				 
				 document.getElementsByName("phanbo"+(i+1)).item(0).value  = DinhDangDonGia(a);
				 document.getElementsByName("tonggiatri"+(i+1)).item(0).value = DinhDangDonGia(giatri);
				 
				 console.log("don gia : "+ dongia.item(0).value);
				 console.log("don gia : "+ parseFloat(dongia.item(0).value.replace(/,/g,"")));
				 
				 if(dongia.item(0).value=="" || parseFloat(dongia.item(0).value.replace(/,/g,""))==0  ){
					 error +=1;
					 vitri = i+1;
				 }
				 
				 tonggiatri += parseFloat(giatri);
				 
			 }
		 }
		 
		 console.log("tonggiatri : "+ tonggiatri);
		 //--- nếu số tiền phân bổ vượt quá báo lỗi
		 console.log("vi tri: "+ vitri);
		 
		 if(parseFloat(vitri) > -1){
		 console.log("vi tri: "+ vitri);
	
			 if(error <=1){ //--- chỉ có để trống  1 sp cuối cùng  thì mới phân bổ
				 
				 var sotienconlai = parseFloat(tong.item(0).value.replace(/,/g,"")) - parseFloat(tonggiatri);
				 
				 if( parseFloat(sotienconlai)  < 0){
					 alert ("Phân bổ tiền bị vượt quá");
					 return false;
				 }else if( parseFloat(sotienconlai)  > 0){
					
					 var soluong = document.getElementsByName("soluong"+vitri);
					 var dongia1 = 0;
					 
					 if (parseFloat(soluong.item(0).value.replace(/,/g,"")) > 0) {
						dongia1 = parseFloat(sotienconlai) / parseFloat(soluong.item(0).value.replace(/,/g,"")) ;
					 }
						
					 //document.getElementsByName("dongia"+vitri).item(0).value = DinhDangDonGia(dongia1);
					 document.getElementsByName("phanbo"+vitri).item(0).value = DinhDangDonGia(dongia1);
					 document.getElementsByName("tonggiatri"+vitri).item(0).value = DinhDangDonGia(sotienconlai);
					 
					 
					
				 } 
			 }else{
				 alert("Có hơn 2 sản phẩm đổi quy cách có giá trị bằng 0. Vui lòng kiểm tra lại !!! '");
			 }
			 
		 }else{
			 
			 console.log("vao day");
			 tinhtong_saudoiquycach();
		 }	 
		 
	 } 
	 
	 
	 
	 function tinhtong_saudoiquycach(){
		 var tonggiatri=0;
		 var tongsoluong=0;
		 
		 for(i =1; i < 8 ; i++){
			  var soluong= document.getElementsByName("soluong"+(i+1));
			  var giatri = document.getElementsByName("tonggiatri"+(i+1));
			  
			  tonggiatri +=  parseFloat(giatri.item(0).value.replace(/,/g,""));
			  tongsoluong += parseFloat(soluong.item(0).value.replace(/,/g,""));
		 }
		 
		 document.getElementsByName("thanhtiendqc").item(0).value = DinhDangDonGia(tonggiatri);
		 document.getElementsByName("tongsoluongdqc").item(0).value = DinhDangDonGia(tongsoluong);
	 }
	 
	 
	 
</SCRIPT>
 
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="dqcForm" method="post" action="../../DoiquycachUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>

<input type="hidden" name="xuatkho_doiquycach" value='<%= obj.get_xuatkho_doiquycach()%>'>
<input type="hidden" name="phieuxuatkho_dqc" value='<%= obj.get_xuatkhoId_DQC()%>'>

<div id="main" style="width:100%">
	<div align="left" id="header" style="width:100%; float:none">
	<% if (obj.getId().length() == 0) {%>
		<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng &gt; Quản lý tồn kho  &gt; Đổi quy cách  &gt; Tạo mới
	<%}else{ %>
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng &gt; Quản lý tồn kho  &gt; Đổi quy cách  &gt; Cập nhật
    <%} %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%=userTen%>
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <div style="width:100%; float:none" align="left">
             			<TABLE border="0" width="100%" >
							<TR>
						  		<TR ><TD >
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
												<TR class = "tbdarkrow">
													<TD width="30" align="left"><A href="../../DoiquycachSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
													<TD>&nbsp; </TD>						
												</TR>
										</TABLE>
								</TD></TR>
								<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
				
			    				<textarea name="dataerror"  cols="100%" rows="1" ><%=obj.getmsg() %> </textarea>
								
								</FIELDSET>
						   </TD>
					     </tr>
					     <tr>
					     <td>
					     <FIELDSET>
								<LEGEND class="legendtitle">Xuất đổi quy cách</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
					                    <TR>
										  <TD  class="plainlabel"  width = "20%">Ngày</TD>
										  <TD  class="plainlabel"  ><INPUT class = "days" name="ngay" type="text" size="20" value="<%= obj.getNgay() %>" readonly = readonly></TD>
										</TR> 
										 <TR>
										  <TD  class="plainlabel" width = "20%">Ghi chú</TD>
										  <TD  class="plainlabel"  ><INPUT   name="ghichu" type="text" size="20" value="<%= obj.getghichu() %>"  ></TD>
										</TR> 
										<TR>
											<TD  class="plainlabel" width = "120px">Chọn kho</TD>
											<TD class="plainlabel"  >
											<SELECT NAME = "khoId" onChange = "reload_sp();">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (khoRs != null){
												while(khoRs.next()){
													if(khoRs.getString("KHOID").equals(obj.getKhoId())){
											%>																	
														<OPTION VALUE = "<%= khoRs.getString("KHOID") %>" SELECTED><%= khoRs.getString("KHO")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= khoRs.getString("KHOID") %>" ><%= khoRs.getString("KHO")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
										</TR>
										
										<TR >
										<td colspan="2">
										<table  class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tr class="tbheader" > 
										<TH   width="10%" >Mã</TH>
										<TH   width="30%" >Tên</TH>
										<TH   width="5%" >Đơn vị tính </TH>
										<TH width="10%" >Số lượng </TH>
										<TH width="5%"   >Chọn lô</TH>
										<TD width="10%"   >Đơn giá </TD>
										<TD width="10%"   >Tổng giá trị </TD>
										
										</TR>
										
										<% 
										int i=0;
										for (i=0;i<spDoiquycachlist.size();i++) {  
										ISpDoiquycach  spdqc=spDoiquycachlist.get(i);
										
										%>
											
										 <tr> 
											<TD>
											<INPUT id = "isreload" name="isreload" type="hidden"  value="0" style="width:100%">
											<INPUT  id = "spId" name="spId" type="hidden"  value="<%=spdqc.getSpId1() %>" style="width:100%">
											<INPUT autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" id = "spMa" name="spMa" type="text"  value="<%=spdqc.getMa()%>" style="width:100%"></TD>
										  	<TD><INPUT id = "spTen1" name="spTen1" type="text"  value="<%=spdqc.getSpTen1() %>" style="width:100%"></TD>
										  	<TD><INPUT readonly="readonly" id = "dvt1" name="dvt1" type="text"  value="<%=spdqc.getdonvitinh() %>" style="width:100%"></TD>
										  	<TD    ><INPUT readonly="readonly" id = "soluong" name="soluong" type="text"  value="<%=spdqc.getSoluong1()%>" style="width:100%"></TD>
											
											<TD>
											 
												<a href="" id="spInit<%=i%>" rel="subcontent1<%=i%>">
        	 									   <img alt="BOM" src="../images/vitriluu.png"></a>
        	 									   
        	 									   
						  	 					<DIV id="subcontent1<%=i%>" style="position:absolute; visibility: hidden; border: 5px solid #80CB9B;
								                    		 background-color: white; width:750px; padding: 2px;">
									            <table width="90%" align="center">
							                        <tr>
							                        	<%if(obj.getIsKhuvuc().equals("1")) {%>
							                        	<th width="100px">Khu vực</th>
							                        	<%} %>
							                            <th width="100px">Số lô</th>				                            
							                            <th width="100px">Ngày sản xuất</th>
							                            <th width="100px">Ngày hết hạn </th>
							                            <th width="50px">SL tồn</th>
							                            <th width="50px">Số lượng </th>
							                        </tr>
				                        <%
				                        List<ISpDetail> spDetailList =spdqc.getSpDetailList();
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
				                        				<%if(obj.getIsKhuvuc().equals("1")) {%>
				                        				<td>
							                            	<input type="hidden" name="khuidct1<%=spdqc.getSpId1() %>"   value="<%= spDetail.getKhuId() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="tenkhuct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getKhu() %>" readonly="readonly" /></td>
							                            <%} %>
							                            <td>
							                            <input type="hidden" size="100px" name="ngaybatdauct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getNgaybatdau() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="soloct1<%=spdqc.getSpId1() %>"   value="<%= spDetail.getSolo() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="100px" name="ngaysanxuatct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getNgaysanxuat() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="100px" name="ngayhethanct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getNgayhethan() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="50px"  name="soluongtonct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getSoluongton() %>" readonly="readonly" /></td>
							                             <td>
							                            	<input type="text" size="50px"  name="soluongct1<%=spdqc.getSpId1() %>"  value="<%= spDetail.getSoluong() %>"  onKeyPress = "return keypress(event);" /></td>
							                        </tr>
				                        		<%}
				                        	}
				                        %>
				                    </table>	                    
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent1<%=i%>')" onclick="javascript:submitform();">Hoàn tất</a>
								                     </div>
								                </DIV>
											</TD>
											<% 
												double dongiatmp=0;
								                try{
								                	dongiatmp=  spdqc.getDongia1() ;
								                }catch(Exception er ){}
								                
								                double thahtientmp=0;
								                try{
								                	thahtientmp=  spdqc.getChiphi1() ;
								                }catch(Exception er ){}
								            
											%>
											<TD  ><INPUT name="dongia1" id="dongia1" readonly="readonly" type="text" value="<%= formatter.format(dongiatmp)%>" style = "width:100%"  ></TD>
											
											<TD  ><INPUT name="giatri1" id="giatri1"  type="text" value="<%= formatter.format(thahtientmp) %>" style = "width:100%" readonly=readonly></TD>

										</TR>
										
										
										
										
										
										
										<%} %>
										
										<% for(  int j=i; j < spDoiquycachlist.size()+1 ; j++) {  
										  
										%>
											
										 <tr> 
										
											<TD>
											<INPUT id = "isreload" name="isreload" type="hidden"  value="0" style="width:100%">
											<INPUT id = "spId" name="spId" type="hidden"  value="" style="width:100%">
											<INPUT autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)"  id = "spMa" name="spMa" type="text"  value="" style="width:100%"></TD>
										  	<TD><INPUT id = "spTen1" name="spTen1" type="text"  value="" style="width:100%"></TD>
										  	<TD><INPUT readonly="readonly" id = "dvt1" name="dvt1" type="text"  value="" style="width:100%"></TD>
										  	<TD    ><INPUT readonly="readonly" id = "soluong" name="soluong" type="text"  value="" style="width:100%"></TD>
											
											<TD>
											 
												<a href="" id="spInit<%=j%>" rel="subcontent1<%=j%>">
        	 									   <img alt="BOM" src="../images/vitriluu.png"></a>
						  	 						<DIV id="subcontent1<%=j%>" style="position:absolute; visibility: hidden; border: 5px solid #80CB9B;
								                    		 background-color: white; width:750px; padding: 2px;">
									            <table width="90%" align="center">
				                        <tr>
				                        	<%if(obj.getIsKhuvuc().equals("1")) {%>
				                        	<th width="100px">Khu vực</th>
				                        	<%} %>
				                            <th width="100px">Số lô</th>				                            
				                            <th width="100px">Ngày sản xuất</th>
				                            <th width="100px">Ngày hết hạn </th>
				                            <th width="50px">SL tồn</th>
				                            <th width="50px">Số lượng </th>
				                        </tr>
				                         
				                    </table>	                    
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent1<%=j%>')" onclick="javascript:submitform();">Hoàn tất</a>
								                     </div>
								                </DIV>
											</TD>
											<% 
												double dongiatmp=0;
								                double thahtientmp=0;
								                
											%>
											<TD  ><INPUT name="dongia1" id="dongia1"   type="text" value="<%= formatter.format(dongiatmp)%>" style = "width:100%" readonly=readonly></TD>
											<TD  ><INPUT name="giatri1" id="giatri1"  type="text" value="<%= formatter.format(thahtientmp) %>" style = "width:100%" readonly=readonly></TD>

										</TR>
										
										<%} %>
										
										
										<tr class="plainlabel">
											<td></td>
											<td></td>
											<td  style="color: red;">Tổng số lượng</td>
											<td><input type="text" name="total_soluong_truocdoiquycach" >  </td>
											<td></td>
											<td  style="color: red;">Tổng giá trị</td>
											<td><input type="text" name="total_giatri_truocdoiquycach" ></td>
										</tr>
										
										
										
										
										
										</table>
										</td>
										</TR>
											<TR class="plainlabel">
											<TD width="120px"  >Chi phí </TD>
											
											<TD > 
											<% double soluongcp=0;
											try{
												soluongcp =Double.parseDouble(obj.getChiphi1());
											}catch(Exception err){
												
											}
											
											
											%>
											<INPUT name="chiphi1" id="chiphi1"  type="text" size="20" value="<%= formatter.format(soluongcp) %>"  onChange = "submitform();"> 
									 		Chi phí cấp đông lưu kho 
									 	<% 
									 	
									 	try{
											soluongcp =Double.parseDouble(obj.getChiphikho());
										}catch(Exception err){
	
										}
										
									 	%>
									 	<INPUT name="chiphikho" readonly="readonly" id="chiphikho" type="text" size="20" value="<%= formatter.format(soluongcp) %>"  >
									  <%if(obj.getIsKhuvuc().equals("1")) {%>
										 Chọn khu vực lưu kho 
											<SELECT NAME = "khuvuckhoid"  >
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (khuvuc_lo != null){
												while(khuvuc_lo.next()){
													
													if(khuvuc_lo.getString("PK_SEQ").equals(obj.getKhuvucId())){
											%>																	
														<OPTION VALUE = "<%= khuvuc_lo.getString("PK_SEQ") %>" SELECTED><%= khuvuc_lo.getString("TEN")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= khuvuc_lo.getString("PK_SEQ") %>" ><%= khuvuc_lo.getString("TEN")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
										<%} %> 
									 	
									 </td>
											
										</TR>
										
										<tr class="plainlabel">
											<td  style="color: red;">Tổng giá trị trước đổi quy cách</td>
											<td colspan="5"><input type="text" name="tong_truocdoiquycach"> </td>
										</tr>

									</TABLE>
						</FIELDSET>										
					     <FIELDSET>
								<LEGEND class="legendtitle">Sản phẩm nhận đổi quy cách</LEGEND>
									<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
									<TR class="tbheader">
										<TD   width = "5%" >STT </TD>
										<TD   width = "12%" >Mã</TD>
										<TD   width = "30%" >Tên   </TD>
										<TD width = "10%" >Đơn vị tính </TD>
										<TD  width = "8%" >Số lượng </TD>
										<TD width = "10%" >Đơn giá</TD>
										<TD  width = "12%" >Đơn giá sau phân bổ</TD>
										 <TD  width = "12%" >Tổng giá trị </TD>
									</TR>
										
					               <% for(  i = 1; i < 16; i++){ %>
									
										<TR>
											<TD ><INPUT type="text"  value="<%=i%>"  readonly="readonly" ></TD>
											<TD >
														<INPUT Id = <%="spma" + (i+1)%> name=<%= "spma" + (i+1)%> type="text" value="<%= spMa2[i-1]  %>"   >
											 		    <INPUT Id = <%= "spid" + (i+1)%> name=<%= "spid" + (i+1)%> type="hidden"  value="<%= spId2[i-1]  %>"  > 
											 		  </TD>
										  	<TD ><INPUT Id = <%= "spTen" + (i+1)%> name=<%= "spTen" + (i+1)%> type="text"  value="<%= spTen2[i-1]  %>"  readonly="readonly" ></TD>
										  	
										  	
										  	<TD ><INPUT readonly="readonly" id = <%= "dvt" + (i+1)%> name=<%= "dvt" + (i+1)%> type="text"  value="<%=dvt2[i-1] %>"  ></TD>
										  	
											
											<TD ><INPUT name=<%= "soluong" + (i+1)%> id=<%= "soluong" + (i+1)%>  type="text"  value="<%= formatter1.format(Double.parseDouble(soluong2[i - 1])) %>"   onkeypress= "return keypress(event);" onchange="phanbo_dongia_sp_cuoicung()" ></TD>
											
											
											<TD ><INPUT name=<%= "dongia" + (i+1) %> id=<%= "dongia" + (i+1)%> type="text" value="<%= formatter1.format(Double.parseDouble(dongia2[i - 1])) %>"   onkeypress= "return keypress(event);" onchange="phanbo_dongia_sp_cuoicung()" ></TD>
											<TD ><INPUT readonly="readonly" name=<%= "phanbo" + (i+1)%> id=<%= "phanbo" + (i+1)%> type="text" value="<%= formatter1.format(Double.parseDouble(phanbo2[i - 1])) %>"   onkeypress= "return keypress(event);"  ></TD>
											<TD  ><INPUT name=<%= "tonggiatri" + (i+1)%>   id=<%= "tonggiatri" + (i+1)%> type="text" value="<%= formatter1.format(Double.parseDouble(tonggiatri2[i - 1]))%>"  readonly=readonly></TD>

										</TR>

									<% } %>
									
										<TR>
										 
										  	<TD class="plainlabel" colspan="3"></TD>
										  	
											<TD class="plainlabel" style="color: red;" >Tổng số lượng </TD>
											<TD class="plainlabel">
												<INPUT type="text" name="tongsoluongdqc"  readonly="readonly" id ="tongsoluongdqc" value="<%= obj.getTotalLuong() %>" style = "width:100px; text-align: left;" readonly="readonly" ></TD>
											 
										  	
										  	<TD class="plainlabel" ></TD>
											<TD class="plainlabel" style="color: red;">Tổng giá trị </TD>
											<TD class="plainlabel">
												<INPUT type="text" name="thanhtiendqc" id="thanhtiendqc" readonly="readonly" value="<%= obj.getTotalTien() %>" style = "text-align: left;" readonly="readonly" ></TD>
										</TR>
									
									</TABLE>

								</FIELDSET>
								
								</TD>	
							</TR>
						</TABLE>
         </div>
		 
				
				
</form>
<script type="text/javascript">
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	<% 
	for ( int k =0;k < spDoiquycachlist.size()+1;k++) {  
	%>
	dropdowncontent.init('spInit<%=k%>', "left", 500, "click");
	<%
	}
	%>
	jQuery(function()
	{		
 		<% 	for ( int k =1;k < 16 ;k++) {   %>
		$("#spma<%=(k+1)%>").autocomplete("SanPhamList_2.jsp");		
		<%
		}
		%>
	});	
	

	 replaces();
	 phanbo_dongia_sp_cuoicung();
</script>

</BODY>
</html>
<%
	try{
		if(khoRs != null) khoRs.close();
		if(khuvuc_lo != null) khuvuc_lo.close();
		if( spDoiquycachlist!=null){
			spDoiquycachlist.clear();
		}
		if(obj != null){
			obj.DbClose();
		}
		 
		session.setAttribute("obj",null);
	}catch(Exception err){
			
	}
}%>