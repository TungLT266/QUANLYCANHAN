<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.beans.banggiablkh.IBanggiablKh" %>
<%@ page  import = "geso.traphaco.center.beans.banggiablkh.IBanggiablKhList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IBanggiablKhList obj = (IBanggiablKhList)session.getAttribute("obj"); %>
<% ResultSet bgblclist = obj.getBglist(); %>
<%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BanggiabanlechuanSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.bgblcForm.bgTen.value = "";
    document.bgblcForm.dvkdId.selectedIndex = 0;
    document.bgblcForm.trangthai.selectedIndex = 0;
    submitform();   
}

function submitform()
{
	document.bgblcForm.action.value= 'search';
	document.forms['bgblcForm'].submit();
}

function newform()
{
	document.forms['bgblcForm'].action.value= 'new';
	document.forms['bgblcForm'].submit();
}

</SCRIPT>
</HEAD>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgblcForm" method="post" action="../../BanggiabanleKhSvl">
<input type="hidden" name="userId" value='<%=obj.getUserId() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" class="tbnavigation">
				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR height="22">
					   <TD align="left" colspan="2" class="tbnavigation">
					   		&nbsp;Dữ liệu nền &gt;Sản phẩm &gt; Bảng giá bán lẻ khách hàng</TD>
					   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD>
					</TR>
				  </TABLE>
				</TD>
			</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">				
				<TR>
					<TD>
					<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo</LEGEND>							
				    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" ><%=obj.getMsg()%></textarea>
									<% obj.setMsg(""); %>
								</FIELDSET>
							</TD>
						</TR>
						<TR>
							<TD width="100%" align="left"><FIELDSET>
							<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Mã Sản phẩm</TD>
									<TD class="plainlabel"><INPUT name ="spma" type="text" value="<%= obj.getSpMa() %>" size="40" onChange = "submitform();"/></TD>
									<%-- <TD class="plainlabel">Đơn vị kinh doanh  </TD>
								    <TD class="plainlabel"><SELECT  name="dvkdId"  onChange = "submitform();">
								  		<option value =""></option>
								  		
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD> --%>
								</TR>
								<TR>
									<TD class="plainlabel">Tên Sản phẩm</TD>
									<TD class="plainlabel"><INPUT name ="spten" type="text" value="<%= obj.getSpTen() %>" size="40" onChange = "submitform();"/></TD>
								</TR>
								
								<%-- <TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
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
								    	</select></TD>
								    <TD class="plainlabel" colspan="2">
										<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform()" >Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                	</TD>					
								</TR> --%>
								
								
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
					<LEGEND class="legendtitle">&nbsp; Bảng giá bán &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0) {%>
					<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="" onClick="newform();">Tạo mới </a>
					<%} %>
					</LEGEND>
	
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH >Mã</TH>
									<TH >Sản phẩm</TH>
									<TH width="9%">Ngày tạo</TH>
									<TH width="15%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="15%">Người sửa </TH>
									<TH width="8%" align="center">&nbsp;Tác vụ</TH>
								</TR>

									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (bgblclist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="left"><div align="left"><%= bgblclist.getString("MA")%></div></TD>     
												<TD align="left"><div align="left"><%= bgblclist.getString("TENSP")%></div></TD>                             
												<TD align="center"><div align="center"><%=bgblclist.getString("NGAYTAO") %></div></TD>
												<TD align="center"><%=bgblclist.getString("NGUOITAO")%></TD>
												<TD align="center"><%=bgblclist.getString("NGAYSUA")%></TD>
												<TD align="center"><%=bgblclist.getString("NGUOISUA")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0){ %>
														<A href = "../../BanggiabanleKhUpdateSvl?userId=<%=userId%>&update=<%= bgblclist.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>

													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
								<TR>
									<TD align="center" colspan="10" class="tbfooter">&nbsp;		</TD>
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
</body>
</html>
<%
	if(bgblclist != null)
		bgblclist.close(); 
	obj.DbClose();
	}
	
%>