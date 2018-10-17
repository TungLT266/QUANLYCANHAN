<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.thanhtoanhoadon.*" %>
<%@ page  import = "geso.traphaco.erp.beans.thanhtoanhoadon.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpThanhtoanhoadon tthdBean = (IErpThanhtoanhoadon)session.getAttribute("tthdBean"); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet htttList = tthdBean.getHtttRs(); %>
<% ResultSet nganhangList = tthdBean.getNganhangRs(); %>
<% ResultSet chinhanhList = tthdBean.getChinhanhRs(); %>
<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

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

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpTTHoadonUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > thanh toán hóa đơn > hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpThanhtoanhoadonSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="../../ErpTTHoaDonPdfSvl?userId=<%= tthdBean.getUserId() %>&id=<%= tthdBean.getId() %>&httt=<%= tthdBean.getHtttId() %>" >
	        <IMG src="../images/Printer30.png" title="In phieu" alt="In phieu" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= tthdBean.getMsg() %></textarea>
		         <% tthdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Thanh toán hóa đơn</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Ngày ghi nhận</TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngayghinhan" name="ngayghinhan" value="<%= tthdBean.getNgayghinhan() %>" 
                    		maxlength="10" readonly /></TD>
                </TR> 
                <TR>
                    <TD class="plainlabel">Nhà cung cấp</TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="nccId" id="nccId" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(nccList != null)
                        		{
                        			try
                        			{
                        			while(nccList.next())
                        			{  
                        			if( nccList.getString("pk_seq").equals(tthdBean.getNccId())){ %>
                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nccTen") %></option>
                        			<%}else { %>
                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nccTen") %></option>
                        		 <% } } nccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                <TR>
                    <TD class="plainlabel">Hình thức thanh toán</TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="htttId" id="htttId" onChange="submitform();">
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
                        		 <% } } htttList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
           <%
             if(tthdBean.getHtttId().equals("100001"))
             { %>
                		
                <TR>
                    <TD class="plainlabel">Ngân hàng</TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="nganhangId" id="nganhangId" onchange="submitform();" >
                        	<option value=""> </option>
                        	<%
                        		if(nganhangList != null)
                        		{
                        			try
                        			{
                        			while(nganhangList.next())
                        			{  
                        			if( nganhangList.getString("pk_seq").equals(tthdBean.getNganhangId())){ %>
                        				<option value="<%= nganhangList.getString("pk_seq") %>" selected="selected" ><%= nganhangList.getString("nhTen")%></option>
                        			<%}else { %>
                        				<option value="<%= nganhangList.getString("pk_seq") %>" ><%= nganhangList.getString("nhTen") %></option>
                        		 <% } } nganhangList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                <TR>
                    <TD class="plainlabel">Chi nhánh</TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="chinhanhId" id="chinhanhId" onchange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(chinhanhList != null)
                        		{
                        			try
                        			{
                        			while(chinhanhList.next())
                        			{  
                        			if( chinhanhList.getString("pk_seq").equals(tthdBean.getChinhanhId())){ %>
                        				<option value="<%= chinhanhList.getString("pk_seq") %>" selected="selected" ><%= chinhanhList.getString("cnTen")%></option>
                        			<%}else { %>
                        				<option value="<%= chinhanhList.getString("pk_seq") %>" ><%= chinhanhList.getString("cnTen") %></option>
                        		 <% } } chinhanhList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Số tài khoản</TD>
                    <TD colspan="2" class="plainlabel">
                        <input type="text" name="sotaikhoan" value="<%= tthdBean.getSotaikhoan() %>" readonly="readonly" > 
                     </TD> 
                </TR>
            <% }%>
            	<TR>
                    <TD class="plainlabel">Nội dung thanh toán </TD>
                    <TD colspan="2" class="plainlabel">
                        <input type="text" name="noidungthanhtoan" value="<%= tthdBean.getNoidungtt() %>"> 
                     </TD> 
                </TR>
            	<TR>
                    <TD class="plainlabel">Số tiền thanh toán </TD>
                    <TD colspan="2" class="plainlabel">
                        <input type="text" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan" value="<%= tthdBean.getSotientt() %>" onkeyup="DinhDangTienTT()" onchange="PhanBoTien()" > 
                     </TD> 
                </TR>
            </TABLE>
            <hr> 
            </div>
           
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="15%">Ký hiệu HĐ</TH>
                	<TH align="center" width="15%">Số HĐ</TH>
                	<TH align="center" width="15%">Ngày HĐ</TH>
                	<TH align="center" width="20%">Tổng số tiền (đã có VAT)</TH>
               	 	<TH align="center" width="10%">Thanh toán</TH>
               	 	<TH align="center" width="15%">Còn lại</TH>
               	 	<TH align="center" width="15%">
               	 		Tất cả 
               	 		<input type="checkbox" onchange="sellectAll()" id="chkAll"  />
               	 	</TH>
                </TR>
                <%
                	for(int i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadon hoadon = hoadonList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd" readonly="readonly" >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKyhieu() %>" name= "kyhieuhd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getTongtiencoVAT() %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getThanhtoan() %>" name= "thanhtoan" id="thanhtoan<%= i %>"  onkeyup="TraTienHD(<%= i %>)"  onKeyPress = "return keypress(event);" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getConlai() %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 				<%if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="TraHetHD(<%= i %>)" >
           	 				<%}else{ %>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="TraHetHD(<%= i %>)" >
           	 				<%}%>
           	 				</td>
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div>      
     </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% 
 if (tthdBean!=null)
 {
	 tthdBean.DBclose();
 }
%>