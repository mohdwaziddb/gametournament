<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Tournament Winner</title>
<link id="manifest" rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">

    <button class="add-btn" onclick="window.location.href='/mvc/addtournamentwinner'">Add Tournament Winner</button>
    <table>
        <thead>
        <tr>
            <th>Tournament</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="tournamentwinner_table">

        </tbody>
    </table>

</div>

<script>

    $(document).ready(function () {
        onloadMethod();
    });

    function onloadMethod(){
        let domain = getDomain() + "/rest/game/get_winners_tournament_list";

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
                        let url = "'"+'/mvc/addtournamentwinner'+"'";
                        tabledate += '<tr>'
                            +'<td>'+name+'</td>'
                            +'<td>'+price+'</td>'
                            +'<td class="action-buttons">'
                            +'<input type="hidden" id="tournament_id" value="'+id+'">'
                            +'<button style="background-color: orange;" class="edit-btn" onclick="showData('+url+')">Show</button>'
                            +'</td>'
                            +'</tr>'
                    }
                    $('#tournamentwinner_table').html(tabledate);
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function showData(url){
        let data = "";
        let tournament_id = valuecheck($('#tournament_id').val());
        data += "tournament_id=" + tournament_id;
        window.location=(url+"?__code__="+ btoa(data));
    }
</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
