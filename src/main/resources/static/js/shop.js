$(document).ready(function() {
    $('#viewProduct').change(callAjaxPaging);
    $("#pageNumbers > li.page-item").click(clickPaging);
    $("input[name='brand']").click(callAjaxFilterBrands);
});
function callAjaxFilterBrands(event) {
    // event.preventDefault();
    var brands = [];
    $("input[name='brand']:checked").each(function (){
        brands.push($(this).val());
    });
    var numPerPage = $("#viewProduct option:selected").val();
    var pageNo = $("#pageNumbers > li.active").html();
    var token = $("#csrf").val();
    if (pageNo == null || !parseInt(pageNo)) pageNo = 1;
    console.log(numPerPage);
    console.log(pageNo);
    $.ajax({
        url: '/rest/shop/brand/' + brands + '/' + numPerPage + '/' + pageNo,
        headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
        type: 'GET',
        dataType: "json",
        success: function (response) {
            console.log(response);
            if (response.data) {
                var items = response.data.productList;
                var content = generateContentOfProduct(items);
                console.log(content);

                $("#listContent").html(content);
                var pageNo = response.data.pageNum;
                var numPerPage = response.data.itemsPerPage;
                var startIndex = 1 + (pageNo - 1) * numPerPage;
                var totalCount = response.data.totalCount;
                var endIndex = totalCount > pageNo * numPerPage ? pageNo * numPerPage : totalCount;
                $("#viewProduct").val(numPerPage);
                var pNums = '';
                var pNumsArr = response.data.pageNumbers;
                for (var i = 0; i < pNumsArr.length; i++) {
                    if (pNumsArr[i] === pageNo) {
                        pNums += '<li class="page-item active">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    } else {
                        pNums += '<li class="page-item">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    }
                }
                $("#pageNumbers").html(pNums);
                // $("li.page-item > a").html(pageNo);
                $("#showPageTxt").html('Showing ' + startIndex + '-' + endIndex + ' Of ' + totalCount);

            }
        },
        error: function (err) {
            console.dir(err);
        }
    });
}

function clickPaging(event) {
    // event.preventDefault();
    var numPerPage = $("#viewProduct option:selected").val();
    var pageNo = $(this).children().html();
    var token = $("#csrf").val();
    if (pageNo == null || !parseInt(pageNo)) pageNo = 1;
    console.log(numPerPage);
    console.log(pageNo);
    $.ajax({
        url: '/rest/shop/' + numPerPage + '/' + pageNo,
        headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
        type: 'GET',
        dataType: "json",
        success: function (response) {
            console.log(response);
            if (response.data) {
                var items = response.data.productList;
                var content = generateContentOfProduct(items);
                console.log(content);

                $("#listContent").html(content);
                var pageNo = response.data.pageNum;
                var numPerPage = response.data.itemsPerPage;
                var startIndex = 1 + (pageNo - 1) * numPerPage;
                var totalCount = response.data.totalCount;
                var endIndex = totalCount > pageNo * numPerPage ? pageNo * numPerPage : totalCount;
                $("#viewProduct").val(numPerPage);
                var pNums = '';
                var pNumsArr = response.data.pageNumbers;
                for (var i = 0; i < pNumsArr.length; i++) {
                    if (pNumsArr[i] === pageNo) {
                        pNums += '<li class="page-item active">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    } else {
                        pNums += '<li class="page-item">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    }
                }
                $("#pageNumbers").html(pNums);
                // $("li.page-item > a").html(pageNo);
                $("#showPageTxt").html('Showing ' + startIndex + '-' + endIndex + ' Of ' + totalCount);

            }
        },
        error: function (err) {
            console.dir(err);
        }
    });
}
function callAjaxPaging(event) {
    // event.preventDefault();
    var numPerPage = $("#viewProduct option:selected").val();
    var pageNo = 1;
    var token = $("#csrf").val();
    // console.log(numPerPage);
    // console.log(pageNo);
    $.ajax({
        url: '/rest/shop/' + numPerPage + '/' + pageNo,
        headers: {"X-CSRF-TOKEN": token}, //send CSRF token in header
        type: 'GET',
        dataType: "json",
        success: function (response) {
            console.log(response);
            if (response.data) {
                var items = response.data.productList;
                var content = generateContentOfProduct(items);
                console.log(content);

                $("#listContent").html(content);
                var pageNo = response.data.pageNum;
                var numPerPage = response.data.itemsPerPage;
                var startIndex = 1 + (pageNo - 1) * numPerPage;
                var totalCount = response.data.totalCount;
                var endIndex = totalCount > pageNo * numPerPage ? pageNo * numPerPage : totalCount;
                $("#viewProduct").val(numPerPage);
                var pNums = '';
                var pNumsArr = response.data.pageNumbers;
                for (var i = 0; i < pNumsArr.length; i++) {
                    if (pNumsArr[i] === pageNo) {
                        pNums += '<li class="page-item active">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    } else {
                        pNums += '<li class="page-item">' +
                            '<a class="page-link" href="#" >' + pNumsArr[i] + '</a></li>\n';
                    }
                }
                $("#pageNumbers").html(pNums);
                // $("li.page-item > a").html(pageNo);
                $("#showPageTxt").html('Showing ' + startIndex + '-' + endIndex + ' Of ' + totalCount);

            }
        },
        error: function (err) {
            console.dir(err);
        }
    });
}
function generateContentOfProduct(products) {
    var content = '';

    for (var i = 0; i < products.length; i++) {
        content += '<div class="col-12 col-sm-6 col-md-12 col-xl-6" >\n' +
            '                <div class="single-product-wrapper">\n' +
            '                    <!-- Product Image -->\n' +
            '                    <div class="product-img">\n' +
            '                        <a class="gallery_img" href="/img/product-img/' + products[i].images[0].name + '">\n' +
            '                        <img src="/img/product-img/'+ products[i].images[0].name + '"  alt="">\n' +
            '                        <!-- Hover Thumb -->\n' +
            '                        <img class="hover-img" src="/img/product-img/'+ products[i].images[0].name + '" alt="">\n' +
            '                        </a>\n' +
            '                    </div>\n' +
            '\n' +
            '                    <!-- Product Description -->\n' +
            '                    <div class="product-description d-flex align-items-center justify-content-between">\n' +
            '                        <!-- Product Meta Data -->\n' +
            '                        <div class="product-meta-data">\n' +
            '                            <div class="line"></div>\n' +
            '                            <p class="product-price" >$' + number_format(products[i].price,2)+ '</p>\n' +
            '                            <a href="/product-details/' + products[i].id + '">\n' +
            '                                <h6 >'+ products[i].name + '</h6>\n' +
            '                            </a>\n' +
            '                        </div>\n' +
            '                        <!-- Ratings & Cart -->\n' +
            '                        <div class="ratings-cart text-right">\n' +
            '                            <div class="ratings">\n';
            for(var j = 0; j < products[i].averageRating; j++){
                content += '<i class="fa fa-star" aria-hidden="true" ></i>\n';
            }
        content +=
            '                            </div>\n' +
            '                            <div class="cart">\n' +
            '                                <a href="#" data="' + products[i].id + '" class="btn amado-btn add-to-cart-btn" data-toggle="tooltip" data-placement="left" title="Add to Cart"><img\n' +
            '                                        src="/img/core-img/cart.png" alt=""></a>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>';
    }
        return content;
}