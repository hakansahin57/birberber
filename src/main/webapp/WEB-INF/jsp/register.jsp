<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<c:url value="/register" var="registerUrl" />
<c:url value="/login" var="loginUrl" />
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
                        <a href="${loginUrl}">Login</a><a href="${registerUrl}" class="active">Register</a>
                    </div>
                    <form:form action="${registerUrl}" method="post" modelAttribute="registerForm">
                        <input class="form-control" type="text" name="firstName" placeholder="First Name" value="${registerForm.firstName}" required>
                        <input class="form-control" type="text" name="lastName" placeholder="Last Name" value="${registerForm.lastName}" required>
                        <input class="form-control" type="email" name="email" placeholder="E-mail Address" value="${registerForm.email}" required>
                        <input class="form-control" type="password" name="password" placeholder="Password" value="${registerForm.password}" required>
                        <div class="form-button">
                            <button id="submit" type="submit" class="ibtn">Register</button>
                        </div>
                    </form:form>
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