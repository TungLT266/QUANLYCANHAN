<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.INhomkhuyenmaiList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("NhomkhuyenmaiSvl","",userId);
%>

<% INhomkhuyenmaiList obj = (INhomkhuyenmaiList)session.getAttribute("obj"); %>
<% //List<INhomkhuyenmai> nkmlist = (List<INhomkhuyenmai>)obj.getNkmList(); %>
<% ResultSet Dsnkm = (ResultSet)obj.getDsnkm(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nkmForm.action.value = "new";
   	 document.forms["nkmForm"].submit();
}

function searchform()
{
	 document.nkmForm.action.value = "search";
   	 document.forms["nkmForm"].submit();
}

function clearform()
{
	 	document.forms["nkmForm"].submit();
}
function xuatExcel()
{
	document.forms['nkmForm'].action.value= 'toExcel';
	document.forms['nkmForm'].submit();
	document.forms['nkmForm'].action.value= '';
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkmForm" method="post" action="../../NhomkhuyenmaiSvl">
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
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại &gt; Khai báo &gt; Nhóm sản phẩm khuyến mại </TD> 
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
								      	<option value=""> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else if (obj.getTrangthai().equals("1")){%>		
									  				
								  		<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%} else {%>
									   <option value="" selected> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
                                        <%} %>
										</SELECT></TD>
									
								</TR>
								
										<TR>
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input  class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange = "searchform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input  class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange = "searchform();">
										  		</TD>
										

											  </TR>
											 </TABLE>
									  </TR>
								<TR>
									<TD class="plainlabel">
									
                                 <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	

<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;

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
					<LEGEND class="legendtitle">&nbsp;Nhóm khuyến mại &nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0) {%>
					<a class="button3" href="javascript:submitform()">
    	<img style="top: -4px;" src="../images/New.png" >Tạo mới </a>                            
					<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="9%">Tên nhóm</TH>
									<TH width="19%">Diễn giải </TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="13%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="12%">Người sửa</TH>
									<TH width="9%">Tác vụ</TH>
								</TR>
						<% 
							INhomkhuyenmai nkm = null;
						//	int size = nkmlist.size();
							int m = 0;
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (Dsnkm.next()){
								//nkm = nkmlist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
															
							
									<TD align="left"><%=Dsnkm.getString("ten") %></TD>
							
									<TD><%=Dsnkm.getString("diengiai") %></TD>

									<% if(Dsnkm.getString("trangthai").equals("1")) {%>
										<TD align="center">Hoạt động</TD>
									<%}else {%>
										<TD align="center">Ngưng hoạt động</TD>
									<%} %>
									<TD align="center"><%=Dsnkm.getString("ngaytao") %></TD>
									<TD align="center"><%=Dsnkm.getString("nguoitao") %></TD>
									<TD align="center"><%=Dsnkm.getString("ngaysua") %></TD>
									<TD align="center"><%=Dsnkm.getString("nguoisua") %> </TD>
									<TD align="center">
										<TABLE>
											<TR><TD>
												<%if(quyen[Utility.SUA]!=0) {%>
												<A href = "../../NhomkhuyenmaiUpdateSvl?userId=<%=userId%>&update=<%=Dsnkm.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} %>
											</TD>
											<td>
												<%if(quyen[Utility.XOA]!=0){ %>
		                                		<A href = "../../NhomkhuyenmaiSvl?userId=<%=userId%>&delete=<%= Dsnkm.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm khuyến mại này?')) return false;"></A>									
						                    	<%} %>
											</td>
											<TD>&nbsp;</TD>											
											
											</TABLE>									
									</TD>
								</TR>
							<%m++; }%>
							<TR class="tbfooter">
								<TD align="center" colspan="10"> |< < 1 to 20 of 100 > >|</TD>
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
</HTML>
<%
if(Dsnkm!=null){
	Dsnkm.close();
	
}

}%>