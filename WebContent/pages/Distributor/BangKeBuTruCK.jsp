<%@page import="java.sql.SQLException"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.inchietkhautruckthang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IInKhauTruCKThang obj = (IInKhauTruCKThang)session.getAttribute("obj"); %>

<% ResultSet ddkdRs = obj.getDdkdRs();%>
<% ResultSet nvgnRs = obj.getNvgnRs();%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("InKhauTruCKThangSvl","",userId);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Traphaco - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
   
    <SCRIPT language="javascript" type="text/javascript">
		 function submitform()
		 {   
			document.forms['ckParkForm'].action.value= 'search';
		    document.forms["ckParkForm"].submit();
		 }
		 
		 function xuatExcel()
		 {
			 if (document.forms["ckParkForm"]["tuNgay"].value.length == 0) 
			 {
				setErrors("Vui lòng chọn từ ngày");					
				return;
			 }
			 
			 if (document.forms["ckParkForm"]["denNgay"].value.length == 0)
			 {
				setErrors("Vui lòng chọn đền ngày");
				return;
			 } 		
			 
		 	 document.forms['ckParkForm'].action.value= 'excel_butruCK';
		 	 document.forms['ckParkForm'].submit();
		 }
		 
	</SCRIPT>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../InKhauTruCKThangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="view" value='NPP'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng OTC > Bảng kê biên bản bù trừ công nợ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<tr>
			<TD align="left" colspan="4" class="legendtitle">
				<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
							<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
						<%= obj.getMsg() %>
						</textarea>
				</FIELDSET>
			</TD>
		</tr>
  	</div>
    
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                                             
                        <TD width="120px" class="plainlabel" >Từ ngày</TD>
					  	<TD width="250px" class="plainlabel" >
							<input type="text" class="days" name="tuNgay" size="20" value = "<%=obj.getTungay()%>" >
						</TD>
                 
                     	<TD width="120px" class="plainlabel" >Đến ngày </TD>
					  	<TD class="plainlabel" >
							<input type="text"  class="days" name="denNgay" size="20" value = "<%=obj.getDenngay()%>" >	
						</TD>

                    </TR>
                    
                    <TR>										
							<TD class="plainlabel">Trình dược viên</TD>
							<TD class="plainlabel" ><select name="ddkdId" id="ddkdId" style="width:202px" >
									<option value="" selected >All</option>
									<% if(ddkdRs != null)
									{
										try
	                        			{	
									   while(ddkdRs.next()){
									   if(ddkdRs.getString("pk_seq").equals(obj.getDdkdId() ))
									   { %>
									   <option value="<%= ddkdRs.getString("pk_seq") %>" selected><%=ddkdRs.getString("ten") %></option>
									   <%}else { %>
									   <option value="<%= ddkdRs.getString("pk_seq") %>"><%=ddkdRs.getString("ten") %></option>
									<%} }ddkdRs.close();} catch(SQLException ex){}%>
									<%} %>
							</select></TD>	
							<TD class="plainlabel">Nhân viên giao nhận</TD>
							<TD class="plainlabel" ><select name="nvgnId" id="nvgnId" style="width:202px"  >
									<option value="" selected >All</option>
									<% if(nvgnRs != null)
									{
										try
	                        			{	
									   while(nvgnRs.next()){
									   if(nvgnRs.getString("pk_seq").equals(obj.getNvgnId()))
									   { %>
									   <option value="<%= nvgnRs.getString("pk_seq") %>" selected><%=nvgnRs.getString("ten") %></option>
									   <%}else { %>
									   <option value="<%= nvgnRs.getString("pk_seq") %>"><%=nvgnRs.getString("ten") %></option>
									<%} }nvgnRs.close();} catch(SQLException ex){}%>
									<%} %>
							</select></TD>	
									
					</TR>                                       
                         
                   <TR>                   
                        <TD width="120px" class="plainlabel" >Xem theo</TD>
					  	<TD class="plainlabel" colspan="3" >
							<select name="xemtheo" style="width: 200px;" >
								<% if(obj.getXemtheo().equals("0")) { %>
									<option value="0" selected="selected" >Hóa đơn</option>
								<% } else { %>
									<option value="0" >Hóa đơn</option>
								<% } %>
								<% if(obj.getXemtheo().equals("1")) { %>
									<option value="1" selected="selected" >Biên bản bù trừ</option>
								<% } else { %>
									<option value="1" >Biên bản bù trừ</option>
								<% } %>
							</select>
						</TD>
                    </TR>      
                                       		
                    <TR>
						<TD class="plainlabel" colspan="4">
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
			
                </TABLE>                      
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html><%}%>