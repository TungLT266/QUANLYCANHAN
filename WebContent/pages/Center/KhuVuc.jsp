 <!-- DỮ LIỆU LINK TỪ DMS QUA  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.beans.khuvuc.IKhuvuc" %>
<%@ page  import = "geso.traphaco.center.beans.khuvuc.IKhuvucList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IKhuvucList obj = (IKhuvucList)session.getAttribute("obj"); %>
<% List<IKhuvuc> kvlist = (List<IKhuvuc>)obj.getKvList(); %>
<% ResultSet vungmien = (ResultSet)obj.getVungMien();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("KhuvucSvl","",userId);
	
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
	
	document.kvForm.VungMien.selectedIndex = 0;
    //document.kvForm.DienGiai.value = "";      
    document.kvForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	//alert("hello");
	document.forms['kvForm'].action.value= 'search';
	document.forms['kvForm'].submit();
	
}

function newform()
{
	document.forms['kvForm'].action.value= 'new';
	document.forms['kvForm'].submit();
}
function thongbao()
{
	var tt = document.forms['kvForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['kvForm'].msg.value);
}

function xuatExcel()
{
	document.forms['kvForm'].action.value= 'excel';
 	document.forms['kvForm'].submit();
 	document.forms['kvForm'].action.value= '';
}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kvForm" method="post" action="../../KhuvucSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">Dữ liệu nền > Kinh doanh > Khu Vực </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="0">			
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
						    
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								    <TD width="20%" class="plainlabel">Vùng miền</TD>
								      <TD width="80%" class="plainlabel"><SELECT name="VungMien" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(vungmien.next()){ 
								    	if(vungmien.getString("vmId").equals(obj.getVmId())){ %>
								      		<option value='<%= vungmien.getString("vmId") %>' selected> <%= vungmien.getString("vmTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= vungmien.getString("vmId") %>'> <%= vungmien.getString("vmTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								</TR>
								  <TR>
								    <TD class="plainlabel">Trạng thái </TD>
								    <TD class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();" >
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected>Hoạt động</option>
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
											
                                    </SELECT></TD>
						      </TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
									
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
			<TABLE width="100%" cellpadding="0" cellspacing="0">			
				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Khu Vực &nbsp;&nbsp;
							<%-- <%if(quyen[Utility.THEM]!=0) {%>
									<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %> --%>
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="13%">Khu Vực </TH>
									<TH width="13%">Diễn giải </TH>
									<TH width="13%">Vùng miền </TH>
									<TH width="8%">Trạng thái </TH>
									<TH width="6%">Ngày tạo </TH>
									<TH width="8%">Người tạo </TH>
									<TH width="6%">Ngày sửa</TH>
									<TH width="8%">Người sửa </TH>
									<TH width="6%" align="center">&nbsp;Tác vụ</TH>
								</TR>
							
								<% 
									IKhuvuc kv = null;
									int size = kvlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										kv = kvlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="left"><div align="left"><%= kv.getTen() %></div></TD>
											    <TD align="left"><div align="left"><%= kv.getDiengiai() %></div></TD>
												<TD><div align="center"><%= kv.getVungmien() %></div></TD>                            
												<% if (kv.getTrangthai().equals("Hoat dong")){ %>
													<TD align="center">Hoạt động </TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%= kv.getNgaytao() %></TD>
										 		<TD align="center"><%= kv.getNguoitao() %></TD>
												<TD align="center"><%= kv.getNgaysua() %></TD>
												<TD align="center"><%= kv.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%-- <%if(quyen[Utility.SUA]!=0) {%> --%>
														<A href = "../../KhuvucUpdateSvl?userId=<%=userId%>&update=<%=kv.getId()%>"><img src="../images/Display20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%-- <%} %> --%>
													</TD>
													<TD>&nbsp;</TD>
													<%-- <TD>
													<%if(quyen[Utility.XOA]!=0) {%>
														<A href = "../../KhuvucSvl?userId=<%=userId%>&delete=<%=kv.getId()%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Vùng này ?')) return false;"></A></TD>
													<%} %> --%>
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
	obj.closeDB();
	for(int i = 0; i < kvlist.size(); i++){
		kv = kvlist.get(i);
		kv.closeDB();
	}
}%>