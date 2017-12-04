<!DOCTYPE  html>
<html>

<head>

<title>Update book</title>
<link  type = "text/css"  rel = "stylesheet"  href = "css/style.css">
<link  type = "text/css"  rel = "stylesheet"  href = "css/add-book-style.css">

</head>


<body>

<div  id = "wrapper">
<div  id = "header">
<h2> University library </h2>
</div>
</div>

<div  id = "container">
<h3> Update Book </h3>

<form action="BookControllerServlet"               method = "GET" >

<input  type = "hidden" name = "command"           value = "UPDATE">
<input  type = "hidden" name = "bookId"            value = "${THE_BOOK.id}">

<table>

<tbody>

<tr>
<td><label>Book name:</label></td>
<td><input  type = "text"  name = "bookName"       value = "${THE_BOOK.bookName}" /></td>
</tr>
<tr>
<td><label>Author:</label></td>
<td><input  type = "text"  name = "author"         value = "${THE_BOOK.author}" /></td>
</tr>
<tr>
<td><label>Date of release:</label></td>
<td><input  type = "text"  name = "dateOfRelease"  value = "${THE_BOOK.dateOfRelease}"
            required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"  title="Correct data: YYYY-MM-DD" /></td>
</tr>
<tr>
<td><label></label></td>
<td><input  type = "submit"  value = "Save" class = "save" /></td>
</tr>

</tbody>

</table>

</form>

<div  style =  "clear: both;"></div>

<p>
<a  href = "BookControllerServlet">Back  to  list</a>
</p>

</div>


</body>

</html>