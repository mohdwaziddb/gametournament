<div class="menu-icon" onclick="toggleMenu()">
    <span></span>
    <span></span>
    <span></span>
</div>
<input id="userid" type="hidden">
<input type="hidden" id="microservicequalifier" name="microservicequalifier" value="<%=request.getAttribute("microservicequalifier")%>">
<div class="slider" id="slider">
    <div class="menu">
        <span class="close-btn" onclick="toggleMenu()">X</span>
        <ul class="menu-list">
            <li class="menu-item" onclick="window.location.href='/game/homepage'">Home</li>
            <li class="menu-item" onclick="viewOpenNew('/game/userprofile')">Profile</li>
            <li class="menu-item" onclick="viewOpenNew('/game/activetournament')">Active Tournament</li>
            <li class="menu-item" onclick="viewOpenNew('/game/alljointournament')">All Joined Tournament</li>
            <li class="menu-item" onclick="viewOpenNew('/game/winningtournament')">Winning Tournament</li>
            <li class="menu-item">About</li>
            <li class="menu-item" onclick="logoutProfile()">Logout</li>
        </ul>
    </div>
</div>


<script>

function viewOpenNew(url){
    let data = "";
    let user_id = valuecheck($('#userid').val());
    data += "userid=" + user_id;
    //data += "&status_id=" + status_id;
    //window.location = url;
    window.location=(url+"?__code__="+ btoa(data));

    /*var data_promise = new Promise((resolve,reject)=>{
        if(employee_id!=undefined && employee_id != null && employee_id !=""  && employee_id.length > 0){
            resolve("success");
        } else {
            reject("Kindly Select Employee...");
        }
    });
    data_promise.then((result)=>{
        if(result=="success"){
            window.location=(str+"?encrypted_data="+ btoa(data));
        }
    }).catch((error)=>{
        showValidationMessage("Error", "error", error);
    });*/


}

</script>
