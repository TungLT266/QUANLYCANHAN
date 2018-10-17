<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.kehoachnhanvien.*" %>
<%@ page  import = "geso.traphaco.center.beans.kehoachnhanvien.imp.*" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Date" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 
IKeHoachNhanVien khnvBean = (IKeHoachNhanVien)session.getAttribute("khnvBean"); 
ResultSet nppRs = khnvBean.getNppRs();
ResultSet tinhRs = khnvBean.getTinhRs();
ResultSet quanRs = khnvBean.getQuanRs();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>

<SCRIPT type="text/javascript" src="../scripts/kehoachnhanvien.js"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript">
	//Chứa danh sách chi tiết lấy từ database để vẽ lên view
	var nppCts = []; 
	var ttCts = [];

	//Thêm danh sách Chi nhánh / Đối tác
	var nppArr = [];
	<%	if(nppRs != null) {
			try {
				while(nppRs.next()) { %>
					nppArr.push(new TinhThanh('<%= nppRs.getString("PK_SEQ") %>', '<%= nppRs.getString("TEN") %>'));
			<%  }
				nppRs.close();
			} catch(Exception e) { }
		}
	%>
	
	//Thêm danh sách tỉnh thành
	var tinhArr = [];
	<%	if(tinhRs != null) {
			try {
				while(tinhRs.next()) { %>
					tinhArr.push(new TinhThanh('<%= tinhRs.getString("PK_SEQ") %>', '<%= tinhRs.getString("TEN") %>'));
			<%  }
				tinhRs.close();
			} catch(Exception e) { }
		}
	%>
	
	//Thêm danh sách quận huyện
	var quanArr = [];
	<% 
		if(quanRs != null) {
			try {
				while(quanRs.next()) { %>
					quanArr.push(new QuanHuyen('<%= quanRs.getString("TINHTHANH_FK") %>', '<%= quanRs.getString("PK_SEQ") %>', '<%= quanRs.getString("TEN") %>'));
			<%	}
				quanRs.close();
			} catch(Exception e) { }
		}
	%>
	
	//Đưa các quận huyện vào tỉnh thành
	var _tinh, _quan;
	for(var i = 0; i < tinhArr.length; i++) {
		_tinh = tinhArr[i];
		for(var j = 0; j < quanArr.length; j++) {
			_quan = quanArr[j];
			if(_quan.tinhId == _tinh.id) {
				_tinh.quans.push(_quan);
			}
		}
	}
	
	function getNppSelect(selectedId, ngay) {		
		var html = '<option value=""></option>', qh;
		for(var i = 0; i < nppArr.length; i++) {
			npp = nppArr[i];
			if(npp.id == selectedId) {
				html+= '<option value="'+npp.id+'" selected>'+npp.ten+'</option>';
			} else {
				html+= '<option value="'+npp.id+'">'+npp.ten+'</option>';
			}
		}
		return "<select style='width: 300px;' ngay='"+ngay+"' loai='npp' name='ngay"+ngay+"_npp'>" + html + "</select>";
	}
	
	function getTinhThanhOptions(selectedId, ngay) {
		var html = '<option value=""></option>', qh;
		for(var i = 0; i < tinhArr.length; i++) {
			tinhthanh = tinhArr[i];
			if(tinhthanh.id == selectedId) {
				html+= '<option value="'+tinhthanh.id+'" selected>'+tinhthanh.ten+'</option>';
			} else {
				html+= '<option value="'+tinhthanh.id+'">'+tinhthanh.ten+'</option>';
			}
		}
		return html;
	}
	
	function getTinhThanhSelect(selectedId, ngay) {		
		return "<select style='width: 300px;' ngay='"+ngay+"' loai='tinhthanh' name='ngay"+ngay+"_tinhthanh' onchange='loadQuanHuyen(this, \"\")'>" + getTinhThanhOptions(selectedId, ngay) + "</select>";
	}
	
	function getQuanHuyenOptions(tinhthanhId, selectedId, ngay) {
		//Tạo danh sách quận huyện theo tỉnh thành
		var html = '<option value=""></option>', qh, tinh;
		for(var i = 0; i < tinhArr.length; i++) {
			tinh = tinhArr[i];
			if(tinh.id == tinhthanhId) {
				for(var j = 0; j < tinh.quans.length; j++) {
					qh = tinh.quans[j];
					if(qh.id == selectedId) {
						html += '<option value="'+qh.id+'" selected>'+qh.ten+'</option>';
					} else {
						html += '<option value="'+qh.id+'">'+qh.ten+'</option>';
					}
				}
				break;
			}
		}
		return html;
	}
	 
	function getQuanHuyenSelect(tinhthanhId, selectedId, ngay) {
		return "<select style='width: 300px' ngay='"+ngay+"' loai='quanhuyen' name='ngay"+ngay+"_quanhuyen'>" + getQuanHuyenOptions(tinhthanhId, selectedId, ngay) + "</select>";
	}
	
	function loadQuanHuyen(tinhElement, selectedId) {
		$("select").select2("destroy");
		var $tinh = $(tinhElement), tinhId = $tinh.val(), ngay = $tinh.attr("ngay");
		var $p = $tinh.parent();
		var $quan = $p.find("select[loai=quanhuyen]");
		var html = getQuanHuyenOptions(tinhId, selectedId, ngay);
		$quan.html(html);
		$("select").select2();
	}
	
	function xoaChiTiet(e) {
		e.parentNode.parentNode.removeChild(e.parentNode);
	}
	
	function addChiTiet(loai, ngay) {
		if(loai === "npp") {
			addNpp(ngay, "", "");
		} else if(loai === "thitruong") {
			addThiTruong(ngay, "", "", "");
		}
		$("select").select2("destroy");
		$("select").select2();
	}
	
	function addNpp(ngay, selectedId, ghichu, refresh) {
		$("#chitiet"+ngay).append("<p style='line-height:30px;'><span>NPP</span> " + getNppSelect(selectedId, ngay) + " <input type='text' name='ngay"+ngay+"_nppghichu' style='width: 30%; height: 29px; padding-left: 5px;' value='"+ghichu+"' placeholder='Nhập ghi chú...'> <img src='../images/remove.png' title='Xóa' width='30px' height='30px' align='top' onclick='xoaChiTiet(this);'/> </p>");
		if(refresh) {
			$("select").select2("destroy");
			$("select").select2();
		}
	}
	
	function addThiTruong(ngay, tinhSelectedId, quanSelectedId, ghichu, refresh) {
		$("#chitiet"+ngay).append("<p style='line-height:30px;'><span>Tỉnh thành</span> " + getTinhThanhSelect(tinhSelectedId, ngay) + " <input type='text' name='ngay"+ngay+"_ttghichu' style='width: 30%; height: 29px; padding-left: 5px;'  value='"+ghichu+"' placeholder='Nhập ghi chú...'> <img src='../images/remove.png' title='Xóa' width='30px' height='30px' align='top' onclick='xoaChiTiet(this);'/>  <br/>Quận/Huyện " + getQuanHuyenSelect(tinhSelectedId, quanSelectedId, ngay) + " <img src='../images/none.png' width='30px' height='30px' align='top'/> <img src='../images/none.png' width='30%' height='30px' align='top'/> </p>");
		if(refresh) {
			$("select").select2("destroy");
			$("select").select2();
		}
	}
	
	window.onload = function(e) {
		//Add Chi nhánh / Đối tác, thị trường
		var z;
		for(z = 0; z < nppCts.length; z++ ) {
			var o = nppCts[z];
			addNpp(o.ngay, o.nppId, o.ghichu);
		}
		for(z = 0; z < ttCts.length; z++ ) {
			var o = ttCts[z];
			addThiTruong(o.ngay, o.tinhId, o.quanId, o.ghichu);
		}
		$("select").select2("destroy");
		$("select").select2();
	};
	
</SCRIPT>


<SCRIPT language="javascript" type="text/javascript">
	var num = 0;

	function submitform()
	{
	    document.forms['khnvForm'].submit();
	}
	
	function saveform()
	{
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	document.forms['khnvForm'].action.value= 'save';
	    document.forms['khnvForm'].submit();
	}
	
	function thaydoiloai(e) {
		var $e = $(e), loai = $e.val(), $p = $e.parent().parent();
		$p.find("td[loai]").css("display", "none");
		$p.find("td[loai="+loai+"]").css("display", "");
	}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='khnvForm' method="post" action="../../KeHoachNhanVienUpdateSvl">
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="songay" type="hidden" value='<%= khnvBean != null && khnvBean.getNgayList() != null ? khnvBean.getNgayList().length : "" %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   			Dữ liệu nền > Cơ bản > Kế hoạch nhân viên > Tạo Mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
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
							<TD width="30" align="left"><A href="../../KeHoachNhanVienSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    	<A id="btnSave" href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= khnvBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
					<tr>
					   <TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">Kế hoạch nhân viên
							</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
								  <TD width="24%" class="plainlabel" >Nhân viên</TD>
								  <TD width="70%" class="plainlabel" ><INPUT name="tennhanvien"
									type="text" value='<%= khnvBean.getTenNhanVien() %>' size="20" readonly></TD>
								</TR>
								<TR>
								  <TD width="24%" class="plainlabel" >Tháng <FONT class="erroralert">*</FONT></TD>
								  <TD width="70%" class="plainlabel" >
								  	<select name="thang" style="width: 200px;" onchange="submit()">
								  	<%  String[] thang = {"1","2","3","4","5","6","7","8","9","10","11","12"};
								  		for(int i = 0; i < thang.length; i++) {
								  			if (khnvBean.getThang().equals(thang[i])){%>
									  			<option value ="<%=thang[i] %>" selected><%=thang[i] %></option>
									<%} 	else {%>
												<option value ="<%=thang[i] %>"><%=thang[i] %></option>
									<%		} 
										}%>
									</select>
								  </TD>
							  </TR>
								<TR>
								  <TD class="plainlabel" >Năm <FONT class="erroralert">*</FONT></TD>
								  <TD width="70%" class="plainlabel" >
								  	<select name="nam" style="width: 200px;" onchange="submit()">
								  	<%  
									  	DateFormat dateFormat = new SimpleDateFormat("yyyy");
								        Date date = new Date();
								        int year = Integer.parseInt(dateFormat.format(date));
								  		for(int i = year; i < year + 2; i++) {
								  			if (khnvBean.getNam().equals(String.valueOf(i))) { %>
									  			<option value ="<%=i %>" selected><%=i %></option>
									<%} 	else { %>
												<option value ="<%=i %>"><%=i %></option>
									<%		}
										}%>
									</select>
								  </TD>
							  	</TR>
							</TABLE>
							</FIELDSET>
						</TD>
					</TR>
					<TR>
						<TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">Chi tiết
							</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="5%">Ngày </TH>
												<TH width="10%">Tác vụ </TH>
											    <TH width="85%">Nội dung </TH>
											</TR>
								<% 
									if(khnvBean.getNgayList() != null) 
									{
										%>
										<script>num = <%=khnvBean.getNgayList().length %>;</script>
										<%
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										
										for(int m = 0; m < khnvBean.getNgayList().length; m++) {
											IKeHoachNhanVienNgay khNgay = khnvBean.getNgayList()[m];
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> ngay="<%=khNgay.getNgay() %>" >
											<%} else {%>
												<TR class= <%=darkrow%> ngay="<%=khNgay.getNgay() %>" >
											<%}%>
													<TD align="center">
														<input type="hidden" name="ngay" value='<%=khNgay.getNgay() %>'>
														<b><%=khNgay.getNgay() %></b>
													</TD>
													<TD align="left" valign="top" style="line-height: 16px;">
														<A href="javascript: addChiTiet('npp', <%=khNgay.getNgay() %>)"><IMG src="../images/plus32.png" width="16" height="16" title="NPP" alt="NPP"> <b>Đi NPP</b></A><br/> 
														<A href="javascript: addChiTiet('thitruong', <%=khNgay.getNgay() %>)"><IMG src="../images/plus32.png" width="16" height="16" title="Thị Trường" alt="Thị Trường"> <b>Đi thị trường</b></A>
													</TD>
													<TD align="right" id="chitiet<%=khNgay.getNgay() %>">
													<%
													IKeHoachNhanVienChiTiet chitiet;
													List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
														
													for(int j = 0; j < nppList.size(); j++) {
														chitiet = nppList.get(j);
														%><script>nppCts.push({nppId: '<%=chitiet.getNppId()%>', ngay: '<%=khNgay.getNgay() %>', ghichu: '<%=chitiet.getGhiChu() %>'});</script>
													<%
													}
													nppList = khNgay.getThiTruongList();
														
													for(int j = 0; j < nppList.size(); j++) {
														chitiet = nppList.get(j);
														%><script>ttCts.push({tinhId: '<%=chitiet.getTinhId()%>', quanId: '<%=chitiet.getQuanHuyenId()%>', ngay: '<%=khNgay.getNgay() %>', ghichu: '<%=chitiet.getGhiChu() %>'});</script>
													<%
													}
													%>
													</TD>
												</TR>
										<%
											}
										}
									%>
											<TR>
												<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
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
</form>
</BODY>
<script>
	$("select").select2();
</script>
</HTML>

<% khnvBean.closeDB(); %>
<%}%>