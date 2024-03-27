<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Responsive JSP Page</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
    }

    .container {
      width: 80%;
      margin: 0 auto;
      text-align: center;
    }

    @media screen and (max-width: 768px) {
      .container {
        width: 90%;
      }
    }

    @media screen and (max-width: 480px) {
      .container {
        width: 100%;
      }
    }
  </style>
  <link id="manifest"  rel="manifest" crossorigin="use-credentials" href="/manifest.json">
</head>
<body>
<div class="container">
  <h1>Hello, Welcome to My JSP Page!</h1>
  <p>This is a simple JSP page with responsive design.</p>
</div>
</body>
<script>
  window.addEventListener('load', () => {
    registerSW();
  });

  // Register the Service Worker
  async function registerSW() {
    if ('serviceWorker' in navigator) {
      try {
        await navigator
                .serviceWorker
                .register('serviceworker.js');
      }
      catch (e) {
        console.log('SW registration failed');
      }
    }
  }
</script>
</html>
