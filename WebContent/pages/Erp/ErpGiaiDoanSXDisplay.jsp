<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.erp.beans.giaidoansx.imp.*" %>
<%@page import="geso.traphaco.erp.beans.giaidoansx.*" %>
<%@page import="java.util.List" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="geso.traphaco.center.util.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.sql.SQLException" %>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpGiaidoanSX obj = (IErpGiaidoanSX)session.getAttribute("csxBean");
	List<IErpGiaiDoanSXThongSo> thongSoList = obj.getThongSoList();
	ResultSet LoaisanphamRs = obj.getLoaisanphamRs();
	ResultSet loaimauisRs = obj.getLoaimauinSxRs();
	ResultSet tscdRs = obj.getTscdRs();
	ResultSet dvtRs = obj.getDvtRs();
%>
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
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="giaidoansx" method="post" action="../../ErpGiaidoanSXUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Giai đoạn sản xuất > Hiển thị</TD>
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
									<A href="../../ErpGiaidoanSXSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>					
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Thông tin giai đoạn sản xuất </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD class="plainlabel" valign="middle" width="20%" >Mã giai đoạn</TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3" >
			                        	<input readonly="readonly" type="text" style="width:250px" name="ma" value="<%=obj.getMa() %>">
			                        </TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD class="plainlabel" valign="middle" width="20%">Tên giai đoạn</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" style="width:250px" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" readonly="readonly" > 
			                        </TD>
			                        
			                        <TD class="plainlabel" valign="middle" width="20%" >
			                        	<%=obj.getGiaodiennhap().equals("0") ? "Số lần nhập/Số lượng mẫu" : "" %>
			                        </TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<%if(obj.getGiaodiennhap().equals("0")){ %>
			                        		<input type="text" name="solannhap" id="solannhap" value="<%=obj.getSolannhap() %>" style="width: 250px; text-align: right;" readonly="readonly">
			                        	<%} %>
			                        </TD>
			                  	</TR>
		                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="20%" >Giao diện nhập thông số</TD>   
			                        <TD class="plainlabel" valign="middle" style="width: 300px" >
			                        	<select style="width: 250px" class="select2" disabled>
			                        		<%if(obj.getGiaodiennhap().length() > 0){ %>
				                        		<option <%if(obj.getGiaodiennhap().equals("0")){ %> selected <%} %> value="0">0. Cân chia nguyên liệu</option>
			                        			<option <%if(obj.getGiaodiennhap().equals("1")){ %> selected <%} %> value="1">1. Giao diện nhập thông số 1 lần duy nhất - 1 mẫu</option>
			                        			<option <%if(obj.getGiaodiennhap().equals("2")){ %> selected <%} %> value="2">2. Giao diện nhập thông số nhiều lần - nhiều mẫu</option>
			                        			<option <%if(obj.getGiaodiennhap().equals("3")){ %> selected <%} %> value="3">3. Nhập - xuất</option>
		                        			<%} %>
			                        	</select>
			                        </TD>
			                        
			                        <%if(obj.getGiaodiennhap().equals("0")){ %>
			                        	<TD class="plainlabel" valign="middle" width="20%" >Chọn loại sản phẩm trên BOM</TD>   
				                        <TD class="plainlabel" valign="middle">
				                        	<select multiple="multiple" name="loaisanpham" style="width: 250px" class="select2" disabled="disabled">
												<%if(LoaisanphamRs != null){
													while(LoaisanphamRs.next()){ 
														if(obj.getLoaisanpham().indexOf(LoaisanphamRs.getString("pk_seq")) >= 0){ %>
															<option value="<%=LoaisanphamRs.getString("pk_seq") %>" selected><%=LoaisanphamRs.getString("ten") %></option>
														<%} else { %>
															<option value="<%=LoaisanphamRs.getString("pk_seq") %>" ><%=LoaisanphamRs.getString("ten") %></option>
														<%}
													}
												} %>
				                        	</select>
				                        </TD>
			                        <%} else if(obj.getGiaodiennhap().equals("2")){ %>
			                        	<TD class="plainlabel" valign="middle" width="20%" >Số lượng mẫu</TD>   
				                        <TD class="plainlabel" valign="middle">
				                        	<input type="text" name="soluongmau" value="<%=obj.getSoluongmau() %>" style="width: 250px; text-align: right;" readonly="readonly">
				                        </TD>
			                        <%} else { %>
			                        	<TD class="plainlabel" colspan="2"></TD>
			                        <%} %>
	                  			</TR>
	                  			
	                  			<TR>
	                          		<TD class="plainlabel" valign="middle" width="20%" >Loại mẫu in sản xuất</TD>   
			                        <TD class="plainlabel" valign="middle" style="width: 300px" >
			                        	<select disabled style="width: 250px" class="select2">
			                        		<option value=""></option>
			                        		<%if(loaimauisRs != null){
												while(loaimauisRs.next()){ 
													if(loaimauisRs.getString("pk_seq").equals(obj.getLoaimauinId())){ %>
														<option value="<%=loaimauisRs.getString("pk_seq") %>" selected><%=loaimauisRs.getString("Ten") %></option>
													<%} else { %>
														<option value="<%= loaimauisRs.getString("pk_seq") %>" ><%=loaimauisRs.getString("Ten") %></option>
													<%}
												}
											} %>
			                        	</select>
			                        </TD>         
			                        
			                        <TD class="plainlabel" valign="middle" colspan="2" >
			                        	<%if(obj.getGiaodiennhap().equals("0")){ %>
			                        		<input disabled="disabled" type="checkbox" <%=obj.getIsAllBom().equals("1") ? "checked" : "" %> name="isallbom" value="1" >Lấy từ tất cả BOM trên LSX
			                        	<%} %>
				                    </TD> 
	                  			</TR>
	                  			
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="20%">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" disabled checked><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" disabled ><i> Hoạt động</i>
			                            <%} %>
			                        </TD>                
			                  	</TR>
							</TABLE>
						
							<table cellpadding="0px" cellspacing="1px" width="100%">
								<tr class="tbheader">
									<th width="5%" >STT</th>
									<th width="35%" >TSCĐ/CPTT</th>
									<th width="25%" >Thiết bị sản xuất</th>
									<th width="25%" >Thông số chung</th>
									<th width="10%" >Thông số</th>
								</tr>
								<%IErpGiaiDoanSXThongSo thongSo;
								ResultSet thietBiSXRs;
								for(int i=0; i<thongSoList.size(); i++){
									thongSo = thongSoList.get(i);
									thietBiSXRs = thongSo.getThietBiSXRs(); %>
									<tr>
										<td style="text-align: center;"><%=i+1 %></td>
										
										<td>
											<select style="width: 405px" class="select2" disabled>
												<option value="" ></option>
												<%if(tscdRs != null){
													tscdRs.beforeFirst();
													while(tscdRs.next()){ 
														if(tscdRs.getString("pk_seq").equals(thongSo.getTscdId())){ %>
															<option value="<%=tscdRs.getString("pk_seq") %>" selected><%=tscdRs.getString("ten") %></option>
														<%} else { %>
															<option value="<%= tscdRs.getString("pk_seq") %>" ><%=tscdRs.getString("ten") %></option>
														<%}
													}
												} %>
											</select>
										</td>
										
										<td>
											<select style="width: 290px" class="select2" disabled>
												<option value="" ></option>
												<%if(thietBiSXRs != null){
													while(thietBiSXRs.next()){ 
														if(thietBiSXRs.getString("pk_seq").equals(thongSo.getThietBiSXId())){ %>
															<option value="<%=thietBiSXRs.getString("pk_seq") %>" selected><%=thietBiSXRs.getString("ten") %></option>
														<%} else { %>
															<option value="<%= thietBiSXRs.getString("pk_seq") %>" ><%=thietBiSXRs.getString("ten") %></option>
														<%}
													}
												} %>
											</select>
										</td>
										
										<td><input type="text" value="<%=thongSo.getThongSoChung() %>" style="width: 100%" readonly></td>
										
										<td align="center">
											<%if(thongSo.getTscdId().length() > 0){ %>
												<a href="" id="thongso_<%=i %>" rel="subcontent_<%=i %>">
					           	 					&nbsp;<img alt="Nhập thông số" src="../images/vitriluu.png">
					           	 				</a>
					           	 				
					           	 				<DIV id="subcontent_<%=i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 950px; max-height:250px; overflow:auto; padding: 4px;">
					           	 					<table width="95%" align="center">
					           	 						<tr>
								                        	<th width="3%" style="font-size: 11px">STT</th>
								                            <th width="38%" style="font-size: 11px">Thông số</th>
								                            <th width="22%" style="font-size: 11px">Yêu cầu</th>
								                            <th width="13%" style="font-size: 11px">Thông số từ</th>
								                            <th width="13%" style="font-size: 11px">Thông số đến</th>
								                            <th width="8%" style="font-size: 11px">DVT</th>
								                            <th width="3%" style="font-size: 11px">Tick</th>
								                        </tr>
								                        
								                        <%List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList = thongSo.getTbsxThongsoList();
							                        	for(int k=0; k<tbsxThongsoList.size(); k++){
							                        		ErpGiaiDoanSX_TS_ChiTiet tbsxThongso = tbsxThongsoList.get(k); %>
							                        		
							                        		<tr>
								                        		<td style="text-align: center;">
								                        			<%=k+1 %>
								                        		</td>
								                        		<td><input type="text" readonly style="width: 100%" value="<%=tbsxThongso.getDienGiai() %>"></td>
								                        		<td><input type="text" readonly style="width: 100%" value="<%=tbsxThongso.getYeucau() %>"></td>
								                        		<td><input type="text" readonly style="width: 100%;text-align: right;" value="<%=tbsxThongso.getThongsoTu() %>" onkeypress="return keypress(event);"></td>
								                        		<td><input type="text" readonly style="width: 100%;text-align: right;" value="<%=tbsxThongso.getThongsoDen() %>" onkeypress="return keypress(event);"></td>
								                        		<td>
																	<select disabled style="width: 100%">
																		<option value="" ></option>
																		<%if(dvtRs != null){
																			dvtRs.beforeFirst();
																			while(dvtRs.next()){
																				if(dvtRs.getString("pk_seq").equals(tbsxThongso.getDonVi())){ %>
																					<option selected value="<%=dvtRs.getString("pk_seq") %>"><%=dvtRs.getString("donvi") %></option>
																				<%} else { %>
																					<option value="<%=dvtRs.getString("pk_seq") %>"><%=dvtRs.getString("donvi") %></option>
																				<%}
																			}
																		} %>
																	</select>
																</td>
								                        		<td style="text-align: center;">
								                        			<%if(tbsxThongso.getTick().equals("1")) {%>
																		<input type="checkbox" disabled checked="checked">
																	<%} else {%>
																		<input type="checkbox" disabled>
																	<%} %>
								                        		</td>
								                        	</tr>
							                        	<%} %>
					           	 					</table>
					           	 					<div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%=i %>')" > Đóng lại </a>
							                     	</div>
					           	 				</DIV>
					           	 				<script type="text/javascript">
									            	dropdowncontent.init('thongso_<%=i %>', "left-top", 300, "click");
									            </script>
											<%} %>
										</td>
									</tr>
									<%if(thongSo != null)
										thongSo.DbClose();
								} %>
							</table>
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
		obj.DbClose();
		obj = null;
	}
	session.removeAttribute("csxBean");
} %>
