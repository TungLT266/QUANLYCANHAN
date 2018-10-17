<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList"%>
<%@page import="geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("DangkykhuyenmaitichluySvl","",userId);
	%>

<% IDangkykhuyenmaitichluyList obj = (IDangkykhuyenmaitichluyList)session.getAttribute("obj"); %>
<%ResultSet dstichluy = (ResultSet)obj.getDsTichluy(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
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
	
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>





<SCRIPT language="javascript" type="text/javascript">
function clearform()
{      document.forms['dthForm'].tungay.value= '';
       document.forms['dthForm'].denngay.value= '';
       document.forms['dthForm'].diengiai.value= '';
       submitform();
}

function submitform()
{   
   document.forms['dthForm'].action.value= 'search';
   document.forms["dthForm"].submit();
}

function newform()
{
	document.forms['dthForm'].action.value= 'new';
	document.forms['dthForm'].submit();
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form id="dthForm" name="dthForm" method="post" action="../../DangkykhuyenmaitichluySvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" class="tbnavigation">
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">Quản lý KM, trưng bày > Khuyến mại > Đăng ký khuyến mãi tích lũy</TD> 
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%=obj.getNppTen() %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị &nbsp;</LEGEND>

                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                <TR>
                                	<TD width="19%"  class="plainlabel" >Diễn giải</TD>
								  	<TD class="plainlabel" ><input type="text" name= "diengiai" value ="<%= obj.getDiengiai() %>" onchange="submitform();">
								</TD>
								</TR>
								
<TR>
	<TD class="plainlabel" >Từ ngày</TD>
	<td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
	</td>
</TR>
<TR>
  <TD class="plainlabel" >Ðến ngày</TD>
  <td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
	</td>
</TR>
								

                                <TR>
                                	<TD class="plainlabel" align="left">
                                		<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                	<!-- 
                                		<INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> -->
                                		</TD>
                                	<TD class="plainlabel" colspan=4>&nbsp;</TD>
                                </TR>
                                			
									
								</TABLE>
                              </FIELDSET>
                          	</TD>			
                          </TR>
                      </TABLE>
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Khuyến mãi tích lũy &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                
                                <%if(quyen[Utility.THEM]!=0){ %>
                                 	<a class="button3"  onclick="newform()">
    								<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
    							<%} %>                       
                                <!-- 
								<INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
								 </LEGEND>                                
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Mã CTKM </TH>
                                            <TH >Diễn giải </TH>
                                            <TH >Ngày đăng ký</TH>
                                            <TH >Ngày kết thúc</TH>
                                             <TH >Trạng thái</TH>
                                             <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
                              <%
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(dstichluy!=null)
								while (dstichluy.next()){							

									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
									
                                     <TD align="left" class="textfont"><%=dstichluy.getString("scheme")%> </TD>
                                     <TD align="left" class="textfont"><%=dstichluy.getString("diengiai")%> </TD>
									 <TD align="left" class="textfont"><%=dstichluy.getString("tungay")%> </TD>
									 <TD align="left" class="textfont"><%=dstichluy.getString("denngay")%> </TD>
									 <td>
									 	<%if(dstichluy.getString("trangthai").equals("1")){ %>
									 		Đã chốt
									 	<%}else if(dstichluy.getString("trangthai").equals("2")) { %>
									 		Đã xóa
									 	<%}else{ %>
									 		Đang xử lý
									 	<%}%>
									 </td>
								     <TD align="center">
								     <%if(dstichluy.getString("trangthai").equals("0")){ %>
								     
								     		<%if(quyen[Utility.SUA]!=0){ %>
								     		<A href = "../../DangkykhuyenmaitichluyUpdateSvl?userId=<%=userId%>&update=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
									   		<%} %>
									   		
									   		<%if(quyen[Utility.CHOT]!=0){ %>
									   			<A href = "../../DangkykhuyenmaitichluyUpdateSvl?userId=<%=userId%>&chot=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border = 0></A>
									     	<%} %>
									     	
									     	<%if(quyen[Utility.XOA]!=0){ %>
									     	<A href = "../../DangkykhuyenmaitichluyUpdateSvl?userId=<%=userId%>&delete=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Delete.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border = 0></A> 
									   		<%} %>
									   
									   <%} else { %>
									   
									   		<%if(quyen[Utility.XEM]!=0){ %>
									    	<A href = "../../DangkykhuyenmaitichluyUpdateSvl?userId=<%=userId%>&dislay=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0 "></A>
											<%} %>
											
											<%if( quyen[Utility.HUYCHOT]!=0  ){ %>
												<A href = "../../DangkykhuyenmaitichluyUpdateSvl?userId=<%=userId%>&mochot=<%=dstichluy.getString("pk_seq")%>"><img src="../images/unChot.png" alt="Chot" title="Mở chốt" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn có muốn mở chốt đăng ký tích lũy này?')) return false;" ></A>
			                              	 <% } %>
													
										 <%} %>
									 </TD>
								 </TR>
								<%
									m++;
								} %>
                           
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
      </TD>
     </TR>
    </TABLE>
</form>


</BODY>
</HTML>
<% 	
if(obj != null){
	obj.DBclose();
	obj = null;
}
session.setAttribute("obj",null);
	try{
	
		if(dstichluy != null)
			dstichluy.close();
	
	}
	catch (SQLException e) {}

%>
<%}%>