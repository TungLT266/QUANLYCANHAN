<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.giay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.giay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpNhapKhoLsx nkBean = (IErpNhapKhoLsx)session.getAttribute("nkBean"); %>
	<% String userId = (String) session.getAttribute("userId");  %>
	<% String userTen = (String) session.getAttribute("userTen"); 
	ResultSet  rsbtp=nkBean.getRsBTP();
	ResultSet  rsthupham=nkBean.getRsThupham();
	ResultSet  rskho=nkBean.getrsKhoNhanTP();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Erp - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
	
	 function saveform()
	 {	
		 
		 if(document.forms['nkForm'].ngaynhapkho.value ==''){
			 document.forms['nkForm'].dataerror.value='Vui lòng chọn ngày nhập kho';
			 return;
		 }
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['nkForm'].action.value='save';
	     document.forms['nkForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['nkForm'].action.value='submit';
	     document.forms["nkForm"].submit();
	 }
	 
	 function changeloaisp()
	 { 
		 document.forms['nkForm'].action.value='changeloaisp';
	     document.forms["nkForm"].submit();
	 }
	 
	function changesolo(){
	 
	}
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkForm" method="post" action="../../ErpNhapKhoLsxUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="islsxcongnghe"  value='<%=nkBean.getIsLsxCongNghe()%>'>
<input type="hidden" name="task" value='nhapKho'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng &gt; Sản xuất &gt; Lệnh sản xuất &gt; Nhập kho từ lệnh sản xuất
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpLenhsanxuatgiayActionSvl?userId=<%=userId%>&display=<%= nkBean.getLsxId() %>&congdoan=<%= nkBean.getCongDoanId() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        
        <% if( ! nkBean.getTrangThai().equals("2")) { %>
	        <span id="btnSave">
		        <A href="javascript:saveform()" >
		        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
	        </span>
        <% } %>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= nkBean.getMsg() %></textarea>
		         <% nkBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhập kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Số lệnh sản xuất</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text" id="lsxId" name="lsxId" value="<%= nkBean.getLsxId() %>" onchange="submitform();" readonly="readonly" />
                    	<input type="hidden" id="congdoanId" name="congdoanId" value="<%= nkBean.getCongDoanId() %>"  readonly="readonly" />
                    	<input type="hidden" id="noidungnhap" name="noidungnhap" value="<%= nkBean.getNdnId() %>"  readonly="readonly" />
                    </TD>
                </TR>
                  <TR>
                    <TD width="20%" class="plainlabel" valign="top">Công đoạn sản xuất</TD>
                    <TD class="plainlabel" valign="top"  colspan="3">
                    	<input type="text" id="congdoanId" name="congdoanId" value="<%= nkBean.getCongDoanId() %>"  readonly="readonly" />
                    </TD>
                </TR>
                
            
                
                 <TR>
                    <TD width="20%" class="plainlabel" valign="top">Ngày nhập kho</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  class ="days" id="ngaynhapkho"  name="ngaynhapkho" onchange="changesolo();" value="<%= nkBean.getNgaynhapkho() %>"  readonly="readonly" />
                    </TD>
                    <TD width="20%" class="plainlabel" valign="top">Số lượng sản xuất</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="soluongsanxuat" name="soluongsanxuat" value="<%= nkBean.getSoLuongSanXuat() %>"  readonly="readonly" />
                    </TD>
                </TR>
                
                    <TR>
                    <TD width="20%" class="plainlabel" valign="top">Loại sản phẩm</TD>
                    <TD class="plainlabel" valign="top">
                    	 <select name="loaisanpham" onchange="changeloaisp();" > 
                   		    <%  
 								String[] mang=new String[]{"0","1","2","3"};
 								String[] mangten=new String[]{"Thành phẩm","Bán thành phẩm","Phế phẩm","Thứ phẩm"};
 								
 								 for(int j=0;j<mang.length;j++){
 									 if(nkBean.getLoaisanpham().trim().equals(mang[j])){
 									 %>
 									 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
 									 <%
 									 }else{
 									 %>
 									 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
 									 <% 
 									 }
 								 }
 								%>
                            
                           </select>
                           
                    </TD>
                    <TD width="20%" class="plainlabel" valign="top">Đơn vị tính sản xuất</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="donvitinh" name="donvitinh" value="<%= nkBean.getDonViTinh() %>"  readonly="readonly" />
                    </TD>
                </TR>
                
               
               			<%if(nkBean.getIsLsxCongNghe().equals("1")){ %>
                 <TR>
                    <TD width="20%" class="plainlabel" valign="top">Không kiểm định</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                         <%if(nkBean.getKhongkiemdinh().equals("1")) {%>
               					<input type="checkbox" name="khongkiemdinh" value="1" checked="checked">
               			<%}else{ %>
               					<input type="checkbox" name="khongkiemdinh" value="1">
               			<%} %> 
                    </TD>
                </TR>
                <%} %>
                
                 
                <%if(nkBean.getLoaisanpham().equals("1")){ %>
                 <TR>
                    <TD width="20%" class="plainlabel" valign="top">Chọn bán thành phẩm</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	 <select name="BTPID" onchange="changeloaisp();" > 
                    	  <option>   </option>
                   		    <%  
 								 
 								if(rsbtp!=null)
 								while (rsbtp.next()){
 									 if(nkBean.getBTPId().trim().equals(rsbtp.getString("PK_SEQ"))){
 									 %>
 									 <option selected="selected" value="<%=rsbtp.getString("PK_SEQ")%>"> <%=rsbtp.getString("ten") %> </option>
 									 <%
 									 }else{
 									 %>
 									 <option value="<%=rsbtp.getString("PK_SEQ")%>"> <%=rsbtp.getString("ten") %> </option>
 									 <% 
 									 }
 								 }
 								%>
                            
                           </select>
                           
                    </TD>
                </TR>
                <%}else if(nkBean.getLoaisanpham().equals("3")){ %>
                	  <TR>
                    <TD width="20%" class="plainlabel" valign="top">Chọn thứ phẩm</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	 <select name="thuphamid" onchange="changeloaisp();" > 
                    	  <option>   </option>
                   		    <%  
 								 
 								if(rsthupham!=null)
 								while (rsthupham.next()){
 									 if(nkBean.getThuphamId().trim().equals(rsthupham.getString("PK_SEQ"))){
 									 %>
 									 <option selected="selected" value="<%=rsthupham.getString("PK_SEQ")%>"> <%=rsthupham.getString("ten") %> </option>
 									 <%
 									 }else{
 									 %>
 									 <option value="<%=rsthupham.getString("PK_SEQ")%>"> <%=rsthupham.getString("ten") %> </option>
 									 <% 
 									 }
 								 }
 								%>
                            
                           </select>
                           
                    </TD>
                </TR>
                <%} %>
               <tr>
              				 <TD class="plainlabel" valign="middle">Chọn kho nhận hàng đạt</TD>
											<TD class="plainlabel" valign="middle">
											
											 <select id="khonhanhangdatid" name="khonhanhangdatid">
											 <option   >  </option>
											<%
											 
											if(  rskho!=null){
				           	 					while (rskho.next()){ %>
				           	 						<% if( rskho.getString("pk_Seq").equals(nkBean.getKhoNhanTP()) ) { %>
				           	 							<option selected="selected" value="<%=rskho.getString("pk_Seq")%>" > <%=rskho.getString("ten")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=rskho.getString("pk_Seq")%>" >  <%=rskho.getString("ten")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				rskho.close();
				           	 					} 
											 %>
											 </select>
											 </TD>
											 
               </tr>
                
                <tr style="display: none">
                	<td class="plainlabel">Kho nhập</td>
                    <td class="plainlabel">
                    	<input type="hidden" name="khoId" id="khoId" value="<%= nkBean.getKhoId()%>">
                    </td>
                </tr>
                <tr style="display: none">
                    <td class="plainlabel">
                    	<input type="hidden" name="isQlKhuvuc" id="isQlKhuvucId" value="<%= nkBean.getIsQLKV()%>">
                    </td>
                </tr>
            </TABLE>
            <hr> 
            </div>
           
           <div align="left" style="width:100%; float:none; clear:none;">
           <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>
                	<TH align="center" width="7%">Mã Nhập kho</TH>
                	<TH align="center" width="3%">In</TH>
	                <TH align="center" width="10%">Mã sản phẩm</TH>
	                <TH align="center" width="20%">Tên sản phẩm</TH>
	                 <TH align="center" width="7%">Đơn vị tính nhập kho </TH>
	                 	
               	 	<!-- <TH align="center" width="7%">Số lượng SX</TH> -->
               	 	<TH align="center" width="7%">Số lượng nhập</TH>
						<TH align="center" width="7%">SL lấy mẫu</TH>
						 <TH align="center" width="5%"> Đơn vị lấy mẫu </TH>
						 
               	 	<%if(nkBean.getIsQLKV().equals("1")) {%>
               	 	<TH align="center" width="10%">Khu vực kho</TH>
           	 		<%} %>
               	 	<TH align="center" width="7%">Số lô</TH>
               	 	<TH align="center" width="8%">Ngày sản xuất</TH>
               	 	<TH align="center" width="8%">Ngày hết hạn</TH>
               	 
                </TR>
                
                <%
                
                	for(int index=0;index<nkBean.getListNhapKho().size();index++)
                	{
                		IErpNhapkho e=nkBean.getListNhapKho().get(index);
                		List<ISanpham> spList=e.getSpList();
                		String readonly="";
                		if(e.getTrangthai().equals("1"))
                			readonly="readonly=\"readonly\"";
                		%>
                		<input type="hidden" value="<%=e.getId() %>" name= "nhapkhoId" readonly="readonly" <%=readonly %>>
                	<% 
                		for(int i = 0; i < spList.size(); i++)
                		{
                			ISanpham sp = spList.get(i);
	              %>
	              <% if( ( e.getId().trim().length() > 0 && Double.parseDouble(sp.getSoluongnhapkho()) > 0 ) || ( e.getId().trim().length() <= 0 ) ) {
	            	  	//System.out.println("SO NHAP KHO: " + e.getId().trim() + "  --SO LUONG SN:  " + Double.parseDouble(sp.getSoluongnhapkho()) );
	            	  %>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text"    style="width: 100%; text-align: center;" value="<%= index + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="center">
           	 					<input type="text"  <%=readonly %> value="<%= e.getId() %>" name= "nhapkhoId_<%=e.getId() %>" readonly="readonly" style="width:70%;">           	 					
           	 				</td>
           	 				<td align="center">
           	 					<% if(e.getId().length() > 0) { %> 
           	 						<A href="../../ErpPhieunhapkhoPdfSvl?userId=<%= nkBean.getUserId() %>&id=<%= e.getId() %>&task=nhapkho" >
	        						<IMG src="../images/Printer30.png" title="In phieu nhap kho" alt="In phieu nhap kho" width="24" height="24"></A>
	        					<%} %>
           	 				</td>
           	 				<td align="center">
           	 					<input type="text"  <%=readonly %> style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua_<%=e.getId() %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text"  <%=readonly %> style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "diengiai_<%=e.getId() %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center" >
           	 				
           	 					<select name="dvdlid_<%=e.getId() %>" style="width: 100%; text-align: left;" > 
           	 					<% 
           	 					  ResultSet rsdvdl=sp.getRsDvld();
           	 					if(rsdvdl!=null){
           	 					while (rsdvdl.next()){ %>
           	 						<% if( rsdvdl.getString("pk_Seq").equals(sp.getDvdlId()) ) { %>
           	 							<option selected="selected" value="<%=rsdvdl.getString("pk_Seq")%>" > <%=rsdvdl.getString("diengiai")%> </option>
           	 						<%}else{ %>
           	 							<option value="<%=rsdvdl.getString("pk_Seq")%>" >  <%=rsdvdl.getString("diengiai")%> </option>
           	 						<%}%>
           	 						
           	 				    <%}  
           	 					rsdvdl.close();
           	 					} 
           	 				    %>
           	 					</select>
           	 					
           	 				</td>
           	 				
           	 		<%-- 		<td align="center">
           	 					
           	 					
           	 				</td> --%>
           	 				<td align="center">
           	 				<input type="hidden"  <%=readonly %>  style="width: 100%; text-align: right;" value="<%= sp.getSoluongSx() %>" name= "soluongsx_<%=e.getId() %>" readonly="readonly" >
           	 					
           	 				
	           	 				<input type="hidden" value="<%= sp.getDongia() %>" name= "dongia_<%=e.getId() %>"  <%=readonly %> >
	           	 					<input type="hidden" value="<%= sp.getDongiaViet() %>" name= "dongiaViet_<%=e.getId() %>"   <%=readonly %>>
	           	 					<input type="hidden" value="<%= sp.getTiente() %>" name= "tiente_<%=e.getId() %>"  <%=readonly %> >
	           	 					 
	           	 					<input type="hidden" value="<%= sp.getId() %>" name= "sanphamId_<%=e.getId() %>"   <%=readonly %>>
					         	<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongnhapkho() %>" name= "soluongnhap_<%=e.getId() %>" onkeypress="return keypress(event);" <%=readonly %>>
           	 				</td>
           	 				<td align ="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluonglaymau() %>" name= "soluonglaymau_<%=e.getId() %>"  <%=readonly %>   onkeypress="return keypress(event);">
           	 				</td>
           	 				<td align ="center">
           	 				<input type="hidden" style="width: 100%; text-align: right;" value="<%= sp.getDvdl_Mau_Id() %>" name= "dvdl_mau_Id_<%=e.getId() %>"   readonly    >
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getDvdl_Mau() %>" name= "dvdl_mau_<%=e.getId() %>"   readonly >
           	 				</td>
           	 				
           	 				<%if(nkBean.getIsQLKV().equals("1")) {%>
           	 				<td>
           	 					<select style="width: 100%" id="kvKhoId<%=e.getId() %>" name="kvKhoId_<%=e.getId() %>">
		                    		<option value=""> </option>
		                        	<%
		                        		if(sp.getKhuvucRs() != null)
		                        		{
		                        			try
		                        			{
		                        			while(sp.getKhuvucRs().next())
		                        			{  
		                        			if( sp.getKhuvucRs().getString("pk_seq").equals(sp.getKhuvucId())){ %>
		                        				<option value="<%= sp.getKhuvucRs().getString("pk_seq") %>" selected="selected" ><%=    sp.getKhuvucRs().getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= sp.getKhuvucRs().getString("pk_seq") %>" ><%=  sp.getKhuvucRs().getString("ten") %></option>
		                        		 <% } } sp.getKhuvucRs().close();
		                        		 	} catch(SQLException ex){}
		                        		}
		                        	%>
		                    	</select>
           	 				</td>
           	 				<%} %>
           	 				<td align="center">
           	 					<%if(nkBean.getIsQLKV().equals("0")) {%>
           	 					<input type="hidden" name="kvKhoId_<%=e.getId() %>" value = ""/>
           	 					<%} %>
           	 					<input type="text" style="width: 100%;" value="<%= sp.getSolo() %>" name= "solo_<%=e.getId() %>" <%=readonly %> >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= sp.getNgaySanXuat() %>" name= "ngaysanxuat_<%=e.getId() %>" readonly class="days" onchange="changesolo();"  <%=readonly %>>
           	 				</td>
           	 				 <td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= sp.getNgayhethan() %>" name= "ngayhethan_<%=e.getId() %>" readonly class="days" >
           	 				</td>
           	 				
           	 			</tr>
           	 		<% }
	              	 } 
                		spList.clear(); 
                	e.DBclose();	
                	} %>
                   
            </TABLE> 
        </div>      
    
    
    
     </fieldset>	
    </div>
</div>
</form>
<%	
try{
	if(rsbtp!=null){
		rsbtp.close();
	}
	if(rsthupham!=null){
		rsthupham.close();
	}
	
}catch(Exception er){
	
}finally{
	
	nkBean.shutdown();	
	session.setAttribute("nkBean", null) ; 
}

%>
</BODY>
</html>