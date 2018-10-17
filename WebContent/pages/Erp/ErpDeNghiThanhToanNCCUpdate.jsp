<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.thanhtoanhoadon.*" %>
<%@ page  import = "geso.traphaco.erp.beans.uynhiemchi.*" %>
<%@ page  import = "geso.traphaco.erp.beans.uynhiemchi.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpDenghithanhtoanNCC tthdBean = (IErpDenghithanhtoanNCC)session.getAttribute("tthdBean"); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet nvList = tthdBean.getNhanvienRs(); %>
<% ResultSet nhomnccList = tthdBean.getNhomNCCRs(); %>
<% ResultSet khList = tthdBean.getKhachhangRs(); %>
<% ResultSet tienteList = tthdBean.getTienteRs(); %>
<% ResultSet htttList = tthdBean.getHtttRs(); %>
<% ResultSet nganhangList = tthdBean.getNganhangRs(); %>
<% ResultSet chinhanhList = tthdBean.getChinhanhRs(); %>
<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>
<% ResultSet sotkRs = tthdBean.getSotkRs(); %>
<% ResultSet sotkRs_tp = tthdBean.getSotkRs_tp(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); %>
<%	NumberFormat formatter2 = new DecimalFormat("#,###,###"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoERP - Project</TITLE>
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
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>


<script language="javascript" type="text/javascript">
	
	

	function ThanhToan(n)
	{
		var trichphi = document.getElementsByName("trichphi");
		var trahet = document.getElementsByName("trahet");
		var avat = document.getElementsByName("avat");
		var thanhtoan = document.getElementsByName("thanhtoan");
		var conlai = document.getElementsByName("conlai");
		 
		var tigia = document.getElementsByName("tigia");
		var tg = tigia.item(0).value.replace(/,/g,"");

		var tienteId = document.getElementsByName("tienteId");
		 	 
		var tongtienNT = 0;
		var tongtienVND = 0;
		var sotienNT;
		var tigiaHD = document.getElementsByName("tigiaHD");
		var doituongthanhtoan = document.getElementsByName("doituongthanhtoan");

  if(doituongthanhtoan.item(0).value != "3"){// LOAI THANH TOAN HOA DON / TAM UNG
// VÒNG LẶP CHO TẤT CẢ HÓA ĐƠN
	 	for(i = 0; i < trahet.length; i++)
	 	{

			if(trahet.item(i).checked ) // CHỌN TRẢ HẾT HÓA ĐƠN
			{
// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN				
				if(n == 100){ 
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
					}else{
						tt = 0;
					}												

// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN + THANH TOAN HOA DON TIEN VNĐ					
// CHỈ ĐƯỢC THANH TOÁN TỐI ĐA BẰNG SỐ TIỀN HÓA ĐƠN
					if(tienteId.item(0).value == "100000"){ 
						if(Math.abs(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
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
						if(Math.abs(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
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
			}else if(i == n){ 
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
					if(Math.abs(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0){
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

					if(Math.abs(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0){
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

	 		document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(tongtienVND, 0));

 			var pnganhangVND = document.getElementsByName("pnganhangVND");

 			var pnhVND;
			if(pnganhangVND.item(0).value != ''){
				pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
				 
			}else{
				pnhVND = 0;
			}					

 			var thuevatVND = document.getElementsByName("vatVND");
 			
 			var vatVND;
			if(thuevatVND.item(0).value != ''){
				vatVND = thuevatVND.item(0).value.replace(/,/g,"");
				 
			}else{
				vatVND = 0;
			}

 			var tongthanhtoan = parseFloat(tongtienVND) + parseFloat(pnhVND) + parseFloat(vatVND);

 			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongthanhtoan, 0));
 			
 			document.getElementById("vatVND").value = DinhDangTien(document.getElementById("vatVND").value);
 			document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(pnhVND, 0));

 			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value;
 			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
 			
 			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(vatVND)*100/parseFloat(pnhVND)).toFixed(2));

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ	 	
	 	}else{ 
	 		
			var tongttVND = 0;
	 		var tongttNT = 0;
	 		
	 		document.getElementById("sotienHDNT").value = DinhDangDonGia((tongtienNT).toFixed(2));
	 		document.getElementById("sotienHDVND").value = DinhDangTien(tongtienVND, 0);

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ + TRICH PHÍ NGOẠI TỆ			
			if(trichphi.item(0).checked == false){ 
				var pnganhangNT = document.getElementsByName("pnganhangNT");
	 			var pnhNT;
				if(pnganhangNT.item(0).value != ''){
					pnhNT = pnganhangNT.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhNT = 0;
				}					

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(pnhNT)*parseFloat(tg), 0));

				var thuevatNT = document.getElementsByName("vatNT");
		 		var vatNT;
				if(thuevatNT.item(0).value != ''){
					vatNT = thuevatNT.item(0).value.replace(/,/g,"");
					 
				}else{
					vatNT = 0;
				}					
				
				document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(vatNT)*parseFloat(tg), 0));
				
	 			document.getElementById("pnganhangNT").value = DinhDangDonGia((parseFloat(pnhNT)).toFixed(2));

	 			document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(document.getElementById("vatNT").value)).toFixed(2));
	 			
	 			tongttVND = parseFloat(tongtienVND) + parseFloat(pnhNT)*parseFloat(tg) + parseFloat(vatNT)*parseFloat(tg);
	 			
	 			tongttNT = parseFloat(tongtienNT) + parseFloat(pnhNT) + parseFloat(vatNT);

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ + TRICH PHÍ VNĐ			
	 			
			}else{ 
				var pnganhangVND = document.getElementsByName("pnganhangVND");
	 			var pnhVND;
				if(pnganhangVND.item(0).value != ''){
					pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhVND = 0;
				}					

				var thuevatVND = document.getElementsByName("vatVND");
		 		var vatVND;
				if(thuevatVND.item(0).value != ''){
					vatVND = thuevatVND.item(0).value.replace(/,/g,"");
					 
				}else{
					vatVND = 0;
				}					
				
				document.getElementById("pnganhangNT").value = "0";
				document.getElementById("vatNT").value = "0";

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("pnganhangVND").value.replace(/,/g,"")), 0));
	 			document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("vatVND").value.replace(/,/g,"")), 0));
				
				tongttNT = parseFloat(tongtienNT) ;
				tongttVND = parseFloat(tongtienVND) + parseFloat(pnhVND) + parseFloat(vatVND);
			}
			
			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongttVND, 0));
			document.getElementById("tongtienNT").value = DinhDangDonGia((tongttNT).toFixed(2));
			
			var chenhlechVND = parseFloat(tongttNT)*parseFloat(tg) - parseFloat(tongttVND);
			document.getElementById("chenhlechVND").value = DinhDangTien(roundNumber(chenhlechVND, 0));
			
 			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value; 			
 			
 			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
 			
 			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(document.getElementById("vatVND").value.replace(/,/g,""))*100/document.getElementById("pnganhangVND").value.replace(/,/g,"")).toFixed(2));
 			
 			document.getElementById("tigia").value = DinhDangTien(document.getElementById("tigia").value);
	 	}
  } else { // LOAI THANH TOAN : KHÁC
	// THANH TOAN VND
	  if(tienteId.item(0).value == "100000"){
		  var sotienthanhtoan = document.getElementsByName("sotienHDVND");
          var sotienthanhtoanVND;
			if(sotienthanhtoan.item(0).value != ''){
				sotienthanhtoanVND = sotienthanhtoan.item(0).value.replace(/,/g,"");
				 
			}else{
				sotienthanhtoanVND = 0;
			}
		  
		  document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(sotienthanhtoanVND, 0));

		  var pnganhangVND = document.getElementsByName("pnganhangVND");

		  var pnhVND;
			if(pnganhangVND.item(0).value != ''){
				pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
				 
			}else{
				pnhVND = 0;
			}					

			var thuevatVND = document.getElementsByName("vatVND");
			
			var vatVND;
			if(thuevatVND.item(0).value != ''){
				vatVND = thuevatVND.item(0).value.replace(/,/g,"");
				 
			}else{
				vatVND = 0;
			}
			
			var tongthanhtoan = parseFloat(sotienthanhtoanVND) + parseFloat(pnhVND) + parseFloat(vatVND);

			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongthanhtoan, 0));
			
			document.getElementById("vatVND").value = DinhDangTien(document.getElementById("vatVND").value);
			document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(pnhVND, 0));

			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value;
			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
			
			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(vatVND)*100/parseFloat(pnhVND)).toFixed(2)); 
	      
	  }else { // THANH TOAN BANG NGOAI TE
		  
		  var sotienthanhtoanNT = document.getElementsByName("sotienHDNT");
		  var sotienttNT= 0;
	      if(sotienthanhtoanNT.item(0).value != ''){
	    	  sotienttNT = sotienthanhtoanNT.item(0).value.replace(/,/g,"");
	      }else {
	    	  sotienttNT = 0;
	      }
	      var sotienttVND = parseFloat(sotienttNT)*parseFloat(tg);
	      document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(parseFloat(sotienttNT)*parseFloat(tg), 0)); 
	      document.getElementById("sotienHDNT").value = DinhDangTien(sotienttNT); 
	      
	   // THANH TOÁN CHO LOAI "KHAC" BANG NGOẠI TỆ + TRICH PHÍ NGOẠI TỆ			
			if(trichphi.item(0).checked == false){
				var pnganhangNT = document.getElementsByName("pnganhangNT");
	 			var pnhNT;
				if(pnganhangNT.item(0).value != ''){
					pnhNT = pnganhangNT.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhNT = 0;
				}					

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(pnhNT)*parseFloat(tg), 0));

				var thuevatNT = document.getElementsByName("vatNT");
		 		var vatNT;
				if(thuevatNT.item(0).value != ''){
					vatNT = thuevatNT.item(0).value.replace(/,/g,"");
					 
				}else{
					vatNT = 0;
				}					
				
				document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(vatNT)*parseFloat(tg), 0));
				
	 			document.getElementById("pnganhangNT").value = DinhDangDonGia((parseFloat(pnhNT)).toFixed(2));

	 			document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(document.getElementById("vatNT").value)).toFixed(2));
	 			
	 			tongttVND = parseFloat(sotienttVND) + parseFloat(pnhNT)*parseFloat(tg) + parseFloat(vatNT)*parseFloat(tg);
	 			
	 			tongttNT = parseFloat(sotienttNT) + parseFloat(pnhNT) + parseFloat(vatNT);
	 			
			}else{  // THANH TOÁN CHO LOAI "KHAC" BANG NGOẠI TỆ + TRICH PHÍ VND	
				var pnganhangVND = document.getElementsByName("pnganhangVND");
	 			var pnhVND;
				if(pnganhangVND.item(0).value != ''){
					pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhVND = 0;
				}					

				var thuevatVND = document.getElementsByName("vatVND");
		 		var vatVND;
				if(thuevatVND.item(0).value != ''){
					vatVND = thuevatVND.item(0).value.replace(/,/g,"");
					 
				}else{
					vatVND = 0;
				}					
				
				document.getElementById("pnganhangNT").value = "0";
				document.getElementById("vatNT").value = "0";

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("pnganhangVND").value.replace(/,/g,"")), 0));
	 			document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("vatVND").value.replace(/,/g,"")), 0));
				
				tongttNT = parseFloat(sotienttNT) ;
				tongttVND = parseFloat(sotienttVND) + parseFloat(pnhVND) + parseFloat(vatVND);
				
			}
	   			  
			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongttVND, 0));
			document.getElementById("tongtienNT").value = DinhDangDonGia((tongttNT).toFixed(2));		
			
			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value; 			
				
			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
				
			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(document.getElementById("vatVND").value.replace(/,/g,""))*100/document.getElementById("pnganhangVND").value.replace(/,/g,"")).toFixed(2));
				
			document.getElementById("tigia").value = DinhDangTien(document.getElementById("tigia").value); 		  
	  }
    
  }
	}
	
	function ThanhToan_2(n)
	{
		var trichphi = document.getElementsByName("trichphi");
		var trahet = document.getElementsByName("trahet");
		var avat = document.getElementsByName("avat");
		var thanhtoan = document.getElementsByName("thanhtoan");
		var conlai = document.getElementsByName("conlai");
		 
		var tigia = document.getElementsByName("tigia");
		var tg = tigia.item(0).value.replace(/,/g,"");

		var tienteId = document.getElementsByName("tienteId");
		
		var tongtienNT = 0;
		var tongtienVND = 0;
		var sotienNT;
		
//		var tigiaHD = document.getElementsByName("tigiaHD");
		var loaithanhtoan = document.getElementsByName("loaithanhtoan");

  if(loaithanhtoan.item(0).value != "6"){ // LOAI THANH TOAN HOA DON / TAM UNG
// VÒNG LẶP CHO TẤT CẢ HÓA ĐƠN
	 	for(i = 0; i < trahet.length; i++)
	 	{

// CHỌN TRẢ HẾT HÓA ĐƠN
			if(trahet.item(i).checked ) 
			{
// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN				
				if(n == 100){ 
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
					}else{
						tt = 0;
					}												

// CHỌN TRẢ HẾT HÓA ĐƠN + THAY ĐỒI SỐ TIỀN THANH TOÁN + THANH TOAN HOA DON TIEN VNĐ					
// CHỈ ĐƯỢC THANH TOÁN TỐI ĐA BẰNG SỐ TIỀN HÓA ĐƠN
					if(tienteId.item(0).value == "100000"){ 
						if((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
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
						if((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
							conlai.item(i).value =DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));

							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tg);
									
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
							trahet.item(i).checked = false;
						
						}else{
							thanhtoan.item(i).value = sotienNT.item(i).value;
							conlai.item(i).value = parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value);
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");
							
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
							
							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tg);
							
							trahet.item(i).checked = true;
						}						
					}
// CHỌN TRẢ HẾT HÓA ĐƠN + KHÔNG THAY ĐỔI SỐ TIỀN 
				}else{ 
// CHỌN TRẢ HẾT HÓA ĐƠN + KHÔNG THAY ĐỔI SỐ TIỀN
// THANH TOÁN CHO HÓA ĐƠN VNĐ
					if(tienteId.item(0).value == "100000"){ 
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = DinhDangDonGia((parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
					
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
						
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
						avat.item(i).value = DinhDangTien(roundNumber(tt, 0));
						
					}else{
						var sotienNT = document.getElementsByName("sotienNT");
						thanhtoan.item(i).value = sotienNT.item(i).value;
						conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
					
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tg);

						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						sotienNT.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
					}					
				}
				
// CLICK BỎ CHỌN TRẢ HẾT HÓA ĐƠN
			}else if(i == n){ 
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
					if((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0){
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
					
					avat.item(i).value = DinhDangTien(avat.item(i).value.replace(/,/g,""));
					
// KHÔNG CHỌN TRẢ HẾT HÓA ĐƠN + THANH TOÁN HÓA ĐƠN NGOẠI TỆ						
				}else{ 
					var sotienNT = document.getElementsByName("sotienNT");

					if((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0){
						conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));
				
						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tg);
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						
					}else{
						thanhtoan.item(i).value = sotienNT.item(i).value;
						conlai.item(i).value = parseFloat(sotienNT.item(i).value) - parseFloat(thanhtoan.item(i).value);
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						
						tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)*parseFloat(tg);
						
						trahet.item(i).checked = true;
					}
					
					sotienNT.item(i).value = DinhDangDonGia(parseFloat(sotienNT.item(i).value.replace(/,/g,"")).toFixed(2));
				}				
			}
	 	}
	 	
// THANH TOÁN CHO HÓA ĐƠN VNĐ	 	
	 	if(tienteId.item(0).value == "100000"){ 

	 		document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(tongtienVND, 0));

 			var pnganhangVND = document.getElementsByName("pnganhangVND");

 			var pnhVND;
			if(pnganhangVND.item(0).value != ''){
				pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
				 
			}else{
				pnhVND = 0;
			}					

 			var thuevatVND = document.getElementsByName("vatVND");
 			
 			var vatVND;
			if(thuevatVND.item(0).value != ''){
				vatVND = thuevatVND.item(0).value.replace(/,/g,"");
				 
			}else{
				vatVND = 0;
			}

 			var tongthanhtoan = parseFloat(tongtienVND) + parseFloat(pnhVND) + parseFloat(vatVND);

 			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongthanhtoan, 0));
 			
 			document.getElementById("vatVND").value = DinhDangTien(document.getElementById("vatVND").value);
 			document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(pnhVND, 0));

 			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value;
 			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
 			
 			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(vatVND)*100/parseFloat(pnhVND)).toFixed(2));

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ	 	
	 	}else{ 
	 		
			var tongttVND = 0;
	 		var tongttNT = 0;
	 		
	 		document.getElementById("sotienHDNT").value = DinhDangDonGia((tongtienNT).toFixed(2));
	 		document.getElementById("sotienHDVND").value = DinhDangTien(tongtienVND, 0);

	 		
// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ + TRICH PHÍ NGOẠI TỆ			
			if(trichphi.item(0).checked == false){ 
				var pnganhangNT = document.getElementsByName("pnganhangNT");
	 			var pnhNT;
				if(pnganhangNT.item(0).value != ''){
					pnhNT = pnganhangNT.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhNT = 0;
				}					

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(pnhNT)*parseFloat(tg), 0));

				var thuevatNT = document.getElementsByName("vatNT");
		 		var vatNT;
				if(thuevatNT.item(0).value != ''){
					vatNT = thuevatNT.item(0).value.replace(/,/g,"");
					 
				}else{
					vatNT = 0;
				}					
				
				document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(vatNT)*parseFloat(tg), 0));
				
	 			document.getElementById("pnganhangNT").value = DinhDangDonGia((parseFloat(pnhNT)).toFixed(2));

	 			document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(document.getElementById("vatNT").value)).toFixed(2));
	 			
	 			tongttVND = parseFloat(tongtienVND) + parseFloat(pnhNT)*parseFloat(tg) + parseFloat(vatNT)*parseFloat(tg);
	 			
	 			tongttNT = parseFloat(tongtienNT) + parseFloat(pnhNT) + parseFloat(vatNT);

// THANH TOÁN CHO HÓA ĐƠN NGOẠI TỆ + TRICH PHÍ VNĐ			
	 			
			}else{ 
				var pnganhangVND = document.getElementsByName("pnganhangVND");
	 			var pnhVND;
				if(pnganhangVND.item(0).value != ''){
					pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhVND = 0;
				}					

				var thuevatVND = document.getElementsByName("vatVND");
		 		var vatVND;
				if(thuevatVND.item(0).value != ''){
					vatVND = thuevatVND.item(0).value.replace(/,/g,"");
					 
				}else{
					vatVND = 0;
				}					
				
				document.getElementById("pnganhangNT").value = "0";
				document.getElementById("vatNT").value = "0";

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("pnganhangVND").value.replace(/,/g,"")), 0));
	 			document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("vatVND").value.replace(/,/g,"")), 0));
				
				tongttNT = parseFloat(tongtienNT) ;
				tongttVND = parseFloat(tongtienVND) + parseFloat(pnhVND) + parseFloat(vatVND);
			}
			
			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongttVND, 0));
			document.getElementById("tongtienNT").value = DinhDangDonGia((tongttNT).toFixed(2));
			
			var chenhlechVND = parseFloat(tongttNT)*parseFloat(tg) - parseFloat(tongttVND);
			document.getElementById("chenhlechVND").value = DinhDangTien(roundNumber(chenhlechVND, 0));
			
 			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value; 			
 			
 			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
 			
 			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(document.getElementById("vatVND").value.replace(/,/g,""))*100/document.getElementById("pnganhangVND").value.replace(/,/g,"")).toFixed(2));
 			
 			document.getElementById("tigia").value = DinhDangTien(document.getElementById("tigia").value);
	 	}
  } else { // LOAI THANH TOAN : KHÁC
	// THANH TOAN VND
	  if(tienteId.item(0).value == "100000"){
		  var sotienthanhtoan = document.getElementsByName("sotienHDVND");
          var sotienthanhtoanVND;
			if(sotienthanhtoan.item(0).value != ''){
				sotienthanhtoanVND = sotienthanhtoan.item(0).value.replace(/,/g,"");
				 
			}else{
				sotienthanhtoanVND = 0;
			}
		  
		  document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(sotienthanhtoanVND, 0));

		  var pnganhangVND = document.getElementsByName("pnganhangVND");

		  var pnhVND;
			if(pnganhangVND.item(0).value != ''){
				pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
				 
			}else{
				pnhVND = 0;
			}					

			var thuevatVND = document.getElementsByName("vatVND");
			
			var vatVND;
			if(thuevatVND.item(0).value != ''){
				vatVND = thuevatVND.item(0).value.replace(/,/g,"");
				 
			}else{
				vatVND = 0;
			}
			
			var tongthanhtoan = parseFloat(sotienthanhtoanVND) + parseFloat(pnhVND) + parseFloat(vatVND);

			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongthanhtoan, 0));
			
			document.getElementById("vatVND").value = DinhDangTien(document.getElementById("vatVND").value);
			document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(pnhVND, 0));

			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value;
			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
			
			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(vatVND)*100/parseFloat(pnhVND)).toFixed(2)); 
	      
	  }else { // THANH TOAN BANG NGOAI TE
		  
		  var sotienthanhtoanNT = document.getElementsByName("sotienHDNT");
		  var sotienttNT= 0;
	      if(sotienthanhtoanNT.item(0).value != ''){
	    	  sotienttNT = sotienthanhtoanNT.item(0).value.replace(/,/g,"");
	      }else {
	    	  sotienttNT = 0;
	      }
	      var sotienttVND = parseFloat(sotienttNT)*parseFloat(tg);
	      document.getElementById("sotienHDVND").value = DinhDangTien(roundNumber(parseFloat(sotienttNT)*parseFloat(tg), 0)); 
	      document.getElementById("sotienHDNT").value = DinhDangTien(sotienttNT); 
	      
	   // THANH TOÁN CHO LOAI "KHAC" BANG NGOẠI TỆ + TRICH PHÍ NGOẠI TỆ			
			if(trichphi.item(0).checked == false){
				var pnganhangNT = document.getElementsByName("pnganhangNT");
	 			var pnhNT;
				if(pnganhangNT.item(0).value != ''){
					pnhNT = pnganhangNT.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhNT = 0;
				}					

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(pnhNT)*parseFloat(tg), 0));

				var thuevatNT = document.getElementsByName("vatNT");
		 		var vatNT;
				if(thuevatNT.item(0).value != ''){
					vatNT = thuevatNT.item(0).value.replace(/,/g,"");
					 
				}else{
					vatNT = 0;
				}					
				
				document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(vatNT)*parseFloat(tg), 0));
				
	 			document.getElementById("pnganhangNT").value = DinhDangDonGia((parseFloat(pnhNT)).toFixed(2));

	 			document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(document.getElementById("vatNT").value)).toFixed(2));
	 			
	 			tongttVND = parseFloat(sotienttVND) + parseFloat(pnhNT)*parseFloat(tg) + parseFloat(vatNT)*parseFloat(tg);
	 			
	 			tongttNT = parseFloat(sotienttNT) + parseFloat(pnhNT) + parseFloat(vatNT);
	 			
			}else{  // THANH TOÁN CHO LOAI "KHAC" BANG NGOẠI TỆ + TRICH PHÍ VND	
				var pnganhangVND = document.getElementsByName("pnganhangVND");
	 			var pnhVND;
				if(pnganhangVND.item(0).value != ''){
					pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");
					 
				}else{
					pnhVND = 0;
				}					

				var thuevatVND = document.getElementsByName("vatVND");
		 		var vatVND;
				if(thuevatVND.item(0).value != ''){
					vatVND = thuevatVND.item(0).value.replace(/,/g,"");
					 
				}else{
					vatVND = 0;
				}					
				
				document.getElementById("pnganhangNT").value = "0";
				document.getElementById("vatNT").value = "0";

				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("pnganhangVND").value.replace(/,/g,"")), 0));
	 			document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(document.getElementById("vatVND").value.replace(/,/g,"")), 0));
				
				tongttNT = parseFloat(sotienttNT) ;
				tongttVND = parseFloat(sotienttVND) + parseFloat(pnhVND) + parseFloat(vatVND);
				
			}
	   			  
			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongttVND, 0));
			document.getElementById("tongtienNT").value = DinhDangDonGia((tongttNT).toFixed(2));		
			
			document.getElementById("tienhang_VAT").value = document.getElementById("pnganhangVND").value; 			
				
			document.getElementById("tienthue_VAT").value = document.getElementById("vatVND").value;
				
			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(document.getElementById("vatVND").value.replace(/,/g,""))*100/document.getElementById("pnganhangVND").value.replace(/,/g,"")).toFixed(2));
				
			document.getElementById("tigia").value = DinhDangTien(document.getElementById("tigia").value); 		  
	  }
    
  }
	}

	function ThaydoiSotienNT(){
		var trichphi = document.getElementsByName("trichphi");
		var tigia = document.getElementsByName("tigia");
		var tg = tigia.item(0).value.replace(/,/g,"");
		
		
		if(trichphi.item(0).checked == false){ // TRICH PHI BANG NGOAI TE 
			
			var tongtienNT = document.getElementsByName("tongtienNT");
			var tongNT = tongtienNT.item(0).value.replace(/,/g,"");

			document.getElementById("tongtienVND").value = DinhDangTien(parseFloat(tongNT)*parseFloat(tg));

			var sotienHDVND = document.getElementsByName("sotienHDVND");
			var stHDVND = sotienHDVND.item(0).value.replace(/,/g,"");

			var pnganhangVND = document.getElementsByName("pnganhangVND");
			var pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");

			var vatVND = document.getElementsByName("vatVND");
			var vat = vatVND.item(0).value.replace(/,/g,"");

			var chenhlech = parseFloat(tongNT)*parseFloat(tg) - (parseFloat(stHDVND) + parseFloat(pnhVND) + parseFloat(vat));
			document.getElementById("chenhlechVND").value = DinhDangTien(chenhlech);
			document.getElementById("tongtienNT").value = DinhDangDonGia((tongNT).toFixed(2));
		}else{ // TRICH PHI BANG VND

			var tongtienNT = document.getElementsByName("tongtienNT");
			var tongNT = tongtienNT.item(0).value.replace(/,/g,"");

			var tongVND = parseFloat(tongNT)*parseFloat(tg);

			document.getElementById("tongtienVND").value = DinhDangTien(tongVND);

			var sotienHDVND = document.getElementsByName("sotienHDVND");
			var stHDVND = sotienHDVND.item(0).value.replace(/,/g,"");

			var pnganhangVND = document.getElementsByName("pnganhangVND");
			var pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");

			var vatVND = document.getElementsByName("vatVND");
			var vat = vatVND.item(0).value.replace(/,/g,"");

			var chenhlech = parseFloat(tongVND) - (parseFloat(stHDVND) + parseFloat(pnhVND) + parseFloat(vat));
			document.getElementById("chenhlechVND").value = DinhDangTien(chenhlech);
			
		}
	}
	
	function ThaydoiSotienTT(){
		var tongtienVND = document.getElementsByName("tongtienVND");
		var tongVND = tongtienVND.item(0).value.replace(/,/g,"");
		
		var sotienHDVND = document.getElementsByName("sotienHDVND");
		var stHDVND = sotienHDVND.item(0).value.replace(/,/g,"");

		var pnganhangVND = document.getElementsByName("pnganhangVND");
		var pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");

		var vatVND = document.getElementsByName("vatVND");
		var vat = vatVND.item(0).value.replace(/,/g,"");

		document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongVND, 0));
		
		var chenhlech = parseFloat(tongVND) - (parseFloat(stHDVND) + parseFloat(pnhVND) + parseFloat(vat));

		document.getElementById("chenhlechVND").value = DinhDangTien(roundNumber(chenhlech, 0));
	}

	function changeNcc()
	{
		var nccId=document.getElementById("nccId");
		//alert("da toi day");
		//alert(nccId.value);
		//alert(nccId.value.indexOf(' -- '));
		if(nccId.value.indexOf(' -- ') > 0 )
		{
			//alert("da toi day roi ne");
			//submitform();
			 document.forms['tthdForm'].action.value='submit';
		     document.forms["tthdForm"].submit();
		}
		
		
	}
	function TongTienThanhToanVND()
	{
		var sotienthanhtoanVND = document.getElementsByName("sotienthanhtoanVND");
		var stttVND = sotienthanhtoanVND.item(0).value.replace(/,/g,"");
		
		var pnganhangVND = document.getElementsByName("pnganhangVND");
		var pnhVND = pnganhangVND.item(0).value.replace(/,/g,"");

		var vatVND = document.getElementsByName("vatVND");
		var vat = vatVND.item(0).value.replace(/,/g,"");
		
		var tongVND = parseFloat(stttVND) + parseFloat(pnhVND) + parseFloat(vat);
		
		document.getElementById("sotienthanhtoanVND").value = DinhDangTien(stttVND);
		document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongVND, 0));
	}
	
	function DinhDangTienTT()
	{
		var giatrinhap = document.getElementById("sotienthanhtoan");
		giatrinhap.value = DinhDangTien(giatrinhap.value);
	}
	
	function PhanBoTien()
	{	
		 var kyhieuhd = document.getElementsByName("kyhieuhd");
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 
		 for(j = 0; j < kyhieuhd.length; j++)
		 {
			 thanhtoan.item(j).value = "";
			 conlai.item(j).value = "";
			 trahet.item(j).checked = false;
		 }
		 
		 var sotienphanbo = document.getElementById("sotienthanhtoan").value.replace(/,/g,"");
		 //alert(sotienphanbo);

		 var tongtien = 0;
		 for(i = 0; i < kyhieuhd.length; i++)
		 {
			tienAvat =  avat.item(i).value.replace(/,/g, "");
			tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
			
			if(tongtien < parseFloat(sotienphanbo))
			{
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
				conlai.item(i).value = 0;
				trahet.item(i).checked = true;
			}
			else
			{
				tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
				var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
				conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
				if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
					trahet.item(i).checked = true;
				else
					trahet.item(i).checked = false;
				
				break;
			}
		 } 
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46|| keypressed == 45)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var ngayghinhan = document.getElementById("ngayghinhan");
 		 if(ngayghinhan.value == "")
		 {
			alert("Vui long chon ngay ghi nhan");
			return;
		 }
		 
		 var sotienHDVND = document.getElementById("sotienHDVND");
		 if(sotienHDVND.value == "" || sotienHDVND.value == "0")
		 {
			alert("Vui long nhap so tien thanh toan");
			return;
		 } 
		 

		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tthdForm'].action.value='save';
	     document.forms['tthdForm'].submit();
	 }
	 
	 function ChangeTienTe(){
		 document.forms['tthdForm'].action.value= 'changeTT';
	     document.forms["tthdForm"].submit();
		 
	 }

	 
	 function submitform()
	 { 
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function submitform_HTTT(){
		 document.forms['tthdForm'].action.value='changeHTTT';
	     document.forms["tthdForm"].submit();
	 
	 }
	 
	 function nextform()
	 { 
		 document.forms['tthdForm'].action.value='next';
	     document.forms["tthdForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['tthdForm'].action.value='changePO';
	     document.forms["tthdForm"].submit();
	 }
	 function changeLoaiThanhToan()
	 { 
		 document.forms['tthdForm'].action.value='loaithanhtoan';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function changeNhomNCC()
	 {
		document.forms['tthdForm'].action.value='nhomncccn';
		document.forms["tthdForm"].submit();
		
	 } 
	 
	function replacesdinhkhoan()
	{
		var dinhkhoanOld =  document.getElementById("dinhkhoannoId").value;
		dinhkhoanOld = parseInt(dinhkhoanOld);
		var temp = document.getElementById("dinhkhoanno").value;
		var dinhkhoanNew = temp.substring(0, parseInt(temp.indexOf(" -- ")));
					
		if(dinhkhoanNew.length >0)
		{
			if(dinhkhoanOld!=dinhkhoanNew)
			{									
				document.forms["tthdForm"].dinhkhoannoId.value = dinhkhoanNew;			
				document.forms["tthdForm"].submit();
			}
		}		
		setTimeout(replacesdinhkhoan, 300);
	}
	
	function mauhoadon(){
		var mauHD = document.getElementById("mauHD_VAT").value;
		var maHD;
		
		if(mauHD.length >= 6) 
			maHD =  mauHD.substring(0, 6) ;
		else
			maHD = mauHD;
		
		document.getElementById("maHD_VAT").value = maHD;
	} 
	
	function tinhthue(){
		var tienhang = document.getElementById("tienhang_VAT").value;
		var thuesuat = document.getElementById("thuesuat_VAT").value;		
		var tienteId = document.getElementsByName("tienteId");
		
		tienhang = tienhang.replace(/,/g, "");
		thuesuat = thuesuat.replace(/,/g, "");

		var tigia = document.getElementById("tigia").value;
		tigia = tigia.replace(/,/g, "");

		document.getElementById("tienhang_VAT").value = DinhDangTien(roundNumber(parseFloat(tienhang), 0));		
		document.getElementById("tienthue_VAT").value = DinhDangTien(roundNumber(parseFloat(tienhang)*parseFloat(thuesuat)/100, 0));


		if(tienteId.item(0).value != "100000"){ //tien te la ngoai te
			var trichphi = document.getElementsByName("trichphi");
			if(trichphi.item(0).checked == true){ //Ngoai te, nhung trich phi bang VND
				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(tienhang), 0));
				document.getElementById("vatVND").value = document.getElementById("tienthue_VAT").value;	
				
				document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(tienhang)*parseFloat(thuesuat)/(100*parseFloat(tigia))).toFixed(2));
				document.getElementById("pnganhangNT").value = DinhDangDonGia(parseFloat(tienhang)/parseFloat(tigia));
				
				
			}else{ //Ngoai te, va trich phi bang ngoai te 
				document.getElementById("pnganhangNT").value = DinhDangTien(roundNumber(parseFloat(tienhang), 0));
				document.getElementById("vatNT").value = document.getElementById("tienthue_VAT").value;	
				
				document.getElementById("vatVND").value = DinhDangTien(roundNumber(parseFloat(tienhang)*parseFloat(thuesuat)*parseFloat(tigia)/100, 0));
				document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(parseFloat(tienhang)*parseFloat(tigia), 0));

			}
		}else{ //tien te la tien Viet

			document.getElementById("pnganhangVND").value = document.getElementById("tienhang_VAT").value;
			document.getElementById("vatVND").value = document.getElementById("tienthue_VAT").value;	
 			
 			var sotienHDVND = document.getElementsByName("sotienHDVND");
 			
 			var sotienVND;
			if(sotienHDVND.item(0).value != ''){
				sotienVND = sotienHDVND.item(0).value.replace(/,/g,"");
				 
			}else{
				sotienVND = 0;
			}					

			var tongtienVND = parseFloat(sotienVND) + parseFloat(tienhang) + parseFloat(tienhang)*parseFloat(thuesuat)/100;

 			document.getElementById("tongtienVND").value = DinhDangTien(roundNumber(tongtienVND, 0));
 			
		}		

		//ThanhToan(100);
	}
	
	function tinhthuesuat(){
		var tienhang = document.getElementById("tienhang_VAT").value;
		var tienthue = document.getElementById("tienthue_VAT").value;

		tienhang = tienhang.replace(/,/g, "");
		tienhue = tienthue.replace(/,/g, "");
		
		var tigia = document.getElementById("tigia").value;
		tigia = tigia.replace(/,/g, "");

		if(tienteId.item(0).value != "100000"){ //tien te la ngoai te
		
			var trichphi = document.getElementsByName("trichphi");
			if(trichphi.item(0).checked == true){ //Ngoai te, nhung trich phi bang VND

			}else{ //Ngoai te, va trich phi bang ngoai te
				
			}
		}else{ //tien te la tien Viet
			document.getElementById("tienhang_VAT").value = DinhDangTien(roundNumber(document.getElementById("tienhang_VAT").value, 0));
			document.getElementById("tienthue_VAT").value = DinhDangTien(roundNumber(document.getElementById("tienthue_VAT").value, 0));
			document.getElementById("thuesuat_VAT").value = DinhDangDonGia((parseFloat(tienthue)*100/parseFloat(tienhang)).toFixed(2));
			
			document.getElementById("vatVND").value = document.getElementById("tienthue_VAT").value;
			document.getElementById("vatNT").value = DinhDangDonGia((parseFloat(tienhue)/parseFloat(tigia)).toFixed(2));
			
			document.getElementById("pnganhangVND").value = DinhDangTien(roundNumber(tienhang, 0));
			document.getElementById("pnganhangNT").value = DinhDangDonGia(parseFloat(tienhang)/parseFloat(tigia));
			
		}
		
		
		ThanhToan(100);
	}
	
	
	function ChangeNganHang(){
		var nganhang = document.getElementById("nghangTen").value;
//		NGÂN HÀNG TMCP NGOẠI THƯƠNG - THỦ ĐỨC - TP.HCM, Mã số thuế: Chua có [ 100039:100051 ]
		
		if( nganhang.indexOf(" - ") > 0){
			var nghangTen = nganhang.substring(0, nganhang.indexOf(" - "));
			document.getElementById("nghangTen").value = nghangTen;
		
			var mst = nganhang.substring(nganhang.indexOf(": ") + 2, nganhang.indexOf("["));
			document.getElementById("mst_VAT").value = mst;
			
			var nhId_VAT = nganhang.substring(nganhang.indexOf("[ ") + 2, nganhang.indexOf(":", nganhang.indexOf("[ "))); 
			document.getElementById("nhId_VAT").value = nhId_VAT;
			
			var cnId_VAT = nganhang.substring(nganhang.indexOf(":", nganhang.indexOf("[ "))+ 1, nganhang.length-2); // Tim ":" bat dau tu "[ "
			
			document.getElementById("cnId_VAT").value = cnId_VAT;
			
		}
		setTimeout(ChangeNganHang, 300);
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpDenghithanhtoanNCCUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= tthdBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải trả > Đề nghị thanh toán NCC > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDenghithanhtoanNCCSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        <%--   <A href="../../ErpDenghithanhtoanNCCPdfSvl?userId=<%= tthdBean.getUserId() %>&id=<%= tthdBean.getId() %>&httt=<%= tthdBean.getHtttId() %>" >
	        <IMG src="../images/Printer30.png" title="In phieu" alt="In phieu" border ="1px" style="border-style:outset"></A> --%>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= tthdBean.getMsg() %></textarea>
		         <% tthdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Đề nghị thanh toán NCC </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = '0'>		            	
            	<TR>
                   <TD width="20%" class="plainlabel" valign="top">Loại thanh toán </TD>
                    <TD class="plainlabel" valign="top"  >
                    	 <select name="doituongthanhtoan" id="doituongthanhtoan" onChange="submitform();" style = "width:200px">
			              		<option value="1" >Nhà cung cấp</option>				              		
					      </select>
                    </TD>
                    
                     <TD class="plainlabel"  style="width:150px" align = "right">
                    <%if(Double.parseDouble(tthdBean.getCheckThanhtoantuTV()) == 1){ %>
                    	<input type="checkbox" id="thanhtoantuTV" name ="thanhtoantuTV" value= "1" checked="checked" onchange="submitform();">
                    <%}else{ %>
                    	<input type="checkbox" id="thanhtoantuTV" name ="thanhtoantuTV" value= "1" onchange="submitform();"() >
                    <%} %>
                    </TD>
                    
                    <TD class="plainlabel" colspan = 2>Thanh toán từ tiền vay</TD>
                    
                    <%//NẾU CÓ CHECKED THANHTOANTUTIENVAY THÌ CHỈ ĐƯỢC SỬA Ô PHÍ && THUẾ, CÁC Ô CÒN LẠI KHÔNG ĐƯỢC SỬA %>
                	</TR> 				
	                <TR>
	                 <TD width="20%" class="plainlabel" valign="top">Ngày chứng từ</TD>
	                                 
	                    <TD  class="plainlabel" valign="top" colspan = 4>
	                    	<input type="text"  class="days" id="ngayghinhan" name="ngayghinhan" value="<%= tthdBean.getNgayghinhan() %>" 
	                    		maxlength="10"  onChange = "ChangeTienTe();" />
	                    </TD>
	                    
	                </TR> 
	                
	                <TR>
	                    <TD class="plainlabel">Nhà cung cấp / Nhóm NCC
	                    	<% if(tthdBean.getNhomNCCCN().equals("1")) { %>
									<input type="checkbox" id="nhomncccn" name="nhomncccn" value="1" checked = "checked" onchange="ChangeTienTe();" >
									<% } else {  %>
										<input type="checkbox" id="nhomncccn" name="nhomncccn" value="1" onchange="ChangeTienTe();" >
									<% } %>
	                    </TD>
	                    <% if(tthdBean.getNhomNCCCN().equals("1")) { %>
	                    <TD class="plainlabel" colspan="3">
		                  <select name="nhomnccId" id="nhomnccId" style="width: 300px" onChange="ChangeTienTe();">
                        	<option value=""> </option>
                        	<%
                        		if(nhomnccList != null)
                        		{
                        			try
                        			{
                        			while(nhomnccList.next())
                        			{  
                        			if( nhomnccList.getString("pk_seq").equals(tthdBean.getNhomNCCCNId())){ %>
                        				<option value="<%= nhomnccList.getString("pk_seq") %>" selected="selected" ><%= nhomnccList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nhomnccList.getString("pk_seq") %>" ><%= nhomnccList.getString("ten") %></option>
                        		 <% } } nhomnccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
		               </TD>
	                    <%}else{ %>
	                    <TD class="plainlabel" colspan="3">
		                  <select name="nccId" id="nccId" style="width:300px" onChange="ChangeTienTe();">
                        	<option value=""> </option>
                        	<%
                        		if(nccList != null)
                        		{
                        			try
                        			{
                        			while(nccList.next())
                        			{  
                        			if( nccList.getString("pk_seq").equals(tthdBean.getNccId())){ %>
                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("ten") %></option>
                        		 <% } } nccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
		               </TD> 			                        										
						<%} %>		
	                </TR>
										
                <TR>
                   <TD class="plainlabel">Hình thức thanh toán</TD>               
                    <TD colspan="6" class="plainlabel">
                        <select name="htttId" id="htttId" style="width:200px" onChange = "submitform_HTTT();">
                        	<option value=""> </option>
                        	<%
                        		if(htttList != null)
                        		{
                        			try
                        			{
                        			while(htttList.next())
                        			{  
                        			if( htttList.getString("pk_seq").equals(tthdBean.getHtttId())){ %>
                        				<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ma")%></option>
                        			<%}else { %>
                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ma") %></option>
                        		 <% } } htttList.close();} catch(SQLException ex){
                        			 
                        		 }
                        		}
                        	%>
                        </select>
                     </TD> 
                   
                </TR>

            	<TR>
                    <TD class="plainlabel">Tiền tệ</TD>
                    <TD class="plainlabel" style = "width:100px">
                        <select name="tienteId" id="tienteId" onChange = "ChangeTienTe();" style = "width:200px;">
                        	<%
                        		if(tienteList != null)
                        		{
                        			try
                        			{
                        			while(tienteList.next())
                        			{  
                        			if( tienteList.getString("pk_seq").equals(tthdBean.getTienteId())){ %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 <% } } tienteList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                

                     <TD class="plainlabel" style="width:150px" align = "right">Tỉ giá</TD>
                      <TD class="plainlabel" colspan = 3>
           		<% if(tthdBean.getTienteId().equals("100000")){ %>
                      	<input type="text" name="tigia" Id="tigia" value="<%= formatter2.format(Double.parseDouble(tthdBean.getTigia().replaceAll(",","")))  %>" style = "width:100px;text-align: right;"  readonly>
           		<%}else{ %>
			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	) 
						||  tthdBean.getLoaiThanhToan().equals("0")){ %>                    
           				<input type="text" name="tigia" Id="tigia" value="<%= formatter2.format(Double.parseDouble(tthdBean.getTigia().replaceAll(",","")))  %>" style = "width:100px;text-align: right;"  onchange="ThanhToan(100);" onKeyPress = "return keypress(event);">
           		<%   }else{ %>
           				<input type="text" name="tigia" Id="tigia" value="<%= formatter2.format(Double.parseDouble(tthdBean.getTigia().replaceAll(",","")))  %>" style = "width:100px;text-align: right;"  onchange="ThanhToan(100);" onKeyPress = "return keypress(event);">
           		<%   } %>
           		<%} %>
                      </TD> 
            	
            	</TR>
				<%// THANH TOAN HOA DON DUNG NGOAI TE %>
            <% if(!tthdBean.getTienteId().equals("100000")){ %>
            	<TR>
                    <TD class="plainlabel">Số tiền hóa đơn (Ngoại tệ) </TD>
                    <TD class="plainlabel">
                         <input type="text" style="text-align: right;" name="sotienHDNT" id = "sotienHDNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getSotienHDNT().replaceAll(",",""))) %>" readonly  > 
                     </TD>

                    <TD class="plainlabel">Số tiền hóa đơn (VNĐ) </TD>
					
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="sotienHDVND" id = "sotienHDVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getSotienHD().replaceAll(",",""))) %>" readonly  > 
                     </TD>
                      
                </TR>

                <TR>
                  	<% if(tthdBean.getTrichphi().equals("0")) { %>

                    <TD class="plainlabel" colspan = 5>Trích phí bằng VNĐ
							<input type="checkbox" id="trichphi" name="trichphi" value="1"  onChange= "ChangeTienTe();" >
	                </TD>
	               <%}else{ %> 
                    <TD class="plainlabel">Trích phí bằng VNĐ
							<input type="checkbox" id="trichphi" name="trichphi" value="1" checked = "checked" onChange= "ChangeTienTe();" >
	                </TD>
					<TD class="plainlabel">Tài khoản trích phí (VND) </TD>				
					<TD class="plainlabel" colspan = 3>
                     
                        <select name="sotaikhoan_tp" id="sotaikhoan_tp" style="width: 500px" onChange = "submitform();">
                        		<OPTION VALUE = "">&nbsp</OPTION>
                        	<%
                        		if(sotkRs_tp != null)
                        		{
                        			try
                        			{
                        			while(sotkRs_tp.next())
                        			{  
                        			if( sotkRs_tp.getString("SOTAIKHOAN").equals(tthdBean.getSotaikhoan_tp())){ %>
                        				<option value="<%= sotkRs_tp.getString("SOTAIKHOAN") %>" selected="selected" ><%= sotkRs_tp.getString("TAIKHOAN")%></option>
                        			<%}else { %>
                        				<option value="<%= sotkRs_tp.getString("SOTAIKHOAN") %>" ><%= sotkRs_tp.getString("TAIKHOAN") %></option>
                        		 <% } } sotkRs_tp.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>

                    </TD>
					
				   <%} %>	
                </TR>                    
               
	<%// THANH TOAN HOA DON DUNG NGOAI TE - TRICH PHI BANG NGOAI TE
               if(tthdBean.getTrichphi().equals("0")) { %>

				<TR>
                    <TD class="plainlabel" style = "width:150px">Phí ngân hàng (Ngoại tệ)</TD>
                    <TD  class="plainlabel">

			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	) 
						||  tthdBean.getLoaiThanhToan().equals("0")){ %>                    
                    
                    	<input type="text" style="text-align: right;" name="pnganhangNT" id = "pnganhangNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getPhinganhangNT().replaceAll(",",""))) %>" onchange="ThanhToan(100)" onKeyPress = "return keypress(event);"  >
			<%	 }else{ %>
						<input type="text" style="text-align: right;" name="pnganhangNT" id = "pnganhangNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getPhinganhangNT().replaceAll(",",""))) %>" onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);"  >
			<%   } %>                    
                     </TD> 
				
                    <TD class="plainlabel" style = "width:150px">Phí ngân hàng (VNĐ)</TD>
                    <TD  class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="pnganhangVND" id = "pnganhangVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getPhinganhang().replaceAll(",",""))) %>" readonly  > 
                     </TD> 

				</TR>
            	<TR>
                    <TD class="plainlabel">Thuế VAT (Ngoại tệ) </TD>
                    <TD  class="plainlabel">
			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	) 
						||  tthdBean.getLoaiThanhToan().equals("0")){ %>                    
                    
                         <input type="text" style="text-align: right;" name="vatNT" id = "vatNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getThueVATNT().replaceAll(",",""))) %>"  onchange="ThanhToan(100)" onKeyPress = "return keypress(event);" > 
            <%	 }else{ %>
                   		<input type="text" style="text-align: right;" name="vatNT" id = "vatNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getThueVATNT().replaceAll(",",""))) %>"  onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);" >
            <%   } %>
                     </TD> 

                    <TD class="plainlabel">Thuế VAT (VND) </TD>
                    <TD  class="plainlabel" style="width:200px">
                         <input type="text" style="text-align: right;" name="vatVND" id = "vatVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getThueVAT().replaceAll(",",""))) %>" readonly   > 
                    </TD> 

					<TD align = "left" class="plainlabel" >
	                   <A href="" id="tinhVATNT" rel="subcontentVATNT"><img alt="Tính VAT" title="Tính VAT" src="../images/vitriluu.png"></a>
	                   <DIV id="subcontentVATNT" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 900px; padding: 4px; max-height: 300px; overflow: auto; ">
							<TABLE width="100%" align="center">
        						<TR class="tbheader">
						        	<TH width="80px" align = "center">Mã hóa đơn</TH>
						        	<TH width="80px" align = "center">Mẫu hóa đơn</TH>
						        	<TH width="80px" align = "center">Ký hiệu</TH>
						         	<TH width="100px" align = "center">Số hóa đơn</TH>
						         	<TH width="100px" align = "center">Ngày hóa đơn </TH>
									<TH width="100px" align = "center">Tên NCC </TH>
									<TH width="100px" align = "center">MST </TH>
									<TH width="100px" align = "center">Tiền hàng </TH>
									<TH width="100px" align = "center">Thuế suất(%) </TH>
									<TH width="100px" align = "center">Tiền thuế </TH>
								</TR>
					      		<TR>
									<TD>
										<input type="text" name = "maHD_VAT" id = "maHD_VAT" value = "<%= tthdBean.getMahoadon() %>" style="width: 100%" readonly="readonly" >
									</TD>
									<TD>
										<input type="text" name = "mauHD_VAT" id = "mauHD_VAT" value = "<%= tthdBean.getMauhoadon() %>" onChange = "javascript:mauhoadon();" style="width: 100%" >
									</TD>
									<TD>
										<input type="text" name = "kyhieu_VAT" id = "kyhieu_VAT" value = "<%= tthdBean.getKyhieu() %>" style="width: 100%" >
									</TD>
											      		
									<TD>
										<input type="text" name = "sohd_VAT" id = "sohd_VAT" value = "<%= tthdBean.getSohoadon() %>" style="width: 100%" >
									</TD>

									<TD>
										<input type="text" name = "ngayhd_VAT" id = "ngayhd_VAT" value = "<%= tthdBean.getNgayhoadon() %>" style="width: 100%" class="days" readonly="readonly">
									</TD>
								
									<TD>
										<input type="text" name = "nghangTen" id = "nghangTen" value = "<%= tthdBean.getTenNCC_VAT() %>" style="width: 100%" onChange ="ChangeNganHang();" >
										<input type="hidden" name = "nhId_VAT" id = "nhId_VAT" value = "<%= tthdBean.getNhId_VAT() %>" style="width: 100%"  >
										<input type="hidden" name = "cnId_VAT" id = "cnId_VAT" value = "<%= tthdBean.getCnId_VAT() %>" style="width: 100%"  >
										
									</TD>

									<TD>
										<input type="text" name= "mst_VAT" id= "mst_VAT" value = "<%= tthdBean.getMST() %>" style="width: 100%"  >
									</TD>

									<TD>
										<input type="text" name= "tienhang_VAT" id= "tienhang_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienhang().replaceAll(",",""))) %>" style="width: 100%;text-align: right" readonly>
									</TD>

									<TD>
										<input type="text" name= "thuesuat_VAT" id= "thuesuat_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getThuesuat().replaceAll(",",""))) %>" style="width: 100%;text-align: right" readonly>
									</TD>

									<TD>
										<input type="text" name= "tienthue_VAT" id= "tienthue_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienthue().replaceAll(",",""))) %>" style="width: 100%;text-align: right" readonly>
									</TD>
								</TR>
							</TABLE>
						    <DIV align="right">
					             <LABEL style="color: red" ></LABEL>
					               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	              	<a href="javascript:dropdowncontent.hidediv('subcontentVATNT')">Hoàn tất</a>
							</DIV>
						     				
    					</DIV>

					</TD>

                </TR>
            	<TR>
                    <TD class="plainlabel" style = "width:150px">Tổng tiền thanh toán (Ngoại tệ)</TD>
                    <TD class="plainlabel">
                         <input type="text" style="text-align: right;" name="tongtienNT" id = "tongtienNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getSotienttNT().replaceAll(",",""))) %> "  onKeyPress = "return keypress(event);"  onChange = "ThaydoiSotienNT();"  > 
                     </TD> 

                    <TD class="plainlabel" style = "width:150px">Tổng tiền thanh toán (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="tongtienVND" id = "tongtienVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",",""))) %>"   onKeyPress = "return keypress(event);" readonly> 
                     </TD> 
                </TR>
		
            	<TR>
                    <TD class="plainlabel">Nội dung thanh toán </TD>
                    <TD  class="plainlabel" style = "width:350px" >
                        <input type="text" name="noidungthanhtoan" value="<%= tthdBean.getNoidungtt() %>"> 
                     </TD> 
                  
                    
                    <TD align = "right" class="plainlabel" style = "width:200px">Chênh lệch (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="chenhlechVND" id = "chenhlechVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getChenhlech().replaceAll(",",""))) %>" readonly > 
                     </TD> 

                </TR>

<%// THANH TOAN HOP DONG DUNG NGOAI TE - TRICH PHI VNĐ
			}else{ %>        
				<TR>
                    <TD class="plainlabel" style = "width:150px">Phí ngân hàng (Ngoại tệ)</TD>
                    <TD  class="plainlabel">
                    
                    	<input type="text" style="text-align: right;" name="pnganhangNT" id = "pnganhangNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getPhinganhangNT().replaceAll(",",""))) %>"  readonly >
                    
                     </TD> 
				
                    <TD class="plainlabel" style = "width:150px">Phí ngân hàng (VNĐ)</TD>
                    <TD  class="plainlabel" colspan = 3>
			
			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	)){ %>                    
                         <input type="text" style="text-align: right;" name="pnganhangVND" id = "pnganhangVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getPhinganhang().replaceAll(",",""))) %>" onchange="ThanhToan(100)" onKeyPress = "return keypress(event);"  > 
            <%	 }else{ %>
                   		<input type="text" style="text-align: right;" name="pnganhangVND" id = "pnganhangVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getPhinganhang().replaceAll(",",""))) %>" onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);"  >
            <%   } %>
                     </TD> 

				</TR>
            	<TR>
                    <TD class="plainlabel">Thuế VAT (Ngoại tệ) </TD>
                    <TD  class="plainlabel">
                         <input type="text" style="text-align: right;" name="vatNT" id = "vatNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getThueVATNT().replaceAll(",",""))) %>"  readonly> 
                     </TD> 

                    <TD class="plainlabel">Thuế VAT (VND) </TD>
                    <TD  class="plainlabel" style="width:200px">
			<%   if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 || (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 || (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 )){ %>                    
                         <input type="text" style="text-align: right;" name="vatVND" id = "vatVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getThueVAT().replaceAll(",",""))) %>" onchange="ThanhToan(100)" onKeyPress = "return keypress(event);"     > 
            <%   }else{ %>
            	         <input type="text" style="text-align: right;" name="vatVND" id = "vatVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getThueVAT().replaceAll(",",""))) %>" onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);"     >
            <%   } %>
                    </TD> 

					<TD align = "left" class="plainlabel" >
	                   <A href="" id="tinhVATNT" rel="subcontentVATNT"><img alt="Tính VAT" title="Tính VAT" src="../images/vitriluu.png"></a>
	                   <DIV id="subcontentVATNT" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 900px; padding: 4px; max-height: 300px; overflow: auto; ">
							<TABLE width="100%" align="center">
        						<TR class="tbheader">
						        	<TH width="80px" align = "center">Mã hóa đơn</TH>
						        	<TH width="80px" align = "center">Mẫu hóa đơn</TH>
						        	<TH width="80px" align = "center">Ký hiệu</TH>
						         	<TH width="80px" align = "center">Số hóa đơn</TH>
						         	<TH width="80px" align = "center">Ngày hóa đơn </TH>
									<TH width="100px" align = "center">Tên NCC </TH>
									<TH width="100px" align = "center">MST </TH>
									<TH width="100px" align = "center">Tiền hàng </TH>
									<TH width="100px" align = "center">Thuế suất(%) </TH>
									<TH width="100px" align = "center">Tiền thuế </TH>
								</TR>
					      		<TR>
									<TD>
										<input type="text" name = "maHD_VAT" id = "maHD_VAT" value = "<%= tthdBean.getMahoadon() %>" style="width: 100%" readonly="readonly" >
									</TD>
									<TD>
										<input type="text" name = "mauHD_VAT" id = "mauHD_VAT" value = "<%= tthdBean.getMauhoadon() %>" onChange = "javascript:mauhoadon();" style="width: 100%" >
									</TD>
									<TD>
										<input type="text" name = "kyhieu_VAT" id = "kyhieu_VAT" value = "<%= tthdBean.getKyhieu() %>" style="width: 100%" >
									</TD>
											      		
									<TD>
										<input type="text" name = "sohd_VAT" id = "sohd_VAT" value = "<%= tthdBean.getSohoadon() %>" style="width: 100%" >
									</TD>

									<TD>
										<input type="text" name = "ngayhd_VAT" id = "ngayhd_VAT" value = "<%= tthdBean.getNgayhoadon() %>" style="width: 100%" class="days" readonly="readonly">
									</TD>								
								
									<TD>
										<input type="hidden" name = "nhId_VAT" id = "nhId_VAT" value = "<%= tthdBean.getNhId_VAT() %>" style="width: 100%"  >
										<input type="hidden" name = "cnId_VAT" id = "cnId_VAT" value = "<%= tthdBean.getCnId_VAT() %>" style="width: 100%"  >
										<input type="text" name = "nghangTen" id = "nghangTen" value = "<%= tthdBean.getTenNCC_VAT() %>" style="width: 100%" onChange ="ChangeNganHang();" >
									</TD>

									<TD>
										<input type="text" name= "mst_VAT" id= "mst_VAT" value = "<%= tthdBean.getMST() %>" style="width: 100%"  >
									</TD>

									<TD>
										<input type="text" name= "tienhang_VAT" id= "tienhang_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienhang().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthue();" onKeyPress = "return keypress(event);" >
									</TD>

									<TD>
										<input type="text" name= "thuesuat_VAT" id= "thuesuat_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getThuesuat().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthue();" onKeyPress = "return keypress(event);" >
									</TD>
									
									<TD>
										<input type="text" name= "tienthue_VAT" id= "tienthue_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienthue().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthuesuat();" onKeyPress = "return keypress(event);">
									</TD>
								</TR>
							</TABLE>
						    <DIV align="right">
					             <LABEL style="color: red" ></LABEL>
					               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	              	<a href="javascript:dropdowncontent.hidediv('subcontentVATNT')">Hoàn tất</a>
							</DIV>
						     				
    					</DIV>

					</TD>

                </TR>
            	<TR>
                    <TD class="plainlabel" style = "width:150px">Tổng tiền thanh toán (Ngoại tệ)</TD>
                    <TD class="plainlabel">
                         <input type="text" style="text-align: right;" name="tongtienNT" id = "tongtienNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getSotienttNT().replaceAll(",",""))) %>"  onKeyPress = "return keypress(event);" onChange = "ThaydoiSotienNT();" > 
                     </TD> 

                    <TD class="plainlabel" style = "width:150px">Tổng tiền thanh toán (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="tongtienVND" id = "tongtienVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",",""))) %>" onKeyPress = "return keypress(event);" > 
                     </TD> 
                </TR>
		
            	<TR>
                    <TD class="plainlabel">Nội dung thanh toán </TD>
                    <TD  class="plainlabel" style = "width:350px" >
                        <input type="text" name="noidungthanhtoan" value="<%= tthdBean.getNoidungtt() %>"> 
                     </TD> 

                    <TD align = "right" class="plainlabel" style = "width:200px">Chênh lệch (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="chenhlechVND" id = "chenhlechVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getChenhlech().replaceAll(",",""))) %>" readonly > 
                     </TD> 
  					
                </TR>
				<%} %>
	<%// THANH TOAN HOA DON VNĐ			%>
	<%}else{ %>
            	<TR>
                    <TD class="plainlabel" style = "display:none">Số tiền hóa đơn (Ngoại tệ) </TD>
                    <TD class="plainlabel" style = "display:none">
                         <input type="text" style="text-align: right;display:none" name="sotienHDNT" id = "sotienHDNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getSotienHDNT().replaceAll(",",""))) %>" readonly  > 
                     </TD>
                                      
                    <TD class="plainlabel">Số tiền hóa đơn (VNĐ) </TD>
                     <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="sotienHDVND" id = "sotienHDVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getSotienHD().replaceAll(",",""))) %>" readonly  > 
                     </TD>
             <%}%>

                     
                </TR>
                <TR style="display:none;">
					<TD class="plainlabel" style="display:none;" >Tài khoản trích phí (VND) </TD>				
					<TD class="plainlabel" colspan = 5 style="display:none;">
                     
                        <select name="sotaikhoan_tp" id="sotaikhoan_tp" style="width: 500px" >
                        	
                        	<%
                        		if(sotkRs_tp != null)
                        		{
                        			try
                        			{
                        			while(sotkRs_tp.next())
                        			{  
                        			if( sotkRs_tp.getString("SOTAIKHOAN").equals(tthdBean.getSotaikhoan_tp())){ %>
                        				<option value="<%= sotkRs_tp.getString("SOTAIKHOAN") %>" selected="selected" ><%= sotkRs_tp.getString("TAIKHOAN")%></option>
                        			<%}else { %>
                        				<option value="<%= sotkRs_tp.getString("SOTAIKHOAN") %>" ><%= sotkRs_tp.getString("TAIKHOAN") %></option>
                        		 <% } } sotkRs_tp.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>

                    </TD>
					
                </TR>                
				<TR>
                    <TD class="plainlabel" style = "width:150px;display:none">Phí ngân hàng (Ngoại tệ)</TD>
                    <TD  class="plainlabel" style = "display:none">
                         <input type="text" style="text-align: right;display:none" name="pnganhangNT" id = "pnganhangNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getPhinganhangNT().replaceAll(",",""))) %>" readonly  > 
                     </TD> 
				
                    <TD class="plainlabel" style = "width:150px">Phí ngân hàng (VNĐ)</TD>
                    <TD  class="plainlabel" colspan = "3">
			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	) ){ %>                    
                         <input type="text" style="text-align: right;" name="pnganhangVND" id = "pnganhangVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getPhinganhang().replaceAll(",",""))) %>" onchange="ThanhToan(100)" onKeyPress = "return keypress(event);"  >
                    <%	 }else{ %>
                    	<input type="text" style="text-align: right;" name="pnganhangVND" id = "pnganhangVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getPhinganhang().replaceAll(",",""))) %>" onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);"  >
                    <%   } %> 
                     </TD> 

				</TR>
            	<TR>
                    <TD class="plainlabel" style = "display:none">Thuế VAT (Ngoại tệ) </TD>
                    <TD  class="plainlabel" style = "display:none">
                         <input type="text" style="text-align: right;display:none" name="vatNT" id = "vatNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getThueVATNT().replaceAll(",",""))) %>" readonly  > 
                     </TD> 

                    <TD class="plainlabel" >Thuế VAT (VND) </TD>
					<TD align = "left" class="plainlabel" colspan = 3>
	                   <A href="" id="tinhVAT" rel="subcontentVAT"><img alt="Tính VAT" title="Tính VAT" src="../images/vitriluu.png"></a>
	                   <DIV id="subcontentVAT" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 900px; padding: 4px; max-height: 300px; overflow: auto; ">
							<TABLE width="100%" align="center">
        						<TR class="tbheader">
						        	<TH width="80px" align = "center">Mã hóa đơn</TH>
						        	<TH width="80px" align = "center">Mẫu hóa đơn</TH>
						        	<TH width="80px" align = "center">Ký hiệu</TH>
						         	<TH width="100px" align = "center">Số hóa đơn</TH>
						         	<TH width="100px" align = "center">Ngày hóa đơn </TH>
									<TH width="100px" align = "center">Tên NCC </TH>
									<TH width="100px" align = "center">MST </TH>	
									<TH width="100px" align = "center">Tiền hàng </TH>
									<TH width="100px" align = "center">Thuế suất(%) </TH>
									<TH width="100px" align = "center">Tiền thuế </TH>
								</TR>
					      		<TR>
									<TD>
										<input type="text" name = "maHD_VAT" id = "maHD_VAT" value = "<%= tthdBean.getMahoadon() %>" style="width: 100%" readonly="readonly" >
									</TD>
									<TD>
										<input type="text" name = "mauHD_VAT" id = "mauHD_VAT" value = "<%= tthdBean.getMauhoadon() %>" onChange = "javascript:mauhoadon();" style="width: 100%" >
									</TD>
									<TD>
										<input type="text" name = "kyhieu_VAT" id = "kyhieu_VAT" value = "<%= tthdBean.getKyhieu() %>" style="width: 100%" >
									</TD>
											      		
									<TD>
										<input type="text" name = "sohd_VAT" id = "sohd_VAT" value = "<%= tthdBean.getSohoadon() %>" style="width: 100%" >
									</TD>

									<TD>
										<input type="text" name = "ngayhd_VAT" id = "ngayhd_VAT" value = "<%= tthdBean.getNgayhoadon() %>" style="width: 100%" class="days" readonly="readonly">
									</TD>
								
									<TD>
										<input type="hidden" name = "nhId_VAT" id = "nhId_VAT" value = "<%= tthdBean.getNhId_VAT() %>" style="width: 100%"  >
										<input type="hidden" name = "cnId_VAT" id = "cnId_VAT" value = "<%= tthdBean.getCnId_VAT() %>" style="width: 100%"  >
										
										<input type="text" name = "nghangTen" id = "nghangTen" value = "<%= tthdBean.getTenNCC_VAT() %>" style="width: 100%" onChange ="ChangeNganHang();" >
									</TD>

									<TD>
										<input type="text" name= "mst_VAT" id= "mst_VAT" value = "<%= tthdBean.getMST() %>" style="width: 100%"  readonly="readonly">
									</TD>

									<TD>
										<input type="text" name= "tienhang_VAT" id= "tienhang_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienhang().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthue();" onKeyPress = "return keypress(event);">
									</TD>

									<TD>
										<input type="text" name= "thuesuat_VAT" id= "thuesuat_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getThuesuat().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthue();" onKeyPress = "return keypress(event);">
									</TD>

									<TD>
										<input type="text" name= "tienthue_VAT" id= "tienthue_VAT" value = "<%= formatter2.format(Double.parseDouble(tthdBean.getTienthue().replaceAll(",",""))) %>" style="width: 100%;text-align: right" onChange = "javascript:tinhthuesuat();" onKeyPress = "return keypress(event);">
									</TD>
								</TR>
							</TABLE>
						    <DIV align="right">
					             <LABEL style="color: red" ></LABEL>
					               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	              	<a href="javascript:dropdowncontent.hidediv('subcontentVAT')">Hoàn tất</a>
							</DIV>
						     				
    					</DIV>
			<%  	 if(((Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) < 4)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) == 2014 & Integer.parseInt(tthdBean.getNgayghinhan().substring(5, 7)) >=8)
					 	|| (Integer.parseInt(tthdBean.getNgayghinhan().substring(0, 4)) > 2014)
					 	) ){ %>                    
                         <input type="text" style="text-align: right;width:181px" name="vatVND" id = "vatVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getThueVAT().replaceAll(",",""))) %>" onchange="ThanhToan(100)" onKeyPress = "return keypress(event);"  >
                    <%	 }else{ %>
                    	 <input type="text" style="text-align: right;width:181px" name="vatVND" id = "vatVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getThueVAT().replaceAll(",",""))) %>" onchange="ThanhToan_2(100)" onKeyPress = "return keypress(event);"  >
                    <%   } %> 
                     </TD> 

                </TR>
            	<TR>
                    <TD class="plainlabel" style = "width:150px;display:none"">Tổng tiền thanh toán (Ngoại tệ)</TD>
                    <TD class="plainlabel" style = "display:none">
                         <input type="text" style="text-align: right;display:none" name="tongtienNT" id = "tongtienNT" value="<%= formatter1.format(Double.parseDouble(tthdBean.getSotienttNT().replaceAll(",",""))) %>" onKeyPress = "return keypress(event);" >
                     </TD> 

                    <TD class="plainlabel" style = "width:150px">Tổng tiền thanh toán (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="tongtienVND" id = "tongtienVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",",""))) %>" onKeyPress = "return keypress(event);" onChange = "ThaydoiSotienTT();"> 
                     </TD> 
                </TR>			

            	<TR>

                    <TD class="plainlabel">Nội dung thanh toán </TD>
                    <TD  class="plainlabel" >
                        <input type="text" name="noidungthanhtoan" value="<%= tthdBean.getNoidungtt() %>" > 
                     </TD> 

					 <%if(tthdBean.getLoaiThanhToan().equals("1")|| tthdBean.getLoaiThanhToan().equals("2") || tthdBean.getLoaiThanhToan().equals("4")|| tthdBean.getLoaiThanhToan().equals("5")) { %>
                    <TD align = "right" class="plainlabel" style = "width:200px">Chênh lệch (VNĐ)</TD>
                    <TD class="plainlabel" colspan = 3>
                         <input type="text" style="text-align: right;" name="chenhlechVND" id = "chenhlechVND" value="<%= formatter2.format(Double.parseDouble(tthdBean.getChenhlech().replaceAll(",",""))) %>" readonly > 
                     </TD> 
  					<%} else{ %>	
						<TD class="plainlabel" colspan = 3></TD>
					<%} %>
                </TR>
			
			<% %>


            </TABLE>

            
            </div>                   
           
           
           	<hr> 
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
	        <% if(tthdBean.getTienteId().equals("100000")){ // Don vi tien la VND %>
                <TR class="tbheader"> 
                	<TH align="center" width="10%">Loại hóa đơn</TH>
                	<TH align="center" width="8%">Ký hiệu HĐ</TH>
                	<TH align="center" width="8%">Số HĐ</TH>
                	<TH align="center" width="8%">Ngày HĐ</TH>
                <%if(tthdBean.getDoiTuongChiPhiKhac().equals("1")) { %>	
                	<TH align="center" width="8%">Ngày đến han TT</TH>
                <%} %>
                	<TH align="center" width="10%">Tổng số tiền (đã có VAT)</TH>
               	 	<TH align="center" width="10%">Thanh toán (VNĐ)</TH>
               	 	<TH align="center" width="10%">Còn lại</TH>
               	 	<TH align="center" width="5%">Trả hết</TH>
                </TR>

            <%}else{ // NGOẠI TỆ %>
                <TR class="tbheader"> 
               	    <TH align="center" width="10%">Loại hóa đơn</TH>
                	<TH align="center" width="8%">Ký hiệu HĐ</TH>
                	<TH align="center" width="8%">Số HĐ</TH>
                	<TH align="center" width="8%">Ngày HĐ</TH>
                <%if(tthdBean.getDoiTuongChiPhiKhac().equals("1")) { %>	
                	<TH align="center" width="8%">Ngày đến han TT</TH>
                <%} %>	
                	<TH align="center" width="10%">Tổng số tiền (VNĐ)</TH>
                	<TH align="center" width="10%">Tổng số tiền (Ngoại tệ)</TH>
               	 	<TH align="center" width="10%">Thanh toán (Ngoại tệ)</TH>
               	 	<TH align="center" width="10%">Còn lại</TH>
               	 	<TH align="center" width="10%">Tỉ giá</TH>
               	 	<TH align="center" width="5%">Trả hết</TH>
                </TR>
			<%} %>
                <%
                	for(int i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadon hoadon = hoadonList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getTienteId() %>" name= "ttId" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd1() %>" name= "loaihdId" readonly="readonly" >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getTenloaihd1() %>" name= "tenloaihd" readonly="readonly" >
           	 				</td>
							
							<td align="center">
        	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKyhieu() %>" name= "kyhieuhd" readonly="readonly" >
           	 				</td>
							
		                	<Td align="center" >
		                		<input type="text" style="width: 100%;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >
		                	</Td>
		                	<td align="center">
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
           	 				</td>
           	 										
						  <%if(tthdBean.getDoiTuongChiPhiKhac().equals("1")) { %>	                
						    <td align="center">
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= hoadon.getNgaydenhanTT() %>" name= "ngayhd" readonly="readonly" >
           	 				</td>
           	 			<%} %>						
						
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter2.format(Double.parseDouble(hoadon.getTongtiencoVAT().replaceAll(",",""))) %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>
           	 				
           	 			<% if(!tthdBean.getTienteId().equals("100000")){ // Ngoại tệ%>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter1.format(Double.parseDouble(hoadon.getSotienNT().replaceAll(",",""))) %>" name= "sotienNT" id="sotienNT<%= i %>" readonly="readonly" >
           	 				</td>
           	 			
           	 			<%} %>
          	 				
           	 				<td align="center">						
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter1.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>" name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="ThanhToan(100)" onKeyPress = "return keypress(event);">
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter1.format(Double.parseDouble(hoadon.getConlai().replaceAll(",",""))) %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 				</td>
						
						<% if(!tthdBean.getTienteId().equals("100000")){ // Ngoại tệ%>           	 				
           	 				<td align="center">
           	 					<input type="text" style="width: 100%;text-align: right;" value="<%= formatter1.format(Double.parseDouble(hoadon.getTigia().replaceAll(",",""))) %>" name= "tigiaHD" id="tigiaHD<%= i %>" readonly="readonly" >
           	 				</td>
           	 			<%} %>
           	 			
           	 				<td align="center">
           	 				<% 	if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="ThanhToan(<%= i %>)" >
           	 				<%} else { %>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="ThanhToan(<%= i %>)" >
           	 				<%} %>
           	 				</td>
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div> 
        	
     </fieldset>	
    </div>
</div>
<script type="text/javascript">
replaces();
</script>
</form>
<script type="text/javascript">		
		
		jQuery(function()
		{		
			$("#NhanVienId").autocomplete("ErpThanhToanTamUngList.jsp");
			$("#nccId").autocomplete("ErpThanhToanTamUngList.jsp");
			$("#dinhkhoanno").autocomplete("ErpTaiKhoanKeToanList.jsp");
			$("#doituongdinhkhoan").autocomplete("ErpDoiTuongTaiKhoanKeToanList.jsp");
			
			
		});	
		
		
		//ChangeNganHang();
		
</script>

<script type="text/javascript">
	dropdowncontent.init("tinhVATNT", "left-top", 200, "click");
	dropdowncontent.init("tinhVAT", "right-top", 200, "click");
</script>

</BODY>
</html>

<%
	if(nccList != null) nccList.close(); 
	if(nhomnccList != null) nhomnccList.close(); 
	if(khList != null) khList.close(); 
	if(nvList != null) nvList.close();  
	if(htttList != null) htttList.close(); 
	if(tienteList != null) tienteList.close(); 
	if(nganhangList != null) nganhangList.close();
	if(nganhangList != null) nganhangList.close(); 
	if(chinhanhList != null) chinhanhList.close();
	if(tienteList != null) tienteList.close();
	if(sotkRs != null) sotkRs.close();
	if(sotkRs_tp != null) sotkRs_tp.close();
	if(hoadonList != null) hoadonList.clear();
	
	tthdBean.DBclose();
	%>
