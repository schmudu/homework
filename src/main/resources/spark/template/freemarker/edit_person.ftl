<div class="modal fade" id="editPersonModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" ng-controller="EditPersonsController">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Update Person</h5>
      </div>
      <div class="modal-body">
        <form class="form-inline">
          <label for="lgFormGroupInput" class="col-sm-2 col-form-label col-form-label-lg">Name</label>
          <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" id="editPersonInput" ng-model="updatedName">
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" ng-disabled="disabledUpdateName()" ng-click="updateName()">Update Name</button>
      </div>
    </div>
  </div>
</div>