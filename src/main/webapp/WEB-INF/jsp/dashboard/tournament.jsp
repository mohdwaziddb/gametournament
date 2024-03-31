<%@ include file="/WEB-INF/jsp/dashboard/header.jsp"%>
<title>Homepage</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>
    select {
        width: 100%;
        background: #ffffff;
        color: #444;
        padding: 8px; /* Add padding for better appearance */
        border: 1px solid #ccc; /* Add a border */
        border-radius: 5px; /* Optional: Add border radius for rounded corners */
        box-sizing: border-box; /* Make sure padding and border are included in the element's total width */
    }

    /* Hide default file input appearance */
    input[type="file"] {
        display: none;
    }

    /* Style for the custom file input container */
    .file-input-container {
        position: relative;
        display: inline-block;
    }

    /* Style for the custom file input */
    .custom-file-input {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
        background: #fff;
        height: 12px;
        color: #444;
    }

    /* Style for the placeholder text */
    .placeholder-text {
        position: absolute;
        top: 50%;
        left: 10px;
        transform: translateY(-50%);
        color: #999;
    }
</style>

<div class="container">
    <h2 id="add_edit_tournament">Tournament</h2>
    <form>
        <%--<label for="tournament_name">Name:</label><br>
        <input disabled type="text" id="tournament_name" name="tournament_name"><br>

        <input type="hidden" id="tournament_id">--%>

        <label for="tournament_price">Price:</label><br><br>
        <input disabled type="text" id="tournament_price" name="tournament_price"><br>

        <label for="tournament_game">Game:</label><br><br>
        <input disabled type="text" id="tournament_game" name="tournament_game"><br>

        <label for="tournament_description">Description:</label><br><br>
        <textarea disabled class="textAreaCss" type="text" id="tournament_description" name="tournament_description"></textarea><br><br>

        <label for="tournament_date">Date:</label><br>
        <input disabled type="date" id="tournament_date" name="tournament_date"><br>
        <label for="tournament_image">Image:</label><br><br>
        <div class="file-input-container" style="width: 98%;">
            <div class="custom-file-input" onclick="document.getElementById('tournament_image').click()">
                <span class="placeholder-text">Choose a file...</span>
            </div>
            <input type="file" id="tournament_image" name="tournament_image" onchange="updatePlaceholder()">
        </div>
        <br><br>


        <label for="tournament_start">Start Time:</label><br>
        <input disabled type="time" id="tournament_start" name="tournament_start"><br>
        <label for="tournament_end">End Time:</label><br>
        <input disabled type="time" id="tournament_end" name="tournament_end"><br>

        <label for="tournament_min_player">Min Player:</label><br>
        <input disabled type="number" id="tournament_min_player" name="tournament_min_player"><br>
        <label for="tournament_max_player">Max Player:</label><br>
        <input disabled type="number" id="tournament_max_player" name="tournament_max_player"><br>


        <%--<input type="file" id="tournament_image" name="tournament_image"><br>--%>

        <label for="tournament_secret_code">Secret Code:</label><br><br>
        <textarea disabled class="textAreaCss" type="text" id="tournament_secret_code" name="tournament_secret_code"></textarea><br>

        <button type="button" onclick="joinTournament()" id="joinButt">Join Tournament</button>

    </form>
</div>

<script>

    $(document).ready(function () {
        onloadMethod();
    });
    var urlSearchParams = new URLSearchParams(window.location.search);
    var id = urlSearchParams.get('id');

    function onloadMethod(){
        let domain = getDomain() + "/rest/game/get_tournaments_foruser";

        let data = {
            "id":id,
        }

        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                //console.log(response);
                let tournament = valuecheck(response.data.tournament);

                let name1 = valuecheck(tournament.name);
                let price1 = valuecheck(tournament.price);
                let description1 = valuecheck(tournament.description);
                let date1 = valuecheck(tournament.date);
                let endtime1 = valuecheck(tournament.endtime);
                let game1 = valuecheck(tournament.game);
                let maximumplayer1 = valuecheck(tournament.maximumplayer);
                let minimumplayer1 = valuecheck(tournament.minimumplayer);
                let secretcode1 = valuecheck(tournament.secretcode);
                let starttime1 = valuecheck(tournament.starttime);
                let isjoin1 = valuecheck(tournament.isjoin);

                //$('#tournament_name').val(name1);
                $('#add_edit_tournament').text(name1);
                $('#tournament_description').val(description1);
                $('#tournament_date').val(date1);
                $('#tournament_start').val(starttime1);
                $('#tournament_end').val(endtime1);
                $('#tournament_min_player').val(minimumplayer1);
                $('#tournament_max_player').val(maximumplayer1);
                $('#tournament_secret_code').val(secretcode1);

                $('#tournament_game').val(game1);
                $('#tournament_price').val(price1);

                if(isjoin1){
                    $('#joinButt').text("Already Joined");
                    $('#joinButt').removeAttr('onclick');
                    $('#joinButt').css('background','#7b7d7c')
                } else {
                    $('#joinButt').text("Join Tournament (Rs. "+price1+")");
                }


            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }
            }
        });
    }

    function joinTournament(){
        //console.log(id);
        let domain = getDomain() + "/rest/game/joining_tournament";
        let data = {
            "id": id,
        }
        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
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

</script>

<%@ include file="/WEB-INF/jsp/dashboard/footer.jsp"%>
</body>
</html>
