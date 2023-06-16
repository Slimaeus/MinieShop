$(document).ready(function () {
    $('.quantity').change(function () {
        let quantity = $(this).val();
        let id = $(this).attr('data-id');
        $.ajax({
            url: '/cart/updateCart/' + id + '/' + quantity,
            type: 'GET',
            success: function () {
                location.reload();
            }
        });
    });
    $('.add-to-cart-buttons').click(function (event) {
        event.preventDefault();
        const id = $(this).attr("data-id");
        $.ajax({
            url: '/api/cart/add-to-cart/' + id + '/' + 1,
            type: 'GET',
            success: function () {
                Snackbar.show({
                    text: "Thêm thành công"
                });
            }
        });
    })
});
