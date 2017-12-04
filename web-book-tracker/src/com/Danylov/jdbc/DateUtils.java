//========================================================================================================//
package com.Danylov.jdbc;
//========================================================================================================//
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//========================================================================================================//
public class DateUtils 
{
// See this link for details: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.htm
private  static  SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
//--------------------------------------------------------------------------------------------------------//	
// Read  date  string  and  parse / convert  it  to  a  date
public  static  Date  parseDate(String  dateStr)  throws  ParseException
{
Date    theDate = formatter.parse(dateStr);
return  theDate;
} // public  static  Date  parseDate(String  dateStr)  throws  ParseException
//--------------------------------------------------------------------------------------------------------//	
// Read  a  date  and  format / convert  it  to  a  date  string
public  static  String  formatDate(Date  theDate)
{
String  result  =  null;
if  (theDate != null)  result = formatter.format(theDate);
return  result;
} // public  static  Date  parseDate(String  dateStr)  throws  ParseException
//--------------------------------------------------------------------------------------------------------//	
} // public class DateUtils
//========================================================================================================//