# time-mobile-frontend
<ul>
<li>To login, send a POST to http://localhost:8080/login</li>
<li>Secured endpoint example: http://localhost:8080/api/me</li>
<li>Unsecured endpoint example: http://localhost:8080/unsecured</li>
<li>To logout, send a GET request to: http://localhost:8080/api/logout</li>
</ul>
<b>login headers:</b></br>
X-Requested-With: XMLHttpRequest</br>
Content-Type: application/json</br>
Cache-Control: no-cache</br>
<b>login body:</b></br>
{</br>
"username": "test1",</br>
"password": "test1"</br>
}</br>
</br>
<b>login response:</b></br>
{</br>
"token": "eyJ... the token to send on each request on the X-Authorization header", </br>
"refreshToken": "eyJhbGciO... used to acquire new Access Token calling http://localhost:8080/refresh "</br>
}
</br></br>

<b>Sample GET request:</b></br>
GET /api/me HTTP/1.1</br>
Host: localhost:8080</br>
X-Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsInNjb3BlcyI6IlVTRVIiLCJpc3MiOiJodHRwOi8vdGVzdGluZy5jb20iLCJpYXQiOjE0OTY3MDY4MjQsImV4cCI6MTQ5NjcwNzcyNH0.0L68nr2NapNKdXmahAgD_1AzO_BMvCwQTUdg6x53awuPi5slpiXj-j0B7cjrprCc_ZSj7dZ-DxJcLsqpOodhwQ


<b>Don't forget to configure the Json Web Tokens parameters on the application.yaml file<b>

  tokenExpirationTime: 15 # Number of minutes until token expires</br>
  refreshTokenExpTime: 60 # Number of minutes until a refreshed token expires</br>
  tokenIssuer: http://testing.com</br>
  tokenSigningKey: xm8EV6Hy5RMFK4EEACIDAwQus</br>