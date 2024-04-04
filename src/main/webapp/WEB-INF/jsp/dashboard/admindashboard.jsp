<%@ include file="/WEB-INF/jsp/dashboard/admindashboardheader.jsp"%>
<title>Admin-Dashboard</title>
<link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
<style>

</style>
<body>
<div class="container">

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
        <div class="box" onclick="window.location.href='/mvc/addtournamentwinner'">
            <i class="fas fa-cogs"></i>
            <p>Add Tournament Winner</p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/dashboard/admindashboardfooter.jsp"%>
</body>
</html>
