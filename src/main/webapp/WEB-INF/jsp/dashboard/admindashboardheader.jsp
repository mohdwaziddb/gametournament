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
      /*display: flex;*/
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

    .dbTab td {
        padding: 5px;
    }
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
</head>