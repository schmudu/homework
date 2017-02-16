<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  <div class="rows">
    <div class="col-md-12">
      <div class="page-header text-center">
        <h1>Patrick's Homework</h1>
      </div>
    </div>
  </div>
  <div class="testContainer" ng-app="homeworkApp" ng-controller="PeopleController">
    {{1+3}}
    <div class="rows">
      <div class="col-md-11 col-md-offset-1">
        <form class="form-inline">
          <label class="sr-only" for="inlineFormInput">Name</label>
          <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" id="inlineFormInput" placeholder="Jane Doe">
          <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus"></span>
            Create Person
          </button>
        </form>
        <br/>
        <table class="table table-striped">
          <tr>
            <td> Hello </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="rows">
    </div>
  </div>

</body>
</html>
