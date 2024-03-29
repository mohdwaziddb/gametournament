<%@ include file="/WEB-INF/jsp/dashboard/header.jsp"%>
<title>Homepage</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>


<div id="tournamentdata">

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
                        let attachment = tournamentListElement.attachment;
                        let date = tournamentListElement.date;
                        let description = tournamentListElement.description;
                        let endtime = tournamentListElement.endtime;
                        let maximumplayer = tournamentListElement.maximumplayer;
                        let minimumplayer = tournamentListElement.minimumplayer;
                        let starttime = tournamentListElement.starttime;
                        let secretcode = tournamentListElement.secretcode;
                        let game = tournamentListElement.game;
                        tabledate += '<div class="container">'
                            +'<div class="card">'
                            +'<img src="https://www.w3schools.com/w3images/hamburger.jpg" alt="Image 1">'
                            +'<div class="details">'
                            +'<h2>'+name+'</h2>'
                            +'<p>Price: '+price+'</p>'
                            +'<p>Game Date: '+date+'</p>'
                            +'<p>Start Time: '+starttime+'</p>'
                            +'<p>End Time: '+endtime+'</p>'
                            +'<div>' +
                            '<button class="join-btn" onclick="joinButCLick('+id+')">Join Game</button>' +
                            '<button style="background-color: blue;" class="join-btn" onclick="moreDetailsButClick('+id+')">More Details</button>' +
                            '</div>'
                            +'</div>'
                            +'</div>'
                            +'</div>'
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

    function joinButCLick(id){
        console.log(id)
    }

    function moreDetailsButClick(id){
        console.log(id)
    }




</script>

<%@ include file="/WEB-INF/jsp/dashboard/footer.jsp"%>
</body>
</html>
