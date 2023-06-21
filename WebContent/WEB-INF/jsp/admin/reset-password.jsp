<!doctype html>
<html lang="en">
 <%	String context=request.getContextPath(); %>
 
<head>
    
    <title>Allinone Property Soluction || Reset Page</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=context%>/html/admin/assets/vendor/bootstrap/css/bootstrap.min.css">
    <link href="<%=context%>/html/admin/assets/vendor/fonts/circular-std/style.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=context%>/html/admin/assets/libs/css/style.css">
    <link rel="stylesheet" href="<%=context%>/html/admin/assets/vendor/fonts/fontawesome/css/fontawesome-all.css">
    <style>
    html,
    body {
        height: 100%;
    }

    body {
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
    }
    </style>
   
</head>

<body>
    <!-- ============================================================== -->
    <!-- forgot password  -->
    <!-- ============================================================== -->
    <div class="splash-container">
        <div class="card">
            <div class="card-header text-center"><h2 style="color: blue">Allinone Property Soluction</h2><span class="splash-description">Please enter your user information.</span></div>
            <div class="card-body">
                <form role="form" method="post" action="<%=context%>/admin/saveNewPassword.html" name="changepassword" onsubmit="return checkpass();">
                    <p style="font-size:16px; color:red" align="center">${message}</p>
                    <input type="hidden" name="adminId" value="${adminUser.adminId}" />
                    <div class="form-group">
                        <input type="password" name="newpassword" class="form-control form-control-lg" placeholder="New Password" required="true">

                    </div>
                     <div class="form-group">
                        <input type="password" name="confirmpassword" class="form-control form-control-lg" placeholder="Confirm Password" required="true">
                        
                    </div>
                    <div class="form-group pt-1"><button type="submit" class="btn btn-primary btn-lg btn-block" name="submit">Reset</button></div>
                </form>
            </div>
            <div class="card-footer text-center">
                <span><a href="login.html">Sign In</a></span>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    	function checkpass()
    	{
    		if($("#newpassword").val()!=$("#confirmpassword").val())
    		{
    			alert("New Password and Confirm Password did not match");
    			return false;
    		}
    		return true;
    	}
    </script>
    <!-- ============================================================== -->
    <!-- end forgot password  -->
    <!-- ============================================================== -->
    <!-- Optional JavaScript -->
    <script src="<%=context%>/html/assets/vendor/jquery/jquery-3.3.1.min.js"></script>
    <script src="<%=context%>/html/assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
</body>

 
</html>