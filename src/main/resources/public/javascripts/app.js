// Define the `homeworkApp` module
var homeworkApp = angular.module('homeworkApp', [])

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
      method: "get",
      url: "/limits/email"
    });
  };

  /*
  personsFactory.sendReferralList = function(listSlug, emailAddresses){
    var data = {
      "email_addresses" : emailAddresses
    };

    return $http({
      method: "post",
      url: "/referral_lists/" + listSlug + "/email",
      data: data
    });
  };
  */

  return personsFactory;
}]);

// Define the `PhoneListController` controller on the `homeworkApp` module
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
        console.log("person created: " + $scope.newName);
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "retrieve the pricing bands.");
      });
    console.log("going to create person with name: " + $scope.newName);
  };

  $scope.refreshPeople = function(){
    personsFactory.getPeople()
      .then (function successCallback(response){
        $scope.people = response.data;
        console.log("got people: " + $scope.people);
      }, function errorCallback(response){
        setErrorMsg("Due to a technical error we were unable to " +
          "retrieve the name from the database.");
      });
    console.log("going to create person with name: " + $scope.newName);
  };

  function setErrorMsg(msg){
    $scope.errorMsg = true;
    $scope.errorMsgValue = msg;
  }

  $scope.phones = [
    {
      name: 'Nexus S',
      snippet: 'Fast just got faster with Nexus S.'
    }, {
      name: 'Motorola XOOM™ with Wi-Fi',
      snippet: 'The Next, Next Generation tablet.'
    }, {
      name: 'MOTOROLA XOOM™',
      snippet: 'The Next, Next Generation tablet.'
    }
  ];
}]);

