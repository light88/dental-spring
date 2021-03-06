<div>
  <h3>Patients</h3>
  <hr class="colorgraph">
  <div class="form-group">
    <span class="col-sm-2 ">Search : </span>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="search" ng-model="patient.$">
    </div>
  </div>
  <table class="table table-hover table-bordered">
    <thead>
    <tr>
      <th>LastName</th>
      <th>FirstName</th>
      <th>MiddleName</th>
      <th>Birthday</th>
      <th>Gender</th>
      <th>Phone</th>
      <th>Change</th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="p in patients | orderBy:'firstName' | filter:patient">
      <td>{{p.lastName}}</td>
      <td>{{p.firstName}}</td>
      <td>{{p.middleName}}</td>
      <td>{{p.birthday}}</td>
      <td>{{p.gender}}</td>
      <td>{{p.phone}}</td>
      <td>
        <div class="text-center">
          <a ng-href="#view/{{p.id}}"><span class="glyphicon glyphicon-eye-open"></span></a>&nbsp;&nbsp;&nbsp;
          <a ng-href="#edit/{{p.id}}" ng-click="edit(p.id)"><span class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;&nbsp;
          <a href="javascript:void(0)"><span class="glyphicon glyphicon-remove-sign"></span></a>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</div>
