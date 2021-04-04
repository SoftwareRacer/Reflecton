<?php
/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 27.05.17
 * Time: 06:44
 */

require '../includes/Column.php';

/*---------------------------------------------COLUMNS---------------------------------------------*/
    $columnElementsSignUp = array(
        new Column('first_name', 'Firstname', 'varchar(255)', null),
        new Column('last_name', 'Lastname', 'varchar(255)', null),
        new Column('email', 'E-Mail', 'varchar(255)', 'UNIQUE(email)'),
        new Column('password', 'Password','varchar(255)', null),
        new Column('data', 'Data', 'varchar(255)', null)
    );

/*----------------------------------------------ROWS----------------------------------------------*/
    $rowElements = array();