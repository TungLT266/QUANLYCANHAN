<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.canhbaothieuhang.*"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<% dbutils db = new dbutils(); %> 

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpCanhbaothieunguyenlieu obj =(IErpCanhbaothieunguyenlieu)session.getAttribute("obj");
	ResultSet thangRs = obj.getThangRs();
	ResultSet namRs = obj.getNamRs();
	List<ICanhbao> canhbaoList = obj.getCanhbaoList();
	ResultSet nccRs = obj.getNccRs();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
		
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
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}
	
	function CapNhatPO()
	{
		document.forms['khtt'].action.value= 'updatePO';
		document.forms['khtt'].submit();
	}
	
	function keypress(e) 
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
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpCanhbaothieunguyenlieuSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Cảnh báo thiếu nguyên liệu </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                    
                    	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
						
			    				<textarea name="dataerror"  style="background-color:white; width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
								</FIELDSET>
						   </TD>
						</tr>	
                    
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Cảnh báo thiếu nguyên liệu</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR style="display: none;" >
                             	<TD width="80px" class="plainlabel" style="display: none;" >Tháng </TD>
								<TD width="240px"  class="plainlabel" style="display: none;" >
									<select name="thangId" style = "width: 100px" onchange="submitform();" >
										<option value=""></option>
		                            	<%
			                        		if(thangRs != null)
			                        		{
			                        			while(thangRs.next())
			                        			{  
			                        			if( thangRs.getString("thangId").equals(obj.getThangId())){ %>
			                        				<option value="<%= thangRs.getString("thangId") %>" selected="selected" ><%= thangRs.getString("thangTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= thangRs.getString("thangId") %>" ><%= thangRs.getString("thangTen") %></option>
			                        		 <% } } thangRs.close();
			                        		}
			                        	%>
		                            </select>
								</TD>
                             
                             	<TD width="80px" class="plainlabel" style="display: none;" >Năm </TD>
								<TD width="240px" class="plainlabel" style="display: none;" >
									<select name="namId"  style = "width: 100px" onchange="submitform();"  >
										<option value=""></option>
		                            	<%
			                        		if(namRs != null)
			                        		{
			                        			while(namRs.next())
			                        			{  
			                        			if( namRs.getString("namId").equals(obj.getNamId())){ %>
			                        				<option value="<%= namRs.getString("namId") %>" selected="selected" ><%= namRs.getString("namTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= namRs.getString("namId") %>" ><%= namRs.getString("namTen") %></option>
			                        		 <% } } namRs.close();
			                        		}
			                        	%>
		                            </select>
								</TD>
								
								<TD width="80px" class="plainlabel" style="display: none;" >Xem theo </TD>
								<TD  class="plainlabel">
									<select name="viewMode"  onchange="submitform();" style="display: none;"  >
										<% if(obj.getViewMode().equals("0")) { %>
											<option value="0" selected="selected" >Xem chi tiết</option>
										<% } else { %>
											<option value="0" >Xem chi tiết</option>
										<% } %>
		                            	
		                            	<% if(obj.getViewMode().equals("1")) { %>
											<option value="1" selected="selected" >Xem tổng quan</option>
										<% } else { %>
											<option value="1" >Xem tổng quan</option>
										<% } %>
		                            </select>
								</TD>
                             </TR >
                             <TR>
                             	<TD colspan="6" >
                             		<% if(obj.getViewMode().equals("0")) { %>
	                             		<% if(canhbaoList.size() > 0) { %> 
				                          <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
				                              <TR class="tbheader">
				                                  <TH width="10%" align="center">Ngày</TH>
				                                  <TH width="10%" align="center">Lệnh sản xuất</TH>
				                                  <TH width="40%" align="center">Nguyên liệu</TH>
				                                  <TH width="15%" align="center">Số lượng cần</TH>
				                                  <TH width="15%" align="center">Số lượng thiếu</TH>
				                              </TR>
				                              
				                              <% for(int i = 0; i < canhbaoList.size(); i++) { ICanhbao canhbao = canhbaoList.get(i);  %>
				                              
				                              	<TR class="tbdarkrow">
				                              		<td><%= canhbao.getNgaygiao() %> </td>
				                              		<td><%= canhbao.getChungtu() %> </td>
				                              		<td><%= canhbao.getSanpham() %> </td>
				                              		<td style="text-align: right;"><%= canhbao.getSoluonggiao() %> </td>
				                              		<td style="text-align: right;"><%= canhbao.getThieu() %> </td>
				                              	</TR>
				                              
				                              <% } %>
				                              
				                              <TR class="tbfooter" >
												  <TD align="center" colspan="15"> &nbsp; </TD>
											  </TR>                                                  
				                          </TABLE>
				                          <% } %>
                             		  <% } else { %>
                             		  
                             		  	<% if(canhbaoList.size() > 0) { %> 
				                          <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
				                              <TR class="tbheader">
				                                  <TH width="10%" align="center">Ngày</TH>
				                                  <TH width="40%" align="center">Nguyên liệu</TH>
				                                  <TH width="15%" align="center">Số lượng cần</TH>
				                                  <TH width="15%" align="center">Số lượng thiếu</TH>
				                                  <TH width="10%" align="center">PO</TH>
				                              </TR>
				                              
				                              <% for(int i = 0; i < canhbaoList.size(); i++) { ICanhbao canhbao = canhbaoList.get(i);  
				                              
				                              	 	if(i % 2 == 0){
				                              %>
				                              
				                              	<TR class="tbdarkrow">
				                              	
				                              <%	 }else{ %>
				                              
				                              	<TR class="tblightrow">
				                              	
				                              <%     } %>
				                              		<td>
				                              			<%= canhbao.getNgaygiao() %> 
				                              			<input type="hidden" name="spCanhbaoId" value="<%= canhbao.getSanphamId().trim() %>" > 
				                              		</td>
				                              		<td>
				                              			<%= canhbao.getSanpham() %> 
				                              			<input type="hidden" name="spCanhbaoNgay" value="<%= canhbao.getNgaygiao().trim() %>" > 
				                              		</td>
				                              		<td style="text-align: right;"><%= canhbao.getSoluonggiao() %> </td>
				                              		<td style="text-align: right;"><%= canhbao.getThieu() %> </td>
				                              		<td style="text-align: center;">
				                              		<% 
				                              		   ResultSet rsLsx = canhbao.getLsxRs();
				                              		   if(rsLsx != null) 
				                              		   { %>
				                              				   
				                              			    <a href="" id="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao() %>" rel="subcontent<%= i %>">
							           	 							<img alt="Số lô - Số lượng nhận" src="../images/vitriluu.png"></a>
								           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
											                             background-color: white; width: 750px; padding: 4px;">
											                    <table width="90%" align="center">
											                        <tr>
											                            <th width="80px">PO</th>
											                            <th width="80px">Ngày nhận</th>
											                            <th width="250px">Sản phẩm</th>
											                            <th width="150px">Nhà cung cấp</th>
											                            <th width="80px">Số lượng</th>
											                        </tr>
											                        
											                        <%
											                        	rsLsx.beforeFirst();
						                              				    while(rsLsx.next())
						                              				    {
						                              					    %>
						                              					    
						                              					    <tr>
						                              					    	<td>
						                              					    		<input name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_ChungTu" %>" 
						                              					    				type="text" style="width: 100%" value="<%= rsLsx.getString("chungtu") %>" readonly="readonly" > 
						                              					    	</td>
						                              					    	<td>
						                              					    		<input name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_NgayGiao" %>" 
						                              					    				type="text" style="width: 100%" value="<%= rsLsx.getString("NGAYNHAN") %>" readonly class="days"  >
						                              					    		<input name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_NgayGiaoOLD" %>" 
						                              					    				type="hidden" style="width: 100%" value="<%= rsLsx.getString("NGAYNHAN") %>"   >
						                              					    	</td>
						                              					    	<td>
						                              					    		<input type="text" style="width: 100%" readonly="readonly" value="<%= canhbao.getSanpham() %>" name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_SpIdDat" %>"  >
						                              					    	</td>
						                              					    	<td>
						                              					    		<input type="text" style="width: 100%" readonly="readonly" value="" name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_NccId" %>"  >
						                              					    	</td>
						                              					    	<td>
						                              					    		<input name="<%= canhbao.getSanphamId().trim() + "." + canhbao.getNgaygiao().trim() + ".updatePO_SoLuong" %>"
						                              					    		 		type="text" style="width: 100%; text-align: right;" value="<%= rsLsx.getString("SOLUONG") %>" readonly="readonly" >
						                              					    	</td>
						                              					    </tr>
						                              					    
						                              				    <% }
						                              				    rsLsx.close();
											                        %>
											                        
											                        
											                    </table>
											                     <div align="right">
											                     	<label style="color: red" ></label>
											                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											                     	<br />
											                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')" onclick="CapNhatPO();" >Cập nhật PO</a> &nbsp; | &nbsp;
											                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')" >Đóng lại</a>
											                     </div>
											                </DIV>	   
				                              				   
				                              		<%	}	%>
				                              												                
				                              		</td>
				                              	</TR>
				                              
				                              <% } %>
				                              
				                              <TR class="tbfooter" >
												  <TD align="center" colspan="15"> &nbsp; </TD>
											  </TR>                                                  
				                          </TABLE>
				                          <% } %>
                             		  
                             		  <% } %>
                             	</TD>
                             </TR >
                              
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

        </TABLE>
        </TD>
    </TR>
</TABLE>
</form>

<script type="text/javascript">
	
	<%  if(obj.getViewMode().equals("1")) {
		for(int i = 0; i < canhbaoList.size(); i++)
		{
			ICanhbao sp = canhbaoList.get(i);
	%>
		dropdowncontent.init('<%= sp.getSanphamId().trim() + "." + sp.getNgaygiao() %>', "left-bottom", 300, "click");
	<% } } %>
</script>

</BODY>
</html>
<% 	
	try
    {
		if(thangRs != null)
			thangRs.close();
		if(namRs != null)
			namRs.close();
		if(canhbaoList.size() > 0)
			canhbaoList.clear();
		if(nccRs != null)
			nccRs.close();
		
		
		session.setAttribute("obj", null);
		
		
	}
	catch (Exception e) {e.printStackTrace();}
	finally
	{
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		db.shutDown();
	}
	%>
<%}%>