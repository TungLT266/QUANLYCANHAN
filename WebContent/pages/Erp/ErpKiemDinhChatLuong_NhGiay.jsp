<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluongList_NhGiay"%>
<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluongList_NhGiay"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.*"%>
<%@ page  import = "geso.traphaco.erp.beans.nhanhangnhapkhau.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhanhangnhapkhau.imp.*" %>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoERP/index.jsp");
	}else{ %>
<%	
	IErpKiemdinhchatluongList_NhGiay obj =(ErpKiemdinhchatluongList_NhGiay)session.getAttribute("obj");
	ResultSet csxRs = obj.getKdclRs();
	// khai bao doi tuong nhanhang de lay loai mua hang
	//IErpNhanhang_Giay nhBean =(IErpNhanhang_Giay)session.getAttribute("obj");
	NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpKiemdinhchatluong_NhGiaySvl","",userId);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
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
	

<SCRIPT language="javascript" type="text/javascript">
	function newform()
	{   
		document.forms["kdcl"].action.value = "new";
	    document.forms["kdcl"].submit();
	}
	 
	function submitform()
	{
		document.kdcl.action.value= 'search';
		document.forms["kdcl"].submit();
	}
	
	function clearform()
	{ 
	    document.kdcl.solo.value= "";
	    document.kdcl.sanpham.value= "";
	    document.kdcl.trangthai.value = "";
	    document.kdcl.sonhanhang.value = "";
	    document.kdcl.sochungtu.value = "";
	    document.kdcl.ngaynhan.value = "";
	    document.kdcl.tungayKD.value = "";
	    document.kdcl.denngayKD.value = "";
	    document.kdcl.tungayNH.value = "";
	    document.kdcl.denngayNH.value = "";
	    document.kdcl.sohoadon.value = "";
	    submitform();
	}

	function newform()
	{
		document.kdcl.action.value= 'new';
		document.kdcl.submit();
	}
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>
 	
 	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kdcl" method="post" action="../../ErpKiemdinhchatluong_NhGiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="loaimuahang" value="<%= obj.getloaimuahang() %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">
                            
                            <% if( obj.getloaimuahang().equals("0")) {%>
                            
                            Quản lý mua hàng > Mua hàng nhập khẩu > Kiểm định chất lượng 
                            
                            <% }else if( obj.getloaimuahang().equals("1")) { %>
                             Quản lý mua hàng > Mua hàng trong nước > Kiểm định chất lượng
                             <% }else if( obj.getloaimuahang().equals("2")) { %>
                             Quản lý cung ứng > Sản xuất > Kiểm định chất lượng
                            <%} %>
                            </TD>  
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
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
			                    <TR>
			                       
			                        <TD class="plainlabel" width="12%">Ngày kiểm định từ: </TD>
			                        <TD class="plainlabel" width="25%">
			                            <input type="text" class="days" 
			                                   id="tungayKD" name="tungayKD" value="<%=obj.getTungay() %>" maxlength="10" onchange="submitform()" />
			                        </TD>
			                        <TD class="plainlabel" width="12%">Đến ngày </TD>
			                        <TD class="plainlabel" >
			                            <input type="text" class="days" 
			                                   id="denngayKD" name="denngayKD" value="<%=obj.getDenngay() %>" maxlength="10" onchange="submitform()"/>
			                        </TD>
			                    </TR>
			                    
			                    <TR>
                       
			                        <TD class="plainlabel" width="12%">Ngày nhận hàng từ: </TD>
			                        <TD class="plainlabel" width="25%">
			                            <input type="text" class="days" 
			                                   id="tungayNH" name="tungayNH" value="<%=obj.getTungayNH() %>" maxlength="10" onchange="submitform()" />
			                        </TD>
			                        <TD class="plainlabel" width="12%">Đến ngày </TD>
			                        <TD class="plainlabel" >
			                            <input type="text" class="days" 
			                                   id="denngayNH" name="denngayNH" value="<%=obj.getDenngayNH() %>" maxlength="10" onchange="submitform()"/>
			                        </TD>
			                    </TR>
                    
                            
                            
                             <TR>
                             	<TD  class="plainlabel" >Số lô </TD>
								<TD class="plainlabel">
									<input type="text" name="solo" value="<%= obj.getMa()%>" size="20" onchange=submitform();>
								</TD>
								<TD class="plainlabel" >Sản phẩm </TD>
								<%-- <TD class="plainlabel">
									<input type="text" name=sanpham value="<%= obj.getSanpham() %>" size="20" onchange=submitform();>
								</TD> --%>
								
								<% ResultSet rsSP= obj.getSpRs() ;%>  
								<TD class="plainlabel" valign="top" >
			                    	<select name="sanpham" id="sanpham" onchange="submitform();"  style="width: 200px;">
			                    		<option value=""> Tất cả</option>
			                        	<%
			                        		if(rsSP != null)
			                        		{
			                        			try
			                        			{
			                        			while(rsSP.next())
			                        			{  
			                        			if( rsSP.getString("pk_seq").equals(obj.getSanpham())){ %>
			                        				<option value="<%= rsSP.getString("pk_seq") %>" selected="selected" ><%=rsSP.getString("ma") +" -- "+  rsSP.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= rsSP.getString("pk_seq") %>" ><%=rsSP.getString("ma") +"--"+  rsSP.getString("ten") %></option>
			                        		 <% } } rsSP.close();} catch(SQLException ex){}
			                        		}
			                        	%>
			                        </select>
			                    </TD>

								
								
								
								
                             </TR>
                             
                             
                             <TR>
                             	<TD class="plainlabel" >Số nhận hàng </TD>
								<TD class="plainlabel">
									<input type="text" name=sonhanhang value="<%= obj.getSonhanhang() %>" size="20" onchange=submitform();>
								</TD>
								<TD class="plainlabel" >Số chứng từ kiểm định </TD>
								<TD class="plainlabel">
									<input type="text" name=sochungtu value="<%= obj.getsochungtu() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             
                             
                             
                              <TR>
                             	
                             </TR >
                             
                             <TR>
                             	<TD class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel" >
								
										<% 
                    	String mang[] =new String[]{"0","1","2","3"};
                    	String mangten[] =new String[]{"Chờ kiểm định","Đã chốt","Hoàn tất","Đã hủy"};
                    	%> <select name="trangthai" onChange="submitform();" style="width: 200px;">
										<option value=""></option>
										<%
                    	for(int j=0;j<mang.length;j++){
							 if(obj.getTrangthai().trim().equals(mang[j])){
							 %>
										<option selected="selected" value="<%=mang[j]%>" style="width: 200px;">
											<%=mangten[j] %>
										</option>
										<%
							 }else{
							 %>
										<option value="<%=mang[j]%>" style="width: 200px;">
											<%=mangten[j] %>
										</option>
										<% 
							 }
						 }
                    	%>
								 								</TD>
								<TD class="plainlabel" >Ngày nhận </TD>
                       			 <TD class="plainlabel" >
                           		 <input type="text" class="days" 
                                   id="ngaynhan" name="ngaynhan" value="<%=obj.getNgayNhan() %>" maxlength="10" onchange="submitform()"/>
                        </TD>
                             </TR >
                             
                             
                             
                             
                              
                             <TR>
                             	<TD class="plainlabel" >Số hóa đơn </TD>
								<TD class="plainlabel" colspan="3">
									<input type="text" name=sohoadon value="<%= obj.getSohoadon()%>" size="20" onchange=submitform();>
								</TD>
								
                             </TR >
                             
                             
                             <%-- <TR>
                             	<TD  class="plainlabel" > Mã đơn mua hàng </TD>
								<TD class="plainlabel" colspan="3">
									<input type="text" name="donmuahang" value="<%= obj.getDonMuaHang()%>" size="20" onchange=submitform();>
								</TD>
                             </TR> --%>
                             
                              
                             <tr class="plainlabel"> 
                             	<td colspan="4" > 
                             		<a class="button" href="javascript:submitform()">
                                	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
                            <LEGEND class="legendtitle">&nbsp;Kiểm định chất lượng &nbsp;&nbsp;	
	
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="5%" align="center">Mã </TH>
                                    <TH width="10%" align="center">Số nhận hàng</TH>
                                   <!--  <TH width="8%" align="center">Ngày sản xuất</TH>
                                    <TH width="8%" align="center">Ngày hết hạn</TH> -->
                                    <TH width="6%" align="center">Số hóa đơn</TH>
                                    <TH width="20%" align="center">Sản phẩm</TH>
                                    <TH width="6%" align="center">Số lượng kiểm</TH>
                                    <TH width="6%" align="center">Số lượng đạt</TH>
                                    <TH width="6%" align="center">Số lô</TH>
                                    <TH width="6%" align="center">Trạng thái</TH>
                                    <TH width="8%" align="center">Ngày nhận hàng</TH>
                                    <TH width="8%" align="center">Ngày kiểm định</TH>
                                    <TH width="8%" align="center">Người kiểm</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(csxRs != null)
                                    while ( csxRs.next()){
                                       
                                    	String tt = csxRs.getString("trangthai").trim();
                                    	String loaimh="Mua hàng hàng";
                                    	String style="";
                                    	if(csxRs.getString("muahang_fk").trim().equals("0")){
                                    		if(csxRs.getString("trahang_fk").trim().equals("0")){
                                    			loaimh="Sản xuất";
                                        		style="color:blue;font-weight: bold;";
                                    		}else{
                                    		loaimh="Trả hàng";
                                    		style="color:blue;font-weight: bold;";
                                    		}
                                    	}
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= "200"+csxRs.getString("pk_seq") %> </TD>                                   
                                                <TD align="center" style="<%=style%>" ><%= csxRs.getString("sonhapkho")+" ("+loaimh+")" %> </TD>
                                           <%--       <TD align="center"><%= csxRs.getString("ngaysanxuat") %> </TD>
                                                <TD align="center"><%= csxRs.getString("ngayhethan") %> </TD> --%>
                                                 <TD align="left"><%= csxRs.getString("spSohoadon")%> </TD>
                                                <TD align="left"><%= csxRs.getString("spTen")%> </TD>
                                                <TD align="center"><%= csxRs.getString("soluong") %> </TD>
                                                <TD align="center"><%= csxRs.getString("soluongDat") %> </TD>
                                                <TD align="center"><%= csxRs.getString("solo") %> </TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chờ kiểm định</TD>
                                                <% } else if( tt.equals("1") )  { 
                                                	 
                                                		if(csxRs.getString("THIEUHOSO").equals("1")){ %>       
                                                		<TD align="center">Đã kiểm(Thiếu hồ sơ)</TD>
                                                		<%}else{ %>
                                                		<TD align="center">Đã kiểm</TD>
                                                		<%} %>
                                                	
                                                <% } else if(tt.equals("2")){  
                                                		if(csxRs.getString("THIEUHOSO").equals("1")){ %>       
                                                		<TD align="center">Hoàn tất(Thiếu hồ sơ)</TD>
                                                		<%}else{ %>
                                                		<TD align="center">Hoàn tất</TD>
                                                		<%} %>
                                                 <%}else { %>
                                                 	<TD align="center">
                                                 		<span style="color: red;">
                                                 		Đã hủy
                                                 		</span>
                                                 	</TD>
                                                 <%} %>
                                                <TD align="center"><%= csxRs.getString("NGAYNHAN")%> </TD>
                                                <TD align="center"><%= csxRs.getString("NGAYSUA")%> </TD>
                                                <TD align="left"><%= csxRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
                                                	<%if(quyen[2]!=0){ %>
							                   		<A href = "../../ErpKiemdinhchatluongUpdate_NhGiaySvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>&loaiMH=<%= obj.getloaimuahang()%>">
							                   			<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
							                   		<%} %>
							                   		 
												<% } else if(tt.equals("1")) { %>
													 
													<%if(quyen[2]!=0){ %>
							                   			<A href = "../../ErpKiemdinhchatluongUpdate_NhGiaySvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>&loaiMH=<%= obj.getloaimuahang()%>"">
							                   				<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
							                   		<%} %>
													<A href = "../../ErpKiemdinhchatluong_NhGiaySvl?userId=<%=userId%>&hoantat=<%= csxRs.getString("pk_seq") %>&loaiMH=<%= obj.getloaimuahang()%>">
														<IMG src="../images/Chot.png" alt="Hoàn tất" title="Hoàn tất" border=0></A>&nbsp;
												 	
													
												<% } else if(tt.equals("2")) {%>
												
													 <%if(csxRs.getString("THIEUHOSO").equals("1")){  %>
													 <A href = "../../ErpKiemdinhchatluongUpdate_NhGiaySvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>&loaiMH=<%= obj.getloaimuahang()%>">
													 	<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
													<%}  %>
													 
												<% }%>
												<A href = "../../ErpKiemdinhchatluongUpdate_NhGiaySvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>&loaiMH=<%= obj.getloaimuahang()%>">
													<IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
                                          
                                          
                                          
                                          
                                          <TR class="tbfooter" >
											<TD align="center" colspan="12"> 
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
											</TD>
										</TR>                                                  
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% 	
	try
    {
		if(csxRs != null)
			csxRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>