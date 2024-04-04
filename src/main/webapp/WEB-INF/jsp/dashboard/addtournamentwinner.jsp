<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Create Price</title>
<link id="manifest" rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>
    /* Styling for select wrapper */
    .select-wrapper {
        position: relative;
    }

    /* Hide options initially */
    .options {
        display: none;
        position: absolute;
        background-color: white;
        border: 1px solid #ccc;
        z-index: 1;
        width: 100%; /* Adjust width to fit container */
        margin-top: 5px; /* Add some margin from the input */
        box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2); /* Add a box shadow for better appearance */
    }

    /* Show options when select wrapper is clicked */
    .select-wrapper:focus-within .options {
        display: block;
    }

    /* Style for input field */
    #selectedUsername {
        /* width: 100%;*/ /* Adjust width to fit container */
        padding: 10px; /* Add some padding for better appearance */
        border: 1px solid #ccc; /* Add border */
        border-radius: 5px; /* Add border radius for better appearance */
        cursor: pointer; /* Change cursor to pointer */
    }

    /* Style for individual options */
    .options select {
        width: 100%; /* Adjust width to fit container */
        border: none; /* Remove default select border */
        background-color: transparent; /* Make background transparent */
    }

    /* Style for single select */
    .single-select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
        background-color: transparent;
    }
</style>
<body>
<div class="container">
    <div class="icon" onclick="toggleLogin(event)">
        <i class="fas fa-user"></i>
    </div>
    <div class="login-details" id="loginDetails">
        <p>Name: John Doe</p>
        <p>Email: johndoe@example.com</p>
        <p>Phone: +1234567890</p>
        <p>Username: johndoe123</p>
    </div>

    <button class="add-btn" onclick="openModal()">Add Winner</button>
    <table>
        <thead>
        <tr>
            <th>UserName</th>
            <th>Tournament</th>
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
            <h2 id="add_edit_price">Add Winner</h2>
            <form>
                <!-- Replace input field with styled select -->
                <div class="select-wrapper">
                    <input type="text" id="selectedUsername" placeholder="Select Winner Name" readonly>
                    <div class="options" id="options">
                        <select multiple id="usernames" name="usernames" onchange="updateSelectedUsernames()">
                        </select>
                    </div>
                </div>
                <label for="tournament">Tournament:</label><br>
                <input type="text" id="tournament" name="tournament"><br>
                <label for="price">Price:</label><br>
                <input disabled type="text" id="price" name="price"><br>
                <button type="button" onclick="savePrice()">Save</button>
            </form>
        </div>
    </div>

</div>

<script>

    $(document).ready(function () {
        //onloadMethod();
    });

    function AddButtonClick() {
        let domain = getDomain() + "/rest/game/gettournamentanduserdetails";

        let data = {}

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                //console.log(response);
                let user_list = response.data.users;
                let tournament_list = response.data.tournaments;
                let optiodate = "";
                if (user_list != null) {
                    for (let data in user_list) {
                        let id = data;
                        let name = user_list[data];
                        optiodate += '<option value="'+id+'">'+name+'</option>'
                    }
                    $('#usernames').html(optiodate);
                }

            }, error: function (error) {
                if (!error.responseJSON.success) {
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
            "id": id
        }

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                let priceList = response.data.price_list;
                if (priceList != null) {
                    for (let data in priceList) {
                        let name = priceList.price;
                        let id = priceList.id;
                        $('#price').val(name);
                        $('#price_id').val(id);
                    }
                }

            }, error: function (error) {
                if (!error.responseJSON.success) {
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }


    function savePrice() {
        let price = $('#price').val();
        let id = $('#price_id').val();

        let domain = getDomain() + "/rest/game/createprice";

        let data = {
            "price": price,
            "id": id
        }

        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                //console.log(response);
                if (response.success) {
                    window.location.reload();
                    closeModal();
                }

            }, error: function (error) {
                if (!error.responseJSON.success) {
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
        AddButtonClick();
    }

    // Update the input field with selected usernames
    function updateSelectedUsernames() {
        let selectedOptions = $('#usernames option:selected');
        let selectedUsernames = [];

        selectedOptions.each(function () {
            selectedUsernames.push($(this).text());
        });

        $('#selectedUsername').val(selectedUsernames.join(', '));
    }
</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
