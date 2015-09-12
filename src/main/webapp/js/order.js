function IsNumeric(sText) {
    var ValidChars = "0123456789.";
    var IsNumber = true;
    var Char;
    for (i = 0; i < sText.length && IsNumber == true; i++) {
        Char = sText.charAt(i);
        if (ValidChars.indexOf(Char) == -1) {
            IsNumber = false;
        }
    }
    return IsNumber;
};
function calcProdSubTotal() {
    var prodSubTotal = 0;
    $("input[class='row-total-input']").each(function () {
        var valString = $(this).val() || 0;
        prodSubTotal=parseFloat(prodSubTotal+parseFloat(valString)).toFixed(4);
    });
    $("#product-subtotal").parents('td').find('input:eq(1)').css('background-color','white').val(prodSubTotal);
};
function calcTotalPallets() {
    var totalPallets = 0;
    $(".num-pallets-input").each(function () {
        var thisValue = $(this).val();
        if ((IsNumeric(thisValue)) && (thisValue != '')) {
            totalPallets += parseInt(thisValue);
        }
        ;
    });
    $("#total-pallets-input").val(totalPallets);
};
function calcShippingTotal() {
    var totalPallets = $("#total-pallets-input").val() || 0;
    var shippingRate = $("#shipping-rate").text() || 0;
    var shippingTotal = totalPallets * shippingRate;
    $("#shipping-subtotal").val(shippingTotal);
};
function calcOrderTotal() {
    var orderTotal = 0;
    var productSubtotal = $("#product-subtotal").val() || 0;
    var shippingSubtotal = $("#shipping-subtotal").val() || 0;
    var orderTotal = parseInt(productSubtotal) + parseInt(shippingSubtotal);
    var orderTotalNice = "$" + orderTotal;
    $("#order-total").val(orderTotalNice);
};

$(function () {
    addEventForInput();
});

function addEventForInput(){
    //
    var palletsList=$('.num-pallets-input,.price-per-pallet').parent().find('input:eq(1)');
    for(var i=0;i<palletsList.length;i++){
        palletsList.eq(i).blur(function () {
            var $this = $(this);
            var numPallets = $this.parents('tr').find("input.num-pallets-input").val();
            var multiplier = $this.parents('tr').find("input.price-per-pallet").val();
            if ((IsNumeric(numPallets)) && (numPallets != '')&&(IsNumeric(multiplier)) && (multiplier != '')) {
                var rowTotal = parseFloat(numPallets * multiplier).toFixed(4);
                $this.parents('tr').find("td:eq(6)").find('input[class="row-total-input"]').val(rowTotal);
            }
            calcProdSubTotal();

        });
    }
}
