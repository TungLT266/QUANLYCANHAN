
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.hoadon.IHoaDonList" %>
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
	}else{ %>

<% IHoaDonList obj = (IHoaDonList)session.getAttribute("obj"); %>
<% ResultSet nhaphanglist = (ResultSet)obj.getNhaphangList(); 
ResultSet nppRs=(ResultSet)obj.getNppRs();
obj.setNextSplittings();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

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

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css"> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
});
</script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
    document.nhForm.sohoadon.value = "";
    document.nhForm.tungay.value = "";
    document.nhForm.denngay.value = "";
    document.nhForm.nppId.selectedIndex = 0;
    document.nhForm.trangthai.selectedIndex = 0;
    document.forms['nhForm'].submit();
}

function submitform()
{   
   document.forms["nhForm"].submit();
}
function seach()
{   
   document.forms["nhForm"].submit();
}
function xuatExcel()
{
	document.forms['nhForm'].action.value= 'toExcel';
	document.forms['nhForm'].submit();
	document.forms['nhForm'].action.value= '';
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../HoaDonSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value="1"> 
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting"value="<%= obj.getNxtApprSplitting() %>">
<%obj.setMsg(""); %>

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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng &gt; &nbsp;Hóa đơn</TD>
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>  &nbsp;</TD>
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
                                        <TD width="19%" class="plainlabel">Số HĐ</TD>
                                      	<TD width="81%" class="plainlabel">
                                        	<input type="text" name="sohoadon" value="<%= obj.getSoHoaDon() %>"  onChange="submitform();">
                                      	</TD>
                                    </TR>
                                    <TR>
                                        <TD width="19%" class="plainlabel">Số Đơn Đặt hàng</TD>
                                      	<TD width="81%" class="plainlabel">
                                      	
                                        	<input type="text" name="ddhId" value="<%= obj.getDdhId() %>"  >
                                      	</TD>
                                    </TR>
                                    
								<TR>
											<TD class="plainlabel" >Từ ngày </TD>
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
								<TD class="plainlabel">Chi nhánh / Đối tác </TD>
									<TD class="plainlabel">
										<select name="nppId"  id="nppId" style="width:250px;">
											<option value="" selected>All</option>
											<%if (nppRs != null)
													while (nppRs.next()) {
														if (nppRs.getString("nppId").equals(obj.getNppId()   )) {%>
															<option value="<%=nppRs.getString("nppId")%>" selected><%=nppRs.getString("nppTen")%></option>
													<%} else {%>
														<option value="<%=nppRs.getString("nppId")%>"><%=nppRs.getString("nppTen")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  		<option value=""></option>
									<%if (obj.getTrangthai().equals("0")){ %>								  		
								    	<option value="0" selected>Chưa nhận hàng </option>
								    	<option value="1">Đã nhận hàng</option>
								    	<option value="2" >Hóa đơn hủy</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>								  		
								    	<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" selected>Đã nhận hàng</option>
								    	<option value="2" >Hóa đơn hủy</option>
									<%  }else if (obj.getTrangthai().equals("2")){ %>								  		
								    	<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" >Đã nhận hàng</option>
										<option value="2" selected>Hóa đơn hủy</option>
									<% }else{  %>
										<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" >Đã nhận hàng</option>
										<option value="2" >Hóa đơn hủy</option>
									<% }}%>
								    	</select></TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Loại HĐ</TD>
									<TD class="plainlabel">
									<select name="loaihd" >
									<%String [] loaihd={"HĐ Bán","Khuyến mại","Trả lại","Trưng bày"}; %>
								  		<option value=""></option>
									<%for(int i=0;i<3;i++){ 
										if(obj.getLoaihd().equals(i+""))
										{ %>
										<option value="<%=i%>" selected="selected"  > <%=loaihd[i] %>  </option>
									<% 	}else { %>
										<option value="<%=i%>"  > <%=loaihd[i] %>  </option>
									 <%  } %>
									<%} %>
								    	</select></TD>
								</TR>
								
								

                                <TR>
                                	<TD align="left" colspan="4">
                                	
                                	 
									
                                		 <a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
										
										<a class="button2" href="javascript:submitform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
		<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;											
											
                                    	</TD>
                                    <TD class="plainlabel">
                                   
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
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Nhận hàng</LEGEND> 
                                
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ngày hóa đơn </TH>
                                            <TH >Số HĐ </TH>
                                            <TH >Loại HĐ </TH>
                                            <TH> Số đơn hàng đặt hàng</TH>
                                            <TH >Đơn vị kinh doanh </TH>
                                            <TH >Kênh bán hàng </TH>
                                            <TH >Chi nhánh / Đối tác </TH>
                                            <TH >Tổng số tiền có VAT (VND) </TH>
                                            <TH >Số SKU </TH>
											<TH >Trạng thái </TH>
                                            <TH >Người sửa </TH>
                                            <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(nhaphanglist != null){
								try{								
                                    while (nhaphanglist.next()){
                                        	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= nhaphanglist.getString("ngaychungtu") %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("SoHoaDon") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("LoaiHD") %></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("dathang_fk") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("donvikinhdoanh") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("kenhbanhang") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("nppTen") %></div></TD>
                                                <TD><div align="center"><%= formatter.format(nhaphanglist.getDouble("sotienavat")) %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("soSku") %></TD>
                                                 <TD align="center"> <%= nhaphanglist.getString("trangthai") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("nguoisua")%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR>
                                	                    <TD>	                                    	                    
                                	                    	<A href = "../../HoaDonUpdateSvl?userId=<%=userId%>&display=<%=nhaphanglist.getString("nhapHangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>															                                    	                   
                                        	            </TD>                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
								}catch(java.sql.SQLException e){e.printStackTrace();}
                               		}%>
                               			<tr class="tbfooter" > 
                               			
                               			<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
                               			
                               			
                               			 </tr> 
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
<% if(!(nhaphanglist == null)) nhaphanglist.close(); %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>