<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.phieuphache.*"%>
<%@page import="geso.traphaco.erp.beans.phieuphache.imp.*"%>
<%@page import="geso.traphaco.center.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.text.NumberFormat" %>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
	
	IErpPhieuPhaChe obj = (IErpPhieuPhaChe)session.getAttribute("obj");
	List<IErpPhieuPhaChe_SanPham> sanphamList = obj.getSanphamList();
	List<ThongTinNhapKho> ThongTinNhapKhoList = obj.getThongTinNhapKhoList();
	ResultSet KehoachChitietRs = obj.getKehoachChitietRs();
	/* ResultSet SanphamRs = obj.getSanphamRs(); */
	ResultSet KehoachRs = obj.getKehoachRs();
	ResultSet TailieuphacheRs = obj.getTailieuphacheRs();
	ResultSet KhoRs = obj.getKhoRs();
	ResultSet BinRs = obj.getBinRs(); %>
	
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
<form name ="phieuphache" method="post" action="../../ErpPhieuPhaCheUpdateSvl" >
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
							  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ sơ kiểm nghiệm > Chức năng > Phiếu pha chế, hiệu chuẩn thuốc thử > Hiển thị</TD>
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
									<A href="../../ErpPhieuPhaCheSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
								<TD >&nbsp; </TD>						
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Phiếu pha chế, hiệu chuẩn thuốc thử </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="15%" class="plainlabel">Ngày chứng từ <FONT class="erroralert">*</FONT></TD>
									<TD class="plainlabel" valign="middle" colspan="3">
										<input type="text" readonly id="ngaychungtu" name="ngaychungtu" value="<%=obj.getNgaychungtu() %>" style="width: 300px">
									</TD>
								</TR>
							
                          		<TR>
	                          		<TD width="15%" class="plainlabel">Thuộc loại</TD>
									<TD class="plainlabel" valign="middle">
										<select name="thuocloai" id="thuocloai" style="width: 300px" class="select2" disabled="disabled">
											<option value="0"></option>
											<%if (obj.getThuocloai().equals("1")) { %>
												<option value="1" selected="selected">Pha chế</option>
												<option value="2">Hiệu chuẩn</option>
												<option value="3">Môi trường</option>
												<option value="4">Môi trường nuôi cấy</option>
											<%} else if (obj.getThuocloai().equals("2")) { %>
												<option value="1">Pha chế</option>
												<option value="2" selected="selected">Hiệu chuẩn</option>
												<option value="3">Môi trường</option>
												<option value="4">Môi trường nuôi cấy</option>
											<%} else if (obj.getThuocloai().equals("3")) { %>
												<option value="1">Pha chế</option>
												<option value="2">Hiệu chuẩn</option>
												<option value="3" selected="selected">Môi trường</option>
												<option value="4">Môi trường nuôi cấy</option>
											<%} else if (obj.getThuocloai().equals("4")) { %>
												<option value="1">Pha chế</option>
												<option value="2">Hiệu chuẩn</option>
												<option value="3">Môi trường</option>
												<option value="4" selected="selected">Môi trường nuôi cấy</option>
											<%} else { %>
												<option value="1">Pha chế</option>
												<option value="2">Hiệu chuẩn</option>
												<option value="3">Môi trường</option>
												<option value="4">Môi trường nuôi cấy</option>
											<%} %>
										</select>
									</TD>
									
									<TD width="15%" class="plainlabel">Chọn ID kế hoạch <FONT class="erroralert">*</FONT></TD>
									<TD class="plainlabel">
										<select name="kehoach" id="kehoach" style="width: 300px" class="select2" disabled>
			                        		<option value=""></option>
			                        		<%if(KehoachRs != null){
												while(KehoachRs.next()){
													if(KehoachRs.getString("pk_seq").equals(obj.getKehoach())){ %>
														<option value="<%=KehoachRs.getString("pk_seq") %>" selected><%=KehoachRs.getString("pk_seq") %></option>
													<%} else { %>
														<option value="<%= KehoachRs.getString("pk_seq") %>" ><%=KehoachRs.getString("pk_seq") %></option>
													<%}
												}
											} %>
			                        	</select>
			                        	
			                        	<%if(obj.getKehoach().length() > 0){ %>
				                        	<a href="" id="thongso1" rel="subcontent1">
				           	 					&nbsp;<img alt="Thông tin nhập kho" src="../images/vitriluu.png">
				           	 				</a>
				           	 				
				           	 				<DIV id="subcontent1" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 400px; max-height:150px; overflow:auto; padding: 4px;">
				           	 					<table width="95%" align="center">
				           	 						<tr>
							                        	<th width="40%" style="font-size: 11px">Ngày kế hoạch</th>
							                            <th width="50%" style="font-size: 11px">Ghi chú</th>
							                            <th width="10%" style="font-size: 11px">Chọn</th>
							                        </tr>
							                        
							                        <%if(KehoachChitietRs != null){ %>
							                        	<%int count = 0; %>
							                        	<%while(KehoachChitietRs.next()){ %>
							                        		<tr>
									                        	<td><input readonly type="text" value="<%=KehoachChitietRs.getString("ngayphache") %>" style="width: 100%"></td>
									                            <td><input readonly type="text" value="<%=KehoachChitietRs.getString("ghichu") %>" style="width: 100%"></td>
									                            <td align="center">
									                            	<%if(KehoachChitietRs.getString("pk_seq").equals(obj.getKehoachCt())){ %>
									                            		<input type="checkbox" disabled id="kehoach_ct_<%=count %>" checked name="kehoach_ct" value="<%=KehoachChitietRs.getString("pk_seq") %>">
									                            	<%} else { %>
									                            		<input type="checkbox" disabled id="kehoach_ct_<%=count %>" name="kehoach_ct" value="<%=KehoachChitietRs.getString("pk_seq") %>">
									                            	<%} %>
																</td>
									                        </tr>
									                        <%count++; %>
							                        	<%} %>
							                        <%} %>
				           	 					</table>
				           	 					<div align="right">
							                     	<label style="color: red" ></label>
							                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                     	<a href="javascript:dropdowncontent.hidediv('subcontent1')" > Đóng lại </a>
						                     	</div>
				           	 				</DIV>
				           	 				<script type="text/javascript">
								            	dropdowncontent.init('thongso1', "right-top", 300, "click");
								            </script>
							            <%} %>
									</TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD width="15%" class="plainlabel">Sản phẩm</TD>
									<TD class="plainlabel">
										<%-- <input type="hidden" name="sanphamid" id="sanphamid" value="<%=obj.getSanphamId() %>"> --%>
										<input type="text" readonly="readonly" name="sanpham" id="sanpham" value="<%=obj.getSanpham() %>" style="width: 300px">
										<%-- <input type="hidden" name="spiskqlsl" value="<%=obj.getSpIsKqlsl() %>"> --%>
									</TD>
	                          		
									<TD width="15%" class="plainlabel">Người pha chế</TD>
									<TD class="plainlabel">
										<input readonly type="text" name="nguoiphache" value="<%=obj.getNguoiphache() %>" style="width: 300px">
									</TD>
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD width="15%" class="plainlabel">Tài liệu pha chế <FONT class="erroralert">*</FONT></TD>
									<TD class="plainlabel">
										<input type="hidden" name="tailieuphachecu" value="<%=obj.getTailieuphache() %>">
										<select name="tailieuphache" id="tailieuphache" style="width: 300px" class="select2" disabled>
			                        		<option value=""></option>
			                        		<%if(TailieuphacheRs != null){
												while(TailieuphacheRs.next()){
													if(TailieuphacheRs.getString("pk_seq").equals(obj.getTailieuphache())){ %>
														<option value="<%=TailieuphacheRs.getString("pk_seq") %>" selected><%=TailieuphacheRs.getString("ten") %></option>
													<%} else { %>
														<option value="<%= TailieuphacheRs.getString("pk_seq") %>" ><%=TailieuphacheRs.getString("ten") %></option>
													<%}
												}
											} %>
			                        	</select>
									</TD>
									<TD width="15%" class="plainlabel">Công thức pha chế</TD>
									<TD class="plainlabel">
										<input type="text" readonly value="<%=obj.getCongthucphache() %>" style="width: 300px">
									</TD>
			                  	</TR>
			                  	
			                  	<TR <%=obj.getSpIsKqlsl().equals("1") ? "style=\"display: none;\"" : "" %> id="khonhap1">
	                          		<TD width="15%" class="plainlabel">Kho nhập <FONT class="erroralert">*</FONT></TD>
									<TD class="plainlabel">
										<select name="khonhap" id="khonhap" style="width: 300px" class="select2" disabled>
			                        		<option value=""></option>
			                        		<%if(KhoRs != null){
			                        			KhoRs.beforeFirst();
												while(KhoRs.next()){
													if(KhoRs.getString("pk_seq").equals(obj.getKhonhap())){ %>
														<option value="<%=KhoRs.getString("pk_seq") %>" selected><%=KhoRs.getString("ten") %></option>
													<%} else { %>
														<option value="<%= KhoRs.getString("pk_seq") %>" ><%=KhoRs.getString("ten") %></option>
													<%}
												}
											} %>
			                        	</select>
									</TD>
									<TD width="15%" class="plainlabel">Số lượng nhập</TD>
									<TD class="plainlabel">
										<input readonly type="text" name="soluongnhap" id="soluongnhap" value="<%=obj.getSoluongnhap() %>" style="width: 300px; text-align: right;">
									</TD>
			                  	</TR>
			                  	
			                  	<TR <%=obj.getSpIsKqlsl().equals("1") ? "style=\"display: none;\"" : "" %> id="khonhap2">
			                  		<TD class="plainlabel" colspan="2"></TD>
			                  		<TD width="15%" class="plainlabel">Thông tin nhập kho</TD>
									<TD class="plainlabel">
										<a href="" id="thongso" rel="subcontent">
			           	 					&nbsp;<img alt="Thông tin nhập kho" src="../images/vitriluu.png">
			           	 				</a>
			           	 				
			           	 				<DIV id="subcontent" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 700px; max-height:290px; overflow:auto; padding: 4px;">
			           	 					<table width="95%" align="center">
			           	 						<tr>
						                        	<th width="20%" style="font-size: 11px">Số lô</th>
						                            <th width="35%" style="font-size: 11px">Vị trí</th>
						                            <th width="15%" style="font-size: 11px">Số thùng</th>
						                            <th width="15%" style="font-size: 11px">Số mẻ</th>
						                            <th width="15%" style="font-size: 11px">Số lượng chi tiết</th>
						                        </tr>
						                        <%ThongTinNhapKho ThongTinNhapKho;
						                        for(int i = 0; i < ThongTinNhapKhoList.size(); i++){
						                        	ThongTinNhapKho = ThongTinNhapKhoList.get(i); %>
							                        <tr>
							                        	<td><input type="text" readonly name="solo" value="<%=ThongTinNhapKho.getSolo() %>" style="width: 100%"></td>
							                        	<td>
															<select name="vitri" disabled style="width: 220px" class="select2">
								                        		<option value=""></option>
								                        		<%if(BinRs != null){
								                        			BinRs.beforeFirst();
																	while(BinRs.next()){
																		if(BinRs.getString("pk_seq").equals(ThongTinNhapKho.getVitri())){ %>
																			<option value="<%=BinRs.getString("pk_seq") %>" selected><%=BinRs.getString("ten") %></option>
																		<%} else { %>
																			<option value="<%= BinRs.getString("pk_seq") %>" ><%=BinRs.getString("ten") %></option>
																		<%}
																	}
																} %>
								                        	</select>
														</td>
							                        	<td><input type="text" readonly name="sothung" value="<%=ThongTinNhapKho.getSothung() %>" style="width: 100%"></td>
							                        	<td><input type="text" readonly name="some" value="<%=ThongTinNhapKho.getSome() %>" style="width: 100%"></td>
							                        	<td><input type="text" readonly name="soluongchitiet" value="<%=ThongTinNhapKho.getSoluongchitiet() %>" style="width: 100%; text-align: right;"></td>
							                        </tr>
						                        <%} %>
			           	 					</table>
			           	 					<div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent')" > Đóng lại </a>
					                     	</div>
			           	 				</DIV>
			           	 				<script type="text/javascript">
							            	dropdowncontent.init('thongso', "left-top", 300, "click");
							            </script>
									</TD>
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="15%">Diễn giải/Mô tả</TD>
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<textarea readonly name="diengiai" rows="5" style="width: 90%;color: black;"><%=obj.getDiengiai() %></textarea>
			                        </TD>           
			                  	</TR>
							</TABLE>
							<%if(obj.getTailieuphache().length() > 0){ %>
								<FIELDSET style="margin: 5px;">
									<LEGEND class="legendtitle" style="color:black">Sản phẩm chi tiết </LEGEND>
									<table cellpadding="0px" cellspacing="1px" width="100%">
										<tr class="tbheader">
											<th width="5%">STT</th>
											<th width="13%">Mã sản phẩm</th>
											<th width="25%">Tên sản phẩm</th>
											<th width="8%">ĐVT</th>
											<th width="22%">Kho xuất</th>
											<th width="14%">Số lượng lý thuyết</th>
											<th width="13%">Số lượng thực tế</th>
										</tr>
										
										<%IErpPhieuPhaChe_SanPham sanpham;
										List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList;
										ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
										for(int i = 0; i < sanphamList.size(); i++){
											sanpham = sanphamList.get(i); %>
											<tr>
												<td align="center"><%=i+1 %>
													<input type="hidden" name="tlpctt" value="<%=sanpham.getTlpcTtId() %>">
													<input type="hidden" name="idsp" value="<%=sanpham.getIdsp() %>">
												</td>
												<td><input type="text" readonly name="masp" value="<%=sanpham.getMasp() %>" style="width: 100%"></td>
												<td><input type="text" readonly name="tensp" value="<%=sanpham.getTensp() %>" style="width: 100%"></td>
												<td><input type="text" readonly name="dvt" value="<%=sanpham.getDvt() %>" style="width: 100%"></td>
												<td>
													<input type="hidden" name="khoxuatcu" value="<%=sanpham.getKhoxuat() %>">
													<select name="khoxuat" style="width: 250px" class="select2" disabled>
						                        		<option value=""></option>
						                        		<%if(KhoRs != null){
						                        			KhoRs.beforeFirst();
															while(KhoRs.next()){
																if(KhoRs.getString("pk_seq").equals(sanpham.getKhoxuat())){ %>
																	<option value="<%=KhoRs.getString("pk_seq") %>" selected><%=KhoRs.getString("ten") %></option>
																<%} else { %>
																	<option value="<%= KhoRs.getString("pk_seq") %>" ><%=KhoRs.getString("ten") %></option>
																<%}
															}
														} %>
						                        	</select>
												</td>
												<td><input type="text" readonly name="sllythuyet" value="<%=sanpham.getSoluonglythuyet() %>" style="width: 100%; text-align: right;"></td>
												<td align="center">
													<a href="" id="thongso_<%=i %>" rel="subcontent_<%=i %>">
						           	 					&nbsp;<img alt="Thông tin nhập kho" src="../images/vitriluu.png">
						           	 				</a>
						           	 				
						           	 				<DIV id="subcontent_<%=i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 1000px; max-height:500px; overflow:auto; padding: 4px;">
						           	 					<table width="95%" align="center">
						           	 						<tr>
									                    		<td colspan="2" style="font-size: 11px"><b>Tổng xuất</b></td>
									                    		<td colspan="10" ><input type="text" id="tongxuat_<%=i %>" name="tongxuat" value="<%= sanpham.getTongxuat() %>" style="width: 100px; text-align: right;" readonly></td>
									                    	</tr>
						           	 					
						           	 						<tr>
									                            <th width="6%" style="font-size: 11px">Số lô</th>
									                            <th width="8%" style="font-size: 11px">Ngày hết hạn</th>
									                            <th width="8%" style="font-size: 11px">Ngày nhập kho</th>
									                            <th width="6%" style="font-size: 11px">Mã mẻ</th>
									                            <th width="6%" style="font-size: 11px">Mã thùng</th>
									                            <th width="7%" style="font-size: 11px">Vị trí</th>
									                            <th width="6%" style="font-size: 11px">Mã phiếu</th>
									                            <th width="6%" style="font-size: 11px">Phiếu ĐT</th>
									                            <th width="6%" style="font-size: 11px">Phiếu EO</th>
									                            <th width="6%" style="font-size: 11px">Marq</th>
									                            <th width="6%" style="font-size: 11px">Hàm lượng</th>
									                            <th width="6%" style="font-size: 11px">Hàm ẩm</th>
									                            <th width="7%" style="font-size: 11px">Nhà sản xuất</th>
									                            <th width="8%" style="font-size: 11px">Số lượng tồn</th>
									                            <th width="8%" style="font-size: 11px">Số lượng chi tiết</th>
									                        </tr>
									                        
									                        <%SpChitietList = sanpham.getSpChitietList();
									                        for(int j = 0; j < SpChitietList.size(); j++){
									                        	SpChitiet = SpChitietList.get(j); %>
									                        	<tr>
									                        		<td>
									                        			<input type="hidden" name="khott_sp_ct_<%=i %>" value="<%=SpChitiet.getKhoSpCtId() %>">
									                        			<input type="hidden" name="loaidoituong_<%=i %>" value="<%=SpChitiet.getLoaidoituong() %>">
									                        			<input type="hidden" name="doituongid_<%=i %>" value="<%=SpChitiet.getDoituongid() %>">
									                        			<input type="text" readonly name="solo_<%=i %>" value="<%=SpChitiet.getSolo() %>" style="width: 100%">
									                        		</td>
									                        		<td><input type="text" readonly name="ngayhethan_<%=i %>" value="<%=SpChitiet.getNgayhethan() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="ngaynhapkho_<%=i %>" value="<%=SpChitiet.getNgaynhapkho() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="mame_<%=i %>" value="<%=SpChitiet.getMame() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="mathung_<%=i %>" value="<%=SpChitiet.getMathung() %>" style="width: 100%"></td>
									                        		<td>
									                        			<input type="hidden" name="binfk_<%=i %>" value="<%=SpChitiet.getBinFk() %>">
									                        			<input type="text" readonly name="vitri_<%=i %>" value="<%=SpChitiet.getVitri() %>" style="width: 100%">
									                        		</td>
									                        		<td><input type="text" readonly name="maphieu_<%=i %>" value="<%=SpChitiet.getMaphieu() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="phieudt_<%=i %>" value="<%=SpChitiet.getMaphieudinhtinh() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="phieueo_<%=i %>" value="<%=SpChitiet.getPhieueo() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="marq_<%=i %>" value="<%=SpChitiet.getMarq() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="hamluong_<%=i %>" value="<%=SpChitiet.getHamluong() %>" style="width: 100%"></td>
									                        		<td><input type="text" readonly name="hamam_<%=i %>" value="<%=SpChitiet.getHamam() %>" style="width: 100%"></td>
									                        		<td>
									                        			<input type="text" readonly name="nhasanxuat_<%=i %>" value="<%=SpChitiet.getNhasanxuat() %>" style="width: 100%">
									                        			<input type="hidden" readonly name="nsxid_<%=i %>" value="<%=SpChitiet.getNsxId() %>">
									                        		</td>
									                        		<td><input type="text" readonly name="soluongton_<%=i %>" value="<%=SpChitiet.getSoluongton() %>" style="width: 100%; text-align: right;"></td>
									                        		<td><input type="text" readonly name="soluongchitiet_<%=i %>" value="<%=SpChitiet.getSoluongchitiet() %>" style="width: 100%; text-align: right;" onchange="tinhtongxuat(<%=i %>);"></td>
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
												</td>
											</tr>
										<%} %>
									</table>
								</FIELDSET>
							<%} %>
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
