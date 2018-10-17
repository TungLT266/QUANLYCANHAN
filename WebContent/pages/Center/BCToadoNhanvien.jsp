<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.bctoadonhanvien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IBCToadoNhanvien obj = (IBCToadoNhanvien)session.getAttribute("obj"); %>
<% ResultSet nvRs = obj.getNhanvienRs(); %>
<% ResultSet vung = obj.getVungRs(); %>
<% ResultSet ttRs = obj.getttRs(); %>
<% ResultSet npp = obj.getNppRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Diageo/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("BCToadoNhanvienSvl","22",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
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
		 function search()
		 {   
			 
			 document.forms['ckParkForm'].action.value= 'submit';
		    document.forms["ckParkForm"].submit();
		    
		 }
		 
		 function xuatExcel()
		 {
		 	document.forms['ckParkForm'].action.value= 'excel';
		 	document.forms['ckParkForm'].submit();
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
</head>
<body>
<form name="ckParkForm" method="post" action="../../BCToadoNhanvienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Báo cáo quản trị > Báo cáo khác > Tọa độ nhân viên
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
    
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="100px">Từ ngày</TD>
                        <TD class="plainlabel" width="300px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10"  />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10"  />
                        </TD>
                    </TR>
                    <TR>
                    	<TD class="plainlabel">Miền</TD>
						<TD class="plainlabel" >
							<select name="vungId" id="vungId" onchange="search();"  style="width:250px">
								<option value="" selected>All</option>
								<%if (vung != null)
										while (vung.next()) {
											if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
										<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
									<%} else {%>
										<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
								<%}}%>
							</select>
						</TD>
						<TD class="plainlabel">Địa bàn  </TD>
						<TD class="plainlabel">
							<select name="ttId" id="ttId" style="width:250px" onchange="search();" >
								<option value="" >All</option>
								<%if (ttRs != null)
										while (ttRs.next()) {
											if (ttRs.getString("pk_seq").equals(obj.getTtId()  )) {%>
								   <option value="<%=ttRs.getString("pk_seq")%>" selected><%=ttRs.getString("ten")%></option>
								   <%} else {%>
								   <option value="<%=ttRs.getString("pk_seq")%>"><%=ttRs.getString("ten")%></option>
								<%}}%>
							</select>
						</TD>
					</TR>
					<TR>
					    <TD class="plainlabel">Chi nhánh / Đối tác </TD>
					      <TD class="plainlabel">
					      	<SELECT name="nppId" id="nppId" style="width:250px" onchange="search();">	
					      	<option value="">All</option>							      
					  	 	<%if(npp != null) try{ while(npp.next()){ 
					    	if(npp.getString("pk_seq").trim().equals(obj.getNppId())){ %>
					      		<option value='<%=npp.getString("pk_seq") %>' selected><%=npp.getString("ten") %></option>
					      	<%}else{ %>
					     		<option value='<%=npp.getString("pk_seq") %>'><%=npp.getString("ten") %></option>
					     	<%}}}catch(java.sql.SQLException e){} %>	
                               </select>
				  		</TD>
				  		<TD class="plainlabel"></TD>
						<TD class="plainlabel"></TD>
					</TR>	
					
 					<TR>
 						<TD class="plainlabel">Loại NV</TD>
						<TD class="plainlabel"><select name="loai"  style="width:250px"
							id="loai" onchange="search();">
								<option value=""
									<%=obj.getLoai().equals("0") ? " selected " : ""%>></option>
								<option value="1"
									<%=obj.getLoai().equals("1") ? " selected " : ""%>>Giám Đốc Miền</option>
								<option value="2"
									<%=obj.getLoai().equals("2") ? " selected " : ""%>>Phụ trách Vùng</option>
								<option value="3"
									<%=obj.getLoai().equals("3") ? " selected " : ""%>>Phụ Trách Tỉnh/ GĐCN Cấp 2</option>																			
							</select>
						</TD>
 						<TD class="plainlabel">Nhân viên</TD>
						<TD class="plainlabel">
							<SELECT name="nhanvienId"  style="width:250px">
								<option value=""></option>
								<%if(nvRs != null)
									try {
											while (nvRs.next()) {
												if (obj.getNhanvienId().equals(nvRs.getString("PK_SEQ"))) {
								%>
								<option value="<%=nvRs.getString("PK_SEQ")%>" selected><%=nvRs.getString("TEN")%></option>
								<%
									} else {
								%>
								<option value="<%=nvRs.getString("PK_SEQ")%>"><%=nvRs.getString("TEN")%></option>
								<%
									}
											}
										} catch (java.sql.SQLException e) {
										}
								%>
							</SELECT>
						</TD>
 					</TR>
                   
                    <TR>
						<TD class="plainlabel" colspan="4">
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Xuất báo cáo </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
			
                </TABLE>                      
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html><%
obj.DBclose();
}%>