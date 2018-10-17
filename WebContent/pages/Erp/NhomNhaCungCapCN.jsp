<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcn" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcnList" %>
<% INhomnhacungcapcnList obj = (INhomnhacungcapcnList)session.getAttribute("obj"); %>
<% List<INhomnhacungcapcn> ncclist = (List<INhomnhacungcapcn>)obj.getNccList(); %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Phanam/index.jsp");
	}else{  
	
		int[] quyen = new  int[5];
		quyen = util.Getquyen("NhomnhacungcapcnSvl","",userId);
		
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>

<LINK rel="stylesheet" type="text/css" href="../css/style.css" />


	
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
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
<SCRIPT language="JavaScript" type="text/javascript">

function submitform()
{
	 document.nNccForm.action.value = "new";
   	 document.forms["nNccForm"].submit();
}

function searchform()
{
	 document.nNccForm.action.value = "search";
   	 document.forms["nNccForm"].submit();
}

function clearform()
{
	//document.nNccForm.maKH.value = "";
	document.nNccForm.ten.value = "";
    document.nNccForm.trangthai.value="";
	document.nNccForm.tungay.value = "";
	document.nNccForm.denngay.value = "";
 	document.forms["nNccForm"].submit();
}

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
		
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nNccForm" method="post" action="../../NhomnhacungcapcnSvl">
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
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Nhóm nhà cung cấp</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
							
								  <TR style="display:none">
									<TD width="19%" class="plainlabel">Mã nhà cung cấp </TD>
								    <TD width="81%" class="plainlabel">
						            <INPUT name="maNcc" type="text" size="40" value="<%=obj.getMaNcc()%>" onChange ="searchform();">
		                    			
		                    		</TD>								
								</TR>
								
								<TR>
									<TD width="19%" class="plainlabel">Mã nhóm </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="ten" type="text" size="40" value="<%=obj.getTen()%>" onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();" style ="width:200px">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value="2"> </option>
								    	<option value="1" >Hoạt động</option>
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
											<TD class="plainlabel" width="150px">Từ ngày </TD>
                        					<TD class="plainlabel" width="210px">
                           						 <input type="text" class="days" 
                                  			 name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onChange = "searchform();" />
                        					</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" width="150px" >Đến ngày </TD>
                       					 <TD class="plainlabel">
                           					 <input type="text" class="days" 
                              			      name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="searchform()" />
                       					 </TD>

											  
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
					
					<LEGEND class="legendtitle">&nbsp;Nhóm nhà cung cấp &nbsp;&nbsp;&nbsp;
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
									<TH width="10%">Mã nhóm </TH>
									<TH width="19%">Diễn giải </TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="13%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="12%">Người sửa 
									<TH width="9%">Tác vụ</TH>
								</TR>
						<% 
							INhomnhacungcapcn nNcc = null;
							int size = ncclist.size();
							int m = 0;
							Hashtable parent = new Hashtable();
							parent.put("0", "* ");
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (m < size){
								nNcc = ncclist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
									<TD><%=nNcc.getTen() %></TD>						
									<TD><%=nNcc.getDiengiai() %></TD>
									<% if(nNcc.getTrangthai().equals("1")) {%>
										<TD align="center">Hoạt động</TD>
									<%}else {%>
										<TD align="center">Ngưng hoạt động</TD>
									<%} %>
									<TD align="center"><%=nNcc.getNgaytao() %></TD>
									<TD align="center"><%=nNcc.getNguoitao() %></TD>
									<TD align="center"><%=nNcc.getNgaysua() %></TD>
									<TD align="center"><%=nNcc.getNguoisua() %> </TD>
									<TD align="center">
										<TABLE>
											<TR>
											<TD>
											<%if(quyen[2]!=0 && obj.getChixem().equals("0") ){ %>
												<A href = "../../NhomnhacungcapcnUpdateSvl?userId=<%=userId%>&update=<%=nNcc.getId()%>&chixem=<%=obj.getChixem()%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
											<%} %>
											</TD>
											<TD>
											<%if(quyen[1]!=0 && obj.getChixem().equals("0") ){ %>
												<A href = "../../NhomnhacungcapcnSvl?userId=<%=userId%>&delete=<%=nNcc.getId()%>&chixem=<%=obj.getChixem()%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Co Muon Xoa Nhom Khach Hang Nay?')) return false;"></A>&nbsp; 
											<%} %>
											</TD>
											<!-- QUYEN VIEW DLN --> 
											<TD>
												<A href = "../../NhomnhacungcapcnUpdateSvl?userId=<%=userId%>&display=<%=nNcc.getId()%>&chixem=<%=obj.getChixem()%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
											</TD>
											<!-- END QUYEN VIEW DLN --> 
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
for(int i=0; i<ncclist.size();i++)
{
	ncclist.get(i).DBClose();
}



obj.DBClose(); %>
<%}%>