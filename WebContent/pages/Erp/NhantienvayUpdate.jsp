<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.erp.beans.vayvon.INhantienvay"%>
<%@page import="geso.traphaco.erp.beans.vayvon.IHoadon"%>
<%@page import="geso.traphaco.erp.beans.vayvon.imp.Hoadon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
  <%
  	INhantienvay obj = (INhantienvay)session.getAttribute("obj") ;
  	ResultSet HDRS = obj.getHDRS();
  	ResultSet UNCRS = obj.getUNCRS();
  	ResultSet ttRs = obj.getTtRs();
  %>
<% ResultSet sotkRs = obj.getSotkRs(); %>
<% ResultSet tienteList = obj.getTienteRs(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<% NumberFormat formatter1=new DecimalFormat("#,###,###.###"); %>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/cssf">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">	    
    <script language="javascript" src="../scripts/datepicker.js"></script>
 
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
   <link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
    
    <script type="text/javascript" src="../scripts/jquery.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
   	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
	
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
	
   <script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
	  });
  </script>
  
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
	<!-- <script src="../scripts/colorBox/jquery.min.js"></script> -->
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {			
            $(".button2").colorbox({width:"30%", inline:true, href:"#inline_example1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("SalesUp - Project.");
                return false;
            });
        });
   </script>
 <SCRIPT language="javascript" type="text/javascript">
 
 function ThanhToan(n)
 {
	 var trahet = document.getElementsByName("trahet");
	 var sotienHDNT = document.getElementsByName("sotienHDNT");
	 var sotienHDVND = document.getElementsByName("sotienHDVND");
	 var thanhtoan = document.getElementsByName("thanhtoan");
	 var conlaiHD = document.getElementsByName("conlaiHD");
	 var tigiaHD = document.getElementsByName("tigiaHD"); // Tỉ giá của UNC
	 
	 var sotienNT = document.getElementById("sotienNT");
	 var sotienVND = document.getElementById("sotienVND");
	 var sotienconlai = document.getElementsByName("sotienconlai");
	 var sotienvay = document.getElementsByName("sotienvay");
	 var tigia = document.getElementsByName("tigia");
	 
	 var tienteId = document.getElementsByName("tienteId");
	 
	 var tongtienNT = "0";
	 var tongtienVND = "0";
	 console.log("sotienVND: " + sotienVND.value);
	 for( i = 0; i < trahet.length; i++)
	 {
		if(trahet.item(i).checked) // TRƯỜNG HỢP CHỌN TRẢ HẾT
		{
			
//			
			{				
				//alert(tienteId.item(0).value);
				if(tienteId.item(0).value != "100000" && tienteId.item(0).value != "0")	// SO TIEN NGOAI TE > 0
				{
					var tg = tigiaHD.item(i).value.replace(/,/g,"");
					
					// Tính lại Số tiền HD VND: sotienNT * tigiaHD (mới nhất)
					sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);

					tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
					
					conlaiHD.item(i).value = "0";
					
					console.log("sotienHDVND.item(i): " + sotienHDVND.item(i) + " thanhtoan.item(i): " + thanhtoan.item(i));
					if (sotienHDVND.item(i) != null && thanhtoan.item(i) != null)
						thanhtoan.item(i).value = sotienHDVND.item(i).value.replace(/,/g,"");
					
					var tt = thanhtoan.item(i).value;
					
					tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
					sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
//					trahet.item(i).checked = true;
					console.log("Tick chon het, ngoai te > 0: i = " + i);
				}else
				{
					var tt = thanhtoan.item(i).value;
					conlaiHD.item(i).value = "0";
					
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
					
					var tt = thanhtoan.item(i).value.replace(/,/g,"");
					
					tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					//alert("tong thanh toan: " + tongtienVND);
					
					sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
//					trahet.item(i).checked = true;
					console.log("Tick chon het, ngoai te < 0: i = " + i);
				}
			}
			
		}
//		else if(i==n) 			  //  TRƯỜNG HỢP BỎ CHỌN TRẢ HẾT
		else if(!trahet.item(i).checked)
		{
			var tt = thanhtoan.item(i).value;
//			trahet.item(i).checked = false;	
			if(tienteId.item(0).value  != "100000")	// SO TIEN NGOAI TE > 0
			{
				var tg = tigiaHD.item(i).value.replace(/,/g,"");
				
				sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);
			
				tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
				
				thanhtoan.item(i).value = "0" ;
				console.log("Bo chon het, ngoai te > 0: i = " + i);
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
				conlaiHD.item(i).value = sotienHDVND.item(i).value; //.replace(/,/g,"");
			}
			else
			{										
				thanhtoan.item(i).value = "0" ;
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
				conlaiHD.item(i).value =  sotienHDVND.item(i).value;				
				console.log("Bo chon het, ngoai te < 0: i = " + i);
//				tongtienVND = "0";
//				tongtienVND = parseFloat(tongtienVND) - parseFloat(tt);
			}
		}
		else //  TRƯỜNG HỢP THAY ĐỔI CÁC FIELD DỮ LIỆU
		{
			console.log("thay doi file du lieu i = " + i);
			var tg = tigiaHD.item(i).value.replace(/,/g,"");
			
			//alert(tienteId.item(0).value);			
			if(tienteId.item(0).value  != "100000" && tienteId.item(0).value != "0")	// SO TIEN NGOAI TE > 0
			{
				
				sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);
			}
			
			
			tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
			
			var tt = thanhtoan.item(i).value.replace(/,/g,"");
			
			
			if(parseFloat(sotienHDVND.item(i).value.replace(/,/g,""))- parseFloat(tt) > 0 )
			{
				
				conlaiHD.item(i).value = parseFloat(sotienHDVND.item(i).value.replace(/,/g,"")) - parseFloat(tt);
				tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tt,0));
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""),0));
//				trahet.item(i).checked = false;
								
			}else{
				
				conlaiHD.item(i).value = "0" ;						
				
				thanhtoan.item(i).value = sotienHDVND.item(i).value.replace(/,/g,"") ;
				
				var tt = thanhtoan.item(i).value.replace(/,/g,"");
				
//				tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
				
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""),0));
				
//				trahet.item(i).checked = true;
			}
			
		}
	 }
	 console.log("tongtienVND: " + tongtienVND);
	 // XỬ LÝ CÁC Ô TRÊN MÀN HÌNH TRÊN CÙNG
	 	//alert(tienteId.item(0).value);
	 	if(tienteId.item(0).value == "100000")  // TIỀN VIỆT
		{
			sotienconlai.item(0).value = DinhDangTien(parseFloat(sotienVND.value.replace(/,/g,"")) - parseFloat(tongtienVND));
			sotienvay.item(0).value = DinhDangTien(parseFloat(tongtienVND));
			sotienVND.value = DinhDangTien(sotienVND.value.replace(/,/g,""));
			
//			sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
			
		}else{									// NGOẠI TỆ
			
		 	var tgia = tigia.item(0).value.replace(/,/g,"");
		
		 	sotienVND.value = DinhDangTien(parseFloat(sotienNT.value.replace(/,/g,"")) * parseFloat(tgia));
		 	//alert(sotienVND.item(0).value);
		 	
		 	sotienconlai.item(0).value = parseFloat(sotienVND.value.replace(/,/g,"")) - parseFloat(tongtienVND);
		 	sotienvay.item(0).value = DinhDangTien(parseFloat(tongtienVND));
		 	sotienNT.value = DinhDangDonGia(parseFloat(sotienNT.value.replace(/,/g,"")));
		 	sotienVND.value = DinhDangTien(roundNumber(sotienVND.value.replace(/,/g,"") , 0));
		 	sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
		}
		TinhTienLai() ;
	 	
	 console.log("sotienconlai.item(0).value: " + sotienconlai.item(0).value);
 }

   function ThanhToanOLD(n)
   {
	 var trahet = document.getElementsByName("trahet");
	 var sotienHDNT = document.getElementsByName("sotienHDNT");
	 var sotienHDVND = document.getElementsByName("sotienHDVND");
	 var thanhtoan = document.getElementsByName("thanhtoan");
	 var conlaiHD = document.getElementsByName("conlaiHD");
	 var tigiaHD = document.getElementsByName("tigiaHD"); // Tỉ giá của UNC
	 
	 var chonGN =  document.getElementsByName("chonGN");
	 
	 var sotienNT = document.getElementById("sotienNT");
	 var sotienVND = document.getElementById("sotienVND");
	 var sotienconlai = document.getElementsByName("sotienconlai");
	 var tigia = document.getElementsByName("tigia");
	 
	 var tienteId = document.getElementsByName("tienteId");
	 
	 var tongtienNT = "0" ;
	 var tongtienVND = "0" ;
	 
	 for( i = 0; i < trahet.length; i++)
	 {
		if(trahet.item(i).checked) // TRƯỜNG HỢP CHỌN TRẢ HẾT
		{
			
			if(n==100) // CHỌN TRẢ HẾT NHƯNG THAY ĐỔI THANH TOÁN
			{
				var tt;
				if(thanhtoan.item(i).value != '')
				{
					tt = thanhtoan.item(i).value.replace(/,/g,"");
				}else
				{
					tt = 0;
				}
				
					if(tienteId.item(0).value != "100000" && tienteId.item(0).value != "0")	// SO TIEN NGOAI TE > 0
					{
						var tg = tigiaHD.item(i).value.replace(/,/g,"");
					
						// Tính lại Số tiền HD VND: sotienNT * tigiaHD (mới nhất)
						sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);
					
						tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
					}
				
					if(parseFloat(sotienHDVND.item(i).value.replace(/,/g,"")) - parseFloat(tt) > 0 )
					{
						conlaiHD.item(i).value = parseFloat(sotienHDVND.item(i).value.replace(/,/g,"")) - parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
						
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
						conlaiHD.item(i).value = DinhDangTien(roundNumber(conlaiHD.item(i).value.replace(/,/g,""), 0));
						
						trahet.item(i).checked = false;
						
					}else{
						
						conlaiHD.item(i).value = "0" ;						
						
						thanhtoan.item(i).value = sotienHDVND.item(i).value.replace(/,/g,"") ;
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");
						
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
						
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
						conlaiHD.item(i).value = DinhDangTien(roundNumber(conlaiHD.item(i).value.replace(/,/g,""), 0));
												
						trahet.item(i).checked = true;
					}
											
			}
			else     // CHỌN TRẢ HẾT
			{				
				//alert(tienteId.item(0).value);
				if(tienteId.item(0).value != "100000" && tienteId.item(0).value != "0")	// SO TIEN NGOAI TE > 0
				{
					var tg = tigiaHD.item(i).value.replace(/,/g,"");
					
					// Tính lại Số tiền HD VND: sotienNT * tigiaHD (mới nhất)
					sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);

					tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
					
					conlaiHD.item(i).value = "0";
					
					console.log("sotienHDVND.item(i): " + sotienHDVND.item(i) + " thanhtoan.item(i): " + thanhtoan.item(i));
					if (sotienHDVND.item(i) != null && thanhtoan.item(i) != null)
						thanhtoan.item(i).value = sotienHDVND.item(i).value.replace(/,/g,"");
					
					var tt = thanhtoan.item(i).value;
					
					tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
					sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
				}else
				{
					
					conlaiHD.item(i).value = "0";
					
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
					
					var tt = thanhtoan.item(i).value.replace(/,/g,"");
					
					tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					//alert("tong thanh toan: " + tongtienVND);
					
					sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
					thanhtoan.item(i).value = sotienHDVND.item(i).value;
				}
				
			}
			
		}
		else if(i==n) 			  //  TRƯỜNG HỢP BỎ CHỌN TRẢ HẾT
		{
			
			if(tienteId.item(0).value  != "100000")	// SO TIEN NGOAI TE > 0
			{
				var tg = tigiaHD.item(i).value.replace(/,/g,"");
				
				sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);
			
				tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
				
				thanhtoan.item(i).value = "0" ;
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
				conlaiHD.item(i).value = sotienHDVND.item(i).value; //.replace(/,/g,"");
			}
			else
			{										
				thanhtoan.item(i).value = "0" ;
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""), 0)) ;
				conlaiHD.item(i).value =  sotienHDVND.item(i).value;				
				
				tongtienVND = "0";
			}
		}
		else //  TRƯỜNG HỢP THAY ĐỔI CÁC FIELD DỮ LIỆU
		{
			
			var tg = tigiaHD.item(i).value.replace(/,/g,"");
			
			//alert(tienteId.item(0).value);			
			if(tienteId.item(0).value  != "100000" && tienteId.item(0).value != "0")	// SO TIEN NGOAI TE > 0
			{
				
				sotienHDVND.item(i).value = parseFloat(sotienHDNT.item(i).value.replace(/,/g,"")) * parseFloat(tg);
			}
			
			
			tigiaHD.item(i).value = DinhDangDonGia(parseFloat(tigiaHD.item(i).value.replace(/,/g,"")));
			
			var tt = thanhtoan.item(i).value.replace(/,/g,"");
			
			
			if(parseFloat(sotienHDVND.item(i).value.replace(/,/g,""))- parseFloat(tt) > 0 )
			{
				
				conlaiHD.item(i).value = parseFloat(sotienHDVND.item(i).value.replace(/,/g,"")) - parseFloat(tt);
				tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tt,0));
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""),0));
				trahet.item(i).checked = false;
								
			}else{
				
				conlaiHD.item(i).value = "0" ;						
				
				thanhtoan.item(i).value = sotienHDVND.item(i).value.replace(/,/g,"") ;
				
				var tt = thanhtoan.item(i).value.replace(/,/g,"");
				
				tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
				
				sotienHDVND.item(i).value = DinhDangTien(roundNumber(sotienHDVND.item(i).value.replace(/,/g,""),0));
				
				trahet.item(i).checked = true;
			}
			
		}
	 }
	 
	 // XỬ LÝ CÁC Ô TRÊN MÀN HÌNH TRÊN CÙNG
	 	
	 	if(tienteId.item(0).value == "100000")  // TIỀN VIỆT
 		{
	 		if(!chonGN.item(0).checked)
		 	{
 				sotienconlai.item(0).value = parseFloat(sotienVND.value.replace(/,/g,"")) - parseFloat(tongtienVND);
 				sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
		 	}
 			
 			sotienVND.value = DinhDangTien(sotienVND.value.replace(/,/g,""));
 						
 		}else{									// NGOẠI TỆ
 			
		 	var tgia = tigia.item(0).value.replace(/,/g,"");		 	
 		
		 	sotienVND.value = DinhDangTien(parseFloat(sotienNT.value.replace(/,/g,"")) * parseFloat(tgia));
		 	
		 	if(!chonGN.item(0).checked)
		 	{	
		 		sotienconlai.item(0).value = parseFloat(sotienVND.value.replace(/,/g,"")) - parseFloat(tongtienVND);
		 		sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
		 	}
		 	
		 	sotienNT.value = DinhDangDonGia(sotienNT.value.replace(/,/g,""));
		 	sotienVND.value = DinhDangTien(roundNumber(sotienVND.value , 0));
		 	tigia.item(0).value = DinhDangDonGia(tgia);
		 	
 		}
	 	
	 
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
  	function TinhTienConLai() 
	{

  		
  		 var sotienNT = document.getElementById("sotienNT");
  		 var sotienVND = document.getElementById("sotienVND");
  		 var sotienconlai = document.getElementsByName("sotienconlai");
  		 var sotienvay = document.getElementsByName("sotienvay");
  		 var tigia = document.getElementsByName("tigia");
  		 
  		 var tienteId = document.getElementsByName("tienteId");
  		 
  		 var tongtienNT = "0";
  		 var tongtienVND = "0";
  		 
  		 // XỬ LÝ CÁC Ô TRÊN MÀN HÌNH TRÊN CÙNG
  		 	//alert(tienteId.item(0).value);
  		 	if(tienteId.item(0).value == "100000")  // TIỀN VIỆT
  			{
  				sotienconlai.item(0).value = DinhDangTien(parseFloat(sotienVND.value.replace(/,/g,"")) - parseFloat(sotienvay.item(0).value.replace(/,/g,"")));
  				sotienVND.value = DinhDangTien(sotienVND.value.replace(/,/g,""));
  				sotienvay.item(0).value=DinhDangTien(sotienvay.item(0).value.replace(/,/g,""));
//  				sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
  				
  			}else{									// NGOẠI TỆ
  				
  			 	var tgia = tigia.item(0).value.replace(/,/g,"");
  			
  			 	sotienVND.value = DinhDangTien(parseFloat(sotienNT.value.replace(/,/g,"")) * parseFloat(tgia));
  			 	//alert(sotienVND.item(0).value);
  			 	
  			 	sotienconlai.item(0).value = parseFloat(sotienVND.value.replace(/,/g,""))  - parseFloat(sotienvay.item(0).value.replace(/,/g,""));
  			 	sotienNT.value = DinhDangDonGia(parseFloat(sotienNT.value.replace(/,/g,"")));
  			 	sotienVND.value = DinhDangTien(roundNumber(sotienVND.value.replace(/,/g,"") , 0));
  			 	sotienconlai.item(0).value = DinhDangTien(sotienconlai.item(0).value.replace(/,/g,""));
  			 	sotienvay.item(0).value=DinhDangTien(sotienvay.item(0).value.replace(/,/g,""));
  			}
  			TinhTienLai() ;
  		 	
  		 console.log("sotienconlai.item(0).value: " + sotienconlai.item(0).value);
	}
	
   
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function TinhTienLai() 
	{
		
		 var sotienvay = document.getElementsByName("sotienvay");
		 var travay = document.getElementsByName("travay");
		 var tralai = document.getElementsByName("tralai");
		 var ngaytravay = document.getElementsByName("ngaytravay");
		 var ngayvay = document.getElementsByName("ngay");
		 var conlai = document.getElementsByName("conlai");
		 var laisuat = document.getElementsByName("laisuat");
		 if(travay!=null)
		 {
			if(laisuat.item(0).value!="")
			{
				var ls=parseFloat(laisuat.item(0).value.replace(/,/g,""));	
				console.log("lai suat :" +ls);
			}
			var sotienconlai=parseFloat(sotienvay.item(0).value.replace(/,/g,""));	
			var ngayvay = Date.parse(ngayvay.item(0).value); 
			// vì lần đầu tiên phải trừ đi 1.
			var k=1;
			for( i=0 ; i<travay.length;i++)
			{
			     if((ngaytravay.item(i).value!="")&&(travay.item(i).value!=""))
			     {
					 var ntv = Date.parse(ngaytravay.item(i).value);
					 var songaytinhlai= parseFloat(ntv-ngayvay);
					 var totalDays = Math.round((songaytinhlai / 1000 / 60 / 60 / 24) ) +k; 
					 console.log("songaytinhlai :" +songaytinhlai);
					 conlai.item(i).value= DinhDangDonGia(sotienconlai-parseFloat(travay.item(i).value.replace(/,/g,"")));	
					 tralai.item(i).value=DinhDangDonGia(Math.round((sotienconlai*ls*totalDays)/(100*360)));
					 sotienconlai=sotienconlai-parseFloat(travay.item(i).value.replace(/,/g,""));
					 ngayvay =ntv;
					 k=0;
			     } 
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
	
	function submitform()
	{ 	

		document.forms['hdvForm'].submit();
	}

	function doiThang()
	{ 	
		document.forms['hdvForm'].action.value='doiThang';
		document.forms['hdvForm'].submit();
	}
 	function newform()
	{ 	
		var ngay = document.getElementById("ngay");
		var ghichu = document.getElementById("ghichu");
		var hdId = document.getElementById("hdId");
		var tienteId = document.getElementById("tienteId");
		var tienteId = document.getElementById("thoihan");
		var laisuat = document.getElementById("laisuat");
		var ngaydaohan = document.getElementById("ngaydaohan");
		
		if (ngay.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng chọn "Ngày"';
			return;
		}
		
		if (ghichu.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng nhập "Diễn giải chứng từ"';
			return;
		}
		
		if (hdId.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng chọn "Hợp đồng"';
			return;
		}
		
		if (tienteId.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng chọn "Tiền tệ"';
			return;
		}
		
		if (thoihan.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng nhập "Thời hạn trả vay"';
			return;
		}
		
		if (laisuat.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng nhập "Lãi suất"';
			return;
		}
		
	
		
		if (ngaydaohan.value.length == 0)
		{
			document.getElementById("dataerror").value = 'Vui lòng chọn "Ngày đáo hạn"';
			return;
		}

		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 		document.forms['hdvForm'].action.value= 'save';
		document.forms['hdvForm'].submit();
	}

	/* function isNumberKey2(evt)
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
	}*/
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
	/*(num) 
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
	 } */
	 function isNumberKey2(evt)
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
		/* (num) 
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
		 } */
		 
		 function DinhDangTien(num) 
		 {
		    /* num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100+0.50000000001);
		    num = Math.floor(num/100).toString();
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));

		    return (((sign)?'':'-') + num); */
		 	num = num.toString().replace(/\$|\,/g,'');
		 	
		 	//num = (Math.round( num * 1000 ) / 1000).toString();
		 	
		 	var sole = '';
		 	if(num.indexOf(".") >= 0)
		 	{
		 		sole = num.substring(num.indexOf('.'));
		 		//sole = parseFloat(sole).toFixed(2);
		 		//sole = sole.substring(sole.indexOf('.'));
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
		 	
		 	if(kq.indexOf(".") > 0)
		 	{
		 		//alert(kq.indexOf(".") + '  -- LEN: ' + kq.length );
		 		if( ( parseFloat(kq.indexOf(".")) + 4 ) < kq.length )
		 		{
		 			//alert('Cat toi: ' + ( parseFloat(kq.indexOf(".")) + 3 ) );
		 			kq = kq.substring(0, parseFloat(kq.indexOf(".")) + 4);
		 		}
		 	}
		 	
		 	//alert(kq);
		 	return kq;
		    
		 }

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }
	 function changeform()
	 {
		 document.forms["hdvForm"].changeDdh.value = "true";
		 document.forms['hdvForm'].submit();
	 }
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("chonncc");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="hdvForm" method="post" action="../../NhantienvayUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>
<input type="hidden" name="changeDdh" value='false'>

<div id="main" style="width:100%">
	<div align="left" id="header" style="width:100%; float:none">
	<% if (obj.getId().length() == 0) {%>
		<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý kế toán &gt; Giải ngân  &gt; Tạo mới
	<%}else{ %>
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý kế toán &gt; Giải ngân  &gt; Cập nhật
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
													<TD width="30" align="left"><A href="../../NhantienvaySvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												    <TD width="30" align="left" >
												    <div id="btnSave">
												    <A href="javascript: newform()" >
												    <IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
												    </div></TD>
													<TD>&nbsp; </TD>						
												</TR>
										</TABLE>
								</TD></TR>
								<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
				
			    				<textarea name="dataerror" id="dataerror" cols="100%" rows="1" ><%=obj.getmsg() %> </textarea>
								<%obj.setmsg(""); %>
								</FIELDSET>
						   </TD>
					     </tr>
					     <tr>
					     <td>
					     <FIELDSET>
								<LEGEND class="legendtitle">Giải ngân</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
					                    <TR>
										  <TD  class="plainlabel" >Ngày</TD>
										  <TD  class="plainlabel"  ><INPUT class = "days" name="ngay" id="ngay" type="text" size="20" value="<%= obj.getNgay()%>" readonly = readonly onChange = "submitform();"></TD>
											
										  <TD width="15%" class="plainlabel" >Giải ngân về tài khoản tiền</TD>
										  <TD class="plainlabel" width="30%" >
											<%if(obj.getGiainganveTK().equals("1")){ %>
												<input type="checkbox" name= "chonGN" id= "chonGN" value="1"  checked="checked"  onChange = "submitform();"/>
											<%}else{ %>
												<input type="checkbox" name= "chonGN" id= "chonGN" value="1"  onChange = "submitform();"/>
											<%} %>
											</TD>

										</TR> 
										
					                    <TR>
										  <TD  class="plainlabel" >Diễn giải chứng từ</TD>
										  <TD  class="plainlabel" >
										  	<INPUT name="ghichu" id="ghichu" type="text" size="20" value="<%= obj.getGhichu() %>"  style="width:300"> </TD>

								<%if(obj.getGiainganveTK().equals("0")){// CHECK CHỌN GIẢI NGÂN TỪ TÀI KHOẢN VAY%>        

                						<TD class="plainlabel">&nbsp;</TD>
                						<TD class="plainlabel" >&nbsp;         
										</TD>
										
						<%}else{ %>
					                     <TD class="plainlabel" width="700px" >Tài khoản nhận tiền</TD>
					                     <TD class="plainlabel" >
                     
					                        <select name="sotaikhoan" id="sotaikhoan" style="width: 250px">
					                        	<option value = "">&nbsp;</option>
					                        	<%
					                        		if(sotkRs != null)
					                        		{
					                        			try
					                        			{
					                        			while(sotkRs.next())
					                        			{  
					                        			if( sotkRs.getString("SOTAIKHOAN").equals(obj.getSotaikhoan())){ %>
					                        				<option value="<%= sotkRs.getString("SOTAIKHOAN") %>" selected="selected" ><%= sotkRs.getString("TAIKHOAN")%></option>
					                        			<%}else { %>
					                        				<option value="<%= sotkRs.getString("SOTAIKHOAN") %>" ><%= sotkRs.getString("TAIKHOAN") %></option>
					                        		 <% } } sotkRs.close();} catch(SQLException ex){}
					                        		}
					                        	%>
					                        </select>

                    					 </TD>
						<%} %>
										</TR> 

										<TR>
											<TD  class="plainlabel" >Hợp đồng</TD>
											<TD class="plainlabel" >
											<SELECT NAME = "hdId" id="hdId" onChange = "submitform();" style = "width:300px">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (HDRS != null){
												while(HDRS.next()){
													if(HDRS.getString("HDID").equals(obj.getSoHD())){
											%>																	
														<OPTION VALUE = "<%= HDRS.getString("HDID") %>" SELECTED><%= HDRS.getString("HD")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= HDRS.getString("HDID") %>" ><%= HDRS.getString("HD")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
											<TD  class="plainlabel" >Tài khoản vay</TD>
										  	<TD  class="plainlabel" >
										  		<INPUT name="tkvay" type="text" size="20" value="<%= obj.getTkvay() %>" > 
										  	</TD>
											
										</TR>										
															                		
                					    <TR>									
															                		
                					<TR>
                					
                		
                						<TD class="plainlabel">Tiền tệ</TD>
                		<% 
                		
                		System.out.println(obj.getTienteId());
                						
                		if(obj.getTienteId().equals("100000")){ %>		
                    					<TD class="plainlabel" style = "width:200px" colspan = 3>  
                    		<%}else{ %>         	
                    					<TD class="plainlabel" style = "width:200px"> 
                    		<%} %>
 
					                        <select name="tienteId" id="tienteId" style = "width:200px" >
 
					                        		<option value = "0"></option>
					                        	<%
					                        		if(tienteList != null)
					                        		{
					                        			try
					                        			{
					                        			while(tienteList.next())
					                        			{  
					                        			if( tienteList.getString("pk_seq").equals(obj.getTienteId())){ %>
					                        				<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
					                        			<%}else { %>
					                        				<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
					                        		 <% } } tienteList.close();} catch(SQLException ex){}
					                        		}
					                        	%>
					                        </select>
                    					 </TD>
                    			<%
                    			//NẾU LÀ NGOẠI TỆ, MỚI NHẬP TỈ GIÁ
                    			if(!obj.getTienteId().equals("100000")){ %>		 
                    					 <TD class="plainlabel" width = "10%">Tỉ giá</TD>
                    					 <TD class="plainlabel" >
                    					 <input type="text" id= "tigia" name= "tigia" value = "<%=formatter.format(Double.parseDouble(obj.getTigia().replaceAll(",",""))) %>"  onkeypress="return isNumberKey2(event)" onchange="ThanhToan(100)"/>
                    					 </TD>                  					 
                					
                				<%} %>	
                				</TR>
                				<%if(obj.getTienteId().equals("100000")){ %>
                					<TR>                						
                						<TD class="plainlabel">Số tiền VNĐ</TD>
                						<TD class="plainlabel" colspan="3">
                							<input type="text" id="sotienVND" name="sotienVND" value="<%=formatter.format(Double.parseDouble(obj.getSotienVND()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" onchange="ThanhToan(100)"/>
                						</TD>
                					</TR>
                				<%}else{ %>
                					<TR>
                						<TD class="plainlabel">Số tiền ngoại tệ</TD>
                						<TD class="plainlabel">
                							<input type="text" id="sotienNT" name="sotienNT" value="<%=formatter.format(Double.parseDouble(obj.getSotienNT()))%>" onkeypress="return isNumberKey2(event)" onchange="ThanhToan(100)"/>
                						</TD>
                						
                						<TD class="plainlabel">Số tiền VNĐ</TD>
                						<TD class="plainlabel">
                							<input type="text" id="sotienVND" name="sotienVND" value="<%=formatter.format(Double.parseDouble(obj.getSotienVND()))%>" readonly="readonly"/>
                						</TD>
                					</TR>
                				<%} %>
                				<%if(obj.getGiainganveTK().equals("0")){// CHECK CHỌN GIẢI NGÂN TỪ TÀI KHOẢN VAY%>	
                					<TR>
                						<TD class="plainlabel">Số tiền vay</TD>
                						<TD class="plainlabel" >
                							<input type="text" name="sotienvay" id="sotienvay" value="<%=formatter.format(Double.parseDouble(obj.getSotienvay())) %>" readonly="readonly"/ > VNĐ
                						</TD>
                						 <TD  class="plainlabel" width = "10%"> </TD>
										  <TD  class="plainlabel" width = "40%">
										  </TD>
                						
									</TR>
                					
                					<TR>
                						<TD class="plainlabel">Số tiền còn lại</TD>
                						<TD class="plainlabel" >
                							<input type="text" name="sotienconlai" id="sotienconlai" value="<%=formatter.format(Double.parseDouble(obj.getStConlai())) %>" readonly="readonly"/ > VNĐ
                						</TD>
                						 <TD  class="plainlabel" width = "10%"> </TD>
										  <TD  class="plainlabel" width = "40%">
										  </TD>
                						
									</TR>	
								<%}else {%>
									<TR>
                						<TD class="plainlabel">Số tiền vay</TD>
                						<TD class="plainlabel" >
                							<input type="text" name="sotienvay" id="sotienvay" value="<%=formatter.format(Double.parseDouble(obj.getSotienvay())) %>" onchange="TinhTienConLai()"/ > VNĐ
                						</TD>
                						 <TD  class="plainlabel" width = "10%"> </TD>
										  <TD  class="plainlabel" width = "40%">
										  </TD>
                						
									</TR>
                					
                					<TR>
                						<TD class="plainlabel">Số tiền còn lại</TD>
                						<TD class="plainlabel" >
                							<input type="text" name="sotienconlai" id="sotienconlai" value="<%=formatter.format(Double.parseDouble(obj.getStConlai())) %>" readonly="readonly"/ > VNĐ
                						</TD>
                						 <TD  class="plainlabel" width = "10%"> </TD>
										  <TD  class="plainlabel" width = "40%">
										  </TD>
                						
									</TR>	
								<% } %>
								<TR>
                						 
                						 <TD  class="plainlabel" width = "10%">Thời hạn trả vay</TD>
										  <TD  colspan="4"  class="plainlabel" width = "40%">
										  <INPUT name="thoihan" id="thoihan" type="text" style="width:100" value="<%= obj.getThoihan() %>"  onkeypress="return isNumberKey2(event)" onChange = "doiThang();"> Tháng</TD>
                						
									</TR>	
										<TR>
											<TD width="15%" class="plainlabel" >Lãi suất </TD>
										  	<TD  class="plainlabel" colspan = "4"><INPUT name="laisuat" id="laisuat" type="text" size="40" value="<%= obj.getLaisuat() %>" style = "width:100" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)"> %/ năm</TD>
										</TR>
										
										<TR>
										 <TD class="plainlabel" >Ngày đáo hạn</TD>
										 <TD class="plainlabel" colspan = "4"><INPUT name="ngaydaohan" id="ngaydaohan" type="text"  class ="days" value="<%= obj.getNgayDaoHan() %>"  > </TD>
										 
										</TR>
														
									</TABLE>

							</FIELDSET>
						</TD>
					</TR>
				
				
			<%if(obj.getGiainganveTK().equals("0")){ 
			 //GIẢI NGÂN VỀ TÀI KHOẢN TIỀN
			 
			%>	
				<TR><TD>
				<TABLE  width="100%" cellpadding="4" cellspacing = "1" border = "0">
			   		<TR>
				   		<TD>
				   		<FIELDSET>
				   			<LEGEND class="legendtitle">Uỷ nhiệm chi</LEGEND>
					   			<TABLE width="100%" cellpadding="2" cellspacing = "1" border = "0">
					   				<TR class="tbheader">
					   					<th width="8%">UNC</th>
					   					<th width="20%">Nhà cung cấp</th>
					   					<th width="12%">Số tiền Ngoại tệ</th>
					   					<th width="10%">Tỉ giá</th>
					   					<th width="12%">Số tiền VNĐ</th>
					   					<th width="12%">Thanh toán</th>
					   					<th width="12%">Còn lại</th>
					   					<th width="12%">Trả hết</th>
					   				</TR>
					   				
					   				 <%  
								int i = 0;
					   			if(UNCRS != null){
				    	            while(UNCRS.next())
				                	{
				                		System.out.println("Lấy DL: "+ UNCRS.getString("TTID"));
					               		%>
					               	<tr>
					               		<td>
					               			<input type="text" style= "width:100%;text-align:center" name="chiId" id="chiId<%=i %>" value="<%=UNCRS.getString("TTID")%>" readonly="readonly">
					               		</td>
					               		<td>
					               			<input type="hidden" name="nccId" id="nccId<%=i %>"  value="<%=UNCRS.getString("NCCID")%>" readonly="readonly">
					               			<input type="text" style= "width:100%" name="nccTen" id="nccTen<%=i %>"  value="<%=UNCRS.getString("TENNCC")%>" readonly="readonly">
					               			
					               		</td>
					               		<td>
					               			<input type="text" style= "width:100%;text-align:right" name="sotienHDNT" id="sotienHDNT<%=i %>"  value="<%= formatter1.format(Double.parseDouble(UNCRS.getString("SOTIENNT")) )%>" readonly="readonly" onKeyPress = "return keypress(event);">
					               		</td>
					               		<td>
					               		<% if(Double.parseDouble(UNCRS.getString("SOTIENNT")) > 0){ %>
					               			<input type="text" style= "width:100%;text-align:right" name="tigiaHD" id="tigiaHD<%=i %>" value="<%= formatter1.format(Double.parseDouble(UNCRS.getString("TIGIA")) )%>" onKeyPress = "return keypress(event);" onchange= "ThanhToan(100)">
					               		<% }else{ %>
               							     <input type="text" style= "width:100%;text-align:right" name="tigiaHD" id="tigiaHD<%=i %>" value="<%= formatter1.format(Double.parseDouble(UNCRS.getString("TIGIA")) )%>" onKeyPress = "return keypress(event);" readonly>          		
					               		<% } %>
					               		</td>
					               		<td>
					               			<input type="text" style= "width:100%;text-align:right" name="sotienHDVND" id="sotienHDVND<%=i %>" value="<%= formatter.format(Double.parseDouble(UNCRS.getString("SOTIENVND")) )%>" readonly="readonly" onKeyPress = "return keypress(event);" >
					               		</td>
					               		<td>					               			
					               			<input type="text" style= "width:100%;text-align:right" name="thanhtoan" id="thanhtoan<%=i %>" value="<%= formatter1.format(Double.parseDouble(UNCRS.getString("THANHTOAN")) )%>" onKeyPress = "return keypress(event);" onchange= "ThanhToan(100)">
					               		</td>
					               		<td>
					               			<input type="text" style= "width:100%;text-align:right" name="conlaiHD" id="conlaiHD<%=i %>" type="text" value="<%= formatter.format(Double.parseDouble(UNCRS.getString("CONLAI")) )%>" readonly="readonly" onKeyPress = "return keypress(event);">
					               		</td>
					               		<td align = "center">
					               			<%if(Double.parseDouble(UNCRS.getString("CONLAI")) == 0){ %>
					               				<input type="checkbox" value=<%=UNCRS.getString("TTID") %> name="trahet" id="trahet<%=i %>" checked="checked" onchange="ThanhToan(<%= i %>)" >
					               			<%}else{%>
					               				<input type="checkbox" value=<%=UNCRS.getString("TTID") %> name="trahet" id="trahet<%=i %>" onchange="ThanhToan(<%= i %>)" >
					               			<%}%>
					               		</td>
					               	</tr>
					               	
					               	<%} %>
					   				
					   			</TABLE>
				   		</FIELDSET>
				   		</TD>
			   		</TR>
			   </TABLE>
			<%					} 
			}
			// KẾ HOẠCH TRẢ VAY			
			%>
			</TD></TR>
			<TR><TD>
				<TABLE  width="60%" cellpadding="4" cellspacing = "1" border = "0">
					<TR>
						<TD>
						<% 
						System.out.println("obj.getThoihan(): " + obj.getThoihan());
						if(obj.getThoihan().trim().length() > 0){ %>
					     	<FIELDSET>
								<LEGEND class="legendtitle">Kế hoạch trả vay</LEGEND>
									<TABLE  width="100%" cellpadding="4" cellspacing = "1" border = "0">
										<TR class="tbheader">
											<TH width="25%">Ngày</TH>
											<TH width="25%">Số tiền trả</TH>
											<TH width="25%">Số tiền lãi</TH>
											<TH width="25%">Còn lại</TH>
										</TR>
						<% 
								
								String[] ngaytravay = obj.getNgaytravay();
								String[] travay = obj.getTravay();
								String[] tralai = obj.getTralai();
								String[] conlai = obj.getConlai();
								
								System.out.println("obj.getThoihan(): " + obj.getThoihan());
								if(travay != null && tralai != null && conlai != null )
								{
// 								for(int j = 0; j < Integer.parseInt(obj.getThoihan()); j++){ 
								int l = Integer.parseInt(obj.getThoihan()) > tralai.length ? tralai.length : Integer.parseInt(obj.getThoihan()); 
								for(int j = 0; j < l; j++){ 
									System.out.println("i = " + j);
							    	if (j % 2 != 0) {	%>
										 <TR align="center" align = "left" class = "tblightrow" >
                        <%		 	} else { 			%>
                                         <TR align="center" align = "left" class = "tbdarkrow" >
                        <%			}					%>
								
										  <TD  ><INPUT class = "days" name="ngaytravay" type="text" size="10" value="<%= ngaytravay[j] %>" ></TD>
										  <TD  ><INPUT  name="travay" type="text" size="20" style = "text-align: right" value="<%= formatter1.format(Double.parseDouble(travay[j].replace(",",""))) %>" onchange="TinhTienLai()" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" ></TD>
										  <TD  ><INPUT  name="tralai" type="text" size="20" style = "text-align: right" value="<%= formatter1.format(Double.parseDouble(tralai[j].replace(",",""))) %>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" ></TD>
										  <TD  ><INPUT  name="conlai" type="text" size="20" style = "text-align: right" value="<%= formatter1.format(Double.parseDouble(conlai[j].replace(",",""))) %>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" ></TD>

										</TR>
						<%		} 
								System.out.println("Bat dau trong");
								for(int j = l; j < Integer.parseInt(obj.getThoihan()); j++){ 
									System.out.println("i = " + j);
									if (j % 2 != 0) {	%>
										 <TR align="center" align = "left" class = "tblightrow" >
                        <%		 	} else { 			%>
                                         <TR align="center" align = "left" class = "tbdarkrow" >
                        <%			}					%>
								
										  <TD  ><INPUT class = "days" name="ngaytravay" type="text" size="10" ></TD>
										  <TD  ><INPUT  name="travay" type="text" size="20" style = "text-align: right" value="0"  onchange="TinhTienLai()" ></TD>
										  <TD  ><INPUT  name="tralai" type="text" size="20" style = "text-align: right" value="0" onkeypress="return isNumberKey2(event)" ></TD>
										  <TD  ><INPUT  name="conlai" type="text" size="20" style = "text-align: right" value="0" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" ></TD>

										</TR>
						<%		} 
							} %>
							</TABLE>
							</FIELDSET>			
						<%}
						
						%>
						</TD>	
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
         </div>
		</div>	
		</div>	
	</form>

</BODY>
<script type="text/javascript">
	TinhTienConLai();
	dropdowncontent.init('ncc', "right-bottom", 300, "click");
</script>
</html>
<%
	try{
		if(HDRS != null) HDRS.close();
		if(UNCRS != null) UNCRS.close();
		if(ttRs != null) ttRs.close();
		if(sotkRs != null) sotkRs.close();
			
		if(obj != null){
			obj.DbClose();
		}
			
		session.setAttribute("obj",null);
	}catch(Exception err){
			
	} 
}%>