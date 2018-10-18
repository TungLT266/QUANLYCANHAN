var NhaPhanPhoi = function(id, ten) {
	this.id = id;
	this.ten = ten;
};

var QuanHuyen = function(tinhId, id, ten) {
	this.tinhId = tinhId;
	this.id = id;
	this.ten = ten;
};

var TinhThanh = function(id, ten) {
	this.id = id;
	this.ten = ten;
	this.quans = [];
};