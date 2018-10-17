<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.dieuchuyentien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpDieuchuyentien obj = (IErpDieuchuyentien)session.getAttribute("obj"); %>
<% ResultSet ttRs = (ResultSet)obj.getTienteRs(); %>

<% ResultSet nccRs = (ResultSet)obj.getNccRs(); %>
<% ResultSet PORs = (ResultSet)obj.getPORs(); %>
<% ResultSet NHKyquyRs = (ResultSet)obj.getNHKyquyRs(); %>

<% ResultSet NHChuyenRs = (ResultSet)obj.getNHChuyenRs(); %>

<% ResultSet NHNhanRs = (ResultSet)obj.getNHNhanRs(); %>
<% ResultSet nhtrichphiRs =  (ResultSet)obj.getNHTrichphiRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); %> 
<% NumberFormat formatter2 = new DecimalFormat("#,###,###"); %>
<%  
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	

	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("123",userId);

 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
    function TinhSoTien()
    {
    	var sotienNT = (document.getElementsByName("sotienNT")).item(0).value.replace(/,/g,"");
    	var tigia = (document.getElementsByName("tigia")).item(0).value.replace(/,/g,"");
    	document.getElementsByName("sotienVND").item(0).value = DinhDangTien(parseFloat(sotienNT)*parseFloat(tigia));
    	document.getElementsByName("sotienNT").item(0).value = DinhDangDonGia((parseFloat(sotienNT)).toFixed(2));
    }
    
    function TinhPhi()
    {
    	var phiNT = (document.getElementsByName("phiNT")).item(0).value.replace(/,/g,"");
    	var tigia = (document.getElementsByName("tigia")).item(0).value.replace(/,/g,"");
    	document.getElementsByName("phiVND").item(0).value = DinhDangTien(parseFloat(phiNT)*parseFloat(tigia));
    	document.getElementsByName("phiNT").item(0).value = DinhDangDonGia((parseFloat(phiNT)).toFixed(2));
    	
    }
    
    function TinhThue()
    {
    	var thueNT = (document.getElementsByName("thueNT")).item(0).value.replace(/,/g,"");
    	var tigia = (document.getElementsByName("tigia")).item(0).value.replace(/,/g,"");
    	document.getElementsByName("thueVND").item(0).value = DinhDangTien(parseFloat(thueNT)*parseFloat(tigia));
    	document.getElementsByName("thueNT").item(0).value = DinhDangDonGia((parseFloat(thueNT)).toFixed(2));    	
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

    function DinhdangVND()
    {
    	var sotienVND = (document.getElementsByName("sotienVND")).item(0).value.replace(/,/g,"");
    	var phiVND = (document.getElementsByName("phiVND")).item(0).value.replace(/,/g,"");
    	var thueVND = (document.getElementsByName("thueVND")).item(0).value.replace(/,/g,"");
    	
    	document.getElementsByName("sotienVND").item(0).value = DinhDangTien(parseFloat(sotienVND));
    	document.getElementsByName("phiVND").item(0).value = DinhDangTien(parseFloat(phiVND));
    	document.getElementsByName("thueVND").item(0).value = DinhDangTien(parseFloat(thueVND));
    	document.getElementsByName("tienthue_VAT").item(0).value = document.getElementsByName("thueVND").item(0).value;
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
    

    function submitform()
	{   
	   document.forms["erpDctForm"].submit();
	}
	
	function newform()
	{   
		document.forms["erpDctForm"].action.value = "Tao moi";
	    document.forms["erpDctForm"].submit();
	}
 
	function saveform()
	{	
		var loaidc = document.getElementById("loaidc");
		
		var ngaychungtu = document.getElementById("ngaychungtu");
 		if(ngaychungtu.value == "")
		{
			alert("Vui lòng chọn ngày chứng từ");
			return;
		}
		 
		var sochungtu = document.getElementById("sochungtu");
 		if(sochungtu.value == "")
		{
			alert("Vui lòng nhập số chứng từ");
			return;
		}

		var ttId = document.getElementById("ttId");
 		if(ttId.value == "")
		{
			alert("Vui lòng chọn loại tiền");
			return;
		}

 		var nhchuyenId = document.getElementById("nhchuyenId");
 		if(nhchuyenId.value == "")
		{
			alert("Vui lòng chọn ngân hàng chuyển");
			return;
		}

 		if(loaidc.value == '0')
		{
 			var nhnhanId = document.getElementById("nhnhanId");
 			if(nhnhanId.value == "")
 			{
 				alert("Vui lòng chọn ngân hàng nhận");
 				return;
 			}
		}
 		
 		
 		if(loaidc.value == '1')
		{
 			var nhkyquyId = document.getElementById("nhkyquyId");
 			if( nhkyquyId.value == "")
 			{
 	 			var nhkyquyId = document.getElementById("nhkyquyId");
 				alert("Vui lòng chọn ngân hàng ký quỹ");
 				return;
 			}
		
		}
 		
 		
//  		var trichphi = document.getElementsByName("trichphi");
//  		if(trichphi.item(0).checked)
// 		{
//  			var nhtrichphiId = document.getElementById("nhtrichphiId");
//  	 		if(nhtrichphiId.value == "")
//  			{ 			
// 	 			alert("Vui lòng chọn ngân hàng trích phí");
// 				return;
//  			}
// 		}

 		var sotienVND = document.getElementById("sotienVND");
 		if(sotienVND.value == "" || sotienVND.value == "0")
		{
			alert("Vui lòng nhập số tiền");
			return;
		}

 		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Đang xử lý...</a>";
	 	document.forms['erpDctForm'].action.value='save';
	    document.forms['erpDctForm'].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDctForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDctForm"].msg.value);
	 }
	 
	 018718562047
	 
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

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Đang xử lý</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 function DinhDangTiGia()
		{			
			var giatrinhap = document.getElementById("tigia").value;
			giatrinhap = parseFloat(giatrinhap);
			document.getElementById("tigia").value = DinhDangDonGia(giatrinhap.toFixed(2));
			
			var tongcong = document.getElementsByName('sotienNT');	
			var tongcongVND = document.getElementsByName('sotienVND');	
			
			
			if(tongcong[0].value == '') tongcong[0].value = '0';
			if(parseFloat(tongcong[0].value.replace(",","")) > 0)
			{
				var tienvnd = parseFloat(tongcong[0].value.replace(",",""))* giatrinhap;
				tongcongVND[0].value = DinhDangTien(tienvnd);
			} 
		}
		
	
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<body>
<form name="erpDctForm" method="post" action="../../ErpDieuchuyentienUpdateSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý kế toán > Chức năng > Điều chuyển tiền > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDieuchuyentienSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
    
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Điều chuyển tiền</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Ngày chứng từ</TD>
                        <TD class="plainlabel" colspan = 3>
                            <input type="text" class="days" 
                                   id="ngaychungtu" name="ngaychungtu" value="<%= obj.getNgaychungtu() %>"   readonly/>
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%">Số chứng từ</TD>
                        <TD class="plainlabel" colspan = 3>
                            <input type="text" id="sochungtu" name="sochungtu" value="<%= obj.getSochungtu() %>"   />
                        </TD>
                    </TR>

					 <TR>
                        <TD class="plainlabel" >Lý do điều chuyển </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >  
                    		<select name="loaidc" Id="loaidc" onchange="submitform()" style="width: 200px">
                            	
                            	<% if(obj.getLoaidc().equals("0")){ %>
                            		<option value=""></option>
	                        		<option value="0" selected="selected" >Điều chuyển tiền</option>
	                        		<option value="1" >Ký quỹ mở L/C</option>
	                        	<%}else if(obj.getLoaidc().equals("1")){ %>
	                        		<option value=""></option>
	                        		<option value="0" >Điều chuyển tiền</option>
	                        		<option value="1" selected="selected" >Ký quỹ mở L/C</option>
	                        	<%}else{ %>
	                        		<option value="" selected="selected" ></option>
	                        		<option value="0" >Điều chuyển tiền</option>
	                        		<option value="1" >Ký quỹ mở L/C</option>
	                        	<%} %>

                            </select>
                        </TD> 
                     </TR> 
                     
                     <%if(obj.getLoaidc().equals("1")) { %>
                     <TR>
                     	 <TD class="plainlabel">Nhà cung cấp </TD>
                     	 <TD class="plainlabel" colspan="3">
                     	 	<select name="nccId" Id="nccId" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(nccRs != null)
	                        		{
	                        			while(nccRs.next())
	                        			{  
	                        			if( nccRs.getString("PK_SEQ").equals(obj.getNccId())){ %>
	                        				<option value="<%= nccRs.getString("PK_SEQ") %>" selected="selected" ><%= nccRs.getString("TEN") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccRs.getString("PK_SEQ") %>" ><%= nccRs.getString("TEN") %></option>
	                        		 <% } } nccRs.close();
	                        		}
	                        	%>
                            </select>
                     	 </TD>
                     </TR>
                      <TR>
                     	 <TD class="plainlabel">Số PO </TD>
                     	 <TD class="plainlabel" colspan="3">
                     	 	<select name="poId" Id="poId" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(PORs != null)
	                        		{
	                        			while(PORs.next())
	                        			{  
	                        			if( PORs.getString("PK_SEQ").equals(obj.getPOId())){ %>
	                        				<option value="<%= PORs.getString("PK_SEQ") %>" selected="selected" ><%= PORs.getString("dmhId") %></option>
	                        			<%}else { %>
	                        				<option value="<%= PORs.getString("PK_SEQ") %>" ><%= PORs.getString("dmhId") %></option>
	                        		 <% } } PORs.close();
	                        		}
	                        	%>
                            </select>
                     	 </TD>
                     </TR>
                     
                      <%} %>
                                           
					 <TR>
                        <TD class="plainlabel" valign="middle" >Loại tiền </TD>
	                     	<TD class="plainlabel" valign="middle" colspan = 4 >        
	                    		<select name="ttId" Id="ttId" onchange="submitform()" style="width: 200px">
	                            	<option value=""></option>
	                            	<%
		                        		if(ttRs != null)
		                        		{
		                        			while(ttRs.next())
		                        			{  
		                        			if( ttRs.getString("TTID").equals(obj.getTtId())){ %>
		                        				<option value="<%= ttRs.getString("TTID") %>" selected="selected" ><%= ttRs.getString("TIENTE") %></option>
		                        			<%}else { %>
		                        				<option value="<%= ttRs.getString("TTID") %>" ><%= ttRs.getString("TIENTE") %></option>
		                        		 <% } } ttRs.close();
		                        		}
		                        	%>
	                            </select>
	                        </TD>                        

                    </TR>
                   <% if(!obj.getTtId().equals("100000") && !obj.getTtId().equals("")){ %>
                   <TR>
	                        <TD class="plainlabel" valign="middle">Tỉ giá </TD>
	                        <TD  class="plainlabel" valign="middle" colspan="3">
		                        <input type="text" id="tigia" name="tigia" value="<%= formatter2.format(Double.parseDouble(obj.getTigia())) %>"  onchange="DinhDangTiGia();" />
	                        </TD>
	                </TR>
	                <% } %>
                    
                    <TR>

            		<% if(obj.getTtId().equals("100000")){%>
                        <TD class="plainlabel" valign="middle" >Số tiền </TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
							<input type="text" style = "text-align:right" id="sotienVND" name="sotienVND" value="<%= formatter2.format(Double.parseDouble(obj.getSotienVND())) %>"  onKeyPress = "return keypress(event);"  onChange = "DinhdangVND();"/>
                        </TD>                        
					<%}else{ %>
                        <TD class="plainlabel" valign="middle" >Số tiền (Ngoại tệ) </TD>
                        <TD class="plainlabel" valign="middle">
							<input type="text" style = "text-align:right" id="sotienNT" name="sotienNT" value="<%= formatter1.format(Double.parseDouble(obj.getSotienNT())) %>"   onKeyPress = "return keypress(event);"  onChange = "TinhSoTien();"/>
                        </TD>                        

                        <TD class="plainlabel" valign="middle" >Số tiền (VNĐ) </TD>
                        <TD class="plainlabel" valign="middle">
							<input type="text" style = "text-align:right" id="sotienVND" name="sotienVND" value="<%= formatter2.format(Double.parseDouble(obj.getSotienVND())) %>"  readonly />
                        </TD>                        

            		<%} %>    
                	</TR> 
                	
                    <TR>
                        <TD class="plainlabel" valign="middle" >Ngân hàng chuyển </TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                            <select name="nhchuyenId" Id="nhchuyenId" onchange="submitform()" style="width: 450px">
                            	<option value=""></option>
                            	<%
	                        		if(NHChuyenRs != null)
	                        		{
	                        			while(NHChuyenRs.next())
	                        			{  
	                        			if( NHChuyenRs.getString("NHCTYID").equals(obj.getNhchuyenId())){ %>
	                        				<option value="<%= NHChuyenRs.getString("NHCTYID") %>" selected="selected" ><%= NHChuyenRs.getString("NGANHANG") %></option>
	                        			<%}else { %>
	                        				<option value="<%= NHChuyenRs.getString("NHCTYID") %>" ><%= NHChuyenRs.getString("NGANHANG") %></option>
	                        		 <% } } NHChuyenRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    <%if(obj.getLoaidc().equals("0")){  // LOẠI :ĐIỀU CHUYỂN TIỀN >> LOAD %>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Ngân hàng nhận </TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                            <select name="nhnhanId" Id="nhnhanId" onchange="submitform()"  style="width: 450px">
                            	<option value=""></option>
                            	<%
	                        		if(NHNhanRs != null)
	                        		{
	                        			while(NHNhanRs.next())
	                        			{  
	                        			if( NHNhanRs.getString("NHCTYID").equals(obj.getNhnhanId())){ %>
	                        				<option value="<%= NHNhanRs.getString("NHCTYID") %>" selected="selected" ><%= NHNhanRs.getString("NGANHANG") %></option>
	                        			<%}else { %>
	                        				<option value="<%= NHNhanRs.getString("NHCTYID") %>" ><%= NHNhanRs.getString("NGANHANG") %></option>
	                        		 <% } } NHNhanRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    <%} else{ // LOẠI: KÝ QUỸ >> LOAD ĐỐI TƯỢNG KÝ QUỸ %>
                     <TR>
                        <TD class="plainlabel" valign="middle" >Ngân hàng ký quỹ </TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                            <select name="nhkyquyId" Id="nhkyquyId" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(NHKyquyRs != null)
	                        		{
	                        			while(NHKyquyRs.next())
	                        			{  
	                        			if( NHKyquyRs.getString("PK_SEQ").equals(obj.getNhKyquyId())){ %>
	                        				<option value="<%= NHKyquyRs.getString("PK_SEQ") %>" selected="selected" ><%= NHKyquyRs.getString("TEN") %></option>
	                        			<%}else { %>
	                        				<option value="<%= NHKyquyRs.getString("PK_SEQ") %>" ><%= NHKyquyRs.getString("TEN") %></option>
	                        		 <% } } NHKyquyRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                    <%} %>          
            
                	 <% %><TR>
                  	<% 
                  	//TRICH PHI BANG NGOAI TE
             	//if(obj.getTrichphi().equals("0")) { %> --%>

<!--                     	<TD class="plainlabel" colspan = 4>Trích phí bằng VNĐ -->
<!-- 							<input type="checkbox" id="trichphi" name="trichphi" value="1"  onChange= "submitform();" > -->
<!-- 	                	</TD> -->
<%-- 	               <%}else{ %>  --%>
<!-- 	                    <TD class="plainlabel">Trích phí bằng VNĐ -->
<!-- 							<input type="checkbox" id="trichphi" name="trichphi" value="1" checked = "checked" onChange= "submitform();" > -->
<!-- 		                </TD> -->
						
<!-- 						<TD class="plainlabel">Ngân hàng trích phí (VND) </TD>				 -->
<!-- 						<TD class="plainlabel" colspan = 3> -->
                     
<!--                         	<select name="nhtrichphiId" id="nhtrichphiId" style="width: 500px" onChange = "submitform();"> -->
<!--                         		<OPTION VALUE = "">&nbsp</OPTION> -->
<%--                         	<% --%>
<!--                         		if(nhtrichphiRs != null) -->
<!--                         		{ -->
<!--                         			try -->
<!--                         			{ -->
<!--                         			while(nhtrichphiRs.next()) -->
<!--                         			{   -->
<!--                         			if( nhtrichphiRs.getString("NHCTYID").equals(obj.getNHTrichphiId())){ %> -->
<%--                         				<option value="<%= nhtrichphiRs.getString("NHCTYID") %>" selected="selected" ><%= nhtrichphiRs.getString("NGANHANG")%></option> --%>
<%--                         			<%}else { %> --%>
<%--                         				<option value="<%= nhtrichphiRs.getString("NHCTYID") %>" ><%= nhtrichphiRs.getString("NGANHANG") %></option> --%>
<%--                         		 <% } } nhtrichphiRs.close();} catch(SQLException ex){} --%>
<!--                         		} -->
<!--                         	%> -->
<!--                         </select> -->

<!--                     </TD> -->
					
<%-- 				   <%} %>	 --%>
<!--                 </TR>                     -->
                
<%--                 <%  --%>
<!--                 if(!obj.getTtId().equals("100000")){ -->
                
<!--                 %> -->
<!-- 				<TR>				 -->
<!-- 	                <TD class="plainlabel" valign="middle" >Phí (Ngoại tệ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle"> -->
                
<%--                 <%     if(obj.getTrichphi().equals("0")) { %>  --%>
				
<%-- 						<input type="text" style = "text-align:right" id="phiNT" name="phiNT" value="<%= formatter1.format(Double.parseDouble(obj.getPhiNT())) %>"   onKeyPress = "return keypress(event);" onChange = "TinhPhi();"/> --%>
				
<%-- 				<%		}else{ %> --%>
							
<%-- 						<input type="text" style = "text-align:right" id="phiNT" name="phiNT" value="<%= formatter1.format(Double.parseDouble(obj.getPhiNT())) %>" readonly  /> --%>
						
<%-- 				<%		} %>							 --%>
<!--                     </TD>                         -->

<!--                     <TD class="plainlabel" valign="middle" >Phí (VNĐ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle"> -->

<%--                 <%     if(obj.getTrichphi().equals("0")) { %>  --%>

<%-- 						<input type="text" style = "text-align:right" id="phiVND" name="phiVND" value="<%= formatter2.format(Double.parseDouble(obj.getPhiVND())) %>"  readonly /> --%>
						
<%-- 				<%		}else{ %> --%>
				
<%-- 						<input type="text" style = "text-align:right" id="phiVND" name="phiVND" value="<%= formatter2.format(Double.parseDouble(obj.getPhiVND())) %>"  onKeyPress = "return keypress(event);" onChange = "DinhdangVND();"/> --%>
						
<%-- 				<%		} %> --%>
<!--                     </TD>                         -->
							
<!-- 				</TR> -->
				
<!-- 				<TR> -->
<!-- 	                <TD class="plainlabel" valign="middle" >Thuế VAT (Ngoại tệ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle"> -->
                
<%--                 <%     if(obj.getTrichphi().equals("0")) { %>                     --%>
				
<%-- 						<input type="text" style = "text-align:right" id="thueNT" name="thueNT" value="<%= formatter1.format(Double.parseDouble(obj.getThueNT())) %>"   onKeyPress = "return keypress(event);" onChange = "TinhThue();"/> --%>
						
<%-- 				<%		}else{ %> --%>
						
<%-- 						<input type="text" style = "text-align:right" id="thueNT" name="thueNT" value="<%= formatter1.format(Double.parseDouble(obj.getThueNT())) %>"  readonly /> --%>
				
<%-- 				<%		} %> --%>
<!--                     </TD>                         -->

<!--                     <TD class="plainlabel" valign="middle" >Thuế VAT (VNĐ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle"> -->

<%--                 <%     if(obj.getTrichphi().equals("0")) { %>                     --%>

<%-- 						<input type="text" style = "text-align:right" id="thueVND" name="thueVND" value="<%= formatter2.format(Double.parseDouble(obj.getThueVND())) %>"  readonly /> --%>
						
<%-- 				<%		}else{ %> --%>
				
<%-- 						<input type="text" style = "text-align:right" id="thueVND" name="thueVND" value="<%= formatter2.format(Double.parseDouble(obj.getThueVND())) %>"  onKeyPress = "return keypress(event);" onChange = "DinhdangVND();"/> --%>
						
<%-- 				<%		} %> --%>
				
<!-- 	                   <A href="" id="tinhVAT" rel="subcontentVAT"><img alt="Tính VAT" title="Tính VAT" src="../images/vitriluu.png"></a> -->
<!-- 	                   <DIV id="subcontentVAT" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 800px; padding: 4px; max-height: 300px; overflow: auto; "> -->
<!-- 							<TABLE width="100%" align="center"> -->
<!--         						<TR class="tbheader"> -->
<!-- 						        	<TH width="80px" align = "center">Mã hóa đơn</TH> -->
<!-- 						        	<TH width="80px" align = "center">Mẫu hóa đơn</TH> -->
<!-- 						        	<TH width="80px" align = "center">Ký hiệu</TH> -->
<!-- 						         	<TH width="80px" align = "center">Số hóa đơn</TH> -->
<!-- 						         	<TH width="80px" align = "center">Ngày hóa đơn </TH> -->
<!-- 									<TH width="80px" align = "center">Tên NH </TH> -->
<!-- 									<TH width="80px" align = "center">MST </TH> -->
<!-- 									<TH width="80px" align = "center">Tiền thuế </TH> -->
<!-- 								</TR> -->
<!-- 					      		<TR> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "maHD_VAT" id = "maHD_VAT" value = "<%= obj.getMahoadon() %>" style="width: 100%" readonly="readonly" > --%>
<!-- 									</TD> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "mauHD_VAT" id = "mauHD_VAT" value = "<%= obj.getMauhoadon() %>" onChange = "javascript:mauhoadon();" style="width: 100%" > --%>
<!-- 									</TD> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "kyhieu_VAT" id = "kyhieu_VAT" value = "<%= obj.getKyhieu() %>" style="width: 100%" > --%>
<!-- 									</TD> -->
											      		
<!-- 									<TD> -->
<%-- 										<input type="text" name = "sohd_VAT" id = "sohd_VAT" value = "<%= obj.getSohoadon() %>" style="width: 100%" > --%>
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name = "ngayhd_VAT" id = "ngayhd_VAT" value = "<%= obj.getNgayhoadon() %>" style="width: 100%" class="days" readonly="readonly"> --%>
<!-- 									</TD> -->
								
<!-- 									<TD> -->
<%-- 										<input type="text" name = "nghangTen" id = "nghangTen" value = "<%= obj.getTenNH_VAT() %>" style="width: 100%" readonly> --%>
										
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name= "mst_VAT" id= "mst_VAT" value = "<%= obj.getMST() %>" style="width: 100%"  > --%>
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name= "tienthue_VAT" id= "tienthue_VAT" value = "<%= formatter2.format(Double.parseDouble(obj.getThueVND().replaceAll(",",""))) %>" style="width: 100%;text-align: right" readonly> --%>
<!-- 									</TD> -->
<!-- 								</TR> -->
<!-- 							</TABLE> -->
<!-- 						    <DIV align="right"> -->
<!-- 					             <LABEL style="color: red" ></LABEL> -->
<!-- 					               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				    	              	<a href="javascript:dropdowncontent.hidediv('subcontentVAT')">Hoàn tất</a> -->
<!-- 							</DIV> -->
						     				
<!--     					</DIV> -->
				
<!--                     </TD>                         -->
							
<!-- 				</TR>             -->
<%-- 				<%}else{ %> --%>
<!-- 				<TR>				 -->
<!--                     <TD class="plainlabel" valign="middle" >Phí (VNĐ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle" colspan = 3> -->
<%-- 						<input type="text" style = "text-align:right" id="phiVND" name="phiVND" value="<%= formatter2.format(Double.parseDouble(obj.getPhiVND())) %>"   onKeyPress = "return keypress(event);" onChange = "DinhdangVND();" /> --%>
<!--                     </TD>                         -->
							
<!-- 				</TR> -->
<!-- 				<TR> -->
<!--                     <TD class="plainlabel" valign="middle" >Thuế VAT (VNĐ) </TD> -->
<!--                     <TD class="plainlabel" valign="middle" colspan = 3> -->
<!-- 	                   <A href="" id="tinhVAT2" rel="subcontentVAT2"><img alt="Tính VAT" title="Tính VAT" src="../images/vitriluu.png"></a> -->
<!-- 	                   <DIV id="subcontentVAT2" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 800px; padding: 4px; max-height: 300px; overflow: auto; "> -->
<!-- 							<TABLE width="100%" align="center"> -->
<!--         						<TR class="tbheader"> -->
<!-- 						        	<TH width="80px" align = "center">Mã hóa đơn</TH> -->
<!-- 						        	<TH width="80px" align = "center">Mẫu hóa đơn</TH> -->
<!-- 						        	<TH width="80px" align = "center">Ký hiệu</TH> -->
<!-- 						         	<TH width="80px" align = "center">Số hóa đơn</TH> -->
<!-- 						         	<TH width="80px" align = "center">Ngày hóa đơn </TH> -->
<!-- 									<TH width="80px" align = "center">Tên NH </TH> -->
<!-- 									<TH width="80px" align = "center">MST </TH> -->
<!-- 									<TH width="70px" align = "center">Tiền thuế </TH> -->
<!-- 								</TR> -->
<!-- 					      		<TR> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "maHD_VAT" id = "maHD_VAT" value = "<%= obj.getMahoadon() %>" style="width: 100%" readonly="readonly" > --%>
<!-- 									</TD> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "mauHD_VAT" id = "mauHD_VAT" value = "<%= obj.getMauhoadon() %>" onChange = "javascript:mauhoadon();" style="width: 100%" > --%>
<!-- 									</TD> -->
<!-- 									<TD> -->
<%-- 										<input type="text" name = "kyhieu_VAT" id = "kyhieu_VAT" value = "<%= obj.getKyhieu() %>" style="width: 100%" > --%>
<!-- 									</TD> -->
											      		
<!-- 									<TD> -->
<%-- 										<input type="text" name = "sohd_VAT" id = "sohd_VAT" value = "<%= obj.getSohoadon() %>" style="width: 100%" > --%>
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name = "ngayhd_VAT" id = "ngayhd_VAT" value = "<%= obj.getNgayhoadon() %>" style="width: 100%" class="days" readonly="readonly"> --%>
<!-- 									</TD> -->
								
<!-- 									<TD> -->
<%-- 										<input type="text" name = "nghangTen" id = "nghangTen" value = "<%= obj.getTenNH_VAT() %>" style="width: 100%" readonly> --%>
										
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name= "mst_VAT" id= "mst_VAT" value = "<%= obj.getMST() %>" style="width: 100%"  > --%>
<!-- 									</TD> -->

<!-- 									<TD> -->
<%-- 										<input type="text" name= "tienthue_VAT" id= "tienthue_VAT" value = "<%= formatter2.format(Double.parseDouble(obj.getThueVND().replaceAll(",",""))) %>" style="width: 100%;text-align: right" readonly> --%>
<!-- 									</TD> -->
<!-- 								</TR> -->
<!-- 							</TABLE> -->
<!-- 						    <DIV align="right"> -->
<!-- 					             <LABEL style="color: red" ></LABEL> -->
<!-- 					               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				    	              	<a href="javascript:dropdowncontent.hidediv('subcontentVAT2')">Hoàn tất</a> -->
<!-- 							</DIV> -->
						     				
<!--     					</DIV> -->
                    
<%-- 						<input type="text" style = "text-align:right;width:180px" id="thueVND" name="thueVND" value="<%= formatter2.format(Double.parseDouble(obj.getThueVND())) %>"  onKeyPress = "return keypress(event);" onChange = "DinhdangVND();" /> --%>
                                           
<!-- 					</TD>  -->
<!-- 				</TR>             -->
				
<%-- 				<%} %>             --%>
				<TR>
                    <TD class="plainlabel" valign="middle" >Diễn giải chứng từ </TD>
                    <TD class="plainlabel" valign="middle" colspan = 3>
						<input type="text" id="ghichu" name="ghichu" value="<%= obj.getGhichu() %>"   />
                    </TD>                        
							
				</TR>            
				
             </TABLE>                      
        </fieldset>                      
    	</div>
<script type="text/javascript">
	dropdowncontent.init("tinhVAT", "left-top", 200, "click");
	dropdowncontent.init("tinhVAT2", "right-top", 200, "click");
</script>
    	
<%


try{
	if(ttRs != null) ttRs.close();

	if(NHChuyenRs != null) NHChuyenRs.close();

	if(NHNhanRs != null) NHNhanRs.close();

	if(nhtrichphiRs != null) nhtrichphiRs.close();
	obj.DBclose();  
	session.setAttribute("obj",null);

}catch(Exception er){
	er.printStackTrace();
}
}%>
</form>
</body>
</html>