<!DOCTYPE  html>
<html>

<head>

<title>Add book</title>
<link  type = "text/css"  rel = "stylesheet"  href = "css/style.css">
<link  type = "text/css"  rel = "stylesheet"  href = "css/add-book-style.css">

</head>


<body>

<div  id = "wrapper">
<div  id = "header">
<h2> FooBar University </h2>
</div>
</div>

<div  id = "container">
<h3> Add book </h3>

<form action="BookControllerServlet"  method = "GET" >

<input  type = "hidden" name = "command" value = "ADD">

<table>

<tbody>

<tr>
<td><label>Book name:</label></td>
<td><input  type = "text"  name = "bookName" /></td>
</tr>
<tr>
<td><label>Author:</label></td>
<td><input  type = "text"  name = "author" /></td>
</tr>
<tr>
<td><label>Date of release:</label></td>
<td><input  type = "text"  name = "dateOfRelease"  placeholder = "YYYY-MM-DD"
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