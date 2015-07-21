# Incubee Rest
Rest Server for Incubee
 
# Login API 
If user already present it returns the company ID associated with the user for founders.
If not, it returns a 404 . 

Request
```sh
POST http://www.incub.ee/rest/login 
```

Headers
```sh
Content-Type: application/json; 
```
Payload
```sh
{
   "name":"Abinathab Bennet",
   "id":"110489314263267697974",
   "image_url":"https://lh4.googleusercontent.com/-CL6coBFm9VE/AAAAAAAAAAI/AAAAAAAAHCk/ngCxGax3Tcc/s96-c/photo.jpg",
   "email":"abinathab@gmail.com",
   "token":"eyJhbGciOiJSUzI1NiIsImtpZCI6ImRhNjYyNWIzNmJjMDlkMzAwMzUzYjI4YTc0MWNlMTc1MjVhNGMzM2IifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTEwNDg5MzE0MjYzMjY3Njk3OTc0IiwiYXpwIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImVtYWlsIjoiYWJpbmF0aGFiQGdtYWlsLmNvbSIsImF0X2hhc2giOiJIRVBZblI1UEN6N2JtenR2Qk5HOU13IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF1ZCI6IjEwNzkyMTgzNjk3NTMtM2ZnOXNvdTQwa2RyamI1aHNmbm0wb285amowZG9rOWEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJpYXQiOjE0Mzc0NjI1NjUsImV4cCI6MTQzNzQ2NjE2NSwibmFtZSI6IkFiaW5hdGhhYiBCZW5uZXQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDQuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTDZjb0JGbTlWRS9BQUFBQUFBQUFBSS9BQUFBQUFBQUhDay9uZ0N4R2F4M1RjYy9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQWJpbmF0aGFiIiwiZmFtaWx5X25hbWUiOiJCZW5uZXQiLCJsb2NhbGUiOiJlbiJ9.iLVRfNtK_MZVQtgJFyS-0hk6iJ8JjAT9vr0bg1iwYpryhi2-y2kBF4-qKCM3k_wmYqFh4JJDgbS-_AktT01Wo3kvL7atkQBs3kN8jq9YhTZi5NWkafuQPHB3q4xE8ict_xMjCozfxxAquyDMZzymt_qOP_vDERbU0mrJR2FOLqJNENF29GBUCZjiGxJGEDDP6lnk57ZRLLbc_XpzouowlrOiw2x8u0txXE5fBe6xqa1TDV1Xfa9_eSGSv7azPZPkvS3OUew2KDuCTe6WxwOCnFeiAA5rMOwci_zwyvcDj4bsS8vw-LM-LQs_zXSP2gKxIrM2fn1sestAxtfIonNy2A"
}
````

Success Response
```sh
{  
   "statusMessage":"Success",
   "statusCode":"LOG_1000",
   "servicedata":{  
      "company_id":"inc_952745e0-ea2e-4365-83b3-cd379072ce57\n"
   }
}
```

User not found Response
```sh
{"statusMessage":"User not found","statusCode":"LOG_1003","servicedata":null}
```

# Signup API 
If user already present it returns the company ID associated with the user for founders.
If not, it returns a 404. 

Request
```sh
POST http://www.incub.ee/rest/signup 
```

Headers
```sh
Content-Type: application/json; 
```
Payload
```sh
{
   "name":"Abinathab Bennet",
   "id":"110489314263267697974",
   "image_url":"https://lh4.googleusercontent.com/-CL6coBFm9VE/AAAAAAAAAAI/AAAAAAAAHCk/ngCxGax3Tcc/s96-c/photo.jpg",
   "email":"abinathab@gmail.com",
   "token":"eyJhbGciOiJSUzI1NiIsImtpZCI6ImRhNjYyNWIzNmJjMDlkMzAwMzUzYjI4YTc0MWNlMTc1MjVhNGMzM2IifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTEwNDg5MzE0MjYzMjY3Njk3OTc0IiwiYXpwIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImVtYWlsIjoiYWJpbmF0aGFiQGdtYWlsLmNvbSIsImF0X2hhc2giOiJIRVBZblI1UEN6N2JtenR2Qk5HOU13IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF1ZCI6IjEwNzkyMTgzNjk3NTMtM2ZnOXNvdTQwa2RyamI1aHNmbm0wb285amowZG9rOWEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJpYXQiOjE0Mzc0NjI1NjUsImV4cCI6MTQzNzQ2NjE2NSwibmFtZSI6IkFiaW5hdGhhYiBCZW5uZXQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDQuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTDZjb0JGbTlWRS9BQUFBQUFBQUFBSS9BQUFBQUFBQUhDay9uZ0N4R2F4M1RjYy9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQWJpbmF0aGFiIiwiZmFtaWx5X25hbWUiOiJCZW5uZXQiLCJsb2NhbGUiOiJlbiJ9.iLVRfNtK_MZVQtgJFyS-0hk6iJ8JjAT9vr0bg1iwYpryhi2-y2kBF4-qKCM3k_wmYqFh4JJDgbS-_AktT01Wo3kvL7atkQBs3kN8jq9YhTZi5NWkafuQPHB3q4xE8ict_xMjCozfxxAquyDMZzymt_qOP_vDERbU0mrJR2FOLqJNENF29GBUCZjiGxJGEDDP6lnk57ZRLLbc_XpzouowlrOiw2x8u0txXE5fBe6xqa1TDV1Xfa9_eSGSv7azPZPkvS3OUew2KDuCTe6WxwOCnFeiAA5rMOwci_zwyvcDj4bsS8vw-LM-LQs_zXSP2gKxIrM2fn1sestAxtfIonNy2A"
}
````

Success Response
```sh
httpCode : 201 Created
body
{  
   "statusMessage":"Success",
   "statusCode":"SIGN_1000"
}
```

User already present Response
```sh
http code: 409 Conflict
{
	"statusMessage":"User already found with that User ID please login","statusCode":"SIGN_1004"}
```

