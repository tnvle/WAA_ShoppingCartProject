$(document).ready(function() {

    var cashradiobtn = document.getElementById("payment.id1");
    if(cashradiobtn)
        cashradiobtn.setAttribute("checked","checked");

    $('#btnCancelOrder').click(function () {

        var orderId = $('#orderId').val();
        $.ajax({
            url: '/buyer/order/' + orderId + '/canceled',
            headers: {'X-CSRF-TOKEN': $('#csrf').val()},
            type: 'POST',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                $('#point_'+orderId).html(data.data);
                $('#status_'+orderId).html("Cancelled");
                $('#' + orderId).text("Cancelled");
                $('#' + orderId).attr('class', 'btn btn-success');
                $('#' + orderId).attr("disabled", true);
                $("#orderModelConfirmation").modal('hide');
            },
            error: function () {
                alert("Something wrong")

            }

        });

    });

});