//===========================================================================================//
package com.Danylov.jdbc;
//===========================================================================================//
import java.sql.Date;
//===========================================================================================//
public class Book 
{
private  int     id;
private  String  bookName;
private  String  author;
private  Date    dateOfRelease;	
//-------------------------------------------------------------------------------------------//
public Book(int id, String bookName, String author, Date dateOfRelease) 
{
super();
this.id            = id;
this.bookName      = bookName;
this.author        = author;
this.dateOfRelease = dateOfRelease;
}
//-------------------------------------------------------------------------------------------//
public Book(String bookName, String author, Date dateOfRelease) 
{
super();
this.bookName      = bookName;
this.author        = author;
this.dateOfRelease = dateOfRelease;
}
//-------------------------------------------------------------------------------------------//
public int getId() 
{
return id;
}
//-------------------------------------------------------------------------------------------//
public void setId(int id) 
{
this.id = id;
}
//-------------------------------------------------------------------------------------------//
public String getBookName() 
{
return bookName;
}
//-------------------------------------------------------------------------------------------//
public void setBookName(String bookName) 
{
this.bookName = bookName;
}
//-------------------------------------------------------------------------------------------//
public String getAuthor() 
{
return author;
}
//-------------------------------------------------------------------------------------------//
public void setAuthor(String author) 
{
this.author = author;
}
//-------------------------------------------------------------------------------------------//
public Date getDateOfRelease() 
{
return dateOfRelease;
}
//-------------------------------------------------------------------------------------------//
public void setDateOfRelease(Date dateOfRelease) 
{
this.dateOfRelease = dateOfRelease;
}
//-------------------------------------------------------------------------------------------//
@Override
public String toString() 
{
return "Book [id=" + id + ", bookName=" + bookName + ", author=" + author + ", dateOfRelease=" + 
        DateUtils.formatDate(dateOfRelease) + "]";
}
} // public class Book 
//===========================================================================================//