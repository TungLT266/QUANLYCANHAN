<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpThuenhapkhauList obj =(IErpThuenhapkhauList)session.getAttribute("obj");
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
	ResultSet tnkRs = obj.getThuenhapkhauRs();
	ResultSet rs;
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color
{
	background-color: red;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
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
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["tnk"].submit();
		}
			
		setTimeout(replaces, 300);
	}
	
	function clearform()
	{ 
		document.forms['tnk'].poId.value= "";
		//document.forms['tnk'].ncc.value= "";
		document.forms['tnk'].nccId.value= "";
	    document.forms['tnk'].diengiai.value= "";
	    document.forms['tnk'].trangthai.value = "";
	    document.forms['tnk'].sochungtu.value = "";
	    document.forms['tnk'].sotokhai.value = "";
	    document.forms['tnk'].sohoadon.value = "";
	    document.forms['tnk'].tungayTK.value = "";
	    document.forms['tnk'].denngayTK.value = "";
		document.forms['tnk'].submit();
	}

	function submitform()
	{
		document.forms['tnk'].action.value= 'search';
		document.forms['tnk'].submit();
	}

	function newform()
	{
		document.forms['tnk'].action.value= 'new';
		document.forms['tnk'].submit();
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != "")
		{
			alert(document.getElementById("msg").value);
		}
	}
	
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tnk" method="post" action="../../ErpThuenhapkhauSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="loaimh" value="<%=obj.getLoaiMh()%>" >

<input type="hidden" id="msg" value="<%= obj.getMsg()  %>" >
<script type="text/javascript">thongbao();</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý mua hàng &gt; Mua hàng nhập khẩu &gt; Thuế nhập khẩu </TD>  
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
		                       
		                        <TD class="plainlabel" width="10%">Ngày tờ khai từ: </TD>
		                        <TD class="plainlabel" width="200px">
		                            <input type="text" class="days" 
		                                   id="tungayTK" name="tungayTK" value="<%=obj.getTungay() %>" maxlength="10" onchange="submitform()" />
		                        </TD>
		                        
		                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngayTK" name="denngayTK" value="<%=obj.getDenngay()%>" maxlength="10" onchange="submitform()"/>
                        </TD>
		                   			 </TR>
				                   
				                    <TR>
        		                <TD class="plainlabel" width="15%">Số PO </TD>
                		        <TD class="plainlabel">
                        	    <input type="text" id="poId"  name="poId" value="<%= obj.getPoId() %>" maxlength="30" onchange="submitform()" />
                        		</TD>
                        		  <TD class="plainlabel" width="15%">Số chứng từ </TD>
                		        <TD class="plainlabel">
                        	    <input type="text" id="sochungtu"  name="sochungtu" value="<%= obj.getId() %>" maxlength="10" onchange="submitform()" />
                        		</TD>
                    		</TR>
                    		<TR>
                        		<TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        		<TD class="plainlabel" valign="middle">
								<SELECT NAME = "nccId" id = "nccId"  style="width: 400px" onChange = "submitform();">
										<OPTION VALUE = "" >&nbsp;</OPTION>
			               <% 	rs = obj.getNccList();
        		              	if(rs != null){
        		                	while(rs.next()){	
        		               			if(obj.getNccId().equals(rs.getString("NCCID"))){	%> 	   	
        		                	   
        		                	   <OPTION VALUE = "<%= rs.getString("NCCID")%>" SELECTED><%= rs.getString("NCCTEN")%></OPTION>
        		                	   
        		                <% 	  	}else{ %>
        		                		<OPTION VALUE = "<%= rs.getString("NCCID")%>" ><%= rs.getString("NCCTEN")%></OPTION>
        		                <%		}
        		                	}
        		                	rs.close();
        		                }
        		                %>
								
								</SELECT>                        	
                        		</TD>  
                        		
                        		
                        		  <TD class="plainlabel" width="15%">Số tờ khai </TD>
                		        <TD class="plainlabel">
                        	    <input type="text" id="sotokhai"  name="sotokhai" value="<%= obj.getSotokhai() %>" maxlength="10" onchange="submitform()" />
                        		</TD>                      
                    		</TR>
                             
                             <TR>
                             	<TD width="20%" class="plainlabel" >Diễn giải</TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
								  <TD class="plainlabel" width="15%">Số hoá đơn </TD>
                		        <TD class="plainlabel">
                        	    <input type="text" id="sohoadon"  name="sohoadon" value="<%= obj.getSohoadon() %>" maxlength="50" onchange="submitform()" />
                        		</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel" colspan="3">
									<select name="trangthai" style="width: 200px" onChange="submitform();">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value=""> </option>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt thuế nhập khẩu</option>
											<option value="2">Đã chốt thuế VAT</option>
											<option value="3">Đã hủy</option>
											
										<%} else if( obj.getTrangthai().equals("1") ) { %>	
											<option value=""> </option>									
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt thuế nhập khẩu</option>
											<option value="2">Đã chốt thuế VAT</option>
											<option value="3">Đã hủy</option>										
											
										<% } else if( obj.getTrangthai().equals("2") ) {  %>
											<option value="" ></option>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt thuế nhập khẩu</option>			
											<option value="2" selected >Đã chốt thuế VAT</option>
											<option value="3">Đã hủy</option>								
										  <% } else if( obj.getTrangthai().equals("3") ) {  %>
											<option value="" ></option>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt thuế nhập khẩu</option>			
											<option value="2" >Đã chốt thuế VAT</option>
											<option value="3" selected>Đã hủy</option>		
										 <% }else {  %>
										 	<option value=""  selected ></option>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt thuế nhập khẩu</option>			
											<option value="2">Đã chốt thuế VAT</option>							
											<option value="3">Đã hủy</option>	
										 <% } %>										
									 </select>
								</TD>
								
								 
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="4" > 
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
                            <LEGEND class="legendtitle">&nbsp;Thuế nhập khẩu &nbsp;&nbsp;	
                            	
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="8%" align="center">Số chứng từ</TH>
                                    <TH width="18%" align="center">Diễn giải</TH>
                                      <TH width="8%" align="center">Số tờ khai</TH>
                                    <TH width="8%" align="center">Ngày tờ khai</TH>
                                    <TH width="8%" align="center">Trạng thái</TH>
                                    <TH width="5%" align="center">Ngày tạo</TH>
                                    <TH width="10%" align="center">Người tạo</TH>
                                    <TH width="5%" align="center">Ngày sửa</TH>
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
                                    	
                                    	String tt = ht.getTRANGTHAI().trim();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= ht.getTNKID() %></TD>                                   
                                                <TD align="left"><%=ht.getDIENGIAI()%></TD>
                                                <TD align="left"><%= ht.getSOCHUNGTU()%></TD>
                                                <TD align="center"><%= ht.getNGAYCHUNGTU()%></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	
                                                	<TD align="center">Chưa chốt</TD>
                                                	
                                                <% } else if(tt.equals("1")) { %>       
                                                	
                                                	<TD align="center">Đã chốt thuế nhập khẩu</TD>
                                                	
                                                <%} else if(tt.equals("2")) { %> 
                                                	
                                                	<TD align="center">Đã chốt thuế VAT</TD>
                                                	
                                                <%	}else{ %>
                                                	<TD align="center">Đã hủy</TD>
                                                <%  }  %> 
                                                	
                                                <TD align="center"><%= ht.getNGAYTAO()%> </TD>
                                                <TD align="left"><%= ht.getNGUOITAO()%></TD>
                                                <TD align="center"><%= ht.getNGAYSUA()%> </TD>
                                                <TD align="left"><%= ht.getNGAYSUA()%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
							                   		<A href = "../../ErpThuenhapkhauUpdateSvl?userId=<%=userId%>&update=<%=ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Edit20.png" alt="Cập nhật thuế nhập khẩu" title="Cập nhật thuế nhập khẩu" width="20" height="20" longdesc="Cập nhật thuế nhập khẩu" border = 0></A> &nbsp;
													<A href = "../../ErpThuenhapkhauSvl?userId=<%=userId%>&chotthue=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Chot.png" alt="Chốt thuế nhập khẩu" width="20" height="20" title="Chốt thuế nhập khẩu" longdesc="Chốt thuế nhập khẩu" border=0 onclick="if(!confirm('Bạn muốn chốt Thuế Nhập Khẩu này?')) return false;"></A>
													<A href = "../../ErpThuenhapkhauSvl?userId=<%=userId%>&delete=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Delete20.png" alt="Xóa" width="20" height="20" title="Xóa" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn muốn xóa Thuế Nhập Khẩu này?')) return false;"></A>
												
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		
						                                 List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
						                                 if(ktList.size() > 0)
											                {
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
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
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
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>
	                							
												<% } else if( tt.equals("1") ) { %>
													<A href = "../../ErpThuenhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" title = "Hiển thị" longdesc="Hiển thị" border = 0></A>&nbsp;
<%-- 							                   		<A href = "../../ErpThuenhapkhauUpdateSvl?userId=<%=userId%>&updateVAT=<%= ht.getTNKID() %>"><img src="../images/Edit20.png" alt="Cập nhật thuế VAT" title="Cập nhật thuế VAT" width="20" height="20" longdesc="Cập nhật thuế VAT" border = 0></A> &nbsp; --%>
													<A href = "../../ErpThuenhapkhauSvl?userId=<%=userId%>&chotVAT=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/button.png" alt="Chốt thuế VAT" width="20" height="20" title="Chốt thuế VAT" longdesc="Chốt thuế VAT" border=0 onclick="if(!confirm('Bạn muốn chốt Thuế VAT?')) return false;"></A>
													
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		 List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
					                                 		if(ktList.size() > 0)
										                	{							                       	 	
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
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" /></td>
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
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>
	                																
												<% }else if (tt.equals("2")){ %>
													<A href = "../../ErpThuenhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" title = "Hiển thị" longdesc="Hiển thị" border = 0></A>&nbsp;
													
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
					                                 		if(ktList.size() > 0)
										               		 {							                       	 	
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
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" /></td>
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
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>
	                							
												<% } else { %>
													<A href = "../../ErpThuenhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getTNKID() %>&loaimh=<%=obj.getLoaiMh()%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" title = "Hiển thị" longdesc="Hiển thị" border = 0></A>&nbsp;
												<%} %>
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% 	
	try
    {
		if(tnkRs != null)
			tnkRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>