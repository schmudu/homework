<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  <div class="container">
    <div class="row">
      <div class = "col-md-6 col-md-offset-3">
        <br/>
        <br/>
        <form class="form-inline" action="/sessions" METHOD="PUT">
          <button type="submit" class="btn btn-default">
            Sign out
          </button>
          <input type="hidden" name="_method" value="DELETE"/>
        </form>
        <div class="rows">
          <div class="col-md-12">
            <div class="page-header text-center">
              <h1>Patrick's Homework</h1>
            </div>
          </div>
        </div>
        <div class="testContainer" ng-app="homeworkApp" ng-controller="PersonsController" ng-init="init()">
          <#include "edit_person.ftl">
          <div class="rows">
            <div class="col-md-11 col-md-offset-1">
              <form class="form-inline">
                <label class="sr-only" for="inlineFormInput">Name</label>
                <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" id="inlineFormInput" placeholder="Enter Name" ng-model="newName">
                <button type="submit" class="btn btn-primary" ng-disabled="disableCreatePerson()" ng-click="createPerson()">
                  <span class="glyphicon glyphicon-plus"></span>
                  Create Person
                </button>
              </form>
              <br/>
              <table class="table table-striped">
                <tr ng-repeat="person in people | orderBy:'name'">
                  <td class="col-md-6"> {{person.name}} </td>
                  <td class="col-md-6 text-right"> 
                    <button type="submit" class="btn btn-default" ng-click="showEditPersonModal(person.id, person.name)">
                      Edit
                    </button>
                    <button type="submit" class="btn btn-warning" ng-click="deletePerson(person.id)">
                      Delete
                    </button>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
