<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>
<body>

  <div class="container col-md-4 col-md-offset-4">
    <div class="alert alert-warning">
      ${message}
    </div>
    <br/>
    <br/>
    <form action="/sessions" method="post">
      <div class="form-group row">
        <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" id="inputUsername" placeholder="Username" name="username">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
        <div class="col-sm-10">
          <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="password">
        </div>
      </div>
      <div class="form-group row">
        <div class="offset-sm-2 col-sm-10">
          <button type="submit" class="btn btn-primary">Sign in</button>
        </div>
      </div>
    </form>
  </div>
</body>
</html>