<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@page import="geso.traphaco.distributor.util.Utility"%>
<% HttpSession s = request.getSession();
   if (s.isNew()){
	   s.invalidate();
	   System.out.println("New session");
   }else{
	   System.out.println("Old session");
   }
   
%>
<% IDondenghidathangNpp dhBean = (IDondenghidathangNpp)s.getAttribute("dhBean"); 
	
%>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList(); %>
<% String ngaykhoaso = (String)dhBean.getNgayKs(); %>
<% String userId = (String) s.getAttribute("userId");  %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>

<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>
<% String ck = dhBean.getChietkhau();
	System.out.println(ck);
	System.out.println("DondenghidathangNppUpdate.jsp user :" + userId + " , khId: "+ dhBean.getKhId() +" ten: "+ dhBean.getKhTen() + "  ,sessionId: " + s.getId());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();
	})
</script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">
function replaces()
{
	var khTen = document.getElementsByName("khTen"); 
	var smartId = document.getElementsByName("smartId");
	var ckId = document.getElementsByName("ChietKhau");
	var bgstId = document.getElementById("bgstId");
	
	for(i=0; i < smartId.length; i++)
	{
		var tem = smartId.item(0).value;
		if(tem == "")
		{
			khTen.item(0).value = "";
			document.getElementById("khId").value = "";
			ckId.item(0).value = "0";
			bgstId.value = "0";
			break;
		}	
		if(parseInt(tem.indexOf("-->")) > 0)
		{
			var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
			document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
			smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
			tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
			khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
			ckId.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			
			tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
			bgstId.value = tem.substring(0, parseInt(tem.indexOf("]")));
			
			if(khTen.item(0).value != "")
			{				 
				if(document.getElementById("gsbhId").value == "")
				{
					document.forms['dhForm'].action.value = 'submitKh';
			    	document.forms["dhForm"].submit();
				}
				else
				{
					document.forms['dhForm'].action.value = 'submit';
			    	document.forms["dhForm"].submit();
				}
			}
			
			break;
		}	
	}
	
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp1");
	var donvitinh = document.getElementsByName("donvitinh1");
	var dongia = document.getElementsByName("dongia1");
	var chietkhau = document.getElementsByName("spchietkhau1");
	
	var soluong1 = document.getElementsByName("soluong1");
	var soluong2 = document.getElementsByName("soluong2");
	var soluongThung = document.getElementsByName("soluongThung");
	
	var soluong = document.getElementsByName("soluong");
	var tonkho = document.getElementsByName("tonkho1");
	var thanhtien = document.getElementsByName("thanhtien1");
	var ctkm = document.getElementsByName("ctkm1");
	var ckId = document.getElementById("ChietKhau");
	var i;
	for(i=0; i < masp.length; i++)
	{
		if(masp.item(i).value != "")
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("] ["))));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				//tonkho.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("] ["))));
				tonkho.item(i).value = (sp.substring(0, parseInt(sp.indexOf("] ["))));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				soluong1.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				soluong2.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));

			}
			
			var so_luong = soluong.item(i).value;
			while(so_luong.match(","))
			{
				so_luong = so_luong.replace(",","");
			}
			
			if(parseInt(so_luong) > 0)
			{		
				//soluongThung.item(i).value = roundNumber( parseFloat(so_luong) * parseInt(soluong2.item(i).value) / parseInt(soluong1.item(i).value) , 2);
				
				var don_gia = dongia.item(i).value;
				while(don_gia.match(","))
				{
					don_gia = don_gia.replace(",","");
				}
				//don_gia = don_gia.replace(".", "");
				//tinh chiet khau theo san pham
				var tck = (parseFloat(ckId.value) * parseInt(so_luong) * parseFloat(don_gia)) / 100;
				chietkhau.item(i).value = DinhDangTien(roundNumber(tck, 2)); 
				//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var tt = parseInt(so_luong) * parseFloat(don_gia);
				thanhtien.item(i).value = DinhDangTien(roundNumber(tt, 2));
				TinhTien();
			}
			else			
			{
				chietkhau.item(i).value = "";
				thanhtien.item(i).value = "";
			}
			
			if(checkMasp(masp.item(i).value) == true)
			{
				masp.item(i).parentNode.parentNode.bgColor = "#9FC";
			}
		}
		else
		{
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			dongia.item(i).value = "";
			chietkhau.item(i).value = "";
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			tonkho.item(i).value = "";
			//ctkm.item(i).value = "";
			TinhTien();
		}
	}	
	
	setTimeout(replaces, 300);
	}
	
function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien1");
	//var chietkhau = document.getElementsByName("ChietKhau");
	var tongtien = 0;
	for(var i=0; i < thanhtien.length; i++)
	{
		var thanh_tien = thanhtien.item(i).value;
		while(thanh_tien.match(","))
		{
			thanh_tien = thanh_tien.replace(",","");
		}
		if(thanh_tien != "")
		{
			//var thanh_tien = thanhtien.item(i).value.replace(".", "");
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
		}
	}
	var tienchuaVAT = tongtien;
	document.getElementById("SoTienChuaVAT").value = DinhDangTien(tienchuaVAT);

 
	var ck = document.getElementById("ChietKhau").value;
	if(ck == "")
		ck = "0";
	var tienchietkhau = (tienchuaVAT * parseFloat(ck)) / 100;

	document.getElementById("TienChietKhau").value = DinhDangTien(tienchietkhau);

	var tiensauCK = tienchuaVAT- tienchietkhau;
	document.getElementById("SoTienSauCK").value = DinhDangTien(parseFloat(tiensauCK));

	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";

	var tongtiencoVAT = (parseFloat(vat) * tiensauCK) / 100 + tiensauCK;
	document.getElementById("SoTienCoVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
	document.getElementById("SoTienCKKM").value = DinhDangTien(parseFloat(TongchietkhauKM()));
	var SoTienSauCKKM = tongtiencoVAT - parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()); //tong tien khuyen mai co dau (-) dang truoc
	document.getElementById("SoTienSauCKKM").value = DinhDangTien(Math.round(SoTienSauCKKM));
}
	function Tongtienkhuyenmai()
	{
		var ttTrakm = document.getElementsByName("ttTrakm");
		var sum = 0;
		if(ttTrakm.length > 0)
		{
			for(h =0; h < ttTrakm.length; h++)
			{
				var thanh_tien = ttTrakm.item(h).value;
				while(thanh_tien.match(","))
				{
					thanh_tien = thanh_tien.replace(",","");
				}
				if(thanh_tien!= "")
					sum = parseFloat(sum) + parseFloat(thanh_tien);
			}
		}
		return sum;
	}
	
	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("ckTrakm");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			for(h =0; h < ckTrakm.length; h++)
			{
				var thanh_tien = ckTrakm.item(h).value;
				while(thanh_tien.match(","))
				{
					thanh_tien = thanh_tien.replace(",","");
				}
				if(thanh_tien != "")
					sum = parseFloat(sum) + parseFloat(thanh_tien);
			}
		}
		return sum;
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
	
	function checkMasp(masanpham)
	{
		var masp = document.getElementsByName("masp");
		for(i = 0; i < masp.length ; i++)
		{
			if(masp.item(i).value == masanpham)
				return true;
		}
		return false;
	}
	
	function CheckSoluongOld()
	{
		 var trangthaidh = document.getElementById("trangthaiDh").value;
		 if(trangthaidh == 3)
		 {
			 var soluong = document.getElementsByName("soluong");
			 var soluongOld = document.getElementsByName("soluongOld");
			 
			 //alert(soluong.length);
			 //alert(soluongOld.length);
			 
			 for(i = 0; i < soluong.length ; i++)
			 {
				 var thanh_tien = soluong.item(i).value;
				 while(thanh_tien.match(","))
				 {
					  thanh_tien = thanh_tien.replace(",","");
				 }
					
				if(parseInt(thanh_tien) > parseInt(soluongOld.item(i).value))
				{
				    soluong.item(i).value = soluongOld.item(i).value;
				    alert("Đơn hàng đã xuất kho, Bạn không thể nhập số lượng lớn hơn, Vui lòng nhập lại..");
					return;
				}
			 }			
		 }
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
			{//Phím Delete và Phím Back va dau cham, phim Tab
				return;
			}
			return false;
		}
	}
	
	function keypress2(e)
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
			if (keypressed == 13)
			{
				var khTen = document.getElementsByName("khTen"); 
				var smartId = document.getElementsByName("smartId");
				var ckId = document.getElementsByName("ChietKhau");
				var bgstId = document.getElementById("bgstId");
				
				for(i=0; i < smartId.length; i++)
				{
					var tem = smartId.item(0).value;
					if(parseInt(tem.indexOf("-->[")) > 0)
					{
						var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
						document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
						smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
						tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
						khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
						tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
						ckId.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
						
						tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
						bgstId.value = tem.substring(0, parseInt(tem.indexOf("]")));
					}
				}
				
				document.forms['dhForm'].action.value='submitKh';
				document.forms["dhForm"].submit();
			}
	}
	
	function saveform()
	{	
		 if(document.getElementById("aplaikm").value == "true")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi lưu');
			 return;
		 }
		 
		 if(document.getElementById("aplaitb").value == "true")
		 {
			 alert('Đơn hàng này đã đăng ký trưng bày, bạn phải áp lại trưng bày trước khi lưu');
			 return;
		 }
		
		 if(checkSanPham() == false)
		 {
			alert("Lỗi, bạn phải nhập sản phẩm cho đơn hàng...");
			return;
		 } 
		 congDonSPCungMa();
		
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 
		 if(gsbhId.value == "")
		 {
			alert("Vui lòng kiểm tra PTT / GĐ CN 2...");
			return;
		 }
		 
		 if(ddkdId.value == "")
			{
				alert("vui lòng chọn nhân viên bán hàng...");
				return;
			}
			if(khId.value == "")
			{
				alert("Vui lòng chọn khách hàng...");
				return;
			}
			if(khoId.value == "")
			{
				alert("vui lòng chọn kho nhập hàng...");
				return;
			}
			
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(document.getElementById("trangthaiDh").value != 3)
				{
					if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
					{
						alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
						return;
					}
				}
			}
		 }
		
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 var muctindung = document.getElementById("muctindung").value;
		 if(parseFloat(SoTienCoVAT) > parseFloat(muctindung) && parseFloat(muctindung) > 0 )
	     {
		  if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
		  	return;
		 }
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
	 }

	 function checkSanPham()
	 {
		 var masp2 = document.getElementsByName("masp");
		 for(var hh = 0; hh < masp2.length; hh++)
		 {
			if(masp2.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 
	 function ChotForm()
	 {
		 if(document.getElementById("aplaikm").value == "true")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi chốt đơn hàng');
			 return;
		 }
		 
		 if(document.getElementById("aplaitb").value == "true")
		 {
			 alert('Đơn hàng này đã đăng ký trưng bày, bạn phải áp lại trưng bày trước khi chốt đơn hàng');
			 return;
		 }
		
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 
		 if(gsbhId.value == "")
		 {
			alert("Vui lòng kiểm tra PTT / GĐ CN 2...");
			return;
		 }
		 
		 if(ddkdId.value == "")
			{
				alert("vui lòng chọn nhân viên bán hàng...");
				return;
			}
			if(khId.value == "")
			{
				alert("Vui lòng chọn khách hàng...");
				return;
			}
			if(khoId.value == "")
			{
				alert("vui lòng chọn kho nhập hàng...");
				return;
			}
			
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "" || tensp.item(k).value == "")			
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
			}
		 }
		
		 document.getElementById("btnChot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 
	 function Apkhuyenmai()
	 { 
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 var muctindung = document.getElementById("muctindung").value;

		 congDonSPCungMa();
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
		 
		 document.getElementById("btnApKhuyenMai").innerHTML = "<a href='#'><img src='../images/waiting.gif'  border='1' > Processing...</a>";
		 document.forms['dhForm'].action.value='apkhuyenmai';

		 document.getElementById("aplaikm").value = "false";
		 document.getElementById("aplaitb").value = "false";

		 document.forms['dhForm'].submit();
	 }
	 
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{
					//alert(masp.item(ii).value + "-" + masp.item(j).value);			
					if(soluong.item(j).value == "")
						soluong.item(j).value = "0";
					
					soluong.item(ii).value = parseInt(soluong.item(ii).value) + parseInt(soluong.item(j).value);
					masp.item(j).value = "";
				}
			}
		}

		 var tensp1 = document.getElementsByName("tensp1");
		 var donvitinh1 = document.getElementsByName("donvitinh1");
		 var dongia1 = document.getElementsByName("dongia1");
		 var chietkhau1 = document.getElementsByName("spchietkhau1");
	 	 var thanhtien1 = document.getElementsByName("thanhtien1");
	 	var tonkho1 = document.getElementsByName("tonkho1");
		 
		 var tensp = document.getElementsByName("tensp");
		 var donvitinh = document.getElementsByName("donvitinh");
		 var dongia = document.getElementsByName("dongia");
		 var chietkhau = document.getElementsByName("spchietkhau");
	 	 var thanhtien = document.getElementsByName("thanhtien");
	 	var tonkho = document.getElementsByName("tonkho");
	 	 
	 	for(var pos = 0; pos < masp.length; pos++)
	 	{
	 		if(masp.item(pos).value != "")
	 		{
		 		tensp.item(pos).value = tensp1.item(pos).value;
		 		donvitinh.item(pos).value = donvitinh1.item(pos).value;
		 		dongia.item(pos).value = dongia1.item(pos).value;
		 		chietkhau.item(pos).value = chietkhau1.item(pos).value;
		 		thanhtien.item(pos).value = thanhtien1.item(pos).value;
		 		tonkho.item(pos).value = tonkho1.item(pos).value;
	 		}
	 	}
	 }
	 
	 function addRow(name)
		{
			tableName = document.getElementById(name);
			
			var tr = document.createElement("TR");
			var maspAdd = document.createElement("TD");
			var tenspAdd = document.createElement("TD");
			var soluongAdd = document.createElement("TD");
			var dvtinhAdd = document.createElement("TD");
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			
			var soluong1Add = document.createElement("TD");
			var soluong2Add = document.createElement("TD");
			var soluongThungAdd = document.createElement("TD");
			
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "textbox");
			tensp.setAttribute("readonly", "readonly");
			tensp.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
			
			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			/************************************/
			
			var soluong1 = document.createElement("input");
			soluong1.setAttribute("type", "hidden");
			soluong1.value = "";

			soluong1.name = "soluong1";
			soluong1Add.appendChild(soluong1);
			
			
			var soluong2 = document.createElement("input");
			soluong2.setAttribute("type", "hidden");
			soluong2.value = "";

			soluong2.name = "soluong2";
			soluong2Add.appendChild(soluong2);
			
			
			var soluongThung = document.createElement("input");
			soluongThung.setAttribute("type", "textbox");
			soluongThung.value = "";

			soluongThung.name = "soluongThung";
			soluongThungAdd.appendChild(soluongThung);
			
			/************************************/
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			soluong.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			dvt.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			dvt.value = "";
			
			var soluongOld=document.createElement("input");
			soluongOld.setAttribute("type", "hidden");
			soluongOld.setAttribute("onkeypress","return keypress(event)");
			soluongOld.value = "";
			
			soluongOld.name = "soluongOld";
			soluongAdd.appendChild(soluongOld);
			
			dvt.name = 'donvitinh';
			dvtinhAdd.appendChild(dvt);
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			dongia.value = "";
			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
			var chietkhau = document.createElement("input");
			chietkhau.setAttribute("type", "textbox");
			chietkhau.setAttribute("readonly", "readonly");
			chietkhau.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			chietkhau.value = "";
			chietkhau.name = 'spchietkhau';
			chietkhauAdd.appendChild(chietkhau);
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			thanhtien.value = "";
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
			
			var ctkm = document.createElement("input");
			ctkm.setAttribute("type", "textbox");
			ctkm.setAttribute("readonly", "readonly");
			ctkm.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			ctkm.value = "";
			ctkm.name = 'ctkm';
			ctkmAdd.appendChild(ctkm);
			
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			
			tr.appendChild(soluong1Add);
			tr.appendChild(soluong2Add);
			tr.appendChild(soluongThungAdd);
			
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(chietkhauAdd);
			tr.appendChild(thanhtienAdd);
			tr.appendChild(ctkmAdd);
			
			tableName.appendChild(tr);
		}
	 
		function ThemSanPham()
		{
			 var sl = window.prompt("Nhập số lượng sản phẩm muốn thêm", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow("san_pham");
			 }
			 else
			 {
				 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
				 return;
			 }
		 }
		function ajaxOption(id, str)
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
			xmlhttp.open("POST","../../DonHangAjaxSvl?q=" + str + "&id=" + id,true);
			xmlhttp.send();
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
				element.value=DinhDangTien(element.value);
				if(element.value== '' )
				{
					element.value= '';
				}
				CheckSoluongOld();
			}		
	
		
		function QuyRaLe(pos)
		{
			var soluong1 = document.getElementById("soluong1" + pos);
			var soluong2 = document.getElementById("soluong2" + pos);
			var soluongThung = document.getElementById("soluongThung" + pos);
			
			var soluongLe = document.getElementById("soluong" + pos);
			soluongLe.value = "";
			
			if( parseFloat(soluongThung.value) > 0 )
			{
				soluongLe.value = DinhDangTien( Math.round( parseFloat(soluongThung.value) * parseFloat(soluong1.value) /  parseFloat(soluong2.value) ) );
			}
			
		}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%= ngaykhoaso %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<INPUT type="hidden" name="khoTen" value='<%= dhBean.getKhoId() %>'>
<input type="hidden" name="muctindung" id ="muctindung" value='<%= dhBean.getMuctindung() %>'>
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=dhBean.isAplaikhuyenmai() %>'>
<INPUT type="hidden" name="aplaitb" id="aplaitb" value='<%=dhBean.isAplaitrungbay() %>'>
<INPUT type="hidden" name="cokm" id="cokm" value='<%=dhBean.isCokhuyenmai() %>'>
<input type="hidden" name="tck" id ="tck" value='<%=dhBean.getChietkhau() %>'>
<INPUT type="hidden" name="bgstId" id="bgstId" value='<%= dhBean.getBgstId() %>'>
<input type="hidden" id = "khId" name="khId" value='<%= dhBean.getKhId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Xử lý đơn hàng > Đề nghị đặt hàng > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng  <%= dhBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "../../DondenghidathangNppSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" readonly="readonly" style="width: 100%" rows="1" ><%= dhBean.getMessage() %></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "3" cellspacing = "0" width = "100%" border = 0>

											<TR>
											  <TD width="170px" class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">
											 	  <input type="text"  class="days" size="11"
                                    					id="tungay" name="tungay" value="<%= dhBean.getNgaygiaodich() %>" maxlength="10" readonly />
											  </TD>
											  											  
											    <TD class="plainlabel">PTT / GĐ CN 2 </TD>
												<TD colspan="5" class="plainlabel"> 
									 			<SELECT name="gsbhId" id="gsbhId" onChange = "ajaxOption('ddkdTen', this.value)">
										 			 <option value=""></option>
													  <% if(gsbhs != null){
														  try{ while(gsbhs.next()){ 													 
											    			if(gsbhs.getString("pk_seq").equals(dhBean.getGsbhId())){ %>
											      				<option value='<%=gsbhs.getString("pk_seq")%>' selected><%=gsbhs.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=gsbhs.getString("pk_seq")%>'><%=gsbhs.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>														
											</TR>
											                                    
											<TR class="tblightrow">
												<TD class="plainlabel">Kho hàng </TD>
												<TD class="plainlabel"> 
									 			<SELECT name="khoTen" id="khoTen" onchange="submitform()">
													  <% if(kho != null){
														  try{while(kho.next()){
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ session.setAttribute("khoId", kho.getString("khoId")); %>
											      				<option value='<%= kho.getString("khoId") %>' selected onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											      			<%}else{ if(kho.getString("Ten").indexOf("PR") < 0){ %>
											     				<option value='<%= kho.getString("khoId") %>' onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											     			<%}}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
                                    			                                    			
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
												<TD colspan="5" class="plainlabel"> 
									 			<SELECT name="ddkdTen" id="ddkdTen" onChange = "ajaxOption('smartId', this.value)">
										 			 <option value=""></option>
													  <% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("ddkdId").equals(dhBean.getDdkdId())){ %>
											      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>											
											</TR>
											
											<TR >
												<TD class="plainlabel">Mã khách hàng</TD>
												<TD class="plainlabel" width="250px">
                                                 	<input type="text" id="smartId" name="smartId" value="<%= dhBean.getSmartId() %>" onkeypress="keypress2(event);" />
                                                <TD class="plainlabel" width="190px">Tên khách hàng - Địa chỉ</TD>                                                   
                                                <TD class="plainlabel" colspan="5">
                                                	<input type="text" id="khTen" name="khTen" style="width: 100%" value="<%= dhBean.getKhTen() %>" readonly/>  
                                                </TD>                                                           
                                            </TR>  
                                            
											<TR class="tblightrow">
											    <TD class="plainlabel">% Chiết khấu (khách hàng) </TD>
											    <TD class="plainlabel">
											    	<input name="ChietKhau" id="ChietKhau" type="text" value="<%= dhBean.getChietkhau() %>" onkeypress="return keypress(event);"></TD>
									            <TD class="plainlabel">Tổng chiết khấu</TD>
									            <TD class="plainlabel">
									            	<input name="TienChietKhau" id="TienChietKhau" type="text" disabled="disabled" value="<%= dhBean.getTongChietKhau() %>"> VND </TD>
										   	</TR>
											
											<TR class="tblightrow" style="display: none" >
											  <TD class="plainlabel">VAT (%) </TD>
											  <TD class="plainlabel" >
											  	<input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" onkeypress="return keypress(event);"> %</TD>
												
											  <TD class="plainlabel">Tổng số tiền (sau VAT)</TD>
											  <TD class="plainlabel" colspan="3">
											  	<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly value="<%= dhBean.getTongtiensauVAT()%>"> VND </TD>
											   
											  <!-- 											  
											  <TD  class="plainlabel">Tổng số tiền (trước VAT) </TD>	     
										      <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
									       	   -->	
									       	</TR>											  											

										    <TR class="tblightrow">

											  <TD class="plainlabel">Tổng số tiền </TD>	     
										      <TD class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
											  
											  <TD  class="plainlabel">Tổng số tiền (sau chiết khấu)</TD>
											  <TD colspan="3" class="plainlabel"><input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly 
											  	  value="<%=dhBean.getTongtiensauCK()%>" > VND </TD>										  	
										  	</TR>							    

											<TR class="tblightrow">
											  <TD class="plainlabel">Tổng số tiền KM</TD>
											  <TD class="plainlabel">
											  	<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>"> VND </TD>
											  <TD class="plainlabel">Tổng số tiền (sau KM)</TD>
											  <TD class="plainlabel">
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>"> VND </TD>
										  	</TR>
											
											<TR class="tblightrow">
											  <TD class="plainlabel">&nbsp;</TD>
											  <TD class="plainlabel" colspan="3">
											  	<% if (dhBean.getIsDonHangLe().equals("1")){ %> 
					                        		<input type="checkbox" name="IsDonHangLe" value="1" checked="checked" disabled="disabled"><i> Đơn hàng lẻ</i>
					                        	<% } else { %>
					                        		<input type="checkbox" name="IsDonHangLe" value="1" disabled="disabled"><i> Đơn hàng lẻ</i>
					                        	 <% }  %>
					                        	 
					                        	 &nbsp;&nbsp;
					                        	 
					                        	 <% if (dhBean.getIsSampling().equals("1")){ %> 
					                        		<input type="checkbox" name="IsSampling" id="IsSampling" value="1" checked="checked" disabled="disabled" ><i> Đơn hàng Sampling</i>
					                        	<% } else { %>
					                        		<input type="checkbox" name="IsSampling" id="IsSampling" value="1" disabled="disabled"><i> Đơn hàng Sampling</i>
					                        	 <% }  %>
											  </TD>
										  	</TR>
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
												
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="10%">Mã sản phẩm </TH>
													<TH width="21%">Tên sản phẩm</TH>
													<TH width="7%">Tồn hiện tại (Thùng)</TH>
													<TH width="7%">SL Thùng</TH>
													<TH width="7%">SL Lẻ</TH>
													<TH width="7%">ĐVT</TH>
													<TH width="10%">Đơn giá</TH>
													<TH width="7%">Chiết khấu</TH>
													<TH width="9%">Thành tiền</TH>
													<TH width="9%" >CTKM</TH>
												</TR>
									<% 
							int m = 0;	
							if(splist != null){
							ISanpham sanpham = null;
							int size = splist.size();
							
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
									<% if(!dhBean.getTrangthai().equals("3")){ %>
										<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									<%}else{ %>
										<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>" readonly="readonly" style="width:100%" AUTOCOMPLETE="off">
									<%} %>
									</TD>
									<TD align="left" >
										<input name="tensp1" type="text" disabled="disabled" value="<%=sanpham.getTensanpham()%>" style="width:100%; color: black;" >
										<input name="tensp" type="hidden" value="<%=sanpham.getTensanpham()%>" style="width:100%" >
									</TD>
									<TD align = "center" >
								    	<input name="tonkho1" disabled="disabled" type="text" value="<%= sanpham.getTonhientai() %>"  style="width:100%;text-align:center; color: black;">
								    	<input name="tonkho" type="hidden" value="<%= sanpham.getTonhientai() %>"  style="width:100%;text-align:center">
								    </TD>	
								    <TD align = "center" >
								    	<input name="soluong1" id="soluong1<%= m %>" type="hidden" value="<%= sanpham.getSoluong1() %>"  style="width:100%;text-align:right; color: black;">
								    	<input name="soluong2" id="soluong2<%= m %>" type="hidden" value="<%= sanpham.getSoluong2() %>"  style="width:100%;text-align:right; color: black;">
								    	<input name="soluongThung" id="soluongThung<%= m %>" type="text" onkeyup="QuyRaLe(<%= m %>)"  value="<%= sanpham.getSoluongThung() %>"  style="width:100%;text-align:right; color: black;" onkeypress="return keypress(event);" >
								    </TD>
								    								
						        	<% if (spThieuList.containsKey(sanpham.getMasanpham())){ %>

									    <TD align = "center" ><div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
								        <input name="soluong" id="soluong<%= m %>" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" onkeyup="Dinhdang(this)" style="width:100%; color:red ; cursor:pointer; background-color:#0FF; text-align:right">
								        <input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </div></TD>
								    <%}else{ %>
								    		<%
												System.out.println("SO LUONG  "+formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))));
											%>
							        	<TD align = "center" >
								        <input name="soluong" id="soluong<%= m %>" type="text" value="<%=formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" style="width:100%;text-align:right" onkeyup="Dinhdang(this)">
								        <input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </TD>
								    <%} %>
								    
								    <TD align = "center" >
								    	<input name="donvitinh1" disabled="disabled" type="text" value="<%= sanpham.getDonvitinh() %>"  style="width:100%;text-align:center; color: black;">
								    	<input name="donvitinh" type="hidden" value="<%= sanpham.getDonvitinh() %>"  style="width:100%;text-align:center">
								    </TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia1" disabled="disabled" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getDongia()))) %>" style="width:100% ;text-align:right; color: black;">
								    	<input type="hidden" name="dongia" value="<%= sanpham.getDongia() %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="spchietkhau1" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getChietkhau()))) %>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="thanhtien1" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getChietkhau()))) %>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly  style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="ctkm1" type="text"  value="<%= sanpham.getCTKM() %>" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden"  value="<%= sanpham.getCTKM() %>" readonly style="width:100%">
								    </TD>
								</TR>
								<% m++; }}%>
								
								<%if(scheme_tongtien.size() > 0)
								{
									Enumeration<String> keys = scheme_tongtien.keys();
									while(keys.hasMoreElements())
									{
										String key = keys.nextElement(); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" size="18" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= "-" + Float.toString(scheme_tongtien.get(key)) %>" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text" value="<%= key %>" style="width:100%" readonly></TD>
										</TR>
								<% m++; }}%>						
								<%if(scheme_chietkhau.size() > 0)
								{
									Float tonggiatri = Float.parseFloat(dhBean.getTongtiensauVAT());
									Enumeration<String> keyss = scheme_chietkhau.keys();
									while(keyss.hasMoreElements())
									{
										String key = keyss.nextElement(); 
										//long chietkhau = Math.round((scheme_chietkhau.get(key) / 100) * tonggiatri); 
										long chietkhau = Math.round(scheme_chietkhau.get(key)); 
										%>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%;text-align:right" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ckTrakm"  type="text" value="<%= formatter.format(Math.round(chietkhau)) %>" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text"  value="<%= key %>" style="width:100%" readonly></TD>
										</TR>
								<% m++; }}%>								
								<%if(scheme_sanpham.size() > 0)
								{
									ISanpham tkmSp = null;
									int count = 0;
									while(count < scheme_sanpham.size())
									{
										tkmSp = scheme_sanpham.get(count); %>
										<TR class= 'tblightrow'>
											<TD align="center" >
												<input name="maspTrakm" type="text" value="<%= tkmSp.getMasanpham() %>" style="width:100%;text-align:right" readonly>
											</TD>
											<TD align="left" >
												<input name="tenspTraKm" type="text" readonly value="<%= tkmSp.getTensanpham() %>" style="width:100%"></TD>
									        <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
									        <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
									        <TD align = "center" ><input name="slgTraKm" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(tkmSp.getSoluong()))) %>" style="width:100%;text-align:right" readonly></TD>
									        <TD align = "center" ><input name="dvtTrakm" type="text" value="<%= tkmSp.getDonvitinh() %>" readonly style="width:100%;text-align:center"></TD>
										    <TD align = "center" ><input name="dgTrakm" type="text" value="0" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text" style="width:100%" value="<%= tkmSp.getCTKM() %>"  readonly></TD>
										</TR>
								<% count++; m++; }}%>								
								<%
								if(!dhBean.getTrangthai().equals("3")){
									int i=0;
									while(i <= 40){ //Integer.parseInt(dhBean.getSize())){
								%>
								<TR class= 'tblightrow'>
									<TD align="center" >
										<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									</TD>
									<TD align="left" >
										<input name="tensp1" type="text" disabled="disabled" value="" style="width:100%; color:black; ">
										<input name="tensp" type="hidden" value="" style="width:100%">
									</TD>
									<TD align = "center" >
								    	<input name="tonkho1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">
								    	<input name="tonkho" type="hidden"  style="width:100%;text-align:center">
								    </TD>
								    <TD align = "center" >
								    	<input name="soluong1" id="soluong1<%= m %>" type="hidden" value=""  style="width:100%;text-align:right; color: black;">
								    	<input name="soluong2" id="soluong2<%= m %>" type="hidden" value=""  style="width:100%;text-align:right; color: black;">
								    	<input name="soluongThung" id="soluongThung<%= m %>" type="text" onkeyup="QuyRaLe(<%= m %>)" value=""  style="width:100%;text-align:right; color: black;" onkeypress="return keypress(event);">
								    </TD>	
								    <TD align = "center" >
							        <input name="soluong" type="text" id="soluong<%= m %>" value="" style="width:100%; text-align:right" onkeypress="return keypress(event);" AUTOCOMPLETE="off"></TD>
								     <TD align = "center" >
								    	<input name="donvitinh1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">
								    	<input name="donvitinh" type="hidden"  style="width:100%;text-align:center">
								    </TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia1" disabled="disabled" style="width:100% ;text-align:right; color: black;">
								    	<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="spchietkhau1" type="text" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="thanhtien1" type="text" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" readonly  style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="ctkm1" type="text" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden" readonly style="width:100%">
								    </TD>
								</TR>
								<% i++; m++; }} %>	
								</tbody></TABLE>
								<% if(!dhBean.getTrangthai().equals("3")){ %>	
									&nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
									<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<%} %>							
								</TD>
							  </TR> 
						  </TABLE>
						</TD>
					</TR>				
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	replaces();
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#smartId").autocomplete("KhachHangList.jsp");		
	});	
</script>
</form>
</BODY>

</HTML>
<%
try{
	if(!(ddkd == null ))
		ddkd.close();
	if(!(tbh == null))
		tbh.close();
	 if(kh!=null){
		kh.close();
	}
	if(kho!=null){
		kho.close();
	}
	spThieuList=null;
	scheme_tongtien=null;
	scheme_chietkhau=null;
	if(gsbhs!=null){
		gsbhs.close();
	} 
	if(dhBean != null)
	{
		dhBean.DBclose();
	}
	dhBean=null;
	s.setAttribute("dhBean",null);
}catch(Exception er){
	System.out.println("Error DonHang1080:"+er.toString());
}
%>

