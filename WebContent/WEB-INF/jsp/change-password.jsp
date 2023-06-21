<!DOCTYPE html>
<html dir="ltr" lang="en-US">
 <%	String context=request.getContextPath(); %>

<jsp:include page="header.jsp" />
        <!-- Page Title #1
============================================ -->
        <section id="page-title" class="page-title bg-overlay bg-overlay-dark2">
            <div class="bg-section">
                <img src="<%=context%>/html/assets/images/page-titles/1.jpg" alt="Background" />
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3">
                        <div class="title title-1 text-center">
                            <div class="title--content">
                                <div class="title--heading">
                                    <h1>Change Password</h1>
                                </div>
                                <ol class="breadcrumb">
                                    <li><a href="index.html">Home</a></li>
                                    <li class="active">Change Password</li>
                                </ol>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- .title end -->
                    </div>
                    <!-- .col-md-12 end -->
                </div>
                <!-- .row end -->
            </div>
            <!-- .container end -->
        </section>
        <!-- #page-title end -->

        <!-- #user-profile
============================================= -->
        <section id="user-profile" class="user-profile">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-4">
                        <div class="edit--profile-area">
                        <ul class="edit--profile-links list-unstyled mb-0">
                            <li><a href="user-profile.html" class="active">Edit Profile</a></li>
                            <li><a href="change-password.html">Change Password</a></li>
							<c:choose>
								<c:when test="${user.userType==3}">
                            		<li><a href="enquiries.html>Enquiry Status</a></li>
	                            </c:when>
	                            <c:otherwise>
	                                <li><a href="add-property.html">Add Property</a></li>
	                                <li><a href="my-properties.html">My Properties</a></li>
	                                <li><a href="enquiries.html?status=Received">Received Enquiries</a></li>
	                                <li><a href="enquiries.html?status=Answered">Answered Enquiries</a></li>
	                            </c:otherwise>
                            </c:choose>
                            <li><a href="logout.html">Logout</a></li>
                         </ul>
                        </div>
                    </div>
                    <!-- .col-md-4 -->
                    <div class="col-xs-12 col-sm-12 col-md-8">

                        <form class="mb-0" method="post" name="changepassword" action="updatePassword.html" onsubmit="return checkpass();">
                            <div class="form-box">
                              <p style="font-size:16px; color:red" align="center">${message}</p>
                                <div class="form-box">
                                <h4 class="form--title">Change Password</h4>
                                <div class="form-group">
                                    <label for="password">Current Password</label>
                                    <input type="password" name="currentpassword" id="currentpassword" class="form-control" value="" required='true'>
                                </div>
                                <div class="form-group">
                                    <label for="password">New Password</label>
                                    <input type="password" name="newpassword" id="newpassword" class="form-control" value="" required='true'>
                                </div>
                                <!-- .form-group end -->
                                <div class="form-group">
                                    <label for="confirm-password">Confirm Password</label>
                                    <input type="password" name="confirmpassword" id="confirmpassword" class="form-control" value="" required='true'>
                                </div>
                                <!-- .form-group end -->
                            </div>
                            </div>
                            <input type="submit" value="Save Edits" name="submit" class="btn btn--primary">
                        </form>
                    </div>
                    <!-- .col-md-8 end -->
                </div>
                <!-- .row end -->
            </div>
        </section>
        <!-- #user-profile  end -->

        <!-- cta #1
============================================= -->
        <section id="cta" class="cta cta-1 text-center bg-overlay bg-overlay-dark pt-90">
            <div class="bg-section"><img src="<%=context%>/html/assets/images/cta/bg-1.jpg" alt="Background"></div>
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3">
                        <h3>Join our professional team & agents to start selling your house</h3>
                        <a href="contact.html" class="btn btn--primary">Contact</a>
                    </div>
                    <!-- .col-md-6 -->
                </div>
                <!-- .row -->
            </div>
            <!-- .container -->
        </section>
        <!-- #cta1 end -->
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

<jsp:include page="footer.jsp" />