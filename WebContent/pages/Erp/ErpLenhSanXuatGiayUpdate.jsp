<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuatgiay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuatgiay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*"%>
<% IErpLenhsanxuat lsxBean = (IErpLenhsanxuat)session.getAttribute("lsxBean"); %>
<% ResultSet spRs = lsxBean.getSpRs(); %>
<% ResultSet kbsxRs = lsxBean.getKbsxRs();
List<IErpBom> listbom=lsxBean.getBomList();
%>
<% ResultSet rsdvkd = lsxBean.getRsDvkd(); %>
<% ResultSet nhamayRs = lsxBean.getNhamayRs(); %>
<% ResultSet rskho = lsxBean.getKhoTTRs(); %>
<% List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>)lsxBean.getListDanhMuc(); %>
<% ResultSet rsLohoi = lsxBean.getLohoiRs(); %>
<% ResultSet RskhoTp = lsxBean.getRskhoTp(); %>
<% ResultSet RsKhoNL = lsxBean.getRsKhoLayNL(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
		//System.out.println("in 334533  :");
Utility util = (Utility) session.getAttribute("util");
	List<IDanhmucvattu_SP> vattuthemList = (List<IDanhmucvattu_SP>)lsxBean.getListVattuThem();
	ResultSet rscongdoan=lsxBean.getRsCongDoan();
	List<ILenhSXCongDoan> ListLsxcongdoan= lsxBean.getListCongDoan();
	NumberFormat formatter = new DecimalFormat("#,###,###.#######");
	NumberFormat formatter3 = new DecimalFormat("#,###,###.#######");
		
		//System.out.println( lsxBean.getDvkdId());
			int[] quyenTH = new  int[5];
			quyenTH = util.Getquyen("ErpTieuhaonguyenlieugiaySvl","",userId);
			
			
			int[] quyenNK = new  int[5];
			quyenNK = util.Getquyen("ErpNhapkhoLsxSvl","",userId);
			
			int[] quyenKD = new  int[5];
			quyenKD = util.Getquyen("ErpKiemdinhchatluongSvl","",userId);
			
			
	
	 %>
<% ResultSet rsBom = lsxBean.getBom();%>
<% String tensp = ""; 
   String PK_SEQ_SP = "";
   boolean uutien = false;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/ajax_erplenhsanxuat_giay.js"></script>
<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
     <script src="../scripts/colorBox/jquery.colorbox.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
        $(document).ready(function() { $("#spma").select2(); });
        
    </script>

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
	function Replace_spsx()
	{
		var spsxidsp=document.getElementsByName("spsxidsp");
		var spsxmasp = document.getElementsByName("spsxmasp");
		var spsxtensp = document.getElementsByName("spsxtensp");
		var spsxkhorong = document.getElementsByName("spsxkhorong");  
		var spsxdonvi= document.getElementsByName("spsxdonvi");  
		var spsxtrongluongqd= document.getElementsByName("spsxtrongluongqd");
		var spsxsoluong= document.getElementsByName("spsxsoluong"); 
		var tenkhachhang= document.getElementsByName("tenkhachhang"); 
		
		
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
				 spsxsoluong.item(i).value ="";
				 tenkhachhang.item(i).value ="";
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
	
	 function xoavattuhienthi_loi(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		  document.getElementById("vattudenghi").deleteRow(i);
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
		Replace_khachhang();
		Replace_spsx();
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
				var pos = parseInt(vt.indexOf("-"));
				if(pos > 0)
				{
					mavt.item(i).value = vt.substring(0, pos);
					vt = vt.substr(parseInt(vt.indexOf("-")) + 3);
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
	
	 function TinhTongSoLuong(){
			
	 
			var soluong = document.getElementsByName("spsxsoluong");
			var dvkdid =document.forms['hctmhForm'].dvkdid.value;
			var idsp = document.getElementsByName("spsxidsp");
			var bomid = document.getElementsByName("spsxbomid"); 
			
			var trongluong = document.getElementsByName("spsxtrongluongqd");
			
			var tongsoluong=0;
		
			var tongtrongluong =0; 
			
			
			for(i=0; i < idsp.length; i++)
			{
				if(soluong.item(i).value!='' && ( bomid.item(i).value!='' || dvkdid=="100005") ){
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
	 function DinhDangTien(num) 
	 {
		 num = num.toString().replace(/\$|\,/g,'');
		 
		 num = (Math.round( num * 10000 ) / 10000).toString();
		 
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
	 
	 function GanLaiSoluong(){	
			var sl= document.forms['hctmhForm'].soluongsx.value;
			var soluong = document.getElementsByName("spsxsoluong");
				soluong.item(0).value=sl;
				
				var soluong1 = document.getElementsByName("lsxcdsoluong");
				for(i=0; i < soluong1.length; i++){
					soluong1.item(i).value=0;
				}
	 }
	 
	 function savecongdoan(idcd){
			if(!confirm('Bạn có muốn hoàn tất công đoạn này?'))
			{	return false;
			
			}
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 
	 	 document.forms['hctmhForm'].action.value = 'savecongdoancheckdungsai';
	     document.forms['hctmhForm'].submit();
	     
	 }
	 function saveform()
	 {	
		 var soluong = document.getElementsByName("vattuthemsoluong");
		 var idsp = document.getElementsByName("vattuthemvattuid");
		 var idcd = document.getElementsByName("vattuthemcdid");
		 var soluongton= document.getElementsByName("vattuthemsoluongton"); 
		
	
		for(i=0; i < idsp.length; i++)
		{
			if(idsp.item(i).value!=''){
				/* if(idcd.item(i).value=='')	{
					alert('Vui lòng chọn công đoạn cho nguyên vật liệu thêm');
					return ;
					
				} */
				if(soluong.item(i).value=='' || soluong.item(i).value=='0')	{
					alert('Vui lòng nhập số lượng cho nguyên vật liệu thêm');
					return ;
					
				}
				/* if(parseFloat(soluong.item(i).value) >parseFloat(soluongton.item(i).value)){
					alert('Vui lòng nhập số lượng nhỏ hơn số lượng tồn của các nguyên vật liệu thêm');
					return ;
				} */
				
			}

			
		}
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 
		 document.forms['hctmhForm'].action.value = 'submit';
	     document.forms["hctmhForm"].submit();
	 }
	 function reload_themnguyenlieu()
	 { 
		 
		 document.forms['hctmhForm'].action.value = 'reload_themnguyenlieu';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 
	 function yeucauthemnguyenlieu()
	 { 
		 
		 document.forms['hctmhForm'].action.value = 'yeucauthemnguyenlieu';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function xoavattuhienthi1(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		  
		 document.getElementById("hienthisanpham").deleteRow(i);
		 
		 document.getElementById("khonghienthisanpham").deleteRow(i);
		 
	 }
	 
	 function xoavattuhienthi_them(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		 document.getElementById("hienthisanpham").deleteRow(i);
		 
		 
	 }
 	 function xoavattuhienthi2(chuoi)
	 {
		  var i= document.getElementById(chuoi).parentNode.parentNode.rowIndex;
		 document.getElementById("bangvattuthieu").deleteRow(i);
	 
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
		/* 	if(parseFloat(sotong) >parseFloat(soluongngoai) ) {
				soluong1.item(stt).value=0;
				alert("Không được nhập vượt quá số lượng tổng đề nghị");
				return;
			} */
		 
 }
	 
	 function changeBOM()
	 {
		 
		document.forms["hctmhForm"].action.value = "changeBOM";
	    document.forms["hctmhForm"].submit();
	 }
	 function YeuCauChuyenKho(){
		 document.forms['hctmhForm'].action.value = 'yeucauchuyenkho';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
	 }
	 function YeuCauNguyenLieuThem(){
		 document.forms['hctmhForm'].action.value = 'yeucaunguyenlieuthem';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
	 }
	 function HuyBookedNL(){
		 document.forms['hctmhForm'].action.value = 'HuyBookedNL';
		 document.forms["hctmhForm"].viewBom.value = "1";
		 document.forms["hctmhForm"].submit();
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
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit();
	 }
	 function nhanbanthanhphamloi(nhapkho){
		 document.forms['hctmhForm'].action.value = 'nhapkhotp_loi';
		 document.forms["hctmhForm"].cdidcurrent.value = nhapkho;
		 document.forms["hctmhForm"].submit();
	 }
	 
	 
	 function kiemdinhchatluong(idcd){
		 document.forms['hctmhForm'].action.value = 'kiemdinhchatluong';
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit();
	 }
	 function tieuhaonguyenlieu(idcd){
		 document.forms['hctmhForm'].action.value = 'tieuhaonguyenlieu';
		 document.forms["hctmhForm"].cdidcurrent.value = idcd;
		 document.forms["hctmhForm"].submit();
	 }
	 function thongbaomsg(idcd){
		 	if(!confirm('Bạn đã nhập sản phẩm sản xuất vượt quá dung sai .Bạn có muốn tiếp tục hoàn tất công đoạn này?'))
			{	return false;
			}
		    else
		    {
				document.forms["hctmhForm"].cdidcurrent.value = idcd;
				document.forms['hctmhForm'].action.value = 'savecongdoan';
				document.forms['hctmhForm'].submit();
			}
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
 function YeuCauChuyenKho(){
	 document.forms['hctmhForm'].action.value = 'yeucauchuyenkho';
	 document.forms["hctmhForm"].viewBom.value = "1";
	 document.forms["hctmhForm"].submit();
 }
</script>
 
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpLenhsanxuatgiayUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="trangthai" value='<%=lsxBean.getTrangthai()%>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="viewBom" value='<%= lsxBean.getViewBom() %>'>
<input type="hidden" name="cdidcurrent" value='<%=lsxBean.getCongDoanCurrent()%>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất > Lệnh sản xuất > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
            Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpLenhsanxuatgiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
        	<% if(lsxBean.getTrangthai().equals("0") ) { %>
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        	<%} %>
        	<A href="../../ErpInLenhSanXuatdfSvl?userId=<%=userId%>&display=<%=lsxBean.getId()%>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A>
	        	
       		<A href="../../ErpLenhSanXuatExcelSvl?userId=<%=userId%>&display=<%=lsxBean.getId()%>" ><img src="../images/excel30.gif" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A>
       		<A href="../../ErpInLenhSanXuatNewpdfSvl?userId=<%=userId%>&display=<%=lsxBean.getId()%>" >
	    	<img src="../images/Pdf30.jpg" alt="In" title="Hồ sơ lô" longdesc="Hồ sơ lô" border=1 style="border-style:outset"></A>	
    		 <A href="../../ErpInTongKetLoSanXuatpdfSvl?userId=<%=userId%>&display=<%=lsxBean.getId()%>" >
	    	<img src="../images/pdf.jpg" alt="In" title="Tổng kết lô" longdesc="Tổng kết lô" border=1 style="border-style:outset;height: 30px;width: 30px"></A>		
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Lệnh sản xuất </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">	
            	<tr class="plainlabel">
                	<td>Số lô sản xuất</td>	             
                	<td colspan="1"> 
                		<input type="text" value="<%=lsxBean.getghichu()%>"   name="ghichu">
                		
                	 <td> Căn cứ làm lệnh</td>	                
	                <td colspan="1"> <input type="text" value="<%=lsxBean.getCanCuLamLenh()%>" id="canculamlenh" name="canculamlenh"> </td>
            	
                		
                 </td>
           		</tr>
           		
           		
                <tr class="plainlabel">
	                <td>Chọn Ngày yêu cầu nguyên liệu</td>	                
		            <td colspan="3"> <input type="text" value="<%=lsxBean.getNgaytao()%>" id="ngaybatdau" name="ngaybatdau"   class ="days"> </td>  
		            
		            <%// day la ngay yeu cau nguyen lieu duoc lay tu ngay bat dau cu %> 
                </tr>
                							
                <tr class="plainlabel">
	                <td>Chọn ngày bắt đầu</td>	                
	                <td colspan="1"> <input type="text" value="<%=lsxBean.getNgaybatdau_new()%>" id="ngaybatdau_new" name="ngaybatdau_new"   class ="days"> </td>
	                <td>Chọn ngày kết thúc   </td>	                
	                <td colspan="1"> <input type="text" value="<%=lsxBean.getNgaydukien()%>" id="ngayketthuc" name="ngayketthuc"   class ="days"> </td>
                </tr>
                
                
                

                <tr class="plainlabel">
                 	<td>Phân xưởng   </td>
                	<td colspan="1"> 
                	  <select style="width: 300px" id="nhamayid" name="nhamayid" onchange="submitform()">
                    		<option value=""> </option>
                        	<%
                        		if(nhamayRs != null)
                        		{
                        			try
                        			{
                        			while(nhamayRs.next())
                        			{  
                        			if( nhamayRs.getString("pk_seq").equals(lsxBean.getNhamayId())){ %>
                        				<option value="<%= nhamayRs.getString("pk_seq") %>" selected="selected" ><%=    nhamayRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nhamayRs.getString("pk_seq") %>" ><%=  nhamayRs.getString("ten") %></option>
                        		 <% } } nhamayRs.close();
                        		 	} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                 	</td>
                 	<TD  width="14%"  class="plainlabel" valign="top">Sản phẩm </TD>
                    <TD colspan="1" class="plainlabel" valign="top">
                    	 <%if(Float.parseFloat(lsxBean.getTrangthai()) <=1){ %>
                   		 <select style="width: 300px" id=spma name="spma" onchange="submitform()">
                   		 <option value=""> </option>
                    	<%}else {%>
                    	<select style="width: 300px" id=spma name="spma" >
                    	<%} %>
                        	<%
                        		if(spRs != null)
                        		{
                        			try
                        			{
                        			while(spRs.next())
                        			{  
                        			if( spRs.getString("ma").equals(lsxBean.getSpma())){ %>
                        				<option value="<%= spRs.getString("ma") %>" selected="selected" ><%=  spRs.getString("ma")+"--"+ spRs.getString("ten") %></option>
                        			<%tensp = spRs.getString("ten");
                      			  		PK_SEQ_SP = spRs.getString("PK_SEQ");
                        			}else {  
                        				if(Float.parseFloat(lsxBean.getTrangthai()) <=1){ 
                        				%>
                        				<option value="<%= spRs.getString("ma") %>" ><%=spRs.getString("ma")+"--"+ spRs.getString("ten") %></option>
                        		 <% } } } spRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    	<input type = "hidden" name = "spid" value ="<%=PK_SEQ_SP %>" >		
                    				
                    				<a href="" id="tonkhotp" rel="subcontent_tonkhotp">
          	 							<img alt="BOM" src="../images/vitriluu.png"></a>
  	 								<DIV id="subcontent_tonkhotp" style="position:absolute; visibility: hidden; border: 5px solid #80CB9B;
		                             background-color: white; width:350px; padding: 2px;">
			                   	 <table border="1" style="padding:0" align="center" width="100%">
			                       <tr align="center">		                                
		                                <th width="200px">Mã kho</th>
		                                <th width="100px">Số lượng tồn</th>		                                
		                               
		                       	   </tr>
			                      	<%
			                       		if(RskhoTp != null){
			                       			try{
			                       				while(RskhoTp.next()){ %>
			                       				<tr>
			                       					<td><input type="text" readonly="readonly" value = "<%= RskhoTp.getString("ten") %>" style="width: 100%"></td>
			    		                      		<td><input type="text" readonly="readonly" value = "<%=RskhoTp.getString("soluong") %>" style="width: 100%"></td>
			    			                      
					                       		</tr> 
			                       				<%} RskhoTp.close();
			                       		 	} catch(SQLException ex){ex.printStackTrace();}
			                       		}
				                     %>
						                      	
					                   
			                    </table>		                    
		                     <div align="right">
		                     	<label style="color: red" ></label>
		                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     	<a href="javascript:dropdowncontent.hidediv('subcontent_tonkhotp')"  ">Hoàn tất</a>
		                     </div>
		                </DIV>
                    </TD>
                </tr>
                
                <TR>
                    <TD class="plainlabel" valign="top">Kịch bản sản xuất </TD>
                    <TD colspan="1" class="plainlabel" valign="top">
                   		 <%if(Float.parseFloat(lsxBean.getTrangthai()) <=1){ %>
                    	<select   style="width: 300px" name="kbsxIds" onchange="changeBOM();"  >
                    	<option value=""> </option>
                    	<%}else {%>
                    		<select   style="width: 300px" name="kbsxIds"   >
                    	<%} %>
                    		
                        	<%
                        		if(kbsxRs != null)
                        		{
                        			try
                        			{
                        			while(kbsxRs.next())
                        			{  
                        			if( kbsxRs.getString("pk_seq").equals(lsxBean.getKbsxId())){ %>
                        				<option value="<%= kbsxRs.getString("pk_seq") %>" selected="selected" ><%= kbsxRs.getString("diengiai") %></option>
                        			<%}else { 
                        				if(Float.parseFloat(lsxBean.getTrangthai()) <=1){
                        				
                        				%>
                        				<option value="<%= kbsxRs.getString("pk_seq") %>" ><%= kbsxRs.getString("diengiai") %></option>
                        		 <% } } } kbsxRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>
                    
                    <%-- <TD class="plainlabel" valign="top">Lò hơi</TD>
                    <TD colspan="1" class="plainlabel" valign="top">
                    	<select style="width: 300px" name="lohoiId">
                    		<option value=""> </option>
                        	<%
                        		if(rsLohoi != null)
                        		{
                        			try
                        			{
                        			while(rsLohoi.next())
                        			{
                       				if(rsLohoi.getString("pk_seq").equals(lsxBean.getLohoiId())){ %>
                       					<option value="<%= rsLohoi.getString("pk_seq") %>" selected="selected" ><%= rsLohoi.getString("TEN") %></option>
                        			<%}else
                        			if(rsLohoi.getString("NHAMAY_FK").equals(lsxBean.getNhamayId()) && lsxBean.getLohoiId().length() == 0){ %>
                        				<option value="<%= rsLohoi.getString("pk_seq") %>" selected="selected" ><%= rsLohoi.getString("TEN") %></option>
                        			<%}else { %>
                        				<option value="<%= rsLohoi.getString("pk_seq") %>" ><%= rsLohoi.getString("TEN") %></option>
                        		 <% } } rsLohoi.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD> --%>
                    <td colspan="1" class="plainlabel" valign="top">Số lệnh sản xuất</td>	                
	                <td colspan="1" class="plainlabel" valign="top"> <input type="text" value="<%=lsxBean.getId()%>" id="solenhsanxuat" name="solenhsanxuat" readonly="readonly"> </td>
                    
                </TR>
                
                <TR>	
                     <TD class="plainlabel" valign="top">Số lượng sản xuất </TD>
                    <TD  class="plainlabel" valign="top" >
                    <input type="hidden" name="nhamayid" id="nhamayid"   value="<%= lsxBean.getNhamayId() %>"  />
                    <input type="hidden" name="kichbanspid" id="kichbanspid"   value="<%= lsxBean.getSpId() %>"  />
                    	
                    	<input type="text"  id="soluongsx" name="soluongsx" value="<%= lsxBean.getSoluong() %>" style="text-align: right;" onchange="javascript:submitform();" onkeypress="return keypress(event);"" />
                    
                   </TD>
	                  <TD class="plainlabel" valign="top">Đơn vị tính </TD>
	                    <TD  class="plainlabel" valign="top" colspan="2">       
	                    	<input readonly="readonly" type="text"  id="dvtBOM" name="dvtBOM" value="<%= lsxBean.getDvtBOM() %>" style="text-align: right;" onchange="javascript:submitform();" onkeypress="return keypress(event);"" />
	                    
	               </TD>
                </TR>
                
                
                <tr class="plainlabel">
                	<td>Chọn BOM</td>
               		<td class="plainlabel"  >              
              			<a href="" id="spInit" rel="subcontent1">
          	 							<img alt="BOM" src="../images/vitriluu.png"></a>
  	 						<DIV id="subcontent1" style=" position:absolute; visibility: hidden; border: 5px solid #80CB9B;
		                             background-color: white; width:750px; padding: 2px; z-index:10 ">
		                    <table border="1" style="padding:0" align="center" width="100%">
		                       <tr align="center">	
		                       		<th width="300px">Mã DMVT</th>  	                                
	                                <th width="300px">BOM</th>
	                                 <th width="200px">Văn bản hướng dẫn</th>
	                                 <th width="200px">Ngày hiệu lực</th>
	                                 <th width="100px">Số lượng chuẩn</th>
	                                <th width="200px">Trạng thái</th>		                                
	                                <th width="100px">Chọn</th>
	                       		</tr>
		                      	<%
		                       		if(listbom != null){ 

		                       			try{
		                       				for(int i=0;i<listbom.size();i++){ 
		                       					IErpBom bom=listbom.get(i);
		                       					
		                       					%>
		                       				<tr>
		                       					<td><input type="text" readonly="readonly" value = "<%= bom.getTenBOM() %>" style="width: 100%"></td>
		                       					<td><input type="text" readonly="readonly" value = "<%= bom.getDiengiai() + (bom.getTypeId().equals("1")? " (Ưu tiên)" : "")%>" style="width: 100%"></td>
		                       					<td><input type="text" readonly="readonly" value = "<%= bom.getVanBanHuongDan() %>" style="width: 100%"></td>
		                       					<td><input type="text" readonly="readonly" value = "<%= bom.getHieuluctu() %>" style="width: 100%"></td>
		    		                      		<td><input type="text" readonly="readonly" value = "<%= bom.getSoluongchuan() %>" style="width: 100%"></td>
		    			                      
		    		                      		<td><input type="text" readonly="readonly" value = "<%=bom.getTrangthai() %>" style="width: 100%"></td>
		    			                      	
		    			                      	<td align="center">
				                       			<%if( bom.getId().equals(lsxBean.getBomId())){ %>
				                       				<input type="radio" name="bomid_" value = "<%=  bom.getId() %>" checked="checked"/>
				                       			<%}else { %>
				                       				<input type="radio" name="bomid_" value = "<%= bom.getId() %>"/>
				                       		 	<% } %>
				                       			</td>
				                       		</tr> 
		                       				<%} 
		                       		 	} catch(Exception ex){ex.printStackTrace();}
		                       		
		                       		}
			                     %>
						            				                   
		                    </table>		                    
		                     <div align="right">
		                     	<label style="color: red" ></label>
		                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     	<a href="javascript:dropdowncontent.hidediv('subcontent1')" onclick="javascript:submitform();">Hoàn tất</a>
		                     </div>
		                </DIV>
		                Lệnh sản xuất công nghệ  
               			<%if(lsxBean.getIsLsxCongNghe().equals("1")) {%>
               				<input onchange="javascript:submitform();" type="checkbox" name="islsxcongnghe" value="1" checked="checked">
               			<%}else{ %>
               				<input onchange="javascript:submitform();"  type="checkbox" name="islsxcongnghe" value="1">
               			<%} %>  
           			</td>
           		  	<%if(Float.parseFloat(lsxBean.getTrangthai()) >=1){ %>
               			
                           <td> 
                           Ngày YC thêm  
                            </td>
                            <td>
                            <input type="text" value="<%=lsxBean.getNgayYCThenNL()%>" onchange="reload_themnguyenlieu()"  name="ngayycthem" class ="days">
                            </td>
                            	<td > 
		                  	</td>
                           
               		 
           			<%}else{ %>
           			<td></td>
               		<td class="plainlabel"  >      
               		</td>
           			<%} %>
           			
           			
                </tr>
                 
                <TR class="plainlabel">
					<td colspan="2" class="plainlabel" >
						 
						
						<%-- <% if(lsxBean.getTrangthai().equals("3") ) { %>
	                        <a class="button2" href="javascript:nhapKho()">
	                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập kho từ lệnh sản xuất </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <% } %> --%>
                        
					</td>
					<td colspan="2">
					 	<% if(Float.parseFloat(lsxBean.getTrangthai()) >=2){ %>
						  <a class="button3" href="javascript:yeucauthemnguyenlieu()">
                           <img style="top: -4px;" src="../images/button.png" alt="">Yêu cầu thêm nguyên liệu  </a>
                            
                           <%} %>
                           </td>
				 
					</TR>
                
             </TABLE>
            </div>
     </fieldset>
     <fieldset>
     	<legend class="legendtitle"></legend>
     	<table width="100%">   	 
          	<tr>
         		<td>
          	  		<div id="tabContaier">
          	  			<div class="titleTab"><a  class="active" href="#tab_1">Nguyên liệu yêu cầu</a></div>
						<div class="titleTab"><a href="#tab_2">Định mức chi phí</a></div>
					</div>
      		  		<div class="tabDetails">
      		  			<div id="tab_1" class="tabContents" style="display:block">
		           	   		<table width="100%" align="center" id="vattudenghi"  >
		  	   					<tr class="plainlabel">
		                    	  	<!-- <th width="15%">Công đoạn</th>
		                          	<th width="15%">Mã</th>
		                          	<th width="23%">Tên</th>
		                          	<th width="10%">DVT</th>
		                          	<th width="10%">Hàm ẩm</th>
		                          	<th width="10%">Hàm lượng</th>
		                          	<th width="7%"> SL chuẩn</th>
		                            <th width="7%"> SL đã YC </th>
		                            <th width="7%">Ghi Chú </th>
		                           -->
		                           
		                           
		                           <th width="15%">Công đoạn</th>
		                          	<th width="10%">Mã</th>
		                          	<th width="23%">Tên</th>
		                          	<th width="7%">DVT</th>
		                          	<th width="7%">Hàm ẩm</th>
		                          	<th width="7%">Hàm lượng</th>
		                          	<th width="7%"> SL chuẩn</th>
		                            <th width="7%"> SL đã YC </th>
		                            <th width="14%">Ghi Chú </th>
		                           
		                           
		                           
		                            <% if(Double.parseDouble(lsxBean.getTrangthai())>1 ){ %>
		                            	<th width="7%">Chi tiết</th>
		                            	<th width="7%">Yêu cầu thêm </th>
		                            
		                            <%}else{ %>
		                          		<th width="7%">Xem tồn kho</th>
		                          	<%} %>
		                 
		                    	</tr>
		                    <%int  j =0;  
		                    if(vattuList!=null)
		                   		while (j< vattuList.size()) { 
		                   		   	IDanhmucvattu_SP vt=vattuList.get(j);   
		                   		   	 
		                   		     String syle="width: 100%";
		                   		 	if(!vt.getChon().equals("1")){
		                   		 	 syle="width: 100%;background-color: red;";
		                   		 	}
		                   		       
		                   		   	%> 
		                 		   	<tr class="plainlabel">
		                   	  			<td ><input id="congdoanid1<%=j %>" style="width: 100%" type="hidden" name="congdoanid1"  value="<%=vt.getCongDoanId() %>" > 
		                   	  				<input style="width: 100%" type="hidden" name="bomid1"  value="<%=vt.getBomId() %>" >
		                   	  				<input style="width: 100%" type="text" name="congdoanten1"  value="<%=vt.getTenCongDoan() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="idvt1"  value="<%=vt.getIdVT() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="isTINHHAMAM"  value="<%=vt.getIsTinhHA() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="isTINHHAMLUONG"  value="<%=vt.getIsTinhHL() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="kho1"  value="<%=vt.getkhoid() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="IsNLTieuHao"  value="<%=vt.getIsNLTieuHao() %>" >
		                   	  				<input style="width: 100%" type="hidden" name="chon_"  value="<%=vt.getChon() %>" >
		                   	  				  
		                   	    		</td>
			                            <td><input style="width: 100%" type="text" readonly="readonly" name="mavattu1"  value="<%=vt.getMaVatTu() %>" ></td>
			                            <td ><input style="width: 100%" type="text" readonly="readonly"  name="tenvattu1"  value="<%=vt.getTenVatTu() %>" ></td>
			                            <td ><input style="width: 100%" type="text"  name="donvitinh1"  value="<%=vt.getDvtVT()%>" ></td>
			                            <td ><input style="width: 100%" type="text" readonly="readonly" name="hamam"  value="<%=vt.getHamam()%>" ></td>
			                            <td ><input style="width: 100%" type="text"  readonly="readonly"  name="hamluong"  value="<%=vt.getHamluong()%>" ></td>
			                             <td><input style="<%=syle%>" type="text" readonly="readonly"  name="soluongchuan"  value="<%= formatter.format(Double.parseDouble(vt.getSoLuongChuan().replaceAll(",","")))%>" ></td>
			                             <td><input style="<%=syle%>" type="text" readonly="readonly"  name="soluongdayc"  value="<%= formatter.format(Double.parseDouble(vt.getSoLuong().replaceAll(",","")))%>" ></td>
										  
										 <td><input style="<%=syle%>" type="text"  name="ghichu_vt"  value="<%=vt.getGhichu()%>" ></td>
										  
										  
										  
										    <% if(Double.parseDouble(lsxBean.getTrangthai())>1 ){ %>
										    
										    	<td>
												 	<a href="" id="<%=j%>pubup_dayeucau" rel="subcontendayeucau<%= j %>">
			           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
			           	 							<DIV id="subcontendayeucau<%=j %>" style=" position:absolute; visibility: hidden; border: 1px solid #80CB9B;
						                             background-color: white; max-height:300px;overflow:auto ; width: 1120px; padding:1px;">
						                 		   <table width="100%" align="center" class="tabledetail">
						                        		<tr class="plainlabel" >
				                        		
					                        		<th width="140px">Tên kho</th>
					                        		<th width="120px">MARQUETTE</th>
					                        		<th width="150px">Mã SP</th>
					                        		<th width="200px">Số lô</th>
					                        	    <th width="100px">Phiếu kiểm nghiệm </th>
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
					                            	<th width="50px">Hàm lượng </th>	
					                            	 <th width="50px">Số lượng YC </th>
					                            	 <th width="50px">Số lượng quy đổi </th>
					                            	                        
				                       		 </tr>
				                       		 <%
				                       		  
				                        		List<ISpSanxuatChitiet> list_ct=vt.getListCT_DaYCCK();
				                        		
				                        			 if(list_ct!=null)
				                        			for(int t=0;t<list_ct.size();t++){
				                        				
				                        				ISpSanxuatChitiet sp=list_ct.get(t);
				                        			
				                        				
				                        				if(sp.getIdSpThayThe().length() >0){
				                        				%>
				                        			<tr class='tbdarkrow'>
				                        			<%}else {%>
				                        			<tr class='tblightrow'>
				                        			<%} %>
							                         <td>
							                         	<input type="hidden" size="200px" style="font-size: 12px" name="<%=j%>_khoid_dayc"  value="<%=sp.getKhoId()%>" readonly="readonly" />
							                         		<input type="hidden" size="200px" style="font-size: 12px" name="<%=j%>_binid_dayc"  value="<%=sp.getBinId()%>" readonly="readonly" />
							                         		
							                         		 
							                         		
							                         	<input type="hidden" size="100px" style="font-size: 12px" name="<%=j%>_spId_dayc"  value="<%=sp.getIdSp()%>" readonly="readonly" />
							                         	<input type="hidden" size="100px" name="<%=j%>_spIdThayThe_dayc"  value="<%=sp.getIdSpThayThe()%>" readonly="readonly" />
							                         		<input type="hidden" size="100px" name="<%=j%>_KhuvucId_dayc"  value="<%=sp.getkhuvuckhoId()%>" readonly="readonly" />
							                         		
							                         		<input type="hidden" size="100px" style="font-size: 12px" name="<%=j%>_MARQUETTE_FK_dayc"  value="<%=sp.getMARQUETTE_FK()%>" readonly="readonly" />
							                         		   
							                            	<input type="text" size="100px" style="font-size: 12px" name="<%=j%>_khoten_dayc"  value="<%=sp.getKhoTen()%>" readonly="readonly" /></td>
							                            	 <td>
							                            	<input type="text" size="100px" style="font-size: 12px"  name="<%=j%>_MARQUETTE_dayc"  value="<%=sp.getMARQUETTE() %>" readonly="readonly" /></td>
							                            	 <td>
							                            	<input type="text" size="100px" style="font-size: 12px;background-color: <%=(!sp.getIdSpThayThe().equals(sp.getIdSp())?"blue":"") %>" name="<%=j%>_MASP_dayc" value="<%= sp.getMaSp() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input type="text" size="200px"  style="font-size: 12px" name="<%=j%>_SOLO_dayc" value="<%=sp.getSolo() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input type="text" size="100px" style="font-size: 12px" name="<%=j%>_MAPHIEU_dayc" value="<%=sp.getMAPHIEU() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_MAPHIEU_TINHTINH_dayc" value="<%=sp.getMAPHIEU_TINHTINH() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_MAPHIEU_EO_dayc" value="<%=sp.getMAPHIEU_EO() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_MATHUNG_dayc" value="<%=sp.getMATHUNG() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_MAME_dayc" value="<%=sp.getMAME() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>
							                            	<input type="text" size="150px"   style="font-size: 12px" name="<%=j%>_NGAYHETHAN_dayc" value="<%=sp.getNGAYHETHAN() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" size="150px"   style="font-size: 12px" name="<%=j%>_NGAYNHAPKHO_dayc" value="<%=sp.getNGAYNHAPKHO() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input   style="<%=syle%>"  type="text" size="150px" name="<%=j%>_MANSX_dayc" value="<%=sp.getMANSX() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	
							                            	<td  style="display: none;">
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_khuvuckhoTen_dayc" value="<%=sp.getkhuvuckhoTen() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_bin_dayc" value="<%=sp.getBin() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_HAMAM_dayc" value="<%=sp.getHAMAM() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" size="100px"  style="font-size: 12px" name="<%=j%>_HALUONG_dayc" value="<%=sp.getHAMLUONG() %>" readonly="readonly" />
							                            	</td>
							                            	 
							                            	 	<td>
							                            	<input type="text" size="100px"  name="<%=j%>_Soluongdexuat_dayc" value="<%=sp.getSoluong() %>"  />
							                            	</td>
							                            	 	<td>
							                            	<input type="text" size="100px"  name="<%=j%>_Soluongtonqd_dayc" value="<%=sp.getSoluongtonthute() %>"  />
							                            	</td>
							                          		   
							                          		 </tr>
				                        		<% 
				                        		}
				                       		 %>
				                   			 </table>
				                    		 <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontendayeucau<%= j %>');">Hoàn tất</a></div>
				              			  </DIV>
										</td>
										
										
										    <%} %>
										 <td>
										 	<a href="" id="<%=j%>pubup_ton" rel="subcontenta<%= j %>">
	           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 							<DIV id="subcontenta<%=j %>" style=" position:absolute; visibility: hidden; border: 1px solid #80CB9B;
				                             background-color: white; max-height:300px;overflow:auto ; width: 1120px; padding:1px;">
				                 		   <table width="100%" align="center" class="tabledetail">
				                        		<tr class="plainlabel" >
				                        		
					                        		<th width="140px">Tên kho</th>
					                        		<th width="100px">MARQUETTE</th>
					                        		<th width="200px">Mã SP</th>
					                        		<th width="200px">Số lô</th>
					                        			<th width="100px">Phiếu kiểm nghiệm </th>
					                        		<th width="100px">Phiếu định tính</th>
					                        		<th width="100px">Phiếu EO</th>
					                        		
					                            	<th width="50px">Mã thùng</th>
					                            	<th width="50px">Mã mẻ</th>
					                            	<th width="130px">Ngày hết hạn</th>
					                            	<th width="130px">Ngày nhập kho</th>
					                            		<th width="130px">Mã NSX</th>
					                            		
					                            	<th  style="display: none;" width="100px">Khu vực</th>
					                            		<th width="100px">Vị trí</th>
					                            	<th width="50px">Hàm ẩm</th>
					                            	<th width="50px">Hàm lượng </th>	
					                                <th width="100px">Số lượng tồn </th>		
					                            	 <th width="100px">Số lượng tồn quy đổi </th>
					                            	 <th width="100px">Số lượng đề xuất </th>	                      
				                       		 </tr>
				                       		 <%
				                       		  
				                        		List<ISpSanxuatChitiet> list_ct=vt.getListCTkho();
				                        		
				                        			 
				                        			for(int t=0;t<list_ct.size();t++){
				                        				
				                        				ISpSanxuatChitiet sp=list_ct.get(t);
				                        				
				                        				
				                        			 	syle="font-size: 12px";
				       		                   		 	if(sp.getThangHetHan() <3 ){
				       		                   		 	 syle="font-size: 12px;width: 100%;background-color: yellow;";
				       		                   		 	}
				                        				
				                        				if(sp.getIdSpThayThe().length() >0){
				                        				%>
					                        			<tr class='tbdarkrow' style="<%=syle%>">
					                        			<%}else {%>
					                        			<tr class='tblightrow' style="<%=syle%>">
					                        			<%} %>
					                        			
							                      	   <td>
							                         	<input type="hidden" size="200px" name="<%=j%>_khoid"  value="<%=sp.getKhoId()%>" readonly="readonly" />
							                         		<input type="hidden" size="200px" name="<%=j%>_binid"  value="<%=sp.getBinId()%>" readonly="readonly" />
							                         		<input type="hidden" size="200px" name="<%=j%>_thanghethan"  value="<%=sp.getThangHetHan()%>" readonly="readonly" />
							                         		<input type="hidden" size="100px" name="<%=j%>_nsx_fk"  value="<%=sp.getNSX_FK()%>" readonly="readonly" />
							                         	<input type="hidden" size="100px" name="<%=j%>_spId"  value="<%=sp.getIdSp()%>" readonly="readonly" />
							                         	<input type="hidden" size="100px" name="<%=j%>_spIdThayThe"  value="<%=sp.getIdSpThayThe()%>" readonly="readonly" />
							                         		<input type="hidden" size="100px" name="<%=j%>_KhuvucId"  value="<%=sp.getkhuvuckhoId()%>" readonly="readonly" />
							                         		
							                         		<input type="hidden" size="100px" name="<%=j%>_MARQUETTE_FK"  value="<%=sp.getMARQUETTE_FK()%>" readonly="readonly" />
							                         		   
							                            	<input   style="<%=syle%>" type="text" size="100px" name="<%=j%>_khoten"  value="<%=sp.getKhoTen()%>" readonly="readonly" /></td>
							                            	 <td>
							                            	<input  style="<%=syle%>" type="text" size="100px"  name="<%=j%>_MARQUETTE"  value="<%=sp.getMARQUETTE() %>" readonly="readonly" /></td>
							                            	 <td>
							                            	<input type="text" size="100px" style="font-size: 12px;background-color: <%=(!sp.getIdSpThayThe().equals(sp.getIdSp())?"blue":"") %> " name="<%=j%>_MASP" value="<%= sp.getMaSp() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input   style="<%=syle%>" type="text" size="200px" name="<%=j%>_SOLO" value="<%=sp.getSolo() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input   style="<%=syle%>"  type="text" size="100px" name="<%=j%>_MAPHIEU" value="<%=sp.getMAPHIEU() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_MAPHIEU_TINHTINH" value="<%=sp.getMAPHIEU_TINHTINH() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_MAPHIEU_EO" value="<%=sp.getMAPHIEU_EO() %>" readonly="readonly" /></td>
							                            	
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>" type="text" size="100px" name="<%=j%>_MATHUNG" value="<%=sp.getMATHUNG() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input  style="<%=syle%>"   type="text" size="100px" name="<%=j%>_MAME" value="<%=sp.getMAME() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="150px" name="<%=j%>_NGAYHETHAN" value="<%=sp.getNGAYHETHAN() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="150px" name="<%=j%>_NGAYNHAPKHO" value="<%=sp.getNGAYNHAPKHO() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input   style="<%=syle%>"  type="text" size="150px" name="<%=j%>_MANSX" value="<%=sp.getMANSX() %>" readonly="readonly" />
							                            	</td>
							                            	<td  style="display: none;">
							                            	<input type="text" size="100px" name="<%=j%>_khuvuckhoTen" value="<%=sp.getkhuvuckhoTen() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input    style="<%=syle%>" type="text" size="100px" name="<%=j%>_bin" value="<%=sp.getBin() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_HAMAM" value="<%=sp.getHAMAM() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_HALUONG" value="<%=sp.getHAMLUONG() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_Soluongton" value="<%=sp.getSoluongton() %>" readonly="readonly" />
							                            	</td>
							                            		<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_Soluongtonthute" value="<%=sp.getSoluongtonthute() %>" readonly="readonly" />
							                            	</td>
							                            	 	<td>
							                            	<input  style="<%=syle%>"  type="text" size="100px" name="<%=j%>_Soluongdexuat" value="<%=sp.getSoluong() %>"  />
							                            	</td>
							                            	 
							                          		   
							                          		 </tr>
				                        		<% 
				                        		}
				                       		 %>
				                   			 </table>
				                    		 <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontenta<%= j %>');">Hoàn tất</a></div>
				              			  </DIV>
										</td>
										
										
										
									
										
										
									<%-- <td>
										 	<a href="" id="<%=j%>pubup_ton" rel="ghichu<%= j %>">
	           	 							<img alt="Ghi Chú" src="../images/vitriluu.png"></a>
	           	 							<DIV id="ghichu<%=j %>" style=" position:absolute; visibility: hidden; border: 1px solid #80CB9B;
				                             background-color: white; max-height:300px;overflow:auto ; width: 1120px; padding:1px;">
				                             
				                              <table width="100%" align="center" class="tabledetail">
				                        		<tr class="plainlabel" >
				                        		
					                        	<th width="200px">Ghi Chú</th>
					    	                      <td>
							                       <input style="font-size: 12px"  type="text" size="100px" name="<%=j%>_ghichu" value=""  />
							                      </td>
				                       		 	</tr>
				                       		 </table>
				                       	<div align="right"><a href="javascript:dropdowncontent.hidediv('ghichu<%= j %>');">Hoàn tất</a></div>
				                 		   
			                              	</DIV>
		            				</td> --%>
		            				
		            				
		                		   	
		                			<%
		                			 j++;
		                			} %>
		                		</TABLE>
		                	</div>
               			<div id="tab_2" class="tabContents" style="display:none;">
               				<table cellspacing="1" cellpadding="0" width="100%">
               					<tr class="tbheader">
										<th align="center" width="50%">Tên chi phí</th>
										<th align="center" width="25%">Đơn vị tính</th>
										<th align="center" width="25%">Định mức</th>
									</tr>
									<%for(int i = 0; i < lsxBean.getDinhmucList().size(); i++)
									{ 
										IErpDinhmuc dm = lsxBean.getDinhmucList().get(i); 
									%>
										<tr>
											<td><input type="hidden" name="dmid" value="<%= dm.getId() %>" style="width: 100%">
											<input type="hidden" name="dmloai" value="<%= dm.getLoai() %>" style="width: 100%">
											<input type="text" value="<%= dm.getTen() %>" style="width: 100%" readonly="readonly"/></td>
											<td><input type="text" value="<%= dm.getDVT() %>" style="width: 100%" readonly="readonly"/></td>
											<td><input type="text" name = "dmsoluong" value="<%= formatter3.format(dm.getSoluong())%>" style="width: 98%; readonly="readonly"/></td>
										</tr>
										
									<%} %>
               				</table>
               			</div>
               		</div>
               	</td>
               </tr>
              
				<TR>
	           
                    <td colspan="3"> 
                    		
                       <ul  class="tabs"  id="tabnew">
                     	  <% for(int i=0;i<ListLsxcongdoan.size(); i ++) {
                     		if(i==(ListLsxcongdoan.size()-1)){
                     		  %>
						
							<li class="current" > <a href="#tab<%=i%>">Công đoạn <%=(i+1) %></a></li>
							<%}else{ %>
								<li> <a  class=""  href="#tab<%=i%>">Công đoạn <%=(i+1) %></a></li>
						<%} } %>
						</ul>
						
						<% for(int i=0;i<ListLsxcongdoan.size(); i ++)  {  
							ILenhSXCongDoan lsxcd = ListLsxcongdoan.get(i);
							if(i==2){
							%>
							<div id="tab<%=i %>" class ="tab_content" style="display: block;">
							<%}else{ %>
							<div id="tab<%=i %>" class ="tab_content" style="display:none;">
							
							<%} %>
								<table class="plainlabel"  align="left" width="100%" cellpadding="4" cellspacing="0">
								<tr>
								<td width="12%">
									Phân xưởng 
								</td>
									<td>
									<input  type="hidden" id="lsxcdthutu"  name="lsxcdthutu" value="<%= lsxcd.getThutu()%>" />
									<input  type="hidden" id="lsxcdspid"  name="lsxcdspid" value="<%= lsxcd.getSpid()%>" />
									<input  type="hidden" id="lsxcdspmasp"  name="lsxcdspmasp" value="<%= lsxcd.getMaSp()%>" />
									<input  type="hidden" id="lsxcdbomid"  name="lsxcdbomid" value="<%= lsxcd.getBomId()%>" />
									<input  type="hidden" id="lsxcdnhamayid"  name="lsxcdnhamayid" value="<%= lsxcd.getNhaMayId()%>" />
									<input  type="hidden" id="lsxcdkiemdinhcl"  name="lsxcdkiemdinhcl" value="<%= lsxcd.getKiemDinhCL()%>" />
									<input  type="hidden" id="lsxcdid"  name="lsxcdid" value="<%= lsxcd.getCongDoanId()%>" />
									
										<input readonly="readonly"  style="width: 300px" type="text" id="lsxcdphanxuong"  name="lsxcdphanxuong" value="<%= lsxcd.getPhanXuong()%>" style="text-align: right;"  />
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	 
								        <% 
								        	if(i== ListLsxcongdoan.size()-1){
								        	%>	 <input  style="display: none"  type="checkbox" checked="checked"  id="checkactive<%=lsxcd.getCongDoanId()%>"  name="checkactive<%=lsxcd.getCongDoanId()%>"  value="1" style="text-align: right;"  />
													<label > Hoạt động</label>
								        	<%}else{
								        	if(lsxcd.getActive().equals("1")) { %>
									     	 <input type="checkbox" checked="checked" onchange="viewBom1();" id="checkactive<%=lsxcd.getCongDoanId()%>"  name="checkactive<%=lsxcd.getCongDoanId()%>"  value="1" style="text-align: right;"  />
											<label  for="checkactive<%=lsxcd.getCongDoanId()%>"> Hoạt động</label>
											<%}else{ %>
											 <input type="checkbox" id="checkactive<%=lsxcd.getCongDoanId()%>" onchange="viewBom1();"  name="checkactive<%=lsxcd.getCongDoanId()%>"  value="1" style="text-align: right;"  />
											 <label  for="checkactive<%=lsxcd.getCongDoanId()%>"> Hoạt động</label>
										
										<% }}%>
									</td>
								</tr>
								<tr>
								<td>
									Diễn giải 
								</td>
								<td>
									<input readonly="readonly"  style="width: 300px" type="text" id="lsxcddiengiai"  name="lsxcddiengiai" value="<%= lsxcd.getDiengiai()%>" style="text-align: right;"  />
								</td>
								
								</tr>
								<tr>
								<td>
									Sản phẩm
								</td>
								<td>
									<input  readonly="readonly" style="width: 300px" type="text" id="lsxcdsanpham"  name="lsxcdsanpham" value="<%= lsxcd.getSanPham()%>" style="text-align: right;"  />
								</td>
								</tr>
								<tr>
								<td>
								Số lượng
								</td>
								<td>
									<input type="text" id="lsxcdsoluong"  name="lsxcdsoluong" value="<%= lsxcd.getSoLuong()%>" readonly="readonly" style="text-align: right;"  />
								</td>
								</tr>
								<tr>
								<td>
								Máy  sử dụng
								</td>
								<td>
									<input type="text" id="lsxcdmaysudung"  name="lsxcdmaysudung" value="<%= lsxcd.getMaySuDung()%>" style="text-align: right;" />
								</td>
								</tr>
								<tr>
								<td colspan="3">
									<% 
									double trangthai=0;
									try{
										trangthai=Double.parseDouble( lsxBean.getTrangthai());
									}catch(Exception er){
										
									}
									
									
									if(i == ListLsxcongdoan.size() - 1 &&   trangthai >1){ 
										if(quyenNK[0]==1){
										%>
										 
									<%} }%>
									<%if( i == ListLsxcongdoan.size() - 1 && trangthai >1){ 
										if(quyenKD[0]==1){
										%>
										 	
              								
									<% } } %>
									
									<%if(i == ListLsxcongdoan.size() - 1 && trangthai >1){ 
										if(quyenTH[0]==1){
									   	%>
										
										<a class="button" href=" javascript: tieuhaonguyenlieu(<%=lsxcd.getCongDoanId()%>)">
              								<img style="top: -4px;" src="../images/button.png" alt="">Tiêu hao nguyên liệu </a>&nbsp;&nbsp;&nbsp;
              								
									<%} 
										} %>
								</td> 
								</tr>
								<tr>
								<td colspan="2">
									<%-- <%if(lsxcd.gettrangthai().equals("1")){ %>
										<input type="checkbox" checked="checked" id="check<%=lsxcd.getCongDoanId()%>"  name="check<%=lsxcd.getCongDoanId()%>"  value="1" style="text-align: right;"  /> 
										<label for="check<%=lsxcd.getCongDoanId()%>"> Hoàn tất </label>
									<%}else {%>
										<input type="checkbox" id="check<%=lsxcd.getCongDoanId()%>"   name="check<%=lsxcd.getCongDoanId()%>"  value="1" style="text-align: right;"  />
									<label  for="check<%=lsxcd.getCongDoanId()%>"> Hoàn tất </label>
									<%} %> --%>
								</td>
								
								</tr>
								</table>
					
							</div>
						<%} %>
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
		dropdowncontent.init('yeucauthem_nl', "center", 300, "click");
		dropdowncontent.init('tonkhotp', "left", 400, "click");
		<%if(Double.parseDouble(lsxBean.getTrangthai()) <2){ %>
			
		<%}%>
		
		<% 
		for(int i = 0; i < vattuList.size(); i++)
		{
			IDanhmucvattu_SP vt=vattuList.get(i); 
		%>
			dropdowncontent.init("<%=i%>pubup_ton", "left-top", 900, "click");
			dropdowncontent.init("<%=i%>pubup_dayeucau", "left-top", 900, "click");
			
		<%} %>
		
			TinhTrongLuong();
			replaces();
			 

</script>
<% 
try{
	if(ListLsxcongdoan!=null){
		ListLsxcongdoan.clear();
	}
	if(vattuList!=null){
		vattuList.clear();
	}
}catch(Exception er){
	
}finally{
	lsxBean.DBclose();
}
	
	
	
	//System.out.println(lsxBean.getVuotDungSai());
%>


	
	
<script language="javascript" type="text/javascript">
    <%if(lsxBean.getVuotDungSai()){%>
	   thongbaomsg(<%=lsxBean.getCongDoanCurrent()%>);
    <%}%>
    TinhTrongLuong();
</script> 



</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>