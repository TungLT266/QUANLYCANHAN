package geso.traphaco.erp.servlets.park;


public class DocTien{
	// Tên gọi của các chữ số
	static private String[] chuSo = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
	// Tên gọi đơn vị của các nhóm số (tính từ phải sang trái)
	static private String[] donViNhom = {"", "nghìn", "triệu ", "tỉ"};
	
	static public String docTien(long soTienCanDoc){
		String bangChu = ""; // chứa kết quả đọc số
		
		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for(int i=0; soTienCanDoc > 0; i++){
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int)(soTienCanDoc % 10);
			int hangChuc = (int)((soTienCanDoc / 10) % 10);
			int hangTram = (int)((soTienCanDoc / 100) % 10);
			
			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số
			
			// đọc hàng chục nếu có
			if(soTienCanDoc > 9){
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2 chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa
						.replace("không mươi không", "")
						.replace("không mươi", "lẻ")
						.replace("mươi không", "mươi")
						.replace("mươi một", "mươi mốt")
						.replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười")
						.replace("mười mốt", "mười một")
						.replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}
			
			// đọc hàng trăm nếu có
			if(soTienCanDoc > 99){
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3 chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if(nhomDocLa.trim().equals("không trăm")){
					nhomDocLa = "";
				}
			}
			
			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];
			
			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;
			
			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}
		
		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu
				.replaceAll("\\s+", " ")
				.replaceAll("tỉ triệu nghìn", "tỉ")
				.replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");
		
		if(bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());
		
		return bangChu + "đồng";
	}
	
	static public String docSo(long soTienCanDoc, boolean isUpper){
		String bangChu = ""; // chứa kết quả đọc số
		
		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for(int i=0; soTienCanDoc > 0; i++){
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int)(soTienCanDoc % 10);
			int hangChuc = (int)((soTienCanDoc / 10) % 10);
			int hangTram = (int)((soTienCanDoc / 100) % 10);
			
			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số
			
			// đọc hàng chục nếu có
			if(soTienCanDoc > 9){
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2 chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa
						.replace("không mươi không", "")
						.replace("không mươi", "lẻ")
						.replace("mươi không", "mươi")
						.replace("mươi một", "mươi mốt")
						.replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười")
						.replace("mười mốt", "mười một")
						.replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}
			
			// đọc hàng trăm nếu có
			if(soTienCanDoc > 99){
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3 chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if(nhomDocLa.trim().equals("không trăm")){
					nhomDocLa = "";
				}
			}
			
			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];
			
			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;
			
			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}
		
		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu
				.replaceAll("\\s+", " ")
				.replaceAll("tỉ triệu nghìn", "tỉ")
				.replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");
		
		if(bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		if (isUpper == true)
			bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());
		
		return bangChu;
	}
	
	static public String docTienNgoaiTe(long soTienCanDoc, long phanLe, String tienTe, String tienTeLe){
		String bangChu = "";
		if (tienTe.trim().equals("USD") == true)
			tienTe = "đô la Mỹ";
		else if (tienTe.trim().equals("SGD") == true)
			tienTe = "đô la Singapore";
		else if (tienTe.trim().equals("JPY") == true)
			tienTe = "yên Nhật";
		else if (tienTe.trim().equals("MYR") == true)
			tienTe = "ringgit";
		else if (tienTe.trim().equals("EUR") == true)
			tienTe = "euro";
		else if (tienTe.trim().equals("HKD") == true)
			tienTe = "đô la Hồng Kông";
		bangChu = docSo(soTienCanDoc, true) + tienTe;
		if (phanLe > 0)
			bangChu += " và " + docSo(phanLe, false) + tienTeLe;
		return bangChu;
	}
	
	
	static public String docTien(double soTienCanDoc){
		String bangChu = ""; // chứa kết quả đọc số
		
		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for(int i=0; soTienCanDoc > 0; i++){
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int)(soTienCanDoc % 10);
			int hangChuc = (int)((soTienCanDoc / 10) % 10);
			int hangTram = (int)((soTienCanDoc / 100) % 10);
			
			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số
			
			// đọc hàng chục nếu có
			if(soTienCanDoc > 9){
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2 chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa
						.replace("không mươi không", "")
						.replace("không mươi", "lẻ")
						.replace("mươi không", "mươi")
						.replace("mươi một", "mươi mốt")
						.replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười")
						.replace("mười mốt", "mười một")
						.replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}
			
			// đọc hàng trăm nếu có
			if(soTienCanDoc > 99){
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3 chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if(nhomDocLa.trim().equals("không trăm")){
					nhomDocLa = "";
				}
			}
			
			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];
			
			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;
			
			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}
		
		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu
				.replaceAll("\\s+", " ")
				.replaceAll("tỉ triệu nghìn", "tỉ")
				.replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");
		
		if(bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());
		
		return bangChu + "đồng";
	}
}

