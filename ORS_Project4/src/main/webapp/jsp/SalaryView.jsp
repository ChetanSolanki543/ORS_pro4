<%@page import="com.rays.pro4.controller.SalaryListCtl"%>
<%@page import="com.rays.pro4.controller.SalaryCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Product Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.SalaryBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.SALARY_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						List rlist = (List) request.getAttribute("rlist");
					%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update salary </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add salary </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>



			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				
				<tr>
					<th align="left">Employee<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Employee"
						placeholder="Enter Employee Name" size="25"
						value="<%=DataUtility.getStringData(bean.getEmployee())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("Employee", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>


				 <%-- <tr>
					<th align="left">Amount<span style="color: red">*</span> :
					</th>

					<td><input type="text" name="Amount"
						placeholder="Enter Ammount" size="25"
						value="<%=DataUtility.getStringData(bean.getAmount())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("Amount", request)%></font></td>
				</tr>
				</th>  --%>
				
				<tr>
					<th align="left">Amount<span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("Amount", String.valueOf(bean.getAmount()), rlist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("Amount", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
 

				<tr>
					<th align="left">Applied Date<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Applied Date" size="25" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Status<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Status"
						placeholder="Enter  Status" size="25"
						value="<%=DataUtility.getStringData(bean.getStatus())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Status", request)%></font></td>
				</tr>



				<tr>
					<th style="padding: 1px"></th>
				</tr>


				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=SalaryCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=SalaryListCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=SalaryCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=SalaryCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>