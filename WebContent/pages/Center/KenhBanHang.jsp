 <!-- DỮ LIỆU LINK TỪ DMS QUA  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.kenhbanhang.IKenhbanhang" %>
<%@ page  import = "geso.traphaco.center.beans.kenhbanhang.IKenhbanhangList" %>
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

<% IKenhbanhangList obj = (IKenhbanhangList)session.getAttribute("obj"); %>
<% List<IKenhbanhang> kbhlist = (List<IKenhbanhang>)obj.getKbhList(); 
	int[] quyen = new  int[6];
	quyen = util.Getquyen("KenhbanhangSvl","",userId);

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
     document.kbhForm.KenhBanHang.value = "";
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


function xuatExcel()
{
	document.forms['kbhForm'].action.value= 'excel';
 	document.forms['kbhForm'].submit();
 	document.forms['kbhForm'].action.value= '';
}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kbhForm" method="post" action="../../KenhbanhangSvl">
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
							   <TD align="left" colspan="2" class="tbnavigation">
							   		Dữ liệu nền > Kinh doanh > Kênh bán hàng</TD>
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
										  <TD class="plainlabel" >Kênh bán hàng </TD>
										  <TD class="plainlabel" ><INPUT name="KenhBanHang" size="20" type="text" value="<%= obj.getKenhbanhang()%>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Diễn giải</TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" value="<%= obj.getDiengiai()%>" onChange="submitform();" ></TD>
										</TR>
										<TR>
											<TD class="plainlabel" >Trạng thái </TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Hoạt động</option>
											  	<option value="0">Ngưng hoạt động</option>
											  	<option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>											 	 
											  	<option value="1" >Hoạt động</option>
											  	<option value="0" selected>Ngưng hoạt động</option>
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Kênh bán hàng &nbsp;&nbsp;&nbsp;
							<%-- <%if(quyen[Utility.THEM]!=0) { // TẠO MỚI TỪ DMS // ERP CHỈ ĐƯỢC XEM %>
							<a class="button3" href="javascript:newform()">
    						<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %> --%>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="4%">STT</TH>
												<TH width="12%">Kênh bán hàng </TH>
											    <TH width="22%">Diễn giải </TH>
											    <TH width="11%">Trạng thái </TH>
											    <TH width="9%">Ngày tạo</TH>
											  <TH width="13%">Người tạo </TH>										
											  <TH width="9%">Ngày sửa</TH>
											  <TH width="14%">Người sửa</TH>
											  <TH width="10%">Tác vụ</TH>
										  </TR>
								<% 
									IKenhbanhang kbh = null;
									int size = kbhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										kbh = kbhlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="left"><div align="left"><%= kbh.getKenhbanhang() %></div></TD>                                   
												<TD><div align="center"><%= kbh.getDiengiai() %></div></TD>
											  <% if (kbh.getTrangthai().equals("1")){ %>
													<TD align="center">Hoạt động</TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%=kbh.getNgaytao()%></TD>
												<TD align="center"><%=kbh.getNguoitao()%></TD>
												<TD align="center"><%=kbh.getNgaysua()%></TD>
												<TD align="center"><%=kbh.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0) {%>
													  <A href = "../../KenhbanhangUpdateSvl?userId=<%=userId%>&update=<%= kbh.getId()%>">
                                               <img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<%-- <TD>
													<%if(quyen[Utility.XOA]!=0) {%>
													  <A href = "../../KenhbanhangSvl?userId=<%=userId%>&delete=<%=kbh.getId()%>">
                                                <img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa kênh bán hàng này ?')) return false;"></A>
                                                	<%} %>
                                                </TD> --%>
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
		kbh = kbhlist.get(i);
		kbh.DBClose();
	}
		
	}%>