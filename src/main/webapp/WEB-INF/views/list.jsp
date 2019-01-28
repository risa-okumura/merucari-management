<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
  <script src="../../js/form.js"></script>
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
          <li><a id="logout" href="./login.html">Logout <i class="fa fa-power-off"></i></a></li>
        </ul>
        <sec:authorize access="hasRole('ROLE_USER') and isAuthenticated()">
					<sec:authentication var="userEmail" property="principal.user.email" />
					</sec:authorize>
        <p class="navbar-text navbar-right">
        
          <span id="loginName">user : <c:out value="${userEmail}"/></span>
        </p>
      </div>
    </div>
  </nav>

  <div id="main" class="container-fluid">
    <!-- addItem link -->
    <div id="addItemButton">
      <a class="btn btn-default" href="${pageContext.request.contextPath}/addItem/add"><i class="fa fa-plus-square-o"></i> Add New Item</a>
    </div>

    <!-- 検索フォーム -->
    <div id="forms">
    　　　　 <div class="error" style="color:red"><c:out value="${searchError}"></c:out></div>
      <form:form modelAttribute="searchItemForm" action="${pageContext.request.contextPath}/searchItem/search" class="form-inline" role="form">
        <div class="form-group">
          <form:input path="name" class="form-control" id="name" placeholder="item_name"/>
        </div>
        <div class="form-group"><i class="fa fa-plus"></i></div>
        <div class="form-group">
        
          <form:select id="pulldown1" path="parentId" class="form-control">
            <option value="">- parentCategory -</option>
            <form:options items="${parentList}" itemLabel="name" itemValue="id"></form:options>
          </form:select>
          
          <form:select id="pulldown2" path="childId" class="form-control">
    		<option value="">- childCategory -</option>
		 </form:select>
          
          <form:select id="pulldown3" path="grandChildId" class="form-control">
    		<option value="">- grandChildCategory -</option>
		 </form:select>
      
        </div>
        <div class="form-group"><i class="fa fa-plus"></i></div>
        <div class="form-group">
          <form:input path="brand" class="form-control" id="brand" placeholder="brand"/>
        </div>
        <div class="form-group"></div>
        <button type="submit" class="btn btn-default"><i class="fa fa-angle-double-right"></i> search</button>
      </form:form>
    </div>
    
  <!-- pagination -->
    <div class="pages">
      <nav class="page-nav">
        <ul class="pager">
          <li class="previous"><a href="${pageContext.request.contextPath}/<c:out value="${startPage}"/><c:out value="${preLink}"/>">prev &rarr;</a></li>
          <li class="next"><a href="${pageContext.request.contextPath}/<c:out value="${startPage}"/><c:out value="${nextLink}"/>">next &rarr;</a></li>
        </ul>
      </nav>

    <!-- table -->
    <div class="table-responsive">
      <table id="item-table" class="table table-hover table-condensed">
        <thead>
          <tr>
            <th>name</th>
            <th>price</th>
            <th>category</th>
            <th>brand</th>
            <th>cond</th>
          </tr>
        </thead>
        <tbody>
        
         <c:forEach var="item" items="${itemList}" varStatus="status">
          <tr>
            <td class="item-name"><a href="${pageContext.request.contextPath}/viewItemDetail/detail?id=<c:out value="${item.id}"/>"> <c:out value="${item.name}" /> </a></td>
            <td class="item-price"><c:out value="${item.price}" /></td>
            <td class="item-category">
            
            	<a href="${pageContext.request.contextPath}/searchItem/search?parentId=<c:out value="${item.categoryName.parentId}"/>"><c:out value="${item.categoryName.parentName}" /></a>
	 			
	 			/<a href="${pageContext.request.contextPath}/searchItem/search?childId=<c:out value="${item.categoryName.childId}"/>"><c:out value="${item.categoryName.childName}" /></a>
	 			
	 			/<a href="${pageContext.request.contextPath}/searchItem/search?grandChildId=<c:out value="${item.categoryName.id}"/>"><c:out value="${item.categoryName.grandChildName}" /></a>
	 			
           </td>
            <td class="item-brand"><a href="${pageContext.request.contextPath}/searchItem/search?brand=<c:out value="${item.brand}"/>"><c:out value="${item.brand}" /></a></td>
            <td class="item-condition"><c:out value="${item.condition}" /></td>
          </tr>
          
          </c:forEach>
          
        </tbody>
      </table>
    </div>

        
  <!-- pagination -->
    <div class="pages">
      <nav class="page-nav">
        <ul class="pager">
          <li class="previous"><a href="${pageContext.request.contextPath}/<c:out value="${startPage}"/><c:out value="${preLink}"/>">prev &rarr;</a></li>
          <li class="next"><a href="${pageContext.request.contextPath}/<c:out value="${startPage}"/><c:out value="${nextLink}"/>">next &rarr;</a></li>
        </ul>
      </nav>
      <!-- ページ番号を指定して表示するフォーム -->
      <div id="select-page">
        <form:form id="searchForm" action="${pageContext.request.contextPath}/viewItemList/list" class="form-inline" method="POST">
          <div class="form-group">
            <div class="input-group col-xs-6">
              <label></label>
              <input type="text" name="pageNum" class="form-control"/>
              <!-- 総ページ数 -->
              <div class="input-group-addon">/ <c:out value="${countPage}"/></div>
            </div>
            <div class="input-group col-xs-1">
        	<div class="form-name">
              <div id="form-change"><button type="submit" class="btn btn-default">Go</button></div>
            </div>
            </div>
          </div>
         </form:form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>