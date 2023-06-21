<%
	String context=request.getContextPath();
%>
<jsp:include page="adminHeader.jsp" />
       
        <div class="dashboard-wrapper">
            <div class="container-fluid  dashboard-content">
                <!-- ============================================================== -->
                <!-- pageheader -->
                <!-- ============================================================== -->
                <div class="row">
                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div class="page-header">
                            <h2 class="pageheader-title">Contact Us </h2>
                            
                            <div class="page-breadcrumb">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="<%=context%>/admin/dashboard.html" class="breadcrumb-link">Dashboard</a></li>
                                       
                                        <li class="breadcrumb-item active" aria-current="page">Contact Us
                                    </ol>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
                
                    <div class="row">
                        <!-- ============================================================== -->
                        <!-- valifation types -->
                        <!-- ============================================================== -->
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <div class="card">
                                <h5 class="card-header">Contact Us</h5>
                                <div class="card-body">
                                    <form action="<%=context%>/admin/updatePage" method="post">
                                    	<input type="hidden" value="${page.pageId}" name="pageId" />
                                        <p style="font-size:16px; color:red" align="center"></p>
                                        <div class="form-group row">
                                            <label class="col-12 col-sm-3 col-form-label text-sm-right">Page Title</label>
                                            <div class="col-12 col-sm-8 col-lg-6">
                                                <input type="text" name="pagetitle" id="pagetitle" required="true"  value="${page.pageTitle}" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-12 col-sm-3 col-form-label text-sm-right">Email Address</label>
                                            <div class="col-12 col-sm-8 col-lg-6">
                                                <input type="email" name="email" id="email" value="${page.email}" class="form-control" required='true'>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-12 col-sm-3 col-form-label text-sm-right">Mobile Number</label>
                                            <div class="col-12 col-sm-8 col-lg-6">
                                               <input type="text" name="mobnum" id="mobnum" value="${page.mobileNumber}" class="form-control" required='true' maxlength="10" pattern='[0-9]+'>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-12 col-sm-3 col-form-label text-sm-right">Page Description</label>
                                            <div class="col-12 col-sm-8 col-lg-6">
                                                <textarea type="text" name="pagedes" id="pagedes" required="true"class="form-control">${page.pageDescription}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group row text-right">
                                            <div class="col col-sm-10 col-lg-9 offset-sm-1 offset-lg-0">
                                               <p style="text-align: center;"> <button type="submit" class="btn btn-space btn-primary" name="submit">Submit</button></p>
                                                
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- ============================================================== -->
                        <!-- end valifation types -->
                        <!-- ============================================================== -->
                    </div>
           
            </div>
<jsp:include page="adminFooter.jsp" />