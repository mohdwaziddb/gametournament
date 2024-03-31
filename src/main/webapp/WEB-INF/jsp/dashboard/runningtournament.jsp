<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Running Tournament</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>
</style>
<body>

<div class="icon" onclick="toggleLogin(event)">
    <i class="fas fa-user"></i>
</div>
<div class="login-details" id="loginDetails">
    <p>Name: John Doe</p>
    <p>Email: johndoe@example.com</p>
    <p>Phone: +1234567890</p>
    <p>Username: johndoe123</p>
</div>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>Sr No.</th>
            <th>Name</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="tournamentdata">

        </tbody>
    </table>
</div>

<script>

    $(document).ready(function () {
        onloadMethod();
    });


    function onloadMethod(){
        let domain = getDomain() + "/rest/game/get_tournaments_foruser";

        let data = {
        }

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                let tournament_list = response.data.list;
                let tabledate = "";
                let srno=1;
                if(tournament_list != null && tournament_list.length>0){
                    for (let data in tournament_list) {
                        let tournamentListElement = tournament_list[data];
                        let name = tournamentListElement.name;
                        let price = tournamentListElement.price;
                        let id = tournamentListElement.id;
                        tabledate += '<tr>'
                            +'<td>'+srno+++'</td>'
                            +'<td>'+name+'</td>'
                            +'<td>'+price+'</td>'
                            +'<td class="action-buttons">'
                            +'<button class="edit-btn" onclick="editBut('+id+')">Edit</button>'
                            +'<button class="delete-btn" onclick="deleteBut('+id+')">Delete</button>'
                            +'</td>'
                            +'</tr>'
                    }
                    $('#tournamentdata').html(tabledate);
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function deleteBut(id){
        let domain = getDomain() + "/rest/game/deletetournament";
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

    function editBut(id){
        window.location.href='/mvc/addedittournamentpage?id='+id;
    }

</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
