<?php

/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 27.05.17
 * Time: 07:47
 */

class Database
{
    // Member variables
    private $host;
    private $userName;
    private $userPassword;
    private $databaseName;
    private $tableName;
    private $columnElements;
    private $databaseConnection;

    // Constructor
    public function __construct($host, $userName, $userPassword, $databaseName, $tableName, $columnElements)
    {
        // Initialize member variables
        $this->host = $host;
        $this->userName = $userName;
        $this->userPassword = $userPassword;
        $this->databaseName = $databaseName;
        $this->tableName = $tableName;
        $this->columnElements = $columnElements;
    }

    // MySQL-Database connection
    public function connectToDatabase()
    {
        // Connect to database
        $this->databaseConnection = new mysqli($this->host, $this->userName, $this->userPassword, $this->databaseName);

        // Check for database connection error
        if (mysqli_connect_errno())
        {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }
    }

    // MySQL-Database disconnect
    public function closeConnectionToDatabase()
    {
        // Disconnect from database
        $this->databaseConnection->close();
    }

    // Create table
    public function createTable()
    {
        // Build query
        $sqlQuery = "CREATE TABLE IF NOT EXISTS " . $this->tableName . " (id int NOT NULL AUTO_INCREMENT, PRIMARY KEY(id)";

        for ($i = 0; $i < count($this->columnElements); $i++)
        {
            $sqlQuery = $sqlQuery . ", " . $this->columnElements[$i]->toString();
        }
        
        $sqlQuery = $sqlQuery . ")";

        // Execute statement
        $stmt = $this->databaseConnection->prepare($sqlQuery);
        $stmt->execute();
        $error = $stmt->error;
        $stmt->close();

        // Return error
        return $error;
    }

    // Select row
    public function selectRow($rowElements)
    {
        // Build query
        $sqlQuery = "SELECT * FROM " . $this->tableName . " WHERE ";
        for ($i = 0; $i < count($this->columnElements); $i++)
        {
            $sqlQuery = $sqlQuery . $this->columnElements[$i]->getName();
            $sqlQuery = $sqlQuery . "=" . "'" . $rowElements[$i] . "'";

            if($i < count($this->columnElements) - 1)
            {
                $sqlQuery = $sqlQuery . " AND ";
            }
        }

        // Result of built query
        $result = $this->databaseConnection->query($sqlQuery);

        // Definition of row variable
        $row = "";

        // Fetch array
        while(($array = mysqli_fetch_array($result)) != null)
        {
            ob_start();
            var_dump($array);
            $row = ob_get_clean();
        }

        // Definition of message
        $message = 0;

        // Check if row was found
        if(strlen($row) == 0)
        {
            // if row is not responded -> return 0
            $message = 0;
        }
        else
        {
            // if row is responded -> return 1
            $message = 1;
        }

        // Return message
        return $message;
    }

    // Select row and return result (array)
    public function selectRowReturnResult($rowElements)
    {
        // Build query
        $sqlQuery = "SELECT * FROM " . $this->tableName . " WHERE ";
        for ($i = 0; $i < count($this->columnElements); $i++)
        {
            $sqlQuery = $sqlQuery . $this->columnElements[$i]->getName();
            $sqlQuery = $sqlQuery . "=" . "'" . $rowElements[$i] . "'";

            if($i < count($this->columnElements) - 1)
            {
                $sqlQuery = $sqlQuery . " AND ";
            }
        }

        // Result of built query
        $result = $this->databaseConnection->query($sqlQuery);

        // Fetch array
        $array = mysqli_fetch_array($result);

        // Return array
        return $array;
    }

    // Insert row
    public function insertRow($rowElements)
    {
        // Build query
        $sqlQuery = "INSERT INTO " . $this->tableName . " (";

        for ($i = 0; $i < count($this->columnElements); $i++)
        {
            $sqlQuery = $sqlQuery . $this->columnElements[$i]->getName();

            if($i < count($this->columnElements) - 1)
            {
                $sqlQuery = $sqlQuery . ", ";
            }
        }

        $sqlQuery = $sqlQuery . ") VALUES (";

        for ($i = 0; $i < count($rowElements); $i++)
        {
            $sqlQuery = $sqlQuery . "'" . $rowElements[$i] . "'";

            if($i < count($rowElements) - 1)
            {
                $sqlQuery = $sqlQuery . ", ";
            }
        }

        $sqlQuery = $sqlQuery . ")";

        // Execute statement
        $stmt = $this->databaseConnection->prepare($sqlQuery);
        $stmt->execute();
        $error = $stmt->error;
        $stmt->close();

        // Definition of message
        $message = 0;

        // Check if an error has occurred
        if(strlen($error) == 0)
        {
            // if an error has not occurred -> return 1
            $message = 1;
        }
        else
        {
            // if an error has occurred -> return 0
            $message = 0;
        }

        // Return message
        return $message;
    }
}