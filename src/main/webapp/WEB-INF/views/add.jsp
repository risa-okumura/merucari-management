<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <!-- css -->
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" 
    integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
    integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"/>
  <link rel="stylesheet" href="../../css/mercari.css"/>
  <!-- script -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <script src="../../js/pulldown.js"></script>
  <title>Rakus Items</title>
</head>
<body>
  <!-- navbar -->
  <nav class="navbar navbar-inverse">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/viewItemList/list">Rakus Items</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <div>
        <ul class="nav navbar-nav navbar-right">
          <li><a id="logout" href="./login.html">Logout&nbsp;<i class="fa fa-power-off"></i></a></li>
        </ul>
        <p class="navbar-text navbar-right">
          <span id="loginName">user: userName</span>
        </p>
      </div>
    </div>
  </nav>

  <!-- details -->
  <div id="input-main" class="container">
    <a type="button" class="btn btn-default" href="./list.html"><i class="fa fa-reply"></i> back</a>
    <h2>Add</h2>

    <!-- add form -->
    <form:form modelAttribute="itemForm" action="${pageContext.request.contextPath}/addItem/addItem" class="form-horizontal" role="form">
      <!-- name -->
      <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">name</label>
        <div class="col-sm-8">
          <form:input path="name" class="form-control" id="name" placeholder="item_name"/>
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- price -->
      <div class="form-group">
        <label for="price" class="col-sm-2 control-label">price</label>
        <div class="col-sm-8">
          <form:input path="price" class="form-control" id="price" placeholder="price"/>
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- category -->
      <div class="form-group">
        <label for="category" class="col-sm-2 control-label">category</label>
        <div class="col-sm-8">
          
          <form:select id="pulldown1" path="parentId" class="form-control">
            <option value="">- parentCategory -</option>
            <form:options items="${parentList}" itemLabel="name" itemValue="id"></form:options>
          </form:select>
          
        </div>
      </div>
      <div class="form-group">
        <label for="category" class="col-sm-2 control-label"></label>
        <div class="col-sm-8">
           <form:select id="pulldown2" path="childId" class="form-control">
    		<option value="">- childCategory -</option>
		 </form:select>
        </div>
      </div>
      <div class="form-group">
        <label for="category" class="col-sm-2 control-label"></label>
        <div class="col-sm-8">
       	 <form:select id="pulldown3" path="grandChildId" class="form-control">
    		<option value="">- grandChildCategory -</option>
		 </form:select>
        </div>
      </div>
      <div class="form-group">
        <label for="category" class="col-sm-2 control-label"></label>
        <div class="col-sm-8">
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- brand -->
      <div class="form-group">
        <label for="brand" class="col-sm-2 control-label">brand</label>
        <div class="col-sm-8">
         <form:input path="brand" class="form-control" id="brand" placeholder="brand"/>
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- condition -->
      <div class="form-group">
        <label for="condition" class="col-sm-2 control-label">condition</label>
        <div class="col-sm-8">
          <label for="condition1" class="radio-inline">
            <input type="radio" name="condition" id="condition1" value="1"/> 1
          </label>
          <label for="condition2" class="radio-inline">
            <input type="radio" name="condition" id="condition2" value="2"/> 2
          </label>
          <label for="condition3" class="radio-inline">
            <input type="radio" name="condition" id="condition3" value="3"/> 3
          </label>
        </div>
      </div>
      <div class="form-group">
        <label for="category" class="col-sm-2 control-label"></label>
        <div class="col-sm-8">
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- description -->
      <div class="form-group">
        <label for="description" class="col-sm-2 control-label">description</label>
        <div class="col-sm-8">
         <form:textarea rows="5" path="description" class="form-control" id="description" placeholder="description"/>
          <span class="text-danger">error:may not be empty</span>
        </div>
      </div>
      <!-- submit button -->
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <button type="submit" class="btn btn-default">Submit</button>
        </div>
      </div>
    </form:form>
  </div>
</body>
</html>