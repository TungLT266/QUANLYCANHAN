<%@page import="com.lowagie.tools.split_pdf"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.giay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.giay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpNhapkho nkBean = (IErpNhapkho)session.getAttribute("nkBean"); %>
<% List<ISanpham> spList = nkBean.getSpList();
  ResultSet rsCongdoan=nkBean.getRsCongDoan();
  ResultSet rsLenhsanxuat=nkBean.getRsLenhSanXuat();
%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
	
%>

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

	function replaces()
	{
		var sanpham = document.getElementsByName("mahangmua");
		var soluongnhap = document.getElementsByName("soluongnhap");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var solo = document.getElementsByName("solo");
		var noidungnhap = document.getElementById("noidungnhap").value;
		
		for(var i = 0; i < sanpham.length; i++)
		{
			var tongluong = 0;
			if( sanpham.item(i).value != "" )
			{
				var id = "";
				
				if(noidungnhap == "100004")
					id = sanpham.item(i).value + '.';
				else	
					id = sanpham.item(i).value + '.' + solo.item(i).value;
				
				var soluong = document.getElementsByName(id + '.soluong');
				var khuvuc = document.getElementsByName(id  + '.khuvuc');
				var vitri = document.getElementsByName(id + '.vitri');
				
				for(var j = 0; j < soluong.length; j++)
				{
					if(soluong.item(j).value != "" && khuvuc.item(j).value != "" && vitri.item(j).value != "")
					{
						var slg = soluong.item(j).value.replace(/,/g,"");
						tongluong = parseFloat(tongluong) + parseFloat(slg);
						
						var slgNhan = soluongnhan.item(i).value.replace(/,/g,"");
						if(tongluong > parseFloat(slgNhan))
						{
							tongluong = parseFloat(tongluong) - parseFloat(slg);
							soluong.item(j).value = "";
							khuvuc.item(j).value = "";
							vitri.item(j).value = "";
						}
					}
				}
			
				soluongnhap.item(i).value = tongluong;
			}
		}
		
		setTimeout(replaces, 300);
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
	 	 document.forms['nkForm'].action.value='save';
	     document.forms['nkForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['nkForm'].action.value='submit';
	     document.forms["nkForm"].submit();
	 }
	 
	function LocKhuVuc(id, value)
	{
		var stt = id.substring(id.indexOf('khuvuc') + 6, id.length)
		var vitriId = id + '.vitricon' + stt;
		
		var vitri = document.getElementById(id + '.vitricon' + stt);

		for(var i = 0; i < vitri.length; i++)
		{
			 var str = vitri.options[i].value;
			 if(str.indexOf(value) == 0)
			 {
				 vitri.selectedIndex = parseInt(i);
				 break;
			 }
		}
	}
	
	function LocViTri(id, value)
	{
		var makhuvuc = value.substring(0, value.indexOf(' - '));
		var khuvucId = id.substring(0, id.indexOf('.vitricon'));

		var khuvuc = document.getElementById(khuvucId);

		for(var i = 0; i < khuvuc.length; i++)
		{
			 var str = khuvuc.options[i].value;
			 if(str.indexOf(makhuvuc) == 0)
			 {
				 khuvuc.selectedIndex = parseInt(i);
				 break;
			 }
		}
	}
	 function changePO()
	 { 
		 document.forms['nkForm'].action.value='changePO';
	     document.forms["nkForm"].submit();
	 }
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkForm" method="post" action="../../ErpNhapkhoLsxUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= nkBean.getId() %>'>
<input type="hidden" name="task" value='update'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất > Nhập kho > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpNhapkhoLsxSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform();" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        <A href="../../ErpPhieunhapkhoPdfSvl?userId=<%= nkBean.getUserId() %>&id=<%= nkBean.getId() %>&task=nhapkho" >
	        <IMG src="../images/Printer30.png" title="In phieu nhap kho" alt="In phieu nhap kho" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= nkBean.getmsg() %></textarea>
		         <% nkBean.setmsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhập kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Số nhập kho </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="nhapkhoId" name="nhapkhoId" value="<%= nkBean.getId() %>" 
                    		maxlength="10" readonly />
                    </TD>
                </TR> 
                
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Lệnh sản xuất</TD>
                    <TD class="plainlabel" valign="top">
                    <select name=lsxId id="lsxId" >
                        	<%
                        		if(rsLenhsanxuat != null)
                        		{
                        			try
                        			{
                        			while(rsLenhsanxuat.next())
                        			{  
                        			if( rsLenhsanxuat.getString("pk_seq").equals(nkBean.getSoLenhsx()   )){ %>
                        				<option value="<%= rsLenhsanxuat.getString("pk_seq")%>" selected="selected" ><%= rsLenhsanxuat.getString("pk_seq") %></option>
                        			<%}else { %>
                        				<option value="<%= rsLenhsanxuat.getString("pk_seq") %>" ><%= rsLenhsanxuat.getString("pk_seq") %></option>
                        		 <% } } rsLenhsanxuat.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                    </TD>
                </TR>
                <tr>
                	<td class="plainlabel">Công đoạn</td>
                    <td class="plainlabel">
                    <select name=congdoanId id="congdoanId" >
                        	<%
                        		if(rsCongdoan != null)
                        		{
                        			try
                        			{
                        			while(rsCongdoan.next())
                        			{  
                        			if( rsCongdoan.getString("pk_seq").equals(nkBean.GetCongDoanId()   )){ %>
                        				<option value="<%= rsCongdoan.getString("pk_seq")%>" selected="selected" ><%= rsCongdoan.getString("diengiai") %></option>
                        			<%}else { %>
                        				<option value="<%= rsCongdoan.getString("pk_seq") %>" ><%= rsCongdoan.getString("diengiai") %></option>
                        		 <% } } rsCongdoan.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                    <input type="hidden" name="noidungnhap" value="<%=nkBean.getNdnId() %>">
                     <input type="hidden" name="khoId" value="<%=nkBean.getKhoId() %>">
                    </td>
                </tr>
            </TABLE>
            <hr> 
            </div>
           
           <div align="left" style="width:100%; float:none; clear:none;">
           <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>
	                <TH align="center" width="10%">Mã sản phẩm</TH>
	                <TH align="center" width="30%">Tên sản phẩm</TH>
               	 	<TH align="center" width="10%">Số lượng SX</TH>
               	 	<TH align="center" width="10%">Số lượng nhập</TH>
               	 	<TH align="center" width="10%">Số lô</TH>
               	 	<TH align="center" width="10%">Ngày sản xuất</TH>
               	 	<TH align="center" width="10%">Ngày nhập kho</TH>
                </TR>
                <%
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "diengiai" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongSx() %>" name= "soluongsx" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getDongia() %>" name= "dongia" >
           	 					<input type="hidden" value="<%= sp.getDongiaViet() %>" name= "dongiaViet" >
           	 					<input type="hidden" value="<%= sp.getTiente() %>" name= "tiente" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "sanphamId" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongnhapkho() %>" name= "soluongnhap" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= sp.getSolo() %>" name= "solo" >
           	 					<input type="hidden" style="width: 100%; text-align: center;" value="<%= sp.getNgayhethan() %>" name= "ngayhethan" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getNgaySanXuat() %>" name= "ngaysanxuat" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getNgayNhapKho() %>" name= "ngaynhapkho" >
           	 				</td>
           	 			</tr>
                	<% } }%>   
            </TABLE> 
        </div>      
     </fieldset>	
    </div>
</div>
</form>
<%
try{
	nkBean.DBclose();
	nkBean=null;
	session.setAttribute("nkBean", null) ; 
	if (spList != null)
		spList.clear(); 
}catch (Exception ex)
{
	ex.printStackTrace();
}
%>

</BODY>
</html>