//===========================================================================================//
// Controller
//===========================================================================================//
package com.Danylov.jdbc;
//===========================================================================================//
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//===========================================================================================//
/**
 * Controller  -  Servlet implementation class BookControllerServlet
 */
@WebServlet("/BookControllerServlet")
public class BookControllerServlet extends HttpServlet 
{
private  static  final  long  serialVersionUID = 1L;
private  BookDbUtil  bookDbUtil;
@Resource(name = "jdbc/web_book_tracker") // Inject Tomcat connection pool to variable dataSource
private  DataSource  dataSource;
//-------------------------------------------------------------------------------------------//
@Override
public void init() throws ServletException 
{
super.init();
// Create our Book Db Util ... and pass in the conn pool / DataSource object
try
{
bookDbUtil = new BookDbUtil(dataSource);
} // try
catch(Exception  exc)
{
throw  new  ServletException(exc);
} // catch(Exception  exc)
} // public void init() throws ServletException
//-------------------------------------------------------------------------------------------//
/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
try
{
// Read  the  "command"  parameter  	
String  theCommand = request.getParameter("command");	
	
// If  the  command  is  missing,  then  default  to  listing  books
if  (theCommand == null)  theCommand = "LIST";

// Route  to  the  appropriate  method	
switch(theCommand)
{
case  "LIST":     listBooks(request, response);     break;
case  "ADD":      addBook(request, response);       break;
case  "LOAD":     loadBook(request, response);      break;
case  "UPDATE":   updateBook(request, response);    break;
case  "DELETE":   deleteBook(request, response);    break;
case  "SEARCH":   searchBooks(request, response);   break;
case  "SEARCH_D": searchBooks_D(request, response); break;
default:          listBooks(request, response);
} // switch(theCommand)

// List  the  books  ...  in  MVC  fashion
listBooks(request, response);
}
catch(Exception  exc)
{
throw  new  ServletException(exc);
}
} // protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//-------------------------------------------------------------------------------------------//
private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception 
{
// Read the book ID from form data  
String  theId = request.getParameter("bookId");
	
// Delete book from database
bookDbUtil.deleteBook(theId);	

// Send  back  to  the  main  page (the  book  list) 	
listBooks(request, response);	
	
} // private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception
//-------------------------------------------------------------------------------------------//
private void updateBook(HttpServletRequest request, HttpServletResponse response)  throws Exception
{
// Read book info from form data
int     id            = Integer.parseInt(request.getParameter("bookId"));
String  bookName      = request.getParameter("bookName");
String  author        = request.getParameter("author");
Date    dateOfRelease = new Date(DateUtils.parseDate(request.getParameter("dateOfRelease")).getTime());
	
// Create a new book object
Book  theBook = new Book(id, bookName, author, dateOfRelease);	
	
// Perform update on database
bookDbUtil.updateBook(theBook);	
	
//Send  back  to  the  main  page (the  book  list)
listBooks(request, response);	

} // private void updateBook(HttpServletRequest request, HttpServletResponse response)  throws Exception
//-------------------------------------------------------------------------------------------//
private void loadBook(HttpServletRequest request, HttpServletResponse response)  throws Exception
{
// Read book id from form data
String  theId = request.getParameter("bookId");	
	
// Load book from database  (db util)
Book  theBook = bookDbUtil.getBook(theId);	
	
// Place book in the request attribute
request.setAttribute("THE_BOOK", theBook);	
	
// Send to JSP page: update-book-form.jsp
RequestDispatcher  dispatcher = request.getRequestDispatcher("/update-book-form.jsp");
dispatcher.forward(request, response);
return;	
} // private void loadBook(HttpServletRequest request, HttpServletResponse response)
//-------------------------------------------------------------------------------------------//
private void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception 
{
// Read  book  info  from  form  data
String  bookName      = request.getParameter("bookName");	
String  author        = request.getParameter("author");	
Date    dateOfRelease = new Date(DateUtils.parseDate(request.getParameter("dateOfRelease")).getTime());
	
// Create  a  new  book  object
Book  theBook = new Book(bookName, author, dateOfRelease);	
	
// Add  book  to  the  database
bookDbUtil.addBook(theBook);	
	
// Send  back  to  the  main  page (the  book  list)
listBooks(request, response);	
	
} // private void addBook(HttpServletRequest request, HttpServletResponse response)
//-------------------------------------------------------------------------------------------//
private void listBooks(HttpServletRequest request, HttpServletResponse response)  throws Exception
{
// Get  books  from  Db Util
List<Book>  books = bookDbUtil.getBooks();	

// Add books to the request
request.setAttribute("BOOK_LIST", books);	
	
// Send books to JSP page (view:  list-books.jsp)
RequestDispatcher  dispatcher  =  request.getRequestDispatcher("/list-books.jsp");	
dispatcher.forward(request, response);
return;	
} // private void listBooks(HttpServletRequest request, HttpServletResponse response)
//-------------------------------------------------------------------------------------------//
private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws Exception 
{
// read search name from form data
String theSearchName = request.getParameter("theSearchName");

// search books from db util
List<Book> books = bookDbUtil.searchBooks(theSearchName);

// add books to the request
request.setAttribute("BOOK_LIST", books);
    
// send to JSP page (view)
RequestDispatcher dispatcher = request.getRequestDispatcher("/list-books.jsp");
dispatcher.forward(request, response);
return;	
} // private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws Exception
//-------------------------------------------------------------------------------------------//
private void searchBooks_D(HttpServletRequest request, HttpServletResponse response) throws Exception 
{
//read search name from form data
String theSearchData1 = request.getParameter("theSearchData1");
String theSearchData2 = request.getParameter("theSearchData2");

//search books from db util
List<Book> books = bookDbUtil.searchBooks(theSearchData1, theSearchData2);

//add books to the request
request.setAttribute("BOOK_LIST", books);
  
//send to JSP page (view)
RequestDispatcher dispatcher = request.getRequestDispatcher("/list-books.jsp");
dispatcher.forward(request, response);
return;	
} // private void searchBooks_D(HttpServletRequest request, HttpServletResponse response) throws Exception
} // public class BookControllerServlet extends HttpServlet
//===========================================================================================//