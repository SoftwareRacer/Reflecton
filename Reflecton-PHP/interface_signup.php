<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    <title>Sign Up</title>
</head>

<body class="w3-container" background="background1.jpg">

<header class="w3-container w3-blue w3-round-large w3-card-4">
    <h1>Sign Up</h1>
</header>

<form method="post" action="signup.php" class="w3-form w3-card-4">
    <div class="w3-input-group">
    <p align=center>
    <?php
        require '../includes/signup_config.php';
        global $columnElementsSignUp;

        for ($i = 0; $i < count($columnElementsSignUp); $i++)
        {
            echo $columnElementsSignUp[$i]->getShownName() . ':<br>';
            echo '<input type="text" name="'. $columnElementsSignUp[$i]->getName() .'"><br><br>';
        }

        echo '<input type="submit" class="w3-btn w3-blue w3-round-large" value="Submit">';
    ?>
    </p>
    </div>
</form>

<footer class="w3-container w3-blue w3-round-large w3-card-4">
    <p>Pinnovations (2017)</p>
</footer>

</body>

</html>