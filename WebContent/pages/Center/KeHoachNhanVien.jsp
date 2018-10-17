<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVien" %>
<%@ page  import = "geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVienList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

<% IKeHoachNhanVienList obj = (IKeHoachNhanVienList)session.getAttribute("obj"); %>
<% ResultSet khnvRs = (ResultSet)obj.getKhnvRs(); 
int[] quyen = new  int[4];
	quyen = util.Getquyen("KeHoachNhanVienSvl","121",userId);	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.khnvForm.Thang.selectedIndex = 0;
     document.khnvForm.Nam.selectedIndex = 0;
     submitform();
 }

 function submitform()
 {
 	document.forms['khnvForm'].action.value= 'search';
 	document.forms['khnvForm'].submit();
 }

 function newform()
 {
 	document.forms['khnvForm'].action.value= 'new';
 	document.forms['khnvForm'].submit();
 }
 
 function thongbao()
 {var tt = document.forms['khnvForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['khnvForm'].msg.value);
 	} 
 
</SCRIPT>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khnvForm" method="post" action="../../KeHoachNhanVienSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		Dữ liệu nền > Cơ bản > Kế hoạch nhân viên </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
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
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm   </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD class="plainlabel" > Loại nhân viên </TD>
											<TD class="plainlabel" >
												<select name="Loai" onChange="submitform();">
													<option value=""></option>
													<option value="1" <%=obj.getLoai().equals("1") ? "selected" : ""%>>RSM</option>
													<option value="2" <%=obj.getLoai().equals("2") ? "selected" : ""%>>ASM</option>
													<option value="3" <%=obj.getLoai().equals("3") ? "selected" : ""%>>GSBH</option>
												</select>
											</TD>
										</TR>
										<TR>
											<TD width="20%" class="plainlabel" >Tháng </TD>
										    <TD width="80%" class="plainlabel" >
												<INPUT name="Thang" size="40" type="text" value="<%= obj.getThang()%>" onChange="submitform();">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" > Năm </TD>
											<TD class="plainlabel" >
												<INPUT name="Nam" size="40" type="text" value="<%= obj.getNam()%>" onChange="submitform();">
											</TD>
										</TR>
										<TR>
                                              <TD class="plainlabel" colspan=2>
                                              <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                              
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
							<LEGEND class="legendtitle">&nbsp;Kế hoạch nhân viên &nbsp;&nbsp;&nbsp; 
							<%if(quyen[0]!=0) {%>
							<a class="button3" href="javascript:newform()">
								<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
							</LEGEND> 
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">STT</TH>
												<TH width="10%">Tên nhân viên </TH>
											    <TH width="10%">Tháng </TH>
											    <TH width="10%">Năm </TH>
											    <TH width="10%">Trạng thái </TH>
											    <TH width="10%">Ngày tạo</TH>
												<TH width="10%">Người tạo </TH>										
												<TH width="10%">Ngày sửa </TH>
												<TH width="10%">Người sửa</TH>
												<TH width="10%">Tác vụ</TH>
											</TR>
								<% 
									if(khnvRs != null) 
									{
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										String trangthai = "";
										while (khnvRs.next()) {
											trangthai = khnvRs.getString("trangthai") == null ? "Chưa duyệt" : khnvRs.getString("trangthai").equals("1") ? "Đã duyệt" : "Chưa duyệt";
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
											<%} else {%>
												<TR class= <%=darkrow%> >
											<%}%>
													<TD align="center"><%=m+1 %></TD>
													<TD align="center"><%= khnvRs.getString("nhanvien") %></TD>                                   
													<TD align="center"><%= khnvRs.getString("thang") %></TD>
													<TD align="center"><%= khnvRs.getString("nam") %></TD>
													<TD align="center"><%= trangthai %></TD>
													<TD align="center"><%= khnvRs.getString("ngaytao") %></TD>
													<TD align="center"><%= khnvRs.getString("nguoitao") %></TD>
													<TD align="center"><%= khnvRs.getString("ngaysua") %></TD>
													<TD align="center"><%= khnvRs.getString("nguoisua") %></TD>
													<TD align="center">
														<TABLE border = 0 cellpadding="0" cellspacing="0">
															<TR>
																<TD>
																	<A href = "../../KeHoachNhanVienUpdateSvl?userId=<%=userId%>&display=<%= khnvRs.getString("pk_seq") %>">
																		<img src="../images/Display20.png" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
																</TD>
																<TD>&nbsp;</TD>
															<% if(khnvRs.getString("trangthai").equals("0")) {
																if(khnvRs.getString("loaikehoach").equals("0")) { %>
																	<TD>
																		<A href = "../../KeHoachNhanVienSvl?userId=<%=userId%>&duyet=<%= khnvRs.getString("pk_seq") %>" onclick="if(!confirm('Bạn có muốn duyệt kế hoạch của nhân viên này?')) return false;">
																			<img src="../images/Chot.png" title="Duyệt" width="20" height="20" longdesc="Duyệt" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
															<%  } else { %>
																	<%if(quyen[2]!=0) {%>
																	<TD>
																		<A href = "../../KeHoachNhanVienUpdateSvl?userId=<%=userId%>&update=<%= khnvRs.getString("pk_seq") %>">
																			<img src="../images/Edit20.png" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
																	<%} %>
																	<%if(quyen[1]!=0) {%>
																	<TD>
																		<A href = "../../KeHoachNhanVienSvl?userId=<%=userId%>&delete=<%= khnvRs.getString("pk_seq") %>">
																			<img src="../images/Delete20.png" title="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn có muốn xóa kế hoạch không ?')) return false;"></A>
				                                                	</TD>
				                                                	<%} %>
				                                                <%}
															} else { 
																if(khnvRs.getString("loaikehoach").equals("0")) { %>
																	<TD>
																		<A href = "../../KeHoachNhanVienSvl?userId=<%=userId%>&moduyet=<%= khnvRs.getString("pk_seq") %>" onclick="if(!confirm('Bạn có muốn mở duyệt kế hoạch của nhân viên này?')) return false;">
																			<img src="../images/unChot.png" title="Duyệt" width="20" height="20" longdesc="Duyệt" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
															<%	}
															}%>
															</TR>
														</TABLE>
													</TD>
												</TR>
									<%		m++; 
										}
									}
								%>
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
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
</BODY>
</HTML>
<% obj.closeDB(); %>
<%}%>