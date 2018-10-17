<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.danhmucquyen.IDanhmucquyenList"%>
<%@page import="geso.traphaco.center.beans.danhmucquyen.IDanhmucquyen"%>
    <%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");

	int[] quyen = new  int[6];
	quyen = util.Getquyen("DanhmucquyenSvl","",userId);
	
%>
<% IDanhmucquyenList obj = (IDanhmucquyenList)session.getAttribute("obj"); %>
<% ResultSet Quyen = (ResultSet)obj.getDSQuyen(); %>
<% ResultSet BangQuyen = (ResultSet)obj.BangQuyen(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.forms['dmquyen'].action.value= 'xoa';
	document.forms['dmquyen'].submit();
}

function submitform()
{
	document.forms['dmquyen'].action.value= 'search';
	document.forms['dmquyen'].submit();
}

function newform()
{
	document.forms['dmquyen'].action.value= 'new';
	document.forms['dmquyen'].submit();
}
function xuatExcel()
{
	document.forms['dmquyen'].action.value= 'toExcel';
	document.forms['dmquyen'].submit();
	document.forms['dmquyen'].action.value= '';
}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dmquyen" method="post" action="../../DanhmucquyenSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="action" value='1'>

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
							   		&nbsp;Quản trị hệ thống &gt; Cập nhật quyền
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn &nbsp;<%=userTen %></td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE border="0" width="100%" >
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
								    <TD class="plainlabel">Quyền  </TD>
								    <TD colspan="3" class="plainlabel">
								    	<SELECT name="maquyen" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ 
									    	  if(Quyen!=null)
									    	  while(Quyen.next()){ 
									    	if(Quyen.getString("PK_SEQ").equals(obj.getMaQuyen())){ %>
									      		<option value='<%=Quyen.getString("PK_SEQ") %>' selected='selected'><%=Quyen.getString("Ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=Quyen.getString("PK_SEQ") %>' ><%=Quyen.getString("Ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									     	
									    </SELECT></TD>
				                   </TR>
										<TR>
											<TD width="19%" class="plainlabel" >Tên quyền</TD>
										  <TD width="81%" class="plainlabel" ><INPUT name="tenquyen" type="text" size="40" value="<%=obj.getTenQuyen() %>" onChange="submitform();" ></TD>
										</TR>
										
										<TR>							
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
										<input class="days" type="text" name="tungay" value='<%= obj.getTungay() %>'  size="20" onchange=submitform(); >
												</TD>
												
		   										</TR>
												</TABLE>
																						
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange=submitform(); >
										  		</TD>
										

											  </TR>
											 </TABLE>
									  	</TR>
									
										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>	    

										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
				
			    <TR>
					<TD align="left" >
						<FIELDSET>
				          	<legend>
				          	
				          	  <LEGEND class="legendtitle">Tao moi &nbsp;&nbsp;
				          	         <%    if(quyen[Utility.THEM]!=0){  %>
				          	  <a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
				          	  <%} %>
				          	  <!--  <INPUT name="taomoi" type="button" value="Tao moi" onClick="newform();">--> 
				          	     </LEGEND>
                    	      
                            
                              </legend>
						<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="20%">Mã quyền</TH>
												<TH width="10%">Tên quyền </TH>
												<TH width="9%">Người tạo</TH>
												<TH width="16%">Ngày tạo</TH>
												<TH width="9%">Người sửa</TH>
												<TH width="15%">Ngày sửa</TH>
												<TH width="15%">Trạng thái</TH>
												<TH width="9%">Tác vụ</TH>
									    	</TR>
									    	<%
									    	int m=0;
											if(BangQuyen != null){
											while (BangQuyen.next()){
												if (m % 2 != 0) {%>						
													<TR class= <%="tblightrow"%> >
												<%} else {%>
													<TR class= <%= "tbdarkrow"%> >
												<%}%>
												<TH width="20%"><%=BangQuyen.getString("maquyen") %></TH>
												<TH width="10%"><%=BangQuyen.getString("ten") %> </TH>
												<TH width="9%"><%=BangQuyen.getString("nguoitao") %></TH>
												<TH width="9%"><%=BangQuyen.getString("ngaytao") %></TH>
												<TH width="16%"><%=BangQuyen.getString("nguoisua") %></TH>
												<TH width="9%"><%=BangQuyen.getString("ngaysua") %> </TH>
												<% if(BangQuyen.getString("hoatdong").equals("1")) { %> 
												 <TH width="9%">Hoạt động</TH>
												<%} else { %>
												 <TH width="9%">Ngưng hoạt động</TH>
												<%} %>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<TD>
													       <%    if(quyen[Utility.SUA]!=0){  %>
														<A href = "../../DanhmucquyennewSvl?userId=<%=BangQuyen.getString("maquyen")%>&copy=1"><img src="../images/copy20.png" alt="Copy" width="20" height="20" longdesc="Copy" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													       <%    if(quyen[Utility.SUA]!=0){  %>
														<A href = "../../DanhmucquyennewSvl?userId=<%=BangQuyen.getString("maquyen")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													 <TD>
													        <%    if(quyen[Utility.XOA]!=0){  %>
														<A href = "../../DanhmucquyenSvl?userId=<%=BangQuyen.getString("maquyen")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa danh mục quyền này hay không ?')) return false;"></A>
													 <%} %>
													 </TD>
												</TR></TABLE>
											
								           </TR>
								           	<%m++; }}%>
								           
								         
						              	</TABLE>
							          </TD>
												
									    	</TR>
							     
							
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
<%//} %>