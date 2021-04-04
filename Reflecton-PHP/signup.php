<?php
/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 27.05.17
 * Time: 08:44
 */

require '../includes/Database.php';
require '../includes/Config.php';
require '../includes/signup_config.php';

global $columnElementsSignUp;
global $rowElements;

if($_SERVER['REQUEST_METHOD']=='POST')
{
    $db = new Database(DB_HOST, DB_USER_NAME, DB_USER_PWD, DB_NAME, TABLE_NAME, $columnElementsSignUp);
    $db->connectToDatabase();

    for ($i = 0; $i < count($columnElementsSignUp); $i++)
    {
        $rowElements[$i] = $_POST[$columnElementsSignUp[$i]->getName()];
    }

    $flag1 = $db->createTable();
    echo $flag1;

    $flag2 = $db->insertRow($rowElements);
    echo $flag2;

    $db->closeConnectionToDatabase();
}