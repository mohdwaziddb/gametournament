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
                let userid = response.data.userid;
                let tabledate = "";
                let srno=1;
                $('#userid').val(userid);
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
                        let isjoin = tournamentListElement.isjoin;

                        let isjoin_data = "";
                        if(isjoin){
                            isjoin_data += '<button style="background: #7b7d7c" class="join-btn">Already Joined</button>' +
                                '<button style="background-color: blue;" class="join-btn" onclick="moreDetailsButClick('+id+')">More Details</button>'
                        } else {
                            isjoin_data += '<button class="join-btn" onclick="joinButCLick('+id+','+price+')">Join Game (Rs. '+price+')</button>' +
                                            '<button style="background-color: blue;" class="join-btn" onclick="moreDetailsButClick('+id+')">More Details</button>'
                        }

                        tabledate += '<div class="container">'
                            +'<div class="card">'
                            //+'<img src="https://www.w3schools.com/w3images/hamburger.jpg" alt="Image 1">'
                            +'<img src="'+attachment+'" alt="Image">'
                            +'<div class="details">'
                            +'<h2>'+name+'</h2>'
                            +'<p>Price: '+price+'</p>'
                            +'<p>Game Date: '+date+'</p>'
                            +'<p>Start Time: '+starttime+'</p>'
                            +'<p>End Time: '+endtime+'</p>'
                            +'<div>' +
                            isjoin_data+
                            '</div>'
                            +'</div>'
                            +'</div>'
                            +'</div>'
                    }
                    $('#tournamentdata').html(tabledate);
                } else {
                    tabledate += '<div class="centered-div">No data available</div>';
                    $('#tournamentdata').html(tabledate);
                }

                if(userid==null || userid==""){
                    $('.menu-icon').addClass("d-none");
                }

            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function joinButCLick(tournamentid,price){
        let domain = getDomain() + "/rest/game/createtransaction";
        let data = {
            "amount": price,
        }
        $.ajax({
            type: "GET",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("GET"),
            data: data,
            success: function (response) {
                let emailid = valuecheck(response.emailid);
                let mobileno = valuecheck(response.mobileno);
                let username = valuecheck(response.username);
                let options = {
                    "order_id": response.orderid,
                    "currency": response.currency,
                    "amount": response.amount,
                    "key": response.key,
                    "name": "Tech Parichay",
                    "description": "Tech Parichay -- Tech that you love",
                    "handler": function(handleresponse) {
                        let razorpay_order_id = valuecheck(handleresponse.razorpay_order_id);
                        let razorpay_payment_id = valuecheck(handleresponse.razorpay_payment_id);
                        let razorpay_signature = valuecheck(handleresponse.razorpay_signature);
                        let key_secret = valuecheck(response.key_secret);
                        let orderid = valuecheck(response.orderid);

                        //let generatedSignature = CryptoJS.HmacSHA256(razorpay_order_id + "|" + razorpay_payment_id, key).toString(CryptoJS.enc.Hex);
                        let data = orderid + "|" + razorpay_payment_id;
                        let generatedSignature = CryptoJS.HmacSHA256(data, key_secret).toString(CryptoJS.enc.Hex);

                        if (generatedSignature != razorpay_signature) {
                            showValidationMessage("ERROR", "error", "Transaction is not valid");
                        } else {
                            let validresp = {
                                "message":"Transaction Completed",
                                "success":true,
                                "tournamentid":tournamentid,
                                "transactionid":razorpay_payment_id,
                            }
                            handlePaymentValidation(validresp);
                        }

                        //console.log(response);
                    },
                    "theme": {
                        "color": "#444"
                    },
                    "prefill": {
                        "name": username,
                        "email": emailid,
                        "contact": mobileno,
                    }
                };
                var razorpayObject = new Razorpay(options);
                razorpayObject.open();
                razorpayObject.on('payment.failed', function (response){
                    showValidationMessage("ERROR", "error", response.error.description);
                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);
                });
                /*document.getElementById('rzp-button1').onclick = function(e){
                    rzp1.open();
                    e.preventDefault();
                }*/
                //e.preventDefault();
                //console.log()
            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }
            }
        });
    }

    function handlePaymentValidation(response){
        if(response.success){
            let tournamentid = response.tournamentid;
            let transactionid = response.transactionid;
            joinButCLick1(tournamentid,transactionid);

        }
        //console.log(response);
    }


    function joinButCLick1(id,transactionid){
        //console.log(id);
        let domain = getDomain() + "/rest/game/joining_tournament";
        let data = {
            "id": id,
            "transactionid": transactionid,
            "userid": $('#userid').val(),
        }
        $.ajax({
            type: "POST",
            url: domain,
            contentType: 'application/json',
            headers: getHeaders("POST"),
            data: JSON.stringify(data),
            success: function (response) {
                if(response.success){
                    showValidationMessage("Success", "success", "Tournament Joined");
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }
            }, error: function (error) {
                if(!error.responseJSON.success){
                    showValidationMessage("ERROR", "error", error.responseJSON.message);
                }

            }
        });
    }

    function moreDetailsButClick(id){
        window.location.href="/game/tournament?id="+id
    }




</script>



<%@ include file="/WEB-INF/jsp/dashboard/footer.jsp"%>
<%@ include file="/WEB-INF/jsp/dashboard/userviewslider.jsp"%>
</body>
</html>
