<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuatgiay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuatgiay.imp.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP" %>

<% IErpTieuhaonguyenlieu lsxBean = (IErpTieuhaonguyenlieu)session.getAttribute("lsxBean"); %>
<% List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>)lsxBean.getListDanhMuc(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
   
   ResultSet rsnhapkho =lsxBean.getRsNhapKho();
   ResultSet rsLsp =lsxBean.getLoaisanphamRs();
   
   String chuoiphieunhap=lsxBean.getNhapKhoId();
   
   NumberFormat formatter=new DecimalFormat("#,###,###.######");
   ResultSet lsxRs = lsxBean.getLsxRs(); 
   ResultSet cdsxRs = lsxBean.getCDSXRs(); 

   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/ajax_sanphalisttieuhao.js"></script>
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
 
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>

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
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 function  reloadtieuhao()
	 {	
	 	 document.forms['hctmhForm'].action.value = 'reloadtieuhao';
	     document.forms['hctmhForm'].submit();
	 }
	
	 function tieuHao()
	 {
		document.forms["hctmhForm"].action.value = "tieuhao";
	    document.forms["hctmhForm"].submit();
	 }
	 function changeNoiDung()
	 { 
		 console.log("Da vao");
			var vtId=document.getElementsByName("idvattu");
			var mavt = document.getElementsByName("mavattu");
			var tenvt = document.getElementsByName("tenvattu");
			var donvitinhvt = document.getElementsByName("donvitinh");  
			var soluong = document.getElementsByName("thucte");
			var soluongDM = document.getElementsByName("soluongDM");

			for(var i = 0; i < mavt.length; i++)
			{
			vtId.item(i).value = "";
			mavt.item(i).value = "";
			tenvt.item(i).value = "";
			donvitinhvt.item(i).value = "";		
			soluong.item(i).value = "";
			soluongDM.item(i).value = "";
			}
		document.forms["hctmhForm"].action.value = "changeNoiDung";
	    document.forms["hctmhForm"].submit();
	    
	 }
	 function TinhTongSoLuongXuat()
		{

				var sanpham = document.getElementsByName("idvattu");
				
				var dinhmuc = document.getElementsByName("soluongDM");
				
				var hieusuat = document.getElementsByName("hieusuat");
				
				var soluongxuat = document.getElementsByName("thucte");
				
				for(var i = 0; i < sanpham.length; i++)
				{
					 
					if(sanpham.item(i).value != "")
					{
						var tongluong = 0;
						var id =  sanpham.item(i).value; 
						 
						var solo = document.getElementsByName(id + '.solo');
						var soluong = document.getElementsByName(id + '.soluong');
						
						var soluongton= document.getElementsByName(id + '.soluongton');
		  
						for( var j = 0; j < soluong.length; j++)
						{
							if( soluong.item(j).value != "")
							{

								var slg = soluong.item(j).value.replace(/,/g,"");
								tongluong = parseFloat(tongluong) + parseFloat(slg);
								var slton=soluongton.item(j).value.replace(/,/g,""); 
								
								 if( parseFloat(slg) > parseFloat(slton)){
									 soluong.item(j).value= slton;
									 alert('Vui lòng nhập số lượng nhỏ hơn số lượng tồn của lô'+slg+"slton : "+slton );
								 }
							}
							else
							{
								 
							}
						}
						var dinhmuc_=dinhmuc.item(i).value;
						if(tongluong >0){
							var hieusuat_=dinhmuc_ * 100 /  tongluong ;
							hieusuat.item(i).value = hieusuat_.toFixed(3)+ '%';
						}else{
							hieusuat.item(i).value  ='0%';
						}
						soluongxuat.item(i).value =  tongluong.toFixed(6);
						 
					}
				}
				
		}
	 function  reloadsptieuhao()
	 {	
		  
	 	 document.forms['hctmhForm'].action.value = 'reloadsptieuhao';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function replaces()
		{

			var vtId=document.getElementsByName("idvattu");
			var mavt = document.getElementsByName("mavattu");
			var tenvt = document.getElementsByName("tenvattu");
			var donvitinhvt = document.getElementsByName("donvitinh");  
			var soluong = document.getElementsByName("thucte");
			var soluongDM = document.getElementsByName("soluongDM");
			
		  
			var i;
			for(i=0; i < mavt.length; i++)
			{
				if(mavt.item(i).value != "")
				{
					var vt = mavt.item(i).value;
					var pos = parseInt(vt.indexOf("--"));
					if(pos > 0)
					{
						mavt.item(i).value = vt.substring(0, pos);
						vt = vt.substr(parseInt(vt.indexOf("--")) + 3);
						tenvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" [")));
						vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
						donvitinhvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
						
						vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
						vtId.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));
						reloadsptieuhao();
					}	
					
				}
				else
				{
					vtId.item(i).value = "";
					mavt.item(i).value = "";
					tenvt.item(i).value = "";
					donvitinhvt.item(i).value = "";		
					soluong.item(i).value = "";
					soluongDM.item(i).value = "";
				}
			}
			TinhTongSoLuongXuat();
			setTimeout(replaces, 300);
		}
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpLenhsanxuatgiayActionSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getTieuHaoId() %>'>
<input type="hidden" name="task" value='tieuHao'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất > Lệnh sản xuất > Tiêu hao nguyên liệu
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpLenhsanxuatgiayActionSvl?userId=<%=userId%>&display=<%= lsxBean.getId() %>&congdoan=<%=lsxBean.getCongDoanCurrent() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
	        <span id="btnSave">
		        <A href="javascript:saveform()" >
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
    	<legend class="legendtitle">Tiêu hao nguyên liệu </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
               
             
                
                  <TR>
                	<TD class="plainlabel">Số lệnh SX</TD>
                   	<TD class="plainlabel"  >
                   		<select name="lsxId" style="width: 200px;" onChange="changeNoiDung();" >
                   		<option value=""></option>
                   			<% if( lsxRs != null ) { %>
                    		<% while(lsxRs.next()) 
                    		{ 
                    			if(lsxBean.getLsxIds().equals(lsxRs.getString("PK_Seq"))) 
                    			{ %>
                    				<option value="<%= lsxRs.getString("PK_Seq") %>" selected="selected"><%= lsxRs.getString("diengiai") %></option>
                    			<% } 
                    			else { %> 
                    				<option value="<%= lsxRs.getString("PK_Seq") %>" ><%= lsxRs.getString("diengiai") %></option>
                    			<% }  %> 
                    		<% } lsxRs.close(); }  %>
                    		
                    	</select>
                   	</TD>
                   	<TD class="plainlabel">Công đoạn SX</TD>
                   	<TD class="plainlabel"  >
                   		<select name="CdsxId" style="width: 200px;" onChange="changeNoiDung();" >
                   		<option value=""></option>
                   			<% if( cdsxRs != null ) { %>
                    		<% while(cdsxRs.next()) 
                    		{ 
                    			if(lsxBean.getCDSXId().equals(cdsxRs.getString("PK_Seq"))) 
                    			{ %>
                    				<option value="<%= cdsxRs.getString("PK_Seq") %>" selected="selected"><%= cdsxRs.getString("diengiai") %></option>
                    			<% } 
                    			else { %> 
                    				<option value="<%= cdsxRs.getString("PK_Seq") %>" ><%= cdsxRs.getString("diengiai") %></option>
                    			<% }  %> 
                    		<% } cdsxRs.close(); }  %>
                    		
                    	</select>
                   	</TD>
                   	
                </TR>
                
                 
                <TR>
								<TD class="plainlabel">Loại sản phẩm</TD>
								<TD class="plainlabel"  >
									<select name="loaisanpham" id="loaisanpham" onChange="changeNoiDung();">
									<option value="0"  ></option>
									
                            	<%
                            	
                            	if(rsLsp!=null){
                            		while(rsLsp.next()){
                            			if(rsLsp.getString("pk_seq").equals(lsxBean.getLoaisanphamTH())){
                            				%> 
                            				<option value="<%=rsLsp.getString("pk_seq")%>" selected ><%=rsLsp.getString("ten") %></option>
                            				<%
                            			}else{
                            				%>
                            				<option value="<%=rsLsp.getString("pk_seq")%>"  ><%=rsLsp.getString("ten") %></option>
                            				 <%
                            			}
                            		}
                            		
                            	}
                            	
                        		  %>
									</select>
								</TD>
								
							 
                  
                    <TD class="plainlabel" valign="top">Ngày tiêu hao </TD>
                    <TD  class="plainlabel" valign="top">
                    	<input type="text" value="<%=lsxBean.getNgaytieuhao()%>"  name="ngaytieuhao" class ="days"  onchange="reloadtieuhao()" >
                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Khoản mục chi phí </TD>
                    <TD class="plainlabel" colspan=3>
                    	<select name="khoanmucchiphi" id="khoanmucchiphi" >
									<option value=""  ></option>
									<%
                            	ResultSet khoanmucRS = lsxBean.getKhoanmucchiphiRs();
                            	if(khoanmucRS!=null){
                            		while(khoanmucRS.next()){
                            			if(khoanmucRS.getString("pk_seq").equals(lsxBean.getKhoanmucchiphi())){
                            				%> 
                            				<option value="<%=khoanmucRS.getString("pk_seq")%>" selected ><%=khoanmucRS.getString("ten")+": " +khoanmucRS.getString("diengiai") %></option>
                            				<%
                            			}else{
                            				%>
                            				<option value="<%=khoanmucRS.getString("pk_seq")%>"  ><%=khoanmucRS.getString("ten")+": " +khoanmucRS.getString("diengiai") %></option>
                            				 <%
                            			}
                            		}
                            		
                            	}
                            	
                        		  %>
						</select>
                    </TD>
                </TR>
                  <TR class="plainlabel" >
                    	<td colspan="4" >
                    			<table border="1" > 
                    			<tr class="tbheader">	
	                    			<td>
	                    				Số nhập kho
	                    			</td>
	                    			<td>
	                    				Ngày nhập
	                    			</td>
									<td>
										Tổng số lượng nhận
									</td> 
									<td>
										Đơn vị nhập
									</td> 
									
									<td>
										Chọn tiêu hao
									</td> 
                    			</tr>
                    			<%
                    			if(rsnhapkho!=null)
                    			 while (rsnhapkho.next()) { %>
                    			<tr>
                    				<td>
	                    				<input value="<%=rsnhapkho.getString("PK_SEQ") %>"  type="text" >
	                    			</td>
	                    			<td>
	                    				<input value="<%=rsnhapkho.getString("NGAYNHAPKHO") %>"  type="text" > 
	                    			</td>
									<td>
							 			<input value="<%=rsnhapkho.getString("SOLUONG") %>"  type="text" > 
									</td> 
									<td>
							 			<input value="<%=rsnhapkho.getString("donvi") %>"  type="text" > 
									</td>
									<td>
										<% if(chuoiphieunhap.contains(rsnhapkho.getString("PK_SEQ"))){ %>
										<input name="sophieunhap" onchange="reloadtieuhao()" checked="checked" type="checkbox" value="<%=rsnhapkho.getString("PK_SEQ")%>" >
										
										<%}else{ %>
										<input name="sophieunhap" onchange="reloadtieuhao()"  type="checkbox" value="<%=rsnhapkho.getString("PK_SEQ")%>" >
										<%} %>
									</td>
                    			</tr>
                    			<%} %>
                    			
                    			
                    			</table>
                    	</td>
                    	<td>
                    	</td>
                  </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Số lượng sản xuất </TD>
                    <TD colspan="3" class="plainlabel" valign="top">
                    	<input type="text" id="soluongsx" onkeypress="return keypress(event);" name="soluongsx" value="<%= lsxBean.getSoluong() %>" style="text-align: right;" readonly="readonly" />
                    	<input type="hidden" name="soluongchuan" value="<%= lsxBean.getSoluongchuan() %>" style="text-align: right;" readonly = "readonly"> 
                    	<% if(lsxBean.getChophepTT().equals("1")) { %> 
                       		<input type="hidden" name="chophepTT" value="1" style="display: none" >
                       	<% } else { %> 
                       		<input type="checkbox" name="chophepTT" value="0" style="display: none" >
                       	<% } %>
                    </TD>
                </TR>
                
                

            </TABLE>
			<hr />
			
		 
			
				<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="15%">Mã thành phẩm</th>
					<th align="center" width="25%">Tên thành phẩm</th>
					<th align="center" width="10%">Đơn vị tính</th>
					<th align="center" width="10%">Định mức</th>
					<th align="center" width="10%">Thực tế</th>
					  <th align="center" width="5%">Lô tiêu hao</th>
					  <th align="center" width="15%">Hiệu suất</th>
					     
				</tr>
				<% 
				//System.out.println("1.So vat tu la: " + vattuList.size());
				int count = 0; 
				if(vattuList.size() > 0) 
				{ 
					for(int i = 0; i < vattuList.size(); i++)
					{ 
						IDanhmucvattu_SP vattu = vattuList.get(i); %>
						
						<tr>
							<td >  
								<input type="hidden" name="idvattu" value="<%= vattu.getIdVT() %>"  >
								<input type="hidden" name="loai" value="<%= vattu.getLoai() %>"  >
								 
									<input type="text" name="mavattu" value="<%= vattu.getMaVatTu() %>" style="width: 100%"  >
								 
							</td>
							<td> <input type="text" name="tenvattu" value="<%= vattu.getTenVatTu() %>" style="width: 100%" readonly="readonly"> </td>
							<td> <input type="text" name="donvitinh" value="<%= vattu.getDvtVT() %>" style="width: 100%; " onkeypress="return keypress(event);" readonly = "readonly"> </td>
							<td> 
								<input type="text" value="<%= vattu.getSoLuong() %>" 
										style="width: 100%; text-align: right;" readonly="readonly" > 
								<input type="hidden" name="soluongDM" value="<%= vattu.getSoLuong() %>" 
										style="width: 100%; text-align: right;" readonly="readonly" > 
							</td>
							<td> 
								<input type="text" name="thucte" readonly="readonly" value="" style="width: 100%; text-align: right;"   > 
							</td>
				 			 <td align="center">
							  	<a href="" id="<%= vattu.getMaVatTu()+i %>" rel="subcontent<%= i %>">
	           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden;  overflow: scroll; border: 9px solid #80CB9B;
				                             background-color: white;height: 300px ;width: 900px; padding: 1px;">
				                    <table width="100%" align="center">
				                    
				                    <% 
				                	List<ISpDetail> spDetailList = vattu.getSpDetailList();
				                    boolean isql_khuvuc=false;
				                    for(int sd = 0; sd < spDetailList.size(); sd ++)
	                        		{
	                        			ISpDetail spDetail = spDetailList.get(sd);
	                        			if(spDetail.getKhuId()!=null  && spDetail.getKhuId().length() >0){
	                        				isql_khuvuc=true;
	                        			}
	                        		}
				                    %>
				                        <tr>
				                        	<%if(isql_khuvuc){ %>
				                        	 <th width="100px">Khu vực</th>	
				                        	 <%} %>	
				                        	  <th width="100px">Vị trí</th>
				                        	  
				                             <th width="100px">Số lô</th>
				                             <th width="100px">MARQUETTE </th>
				                             <th width="100px">Mã thùng</th>
				                               <th width="100px">Mã mẻ</th>
				                             <th width="100px">Mã phiếu</th>
				                             <th width="100px">Phiếu EO</th>
				                             <th width="100px">Phiếu định tính</th>
				                         	  <th width="100px">Ngày hết hạn</th>
				                             <th width="100px">Ngày nhập kho </th>
				                              <th width="100px">Hàm ẩm</th>
				                              <th width="100px">Hàm lượng</th>
				                               <th width="100px">Nhà sản xuất</th>
				                             <th width="50px"> Số lượng tồn</th>
				                             <th width="50px"> Số lượng</th>
				                        	 <th width="50px">Đơn vị tính</th>
				                        </tr>
				                        <%
				                         
				                        		int stt = 1; 
				                        	 
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
				                        			<%if(isql_khuvuc){ %>
				                        				  <td>
				                        				  	<input type="hidden" style="width: 100%" name="<%= vattu.getIdVT() + ".khuid" %>" value="<%= spDetail.getKhuId() %>" readonly="readonly" />
				                        				  	
							                            	<input type="text" style="width: 100%" name="<%= vattu.getIdVT() + ".khuvucten" %>" value="<%= spDetail.getKhu() %>" readonly="readonly" />
							                            	
							                            	</td>
							                         
							                            <%} %>
							                            	<td>
							                            	<input type="hidden" style="width: 100%" name="<%= vattu.getIdVT() + ".vitriid" %>" value="<%= spDetail.getVitriId() %>" readonly="readonly" />
							                            	<input type="text" style="width: 100%" name="<%= vattu.getIdVT() + ".vitri" %>" value="<%= spDetail.getVitri() %>" readonly="readonly" />
							                            	<input type="hidden" style="width: 100%" name="<%= vattu.getIdVT() + ".nsx_fk" %>" value="<%= spDetail.getNSX_FK() %>" readonly="readonly" />
							                            	</td>
							                               <td>
							                               <input type="hidden" style="width: 100%" name="<%= vattu.getIdVT() + ".ngaybatdau1" %>" value="<%= spDetail.getNgaybatdau() %>" readonly="readonly" />	
							                            	<input type="text" style="width: 100%" name="<%= vattu.getIdVT() + ".solo" %>" value="<%= spDetail.getSolo() %>" readonly="readonly" />
							                            	</td>
							                            	<!-- ,A.MATHUNG,A.MAME,A.NGAYNHAPKHO,A.MARQ -->
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".MARQ" %>"  
							                            	 value="<%= spDetail.getMarq() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".MATHUNG" %>"  
							                            	 value="<%= spDetail.getMathung() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".MAME" %>"  
							                            	 value="<%= spDetail.getMame() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".MAPHIEU" %>"  
							                            	 value="<%= spDetail.getMaphieu() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".PHIEUEO" %>"  
							                            	 value="<%= spDetail.getPHIEUEO() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".MAPHIEUDINHTINH" %>"  
							                            	 value="<%= spDetail.getMAPHIEUDINHTINH() %>" readonly="readonly" />
							                            	</td>
							                            	 	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".NGAYHETHAN" %>"  
							                            	 value="<%= spDetail.getNgayhethan() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".NGAYNHAPKHO" %>"  
							                            	 value="<%= spDetail.getNgaynhapkho() %>" readonly="readonly" />
							                            	</td>
							                            		<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".HAMLUONG" %>"  
							                            	 value="<%= spDetail.getHamluong() %>" readonly="readonly" />
							                            	</td>
							                            		<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".HAMAM" %>"  
							                            	 value="<%= spDetail.getHamam() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".NHASANXUAT" %>"  
							                            	 value="<%= spDetail.getMaNSX()%>" readonly="readonly" />
							                            	</td>
							                            		
							                          		<td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".soluongton" %>" value="<%= spDetail.getSoluongton() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	
 
							                            <td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".soluong" %>" value="<%=spDetail.getSoluong()%>"  onkeypress="return keypress(event);"  /></td>
							                            <td>
							                            	<input type="text" style="width: 100%"  name="<%= vattu.getIdVT() + ".donvi" %>" value="<%= spDetail.getDvt() %>" readonly="readonly" /></td>
							                           
							                        </tr>
				                        		<%}
				                        	 
				                        %>
				                        
				                    </table>
				                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Đóng lại</a></div>
				                </DIV>
	        	 			</td> 
	        	 			<td> <input type="text" name="hieusuat" value="" style="width: 100%" readonly="readonly"> </td>
						</tr>
						
					<% count++; } }  
					  
				 	for(int i = 0; i < 5; i++)
					{ 
						  %>
					 <tr>
							<td >  
								<input type="hidden" name="idvattu" value=""  >
								<input type="hidden" name="loai" value=""  >
								 
									<input type="text" name="mavattu" value="" style="width: 100%"   AUTOCOMPLETE="off" onkeyup="ajax_showOptions(this,'abc',event)">
								 
							</td>
							<td> <input type="text" name="tenvattu" value="" style="width: 100%" readonly="readonly"> </td>
							<td> <input type="text" name="donvitinh" value="" style="width: 100%; " onkeypress="return keypress(event);" readonly = "readonly"> </td>
							<td> 
								<input type="text" value="" 
										style="width: 100%; text-align: right;" readonly="readonly" > 
								<input type="hidden" name="soluongDM" value="" 
										style="width: 100%; text-align: right;" readonly="readonly" > 
							</td>
							<td> 
								<input type="text" name="thucte" readonly="readonly" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" > 
							</td>
							<td>
							</td>
							<td> <input type="text" name="hieusuat" value="" style="width: 100%" readonly="readonly"> </td>
							</tr>
					 <%} %>
					
					
				</table>
            </div>
     </fieldset>	
    </div>
</div>
</form>

<script type="text/javascript">
replaces();
	 <% 
	 	for(int i = 0; i <= 10; i ++){ %>
			dropdowncontent.init('vtId<%= i %>', "left-top", 300, "click");
	 <% } %>

	<% 
	for(int i = 0; i < vattuList.size(); i++)
	{
		IDanhmucvattu_SP vattu = vattuList.get(i);
	%>
		dropdowncontent.init('<%= vattu.getMaVatTu()+i %>', "left-top", 600, "click");
	<%} %>

</script>

<% 
try{
		if( vattuList!=null){
			vattuList.clear();
		}
		if( rsnhapkho!=null){
			rsnhapkho.close();
		}
}catch(Exception er){
	
}finally{


		session.setAttribute("lsxBean", null) ;   
		
		lsxBean.DBclose();
}


%>

</BODY>
</html>