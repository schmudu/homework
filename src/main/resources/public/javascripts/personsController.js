// Define the `homeworkApp` module
var homeworkApp = angular.module('homeworkApp', []);

// MAIN CONTROLLER
homeworkApp.controller('PersonsController', ['$scope', '$interval', 'personsFactory', function PersonsController($scope, $interval, personsFactory) {
  $scope.errorMsg = false;

  $scope.init = function(){
    $scope.refreshPeople();

    // automatically retrieve users every 5 seconds
    $interval($scope.refreshPeople,5000);
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

  $scope.showEditPersonModal = function(personId, personName){
    $scope.updatedName = personName;
    $scope.selectedPersonName = personName;
    $scope.selectedPersonId = personId;
    $("#editPersonModal").modal("show");
  };

  $scope.refreshPeople = function(){
    console.log("getting new people");
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

