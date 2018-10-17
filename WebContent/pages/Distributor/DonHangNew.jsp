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

<% IDonhang dhBean = (IDonhang)s.getAttribute("dhBean"); 
   /* IDonhangList dhlist = (IDonhangList)s.getAttribute("dhList"); */
%>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList(); %>
<% String ngaykhoaso = (String)dhBean.getNgayKs(); %>
<% String userId = (String) s.getAttribute("userId");  %>
<% String userTen = (String) s.getAttribute("userTen");  %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>

<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); %>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>
<% ResultSet ckbsRs = (ResultSet)dhBean.getCkbosungList(); %>
<% ResultSet nvgn = (ResultSet)dhBean.getnvgnRs(); %>
<% ResultSet tructhuocRs = (ResultSet)dhBean.getTructhuocRs(); %>
<% ResultSet kbhRs = dhBean.getKbhRs(); %>

<%  String ck = dhBean.getChietkhau();
	String[] tichluy_scheme = dhBean.getTichLuy_Scheme();
	String[] tichluy_vat = dhBean.getTichLuy_VAT();
	String[] tichluy_tongtien = dhBean.getTichLuy_Tongtien();
	String[] tichluy_cothexoa = dhBean.getTichLuy_CoTheXoa();
	
	String chietkhau_Ids = dhBean.getCkbosungIds();
	String chietkhau_vat = dhBean.getCkbosung_VAT();
	Hashtable<String, Float> chietkhau_tongtien = dhBean.getCkbosung_CK();
	
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

	function goBack() {
	    window.history.back();
	}
	
	function replaces()
	{
		var khTen = document.getElementsByName("khTen"); 
		var smartId = document.getElementsByName("smartId");
		var ckId = document.getElementsByName("ChietKhau");
		var bgstId = document.getElementById("bgstId");
		var khId = document.getElementById("khId");
		
		for(var i=0; i < smartId.length; i++)
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
				smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));				
				tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
				khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("] [")));
				tem = tem.substr(parseInt(tem.indexOf("] [")) + 3);
				ckId.item(0).value = tem.substring(0, parseInt(tem.indexOf("] [")));
				
				tem = tem.substr(parseInt(tem.indexOf("] [")) + 3);
				bgstId.value = tem.substring(0, parseInt(tem.indexOf("] ")));
				
				tem = tem.substr(parseInt(tem.indexOf("] [")) + 3);
				khId.value = tem.substring(0, parseInt(tem.indexOf("]")));
				
				if(khTen.item(0).value != "")
				{				 
					/* if(document.getElementById("gsbhId").value == "")
					{
						document.forms['dhForm'].action.value = 'submitKh';
				    	document.forms["dhForm"].submit();
					}
					else
					{ */
						document.forms['dhForm'].action.value = 'submitKh';
				    	document.forms["dhForm"].submit();
					//}
				}
				
				break;
			}	
		}
		
		var IsSampling = document.getElementById("IsSampling");
		
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp1");
		var donvitinh = document.getElementsByName("donvitinh1");
		var dongia = document.getElementsByName("dongia1");
		var chietkhau1 = document.getElementsByName("spchietkhau1");
		var chietkhau = document.getElementsByName("spchietkhau");
		
		var soluong = document.getElementsByName("soluong");
		var tonkho = document.getElementsByName("tonkho1");
		var thanhtien = document.getElementsByName("thanhtien1");
		var ctkm = document.getElementsByName("ctkm1");
		
		var ckId = document.getElementById("ChietKhau");
		var pt_ck = ckId.value;
		if(pt_ck == '')
			pt_ck = 0;
		
		var ptVAT = document.getElementsByName("ptVAT1");
		var sptienthue = document.getElementsByName("sptienthue");
		
		var tungay = document.getElementById("tungay").value;
		
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		
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
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")) );
					dongiaGOC.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")) );
					
					chietkhau.item(i).value = 0;
					chietkhau1.item(i).value = 0;
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
	
					tonkho.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("] ["))));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					ptVAT.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				}
				
				var so_luong = soluong.item(i).value.replace(/,/g, "");
				if(parseInt(so_luong) > 0)
				{		
					/* var don_gia = dongia.item(i).value.replace(/,/g, "");
					
					chietkhau.item(i).value = 0;
					chietkhau1.item(i).value = 0;
					var tck = 0;
	
					var _vat = ptVAT.item(i).value;
					if(_vat == '')
						_vat = '0';
					
					var thueVAT = Math.round( Math.round( parseFloat(so_luong) * parseFloat(don_gia) ) * parseFloat( _vat ) / 100 );
							
					//var thueVAT = ( ( parseFloat(so_luong) * parseFloat(don_gia) ) - parseFloat(tck) ) * parseFloat( _vat ) / 100;
					sptienthue.item(i).value = DinhDangTien(thueVAT); 
					
					//var tt =  ( ( parseInt(so_luong) * parseFloat(don_gia) ) - parseFloat(tck) ) + parseFloat(thueVAT) ;				
					var tt =  (  Math.round ( parseFloat(don_gia) * parseFloat(so_luong) ) - parseFloat(tck) ) + parseFloat(thueVAT) ;
					thanhtien.item(i).value = DinhDangTien(tt);
					
					TinhTien(); */
				}
				else			
				{
					chietkhau.item(i).value = "";
					sptienthue.item(i).value = "";
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
				ptVAT.item(i).value = "";
				thanhtien.item(i).value = "";
				tonkho.item(i).value = "";
				//ctkm.item(i).value = "";
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien1");
		var chietkhau = document.getElementsByName("spchietkhau1");
		var dongia = document.getElementsByName("dongia1");
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		var soluong = document.getElementsByName("soluong");
		var ptVAT = document.getElementsByName("ptVAT1");
		var dongiaDACOVAT = document.getElementsByName("dongiaDACOVAT");
		var sptienthue = document.getElementsByName("sptienthue");
		
		var tongtien = 0;
		var tongchietkhau = 0;
		var tongvat = 0;
		
		var tongtien_dongiaGOC = 0;
		
		for(var i = 0; i < thanhtien.length; i++)
		{
			if( dongia.item(i).value != '' && soluong.item(i).value != '' )
			{
				var don_gia = dongia.item(i).value.replace(/,/g, "");
				var so_luong = soluong.item(i).value.replace(/,/g, "");
				var don_giaGOC = dongiaGOC.item(i).value.replace(/,/g, "");

				//Chiết khấu đã được chia vào giá lúc LƯU VÀ ÁP KM rồi
				//var chiet_khau = chietkhau.item(i).value.replace(/,/g, "");
				//if(chiet_khau != '')
					//tongchietkhau = parseFloat(tongchietkhau) +  parseFloat(chiet_khau);
				
				var _vat = ptVAT.item(i).value;
				if( _vat == '' )
					_vat = 0;
				
				dongiaDACOVAT.item(i).value = DinhDangTien( Math.round( parseFloat( don_giaGOC ) * ( 1 + parseFloat( _vat ) / 100.0 ) ) );
				

				var _tienVAT = Math.round( Math.round( parseFloat(so_luong) * parseFloat(don_gia) ) * parseFloat( _vat ) / 100 );
				tongvat = parseFloat(tongvat) +  parseFloat(_tienVAT);

				var _dgGOC_coVAT = Math.round( parseFloat( don_giaGOC ) * ( 1 + parseFloat( _vat ) / 100.0 ) );
				tongtien_dongiaGOC += ( parseFloat( _dgGOC_coVAT ) * parseFloat( so_luong ) );

				tongtien = parseFloat(tongtien) +  Math.round ( parseFloat(don_gia) * parseFloat(so_luong) ) + parseFloat(_tienVAT); 
				
				thanhtien.item(i).value = DinhDangTien( Math.round( parseFloat( _dgGOC_coVAT ) * parseFloat( so_luong ) ) );
				
			}
		}
		
		//var tienchuaVAT = tongtien;
		var tienchuaVAT = tongtien_dongiaGOC;
		document.getElementById("SoTienChuaVAT").value = DinhDangTien(tienchuaVAT);
	
		//tongchietkhau = Math.abs( parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()) );
		
		var tienchietkhau = tongchietkhau;
		document.getElementById("TienChietKhau").value = DinhDangTien(tienchietkhau);
	
		var tiensauCK = tienchuaVAT - tienchietkhau;
		document.getElementById("SoTienSauCK").value = DinhDangTien(parseFloat(tiensauCK));

		var tongtiencoVAT = tongtien;
		document.getElementById("SoTienCoVAT").value = DinhDangTien( Math.round(tongtiencoVAT));
		//document.getElementById("SoTienCKKM").value = DinhDangTien( Math.abs( parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()) ) );
		document.getElementById("SoTienCKKM").value = '0';
		
		//var SoTienSauCKKM = tongtiencoVAT - Math.abs( parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()) ); 
		var SoTienSauCKKM = tongtien; 
		document.getElementById("SoTienSauCKKM").value = DinhDangTien(Math.round(SoTienSauCKKM));
		
		document.getElementById("SoTienVAT").value = DinhDangTien(parseFloat(tongvat));
		document.getElementById("SoTienSauVAT").value = DinhDangTien(parseFloat(tongvat) + parseFloat(SoTienSauCKKM));
		
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
		return roundNumber(sum, 0);
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
		return roundNumber(sum, 0);
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
	
	  function format2(n, dec, currency) {
	    	var _n = parseFloat(n.toString().replace(/\$|\,/g,'')); 
	    	if(isNaN(_n)) 
	    	{
	    		_n = 0;
	    	}
	    	
	    	if(dec == -1) 
	    	{
	    		return currency + _n.replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
	    	}  else {
	    		return currency + _n.toFixed(dec).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
	    	}
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
				document.forms['dhForm'].enterKH.value='1';
				document.forms["dhForm"].submit();
			}
	}
	
	function saveform()
	{
		 /* if(document.getElementById("aplaikm").value == "true")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi lưu');
			 return;
		 } */
		 
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
		 var dongia = document.getElementsByName("dongia1");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 var nvgnId = document.getElementById("nvgnId");
		 
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
			
			/* if(nvgnId.value == "")
			{
				alert("vui lòng chọn nhân viên giao nhận...");
				return;
			}  */
			
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "" && soluong.item(k).value != "" && soluong.item(k).value != "0" )
			{
				if(document.getElementById("trangthaiDh").value != 3)
				{
					if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
					{
						alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
						return;
					}
					
					if(dongia.item(k).value=="")
					{
						alert("Yêu cầu nhập giá sản phẩm!");
						return;
					} 
				}
			}
		 }
		
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 /* var muctindung = document.getElementById("muctindung").value;
		 if(parseFloat(SoTienCoVAT) > parseFloat(muctindung) && parseFloat(muctindung) > 0 )
	     {
		  if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
		  	return;
		 } */
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
	 }
	
	function chotform()
	{	
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
		 var dongia = document.getElementsByName("dongia1");
		 
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
			 if(masp.item(k).value != "" && soluong.item(k).value != "" && soluong.item(k).value != "0" )
			 {
				if(document.getElementById("trangthaiDh").value != 3)
				{
					if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
					{
						alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
						return;
					}
					
					if(dongia.item(k).value=="")
					{
						alert("Yêu cầu nhập giá sản phẩm!");
						return;
					}
				}				
			 }
		 }
		
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 /* var muctindung = document.getElementById("muctindung").value;
		 if(parseFloat(SoTienCoVAT) > parseFloat(muctindung) && parseFloat(muctindung) > 0 )
	     {
		  if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
		  	return;
		 } */
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
		 
		 document.getElementById("btnChot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	 	 document.forms['dhForm'].action.value='chotdonhang';
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
	 
	 function Apkhuyenmai()
	 { 
		 congDonSPCungMa();
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
			
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var dongia = document.getElementsByName("dongia1");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 var nvgnId = document.getElementById("nvgnId");
		 
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
			 if(masp.item(k).value != "" && soluong.item(k).value != "" && soluong.item(k).value != "0" )
			 {
				if(document.getElementById("trangthaiDh").value != 3)
				{
					if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
					{
						alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
						return;
					}
					
					if(dongia.item(k).value=="")
					{
						alert("Yêu cầu nhập giá sản phẩm!");
						return;
					} 
				}
			 }
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
		var khoNVBH1 = document.getElementsByName("khoNVBH1");
		var khoNVBH = document.getElementsByName("khoNVBH");
		for(var k = 0; k < khoNVBH1.length; k++ )
		{
			if(khoNVBH1.item(k).checked == true)
				khoNVBH.item(k).value = "1";
			else
				khoNVBH.item(k).value = "0";
		}
		
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value && khoNVBH.item(ii).value == khoNVBH.item(j).value )
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
		 //var chietkhau1 = document.getElementsByName("spchietkhau1");
	 	 var ptVAT1 = document.getElementsByName("ptVAT1");
	 	 var thanhtien1 = document.getElementsByName("thanhtien1");
	 	 var tonkho1 = document.getElementsByName("tonkho1");
		 
		 var tensp = document.getElementsByName("tensp");
		 var donvitinh = document.getElementsByName("donvitinh");
		 var dongia = document.getElementsByName("dongia");
		 //var chietkhau = document.getElementsByName("spchietkhau");
		 var ptVAT = document.getElementsByName("ptVAT");
	 	 var thanhtien = document.getElementsByName("thanhtien");
	 	 var tonkho = document.getElementsByName("tonkho");
	 	 
	 	for(var pos = 0; pos < masp.length; pos++)
	 	{
	 		if(masp.item(pos).value != "")
	 		{
		 		tensp.item(pos).value = tensp1.item(pos).value;
		 		donvitinh.item(pos).value = donvitinh1.item(pos).value;
		 		dongia.item(pos).value = dongia1.item(pos).value;
		 		//chietkhau.item(pos).value = chietkhau1.item(pos).value;
		 		ptVAT.item(pos).value = ptVAT1.item(pos).value;
		 		thanhtien.item(pos).value = thanhtien1.item(pos).value;
		 		tonkho.item(pos).value = tonkho1.item(pos).value;
	 		}
	 	}

	 }
	 
	 
	 
		function ThemSanPham(pos)
		{
			 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow(pos);
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
			var soluong1 = document.getElementById("soluong1_" + pos);
			//alert(soluong1.value);
			var soluong2 = document.getElementById("soluong2_" + pos);
			//alert(soluong2.value);
			var soluongThung = document.getElementById("soluongThung_" + pos);
			
			var soluongLe = document.getElementById("soluong_" + pos);
			soluongLe.value = "";
			
			//alert("So luong le la: " + soluongLe.value);
			
			if( parseFloat(soluongThung.value) > 0 )
			{
				var slgLe = Math.round( parseFloat(soluongThung.value) * parseFloat(soluong1.value) /  parseFloat(soluong2.value) );
				//alert('So luong la: ' + slgLe);
				//soluongLe.value = DinhDangTien( slgLe );
				
				soluongLe.value = slgLe;
			}
			
		}
		
		function EnterKhachHang()
		{
			<% if(dhBean.getEnterKH().equals("1")) { %>
				var masp = document.getElementsByName("masp");
				masp.item(0).focus();
			<% } %>
		}
		
		function XoaChietKhauTL(scheme)
		{
			/* var xmlhttp;
			var id = document.getElementById("dhID").value;
			
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
				   if(xmlhttp.responseText != 'OK')
				    {
					   alert('Lỗi khi xóa tích lũy: ' + xmlhttp.responseText);
					}
				   else
				   {
					   window.location.assign("http://www.w3schools.com");
					   window.location.replace("http://google.com");
					   window.location.href = 'http://www.google.com';
					   window.open('http://www.google.com'); 
					   //window.location.replace("../../DonHangUpdateSvl?");
				   }
			   }
			}
			
			xmlhttp.open("GET","../../DonHangAjaxSvl?scheme=" + scheme + "&id=" + id, true);
			xmlhttp.send(); */
			
			document.getElementById("schemeTL_XOA").value = scheme;
			document.forms['dhForm'].action.value='xoatichluy';
		    document.forms["dhForm"].submit();
		}
		
		function FormatTien(e)
		{

			if(e.value == '-' )
				return;
			
			if(e.value == '')
				e.value = '0';
			else
			{
				e.value = DinhDangDonGia(e.value);
			}
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

		function TaoMoi()
		{
			document.forms['dhForm'].action.value='taomoi';
		    document.forms["dhForm"].submit();
		}
		
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="EnterKhachHang();" >
<form name="dhForm" method="post" action="../../DonhangUpdateSvl">
<input type="hidden" name="userId" value='<%=userId %>'> 
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id="ngaykhoaso" value='<%= ngaykhoaso %>'> 
<input type="hidden" name="action" value='1'> 
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'> 
<input type="hidden" name="muctindung" id="muctindung" value='<%= dhBean.getMuctindung() %>'> 
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=dhBean.isAplaikhuyenmai() %>'>
<INPUT type="hidden" name="aplaitb" id="aplaitb" value='<%=dhBean.isAplaitrungbay() %>'> 
<INPUT type="hidden" name="cokm" id="cokm" value='<%=dhBean.isCokhuyenmai() %>'> 
<input type="hidden" name="tck" id="tck" value='<%=dhBean.getChietkhau() %>'>
<INPUT type="hidden" name="bgstId" id="bgstId" value='<%= dhBean.getBgstId() %>'> 
<input type="hidden" id="khId" name="khId" value='<%= dhBean.getKhId() %>'> 
<input type="hidden" name="enterKH" value='<%= dhBean.getEnterKH() %>'>
<input type="hidden" name="schemeTL_XOA" id="schemeTL_XOA" value=''>
<input type="hidden" name="LOAINHAPHANPHOI" id="LOAINHAPHANPHOI" value='<%= dhBean.getLoaiNppId() %>'>

<%-- <input type="hidden" name="trangthaiSEARCH" value='<%= dhlist.getTrangthai() %>'>
<input type="hidden" name="sohoadon" value='<%= dhlist.getSohoadon() %>'>
<input type="hidden" name="khachhang" value='<%= dhlist.getKhachhang() %>'>
<input type="hidden" name="mafast" value='<%= dhlist.getMafast() %>'>
<input type="hidden" name="tungay" value='<%= dhlist.getTungay() %>'>
<input type="hidden" name="denngay" value='<%= dhlist.getDenngay() %>'>
<input type="hidden" name="tdvId" value='<%= dhlist.getDdkdId() %>'> --%>


		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE border=0 width="100%" cellpadding="2" cellspacing="0">
						<TBODY>
							<TR height="22">
								<TD align="left">
									<TABLE width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD align="left">
												<TABLE width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<TR height="22">
														<TD align="left" class="tbnavigation">Quản lý bán hàng > NPP bán hàng > Đơn hàng bán > Tạo mới
														</TD>
														<TD align="right" class="tbnavigation">Chào mừng <%= userTen %> 	&nbsp;</TD>
													</TR>
												</TABLE></TD>
										</TR>
									</TABLE>
									<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR class="tbdarkrow">
														<%-- <TD width="30" align="left">
															<A href="../../DonhangSvl?userId=<%=userId %>">
																<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve"	style="border-style: outset">
															</A>
														</TD>
														<TD width="2" align="left">&nbsp;</TD> --%>
														<TD width="30" align="left">
															<!-- <div id="btnSave">
																<A href="javascript:saveform()">
																	<img src="../images/Save30.png" alt="Luu lai" title="Luu lai" border="1" longdesc="Luu lai" style="border-style: outset">
																</A>
															</div> -->
															
															<A href= "javascript:TaoMoi();" >
        														<img src="../images/New.png" alt="Tạo đơn hàng khác"  title="Tạo đơn hàng khác" border="1px" style="border-style:outset"></A>
															
														</TD>
														<TD align="left">&nbsp;</TD>
													</TR>
												</TABLE></TD>
										</TR>
									</TABLE>
									<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<TD align="left" colspan="4" class="legendtitle">
												<FIELDSET>
													<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
													<textarea name="dataerror" style="width: 100%; font-weight: bold" readonly="readonly" style="width: 100%" rows="1"><%= dhBean.getMessage() %></textarea>
												</FIELDSET></TD>
										</tr>
										<TR>
											<TD align="left">
												<FIELDSET>
													<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
													<TABLE cellpadding="3" cellspacing="0" width="100%" border=0>
														
														<TR>
															<TD width="170px" class="plainlabel">Nhà phân phối </TD>
															<TD colspan="3" class="plainlabel" >
																<SELECT name="tructhuocId" onchange="submitform()" >
																	<option value=""></option>
																	<% if(tructhuocRs != null) {
														  				try{ while(tructhuocRs.next()){ 													 
											    							if(tructhuocRs.getString("pk_seq").equals(dhBean.getTructhuocId())){ %>
																		<option value='<%=tructhuocRs.getString("pk_seq")%>' selected><%= tructhuocRs.getString("ten") %></option>
																	<%}else{ %>
																		<option value='<%=tructhuocRs.getString("pk_seq")%>'><%= tructhuocRs.getString("ten") %></option>
																	<%}}} catch(Exception e){} tructhuocRs.close(); } %>
																</SELECT>
															</TD>
														</TR>
														
														<TR>
															<TD width="170px" class="plainlabel">Ngày giao dịch </TD>
															<TD class="plainlabel"><input type="text"
																class="days" size="11" onchange="submitform()"
																id="tungay" name="ngaygiaodich"
																value="<%= dhBean.getNgaygiaodich() %>" maxlength="10"
																readonly /></TD>

															<TD class="plainlabel">Giám sát</TD>
															<TD colspan="5" class="plainlabel"><SELECT
																name="gsbhId" id="gsbhId"
																onChange="ajaxOption('ddkdTen', this.value)">
																	<!-- <option value=""></option> -->
																	<% if(gsbhs != null){
														  try{ while(gsbhs.next()){ 													 
											    			if(gsbhs.getString("pk_seq").equals(dhBean.getGsbhId())){ %>
																	<option value='<%=gsbhs.getString("pk_seq")%>' selected><%=gsbhs.getString("ten") %></option>
																	<%}else{ %>
																	<option value='<%=gsbhs.getString("pk_seq")%>'><%=gsbhs.getString("ten") %></option>
																	<%}}}catch(java.sql.SQLException e){}} %>
															</SELECT>
															</TD>
														</TR>

														<TR class="tblightrow">
															<TD class="plainlabel">Kho hàng</TD>
															<TD class="plainlabel"><SELECT name="khoTen"
																id="khoTen" onchange="submitform()">
																	<% if(kho != null){
														  try{while(kho.next()){
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ session.setAttribute("khoId", kho.getString("khoId")); %>
																	<option value='<%= kho.getString("khoId") %>' selected
																		onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"
																		; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
																	<%}else{ if(kho.getString("Ten").indexOf("PR") < 0){ %>
																	<option value='<%= kho.getString("khoId") %>'
																		onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"
																		; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
																	<%}}}}catch(java.sql.SQLException e){} }%>
															</SELECT>
															</TD>

															<TD class="plainlabel">Nhân viên bán hàng</TD>
															<TD colspan="5" class="plainlabel"><SELECT
																name="ddkdTen" id="ddkdTen"
																onChange="ajaxOption('gsbhId', this.value)">
																	<option value=""></option>
																	<% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("ddkdId").equals(dhBean.getDdkdId())){ %>
																	<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
																	<%}else{ %>
																	<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
																	<%}}}catch(java.sql.SQLException e){}} %>
															</SELECT>
															</TD>
														</TR>
														<TR > 
										                    <TD class="plainlabel" >Kênh bán hàng </TD>
										                    <TD class="plainlabel" colspan="3" >
										                    	<select name = "kbhId"  id="kbhId"  class="select2" style="width: 200px"   >
										                    		<option value="" ></option>
										                        	<%
										                        		if(kbhRs != null)
										                        		{
										                        			try
										                        			{
										                        			while(kbhRs.next())
										                        			{  
										                        			if( kbhRs.getString("pk_seq").equals(dhBean.getKbhId())){ %>
										                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
										                        			<%}else { %>
										                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
										                        		 <% } } kbhRs.close();} catch(Exception ex){}
										                        		}
										                        	%>
										                    	</select>
										                    </TD>  
										                </TR>
														<TR>
															<TD class="plainlabel">Mã khách hàng</TD>
															<TD class="plainlabel" width="250px"><input type="text" id="smartId" name="smartId" value="<%= dhBean.getSmartId() %>" onkeypress="keypress2(event);" />
															<TD class="plainlabel" width="190px">Tên khách hàng - Địa chỉ</TD>
															<TD class="plainlabel"><input type="text" id="khTen" name="khTen" style="width: 400px" value="<%= dhBean.getKhTen() %>" readonly /></TD>
														</TR>
														<TR>
															<TD class="plainlabel">Chủ cửa hiệu</TD>
															<TD class="plainlabel"><input type="text" name="chucuahieu" value="<%= dhBean.getChucuahieu() %>" readonly="readonly" /></TD>
															
															<TD class="plainlabel" valign="top">Nhân viên giao nhận</TD>
															<TD class="plainlabel" valign="top" >
															  	<SELECT name="nvgnId" id="nvgnId" >
																    <% try{ while(nvgn.next()){ 
															    			if(nvgn.getString("PK_SEQ").equals(dhBean.getnvgnId()   )){ %>
															      				<option value='<%=nvgn.getString("PK_SEQ")%>' selected>
															      				<%=nvgn.getString("Ten") %></option>
															      			<%}else{ %>
															     				<option value='<%=nvgn.getString("PK_SEQ")%>'>
															     				<%=nvgn.getString("Ten") %></option>
															     			<%}}}catch(Exception e){} %>
																 </SELECT>
															</TD>
															
															<%-- <td class="plainlabel" colspan="2" >
			                                                	&nbsp;&nbsp;
																	 Đơn hàng khác
																	 
																	 <%  if (dhBean.getDonhangKhac().equals("1")){%>
									                                      <input name="donhangKhac" type="checkbox" value ="1" checked onchange="submitform()">
									                                <%} else {%>
									                                       <input name="donhangKhac" type="checkbox" value ="1" onchange="submitform()">
									                                <%} %> 
									                                
									                                &nbsp;&nbsp;
																	 Đề nghị trả CK tháng
																	 
																	 <%  if (dhBean.getDenghitrackThang().equals("1")){%>
									                                      <input name="denghitraCK" type="checkbox" value ="1" checked >
									                                <%} else {%>
									                                       <input name="denghitraCK" type="checkbox" value ="1" >
									                                <%} %> 
									                                
									                                &nbsp;&nbsp;
																	 Đơn quà tặng
																	 
																	 <%  if (dhBean.getIsDonquatang().equals("1")){%>
									                                      <input name="donquatang" type="checkbox" value ="1" checked >
									                                <%} else {%>
									                                       <input name="donquatang" type="checkbox" value ="1" >
									                                <%} %> 
			                                                </td>    --%>
														</TR>

														<TR class="tblightrow" style="display: none;" >
															<TD class="plainlabel">% Chiết khấu (khách hàng)</TD>
															<TD class="plainlabel"><input name="ChietKhau"
																id="ChietKhau" type="text"
																value="<%= dhBean.getChietkhau() %>"
																onkeypress="return keypress(event);" readonly="readonly">
																
																<input name="ChietKhaucu" style="display: none;"
																id="ChietKhau" type="text"
																value="<%= dhBean.getChietkhaucu() %>"
																onkeypress="return keypress(event);" readonly="readonly">
																
															</TD>
															<TD class="plainlabel">Tổng chiết khấu</TD>
															<TD class="plainlabel"><input name="TienChietKhau"
																id="TienChietKhau" type="text" disabled="disabled"
																value="<%= dhBean.getTongChietKhau() %>"> VND</TD>
														</TR>

														<TR class="tblightrow" style="display: none">
															<TD class="plainlabel">VAT (%)</TD>
															<TD class="plainlabel"><input name="VAT" id="VAT"
																type="text" value="<%= dhBean.getVAT() %>"
																onkeypress="return keypress(event);"> %</TD>

															<TD class="plainlabel">Tổng số tiền (sau VAT)</TD>
															<TD class="plainlabel" colspan="3"><input
																name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly
																value="<%= dhBean.getTongtiensauVAT()%>"> VND</TD>

														</TR>

														<TR class="tblightrow" style="display: none;" >

															<TD class="plainlabel">Tổng số tiền (sau chiết khấu)</TD>
															<TD colspan="3" class="plainlabel">
																<input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly value="<%=dhBean.getTongtiensauCK()%>"> VND</TD>
															
															<TD class="plainlabel" style="display: none;" >Tổng số tiền KM</TD>
															<TD class="plainlabel" style="display: none;" >
																<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>"> VND</TD>
																
														</TR>

														<TR class="tblightrow">
															
															<TD class="plainlabel">Doanh số</TD>
															<TD class="plainlabel"  >
																<input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly> 
															</TD>
																
															<TD class="plainlabel"><span class="plainlabel" style="color: red;" >Chiết khấu </span></TD>
															<TD class="plainlabel">
										                    	<input type="text" readonly="readonly"  value="<%= dhBean.getPTChietkhauBHKM() %>" id="txtPTChietkhauBH" name="txtPTChietkhauBH" style="text-align: right; width: 200px; " />
										                    </TD>
														</TR>
														
														<TR class="tblightrow">
															
															<TD class="plainlabel">Tổng tiền tích lũy</TD>
															<TD class="plainlabel"  >
																<input name="" id="" type="text" value="" readonly> 
															</TD>
																
															<TD class="plainlabel"><span class="plainlabel" style="color: red;" >Tổng thanh toán </span></TD>
										                    <TD class="plainlabel">
										                    	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>" >
															</TD>
														</TR>

														<TR class="tblightrow" style="display: none;">
															<TD class="plainlabel">Tổng số tiền VAT</TD>
															<TD class="plainlabel">
																<input name="SoTienVAT" id="SoTienVAT" type="text" readonly value=""> VND</TD>
															<TD class="plainlabel">Tổng số tiền (sau VAT)</TD>
															<TD class="plainlabel">
																<input name="SoTienSauVAT" id="SoTienSauVAT" type="text" readonly value=""> VND</TD>
														</TR>

														<TR>
										                	<TD class="plainlabel" >Ghi chú </TD>
										                    <TD class="plainlabel" colspan="3" >
										                    	<input type="text"  name="ghichu" value="<%= dhBean.getGhiChu() %>" style="width: 700px;" />
										                    </TD>
										                </TR>

														<TR class="tblightrow">
															<TD class="plainlabel" colspan='4'>
																<div id="btnApKhuyenMai">
																	<a class="button2" href="javascript:Apkhuyenmai()">
																		<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại </a>
																</div>
															</TD>
														</TR>
													</TABLE>
												</FIELDSET></TD>
										</TR>

										<TR>
											<TD>
												<TABLE width="100%" border="0" cellpadding="0"
													cellspacing="1">
													<tbody id="san_pham">
														<TR class="tbheader">
															<TH width="15%">Mã sản phẩm</TH>
															<TH width="30%">Tên sản phẩm</TH>
															<TH width="8%">Tồn hiện tại</TH>
															<TH width="1%" style="display: none;">Số lượng <br />(Thùng )</TH>
															<TH width="7%">Số lượng</TH>
															<TH width="8%">ĐVT</TH>
															
															<TH width="1%" style="display: none;" >Đơn giá</TH>
															<TH width="1%" style="display: none;" >VAT</TH>
															<TH width="1%" style="display: none;" >Chiết khấu</TH>
															<TH width="1%" style="display: none;" >Tiền thuế</TH>
															
															<th width="12%" >Đơn giá <br/>(Sau VAT)</th>
															
															<TH width="7%">Thành tiền</TH>
															<TH width="10%">CTKM</TH>
															<TH width="1%" style="display: none;">Kho</TH>
														</TR>
														<% 
														int m = 0;	
														if(splist != null){
														ISanpham sanpham = null;
														int size = splist.size();
														
														while (m < size){
															sanpham = splist.get(m); %>
														<TR class='tblightrow'>
															<TD align="left">
																<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>"	onkeyup="ajax_showOptions(this,'abc',event)" style="width: 100%" AUTOCOMPLETE="off">
															</TD>
															<TD align="left">
																<input name="tensp1" type="text" disabled="disabled" value="<%=sanpham.getTensanpham()%>" style="width: 100%; color: black; cursor: pointer;">
																<input name="tensp" type="hidden" value="<%=sanpham.getTensanpham()%>" style="width: 100%">
															</TD>
															<TD align="center"><input name="tonkho1" disabled="disabled" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getTonhientai().replaceAll(",", "")))) %>" style="width: 100%; text-align: center; color: black;">
																<input name="tonkho" type="hidden" style="width: 100%; text-align: center" value="<%= sanpham.getTonhientai().replaceAll(",", "") %>" >
																</TD>
															<TD align="center" style="display: none;">
																<input name="soluong1" id="soluong1_<%= m %>" type="hidden" value="<%= sanpham.getSoluong1() %>" style="width: 100%; text-align: right; color: black;">
																<input name="soluong2" id="soluong2_<%= m %>" type="hidden" value="<%= sanpham.getSoluong2() %>" style="width: 100%; text-align: right; color: black;">
																<input name="soluongThung" id="soluongThung_<%= m %>" type="text" onkeyup="QuyRaLe(<%= m %>)" value="<%= sanpham.getSoluongThung() %>"	style="width: 100%; text-align: right; color: black;" onkeypress="return keypress(event);">
															</TD>

															<% if (spThieuList.containsKey(sanpham.getMasanpham())){ %>

															<TD align="center">
																<div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
																	<input name="soluong" id="soluong_<%= m %>" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" onkeyup="Dinhdang(this)" style="width: 100%; color: red; cursor: pointer; background-color: #0FF; text-align: right">
																	<input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>">
																</div>
															</TD>
															<%}else{ %>
															<TD align="center">
																<input name="soluong" id="soluong_<%= m %>" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" style="width: 100%; text-align: right" onkeyup="Dinhdang(this)"> 
																<input 	name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>">
															</TD>
															<%} %>
															
															 
								    						<TD align="center"><input name="donvitinh1" disabled="disabled" type="text" value="<%= sanpham.getDonvitinh() %>" style="width: 100%; text-align: center; color: black;"> 
															<input name="donvitinh" type="hidden" value="<%= sanpham.getDonvitinh() %>" style="width: 100%; text-align: center"></TD>
															
															<TD align="center">
																<input type="text" name="dongiaDACOVAT" value="" style="width: 100%; text-align: right;" disabled="disabled" >
																<input type="hidden" name="dongiaGOC" value="<%= sanpham.getDongiaGoc() %>"  >
																
																<input type="hidden" name="dongia1" disabled="disabled" value="<%= sanpham.getDongia() %>" style="width: 100%; text-align: right; color: black;">
																<input type="hidden" name="dongia" value="<%= sanpham.getDongia() %>" readonly style="width: 100%; text-align: right">
															</TD>
															
															<TD align="center" style="display: none;" ><input type="text" name="ptVAT1" disabled="disabled" value="<%= sanpham.getKhoNVBH() %>" style="width: 100%; text-align: right; color: black;">
																<input type="hidden" name="ptVAT" value="<%= sanpham.getKhoNVBH() %>" style="width: 100%; text-align: right; color: black;">
															</TD>

															<TD align="center" style="display: none;" ><input name="spchietkhau1" type="text" value="<%= sanpham.getChietkhau()%>" disabled="disabled" style="width: 100%; text-align: right; color: black;">
																<input name="spchietkhau" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly 	style="width: 100%; text-align: right"></TD>
															<TD align="center" style="display: none;" >
																<input name="sptienthue" type="text" value="<%= sanpham.getChietkhau()%>" disabled="disabled" style="width: 100%; text-align: right; color: black;">
															</TD>
															<TD align="center">
																<input name="thanhtien1" type="text" value="<%= formatter.format(Math.round(Double.parseDouble( ( sanpham.getChietkhau().trim().length() <= 0 ? "0" : sanpham.getChietkhau() ) ))) %>" disabled="disabled" style="width: 100%; text-align: right; color: black;">
																<input name="thanhtien" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly style="width: 100%; text-align: right"></TD>
															<TD align="center"><input name="ctkm1" type="text" value="<%= sanpham.getCTKM() %>" disabled="disabled" style="width: 100%; color: black;"> 
																<input name="ctkm" type="hidden" value="<%= sanpham.getCTKM() %>" readonly style="width: 100%"></TD>
															<TD align="center" style="display: none;">
																<% if(sanpham.getKhoNVBH().equals("1")) { %> 
																<input 	name="khoNVBH1" type="checkbox" value="1" checked="checked" onclick="return false;"> 
																<% } else { %>
																<input name="khoNVBH1" type="checkbox" value="1" onclick="return false;"> <% } %> 
																<input 	name="khoNVBH" type="hidden" value="<%= sanpham.getKhoNVBH() %>"></TD>
																
																<% m++; } %>
														</TR>
														<% } %>

														<%if(scheme_tongtien.size() > 0)
														{
															Enumeration<String> keys = scheme_tongtien.keys();
															while(keys.hasMoreElements())
															{
																String key = keys.nextElement(); %>
														<TR class='tblightrow'>
															<TD align="center">
																<input type="text" size="18" readonly>
															</TD>
															<TD align="left">
																<input type="text" readonly	style="width: 100%">
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" readonly style="width: 100%">
															</TD>
															<TD align="center">
																<input type="text" value="" readonly style="width: 100%">
															</TD>
															<TD align="center">
																<input type="text" value="" readonly style="width: 100%">
															</TD>
															<TD align="center">
																<input name="ttTrakm" type="text" value="<%= "-" + Float.toString(scheme_tongtien.get(key)) %>" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center"><input name="trakmSpScheme" type="text" value="<%= key %>" style="width: 100%" readonly>
															</TD>
															<TD align="center" style="display: none;">
																<input name="khoNVBH1" type="checkbox" value="0" onclick="return false;"> <input name="khoNVBH" type="hidden" value="0">
															</TD>
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
														<TR class='tblightrow'>
															<TD align="center">
																<input type="text" style="width: 100%; text-align: right" readonly>
															</TD>
															<TD align="left">
																<input type="text" readonly style="width: 100%"> 
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center" >
																<input type="text" value="" readonly style="width: 100%">
															</TD>
															<TD align="center" style="display: none;" >
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center" style="display: none;" >
																<input type="text" value="" readonly style="width: 100%">
															</TD>
															<TD align="center" style="display: none;" >
																<input name="ckTrakm" type="text" value="<%= formatter.format(Math.round(chietkhau)) %>" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center"><input type="text" value="<%= formatter.format(Math.round(chietkhau)) %>" readonly style="width: 100%; text-align: right;">
															</TD>
															<TD align="center"><input name="trakmSpScheme" type="text" value="<%= key %>" style="width: 100%; " readonly>
															</TD>
															<TD align="center" style="display: none;"><input name="khoNVBH1" type="checkbox" value="0" onclick="return false;"> 
																<input name="khoNVBH" type="hidden" value="0"></TD>
														</TR>
														<% m++; }}%>
														<%if(scheme_sanpham.size() > 0)
														{
															ISanpham tkmSp = null;
															int count = 0;
															while(count < scheme_sanpham.size())
															{
																tkmSp = scheme_sanpham.get(count); %>
														<TR class='tblightrow'>
															<TD align="center"><input name="maspTrakm" type="text" value="<%= tkmSp.getMasanpham() %>" style="width: 100%; text-align: right" readonly>
															</TD>
															<TD align="left"><input name="tenspTraKm" type="text" readonly value="<%= tkmSp.getTensanpham() %>" style="width: 100%">
															</TD>
															<TD align="center"><input type="text" value="" style="width: 100%" readonly>
															</TD>

															<TD align="center"><input name="slgTraKm" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(tkmSp.getSoluong()))) %>" style="width: 100%; text-align: right" readonly>
															</TD>
															<TD align="center"><input name="dvtTrakm" type="text" value="<%= tkmSp.getDonvitinh() %>" readonly style="width: 100%; text-align: center">
															</TD>
															<TD align="center"><input name="dgTrakm" type="text" value="" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center">
																<input type="text" value="" style="width: 100%" readonly>
															</TD>
															<TD align="center" style="display: none;" ><input name="" type="text" value="0" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center" style="display: none;" ><input name="" type="text" value="0" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center" style="display: none;" ><input name="" type="text" value="0" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center"><input name="trakmSpScheme" type="text" style="width: 100%; text-align: right;" value="<%= tkmSp.getCTKM() %>" readonly>
															</TD>
															<TD align="center" style="display: none;">
																<% if(tkmSp.getKhoNVBH().equals("1")) { %> 
																<input name="khoNVBH1" type="checkbox" value="1" checked="checked" onclick="return false;"> 
																<% } else { %>
																<input name="khoNVBH1" type="checkbox" value="1" onclick="return false;"> <% } %> 
																<input name="khoNVBH" type="hidden" value="<%= tkmSp.getKhoNVBH() %>"></TD>
														</TR>
														<% count++; m++; }}%>

														

														<%
														if(!dhBean.getTrangthai().equals("3")){
															int i=0;
															while(i <= 40){ //Integer.parseInt(dhBean.getSize())){
														%>
								
														<TR class='tblightrow'>
															<TD align="center">
																<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width: 100%" AUTOCOMPLETE="off">
															</TD>
															<TD align="left">
																<input name="tensp1" type="text" disabled="disabled" value="" style="width: 100%; color: black;"> 
																<input name="tensp" type="hidden" value="" style="width: 100%">
															</TD>
															<TD align="center">
																<input name="tonkho1" disabled="disabled" type="text" style="width: 100%; text-align: center; color: black;">
																<input name="tonkho" type="hidden" style="width: 100%; text-align: center"></TD>
															<TD align="center" style="display: none;">
																<input name="soluong1" id="soluong1_<%= i %>" type="hidden" value="" style="width: 100%; text-align: right; color: black;">
																<input name="soluong2" id="soluong2_<%= i %>" type="hidden" value="" style="width: 100%; text-align: right; color: black;">
																<input name="soluongThung" id="soluongThung_<%= i %>" type="text" onkeyup="QuyRaLe(<%= i %>)" onkeypress="return keypress(event);" value="" style="width: 100%; text-align: right; color: black;">
															</TD>
															<TD align="center">
																<input name="soluong" id="soluong_<%= i %>" type="text" value="" style="width: 100%; text-align: right" onkeyup="Dinhdang(this)">
															</TD>
															<TD align="center">
																<input name="donvitinh1" disabled="disabled" type="text" style="width: 100%; text-align: center; color: black;">
																<input name="donvitinh" type="hidden" style="width: 100%; text-align: center"></TD>
															<TD align="center">
																<input type="text" name="dongiaDACOVAT" value="" style="width: 100%; text-align: right;" disabled="disabled" >
																<input type="hidden" name="dongiaGOC" value=""  >
																
																<input type="hidden" name="dongia1" disabled="disabled" style="width: 100%; text-align: right; color: black;">
																<input type="hidden" name="dongia" readonly style="width: 100%; text-align: right">
															</TD>
															<TD align="center" style="display: none;" >
																<input type="text" name="ptVAT1" disabled="disabled" style="width: 100%; text-align: right; color: black;">
																<input type="hidden" name="ptVAT" readonly="readonly" style="width: 100%; text-align: right; color: black;">
															</TD>
															<TD align="center" style="display: none;" >
																<input name="spchietkhau1" type="text" disabled="disabled" 	style="width: 100%; text-align: right; color: black;">
																<input name="spchietkhau" type="hidden" readonly style="width: 100%; text-align: right"></TD>
															<TD align="center" style="display: none;" >
																<input name="sptienthue" type="text" disabled="disabled" style="width: 100%; text-align: right; color: black;">
															</TD>
															<TD align="center">
																<input name="thanhtien1" type="text" disabled="disabled" style="width: 100%; text-align: right; color: black;">
																<input name="thanhtien" type="hidden" readonly 	style="width: 100%; text-align: right"></TD>
															<TD align="center">
																<input name="ctkm1" type="text" 	disabled="disabled" style="width: 100%; color: black;">
																<input name="ctkm" type="hidden" readonly style="width: 100%"></TD>
															<TD align="center" style="display: none;">
																<input name="khoNVBH1" type="checkbox" value="1" onclick="return false;"> 
																<input name="khoNVBH" type="hidden" value="0" onclick="return false;">
															</TD>
														</TR>

														<% i++; m++; } } %>
													</tbody>
												</TABLE> <% if(!dhBean.getTrangthai().equals("3")){ %>
												&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
												href="javascript:ThemSanPham()"> <img style="top: -4px;"
													src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
												<%} %>
											</TD>
										</TR>
									</TABLE></TD>
							</TR>
						</TBODY>
					</TABLE></td>
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
	
	if(ckbsRs != null)
		ckbsRs.close();
	if(chietkhau_tongtien != null)
		chietkhau_tongtien.clone();
	
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

