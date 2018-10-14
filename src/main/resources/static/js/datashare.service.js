var app = angular.module("mainApp");

app.factory('ShareData', function() {
	var data = {};
	data.cart = [];
	data.orderPlaced = false;
	return data;
});

app.service('CartUtils', function(ShareData){
	
	this.calc = function(data){
		const discounts = {
				DVD : {
					desc :'Congratulations! You have received a new discount of 10% for buying all movies with DVD print type.',
					discount : 0.05
				},
				BLURAY : {
					desc : 'Congratulations! You have received a new discount of 15% for buying all movies with Blu-ray print type.',
					discount : 0.15
				},
				BULK : {
					desc: 'Congratulations! You have received a new discount received for buying all movies with Blu-ray print type.',
					discount : 0.05
				}
				 
		};
		var discountReceived = [];
		var totalBillAmount = 0;
		var discountAmount = 0;
		var grossAmount = 0;
		var totalItems = 0; 
		var dvdTotal = 0;
		var bluRayTotal = 0;
		var discountedAmount = 0;
		var dvdTypeCount = (_.filter(data, function(elem){
			return elem.data.printType === 'DVD';})).length;

		var bluRayTypeCount = ( _.filter(data, function(elem){
			return elem.data.printType === 'Blu-Ray';})).length; 


		_.forEach(data, function(elem){
				totalItems += elem.length;
				totalBillAmount += elem.total;
				if(dvdTypeCount >= 3 && elem.data.printType === 'DVD'){
					dvdTotal += elem.total;
				}
				if(bluRayTypeCount >= 3 && elem.data.printType === 'Blu-Ray'){
					bluRayTotal += elem.total;
				}
				
		});
		
		
		
		
		if(dvdTypeCount >= 3){
			discountReceived.push({
				discountType: '3-DVD type prints',
				discount: '-5% on DVD prints'
			});
			discountAmount += dvdTotal * discounts.DVD.discount;
		}
		
		if(bluRayTypeCount >= 3){
			discountReceived.push({
				discountType: '3-BluRay type prints',
				discount: '-15% on BluRay prints'
			});
			discountAmount += bluRayTotal * discounts.BLURAY.discount;
			
		}
		if(totalItems >= 100){
			discountReceived.push({
				discountType: 'Bulk-Discount',
				discount: '-5%'
			});
			discountAmount +=  totalBillAmount * discounts.BULK.discount;
		}
		discountedAmount =  totalBillAmount - discountAmount;
		
		return {
			totalBillAmount : totalBillAmount,
			discountsReceived : discountReceived,
			discountAmount : discountAmount,
			discountedAmount : discountedAmount
		}
	}
	
	this.addToCart = function(cart, data){
		var index = _.findIndex(cart, function(elem) {
			return (elem.data.movieName == data.movieName)
					&& (elem.data.printType == data.printType);
		});
		if (index === -1) {
			cart.push({
				data : data,
				length : 1,
				total : data.price
			});
		}
		updateCart(index, 'add', cart, data);
		return cart;

	}
	
	this.removeFromCart = function(cart, data){
		var index = _.findIndex(cart, function(elem) {
			return (elem.data.movieName == data.movieName && elem.data.printType == data.printType);
		});
		
		cart = updateCart(index, 'remove', cart, data);
		return cart;
	}
	
	
	function updateCart(index, flag, cart, data) {
		if (index >= 0 && cart[index].length > 0) {
			if (flag === 'add') {
				cart[index].length++;
			} else {
				cart[index].length--;
				if (index !== -1 && cart[index].length === 0) {
					cart.splice(index, 1);
				}
			}

			
		}
		if(cart.length > 0 && cart[index]){
			cart[index].total = cart[index].length
			* cart[index].data.price;
		}
		if (cart) {
			ShareData.cart = cart;
		}

		return cart;
	}
	
	
});