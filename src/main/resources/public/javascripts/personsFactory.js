var homeworkApp = angular.module('homeworkApp');

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


  personsFactory.updatePerson = function(personId, personName){
    return $http({
      method: "put",
      url: "/persons/" + personId + "?name=" + personName
    });
  };

  return personsFactory;
}]);
