//===========================================================================================//
// Additional  (helper)  class  -  work  with  DB
//===========================================================================================//
package com.Danylov.jdbc;
//===========================================================================================//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.Date;
//===========================================================================================//
public class BookDbUtil 
{
private  DataSource  dataSource;
//-------------------------------------------------------------------------------------------//
public BookDbUtil(DataSource  theDataSource) 
{
dataSource = theDataSource;
} // public BookDbUtil(DataSource  theDataSource)
//-------------------------------------------------------------------------------------------//
public  List<Book>  getBooks()  throws  Exception
{
List<Book>  books = new ArrayList<>();

Connection  myConn  =  null;
Statement   myStmt  =  null;
ResultSet   myRs    =  null;

try
{
// Get a connection
myConn = dataSource.getConnection();

// Create  SQL  statement
String  sql = "select  *  from  book  order  by  bookName";

// Execute  query
myStmt = myConn.createStatement();
myRs   = myStmt.executeQuery(sql);

// Process  result  set
while  (myRs.next())
{
// Retrieve  data  from  result  set  row
int     id            = myRs.getInt("id");
String  bookName      = myRs.getString("bookName");
String  author        = myRs.getString("author");
Date    dateOfRelease = myRs.getDate("dateOfRelease");

// Create  new  book  object
Book tempBook = new Book(id, bookName, author, dateOfRelease); 	

// Add  it  to  the  list  of  books
books.add(tempBook);	
}
return  books;
}
finally
{
// Close  JDBC  object
close(myConn, myStmt, myRs);	
}
} // public  List<Book>  getBooks()  throws  Exception
//-------------------------------------------------------------------------------------------//
private void close(Connection myConn, Statement myStmt, ResultSet myRs) 
{
try
{
if (myRs   != null)  myRs.close(); 	
if (myStmt != null)  myStmt.close(); 	
if (myConn != null)  myConn.close(); // Come it back to connection pool	
}
catch(Exception  exc)
{
exc.printStackTrace();	
}
} // private void close(Connection myConn, Statement myStmt, ResultSet myRs)
//-------------------------------------------------------------------------------------------//
public void addBook(Book theBook) throws Exception
{
Connection         myConn = null;
PreparedStatement  myStmt = null;

try
{
// Get  DB  connection
myConn = dataSource.getConnection();	
	
// Create SQL for insert
String  sql = "insert  into  book  (bookName, author, dateOfRelease)  values  (?, ?, ?)";	
	
// Set the param values for the book
myStmt = myConn.prepareStatement(sql);	
myStmt.setString(1, theBook.getBookName());	
myStmt.setString(2, theBook.getAuthor());
myStmt.setDate(3,   theBook.getDateOfRelease());	
	
// Execute SQL insert
myStmt.execute();
}
finally
{
// Clean up JDBC objects
close(myConn, myStmt, null);	
	
} // finally	
} // public void addBook(Book theBook)
//-------------------------------------------------------------------------------------------//
public Book getBook(String theBookId)  throws Exception
{
Book               theBook = null;
Connection         myConn  = null;
PreparedStatement  myStmt  = null;
ResultSet          myRs    = null;

int bookId;

try
{
// Convert book id to int
bookId = Integer.parseInt(theBookId);
	
// Get connection to database
myConn = dataSource.getConnection();	
	
// Create SQL to get selected book
String  sql = "select  *  from  book  where  id = ?";	
	
// Create prepared statement
myStmt = myConn.prepareStatement(sql);	
	
// Set params
myStmt.setInt(1, bookId); 	
	
// Execute  statement
myRs = myStmt.executeQuery();	
	
// Retrieve  data  from  result  set  row	
if (myRs.next())
{
String  bookName      = myRs.getString("bookName");	
String  author        = myRs.getString("author");	
// Date    dateOfRelease = DateUtils.parseDate(myRs.getString("dateOfRelease"));
Date    dateOfRelease = new Date(DateUtils.parseDate(myRs.getString("dateOfRelease")).getTime());
	
// Use  the  bookId during construction
theBook = new Book(bookId, bookName, author, dateOfRelease);
}
else  throw  new  Exception("Could not find book id: " + bookId);
return  theBook;
}
finally
{
// Clean up JDBC  objects 	
close(myConn, myStmt, myRs);
}
} // public Book getBook(String theBookId)
//-------------------------------------------------------------------------------------------//
public void updateBook(Book theBook)  throws  Exception 
{
Connection         myConn = null;
PreparedStatement  myStmt = null;

try
{
// Get DB connection
myConn = dataSource.getConnection();

// Create update SQL statement
String  sql = "update book set bookName = ?, author = ?, dateOfRelease = ? where id = ?";

// Prapare  statement
myStmt = myConn.prepareStatement(sql);

// Set params
myStmt.setString(1, theBook.getBookName());
myStmt.setString(2, theBook.getAuthor());
myStmt.setString(3, DateUtils.formatDate(theBook.getDateOfRelease()));
myStmt.setInt(4,    theBook.getId());

// Execute SQL statement
myStmt.execute();
}
finally
{
// Clean up JDBC objects	
close(myConn, myStmt, null);	
}
} // public void updateBook(Book theBook)  throws  Exception
//-------------------------------------------------------------------------------------------//
public void deleteBook(String theBookId) throws Exception 
{
Connection         myConn = null;
PreparedStatement  myStmt = null;
	
try
{
// Convert book Id to int
int  bookId = Integer.parseInt(theBookId);	
	
// Get connection to database
myConn = dataSource.getConnection();	
	
// Create SQL to delete book
String  sql = "delete from book where id = ?";	
	
// Prepare statement
myStmt = myConn.prepareStatement(sql);	
	
// Set params
myStmt.setInt(1, bookId);	
	
// Execute SQL statement
myStmt.execute();	
	
} // try
finally
{
// Clean up JDBC  objects
close(myConn, myStmt, null);

} // finally
} // public void deleteBook(String theBookId) throws Exception
//-------------------------------------------------------------------------------------------//
public List<Book> searchBooks(String theSearchName)  throws Exception 
{
/*1.*/ List<Book> Books = new ArrayList<>();

Connection         myConn = null;
PreparedStatement  myStmt = null;
ResultSet          myRs   = null;

/*2.*/ try 
{
// Get connection to database
/*2.1.*/ myConn = dataSource.getConnection();

// Only search by name if theSearchName is not empty
/*2.2.*/ if (theSearchName != null && 0 < theSearchName.trim().length()) 
{

// Create sql to search for Books by name
String sql = "select * from Book where lower(bookName) like ? or lower(author) like ?";

// Create prepared statement
myStmt = myConn.prepareStatement(sql);

// Set params
String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
myStmt.setString(1, theSearchNameLike);
myStmt.setString(2, theSearchNameLike);
} // 2.2. 
/*2.3.*/ else 
{
// Create sql to get all Books
String sql = "select * from Book order by author";

// Create prepared statement
myStmt = myConn.prepareStatement(sql);
} // 2.3.

// Execute statement
/*2.4.*/ myRs = myStmt.executeQuery();

// Retrieve data from result set row
/*2.5.*/ while (myRs.next()) 
{
// Retrieve  data  from  result  set  row
int     id            = myRs.getInt("id");
String  bookName      = myRs.getString("bookName");
String  author        = myRs.getString("author");
Date    dateOfRelease = myRs.getDate("dateOfRelease");

// Create  new  book  object
Book tempBook = new Book(id, bookName, author, dateOfRelease); 
	
// Add it to the list of Books
Books.add(tempBook);            
} // 2.5.
    
/*2.6.*/ return Books;
} // 2.
/*3.*/ finally 
{
// Clean up JDBC objects
close(myConn, myStmt, myRs);
} // 3.
} // public List<Book> searchBooks(String theSearchName)  throws Exception 
//-------------------------------------------------------------------------------------------//
public List<Book> searchBooks(String theSearchData1, String theSearchData2)  throws Exception 
{
/*1.*/ List<Book> Books = new ArrayList<>();

Connection         myConn = null;
PreparedStatement  myStmt = null;
ResultSet          myRs   = null;

/*2.*/ try
{
//Get connection to database
/*2.1.*/ myConn = dataSource.getConnection();

//Only search by name if theSearchName is not empty
/*2.2.*/ if (theSearchData1 != null && 0 < theSearchData1.trim().length() &&
		     theSearchData2 != null && 0 < theSearchData2.trim().length()) 
{

Date  theSearchData1_D = new Date(DateUtils.parseDate(theSearchData1).getTime());	
Date  theSearchData2_D = new Date(DateUtils.parseDate(theSearchData2).getTime());	
	
// Create sql to search for Books by name
String sql = "select * from Book where ? <= dateOfRelease and dateOfRelease <= ?";

// Create prepared statement
myStmt = myConn.prepareStatement(sql);

// Set params
myStmt.setDate(1, theSearchData1_D);
myStmt.setDate(2, theSearchData2_D);
} // 2.2. 
/*2.3.*/ else 
{
//Create sql to get all Books
String sql = "select * from Book order by author";

//Create prepared statement
myStmt = myConn.prepareStatement(sql);
} // 2.3.

//Execute statement
/*2.4.*/ myRs = myStmt.executeQuery();

//Retrieve data from result set row
/*2.5.*/ while (myRs.next()) 
{
//Retrieve  data  from  result  set  row
int     id            = myRs.getInt("id");
String  bookName      = myRs.getString("bookName");
String  author        = myRs.getString("author");
Date    dateOfRelease = myRs.getDate("dateOfRelease");

//Create  new  book  object
Book tempBook = new Book(id, bookName, author, dateOfRelease); 
	
//Add it to the list of Books
Books.add(tempBook);            
} // 2.5.
  
/*2.6.*/ return Books;
} // 2.
/*3.*/ finally 
{
//Clean up JDBC objects
close(myConn, myStmt, myRs);
} // 3.
} // public List<Book> searchBooks(String theSearchData1, String theSearchData2)  throws Exception 
} // public class BookDbUtil
//===========================================================================================//
