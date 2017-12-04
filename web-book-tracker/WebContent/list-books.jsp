<!-- // Viewer // -->

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core"  prefix = "c" %>

<!DOCTYPE  html>

<html>

<head>

<title> Book Tracker App </title>
<link  type = "text/css"  rel = "stylesheet"  href = "css/style.css">

</head>

<body>

<div  id = "wrapper">
<div  id = "header">
<h2> University library </h2>
</div>
</div>

<div  id = "container">
<div  id = "content">


<!-- Put  new  button: "Add book" -->
<input type = "button"  value = "Add book"  onclick = "window.location.href = 'add-book-form.jsp'; return false"
       class = "add-book-button"/>


<!--  Search box -->
<br><br>
<form action="BookControllerServlet" method="GET">
<input type="hidden" name="command" value="SEARCH" />
Search book by Book name or by Author: <input  type="text"  name="theSearchName"  size="14"/>
<input type="submit" value="Search" class="add-book-button" />
</form>
<br>

<form action="BookControllerServlet" method="GET">
<input type="hidden" name="command" value="SEARCH_D" />
Search book by Date interval: <input type="text" name="theSearchData1" size="12" placeholder="YYYY-MM-DD"
                               required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"  title="Correct data: YYYY-MM-DD" /> <input type="text" name="theSearchData2" size="11" placeholder="YYYY-MM-DD" 
                                                                                                                   required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"  title="Correct data: YYYY-MM-DD"/>
<input type="submit" value="Search" class="add-book-button" />
</form>
<br><br>


<table>

<tr> 
<th width="40%">Book name</th>
<th width="25%">Author</th>
<th width="20%">Date of release</th>
<th width="15%">Action</th>
</tr>

<c:forEach  var = "tempBook"  items = "${BOOK_LIST}">
<!-- Set up a link to update for each book -->
<c:url    var  = "tempLink"   value = "BookControllerServlet">
<c:param  name = "command"    value = "LOAD"></c:param>
<c:param  name = "bookId"     value = "${tempBook.id}"></c:param>
</c:url>
<!-- Set up a link to delete a book -->
<c:url    var  = "deleteLink" value = "BookControllerServlet">
<c:param  name = "command"    value = "DELETE"></c:param>
<c:param  name = "bookId"     value = "${tempBook.id}"></c:param>
</c:url>
<tr> 
<td> ${tempBook.bookName}      </td>
<td> ${tempBook.author}        </td>
<td> ${tempBook.dateOfRelease} </td>
<td> 

<a href = "${tempLink}">Update</a>
 | 
<a href = "${deleteLink}" onclick = "if (!(confirm('Are you sure you want to delete?'))) return false">Delete</a>
<td>
</tr>
<%-- <% } %> --%>
</c:forEach>
</table>
</div>
</div>

</body>

</html>