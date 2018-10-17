<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.dontrahang.IDontrahangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("Duyetdontrahang","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDontrahangList obj = (IDontrahangList)session.getAttribute("obj"); %>
<% ResultSet dthlist = (ResultSet)obj.getDthList(); 
	int[] quyen = new  int[6];
	quyen = util.Getquyen("Duyetdontrahang","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
	<style type="text/css">
#tab tr td input{
cursor:default;
background-repeat: no-repeat;
background: none;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>





<SCRIPT language="javascript" type="text/javascript">
function clearform()
{

    document.dthForm.tungay.value = "";
    document.dthForm.denngay.value = "";
    document.dthForm.trangthai.selectedIndex = 0;
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
   document.forms["dthForm"].submit();
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form id="dthForm" name="dthForm" method="post" action="../../Duyetdontrahang">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="msg" value='<%=obj.getMessage()%>'>
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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %></TD> 
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</TD>
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
											<TD class="plainlabel" width="19%">Từ ngày </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR><TD>
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
										</TD>

                                    	</TR>
										</TABLE>
									</TD>
										</TR>
								<TR>
                                    <TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange="submitform();">
												</TD>

                	                     	</TR>
										</TABLE>
									</TD>

								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
								  	<%	if (obj.getTrangthai().equals("1")){ %>
								  		<option value=""></option>
								    	<option value="1" selected>Chờ duyệt</option>
								    	<option value="2">Đã duyệt</option>
								    	<option value="3">Đã xóa</option>
									<%}else
										if (obj.getTrangthai().equals("2")){ %>							
								  		<option value=""></option>
								    	<option value="1" >Chờ duyệt</option>
								    	<option value="2" selected>Đã duyệt</option>
								    	<option value="3">Đã xóa</option>
									<%}else if(obj.getTrangthai().equals("3") ) {%>
								  		<option value=""></option>
								    	<option value="1" >Chờ duyệt</option>
								    	<option value="2" >Đã duyệt</option>
								    	<option value="3" selected>Đã xóa</option>
										
									<%}else{
										%>
										<option value=""></option>
								    	<option value="1" >Chờ duyệt</option>
								    	<option value="2" >Đã duyệt</option>
								    	<option value="3">Đã xóa</option>
										<%
									} %>
								    	</select></TD>
								</TR>

                                <TR>
                                	<TD class="plainlabel" align="left">
                                		<a class="button2" href="javascript:clearform()">
					<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                	
                                	<!-- <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> -->	
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
                                <LEGEND class="legendtitle">&nbsp;Đơn trả hàng &nbsp;&nbsp;&nbsp;
								                                
    							</LEGEND>
    							
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" id="tab" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ngày trả hàng </TH>
                                            <TH >Số chứng từ </TH>
                                            <TH >Đơn vị kinh doanh </TH>
                                            <TH >Tổng tiền có VAT (VND) </TH>
											<TH >Trạng thái </TH>
                                            <TH >Người tạo </TH>
                                            <TH >Người sửa </TH>
                                            <TH align="center">&nbsp;tác vụ </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dthlist != null){
								try{								
                                    while (dthlist.next()){
                                        	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= dthlist.getString("ngaytra") %></div></TD>                                   
                                                <TD><div align="center"><%= dthlist.getString("chungtu") %></div></TD>
                                                <TD><div align="center"><%= dthlist.getString("donvikinhdoanh") %></div></TD>
                                              <TD><div align="center"><%= formatter.format(dthlist.getDouble("sotienavat")) %></div></TD>
                                                <% if (dthlist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center">Còn chỉnh sửa</div></TD>
                                                <%}else if (dthlist.getString("trangthai").equals("1")){ %>
                                                   		<TD><div align="center"><FONT class="erroralert">Chờ duyệt</FONT></div></TD>
                                                <%}else if (dthlist.getString("trangthai").equals("2")) {%>
                                                   	<TD><div align="center">Đã duyệt</div></TD>
                                                  
                                                  <%}else {
                                                	  %>
                                                	  <TD><div align="center">Đã hủy </div></TD>
                                                	  <% 
                                                  }
                                                  %>
                                                <TD align="center"><%= dthlist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= dthlist.getString("nguoisua")%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR>
                                	                    <TD>
                                	                     <% if (dthlist.getString("trangthai").equals("1")){ %>
	                                    	                <%if(quyen[Utility.CHOT]!=0){ %>
	                                    	                <A href = "../../Duyetdontrahang?userId=<%=userId%>&display=<%=dthlist.getString("chungtu") %>"><img src="../images/Chot.png" alt="Hien thi" title="Chốt" width="20" height="20" longdesc="Hien thi" border = 0 "></A>
	                                    	               <%} %>
	                                    	                <%if(quyen[Utility.XOA]!=0) {%>
	                                    	                 <A href = "../../Duyetdontrahang?userId=<%=userId%>&delete=<%=dthlist.getString("chungtu") %>"><img src="../images/Delete20.png" alt="Hien thi" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 onclick="if(!confirm('Bạn có muốn xóa sản phẩm này ?')) return false;"></A>
	                                    	                <%} %>
                                        	            <%}else{
                                        	            	%>
                                        	            		<%if(quyen[Utility.XEM]!=0){ %>
                                        	            	  <A href = "../../Duyetdontrahang?userId=<%=userId%>&display=<%=dthlist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0 "></A>
                                        	            		<%} %>
                                        	            	<%
                                        	               }	%>
                                        
                                        	            </TD>
                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
                                        }catch(java.sql.SQLException e){}
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
      </TD>
     </TR>
    </TABLE>
</form>


</BODY>
</HTML>
<<script type="text/javascript">
<!--
if(document.forms["dthForm"].msg.value!=""){
	alert(document.forms["dthForm"].msg.value);
}

//-->
</script>
<% if(!(dthlist == null)) dthlist.close(); %>
<% obj.DBclose(); %>
<%}%>