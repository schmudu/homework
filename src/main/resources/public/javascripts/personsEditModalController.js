var homeworkApp = angular.module('homeworkApp');

// EDIT PERSON CONTROLLER
homeworkApp.controller('EditPersonsController', ['$scope', 'personsFactory', function PeopleController($scope, personsFactory) {
  $scope.updatingName = false;
  $scope.errorMsg = false;

  $('#editPersonModal').on('shown.bs.modal', function() {
    $("#editPersonInput").select();
  });

  $scope.updateName = function(){
    personsFactory.updatePerson($scope.selectedPersonId, $scope.updatedName)
      .then (function successCallback(response){
        $scope.errorMsg = false;
        $scope.updatingName = false;
        $scope.closeModal();
        $scope.refreshPeople();
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "retrieve the names from the database.");
      });
  };

  $scope.disabledUpdateName = function(){
    if(($scope.updatedName === undefined) ||
      ($scope.updatedName === "") ||
      ($scope.updatedName === $scope.selectedPersonName) ||
      ($scope.updatingName === true)){
      return true;
    }
    return false;
  };

  $scope.closeModal = function(){
    $("#editPersonModal").modal("hide");
  };

  function setErrorMsg(msg){
    $scope.errorMsg = true;
    $scope.errorMsgContent = msg;
  }
}]);
