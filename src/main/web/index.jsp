<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Shopping Cart</title>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath }/resources/js/jquery.js"></script>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath }/resources/js/jquery-ui-1.8.2.custom.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {

            $('#listProducts img').css({
                'z-index' : 100
            }).draggable({
                'opacity' : '0.5',
                'revert' : true,
                'cursor' : 'pointer'
            });

            function displayCart(data) {
                var s = '';
                var sum = 0;
                for (var i = 0; i < data.length; i++) {
                    sum += parseInt(data[i].quantity) * parseFloat(data[i].product.price);
                    s += '<br><img src="${pageContext.servletContext.contextPath }/resources/http://learningprogramming.net/wp-content/uploads/java/spring_mvc/" width="50" height="50">';
                    s += '<br>Id: ' + data[i].product.id;
                    s += '<br>Name: ' + data[i].product.name;
                    s += '<br>Quantity: ' + data[i].quantity;
                    s += '<br>Sub Total: ' + (parseInt(data[i].quantity) * parseFloat(data[i].product.price));
                    s += '<br><a href="#?productIdCart=' + data[i].product.id + '" class="delete">Delete</a>';
                    s += '<br>--------------------------';
                }
                s += '<br>Totals: ' + sum;
                $('#cart').html(s);
            }

            $('#cart').droppable({
                drop: function (event, ui) {
                    var productId = $(ui.draggable).siblings('.productId').val();
                    $.ajax({
                        type: 'GET',
                        dataType: 'json',
                        contentType: 'application/json',
                        url: '${pageContext.request.contextPath}/cart/buy/' + productId ,
                        success: function (data) {
                            displayCart(data);
                        }
                    });
                }
            });

            $('#cart').ajaxComplete(function () {
                $('.delete').bind('click', function () {
                    var productIdCart = $(this).attr('href').split('=');
                    $.ajax({
                        type: 'GET',
                        dataType: 'json',
                        contentType: 'application/json',
                        url: '${pageContext.request.contextPath}/cart/delete/' + productIdCart[1],
                        success: function (data) {
                            displayCart(data);
                        }
                    });
                });
            });


        });
    </script>

</head>
<body>

<div style="float: left; margin-right: 10px;" id="listProducts">
    <table cellpadding="2" cellspacing="2" border="1">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <c:forEach var="product" items="${products }">
            <tr>
                <td>${product.id }</td>
                <td>${product.name }</td>
                <td>${product.price }</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="cart" style="width: 200px; min-height: 200px; border: 1px solid red; margin-left: 300px; padding: 5px;">

</div>

</body>
</html>