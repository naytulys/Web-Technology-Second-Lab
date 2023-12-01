<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>\
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="../layouts/default.jsp" />

<c:if test="${not empty sessionScope['role']}">
    <c:if test="${sessionScope['role'] == 1}">
        <a href="/apanel"> Admin panel</a>
    </c:if>
</c:if>
<div class="uk-container uk-margin-medium uk-margin-large-bottom">
    <h4 class="uk-heading-bullet uk-margin-small-bottom">My bookings</h4>
    <div class="uk-margin-bottom">
        <c:if test="${empty resultList}">
            <div class="uk-card uk-margin uk-card-default uk-card-body uk-card-small">
                <h5 class="uk-text-bolder uk-text-danger uk-text-italic uk-text-center">Looks like there is nothing to display...</h5>
            </div>
        </c:if>
        <c:forEach items="${resultList}" var="record" varStatus="status">
            <div class="uk-card uk-margin uk-card-default uk-card-body uk-card-small">
                <div class="uk-flex">
                    <div class="uk-width-1-2">
                        <h5 class="uk-text-bolder uk-margin-remove-bottom">Booking from <i><c:out value="${record.booking_time}"/></i></h5>
                        <c:if test="${record.discount_percent > 0}">
                            <h5 class="uk-text-bolder uk-margin-remove-bottom uk-margin-remove-top">Discount <i></i><c:out value="${record.discount_percent}"/>%</i></h5>
                        </c:if>
                    </div>
                    <div class="uk-width-1-2 uk-text-right">
                        <div>
                            <a href="#delete-modal-<c:out value="${record.id}"/>" class="uk-icon-link" uk-icon="icon: trash; ratio: 1.5" uk-tooltip="Delete booking" uk-toggle></a>
                            <c:if test="${record.approved eq 0}">
                                <a href="#edit-modal-<c:out value="${record.id}"/>" class="uk-icon-link uk-margin-left" uk-icon="icon: pencil; ratio: 1.5" uk-toggle uk-tooltip="Edit booking details"></a>
                            </c:if>
                        </div>
                    </div>
                </div>
                <hr>
                <div>
                    Amount of guests: <i><c:out value="${record.booking_amount}"/></i> • Booking date: <i><c:out value="${record.booking_date}"/></i> •
                    <c:if test="${record.approved eq 1}">
                        Status: <span class="uk-text-success uk-text-italic">Approved</span>
                    </c:if>
                    <c:if test="${record.approved eq 0}">
                        Status: <span class="uk-text-primary uk-text-italic">Pending review by administrator</span>
                    </c:if>
                    <c:if test="${record.approved eq 2}">
                       Status: <span class="uk-text-danger uk-text-italic">Not approved</span>
                    </c:if>
                </div>
            </div>

            <div id="delete-modal-<c:out value="${record.id}"/>" class="uk-flex-top" uk-modal>
                <div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
                    <button class="uk-modal-close-default" type="button" uk-close></button>
                    <p>Are you sure you want to delete your reservation?</p>
                    <form action="${pageContext.request.contextPath}/DeleteBookingServlet" method="post">
                        <input type="hidden" name="id" value="<c:out value="${record.id}"/>">
                        <div class="uk-text-right uk-margin-top">
                            <button type="submit" class="uk-button uk-button-danger uk-text-right">Delete</button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="edit-modal-<c:out value="${record.id}"/>" class="uk-flex-top" uk-modal>
                <div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
                    <button class="uk-modal-close-default" type="button" uk-close></button>
                    <p>Update reservation details</p>
                    <form action="${pageContext.request.contextPath}/UpdateBookingServlet" method="post">
                        <input type="hidden" name="id" value="<c:out value="${record.id}"/>">
                        <div class="uk-flex uk-flex-center uk-flex-middle">
                            <div class="uk-width-1-2">
                                <h5 class="uk-margin-small-bottom">Pick a date for reservation</h5>
                                <label>
                                    <input type="date" class="uk-input" required name="date">
                                </label>
                            </div>
                            <div class="uk-margin-left uk-width-1-2">
                                <h5 class="uk-margin-small-bottom">Amount of guests</h5>
                                <label>
                                    <input type="number" min="1" max="99" class="uk-input" required name="amount">
                                </label>
                            </div>
                        </div>
                        <div class="uk-text-right uk-margin-top">
                            <button type="submit" class="uk-button uk-button-primary uk-text-right">Update</button>
                        </div>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
    <h4 class="uk-heading-bullet uk-margin-small-bottom">Make a reservation</h4>
    <div class="uk-card uk-card-body uk-card-default">
        <form action="${pageContext.request.contextPath}/CreateBookingServlet" method="post">
            <div class="uk-flex uk-flex-center uk-flex-middle">
                <div class="uk-width-1-2">
                    <h5 class="uk-margin-small-bottom">Pick a date for reservation</h5>
                    <label>
                        <input type="date" class="uk-input" required name="date">
                    </label>
                </div>
                <div class="uk-margin-left uk-width-1-2">
                    <h5 class="uk-margin-small-bottom">Amount of guests</h5>
                    <label>
                        <input type="number" min="1" max="99" class="uk-input" required name="amount">
                    </label>
                </div>
            </div>
            <div class="uk-text-right uk-margin-top">
                <button type="submit" class="uk-button uk-button-primary">
                    Request a reservation
                </button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="../layouts/footer.jsp" />