<?php
/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 26.09.17
 * Time: 16:20
 */

require '../includes/Database.php';
require '../includes/Config.php';
require '../includes/signin_config.php';

global $columnElementsSignIn;
global $rowElements;

if($_SERVER['REQUEST_METHOD']=='POST')
{
    $db = new Database(DB_HOST, DB_USER_NAME, DB_USER_PWD, DB_NAME, TABLE_NAME, $columnElementsSignIn);
    $db->connectToDatabase();

    for ($i = 0; $i < count($columnElementsSignIn); $i++)
    {
        $rowElements[$i] = $_POST[$columnElementsSignIn[$i]->getName()];
    }

    $flag1 = $db->selectRow($rowElements);
    echo $flag1;

    $db->closeConnectionToDatabase();
}