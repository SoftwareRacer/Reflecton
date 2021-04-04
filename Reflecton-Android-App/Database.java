package reflecton.reflecton_android_app;

//
// HomeViewController.java
// Reflecton
//
// Created by Marco Hennermann on 19.12.17
// Copyright © 2017 Marco Hennermann. All rights reserved.
//

public class Database
{
/*
    private PreparedStatement statement;
    private Connection dbConnection = null;

    //connect to Database
    public void Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //dbConnection = DriverManager.getConnection("jdbc:mysql://db702720703.db.1and1.com/db702720703", Configuration.username, Configuration.password);
            dbConnection = DriverManager.getConnection(Configuration.hostName + "/" + Configuration.dataBaseName, Configuration.username, Configuration.password);
        } catch (SQLException x)
        {
            System.out.println(x);
        } catch (Exception clnfx) {}
    }

    //select From Database
    public ResultSet Select(String what, String from, String where) throws SQLException
    {
        String query = "SELECT " + what + " FROM " + from;
        if (where != null)
            query += " WHERE " + where;
        query += ";";
        return (executeQuery(query));
    }

    // Insert Into Database
    public void Insert(String insert_into, String columnNames[], String values[]) throws SQLException
    {
        String query = "INSERT INTO " + insert_into + "(";//+ "(column1, column2, column3) VALUES ()";

        for (String q : columnNames)
        {
            query += q + ",";
        }

        query = query.substring(0, query.length() - 1);
        query += ") VALUES (";
        for (String v : values)
        {
            query += "'"+v + "',";
        }
        query = query.substring(0, query.length() - 1);
        query += ");";
        executeQuery(query);
    }

    //Update Database
    public void Update(String table,String ColumnNames[],String ColumnValues[], String where) throws SQLException
    {
        String query = "UPDATE " + table + " SET ";
        if(ColumnNames.length != ColumnValues.length)
            return;
        for (int i = 0;i<ColumnNames.length;i++)
        {
            query += ColumnNames[i] + "=" + "'"+ColumnValues[i]+"',";
        }
        query = query.substring(0, query.length() - 1);
        query += " WHERE " + where + ";";
        executeQuery(query);
    }

    //Do not Modify table
    private ResultSet executeQuery(String query) throws SQLException
    {
        if (dbConnection != null)
        {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return stmt.executeQuery(query);
        }
        return null;
    }

    //Modify Table
    public void executeUpdate(String query) throws SQLException
    {
        if (dbConnection != null)
        {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
        }
    }
    //delete
    */
}

