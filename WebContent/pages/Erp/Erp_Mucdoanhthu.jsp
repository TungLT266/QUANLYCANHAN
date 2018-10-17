<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.doanhthu.IMucdoanhthu" %>
<%@ page  import = "geso.traphaco.erp.beans.doanhthu.IMucdoanhthuList" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IMucdoanhthuList obj = (IMucdoanhthuList)session.getAttribute("obj"); %>
<% 	ResultSet dtlist = (ResultSet)obj.getDtList(); 
	
	ResultSet dvttlist = (ResultSet)obj.getDvttList() ;
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("Erp_MucdoanhthuSvl","",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.DtForm.action.value = "new";
   	 document.forms["DtForm"].submit();
}

function searchform()
{
	 document.DtForm.action.value = "search";
   	 document.forms["DtForm"].submit();
}

function clearform()
{
	document.DtForm.diengiai.value = "";
	//document.DtForm.loai.selectedIndex = 2;
	document.DtForm.dvttId.selectedIndex = 0;
    document.DtForm.trangthai.selectedIndex = 0;
    document.DtForm.action.value = "search";
    document.forms["DtForm"].submit();
}

</SCRIPT>

	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="DtForm" method="post" action="../../Erp_MucdoanhthuSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="chixem" value='<%= obj.getChixem() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Khoản mục doanh thu</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Tên / Diễn giải </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
						      	<TR>
									<TD width="19%" class="plainlabel">Phòng ban </TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="dvttId" onChange="searchform();" style="width:200px" class="select2">												
													<option value=""  ></option>
												
												<% 	if(dvttlist != null){
														try{
															while (dvttlist.next()){%>
																
																<%	if(dvttlist.getString("DVTTID").equals(obj.getDvttId())){ %>											
																		<option value= <%=dvttlist.getString("DVTTID")%> selected><%= dvttlist.getString("TEN") %></option>															
																<%}else{%>
																		<option value= <%=dvttlist.getString("DVTTID")%> ><%= dvttlist.getString("TEN") %></option>																																		
																<%	}
															}
															
														}catch(java.sql.SQLException e){}
													}
														%>														                                           
                                              			</SELECT>
                                         			</TD>
											</TR>
										</TABLE>									
									</TD>

								</TR>

								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();" style="width:200px" class="select2">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>

										</SELECT></TD>
									
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
					<LEGEND class="legendtitle">&nbsp;Khoản mục doanh thu&nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0 && obj.getChixem().equals("0") ){ %>
						<a class="button3" href="javascript:submitform()">
				    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>     
				    	<%} %>                       		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="20%" >Tên </TH>
									<TH width="35%" >Diễn giải </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="15%">Đơn vị thực hiện</TH>
									<TH width="5%">Tác vụ</TH>
								</TR>
						<%  
						int m = 0;
						String lightrow = "tblightrow";
						String darkrow = "tbdarkrow";

						if(dtlist != null){
							while(dtlist.next()){ 
								if(m % 2 != 0) {%>
									<TR class = <%=lightrow%> >
							<%	} else {%>
									<TR class = <%=darkrow%> >
							<%	}%>
										<TD><%= dtlist.getString("DTTEN") %>
										<TD><%= dtlist.getString("DIENGIAI") %>
								    
								    <% if (dtlist.getString("TRANGTHAI").equals("1")){ %>								    
									
										<TD align = "center">Hoạt động</TD>
									
									<%}else{ %>
									
										<TD align = "center">Ngưng hoạt động</TD>
									<%} %>

									<TD align = "center"><%= dtlist.getString("DVTHTEN") %>
									<TD align = "center">
										<TABLE border = 0 cellpadding="0" cellspacing="0">

											<TR>
										<% if(dtlist.getString("LOAI").equals("1")){ %>											
												<TD>
													<%if(quyen[2]!=0 && obj.getChixem().equals("0") ){ %>
													<A href = "../../Erp_MucdoanhthuUpdateSvl?userid=<%=userId%>&update=<%=dtlist.getString("DTID")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
													<%} %>
												</TD>
												<TD>
													<%if(quyen[1]!=0 && obj.getChixem().equals("0") ){ %>
													<A href = "../../Erp_MucdoanhthuSvl?userid=<%=userId%>&delete=<%=dtlist.getString("DTID")%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa khoản mục doanh thu này ?')) return false;"></A>&nbsp;
													<%}%>
												</TD>
												<TD>
													<!-- QUYEN VIEW DLN -->
													<A href = "../../Erp_MucdoanhthuUpdateSvl?userid=<%=userId%>&display=<%=dtlist.getString("DTID")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<!-- END QUYEN VIEW DLN -->
												</TD>
										<%}else{ %>
											<TD>&nbsp;
													<!-- QUYEN VIEW DLN -->
													<A href = "../../Erp_MucdoanhthuUpdateSvl?userid=<%=userId%>&display=<%=dtlist.getString("DTID")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<!-- END QUYEN VIEW DLN -->
											</TD>
										<%} %>
											</TR>
										</TABLE>				
								</TR>
									
									<% 	m++;
									
							}
							 
						}%>
							
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
 	if( dtlist != null) dtlist.close(); 
 	if( dvttlist != null) dvttlist.close();
 	session.setAttribute("obj",null);
 	obj.DBClose();
	} %>
	