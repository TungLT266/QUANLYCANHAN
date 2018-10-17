<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.distributor.beans.hoadonphelieu.imp.*" %>
<%@ page import="geso.traphaco.distributor.beans.hoadonphelieu.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%IErpHoadonphelieu obj =(IErpHoadonphelieu)session.getAttribute("csxBean");
ResultSet poRs = (ResultSet)obj.getPORs();
ResultSet KhoanmucDTRs = (ResultSet)obj.getKhoanmucDTRs();
ResultSet TaikhoanghinoRs = (ResultSet)obj.getTaikhoanghinoRs();
ResultSet khRs=obj.getkhRs();
ResultSet hdRs=obj.getHdRs();
ResultSet tkdtRs=obj.getTaikhoandoanhthuRs();
String[] tenSanpham = obj.getTensansham();
String[] donvitinh = obj.getDvt();
String[] soluong = obj.getSoluong();
String[] dongia = obj.getDongia();
String[] thanhtien = obj.getTongtien();
List<IErpHoaDonPL_SP> splist =obj.GetSanPhamList();
ResultSet kbhRs = obj.getKbhRs();
ResultSet hoadonRs = obj.getHoadonRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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

#ajax_listOfOptions div {re
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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>

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
<script type="text/javascript" src="../scripts/sphdkhacList.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">
/* 	function replaces()
	{
		var nccId = document.getElementById("nccId");
		var nccTen = document.getElementById("nccTen");
		
		var tem = nccId.value;
		if(tem == "")
		{
			nccTen.value = "";
		}
		else
		{
			if(parseInt(tem.indexOf(" - ")) > 0)
			{
				nccId.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
				
				nccTen.value = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
			}
		}
		
		//UpdateTotal();
		
		setTimeout(replaces, 300);
	} */

	function UpdateTotal()
	{
		var diengiai_sanpham = document.getElementsByName("diengiai_sanpham");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var dongiadagiam = document.getElementsByName("dongiadagiam");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var total = 0;
		for(i = 0; i < diengiai_sanpham.length; i++)
		{
			if(diengiai_sanpham.item(i).value != '')
			{
				if( dongiadagiam.item(i).value != '' && soluong.item(i).value != '')
				{
					var slg = soluong.item(i).value.replace(/,/g,"") ;
					var dg = dongiadagiam.item(i).value.replace(/,/g,"") ;
					
					var tt = parseFloat(slg) * parseFloat(dg) ;
					thanhtien.item(i).value = DinhDangDonGia(tt);					
					
					total = parseFloat(total) + parseFloat(tt);
				}
			}
			else
			{
				soluong.item(i).value = '';
				//dongia.item(i).value = '';
				dongiadagiam.item(i).value = '';
				thanhtien.item(i).value = '';
			}
		}
		
		var vat = document.getElementById("vat").value.replace(/,/g,"");
		
		document.getElementById("bvat").value = DinhDangDonGia(total);
		document.getElementById("avat").value = DinhDangDonGia( parseFloat(total) + (  parseFloat(total) * vat / 100 ) );
		
	}
	
	function submitform()
	{
	    document.forms["khtt"].submit();
	}
	
	function changePO()
	 { 
		 document.forms['khtt'].action.value='changePO';
	     document.forms["khtt"].submit();
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 45|| keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function FormatTien(e)
	{
		if(e.value == '')
			e.value = '0';
		else
		{
			e.value = DinhDangDonGia(e.value);
		}
	}

	function DinhDangTTTT()
	{
		var vat = document.getElementById("avat").value.replace(/,/g,"");
		var doanhthuId = document.getElementById("doanhthuId").value;
		
		if(doanhthuId == '400004')
			{
				if(vat < 0){
					vat = '0';
				}
				if(vat == ''){
					vat = '0';
				}
			}
		if(doanhthuId == '400005')
		{
			if(vat == ''){
				vat = '0';
			}
		}
		document.getElementById("avat").value = DinhDangDonGia( vat);
	}
	
	function DinhDangTT()
	{
		var thanhtien = document.getElementsByName("thanhtien");
		var diengiai_sanpham = document.getElementsByName("diengiai_sanpham");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var dongiadagiam = document.getElementsByName("dongiadagiam");
		var donvitinh = document.getElementsByName("donvitinh");
		
		var doanhthuId = document.getElementById("doanhthuId").value;

		var total= 0;
		for(i = 0; i < diengiai_sanpham.length; i++)
		{
			if(diengiai_sanpham.item(i).value != '')
			{
				if( dongia.item(i).value == '' && dongiadagiam.item(i).value == '' && soluong.item(i).value == '') // HD CHIẾT KHẤU : CHỈ GÕ THÀNH TIỀN
				{	
					
					if(doanhthuId == '400003') // HÓA ĐƠN ĐIỀU CHỈNH GIẢM - ĐƠN GIÁ || ĐƠN GIÁ ĐÃ GIẢM || THÀNH TIỀN < 0
					{
						if(thanhtien.item(i).value  > 0)
							thanhtien.item(i).value = '0';
					}
					
					if(doanhthuId == '400002')// HÓA ĐƠN ĐIỀU CHỈNH TĂNG - ĐƠN GIÁ || ĐƠN GIÁ ĐÃ GIẢM || THÀNH TIỀN > 0
					{
						if(thanhtien.item(i).value < 0)
							thanhtien.item(i).value = '0';
					}
					
					var tt =thanhtien.item(i).value ;
					thanhtien.item(i).value = DinhDangDonGia(tt);
					total = parseFloat(total) + parseFloat(tt);
					
				}
				else{
					
					if(doanhthuId == '400003') // HÓA ĐƠN ĐIỀU CHỈNH GIẢM - ĐƠN GIÁ || ĐƠN GIÁ ĐÃ GIẢM || THÀNH TIỀN < 0
					{
						if(dongia.item(i).value > 0)
							dongia.item(i).value = '0';
						
						if(dongiadagiam.item(i).value > 0)
							dongiadagiam.item(i).value = '0';
						
						if(thanhtien.item(i).value  > 0)
							thanhtien.item(i).value = '0';
					}
					
					if(doanhthuId == '400002')// HÓA ĐƠN ĐIỀU CHỈNH TĂNG - ĐƠN GIÁ || ĐƠN GIÁ ĐÃ GIẢM || THÀNH TIỀN > 0
					{
						if(dongia.item(i).value < 0)
							dongia.item(i).value = '0';
						
						if(dongiadagiam.item(i).value < 0)
							dongiadagiam.item(i).value = '0';
						
						if(thanhtien.item(i).value  < 0)
							thanhtien.item(i).value = '0';
					}
					
					
					if(dongia.item(i).value == 0 && dongiadagiam.item(i).value == 0 && soluong.item(i).value == 0  && thanhtien.item(i).value != 0) // HD CHIẾT KHẤU : CÓ SL &DG SAU ĐÓ SỬA: GÕ THÀNH TIỀN
					{
						soluong.item(i).value = '';
						dongia.item(i).value = '';
						dongiadagiam.item(i).value = '';
						
						var tt =thanhtien.item(i).value.replace(/,/g,"") ;
						thanhtien.item(i).value = DinhDangDonGia(tt);
						total = parseFloat(total) + parseFloat(tt);
						
					}
					else // CAC TH CON LAI
					{
						var slg = soluong.item(i).value.replace(/,/g,"") ;
						var dong = dongia.item(i).value.replace(/,/g,"") ;
						var dg = dongiadagiam.item(i).value.replace(/,/g,"") ;
						
						if(parseFloat(dg)>parseFloat(dong)){
							dg = dong;
						}
						if(parseFloat(dg) == 0){
							dg = dong;
						}
						
						var tt = Math.round(parseFloat(slg) * parseFloat(dg)) ;
						thanhtien.item(i).value = DinhDangDonGia(tt);					
						
						total = parseFloat(total) + parseFloat(tt);
						
						soluong.item(i).value = DinhDangDonGia(slg);
						dongia.item(i).value = DinhDangDonGia(dong);
						dongiadagiam.item(i).value = DinhDangDonGia(dg);
					}
				}
			}
			else
			{
				soluong.item(i).value = '';
				dongia.item(i).value = '';
				dongiadagiam.item(i).value = '';
				thanhtien.item(i).value = '';
				donvitinh.item(i).value = '';
			}
		}
		var vat = document.getElementById("vat").value.replace(/,/g,"");
		
		document.getElementById("bvat").value = DinhDangDonGia(total);
		document.getElementById("avat").value = DinhDangDonGia(Math.round (parseFloat(total) + (  parseFloat(total) * vat / 100 )) );
	}

	 function loadhd()
	 {             
		document.forms['khtt'].action.value='loadhd';
	    document.forms["khtt"].submit();
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
	
	function save()
	{
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	 function loadcontent()
	 {             
		document.forms['khtt'].action.value='reload';
	    document.forms["khtt"].submit();
	 }
	 
	 function loadhd()
	 {             
		document.forms['khtt'].action.value='loadhd';
	    document.forms["khtt"].submit();
	 }
	
	 
	function replaces()
	{			
			var tensp = document.getElementsByName("diengiai_sanpham");
			var donvitinh = document.getElementsByName("donvitinh");
			var sanphamId = document.getElementsByName("sanphamId");
			var dvt_fk = document.getElementsByName("dvt_fk");
			
			var sodong =  tensp.length;
			var i;
			for(i = 0; i < sodong; i++)
			{				
				if(  tensp.item(i).value != ""    )
				{	
					var sp = tensp.item(i).value;
					var pos = parseInt(sp.indexOf(" -- "));
					
					if(pos > 0)
					{
						var idsp = sp.substring(0, pos);
						sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
						tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
						
						sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
						donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
						
						sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
						sanphamId.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
						
						sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
						dvt_fk.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
												
					}
				}
			}
		 	
			setTimeout(replaces, 500);
	}
			
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpHoadonphelieuUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng > Nghiệp vụ khác > Xuất hóa đơn khác > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpHoadonphelieuSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			
											 
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin hóa đơn</LEGEND>
						
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							 <TR>
			 	   <TD class="plainlabel" valign="middle" width="140px" >Loại hóa đơn</TD> 
			 	   <TD class="plainlabel" >
			 	   	<select name="doanhthuId" id="doanhthuId" class="select2" style="width: 200px" onchange="submitform();" >
                        	<option value=""> </option>
                        	<%
                        		if(KhoanmucDTRs != null)
                        		{
                        			try
                        			{
                        			while(KhoanmucDTRs.next())
                        			{  
                        			if( KhoanmucDTRs.getString("doanhthuId").equals(obj.getKhoanmucDTId())){ %>
                        				<option value="<%= KhoanmucDTRs.getString("doanhthuId") %>" selected="selected" ><%= KhoanmucDTRs.getString("doanhthuTen")%></option>
                        			<%}else { %>
                        				<option value="<%= KhoanmucDTRs.getString("doanhthuId") %>" ><%= KhoanmucDTRs.getString("doanhthuTen") %></option>
                        		 <% } } KhoanmucDTRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
			 	   </TD> 
			 	   <td class="plainlabel">
					Hình thức thanh toán
					</td>
					<td class="plainlabel">
					   <select name="hinhthuc" id="hinhthuc" class="select2" style="width: 220px">
					    <% 
					   String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt","Thanh toán sau","Bù trừ công nợ"};
					   for(int k=0;k < mangchuoi.length;k ++ ){
						   
						   if(obj.gethinhthucthanhtoan().equals(mangchuoi[k])) {
							   %>
							    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
							   <%
						   }else{
							   %>
						    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
						  		 <%  
						   }
					   }
					  %>
					   </select>
					</td>
				</TR>
				
	                          <TR>
	                          		<TD class="plainlabel" valign="middle" width="140px" >Ngày ghi nhận</TD>   
			                        <TD class="plainlabel" valign="middle" width="280px" >
			                        	<input type="text" class="days" name="ngayghinhan" value="<%= obj.getNgayghinhan() %>"   > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="100px" >Ngày hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" class="days" name="ngayhoadon" value="<%= obj.getNgayhoadon() %>"   > 
			                        </TD>          
			                  </TR> 
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Ký hiệu hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text"  name="kyhieuhoadon" value="<%= obj.getKyhieuhoadon() %>"   > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >Số hóa đơn</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text"  name="sohoadon" value="<%= obj.getSohoadon() %>"   > 
			                        </TD>          
			                  </TR> 
			                  
			                  <%if(!obj.getKhoanmucDTId().equals("400000") && !obj.getKhoanmucDTId().equals("400001")&&!obj.getKhoanmucDTId().equals("400002")&&!obj.getKhoanmucDTId().equals("400003")&&!obj.getKhoanmucDTId().equals("400004")&&!obj.getKhoanmucDTId().equals("400005")){ %>    
			                     <TR>
			                        <TD class="plainlabel" valign="middle" width="120px" >Trung tâm doanh thu</TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3" >
			                        	<select name="poId" id="soPO" class="select2"  style="width: 200px" >
				                        	<%
				                        		if(poRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(poRs.next())
				                        			{  
				                        			if( poRs.getString("poId").equals(obj.getPOId())){ %>
				                        				<option value="<%= poRs.getString("poId") %>" selected="selected" ><%= poRs.getString("poTen")%></option>
				                        			<%}else { %>
				                        				<option value="<%= poRs.getString("poId") %>" ><%= poRs.getString("poTen") %></option>
				                        		 <% } } poRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
				                        </select>
			                        </TD>			                      	                          		         
			                  </TR> 
			                  <%} %>
			                  
			                  <%if(obj.getKhoanmucDTId().equals("400002")||obj.getKhoanmucDTId().equals("400003")){ %>
			                  <TR>
			                  	<TD class="plainlabel" valign="middle" width="120px" >Ghi chú </TD>   
			                    <TD class="plainlabel" valign="middle" >
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                    </TD> 
			                    
			                    <TD class="plainlabel" valign="middle"  >Tài khoản doanh thu</TD>  
			                    <TD class="plainlabel"     >
			                    		<select name = "taikhoandoanhthu"   id = "taikhoandoanhthu" class="select2" style="width: 200px" onchange="loadcontent();"> 
			                    		<option value=""> &nbsp; </option>
				                        	<%
				                        		if(tkdtRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(tkdtRs.next())
				                        			{  
				                        			if( tkdtRs.getString("pk_seq").equals(obj.getTaikhoandoanhthuId())){ %>
				                        				<option value="<%= tkdtRs.getString("pk_seq") %>" selected="selected" ><%= tkdtRs.getString("tentk") %></option>
				                        			<%}else { %>
				                        				<option value="<%= tkdtRs.getString("pk_seq") %>" ><%= tkdtRs.getString("tentk") %></option>
				                        		 <% } } tkdtRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
			                    		</select>
			                   	</TD> 		
			                  </TR>
			                  <%} else{ %>
			                  <% if(obj.getKhoanmucDTId().equals("400005")) { %>
			                   <TR>
			                  	<TD class="plainlabel" valign="middle" width="120px" >Ghi chú </TD>   
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                    </TD> 
			                    <TD class="plainlabel" valign="middle"  >Tài khoản ghi nợ</TD>  
			                    <TD class="plainlabel"     >
			                    		<select name = "taikhoanghino"   id = "taikhoanghino" class="select2" style="width: 220px" onchange="loadcontent();"> 
			                    		<option value=""> &nbsp; </option>
				                        	<%
				                        		if(TaikhoanghinoRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(TaikhoanghinoRs.next())
				                        			{  
				                        			if( TaikhoanghinoRs.getString("pk_seq").equals(obj.getTaikhoanghinoId())){ %>
				                        				<option value="<%= TaikhoanghinoRs.getString("pk_seq") %>" selected="selected" ><%= TaikhoanghinoRs.getString("SOHIEUTAIKHOAN") %></option>
				                        			<%}else { %>
				                        				<option value="<%= TaikhoanghinoRs.getString("pk_seq") %>" ><%= TaikhoanghinoRs.getString("SOHIEUTAIKHOAN") %></option>
				                        		 <% } } TaikhoanghinoRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
			                    		</select>
			                   	</TD> 		
			                  </TR>
			                  <%} else { %>
			                   <TR>
			                  	<TD class="plainlabel" valign="middle" width="120px" >Ghi chú </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                    </TD> 
			                  </TR>
			                  <%} %>
			                  <%} %>
			                  
			                  	<TR>
									<TD class="plainlabel" >Tìm từ ngày</TD>
			                    	<TD class="plainlabel">                               
                                           <input type="text" size="10" class="days" id="tungay" name="tungay" value="<%=obj.getTungay()%>" maxlength="10" onchange="loadcontent();" /> 
					  				</TD>
					  				
					  				<TD class="plainlabel" >Tìm đến ngày</TD>
			                    	<TD class="plainlabel">                               
                                        <input type="text" size="10" class="days" id="denngay" name="denngay" value="<%=obj.getDenngay()%>" maxlength="10" onchange="loadcontent();" /> 
					  				</TD>
								  				
								</TR>
								
								
								<TR>
									<TD class="plainlabel" >Tìm số hóa đơn </TD>
			                    	<TD class="plainlabel" colspan="4">                               
                                           <input type="text" size="10"  id="timsohoadon" name="timsohoadon" value="<%=obj.gettimHoadon()%>" maxlength="10" onchange="loadcontent();" /> 
					  				</TD>								  				
								</TR>
								
	                          <TR>
	                          		<TD class="plainlabel" >Khách hàng </TD>
				                    <TD class="plainlabel"  width="250px" >
				                    	<select name = "khIdgoc" class="select2" style="width: 200px" onchange="submitform();" >
				                    		<option value=""> &nbsp; </option>
				                        	<%
				                        		if(khRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(khRs.next())
				                        			{  
				                        			if( khRs.getString("pk_seq").equals(obj.getkhIdGoc())){ %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
				                        		 <% } } khRs.close();} catch(SQLException ex){ ex.printStackTrace();}
				                        		}
				                        	%>
				                    	</select>
				                    </TD>
				                    
				                    <TD  class="plainlabel" valign="middle" >Kênh bán hàng</TD>
			                  		<TD  class="plainlabel" valign="middle" >
			                  		<select id= "kbhId" name= "kbhId" style="width: 200px">			                  			
			                  	<!-- 		<option value = ""></option>  -->
			                  			<%try{ %>
			                  				<%if(kbhRs != null){ 
			                  				   while(kbhRs.next()) {%>
			                  				   <%if(obj.getKbhId().equals(kbhRs.getString("pk_seq")) ) { %>
			                  				   		<option value = <%= kbhRs.getString("pk_seq") %> selected="selected"> <%= kbhRs.getString("ten") %></option>
			                  				   <%}else{ %>
			                  				  		 <option value = <%= kbhRs.getString("pk_seq") %> > <%= kbhRs.getString("ten") %></option>
			                  				   <%} %>
			                  				   
			                  			<%    } kbhRs.close();
			                  			    }
			                  			}catch(Exception e){} %>
			                  		</select>
			                   		</TD>            	
			                   
				                             
			                  </TR>
			                  
			                  <TR>
			                  		<TD class="plainlabel" valign="middle"  >Diễn giải </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                        </TD>  
			                  </TR>
			                  
			                  <!-- Hóa đơn tăng / giảm  -->
			                  <% if(obj.getKhoanmucDTId().equals("400002")||obj.getKhoanmucDTId().equals("400003")) { %>
			                  <TR class="plainlabel"> 
								<td class="plainlabel" colspan="3">
									<fieldset >
									<legend> Chọn hóa đơn</legend>
									<table width="100%">
										<TR class="tbheader">
												<TH align="center" width="15%">Số chứng từ</TH>
						                        <TH align="center" width="15%">Số hóa đơn</TH>
						                        <TH align="center" width="15%"> Ngày </TH>
						                        <TH align="center" width="55%"> Khách hàng</TH>
						                        <TH align="center" width="8%"> Chọn </TH>
	                					</TR>
                  					<%
										if(hdRs != null)
	         							{
	         								String dh = obj.getHdId();
	         								while(hdRs.next()){ %>
	           								<tr class= 'tblightrow'>
	           									<td align="center" >
	           										 <%=hdRs.getString("PK_SEQ") %> 		
	           									</td >
	           									<td align="center" >
	           										 <%=hdRs.getString("SOHOADON") %> 		
	           									</td >
	           									<td align="center" >                   										
	           										 <%=hdRs.getString("NGAYXUATHD") %> 	
	           									</td>
	           									<td>                   										
	           										 <%=hdRs.getString("KHACHHANG") %> 	
	           									</td>
	           									
	  										<% 
	   										 if(obj.getHdId().trim().contains(hdRs.getString("PK_SEQ").trim())){%>
	       										<TD align="center"><input type ="radio" checked="checked" name ="hdid" value ="<%= hdRs.getString("pk_seq") %>"  onchange="loadhd();" ></TD>
	       									<%}else{ %>
	       										<TD align="center"><input type ="radio" id="ddhid" name ="hdid" value ="<%= hdRs.getString("pk_seq") %>" onchange="loadhd();"  ></TD>
	       									<%}%>
	       									
	   										</tr>
	   									<% } hdRs.close(); } else{ %>
	   										<tr class= 'tblightrow'><td></td><td></td></tr>
	   									<%} %>    
                 								</table>
                 							</fieldset>
                 							</td>
                 							<td colspan="2" valign="bottom" class="plainlabel" ></td>
                				</TR>
			                  <%} %>
			                  
			                   <TR>
	                          		<TD class="plainlabel" valign="middle" >Tên hàng hóa dịch vụ</TD>   
			                        <TD class="plainlabel" valign="middle" width="400px" colspan="3" >
			                        	<input type="text" id="tenhanghoadichvu" name="tenhanghoadichvu" value="<%= obj.gettenhanghoadichvu() %>"  style="width: 400px;" > 
			                        </TD>  			                                  
			                  </TR>
			                  
			                  
			                   <%if(!obj.getKhoanmucDTId().equals("400004")&&!obj.getKhoanmucDTId().equals("400005")){ %>
			                  <TR>
	                          		<TD class="plainlabel" valign="middle" >Số tiền trước VAT</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text" id="bvat"  name="bvat"  style="text-align:right;" value="<%= obj.getBvat() %>" readonly="readonly"  > VNĐ
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >VAT</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text"  id="vat" name="vat" style="text-align:right;" value="<%= obj.getVat() %>"   onchange="DinhDangTT();"> %
			                        </TD>          
			                  </TR> 
			                  
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Số tiền sau VAT</TD>   
			                        <TD class="plainlabel"  valign="middle" colspan="3" >
			                        	<input type="text" id="avat"  name="avat"  style="text-align:right;" value="<%= obj.getAvat() %>"  readonly="readonly" > VNĐ
			                        </TD>                
			                  </TR>
			                  <%} %>
			                  
			                  <%if(obj.getKhoanmucDTId().equals("400004")||obj.getKhoanmucDTId().equals("400005")) { %>
			                   <TR>       
	                          		<TD class="plainlabel" valign="middle" width="120px" >VAT</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text"  id="vat" name="vat" style="text-align:right;" value="<%= obj.getVat() %>" > %
			                        </TD>   
			                        
			                       <TD class="plainlabel" valign="middle" >Số tiền VAT</TD>   
			                        <TD class="plainlabel"  valign="middle" colspan="3" >
			                        	<input type="text" id="avat"  name="avat"  style="text-align:right;" value="<%= obj.getAvat() %>"  onchange="DinhDangTTTT();"> VNĐ
			                        	<input type="hidden" id="bvat"  name="bvat"  style="text-align:right;" value="0" >
			                        </TD>     
			                  </TR> 
			                  <%} %>
			                  
			                 
			                   <%if(!obj.getKhoanmucDTId().equals("400004")&&!obj.getKhoanmucDTId().equals("400005")) { %> 
				                  <TR>
				                  	<TD colspan="4">
				                  	
				                  		<table width="100%; " cellpadding="0" cellspacing="1"  >
				                  		
				                  			<tr class="tbheader" >
				                  				<th width="50%" >Diễn giải</th>
				                  				<th width="10%" >Đơn vị </th>
				                  				<th width="10%" >Số lượng</th>
				                  				<th width="10%" >Đơn giá</th>			                  				
				                  				<th width="10%" >Đơn giá giảm / tăng </th>
				                  				<th width="10%" >Thành tiền</th>
				                  				<th width="10%" >Ghi chú</th>
				                  			</tr>

				                  			<% 
				                  			  int m = 0 ;
				                  			  if(splist!= null){
				                  				IErpHoaDonPL_SP sanpham;
				                  				int size = splist.size();
				    							int stt = 0;
				                  			    
				                  				while(m < size) { 
				                  					sanpham = splist.get(m);
				                  			      if(sanpham.getTenSanPham().trim().length() > 0){ %> 
				                  				<tr>
				                  					<td>
				                  						<input type="text" name="diengiai_sanpham" value="<%= sanpham.getTenSanPham() %>" style="width: 100%;" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off">
				                  						<input type="hidden" name="sanphamId" value="<%= sanpham.getIdSanPham() %>" style="width: 100%;" >
				                  					</td>
				                  					<td>
				                  						<input type="text" name="donvitinh" value="<%= sanpham.getDonViTinh() %>" style="width: 100%; " readonly="readonly" >
				                  						<input type="hidden" name="dvt_fk" value="<%= sanpham.getDVT_FK() %>" style="width: 100%; "  >
				                  					</td>
				                  					<td>
				                  						<input type="text" name="soluong" value="<%= sanpham.getSoLuong() %>" style="width: 100%; text-align: right; " onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  					<td>
				                  						<input type="text" name="dongia" value="<%= sanpham.getDonGia() %>" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  					<td>
				                  						<input type="text" name="dongiadagiam" value="<%= sanpham.getDonGiaDaGiam() %>" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  				<%if(!obj.getKhoanmucDTId().equals("400000")&&!obj.getKhoanmucDTId().equals("400002")&&!obj.getKhoanmucDTId().equals("400003")){%>
				                  					<td>
				                  						<input type="text" name="thanhtien" value="<%= sanpham.getThanhTien() %>" style="width: 100%; text-align: right; " readonly="readonly" >
				                  					</td>
				                  				<%}else{ %>
				                  					<td>
				                  						<input type="text" name="thanhtien" value="<%= sanpham.getThanhTien() %>" style="width: 100%; text-align: right; " onKeyPress = "return keypress(event);" onchange="DinhDangTT();" >
				                  					</td>
				                  				<%} %>
				                  				
				                  				  <td align="center">
					           	 					<a href="" id="ghichu<%= m %>" rel="subcontent<%= m %>">
						           	 							<img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
						           	 				<DIV id="subcontent<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
									                             background-color: white; width: 450px; padding: 4px;">
									                    <table width="90%" align="center">
									                        <tr>
									                            <th width="100px">Ghi chú</th>
									                        </tr>
						                        			<tr>
									                            <td>
									                            	<input type="text" style="width: 100%;" name="ghichusanpham" value="<%= sanpham.getGhiChu1() %>" />
									                            </td>
									                            
									                        </tr>
									                    </table>
									                     <div align="right">
									                     	<label style="color: red" ></label>
									                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= m %>')">Hoàn tất</a>
									                     </div>
									                </DIV>								               
					           	 		     </td>
				                  				</tr>
				                  			
				                  		<% stt++; } m++; } }%>
				                  			
				                  			<% 
				                  			 while (m < 20) { %>
				                  			
				                  				<tr>
				                  					<td>
				                  						<input type="text" name="diengiai_sanpham" value="" style="width: 100%;" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" >
				                  						<input type="hidden" name="sanphamId" value="" style="width: 100%;" >
				                  					</td>
				                  					<td>
				                  						<input type="text" name="donvitinh" value="" style="width: 100%;" readonly="readonly">
				                  						<input type="hidden" name="dvt_fk" value="" style="width: 100%;" readonly="readonly">
				                  					</td>
				                  					<td>
				                  						<input type="text" name="soluong" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  					<td>
				                  						<input type="text" name="dongia" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  					<td>
				                  						<input type="text" name="dongiadagiam" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onchange="DinhDangTT();">
				                  					</td>
				                  				<%if(!obj.getKhoanmucDTId().equals("400000")&&!obj.getKhoanmucDTId().equals("400002")&&!obj.getKhoanmucDTId().equals("400003")){%>
				                  					<td>
				                  						<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right; " readonly="readonly" >
				                  					</td>
				                  				<%}else{ %>
				                  					<td>
				                  						<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);"  onchange="DinhDangTT();">
				                  					</td>
				                  				<%} %>
				                  				
				                  				<td align="center">
					           	 					<a href="" id="ghichu<%= m %>" rel="subcontent<%= m %>">
						           	 							<img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
						           	 				<DIV id="subcontent<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
									                             background-color: white; width: 450px; padding: 4px;">
									                    <table width="90%" align="center">
									                        <tr>
									                            <th width="100px">Ghi chú</th>
									                        </tr>
						                        			<tr>
									                            <td>
									                            	<input type="text" style="width: 100%;" name="ghichusanpham" value=""/>
									                            </td>
									                        </tr>
									                    </table>
									                     <div align="right">
									                     	<label style="color: red" ></label>
									                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= m %>')">Hoàn tất</a>
									                     </div>
									                </DIV>								               
					           	 		     </td>
				                  				</tr>
				                  			
				                  			<%  m++ ;} %>
				                  			
				                  		</table>
				               
				                  		
				                  	</TD>
				                  </TR>
			                  <%} %>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	
	<script type="text/javascript">
	replaces();
	for(var k = 0; k < 20; k++)
	{
		  dropdowncontent.init('ghichu'+k, "left-top", 300, "click");
	} 
	</script>

</form>



</BODY>
</HTML>
<%
if(obj!=null)
{
	obj.DbClose();
	session.setAttribute("obj",null);
}
}%>

