<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.*" %>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<% IErpBangkethutien tthdBean = (IErpBangkethutien)session.getAttribute("tthdBean"); %>

<% ResultSet nvgnRs = tthdBean.getNvgnRs(); %>
<% ResultSet ddkdRs = tthdBean.getDdkdRs(); %>
<% ResultSet khuvucRs = tthdBean.getKhuvucRs(); %>
<% ResultSet quanhuyenRs = tthdBean.getQuanhuyenRs(); %>
<% Hashtable<String, Double> hoadon_thanhtoan = tthdBean.getThanhtoan(); %>
<% ResultSet htttList = tthdBean.getHtttRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>
<% NumberFormat formater = new DecimalFormat("##,###,###.###");%>

<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Phanam - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

	<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
	
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


<script language="javascript" type="text/javascript">

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
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tthdForm'].action.value='save';
	     document.forms['tthdForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function changePO()
	 { 
		 document.forms['tthdForm'].action.value='changePO';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function DinhDangDonGia(num) 
	 {
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
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
	  
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 
		 // Hóa đơn
		 var idHd = document.getElementsByName("idHd"); 
		 
		 var totalTT = 0;
		 if(chkAll.checked)
		 {
			 for(i = 0; i < idHd.length; i++)
			 {
				 var trahetsp = document.getElementById("trahetsp"+idHd.item(i).value);
				 trahetsp.checked = true;
				 
				 TraTungSp( idHd.item(i).value );
			 }
		 }
		 else
		 {
			 for(i = 0; i < idHd.length; i++)
			 {
				 var trahetsp = document.getElementById("trahetsp"+idHd.item(i).value);
				 trahetsp.checked = false;
				 
				 TraTungSp( idHd.item(i).value );
			 }
		 }
		 	 			 
	 }
	  
	 function goBack()
	 {
	  	window.history.back();
	 }
	 
	 function TraHet(pos)
	 {
		 var tienAVAT = document.getElementsByName("tienAVAT").item(pos).value;
		 var thanhtoan = document.getElementsByName("thanhtoan");
		
		 thanhtoan.item(pos).value = tienAVAT;
		
		 TongTienThanhToan();
	 }
	 
	 function TongTienThanhToan( element )
	 {
		 //element.value = DinhDangTien(element.value);
		 
		 var tienAVAT = document.getElementsByName("tienAVAT");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 
		 var totalTT = 0;
		 for(i = 0; i < tienAVAT.length; i++ )
		 {
			 if(thanhtoan.item(i).value != '')
			 {
				 totalTT += parseFloat(thanhtoan.item(i).value.replace(/,/g,""));
				 
				 conlai.item(i).value = DinhDangTien( parseFloat(tienAVAT.item(i).value.replace(/,/g,"")) - parseFloat(thanhtoan.item(i).value.replace(/,/g,"")) );
				 thanhtoan.item(i).value = DinhDangTien( thanhtoan.item(i).value );
			 }
		 }
		 
		 document.getElementById("tongthanhtoan").value = DinhDangTien( totalTT );
		 
	 }
	 
	 function Dinhdang(element)
	 {
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	 } 	
	 
	 function TraTienHDSP(hdId)
		{				
			var idHD = document.getElementsByName("idHd"); 
					
			var sumthanhtoanhd = 0;
			
			var trahet = document.getElementById("trahetsp"+hdId);
			
			trahet.checked = false;
			
			/* for(var j = 0; j < idHD.length; j ++ ) // vòng hóa đơn bên ngoài
			{ */				
				var tongtientthd = 0;  
				var avatHD = document.getElementById("avat_"+hdId);
				var thanhtoanHD = document.getElementById("thanhtoan_"+hdId);
				var ptckHD = document.getElementById("ptck_"+hdId);
				var tienckHD = document.getElementById("tienck_"+hdId); 
				var thucthuHD = document.getElementById("thucthu_"+hdId);
				var kbhHD = document.getElementById("kbhid_"+hdId);
								
				if(thucthuHD.value == "") thucthuHD.value = '0';
				var tientt = 0;
				var tiencks = 0;
				var ptck = parseFloat(ptckHD.value.replace(/,/g,"")) ;
				var thucthu = parseFloat(thucthuHD.value.replace(/,/g,""));
				var avat = parseFloat(avatHD.value.replace(/,/g,""));
				
				//NẾU KÊNH BÁN HÀNG != KÊNH CLC THÌ CHIẾT KHẤU THEO HÓA ĐƠN (CHIẾT KHẤU THEO % CHIẾT KHẤU THANH TOÁN ĐƯỢC KHAI BÁO TRONG DLN KHÁCH HÀNG)
				if(kbhHD.value != '100052')
				{
					if(thucthu >= 0) 
					{					
						tientt = Math.round(thucthu*100/(100-ptck),0);
						tiencks = Math.round(tientt*ptck/100,0); // tính từ tiền thực thu - người nhập đánh
						
						tienckt = Math.round(avat*ptck/100,0) ; // tính từ tiền hóa đơn - hệ thống có
						
						//B1: KIỂM TRA NẾU tiencks > tienckt thì mặc định cho tiền chiết khấu = tienckt hệ thống tính // tiền cks không được phép lớn hơn tiền ckt
												
						//NẾU TIỀN CHIẾT KHẤU TÍNH TỪ THỰC THU > TIỀN CHIẾT KHẤU TÍNH TỪ SỐ TIỀN HÓA ĐƠN => yêu cầu đánh lại số
													
						thucthuHD.value = DinhDangTien(thucthu);
						thanhtoanHD.value = DinhDangTien(tientt);
						tienckHD.value = DinhDangTien(tiencks);
						
						TraTienHDSPCT(hdId, kbhHD.value, thucthu );
						
					}
				}  //NẾU KÊNH BÁN HÀNG = KÊNH CLC THÌ CHIẾT KHẤU THEO SẢN PHẨM HÓA ĐƠN
				else
				{
					if(thucthu >= 0 ) // KÊNH CLC CHƯA CÓ % CHIẾT KHẤU
					{
						TraTienHDSPCT(hdId, kbhHD.value, thucthu );						
					}
				}				
			/* } */
			
			tinhtongtt();
		}
	 
	 function tinhtongtt()
	 {
		 var idHD = document.getElementsByName("idHd"); 
		 var tongthanhtoan =  document.getElementById("tongthanhtoan");
		 
		 var tongtt = 0;
		 
		  for(var j = 0; j < idHD.length; j ++ ) // vòng hóa đơn bên ngoài
			{ 			
				var thucthuHD = document.getElementById("thucthu_"+idHD.item(j).value).value.replace(/,/g,"");
				
				tongtt += parseFloat(thucthuHD);
		    } 
		
		  tongthanhtoan.value = DinhDangTien(tongtt);
	 }
	 
	 function TraTienHDSPCT(hdId, kbhid, thucthu)
	 {
		 	// DÒNG HÓA ĐƠN TỔNG
		 	var avatHD = document.getElementById("avat_"+hdId);
			var thanhtoanHD = document.getElementById("thanhtoan_"+hdId);
			var ptckHD = document.getElementById("ptck_"+hdId);
			
			var tienckHD = document.getElementById("tienck_"+hdId); 
			var thucthuHD = document.getElementById("thucthu_"+hdId);
						
			var tthd = thucthu;
			
			//DÒNG HÓA ĐƠN CHI TIẾT
		 	var maspct = document.getElementsByName("maspct_"+hdId);
			var soluonghdct = document.getElementsByName("soluonghdct_"+hdId);
			var thanhtienhdct = document.getElementsByName("thanhtienhdct_"+hdId);
			var soluongttct = document.getElementsByName("soluongttct_"+hdId);
			var thanhtienttct = document.getElementsByName("thanhtienttct_"+hdId);
			var slconlaict = document.getElementsByName("slconlaict_"+hdId);
			var ptckct = document.getElementsByName("ptckct_"+hdId);
			var tienckct = document.getElementsByName("tienckct_"+hdId);
			var thucthuct = document.getElementsByName("thucthuct_"+hdId);
			
			var sumtienck = 0;
			var sumthanhtienhd = 0;
			var sumtthd = 0;
			
			var sumthucthu = 0;
			
			for( var i = 0; i < maspct.length; i++ )
			{
				if(thucthuct.item(i).value == '') thucthuct.item(i).value= '0';
				
				var thanhtienhdct1 =  parseFloat(thanhtienhdct.item(i).value.replace(/,/g,""));
				var ptckct1 = parseFloat(ptckct.item(i).value.replace(/,/g,""));
				var tienckct1 = 0;
				var thucthuct1 = 0;
				
				//TÍNH TIỀN CK - TIỀN THỰC THU TỪ THÀNH TIỀN CỦA TỪNG SẢN PHẨM
				tienckct1 = Math.round(thanhtienhdct1*ptckct1/100,2);
				thucthuct1 = thanhtienhdct1 - tienckct1;
						
				if(thucthu >= 0)
				{
					var sttdong = maspct.length - 1;
				  // KIỂM TRA XEM CÓ PHẢI DÒNG SẢN PHẨM CUỐI CÙNG K
				   if(i != sttdong) // KHÔNG PHẢI LÀ DÒNG SẢN PHẨM CUỐI CÙNG
				   {
						if(thucthuct1 <= thucthu)
						{
							thanhtienttct.item(i).value =  DinhDangTien(thanhtienhdct1);
							tienckct.item(i).value = DinhDangTien(tienckct1);
							thucthuct.item(i).value = DinhDangTien(thucthuct1);
							
							thucthu = thucthu - thucthuct1;
							
							sumtienck += tienckct1;
							sumthanhtienhd += thanhtienhdct1;
						}
						else // PHẦN CÒN LẠI CỦA THỰC THU
						{
							thucthuct.item(i).value = DinhDangTien(thucthu);
							
							var tt = Math.round(thucthu*100/(100-ptckct1),0);
							
							thanhtienttct.item(i).value =DinhDangTien(tt) ;
							
							var ck = Math.round(tt*ptckct1/100,0);
							tienckct.item(i).value = DinhDangTien(ck);	
							thucthu = '0';		
							
							sumtienck += ck;
							sumthanhtienhd += tt;
						}
				   }
				   else //NẾU LÀ DÒNG CUỐI CÙNG THÌ GÁN SỐ TIỀN THỰC THU BẰNG SỐ TIỀN THỰC THU CÒN LẠI
				   {
					   if(thucthuct1 < thucthu)
						{
							thucthuct.item(i).value = DinhDangTien(thucthu);
							tienckct.item(i).value = DinhDangTien(tienckct1);
							var t = thucthu + tienckct1;
							thanhtienttct.item(i).value =  DinhDangTien(t);
							
							sumtienck += tienckct1;
							sumthanhtienhd += t;
							
						}
						else // PHẦN CÒN LẠI CỦA THỰC THU
						{
							thucthuct.item(i).value = DinhDangTien(thucthu);
							
							var tt = tt = Math.round(thucthu*100/(100-ptckct1),0);
							
							thanhtienttct.item(i).value =DinhDangTien(tt) ;
							
							var ck = Math.round(tt*ptckct1/100,0);
							tienckct.item(i).value = DinhDangTien(ck);	
							thucthu = '0';		
							
							sumtienck += ck;
							sumthanhtienhd += tt;
						}
				   }
				}
			}
			
			thucthuHD.value = DinhDangTien(tthd);
			tienckHD.value = DinhDangTien(sumtienck); 
			thanhtoanHD.value = DinhDangTien(sumthanhtienhd);
			if(sumtienck != 0)
				ptckHD.value = roundNumber(sumtienck/sumthanhtienhd*100,2); 		
			else
				ptckHD.value = 0; 		
			
			// HÀM TÍNH TỔNG TIỀN THỰC THU
			
			tinhtongtt();
	 }
	  
	
	 
	 function TraTienHDSP_CT(hdId)
	 {
		 	// DÒNG HÓA ĐƠN TỔNG
		 	  
			var avatHD = document.getElementById("avat_"+hdId);
			var thanhtoanHD = document.getElementById("thanhtoan_"+hdId);
			var ptckHD = document.getElementById("ptck_"+hdId);
			var tienckHD = document.getElementById("tienck_"+hdId); 
			var thucthuHD = document.getElementById("thucthu_"+hdId);
			var kbhHD = document.getElementById("kbhid_"+hdId);
			
			var thucthu = parseFloat(thucthuHD.value.replace(/,/g,""));
			var tthd = thucthu;
			
			//DÒNG HÓA ĐƠN CHI TIẾT
		 	var maspct = document.getElementsByName("maspct_"+hdId);
			var soluonghdct = document.getElementsByName("soluonghdct_"+hdId);
			var thanhtienhdct = document.getElementsByName("thanhtienhdct_"+hdId);
			var soluongttct = document.getElementsByName("soluongttct_"+hdId);
			var thanhtienttct = document.getElementsByName("thanhtienttct_"+hdId);
			var slconlaict = document.getElementsByName("slconlaict_"+hdId);
			var ptckct = document.getElementsByName("ptckct_"+hdId);
			var tienckct = document.getElementsByName("tienckct_"+hdId);
			var thucthuct = document.getElementsByName("thucthuct_"+hdId);
			
			var sumtienck = 0;
			var sumthanhtienhd = 0;
			var sumtthd = 0;
			
			var sumthucthu = 0;
			
				for( var i = 0; i < maspct.length; i++ )
				{
					if(thucthuct.item(i).value == '') thucthuct.item(i).value= '0';
					if(ptckct.item(i).value == '') ptckct.item(i).value= '0';
					
					var thanhtienhdct1 =  parseFloat(thanhtienhdct.item(i).value.replace(/,/g,""));
					var ptckct1 = parseFloat(ptckct.item(i).value.replace(/,/g,""));
					var tienckct1 = 0;
					var thucthuct1 = 0;
					
					//TÍNH TIỀN CK - TIỀN THỰC THU TỪ THÀNH TIỀN CỦA TỪNG SẢN PHẨM
					tienckct1 = Math.round(thanhtienhdct1*ptckct1/100,2);
					thucthuct1 = thanhtienhdct1 - tienckct1;
					
					if(thucthu >= 0)
					{
						var sttdong = maspct.length - 1;
					  // KIỂM TRA XEM CÓ PHẢI DÒNG SẢN PHẨM CUỐI CÙNG K
					   if(i != sttdong) // KHÔNG PHẢI LÀ DÒNG SẢN PHẨM CUỐI CÙNG
					   {
							if(thucthuct1 <= thucthu)
							{
								thanhtienttct.item(i).value =  DinhDangTien(thanhtienhdct1);
								tienckct.item(i).value = DinhDangTien(tienckct1);
								thucthuct.item(i).value = DinhDangTien(thucthuct1);
								
								thucthu = thucthu - thucthuct1;
								
								sumtienck += tienckct1;
								sumthanhtienhd += thanhtienhdct1;
							}
							else // PHẦN CÒN LẠI CỦA THỰC THU
							{								   
								thucthuct.item(i).value = DinhDangTien(thucthu);
								
								var tt = Math.round(thucthu*100/(100-ptckct1),0);
								
								thanhtienttct.item(i).value =DinhDangTien(tt) ;
								
								var ck = Math.round(tt*ptckct1/100,0);
								tienckct.item(i).value = DinhDangTien(ck);	
								thucthu = '0';		
								
								sumtienck += ck;
								sumthanhtienhd += tt;
							}
					   }
					   else //NẾU LÀ DÒNG CUỐI CÙNG THÌ GÁN SỐ TIỀN THỰC THU BẰNG SỐ TIỀN THỰC THU CÒN LẠI
					   {
						   if(thucthuct1 < thucthu)
							{
								thucthuct.item(i).value = DinhDangTien(thucthu);
								tienckct.item(i).value = DinhDangTien(tienckct1);
								var t = thucthu + tienckct1;
								thanhtienttct.item(i).value =  DinhDangTien(t);
								
								sumtienck += tienckct1;
								sumthanhtienhd += t;
								
							}
							else // PHẦN CÒN LẠI CỦA THỰC THU
							{
								thucthuct.item(i).value = DinhDangTien(thucthu);
								
								var tt = Math.round(thucthu*100/(100-ptckct1),0);
								
								thanhtienttct.item(i).value =DinhDangTien(tt) ;
								
								var ck = Math.round(tt*ptckct1/100,0);
								tienckct.item(i).value = DinhDangTien(ck);	
								thucthu = '0';		
								
								sumtienck += ck;
								sumthanhtienhd += tt;
							}
					   }
					  
					}
					
			    }
				
				thucthuHD.value = DinhDangTien(tthd);
				tienckHD.value = DinhDangTien(sumtienck); 
				thanhtoanHD.value = DinhDangTien(sumthanhtienhd);
				ptckHD.value = roundNumber(sumtienck/sumthanhtienhd*100,2); 	
				tinhtongtt();
				
	 }
	 	 
	 function TraTungSp(hdId)
		{		
		 
			var trahet = document.getElementById("trahetsp"+hdId);
			
			// DÒNG HÓA ĐƠN TỔNG
			var idHD = document.getElementsByName("idHd"); 
		 	var avatHD = document.getElementById("avat_"+hdId);
			var thanhtoanHD = document.getElementById("thanhtoan_"+hdId);
			var ptckHD = document.getElementById("ptck_"+hdId);
			var tienckHD = document.getElementById("tienck_"+hdId); 
			var thucthuHD = document.getElementById("thucthu_"+hdId); 
			var kbhidHD = document.getElementById("kbhid_"+hdId);
						
			//DÒNG HÓA ĐƠN CHI TIẾT
		 	var maspct = document.getElementsByName("maspct_"+hdId);
			var soluonghdct = document.getElementsByName("soluonghdct_"+hdId);
			var thanhtienhdct = document.getElementsByName("thanhtienhdct_"+hdId);
			var soluongttct = document.getElementsByName("soluongttct_"+hdId);
			var thanhtienttct = document.getElementsByName("thanhtienttct_"+hdId);
			var slconlaict = document.getElementsByName("slconlaict_"+hdId);
			var ptckct = document.getElementsByName("ptckct_"+hdId);
			var tienckct = document.getElementsByName("tienckct_"+hdId);
			var thucthuct = document.getElementsByName("thucthuct_"+hdId);
						 		 
			var tientt = 0;
			var ptck = 0;
			var tienck = 0;
			var thucthu = 0;
			var kbhid = kbhidHD.value;
			
			
			if(trahet.checked == true)
			{
				var sumtienck = 0;
				var sumtthd = 0;
				
				var sumthucthu = 0;
				
				var sumptck = 0;
				
				if(kbhid != '100052') // KHÁC KÊNH CLC THÌ CHIẾT KHẤU THEO % HÓA ĐƠN 
				{		
					for( var i = 0; i < maspct.length; i++ )
					{
						var tientt_ct = parseFloat(thanhtienhdct.item(i).value.replace(/,/g,""));
						var ptck_ct = parseFloat(ptckct.item(i).value.replace(/,/g,""));
						var tienck_ct = 0;
						var thucthu_ct = 0;
						
						tienck_ct = Math.round(tientt_ct*ptck_ct/100,0);
						thucthu_ct = tientt_ct - tienck_ct;
						
						thanhtienttct.item(i).value = DinhDangTien(tientt_ct);
						tienckct.item(i).value = DinhDangTien(tienck_ct);
						thucthuct.item(i).value = DinhDangTien(thucthu_ct);
						
						sumtienck += tienck_ct;
						sumtthd += tientt_ct;
						sumthucthu += thucthu_ct;
					}
					
					thanhtoanHD.value = DinhDangTien(sumtthd);
					tienckHD.value = DinhDangTien(sumtienck);
					thucthuHD.value = DinhDangTien(sumthucthu);
					ptckHD.value = roundNumber(sumtienck/sumtthd*100,2); 					
					
				}				
				else // NẾU LÀ KÊNH CLC THÌ CHIẾT KHẤU THEO SẢN PHẨM
				{					
					for( var i = 0; i < maspct.length; i++ )
					{
						var tientt_ct = parseFloat(thanhtienhdct.item(i).value.replace(/,/g,""));
						var ptck_ct = parseFloat(ptckct.item(i).value.replace(/,/g,""));
						var tienck_ct = 0;
						var thucthu_ct = 0;
						
						tienck_ct = Math.round(tientt_ct*ptck_ct/100,0);
						thucthu_ct = tientt_ct - tienck_ct;
						
						thanhtienttct.item(i).value = DinhDangTien(tientt_ct);
						tienckct.item(i).value = DinhDangTien(tienck_ct);
						thucthuct.item(i).value = DinhDangTien(thucthu_ct);
						
						sumtienck += tienck_ct;
						sumtthd += tientt_ct;
						sumthucthu += thucthu_ct;
					}
					
					thanhtoanHD.value = DinhDangTien(sumtthd);
					tienckHD.value = DinhDangTien(sumtienck);
					thucthuHD.value = DinhDangTien(sumthucthu);
					ptckHD.value = roundNumber(sumtienck/sumtthd*100,2); 
				}
			}
			else if(trahet.checked == false)
			{								
				thanhtoanHD.value = '0';
				tienckHD.value = '0';
				thucthuHD.value = '0';
				ptckHD.value = '0';
				
				for( var i = 0; i < maspct.length; i++ )
				{						
					thanhtienttct.item(i).value = '0';
					tienckct.item(i).value = '0';
					thucthuct.item(i).value = '0';
				}
				
			}
			
			tinhtongtt();
			
		}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpBangkethutienUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" id="msg" value=''>
<input type="hidden" name="nppId" value='<%= tthdBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= tthdBean.getId() %>'>
 
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải thu > Bảng kê thu tiền > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href = "javascript: goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= tthdBean.getMsg() %></textarea>
		         <% tthdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Bảng kê thu tiền </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = 0>							
                <TR>
                    <TD width="150px" class="plainlabel" >Ngày chứng từ</TD>
                    <TD class="plainlabel" width="300px" valign="top">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" value="<%= tthdBean.getNgaychungtu() %>" 
                    		maxlength="10" readonly /></TD>
                    <TD width="150px" class="plainlabel" >Ngày ghi sổ</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngayghiso" name="ngayghiso" value="<%= tthdBean.getNgayghiso() %>" 
                    		maxlength="10" readonly onchange="submitform()" /></TD>
                </TR> 
                
                <TR>
                    <TD width="150px" class="plainlabel" >Tìm từ ngày</TD>
                    <TD class="plainlabel" width="300px" valign="top">
                    	<input type="text"  class="days" id="tungay" name="tungay" value="<%= tthdBean.getTungay() %>" maxlength="10" readonly /></TD>
                    <TD width="150px" class="plainlabel" >Đến ngày</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="denngay" name="denngay" value="<%= tthdBean.getDenngay() %>" maxlength="10" readonly /></TD>
                </TR> 
                
                
				<TR>
                    <TD class="plainlabel">Nhân viên giao nhận</TD>
                    <TD class="plainlabel">
                    	<select name="nvgnId" class="select2" style="width: 200px;"  >
                    		<option value="" ></option>
	                       	<% if(nvgnRs != null ) { 
	                       		while( nvgnRs.next() ) {
	                       			if( nvgnRs.getString("pk_seq").equals( tthdBean.getNvgnId() ) ) { %>
	                       				<option value="<%= nvgnRs.getString("pk_seq") %>" selected="selected" ><%= nvgnRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= nvgnRs.getString("pk_seq") %>" ><%= nvgnRs.getString("ten") %></option>
	                       	<% 	} } nvgnRs.close();
	                       	} %>
                       	</select>
                     </TD> 
               
                    <TD class="plainlabel">Trình dược viên</TD>
                    <TD  class="plainlabel">
                        <select name="ddkdId" class="select2" style="width: 200px;"  >
                    		<option value="" ></option>
	                       	<% if(ddkdRs != null ) { 
	                       		while( ddkdRs.next() ) {
	                       			if( ddkdRs.getString("pk_seq").equals( tthdBean.getDdkdId() ) ) { %>
	                       				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
	                       	<% 	} } ddkdRs.close();
	                       	} %>
                       	</select>
                     </TD> 
                </TR>
                
                <TR>
                    <TD class="plainlabel">Mã khách hàng</TD>
                    <TD class="plainlabel">
                       	<input type="text" name="makhachhang" value="<%= tthdBean.getMakhachhang() %>"  >
                     </TD> 
               
                    <TD class="plainlabel">Tỉnh/Thành phố</TD>
                    <TD  class="plainlabel">
                        <select name="khuvucId" class="select2" style="width: 200px;" onchange="submitform()">
                    		<option value="" ></option>
	                       	<% if(khuvucRs != null ) { 
	                       		while( khuvucRs.next() ) {
	                       			if( khuvucRs.getString("pk_seq").equals( tthdBean.getKhuvucId() ) ) { %>
	                       				<option value="<%= khuvucRs.getString("pk_seq") %>" selected="selected" ><%= khuvucRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= khuvucRs.getString("pk_seq") %>" ><%= khuvucRs.getString("ten") %></option>
	                       	<% 	} } khuvucRs.close();
	                       	} %>
                       	</select>
                     </TD> 
                </TR> 
                
                 <TR>
                                   
                    <TD class="plainlabel">Quận/Huyện</TD>
                    <TD  class="plainlabel">
                        <select name="quanhuyenId" class="select2" style="width: 200px;"  >
                    		<option value="" ></option>
	                       	<% if(quanhuyenRs != null ) { 
	                       		while( quanhuyenRs.next() ) {
	                       			if( quanhuyenRs.getString("pk_seq").equals( tthdBean.getQuanhuyenId() ) ) { %>
	                       				<option value="<%= quanhuyenRs.getString("pk_seq") %>" selected="selected" ><%= quanhuyenRs.getString("ten") %></option>
	                       	<% 	} else { %>
	                       				<option value="<%= quanhuyenRs.getString("pk_seq") %>" ><%= quanhuyenRs.getString("ten") %></option>
	                       	<% 	} } quanhuyenRs.close();
	                       	} %>
                       	</select>
                     </TD> 
                     
                    <TD class="plainlabel">Hình thức thanh toán</TD>
                    <TD class="plainlabel" colspan = 5>
                        <select name="htttId" id="htttId" style="width: 200px" >
                        	<%
                        		if(htttList != null)
                        		{
                        			try
	                        		{
	                        			while(htttList.next())
	                        			{  
	                        				if( htttList.getString("pk_seq").equals(tthdBean.getHtttId())){ %>
	                        					<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ten")%></option>
	                        				<%}else { %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ten") %></option>
	                        		 	<% } 
	                        			} htttList.close();
	                        		 } catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                       	
                </TR> 
				
            	<TR>
                    <TD class="plainlabel">Tổng tiền thanh toán </TD>
                    <TD  class="plainlabel"  >
                        <input type="text" style="text-align: right;" name="tongthanhtoan" id = "tongthanhtoan" value = "<%= formater.format(Double.parseDouble(tthdBean.gettongtientt())) %>" readonly="readonly">
                     </TD> 
               
                    <TD class="plainlabel">Ghi chú </TD>
                    <TD  class="plainlabel"  >
                        <input type="text" name="ghichu" value="<%= tthdBean.getGhichu() %>"> 
                     </TD> 
                </TR>
                
                <TR>
                    <td colspan="4" class="plainlabel">
                        <a class="button" href="javascript:submitform();">
                            <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </TR>    
                
            </TABLE>
            <hr> 
            </div>
        
		<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
	            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
	              <TR class="tbheader"> 
	                	<TH align="center" width="8%">Mã KH</TH>
	                	<TH align="center" width="18%">Tên KH</TH>
	                	<TH align="center" width="5%">Số HĐ</TH>
	                	<TH align="center" width="8%">Ngày HĐ</TH>
	                	<TH align="center" width="6%">KBH</TH>
	                	<TH align="center" width="10%">Tiền hóa đơn</TH>
	               	 	<TH align="center" width="10%">Tiền thanh toán</TH>
	               	 	<TH align="center" width="5%">% CK</TH>
	               	 	<TH align="center" width="10%">Tiền CK</TH>
	               	 	<TH align="center" width="10%">Số thực thu</TH>
	               	 	<TH align="center" width="10%">Ghi chú</TH>
	               	 	<TH align="center" width="3%">Thu hết
	               	 		 <input type="checkbox" onchange="sellectAll()" id="chkAll"  />
	               	 	</TH>
	               	 	<TH align="center" width="10%">
	               	 		Chi tiết HĐ	               	 		
	               	 	</TH>
	                </TR>
	                
	               <%  
				int i;
	            for(i = 0; i < hoadonList.size(); i++)
            	{
            		IHoadon hoadon = hoadonList.get(i);
               		%>
               		<tr>
               			<td align="center">							
       	 					<input type="text" style="width: 100%;text-align: center;" value="<%= hoadon.getMaKH()%>" name= "makh" readonly="readonly" >
       	 				</td>
       	 				
       	 				<td align="center">							
       	 					<input type="text" style="width: 100%;text-align: left;" value="<%= hoadon.getTenkh()%>" name= "tenkh" readonly="readonly" >
       	 				</td>
       	 				
       	 				<td align="center">
       	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd"  >  
       	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getKhId() %>" name= "khId"  > 
       	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getisNPP() %>" name= "isNPP"  > 
       	 					<input type="text" style="width: 100%;text-align: center;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" > 	 					
       	 				</td>
       	 				       	 				
       	 				<td align="center">          	 				
       	 					<input type="text" style="width: 100%; text-align: center;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
       	 					<input type="hidden" style="width: 100%; text-align: center;" value="<%= hoadon.getNgaydenhanTT() %>" name= "ngaydenhanTT" id="ngaydenhanTT" readonly="readonly" >
						</td>
						       	 	
						<td align="center">							
							<input type="hidden" style="width: 100%;text-align: center;" value="<%= hoadon.getKenhId()%>" name= "kbhid" id="kbhid_<%= hoadon.getId() %>" readonly="readonly" >
       	 					<input type="text" style="width: 100%;text-align: center;" value="<%= hoadon.getKenhTen()%>" name= "kbhTen" readonly="readonly" >
       	 				</td>
       	 						
						 <!-- Tổng số tiền HĐ  -->	
       	 				<td align="center">
       	 					<input type="text" style="width: 100%; text-align: right;" name= "avat" id="avat_<%= hoadon.getId() %>" value="<%= formatter.format(Double.parseDouble(hoadon.getTiengocHD().replaceAll(",",""))) %>"  readonly="readonly" >
       	 				</td>
       	 				      	 		
       	 				 <!-- Số tiền thanh toán  -->		
       	 				<td align="center">
       	 					<input type="text" style="width: 100%; text-align: right;" name= "thanhtoan" id= <%= "thanhtoan_"+ hoadon.getId() %> value="<%= formatter.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>"  readonly="readonly" >
       	 				</td> 
       	 				
       	 				 <!-- % chiết khấu  -->	
       	 				<td align="center">
       	 					<input type="text" style="width: 100%; text-align: right;" name= "ptck" id= <%= "ptck_"+ hoadon.getId() %> value="<%= formatter2.format(Double.parseDouble(hoadon.getptck().replaceAll(",",""))) %>"  readonly="readonly" >
       	 				</td>
       	 				
       	 				 <!-- tiền chiết khấu  -->	
       	 				<td align="center">
       	 					<input type="text" style="width: 100%; text-align: right;" name= "tienck" id= <%= "tienck_"+ hoadon.getId() %> value="<%= formatter.format(Double.parseDouble(hoadon.gettienck().replaceAll(",",""))) %>"  readonly="readonly" >
       	 				</td>
       	 				
       	 				 <!-- số thực thu  -->	
       	 				<td align="center">
       	 					<input type="text" style="width: 100%; text-align: right;" name= "thucthu" id= <%= "thucthu_"+ hoadon.getId() %> value="<%= formatter.format(Double.parseDouble(hoadon.getthucthu().replaceAll(",",""))) %>"  onkeypress="return keypress(event);" onkeyup="TraTienHDSP(<%=hoadon.getId()%>);" >
       	 				</td>
       	 				
       	 				 <!-- ghi chú  -->	
       	 				<td align="center">
       	 					<input type="text" style="width: 100%;" value="<%= hoadon.getghichu() %>" name= "ghichuhd"  >  
       	 				</td>
       	 				
       	 				<td align="center">
           	 				<input type="checkbox" value="" name = <%= "trahetsp_" + hoadon.getId() %>  id="trahetsp<%= hoadon.getId()  %>" onchange="TraTungSp(<%=hoadon.getId()%>)" >           	 		
           	 			</td>
   	 					
       	 				<td align="left">
				           	<a href="" id="tenhd_<%= i %>" rel="subcontent<%= i %>">
					        <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           <DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								    background-color: white; width: 850px; padding: 4px; max-height: 200px; overflow: auto;">
								    <table width="100%" align="center">
		        						<tr class="tbheader">
		        						<th width="80px" >STT</th>
	         							<th width="100px">Tên sản phẩm</th>
	         							<!-- <th width="100px">Số lượng HĐ </th>			 -->	         							
	         							<th width="100px">Thành tiền HĐ </th>
	         							<!-- <th width="100px">Số lượng thanh toán </th> -->
	         							<th width="100px">Thành tiền thanh toán </th>
	         							<th width="100px">% chiết khấu </th>					         							
	         							<th width="100px">Tiền chiết khấu </th>
	         							<th width="100px">Tiền thực thu</th>
	         							
			        					</tr>
								      <% 
								      	int m;
								        int dem = 0;
								      	List<IHoadonct> HdctList = hoadon.getHoadonList();
								      		for(m = 0; m < HdctList.size(); m++){ 
								      			IHoadonct hdct = HdctList.get(m);	%>
								      		<tr>
								      			
								      		<td>
								      			<input type="text" name=<%= "STT_" + hoadon.getId() %> value = "<%= m + 1 %>" style="width: 100%" readonly="readonly" >	
								      		</td>											      			
								      		
								      		<td>
								      			<input type="hidden" name=<%= "maspct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%=hdct.getspId() %>" style="width: 100%" readonly="readonly"  >
								      			<input type="hidden" name=<%= "hdIdct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%=hdct.gethdId() %>" style="width: 100%"  readonly="readonly" >
								      			<input type="text" name=<%= "tenspct_" + hoadon.getId()%> value = "<%=hdct.getTensp() %>" style="width: 100%" readonly="readonly"  >
								      			 <!-- số lượng HĐ  -->	
								      			<%-- <input type="hidden" name=<%= "soluonghdct_" + hoadon.getId() %> value = "<%=hdct.getSoluonghd() %>" style="width: 100%" readonly="readonly" > --%>	
								      		</td>								      		
								      		
								      		 <!-- thành tiền HĐ  -->	
								      		<td>								      			
								      			<input type="text" name=<%= "thanhtienhdct_" + hoadon.getId()  %> style="width: 100%; text-align: right;" value = "<%= hdct.getThanhtienhd() %>" style="width: 100%" readonly="readonly" >
								      			 <!-- số lượng thanh toán  -->
								      			 <%-- <input type="hidden" name=<%= "soluongttct_" + hoadon.getId() %> value = "<%= hdct.getSoluongtt() %>" style="width: 100%" readonly="readonly"> --%>	
								      		</td>
											
								      		<!-- thành tiền thanh toán  -->											      		
								      		<td>
								      			<input type="text" name=<%= "thanhtienttct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%= hdct.getThanhtoan() %>" style="width: 100%" onkeypress="return keypress(event);"  readonly="readonly">
								      		</td>
											<!-- pt chiết khấu  -->	
								      		<td>
								      			<input type="text" name=<%= "ptckct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%= hdct.getptchietkhau() %>" style="width: 100%" onkeypress="return keypress(event);" onkeyup="TraTienHDSP_CT(<%=hoadon.getId()%>);" >											      			
								      		</td>
								      		<!-- tiền chiết khấu  -->	
											<td>				
								      			<input type="text" name=<%= "tienckct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%= hdct.getsotienck() %>" style="width: 100%" readonly="readonly">					      		
											</td>
											  <!-- số thực thu  -->	
								      		<td>
								      			<input type="text" name=<%= "thucthuct_" + hoadon.getId() %> style="width: 100%; text-align: right;" value = "<%= hdct.getThucthu() %>"  style="width: 100%" onkeypress="return keypress(event);" readonly="readonly" >
								      			
								      			<input type="hidden" name=<%= "dongiact_" + hoadon.getId() %> value = "<%=hdct.getDongiahd() %>" style="width: 100%"  readonly="readonly" >
								      			
								      			<input type="hidden" name=<%= "sotienckct_" + hoadon.getId() %> value = "<%=hdct.getsotienckhd() %>" style="width: 100%"  readonly="readonly" >
								      			
								      			<input type="hidden" name=<%= "vatct_" + hoadon.getId() %> value = "<%=hdct.getvat() %>" style="width: 100%"  readonly="readonly" >
								      			
								      			<input type="hidden" name=<%= "isdathanhtoanct_" + hoadon.getId() %> value = "<%=hdct.getIsdathanhtoan() %>" style="width: 100%"  readonly="readonly" >
								      		</td>
								      		
											
											</tr>
								      	<%    			// Ket thuc vong lap
								      	   dem++;
								      		}
							      	 %>
										</table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
					                     </div>
					                </DIV>
					                
					                <script type="text/javascript">
										for(var i = 0; i < 2000; i++) {
											dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
										}
									</script>

	           	 				</td>
	           	 			
       	 			</tr>     	 	
	               <%} %> 
	            </TABLE> 
	        	</div>  

     </fieldset>	
    </div>
</div>
</form>

</BODY>
</html>

<%
try{
	if(nvgnRs != null) nvgnRs.close(); 
	if(ddkdRs != null) ddkdRs.close(); 
	if(khuvucRs != null) khuvucRs.close(); 
	if(quanhuyenRs != null) quanhuyenRs.close();
	
	tthdBean.DBclose();
	
}catch (Exception ex)
{
	ex.printStackTrace();
}
%>