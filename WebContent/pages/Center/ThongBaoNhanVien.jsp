<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="geso.traphaco.center.beans.thongbao.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% IThongbaoList thongbao = (IThongbaoList)session.getAttribute("obj"); %>
<% ResultSet tbList = thongbao.getThongbaoList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% thongbao.setNextSplittings();
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{
int[] quyen = new  int[6];
quyen = util.Getquyen("ThongbaoSvl","&viewMode=" + thongbao.getViewMode() + "&loaivanban=" + thongbao.getLoaithongbao() + "&task=" + thongbao.getTask(), userId);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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
<script type="text/javascript">
	function clearform()
	{
	    document.forms["ctForm"].maso.value = "";
	    document.forms["ctForm"].ngaybatdau.value = "";
	    document.forms["ctForm"].ngayketthuc.value = "";
	    document.forms["ctForm"].tieude.value = "";
	    document.forms["ctForm"].trangthai.value = "";
	    Submitform();
	}
	function Submitform()
	{
		document.forms["ctForm"].action.value = "nhanvien";
		document.forms["ctForm"].submit();
	}
	function BackHome()
	{
		document.forms["ctForm"].action.value = "backhome";
		document.forms["ctForm"].submit();
	}
	function TaoMoi()
	{
		document.forms["ctForm"].action.value = "new";
		document.forms["ctForm"].submit();
	}
	function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
</script>
</head>
<body >
<form name="ctForm" method="post" action="../../ThongbaoSvl" >
<input type="hidden" name="currentPage" value="<%= thongbao.getCurrentPage() %>" >
<input type="hidden" name="action">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top'>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		Quản trị thông báo > Chức năng > Hộp thư đến </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn &nbsp;<%=userTen%>  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left">
								<FIELDSET >
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
									   <TR>
										  <TD class="plainlabel" width="120px" >Ngày bắt đầu</TD>
										  <TD class="plainlabel" width="220px" ><input type="text" class="days" id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" width="120px" >Ngày kết thúc</TD>
										  <TD class="plainlabel"  ><input type="text" class="days" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc()%>" onchange="Submitform()" ></TD>
									   </TR>
									   <TR>
										  <TD class="plainlabel" >Tiêu đề</TD>
										  <TD class="plainlabel" ><input type="text" id="tieude"  name="tieude" size="20" value="<%= thongbao.getTieude()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" >Nội dung</TD>
										  <TD class="plainlabel" ><input type="text" id="noidung"  name="noidung" size="20" value="<%= thongbao.getNoidung() %>" onchange="Submitform()" ></TD>
									   </TR>

									   <TR>
										  <TD class="plainlabel" >Mã số</TD>
										  <TD class="plainlabel" ><input type="text" id="maso"  name="maso" size="20" value="<%= thongbao.getId()%>" onchange="Submitform()" ></TD>
										  
									   
										<TD class="plainlabel">Trạng thái</TD>
										<TD class="plainlabel">
											<select name="trangthai" name="trangthai" onchange="Submitform()" >
											  	<% if (thongbao.getTrangthai().equals("0")){ %>
											  		<option value=""></option>
											    	<option value="0" selected>Chưa xem</option>
											    	<option value="1" >Đã xem</option>
												
												<%	}else if(thongbao.getTrangthai().equals("1")){%>
												    <option value=""></option>
												    <option value="0" >Chưa xem</option>
											    	<option value="1" selected>Đã xem </option>	
												   
												<%}else{ %>
													<option value=""></option>
												    <option value="0" >Chưa xem</option>
											    	<option value="1">Đã xem </option>	
												<%} %>
									    	</select>
									    </TD>
								</TR>
										<TR>
                                             <TD class="plainlabel" colspan='1'> 
                                             <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                             </TD>
                                              <TD class="plainlabel" colspan='4'> 
                                             <a class="button2" href="javascript:BackHome()">
	<img style="top: -4px;height:32px; witdh:32px;" src="../images/home.png" alt="">Trang chủ </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Thông báo&nbsp;&nbsp;&nbsp;
							
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH align="center" width="10%" >Mã</TH>
											  	<TH align="center" width="30%" >Tiêu đề</TH>
											  	<TH align="center" width="10%" >Loại</TH>
							                    <TH align="center" width="10%" >Trạng thái</TH>
							                    <TH align="center" width="10%" > Ngày bắt đầu </TH>
							                    <TH align="center" width="10%" >Ngày kết thúc</TH>
							                    <TH align="center" width="10%" >Người gửi</TH>
							                    <TH align="center" width="10%"  >Tác vụ</TH>
										  	</TR>									
												<%
												if(tbList != null)
												{
													try
													{
														int m = 0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while (tbList.next())
														{
															if (m % 2 != 0) 
															{%>						
																<TR class= <%=lightrow%> >
															<%} 
															else 
															{%>
																<TR class= <%= darkrow%> >
															<%}%>
															<TD align="center"><%= tbList.getString("PK_SEQ") %></TD>
															<%
																String filename=tbList.getString("filename");
																if(filename.equals("0")){
																%>
																<TD><%= tbList.getString("TIEUDE") %></TD> 
															<%}else { %>
																<TD ><%= tbList.getString("TIEUDE") %><img src="../images/paperclip.png"></img></TD> 
															<%} %>
															<TD> <%= tbList.getString("loaithongbao").equals("5") ? "Thông báo" : "Văn bản" %> </TD>  									                                    
													     	<% if(tbList.getString("trangthai").trim().equals("0")){%>
				                                                 <TD align="center" style="color: red;" >Chưa xem</TD>
				                                                 <%}else{ %>
				                                                  <TD align="center">Đã xem</TD>
				                                                 <%} %>	
										                    <TD align="center"><%= tbList.getString("ngaybatdau") %></TD>
								         					<TD align="center"><%= tbList.getString("ngayketthuc") %></TD>
								         					<TD align="center"><%= tbList.getString("tennv") %></TD>
															<TD align="center" >
															
														  		<A href="../../ThongbaoUpdateSvl?userId=<%= userId %>&id=<%= tbList.getString("PK_SEQ") %>&task=capnhatnv&viewMode=0">
	                                               				<img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
			                                               				
															</TD>
														</TR>
														<% m++; 
			                                                }
														tbList.close();
														}
														catch(SQLException ex){ System.out.print("Loi roi....");
														ex.printStackTrace();
														}
														}
														%>
												
									<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% thongbao.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= thongbao.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= thongbao.getNxtApprSplitting() %>" >
											 	<%if(thongbao.getNxtApprSplitting() >1) {%>
													<!-- <img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'viewnv')"> &nbsp; -->
												<%}else {%>
													<!-- <img alt="Trang Dau" src="../images/first.gif" > &nbsp; -->
													<%} %>
												<% if(thongbao.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'viewnv')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = thongbao.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + thongbao.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == thongbao.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'viewnv')"></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(thongbao.getNxtApprSplitting() < thongbao.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'viewnv')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(thongbao.getNxtApprSplitting() == thongbao.getTheLastSplitting()) {%>
											   		<!-- <img alt="Trang Cuoi" src="../images/last.gif" > &nbsp; -->
										   		<%}else{ %>
										   			<!-- <img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'viewnv')"> &nbsp; -->
										   		<%} %>
						</TD>
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
<%
thongbao.DBClose();
%>
</form>
</body>
<%} %>
</html>