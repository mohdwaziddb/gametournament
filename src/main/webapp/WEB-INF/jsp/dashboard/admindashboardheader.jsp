<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <%-- <title>Admin-Dashboard</title>--%>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="/Common/css/common.css">
  <link rel="stylesheet" href="/Common/css/jquery.toast.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <style>
    .icon {
      position: absolute;
      top: 10px;
      right: 20px;
      cursor: pointer;
      z-index: 2;
      color: black;
    }
    .login-details {
      display: none;
      position: absolute;
      top: 33px;
      right: 0;
      background-color: #f9f9f9;
      border: 1px solid #ccc;
      padding: 12px;
      z-index: 1;
      color: black;
      text-align: left;
      margin-right: 15px;
    }
    .box-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      margin: 20px;

    }
    .box {
      width: 48%;
      margin-bottom: 20px;
      background-color: #f3f3f3;
      border-radius: 10px;
      padding: 20px;
      box-sizing: border-box;
      color: black;
    }
    .box i {
      font-size: 30px;
      margin-bottom: 10px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      color: black;
    }
    th, td {
      padding: 12px;
      border-bottom: 1px solid #ddd;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
    tr:nth-child(odd) {
      background-color: #ffffff;
    }
    .action-buttons {
      display: flex;
      justify-content: space-around;
    }
    .edit-btn, .delete-btn, .add-btn {
      padding: 8px 12px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    .edit-btn {
      background-color: #4caf50;
      color: white;
    }
    .delete-btn {
      background-color: #f44336;
      color: white;
    }
    .add-btn {
      background-color: #008CBA;
      color: white;
    }
    .modal {
      display: none;
      position: fixed;
      z-index: 1;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgba(0,0,0,0.4);
      padding-top: 100px;
    }
    .modal-content {
      background-color: #444;
      margin: auto;
      padding: 20px;
      border: 1px solid #888;
      width: 80%;
      max-width: 400px;
      border-radius: 8px;
      color: #ffffff;
    }
    .close {
      color: #aaa;
      float: right;
      font-size: 28px;
      font-weight: bold;
      cursor: pointer;
    }
    .close:hover,
    .close:focus {
      color: black;
      text-decoration: none;
      cursor: pointer;
    }
  </style>
</head>