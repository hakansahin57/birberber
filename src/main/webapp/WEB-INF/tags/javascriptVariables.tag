<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="variable" tagdir="/WEB-INF/tags/" %>


<spring:htmlEscape defaultHtmlEscape="true"/>

<%-- JS configuration --%>
<script>
    /*<![CDATA[*/
    <%-- Define a javascript variable to hold the content path --%>
    var ACC = {config: {}};
</script>
<variable:message/>