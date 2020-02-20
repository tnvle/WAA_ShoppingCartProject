(function ($) {
    'use strict';

    var $window = $(window);

    // :: 1.0 Masonary Gallery Active Code

    var proCata = $('.amado-pro-catagory');
    var singleProCata = ".single-products-catagory";

    if ($.fn.imagesLoaded) {
        proCata.imagesLoaded(function () {
            proCata.isotope({
                itemSelector: singleProCata,
                percentPosition: true,
                masonry: {
                    columnWidth: singleProCata
                }
            });
        });
    }

    // :: 2.1 Search Active Code
    var amadoSearch = $('.search-nav');
    var searchClose = $('.search-close');

    amadoSearch.on('click', function () {
        $('body').toggleClass('search-wrapper-on');
    });

    searchClose.on('click', function () {
        $('body').removeClass('search-wrapper-on');
    });

    // :: 2.2 Mobile Nav Active Code
    var amadoMobNav = $('.amado-navbar-toggler');
    var navClose = $('.nav-close');

    amadoMobNav.on('click', function () {
        $('.header-area').toggleClass('bp-xs-on');
    });

    navClose.on('click', function () {
        $('.header-area').removeClass('bp-xs-on');
    });

    // :: 3.0 ScrollUp Active Code
    if ($.fn.scrollUp) {
        $.scrollUp({
            scrollSpeed: 1000,
            easingType: 'easeInOutQuart',
            scrollText: '<i class="fa fa-angle-up" aria-hidden="true"></i>'
        });
    }

    // :: 4.0 Sticky Active Code
    $window.on('scroll', function () {
        if ($window.scrollTop() > 0) {
            $('.header_area').addClass('sticky');
        } else {
            $('.header_area').removeClass('sticky');
        }
    });

    // :: 5.0 Nice Select Active Code
    if ($.fn.niceSelect) {
        $('select').niceSelect();
    }

    // :: 6.0 Magnific Active Code
    if ($.fn.magnificPopup) {
        $('.gallery_img').magnificPopup({
            type: 'image'
        });
    }

    // :: 7.0 Nicescroll Active Code
    if ($.fn.niceScroll) {
        $(".cart-table table").niceScroll();
    }

    // :: 8.0 wow Active Code
    if ($window.width() > 767) {
        new WOW().init();
    }

    // :: 9.0 Tooltip Active Code
    if ($.fn.tooltip) {
        $('[data-toggle="tooltip"]').tooltip();
    }

    // :: 10.0 PreventDefault a Click
    $("a[href='#']").on('click', function ($) {
        $.preventDefault();
    });

    // :: 11.0 Slider Range Price Active Code
    $('.slider-range-price').each(function () {
        var min = jQuery(this).data('min');
        var max = jQuery(this).data('max');
        var unit = jQuery(this).data('unit');
        var value_min = jQuery(this).data('value-min');
        var value_max = jQuery(this).data('value-max');
        var label_result = jQuery(this).data('label-result');
        var t = $(this);
        $(this).slider({
            range: true,
            min: min,
            max: max,
            values: [value_min, value_max],
            slide: function (event, ui) {
                var result = label_result + " " + unit + ui.values[0] + ' - ' + unit + ui.values[1];
                console.log(t);
                t.closest('.slider-range').find('.range-price').html(result);
            }
        });
    });

    $("#reviewId").click(function(){
        $("#myModal").modal();
        $('#btnSubmitReview').click(function(){
            var rating = $('#rating').val();
            var message = $('#message').val();
            var productId = $('#productId').val();

            if(message ===''){
                alert('Please enter the message');
                return;
            }

            $.ajax({
                url: '/product/review/'+productId,
                headers: {'X-CSRF-TOKEN': $('#csrf').val()},
                type: 'POST',
                data: JSON.stringify({rating: rating, message: message}),
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    if(data.success == true) {
                        $('#message').val('');
                        $('#btnSubmitReview').attr("disabled", true);
                        $('#modal-body-id').append('<div style="text-align: center"><img src="/img/icon/checkmark.gif" width="300" /><br/>Your Review need Admin\'s approval to be showed up</div> ');

                    }else {
                        $('#modal-body-id').html('');
                        $('#btnSubmitReview').attr("disabled", true);
                        $('#modal-body-id').append('<div style="text-align: center;color: red;"> You need to order this product first before reviewing it');
                    }
                },
                error: function () {
                    window.location='/login';
                }

            });

        });
    });

    $('#btnApproveReview').click(function () {

        var reviewId = $('#reviewId').val();
        $.ajax({
            url: '/admin/product/reviews/approve/'+reviewId,
            headers: {'X-CSRF-TOKEN': $('#csrf').val()},
            type: 'POST',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                $('#'+reviewId).text("Approved");
                $('#'+reviewId).attr('class', 'btn btn-success');
                $('#'+reviewId).attr("disabled", true);
                $("#reviewModelConfirmation").modal('hide');
            },
            error: function () {
                alert("Something wrong")

            }

        });

    });


    $("#btnUpdateStatus").click(function(){
            var orderId = $('#orderId').val();

            var status = $('#statusSelect').val();

            if(orderId ===''){
                alert('Please select the status');
                return;
            }

            $.ajax({
                url: '/admin/product/orders/'+orderId+'/'+ status,
                headers: {'X-CSRF-TOKEN': $('#csrf').val()},
                type: 'PUT',
                data: {},
                contentType: 'application/json',
                dataType: 'json',
                success: function (data) {
                    $('#'+orderId).text(status);
                    $('#point_'+orderId).html(data.data);
                    if(status == 'Successful'){
                        $('#'+orderId).attr('class', 'btn btn-success');
                    }else {
                        $('#'+orderId).attr('class', 'btn btn-warning');
                    }
                    $("#orderList").modal('hide');
                },
                error: function () {
                    alert("Something wrong")
                }

            });

    });


    $('#btnApproveShowAds').click(function () {

        var adsId = $('#adsId').val();
        $.ajax({
            url: '/admin/ads/approve/'+adsId,
            headers: {'X-CSRF-TOKEN': $('#csrf').val()},
            type: 'POST',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                let adses = data.data;
                var newRows = '';
                $.each( adses, function( key, ads ) {

                    newRows += '<tr> <td> ' + ads.id + ' </td>';
                    newRows += '<td> <img width="100" src="/img/core-img/ads-img/' + ads.image.name + '"</img </td>';
                    newRows +='<td>' + ads.title + '</td><td>' + ads.description + '<td><td style = "text-align: center;vertical-align: middle;" >';
                    if (ads.show === true) {
                        newRows += ' <button class = "btn btn-success" disabled > Showed </button> ';
                    } else {
                        newRows += ' <button class = "btn btn-warning" id="'+ads.id+'" onclick = "document.getElementById(\'adsId\').value = this.id" data-toggle = "modal" data-target = "#adsModelConfirmation"> Not Showed </button>';
                    }

                    newRows += '</td> </tr>';
                });
                $('table tbody').html(newRows);


                $("#adsModelConfirmation").modal('hide');

            },
            error: function () {
                alert("Something wrong")

            }

        });

    });


})(jQuery);