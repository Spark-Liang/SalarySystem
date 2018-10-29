<!DOCTYPE html>
<html lang="en">
<head>
<base href="${basePath}">
<meta charset="UTF-8">
<title>登录页面</title>
</head>
<body>
	<h2>登录页面</h2>
	<form action="user/login" method="post">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="username"></td>       
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">LOG IN</button> &nbsp;
					<button type="reset">RESET</button>
			</tr>
		</table>
	</form>
</body>
</html>