<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.congty.*"%>
<%@page import="geso.traphaco.erp.beans.congty.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpCongTy congty = (IErpCongTy) session.getAttribute("ctBean");
	List<IErpNganHang> nganhang = congty.getNganHangList();
	ResultSet ctyRs = congty.getCtyRs();
	String tkctyId = congty.getTkCtyId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">
	function saveform() {
		var ctMa = document.getElementById('ma');
		var ctTen = document.getElementById('ten');
		var ctTentienganh = document.getElementById('tentienganh');
		var ctDiachi = document.getElementById('diachi');
		var ctDiachitienganh = document.getElementById('diachitienganh');
		var ctMasothue = document.getElementById('masothue');
		var ctNganhnghekinhdoanh = document
				.getElementById('nganhnghekinhdoanh');
		var ctDienthoai = document.getElementById('dienthoai');
		var ctGiamdoc = document.getElementById('giamdoc');
		var ctKetoantruong = document.getElementById('ketoantruong');
		if (ctMa.value == "") {
			alert("Vui lòng nhập vào mã Công ty");
			ctMa.focus();
			return;
		}
		if (ctTen.value == "") {
			alert("Vui lòng nhập vào tên Công ty");
			ctTen.focus();
			return;
		}

		document.forms['congtyForm'].submit();
	}
</SCRIPT>
</head>
<body>
	<form name="congtyForm" method="post" action="../../ErpCongTyUpdateSvl">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Cơ bản > Công ty > Tạo mới</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>
										</TD>
									</tr>
									<tr>
										<TD align="left" height="5" colspan="4" class=""></td>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="../../ErpCongTySvl?userId=1000"><img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A href="javascript: saveform()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset"></A></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width=100% cellpadding="3" cellspacing="0" border="0">
						<TR>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=congty.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</TR>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin công ty </LEGEND>
									<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
										<TR>
											<TD width="15%" class="plainlabel">Mã<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" maxlength="100" size="20" id="ma" name="ma" value="<%=congty.getMA()%>"></TD>
											<TD width="15%" class="plainlabel">Mã số thuế</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="masothue" maxlength="50" name="masothue"
												value="<%=congty.getMASOTHUE()%>"></TD>
											
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Tên<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="ten" name="ten" value="<%=congty.getTEN()%>"></TD>	
											
											<TD width="15%" class="plainlabel">Tên (Tiếng Anh)</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="tentienganh" name="tentienganh" maxlength="250"
												value="<%=congty.getTENTIENGANH()%>"></TD>	
											
										</TR>
											<TR>
											
											<TD width="15%" class="plainlabel">Địa chỉ</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="diachi" name="diachi" maxlength="250"
												value="<%=congty.getDIACHI()%>"></TD>
											<TD width="15%" class="plainlabel">Địa chỉ (Tiếng Anh)</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="diachitienganh" maxlength="50" name="diachitienganh"
												value="<%=congty.getDIACHITIENGANH()%>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Ngành nghề kinh doanh</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="nganhnghekinhdoanh" maxlength="50" name="nganhnghekinhdoanh"
												value="<%=congty.getNGANHNGHEKINHDOANH()%>"></TD>
												
											<TD width="15%" class="plainlabel">Điện thoại</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="dienthoai" maxlength="50" name="dienthoai"
												value="<%=congty.getDIENTHOAI()%>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Fax</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" name="fax" maxlength="50" value="<%=congty.getFAX()%>"></TD>
											
											<TD width="15%" class="plainlabel">Tổng giám đốc</TD>
											<TD class="plainlabel"><input type="text" class="txtsearch" size="20" id="giamdoc" maxlength="50" name="giamdoc"
												value="<%=congty.getGIAMDOC()%>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Số tài khoản</TD>
											<TD width="15%" class="plainlabel"><input type="text" name="sotaikhoan" value="<%=congty.getSoTaiKhoan()%>"></TD>
											
											<TD width="15%" class="plainlabel">Giám đốc tài chính</TD>
											<TD class="plainlabel"><input name="ketoantruong" id="ketoantruong" maxlength="50" type="text" size="20" id="giamdoc" name="giamdoc"
												value="<%=congty.getKETOANTRUONG()%>"></TD>
										</TR>
										
										<TR>
											<TD width="15%" class="plainlabel">Giấy phép kinh doanh</TD>
											<TD width="15%" class="plainlabel"><input type="text" name="giayphepkinhdoanh" value="<%=congty.getGiayPhepKinhDoanh()%>"></TD>
											
											<TD width="15%" class="plainlabel">Vốn điều lệ</TD>
											<TD class="plainlabel"><input name="vondieule" id="vondieule" maxlength="50" type="text" size="20" 
												value="<%=congty.getVonDieuLe()%>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Tại Ngân hàng</TD>
											<TD width="15%" class="plainlabel"><Select name="nganhang">
													<option value=""></option>
													<%
														for (IErpNganHang n : nganhang)
														{

														if (n.getId().equals(congty.getNganHang_FK()))
															{
													%>
													<option value="<%=n.getId()%>" selected="selected"><%=n.getTen()%></option>
													<%
														}
															else
															{
													%>
													<option value="<%=n.getId()%>"><%=n.getTen()%></option>
													<%
														}
														}
													%>
											</Select></TD>
											<TD class="plainlabel"> Thừa hưởng hệ thống tài khoản từ</TD>
											<TD width="15%" class="plainlabel"><Select name="tkctyId">
												<option value=""></option>
											<%
												if(ctyRs != null){
													try{
													
													while(ctyRs.next()){
													if(ctyRs.getString("CTYID").equals(tkctyId)){ %>
														<option value = "<%= ctyRs.getString("CTYID") %>" selected><%= ctyRs.getString("CTY")%></option>														
											<%		}else{ %>
														<option value = "<%= ctyRs.getString("CTYID") %>" ><%= ctyRs.getString("CTY")%></option>			
											<%		}
													}
													}catch(java.sql.SQLException e){}
												}
											
											%>
											</Select></TD>
											
											
											
										</TR>
										
										<TR class="plainlabel" >
											  <TD class="plainlabel">Ngày bắt đầu tài chính </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" 
                                     			   id="ngayxuathd" name="ngaybatdautc" value="<%=congty.getngaybatdautc()%>" maxlength="10"/> 
											  </TD>
											   
											  <TD class="plainlabel">Ngày kết thúc tài chính</TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" 
                                     			   id="ngaycongno" name="ngayketthuctc" value="<%=congty.getngayketthuctc()%>" maxlength="10" /> 
											  </TD>
										</TR>
											
											
										<TR>										
											<TD width="15%" class="plainlabel" colspan = "3">
											
											<label> <%
 											if (congty.getIsTongCty().equals("1"))
 											{
 											%> <input name="isTongCty" type="checkbox"	value="1" checked> <%
 											}
 											else
 											{
 											%> <input name="isTongCty" type="checkbox" value="1"> <%
 											}
 												%> Tổng công ty
											</label>
											
											&nbsp;&nbsp;
											
											<label> <%
 											if (congty.getTRANGTHAI().equals("1"))
 											{
 											%> <input name="trangthai" type="checkbox"	value="1" checked> <%
 											}
 											else
 											{
 											%> <input name="trangthai" type="checkbox" value="1"> <%
 											}
 												%> Hoạt động
											</label>
											
											
											
											</TD>
											<TD class="plainlabel"></TD>
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
</body>
</html>
<%
	if(ctyRs!=null){
		ctyRs.close();
	}
	if(nganhang!=null){
		nganhang.clear();
	}
	session.setAttribute("ctBean",null);
	congty.closeDB();
}%>