<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Create Game</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">
    <button class="add-btn" onclick="openModal()">Add Game</button>
    <table>
        <thead>
        <tr>
            <th>Sr No.</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="gamedata">

        </tbody>
    </table>


    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2 id="add_edit_game">Add Game</h2>
            <form>
                <label for="name">Name:</label><br>
                <input type="text" id="name" name="name"><br>
                <input type="hidden" id="game_id">
                <label for="description">Description:</label><br>
                <textarea class="textAreaCss" type="text" id="description" name="description"></textarea><br><br>
                <%--<label for="banner_image">Banner Image:</label><br>
                <input type="file" id="banner_image" name="banner_image"><br><br>--%>
                <button type="button" onclick="saveGame()">Save</button>
            </form>
        </div>
    </div>
</div>

<script>

    $(document).ready(function () {
        onloadMethod();
    });

    function onloadMethod(){
        let domain = getDomain() + "/rest/game/games_list";

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
                let gameList = response.data.games_list;
                let tabledate = "";
                let srno=1;
                if(gameList != null && gameList.length>0){
                    for (let data in gameList) {
                        let gameListElement = gameList[data];
                        let name = gameListElement.name;
                        let id = gameListElement.id;
                        tabledate += '<tr>'
                            +'<td>'+srno+++'</td>'
                            +'<td>'+name+'</td>'
                            +'<td class="action-buttons">'
                                +'<button class="edit-btn" onclick="editBut('+id+')">Edit</button>'
                                +'<button class="delete-btn" onclick="deleteBut('+id+')">Delete</button>'
                            +'</td>'
                            +'</tr>'
                    }
                    $('#gamedata').html(tabledate);
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
        $('#add_edit_game').text('Edit Game');
        let domain = getDomain() + "/rest/game/games_list";

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
                let gameList = response.data.games_list;
                if(gameList != null){
                    for (let data in gameList) {
                        let name = gameList.name;
                        let id = gameList.id;
                        let description = gameList.description;
                        $('#name').val(name);
                        $('#description').val(description);
                        $('#game_id').val(id);
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
        let domain = getDomain() + "/rest/game/deletegame";
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


    function saveGame(){
        let gamename = $('#name').val();
        let gamedescription = $('#description').val();
        let id = $('#game_id').val();
        //let banner_image = $('#banner_image').prop('files')[0];

        let domain = getDomain() + "/rest/game/creategame";

        let data = {
            "name":gamename,
            "description":gamedescription,
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
        $('#add_edit_game').text('Add Game');
        $('#game_id').val('');
        $('#name').val('');
        $('#description').val('');
    }
</script>

<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
