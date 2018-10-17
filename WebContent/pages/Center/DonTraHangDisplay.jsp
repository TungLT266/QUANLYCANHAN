<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.dontrahang.IDontrahang"%>
<%@ page import="geso.traphaco.center.beans.dontrahang.imp.Dontrahang"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("DuyetdontrahangSvl","");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>

<%
	IDontrahang dthBean = (IDontrahang) session
				.getAttribute("dthBean");
%>
<%
	ResultSet ncc = (ResultSet) dthBean.getNcc();
%>
<%
	ResultSet dvkd = (ResultSet) dthBean.getDvkdIds();
%>
<%
	ResultSet kbh = (ResultSet) dthBean.getKbhIds();
%>
<%
	ResultSet kho = (ResultSet) dthBean.getKhoIds();
%>
<%
	String[] spId = dthBean.getSpId();
%>
<%
	String[] masp = dthBean.getMasp();
%>
<%
	String[] tensp = dthBean.getTensp();
%>
<%
	String[] sl = dthBean.getSl();
%>
<%
	String[] tienbVAT = dthBean.getTienbVAT();
%>
<%
	String[] dg = dthBean.getDongia();
%>
<%
	String[] dv = dthBean.getDonvi();
	String[] dongiathung = dthBean.getDongiathung();
	String[] qc=dthBean.getQuycach();
	String[] solo=dthBean.getSolo();
	String[] soluongthung=dthBean.getSoluongthung();
	String[] tonkho = dthBean.getTonkho();
	String[] tonle = dthBean.getTonle();
	String[] tonthung=dthBean.getTonthung();
	String[] spNGAYHETHAN = dthBean.getSpNgayHetHan();

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/Numberformat.js"></script>

<SCRIPT type="text/javascript">
function duyetform()
{  
	document.getElementById("duyetid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
	document.dthForm.action.value="duyet";
    document.forms["dthForm"].submit();
}

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dthForm" method="post" action="../../DuyetdontrahangSvl">
		<INPUT name="id" type="hidden" value='<%=dthBean.getId()%>'>
		<INPUT name="userId" type="hidden" value='<%=userId%>'> <input
			type="hidden" name="nppId" value='<%=dthBean.getNppId()%>'>
		<input type="hidden" name="action" value='1'>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" bgcolor="#FFFFFF">
			<TR>
				<TD colspan="4" align='left' valign='top'>

					<TABLE width="100%">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation"><%=url %>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%>&nbsp;</TD>

									</tr>
								</table></TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">

										<TD width="30" align="left"><A
											href="../../DuyetdontrahangSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</A>
										</TD>
										<%
											if (dthBean.getTrangthai().equals("1")) {
										%>
										<TD width="2" align="left">&nbsp;</TD>
										<TD width="30" align="left"><A id="duyetid"
											href="javascript: duyetform()"><img
												src="../images/Chot.png" alt="Duyet" title="Duyet"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</A>
										</TD>
										<%
											}
										%>
										<TD width="2" align="left">&nbsp;</TD>
										<TD width="30" align="left"><A
											href="javascript: printform()"><img
												src="../images/Printer30.png" alt="In" title="In chung tu"
												width="30" height="30" longdesc="In chung tu" border=1
												style="border-style: outset">
										</A>
										</TD>
										<TD align="left">&nbsp;</TD>
									</TR>
								</TABLE></TD>
						</TR>

					</TABLE>

					<TABLE width=100% cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>

									<textarea name="err" style="width: 100%" rows="1"><%=dthBean.getMessage()%></textarea>
									<%
										dthBean.setMessage("");
									%>
								</FIELDSET></TD>
						</tr>

						<TR>
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD align="left">
											<FIELDSET>

												<LEGEND class="legendtitle">&nbsp;Đơn trả hàng
													&nbsp;</LEGEND>
												<TABLE cellpadding=4 cellspacing=0 width="100%" border=0>
													<TR>
														<TD class="plainlabel">Chi nhánh / Đối tác</TD>
														<TD class="plainlabel" colspan="3"><%=dthBean.getNppTen()%>
													</TR>


													<TR>
														<TD class="plainlabel">Ngày trả hàng</TD>
														<TD class="plainlabel">

															<table border=0 cellpadding=0 cellspacing=0>
																<tr>
																	<td class="plainlabel"><input type="text"
																		name="ngayth" size="11"
																		value="<%=dthBean.getNgayth()%>" readonly="readonly">
																	</td>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
															</table></TD>
														<TD class="plainlabel">Kênh bán hàng</TD>
														<TD class="plainlabel"><SELECT name="kbhId"
															readonly="readonly">
																<option value="">&nbsp;</option>
																<%
																	try {
																			while (kbh.next()) {
																				if (kbh.getString("kbhId").equals(dthBean.getKbhId())) {
																%>
																<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbh")%></option>
																<%
																	} else {
																%>
																<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbh")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</select></TD>
													</TR>
													<TR class="tblightrow">
														<TD class="plainlabel">Nhà cung cấp</TD>
														<TD class="plainlabel"><select name="nccId"
															readonly="readonly">
																<option value="">&nbsp;</option>
																<%
																	try {
																%>

																<%
																	if (ncc != null) {
																				while (ncc.next()) {
																					if (ncc.getString("nccId").equals(
																							dthBean.getNccId())) {
																%>
																<option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>

																<%
																	} else {
																%>
																<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>
																<%
																	}
																				}
																			}
																%>
																<%
																	} catch (java.sql.SQLException e) {
																%>
																<%
																	}
																%>
														</select></TD>
														<TD class="plainlabel">Kho</TD>
														<TD class="plainlabel"><SELECT name="khoId"
															readonly="readonly">
																<option value="">&nbsp;</option>
																<%
																	try {
																			while (kho.next()) {
																				if (kho.getString("khoId").equals(dthBean.getKhoId())) {
																%>
																<option value='<%=kho.getString("khoId")%>' selected><%=kho.getString("kho")%></option>
																<%
																	} else {
																%>
																<option value='<%=kho.getString("khoId")%>'><%=kho.getString("kho")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Đơn vị kinh doanh</TD>
														<TD class="plainlabel"><SELECT name="dvkdId"
															readonly="readonly">
																<option value="">&nbsp;</option>
																<%
																	try {
																			while (dvkd.next()) {
																				if (dvkd.getString("dvkdId")
																						.equals(dthBean.getDvkdId())) {
																%>
																<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd")%></option>
																<%
																	} else {
																%>
																<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkd")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</select></TD>
														<TD class="plainlabel">Tổng số tiền (chưa VAT)</TD>
														
														<%
														NumberFormat formatter = new DecimalFormat("#,###,###");
														%>
														<TD class="plainlabel"><input type="text"
															name="tongtienbvat" id="tongtienbvat" readonly="readonly"
															value="<%=formatter.format(Double.parseDouble(dthBean.getTongtienbVAT()))%>"
															style="text-align: right"> VND</TD>
													
													</TR>
												

													


													

													<TR class="tblightrow">
														<TD class="plainlabel">Bằng chữ</TD>
														<TD class="plainlabel" colspan="3"><span id="lblDocSo"
															style="font-weight: bold; font-style: italic;"></span></TD>

													</TR>

												</TABLE>

											 <TABLE  width = 100% cellpadding="1" border="1" style="border-color: #80CB9B" cellspacing="0" >																										
													<TR class="tbheader">
														<TH rowspan="2" width="10%">Mã sản phẩm</TH>
														<TH rowspan="2" width="20%">Tên sản phẩm</TH>
														<TH width="10%" colspan="2">Tồn kho </TH>														
														<TH width="10%" colspan="2">Số lượng trả</TH>
														<TH width="15%" rowspan="2" >Số Lô</TH>
														<th align="left" width="10%" rowspan="2" > Ngày hết hạn</th>
														<TH width="15%" rowspan="2">Đơn giá</TH>
														<TH width="20%" rowspan="2">T. tiền (w/o VAT)</TH>														
													</TR>
													<TR class="tbheader">
														<TH width="5%">Thùng</TH>
														<TH width="5%">Lẻ</TH>													
														<TH width="5%">Thùng</TH>
														<TH width="5%">Lẻ</TH>																											
													</TR>
													  <% 
			               								String lightrow = "tblightrow";
			                                            String darkrow = "tbdarkrow";
			                                            int m= 0;
			                                        	int size = new Integer(dthBean.getSize()).intValue();
														for (int i = 0; i < size; i++)
														{
															if(m%2 == 0){					%>
															<TR class= <%=lightrow%> >
													<% 	}else{%>	         
															<TR class= <%=darkrow%> >
													<%	} %>
														<TD>
															<input type="text" name="masp" value='<%=masp[i]%>' readonly="readonly"style="text-align: left; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="tensp"value='<%=tensp[i]%>' readonly="readonly"style="text-align: left; width: 100%;border:0">
														</TD>																											
														<TD>
															<input type="text" name="tonthung" value='<%=tonthung[i]%>' readonly="readonly" style="text-align: right; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="tonle" value='<%=tonle[i]%>' readonly="readonly" style="text-align: right; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="soluongthung"  id="soluongthung_<%=i %>"  value='<%=soluongthung[i]%>'    onChange=" QuyRaLe(<%=i %>);setTTienbVAT();" onKeyPress="return submitenter(this,event);"   style="text-align: right; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="soluongle"   id="soluongle_<%=i %>"   value='<%=sl[i]%>' onChange="QuyRaThung(<%=i %>);setTTienbVAT();" onKeyPress="return submitenter(this,event);"   style="text-align: right; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="solo" value='<%=solo[i]%>'  readonly="readonly" style="text-align: left; width: 100%;border:0">
														</TD>
														
															<td>
																<input type="text" name="spNgayHetHan" value="<%= spNGAYHETHAN[i] %>"  style="text-align: left; width: 100%;border:0"  >
															</td>
														
														<TD>
															<input type="text" name="dongiathung" value='<%=dongiathung[i]%>'  readonly="readonly" style="text-align: right; width: 100%;border:0">
														</TD>
														<TD>
															<input type="text" name="tien" value='<%=tienbVAT[i]%>'  readonly="readonly"style="text-align: right; width: 100%;border:0"> 
															<input type="hidden" name="spId" value='<%=spId[i]%>'  readonly="readonly" style="text-align: right; width: 100%;border:0"> 
															<input type="hidden" name="quycach" id="quycach_<%=i %>" value='<%=qc[i]%>'  style="text-align: right; width: 100%;border:0">
															<input type="hidden" name="tonkho"  value='<%=tonkho[i]%>'  style="text-align: right; width: 100%;border:0">
															<input type="hidden" name="dongia"  value='<%=dg[i]%>'  style="text-align: right; width: 100%;border:0">
														</TD>
													</TR>
													<%
														m++;
                                            }
													%>
												</TABLE>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</td>

			</tr>
		</table>

	</form>
</body>
</html>

<%
	if (!(ncc == null))
			ncc.close();
%>
<%
	if (!(dvkd == null))
			dvkd.close();
%>
<%
	if (!(kbh == null))
			kbh.close();
%>
<%
	dthBean.DBclose();
%>
<%
	}
%>