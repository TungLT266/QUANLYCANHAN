<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.*"%>
<%@ page import="geso.traphaco.center.beans.thongtinsanpham.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else
	{
%>
<%
	IErpKiemdinhchatluong_kho obj = (IErpKiemdinhchatluong_kho) session.getAttribute("kdcl");
	ResultSet rsSolo = (ResultSet) obj.getRsSolo();
	ResultSet rsKho = (ResultSet) obj.getRsKho();
	ResultSet rsKhoChoXuLy = (ResultSet) obj.getRsKhoChoXuLy();
	ResultSet rsSanpham = obj.getRsSanPham();
	ResultSet rsLoaiSanpham = obj.getRsLoaiSanPham();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script type="text/javascript" src="../scripts/ajax.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>


<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
	
</script>


<script>
	$(document).ready(function()
	{
		$("#spId").select2();
		$("#loaispId").select2();
	});
	
</script>

<script>

$(function() {
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	
	
	function checkedAll(chk,allquyen) {
		for(i=0; i<chk.length; i++){		 		
		 if(allquyen.checked==true){
				chk[i].checked = true;
			}else{
				chk[i].checked = false;
			}
		 }
	}
	
	function convert(stt)
	{
		var trangthai = document.getElementsByName("trangthai");
		var dat = document.getElementsByName("dat");
		
		if(dat.item(stt).value == '0' )
		{
			trangthai.item(stt).value = 'Đạt';
			dat.item(stt).value = '1';
			document.getElementById("tuychon" + stt).innerHTML = " ";
		}
		else
		{
			trangthai.item(stt).value = 'Không đạt';
			dat.item(stt).value = '0';
			document.getElementById("tuychon" + stt).innerHTML = "<a href='javascript:convert(" + stt + ")' > Đạt </a>";
		}
		
		 
		 
	}
	
	function DatChatLuong()
	{
		var dat = document.getElementsByName("diemdat");
		//check tong dat
		var flag = true;
		for(i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
		
		if(flag == false)
		{
			alert('Có tiêu chí chưa nhập kết quả kiểm tra. Vui lòng kiểm tra lại.');
			return;
		}
		
	  	document.forms["khtt"].action.value = "duyet";
	  	document.forms["khtt"].submit(); 		
	}
	
	function duyetDat()
	{
		if(!confirm('Bạn có muốn duyệt  yêu cầu kiểm định  này?'))
		{
			return false ;
		}
		
	 	document.forms["khtt"].datcl.value = "1"; 
		document.forms["khtt"].action.value = "duyet";
		document.forms["khtt"].submit(); 
	}
	
	function duyetKDat()
	{
		if(!confirm('Bạn có muốn duyệt  yêu cầu kiểm định  này?')) 
		{
			return false ;
		}
		
		document.forms["khtt"].datcl.value="0"; 
		document.forms["khtt"].action.value = "duyet";
		document.forms["khtt"].submit(); 
	}
	
	function submitform()
	{
		document.forms["khtt"].action.value = "reload";
		document.forms["khtt"].submit(); 
	}
	
	function changeYeuCau()
	{
		 document.forms["khtt"].action.value = "change";
		 document.forms["khtt"].submit(); 
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
 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
			{
 
				return;
			}
			return false;}
			
	}	
	function autoCheck()
	{
		TinhSoLuong();
	}
	
	function TinhSoLuong()
	{
		
		var tongsoluongnhap=document.getElementById("tongsoluongnhap");
		
		var soluongchuakiem=document.getElementById("soluongchuakiem");
		
		
		
		var soluongkiemdinh = document.getElementById("soluongkiemdinh");
		if( parseFloat(soluongchuakiem.value) <parseFloat(soluongkiemdinh.value) ){
			soluongkiemdinh.value="0";
			alert("Số lượng còn lại không đủ để kiểm định");
		}
			
		
		var soluongkodat = document.getElementById("soluongkodat"); 
		var soluongmau=document.getElementById("soluongmau"); 
		
		var  soluongdat= document.getElementById("soluongdat");
		
		
		
		if(soluongkodat.value != '')
		{
			soluongdat.value = parseFloat(soluongkiemdinh.value) - parseFloat(soluongkodat.value) - parseFloat(soluongmau.value);
		}
		else
		{
			soluongkodat.value = soluongkiemdinh.value;
		}
		
		if( (parseFloat(soluongkiemdinh.value) - parseFloat(soluongkodat.value) - parseFloat(soluongmau.value) ) <0){
			soluongkodat.value="0";
			soluongmau.value="0";
			alert("Vui lòng kiểm tra lại số lượng không đạt và mẫu,vượt quá số lượng duyệt");
		}
	}
	
	function changeDat(pos)
	{
		var dat = document.getElementsByName("diemdat");
		for(var i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
		
	}
	
	
	function save()
	{
		var dat = document.getElementsByName("diemdat");
		
		//check tong dat
		var flag = true;
		for(i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
		
		if(flag == false)
		{
			alert('Có tiêu chí chưa nhập kết quả kiểm tra. Vui lòng kiểm tra lại.');
			return;
		}
		
	    document.forms["khtt"].action.value = "save";
	    document.forms["khtt"].submit(); 
    }
	
</SCRIPT>

</HEAD>

		
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="autoCheck()" >
	<form name="khtt" method="post" action="../../ErpKiemdinhchatluongUpdate_khoSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="loai" value="2">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Quản lý tồn kho > Kiểm định chất lượng >Hiển thị </TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../ErpKiemdinhchatluong_khoSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left">
										</TD>
										 
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%; color: #F00; font-weight: bold" style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Kiểm định chất lượng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle"  width = "22%" >Số chứng từ</TD>
											<TD class="plainlabel" valign="middle" width = "28%" >
												<input type="text" name="id" value="<%= obj.getId() %>" readonly="readonly">
											</TD>
											<TD class="plainlabel" valign="middle"  width = "22%" >Ngày kiểm định</TD>
											<TD class="plainlabel" valign="middle" width = "28%" >
												<input type="text" class="days" name="ngaykiem" value="<%= obj.getNgayKiem() %>" readonly="readonly">
											</TD>
										</TR>

							<tr>
								<td class="plainlabel" valign="middle">Loại sản phẩm</td>
								<td  align="left" class="plainlabel">
								<select name="loaispId" onchange="submitform()" id="loaispId" style = "width:300;">
										<option value=""></option>
										<%
											if (rsLoaiSanpham != null)
												while (rsLoaiSanpham.next())
												{
													if (rsLoaiSanpham.getString("LOAISPID").equals(obj.getLOAISPID()))
													{
										%>
										<option value="<%=rsLoaiSanpham.getString("LOAISPID")%>"
											selected="selected"><%=rsLoaiSanpham.getString("Ten")%></option>
										<%
											}
													else
													{
										%>
										<option value="<%=rsLoaiSanpham.getString("LOAISPID")%>"><%=rsLoaiSanpham.getString("Ten")%></option>
										<%
											}
												}
										%>
								</select>
								</td>
							
								<td  class="plainlabel" valign="middle">Sản phẩm</td>
								<td  align="left" class="plainlabel">
								<select name="spId" onchange="submitform()" id="spId" style = "width:300;">
										<option value=""></option>
										<%
								if (rsSanpham != null)
									while (rsSanpham.next())
									{
										if (rsSanpham.getString("SPID").equals(obj.getSpId()))
										{
										%>
										<option value="<%=rsSanpham.getString("SPID")%>" selected="selected"><%=rsSanpham.getString("Ten")%></option>
										<%
										}
										else
										{
										%>
										<option value="<%=rsSanpham.getString("SPID")%>"><%=rsSanpham.getString("Ten")%></option>
										<%
										}
									}
										%>
								</select></td>
							</tr>

							<TR>
									<TD class="plainlabel" valign="middle" >Kho kiểm định</TD>
									<TD class="plainlabel" valign="middle" >
										<select name="khoId" id="khoId" onchange="submitform()">
													<option></option>
													<%
														if (rsKho != null)
															{
																while (rsKho.next())
																{
																	if (obj.getKhoId().equals(rsKho.getString("khoId")))
																	{
													%>
													<option selected="selected" value="<%=rsKho.getString("khoId")%>">
														<%= rsKho.getString("ten")%>
													</option>
													<%
														} else	{
													%>
													<option value="<%=rsKho.getString("khoId")%>">
														<%=rsKho.getString("ten")%>
													</option>
													<% } } } %>
											</select> 
												
									</TD>

									<TD class="plainlabel" valign="middle" >Kho chờ xử lý</TD>
									<TD class="plainlabel" valign="middle" >
										<select name="khochoxulyId" id="khoId" onchange="submitform()">
													<option></option>
													<%
														if (rsKhoChoXuLy != null)
															{
																while (rsKhoChoXuLy.next())
																{
																	if (obj.getKhoChoXuLyId().equals(rsKhoChoXuLy.getString("khoId")))
																	{
													%>
													<option selected="selected" value="<%=rsKhoChoXuLy.getString("khoId")%>">
														<%= rsKhoChoXuLy.getString("ten")%>
													</option>
													<%
														} else	{
													%>
													<option value="<%=rsKhoChoXuLy.getString("khoId")%>">
														<%=rsKhoChoXuLy.getString("ten")%>
													</option>
													<% } } } %>
											</select> 
												
									</TD>
							
							</TR>
						<%-- 	<TR>
									<TD class="plainlabel" valign="middle">Số lô</TD>
									<TD class="plainlabel" valign="middle" >
											<select name="solo" id="solo" >
													<option></option>
													<%
														if (rsSolo != null)
															{
																while (rsSolo.next())
																{
																	if (obj.getSolo().equals(rsSolo.getString("solo")))
																	{
													%>
													<option selected="selected" value="<%=rsSolo.getString("solo")%>">
														<%= rsSolo.getString("solo")%>
													</option>
													<%
														} else	{
													%>
													<option value="<%=rsSolo.getString("solo")%>">
														<%=rsSolo.getString("solo")%>
													</option>
													<% } } } %>
											</select> 
									</TD>

									<TD class="plainlabel" valign="middle">Số lượng không đạt  </TD>
									<td class="plainlabel" colspan = 3 >
										<input type="text" name="soluongkodat" id="soluongkodat" style="text-align: right;" onkeypress="return keypress(event)" value="<%= obj.getsoluongkhongDat() %>">
									</TD>																					
							</TR>
							<TR>
											
									<TD class="plainlabel" valign="middle" width="150px">Ghi chú</TD>
									<TD class="plainlabel"  colspan="3">
										<textarea  name="ghichu" id = "ghichu" style="width: 100%; text-align:left; left: 5px"  rows="2" ><%= obj.getGhichu().trim() %></textarea>
									</TD>
											
							</TR> --%>
							
							<tr>
								<td colspan="4">
									
									 <table class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
							                        <tr class="tbheader">
							                        	<%if(obj.getIsKhuvuc().equals("1")) {%>
							                        	<th width="100px">Khu vực</th>
							                        	<%} %>
							                            <th width="100px">Số lô</th>				                            
							                            <th width="100px">Ngày sản xuất</th>
							                            <th width="100px">Ngày hết hạn </th>
							                            <th width="50px">SL tồn</th>
							                            <th width="50px">Số lượng kiểm định </th>
							                        </tr>
							                          <%
							                          List<ISpDetail> spDetailList =obj.getSpDetailList();
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
				                        				<%if(obj.getIsKhuvuc().equals("1")) {%>
				                        				<td>
							                            	<input type="hidden" name="khuidct1"   value="<%= spDetail.getKhuId() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="tenkhuct1"  value="<%= spDetail.getKhu() %>" readonly="readonly" /></td>
							                            <%} %>
							                            <td>
							                            <input type="hidden" size="100px" name="ngaybatdauct1"  value="<%= spDetail.getNgaybatdau() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="soloct1"   value="<%= spDetail.getSolo() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="100px" name="ngaysanxuatct1"  value="<%= spDetail.getNgaysanxuat() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="100px" name="ngayhethanct1"  value="<%= spDetail.getNgayhethan() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="50px"  name="soluongtonct1"  value="<%= spDetail.getSoluongton() %>" readonly="readonly" /></td>
							                             <td>
							                            	<input type="text" size="50px"  name="soluongct1"  value="<%= spDetail.getSoluong() %>"  onKeyPress = "return keypress(event);" /></td>
							                        </tr>
				                        		<%}
				                        	}
				                        	spDetailList.clear();
				                        %>
							                        </table>
									
								</td>
								
							</tr>
									<tr>
											
											<TD class="plainlabel" valign="middle" width="150px">Ghi chú</TD>
											<TD class="plainlabel"  colspan="3">
												<textarea  name="ghichu" style="width: 100%; text-align:left; "  rows="2" ><%=obj.getGhichu()%></textarea>
												
											</TD>
											
										</tr>								
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
<%
	try
		{
		
		if(rsSolo != null) rsSolo.close();
		if(rsKho != null) rsKho.close();
		if(rsKhoChoXuLy != null) rsKhoChoXuLy.close();
		if(rsSanpham != null) rsSanpham.close();
		if(rsLoaiSanpham != null) rsLoaiSanpham.close();
		
		obj.DbClose();
		session.setAttribute("kdcl", null) ;  
		
		obj = null;
	} catch (Exception er)
	{
		er.printStackTrace();
	}
}
%>
