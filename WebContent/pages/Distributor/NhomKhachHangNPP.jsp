<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.nhomkhachhang.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhomkhachhangListNPP obj = (INhomkhachhangListNPP)session.getAttribute("obj"); %>
<% List<INhomkhachhangNPP> nkhlist = (List<INhomkhachhangNPP>)obj.getNkhList(); 
	int[] quyen = new  int[4];
	quyen = util.Getquyen("NhomkhachhangNPPSvl",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nkhForm.action.value = "new";
   	 document.forms["nkhForm"].submit();
}

function searchform()
{
	 document.nkhForm.action.value = "search";
   	 document.forms["nkhForm"].submit();
}

function clearform()
{
	document.nkhForm.maKH.value = "";
	document.nkhForm.diengiai.value = "";
    document.nkhForm.trangthai.selectedIndex = 0;
	document.nkhForm.tungay.value = "";
	document.nkhForm.denngay.value = "";
 	document.forms["nkhForm"].submit();
}
function xuatExcel()
{
	document.forms['nkhForm'].action.value= 'excel';
	document.forms['nkhForm'].submit();
	document.forms['nkhForm'].action.value= '';
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkhForm" method="post" action="../../NhomkhachhangNPPSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu &gt; Dữ liệu nền kinh doanh&gt; Nhóm khách hàng</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
							
															<TR>
									<TD width="19%" class="plainlabel">Mã nhóm khách hàng </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="maKH" type="text" size="40" value='<%=obj.getMaKH()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								
								<TR>
									<TD width="19%" class="plainlabel">Diễn giải </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value="2"> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value="2"> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="2" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>

										</SELECT></TD>
									
								</TR>
								
										<TR>
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onFocus = "searchform();">
													</TD>
													<TD>
<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar'; return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'nkhForm.tungay',false,'yyyy-mm-dd');  return false;">
		  &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
			   										</TD>
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onFocus = "searchform();">
										  		</TD>
										<TD>
<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'nkhForm.denngay',false,'yyyy-mm-dd'); return false;">
		  &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>

                                        </TD>

											  </TR>
											 </TABLE>
									  </TR>
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                  </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhóm khách hàng &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0) {%>
					<a class="button3" href="javascript:submitform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="4%">Mã nhóm</TH>
									<TH width="19%">Diễn giải </TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="11%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="11%">Người sửa 
									<TH width="8%">Tác vụ</TH>
								</TR>
						<% 
							INhomkhachhangNPP nkh = null;
							int size = nkhlist.size();
							int m = 0;
							Hashtable parent = new Hashtable();
							parent.put("0", "* ");
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (m < size){
								nkh = nkhlist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
									<TD align="center"><%=m+1 %></TD>
									<TD><%=nkh.getTen()%></TD>
									<TD><%=nkh.getDiengiai() %></TD>
									<% if(nkh.getTrangthai().equals("1")) {%>
										<TD align="center">Hoạt động</TD>
									<%}else {%>
										<TD align="center">Ngưng hoạt động</TD>
									<%} %>
									<TD align="center"><%=nkh.getNgaytao() %></TD>
									<TD align="center"><%=nkh.getNguoitao() %></TD>
									<TD align="center"><%=nkh.getNgaysua() %></TD>
									<TD align="center"><%=nkh.getNguoisua() %> </TD>
									<TD align="center">
										<TABLE>
											<TR><TD>
											<%if(quyen[2]!=0) {%>
												<A href = "../../NhomkhachhangNPPUpdateSvl?userId=<%=userId%>&update=<%=nkh.getId()%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
											<%} %>
											</TD>
											<TD>&nbsp;</TD>
											<TD>
											<%if(quyen[1]!=0) {%>
												<A href = "../../NhomkhachhangNPPSvl?userId=<%=userId%>&delete=<%=nkh.getId()%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Co Muon Xoa Nhom Khach Hang Nay?')) return false;"></A>
												<%} %>
												</TD>
											
											</TR>												
										</TABLE>									
									</TD>
								</TR>
							<%m++; }%>
							
								<TR>
									<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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
for(int i=0; i<nkhlist.size();i++)
{
	nkhlist.get(i).DBClose();
}
	
	}%>