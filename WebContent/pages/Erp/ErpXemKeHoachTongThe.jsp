<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpKiemtrakehoach obj =(IErpKiemtrakehoach)session.getAttribute("xkhBean");
	ResultSet spRs = obj.getSpRs();
	
	ResultSet thangRs = obj.getThangRs();
	ResultSet namRs = obj.getNamRs();
	ResultSet chungloaiRs = obj.getChungloaiRs();
	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

	
<script>

	$(function() {
	 // setup ul.tabs to work as tabs for each div directly under div.panes
	 	$("ul.tabs").tabs("div.panes > div");

	});
	
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#spId").select2(); });
     
 </script>



<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
		
	    document.forms["ktrakh"].submit();
	}
	
	function Loaisp()
	{
		document.forms["ktrakh"].spId.value = '';
		document.forms["ktrakh"].submit();
		
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
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ktrakh" method="post" action="../../ErpXemkehoachUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Kiểm tra kế hoạch</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></TR>
						</TABLE>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Kiểm tra kế hoạch </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                           <TR style="display:none;">
                           
		                        <TD class="plainlabel" valign="middle" width = "10%">Tháng </TD>
		                        <TD class="plainlabel" valign="middle" width = "10%" >
		                            <select name="thang" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(thangRs != null)
			                        		{
			                        			while(thangRs.next())
			                        			{  
			                        			if( thangRs.getString("thang").equals(obj.getThang())){ %>
			                        				<option value="<%= thangRs.getString("thang") %>" selected="selected" ><%= thangRs.getString("thangTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= thangRs.getString("thang") %>" ><%= thangRs.getString("thangTen") %></option>
			                        		 <% } } thangRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                        <TD class="plainlabel" valign="middle" width="5%" >Năm </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name="nam" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(namRs != null)
			                        		{
			                        			while(namRs.next())
			                        			{  
			                        			if( namRs.getString("nam").equals(obj.getNam())){ %>
			                        				<option value="<%= namRs.getString("nam") %>" selected="selected" ><%= namRs.getString("namTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= namRs.getString("nam") %>" ><%= namRs.getString("namTen") %></option>
			                        		 <% } } namRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>
		                  
                           <TR>     
                           
		                        <TD class="plainlabel" valign="middle" width = "10%">Loại sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" width="50%" colspan = "3">
		                            <select name="loaiId" onchange="Loaisp();" >
		                            <option value="" ></option>
				<%if (obj.getLoaiId().equals("100002")){ %>		                            
		                            	<option value="100002" selected>Thành phẩm</option>
		                            	<option value="100003" >Hàng hóa thương mại</option>
		                            	<option value="100000">Nguyên liệu </option>
		                            	<option value="100008">Vật liệu phụ </option>
		                            	
		        <%}else if(obj.getLoaiId().equals("100003")){ %>
		                            	<option value="100002" >Thành phẩm</option>
		                            	<option value="100003" selected >Hàng hóa thương mại</option>
		                            	<option value="100000">Nguyên liệu </option>
		                            	<option value="100008">Vật liệu phụ </option>
		                            	
				<%}else if(obj.getLoaiId().equals("100000")){ %>
		                            	<option value="100002" >Thành phẩm</option>
		                            	<option value="100003" >Hàng hóa thương mại</option>
		                            	<option value="100000" selected >Nguyên liệu </option>
		                            	<option value="100008">Vật liệu phụ </option>
		                            	
				<%}else if(obj.getLoaiId().equals("100008")){ %>
		                            	<option value="100002" >Thành phẩm</option>
		                            	<option value="100003" >Hàng hóa thương mại</option>
		                            	<option value="100000" >Nguyên liệu </option>
		                            	<option value="100008" selected >Vật liệu phụ </option>
				<%}else{ %>		
		                            	<option value="100002" >Thành phẩm</option>
		                            	<option value="100003" >Hàng hóa thương mại</option>
		                            	<option value="100000" >Nguyên liệu </option>
		                            	<option value="100008" >Vật liệu phụ </option>
				<%} %>								        
		                            </select>
		                        </TD>   
		                  </TR>		                  
				<% if (obj.getLoaiId().equals("100002") || obj.getLoaiId().equals("100003")){ %>		                  
		                  <TR>

		                        <TD class="plainlabel" valign="middle" width="100px" >Chủng loại </TD>   
		                        <TD class="plainlabel" valign="middle"  colspan="3">
		                        	<select name="clId" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(chungloaiRs != null)
			                        		{
			                        			while(chungloaiRs.next())
			                        			{  
			                        			if( chungloaiRs.getString("pk_seq").equals(obj.getClId())){ %>
			                        				<option value="<%= chungloaiRs.getString("pk_seq") %>" selected="selected" ><%= chungloaiRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= chungloaiRs.getString("pk_seq") %>" ><%= chungloaiRs.getString("ten") %></option>
			                        		 <% } } chungloaiRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>
		               
		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                            <select name="spId" id="spId" onchange="submitform();" style="width: 560px;" >
		                            	<option value=""></option>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  
			                        			if( spRs.getString("spId").equals(obj.getSpId())){ %>
			                        				<option value="<%= spRs.getString("spId") %>" selected="selected" ><%= spRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= spRs.getString("spId") %>" ><%= spRs.getString("ten") %></option>
			                        		 <% } } spRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>                
		                  </TR> 
		     <%}else{ %>
		     
		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Nguyên liệu </TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                            <select name="spId" id="spId" onchange="submitform();" style="width: 560px;"  >
		                            	<option value=""></option>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  
			                        			if( spRs.getString("spId").equals(obj.getSpId())){ %>
			                        				<option value="<%= spRs.getString("spId") %>" selected="selected" ><%= spRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= spRs.getString("spId") %>" ><%= spRs.getString("ten") %></option>
			                        		 <% } } spRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>              
		                  </TR> 
			<%} %>		     
		                </TABLE>
   
						<TABLE width="60%" align="left" cellpadding="6" cellspacing="2px">
							<TR class="tbheader">
								<TH width="5%" align="center" >Thứ tự</TH>
								<TH width="10%" align="center" >Số lượng</TH>
								<TH width="10%" align="center" >Còn lại</TH>		
								<TH width="30%" align="center" >Ghi chú</TH>
							</TR>
													
					
<%   if(obj.getSpId().length() > 0 ){
		int m = 0;
   		 
   		long ton = 0;
   		long yeucau;
    		
		String tungay = obj.getNgayhientai();
		String denngay;
		
		if(obj.getThang().length()== 1){
    		denngay = "" + obj.getNam() + "-0" + obj.getThang() + "-31";
		}else{
			denngay =  "" + obj.getNam() + "-" + obj.getThang() + "-31";
		}
		
    	System.out.println("DEN NGAY :" + denngay);
    	
    	
		ResultSet data;
		if (obj.getLoaiId().equals("100002") || obj.getLoaiId().equals("100003")){
    		data = obj.getData();
		}else{
			data = obj.getDataNL();
		}
		try{
			if(data != null){
       			double conlai = 0;
				while(data.next()){	
       				if((m % 2 ) == 0) {%>
                 			<TR class='tbdarkrow'>
                <%  }else{ %>
                  			<TR class='tblightrow'>
                <%  }  			
         		if (obj.getLoaiId().equals("100002") || obj.getLoaiId().equals("100003")){     %>  
								<TD align = "center"><%= (m + 1) %> </TD>
						<% if(data.getString("LOAI").indexOf("-1") >= 0 || data.getString("LOAI").indexOf("-3") >= 0){ 
								conlai += (-1)*data.getDouble("SOLUONG");
						%>
						<%		if(data.getDouble("SOLUONG") > 0) { %>
								<TD align = "center"><%=  formatter.format((-1)*data.getDouble("SOLUONG")) %> </TD>
						<%		}else{ %>
								<TD align = "center">0 </TD>
						<%      } %>
						<%	
							}else{
								conlai += data.getDouble("SOLUONG");%>
								<TD align = "center"><%= formatter.format(data.getDouble("SOLUONG")) %> </TD>
						<%  } %>
						
								<TD align = "center"><%= formatter.format(conlai) %> </TD>
								
						<% if(data.getString("LOAI").indexOf("-0") >= 0){ %>
								<TD align = "left">Tồn kho hiện tại</TD>
						<% } %>		
								
						<% if(data.getString("LOAI").indexOf("-1") >= 0){ %>
								<TD align = "left">Tồn kho an toàn</TD>
						<% } %>		

						<% if(data.getString("LOAI").indexOf("-2") >= 0){ %>
								<TD align = "left">Lệnh sản xuất tháng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %> (<%= data.getString("ID")%>)</TD>
						<% } %>		

						<% if(data.getString("LOAI").indexOf("-4") >= 0){ %>
								<TD align = "left">Đề nghị sản xuất tháng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %> (<%= data.getString("ID")%>)</TD>
						<% } %>		
								
						<% if(data.getString("LOAI").indexOf("-3") >= 0){ %>
								<TD align = "left">Dự kiến bán tháng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %></TD>
						<% } %>		
			<%}else{ %>					
								<TD align = "center"><%= (m + 1) %> </TD>
						<% if(data.getString("LOAI").indexOf("-1") >= 0 || data.getString("LOAI").indexOf("-3") >= 0){ 
								conlai += (-1)*data.getDouble("SOLUONG");
						%>
						<%		if(data.getDouble("SOLUONG") > 0) { %>
								<TD align = "center"><%=  formatter.format((-1)*data.getDouble("SOLUONG")) %> </TD>
						<%		}else{ %>
								<TD align = "center">0 </TD>
						<%      } %>
						<%	
							}else{
								conlai += data.getDouble("SOLUONG");%>
								<TD align = "center"><%= formatter.format(data.getDouble("SOLUONG")) %> </TD>
						<%  } %>
						
								<TD align = "center"><%= formatter.format(Math.round(conlai)) %> </TD>
								
						<% if(data.getString("LOAI").indexOf("-0") >= 0){ %>
								<TD align = "left">Tồn kho hiện tại</TD>
						<% } %>		
								
						<% if(data.getString("LOAI").indexOf("-1") >= 0){ %>
								<TD align = "left">Tồn kho an toàn</TD>
						<% } %>		

						<% if(data.getString("LOAI").indexOf("-2") >= 0){ %>
								<TD align = "left">Đơn mua hàng tháng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %> (<%= data.getString("ID")%>)</TD>
						<% } %>		

						<% if(data.getString("LOAI").indexOf("-4") >= 0){ %>
								<TD align = "left">Đề nghị mua hàng tháng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %> (<%= data.getString("ID")%>)</TD>
						<% } %>		
								
						<% if(data.getString("LOAI").indexOf("-3") >= 0){ %>
								<TD align = "left">Yêu cầu cung ứng <%= data.getString("THANG") %>, năm <%= data.getString("NAM") %></TD>
						<% } %>					
			
			<%}%>	
					</TR>	
   <%
   					m++;
   				}
				data.close();
			}
   		}catch(java.sql.SQLException e){}
   
	} %>						
	
							
						</TABLE>
									
					</FIELDSET>											
				</TD>
			</TR>
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-bottom", 300, "click");
</script>
</BODY>
</HTML>
<%
	if(spRs != null) spRs.close();

	if(thangRs != null) thangRs.close();
	
	if(namRs != null) namRs.close();

	if(chungloaiRs != null) chungloaiRs.close();
	
	obj.DbClose();
	 
	session.setAttribute("xkhBean", null);
	}%>
