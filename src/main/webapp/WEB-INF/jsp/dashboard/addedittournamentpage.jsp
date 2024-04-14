<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Add/Edit Tournament</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">
  <h2 id="add_edit_tournament">Add Tournament</h2>
  <form>
    <label for="tournament_name">Name:</label><br>
    <input  type="text" id="tournament_name" name="tournament_name"><br>

    <input type="hidden" id="tournament_id">

    <label for="tournament_price">Price:</label><br><br>
    <select class="db_disableElement"  id="tournament_price"></select><br><br>
    <label for="tournament_game">Game:</label><br><br>
    <select class="db_disableElement" id="tournament_game"></select><br><br>



    <label for="tournament_description">Description:</label><br><br>
    <textarea class="textAreaCss" type="text" id="tournament_description" name="tournament_description"></textarea><br><br>

    <label for="tournament_date">Date:</label><br>
    <input  type="date" id="tournament_date" name="tournament_date"><br>
    <label for="tournament_image">Image:</label><br><br>
    <div class="file-input-container" style="width: 98%;">
      <div class="custom-file-input" onclick="document.getElementById('tournament_image').click()">
        <span id="imagename_placeholder" class="placeholder-text">Choose a file...</span>
        <input id="isimagechange" type="hidden"></input>
      </div>
      <input type="file" id="tournament_image" name="tournament_image" onchange="updatePlaceholder()">
    </div>
    <br><br>


    <label for="tournament_start">Start Time:</label><br>
    <input type="time" id="tournament_start" name="tournament_start"><br>
    <label for="tournament_end">End Time:</label><br>
    <input  type="time" id="tournament_end" name="tournament_end"><br>

    <label for="tournament_min_player">Min Player:</label><br>
    <input class="db_disableElement" type="number" id="tournament_min_player" name="tournament_min_player"><br>
    <label for="tournament_max_player">Max Player:</label><br>
    <input class="db_disableElement" type="number" id="tournament_max_player" name="tournament_max_player"><br>

    <label for="tournament_secret_code">Secret Code:</label><br><br>
    <textarea class="textAreaCss" type="text" id="tournament_secret_code" name="tournament_secret_code"></textarea><br><br>

    <label for="is_completed">Is Tournament Completed: </label>
    <input type="checkbox" id="is_completed" name="is_completed"><br><br>
    <button type="button" onclick="openModal()" id="winner_prize" style="width: 100%; background: #ff5454; font-weight: 600;">Add Winner Prizes</button><br>


    <button type="button" onclick="saveTournament()">Save</button>
  </form>
</div>

<div id="myModal" class="modal">
  <div class="modal-content">
    <span class="close" onclick="closeModal()">&times;</span>
    <h2 id="add_edit_winner_modal_head">Add Winner Prize</h2>
    <table id="prizeTable" class="dbTab">

    </table>
    <button class="db_disableElement db_dNoneElement" style="background: #ff5454;" type="button" onclick="addRow()">Add Multiple Winners</button>
  <div class="text-center"><button class="db_disableElement db_dNoneElement" type="button" onclick="saveWinnerData()">Save Winners</button></div>
  </div>
</div>

<script>

  $(document).ready(function () {
    onloadMethod();
  });
  var rowCount = 0;

  function openModal() {
    document.getElementById("myModal").style.display = "block";
    //addRow();
  }

  function closeModal() {
    document.getElementById("myModal").style.display = "none";
  }

  function addRow() {
    var table = document.getElementById("prizeTable");
    var newRow = table.insertRow();
    if(rowCount==-1){
      rowCount++
    }
    var count = rowCount;
    newRow.innerHTML = '<td><input class="db_disableElement" type="number" name="fromwinner_tr_' + count + '" id="fromwinner_tr_' + count + '" placeholder="From"></td>' +
            '<td><input class="db_disableElement" type="number" name="towinner_tr_' + count + '" id="towinner_tr_' + count + '" placeholder="To"></td>' +
            '<td width="40%"><input class="db_disableElement" type="number" name="winnerprize_tr_' + count + '" id="winnerprize_tr_' + count + '" placeholder="Winner Prize"></td>' +
            '<td><button style="padding: 5px" type="button" onclick="deleteRow(this)">Delete</button></td>';
    rowCount++;
    updateRowCount();
  }

  function deleteRow(button) {
    var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
    rowCount--;
    updateRowCount();
  }

  function updateRowCount() {
    var table = document.getElementById("prizeTable");
    var rows = table.getElementsByTagName("tr");
    for (var i = 0; i < rows.length; i++) {
      var inputs = rows[i].getElementsByTagName("input");
      for (var j = 0; j < inputs.length; j++) {
        var name = inputs[j].getAttribute("name");
        inputs[j].setAttribute("name", name.replace(/_[0-9]+$/, "_" + i));
        inputs[j].setAttribute("id", name.replace(/_[0-9]+$/, "_" + i));
      }
    }
  }

  var winner_prizes = [];
  function saveWinnerData() {

    var table = document.getElementById("prizeTable");
    var rows = table.rows;
    //let table_length = valuecheck($("#prizeTable").children().length);
    let table_length = $("#prizeTable").find("tr").length;
    for (var i = 0; i < table_length; i++) {
      var from_player = document.getElementById("fromwinner_tr_" + i).value;
      var to_player = document.getElementById("towinner_tr_" + i).value;
      var prize = document.getElementById("winnerprize_tr_" + i).value;
      if (from_player.trim() !== "" && to_player.trim() !== "" && prize.trim() !== "") {
        winner_prizes.push({from_player: from_player, to_player: to_player, prize: prize});
      }
    }
    closeModal();
  }

  var urlSearchParams = new URLSearchParams(window.location.search);
  var id = urlSearchParams.get('id');

  /*var params = Object.fromEntries(
          new URLSearchParams(window.location.search)
  )*/

  function onloadMethod(){
    let domain = getDomain() + "/rest/game/get_tournaments";
    if(id != null && id != undefined){
      $('#add_edit_tournament').text("Edit Tournament");
      //$('.db_disableElement').prop('disabled', true).css('background-color', '#ccc');
      //$('.db_dNoneElement').addClass('d-none');
      $('#winner_prize').text("Show Winner Prize");
      $('#add_edit_winner_modal_head').text("Show Winner Prize");
    } else {
      $('#add_edit_tournament').text("Add Tournament");
      $('#winner_prize').text("Add Winner Prize");
      $('#add_edit_winner_modal_head').text("Add Winner Prize");
    }

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
        let gameList = valuecheck(response.data.games_list);
        let priceList = valuecheck(response.data.price_list);
        let tournament = valuecheck(response.data.tournament);
        let winnerprizeslist = valuecheck(response.data.winnerprizeslist);

        let name1 = valuecheck(tournament.name);
        let priceid1 = valuecheck(tournament.priceid);
        let description1 = valuecheck(tournament.description);
        let date1 = valuecheck(tournament.date);
        let endtime1 = valuecheck(tournament.endtime);
        let gameid1 = valuecheck(tournament.gameid);
        let maximumplayer1 = valuecheck(tournament.maximumplayer);
        let minimumplayer1 = valuecheck(tournament.minimumplayer);
        let secretcode1 = valuecheck(tournament.secretcode);
        let starttime1 = valuecheck(tournament.starttime);
        let is_completed = valuecheck(tournament.iscompleted);
        let attachment = valuecheck(tournament.attachment);
        let winnerprizes = valuecheck(tournament.winnerprizes);
        let winnerprizes_JSON = "";
        let winnerprizes_Array = "";
        if(winnerprizes != null && winnerprizes != ""){
          winnerprizes_JSON = valuecheck(JSON.parse(winnerprizes));
          winnerprizes_Array = valuecheck(winnerprizes_JSON.winner_prize_array);
        }
        let imageDateTime = "";
        let imageFileName = ""
        if(attachment != undefined && attachment != null){
          let split = attachment.split('__');
          imageDateTime = split[0];
          imageFileName = split[1];
        }

        $('#imagename_placeholder').text(imageFileName);
        $('#tournament_name').val(name1);
        $('#tournament_description').val(description1);
        $('#tournament_date').val(date1);
        $('#tournament_start').val(starttime1);
        $('#tournament_end').val(endtime1);
        $('#tournament_min_player').val(minimumplayer1);
        $('#tournament_max_player').val(maximumplayer1);
        $('#tournament_secret_code').val(secretcode1);
        if(is_completed){
          $('#is_completed').prop('checked',true);
        }

        let data1 = "<option>Select Game</option>";
        if(gameList != null){
          for (let data in gameList) {
            let gameListElement = gameList[data];
            let name = gameListElement.name;
            let id = gameListElement.id;
            if(gameid1 == id){
              data1 += '<option selected value="'+id+'">'+name+'</option>';
            } else {
              data1 += '<option value="'+id+'">'+name+'</option>';
            }

          }
          $('#tournament_game').html(data1);
        }

        let data2 = "<option>Select Price</option>";
        if(priceList != null){
          for (let data in priceList) {
            let priceListElement = priceList[data];
            let price = priceListElement.price;
            let id = priceListElement.id;
            if(priceid1 == id){
              data2 += '<option selected value="'+id+'">'+price+'</option>';
            } else {
              data2 += '<option value="'+id+'">'+price+'</option>';
            }

          }
          $('#tournament_price').html(data2);
        }

        let disabled_Element = "";
        let disabled_Element_color = "";
        /*if(id != null && id != undefined){
          disabled_Element = " disabled "
          disabled_Element_color = " d-none "
        }*/

        let winnertabledate="";
        if(winnerprizeslist != null){
          for (let data in winnerprizeslist) {
            let obj = winnerprizeslist[data];
            let from_player = obj.fromwinner;
            let to_player = obj.towinner;
            let prize = obj.prize;
            winnertabledate += '<tr><td><input '+disabled_Element+' value="'+from_player+'" type="number" name="fromwinner_tr_' + data + '" id="fromwinner_tr_' + data + '" placeholder="From"></td>' +
                    '<td><input  '+disabled_Element+' value="'+to_player+'" type="number" name="towinner_tr_' + data + '" id="towinner_tr_' + data + '" placeholder="To"></td>' +
                    '<td width="40%"><input  '+disabled_Element+' value="'+prize+'" type="number" name="winnerprize_tr_' + data + '" id="winnerprize_tr_' + data + '" placeholder="Winner Prize"></td>' +
                    '<td><button '+disabled_Element+' class="'+disabled_Element_color+'" style="padding: 5px" type="button" onclick="deleteRow(this)">Delete</button></td></tr>';

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

  function updatePlaceholder() {
    $('#isimagechange').val(true);
    const input = document.getElementById('tournament_image');
    const placeholder = document.querySelector('.placeholder-text');

    if (input.files && input.files.length > 0) {
      placeholder.textContent = input.files[0].name;
    } else {
      placeholder.textContent = 'Choose a file...';
    }
  }

  function saveTournament(){
    let tournament_name = $('#tournament_name').val();
    let tournament_price = $('#tournament_price').val();
    let tournament_game = $('#tournament_game').val();
    let tournament_description = $('#tournament_description').val();
    let tournament_date = $('#tournament_date').val();
    let tournament_image = $('#tournament_image').val();
    let tournament_start = $('#tournament_start').val();
    let tournament_end = $('#tournament_end').val();
    let tournament_min_player = $('#tournament_min_player').val();
    let tournament_max_player = $('#tournament_max_player').val();
    let tournament_secret_code = $('#tournament_secret_code').val();
    let tournament_isimagechange = $('#isimagechange').val();

    if(tournament_name==undefined || tournament_name==null || tournament_name==""){
      return showValidationMessage("ERROR", "error", "Name cannot be blank");
    }
    if(tournament_price==undefined || tournament_price==null || tournament_price==""){
      return showValidationMessage("ERROR", "error", "Price cannot be blank");
    }
    if(tournament_game==undefined || tournament_game==null || tournament_game==""){
      return showValidationMessage("ERROR", "error", "Game cannot be blank");
    }
    if(tournament_description==undefined || tournament_description==null || tournament_description==""){
      return showValidationMessage("ERROR", "error", "Description cannot be blank");
    }
    if(tournament_date==undefined || tournament_date==null || tournament_date==""){
      return showValidationMessage("ERROR", "error", "Date cannot be blank");
    }
    if(tournament_start==undefined || tournament_start==null || tournament_start==""){
      return showValidationMessage("ERROR", "error", "Start Time cannot be blank");
    }
    if(tournament_end==undefined || tournament_end==null || tournament_end==""){
      return showValidationMessage("ERROR", "error", "End Time cannot be blank");
    }
    if(tournament_min_player==undefined || tournament_min_player==null || tournament_min_player==""){
      return showValidationMessage("ERROR", "error", "Minimum Player cannot be blank");
    }
    if(tournament_max_player==undefined || tournament_max_player==null || tournament_max_player==""){
      return showValidationMessage("ERROR", "error", "Maximum Player cannot be blank");
    }

    let is_completed = $('#is_completed').prop('checked');

    let domain = getDomain() + "/rest/game/createtournament";

    var formData = new FormData();
    formData.append('file', $('#tournament_image').prop('files')[0]);
    formData.append('name', tournament_name);
    formData.append('id', id);
    formData.append('price', tournament_price);
    formData.append('game', tournament_game);
    formData.append('description', tournament_description);
    formData.append('date', tournament_date);
    formData.append('starttime', tournament_start);
    formData.append('endtime', tournament_end);
    formData.append('minimum_player', tournament_min_player);
    formData.append('maximum_player', tournament_max_player);
    formData.append('secret_code', tournament_secret_code);
    formData.append('is_completed', is_completed);
    formData.append('isimagechange', tournament_isimagechange);
    formData.append('winner_prizes', JSON.stringify(winner_prizes));

    $.ajax({
      type: "POST",
      url: domain,
      //contentType: 'application/json',
      enctype: 'multipart/form-data',
      headers: getHeaders("POST"),
      processData: false,
      contentType: false,
      data: formData,
      success: function (response) {
        //console.log(response);
        if(response.success){
          window.location.href="/mvc/createtournament";
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
