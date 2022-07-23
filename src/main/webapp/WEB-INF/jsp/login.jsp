<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iofrm</title>
    <link rel="stylesheet" type="text/css" href="/ui/style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/ui/style/fontawesome-all.min.css">
    <link rel="stylesheet" type="text/css" href="/ui/style/iofrm-style.css">
    <link rel="stylesheet" type="text/css" href="/ui/style/iofrm-theme14.css">
</head>
<body>
    <div class="form-body" class="container-fluid">
        <div class="row">
            <div class="form-holder">
                <div class="form-content">
                    <div class="form-items">
                        <div class="website-logo-inside">
                            <a href="/">ANASAYFA
                                <div class="logo">
                                    <img class="logo-size" src="images/logo-light.svg" alt="">
                                </div>
                            </a>
                        </div>
                        <h3>Get more things done with Loggin platform.</h3>
                        <p>Access to the most powerfull tool in the entire design and web industry.</p>
                        <div class="page-links">
                            <a href="/login" class="active">Login</a><a href="/register">Register</a>
                        </div>
                        <form:form action="/login" method="post" modelAttribute="loginForm">
                            <input class="form-control" type="text" name="username" placeholder="E-mail Address" required>
                            <input class="form-control" type="password" name="password" placeholder="Password" required>
                            <div class="form-button">
                                <button id="submit" type="submit" class="ibtn">Login</button>
                            </div>
                        </form:form>
                        <div class="other-links">
                            <span>Or login with</span><a href="#"><i class="fab fa-facebook-f"></i></a><a href="#"><i class="fab fa-google"></i></a><a href="#"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript" src="/ui/js/jquery.min.js"></script>
<script type="text/javascript" src="/ui/js/popper.min.js"></script>
<script type="text/javascript" src="/ui/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/ui/js/main.js"></script>
</body>
</html>