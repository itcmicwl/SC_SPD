<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<style>
		.testPanel{
		    padding:2px;
			border:1px solid grey;
		}
		.field{
			padding:2px;
		}
		label {
		   display:inline-block;
		   width:6em;
		   text-align:left;
		}
	</style>
</head>
<body>
	<h2>中Hello World!</h2>
	<div class = "testPanel">
		<h3>v1</h3>
		<form method="post" action="validate/ajax">
			<div class = "field"><label>id:</label><input type="text" name="id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password"/></div>
			<div class = "field"><input type="submit" /></div>
		</form>
	</div>
	<div class = "testPanel">
		<h3>传对象数组</h3>
		<form method="post" action="vto/array1">
			<div class = "field"><label>id:</label><input type="text" name="id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password"/></div>
			<div class = "field"><label>id:</label><input type="text" name="id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password"/></div>
			<div class = "field"><label>id:</label><input type="text" name="id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password"/></div>
			<div class = "field"><input type="submit" /></div>
		</form>
	</div>
	<div class = "testPanel">
		<h3>传对象数组1</h3>
		<form method="post" action="vto/array1">
			<div class = "field"><label>id:</label><input type="text" name="id[0]"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name[0]"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password[0]"/></div>
			<div class = "field"><label>id:</label><input type="text" name="id[1]"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name[1]"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password[1]"/></div>
			<div class = "field"><label>id:</label><input type="text" name="id[2]"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="name[2]"/></div>
			<div class = "field"><label>password:</label><input type="text" name="password[2]"/></div>
			<div class = "field"><input type="submit" /></div>
		</form>
	</div>
	<div class = "testPanel">
		<h3>传对象数组2</h3>
		<form method="post" action="vto/array2" enctype="application/x-www-form-urlencoded">
			<div class = "field"><label>id:</label><input type="text" name="users[0].id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="users[0].name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="users[0].password"/></div>
			<div class = "field"><label>id:</label><input type="text" name="users[1].id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="users[1].name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="users[1].password"/></div>
			<div class = "field"><label>id:</label><input type="text" name="users[2].id"/></div>			
			<div class = "field"><label>name:</label><input type="text" name="users[2].name"/></div>
			<div class = "field"><label>password:</label><input type="text" name="users[2].password"/></div>
			<div class = "field"><label>msg:</label><input type="text" name="msg"/></div>
			<div class = "field"><label>writeDate:</label><input type="text" name="writeDate"/></div>
			<div class = "field"><label>total:</label><input type="text" name="total"/></div>
			<div class = "field"><label>vals:</label><input type="radio" name="vals" value="0"/></div>
			<div class = "field"><label>vals:</label><input type="radio" name="vals" value="1"/></div>
			<div class = "field"><input type="submit" /></div>
		</form>
	</div>
</body>
</html>
