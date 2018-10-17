<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.IChotdonhang" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.imp.Chotdonhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = " geso.traphaco.distributor.db.sql.dbutils" %>
<% dbutils db = new dbutils(); %> 
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChotdonhang obj = (IChotdonhang)session.getAttribute("obj"); %>
<% String msg = obj.getMessege(); %>

<% ResultSet nvbh = (ResultSet)obj.getNvbhList(); %>
<% ResultSet nvgn = (ResultSet)obj.getNvgnList(); %>
<% ResultSet dhList = (ResultSet)obj.getDhList(); %>

<% Hashtable<Integer, String> dhIds = (Hashtable<Integer, String>)obj.getDhIds(); %>

<% obj.setNextSplittings(); %>

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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
  	
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
	
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
    <script language="javascript" type="text/javascript">
		function CheckAll()
		{
			var selectAll = document.getElementById("selectAll");
			var chon = document.getElementsByName("dhIds");
			if(selectAll.checked)
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = true;
			}
			else
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = false;
			}
		}
		
		function UnCheckedAll()
		{
			var selectAll = document.getElementById("selectAll");
			selectAll.checked = false;
		}
		function Load()
		{
			var CTTrungBayId = document.getElementsByName("CTTrungBayId");
			if(CTTrungBayId.length<=0  )
				{
				var btnTraTrungBay = document.getElementById("btnTraTrungBay");
				btnTraTrungBay.style.display='none';
				}
		}
		
		function submitform()
		{   
			document.forms['cdhForm'].action.value='submitForm';
		    document.forms['cdhForm'].submit();
		}
		function checkDhIds()
		{
			var dhIds = document.getElementsByName("dhIds");
			for(i = 0; i < dhIds.length; i++)
			{
				if(dhIds.item(i).checked == true)
					return true;
			}
			return false;
		}
		function saveform()
		{	
			/*
			var nvgn = document.getElementById("nvgnList");
			if(nvgn.value == "")
			{
				alert('Ban phai chon nhan vien giao nhan...');
				return;
			}
			*/
			if(checkDhIds() == false)
			{
				alert('Bạn phải chọn đơn hàng...');
				return;
			}
			document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

			document.forms['cdhForm'].action.value='save';
		    document.forms['cdhForm'].submit();
		}
		function TraTrungBay()
		 {
			document.forms['cdhForm'].action.value = "TraTrungBay";
		    document.forms['cdhForm'].submit();
			
		 }
	</script>
    
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="Load()">
<form name="cdhForm" method="post" action="../../ChotdonhangSvl">
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Xử lý đơn hàng> Chốt đơn hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= userTen %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href="javascript:backform()">
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </label>
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Thông báo </legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width:100%" rows="1" readonly="readonly"><%= obj.getMessege() %></textarea>
		         <% obj.setMessege(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Tiêu chí tìm kiếm </legend>        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
            		<tr>
           				<td class="plainlabel" >Nhân viên giao nhận </td> 
                        <td class="plainlabel">
                           <select name="nvgnTen" id="nvgnTen" onChange="submitform()">
                            <option value="">&nbsp;</option>
                            <% if(nvgn != null){
                                  try{ while(nvgn.next()){ 
                                    if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
                                        <option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
                                    <%}else{ %>
                                        <option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
                            <%}} nvgn.close(); }catch(java.sql.SQLException e){} }%>
                        </select>
                        </td>
                    </tr>
                    <tr>
           				<td class="plainlabel" width="15%">Nhân viên bán hàng </td> 
                        <td class="plainlabel">
                            <select name="nvbhTen" id="nvbhTen" onChange="submitform()">
                            <option value="">&nbsp;</option>
                            <% if(nvbh != null){
                                  try{ while(nvbh.next()){ 
                                    if(nvbh.getString("nvbhId").equals(obj.getNvbhId())){ %>
                                        <option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
                                    <%}else{ %>
                                        <option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
                            <%}} nvbh.close(); }catch(java.sql.SQLException e){} }%>
                        </select>
                        </td>                       
                    </tr>
                    <tr>
           				<td class="plainlabel">Ngày giao hàng </td> 
                        <td class="plainlabel">
                        	<input type="text" size="11" class="days" 
                                    id="ngaygiao" name="ngaygiao" value="<%= obj.getNgaygiao() %>" maxlength="10"/>
                        </td>
                    </tr> 
                    <tr style="background-color: #C5E8CD">
                    	<td colspan="2">
	                    <a class="button2" href="javascript:submitform()" >
	        				<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
                    	</td>
                    </tr>                             
                 </table>
                 <hr>
                 <table width="100%" cellpadding="4px" cellspacing="1px">
                
                 	<tr class="tbheader">
                    	<th align="center">Mã đơn hàng</th>
                        <th align="center">Ngày nhập</th>
                        <th align="center">Mã khách hàng</th>
                        <th align="left">Tên khách hàng</th>
                        <th valign="middle" width="10%" align="center">Chọn
                        	<input type="checkbox" name="selectAll" id="selectAll" onChange="CheckAll()"></th>
						<th align="left" width="50px">Tác vụ</th>
                    </tr>
                    <%
                    int count=0;
					if(dhList != null){
					try{ while(dhList.next())
					{ 
					%>
					<TR class= "tbdarkrow" >
						<TD align="center"><%= dhList.getString("dhId") %></TD>
						<TD align="center"><%= dhList.getDate("ngaynhap").toString() %></TD>
						<TD align="center"><%= dhList.getString("smartid") %></TD>	
						<TD align="left"><%= dhList.getString("khTen") %></TD>									
						<% if(dhIds.contains(dhList.getString("dhId"))){ %>
							<TD align="center"><input name="dhIds" type="checkbox" value ="<%= dhList.getString("dhId") + "," + dhList.getString("khId") + "," + dhList.getString("tonggiatri") + "," + dhList.getString("nvgnId") + "," + dhList.getString("pxkId") + "," + dhList.getString("ngaynhap") %>" checked onChange="UnCheckedAll()"></TD>
						<%}else{%>
							<TD align="center"><input name="dhIds" type="checkbox" value ="<%= dhList.getString("dhId") + "," + dhList.getString("khId") + "," + dhList.getString("tonggiatri") + "," + dhList.getString("nvgnId") + "," + dhList.getString("pxkId") + "," + dhList.getString("ngaynhap") %>" onChange="UnCheckedAll()"></TD>
						<%}%> 
						<TD>
						<% if(dhList.getString("DangKyTrungBayId")!=null ){
							count++;
							String query =
							"	SELECT TB.PK_SEQ AS CTTBID,TRA.TRATRUNGBAY_FK AS TRATBID,TRA.TONGLUONG,TRA.SANPHAM_FK AS SANPHAMID,SP.TEN AS TENSP,SP.MA AS MASP,DKTB_DH.DONHANG_FK AS DONHANGID,DKTB_DH.DAT AS SOXUAT,DKTB_DH.DAT* TRA.TONGLUONG AS TONGTRA,DH.NPP_FK,DH.KHACHHANG_FK, TB.SCHEME ,KHO.AVAILABLE AS TONHIENTAI,D.SOLUONG,BG.GIABANLENPP AS GIABAN "+
							"	FROM DKTRUNGBAY_DONHANG DKTB_DH "+
							"		INNER JOIN ( SELECT PK_SEQ,NPP_FK,KHACHHANG_FK,KHO_FK,KBH_FK FROM DONHANG WHERE NPP_FK='"+dhList.getString("NPPID") +"' AND KHACHHANG_FK='"+dhList.getString("KHID") +"' AND PK_SEQ='"+dhList.getString("DHID") +"')DH ON DH.PK_SEQ=DKTB_DH.DONHANG_FK "+
							"		INNER JOIN DANGKYTRUNGBAY DKTB ON DKTB.PK_SEQ=DKTB_DH.DKTRUNGBAY_FK "+
							"		INNER JOIN CTTRUNGBAY TB ON TB.PK_SEQ=DKTB.CTTRUNGBAY_FK "+
							"		INNER JOIN CTTB_TRATB ON CTTB_TRATB.CTTRUNGBAY_FK=TB.PK_SEQ "+
							"		INNER JOIN "+
							"		("+
							"			SELECT ISNULL(TRA.TONGLUONG, "+
							"				( "+
							"					SELECT SUM(SOLUONG) "+
							"					FROM TRATRUNGBAY_SANPHAM "+
							"					WHERE TRATRUNGBAY_FK=TRA.PK_SEQ "+
							"					GROUP BY TRATRUNGBAY_FK "+
							"				)) AS TONGLUONG,TRASP.SANPHAM_FK,TRASP.TRATRUNGBAY_FK,TRASP.DONGIA "+
							"			FROM TRATRUNGBAY TRA INNER JOIN TRATRUNGBAY_SANPHAM TRASP ON TRASP.TRATRUNGBAY_FK=TRA.PK_SEQ "+
							"			WHERE TRA.LOAI=2 "+ 
							"		)TRA ON TRA.TRATRUNGBAY_FK=CTTB_TRATB.TRATRUNGBAY_FK "+
							"		INNER JOIN SANPHAM SP ON SP.PK_SEQ=TRA.SANPHAM_FK "+ 
							"		INNER JOIN BANGGIABANLENPP BGNPP ON BGNPP.NPP_FK=DH.NPP_FK  "+
							"		INNER JOIN BGBANLENPP_SANPHAM BG ON BG.SANPHAM_FK=TRA.SANPHAM_FK AND BG.BGBANLENPP_FK=BGNPP.PK_SEQ	"+
							"		INNER JOIN NHAPP_KHO KHO ON KHO.KHO_FK=DH.KHO_FK AND KHO.NPP_FK=DH.NPP_FK AND KHO.SANPHAM_FK=TRA.SANPHAM_FK "+
							"		LEFT JOIN DONHANG_CTTB_TRATB D ON D.CTTB_FK=TB.PK_SEQ AND D.SANPHAM_FK=TRA.SANPHAM_FK AND D.DONHANG_FK=DH.PK_SEQ "+
							"   ORDER BY KHO.AVAILABLE DESC   ";
						//	System.out.println("Query lay trung bay"+query);
								ResultSet rs=db.get(query);
						%>
						<a href="" id="PlnId<%= count %>" rel="subcontentPlnId<%= count %>">  
						
							<img alt="Nhập số lượng sản phẩm trưng bày"  title="Nhập số lượng sản phẩm trưng bày" src="../images/Promotion.png"></a>
							
							   <DIV id="subcontentPlnId<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
												                             background-color: white; width: 500px; max-height:250px; overflow:auto; padding: 4px; z-index: 100000000; ">
 	                 <a class="button" href="javascript:TraTrungBay()">
				  		<img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật  </a>
												                             
												                             
												                    <table width="90%" align="center" cellpadding="6px" cellspacing="6px">
												                    	<tr class="tbheader">
                    														<th align="center">Sản phẩm</th>
                    														<th align="center">Tồn hiện tại</th>
                    														<th align="center">Số lượng</th>
                    													</tr>
												                        <%if(rs!=null){
												                        	int i=0;
												                        	while(rs.next())
												                        	{ 
												                        		String SoLuong=rs.getString("SoLuong")==null?"":rs.getString("SoLuong");
												                        if(i==0){
												                        %>
												                        <tr style="color:red">
											                            	<td width="300px" style="font-size: 12px"><b><%=rs.getString("Scheme") %></b></td>
											                            	<td width="50px" style="font-size: 12px"><b>Trả tối đa <%=rs.getString("TongTra") %> </b>
											                            	<input type="hidden" value="<%=rs.getString("TongTra") %>" name="TongTra">
											                            	<input type="hidden" value="<%=rs.getString("SoXuat") %>" name="SoXuat">
											                            	<input type="hidden" value="<%=rs.getString("DonHangId") %>" name="DonHangId">
											                            	<input type="hidden" value="<%=rs.getString("CttbId") %>" name="CTTrungBayId">
											                            	<input type="hidden" value="<%=rs.getString("TraTbId") %>" name="TraTrungBayId">
											                            	</td>
											                            	
											                       		 </tr>
											                       		 <%}i++; %>
												                        <tr >
												                            <td width="300px" style="font-size: 10px">
												                            <input type="text" style="width:300px" readonly="readonly" value="<%=rs.getString("TenSp")%>"></td>
												                            <td width="50px" style="font-size: 12px">
												                            <input type="hidden" style="width:50px"  name="SanPham_<%=rs.getString("DonHangId")%>" id="SanPham_<%=rs.getString("DonHangId")%>" value="<%=rs.getString("SanPhamId")%>">
												                           <input type="hidden" value="<%=rs.getString("GiaBan") %>" name="GiaBan_<%=rs.getString("DonHangId")%>"> 
												                            <input type="text" style="width:50px" readonly="readonly" name="TonHienTai_<%=rs.getString("DonHangId")%>" id="SoLuong_<%=rs.getString("DonHangId")%>" value="<%=rs.getString("TonHienTai")%>">
												                            </td>
												                            <td> <input type="text" style="width:50px" name="SoLuong_<%=rs.getString("DonHangId")%>" id="SoLuong_<%=rs.getString("DonHangId")%>" value="<%=SoLuong%>"></td>
												                        </tr>
												                        <%}rs.close(); }%>
												                    </table>
												                     <div align="right">
												                      <a class="button" href="javascript:TraTrungBay()">
				  														<img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật  </a>     
												                     	<a href="javascript:dropdowncontent.hidediv('subcontentPlnId<%= count %>')" > <b>Đóng lại</b> </a>
												                     </div>
													            </DIV> 
							
						<%} %>
						</TD>
                    </TR> 
                    <%}dhList.close(); }catch(java.sql.SQLException e){} }%>
                    
 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('cdhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('cdhForm', eval(cdhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('cdhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('cdhForm', eval(cdhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('cdhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  

                 </table>
            </div>         
    </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript">
 <% for(int i = 0; i <= count; i++) { %>
		dropdowncontent.init('PlnId<%= i %>', "left-top", 300, "click");
 <% } %>
</script>
</BODY>
</html>

<% 	
	
	try{
		if(nvbh != null)
			nvbh.close();
		if(nvgn != null)
			nvgn.close();
		if(dhList != null)
			dhList.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		if(db!=null)
			db.shutDown();
		session.setAttribute("obj",null);
	}
	catch (SQLException e) {e.printStackTrace();}

%>
<%}%>