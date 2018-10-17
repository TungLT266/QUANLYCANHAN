<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaChe"%>
<%@page import="geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaChe_ChiTiet"%>
<%@page import="geso.traphaco.center.util.*"%>
<%@page import="java.util.List"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpKeHoachPhaChe obj = (IErpKeHoachPhaChe)session.getAttribute("obj");
	List<ErpKeHoachPhaChe_ChiTiet> KhpcChitietList = obj.getKhpcChitietList();
	ResultSet SanphamRs = obj.getSanphamRs();
	ResultSet KhuvucSXRs = obj.getKhuvucSXRs(); %>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function save() {
		if(document.getElementById("ngaykehoach").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập ngày kế hoạch.";
			return false;
		}
		
		if(document.getElementById("bophanthuchien").value == ""){
			document.getElementById("dataerror").value = "Bạn chưa chọn bộ phận thực hiện.";
			return false;
		}
		
		if(document.getElementById("sanpham").value == ""){
			document.getElementById("dataerror").value = "Bạn chưa chọn sản phẩm.";
			return false;
		}
		
		document.forms["kehoachphache"].action.value = "save";
		document.forms["kehoachphache"].submit(); 
    }
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="kehoachphache" method="post" action="../../ErpKeHoachPhaCheUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							  	<%if(obj.getId().length()>0){%>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ sơ kiểm nghiệm > Chức năng > Kế hoạch pha chế, hiệu chuẩn thuốc > Cập nhật</TD>
							  	<%} else { %>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ sơ kiểm nghiệm > Chức năng > Kế hoạch pha chế, hiệu chuẩn thuốc > Tạo mới</TD>
						  		<%} %>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ErpKeHoachPhaCheSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
							    <TD width="2" align="left" ></TD>
						    	<TD width="30" align="left" >
						    		<div id="btnSave">
						    			<A href="javascript: save()" >
						    				<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset">
					    				</A>
						    		</div>
							    </TD>
								<TD >&nbsp; </TD>						
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
		    				<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Kế hoạch pha chế, hiệu chuẩn thuốc </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD width="15%" class="plainlabel">Ngày kế hoạch</TD>
									<TD class="plainlabel" valign="middle">
										<input class="days" type="text" name="ngaykehoach" id="ngaykehoach" value="<%=obj.getNgaykehoach() %>" style="width: 200px">
									</TD>
									<TD width="15%" class="plainlabel">Bộ phận thực hiện</TD>
									<TD class="plainlabel">
										<select name="bophanthuchien" id="bophanthuchien" style="width: 300px" class="select2">
			                        		<option value=""></option>
			                        		<%if(KhuvucSXRs != null){
												while(KhuvucSXRs.next()){ 
													if(KhuvucSXRs.getString("pk_seq").equals(obj.getBophanthuchien())){ %>
														<option value="<%=KhuvucSXRs.getString("pk_seq") %>" selected><%=KhuvucSXRs.getString("ten") %></option>
													<%} else { %>
														<option value="<%= KhuvucSXRs.getString("pk_seq") %>" ><%=KhuvucSXRs.getString("ten") %></option>
													<%}
												}
											} %>
			                        	</select>
									</TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD width="15%" class="plainlabel">Loại</TD>
									<TD class="plainlabel">
										<select name="loai" style="width:200px" class="select2">
											<option value="1" selected>Pha chế</option>
											<option value="2" <%if(obj.getLoai().equals("2")){ %>selected<%} %>>Hiệu chuẩn</option>
											<option value="3" <%if(obj.getLoai().equals("3")){ %>selected<%} %>>Môi trường</option>
											<option value="4" <%if(obj.getLoai().equals("4")){ %>selected<%} %>>Môi trường nuôi cấy</option>
										</select>
									</TD>
									<TD width="15%" class="plainlabel">Sản phẩm</TD>
									<TD class="plainlabel">
										<select name="sanpham" id="sanpham" style="width: 300px" class="select2">
			                        		<option value=""></option>
			                        		<%if(SanphamRs != null){
												while(SanphamRs.next()){ 
													if(SanphamRs.getString("pk_seq").equals(obj.getSanpham())){ %>
														<option value="<%=SanphamRs.getString("pk_seq") %>" selected><%=SanphamRs.getString("ten") %></option>
													<%} else { %>
														<option value="<%= SanphamRs.getString("pk_seq") %>" ><%=SanphamRs.getString("ten") %></option>
													<%}
												}
											} %>
			                        	</select>
									</TD>
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Diễn giải/Mô tả</TD>
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<textarea name="diengiai" rows="7" style="width: 80%;color: black;"><%=obj.getDiengiai() %></textarea>
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" name="trangthai" value="1" checked><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" name="trangthai" value="1" ><i> Hoạt động</i>
			                            <%} %>
			                        </TD>                
			                  	</TR>
							</TABLE>
							<FIELDSET style="margin: 5px;">
							<LEGEND class="legendtitle" style="color:black">Kế hoạch chi tiết </LEGEND>
							<table cellpadding="0px" cellspacing="1px" width="100%">
								<tr class="tbheader">
									<th width="5%">STT</th>
									<th width="20%">Ngày pha chế/hiệu chuẩn</th>
									<th width="75%">Ghi chú</th>
								</tr>
								
								<%ErpKeHoachPhaChe_ChiTiet KhpcChitiet;
								for(int i=0; i<KhpcChitietList.size(); i++){
									KhpcChitiet = KhpcChitietList.get(i); %>
									<tr>
										<td style="text-align: center;"><%=i+1 %></td>
										<td><input class="days" type="text" name="ngayphache" style="width: 100%" value="<%=KhpcChitiet.getNgayphache() %>"></td>
										<td><input type="text" name="ghichu" value="<%=KhpcChitiet.getGhichu() %>" style="width: 100%"></td>
									</tr>
								<%} %>
								
								<%for(int j=KhpcChitietList.size(); j<10; j++){ %>
									<tr>
										<td style="text-align: center;"><%=j+1 %></td>
										<td><input class="days" type="text" name="ngayphache" style="width: 100%" value=""></td>
										<td><input type="text" name="ghichu" value="" style="width: 100%"></td>
									</tr>
								<%} %>
							</table>
							</FIELDSET>
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
	if (obj != null) {
		obj.DBClose();
		obj = null;
	}
	session.removeAttribute("obj");
} %>
