<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.loaicuahang.ILoaicuahang" %>
<%@ page  import = "geso.traphaco.center.beans.loaicuahang.ILoaicuahangList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ILoaicuahangList obj = (ILoaicuahangList)session.getAttribute("obj"); %>
<% List<ILoaicuahang> lchlist = (List<ILoaicuahang>)obj.getLchList(); 
int[] quyen = new  int[4];
	quyen = util.Getquyen("19", "", userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
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
     document.lchForm.LoaiCuaHang.value = "";
     document.lchForm.DienGiai.value = "";      
     document.lchForm.TrangThai.selectedIndex = 2;
     submitform();
 }

 function submitform()
 {
 	document.forms['lchForm'].action.value= 'search';
 	document.forms['lchForm'].submit();
 }

 function newform()
 {
 	document.forms['lchForm'].action.value= 'new';
 	document.forms['lchForm'].submit();
 }
 
 function thongbao()
 {var tt = document.forms['lchForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['lchForm'].msg.value);
 	} 
 
 
 function xuatExcel()
 {
 	document.forms['lchForm'].action.value= 'excel';
 	document.forms['lchForm'].submit();
 	document.forms['lchForm'].action.value= '';
 }
 
</SCRIPT>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="lchForm" method="post" action="../../LoaicuahangSvl">
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
							   		Dữ liệu nền > Kinh doanh > Loại cửa hàng </TD>
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
										  <TD class="plainlabel" >Loại cửa hàng </TD>
										  <TD class="plainlabel" ><INPUT name="LoaiCuaHang" size="20" type="text" 
                                          					value="<%= obj.getLoaicuahang() %>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Diễn giải </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" 
                                            				value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" > Trạng thái </TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected> Hoạt động </option>
											  	<option value="0">Ngưng hoạt động</option>
											  	<option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	 <option value="0" selected>Ngưng hoạt động</option>
											  	<option value="1" >Hoạt động</option>
											 	 <option value="2" > </option>
											  
											<%}else{%>																						 	 
											  	<option value="1" >Hoạt động</option>
											  	<option value="0" >Ngưng hoạt động</option>
											  	<option value="2" selected> </option>
											<%}%>
									          </select></TD>
										</TR>
										<TR>
                                              <TD class="plainlabel" colspan=2>
                                              <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                              	<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
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
							<LEGEND class="legendtitle">&nbsp;Loại của hàng &nbsp;&nbsp;&nbsp; 
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
												<TH width="4%">STT</TH>
												<TH width="12%">Loại cửa hàng </TH>
											    <TH width="22%">Diễn giải </TH>
											    <TH width="11%">Trạng thái </TH>
											    <TH width="9%">Ngày tạo</TH>
											  <TH width="13%">Người tạo </TH>										
											  <TH width="9%">Ngày sửa </TH>
											  <TH width="14%">Người sửa</TH>
											  <TH width="10%">Tác vụ</TH>
										  </TR>
								<% 
									ILoaicuahang lch = null;
									int size = lchlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										lch = lchlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1 %></TD>
												<TD align="left"><div align="left"><%= lch.getLoaicuahang() %></div></TD>                                   
												<TD><div align="left"><%= lch.getDiengiai() %></div></TD>
											  <% if (lch.getTrangthai().equals("1")){ %>
													<TD align="left">Hoạt động</TD>
												<%}else{%>
													<TD align="left">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%=lch.getNgaytao()%></TD>
												<TD align="center"><%=lch.getNguoitao()%></TD>
												<TD align="center"><%=lch.getNgaysua()%></TD>
												<TD align="center"><%=lch.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0) {%>
													  <A href = "../../LoaicuahangUpdateSvl?userId=<%=userId%>&update=<%= lch.getId()%>">
                                               <img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0) {%>
													  <A href = "../../LoaicuahangSvl?userId=<%=userId%>&delete=<%=lch.getId()%>">
                                                <img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa loại cửa hàng này không ?')) return false;"></A>
                                                	<%} %>
                                                </TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
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