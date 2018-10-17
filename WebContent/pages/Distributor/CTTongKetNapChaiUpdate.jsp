<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchai"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.dangkytrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>
<% ICttongketnapchai obj = (ICttongketnapchai)session.getAttribute("obj"); %>
<% ResultSet khRs = (ResultSet)obj.getKhList(); %>
<% ResultSet spRs = (ResultSet)obj.getSpRS(); %>
<% ResultSet nvbhRs = (ResultSet)obj.getNvBanhang(); %>
<% Hashtable<String, String> hbkh = (Hashtable<String, String>)obj.getKhSoluonHtb(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
  	<link href="../css/select2.css" rel="stylesheet" />
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
			
		});
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
    <script type="text/javascript">
	    function saveform()
		{  
		    document.forms['dktbForm'].action.value='save';
		    document.forms['dktbForm'].submit();
		}
	    
		function submitform()
		{
			document.forms['dktbForm'].action.value='submit';
		    document.forms['dktbForm'].submit();
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
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dktbForm" method="post" action="../../CttongketnapchaiUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="id" value='<%= obj.getId() %>'>
<input type="hidden" name="action" value='1'>

   <div id="main" style="width:99%; padding-left:2px">
	 <div align="left" id="header" style="width:100%; float:none">
    	  <div style="width:40%; padding:5px; float:left" class="tbnavigation">
        	Quản lý khuyến mãi > Khai báo > Chuong trình tổng kết nắp chai > Tạo mới
          </div>
          <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= userTen %> &nbsp;
         </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../DangkykhuyenmaitichluySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        	
        	<A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        	
    </div>
  	<div align="left" style="width:100%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; font-weight:bold" cols="71" rows="1"  style="width: 100% " readonly="readonly" ><%= obj.getMessage() %></textarea>
           
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle"> Đăng ký khuyến mãi tích lũy </legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
            <tr>
	            <td class="plainlabel">
	            	Mã chương trình
	            </td>
	            <td class="plainlabel" >
	            	<input type="text" id="ma" name = "ma" value="<%= obj.getMa() %>"  > 
	            </td>
	            <td class="plainlabel">
	            	Diễn giải
	            </td>
	            <td class="plainlabel" >
	            	<input type="text" id="diengiai" name = "diengiai" value="<%= obj.getDiengiai() %>" > 
	            </td>
            </tr>
            <tr>
	            <td class="plainlabel">
	            	Từ ngày
	            </td>
	            <td class="plainlabel" >
	            	<input type="text" class="days" id="tungay" name = "tungay" value="<%= obj.getTungay() %>"  onchange="submitform()" > 
	            </td>
	            <td class="plainlabel">
	            	Đến ngày
	            </td>
	            <td class="plainlabel" >
	            	<input type="text" class="days" id="denngay" name = "denngay" value="<%= obj.getDenngay() %>" onchange="submitform()" > 
	            </td>
            </tr>
            <tr>
            	<td class="plainlabel">
	            	Số lượng nắp chai
	            </td>
	            <td class="plainlabel" colspan=3>
	            	<input type="text" id="soluong" name = "soluong" value="<%= obj.getSoluong() %>" onkeypress="return keypress(event);"> 
	            </td>
            </tr>
            <TR >
            	<TD class="plainlabel">Nhân viên bán hàng </TD>
            	<TD class="plainlabel">
            		<select class="select2" name="nvbhId" id="nvbhId" onchange="submitform()" style="width: 200px;">
            			<option value="" ></option>
            			<%
            				if(nvbhRs != null)
            				{
            					while(nvbhRs.next())
            					{
            						if(nvbhRs.getString("pk_seq").equals(obj.getNvbhIds()))
            						{ %>
            							<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("TEN") %></option>
            						<% } else { %>
            							<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("TEN") %></option>
            						<% } 	
            					}
            					nvbhRs.close();
            				}
            			%>
            		</select>
            	</TD>
            	<TD class="plainlabel">Sản phẩm </TD>
            	<TD class="plainlabel">
            		<select class="select2" name="spId" id="spId" onchange="submitform()" style="width: 200px;">
            			<option value="" ></option>
            			<%
            				if(spRs != null)
            				{
            					while(spRs.next())
            					{
            						if(spRs.getString("pk_seq").equals(obj.getSpId()))
            						{ %>
            							<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("TEN") %></option>
            						<% } else { %>
            							<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("TEN") %></option>
            						<% } 	
            					}
            					spRs.close();
            				}
            			%>
            		</select>
            	</TD>
            </TR>
                     				
        </TABLE>
        
        <hr />
        <table style="width: 100%" cellpadding="0" cellspacing="1">
        	<Tr class="tbheader">
        		<td width="20%" >Mã khách hàng</td>
        		<td width="35%" >Tên khách hàng</td>
        		<td width="35%" >Địa chỉ</td>
        		<td align="center" >Số lượng </td>
        	</Tr>
        	<% if(khRs != null)
        	{ 
        		while(khRs.next())
        		{
        			%>
        			
        			<TR>
        				<td>
        					<input type="text" value="<%= khRs.getString("MA") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("TEN") %>" style="width: 100%" readonly="readonly" >  
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("DIACHI") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td align="center">
               				<%	String soluong = hbkh.get(khRs.getString("pk_seq")) != null ? hbkh.get(khRs.getString("pk_seq")) : "0"; %>
           					<input type="text" name="Soluong" value="<%=soluong %>" readonly="readonly">
           					<input type="hidden" name="khIds" value="<%=khRs.getString("pk_seq") %>" >
						</td>
        			</TR>
        			
        		<%  }
        		khRs.close();
        	} %>
        </table>
        
        </div>
	</fieldset>
 </div>
 </div>
        
    
</form>
</BODY>
</html>
<% 	
if(obj != null){
	obj.DBclose();
	obj = null;
}
	try{
		if(khRs != null)
			khRs.close();
		
		if(nvbhRs != null)
			nvbhRs.close();
	}
	catch (SQLException e) {}

%>
<%}%>

    

    