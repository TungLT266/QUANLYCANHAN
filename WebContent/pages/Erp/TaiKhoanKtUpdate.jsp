<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.taikhoankt.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ITaikhoankt tkktBean = (ITaikhoankt)session.getAttribute("tkktBean"); %>
<% ResultSet CongTyRs = tkktBean.getCongTyRs(); %>
<% ResultSet LoaiTaiKhoanRs = tkktBean.getLoaiTaiKhoanRs(); %>
<% ResultSet loaiTKBaoCaoRs = tkktBean.getLoaiTKBaoCaoRs(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tài khoản kế toán</title>
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">

function submitform() {
	document.forms['tkktForm'].action.value= 'submit';
    document.forms['tkktForm'].submit();
}

function saveform()
{
	 var ctSohieu = document.getElementById('SoHieuTaiKhoan');
	 var ctTen= document.getElementById('TenTaiKhoan');
	 var ctLoai = document.getElementById('LoaiTaiKhoanId');
	 //var CongTyId = document.getElementById('CongTyId');
	 if(ctSohieu.value == "")
	 {
		 alert("Vui lòng nhập vào Số Hiệu Tài Khoản !");
		 ctSohieu.focus();
		 return;
	 }
	 if(ctTen.value == "")
	 {
		 alert("Vui lòng nhập vào Tên Tài Khoản !");
		 ctTen.focus();
		 return;
	 }
	 if(ctLoai.value == "")
	 {
		 alert("Vui lòng nhập vào loại tài khoản !");
		 ctLoai.focus();
		 return;
	 }
	/*  if(CongTyId.value == "")
	 {
		 alert("Vui lòng chọn Công ty !");
		 ctTen.focus();
		 return;
	 } */
	 document.forms['tkktForm'].action.value= 'Save';
     document.forms['tkktForm'].submit();
}
 </SCRIPT>
</head>
<body>
<form name="tkktForm" method="post" action="../../TaikhoanktUpdateSvl" >
<input type="hidden" name="action" value="1"/>
<input type="hidden" name="id" value='<%=tkktBean.getId()%>'/>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Tài khoản kế toán &gt; Cập nhật </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../TaikhoanketoanSvl?userId=<%= userId %>&view=TT&chixem=0" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>				
		    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%= tkktBean.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Tài khoản kế toán
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" >Số hiệu tài khoản<FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" >
									  <input maxlength="50" type="text" class="txtsearch" size="20" readonly="readonly" id="SoHieuTaiKhoan" name="SoHieuTaiKhoan" value="<%= tkktBean.getSoHieuTaiKhoan() %> "></TD>
								  </TR>
								  <TR>
									 <TD width="15%" class="plainlabel" >Tên tài khoản<FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><input maxlength="50" type="text" class="txtsearch" size="20" id="TenTaiKhoan" name="TenTaiKhoan" value="<%= tkktBean.getTenTaiKhoan() %>"></TD>
								  </TR>
								  
								   <TR>
											<TD width="20%" class="plainlabel" >Thuộc loại tài khoản<FONT class="erroralert">*</FONT></TD>
										    <TD width="80%" class="plainlabel" >
										   		<select id="LoaiTaiKhoanId"  name="LoaiTaiKhoanId" >
												<option value="2"> </option>
													<%
													if(LoaiTaiKhoanRs != null)
													{
														try
														{
															while(LoaiTaiKhoanRs.next())
															{
																if(tkktBean.getLoaiTaiKhoanId().equals(LoaiTaiKhoanRs.getString("pk_seq")))
																{%>
																	<option value="<%= LoaiTaiKhoanRs.getString("pk_seq") %>"   selected="selected"><%= LoaiTaiKhoanRs.getString("Loaitk") %> </option> 								
																<%}else{ %>
																	<option value="<%=LoaiTaiKhoanRs.getString("pk_seq") %>" ><%= LoaiTaiKhoanRs.getString("Loaitk") %></option> 
																<% }
															}
															LoaiTaiKhoanRs.close();
														}
														catch(SQLException ex){}
													}
													%>
												</select>
                         
										    </TD>
									</TR>
									
									 <TR>
                                      <TD width="20%" class="plainlabel" >Loại tài khoản báo cáo <FONT class="erroralert">*</FONT></TD>
                                        <TD width="80%" class="plainlabel" >
                                          <select id="loaiTKBaoCao"  name="loaiTKBaoCao" >
                                        <option value="0"> </option>
                                          <%
                                          if(loaiTKBaoCaoRs != null)
                                          {
                                            try
                                            {
                                              while(loaiTKBaoCaoRs.next())
                                              {
                                                if(tkktBean.getLoaiTaiKhoanId().equals(loaiTKBaoCaoRs.getString("pk_seq")))
                                                {%>
                                                  <option value="<%= loaiTKBaoCaoRs.getString("pk_seq") %>"   selected="selected"><%= loaiTKBaoCaoRs.getString("Loaitk") %> </option>                 
                                                <%}else{ %>
                                                  <option value="<%=loaiTKBaoCaoRs.getString("pk_seq") %>" ><%= loaiTKBaoCaoRs.getString("Loaitk") %></option> 
                                                <% }
                                              }
                                              loaiTKBaoCaoRs.close();
                                            }
                                            catch(SQLException ex){}
                                          }
                                          %>
                                        </select>
                                         
                                        </TD>
                                  </TR>
										
								 	<TR>
											<TD width="20%" class="plainlabel" >Tài khoản có chi tiết</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String tk2= tkktBean.getTaiKhoanCoChiTiet();
										    
												if(tk2.equals("1"))
												{
												%>
													<input id="TaiKhoanCoChiTiet" name="TaiKhoanCoChiTiet" value="1" type="checkbox" checked="checked" onchange="submitform();">
												<%
												}
												else
												{%>
													<input id="TaiKhoanCoChiTiet" name="TaiKhoanCoChiTiet" value="1" type="checkbox" onchange="submitform();">
												<%}%>	
										    </TD>
										</TR>
								  

								   <%-- 		<TR>
											<TD width="20%" class="plainlabel" >Thuộc công ty <FONT class="erroralert">*</FONT></TD>
						    				<TD width="80%" class="plainlabel" >
						   						<select id="CongTyId"  name="CongTyId" <%=tkktBean.AllowtoChangeCty() ? "" : "disabled = \"disabled\"" %>>
													<option value=""> </option>
												<%
												if(CongTyRs != null)
												{
													try
													{
														while(CongTyRs.next())
														{
															if(tkktBean.getCongTyId().equals(CongTyRs.getString("pk_seq")))
															{%>
														<option value="<%= CongTyRs.getString("pk_seq") %>"   selected="selected"><%= CongTyRs.getString("ten") %></option> 								
															
															<%}else{ %>
														<option value="<%=CongTyRs.getString("pk_seq") %>" ><%= CongTyRs.getString("ten") %></option> 
															<% }
														}
														CongTyRs.close();
													}
													catch(SQLException ex){}
												}
												%>
													</select>
						    					</TD>
										</TR> --%>
								  	
								  		<TR >
											<TD width="20%" class="plainlabel" >Có sử dụng trung tâm chi phí</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String cp= tkktBean.getTaiKhoanCoChiPhi();
										    
												if(cp.equals("1"))
												{
												%>
													<input id="TaiKhoanCoChiPhi" name="TaiKhoanCoChiPhi" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="TaiKhoanCoChiPhi" name="TaiKhoanCoChiPhi" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
								  	
								  		<% if(tk2.equals("1")) { %>
								  		<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Khách hàng</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String kh= tkktBean.getDungchokhachhang();
										    
												if(kh.equals("1"))
												{
												%>
													<input id="dungchokh" name="dungchokh" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchokh" name="dungchokh" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
								  	
								  		<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Nhà cung cấp</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String ncc= tkktBean.getDungchonhacungcap();
										    
												if(ncc.equals("1"))
												{
												%>
													<input id="dungchoncc" name="dungchoncc" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchoncc" name="dungchoncc" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
										
										<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Nhân viên</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String nhanvien= tkktBean.getDungchonhanvien();
										    
												if(nhanvien.equals("1"))
												{
												%>
													<input id="dungchonv" name="dungchonv" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchonv" name="dungchonv" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
						
								  		<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Ngân hàng</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String nh= tkktBean.getDungchonganhang();
										    
												if(nh.equals("1"))
												{
												%>
													<input id="dungchonh" name="dungchonh" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchonh" name="dungchonh" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
									 
								  		<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Sản phẩm</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String kho= tkktBean.getDungchokho();
										    
												if(kho.equals("1"))
												{
												%>
													<input id="dungchokho" name="dungchokho" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchokho" name="dungchokho" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>

								  		<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Tài sản</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String ts = tkktBean.getDungchotaisan();
										    
												if(ts.equals("1"))
												{
												%>
													<input id="dungchots" name="dungchots" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchots" name="dungchots" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
										
										<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Danh mục dự án</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String da = tkktBean.getDungChoDanhMucDuAn();
										    
												if(da.equals("1"))
												{
												%>
													<input id="dungChoDanhMucDuAn" name="dungChoDanhMucDuAn" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungChoDanhMucDuAn" name="dungChoDanhMucDuAn" value="1" type="checkbox" >
												<%}%>	
										    </TD>
										</TR>
										
										<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Đối tượng khác</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String dtk = tkktBean.getDungChoDoiTuongKhac();
										    
												if(dtk.equals("1"))
												{
												%>
													<input id="dungChoDoiTuongKhac" name="dungChoDoiTuongKhac" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungChoDoiTuongKhac" name="dungChoDoiTuongKhac" value="1" type="checkbox" >
												<%}%>	
										    </TD>
										</TR>
										
										<% } %>
									 <TR >
											<TD width="20%" class="plainlabel" >Dùng cho Mã sữa chữa lớn</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String nh= tkktBean.getDungchoMSCL();
										    
												if(nh.equals("1"))
												{
												%>
													<input id="dungchomscl" name="dungchomscl" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchomscl" name="dungchomscl" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>
										
										<TR >
											<TD width="20%" class="plainlabel" >Dùng cho Chi phí trả trước</TD>
										    <TD width="80%" class="plainlabel" >
										    	<%
										    	String nh1= tkktBean.getDungchoCPTT();
										    
												if(nh1.equals("1"))
												{
												%>
													<input id="dungchocptt" name="dungchocptt" value="1" type="checkbox" checked="checked"  >
												<%
												}
												else
												{%>
													<input id="dungchocptt" name="dungchocptt" value="1" type="checkbox"  >
												<%}%>	
										    </TD>
										</TR>		
					    				<TR>
						  					<TD width="15%" class="plainlabel" >Hoạt động<label>
											<%  if (tkktBean.getTrangThai().equals("1")){%>
							  					<input name="TrangThai" type="checkbox" value ="1" checked>
											<%	} else {%>
												<input name="TrangThai" type="checkbox" value ="0">
											<%	} %>
						 					</label>
						 					</TD>
										<TD  class="plainlabel" >&nbsp;</TD>
					  					</TR>
									</TABLE>
								</FIELDSET>
							</TD>	
						</TR>
					</TABLE>
			</TD>
	</TR>
 
</TABLE>
<%  
 	if(CongTyRs != null ) CongTyRs.close(); 
	if(LoaiTaiKhoanRs != null) LoaiTaiKhoanRs.close() ; 
	tkktBean.closeDB();

}%>
</form>
</body>
</html>