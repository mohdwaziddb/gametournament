<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Add/Edit Tournament Winner</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
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
    <h2 id="add_edit_tournament">Add Tournament Winner</h2>
    <form>
        <label for="tournament_name">Tournament:</label><br>
        <select onchange="chnageTournamentWinnerPrize(this,'')" class="" id="tournament_name"></select><br><br>

        <button type="button" onclick="openModal()" id="winner_prize" style="width: 100%; background: #ff5454; font-weight: 600;">Add Winner Prizes</button><br>

<%--
        <button type="button" onclick="saveTournament()">Save</button>--%>
    </form>
</div>

<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="add_tournament_modal_header"></h2>
        <table  class="dbTab">
            <thead><tr>
                <td>From</td>
                <td>To</td>
                <td>Prize (Amt)</td>
                <td>
                Select Winners
                </td>
            </tr>
            </thead>
            <tbody id="prizeTable">

            </tbody>
        </table>
        <div class="text-center"><button type="button" onclick="saveWinnerActualData()">Save Winners</button></div>
    </div>
</div>

<script>

    var urlSearchParams = new URLSearchParams(window.location.search);
    var encryptedData = urlSearchParams.get('__code__');
    var decodedData = atob(encryptedData);

    let decorderdate_split = decodedData.split("&");
    var storing_decordeddata_obj = {};
    for(var i in decorderdate_split){
        let key_value_both = decorderdate_split[i].split("=");
        let key = key_value_both[0];
        let value = key_value_both[1];
        storing_decordeddata_obj[key]=value;
    }

    var tournament_id_global = storing_decordeddata_obj.tournament_id;

    $(document).ready(function () {
        onloadMethod();
    });


    function openModal() {
        let val = $('#tournament_name').val();
        if(val== null || val == "" || val == "-1"){
            return showValidationMessage("ERROR", "error", "Select Tournament...");
        }
        document.getElementById("myModal").style.display = "block";
        if(tournament_id_global != null && tournament_id_global != ""){
            chnageTournamentWinnerPrize('',tournament_id_global);
        }
    }

    var prize_id_global = "";

    function chnageTournamentWinnerPrize(source,tournament_id_global){
        let tournamentid = valuecheck(source.value);
        if(tournament_id_global != null && tournament_id_global != ""){
            tournamentid = tournament_id_global;
        }
        let data = {
            "tournamentid":tournamentid,
        }
        let domain = getDomain() + "/rest/game/get_winners_data";
        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                let winnerprizeslist = valuecheck(response.data.winner_prize_list);
                let users_list = valuecheck(response.data.users_list);
                let tournament_name = valuecheck(response.data.tournament_name);
                $('#add_tournament_modal_header').text("Add "+tournament_name +" Winner Prizes ")
                let user_list_data = "";
                if(users_list != null && users_list != undefined){
                    for (let i in users_list){
                        let obj = valuecheck(users_list[i]);
                        let employee_id = valuecheck(obj.id);
                        let employee_name = valuecheck(obj.name);
                        user_list_data += '<option value="'+employee_id+'">'+employee_name+'<option>'

                    }
                }
                let winnertabledate="";
                if(winnerprizeslist != null){
                    for (let data in winnerprizeslist) {
                        let obj = winnerprizeslist[data];
                        let from_player = obj.fromwinner;
                        let prize_id = obj.id;

                        if (prize_id_global !== "") {
                            prize_id_global += ",";
                        }

                        prize_id_global += prize_id;
                        let to_player = obj.towinner;
                        let prize = obj.prize;
                        winnertabledate += '<tr><td id="prize_id_'+prize_id+'"><input disabled value="'+from_player+'" type="number" placeholder="From"></td>' +
                            '<td><input disabled value="'+to_player+'" type="number" placeholder="To"></td>' +
                            '<td><input disabled value="'+prize+'" type="number" placeholder="Winner Prize"></td>' +
                            '<td width="40%">' +
                            '<div class="select-wrapper">'+
                            '<input type="text" id="selectedUsername_'+prize_id+'" placeholder="Select Winner Name" readonly>'+
                            '<div class="options" id="options">'+
                                '<select multiple id="usernames_'+prize_id+'" name="usernames" onchange="updateSelectedUsernames('+prize_id+')">'+
                                    user_list_data+
                                '</select>'+
                            '</div>'+
                            '</div>' +
                            '</td></tr>';

                    }
                    $('#prizeTable').html(winnertabledate);
                }


            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    // Update the input field with selected usernames
    function updateSelectedUsernames(prizeid) {
        let selectedOptions = $('#usernames_'+prizeid+' option:selected');
        let selectedUsernames = [];

        selectedOptions.each(function () {
            selectedUsernames.push($(this).text());
        });

        $('#selectedUsername_'+prizeid+'').val(selectedUsernames.join(', '));
    }

    function closeModal() {
        document.getElementById("myModal").style.display = "none";
    }

    function onloadMethod(){
        let domain = getDomain() + "/rest/game/get_active_tournament";
        if(tournament_id_global != null && tournament_id_global != undefined){
            $('#add_edit_tournament').text("Edit Tournament Winner");
            $('#winner_prize').text("Show Winner Prizes");
        } else {
            $('#add_edit_tournament').text("Add Tournament Winner");
            $('#winner_prize').text("Add Winner Prizes");
        }

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
                let tournament_list = valuecheck(response.data.tournament_list);

                let data1 = "<option value='-1'>Select Tournament</option>";
                if(tournament_list != null){
                    for (let data in tournament_list) {
                        let obj = tournament_list[data];
                        let name = obj.name;
                        let id = obj.id;
                        if(tournament_id_global == id){
                            data1 += '<option selected value="'+id+'">'+name+'</option>';
                        } else {
                            data1 += '<option value="'+id+'">'+name+'</option>';
                        }
                    }
                    $('#tournament_name').html(data1);
                }
            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }
            }
        });
    }

    function saveWinnerActualData(){
        let domain = getDomain() + "/rest/game/save_winners_data";
        if (prize_id_global==null || prize_id_global==""){
            return showValidationMessage("ERROR", "error", "Prizes are Empty...");
        }

        let data_list = [];
        if(prize_id_global != null){
            let split_prize_ids = prize_id_global.split(',');
            for (let i in split_prize_ids){
                let single_prizeid = split_prize_ids[i];
                let selectedvalue = $('#usernames_'+single_prizeid).val();
                if(selectedvalue == null || selectedvalue == ""){
                    return showValidationMessage("ERROR", "error", "Kindly select winner in all prizes..");
                }
                let map = {
                    "id":single_prizeid,
                    "winneruserid":convertArrayTCommaSeparatedString(selectedvalue),
                }
                data_list.push(map);
            }
        }


        var data = {
            "list":data_list,
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
                    window.location.href="/mvc/tournamentwinner";
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
