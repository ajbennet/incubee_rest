# Incubee Rest
Rest Server for Incubee
 
## Login API 
If user already present it returns the company ID associated with the user for founders.
If not, it returns a 404 . 

Request
```sh
POST http://www.incub.ee/rest/v1.0/login 
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
POST http://www.incub.ee/rest/v1.0/signup 
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
##Delete User API
```sh
DELETE http://www.incub.ee/rest/v1.0/user?uid={uid}
```
Success Response
```sh
http Code: 200 
Message: User Deleted
````

##Get Incubee
```sh
GET http://www.incub.ee/rest/v1.0/{incubee_id}
```
Success Response
```sh
{
   "company_name":"Socigo",
   "company_url":"www.socigo.com",
   "logo_url":null,
   "high_concept":"A New Generation Open Source Gaming Platform",
   "description":"Socigo rethinks the way gaming platform for the modern gamers.",
   "twitter_url":"https://twitter.com/socigo",
   "video_url":null,
   "founder":"Mark Cuban",
   "location":"Belgium",
   "contact_email":null,
   "images":[
      "https://incubee-images.s3.amazonaws.com/img_246b5df9-bd5d-46be-ba4f-301c971c1b5b",
      "https://incubee-images.s3.amazonaws.com/img_246c39af-d880-40b7-937a-242227be220b",
      "https://incubee-images.s3.amazonaws.com/img_4a9c58b5-fc53-4323-9b10-8c4f7a3328da",
      "https://incubee-images.s3.amazonaws.com/img_54099163-d0bc-4931-9eca-697884633629"
   ],
   "video":"https://incubee-images.s3.amazonaws.com/vid_78fab564-c311-4cdd-8589-d4390673440e",
   "funding":true,
   "project_status":"Just-launched",
   "field":"Gaming",
   "id":"inc_952745e0-ea2e-4365-83b3-cd379072ce57"
}
```

##Get All Incubees
```sh
GET http://www.incub.ee/rest/v1.0/all
```

Success Response
```sh
[
   {
      "company_name":"Socigo",
      "company_url":"www.socigo.com",
      "logo_url":null,
      "high_concept":"A New Generation Open Source Gaming Platform",
      "description":"Socigo rethinks the way gaming platform for the modern gamers.",
      "twitter_url":"https://twitter.com/socigo",
      "video_url":null,
      "founder":"Mark Cuban",
      "location":"Belgium",
      "contact_email":null,
      "images":[
         "https://incubee-images.s3.amazonaws.com/img_246b5df9-bd5d-46be-ba4f-301c971c1b5b",
         "https://incubee-images.s3.amazonaws.com/img_246c39af-d880-40b7-937a-242227be220b",
         "https://incubee-images.s3.amazonaws.com/img_4a9c58b5-fc53-4323-9b10-8c4f7a3328da",
         "https://incubee-images.s3.amazonaws.com/img_54099163-d0bc-4931-9eca-697884633629"
      ],
      "video":"https://incubee-images.s3.amazonaws.com/vid_78fab564-c311-4cdd-8589-d4390673440e",
      "funding":true,
      "project_status":"Just-launched",
      "field":"Gaming",
      "id":"inc_952745e0-ea2e-4365-83b3-cd379072ce57"
   },
   {
      "company_name":"Hipmunk",
      "company_url":"www.hipmunk.com",
      "logo_url":null,
      "high_concept":"Search flights and hotels faster ",
      "description":"Fastest way to travel and Hipmunk makes it easier.",
      "twitter_url":"http://twitter.com/hipmunk",
      "video_url":null,
      "founder":"Elon Musk",
      "location":"WA",
      "contact_email":null,
      "images":[
         "https://incubee-images.s3.amazonaws.com/img_3233ac62-7e66-4f50-b5ed-99260ed08cdb",
         "https://incubee-images.s3.amazonaws.com/img_9d3cb74d-dde2-4234-8981-6e6582099492",
         "https://incubee-images.s3.amazonaws.com/img_ad648878-2185-4f78-8682-42f286d75f93",
         "https://incubee-images.s3.amazonaws.com/img_dfc833ce-d07b-43ce-94b3-df61b0482058"
      ],
      "video":"https://incubee-images.s3.amazonaws.com/vid_9a1bf404-da5d-419f-95ca-6446988268e2",
      "funding":true,
      "project_status":"Launched-with-customers",
      "field":"Travel",
      "id":"inc_68237436-bedf-4aa8-8cd9-321893d1255c"
   },
   {
      "company_name":"DollarShaveClub",
      "company_url":"http://www.dollarshaveclub.com/",
      "logo_url":null,
      "high_concept":"Get a shaving kit for just a dollar a month.",
      "description":"A great shave for a few bucks no commitments. no fees. Do it.",
      "twitter_url":"https://twitter.com/dollarshaveclub",
      "video_url":null,
      "founder":"Michael Dubin",
      "location":"Venice, CA",
      "contact_email":null,
      "images":[
         "https://incubee-images.s3.amazonaws.com/img_2b7c82a1-02f6-41bc-97f2-c77aa3881876",
         "https://incubee-images.s3.amazonaws.com/img_4a783486-efbd-4e6f-9a98-136dc91d4e36",
         "https://incubee-images.s3.amazonaws.com/img_d8078fd9-58c3-41d1-9a2d-eac995cbe7b6",
         "https://incubee-images.s3.amazonaws.com/img_f4b23aa8-fb22-4d84-9255-5fee63490b5b"
      ],
      "video":"https://incubee-images.s3.amazonaws.com/vid_ee73895c-a4cf-4220-8cbf-10df8b2b4091",
      "funding":false,
      "project_status":"Launched-with-customers",
      "field":"ecommerce",
      "id":"inc_e14651b7-1f65-460f-8841-5cb716236704"
   }
]
```



## Like API 
API called when a user likes a startup

```sh
POST http://www.incub.ee/rest/v1.0/like/{incubee_id}?uid={uid}
```

Success Response
```sh
http
{  
   "statusMessage":"Success",
   "statusCode":"LIK_1000",
}
```


## Get All Likes
```sh
GET http://www.incub.ee/rest/v1.0/like?id={user_id}

```

Success Response
```sh
http Code: 200
{  
   "statusMessage":"Success",
   "statusCode":"LIK_1000",
   "incubeeList":[
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
POST http://www.incub.ee/rest/v1.0/customer/{incubee_id}?uid={user_id}
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
GET http://www.incub.ee/rest/v1.0/customer?id={incubee_id}
```

Success Response
```sh
{
   "statusMessage":"Success",
   "statusCode":"CUS_1000",
   "customerList":[
      {
         "id":"11442342337697974",
         "image_url":"https://lh4.googleusercontent.com/-CL6coBFm9VE/AAAdsdsAAAAAI/AAAAAAAAHCk/ngCxGax3Tcc/s96-c/photo.jpg",
         "email":"john@gmail.com",
         "name":"John Doe"
      }
   ]
}
```

## Send Message API

Send a message to another user.

Request
```sh
POST http://www.incub.ee/rest/v1.0/msg?eid={uid}
```
Requestbody
```sh
{
	"body":"Hi I like this idea",
	"eid":{uid},
	"name":"Abinathab Bennet",
	"to":"{to_uid}",
	"longitude":914,
	"latitude":323,
	"type":"USR"
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
GET http://www.incub.ee/rest/v1.0/msg/all?eid={eid}
```

Response

```sh
httpCode : 200 OK
{
   "statusMessage":"Success",
   "statusCode":"MSGS_1000",
   "messages":[
      {
         "mid":"msg_zbnhto3a4tubsz9",
         "to":"110310242727937004157",
         "eid":"110489314263267697974",
         "time":1438140708801,
         "stime":1438140708801,
         "status":"NEW",
         "name":"Abinathab Bennet",
         "body":"Hi I like this idea",
         "type":"USR",
         "dir":"O",
         "lattitude":323,
         "longitude":914,
         "media":null
      }
    ]
}
```

## Get Message for Message ID
```sh
GET http://www.incub.ee/rest/v1.0/msg/{mid}?eid={eid}

eg: http://www.incub.ee/rest/v1.0/msg/msg_zbnhto3a4tubsz9?eid=110489314263267697974
```

Response

```sh
httpCode : 200 OK
{
   "statusMessage":"Success",
   "statusCode":"MSG_1000",
   "messages":[
      {
         "mid":"msg_zbnhto3a4tubsz9",
         "to":"110310242727937004157",
         "eid":"110489314263267697974",
         "time":1438140708801,
         "stime":1438140708801,
         "status":"NEW",
         "name":"Abinathab Bennet",
         "body":"Hi I like this idea",
         "type":"USR",
         "dir":"O",
         "lattitude":323,
         "longitude":914,
         "media":null
      }
    ]
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

## Get Message for Message ID
```sh
GET http://www.incub.ee/rest/v1.0/customer/details?id={userid}

eg: GET http://www.incub.ee/rest/v1.0/customer/details?id=110489432434234974
```

Response

```sh
httpCode : 200 OK
{
   "statusMessage":"Success",
   "statusCode":"CUS_1000",
   "customerList":[
      {
         "id":"110483234342397974",
         "image_url":"https://lh4.googleusercontent.com/-CL6coBFm9VE/AADAAAAAAAI/AAAAAAAAHCk/ngCxGax3Tcc/s96-c/photo.jpg",
         "email":"john@gmail.com",
         "name":"John Doe"
      }
   ]
}
```

