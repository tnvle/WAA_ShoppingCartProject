$(document).ready(function() {

    $('#followSeller').click(updateFollower);
    $('#unfollowSeller').click(updateFollower);

});
function updateFollower() {
    event.preventDefault();
    var mode = $(this).attr("mode");
    var sellerId = $(this).attr("seller-id");
    var token = $("#csrf").val();
    $.ajax({
        url: '/rest/' + mode + '/' + sellerId,
        headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
        type: 'PUT',
        dataType: "json",
        success: function(response){
            if(response.data) {
                console.log("Successfully follow to the Seller!");
                location.reload();
            }
        },
        error: function(err){
            console.dir(err);
        }
    });
}