<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.vuviec.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IVuviecList obj = (IVuviecList)session.getAttribute("obj");  %>
<%
	List<IVuviec> kbhlist = (List<IVuviec>)obj.getDmplList();   
	int[] quyen = new  int[5];
	quyen = util.Getquyen("VuviecSvl","",userId);
	 
	 NumberFormat format = new DecimalFormat("#,###,###");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.kbhForm.ma.value = "";
     document.kbhForm.DienGiai.value = "";
     document.kbhForm.TrangThai.selectedIndex = 2;
     submitform();
 }

 function submitform()
 {
 	document.forms['kbhForm'].action.value= 'search';
 	document.forms['kbhForm'].submit();
 }

 function newform()
 {
 	document.forms['kbhForm'].action.value= 'new';
 	document.forms['kbhForm'].submit();
 }
 function thongbao()
 {var tt = document.forms['kbhForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['kbhForm'].msg.value);
 	}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kbhForm" method="post" action="../../VuviecSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

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
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Mua hàng > Vụ việc</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
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
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" >Mã vụ việc</TD>
										  <TD class="plainlabel" ><INPUT name="ma" size="20" type="text" value="<%= obj.getMa()%>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Tên vụ việc</TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" value="<%= obj.getDiengiai()%>" onChange="submitform();" ></TD>
										</TR>
										<TR>
											<TD class="plainlabel" >Trạng thái </TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã chốt</option>
											  	<option value="0">Chưa chốt</option>
											  	<option value="2">Hoàn tất </option>
											  	<option value=""> </option>
											<%}else if(obj.getTrangthai().equals("0")) {%>											 	 
											  	<option value="1" >Đã chốt</option>
											  	<option value="0" selected>Chưa chốt</option>
											 	<option value="2" >Hoàn tất  </option>
											  	<option value=""> </option>
											<%}else if(obj.getTrangthai().equals("2")) {%>											 	 
											  	<option value="1" >Đã chốt</option>
											  	<option value="0" >Chưa chốt</option>
											 	<option value="2" selected>Hoàn tất  </option>
											  	<option value=""> </option>
											<%}else{%>																						 	 
											  	<option value="1" >Đã chốt</option>
											  	<option value="0" >Chưa chốt</option>
											  	<option value="2" >Hoàn tất  </option>
											  	<option value="" selected> </option>
											<%}%>
									          </select></TD>
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
							<LEGEND class="legendtitle">&nbsp;Vụ việc &nbsp;&nbsp;&nbsp;
							
<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
    	<%} %>                            
							
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="7%">Mã vụ việc </TH>
											    <TH width="25%">Tên vụ việc </TH>
											    <TH width="11%">Trạng thái </TH>
											    <TH width="9%">Ngày tạo</TH>
											  <TH width="10%">Người tạo </TH>										
											  <TH width="9%">Ngày sửa</TH>
											  <TH width="10%">Người sửa</TH>
											  <TH width="15%">Tác vụ</TH>
										  </TR>
								<% 
								    IVuviec dmpl = null;
									int size = kbhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										dmpl = kbhlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%= dmpl.getMa() %></div></TD>                                   
												<TD><div align="center"><%= dmpl.getDiengiai() %></div></TD>
											  <% if (dmpl.getTrangthai().equals("1")){ %>
													<TD align="center">Đã chốt</TD>
												<%} else if (dmpl.getTrangthai().equals("2")){ %>
													<TD align="center">Hoàn tất</TD>
													<%} else if (dmpl.getTrangthai().equals("3")){ %>
													<TD align="center">Đã xóa</TD>
												<%}else{%>
													<TD align="center">Chưa chốt</TD>
												<%}%>
												<TD align="center"><%=dmpl.getNgaytao()%></TD>
												<TD align="center"><%=dmpl.getNguoitao()%></TD>
												<TD align="center"><%=dmpl.getNgaysua()%></TD>
												<TD align="center"><%=dmpl.getNguoisua()%></TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													 <% if (dmpl.getTrangthai().equals("0")){ %>
													 <TD>
													<%if(quyen[2]!=0){ %>														
														  <A href = "../../VuviecUpdateSvl?userId=<%=userId%>&update=<%= dmpl.getId()%>">
	                                              		 <img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
	                                               <%} %>
													</TD>
													<TD>													
													<%if(quyen[4]!=0){ %>
					                                	 <A href = "../../VuviecSvl?userId=<%=userId%>&chot=<%=dmpl.getId()%>">
					                                 	<img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn chốt vụ việc này ?')) return false;"></A>
											       <%} %>
											       </TD>
											       <TD>
														  <A href = "../../VuviecUpdateSvl?userId=<%=userId%>&display=<%= dmpl.getId()%>">
	                                               		 <img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
														  <A href = "../../VuviecSvl?userId=<%=userId%>&delete=<%=dmpl.getId()%>">
	                                                	  <img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa vụ việc này ?')) return false;"></A>&nbsp; 
	                                                <%} %>
	                                                </TD>
													
													
													<%}else if(dmpl.getTrangthai().equals("1")) { %>
													<TD>
													<%if(quyen[2]!=0){ %>														
														   <A href = "../../VuviecSvl?userId=<%=userId%>&hoantat=<%=dmpl.getId()%>">
	                                                	  <img title="Hoàn tất" src="../images/hoantat.gif" alt="HoanTat" width="20" height="20" longdesc="Hoantat" border=0 onclick="if(!confirm('Bạn có muốn hoàn tất vụ việc này ?')) return false;"></A>&nbsp; 
	                                               <%} %>
													</TD>
													<TD>
														  <A href = "../../VuviecUpdateSvl?userId=<%=userId%>&display=<%= dmpl.getId()%>">
	                                               		 <img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													<%}else if(dmpl.getTrangthai().equals("3")) { %>
													<TD>
														  <A href = "../../VuviecUpdateSvl?userId=<%=userId%>&display=<%= dmpl.getId()%>">
	                                               		 <img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													<%}else{ %>	
													<TD>
														  <A href = "../../VuviecUpdateSvl?userId=<%=userId%>&display=<%= dmpl.getId()%>">
	                                               		 <img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													<%} %>
													</TR></TABLE>
												</TD>
												
											</TR>
								<%m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
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
<%
	obj.DBClose();
	for(int i = 0; i < kbhlist.size(); i++){
		dmpl = kbhlist.get(i);
		dmpl.DBClose();
	}
		
	}%>	