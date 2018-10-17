<%@page import="geso.traphaco.erp.util.DinhDang"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.ISpDetail"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.ISanpham"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.IErpNhanhang_khac"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.erp.util.DinhDang"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.ISpDetail"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.ISanpham"%>
<%@page import="geso.traphaco.erp.beans.nhanhangkhac.IErpNhanhang_khac"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="geso.traphaco.erp.beans.nhanhangtrongnuoc.*"%>
<%@ page import="geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.*"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<% IErpNhanhang_khac nhBean = (IErpNhanhang_khac)session.getAttribute("nhBean"); %>
<% ResultSet dvthList = nhBean.getDvthList(); %>
<% ResultSet poList = nhBean.getDmhList(); %>
<% ResultSet ndnList = nhBean.getNdnList(); %>
<% ResultSet ldnList = nhBean.getLdnList(); %>
<% ResultSet rskhoCxl = nhBean.getrskhoCxl(); %>
<% ResultSet khList = nhBean.getNccList(); %>
<% ResultSet nhomCPList = nhBean.getNhomchiphiRs(); %>
<% ResultSet hoadonNCCList = nhBean.getHdNccList(); %>
<% List<ISanpham> spList = nhBean.getSpList();  %>

<% List<KhuVucKho> listKhuVucKho = nhBean.getListKhuVucKho();  %>
<% List<KhuVucKho> listKhuVucKhoCXL = nhBean.getListKhuVucKhoCXL(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	Utility util = (Utility) session.getAttribute("util"); 
	String sum = (String) session.getAttribute("sum");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoERP/index.jsp");
	}else{	
		int pb= util.getPhongBan(userId); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Diageo - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
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
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
	});	
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spListgetvitri.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
<script language="javascript" type="text/javascript">
	
	function TangDate(ngay, songay)
	{   
		var ArrNgay=ngay.split("-");
		var d = new Date(ArrNgay[0],ArrNgay[1]-1,ArrNgay[2]);
		d.setDate(d.getDate() + parseInt(songay));
		
		month = parseInt(d.getMonth()) + 1;
		if(month < 10)
			month = '0' + month;
		
		date = parseInt(d.getDate());
		if(date < 10)
			date = '0' + date;
					
		//alert('Ngay sau khi tang: ' + d.getFullYear() + '-' + month + '-' + date);
		return d.getFullYear() + '-' + month + '-' + date;
	}

	var t;
	/* function replaces()
	{
	
		var sanpham = document.getElementsByName("idhangmua");
		var ngaynhandukien = document.getElementsByName("ngaynhandukien");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var hansudung = document.getElementsByName("hansudung");
		
		var nccNK = document.getElementById("nccNK").value;
		var muahangId = document.getElementsByName("muahang_fk");		
		
		var soluongdat = document.getElementsByName("soluongdat");
		var conlai = document.getElementsByName("conlai");
		var dungsai = document.getElementsByName("dungsai");
		var soluongMaxNhan = document.getElementsByName("soluongMaxNhan");
		var dongia =document.getElementsByName("dongia");
		var thanhtien=document.getElementsByName("thanhtien");
		var tongthanhtien=document.getElementById("tongthanhtien");
		var sumnhap=0;
		for(var i = 0; i < sanpham.length; i++)
		{
			var slgDat = soluongdat.item(i).value.replace(/,/g,"");
			//var slgMax = parseInt(slgDat) +  Math.round( parseInt(slgDat) * Math.abs( parseInt(dungsai.item(i).value) ) / 100 );
			
			var tongluong = 0;
			var tongtien= 0;
			
			if(dongia.item(i).value.replace(/,/g,"")=="")
				dongia.item(i).value=0;
			
			if(sanpham.item(i).value != "")
			{
                
				var id =  sanpham.item(i).value + '.' + ngaynhandukien.item(i).value  + '.' + muahangId.item(i).value;

				var solo = document.getElementsByName(id + '.solo');
				var soluong = document.getElementsByName(id + '.soluong');
				var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
				var ngayhethan = document.getElementsByName(id + '.ngayhethan');
				
				var mame = document.getElementsByName(id + '.mame');
				var mathung = document.getElementsByName(id + '.mathung');
				var vitri = document.getElementsByName(id + '.vitri');
				var vitriid = document.getElementsByName(id + '.vitriid');
				var maphieu = document.getElementsByName(id + '.maphieu');
				var phieudt = document.getElementsByName(id + '.phieudt');
				var phieueo = document.getElementsByName(id + '.phieueo');
				var marquette = document.getElementsByName(id + '.marquette');
				var hamluong = document.getElementsByName(id + '.hamluong');
				var hamam = document.getElementsByName(id + '.hamam');
				//var error = document.getElementById(id + '.error');
				
				for( var j = 0; j < soluong.length; j++)
				{
					if(solo.item(j).value != "" && soluong.item(j).value != "")
					{
					
					
						var vt = vitri.item(j).value;
						var pos = parseInt(vt.indexOf(" - "));
					   
						if(pos > 0)
						{
							
							vitri.item(j).value=vt.substring(0, pos);
							vt = vt.substr(parseInt(vt.indexOf(" [")+2));
							vitriid.item(j).value = vt.substring(0, parseInt(vt.indexOf("]")));
							 console.log('vi tri id'+ vt)
							/* spMa.item(i).value = sp.substring(0, pos);
							sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
							
							spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
							
							sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
							donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
							
							//VAT
							sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
							vat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
							
							submitform(); */
						//}
						
						//Check solo
						//var flag = false;
						/* for(var k = 0; k < j; k++)
						{
							if(solo.item(k).value == solo.item(j).value)
							{
								flag = true;
								break;
							}
						} */
						
						/*if(flag)
						{
							//var msg = 'Bạn đã nhập lô ' + solo.item(j).value + ' rồi, Vui lòng nhập số lô khác';
							//alert(msg);
							
							solo.item(j).value = "";
							soluong.item(j).value = "";
							ngaysanxuat.item(j).value = "";
							ngayhethan.item(j).value = "";
							mame.item(j).value = "";
							mathung.item(j).value = "";
							vitri.item(j).value = "";
							maphieu.item(j).value = "";
							phieudt.item(j).value = "";
							phieueo.item(j).value = "";
							marquette.item(j).value = "";
							hamluong.item(j).value = "";
							hamam.item(j).value = "";
							
						
							
							
						}
						  
						var slg = soluong.item(j).value.replace(/,/g,"");
						tongluong = parseFloat(tongluong) + roundNumber(parseFloat(slg),4);
						tongtien =  parseFloat(tongtien) + roundNumber((parseFloat(slg)*parseFloat(dongia.item(i).value.replace(/,/g,""))),0);
						
						if(ngaysanxuat.item(j).value != "" && hansudung.item(i).value != "" && ngayhethan.item(j).value=="")
							{
								ngayhethan.item(j).value = TangDate(ngaysanxuat.item(j).value, hansudung.item(i).value);
							}
						*/
						
						/* if(parseFloat(tongluong) > parseFloat(soluongMaxNhan.item(i).value.replace(/,/g,"")))
						{
							tongluong = parseFloat(tongluong) - parseFloat(slg);
							
							var msg = 'Lô ' + solo.item(j).value + ' với số lượng ' + soluong.item(j).value + ' đã vượt quá tổng số lượng đặt và dung sai (' + dungsai.item(i).value + '). Bạn chỉ có thể nhận tối đa là ( ' + soluongMaxNhan.item(i).value + ' ). Vui lòng nhập lại số lượng';
							alert(msg);
							
							//solo.item(j).value = "";
							soluong.item(j).value = "";
							//ngaysanxuat.item(j).value = "";
							//ngayhethan.item(j).value = "";
						} */
				/*	}
					else
					{
						if( solo.item(j).value == "" )
						{
							soluong.item(j).value = "";
							ngaysanxuat.item(j).value = "";
							ngayhethan.item(j).value = "";
							mame.item(j).value = "";
							mathung.item(j).value = "";
							vitri.item(j).value = "";
							maphieu.item(j).value = "";
							phieudt.item(j).value = "";
							phieueo.item(j).value = "";
							marquette.item(j).value = "";
							hamluong.item(j).value = "";
							hamam.item(j).value = "";
						}
					}
				}
				
				soluongnhan.item(i).value =DinhDangDonGia_sole(roundNumber(parseFloat(tongluong),4),4);
				
				thanhtien.item(i).value = DinhDangDonGia_sole(parseFloat(tongtien),0);
				sumnhap =parseFloat(sumnhap)+parseFloat(tongtien)
				if(soluongnhan.item(i).value != "")
					conlai.item(i).value = parseFloat(slgDat) - parseFloat(soluongnhan.item(i).value);
				 var nsxs=document.getElementsByName(id + ".NSXTEN");
				  var nsxid=document.getElementsByName(id + ".NSXID");
				  
				  var sodong =  nsxs.length;
				  var sln = 0;
				  
				  for(var j = 0; j < sodong; j++)
				  {
				   if(nsxs.item(j).value != "")
				   {
				    var nsx = nsxs.item(j).value;
				    var pos = parseInt(nsx.indexOf("-->["));

				    if(pos > 0)
				    {
				     nsxid.item(j).value = nsx.substring(0, pos);
				     nsx = nsx.substr(parseInt(nsx.indexOf("-->[")) + 4);
				     
				     nsxs.item(j).value = nsx.substring(0, parseInt(nsx.indexOf("][")));
				     
				    }
				   }
				   else
				   {
				    
				    nsxid.item(j).value = "";
				    
				   }
				  }
			}
		}
		tongthanhtien.value= DinhDangDonGia_sole(parseFloat(sumnhap),0);
		 
		setTimeout(replaces,100);
		//t = setTimeout(replaces, 100);
	} */

 	
	 function changeSoMe(){
		 var muaHangFk = document.getElementsByName("muahang_fk");
		 var muaHangId = document.getElementsByName("idhangmua");
		 var dem = 0 ;
		 for(dem = 0 ; dem < muaHangFk.length;dem++){
			 var soThung = document.getElementsByName(muaHangId.item(dem).value+"."+muaHangFk.item(dem).value+dem+"."+"sothung");
			 var i =0;
			 for(i = 0 ; i<soThung.length ; i++){
				 if (soThung.item(i).value == ""){
					 alert ("Nhập thông tin số thùng của từng phiếu mua");
					 return ;
				 }
			 }
		 }
		 
		 document.forms['nhForm'].action.value='save';
		 document.forms['nhForm'].theloai.value='changeSoMe';
	     document.forms["nhForm"].submit();
	 }
	
	function ChangeSLT(){
		console.log("vao day choi cai ");
		var soluongnhan = document.getElementsByName("soluongnhan");
		
		var soluongdat = document.getElementsByName("soluongdat");
		var sanpham = document.getElementsByName("idhangmua");
		var muahangId = document.getElementsByName("muahang_fk");	
		var dongia =document.getElementsByName("dongia");
		var thanhtien=document.getElementsByName("thanhtien");
		var conlai = document.getElementsByName("conlai");
		var sumnhap=0;
		for(var i = 0; i < sanpham.length; i++)
		{
			
			var slgDat = soluongdat.item(i).value.replace(/,/g,"");
			//var slgMax = parseInt(slgDat) +  Math.round( parseInt(slgDat) * Math.abs( parseInt(dungsai.item(i).value) ) / 100 );
			
			var tongluong = 0;
			var tongtien= 0;
			if(sanpham.item(i).value != "")
			{
			var id =  sanpham.item(i).value + '.' + muahangId.item(i).value +i;
			var sothung = document.getElementsByName(id + '.sothung');
			var solo = document.getElementsByName(id + '.solo');
			var soluong = document.getElementsByName(id + '.soluongtong');
			var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
			var ngayhethan = document.getElementsByName(id + '.ngayhethan');
			
			for( var j = 0; j < soluong.length; j++)
			{
				if(solo.item(j).value != "" && soluong.item(j).value == "")
				{
					soluong.item(j).value = 0;
				}
				if(solo.item(j).value != "" && soluong.item(j).value != "")
				{					
					//Check solo : không được nhập trùng lô
					var flag = false;
					for(var k = 0; k < j; k++)
					{
						if(solo.item(k).value == solo.item(j).value)
						{
							flag = true;
							break;
						}
					}
					
					if(flag)
					{
						var msg = 'Bạn đã nhập lô ' + solo.item(j).value + ' rồi, Vui lòng nhập số lô khác';
						alert(msg);
						
						solo.item(j).value = "";
						soluong.item(j).value = "";
						ngaysanxuat.item(j).value = "";
						ngayhethan.item(j).value = "";
					}
					 
					var slg =( soluong.item(j).value.replace(/,/g,""));
					tongluong = parseFloat(tongluong) + parseFloat(slg);
					tongtien =  parseFloat(tongtien) + roundNumber((parseFloat(slg)*parseFloat(dongia.item(i).value.replace(/,/g,""))),0);
				}
				else
				{
					if( solo.item(j).value == "" )
					{
						soluong.item(j).value = "";
						ngaysanxuat.item(j).value = "";
						ngayhethan.item(j).value = "";
					}
				}
			}
			
			soluongnhan.item(i).value = tongluong;
			thanhtien.item(i).value = DinhDangDonGia_sole(parseFloat(tongtien),0);
			sumnhap =parseFloat(sumnhap)+parseFloat(tongtien)
			if(soluongnhan.item(i).value != "")
				conlai.item(i).value = parseFloat(slgDat) - parseFloat(soluongnhan.item(i).value);
		}
		}
		tongthanhtien.value= DinhDangDonGia_sole(parseFloat(sumnhap),0);
	}
	
	function replaces()
	{
	console.log("kdkek: ");
		var sanpham = document.getElementsByName("idhangmua");
		var muahangId = document.getElementsByName("muahang_fk");	
		
		var ngaynhandukien = document.getElementsByName("ngaynhandukien");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var hansudung = document.getElementsByName("hansudung");
		
		var nccNK = document.getElementById("nccNK").value;
			
		
		var soluongdat = document.getElementsByName("soluongdat");
		var conlai = document.getElementsByName("conlai");
		var dungsai = document.getElementsByName("dungsai");
		var soluongMaxNhan = document.getElementsByName("soluongMaxNhan");
		var dongia =document.getElementsByName("dongia");
		var thanhtien=document.getElementsByName("thanhtien");
		var tongthanhtien=document.getElementById("tongthanhtien");
		var sumnhap=0;
		for(var i = 0; i < sanpham.length; i++)
		{
			
			var slgDat = soluongdat.item(i).value.replace(/,/g,"");
			//var slgMax = parseInt(slgDat) +  Math.round( parseInt(slgDat) * Math.abs( parseInt(dungsai.item(i).value) ) / 100 );
			
			var tongluong = 0;
			var tongtien= 0;
			
			if(dongia.item(i).value.replace(/,/g,"")=="")
				dongia.item(i).value=0;
			
			if(sanpham.item(i).value != "")
			{
                
			//	var id =  sanpham.item(i).value + '.' + ngaynhandukien.item(i).value  + '.' + muahangId.item(i).value;
				var id =  sanpham.item(i).value + '.' + muahangId.item(i).value +i;
				var sothung = document.getElementsByName(id + '.sothung');
				var solo = document.getElementsByName(id + '.solo');
				var soluong = document.getElementsByName(id + '.soluongtong');
				var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
				var ngayhethan = document.getElementsByName(id + '.ngayhethan');
				
				var nsxs=document.getElementsByName(id + ".nsx");
				var nsxid=document.getElementsByName(id + ".nsxid");
				
				for( var j = 0; j < soluong.length; j++)
				{
								
					 if(nsxs.item(j).value != "")
					  {
					    var nsx = nsxs.item(j).value;
					    var pos = parseInt(nsx.indexOf("-->["));
					    if(pos > 0)
					    {
					     nsxid.item(j).value = nsx.substring(0, pos);
					     nsx = nsx.substr(parseInt(nsx.indexOf("-->[")) + 4);
					     
					     nsxs.item(j).value = nsx.substring(0, parseInt(nsx.indexOf("]["))); 
					    }
					  }
					   else
					   {
					    nsxid.item(j).value = "";
					   } 
				}
				
				
			}
		
		}
		//tongthanhtien.value= DinhDangDonGia_sole(parseFloat(sumnhap),0);
		 
		setTimeout(replaces,100);
		//t = setTimeout(replaces, 100);
	}
	
	function getvitri() 
	{
		alert('message');
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
			return false;
		}
	}
	
	 function saveform()
	 {	
		 /* var sohoadon = document.getElementById("sohoadon");
		 if(sohoadon.value == "")
		 {
			alert("Vui lòng nhập số hóa đơn");
			return;
		 } */
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['nhForm'].action.value='save';
	     document.forms['nhForm'].submit();
	 }
	 
	 function checkTTSanPham()
	 {
		 var solo = document.getElementsByName("solo");
		 var soluongnhan = document.getElementsByName("soluongnhan");
		 var ngaysanxuat = document.getElementsByName("ngaysanxuat");
		
		 for(var h = 0; h < solo.length; h++)
		 {
			if(solo.item(h).value != "")
			{
				if(soluongnhan.item(h).value == "")
				{
					return 'Ban phai nhap so luong nhan';
				}
				if(ngaysanxuat.item(h).value == "")
				{
					return 'Ban phai nhap ngay san xuat';
				}
			}
		 }
		 return '';
	 }
	 
	 function submitform()
	 { 
		 document.forms['nhForm'].action.value='submit';
	     document.forms["nhForm"].submit();
	 }
	 function nextform()
	 { 
		 document.forms['nhForm'].action.value='next';
	     document.forms["nhForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['nhForm'].action.value='changePO';
	     document.forms["nhForm"].submit();
	 }
	 function changeLHH()
	 { 
		 document.forms['nhForm'].action.value='changeLHH';
	     document.forms["nhForm"].submit();
	 }
	 
	 
	 // dinh dang sole  ( giatri, so chu so thap phan)
	 function DinhDangDonGia_sole(num, thapphan) 
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
				sole = sole.substring(0,thapphan+1);
				kq = (((sign)?'':'-') + num) + sole;
			}
			else
				kq = (((sign)?'':'-') + num);
			
			//alert(kq);
			return kq;
		}
		
		
	 	function Dinhdang(element)
		{
			//element.value=DinhDangTien(element.value);
			element.value=DinhDangDonGia_sole(element.value,4);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	 	
	 	
	 
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ErpNhanhangUpdate_khacSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="theloai" />
<input type="hidden" name="id" value='<%= nhBean.getId() %>'>
<input type="hidden" name="loaimh" value='<%= nhBean.getLoaimh() %>'>
<input type="hidden"  id="nccNK" name="nccNK" value='<%= nhBean.getIsNCCNK()%>'>
<input type="hidden"  id="istudong" name="istudong" value='<%= nhBean.getIsTudong()%>'>
<input type="hidden"  id="khonhanid" name="khonhanid" value='<%= nhBean.getKhonhanId()%>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Nhận hàng khác > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpNhanhang_khacSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= nhBean.getMsg() %></textarea>
		         <% nhBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhận hàng</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Ngày chứng từ</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text"  class="days" id="ngaynhanhang" name="ngaynhanhang" 
                    			value="<%= nhBean.getNgaynhanhang() %>"  maxlength="10" readonly /></TD>
				</TR>
                <TR >
                	<TD class="plainlabel" >Loại hàng hóa</TD>
					<TD class="plainlabel" >
						<select name="loaihh" id="loaihh" onChange="submitform();"  style="width: 200px">
							<option value="0">Sản phẩm nhập kho</option>
						</select>
					</TD>
					
					  <TD  class="plainlabel" valign="top" >Số chứng từ</TD>
                     <TD class="plainlabel" valign="top">
                    	<input type="text" id="sochungtu" name="sochungtu" value="<%= nhBean.getSochungtu() %>" /></TD>
					
					
                   
                </TR>
                <TR >
                    <TD class="plainlabel">Đơn vị thực hiện</TD>
                    <TD class="plainlabel">
                        <select name="dvthId" id="dvthId" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(dvthList != null)
                        		{
                        			try
                        			{
                        			while(dvthList.next())
                        			{  
                        			if( dvthList.getString("pk_seq").equals(nhBean.getDvthId())){ %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
                        		 <% } } dvthList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                	 
                	 <TD class="plainlabel" valign="middle" >Yêu cầu nhập</TD>   
                      <TD class="plainlabel">
                    		<input type="hidden" name="sopo" value="<%= nhBean.getSopo_Id()%>">
                    		<input type="hidden" name="hoadonncc_fk" value="<%= nhBean.getHdNccId()%>">
                    		<input type="hidden" name="muahangfk_Id" value="<%= nhBean.getmuahang_fk()%>">
                    		<input type="hidden" name="tigia" value="<%= nhBean.getTigia() %>"  >
                    	 <select name="sohoadonNCC" id="sohoadonNCC" onChange="changePO();" style="width: 200px">
                    	 	<option value=""> </option>
                    	 	<%
                    	 	if(hoadonNCCList!=null){
                    	 		try{
                    	 			while(hoadonNCCList.next()){
                    	 			if(hoadonNCCList.getString("pk_seq").trim().equals(nhBean.getHdNccId())){	
                    	 	%>	
                    	 		<option value="<%= hoadonNCCList.getString("pk_seq") %>" selected="selected"><%= hoadonNCCList.getString("sohoadon") %></option>
                    	 	<%}else{ %>	
                    	 		<option value="<%= hoadonNCCList.getString("pk_seq") %>"><%= hoadonNCCList.getString("sohoadon") %></option>
                    	 	<%} %>	
                    	 	<%}}catch(Exception e){e.printStackTrace();}}%>
                    	 </select>
                    </TD>  
                	 
                </TR>
                <TR>
                    <TD class="plainlabel">Nội dung nhập</TD>
                    <TD class="plainlabel" colspan="3" >
                        <select name="ldnId" id="ldnId"  style="width: 200px">
                        <option value=""></option>
                        	<%
                        		if(ldnList != null)
                        		{
                        			try
                        			{
                        			while(ldnList.next())
                        			{  
                        			if( ldnList.getString("pk_seq").equals(nhBean.getNdnId())){ %>
                        				<option value="<%= ldnList.getString("pk_seq") %>" selected="selected" ><%= ldnList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ldnList.getString("pk_seq") %>" ><%= ldnList.getString("ten") %></option>
                        		 <% } } ldnList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                     
                 	 <!-- <TD class="plainlabel">Khoản mục chi phí</TD> -->
                    
                    
                     <TD class="plainlabel" valign="middle" colspan="3"   style="display:none">
                      	<select name="ncpId" id="ncpId"  style="width: 200px" >
						<option value=""></option>
						<% if (nhomCPList != null)
						{
							while (nhomCPList.next())
							{
								if (nhomCPList.getString("pk_seq").equals(nhBean.getNhomchiphiId()))
								{
								%>
									<option value="<%=nhomCPList.getString("pk_seq")%>" selected="selected"><%=nhomCPList.getString("ten")%></option>
								<% } else { %>
									<option value="<%=nhomCPList.getString("pk_seq")%>"><%=nhomCPList.getString("ten")%></option>
						<% } } nhomCPList.close(); } %>
						</select>
                     </TD>    
                     

                </TR>

				<TR >                                      
                     <TD  class="plainlabel" valign="top" >Diễn giải</TD>
                     <TD class="plainlabel" valign="top" >
                    	<input type="text" id="diengiai" name="diengiai" value="<%= nhBean.getDiengiai() %>" /></TD>
                     <TD  class="plainlabel" valign="top" >Tổng Tiền</TD>
                     <TD class="plainlabel" valign="top">
                    	<input type="text" id="tongthanhtien" name="tongthanhtien" value="<%= nhBean.getTongtien() %>" /></TD>
                </TR>				
			 
            </TABLE>
            <hr> 
            </div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>   	         		
                	<TH align="center" width="10%">Mã sản phẩm</TH>
	                <TH align="center" width="20%">Tên sản phẩm</TH>
	                <TH align="center" width="10%">Kho nhận</TH>
                	<TH align="center" width="8%">ĐVT</TH>
               	 	<TH align="center" width="6%">SL đặt </TH>
               	 	<TH align="center" width="8%">Đơn giá</TH>
               	 	<TH align="center" width="8%">SL đã nhận</TH>
               	 	<TH align="center" width="8%">SL nhận</TH>      
               	 	<TH align="center" width="8%">Thành tiền</TH>           	 	
           	 	
                </TR>
                
                
                <!--  LOAD SẢN PHẨM RA -->
                
                <%
                               
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align:center; " value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 					<input type="hidden" value="<%= sp.getMuahang_fk() %>" name= "muahang_fk">
           	 				</td>
           	 				
           	 				<td  style="display: none">
		                		<input type="text" style="width: 100%; text-align: left;"  value="<%= sp.getSoPO() %>" name= "po" readonly="readonly" >
		                		
		                	</td>
           	 				
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">
           	 					<input type="hidden" value="<%= sp.getHansudung() %>" name= "hansudung">
           	 					<input type="hidden" value="<%= sp.getDungsai() %>" name= "dungsai">
           	 					<input type="hidden" value="<%= sp.getSoluongMaxNhan() %>" name= "soluongMaxNhan">
           	 					<input type="hidden" value="<%= sp.getDongia() %>" name= "dongiaMua">
           	 					<input type="hidden" value="<%= sp.getTiente() %>" name= "tiente">
           	 					<input type="hidden" value="<%= sp.getTigiaquydoi() %>" name= "tygiaquydoi">
           	 					<input type="hidden" value="<%= sp.getDongiaViet() %>" name= "dongiaViet">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "tenhangmua" readonly="readonly" >
           	 				</td>

           	 					<td align="right">
           	 					
	           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getKhonhanTen() %>" name= "khonhanTen" readonly="readonly" >
	           	 					<input type="hidden" style="width: 100%; text-align: left;" value="<%= sp.getKhonhanId() %>" name= "khonhanId"  >

	           	 				</td>
           	 				
           	 				
           	 			
           	 				<td align="left">
           	 				<input type="hidden"   class="days" style="width: 100%; text-align: center; " value="<%= sp.getNgaynhandukien() %>" name= "ngaynhandukien">
           	 				
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDvdl() %>" name= "dvdl" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text"  onkeyup="Dinhdang(this)" style="width: 100%; text-align: right;" value="<%= sp.getSoluongdat() %>" name= "soluongdat" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text"  onchange="replaces()" style="width: 100%; text-align: right;" value="<%= sp. getDongia() %>" name="dongia"  onkeyup="Dinhdang(this)" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongDaNhan() %>" name= "soluongdanhan" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="left">
           	 				<% if(nhBean.getLoaihanghoa().equals("0")){ %>
           	 					<input type="text" style="width: 75%; text-align: right;" value="<%= sp.getSoluongnhan() %>" name= "soluongnhan" readonly="readonly" >

	           	 				 <a href="" id="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() %>" rel="subcontent<%= i %>">
	           	 				 <img alt="Số lô - Số lượng nhận" src="../images/vitriluu.png"></a>
	           	 					
	           	 							
								<DIV id="subcontent<%= i %>"
										style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; 
											background-color: white; width: 1000px; padding: 4px;  z-index:20; height: 400px;overflow-y: scroll;">
										<table width="80%" align="center" class="detail">
											<tr>
												<th width="100px">Số lô</th>
												<th width="100px">NSX</th>
												<th width="100px">Marrquet</th>
												<th width="100px">Số thùng</th>
												<th width="100px">Số lượng tổng</th>
												<th width="100px">Ngày sản xuất</th>
												<th width="100px">Ngày hết hạn</th>
											</tr>
											<%
				                        	List<ISpDetail> spDetailList = sp.getSpDetail();
				                        	int stt = 0;
				                     				                      
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
											<tr>
												<% if(nhBean.getLoaimh().trim().equals("1") || nhBean.getLoaimh().trim().equals("0")){ %>
												<td><input type="text" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".solo" %>"
													value="<%= spDetail.getSolo() %>" onchange="replaces()"
													readonly="readonly" /> 
													
												</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsx" %>"
													value="<%= spDetail.getNsxTen() %>" 
													readonly />
													<input type="hidden" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsxid" %>"
													value="<%= spDetail.getNsxId() %>" 
													readonly />
													</td>
												
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".marrquet" %>"
													value="<%=spDetail.getMarquette()%>" 
													readonly />
													</td>
												
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".sothung" %>"
													value="<%= spDetail.getSothung() %>" id="sothung"
													onchange="replaces()" onkeypress="return keypress(event)" />
													<input type="hidden" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() + i+ ".soluongmax" %>"
													value="<%= spDetail.getSoluongmax() %>"
													style="text-align: right;" onchange="replaces()" />
													</td>
												<td>
													<input type="text" size="100px" 
														name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongtong" %>"
														value="<%=spDetail.getSoluong() %>"
														style="text-align: right;"
														onkeyup="ChangeSLT()" />
													
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "."  + sp.getMuahang_fk() +i+ ".ngaysanxuat" %>"
													value="<%= spDetail.getNgaysx() %>" readonly="readonly"
													onchange="replaces()" readonly="readonly" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan" %>"
													value="<%= spDetail.getNgayhh() %>" readonly="readonly"
													readonly="readonly" /></td>
												<% } else { %>
												<td><input type="text" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".solo" %>"
													value="<%= spDetail.getSolo() %>" onchange="replaces()" />
													
												</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsx" %>"
													value="<%= spDetail.getNsxTen() %>" 
													onchange="replaces()" />
													<input type="hidden" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsxid" %>"
													value="<%= spDetail.getNsxId() %>" 
													onchange="replaces()" />
													</td>
											
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".marrquet" %>"
													value="<%= spDetail.getMarquette() %>" 
													readonly />
													</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".sothung" %>"
													value="<%= spDetail.getSothung() %>" id="sothung"
													onkeypress="return keypress(event)" onchange="replaces()" />

												</td>
												<input type="hidden" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongthung" %>"
													value="" id="soluongthung"
													onkeypress="return keypress(event)" onchange="replaces()"
													style="text-align: right;" />
													
												<td><input type="text" size="100px" 
													name="<%= sp.getId()  + "." + sp.getMuahang_fk() +i+ ".soluongtong" %>"
													value=" <%=spDetail.getSoluong() %>" onkeyup="ChangeSLT()" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuat" %>"
													value="<%= spDetail.getNgaysx()%>" readonly="readonly"
													onchange="replaces()" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan" %>"
													value="<%=spDetail.getNgayhh() %>" readonly="readonly">
												</td>
												<%} %>
											</tr>
											<% stt++; }
				                        	} else{
				                        %>
				                        	<tr>
												<% if(nhBean.getLoaimh().trim().equals("1") || nhBean.getLoaimh().trim().equals("0")){ %>
												<td><input type="text" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".solo" %>"
													value="" onchange="replaces()"
													readonly="readonly" />
													
												</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsx" %>"
													value="" 
													readonly />
													<input type="hidden" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsxid" %>"
													value="" 
													readonly />
													</td>
												
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".marrquet" %>"
													value="" 
													onchange="replaces()" />
													</td>
												
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".sothung" %>"
													value="" id="sothung"
													onchange="replaces()" onkeypress="return keypress(event)" />
													</td>
												<td>
													<input type="text" size="100px" 
														name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongtong" %>"
														value=""
														style="text-align: right;"
														onkeyup="ChangeSLT()" />
													
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "."  + sp.getMuahang_fk() +i+ ".ngaysanxuat" %>"
													value="" readonly="readonly"
													onchange="replaces()" readonly="readonly" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan" %>"
													value="" readonly="readonly"
													readonly="readonly" /></td>
												<% } else { %>
												<td><input type="text" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".solo" %>"
													value="" onchange="replaces()" />
													
												</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsx" %>"
													value="" 
													onchange="replaces()" />
													<input type="hidden" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsxid" %>"
													value="" 
													onchange="replaces()" />
													</td>
												
												<td>
												<input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".marrquet" %>"
													value="" 
													onchange="replaces()" />
													</td>
												<td><input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".sothung" %>"
													value="" id="sothung"
													onkeypress="return keypress(event)" onchange="replaces()" />

												</td>
												
												<td><input type="text" size="100px" 
													name="<%= sp.getId()  + "." + sp.getMuahang_fk() +i+ ".soluongtong" %>"
													value="" onkeyup="ChangeSLT()" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuat" %>"
													value="" readonly="readonly"
													onchange="replaces()" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan" %>"
													value="" readonly="readonly">
												</td>
												<%} %>
											</tr>
				                        
				                        <%} %>
											<% if(!nhBean.getLoaimh().trim().equals("1")){} %>
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td class="rightaligned"><a class="button3"
													href="javascript:changeSoMe()" style="width: 80px;">Ghi nhận</a>
												</td>
											</tr>
											
											  <script type="text/javascript">
	
															jQuery(function()
																{		
																	$("input[name='<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".nsx"%>']").autocomplete("NhaSanXuatList.jsp?");
																});	
															jQuery(function()
																	{		
																		$("input[name='<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".marrquet" %>']").autocomplete("MarrquetList.jsp?sanphamId=<%=sp.getId()%>");
																	});	
												</script>
										</table>

										 <table width="80%" align="center" class="detail">
											<tr>
												<th width="10%">Mã thùng/Số chai</th>
												<th width="25%">Khu vực</th>
												<th width="10%">Mẻ</th>
												<th width="10%">Số lượng</th>
												<th width="10%">Số lô</th>
												<th width="20%">NSX</th>
												<th width="20%">Marrquet</th>
											</tr>
											
											<% for(int j=0; j< sp.getListDetailMeSp().size(); j++){ %>
											<% DetailMeSp temp = sp.getListDetailMeSp().get(j); %>
												<tr>
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".mathungX" %>"
													id="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".mathungX_"+j %>"
													value="<%= temp.getMaThung() %>"  onchange= "TaoRaSoThung(this); "></td>
													<!-- Phần khu vực này sẽ căn cứ theo sản phẩm nếu có kiểm định thì hiển thị khu vực của kiểm định
													Không kiểm định thì hiển thị không kiểm định -->
													
													<% System.out.println("Sản phẩm kiểm định:"+ sp.getIsKiemDinh()); %>
													<td>
														<% if(sp.getIsKiemDinh().equals("0")){ %>
															<select name= "<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".khuvucX" %>"  
															id = "<%= "sp"+ sp.getId() + sp.getMuahang_fk() +i+ "khuvucX_" +j %>"  onchange= "TaoRaKhuVuc(this); " 
																style="width: 270px;">
																<%for (int k=0; k<listKhuVucKho.size(); k++){ %>
																	<% if (listKhuVucKho.get(k).getId().equals(temp.getKhuVuc())){ %>
																		<option value= "<%= listKhuVucKho.get(k).getId()%>" selected > 
																			<%=listKhuVucKho.get(k).getTen() %> </option>
																	<%} else { %>
																		<option value= "<%= listKhuVucKho.get(k).getId()%>"> 
																			<%=listKhuVucKho.get(k).getTen() %> </option>
																	<%} %>
																<%} %>
															</select>
														
														<%} else { %>
														
															<select name= "<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".khuvucX" %>"  
															id = "<%= "sp"+ sp.getId() + sp.getMuahang_fk() +i+ "khuvucX_" +j %>"  onchange= "TaoRaKhuVuc(this); "
															style="width: 270px;">
																<%for (int k=0; k<listKhuVucKhoCXL.size(); k++){ %>
																	<% if (listKhuVucKhoCXL.get(k).getId().equals(temp.getKhuVuc())){ %>
																		<option value= "<%= listKhuVucKhoCXL.get(k).getId()%>" selected > 
																			<%=listKhuVucKhoCXL.get(k).getTen() %> </option>
																	<%} else { %>
																		<option value= "<%= listKhuVucKhoCXL.get(k).getId()%>"> 
																			<%=listKhuVucKhoCXL.get(k).getTen() %> </option>
																	<%} %>
																<%} %>
															</select>
														<%} %>
														
													</td>
													
													<td><input type="text" size="100px" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".meX" %>"
													value="<%= temp.getMe() %>"    id= "<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".meX_"+j %>" onchange= "TaoRaSoMe(this); " onkeypress="return keypress(event)"></td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongX" %>"
													id="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongX_"+j %>"
													value="<%= temp.getSoLuong() %>" onchange= "TaoRaSoLuong(this); "></td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soloX" %>"
													value="<%= temp.getSoLo() %>" readonly="readonly">
													 <!-- Thẻ hidden -->
													 <input type="hidden" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuatX" %>"
													  value = "<%= temp.getNgaySanXuat()%>" >
													   <input type="hidden" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethanX" %>"
													  value = "<%= temp.getNgayHetHan()%>" >
													  
													</td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxX" %>"
													id="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxX_"+j %>"
													value="<%= temp.getNsxTen() %>" readonly>
													<input type="hidden" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxidX" %>"
													id="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxidX_"+j %>"
													value="<%= temp.getNsxId() %>" readonly></td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".marrquetX" %>"
													id="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".marrquetX_"+j %>"
													value="<%= temp.getMarrquet() %>" readonly></td>
												</tr>
											<%}%> 
										</table> 



										<div align="right">
											<label style="color: red"></label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
												href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
										</div>
									</DIV>			
				            <%--                  		<DIV id="subcontent<%=i%>" style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; 
											width: 85%; max-height: 500px; overflow: auto; padding: 4px;">
				                    <table width="100%" align="center">
				                        <tr>
				                        	<th width="100px">Số lô</th>
				                            <th width="100px">Số lượng</th>
				                            <th width="100px">Ngày sản xuất</th>
				                            <th width="100px">Ngày hết hạn</th>
				                            <th width="100px">Mẻ</th>
				                            <th width="100px">Thùng</th>
				                            <th width="100px">Vị trí</th>
				                            <th width="100px">Mã phiếu</th>
				                            <th width="100px">Phiếu ĐT</th>
				                            <th width="100px">Phiếu EO</th>
				                            <th width="100px">Marquette</th>
				                            <th width="100px">Hàm lượng</th>
				                            <th width="100px">Hàm ẩm</th>
				                             <th width="100px">Nhà sản xuất</th>
				                            	
				                        </tr>
				                        <%
				                        	List<ISpDetail> spDetailList = sp.getSpDetail();
				                        	int stt = 0;
				                     				                      
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".solo" %>" value="<%= spDetail.getSolo() %>" onchange="replaces()"/>
							                            </td>
							                            <td>
							                            	<input type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".soluong" %>" value="<%= spDetail.getSoluong() %>" style="text-align: right;" onchange="replaces()"   onkeyup="Dinhdang(this)"/></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".ngaysanxuat" %>" value="<%= spDetail.getNgaysx() %>" readonly="readonly" onchange="replaces()" /></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + "." + sp.getNgaynhandukien()+ "." + sp.getMuahang_fk() + ".ngayhethan" %>" value="<%= spDetail.getNgayhh() %>" readonly="readonly"   />
							                            </td>
							                            
							                            
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".mame" %>" value="<%= spDetail.getMame() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".mathung" %>" value="<%= spDetail.getMathung() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" onkeyup="ajax_showOptions(this,'layvitriNHANHANG',event)" AUTOCOMPLETE="off" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".vitri" %>" value="<%= spDetail.getVitri()==null?"": spDetail.getVitri() %>" onchange="replaces()"/>
							                           		<input type="hidden" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".vitriid" %>" value="<%= spDetail.getVitriid() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".maphieu" %>" value="<%= spDetail.getMaphieu() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".phieudt" %>" value="<%= spDetail.getPhieudt() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".phieueo" %>" value="<%= spDetail.getPhieueo() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".marquette" %>" value="<%= spDetail.getMarquette() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".hamluong" %>" value="<%= spDetail.getHamluong() %>" onchange="replaces()"/>
							                            </td>
							                             <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".hamam" %>" value="<%= spDetail.getHamam() %>" onchange="replaces()"/>
							                            </td>
							                            <td>
							                            <input type="hidden" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".NSXID" %>" value="<%= spDetail.getNsxId() %>" onchange="replaces()"/>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".NSXTEN" %>" value="<%= spDetail.getNsxTen() %>" onchange="replaces()"/>
							                            </td>
							                            
							                           
							                           
							                        </tr>
				                        		<% stt++; }
				                        	} 
				                        %>
				                        <%
				                        	for(int k = 0; k < 6 - spDetailList.size(); k++)
				                        	{
				                        		%>
				                        		<tr>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".solo" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".soluong" %>" value="" style="text-align: right;" onchange="replaces()"  onkeyup="Dinhdang(this)"/>
						                            </td>
						                            
						                            <td>
						                            	<input type="text" size="100px"  class="days" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".ngaysanxuat" %>" 
						                            	  value="" readonly="readonly" onChange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." +  sp.getMuahang_fk() + ".ngayhethan" %>"  class="days" 
						                            	 value="" readonly="readonly"  />
						                            </td>
						                            
						                            
						                            
						                          	<td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".mame" %>" value="" onchange="replaces()" />
						                            </td>
						                            
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".mathung" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" onkeyup="ajax_showOptions(this,'layvitriNHANHANG',event)" AUTOCOMPLETE="off" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".vitri" %>" value="" onchange="replaces()" />
						                            	<input type="hidden" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".vitriid" %>" value="" onchange="replaces()" />
						                            	
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".maphieu" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".phieudt" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".phieueo" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".marquette" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".hamluong" %>" value="" onchange="replaces()" />
						                            </td>
						                              <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".hamam" %>" value="" onchange="replaces()" />
						                            </td>
						                            <td>
							                            <input type="hidden" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".NSXID" %>" value="" onchange="replaces()"/>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".NSXTEN" %>" value="" onchange="replaces()"/>
							                            </td>
						                           
						                        </tr>
				                        	<% stt++; }
				                        
				                     %>
				                     <script type="text/javascript">
	
															jQuery(function()
																{		
																	$("input[name='<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +  ".NSXTEN" %>']").autocomplete("NhaSanXuatList.jsp?");
																});	
															jQuery(function()
																	{		
																		$("input[name='<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() + ".marquette" %>']").autocomplete("MarrquetList.jsp?sanphamId=<%=sp.getId()%>");
																	});	
												</script>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
				                     </div>
				                </DIV> --%>
				                <% } else {  %>
				                	<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongnhan() %>" name= "soluongnhan"  >
				                <% } %>
				                
           	 				</td>
           	 				

           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%=sp.getthanhtien() %>" readonly="readonly" name="thanhtien" >
           	 				</td>

           	 				
           	 				<td align="right" style="display: none">
           	 					<%
           	 						String conlai = sp.getSoluongdat().replaceAll(",", "") ;
           	 						if(sp.getSoluongnhan().length() > 0)
           	 						{
           	 							conlai = DinhDang.dinhdangkho(Double.parseDouble(conlai) - Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "") ));
           	 						}
           	 					%>
           	 					<input type="text" style="width: 100%; text-align: right;" 
           	 						value="<%= conlai %>" name= "conlai" readonly="readonly" >
           	 				</td>
           	 				
           	 				
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div>      
     
     </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript">
	replaces();
	
	<% 
		for(int i = 0; i < spList.size(); i++)
		{
			ISanpham sp = spList.get(i);
	%>
		   dropdowncontent.init('<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk()  %>', "left-bottom", 300, "click");
	<%} %>
</script>

<%
try{

	if(spList!=null){
		spList.clear();
	}
	nhBean.close();
	
}catch(Exception er){
	
}
		 }
%>
<script type='text/javascript' src='../scripts/loadingv2.js'></script>

</BODY>
</html>