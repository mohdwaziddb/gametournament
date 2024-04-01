<%@ include file="/WEB-INF/jsp/dashboard/header.jsp"%>
<title>Homepage</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>
  .form-container {
    display: flex;
    flex-direction: column;
  }

  .form-row {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }

  .form-label {
    margin-right: 10px;
  }

  .form-input {
    flex: 1;
    padding: 5px !important;
  }

  .form-button {
    width: 100%;
    padding: 8px 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    cursor: pointer;
  }


</style>
<body>


<div class="container">
  <h1>User Profile</h1>
  <div id="profile-form" class="form-container">
    <div class="form-row">
      <label for="name" class="form-label">Username:</label>
      <input disabled type="text" id="name" class="form-input">
    </div>
    <div class="form-row">
      <label for="firstname" class="form-label">First Name:</label>
      <input type="text" id="firstname" class="form-input">
    </div>
    <div class="form-row">
      <label for="lastname" class="form-label">Last Name:</label>
      <input type="text" id="lastname" class="form-input">
    </div>
    <div class="form-row">
      <label for="email" class="form-label">Email:</label>
      <input type="email" id="email" class="form-input">
    </div>
    <div class="form-row">
      <label for="phone" class="form-label">Phone:</label>
      <input type="text" id="phone" class="form-input">
    </div>
    <button id="save-btn" class="form-button" onclick="saveUserData()">Save</button>
  </div>



</div>

<script>


  $(document).ready(function () {
    onloadMethod();
  });

  function saveUserData(){
    let username = $('#username').val();
    let firstname = $('#firstname').val();
    let lastname = $('#lastname').val();
    let email = $('#email').val();
    let phone = $('#phone').val();

    if(firstname==undefined || firstname==null || firstname==""){
      return showValidationMessage("ERROR", "error", "First Name cannot be blank");
    }

    if(lastname==undefined || lastname==null || lastname==""){
      return showValidationMessage("ERROR", "error", "Last Name cannot be blank");
    }

    if(email==undefined || email==null || email==""){
      return showValidationMessage("ERROR", "error", "Email Id cannot be blank");
    }

    if(phone==undefined || phone==null || phone==""){
      return showValidationMessage("ERROR", "error", "Phone No. cannot be blank");
    }

    let data = {
      "username":username,
      "firstname":firstname,
      "lastname":lastname,
      "emailid":email,
      "phoneno":phone
    }

    let domain = getDomain() + "/rest/game/saveuserdetailsbyid";

    $.ajax({
      type: "POST",
      url: domain,
      contentType: 'application/json',
      headers: getHeaders("POST"),
      data: data,
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


  function onloadMethod(){
    let domain = getDomain() + "/rest/game/getuserdetailsbyid";

    let data = {
      "username":localStorage.getItem("username")
    }

    $.ajax({
      type: "GET",
      url: domain,
      contentType: 'application/json',
      headers: getHeaders("GET"),
      data: data,
      success: function (response) {
        let userdetail_object = response.data.userdetail;



      }, error: function (error) {
        if(!error.responseJSON.success){
          showValidationMessage("ERROR", "error", error.responseJSON.message);
        }

      }
    });
  }





</script>
<%@ include file="/WEB-INF/jsp/dashboard/footer.jsp"%>
<%@ include file="/WEB-INF/jsp/dashboard/userviewslider.jsp"%>

</body>
</html>
