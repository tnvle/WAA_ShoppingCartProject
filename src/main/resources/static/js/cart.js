$(document).ready(function() {

    $("input[name=quantity]").change(function(event) {

        event.preventDefault();
        var productId = $(this).attr("product-id");
        var qty = $(this).val();
        var token = $("#csrf").val();

        $.ajax({
            url: '/rest/cart/update/' + productId + '/' + qty,
            headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
            type: 'PUT',
            dataType: "json",
            success: function (response) {
                var items = response.data.cartItems;
                var content = generateCartItemRows(items);
                console.log(content);

                $("#tblCartItems tbody").html(content);
                $("#total").html('$' + number_format(response.data.grandTotal, 2));
                $("#grandTotal").html('$' + number_format(response.data.grandTotal, 2));
                // alert("Product Successfully added to the Cart!");
            },
            error: function (err) {
                console.dir(err);
                // alert('Error while request..');
            }
        });
    });
    $(".add-to-cart-btn").click(function(event){

        event.preventDefault();
        var productId = $(this).attr("data");
        var qty = $("#qty").val();
        if(!qty) {
            qty = 1;
        }
        var token = $("#csrf").val();//$("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/rest/cart/add/' + productId + '/' + qty,
            headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
            type: 'PUT',
            dataType: "json",
            success: function(response){
                console.log("Product Successfully removed to the Cart!");
                if(response.data) {
                    var items = response.data.cartItems;
                    // var content = generateCartItemRows(items);
                    // console.log(content);
                    //
                    // $("#tblCartItems tbody").html(content);
                    // $("#total").html('$' + number_format(response.data.grandTotal, 2));
                    // $("#grandTotal").html('$' + number_format(response.data.grandTotal, 2));
                    $("#cartItemsNum").html('(' + items.length + ')');
                    alert("Product Successfully added to the Cart!");
                }
            },
            error: function(err){
                console.dir(err);
            }
        });
    });
    $(".remove-from-cart").click(function(event){

        event.preventDefault();
        var productId = $(this).attr("data");
        var token = $("#csrf").val();//$("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/rest/cart/remove/' + productId ,
            headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
            type: 'PUT',
            dataType: "json",
            success: function(response){
                console.log("Product Successfully removed to the Cart!");
                if(response.data) {
                    var items = response.data.cartItems;
                    var content = generateCartItemRows(items);
                    console.log(content);

                    $("#tblCartItems tbody").html(content);
                    $("#total").html('$' + number_format(response.data.grandTotal,2) );
                    $("#grandTotal").html('$' + number_format(response.data.grandTotal,2));
                    $("#cartItemsNum").html('('+ items.length + ')');
                }
            },
            error: function(err){
                console.dir(err);
            }
        });
    });
    $('.cart-qty-btn').click(function(event){

        event.preventDefault();
        var productId = $(this).attr("product-id");
        var action = $(this).attr("action");
        var qty = $(this).attr("qty");
        var token = $("#csrf").val();
        if(action === "minus") {
            qty = parseInt(qty) - 1;
        } else if(action === "plus"){
            qty = parseInt(qty) + 1;
        }

            $.ajax({
            url: '/rest/cart/update/' + productId + '/' + qty,
                headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
            type: 'PUT',
            dataType: "json",
            success: function(response){
                var items = response.data.cartItems;
                var content = generateCartItemRows(items);
                console.log(content);

                $("#tblCartItems tbody").html(content);
                $("#total").html('$' + number_format(response.data.grandTotal,2) );
                $("#grandTotal").html('$' + number_format(response.data.grandTotal,2));
                // alert("Product Successfully added to the Cart!");
            },
            error: function(err){
                console.dir(err);
                // alert('Error while request..');
            }
        });

    });

});
function generateCartItemRows(items) {
    var content = '';
    for (var i = 0; i < items.length; i++) {
        content += '<tr>';
        content += '<td class="cart_product_img">';
        content += '<a href="/product-details/' + items[i].product.id + '"><img src="/img/product-img/' + items[i].product.images[0].name + '"  alt="Product"></a>';
        content += '</td>\n' +
            ' <td class="cart_product_desc">\n' +
            ' <h5 >'+ items[i].product.name + '</h5>\n' +
            ' </td>\n' +
            ' <td class="price">\n' +
            '  <span >$' + number_format(items[i].product.price,2)+ '</span>\n' +
            ' </td>\n' +
            ' <td class="qty">\n' +
            '  <div class="qty-btn d-flex">\n' +
            '    <div class="quantity">\n' +
            '     <span class="cart-qty-btn qty-minus" product-id="' + items[i].product.id + '" qty="' + items[i].quantity + '" action="minus" >\n' +
            '       <i class="fa fa-minus" aria-hidden="true"></i></span>\n' +
            '       <input type="number" class="qty-text" id="qty" step="1" min="1" max="300"\n' +
            '       name="quantity" value="'+ items[i].quantity + '" >\n' +
            '       <span class="cart-qty-btn qty-plus" product-id="' + items[i].product.id + '" qty="' + items[i].quantity + '" action="plus" >\n' +
            '       <i class="fa fa-plus" aria-hidden="true"></i></span>\n' +
            '    </div>\n' +
            '    <span class="remove-from-cart" data="'+items[i].product.id+'"><i class="fa fa-trash" aria-hidden="true"></i></span>\n' +
            '   </div>\n' +
            ' </td>';
        content += '</tr>';
    }
    return content;
}

function number_format(number, decimals, decPoint, thousandsSep){
    decimals = decimals || 0;
    number = parseFloat(number);

    if(!decPoint || !thousandsSep){
        decPoint = '.';
        thousandsSep = ',';
    }

    var roundedNumber = Math.round( Math.abs( number ) * ('1e' + decimals) ) + '';
    var numbersString = decimals ? roundedNumber.slice(0, decimals * -1) : roundedNumber;
    var decimalsString = decimals ? roundedNumber.slice(decimals * -1) : '';
    var formattedNumber = "";

    while(numbersString.length > 3){
        formattedNumber += thousandsSep + numbersString.slice(-3)
        numbersString = numbersString.slice(0,-3);
    }

    return (number < 0 ? '-' : '') + numbersString + formattedNumber + (decimalsString ? (decPoint + decimalsString) : '');
}