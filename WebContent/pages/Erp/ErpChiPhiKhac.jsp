<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.chiphikhac.IErpChiphikhacList"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpChiphikhacList obj =(IErpChiphikhacList)session.getAttribute("obj");
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
	ResultSet cpkRs = obj.getChiphikhacRs();
	ResultSet nguoitaoRs = obj.getNguoiTaoRs();
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpChiphikhacSvl","120",userId);
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Phanam - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
   
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['cpk'].ma.value= "";
	    document.forms['cpk'].diengiai.value= "";
	    document.forms['cpk'].trangthai.value = "";
	    document.forms['cpk'].ngaytao.value= "";
	    document.forms['cpk'].sohoadon.value= "";
	    document.forms['cpk'].nguoitaoid.value= "";
		document.forms['cpk'].submit();
	}

	function submitform()
	{
		document.forms['cpk'].action.value= 'search';
		document.forms['cpk'].submit();
	}

	function newform()
	{
		document.forms['cpk'].action.value= 'new';
		document.forms['cpk'].submit();
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cpk" method="post" action="../../ErpChiphikhacSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải trả > Chi phí khác </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             
                             <TR>
                             	<TD width="20%" class="plainlabel" >Số chứng từ </TD>
								<TD class="plainlabel">
									<input  type="text" name="ma" value="<%=obj.getMa() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                              <TR>
                             	<TD width="20%" class="plainlabel" >Số hóa đơn </TD>
								<TD class="plainlabel">
									<input  type="text" name="sohoadon" value="<%=obj.getSoHoaDon() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Diễn giải </TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Ngày </TD>
								<TD class="plainlabel">
									<input type="text" class="days" id="ngaytao" name="ngaytao" value="<%= obj.getNgayTao() %>" maxlength="10" onchange="submitform()" />
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value=""> </option>
										<%} else { if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>										
											<option value=""> </option>
										<% } else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>										
											<option value="" selected> </option>
										 <% } }  %>
										
									 </select>
								</TD>
                             </TR >
                             
                             <TR>
                        		<TD class="plainlabel" valign="middle" >Người tạo</TD>
                        		<TD class="plainlabel" colspan="3" valign="middle">
								<SELECT NAME = "nguoitaoid" id = "nguoitaoid"  style="width: 200px" onChange = "submitform();">
										<OPTION VALUE = "" >&nbsp;</OPTION>
			               <% if(nguoitaoRs != null){
        		                	while(nguoitaoRs.next()){	
        		               			if(obj.getNguoiTao().equals(nguoitaoRs.getString("PK_SEQ"))){	%> 	   	
        		                	   
        		                	   <OPTION VALUE = "<%= nguoitaoRs.getString("PK_SEQ")%>" SELECTED><%= nguoitaoRs.getString("TEN")%></OPTION>
        		                	   
        		                <% 	  	}else{ %>
        		                		<OPTION VALUE = "<%= nguoitaoRs.getString("PK_SEQ")%>" ><%= nguoitaoRs.getString("TEN")%></OPTION>
        		                <%		}
        		                	}
        		                	nguoitaoRs.close();
        		                }
        		                %>
								
								</SELECT>                        	
                        		</TD>  
                        	</TR>
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Chi phí khác &nbsp;&nbsp;	
                            	<%if(quyen[0]!=0){ %>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>    
                           		<%} %>  
                            	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="10%" align="center">Số chứng từ</TH>
                                    <TH width="10%" align="center">Ngày</TH>
                                    <TH width="20%" align="center">Diễn giải</TH>
                                    <TH width="20%" align="center">Đối tượng</TH>
                                    <TH width="10%" align="center">Trạng thái</TH>                                    
                                    <TH width="10%" align="center">Người tạo</TH>                                    
                                    <TH width="10%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    for(int i =0; i < htList.size(); i ++)
    								{
                                    	IThongTinHienThi ht = htList.get(i);
                                    	String trangthai = ht.getTRANGTHAI();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= ht.getId()%></TD>                                   
                                                <TD align="center"><%= ht.getNGAYCHUNGTU()%></TD>
                                                <TD align="left"><%= ht.getDIENGIAI()%></TD>
                                                <TD align="left"><%= ht.getKhachhang()%></TD>
                                                <% if( trangthai.equals("0") ) { %>
                                                	<TD align="center">Chưa chốt</TD>
                                                <% } else { if(trangthai.equals("1")) { %>       
                                                	<TD align="center">Đã chốt</TD>
                                                <%} else { %> 
                                                	<TD align="center">Đã hủy</TD>
                                                <% } } %> 
                                                	
                                                <TD align="left"><%= ht.getNGUOITAO()%></TD>
                                                <TD align="left"><%= ht.getNGUOISUA()%> </TD>
                                                <TD align="center"> 
                                                <% if( trangthai.equals("0") ) { %>
                                                <%if(quyen[2]!=0){ %>
							                   		<A href = "../../ErpChiphikhacUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A> &nbsp;
							                   		<%} %>
							                   		<%if(quyen[3]!=0){ %>
													<A href = "../../ErpChiphikhacSvl?userId=<%=userId%>&chot=<%= ht.getId() %>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" longdesc="Chốt" border=0 onclick="if(!confirm('Bạn muốn chốt ghi nhận chi phí này?')) return false;"></A>
													<%} %>
													<%if(quyen[1]!=0){ %>
													<A href = "../../ErpChiphikhacSvl?userId=<%=userId%>&delete=<%= ht.getId() %>"><img src="../images/Delete20.png" alt="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn muốn xóa ghi nhận chi phí này?')) return false;"></A>
													<%} %>
													
												<% } else { %>
													<A href = "../../ErpChiphikhacUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>&nbsp;
												<% }  %>
												<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; 
											     <img alt="Định khoản kế toán" src="../images/vitriluu.png">
										 </A> &nbsp;
											 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>												                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" /></td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
								                     </div>
	                							</DIV>
														
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
                                          <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
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
<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script>
</form>
</BODY>
</html>
<% 	
	try
    {
		if(cpkRs != null)
			cpkRs.close();
		
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>