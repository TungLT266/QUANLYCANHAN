<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.imp.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>

<% IErpChuyenkhoSX lsxBean = (IErpChuyenkhoSX)session.getAttribute("lsxBean"); %>
<% ResultSet ndxList = lsxBean.getNdxList(); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>

<% List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); %>
<% List<IKhu_Vitri> khuList = lsxBean.getKhuList(); %>
<% List<IKhu_Vitri> vitriList = lsxBean.getVitriList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
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


<script language="javascript" type="text/javascript">

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function replaces()
	{
		var sanpham = document.getElementsByName("masp");
		var soluongchuyen = document.getElementsByName("soluongchuyen");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var solo = document.getElementsByName("solo");
		
		//alert('1.Toi day...');
		for(var i = 0; i < sanpham.length; i++)
		{
			var tongluong = 0;
			if(sanpham.item(i).value != "")
			{
				var id = sanpham.item(i).value + '.' + solo.item(i).value;
				var soluong = document.getElementsByName(id + '.soluong');
				var khuvuc = document.getElementsByName(id  + '.khuvuc');
				var vitri = document.getElementsByName(id + '.vitri');
				
				//alert('2.Toi day...');
				
				for(var j = 0; j < soluong.length; j++)
				{
					if(soluong.item(j).value != "" && khuvuc.item(j).value != "" && vitri.item(j).value != "")
					{
						var slg = soluong.item(j).value.replace(/,/g,"");
						tongluong = parseFloat(tongluong) + parseFloat(slg);
						
						//alert('3.Tong luong la: ' + tongluong);
						
						var slgNhan = soluongchuyen.item(i).value.replace(/,/g,"");
						if(tongluong > parseFloat(slgNhan))
						{
							tongluong = parseFloat(tongluong) - parseFloat(slg);
							soluong.item(j).value = "";
							khuvuc.item(j).value = "";
							vitri.item(j).value = "";
						}
					}
				}
				
				//alert('4.Tong luong la: ' + tongluong);
				soluongnhan.item(i).value = tongluong;
			}
		}
		
		//setTimeout(replaces, 200);
	}
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'xuathang';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function LocKhuVuc(id, value)
		{
			var stt = id.substring(id.indexOf('khuvuc') + 6, id.length)
			var vitriId = id + '.vitricon' + stt;
			
			var vitri = document.getElementById(id + '.vitricon' + stt);

			for(var i = 0; i < vitri.length; i++)
			{
				 var str = vitri.options[i].value;
				 if(str.indexOf(value) == 0)
				 {
					 vitri.selectedIndex = parseInt(i);
					 break;
				 }
			}
		}
		
		function LocViTri(id, value)
		{
			var makhuvuc = value.substring(0, value.indexOf(' - '));
			var khuvucId = id.substring(0, id.indexOf('.vitricon'));

			var khuvuc = document.getElementById(khuvucId);

			for(var i = 0; i < khuvuc.length; i++)
			{
				 var str = khuvuc.options[i].value;
				 if(str.indexOf(makhuvuc) == 0)
				 {
					 khuvuc.selectedIndex = parseInt(i);
					 break;
				 }
			}
		}
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpChuyenkhoSXUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Quản lý tồn kho > Yêu cầu xuất kho > Xuất kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpChuyenkhoSXSvl?userId=<%= userId %>&task=2" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform();" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="120px" class="plainlabel" valign="top">Ngày xuất </TD>
                    <TD colspan="3" class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/></TD>
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Lý do </TD>
                    <TD colspan="3" class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>"/>
                    </TD>
                </TR>
                
                 <TR>
                    <TD class="plainlabel" valign="top">Người nhận</TD>
                    <TD colspan="3" class="plainlabel" valign="top">
                    	<input type="text"  name="nguoinhan" value="<%= lsxBean.getNguoinhan() %>"/>
                    </TD>
                </TR>
                
                
                <TR>
                    <TD class="plainlabel">Nội dung xuất</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="hidden" name="noidungxuat" value="<%= lsxBean.getNdxId() %>" >
                        <select disabled="disabled">
                        	<option value=""> </option>
                        	<%
                        		if(ndxList != null)
                        		{
                        			try
                        			{
                        			while(ndxList.next())
                        			{  
                        			if( ndxList.getString("pk_seq").equals(lsxBean.getNdxId())){ %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" selected="selected" ><%= ndxList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" ><%= ndxList.getString("ten") %></option>
                        		 <% } } ndxList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                
                <% if(lsxBean.getNdxId().trim().length() > 0) { %>
                <TR>
                    <TD class="plainlabel" valign="top">Kho chuyển </TD>
                    <TD width="230px" class="plainlabel" valign="top" <%= lsxBean.getNdxId().equals("100015") ? "" : "colspan='3'" %>  >
                    	<input type="hidden" name="khoxuatId" value="<%= lsxBean.getKhoXuatId() %>" >
                    	<select disabled="disabled" >
                    		<option value=""> </option>
                        	<%
                        		if(khoxuatRs != null)
                        		{
                        			try
                        			{
                        			while(khoxuatRs.next())
                        			{  
                        			if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){ %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected" ><%= khoxuatRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" ><%= khoxuatRs.getString("ten") %></option>
                        		 <% } } khoxuatRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    	
                    </TD>
                    
                    <% if(lsxBean.getNdxId().equals("100015")) { %>
	                    <TD width="120px" class="plainlabel">Trạng thái SP</TD>
	                     <TD class="plainlabel" >
	                     		<input type="hidden" name="trangthaisp" value="<%= lsxBean.getTrangthai() %>" >
	                        	<select disabled="disabled" >
	                    		
		                    		<% if(lsxBean.getTrangthaiSP().equals("1")) { %>
		                    			<option value="1" selected="selected">Đạt chất lượng</option>
		                    		<% } else { %>
		                    			 <option value="1">Đạt chất lượng</option>
		                    		<% } %>
		                    		
		                    		<% if(lsxBean.getTrangthaiSP().equals("-1")) { %>
		                    			<option value="-1" selected="selected">Không đạt chất lượng</option>
		                    		<% } else { %>
		                    			 <option value="-1">Không đạt chất lượng</option>
		                    		<% } %>
		                    		
		                    	</select>
	                      </TD> 
                      <% } %>
                    
                </TR>
                
                <% if( lsxBean.getNdxId().equals("100006") || lsxBean.getNdxId().equals("100009") || lsxBean.getNdxId().equals("100010") || lsxBean.getNdxId().equals("100011") || lsxBean.getNdxId().equals("100026")) { %>
	                <TR>
	                	
	                	<TD class="plainlabel" valign="top">Kho nhận </TD>
	                    <TD colspan="3" class="plainlabel" valign="top">
	                    	<input type="hidden" name="khonhapId" value="<%= lsxBean.getKhoNhapId() %>" >
	                    	<select disabled="disabled"  >
	                    		<option value=""> </option>
	                        	<%
	                        		if(khonhapRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(khonhapRs.next())
	                        			{  
	                        			if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
	                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
	                        		 <% } } khonhapRs.close();} catch(SQLException ex){}
	                        		}
	                        	%>
	                    	</select>
	                    </TD>
	                	
	                </TR>
                <% } %>
                
                <% } %>
               
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="15%" >Mã sản phẩm</th>
				<th align="center" width="35%">Tên sản phẩm</th>
			 
				<th align="center" width="15%">Số lượng xuất</th>
				<th align="center" width="10%"  >Vị trí</th>
			</tr>
			
			<% 
			int count = 0; 
			if(spList.size() > 0) 
			{ 
				for(int i = 0; i < spList.size(); i++)
				{ 
					IYeucau yeucau = spList.get(i); %>
					
					<tr>
						<td > 
							<input type="hidden" name="idsp" value="<%= yeucau.getId() %>" > 
							<input type="text" name="masp" value="<%= yeucau.getMa() %>" style="width: 100%"  readonly="readonly"  > 
						</td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
  
						<td> <input type="text" name="soluongnhan" value="<%= yeucau.getSoluongchuyen() %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" > </td>
						
						<td align="center" > 
							 	<a href="" id="<%= yeucau.getMa() + "pobup"%>" rel="subcontent<%= i %>">
		           	 							<img alt="Vi tri luu kho" src="../images/vitriluu.png"></a>
           	 					<DIV  id="subcontent<%= i %>" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; max-height :500px;width: 500px; padding: 1px; overflow: auto; ">
			                    <table width="100%" align="center">
			                        <tr>
			                        	
			                            <%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
				                             	<th width="10%">Khu vực kho</th>
				                             <%} %>
			                             <th width="20%">Số lô</th>
			                             <th width="20%">Ngày nhập kho  </th>
			                             <th width="20%">Số lượng tồn</th>
 										 <th width="20%" >Số lượng xuất</th>
			                        </tr>
			                        <%
			                        	List<ISpDetail> spConList = yeucau.getSpDetailList();
			                        	int stt = 1; 
			                        	if(spConList.size() > 0)
			                        	{
			                        		for(int sd = 0; sd < spConList.size(); sd ++)
			                        		{
			                        			ISpDetail spCon = spConList.get(sd);
			                        		%>
			                        			<tr>
			                        			<%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
			                        			<td> 
			                        				
			                        				<input name="<%= yeucau.getId() + "khuvuc_id" %>" 
						                            	type="hidden" style="width: 100%" value="<%= spCon.getKhuId() %>" readonly="readonly" /> 
						                            	
						                            	<input name="<%= yeucau.getId() + "khuvuc_ten" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getKhu() %>" readonly="readonly" /> 
						                            </td>
						                            <%} %>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "solo" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSolo() %>" readonly="readonly" /> 
						                            </td>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "ngaynhapkho" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getNgaybatdau() %>" readonly="readonly" /> 
						                            </td>
						                            <td>
						                            	<input name="<%= yeucau.getId() + "soluongton" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSoluongton()%>" onkeypress="return keypress(event);" />
						                            	</td>
						                             
						                            	<td>
						                            	<input name="<%= yeucau.getId() + "soluongxuat" %>" 
						                            		type="text" style="width: 100%" value="<%= spCon.getSoluong()%>" onkeypress="return keypress(event);" /> 
						                            	</td>
						                            	
						                           
						                        </tr>
			                        		<%  } 
			                        	}
			                        %>
			                         
			                    </table>
			                     <div align="right"><a href="javascript:hoantat('<%=i %>','<%= yeucau.getMa()%>')">Hoàn tất</a></div>
			                </DIV>
						</td>
					</tr>
					
				<% count++; } } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>
<script type="text/javascript">

	//replaces();

<% 
		for(int i = 0; i < spList.size(); i++)
		{
			IYeucau yeucau = spList.get(i);
	%>
		dropdowncontent.init('<%=yeucau.getMa()+ "pobup"%>', "left-top", 500, "click");
	<%} %>


</script>

<% lsxBean.DBclose(); %>

</form>
</BODY>
</html>