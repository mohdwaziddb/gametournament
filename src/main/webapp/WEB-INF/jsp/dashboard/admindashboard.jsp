<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Admin-Dashboard</title>
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
    <div class="box-container">
        <div class="box" onclick="window.location.href='/mvc/creategame'">
            <i class="fas fa-gamepad"></i>
            <p>Create Game</p>
        </div>
        <div class="box" onclick="window.location.href='/mvc/createprice'">
            <i class="fas fa-money-bill-wave"></i>
            <p>Create Price</p>
        </div>
        <div class="box" onclick="window.location.href='/mvc/createtournament'">
            <i class="fas fa-cogs"></i>
            <p>Create Tournament</p>
        </div>
        <div class="box" onclick="window.location.href='/mvc/runningtournament'">
            <i class="fas fa-cogs"></i>
            <p>Running Tournament</p>
        </div>
        <div class="box" onclick="window.location.href='/mvc/paymenthistory'">
            <i class="fas fa-money-bill-wave"></i>
            <p>Payment History</p>
        </div>
        <div class="box" onclick="window.location.href='/mvc/creategame'">
            <i class="fas fa-cogs"></i>
            <p>Other</p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
