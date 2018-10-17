<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy"%>
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
<% IDangkykhuyenmaitichluy obj = (IDangkykhuyenmaitichluy)session.getAttribute("obj"); %>
<% ResultSet ctkmIds = (ResultSet)obj.getCtkmRs(); %>
<% ResultSet khRs = (ResultSet)obj.getKhList(); %>
<% ResultSet nvbhRs = (ResultSet)obj.getNvBanhang(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
  
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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
<form name="dktbForm" method="post" action="../../TratichluyUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="id" value='<%= obj.getId() %>'>
<input type="hidden" name="action" value='1'>

   <div id="main" style="width:99%; padding-left:2px">
	 <div align="left" id="header" style="width:100%; float:none">
    	  <div style="width:40%; padding:5px; float:left" class="tbnavigation">
        	Quản lý KM, trưng bày > Khuyến mại > Duyệt trả khuyến mãi tích lũy > Hiển thị 
          </div>
          <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= obj.getNppTen() %> &nbsp;
         </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../TratichluySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        	
    </div>
  	<div align="left" style="width:100%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; font-weight:bold" cols="71" rows="1"  style="width: 100% " readonly="readonly" ><%= obj.getMessage() %></textarea>
           
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle"> Duyệt trả khuyến mãi tích lũy </legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="130px">Chương trình </TD>
                <TD class="plainlabel" width="250px" >
                    <select name="ctkmId" id="ctkmId"  >
                       <option ></option>
                            <% if(ctkmIds != null){
					  		try{ while(ctkmIds.next()){ 
		    					if(ctkmIds.getString("pk_seq").equals(obj.getctkmId())){ %>
		      					<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme") %></option>
		      				<%}else{ %>
		      				<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme") %></option>
		      				<%}} ctkmIds.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
                <TD class="plainlabel" width="120px">Mức đăng ký</TD>
				<TD class="plainlabel" >
					<select name="mucdangky"  >
						<% if(obj.getMucdangky().equals("0")) { %>
							<option value="0" selected="selected" >Mức 1</option>
						<% } else { %>
							<option value="0" >Mức 1</option>
						<% } %>
						<% if(obj.getMucdangky().equals("1")) { %>
							<option value="1" selected="selected" >Mức 2</option>
						<% } else { %>
							<option value="1" >Mức 2</option>
						<% } %>
						<% if(obj.getMucdangky().equals("2")) { %>
							<option value="2" selected="selected" >Mức 3</option>
						<% } else { %>
							<option value="2" >Mức 3</option>
						<% } %>
						<% if(obj.getMucdangky().equals("3")) { %>
							<option value="3" selected="selected" >Mức 4</option>
						<% } else { %>
							<option value="3" >Mức 4</option>
						<% } %>
						<% if(obj.getMucdangky().equals("4")) { %>
							<option value="4" selected="selected" >Mức 5</option>
						<% } else { %>
							<option value="4" >Mức 5</option>
						<% } %>
					</select>
				</TD>
            </TR>	
             							
            <TR>
                <TD class="plainlabel">Từ ngày </TD>
                <TD class="plainlabel" colspan="3" >
                    <input type="text" value="<%= obj.getTungay() %>" readonly="readonly" />
                   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" value="<%= obj.getDenngay() %>" readonly="readonly" />
                </TD>
            </TR> 
            
            <TR >
            	<TD class="plainlabel">Nhân viên bán hàng </TD>
            	<TD class="plainlabel" colspan="3" >
            		<select name="nvbhId"  >
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
            </TR>
            
            
                     				
        </TABLE>
        
        <hr />
        <table style="width: 100%" cellpadding="0" cellspacing="1">
        	<Tr class="tbheader">
        		<td width="10%" >Mã khách hàng</td>
        		<td width="15%" >Tên khách hàng</td>
        		<td width="15%" >Địa chỉ</td>
        		<td width="10%" >Số hợp đồng</td>
        		<td width="10%" >Ngày ký HĐ</td>
        		<td width="10%" >Kết thúc HĐ</td>
        		<td width="10%" >Doanh số ĐK</td>
        		<td width="10%" >Doanh số đạt</td>
        		<td width="10%" >Hình thúc trả</td>
        	</Tr>
        	
        	<%
        		NumberFormat formater = new DecimalFormat("#,###,###");
        		String[] khMa = obj.getKhMa();
	        	String[] khTen = obj.getKhTen();
	        	String[] khDiachi = obj.getKhDiachi();
	        	String[] khSohopdong = obj.getKhSohopdong();
	        	String[] khTungay = obj.getKhTungay();
	        	String[] khDenngay = obj.getKhDenngay();
	        	String[] khDoanhso = obj.getKhDoanhso();
	        	//String[] khDoanhsoDAT = obj.getKhDoanhsoDAT();
	        	
	        	Hashtable<String, String> doanhsoDAT = obj.getDoanhsoDat_Upload();
	        	String[] khHinhthucTRA = obj.getKhHinhthuctra();
	        	
	        	if( khMa != null ) 
	        	{ 
	        		for( int i = 0; i < khMa.length; i++ )
	        		{ %>
	        		
	        		<tr>
	        			<td>
        					<input type="text" name="khMa" value="<%= khMa[i] %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" name="khTen" value="<%= khTen[i] %>" style="width: 100%" readonly="readonly" >  
        				</td>
        				<td>
        					<input type="text" name="khDiachi" value="<%= khDiachi[i] %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" name="khSohopdong" value="<%= khSohopdong[i] %>" style="width: 100%"  > 
        				</td>
        				<td>
        					<input type="text" name="khTungay" value="<%= khTungay[i] %>" style="width: 100%; text-align: center;" readonly="readonly" class="days" > 
        				</td>
        				<td>
        					<input type="text" name="khDenngay" value="<%= khDenngay[i] %>" style="width: 100%; text-align: center;" readonly="readonly" class="days" >  
        				</td>
        				<td>
        					<input type="text" name="khDoanhso" value="<%= khDoanhso[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" name="khDoanhsoDAT" value="<%= doanhsoDAT.get( khMa[i] ) == null ? "0" : formater.format( Double.parseDouble( doanhsoDAT.get( khMa[i] ) ) ) %>" style="width: 100%; text-align: right;" readonly="readonly" > 
        				</td>
        				<td>
        					<select name="khHinhthuctra" style="width: 100%" >
        					<% if( khHinhthucTRA[i].equals("0") ) { %>
	        						<option value="0" selected="selected" >Đơn hàng</option>
	        					<% } else { %>
	        						<option value="0" >Đơn hàng</option>
	        					<% } %>
	        					<% if( khHinhthucTRA[i].equals("1") ) { %>
	        						<option value="1" selected="selected" >Trả tiền</option>
	        					<% } else { %>
	        						<option value="1" >Trả tiền</option>
	        					<% } %>
        					</select>
        				</td>
	        		</tr>
	        			
	        <% } }	 %>
        	
        	<%-- <% if(khRs != null)
        	{ 
        		while(khRs.next())
        		{
        			%>
        			
        			<TR>
        				<td>
        					<input type="text" value="<%= khRs.getString("SMARTID") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("TEN") %>" style="width: 100%" readonly="readonly" >  
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("DIACHI") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td align="center" >
        					<% if(obj.getKhId().contains(khRs.getString("PK_SEQ"))) { %> 
        						<input type="checkbox" name="khIds" value="<%= khRs.getString("PK_SEQ")  %>"  checked="checked" >
        					<% } else { %> 
        						<input type="checkbox" name="khIds" value="<%= khRs.getString("PK_SEQ")  %>"  >
        					<%  } %>
        				</td>
        			</TR>
        			
        		<%  }
        		khRs.close();
        	} %> --%>
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
		if(ctkmIds != null)
			ctkmIds.close();
	}
	catch (SQLException e) {}

%>
<%}%>

    