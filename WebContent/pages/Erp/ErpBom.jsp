<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErpBomList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpBomList obj =(IErpBomList)session.getAttribute("obj");
	ResultSet khttRs = obj.getBomRs();
	ResultSet dvkdRs = obj.getDvkdRs();
	ResultSet lspRs = obj.getLspRs();
	 int[] quyen = new  int[5];
	 quyen = util.GetquyenNew("ErpBomSvl","&loaiNPP=0&isKH=0",userId);

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
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
 <link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
        $(document).ready(function() { $("#sanpham").select2(); });
        $(document).ready(function() { $("#lspId").select2(); });
        $(document).ready(function() { $("#manguyenlieu").select2(); });
    </script>
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
		document.forms['khtt'].diengiai.value = "";
		document.forms['khtt'].vanbanhuongdan.value = "";
		document.forms['khtt'].sanpham.value = "";
		document.forms['khtt'].dvkdId.value = "";
		document.forms['khtt'].lspId.value = "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].manguyenlieu.value = "";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}
	function xuatexcel()
	{
		document.forms['khtt'].action.value= 'excel';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	
	if(<%=obj.getMsg().trim().length() %> > 0){
		alert("<%=obj.getMsg() %>");
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpBomSvl">
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Danh mục vật tư (BOM) </TD>  
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
                        <TR>
                            <TD width="100%" align="left" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             
                              <TR>
                             	<TD width="15%" class="plainlabel" >Sản phẩm </TD>
								<TD class="plainlabel">
									
									
									<select name="sanpham"  id="sanpham" style="width: 200px" onChange="submitform();">
										<option value=""> </option>
									<%  
									ResultSet SPRs=obj.getSpRs();
									while(SPRs.next()) {
										  if(SPRs.getString("PK_SEQ").equals(obj.getSanpham())) { %>
											<option value="<%=SPRs.getString("PK_SEQ") %>" selected><%=SPRs.getString("TEN") %></option>
										<%} else { %>
											<option value="<%=SPRs.getString("PK_SEQ") %>"><%=SPRs.getString("TEN") %></option>
										<%}
									   }%>
										
									    </select>
									    
								</TD>
								<TD width="15%" class="plainlabel" >Tên sản phẩm </TD>
								<TD class="plainlabel">
									<input type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR>
                              <TR>
                               	<TD width="15%" class="plainlabel" >Loại sản phẩm </TD>
								<TD class="plainlabel" >
									<select name="lspId"  id="lspId" style="width: 200px" onChange="submitform();">
										<option value=""> </option>
										
									<% while(lspRs.next()) {
										  if(lspRs.getString("PK_SEQ").equals(obj.getLspId())) { %>
											<option value="<%=lspRs.getString("PK_SEQ") %>" selected><%=lspRs.getString("TEN") %></option>
										<%} else { %>
											<option value="<%=lspRs.getString("PK_SEQ") %>"><%=lspRs.getString("TEN") %></option>
										<%}
									   }%>
										
									    </select>
								</TD>
								<TD width="15%" class="plainlabel" >Mã định mức vật tư</TD>
								<TD class="plainlabel">
									<input type="text" name="vanbanhuongdan" value="<%=obj.getVanBanHuongDan() %>" size="20" onchange=submitform();>
								</TD>
                             </TR>
                             <TR>
                             	<TD width="15%" class="plainlabel" >Đơn vị kinh doanh </TD>
								<TD class="plainlabel">
									<select name="dvkdId" onChange="submitform();">
										<option value=""> </option>
										
									<% while(dvkdRs.next()) {
										  if(dvkdRs.getString("PK_SEQ").equals(obj.getDvkdId())) { %>
											<option value="<%=dvkdRs.getString("PK_SEQ") %>" selected><%=dvkdRs.getString("DONVIKINHDOANH") %></option>
										<%} else { %>
											<option value="<%=dvkdRs.getString("PK_SEQ") %>"><%=dvkdRs.getString("DONVIKINHDOANH") %></option>
										<%}
									   }%>
										
									    </select>
								</TD>
								<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
									<% if(obj.getTrangthai().equals("0")){ %>
										<option value="0" selected>Ngưng hoạt động</option>
										<option value="1">Hoạt động</option>
										<option value=""> </option>
									<%} else {
										if (obj.getTrangthai().equals("1")){%>										
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" selected>Hoạt động</option>										
										<option value=""> </option>
									<%}else{ %>
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" >Hoạt động</option>								
										<option value="" selected> </option>
									<%	} 
								}%>
										
									    </select>
								</TD>
                             </TR>
                             
                             <tr>
                             	<TD width="15%" class="plainlabel" >Ghi chú</TD>
								<TD class="plainlabel" >
									<input type="text" name="ghichu" value="<%=obj.getGhichu() %>" size="20" onchange=submitform();>
								</TD>
								
								<TD width="15%" class="plainlabel" >Mã nguyên liệu</TD>
								<TD class="plainlabel">
									
									
									<select name="manguyenlieu"  id="manguyenlieu" style="width: 200px" onChange="submitform();">
										<option value=""> </option>
									<%  
									ResultSet manguyenlieuRs=obj.getManguyenlieuRs();
									while(manguyenlieuRs.next()) {
										  if(manguyenlieuRs.getString("PK_SEQ").equals(obj.getManguyenlieu())) { %>
											<option value="<%=manguyenlieuRs.getString("PK_SEQ") %>" selected><%=manguyenlieuRs.getString("TEN") %></option>
										<%} else { %>
											<option value="<%=manguyenlieuRs.getString("PK_SEQ") %>"><%=manguyenlieuRs.getString("TEN") %></option>
										<%}
									   }
									manguyenlieuRs.close();
									   %>
										
									    </select>
									    
								</TD>
                             </tr>
                             
                             <tr class="plainlabel"> 
                             	<td colspan="4" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:xuatexcel()">
										<img style="top: -4px;" src="../images/excel30.gif" alt="">Xuất Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;		
                             	</td> 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Danh mục vật tư (BOM) &nbsp;&nbsp;	
                            	<%if(quyen[0]!=0){ %>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	 <%} %>    
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
                                <TR class="tbheader">
                                 	<TH width="10%" align="center">Mã ĐMVT</TH>
                                    <TH width="10%" align="center">Tên sản phẩm</TH>
                                     <TH width="11%" align="center">Ghi chú</TH>
                                     <TH width="10%" align="center">Nơi sản xuất</TH>
                                    <TH width="11%" align="center">Hiệu lực</TH> 
                                    <TH width="8%" align="center">Trạng thái</TH>
                                    <TH width="7%" align="center">Ngày tạo</TH>
                                    <TH width="7%" align="center">Người tạo</TH>
                                    <TH width="7%" align="center">Ngày sửa</TH>
                                    <TH width="7%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(khttRs != null)
                                    while ( khttRs.next()){
                                       
                                    	String tt = khttRs.getString("trangthai").trim();
                                    	String ishoatdong = khttRs.getString("ISHOATDONG").trim();
                                    	String isdaduyet = khttRs.getString("ISDADUYET").trim();
                                    	String trangthai = "";
                                    	if(ishoatdong.equals("0"))
                                    	{
                                    		trangthai = "Ngưng hoạt động";
                                    	}else if(isdaduyet.equals("0") && ishoatdong.equals("1"))
                                    	{
                                    		trangthai = "Hoạt động, chưa duyệt";
                                    	}else if(isdaduyet.equals("1") && ishoatdong.equals("1")&&  tt.equals("1"))
                                    	{
                                    		trangthai = "Hoạt động, đã duyệt";
                                    	}
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                       			 <TD align="left"><%= khttRs.getString("tenbom") %></TD>  
                                                <TD align="left"><%= khttRs.getString("diengiai") %></TD>                                   
                                                <TD align="left"><%= khttRs.getString("vanbanhuongdan") %></TD> 
                                                 <TD align="left"><%= khttRs.getString("tennhamay") %></TD>   
                                                 <TD align="center"><%= khttRs.getString("hieuluc")%></TD> 
                                                
                                               <TD align="center" ><%=trangthai %></TD>
                                                
                                                <TD align="center"><%= khttRs.getString("NGAYTAO")%> </TD>
                                                <TD align="center"><%= khttRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= khttRs.getString("NGAYSUA")%> </TD>
                                                <TD align="center"><%= khttRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                               
                                                	<%if(quyen[2]!=0){ 
                                                	if(khttRs.getString("CHECKTONTAI").equals("1"))
                                                	{
                                                	%>
							                   		<A id='<%= khttRs.getString("pk_seq") %>' href = "../../ErpBomUpdateSvl?userId=<%=userId%>&update=<%= khttRs.getString("pk_seq") %>" rel="subcontent<%="suattid" + khttRs.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 ></A> &nbsp;
							                   		<DIV id="subcontent<%="suattid" + khttRs.getString("pk_seq") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
													 background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
								                    <table width="98%" align="center" cellspacing="1" >
								                        <span> BOM này đã có phát sinh chưa hoàn tất ở lệnh sản xuất, gia công, ban vẫn muốn cập nhật</span>
								                    </table>
								        					                    
								                    <div align="right">
								                    							                    
								                     	<a href= "../../ErpBomUpdateSvl?userId=<%=userId%>&update=<%= khttRs.getString("pk_seq") %>" style="color: red; font-weight: bold;">Xác nhận sửa</a>
								                     
								                     	&nbsp;&nbsp;|&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="suattid" + khttRs.getString("pk_seq") %>')" style="font-weight: bold;" >Đóng lại</a>
								                    </div>
									        </DIV> 
								            <script type="text/javascript">
												dropdowncontent.init('<%= khttRs.getString("pk_seq") %>', "left-top", 300, "click");
											</script>
											<%} else {%>
											<A id='<%= khttRs.getString("pk_seq") %>' href = "../../ErpBomUpdateSvl?userId=<%=userId%>&update=<%= khttRs.getString("pk_seq") %>" ><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 ></A> &nbsp;
							                   		<%} %>
							                   		<%} %>
							                   	
							                   		<%if(quyen[2]!=0){ %>
							                   		<A href = "../../ErpBomUpdateSvl?userId=<%=userId%>&copy=<%= khttRs.getString("pk_seq") %>"><img src="../images/copy20.png" alt="Copy BOM" title="Copy BOM" width="20" height="20" longdesc="Copy BOM" border = 0></A> &nbsp;
							                   		<%} %>
							                   	
							                   		<%if(quyen[1]!=0){ 
	                                                	if(khttRs.getString("CHECKTONTAI").equals("1"))
	                                                	{
	                                                	%>
								                   		<A id='xoa_<%= khttRs.getString("pk_seq") %>' href = "../../ErpBomSvl?userId=<%=userId%>&delete=<%= khttRs.getString("pk_seq") %>" rel="subcontent<%="xoattid" + khttRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 ></A> &nbsp;
								                   		<DIV id="subcontent<%="xoattid" + khttRs.getString("pk_seq") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
														 background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
									                    <table width="98%" align="center" cellspacing="1" >
									                        <span>BOM này đã có phát sinh chưa hoàn tất ở lệnh sản xuất, gia công, ban vẫn muốn xóa?</span>
									                    </table>
									        					                    
									                    <div align="right">
									                    							                    
									                     	<a href= "../../ErpBomSvl?userId=<%=userId%>&delete=<%= khttRs.getString("pk_seq") %>" style="color: red; font-weight: bold;">Xác nhận xóa</a>
									                     
									                     	&nbsp;&nbsp;|&nbsp;&nbsp;
									                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="xoattid" + khttRs.getString("pk_seq") %>')" style="font-weight: bold;" >Đóng lại</a>
									                    </div>
										        </DIV> 
									            <script type="text/javascript">
													dropdowncontent.init('xoa_<%= khttRs.getString("pk_seq") %>', "left-top", 300, "click");
												</script>
												<%} else {%>
												<A href = "../../ErpBomSvl?userId=<%=userId%>&delete=<%= khttRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Ban Muon Xoa Bom Nay?')) return false;"></A>
													   		<%} %>
								                   		<%} %>
												
													<%if(quyen[3]!=0){ %>
													<A href = "../../ErpBomUpdateSvl?userId=<%=userId%>&display=<%= khttRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
													<% }  %>
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
                                          
                                          
                                          
                       					<tr class="tbfooter" >
														<td colspan="11" align="center" class="tbfooter">
															<% obj.setNextSplittings(); %>
															 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
															<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
															<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
								
														 	<%if(obj.getNxtApprSplitting() >1) {%>
																<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
															<%}else {%>
																<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
																<%} %>
															<% if(obj.getNxtApprSplitting() > 1){ %>
																<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
															<%}else{ %>
																<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
															<%} %>
															
															<%
																int[] listPage = obj.getNextSplittings();
																for(int i = 0; i < listPage.length; i++){
															%>
															
															<% 
															
															System.out.println("Current page:" + obj.getNxtApprSplitting());
															System.out.println("List page:" + listPage[i]);
															
															if(listPage[i] == obj.getNxtApprSplitting()){ %>
															
																<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %>
																<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %>
																<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
															<%} %>
															
															<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
																&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
															<%}else{ %>
																&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
															<%} %>
															<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
														   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
													   		<%}else{ %>
													   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
													   		<%} %>
									
														</td>
													</tr>
                            </TABLE>
                            </TD>
                        </TR>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>

        </TABLE>
        </TD>
    </TR>
</TABLE>
</form>
</BODY>
</html>
<% 	
	try
    {
		if(khttRs != null)
			khttRs.close();
		if(lspRs != null)
			lspRs.close();
		if(dvkdRs != null)
			dvkdRs.close();
		 
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>