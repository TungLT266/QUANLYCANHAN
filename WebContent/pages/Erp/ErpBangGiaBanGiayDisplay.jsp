<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.banggiaban.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.banggiaban.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpBanggiabanGiay obj =(IErpBanggiabanGiay)session.getAttribute("bgBean");
	ResultSet lspRs=(ResultSet)obj.getLspRs();
	ResultSet dvkdList = obj.getDvkdRs();
	ResultSet kbhList = obj.getKbhRs();
	ResultSet lkhList = obj.getLkhRs();
	ResultSet khList = obj.getKhachhangRs();
	ResultSet dvdlList = obj.getDonViDoLuongRs();
	List<IBanggia_sp> spList = (List<IBanggia_sp>)obj.getSpList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
 
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#khId").select2(); });
     
 </script>


<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["bgban"].submit();
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
	}
	
	function DinhDangTien(num) 
	{
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function save()
	{
	  document.forms["bgban"].action.value = "save";
	  document.forms["bgban"].submit(); 
    }
	
	function submitFrom()
	{
	  document.forms["bgban"].action.value = "submit";
	  document.forms["bgban"].submit(); 
    }
	 function sellectAll3()
	 {
		 var chkAll = document.getElementById("chkAll3");
		 var spIds = document.getElementsByName("loaisanpham");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	function changeKhachHang()
	{
		document.forms["bgban"].action.value = "changeKhachHang";
		document.forms["bgban"].submit(); 
	}
	function reload_sp(){
		document.forms["bgban"].action.value = "reload_sp";
		document.forms["bgban"].submit(); 
	}
	function checkedAll()
	{
		 var chkAll = document.getElementById("chekAllSp");
		 var spIds = document.getElementsByName("chonban");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	function checkedAll_KhachHang()
	{
		 var chkAll = document.getElementById("checkAllKh");
		 var spIds = document.getElementsByName("khApDung");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	function checkedAll_HopDong(hdId)
	{
		 var chkAll = document.getElementById(hdId + ".checkAll");
		 var spIds = document.getElementsByName(hdId + ".chonban");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="bgban" method="post" action="../../ErpBanggiabanGiayUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getId() %>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Định giá bán > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpBanggiabanGiaySvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin bảng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tên bảng giá </TD>   
		                        <TD class="plainlabel" valign="middle"  colspan = 3 >
		                        	<input type="text" readonly name="tenbanggia" value="<%= obj.getTenbanggia() %>" readonly="readonly" > 
		                        </TD>          
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Đơn vị kinh doanh </TD>   
		                        <TD class="plainlabel" valign="middle"   >
		                        	<input type="hidden" name="dvkdId" value="<%= obj.getDvkdId() %>" >
		                        	<select disabled  disabled="disabled" >
										<option value=""></option>
										<% if (dvkdList != null)
										{
											while (dvkdList.next())
											{
												if (dvkdList.getString("dvkdId").equals(obj.getDvkdId()))
												{
												%>
													<option value="<%=dvkdList.getString("dvkdId")%>" selected="selected"><%=dvkdList.getString("dvkd")%></option>
												<% } else { %>
													<option value="<%=dvkdList.getString("dvkdId")%>"><%=dvkdList.getString("dvkd")%></option>
										<% } } dvkdList.close(); } %>
									</select>
		                        </TD>     
		                        
		                         <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="lspId" rel="subcontentLsp">
	           	 							&nbsp; <img alt="Thông tin loại sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentLsp" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 450px;  max-height:250px; overflow:auto; padding: 4px;"  >
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã </th>
				                            <th width="250px">Tên </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" disabled onchange="sellectAll3()" id="chkAll3" ></th>
				                        </tr>
		                            	<%
			                        		if(lspRs != null)
			                        		{
			                        			while(lspRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= lspRs.getString("ten") %>"></td>
			                        				<td><input style="width: 100%" value="<%= lspRs.getString("ten") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getLspstr().indexOf(lspRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" disabled name="loaisanpham" checked="checked" value="<%= lspRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" disabled name="loaisanpham" value="<%= lspRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } lspRs.close(); } %>
				                    </table>
				            </DIV>                        
                        </TD>            
		                  </TR>
		                 
                          <TR>
                          		<TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="hidden" name="kbhId"  value="<%= obj.getKbhId() %>" >
		                        	<select disabled  disabled="disabled" >
										<option value=""></option>
										<% if (kbhList != null)
										{
											while (kbhList.next())
											{
												if (kbhList.getString("kenhId").equals(obj.getKbhId()))
												{
												%>
													<option value="<%=kbhList.getString("kenhId")%>" selected="selected"><%=kbhList.getString("kenh")%></option>
												<% } else { %>
													<option value="<%=kbhList.getString("kenhId")%>"><%=kbhList.getString("kenh")%></option>
										<% } } kbhList.close(); } %>
									</select>
		                        </TD>    
		                        
		                        <TD class="plainlabel" valign="middle" >Sử dụng cho </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<input type="hidden" name="sudung"  value="<%= obj.getSudung() %>" >
		                        	<select  disabled="disabled" >
										<option value = "0">Bán hàng</option>
										<option value = "1">Dự báo</option>
									</select>
		                        </TD>   
		                              
		                  </TR> 
						  <TR>
								<TD width="170px" class="plainlabel">Hiệu lực từ ngày </TD>
								<TD class="plainlabel">
									<input type="text" readonly  class="days" size="11"
                                    	id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10"  />
								</TD>		              
								
								<TD width="170px" class="plainlabel">Đến ngày </TD>
								<TD class="plainlabel">
									<input type="text" readonly  class="days" size="11"
                                    	id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10"  />
								</TD>		              
								
								    
						  </TR>		                  
		                 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Trạng thái </TD>   
		                        <TD class="plainlabel" valign="middle" colspan = 3>
		                            <% if(obj.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" disabled name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" disabled name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
		                        </TD>                
		                  </TR>
		                  <TR>
		                  		<td colspan="4">
		                  		
		                  			<ul class="tabs">
		                  			
		                  				<li><a href="#">Áp dụng cho khách hàng</a></li>
			                  			<li><a href="#">Chọn sản phẩm</a></li>
			                  			
			                  			
			                  		</ul>
		                  			
		                  			<div class="panes">
		                  			
										<div>
										
										<table>
											 <TR>
				                          		<TD class="plainlabel" valign="middle" width=120px>Loại khách hàng </TD>   
						                        <TD valign="middle"  >
						                        	<select disabled name="lkhId" id="lkhId" onchange="submitFrom();" >
														<option value=""></option>
														<% if (lkhList != null)
														{
															while (lkhList.next())
															{
																if (lkhList.getString("loaikhId").equals(obj.getLkhId()))
																{
																%>
																	<option value="<%=lkhList.getString("loaikhId")%>" selected="selected"><%=lkhList.getString("loaikh")%></option>
																<% } else { %>
																	<option value="<%=lkhList.getString("loaikhId")%>"><%=lkhList.getString("loaikh")%></option>
														<% } } lkhList.close(); } %>
													</select>
						                        </TD>          
						                  </TR> 
						                  </table>	
						                  <br />
											
											<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
												<TR class="tbheader">
													<TH width="10%">Mã khách hàng </TH>
													<TH width="30%">Họ tên</TH>								
													<TH width="10%">Điện thoại</TH>
													<TH width="40%">Địa chỉ </TH>
													
													<TH width="10%">Chọn
														<input type="checkbox" disabled id="checkAllKh" onclick="checkedAll_KhachHang();">
													</TH>
												</TR>
												
												<% if (khList != null)
												{
													khList.beforeFirst();
													
													int rowCount = 0;
													while (khList.next())
													{
													 if( obj.getKhApDungIds().indexOf(khList.getString("pk_seq")) >= 0 ) { %>	
													
											
											<% if(rowCount % 2 == 0) { %>			
													<TR class="tbdarkrow">
											<% } else { %>
													<TR class="tblightrow">
											<% } %>
													
														<TD><%= khList.getString("ma") %></TD>
														<TD><%= khList.getString("ten") %></TD>
														<TD><%= khList.getString("dienthoai") %></TD>
														<TD><%= khList.getString("diachi") %></TD>
														
														<% if( obj.getKhApDungIds().indexOf(khList.getString("pk_seq")) >= 0 ) { %> 
															<TD align="center">
																<input type="checkbox" disabled name="khApDung" value="<%= khList.getString("pk_seq") %>"  checked="checked" >
															</TD>
														<%  } else { %> 
															<TD align="center" > 
																<input type="checkbox" disabled name="khApDung" value="<%= khList.getString("pk_seq") %>"   >
															</TD>	
														<% } %>
														
													</TR>	
														
												<% rowCount++;  } }} %>
												
												
											</TABLE>
											
										</div>
										
										<div>
			                  			
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
												<TR class="tbheader">
													<TH width="10%">Mã sản phẩm </TH>
													<TH width="30%">Tên sản phẩm</TH>								
													
													<TH width="8%">Giá bán</TH>
													<TH width="8%">Giá mới</TH>
													<TH width="8%">Đơn vị</TH>
													<TH width="10%">Chọn bán 
														<input type="checkbox" disabled id="chekAllSp" onclick="checkedAll();">
													</TH>
												</TR>
												
												<% for(int i = 0; i < spList.size(); i++ ) { 
													IBanggia_sp sanpham = spList.get(i);	%>
													<% if(sanpham.getChonban().equals("1")) { %>
													<TR>
														<TD>
															<input type="hidden" style="width: 100%" name="hopdong" value="<%= sanpham.getHopdong() %>" >
															<input type="hidden" style="width: 100%" name="idsanpham" value="<%= sanpham.getIdsp() %>" >
															<input type="text" readonly style="width: 100%" name="masanpham" value="<%= sanpham.getMasp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" readonly style="width: 100%" name="tensanpham" value="<%= sanpham.getTensp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" readonly style="width: 100%; text-align: right;" name="giaban" value="<%= sanpham.getGiaban() %>" readonly="readonly" >
														</TD>
														<TD>
															<% if(obj.getBlock().equals("1")) { %>
																<input type="text" readonly style="width: 100%; text-align: right;" name="giamoi" value="<%= sanpham.getGiabanNew() %>" readonly="readonly" >
															<% } else { %>
																<input type="text" readonly style="width: 100%; text-align: right;" name="giamoi" value="<%= sanpham.getGiabanNew() %>" > <!-- onkeyup="FormatNumber(this);" > -->
															<% } %>
														</TD>
														<TD>
															<%-- <input type="text" readonly style="width: 100%; text-align: center;" name="donvi" value="<%= sanpham.getDonvi() %>" readonly="readonly" > --%>
															
															<select disabled name="dvdlId" id="dvdlId">															
																<% try {
																dvdlList.beforeFirst();
																if (dvdlList != null)
																	{
																		while (dvdlList.next()){
																			if(!(sanpham.getDvdlId()==null)){
																				if(sanpham.getDvdlId().equals(dvdlList.getString("dvdlId"))){ %>
																					<option value="<%= dvdlList.getString("dvdlId") %>" selected><%= dvdlList.getString("dvdlTen")%> </option>
																			<% 	}else{ %>
																					<option value="<%= dvdlList.getString("dvdlId") %>" ><%= dvdlList.getString("dvdlTen")%> </option>
																			<% 	}
																			}
																			else{ %>
																				<option value="<%= dvdlList.getString("dvdlId") %>" ><%= dvdlList.getString("dvdlTen")%> </option>
																		<%	}
																		} 
																	} 
																} catch (java.sql.SQLException e){}
																%>
															</select>
														</TD>
														<TD align="center">
															<% if(sanpham.getChonban().equals("1")) { %>
																<input type="checkbox" disabled name="chonban" value="<%= sanpham.getIdsp() %>"  checked="checked" >
															<% } else { %>
																<input type="checkbox" disabled name="chonban" value="<%= sanpham.getIdsp() %>"  >
															<% } %>
														</TD>
													</TR>
													
												<%} } %>
												
											</TABLE>
										</div>
										
									</div>
		                  		</td>
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
<script type="text/javascript">
	dropdowncontent.init('lspId', "right-bottom", 300, "click");
 	
</script>
</BODY>
</HTML>

<%
try{
	if(khList != null)
		khList.close();
	spList.clear();
	if (dvdlList != null)
		dvdlList.close();
	
}catch (Exception ex)
{
	ex.printStackTrace();
}
finally
{
	if (obj != null)
	{
		obj.DbClose();
		obj = null;
	}
	session.removeAttribute("bgBean") ; 
}
%>
<%}%>
