<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpGuiSMSTDV lsxBean = (IErpGuiSMSTDV)session.getAttribute("lsxBean"); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); %>
<% ResultSet tinhthanhRs = lsxBean.getTinhthanhRs(); %>
<% ResultSet quanhuyenRs = lsxBean.getQuanhuyenRs(); %>
<% ResultSet nvbhRs = lsxBean.getNVBHRs(); %>
<% ResultSet nvgnRs = lsxBean.getNVGNRs(); %>
<% ResultSet nhanvienRs = lsxBean.getNhanvienRs(); %>
<% 
	NumberFormat formater = new DecimalFormat("##,###,###");
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

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
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function changePOform()
	 { 
		 document.forms['hctmhForm'].action.value='changePO';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function LocLaiPO()
	 {
		 document.forms['hctmhForm'].action.value= 'loclaiPO';
	     document.forms["hctmhForm"].submit();
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
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	function DienGhiChuTuDong(e, stt)
	{
		var ghichu = document.getElementById("ghichu");
		var tenkhachhang = document.getElementsByName("tenkhachhang");
		
		//alert( 'GHU CHU: ' + ghichu.value + ' -- checked: ' + e.checked );
		if( ghichu.value == '' )
		{
			if( e.checked == true )
				ghichu.value = tenkhachhang.item(stt).value;
		}
		
		var ngayvanchuyen = document.getElementById("ngayvanchuyen");
		var songayvanchuyen = document.getElementsByName("songayvanchuyen");
		
		//alert( 'GHU CHU: ' + ghichu.value + ' -- checked: ' + e.checked );
		if( ngayvanchuyen.value == '' )
		{
			if( e.checked == true )
				ngayvanchuyen.value = songayvanchuyen.item(stt).value;
		}
	}
	
	
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpGuiSMSTDVUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng > Gửi SMS TDV tỉnh > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpGuiSMSTDVSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Gửi SMS TDV tỉnh </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" >Ngày giao hàng </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>" class="days" />
                    </TD>
                    
                    <TD width="100px" class="plainlabel" >Loại đơn hàng </TD>
                    <TD class="plainlabel" >
                    	<select name="xuatchoId" onchange="submitform();"  >
								<% if (lsxBean.getXuatcho().equals("0")){%>
								  	<option value="0" selected>Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" ></option>
								<%}else if(lsxBean.getXuatcho().equals("1")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1" selected>ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" ></option>
								<%}else if(lsxBean.getXuatcho().equals("2")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" selected >OTC</option>
								  	<option value="" ></option>
								<%} else  {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2">OTC</option>
								  	<option value="" selected ></option>
							<%} %>
                           </select>
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Ngày dự kiến hàng đến </TD>
                    <TD class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"  name="tungay" value="<%= lsxBean.getTungay() %>"  />
                    </TD>
                    <TD class="plainlabel" >Ngày vận chuyển </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="denngay" id='ngayvanchuyen' value="<%= lsxBean.getDenngay() %>" />
                    </TD>
                    
                </TR>

				<TR>
                    <TD class="plainlabel" >Chành xe </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="chanhxe" value="<%= lsxBean.getChanhxe() %>"  />
                    </TD>
                    <TD class="plainlabel" >Điện thoại </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="dienthoai" value="<%= lsxBean.getDienthoai() %>"  />
                    </TD> 
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Số lượng </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="soluong" value="<%= lsxBean.getSoluong() %>"  />
                    </TD>
                    <TD class="plainlabel" >Đơn vị tính </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="donvi" value="<%= lsxBean.getDonvi() %>"  />
                    </TD> 
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" id="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Từ </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "nhanvienTuId" class="select2" style="width: 700px"  >
                    		<!-- <option value="" ></option> -->
                    		<% if( nhanvienRs != null ) { 
                    			nhanvienRs.beforeFirst(); 
                    			while( nhanvienRs.next() ) {
                    				if( lsxBean.getNhanvien_TuId().equals(nhanvienRs.getString("pk_seq")) ){ %>
                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" selected="selected" ><%= nhanvienRs.getString("diengiai") %></option>	
                    		<% 		} else { %>
                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" ><%= nhanvienRs.getString("diengiai") %></option>		
                    		<% } } } %>
                    	</select>
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Đến </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "nhanvienDenId" class="select2" style="width: 700px" >
                    		<option value="" ></option>
                    		<% if( nhanvienRs != null ) { 
                    			nhanvienRs.beforeFirst(); 
                    			while( nhanvienRs.next() ) {
                    				if( lsxBean.getNhanvien_DenId().equals(nhanvienRs.getString("pk_seq")) ){ %>
                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" selected="selected" ><%= nhanvienRs.getString("diengiai") %></option>	
                    		<% 		} else { %>
                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" ><%= nhanvienRs.getString("diengiai") %></option>		
                    		<% } } } %>
                    	</select>
                    	<br />
                    	<input type="checkbox" name="nhanvienDenQL" value="1" <%= ( lsxBean.getNhanvien_DenQL().equals("1") ? " checked = 'checked' " : "" ) %> > Gửi đến nhân viên bán hàng
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Gửi bản sao(CC) </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<table>
                    		<tr>
                    			<td>
                    				<select name = "nhanvienCCQLId" class="select2" style="width: 700px" multiple="multiple" >
			                    		<option value="" ></option>
			                    		<% if( nhanvienRs != null ) { 
			                    			nhanvienRs.beforeFirst(); 
			                    			while( nhanvienRs.next() ) {
			                    				if( lsxBean.getNhanvien_CCQLId().contains(nhanvienRs.getString("pk_seq")) ){ %>
			                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" selected="selected" ><%= nhanvienRs.getString("diengiai") %></option>	
			                    		<% 		} else { %>
			                    				<option value="<%= nhanvienRs.getString("pk_seq") %>" ><%= nhanvienRs.getString("diengiai") %></option>		
			                    		<% } } } %>
			                    	</select>
			                    	<br />
                    				<input type="checkbox" name="nhanvienCCQL" value="1" <%= ( lsxBean.getNhanvien_CCQL().equals("1") ? " checked = 'checked' " : "" ) %> > Gửi đến quản lý
                    			</td>
                    		</tr>
                    		
                    	</table>
                    </TD>
                </TR>
                
				<TR>
                	<TD class="plainlabel" >Tỉnh thành</TD>
                    <TD class="plainlabel" width="250px" >
                    	<select name = "tinhthanhId" class="select2" style="width: 200px" onchange="LocLaiPO();" >
                    		<option value=""> </option>
                        	<%
                        		if(tinhthanhRs != null)
                        		{
                        			try
                        			{
                        			while(tinhthanhRs.next())
                        			{  
                        			if( tinhthanhRs.getString("pk_seq").equals(lsxBean.getTinhthanhId())){ %>
                        				<option value="<%= tinhthanhRs.getString("pk_seq") %>" selected="selected" ><%= tinhthanhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= tinhthanhRs.getString("pk_seq") %>" ><%= tinhthanhRs.getString("ten") %></option>
                        		 <% } } tinhthanhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" >Quận huyện </TD>
                    <TD class="plainlabel" >
                    	<select name = "quanhuyenId" class="select2" style="width: 200px" onchange="LocLaiPO();" >
                        	<option value=""> </option>
                        	<%
                        		if(quanhuyenRs != null)
                        		{
                        			try
                        			{
                        			while(quanhuyenRs.next())
                        			{  
                        			if( quanhuyenRs.getString("pk_seq").equals(lsxBean.getQuanhuyenId())){ %>
                        				<option value="<%= quanhuyenRs.getString("pk_seq") %>" selected="selected" ><%= quanhuyenRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= quanhuyenRs.getString("pk_seq") %>" ><%= quanhuyenRs.getString("ten") %></option>
                        		 <% } } quanhuyenRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Trình dược viên</TD>
                    <TD class="plainlabel" width="250px" >
                    	<select name = "nvbhId" class="select2" style="width: 200px" onchange="LocLaiPO();" >
                    		<option value=""> </option>
                        	<%
                        		if(nvbhRs != null)
                        		{
                        			try
                        			{
                        			while(nvbhRs.next())
                        			{  
                        			if( nvbhRs.getString("pk_seq").equals(lsxBean.getNVBHId())){ %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("ten") %></option>
                        		 <% } } nvbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" >NV giao nhận </TD>
                    <TD class="plainlabel" >
                    	<select name = "nvgnId" class="select2" style="width: 200px" onchange="LocLaiPO();" >
                        	<option value=""> </option>
                        	<%
                        		if(nvgnRs != null)
                        		{
                        			try
                        			{
                        			while(nvgnRs.next())
                        			{  
                        			if( nvgnRs.getString("pk_seq").equals(lsxBean.getNVGNId())){ %>
                        				<option value="<%= nvgnRs.getString("pk_seq") %>" selected="selected" ><%= nvgnRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvgnRs.getString("pk_seq") %>" ><%= nvgnRs.getString("ten") %></option>
                        		 <% } } nvgnRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Mã khách hàng</TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="maFastSEARCH" value="<%= lsxBean.getMaFast() %>" onchange="LocLaiPO();" />
                    </TD>   
                    <TD class="plainlabel" >Tên khách hàng </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="tenkhachhangSEARCH" value="<%= lsxBean.getTenkhachhang() %>" onchange="LocLaiPO();" />
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Mã chứng từ</TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="machungtuSEARCH" value="<%= lsxBean.getMachungtu() %>" onchange="LocLaiPO();" />
                    </TD>   
                    <TD class="plainlabel" >Người tạo </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="nguoitaoSEARCH" value="<%= lsxBean.getNguoitaodon() %>" onchange="LocLaiPO();" />
                    </TD>         	
                </TR>

            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="7%" >STT</th>
					<th align="center" width="15%" >Đơn đặt hàng</th>
					<th align="center" width="10%" >Mã khách hàng</th>
					<th align="center" width="25%" >Khách hàng</th>
					<th align="center" width="10%" >Ngày lập</th>
					<th align="center" width="25%" >Tỉnh thành</th>
					<th align="center" width="8%" >Chọn</th>
				</tr>
				
				<%
					int count = 0;
					if(ddhRs != null)
					{
						while( ddhRs.next() )
						{
						%>
					
						<tr >
							<td >
								<input type="text" name="STT" value="<%= (count + 1) %>" style="width: 100%"   > 
							</td>
							<td> <input type="text" name="machungtu" value="<%= ddhRs.getString("machungtu") %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="makhachhang" value="<%= ddhRs.getString("maKH") %>" style="width: 100%; text-align: center;" readonly="readonly">		
								<input type="hidden" name="songayvanchuyen" value="<%= ddhRs.getString("songayvanchuyen") %>" >							
							</td>
							<td>
								<input type="text" name="tenkhachhang" value="<%= ddhRs.getString("tenKH") %>" style="width: 100%; " readonly="readonly" >							
							</td>
							<td>
								<input type="text" name="ngaylap" value="<%= ddhRs.getString("NgayDonHang") %>" style="width: 100%; " readonly="readonly">							
							</td>
							<td>
								<input type="text" name="tinhthanh" value="<%= ddhRs.getString("tinhthanh") %>" style="width: 100%; " readonly="readonly">							
							</td>
							<td align="center" >
								<input type="checkbox" name="ddhIds" value="<%= ddhRs.getString("pk_seq") %>"  <%= ( lsxBean.getDondathangId().contains( ddhRs.getString("pk_seq") ) ? " checked = 'checked' " : "" ) %> onchange="DienGhiChuTuDong(this, <%= count %>);" >							
							</td>
						</tr>	
							
					<% count ++; } ddhRs.close(); } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<%
	try
	{
		if( nhanvienRs != null )
		{
			nhanvienRs.close();
			nhanvienRs = null;
		}
		
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>