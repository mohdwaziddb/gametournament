
<div class="icon" onclick="toggleLogin(event); showAdminProfile()" >
    <i class="fas fa-user"></i>
</div>
<div class="login-details" id="loginDetails">
    <p>Name: <span id="admin_name"></span></p>
    <p>Email: <span id="admin_email"></span></p>
    <p>Phone: <span id="admin_phone"></span></p>
    <p>Username: <span id="admin_username"></span></p>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript" src="/Common/js/common.js"></script>
<script type="text/javascript" src="/Common/js/serviceworkerregister.js"></script>
<script type="text/javascript" src="/Common/js/jquery.toast.js"></script>
<script>
    // Get the modal
    var modal = document.getElementById("myModal");

    // Get the button that opens the modal
    var btn = document.getElementsByClassName("add-btn")[0];

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];



    // When the user clicks on <span> (x), close the modal
    function closeModal() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    function saveProduct() {
        // Here you can add your logic to save the product
        // For now, let's just close the modal
        closeModal();
    }
</script>

<script>
    function toggleLogin(event) {
        var loginDetails = document.getElementById("loginDetails");
        if (loginDetails.style.display == "none" || loginDetails.style.display == "") {
            loginDetails.style.display = "block";
            event.stopPropagation(); // Prevent the event from bubbling up to the document
            document.addEventListener('click', closeLoginOutside);
        } else {
            loginDetails.style.display = "none";
            document.removeEventListener('click', closeLoginOutside);
        }
    }

    function closeLoginOutside(event) {
        var loginDetails = document.getElementById("loginDetails");
        if (!loginDetails.contains(event.target)) {
            loginDetails.style.display = "none";
            document.removeEventListener('click', closeLoginOutside);
        }
    }


    function showAdminProfile(){
        var username = localStorage.getItem("username");

        let data = {
            'username':username,
        };
        let domain = getDomain() + "/rest/authentication/admindetailsbyusername";
        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                if(response.success){
                    let obj = response.data;
                    let username1 = valuecheck(obj.username);
                    let emailid1 = valuecheck(obj.emailid);
                    let mobileno1 = valuecheck(obj.mobileno);
                    let name1 = valuecheck(obj.name);

                    $('#admin_name').text(name1);
                    $('#admin_email').text(emailid1);
                    $('#admin_phone').text(mobileno1);
                    $('#admin_username').text(username1);

                }
            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }
</script>


