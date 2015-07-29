# Incubee Rest
Rest Server for Incubee
 
## Login API 
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

## Signup API 
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

{  
   "statusMessage":"Success",
   "statusCode":"SIGN_1000"
}
```

User already present Response
```sh
http code: 409 Conflict
{
	"statusMessage":"User already found with that User ID please login",
	"statusCode":"SIGN_1004"
}
```


## Like API 
API called when a user likes a startup

```sh
POST http://www.incub.ee/rest/like/{StartupId}?uid={uid}
```

Success Response
```sh
http
{  
   "statusMessage":"Success",
   "statusCode":"LIK_1000",
}
```

#3 Get All Likes
```sh
GET http://www.incub.ee/rest/like?uid={uid}
```

Success Response
```sh
http
{  
   "statusMessage":"Success",
   "statusCode":"LIK_1000",
   "likedIncubees":[
   {incubeeId1},
   {incubeeId2},
   {incubeeId3},
   {incubeeId4},
   {incubeeId5},
   {incubeeId6}
   ]
}
```
## Customer API 
API called when a user likes a startup

```sh
POST http://www.incub.ee/rest/customer/{StartupId}?uid={uid}
```

Success Response
```sh
http
{  
   "statusMessage":"Success",
   "statusCode":"CUS_1000",
}
```

#3 Get All Customers
```sh
GET http://www.incub.ee/rest/customer?uid={uid}
```

Success Response
```sh
http
{  
   "statusMessage":"Success",
   "statusCode":"CUS_1000",
   "likedIncubees":[
   {incubeeId1},
   {incubeeId2},
   {incubeeId3},
   {incubeeId4},
   {incubeeId5},
   {incubeeId6}
   ]
}

## Send Message API

Send a message to another user.

Request
```sh
POST http://www.incub.ee/rest/msg/{uid}
```
Requestbody
```sh
{
	"body":"Hi I like this idea",
	"uid":"{uid}",
	"name": "{name}",
	"to": "{touid}",
	"longitude": 914,
	"latitude" : 323
}
```

Response

```sh
httpCode : 200 OK
{  
   "statusMessage":"Success",
   "statusCode":"MSG_1000"
}
```

## Get Messages API
```sh
GET http://www.incub.ee/rest/msg/all/{eid}
```

Response

```sh
httpCode : 200 OK
{  
   "statusMessage":"Success",
   "statusCode":"MSG_1000"
   "messages" : [ {
        "body" : "Hi hows it going.",
        "dir" : "I",
        "mid" : 2333,
        "name" : "Mark Cuban",
        "status" : "N",
        "stime" : 1301419911000,
        "time" : 1301419912000,
        "to" : "2345452643643",
        "type" : "umsg"
      },
      { "body" : "Im good you",
        "dir" : "O",
        "mid" : 23432,
        "name" : "Abs",
        "status" : "N",
        "stime" : 1301370381000,
        "time" : 1301370381000,
        "to" : "14083984358",
        "type" : "umsg"
} ],
}
```

Messaging states

1) User A sends a message to Company B

| Action                        | A Inbox                       |       B Inbox		           |
|-------------------------------|-------------------------------|------------------------------|
| A sends  message to           |  mid: 2324,  dir: O,          | mid: 2324_1, dir: I,         |
| company B                     |  status: NEW,  type: USR      | status: NEW, type: USR       |
|                               | to: companyID(B),eid: uid(A)  | eid: companyID(B) to: uid(A) |
|                               |                               |                              |
| B receives message            | mid: 2324,  dir: O,       	| mid: 2324_1, dir: I,         |
|                               | status: RRV                   | status: NEW                  |
|                               |                               |                              |
| B reads message               | mid: 2324, dir: O,        	| mid: 2324_1, dir: I,         |
|                               | status: RRE                   | status: REA                  |
|                               |                               |                              |
| B deletes message             | mid: 2324, dir: O,        	| Message deleted              |
|                               | status: RRE                   |                              |


2) Company B sends a message to User A


| Action                        | A Inbox                       |       B Inbox		           |
|-------------------------------|-------------------------------|------------------------------|
| Company B sends  message to   |  mid: 2325,  dir: O,          | mid: 2325_1, dir: I,         |
| User A                        |  status: NEW,  type: INC      | status: NEW, type: INC       |
|                               | to:uid(A)  ,eid: companyID(B) | eid:uid(A)  to:  companyID(B)|
|                               |                               |                              |
| B receives message            | mid: 2325,  dir: O,       	| mid: 2325_1, dir: I,         |
|                               | status: RRV                   | status: NEW                  |
|                               |                               |                              |
| B reads message               | mid: 2325, dir: O,        	| mid: 2325_1, dir: I,         |
|                               | status: RRE                   | status: REA                  |
|                               |                               |                              |
| B deletes message             | mid: 2325, dir: O,        	| Message deleted              |
|                               | status: RRE                   |                              |

