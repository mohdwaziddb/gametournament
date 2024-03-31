<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Payment History</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

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

  <table>
    <thead>
    <tr>
      <th>Sr No.</th>
      <th>Username</th>
      <%--<th>Game</th>--%>
      <th>Tournament</th>
      <th>Amt</th>
    </tr>
    </thead>
    <tbody id="historydata">

    </tbody>
  </table>

</div>

<script>

  $(document).ready(function () {
    onloadMethod();
  });

  function onloadMethod(){
    let domain = getDomain() + "/rest/game/getplayerdetails";
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
        let list = response.data.list;
        let tabledate = "";
        let srno=1;
        if(list != null && list.length>0){
          for (let data in list) {
            let list_date = list[data];
            let name = list_date.name;
            let game = list_date.game;
            let amount = list_date.amount;
            let tournament = list_date.tournament;
            tabledate += '<tr>'
                    +'<td>'+srno+++'</td>'
                    +'<td>'+name+'</td>'
                    //+'<td>'+game+'</td>'
                    +'<td>'+tournament+'</td>'
                    +'<td>'+amount+'</td>'
                    +'</tr>'
          }
          $('#historydata').html(tabledate);
        }

      }, error: function (error) {
        if(!error.responseJSON.success){
          showValidationMessage("ERROR", "error", error.responseJSON.message);
        }

      }
    });
  }
</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
