<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.dangkytrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>

<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IDangkytrungbay dktbBean = (IDangkytrungbay)session.getAttribute("dktbBean"); %>
<% ResultSet nvbh = (ResultSet)dktbBean.getNvBanhang(); %>
<% ResultSet cttrungbay = (ResultSet)dktbBean.getCttrungbay(); %>
<% ResultSet khList = (ResultSet)dktbBean.getKhList(); %>

<% Hashtable<Integer, String> nvbhIds = (Hashtable<Integer, String>)dktbBean.getNvbhIds(); %>
<% Hashtable<String, Integer> kh_soxuat = dktbBean.getKh_Soxuat(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<style type="text/css">

	#tabledetail tbody tr input:HOVER{
		background: #C5E8CD;
	}
	#tabledetail tbody tr:hover{
		background: #C5E8CD;
	}
	</style>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <script type="text/javascript">
	    function saveform()
		{  
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('Vui lòng chọn chương trình trưng bày...');
				return;
			}
			
			if(checkDangky() == false)
			{
				alert('Không có khách hàng nào được chọn để đăng ký tham gia chương trình...');
				return;
			}
			
			var sosuatphanbo = document.getElementById("sosuatphanbo").value;
			var sosuatdaphanbo = document.getElementById("sosuatdaphanbo").value;
			
			//alert('Ngan sach: ' + sosuatphanbo + ' -- Dang ky: ' + sosuatdaphanbo);
	    	if(parseInt(sosuatdaphanbo) > parseInt(sosuatphanbo))
	    	{
		    	alert('Bạn đã vượt ngân sách được phân bổ, vui lòng giảm số xuất đăng ký');
		    	return;
	    	}
			
			document.getElementById("saveid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
			document.forms['dktbForm'].action.value='save';
			document.forms['dktbForm'].submit();
		}
	    
	    function checkDangky()
	    {
	    	
	    	var dangky = document.getElementsByName("dangky");
	    	for(i = 0; i < dangky.length; i++)
			{
				if(dangky.item(i).value != null)
					return true;
			}
	    	return false;
	    }
	    
	    function Check(str)
		{
	    	kiemtrahetphanbo();
	    	var dangkythem = document.forms['dktbForm'].dangkythem.value;
	    	
			var id = document.getElementById(str);
			var hidden = document.getElementById(str + '.hidden').value;
			
			<% if(dktbBean.getIstinhds().equals("1")){ %>
				if( parseInt(id.value) > parseInt(hidden))
				{
					alert('Số xuất đăng ký phải nhỏ hơn số xuất bán đã mua..');
					id.value = "";
					return;
				}
			<% } %>
			
			congdon();
		}
	    
		function submitform()
		{
			document.forms['dktbForm'].action.value = 'submit';
		    document.forms['dktbForm'].submit();
		}
		
		function submitform2()
		{
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('vui lòng chọn chương trình trưng bày...');
				return;
			}
			document.forms['dktbForm'].action.value='submit';
		    document.forms['dktbForm'].submit();
		}
			
		function kiemtrahetphanbo()
		{
			var sosuatphanbo = document.getElementById("sosuatphanbo").value;
			var sosuatdaphanbo = document.getElementById("sosuatdaphanbo").value;
			var tong =0;
			var conlai = sosuatphanbo;// - sosuatdaphanbo;
	    	var dangky = document.getElementsByName("dangky");
	    	for(i = 0; i < dangky.length; i++)
			{
			  	tong = tong +   parseInt(dangky.item(i).value) ;
			    if(tong > conlai)
		    	{
			    	alert('Bạn chỉ còn ' + conlai + ' xuất phân bổ');
			    	dangky.item(i).value = 0;
			    	return;
		    	}
			}
			
		}
		
		function congdon()
	    {  	
			var sosuatphanbo = document.getElementById("sosuatdaphanbo");
	    	
			var hidden = document.getElementsByName("dangky");
			var tong = 0;
			
			for(i = 0; i< hidden.length; i++)
			{   
				if(hidden.item(i).value.length > 0)
				  tong = tong + parseInt(hidden.item(i).value);
			}
			
			sosuatphanbo.value = tong;
	    }
		
		function check_all(value)
		{
			var checkone=document.getElementsByName("nvbhIds");
			for(var i=0; i<checkone.length; i++ )
			{
				checkone.item(i).checked=value;
			}
		}
		
		function sellectAll()
		 {
			 var chkAll = document.getElementById("chkAll");
			 var nppIds = document.getElementsByName("nvbhIds");
			 
			 if(chkAll.checked)
			 {
				 for(i = 0; i < nppIds.length; i++)
				 {
					 nppIds.item(i).checked = true;
				 }
			 }
			 else
			 {
				 for(i = 0; i < nppIds.length; i++)
				 {
					 nppIds.item(i).checked = false;
				 }
			 }
		 }
		
		function sellectAll(cbId1,cbId2 )
		{
			 var chkAll_Lct = document.getElementById(cbId1);
			 var loaiCtIds = document.getElementsByName(cbId2);
			 

			 
			 if(chkAll_Lct.checked)
			 {
				 for(var i = 0; i < loaiCtIds.length; i++)
				 {
					 loaiCtIds.item(i).checked = true;
				 }
			 }
			 else
			 {
				 for(var i = 0; i < loaiCtIds.length; i++)
				 {
					 loaiCtIds.item(i).checked = false;
				 }
			 }
		}

		
		
	</script>
	
	
	
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dktbForm" method="post"
		action="../../DangkytrungbayUpdateSvl">
		<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
		<input type="hidden" name="nppId" value='<%= dktbBean.getNppId() %>'>
		<input type="hidden" name="dangkythem"
			value='<%= dktbBean.getdangkythem() %>'> <input type="hidden"
			name="action" value='1'> <input type="hidden" name="istinhds"
			id="istinhds" value='<%= dktbBean.getIstinhds()  %>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 50%; padding: 5px; float: left"
					class="tbnavigation">Quản lý KM, trưng bày > Trưng bày > Đăng ký trưng bày > Tạo mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng 
					<%= dktbBean.getNppTen() %>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="javascript:history.back()"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A id="saveid" href="javascript:saveform()"> <IMG
					src="../images/Save30.png" title="Luu lai" alt="Luu lai"
					border="1px" style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none">
				<fieldset>
					<legend class="legendtitle">Báo lỗi nhập liệu </legend>
					<textarea name="dataerror"
						style="width: 100%; color: #F00; font-weight: bold" cols="71"
						rows="1" style="width: 100% " readonly="readonly"><%= dktbBean.getMessage() %></textarea>
					<% dktbBean.setMessage(""); %>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none">
				<fieldset>
					<legend class="legendtitle"> Đăng ký trưng bày </legend>
					<div style="width: 100%; float: non; clear: left; font-size: 0.7em">
						<TABLE width="100%" cellpadding="5px" cellspacing="0px">
							<TR>
								<TD class="plainlabel" width="150px">Chương trình</TD>
								<TD class="plainlabel" width="250px" >
								 	<select name="cttbTen" id="cttbTen" onchange="submitform()">
										<option value=""></option>
										<% if(cttrungbay != null){
										  try
										  { 								  
											  while(cttrungbay.next())
											  { 
											 if(cttrungbay.getString("cttbId").equals(dktbBean.getCttbId()  )) {%>
												<option value="<%= cttrungbay.getString("cttbId") %>" selected="selected"><%= cttrungbay.getString("scheme") %></option>
											<%} else { %>
												<option value="<%= cttrungbay.getString("cttbId") %>"><%= cttrungbay.getString("scheme") %></option>
											<%}  
								     	 } cttrungbay.close(); } catch(Exception e){e.printStackTrace();} }%>
									</SELECT>
								</TD>
								<TD class="plainlabel" width="120px">Mức đăng ký</TD>
								<TD class="plainlabel">
									<select name="mucdangky" onchange="submitform()" >
										<% if(dktbBean.getMucdangky().equals("0")) { %>
											<option value="0" selected="selected" >Mức 1</option>
										<% } else { %>
											<option value="0" >Mức 1</option>
										<% } %>
										<% if(dktbBean.getMucdangky().equals("1")) { %>
											<option value="1" selected="selected" >Mức 2</option>
										<% } else { %>
											<option value="1" >Mức 2</option>
										<% } %>
										<% if(dktbBean.getMucdangky().equals("2")) { %>
											<option value="2" selected="selected" >Mức 3</option>
										<% } else { %>
											<option value="2" >Mức 3</option>
										<% } %>
										<% if(dktbBean.getMucdangky().equals("3")) { %>
											<option value="3" selected="selected" >Mức 4</option>
										<% } else { %>
											<option value="3" >Mức 4</option>
										<% } %>
										<% if(dktbBean.getMucdangky().equals("4")) { %>
											<option value="4" selected="selected" >Mức 5</option>
										<% } else { %>
											<option value="4" >Mức 5</option>
										<% } %>
									</select>
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Mã Chương trình</TD>
								<TD class="plainlabel" colspan="3"><%=dktbBean.getscheme() %>
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Thời gian tính doanh số : Từ ngày</TD>
								<TD class="plainlabel" colspan="3"><input type="text"
									size="15" name="batdauds"
									value="<%=dktbBean.getTgbdTinhds() %>" readonly="readonly"
									style="border-color: gray; color: gray;" />
									&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="text" size="15" name="ketthucds"
									value="<%=dktbBean.getTgktTinhds() %>" readonly="readonly"
									style="border-color: gray; color: gray;" /></TD>
							</TR>
							<TR>

							</TR>
							<TR>
								<TD class="plainlabel">Thời gian trưng bày
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày</TD>
								<TD class="plainlabel" colspan="3"><input type="text"
									size="15" name="batdau" value="<%= dktbBean.getThoigianbd() %>"
									readonly="readonly" style="border-color: gray; color: gray;" />
									&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="text" size="15" name="ketthuc"
									value="<%= dktbBean.getThoigiankt() %>" readonly="readonly"
									style="border-color: gray; color: gray;" /></TD>
							</TR>
							<%-- <% if(dktbBean.getType()==1){%>
            	
				            <TR>
				                <TD class="plainlabel">Thời gian đăng ký &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
				                <TD class="plainlabel" colspan="3">
				                    <input type="text" size="15" name="batdau" value="<%=dktbBean.getTgktTinhds() %>" readonly="readonly" style="border-color: gray;color: gray;"/>
				                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				                    <input type="text" size="15" name="ketthuc" value="<%= dktbBean.getThoigianbd() %>" readonly="readonly"  style="border-color: gray;color: gray;"s/>
				                </TD>
				            </TR> 
				           <% } else {%> --%>
				            <TR>
				                <TD class="plainlabel">Thời gian đăng ký &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
				                <TD class="plainlabel" colspan="3">
				                    <input type="text" size="15" name="batdau" value="<%=dktbBean.getThoigianDk() %>" readonly="readonly"  style="border-color: gray;color: gray;"/>
				                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				                    <input type="text" size="15" name="ketthuc" value="<%=dktbBean.getThoigianKtDk()  %>" readonly="readonly" style="border-color: gray;color: gray;" />
				                </TD>
				            </TR> 
				           <%-- <%} %> --%>
							<TR>

							</TR>

							<TR style="display: none;" >
								<TD class="plainlabel">Số lần thanh toán</TD>
								<TD class="plainlabel" colspan="3"><input type="text"
									size="15" name="solan" value="<%= dktbBean.getSolantt() %>"
									readonly="readonly" style="border-color: gray; color: gray;" />
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Số suất phân bổ</TD>
								<TD class="plainlabel" colspan="3"><input type="text"
									size="15" name="sosuatphanbo" id="sosuatphanbo"
									value="<%= dktbBean.getSosuatphanbo() %>" readonly="readonly"
									style="border-color: gray; color: gray;" /></TD>
							</TR>
							<TR>
								<TD class="plainlabel">Số suất đã đăng ký</TD>
								<TD class="plainlabel" colspan="3"><input type="text"
									size="15" name="sosuatdaphanbo" id="sosuatdaphanbo"
									value="<%= dktbBean.getSosuatdaphanbo() %>" readonly="readonly"
									style="border-color: gray; color: gray;" /></TD>
							</TR>

							<TR>
								<TD class="plainlabel">Nhân viên bán hàng</TD>
								<TD class="plainlabel" colspan="3"><a href="" id="nvbh"
									rel="subcontentNvbh"> &nbsp; <img
										alt="Chọn nhân viên bán hàng" src="../images/vitriluu.png"></a>

									<DIV id="subcontentNvbh"
										style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 560px; max-height: 300px; overflow: auto; padding: 4px;">
										<table width="90%" align="center">
											<tr>
												<th width="80px" style="font-size: 12px">Mã NVBH</th>
												<th width="250px" style="font-size: 12px">Tên NVBH</th>
												<th width="80px" style="font-size: 12px">Điện thoại</th>
												<th width="80px" align="center" style="font-size: 12px">Chọn
													hết <input type="checkbox" onchange="sellectAll()"
													id="chkAll">
												</th>
											</tr>
											<%
	                        		if(nvbh != null)
	                        		{
	                        			while(nvbh.next())
	                        			{  %>

											<tr>
												<td><input style="width: 100%"
													value="<%= nvbh.getString("nvbhId") %>"></td>
												<td><input style="width: 100%"
													value="<%= nvbh.getString("nvbhTen") %>"></td>
												<td><input style="width: 100%"
													value="<%= nvbh.getString("dienthoai") %>"></td>
												<td align="center">
													<% if(nvbhIds.contains(nvbh.getString("nvbhId"))){ %> <input
													type="checkbox" name="nvbhIds"
													value="<%= nvbh.getString("nvbhId") %>" checked> <%}else{ %>
													<input type="checkbox" name="nvbhIds"
													value="<%= nvbh.getString("nvbhId") %>"> <%} %>
												</td>
											</tr>

											<% } nvbh.close(); } %>
										</table>
										<div align="right">
											<label style="color: red"></label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
												href="javascript:dropdowncontent.hidediv('subcontentNvbh')"
												onclick="submitform()">Hoàn tất</a>
										</div>
									</DIV></TD>
							</TR>

						</TABLE>
					</div>

					<hr>
					<div style="width: 100%; float: none; clear: left" align="left">
						<table id="tabledetail" style="width: 100%;" cellpadding="1px"
							cellspacing="1px">
							<thead class="tbheader">
							<tr>
								<th width="10%" align="center">Mã KH</th>
								<th width="25%" align="left">Họ tên</th>
								<th width="30%" align="left">Địa chỉ</th>
								<th width="15%" align="center">Điện thoại</th>
								<th width="10%" align="center">Số suất mua</th>
								<th width="10%" align="center">Số suất ĐK</th>
							</tr>
							</thead>

							<% 
                if(khList != null)
                {
				  	try
				  	{ 
				  		int m = 0;
				  		while(khList.next())
				  		{ 
				   		%>
							<tr class="tbdarkrow">
								<td align="center"><%= khList.getString("ma") %></td>
								<td align="left"><%= khList.getString("khTen") %></td>
								<td align="left"><%= khList.getString("DiaChi") %></td>
								<td align="center"><%= khList.getString("DienThoai") %></td>
								<td align="center"><input type="text" name="soxuat" value="<%= khList.getInt("SoXuat") %>" style="text-align: right; background-color: transparent; border: none;"size="6" readonly></td>
								<td align="center">
									<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="" style="text-align: right; background-color: transparent; border: none;"size="6"onkeyup="Check('<%= "dangky" + Integer.toString(m) %>')"AUTOCOMPLETE="off"> 
									<input type="hidden" id="<%= "dangky" + Integer.toString(m) + ".hidden" %>" value="<%= khList.getString("SoXuat") %>">
									<input type="hidden" name="khIds" value="<%= khList.getString("khachhang_fk") %>">
								</td>
							</tr>
							<% m++;	
	     			   } khList.close(); }catch(Exception e){ System.out.println("Loi trang JSP:  " + e.getMessage() ); } }%>
							<tr class="tbfooter">
								<td colspan="6">&nbsp;</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
	<script type="text/javascript">
	dropdowncontent.init('nvbh', "right-top", 300, "click");
</script>
</BODY>
</html>
<% 	
	try{
		if(nvbh!=null)
		nvbh.close();
		if(cttrungbay!=null)
		cttrungbay.close();
		if(khList!=null)
		khList.close();
		if(dktbBean!=null){
		dktbBean.DBclose();
		dktbBean = null;
		}
		if(nvbhIds!=null){
			nvbhIds.clear();
		}
		if(kh_soxuat!=null){
			kh_soxuat.clear();
		}
		session.setAttribute("dktbBean",null);
	}
	catch (SQLException e) {}

%>
<%}%>


    