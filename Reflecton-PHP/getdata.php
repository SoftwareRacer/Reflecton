<?php
/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 10.10.17
 * Time: 17:25
 */

require '../includes/Database.php';
require '../includes/Config.php';
require '../includes/getdata_config.php';

global $columnElementsGetData;
global $rowElements;
global $resultField;

if($_SERVER['REQUEST_METHOD']=='POST')
{
    $db = new Database(DB_HOST, DB_USER_NAME, DB_USER_PWD, DB_NAME, TABLE_NAME, $columnElementsGetData);
    $db->connectToDatabase();

    for ($i = 0; $i < count($columnElementsGetData); $i++)
    {
        $rowElements[$i] = $_POST[$columnElementsGetData[$i]->getName()];
    }

    $resultArray = $db->selectRowReturnResult($rowElements);

    if (count($resultArray) != 0)
    {
        echo $resultArray[$resultField];
    }
    else
    {
        echo 0;
    }
    $db->closeConnectionToDatabase();
}