<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.*" %>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<% IErpXoakhachhangtt tthdBean = (IErpXoakhachhangtt)session.getAttribute("tthdBean"); %>
<% ResultSet nppList = tthdBean.getNppRs(); %>
<% ResultSet tienteList = tthdBean.getTienteRs(); %>
<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>
<% List<IHoadon> ctttList = tthdBean.getCtttList(); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet nvtutList = tthdBean.getNvtuRs();%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>

<script src="../scripts/Numberformat.js" type="text/javascript"></script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-chiphi.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


<script language="javascript" type="text/javascript">
	function replaces(stt)
	{
		 var trahet = document.getElementById("trahet" + stt);
		 var avat = document.getElementById("avat" + stt);
		 var conlai = document.getElementById("conlai" + stt);
		 var thanhtoan = document.getElementById("thanhtoan" + stt);
		 
		 var sotienungtruoc = document.getElementById("sotienungtruoc").value.replace(/,/g,"");
		 if(sotienungtruoc == '')
			 sotienungtruoc = '0';
		
		 var tienthanhtoan = thanhtoan.value.replace(/,/g,"");
		 if(tienthanhtoan == '')
			 tienthanhtoan = '0';
		 
		 
		 thanhtoan.value = DinhDangTien(tienthanhtoan);	
		 tienAvat = avat.value.replace(/,/g,"");
		 
		 if(parseFloat(tienthanhtoan) >= parseFloat(tienAvat))
		 {
			 thanhtoan.value = DinhDangTien(tienAvat);
			 conlai.value = "0";
			 
			 var ttThanhtoan = TongTienThanhToan();
			 if(parseFloat(sotienungtruoc) < parseFloat(ttThanhtoan) )
			 {
				thanhtoan.value = 0;
				conlai.value = DinhDangTien(parseFloat(tienAvat));
				
				alert('Tổng tiền thanh toán (' + DinhDangTien(ttThanhtoan) + ') không được phép vượt quá tổng số tiền trả trước (' + DinhDangTien(sotienungtruoc) + ') ');
				return;
			 }
		 
			
			 trahet.checked = true;
		 }
		 else
		 {
			 conlai.value = DinhDangTien(parseFloat(tienAvat) - parseFloat(tienthanhtoan));
			 
			 var ttThanhtoan = TongTienThanhToan();
			 if(parseFloat(sotienungtruoc) < parseFloat(ttThanhtoan) )
			 {
				thanhtoan.value = 0;
				conlai.value = DinhDangTien(parseFloat(tienAvat));
				
				alert('Tổng tiền thanh toán (' + DinhDangTien(ttThanhtoan) + ') không được phép vượt quá tổng số tiền trả trước (' + DinhDangTien(sotienungtruoc) + ') ');
				return;
			 }
			 
			 trahet.checked = false;
		 }
				
		 //document.getElementById("sotienungtruoc").value = DinhDangTien(TongTienThanhToan());
	}
	
	function TraTienCT(stt)
	{
		 var trahet = document.getElementById("ctttIds" + stt);
		 var avat = document.getElementById("tienchungtu" + stt);
		 var conlai = document.getElementById("tienconlai" + stt);
		 var thanhtoan = document.getElementById("tienthanhtoan" + stt);
		 var tg = document.getElementById("tigiachungtu" + stt).value;
		
		 
		 var tongcong = 0;
		 var tongcongVND = 0;
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var tienthanhtoan = thanhtoan.value.replace(/,/g,"");
		 if(tienthanhtoan == '')
			 tienthanhtoan = '0';
		 
		 thanhtoan.value = DinhDangTien(tienthanhtoan);
		 tienAvat = avat.value.replace(/,/g,"");
		 
		 if(parseFloat(tienthanhtoan) >= parseFloat(tienAvat))
		 {
			if(tienteId == "100000")
			{
				 thanhtoan.value = DinhDangTien(tienAvat);
				 conlai.value = "0";
				 
				 trahet.checked = true;
				 tongcong =  parseFloat(tongcong) + parseFloat(tienAvat);
			}else
			{
				 thanhtoan.value = DinhDangDonGia( parseFloat(tienAvat).toFixed(2));
				 conlai.value = "0";
				 
				 trahet.checked = true;
				 tongcong =  parseFloat(tongcong) + parseFloat(tienAvat);
				 
				 tongcongVND = tongcongVND + parseFloat(tienAvat)*parseFloat(tg);
			}
			
		 }
		 else
		 {
			if(tienteId == "100000")
			{
				 conlai.value = DinhDangTien(parseFloat(tienAvat) - parseFloat(tienthanhtoan));
				 trahet.checked = false;
				 tongcong =  parseFloat(tongcong) + parseFloat(tienthanhtoan);
			}
			else
			{
				 conlai.value = DinhDangDonGia((parseFloat(tienAvat) - parseFloat(tienthanhtoan)).toFixed(2));
				 trahet.checked = false;
				 tongcong =  parseFloat(tongcong) + parseFloat(tienthanhtoan);
				 
				 tongcongVND = tongcongVND + parseFloat(tienthanhtoan)*parseFloat(tg);
			}
		 }
		 
		 var sotienungtruoc = document.getElementsByName("sotienungtruoc");
		 var sotienungtruocVND = document.getElementsByName("sotienungtruocVND");
		
		 
		if(tienteId == "100000")
		{
		     sotienungtruoc.item(0).value = DinhDangTien(tongcong);
		}
		else
		{
			 sotienungtruoc.item(0).value = DinhDangDonGia(tongcong.toFixed(2));
			 sotienungtruocVND.item(0).value = DinhDangTien(roundNumber(tongcongVND,0));
		}	
		// ThanhToan(100);
		PhanBoTien();
	}
	
	function TraTienHD(stt)
	{
		 var trahet = document.getElementById("trahet" + stt);
		 var avat = document.getElementById("avat" + stt);
		 var conlai = document.getElementById("conlai" + stt);
		 var thanhtoan = document.getElementById("thanhtoan" + stt);
		// var tg = document.getElementById("tigiachungtu" + stt).value;
		
		 
		 var tongcong = 0;
		 var tongcongVND = 0;
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var tienthanhtoan = thanhtoan.value.replace(/,/g,"");
		// if(tienthanhtoan == '')
		//	 tienthanhtoan = '0';
		 
		 thanhtoan.value = DinhDangTien(tienthanhtoan);
		 tienAvat = avat.value.replace(/,/g,"");
		 
		 if(parseFloat(tienthanhtoan) >= Math.abs(parseFloat(tienAvat)))
		 {
			if(tienteId == "100000")
			{
				 thanhtoan.value = DinhDangTien(tienAvat);
				 conlai.value = "0";
				 
				 trahet.checked = true;
				 tongcong = tongcong + parseFloat(tienAvat);
			}else
			{
				 thanhtoan.value = DinhDangDonGia( parseFloat(tienAvat).toFixed(2));
				 conlai.value = "0";
				 
				 trahet.checked = true;
				 tongcong = tongcong + parseFloat(tienAvat);
				 
				 tongcongVND = tongcongVND + parseFloat(tienAvat); //*parseFloat(tg);
			}
			
		 }
		 else
		 {
			if(tienteId == "100000")
			{
				 conlai.value = DinhDangTien(parseFloat(tienAvat) - parseFloat(tienthanhtoan));
				 trahet.checked = false;
				 tongcong = tongcong + parseFloat(tienthanhtoan);
			}
			else
			{
				 conlai.value = DinhDangDonGia((parseFloat(tienAvat) - parseFloat(tienthanhtoan)).toFixed(2));
				 trahet.checked = false;
				 tongcong = tongcong + parseFloat(tienthanhtoan);
				 
				 tongcongVND = tongcongVND + parseFloat(tienthanhtoan); //*parseFloat(tg);
			}
		 }
		 
		 var tongtienthanhtoan = document.getElementsByName("tongthanhtoan");
		 var tongtienthanhtoanVND = document.getElementsByName("tongthanhtoanVND");
		 
		if(tienteId == "100000")
		{
			tongtienthanhtoan.item(0).value = DinhDangTien(tongcong);
		}
		else
		{
			tongtienthanhtoan.item(0).value = DinhDangDonGia(tongcong.toFixed(2));
			tongtienthanhtoanVND.item(0).value = DinhDangTien(roundNumber(tongcongVND,0));
		}	
		 ThanhToan(100);
	}
	
	
	function TongTienThanhToan()
	{
		var tienthanhtoan = document.getElementsByName("thanhtoan");
		var tongthanhtoan = 0;
		for(k = 0; k < tienthanhtoan.length; k++)
		{
			if(tienthanhtoan.item(k).value != "")
			{
				var ttt = tienthanhtoan.item(k).value.replace(/,/g,""); 
				tongthanhtoan = parseFloat(ttt) + parseFloat(tongthanhtoan);
			}
		}
		return tongthanhtoan;
	}
	
	function DinhDangTienTT()
	{
		var giatrinhap = document.getElementById("sotienungtruoc");
		giatrinhap.value = DinhDangTien(giatrinhap.value);
		
		PhanBoTien();
	}
	
	function PhanBoTien()
	{	
		 var kyhieuhd = document.getElementsByName("kyhieuhd");
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 var tigiahd = document.getElementsByName("tigiaHD");
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var tongtienaVat = 0;
		 
		 for(j = 0; j < kyhieuhd.length; j++)
		 {
			 thanhtoan.item(j).value = "0";
			 conlai.item(j).value = avat.item(j).value;
			 trahet.item(j).checked = false;
			 tongtienaVat = tongtienaVat + parseFloat(avat.item(j).value.replace(/,/g,""));
		 }
		 
		 var sotienphanbo = document.getElementById("sotienungtruoc").value.replace(/,/g,"");
		 var sotienphanboVND = document.getElementById("sotienungtruocVND").value.replace(/,/g,"");
		 
		
		 //alert(sotienphanbo);

		 var tongtien = 0;
		 var tongtienVND = 0;
		 var tongthanhtoan = 0;
		 var tongthanhtoanVND = 0;
		 
		 for(i = 0; i < kyhieuhd.length; i++)
		 {
			tienAvat =  avat.item(i).value.replace(/,/g, "");
			tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
			
			var tg = tigiahd.item(i).value.replace(/,/g, "");
			
			if(tongtien < parseFloat(sotienphanbo))
			{
				if(tienteId == "100000")
				{	
					thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
					tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
					conlai.item(i).value = 0;
					trahet.item(i).checked = true;
					
				}else
				{
					thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienAvat).toFixed(2));
					tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
					tongthanhtoanVND = tongthanhtoanVND + (parseFloat(tienAvat)*parseFloat(tg)) ;
					conlai.item(i).value = 0;
					trahet.item(i).checked = true;
				}	
			}
			else
			{
				tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
				var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
				
				if(tienteId == "100000")
				{
					thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
					tongthanhtoan = tongthanhtoan + tienconlai;
					
					conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
				}else
				{
					thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienconlai).toFixed(2));
					tongthanhtoan = tongthanhtoan + tienconlai;
					tongthanhtoanVND = tongthanhtoanVND + parseFloat(tienconlai)*parseFloat(tg) ;
					
					conlai.item(i).value = DinhDangDonGia((parseFloat(tienAvat) - parseFloat(tienconlai)).toFixed(2));
				}
			
				
				if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
					trahet.item(i).checked = true;
				else
					trahet.item(i).checked = false;
				
				break;
			}			
		 }
		 
		 var tongtt = document.getElementsByName("tongthanhtoan");
		 var tongttVND = document.getElementsByName("tongthanhtoanVND");
		 
		 var chenhlech = document.getElementsByName("chenhlech");
		 var chenhlechVND = document.getElementsByName("chenhlechVND");
		 
		 if(tienteId == "100000")
		 {
		    tongtt.item(0).value = DinhDangTien(roundNumber(tongthanhtoan, 0));
		    var cl = parseFloat(sotienphanbo) - tongthanhtoan;
			chenhlech.item(0).value = DinhDangTien(roundNumber(cl, 0));
            
			 
		 }else
		 {
			 tongtt.item(0).value = DinhDangDonGia(parseFloat(tongthanhtoan).toFixed(2));
			 tongttVND.item(0).value = DinhDangTien(roundNumber(tongthanhtoanVND, 0));
			
			 var cl = parseFloat(sotienphanbo) - tongthanhtoan;
			 chenhlech.item(0).value = DinhDangDonGia((cl).toFixed(2));

			 var clVND= parseFloat(sotienphanboVND) - parseFloat(tongthanhtoanVND);
			
			 chenhlechVND.item(0).value = DinhDangTien(roundNumber(clVND, 0));
		 }
		 		 
	}

	
	function TongTienTT()
	{
		var tienthanhtoan = document.getElementsByName("tienthanhtoan");
		var tongtien = 0;
		for(j = 0; j < tienthanhtoan.length; j++)
		{
			if(tienthanhtoan.item(j).value != '')
			{
				var tt = tienthanhtoan.item(j).value.replace(/,/g,"");
				tongtien = parseFloat(tongtien) + parseFloat(tt);
			}
		}

		document.getElementById("sotienungtruoc").value = DinhDangTien(roundNumber(tongtien, 0));
		
		PhanBoTien();
	}
	
	function TraHetCTTT(n)
	 {
		 var ctttIds = document.getElementsByName("ctttIds");
		 var tienchungtu = document.getElementsByName("tienchungtu");
		 var tienthanhtoan = document.getElementsByName("tienthanhtoan");
		 var tienconlai = document.getElementsByName("tienconlai");
		 var tg = document.getElementsByName("tigiachungtu");		
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var tongtien = 0;
		 var tongtienVND = 0;
		 
		 // Lấy tông tien hd
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 		 
		 var tongtienhd = 0;
		
		 for(j = 0; j < trahet.length; j++)
		 {			
			 tongtienhd += parseFloat( avat.item(j).value.replace(/,/g,""));
		 }	
		
		 
		 for(i = 0; i < ctttIds.length; i++)
		 {
			if(ctttIds.item(i).checked)
			{
					tienthanhtoan.item(i).value = tienchungtu.item(i).value;
					tienconlai.item(i).value = '0';
					
					var tt = tienchungtu.item(i).value.replace(/,/g,"");
					
					if(tienteId == "100000") // TIỀN VIỆT
					{
					  tongtien = parseFloat(tongtien) + parseFloat(tt);
					  
					  if(parseFloat(tongtien) > parseFloat(tongtienhd))
					  {
						  alert('Tổng tiền thanh toán của chứng từ (' + DinhDangTien(roundNumber(tongtien,0)) + ') không được phép vượt quá tổng số tiền hóa đơn (' + DinhDangTien(roundNumber(tongtienhd,0)) + ') ');
						  tienthanhtoan.item(i).value = 0;
						  tienconlai.item(i).value = tienchungtu.item(i).value;
						  ctttIds.item(i).checked = false;
						  tongtien = tongtien - parseFloat(tienchungtu.item(i).value.replace(/,/g,""));						  
						  
					  }   
						  
					}else
					{
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						
						if(parseFloat(tongtien) > parseFloat(tongtienhd))
						{
							  alert('Tổng tiền thanh toán của chứng từ (' + DinhDangTien(roundNumber(tongtien,0)) + ') không được phép vượt quá tổng số tiền hóa đơn (' + DinhDangTien(roundNumber(tongtienhd,0)) + ') ');
							  tienthanhtoan.item(i).value = 0;
							  tienconlai.item(i).value = tienchungtu.item(i).value;
							  ctttIds.item(i).checked = false;
							  tongtien = tongtien - parseFloat(tienchungtu.item(i).value.replace(/,/g,""));						  
							  
						}  
						
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)* parseFloat(tg.item(i).value);
					}	
	

			}else{
				if(n == i){
					tienthanhtoan.item(i).value = '0';
					tienconlai.item(i).value = tienchungtu.item(i).value;
				}else{
					var tt = tienthanhtoan.item(i).value.replace(/,/g,"");
					if(tt == '') tt = '0';
					
					if(tienteId == "100000") // TIỀN VIỆT
					{
					  tongtien = parseFloat(tongtien) + parseFloat(tt);
					}else
					{
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)* parseFloat(tg.item(i).value);
					}
					
				}
			}
		 }
		 
		 if(tienteId == "100000") // TIỀN VIỆT
		 {
		   document.getElementById("sotienungtruoc").value = DinhDangTien(roundNumber(tongtien, 0));
		 }else
		 {
			 document.getElementById("sotienungtruoc").value = DinhDangDonGia(parseFloat(tongtien).toFixed(2));
			 document.getElementById("sotienungtruocVND").value = DinhDangTien(roundNumber(tongtienVND, 0));
		 }	 
		 
		 PhanBoTien();
	  }
	
	function TraHetHD(n)
	 {
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 var tg = document.getElementsByName("tigiachungtu");		
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var tongtien = 0;
		 var tongtienVND = 0;
		 
		 for(i = 0; i < trahet.length; i++)
		 {
			if(trahet.item(i).checked)
			{
					thanhtoan.item(i).value = avat.item(i).value;
					conlai.item(i).value = '0';
					
					var tt = avat.item(i).value.replace(/,/g,"");
					
					if(tienteId == "100000") // TIỀN VIỆT
					{
					  tongtien = parseFloat(tongtien) + parseFloat(tt);
					}else
					{
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)* parseFloat(tg.item(i).value);
					}	
	

			}else{
				if(n == i){
					thanhtoan.item(i).value = '0';
					conlai.item(i).value = avat.item(i).value;
				}else{
					var tt = thanhtoan.item(i).value.replace(/,/g,"");
					if(tt == '') tt = '0';
					
					if(tienteId == "100000") // TIỀN VIỆT
					{
					  tongtien = parseFloat(tongtien) + parseFloat(tt);
					}else
					{
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + parseFloat(tt)* parseFloat(tg.item(i).value);
					}
					
				}
			}
		 }
		 
		 if(tienteId == "100000") // TIỀN VIỆT
		 {
		   document.getElementById("tongthanhtoan").value = DinhDangTien(roundNumber(tongtien, 0));
		 }else
		 {
			 document.getElementById("tongthanhtoan").value = DinhDangDonGia(parseFloat(tongtien).toFixed(2));
			 document.getElementById("tongthanhtoanVND").value = DinhDangTien(roundNumber(tongtienVND, 0));
		 }	 
		 
		 PhanBoTienCTTT();
	  }
	
	 function ThanhToan(n)
	 {
		 var sotienungtruoc = document.getElementById("sotienungtruoc").value.replace(/,/g,"");
		 if(sotienungtruoc == '')
			 sotienungtruoc = '0';
		 
		 var sotienungtruocVND = document.getElementById("sotienungtruocVND").value.replace(/,/g,"");
		 if(sotienungtruocVND == '')
			 sotienungtruocVND = '0';
		 
		 var tongthanhtoan = document.getElementById("tongthanhtoan").value.replace(/,/g,"");
		 if(tongthanhtoan == '')
			 tongthanhtoan = '0';

		 var chenhlech = document.getElementById("chenhlech").value.replace(/,/g,"");
		 if(chenhlech == '')
			 chenhlech = '0';
		 		 

		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 var tigiahd = document.getElementsByName("tigiaHD");
		 
		 var tienteId = document.getElementById("tienteId").value;
		 
		 var msg = document.getElementById("msg").value;
		 		 
		 var tongtien = 0;
		 var tongtienVND = 0;
		 var tongcttt = 0;
		 var tongctttVND = 0;
		 
		 for(i = 0; i < trahet.length; i++)
		 {
			var tgHd = tigiahd.item(i).value.replace(/,/g,"");
			if(trahet.item(i).checked) // tuong hop click chon tra het
			{
				if(n == 100){  // tuong hop click chon tra het nhung thay doi thanh toan
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
						 
					}else{
						tt = 0;
					}					

					if((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
						
						if(tienteId == "100000") // TIỀN VIỆT
						{
							conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));						
							tongtien = parseFloat(tongtien) + parseFloat(tt);	
							tongcttt = parseFloat(tongcttt) + parseFloat(tt);
							
							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));	
							trahet.item(i).checked = false;	
						}else  // NGOẠI TỆ
						{
							
							conlai.item(i).value = DinhDangDonGia((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));						
							tongtien = parseFloat(tongtien) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tgHd));
							tongctttVND = parseFloat(tongctttVND) + (parseFloat(tt)*parseFloat(tgHd));
							
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
							trahet.item(i).checked = false;
						}
						
					}else{
						
						if(tienteId == "100000")
						{
							thanhtoan.item(i).value = avat.item(i).value;
							conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");
							tongtien = parseFloat(tongtien) + parseFloat(tt);	
							tongcttt = parseFloat(tongcttt) + parseFloat(tt);
							
							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));	
							trahet.item(i).checked = true;
						}else
						{
						
							thanhtoan.item(i).value = avat.item(i).value;
							conlai.item(i).value = DinhDangDonGia((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");
							tongtien = parseFloat(tongtien) + parseFloat(tt);	
							tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tgHd));
							tongctttVND = parseFloat(tongctttVND) + (parseFloat(tt)*parseFloat(tgHd));
							
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));	
							trahet.item(i).checked = true;
						}
						
					}			
					
						
					
				}else{	
					
					thanhtoan.item(i).value = avat.item(i).value;
					
					var tt = thanhtoan.item(i).value.replace(/,/g,"");
				
					
					if(tienteId == "100000")
					{
						thanhtoan.item(i).value = DinhDangTien(tt) ;
						conlai.item(i).value = DinhDangTien(roundNumber( parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value),0)) ;
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongcttt = parseFloat(tongcttt) + parseFloat(tt);
					}
					else
					{
						thanhtoan.item(i).value = DinhDangDonGia( parseFloat(tt).toFixed(2) ) ;
						conlai.item(i).value = DinhDangDonGia(( parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2)) ;
					    
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tgHd));
						tongctttVND = parseFloat(tongctttVND) + (parseFloat(tt)*parseFloat(tgHd));
					}
					
				}					
			}else if(i == n){ // truong hop bo chon tra het
				
				thanhtoan.item(i).value = "0";				
				conlai.item(i).value = avat.item(i).value;
				
			}else{
			
				var tt;
				if(thanhtoan.item(i).value != ''){
					tt = thanhtoan.item(i).value.replace(/,/g,"");
					 
				}else{
					tt = 0;
				}					
				
				 if((Math.abs(parseFloat(avat.item(i).value.replace(/,/g,""))) - parseFloat(tt)) >= 0){
					
					if(tienteId == "100000" )
					{
						conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));
						
						tongtien = parseFloat(tongtien) + parseFloat(tt);	
						tongcttt = parseFloat(tongcttt) + parseFloat(tt);
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
					}else
					{												
						conlai.item(i).value = DinhDangDonGia((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));
						
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tgHd));
						tongctttVND = parseFloat(tongctttVND) + (parseFloat(tt)*parseFloat(tgHd));
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
					}
					
				}else{
					if(tienteId == "100000") // TIỀN VIỆT
					{
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongcttt = parseFloat(tongcttt) + parseFloat(tt);
						trahet.item(i).checked = true;	
					}else
					{
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = DinhDangDonGia((parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value)).toFixed(2));
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");
						tongtien = parseFloat(tongtien) + parseFloat(tt);
						tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tgHd));
						tongctttVND = parseFloat(tongctttVND) + (parseFloat(tt)*parseFloat(tgHd));
						
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
						trahet.item(i).checked = true;	
					}	
						
				}			 
			}
		 }
		 
		 
			if(tienteId == "100000")
			{
			 document.getElementById("sotienungtruoc").value = DinhDangTien(roundNumber(tongcttt, 0));
			 document.getElementById("tongthanhtoan").value = DinhDangTien(roundNumber(tongtien, 0));
			 document.getElementById("chenhlech").value = DinhDangTien(roundNumber(parseFloat(tongcttt) - tongtien, 0));
			}		 
			else
			{
				document.getElementById("sotienungtruoc").value = DinhDangDonGia(tongcttt.toFixed(2));
				document.getElementById("tongthanhtoan").value = DinhDangDonGia(tongtien.toFixed(2));
				document.getElementById("chenhlech").value = DinhDangDonGia((parseFloat(tongcttt) - tongtien).toFixed(2));
				 
				document.getElementById("sotienungtruocVND").value = DinhDangTien(roundNumber(tongctttVND, 0));
				document.getElementById("tongthanhtoanVND").value = DinhDangTien(roundNumber(tongtienVND, 0));
				document.getElementById("chenhlechVND").value = DinhDangTien(roundNumber(parseFloat(tongctttVND) - tongtienVND, 0)); 
			}
		
			PhanBoTienCTTT();
	 }
	 
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46|| keypressed == 45)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var ngayghinhan = document.getElementById("ngaychungtu");
		 if(ngayghinhan.value == "")
		 {
			alert("Vui lòng nhập ngày chứng từ");
			return;
		 }
		 
		 var sotienungtruoc = document.getElementById("sotienungtruoc");
		 if(sotienungtruoc.value == "")
		 {
			alert("Vui lòng nhập số tiền thu của khách hàng");
			return;
		 }
		 

		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tthdForm'].action.value='save';
	     document.forms['tthdForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 function nextform()
	 { 
		 document.forms['tthdForm'].action.value='next';
	     document.forms["tthdForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['tthdForm'].action.value='changePO';
	     document.forms["tthdForm"].submit();
	 }
	 
	  function DinhDangDonGia(num) 
		{
			num = num.toString().replace(/\$|\,/g,'');
				
			//num = (Math.round( num * 1000 ) / 1000).toString();
				
			var sole = '';
			if(num.indexOf(".") >= 0)
			{
				sole = num.substring(num.indexOf('.'));
			}
				
			if(isNaN(num))
				num = "0";
			sign = (num == (num = Math.abs(num)));
			num = Math.floor(num*100);
			num = Math.floor(num/100).toString();
			for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
				num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
		
			var kq;
			if(sole.length >= 0)
			{
				kq = (((sign)?'':'-') + num) + sole;
			}
			else
				kq = (((sign)?'':'-') + num);
				
				//alert(kq);
			return kq;
		}
	  
	  function goBack()
		 {
		  	window.history.back();
		 }
	  
	  function sellectAll()
		 {
			 var chkAll = document.getElementById("chkAll");
			 var trahet = document.getElementsByName("trahet");
			 
			 // Hóa đơn
			 var avat = document.getElementsByName("avat");
			 var thanhtoan = document.getElementsByName("thanhtoan");
			 var conlai = document.getElementsByName("conlai");
			 
			 var totalTT = '0';
			 
			 if(chkAll.checked)
			 {
				 for(i = 0; i < trahet.length; i++)
				 {
					 trahet.item(i).checked = true;
					 thanhtoan.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,"") ,0));
					 conlai.item(i).value = '0';
					 
					 totalTT = parseFloat(totalTT) + parseFloat(thanhtoan.item(i).value.replace(/,/g,"")) ;
				 }
			 }
			 else
			 {
				 for(i = 0; i < trahet.length; i++)
				 {
					 trahet.item(i).checked = false;
					 thanhtoan.item(i).value = '0';
					 conlai.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,"") ,0));
					 					 
					 totalTT = parseFloat(totalTT) + parseFloat(thanhtoan.item(i).value.replace(/,/g,"")) ;
				 }
			 }
			 
	
			 CheckTienHD_TienCT(totalTT); 
			 			 			 
		 }
	  
	  function CheckTienHD_TienCT(totalTT)
	  {		 
		    var tigiahd = document.getElementsByName("tigiaHD");			 
			var tienteId = document.getElementById("tienteId").value;
			
			// 
			var tongthanhtoan = document.getElementById("tongthanhtoan");
		  
		  // Hóa đơn
		  	 var trahet = document.getElementsByName("trahet");
			 var avat = document.getElementsByName("avat");
			 var thanhtoan = document.getElementsByName("thanhtoan");
			 var conlai = document.getElementsByName("conlai");
			 
		  // CHỨNG TỪ TRẢ TRƯỚC
			 var ctttIds = document.getElementsByName("ctttIds");
			 var tienchungtu = document.getElementsByName("tienchungtu"); 
			 
			 var totalTCT = '0';
			 
			 for( var j = 0; j < tienchungtu.length; j++)
			 {
				 totalTCT = parseFloat(totalTCT) + parseFloat(tienchungtu.item(j).value.replace(/,/g,""));
			 }
			 
			
			 // Kiêm tra Total Tiền hóa đơn && Total tiền chứng từ
			 
			 if(parseFloat(totalTT) - parseFloat(totalTCT) > 0)
			 {
				 document.getElementById("msg").value = "Tổng tiền hóa đơn không được phép lớn hơn tổng tiền chứng từ trả trước. Vui lòng chọn lại.";
				 alert(document.getElementById("msg").value);
				 document.getElementById("chkAll").checked = false;
				 
				 for(i = 0; i < trahet.length; i++)
				 {
					 trahet.item(i).checked = false;
					 thanhtoan.item(i).value = '0';
					 conlai.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,"") ,0));
				 }				 
				 
				 tongthanhtoan.value = '0';

			 }else
			 {
				 if(tienteId == "100000")
				 { 
					 document.getElementById("tongthanhtoan").value = DinhDangTien(roundNumber(totalTT ,0)); 
				 }else
				 {
					 document.getElementById("tongthanhtoan").value = DinhDangDonGia(totalTT.toFixed(2)); 
					 document.getElementById("tongthanhtoanVND").value = DinhDangTien(roundNumber(totalTT*parseFloat(tg) ,0)); 
				 }
				
				 PhanBoTienCTTT();
			 }
	  }
	  
	  function PhanBoTienCTTT()
	  {	
		     var khchungtu = document.getElementsByName("khchungtu");
			 var trahet = document.getElementsByName("ctttIds");
			 var tienchungtu = document.getElementsByName("tienchungtu");
			 var tienthanhtoan = document.getElementsByName("tienthanhtoan");
			 var tienconlai = document.getElementsByName("tienconlai");
			 var tigiahd = document.getElementsByName("tigiachungtu");
			 
			 var tienteId = document.getElementById("tienteId").value;
			 
			 for(j = 0; j < khchungtu.length; j++)
			 {
				 tienthanhtoan.item(j).value = "0";
				 tienconlai.item(j).value = DinhDangTien(roundNumber(tienchungtu.item(j).value.replace(/,/g,"") ,0));
				 trahet.item(j).checked = false;
			
			 }
			 
			 var sotienphanbo = document.getElementById("tongthanhtoan").value.replace(/,/g,"");
			 var sotienphanboVND = document.getElementById("tongthanhtoanVND").value.replace(/,/g,"");
			 

			 var tongtien = 0;
			 var tongtienVND = 0;
			 var tongcttt = 0;
			 var tongctttVND = 0;
			 
			 for(i = 0; i < khchungtu.length; i++)
			 {
				tienAvat =  tienchungtu.item(i).value.replace(/,/g, "");
				tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
				
				var tg = tigiahd.item(i).value.replace(/,/g, "");
								
				if(tongtien < Math.abs(parseFloat(sotienphanbo)))
				{
					if(tienteId == "100000")
					{
						tienthanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
						tongcttt = tongcttt + parseFloat(tienAvat);
						tienconlai.item(i).value = 0;
						trahet.item(i).checked = true;
					}else
					{
						tienthanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienAvat).toFixed(2));
						tongcttt = tongcttt + parseFloat(tienAvat);
						tongctttVND = tongctttVND + (parseFloat(tienAvat)*parseFloat(tg)) ;
						tienconlai.item(i).value = 0;
						trahet.item(i).checked = true;
					}	
				}
				else
				{
					tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
					var tienconlai_ = parseFloat(sotienphanbo) - parseFloat(tongtien);
					
					if(tienteId == "100000")
					{
						tienthanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai_, 0));
						tongcttt = tongcttt + tienconlai_;
						
						tienconlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai_), 0));
					}else
					{
						tienthanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienconlai_).toFixed(2));
						tongcttt = tongcttt + tiencosnlai_;
						tongctttVND = tongctttVND + parseFloat(tienconlai_)*parseFloat(tg) ;
						
						tienconlai.item(i).value = DinhDangDonGia((parseFloat(tienAvat) - parseFloat(tienconlai_)).toFixed(2));
					}
				
					
					if(parseFloat(tienAvat) - Math.abs(parseFloat(tienconlai_)) <= 0)
						trahet.item(i).checked = true;
					else
						trahet.item(i).checked = false;
					
					break;
				}			
			 }
			 
			 var tongtiencttt = document.getElementsByName("sotienungtruoc");
			 var tongtienctttVND = document.getElementsByName("sotienungtruocVND");
			 
			 var chenhlech = document.getElementsByName("chenhlech");
			 var chenhlechVND = document.getElementsByName("chenhlechVND");
			 
			 if(tienteId == "100000")
			 {
				 var cl= parseFloat(sotienphanbo) - parseFloat(tongcttt);
				 tongtiencttt.item(0).value = DinhDangTien(roundNumber(tongcttt, 0));
			     chenhlech.item(0).value = DinhDangTien(roundNumber(cl, 0));
				 
			 }else
			 {
				 tongtiencttt.item(0).value = DinhDangDonGia(parseFloat(tongcttt).toFixed(2));
				 tongtienctttVND.item(0).value = DinhDangTien(roundNumber(tongctttVND, 0));
				
				 var cl= (parseFloat(sotienphanbo) - tongcttt);
				 chenhlech.item(0).value = DinhDangDonGia(cl.toFixed(2));

				 var clVND= parseFloat(sotienphanboVND) - parseFloat(tongctttVND);
				
				 chenhlechVND.item(0).value = DinhDangTien(roundNumber(clVND, 0));
			 }
			 
		  
	  }
	  function goBack()
		 {
		  	window.history.back();
		 }
	  
</script>


<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpXoakhachhangttUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="tungaytk" value='<%= tthdBean.getTungaytk()%>'>
<input type="hidden" name="denngaytk" value='<%= tthdBean.getDenngaytk()%>'>
<input type="hidden" name="maphieutk" value='<%= tthdBean.getmaphieutk()%>'>
<input type="hidden" name="khachhangtk" value='<%= tthdBean.getkhachhangtk()%>'>
<input type="hidden" name="kenhbanhangtk" value='<%= tthdBean.getkenhbanhangtk()%>'>
<input type="hidden" name="sotientk" value='<%= tthdBean.getsotientk()%>'>
<input type="hidden" name="trangthaitk" value='<%= tthdBean.gettrangthaitk()%>'>
<input type="hidden" name="nhomkhtk" value='<%= tthdBean.getnhomkhachhangtk()%>'>
<input type="hidden" id="msg" value=''>
 
 <TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải thu > Xóa nợ khách hàng > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
					
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>

		<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Thông báo </LEGEND>
				
    				<textarea name="dataerror"  style="width: 100% ;  font-weight:bold"  style="width: 100% ; color:#ffffff ; font-weight:bold" 
    				readonly="readonly" rows="1"><%= tthdBean.getMsg() %></textarea>
					</FIELDSET>
			   </TD>
			</TR>			
			<TR>
			  <TD height="100%" width="100%">

	    <fieldset>
    	<legend class="legendtitle"> Xóa nợ khách hàng </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = 0>							
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Ngày chứng từ</TD>
                    <TD class="plainlabel" width="300px" valign="top">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" value="<%= tthdBean.getNgaychungtu() %>" 
                    		maxlength="10" onChange = "submitform();" readonly /></TD>
                    <TD width="140px" class="plainlabel" valign="top">Ngày ghi sổ</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngayghiso" name="ngayghiso" value="<%= tthdBean.getNgayghiso() %>" 
                    		maxlength="10" readonly /></TD>
                </TR> 
                 <%-- <TR>
                        <TD class="plainlabel" width="15%">Lọc Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= tthdBean.getTungay() %>" maxlength="10"  />
                        </TD>
                    
                        <TD class="plainlabel" width="15%" >Lọc Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= tthdBean.getDenngay() %>" maxlength="10"  />
                        </TD>
                </TR> --%>
                    
                <TR>
                	<TD class="plainlabel">Loại </TD>
                    <TD  class="plainlabel">
                        <select class="plainlabel" id="loaiId" name="loaiId" >
                          <%if(tthdBean.getLoaixnId().equals("0")) {%>
                        	<option value="0" selected="selected">Xóa nợ KH trả trước</option>
                        	<option value="1">Xóa tạm ứng</option>
                        	<%}else{ %>
                        	<option value="0" >Xóa nợ KH trả trước</option>
                        	<option value="1" selected="selected">Xóa tạm ứng</option>
                        	<%} %>
                        </select>
                     </TD> 
                    <TD class="plainlabel">Ghi chú </TD>
                    <TD  class="plainlabel">
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>"> 
                     </TD> 
                </TR>
                
                
<%--                 <%if(tthdBean.getLoaixnId().equals("0")) { // XÓA NỢ KHÁCH HÀNG TRẢ TRƯỚC%>                --%>
<!--                 	<TR> -->
<!--                 	<TD class="plainlabel" valign="middle">Loại khách hàng </TD> -->
<!-- 	                        <TD class="plainlabel" valign="middle" colspan="3"> -->
<!-- 	                           <select name="trangthai" onChange="submitform();" style ="width:200px"> -->
<!-- 	                           		<option value=""> </option> -->
<%-- 									<% if (tthdBean.gettrangthaitk().equals("1")){%> --%>
<!-- 									  	<option value="1" selected>Đã chốt</option> -->
<!-- 									  	<option value="0">Chưa chốt</option> -->
<!-- 									  	<option value="2">Đã xóa</option> -->
<!-- 									  	<option value="4" >Đã xuất kho(chưa hoàn tất)</option>
<!-- 									  	<option value="5" >Hoàn tất xuất kho </option> -->
									  
<%-- 									<%}else if(tthdBean.gettrangthaitk().equals("0")) {%> --%>
<!-- 									 	<option value="0" selected>Chưa chốt</option> -->
<!-- 									  	<option value="1" >Đã chốt</option> -->
<!-- 									 	<option value="2" >Đã xóa</option> -->
									  	
<%-- 									<%}else if(tthdBean.gettrangthaitk().equals("2")){%>											 --%>
<!-- 									 	<option value="2" selected>Đã xóa</option>										  	 -->
<!-- 									  	<option value="0" >Chưa chốt</option> -->
<!-- 									  	<option value="1" >Đã chốt</option> -->
									  	  	
<%-- 									<%}else{%> --%>
<!-- 										<option value="0" >Chưa chốt</option> -->
<!-- 									  	<option value="1" >Đã chốt</option>											 -->
<!-- 									  	<option value="2" >Đã xóa</option> -->
<!-- 									  	<option value="4" >Đã xuất kho(chưa hoàn tất)</option>
<!-- 									  	<option value="5" >Hoàn tất xuất kho</option> --> 
<%-- 									<%} %> --%>
<!-- 	                           </select> -->
<!-- 	                        </TD>   -->
<!-- 	                 </TR> -->
<%--                 <%} %> --%>
                
                
                
                <%if(tthdBean.getLoaixnId().equals("1")){ %>
                 <TR>
                  <TD class="plainlabel">Đối tượng tạm ứng</TD>
                    <TD class="plainlabel" valign="top">
                    	 <select name="doituongtamung" id="doituongtamung" onChange="submitform();">
                        	
                        	<%
                        		System.out.println("Tam ung trang JSP : "+tthdBean.getDoiTuongTamUng());
                        			if(tthdBean.getDoiTuongTamUng().equals("1")){ %>
                        				<option value="1" selected="selected" >Nhà cung cấp</option>
                        				<option value="0"  >Nhân viên</option>
                        				
                        			<%}else if(tthdBean.getDoiTuongTamUng().equals("0")){ %>
                        				<option value="1"  >Nhà cung cấp</option>
                        				<option value="0" selected="selected" >Nhân viên</option>
                        		 <% } %>
                 						                       		
                        </select>
                    </TD>
                    <%if(tthdBean.getDoiTuongTamUng().equals("1")) { %>
                      <TD class="plainlabel">Nhà cung cấp</TD>
                       <TD class="plainlabel" >
		                  <select name="nccId" id="nccId" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(nccList != null)
                        		{
                        			try
                        			{
                        			while(nccList.next())
                        			{  
                        			if( nccList.getString("pk_seq").equals(tthdBean.getNccId())){ %>
                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("MA") %> - <%= nccList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("MA") %> - <%= nccList.getString("ten") %></option>
                        		 <% } } nccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
		               </TD> 		                    
                    <%}else{ %>
                      <TD class="plainlabel">Nhân viên</TD>
                       <TD class="plainlabel"   >
		                 <select name="nvtuId" id="nvtuId" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(nvtutList != null)
                        		{
                        			try
                        			{
                        			while(nvtutList.next())
                        			{  
                        			if( nvtutList.getString("pk_seq").equals(tthdBean.getNvtuId())){ %>
                        				<option value="<%= nvtutList.getString("pk_seq") %>" selected="selected" ><%= nvtutList.getString("MA") %> - <%= nvtutList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nvtutList.getString("pk_seq") %>" ><%= nvtutList.getString("MA") %> - <%= nvtutList.getString("ten") %></option>
                        		 <% } } nvtutList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>       	 
		               </TD> 
                    <%} %>
                    
                	
                </TR>  
                <TR>
                    <TD class="plainlabel">Tiền tệ</TD>
                    <TD class="plainlabel" style = "width:100px" colspan="3">
                    	
                        <select name="tienteId" id="tienteId" style = "width:150px" onChange = "submitform();">
                        		
                        	<%
                        		if(tienteList != null)
                        		{
                        			try
                        			{
                        			while(tienteList.next())
                        			{  
                        			if( tienteList.getString("pk_seq").equals(tthdBean.getTienteId())){ %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 <% } } tienteList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                
                </TR>
                <%}else{ %>
                <TR>
                    <TD class="plainlabel">Khách hàng</TD>                            		
                    <TD  class="plainlabel" >
                        <select name="nppId" id="nppId" style = "width:300px" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(nppList != null)
                        		{
                        			try
                        			{
                        			while(nppList.next())
                        			{  
                        			if( nppList.getString("pk_seq").equals(tthdBean.getNppId())){ %>
                        				<option value="<%= nppList.getString("pk_seq") %>" selected="selected" ><%= nppList.getString("MA") %> - <%= nppList.getString("nppTen") %></option>
                        			<%}else { %>
                        				<option value="<%= nppList.getString("pk_seq") %>" ><%= nppList.getString("MA") %> - <%= nppList.getString("nppTen") %></option>
                        		 <% } } nppList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                     
                    <TD class="plainlabel">Tiền tệ</TD>
                    <TD class="plainlabel" style = "width:100px">
                    	
                        <select name="tienteId" id="tienteId" style = "width:150px" onChange = "submitform();">
                        		
                        	<%
                        		if(tienteList != null)
                        		{
                        			try
                        			{
                        			while(tienteList.next())
                        			{  
                        			if( tienteList.getString("pk_seq").equals(tthdBean.getTienteId())){ %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 <% } } tienteList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                     
                </TR>
                <%} %>
            	<TR>
                 <TD class="plainlabel" >Chứng từ trả trước </TD>
			     <TD class="plainlabel" colspan="3">
			      <a href="" id="ctttId" rel="subcontentCttt">
                  &nbsp; <img alt="Chọn chứng từ trả trước" src="../images/vitriluu.png"></a>
             
                         <DIV id="subcontentCttt" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
                                background-color: white; width: 620px; max-height:300px; overflow:auto; padding: 4px;">
                       <table width="90%" align="center">
                           <tr>
                            <th width="100px" align="center" style="font-size: 12px" align="center">Loại chứng từ</th>
                               <th width="100px" style="font-size: 12px" align="center">Số chứng từ</th>
                               <th width="100px" style="font-size: 12px" align="center">Ngày chứng từ</th> 
                             <%if(tthdBean.getTienteId().equals("100000")){ %>
                              <th width="100px" style="font-size: 12px" align="center">Số tiền TT</th>                               
                             <%}else{ %>                              
                               <th width="100px" style="font-size: 12px" align="center">Số tiền TT</th>
                               <th width="100px" style="font-size: 12px" align="center">Số tiền TT (VND)</th>
                             <%} %>  
                               <th width="100px" style="font-size: 12px" align="center">Thanh toán</th>
                               <th width="100px" style="font-size: 12px" align="center">Còn lại</th>
                               <th width="100px" align="center" style="font-size: 12px" align="center">Trả hết</th>
                               
                           </tr>
						 <%
                            if(ctttList.size() > 0)
                            {
                             for(int i = 0; i <ctttList.size(); i++)
                             {
                              IHoadon hoadonTT = ctttList.get(i);
                            
                             %>
                             
                             <tr>
                              <td>
                               <input type="hidden" name="loaict" id = "loaict<%= i %>"  style="width: 100%"  value="<%= hoadonTT.getLoaict() %>" >
                               <%if(hoadonTT.getLoaict().equals("0")) { %>
                                <input type="text" style="width: 100%" value="Thu tiền"> 
                               <%} else if(hoadonTT.getLoaict().equals("3")) { %>
                               <input type="text" style="width: 100%" value="Chi phí khác"> 
                              <%}else if(hoadonTT.getLoaict().equals("12")) { %>
                              <input type="text" style="width: 100%" value="Đề nghị thanh toán"> 
                             <%}else{ %>
                                <input type="text" style="width: 100%" value="Bút toán tổng hợp"> 
                               <%} %>
                              </td>
                              <td>
                               <input type="hidden" name = "idchungtu" style="width: 100%" value="<%= hoadonTT.getId() %>">
                               <input type="hidden"  id = "tigiachungtu<%= i %>" name = "tigiachungtu" style="width: 100%" value="<%= hoadonTT.getTigiaHD() %>">
                               <input type="text" name = "khchungtu" style="width: 100%" value="<%= hoadonTT.getKyhieu() %>">
                              </td>
                              <td><input style="width: 100%"  value="<%= hoadonTT.getNgay() %>" name="ngaychungtu" ></td>
                              
                             <% if(tthdBean.getTienteId().equals("100000")){ %>
                              <td><input style="width: 100%; text-align: right;" name="tienchungtu" id="tienchungtu<%= i %>"
                                value="<%= hoadonTT.getTongtiencoVAT() %>" readonly="readonly"></td>
           				<%}else{ %>
            				<td>
            					<input style="width: 100%; text-align: right;" name="tienchungtu" id="tienchungtu<%= i %>" value="<%= hoadonTT.getTongtiencoVAT() %>" readonly="readonly">
            				</td>
                            <td><input style="width: 100%; text-align: right;" name="tienchungtuVND" id="tienchungtuVND<%= i %>"
                                value="<%= hoadonTT.getTongtiencoVATVND() %>" readonly="readonly"></td>
          				 <%} %>
 
                              <td ><input style="width: 100%; text-align: right;" name="tienthanhtoan" id="tienthanhtoan<%= i %>"  
                                onkeyup="TraTienCT(<%= i %>)" value="<%= hoadonTT.getThanhtoan() %>" ></td>
  
                              <td ><input style="width: 100%; text-align: right;" name="tienconlai" id="tienconlai<%= i %>" readonly="readonly"
                                value="<%= hoadonTT.getConlai() %>" ></td>
                              <td align="center">
                              
                              <% if( hoadonTT.getConlai().equals("0") ){ %>
                               <input type="checkbox" name="ctttIds" id="ctttIds<%= i %>" checked="checked" onchange="TraHetCTTT(<%= i %>)" >
                              <%}else{ %>
                               <input type="checkbox" name="ctttIds" id="ctttIds<%= i %>" onchange="TraHetCTTT(<%= i %>)">
                              <%} %>
                              </td>
                             </tr>
                             
                             <% } }  %>
                       </table>
                        <div align="right">
                         <label style="color: red" ></label>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <a href="javascript:dropdowncontent.hidediv('subcontentCttt')" >Hoàn tất</a>
                        </div>
               </DIV>         
		     </TD>
		 </TR >
                
            	<TR>
                    <TD class="plainlabel">Tổng tiền ứng trước </TD>
                    <TD class="plainlabel">
                        <input type="text" style="text-align: right;" name="sotienungtruoc" id = "sotienungtruoc" 
                        		value="<%= tthdBean.getSotientt() %>"  readonly="readonly" > 
                     </TD> 
                <%if(!tthdBean.getTienteId().equals("100000")){ // NGOẠI TỆ%>
                	<TD class="plainlabel">Tổng tiền ứng trước (VNĐ)</TD>
                    <TD class="plainlabel">
                        <input type="text" style="text-align: right;" name="sotienungtruocVND" id = "sotienungtruocVND" 
                        		value="<%= tthdBean.getSotienttVND() %>"  readonly="readonly" > 
                     </TD> 
                <%} else {%>
                 	<TD class="plainlabel" colspan="2">
                        <input type="hidden" style="text-align: right;" name="sotienungtruocVND" id = "sotienungtruocVND" 
                        		value=""  readonly="readonly" > 
                     </TD>
                <%} %>
                
                </TR>
                <TR>
                    <TD class="plainlabel">Tổng tiền thanh toán </TD>
                    <TD  class="plainlabel">
                        <input type="text" style="text-align: right;" name="tongthanhtoan" id = "tongthanhtoan" 
                        		value="<%= tthdBean.getTongthanhtoan() %>"  readonly="readonly"> 
                     </TD> 
                     
                <%if(!tthdBean.getTienteId().equals("100000")){ // NGOẠI TỆ%>
                	<TD class="plainlabel">Tổng tiền thanh toán (VNĐ)</TD>
                    <TD class="plainlabel">
                        <input type="text" style="text-align: right;" name="tongthanhtoanVND" id = "tongthanhtoanVND" 
                        		value="<%= tthdBean.getTongthanhtoanVND() %>"  readonly="readonly" > 
                     </TD> 
                <%} else {%>
                 	<TD class="plainlabel" colspan="2">
                        <input type="hidden" style="text-align: right;" name="tongthanhtoanVND" id = "tongthanhtoanVND" 
                        		value=""  readonly="readonly" > 
                     </TD> 
                <%} %>
                
                </TR>

                <TR>
                    <TD class="plainlabel">Chênh lệch </TD>
                    <TD  class="plainlabel">
                        <input type="text" style="text-align: right;" name="chenhlech" id = "chenhlech" 
                        		value="<%= tthdBean.getChenhlech() %>"  readonly="readonly" > 
                     </TD> 
                     
                <%if(!tthdBean.getTienteId().equals("100000")){ // NGOẠI TỆ%>
                	<TD class="plainlabel">Chênh lệch (VNĐ)</TD>
                    <TD class="plainlabel">
                        <input type="text" style="text-align: right;" name="chenhlechVND" id = "chenhlechVND" 
                        		value="<%= tthdBean.getChenhlechVND() %>"  readonly="readonly" > 
                     </TD> 
                <%} else {%>
                    <TD class="plainlabel" colspan="2">
                        <input type="hidden" style="text-align: right;" name="chenhlechVND" id = "chenhlechVND" 
                        		value=""  readonly="readonly" > 
                     </TD> 
                <%} %>
                
                </TR>
                
            </TABLE>
            <hr> 
            </div>
        
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="12%">Loại chứng từ</TH>
                	<TH align="center" width="12%">Ký hiệu HĐ</TH>
                	<TH align="center" width="12%">Số HĐ</TH>
                	<TH align="center" width="15%">Ngày HĐ</TH>
                <%if(tthdBean.getTienteId().equals("100000")){ %>
                	<TH align="center" width="20%">Tổng số tiền (đã có VAT)</TH>
                <%}else { %>
                	<TH align="center" width="15%">Tổng số tiền (Ngoại tệ)</TH>
                	<TH align="center" width="15%">Tổng số tiền VNĐ </TH>
                <%} %>
               	 	<TH align="center" width="10%">Thanh toán</TH>
               	 	<TH align="center" width="15%">Còn lại</TH>
               	 	<TH align="center" width="15%">
               	 		Tất cả 
               	 		<input type="checkbox" onchange="sellectAll()" id="chkAll"  />
               	 	</TH>
                </TR>
                <%
                	for(int i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadon hoadon = hoadonList.get(i);
                		String loaichungtu = "";
	               		%>
	               		<tr>
	               			<% if( hoadon.getLoaihd().equals("0")) loaichungtu = "Hóa đơn tài chính";
           	 					else if ( hoadon.getLoaihd().equals("1")) loaichungtu = "Hóa đơn khác";
           	 					else if ( hoadon.getLoaihd().equals("2")) loaichungtu = "Bút toán tổng hợp";
           	 				    else if ( hoadon.getLoaihd().equals("4")) loaichungtu = "Bù trừ công nợ";
           	 					else if ( hoadon.getLoaihd().equals("7")) loaichungtu = "Hóa đơn hàng trả về";
           	 				else if ( hoadon.getLoaihd().equals("6")) loaichungtu = "Thanh toán hóa đơn";
           	 				else if ( hoadon.getLoaihd().equals("5")) loaichungtu = "Tạm ứng";
           	 				%>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= loaichungtu %>" name= "sohd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd() %>" name= "loaiHd" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getTigiaHD() %>" name= "tigiaHD" readonly="readonly" >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKyhieu() %>" name= "kyhieuhd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
           	 				</td>
           	 			 <%if(tthdBean.getTienteId().equals("100000")){ %>	
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getTongtiencoVAT() %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>
           	 			 <%}else { %>
           	 			 	<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getTongtiencoVAT() %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getTongtiencoVATVND() %>" name= "avatVND" id="avatVND<%= i %>" readonly="readonly" >
           	 				</td>
           	 			 <%} %>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getThanhtoan() %>" name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="TraTienHD(<%= i %>)"  onKeyPress = "return keypress(event);" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getConlai() %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 				<%if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="TraHetHD(<%= i %>)" >
           	 				<%}else{ %>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="TraHetHD(<%= i %>)" >
           	 				<%}%>
           	 				</td>
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div>    
     </fieldset>	
    </TD>
    
</TR>
</TABLE>
</TD>
</TR>
</TABLE>

</form>
<script type="text/javascript">
	dropdowncontent.init('ctttId', "right-bottom", 300, "click");		
</script>
<script type="text/javascript">
	$("select").css("width","200px");
</script>
</BODY>
</html>
<%
try{
	if (nppList != null)
		nppList.close();
	if (tienteList != null)
		tienteList.close();
	if (hoadonList != null)
		hoadonList.clear();
	if (ctttList != null)
		ctttList.clear();
	if (nccList != null)
		nccList.close();
	if (nvtutList != null)
		nvtutList.close();
	tthdBean.DBclose();
}
catch (Exception ex)
{
	ex.printStackTrace();
}
%>