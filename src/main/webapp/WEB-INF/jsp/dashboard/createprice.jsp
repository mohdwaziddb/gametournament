<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Create Price</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">
    <button class="add-btn" onclick="openModal()">Add Price</button>
    <table>
        <thead>
        <tr>
            <th>Sr No.</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="pricedata">

        </tbody>
    </table>


    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2 id="add_edit_price">Add Price</h2>
            <form>
                <label for="price">Price:</label><br>
                <input type="number" id="price" name="price"><br>
                <input type="hidden" id="price_id">
                <button type="button" onclick="savePrice()">Save</button>
            </form>
        </div>
    </div>
</div>

<script>

    $(document).ready(function () {
        onloadMethod();
    });

    function onloadMethod(){
        let domain = getDomain() + "/rest/game/price_list";

        let data = {
        }

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                //console.log(response);
                let priceList = response.data.price_list;
                let tabledate = "";
                let srno=1;
                if(priceList != null && priceList.length>0){
                    for (let data in priceList) {
                        let priceListElement = priceList[data];
                        let name = priceListElement.price;
                        let id = priceListElement.id;
                        tabledate += '<tr>'
                            +'<td>'+srno+++'</td>'
                            +'<td>'+name+'</td>'
                            +'<td class="action-buttons">'
                            +'<button class="edit-btn" onclick="editBut('+id+')">Edit</button>'
                            +'<button class="delete-btn" onclick="deleteBut('+id+')">Delete</button>'
                            +'</td>'
                            +'</tr>'
                    }
                    $('#pricedata').html(tabledate);
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function editBut(id) {
        openModal();
        $('#add_edit_price').text('Edit Price');
        let domain = getDomain() + "/rest/game/price_list";

        let data = {
            "id":id
        }

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                let priceList = response.data.price_list;
                if(priceList != null){
                    for (let data in priceList) {
                        let name = priceList.price;
                        let id = priceList.id;
                        $('#price').val(name);
                        $('#price_id').val(id);
                    }
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function deleteBut(id){
        let domain = getDomain() + "/rest/game/deleteprice";
        let data = {
            "id":id,
        }

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                //console.log(response);
                if(response.success){
                    window.location.reload();
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }


    function savePrice(){
        let price = $('#price').val();
        let id = $('#price_id').val();

        let domain = getDomain() + "/rest/game/createprice";

        let data = {
            "price":price,
            "id":id
        }

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                //console.log(response);
                if(response.success){
                    window.location.reload();
                    closeModal();
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });


    }

    // When the user clicks the button, open the modal
    function openModal() {
        modal.style.display = "block";
        $('#add_edit_price').text('Add Price');
        $('#price_id').val('');
        $('#price').val('');
    }
</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
