$(document).ready(function() {

    $('#btnDeleteProduct').click(function () {

        var productId = $('#productId').val();
        $.ajax({
            url: '/seller/products/delete/product-details/' + productId,
            headers: {'X-CSRF-TOKEN': $('#csrf').val()},
            type: 'POST',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                var productIdDeleted = '#' + productId + 'deleted';
                $(productIdDeleted).hide();
                $("#deleteProductModelConfirmation").modal('hide');
            },
            error: function () {
                alert("Something wrong")

            }
        });

    });

});
