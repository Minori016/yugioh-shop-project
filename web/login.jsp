<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Login Form</title>
    </head>
    <body>
        <c:if test="${not empty user}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <div id="logreg-forms">
            <form class="form-signin" action="MainController" method="post">

                <input type="hidden" name="txtAction" value="login" />

                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>
                <input name="user"  type="text" id="inputEmail" class="form-control" placeholder="Username or email" required="" autofocus="" value="${username}">
                <input name="pass"  type="password" id="inputPassword" class="form-control" placeholder="Password" required="">

                
                <c:if test="${not empty msg and empty showSignup}">
                    <span style="color: red; display: block; text-align: center; margin-bottom: 10px;">${msg}</span>
                </c:if>
                
                    
                    
                    
                <c:if test="${not empty param.msg}">
                    <span style="color: green; display: block; text-align: center; margin-bottom: 10px;">${param.msg}</span>
                </c:if>
                <%-- ============================================= --%>

                <div class="form-group form-check">
                    <input name="remember" value="1" type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Remember me</label>
                </div>

                <button class="btn btn-success btn-block" type="submit"><i class="fas fa-sign-in-alt"></i> Sign in</button>
                <hr>
                <button class="btn btn-primary btn-block" type="button" id="btn-signup"><i class="fas fa-user-plus"></i> Sign up New Account</button>
            </form>

            <form action="MainController" method="post" class="form-signup">
                <input type="hidden" name="txtAction" value="signup" />

                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign up</h1>
                

                <c:if test="${not empty msg and not empty showSignup}">
                    <span style="color: red; display: block; text-align: center; margin-bottom: 10px;">${msg}</span>
                </c:if>
                <%-- ============================================= --%>
                
                <input name="user" type="text" id="user-name" class="form-control" placeholder="User name" required="" autofocus="" value="${username}">
                
                <input name="fullName" type="text" id="full-name" class="form-control" placeholder="Full Name" required="" autofocus="" value="${fullName}">
                
                <input name="email" type="email" id="user-email" class="form-control" placeholder="Email address" required autofocus="" value="${email}">
                
                <input name="pass" type="password" id="user-pass" class="form-control" placeholder="Password" required autofocus="">
                <input name="repass" type="password" id="user-repeatpass" class="form-control" placeholder="Repeat Password" required autofocus="">

                <button class="btn btn-primary btn-block" type="submit"><i class="fas fa-user-plus"></i> Sign Up</button>
                <a href="#" id="cancel_signup"><i class="fas fa-angle-left"></i> Back</a>
            </form>
            <br>

        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
            function toggleResetPswd(e) {
                e.preventDefault();
                $('#logreg-forms .form-signin').toggle() // display:block or none
                $('#logreg-forms .form-reset').toggle() // display:block or none
            }

            function toggleSignUp(e) {
                e.preventDefault();
                $('#logreg-forms .form-signin').toggle(); // display:block or none
                $('#logreg-forms .form-signup').toggle(); // display:block or none
            }

            $(() => {
                // Login Register Form
                $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
                $('#logreg-forms #cancel_reset').click(toggleResetPswd);
                $('#logreg-forms #btn-signup').click(toggleSignUp);
                $('#logreg-forms #cancel_signup').click(toggleSignUp);
            })

            $(document).ready(() => {
                const showSignup = '${showSignup}'; 
                
                if (showSignup === 'true') {
                    $('#logreg-forms .form-signin').hide(); 
                    $('#logreg-forms .form-signup').show(); 
                } else {
                    $('#logreg-forms .form-signup').hide();
                }

                // Hi?n th? popup n?u ??ng ký THÀNH CÔNG (t? URL)
                <c:if test="${not empty param.msg}">
                    alert("${param.msg}");
                </c:if>
            });
            // ========================================================
        </script>


    </body>
</html>