<%@page import="geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEO"%>
<%@page import="geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEO"%>
<%@page import="geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEODetail"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IDinhTinhEO obj = (IDinhTinhEO)session.getAttribute("obj"); %>
<% String  action = (String)session.getAttribute("action"); %>
<% ResultSet RsKho = obj.getRsKho(); %>
<% ResultSet RsSanPham = obj.getRsSanPham(); %>
<% List<DinhTinhEODetail> list = obj.getListDetail(); %>

<% NumberFormat formater = new DecimalFormat("##,###,###.##"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	   NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
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
</style>

<style>
	.tbdetail td{
		font-size: 12px;
		font-family: Arial, Helvetica, sans-serif;
		border: 1px solid #ddd;
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
<script type="text/javascript" src="../scripts/erp_splist_yeucauchuyenkho.js"></script>
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
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='getProduct';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function changeKHO()
	 {
		document.forms["hctmhForm"].action.value = "changeKHO";
	    document.forms["hctmhForm"].submit();
	 }
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();});
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpDinhTinhEOUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%=obj.getId() %>'>
<input type="hidden" name="action" value='<%= action %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Định tính/EO > <%= ( obj.getId().length() >0 ? "Cập nhật" : "Tạo mới" ) %> 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDinhTinhEOSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= obj.getMsg() %></textarea>
		    
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Định tính/EO </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            	<TR>
                    <TD style="width: 140px;" class="plainlabel" valign="top"> Ngày chứng từ </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top" colspan= "3" >
                    	<input type="text"  id="ngaychungtu" name="ngaychungtu" class="days" value="<%= obj.getNgayChungTu() %>"/>
                    </TD>
                     
                </TR>	
            	<TR>
                    <TD class="plainlabel" width="10%">Loại</TD>
                    <TD class="plainlabel" width="200px"  >
                            <select name="loai"  style="width: 200px" class="select2">
                           	<option value="">  </option>
                   		    <%  
 								String[] mang1 = new String[]{"0", "1" };
 								String[] mangten1 = new String[]{"Định tính", "EO"};
 								 for(int j=0;j<mang1.length;j++){
 									 if(obj.getLoai().trim().equals(mang1[j])){
 									 %>
 									 <option selected="selected" value="<%=mang1[j]%>"> <%=mangten1[j] %> </option>
 									 <%
 									 }else{
 									 %>
 									 <option value="<%=mang1[j]%>"> <%=mangten1[j] %> </option>
 									 <% 
 									 }
 								 }
 								%>	
                           </select>
                     </TD> 
                     <TD class="plainlabel" width="10%">Kho </TD>
                     <TD class="plainlabel" width="200px" >
                            <select name="khoId" id="khoId"  style="width: 200px" class="select2">
                        	<option value=""> </option>
                        	<%
                        		if(RsKho != null)
                        		{
                        			try
                        			{
                        			while(RsKho.next())
                        			{  
                        			if( RsKho.getString("pk_seq").equals(obj.getKhoId())){ %>
                        				<option value="<%= RsKho.getString("pk_seq") %>" selected="selected" ><%= RsKho.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= RsKho.getString("pk_seq") %>" ><%= RsKho.getString("ten") %></option>
                        		 <% } } RsKho.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                     </TD>
                </TR>						
                <TR>
                	
                    <TD class="plainlabel" width="10%"> Sản phẩm </TD>
                    <TD class="plainlabel" width="200px" >
                            <select name="sanpham" id="sanpham"  style="width: 200px" multiple="multiple" class="select2" disabled >
                        	<option value=""> </option>
                        	<%
                        		if(RsSanPham != null)
                        		{
                        			try
                        			{
                        			while(RsSanPham.next())
                        			{  
                        			if( obj.getSanPham().contains(RsSanPham.getString("pk_seq")) ){ %>
                        				<option value="<%= RsSanPham.getString("pk_seq") %>" selected="selected" ><%= RsSanPham.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= RsSanPham.getString("pk_seq") %>" ><%= RsSanPham.getString("ten") %></option>
                        		 <% } } RsSanPham.close();} catch(SQLException ex){ ex.printStackTrace();}
                        		}
                        	%>
                          </select>
                     </TD>
                    <TD style="width: 140px;" class="plainlabel" valign="top"> Diễn giải </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top"  >
                    	<input type="text"  id="diengiai" name="diengiai" value="<%= obj.getDienGiai() %>"/>
                    </TD>
                     
                </TR>
                
                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%" class="tbdetail">
			<tr class="tbheader">
				<th align="center" width="3%">STT</th>
				<th align="center" width="13%">Tên sản phẩm</th>
				<th align="center" width="7%"> Số lô</th>
				<th align="center" width="7%"> Mã mẻ</th>
				<th align="center" width="7%"> Mã thùng</th>
				<th align="center" width="7%"> Vị trí </th>
				<th align="center" width="7%"> Mã phiếu</th>
				<th align="center" width="7%"> Ngày hết hạn</th>
				<th align="center" width="7%"> Ngày nhập kho</th>
				<th align="center" width="7%"> Hàm lượng </th>
				<th align="center" width="7%"> Hàm ẩm </th>
				<th align="center" width="7%"> Nhà sản xuất </th>
				<th align="center" width="6%"> Mã RQ</th>
				<th align="center" width="6%"> Phiếu EO</th>
				<th align="center" width="6%"> Phiếu định tính</th>
				<% if(obj.getLoai().equals("1")){ %>
				
				<th align="center" width="6%"> Phiếu EO (mới)</th>
				<% }else if(obj.getLoai().equals("0")){ %>
				
				<th align="center" width="6%"> Phiếu định tính(mới)</th>
				<%} %>
			</tr>
			<% if(list.size() >0){ 
				for(int i=0; i< list.size(); i++){
					DinhTinhEODetail detail = list.get(i);	
			%> 
					<tr>
						<td> <%= i+1 %>  
							<input type="hidden" value="<%=detail.getSanPham_fk() %>" name="sanpham_fk" >
							<input type="hidden" value="<%=detail.getSoLo() %>" name="solo" >
							<input type="hidden" value="<%=detail.getMaMe() %>" name="mame" >
							<input type="hidden" value="<%=detail.getMaThung() %>" name="mathung" >
							<input type="hidden" value="<%=detail.getBin_fk() %>" name="bin_fk" >
							<input type="hidden" value="<%=detail.getNgaySanXuat() %>" name="ngaysanxuat" >
							<input type="hidden" value="<%=detail.getNgayHetHan() %>" name="ngayhethan" >
							<input type="hidden" value="<%=detail.getNgayNhapKho() %>" name="ngaynhapkho" >
							<input type="hidden" value="<%=detail.getHamAm() %>" name="hamam" >
							<input type="hidden" value="<%=detail.getHamLuong() %>" name="hamluong" >
							<input type="hidden" value="<%=detail.getMaRQ() %>" name="marq" >
							<input type="hidden" value="<%=detail.getKhott_fk() %>" name="khott_fk" >
							<input type="hidden" value="<%=detail.getNgayBatDau() %>" name="ngaybatdau" >
							<input type="hidden" value="<%=detail.getMaMe() %>" name="maphieu" >
							<input type="hidden" value="<%=detail.getNgayBatDau() %>" name="maphieueo" >
							<input type="hidden" value="<%=detail.getMaMe() %>" name="maphieudinhtinh" >
							<input type="hidden" value="<%=detail.getKhoChiTiet_fk() %>" name="khochitiet_fk" >
							<input type="hidden" value="<%=detail.getMaPhieu() %>" name="maphieu" >
							<input type="hidden" value="<%=detail.getNsxMa() %>" name="nsxma" >
							<input type="hidden" value="<%=detail.getNsx_fk() %>" name="nsx_fk" >
							
						</td>
						<td> <%= detail.getMaSanPham() +"-"+ detail.getTenSanPham() %> </td>
						<td> <%= detail.getSoLo() %> </td>
						<td> <%= detail.getMaMe() %> </td>
						<td> <%= detail.getMaThung() %></td>
						<td> <%= detail.getBin_fk() %></td>
						<td> <%= detail.getMaPhieu() %> </td>
						<td> <%= detail.getNgayHetHan() %> </td>
						<td> <%= detail.getNgayNhapKho() %> </td>
						<td> <%= detail.getHamLuong() %> </td>
						<td> <%= detail.getHamAm() %> </td>
						<td> <%= detail.getNsxMa() %> </td>
						<td> <%= detail.getMaRQ() %> </td>
						<td> <%= detail.getMaPhieuEO() %> </td>
						<td> <%= detail.getMaPhieuDinhTinh() %> </td>
						
						<% if(obj.getLoai().equals("1")){ %>
						
						<td> <input style=" width: 80px;" type ="text" value="<%= detail.getMaPhieuEONew() %>" name="maphieueoNew"  /></td>
						<%} else if(obj.getLoai().equals("0")) { %>
						
					    <td> <input style=" width: 80px;" type ="text" value="<%= detail.getMaPhieuDinhTinhNew() %>"  name="maphieudinhtinhNew" /></td>	
					   	<%} %>		
					</tr>
			<%}}%>	
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<%
	try
	{
		obj.DBclose(); 
		
		if( RsKho != null )
			RsKho.close();
		if( RsSanPham != null )
			RsSanPham.close();
	}
	catch(Exception er)
	{
		er.printStackTrace();
	}
	finally{
		session.setAttribute("obj", null); 
	}
	} %>
</form>
</BODY>
</html>