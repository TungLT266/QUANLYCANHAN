<%@page import="geso.traphaco.erp.beans.hoadon.IHdDetail"%>
<%@page import="java.text.Format"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="geso.traphaco.erp.beans.hoadonphelieu.IErpHoadonphelieuList"%>
<%@page import="geso.traphaco.erp.beans.hoadon.IErpHoaDonList"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpHoadonphelieuList obj =(IErpHoadonphelieuList)session.getAttribute("obj");
	ResultSet csxRs = obj.getGiamgiaRs();
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
	ResultSet KhoanmucDTRs = obj.getKhoanmucDTRs();
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>
<% obj.setNextSplittings(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
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
	
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	  
	    document.forms['khtt'].diengiai.value= "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].sohoadon.value = "";
	    document.forms['khtt'].khachhang.value = "";
	    document.forms['khtt'].doanhthuId.value ="";
	    document.forms['khtt'].tennguoitao.value ="";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	
	function thongbao()
	 {
		 var tt = document.forms["khtt"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["khtt"].msg.value);
	 }
	
	
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpHoadonphelieuSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
	<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
		<script language="javascript" type="text/javascript">
    	thongbao();
		</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation"> Quản lý cung ứng> Quản lý bán hàng > Xuất HĐ khác </TD>  
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
                              <TR style="display: none">
							 	   <TD class="plainlabel"  valign="middle" width="20%" >Loại hóa đơn</TD> 
							 	   <TD class="plainlabel"  	>
							 	   	<select   name="doanhthuId"  onchange="submitform();" style="width: 200px">
				                        	<option value="" > </option>
				                        	<%
				                        		if(KhoanmucDTRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(KhoanmucDTRs.next())
				                        			{  
				                        			if( KhoanmucDTRs.getString("doanhthuId").equals(obj.getKhoanmucDTId())){ %>
				                        				<option value="<%= KhoanmucDTRs.getString("doanhthuId") %>" selected="selected" ><%= KhoanmucDTRs.getString("doanhthuTen")%></option>
				                        			<%}else { %>
				                        				<option value="<%= KhoanmucDTRs.getString("doanhthuId") %>" ><%= KhoanmucDTRs.getString("doanhthuTen") %></option>
				                        		 <% } } KhoanmucDTRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
				                        </select>
							 	   </TD> 
							 </TR>
							 
							 
							  <TR>
                             	<TD width="20%" class="plainlabel" >Người tạo </TD>
								<TD class="plainlabel">
									<input  type="text" name="tennguoitao" value="<%=obj.getTennguoitao() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
							 
							 
                               <TR>
                             	<TD width="20%" class="plainlabel" >Số hóa đơn </TD>
								<TD class="plainlabel">
									<input  type="text" name="sohoadon" value="<%=obj.getSohoadon() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Diễn giải </TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                              <TR>
                             	<TD width="20%" class="plainlabel" >Mã / tên khách hàng </TD>
								<TD class="plainlabel">
									<input  type="text" name="khachhang" value="<%=obj.getKhachhang() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();" style="width: 200px">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Đã hủy</option>
											<option value=""> </option>
										<%} else { if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>	
											<option value="2">Đã hủy</option>									
											<option value=""> </option>											
										<% } else { if ( obj.getTrangthai().equals("2") ){%>
											<option value="0" >Chưa chốt</option>
											<option value="1" >Đã chốt</option>	
											<option value="2" selected >Đã hủy</option>									
											<option value=""> </option>	
											<%}else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>		
											<option value="2">Đã hủy</option>								
											<option value="" selected> </option>
										 <% }} }  %>
										
									 </select>
								</TD>
                             </TR >
                          
                              
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
                            <LEGEND class="legendtitle">&nbsp;Hóa đơn phế liệu &nbsp;&nbsp;	
                            	
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="6%" align="center">Mã hóa đơn</TH>
                                     <TH width="6%" align="center">Số HĐ</TH>
                                     <TH width="7%" align="center">Ngày HĐ </TH>
                                    <TH width="17%" align="center">Khách hàng</TH>
                                    <TH width="13%" align="center">Loại hóa đơn</TH>
                                     <TH width="8%" align="center">Tổng tiền</TH>
                                    <TH width="7%" align="center">Trạng thái</TH>
                                    <TH width="6%" align="center">Ngày tạo</TH>
                                    <TH width="7%" align="center">Người tạo</TH>
                                    <TH width="6%" align="center">Ngày sửa</TH>
                                    <TH width="7%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                int m = 0;
                                for(int i =0; i < htList.size(); i ++)
    							{  
                                	IThongTinHienThi ht = htList.get(i);
                         				String tt = ht.getTrangthai();
                         				
                         				if((m % 2 ) == 0 & tt.equals("2")) {%>
    		                         	<TR class='tbdarkrow' style="color: red;">
    		                          	<%}else if((m % 2 ) != 0 & tt.equals("2")) {%>
    		                          	<TR class='tblightrow' style="color: red;">
    		                          	<%}else if((m % 2 ) == 0) {%>
    		                          		<TR class='tbdarkrow'>
    		                          	<%}else {%>
    		                          	<TR class='tblightrow'>	
    		                        	<%} %>
        		                        
                                                <TD align="center"><%= ht.getId()%></TD> 
                                                 <TD align="center"><%= ht.getSohoadon() %></TD>
                                                 <TD align="center"><%= ht.getNgaychungtu() %></TD>                                   
                                                <TD align="left"><%= ht.getKhachhang()%></TD>
                                                <TD align="left"><%= ht.getPoTen() %></TD>
                                                <TD align="right"><%= formatter.format(Double.parseDouble(ht.getTongtien()) ) %></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chưa chốt</TD>
                                                <% } else { if(tt.equals("1")) { %> 
                                                	<TD align="center">Đã chốt</TD>
                                                <% } else {  %>       
                                                	<TD align="center">Đã hủy</TD>
                                                <%} } %> 
                                                	
                                                <TD align="center"><%= ht.getNgaytao()%> </TD>
                                                <TD align="left"><%= ht.getNguoitao()%></TD>
                                                <TD align="center"><%=ht.getNgaysua() %> </TD>
                                                <TD align="left"><%= ht.getNguoisua()%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
							                   		<A href = "../../ErpHoadonphelieuUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A> &nbsp;
							                   		<A href = "../../ErpHoadonphelieuSvl?userId=<%=userId%>&chot=<%= ht.getId() %>"><img src="../images/Chot.png" alt="Duyệt" width="20" height="20" longdesc="Duyệt" border=0 onclick="if(!confirm('Bạn Muốn Duyệt Hóa Đơn Này?')) return false;"></A>&nbsp;
													<A href = "../../ErpHoadonphelieuSvl?userId=<%=userId%>&delete=<%= ht.getId() %>"><img src="../images/Delete20.png" alt="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn Muốn Xóa Hóa Đơn Này?')) return false;"></A>
													
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
	                                
				                                	<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
					                    				<table width="90%" align="center">
											                 <tr>
									                           	<th width="100px">Nợ/Có</th>
									                            <th width="150px">Số hiệu tài khoản</th>
									                            <th width="150px">Số tiền</th>
									                            <th width="300px">Đối tượng</th>
									                            <th width="80px">Trung tâm CP</th>	
									                            <th width="80px">Trung tâm DT</th>										                       
											                 </tr>
		                        
								                            <% List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
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
													                            	<%if(kt.getSotien().trim().length() > 0){ %>
													                            		<input type="text" style="width: 100%; text-align: right" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" />
													                            	<%} else {%>
													                            		<input type="text" style="width: 100%; text-align: right" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>"  />
													                            	<%} %>												                            	
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
										                        <%  } ktList.clear();
										                        %>
							
					                    				</table>
												        
												        <div align="right">
												            <label style="color: red" ></label>
												                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												                <a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
												        </div>
		                							</DIV>	
		                										
												<% } else if(tt.equals("1") ) { %>
													<A href = "../../ErpHoadonphelieuUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
													
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
	                                
				                                	<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
					                    				<table width="90%" align="center">
											                 <tr>
									                           	<th width="100px">Nợ/Có</th>
									                            <th width="150px">Số hiệu tài khoản</th>
									                            <th width="150px">Số tiền</th>
									                            <th width="300px">Đối tượng</th>
									                            <th width="80px">Trung tâm CP</th>	
									                            <th width="80px">Trung tâm DT</th>										                       
											                 </tr>
		                        
								                            <% List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
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
													                            	<%if(kt.getSotien().trim().length() > 0){ %>
													                            		<input type="text" style="width: 100%; text-align: right" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>"  />
													                            	<%} else {%>
													                            		<input type="text" style="width: 100%; text-align: right" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" />
													                            	<%} %>												                            	
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
										                        <%  }ktList.clear(); %>
							
					                    				</table>
												        
												        <div align="right">
												            <label style="color: red" ></label>
												                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												                <a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
												        </div>
		                							</DIV>	
												<% } else { %>
													<A href = "../../ErpHoadonphelieuUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
												<%} %>
												 
<%-- 												 <%//Nút xem lịch sử in %>
												  <A href="" id="lichsuin<%= count %>" rel="subcontentLSI<%= count %>" >
					           	 							&nbsp; <img alt="Thông tin lịch sử in" src="../images/vitriluu.png"  title="Lịch sử in"></A>
				                            
							                     <DIV id="subcontentLSI<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
													                      background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
													<table width="90%" align="center">
													      <tr>
													         <th width="150px">Số lần in</th>
													         <th width="200px">Trạng thái</th>
													         <th width="300px">Ngày giờ in</th>
													         <th width="250px">Người in</th>
													         <th width="100px" align="center"></th>
													      </tr>
											                   	<%
											                    List<IHdDetail> hdLichSuIn = hd.getHdDetailList();
											                	if(hdLichSuIn.size() > 0)
											                	{
											                		for(int k =0; k < hdLichSuIn.size() ; k++)
											                		{
											                			IHdDetail hdLichSu = hdLichSuIn.get(k) ;
											                			String trangthaihd_in = "";
											                			%>								                										                			
											                			<tr>
											                				<td><input style="width: 100%" value="<%= hdLichSu.getSolanin() %>"></td>
											                				<td><input style="width: 100%" value="<%=hdLichSu.getTrangthai() %>"></td>
											                				<td><input style="width: 100%" value="<%= hdLichSu.getNgayin() %>"></td>
											                				<td><input style="width: 100%" value="<%= hdLichSu.getNguoiin() %>"></td>
											                				<td>								                				
											                				<A href="../../ErpHoaDonPheLieuPdfSvl?userId=<%=userId%>&lsinId=<%= hdLichSu.getId() %>" >
											                				    &nbsp;&nbsp;	
											                				    <IMG src="../images/Printer30.png" alt="In" title="In" width="20" height="20" border=0 >								               					
											                				</A>
											                				</td>
											                			</tr>
											                			
											                    <%}
											                	 } %>
													</table>
												<div align="right">
													 <label style="color: red" ></label>
													 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													 <a href="javascript:dropdowncontent.hidediv('subcontentLSI<%= count %>')"  >Đóng lại</a>
												</div>
											  </DIV> --%>
												
							                    </TD>
                                            </TR>
                                   <% m++;  } %>

								
								 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('khtt', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('khtt', eval(khtt.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('khtt', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('khtt', eval(khtt.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('khtt', -1, 'view')"> &nbsp; <%} %>
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
<script type="text/javascript">
	
	 <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 300, "click");
	   
	  <%}%>
	  
</script>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% 	
	try
    {
		if(csxRs != null)
			csxRs.close();
		if(KhoanmucDTRs != null)
			KhoanmucDTRs.close();
		if (htList != null)
			htList.clear();
		
		
		session.removeAttribute("obj");
	}
	catch (SQLException e) {}
	finally
	{
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
	}
	
	%>
<%}%>
