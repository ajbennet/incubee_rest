# Incubee Rest
Rest Server for Incubee
 
## Login API 
If user already present it returns the company ID associated with the user for founders.
If not, it returns a 404 . 
user_type - can have values I/U/F corresponding to Investor/User/Founder respectively

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
```

Success Response

```sh
{
   "statusMessage":"Success",
   "statusCode":"LOG_1000",
   "servicedata":{
      "user_type":"I",
      "company_id":"inc_952745e0-ea2e-4365-83b3-cd379072ce57"
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
```

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
```

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

## Get Customer Details for User ID
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

## Get Customer Details for User ID

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

## Create Review

‘meeting’ parameter can take these values "PER","PHO" which corresponds to In-Person meeting and Phone meeting.
‘status’ parameter can take these values "INT","INV,"PAS" which corresponds to Interested, Invested, Passed.


```sh
POST http://www.incub.ee/incubee/rest/v1.0/review?uid=110489314263267697974

POST data:
{
	"title":"Loved It",
	"description":"The team looks sharp and the product has great potential",
	"incubee_id":"inc_551573ad-563b-40cc-b3a8-5a8ad57b1506",
	"rating":4,
	"meeting":"PER",
	"status":"INT"
}

```

Response

```sh

{
   "statusMessage":"Success",
   "statusCode":"REV_1000",
   "reviews":[
      {
         "incubee_id":"inc_551573ad-563b-40cc-b3a8-5a8ad57b1506",
         "title":"Loved It",
         "description":"The team looks sharp and the product has great potential",
         "rating":4,
         "user_id":"110489314263267697974",
         "meeting":"PER",
         "status":"INT",
         "date":1494960321895,
         "replies":0,
         "views":1,
         "likes":0,
         "dislikes":0,
         "review_id":"rev_77d1d45c-0842-4f44-ae72-e0316343cc6a"
      }
   ]
}

```

## Edit Review

```sh
PUT http://www.incub.ee/rest/v1.0/review?uid=110489314263267697974&review_id=rev_61cc263f-4949-4a42-90ac-33768a122cbc

PUT data:
{
	"title":"Updated - Loved It",
	"description":"Updated - The team looks sharp and the product has great potential",
	"incubee_id":"inc_551573ad-563b-40cc-b3a8-5a8ad57b1506",
	"rating":5,
	"meeting":"PHO",
	"status":"PAS"
}

```

Response

```sh
{"statusMessage":"Success","statusCode":"REV_1000"}
```

## Delete Review

```sh
DELETE http://www.incub.ee/rest/v1.0/review?uid=110489314263267697974&review_id=rev_61cc263f-4949-4a42-90ac-33768a122cbc
```

Response

```sh
{"statusMessage":"Success","statusCode":"REV_1000"}
```



## Get All Reviews for Incubee

```sh
GET http://www.incub.ee/incubee/rest/v1.0/review/{incubee_id}

Eg: GET http://www.incub.ee/incubee/rest/v1.0/review/inc_551573ad-563b-40cc-b3a8-5a8ad57b1506
```

Response

```sh
{
   "statusMessage":"Success",
   "statusCode":"REV_1000",
   "reviewData":{
      "noOfRatings":1,
      "averageRating":4.0,
      "noOfStars":[
         0,
         0,
         0,
         1,
         0
      ]
   },
   "reviews":[
      {
         "incubee_id":"inc_551573ad-563b-40cc-b3a8-5a8ad57b1506",
         "title":"Loved It",
         "description":"The team looks sharp and the product has great potential",
         "rating":4,
         "user_id":"110489314263267697974",
         "meeting":"PER",
         "status":"INT",
         "date":1453543191808,
         "replies":0,
         "views":0,
         "likes":0,
         "dislikes":0
      }
   ]
}

```
## Invite founder

```sh
POST http://www.incub.ee/incubee/rest/v1.0/invite/{email_id}?uid={uid}

eg: POST http://www.incub.ee/incubee/rest/v1.0/invite/abi@incub.ee?uid=110489314263267697974
```

Response

```sh
{  "statusMessage":"Success","statusCode":"INV_1000"}

```

## Create adhoc Incubee

```sh
POST http://www.incub.ee/incubee/rest/v1.0/adhocincubee?uid=1104893142263267697974

POST data:
{
   "name":"Abi's Startup",
   "email_id":"abinathab@gmail.com"
}

```

```sh

{
   "statusMessage":"Success",
   "statusCode":"ADH_1000",
   "incubeeList":[
      {
         "id":"adh_a8dacc6b-7857-4ca5-8cc8-1e398ab8a3d2",
         "email_id":"abinathab@gmail.com",
         "name":"Abi's Startup",
         "created_by_id":"110489314263267697974"
      }
   ]
}
```

## Get all adhoc Incubees

```sh
GET http://www.incub.ee/incubee/rest/v1.0/adhocincubee
```

```sh
{
   "statusMessage":"Success",
   "statusCode":"ADH_1000",
   "incubeeList":[
      {
         "id":"adh_2cd40246-cd81-4a74-889b-18071e7aaaaf",
         "email_id":"abinathab@gmail.com",
         "name":"Abi's Startup",
         "created_by_id":"110489314263267697974"
      },
      {
         "id":"adh_f4240614-c55c-42b9-bd3d-8e733283cfb7",
         "email_id":"abinathab@gmail.com",
         "name":"Abinathab Bennet",
         "created_by_id":"110489314263267697974"
      },
      {
         "id":"adh_ef5d5362-8903-4fee-965a-77e621a8a9e4",
         "email_id":"abinathab@gmail.com",
         "name":"Abi's Startup",
         "created_by_id":"110489314263267697974"
      }
   ]
}
```

## Create incubee with images/video

```sh
POST http://www.incub.ee/rest/handle
```

Headers

```sh

Accept-Encoding:gzip, deflate
Accept-Language:en-US,en;q=0.8
Content-Length:31981
Content-Type:multipart/form-data; boundary=----WebKitFormBoundarywNk6cpyuLOamnsFg

```

Payload

```sh

------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="company_name"

Seed Invest
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="company_url"

Investors playground
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="logo_url"

Logo URL
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="high_concept"

High concept or a brief description
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="description"

Description about the company/product
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="twitter_url"

Twitter URL
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="video_url"

Video URL
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="location"

Location
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="founder"

Information about the founder
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="field"

Field
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="images"; filename="SeedInvest_Logo.png"
Content-Type: image/png


------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="video"; filename=""
Content-Type: application/octet-stream


------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="token"

{"name":"Abs msft","id":"103142199198521131634","image_url":"https://lh3.googleusercontent.com/-kGTeAHkXY90/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV2ypT8UI5mQFAsFsCQYRGOrQMTeyw/s96-c/photo.jpg","email":"absmsft@gmail.com","token":"eyJhbGciOiJSUzI1NiIsImtpZCI6IjcwYWFlMDk3YmUwZmEyYTk5OTg1MmQ1N2E0ODBlNGNhZDZiZGI4MWMifQ.eyJpc3MiOiJhY2NvdW50cy5nb29GUuY29tIiwiaWF0IjoxNDg3OTExMjUyLCJleHAiOjE0ODc5MTQ4NTIsImF0X2hhc2giOiJUemh6Y3B5eEVXX21KamlOZDQ0UG5nIiwiYXVkIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzE0MjE5OTE5ODUyMTEzMTYzNCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiIxMDc5MjE4MzY5NzUzLTNmZzlzb3U0MGtkcmpiNWhzZm5tMG9vOWpqMGRvazlhLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJhYnNtc2Z0QGdtYWlsLmNvbSIsIm5hbWUiOiJBYnMgbXNmdCIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vLWtHVGVBSGtYWTkwL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FBb212VjJ5cFQ4VUk1bVFGQXNGc0NRWVJHT3JRTVRleXcvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IkFicyIsImZhbWlseV9uYW1lIjoibXNmdCIsImxvY2FsZSI6ImVuIn0.J8L1YSPelsPxyGOuBmCIskbqeNM1Yr6avGYZoKQx2MPoc6wdJIXxJZ1rwPl3WjqSYjvUv_FlECOAZpO_Rmdw0qEBMkMw36c9Xcnf9iXVsZUjRtndzoSeG3r1DQu7Y-ZVuQ3xWMljhDJOlw5hjCHJv7PsuA00fbN7KWxiStiL0PmIpvgmjXSk1O_2d4jOljpwQw_jEKi5Ny1Xc223MEEZi7-Xsb1jQHj-WazBe9yXARfzJf4Pv93cIJNvnaOyDRYyluCCd3bGLKBY9aN5o7m-dYk3AK-RgzPdM8JMZ3inrLAtqkzAxhj5aqdU8IYDICvI7xAW9wU7py9e5IG5bQCW_Q"}
------WebKitFormBoundarywNk6cpyuLOamnsFg
Content-Disposition: form-data; name="id"


------WebKitFormBoundarywNk6cpyuLOamnsFg--

```

Possible success responses

```sh
{
	"statusMessage":"User & Incubee created",
	"statusCode":"INC_1000"
}
```

```sh
{
	"statusMessage":"Incubee created and user updated with company information",
	"statusCode":"INC_1000"
}
```
```sh
{
	"statusMessage":"Incubee Created for the admin user",
	"statusCode":"INC_1000"
}
```
```sh
{
	"statusMessage":"Incubee Updated",
	"statusCode":"INC_1000"
}
```
Possible failure responses

```sh
{
	"statusMessage":"Incubee created but user creation failed",
	"statusCode":"INC_1001"
}
```
```sh
{
	"statusMessage":"Incubee creation or updation failed",
	"statusCode":"INC_1002"
}
```
```sh
{
	"statusMessage":"Incubee created but user failed to update",
	"statusCode":"INC_1003"
}
```