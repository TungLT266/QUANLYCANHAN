<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchaiList"%>
<%@page import="geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchai"%>
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
		quyen = util.Getquyen("CttongketnapchaiSvl","",userId);
	%>

<% ICttongketnapchaiList obj = (ICttongketnapchaiList)session.getAttribute("obj"); %>
<%ResultSet cttknc = (ResultSet)obj.getCttongketRS(); %>

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
<form id="dthForm" name="dthForm" method="post" action="../../CttongketnapchaiSvl">
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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mãi &gt;&nbsp;Chức năng &gt;&nbsp; Chương trình tổng kết nắp chai</TD> 
                                   
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
                                	<TD width="19%"  class="plainlabel" >Mã</TD>
								  	<TD class="plainlabel" ><input type="text" name= "ma" value ="<%= obj.getMa() %>" onchange="submitform();">
								</TD>
								</TR>
                                <TR>
                                	<TD width="19%"  class="plainlabel" >Diễn giải</TD>
								  	<TD class="plainlabel" ><input type="text" name= "diengiai" value ="<%= obj.getDiengiai() %>" onchange="submitform();">
								</TD>
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
                                <LEGEND class="legendtitle">&nbsp;Chương trình tổng kết nắp chai &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                
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
                                            <TH >Mã </TH>
                                            <TH >Diễn giải </TH>
                                             <TH >Ngày tạo</TH>
                                             <TH >Người tạo</TH>
                                             <TH >Ngày sửa</TH>
                                             <TH >Người sửa</TH>
                                             <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
                              <%
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(cttknc!=null)
								while (cttknc.next()){							

									if (m % 2 != 0) { %>						
										<TR class= <%=lightrow%> >
									<%} else { %>
										<TR class= <%= darkrow%> >
									<%}%>
									
                                     <TD align="center" class="textfont"><%=cttknc.getString("ma")==null?"":cttknc.getString("ma")%> </TD>
                                     <TD align="center" class="textfont"><%=cttknc.getString("diengiai")==null?"":cttknc.getString("diengiai")%> </TD>
									 <TD align="center" class="textfont"><%=cttknc.getString("ngaytao")%> </TD>
									 <TD align="center" class="textfont"><%=cttknc.getString("nguoitao")%> </TD>
									 <TD align="center" class="textfont"><%=cttknc.getString("ngaysua")%> </TD>
									 <TD align="center" class="textfont"><%=cttknc.getString("nguoisua")%> </TD>
								     <TD align="center">
								     
								     		<%if(quyen[Utility.SUA]!=0){ %>
								     		<A href = "../../CttongketnapchaiUpdateSvl?userId=<%=userId%>&update=<%=cttknc.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
									   		<%} %>
									     	
									     	<%if(quyen[Utility.XOA]!=0){ %>
									     	<A href = "../../CttongketnapchaiUpdateSvl?userId=<%=userId%>&delete=<%=cttknc.getString("pk_seq")%>"><img src="../images/Delete.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border = 0></A> 
									   		<%} %>
									  </TD> 
											
										 <%m++;} %>
									 
								 </TR>
                           
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
	
		if(cttknc != null)
			cttknc.close();
	
	}
	catch (SQLException e) {}

%>
<%}%>