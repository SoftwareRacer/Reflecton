<?php
/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 10.10.17
 * Time: 17:26
 */

require '../includes/Column.php';

/*---------------------------------------------COLUMNS---------------------------------------------*/
    $columnElementsGetData = array(
        new Column('email', 'E-Mail', 'varchar(255)', 'UNIQUE(email)')
    );

/*----------------------------------------------ROWS----------------------------------------------*/
    $rowElements = array();

/*------------------------------------------RESULT-FIELD------------------------------------------*/
    $resultField = 'data';