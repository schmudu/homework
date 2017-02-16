// Define the `homeworkApp` module
var homeworkApp = angular.module('homeworkApp', []);

// FACTORY
homeworkApp.factory("personsFactory", ['$http', function($http){
  var personsFactory = {};

  personsFactory.getPeople = function(){
    return $http({
      method: "get",
      url: "/persons"
    });
  };

  personsFactory.createPerson = function(name){
    return $http({
      method: "post",
      url: "/persons?name="+name,
    });
  };

  personsFactory.deletePerson = function(id){
    return $http({
      method: "delete",
      url: "/persons?id="+id,
    });
  };

  return personsFactory;
}]);

// CONTROLLER
homeworkApp.controller('PeopleController', ['$scope', 'personsFactory', function PeopleController($scope, personsFactory) {
  $scope.errorMsg = false;

  $scope.init = function(){
    console.log("initing.");
    $scope.refreshPeople();
  };

  $scope.disableCreatePerson = function(){
    if($scope.newName){
      return false;
    }
    else{
      return true;
    }
  };

  $scope.createPerson = function(){
    personsFactory.createPerson($scope.newName)
      .then(function successCallback(response){
        // update list
        $scope.refreshPeople();

        // clear input
        $scope.newName = "";
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "create a new person.");
      });
  };

  $scope.deletePerson = function(personId){
    personsFactory.deletePerson(personId)
      .then(function successCallback(response){
        // update list
        $scope.refreshPeople();
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "delete the person.");
      });
  };

  $scope.editPerson = function(personId, personName){
      $("#editPersonModal").modal("show");
  };

  $scope.refreshPeople = function(){
    personsFactory.getPeople()
      .then (function successCallback(response){
        $scope.people = response.data;
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "retrieve the names from the database.");
      });
  };

  function setErrorMsg(msg){
    $scope.errorMsg = true;
    $scope.errorMsgValue = msg;
  }
}]);

