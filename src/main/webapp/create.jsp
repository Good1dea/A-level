<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Create a Car</title>
</head>
<body>
	<h1>Create your car</h1>
	<form method="post" action="createCar">
		<label for="Type">Select type:</label>
		<select name="Type" id="Type">
			<option value="CAR">Passenger Car</option>
			<option value="TRUCK">Truck</option>
		</select>
		<br><br>
		<input type="submit" value="Create Car">
	</form>
</body>
</html>