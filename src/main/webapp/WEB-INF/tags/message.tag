<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<script>
    ACC.message  = {};

    ACC.message.goals = {
        updated : '<spring:message code="text.message.goals.updated" htmlEscape="false" javaScriptEscape="true" />'
    }

</script>
