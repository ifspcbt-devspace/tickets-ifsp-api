# Tickets IFSP

# TicketsIFSP/Company

<a id="opIdget_2"></a>

## GET Get company by id

GET /v1/company/{id}

Get company information by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Company found|[CompanyResponse](#schemacompanyresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Company not found|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdupdate"></a>

## PUT Update company

PUT /v1/company/{id}

Update company information

> Body Parameters

```json
{
  "name": "string",
  "description": "string",
  "cnpj": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|body|body|[UpdateCompanyRequest](#schemaupdatecompanyrequest)| no |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Company updated successfully|[CompanyResponse](#schemacompanyresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Company not found|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIddelete"></a>

## DELETE Delete company

DELETE /v1/company/{id}

Delete company by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 204 Response

```json
{}
```

> 401 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|Company deleted successfully|Inline|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Company not found|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdcreate_2"></a>

## POST Create company

POST /v1/company

Create a new company

> Body Parameters

```json
{
  "owner_id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "zip_code": "string"
  }
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateCompanyRequest](#schemacreatecompanyrequest)| no |none|

> Response Examples

> 201 Response

```json
{}
```

> 400 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Company created successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdsearch_1"></a>

## POST Search companies

POST /v1/company/search

Search companies by name, cnpj, state, city, neighborhood, street, number, complement, zip code, phone, email, website, and contact name

> Body Parameters

```json
{
  "filters": [
    {
      "filter_key": "string",
      "value": {},
      "operation": "eq",
      "data_option": "all"
    }
  ],
  "sorts": [
    {
      "sort": "string",
      "direction": "asc"
    }
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|perPage|query|integer(int32)| no |none|
|body|body|[AdvancedSearchRequest](#schemaadvancedsearchrequest)| no |none|

> Response Examples

> 200 Response

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "name": "string",
      "description": "string",
      "cnpj": "string",
      "address": {
        "street": "string",
        "number": "string",
        "complement": "string",
        "neighborhood": "string",
        "city": "string",
        "state": "string",
        "country": "string",
        "zip_code": "string"
      }
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Companies found|[PaginationSearchCompanyResponse](#schemapaginationsearchcompanyresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

# TicketsIFSP/Auth

<a id="opIdgetUserById"></a>

## GET Get user by id

GET /v1/auth/{id}

Get user information by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "username": "string",
  "role": {
    "code": 0,
    "description": "string"
  },
  "birth_date": "string",
  "document_initials": "string",
  "phone_number_initials": "string",
  "company_id": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|User found successfully|[UserResponse](#schemauserresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|User not found|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdupdateUser"></a>

## PUT Update user

PUT /v1/auth/{id}

Update user information

> Body Parameters

```json
{
  "name": "string",
  "bio": "string",
  "birth_date": "string",
  "document": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|body|body|[UpdateUserRequest](#schemaupdateuserrequest)| no |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "username": "string",
  "role": {
    "code": 0,
    "description": "string"
  },
  "birth_date": "string",
  "document_initials": "string",
  "phone_number_initials": "string",
  "company_id": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|User updated successfully|[UserResponse](#schemauserresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|User not found|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdregister"></a>

## POST Register user

POST /v1/auth/register

Register a new user

> Body Parameters

```json
{
  "name": "string",
  "email": "string",
  "username": "string",
  "password": "string",
  "birth_date": "string",
  "document": "string",
  "phone_number": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[RegisterRequest](#schemaregisterrequest)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|User registered successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdforgotPassword"></a>

## POST Forgot password

POST /v1/auth/recovery/{login}

Send recovery request to user

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|login|path|string| yes |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|Recovery request sent successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid login|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdaccountRecovery"></a>

## POST Change password

POST /v1/auth/recovery/change

Change user password

> Body Parameters

```json
{
  "token": "string",
  "password": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[RecoveryRequest](#schemarecoveryrequest)| no |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|Password changed successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdlogin"></a>

## POST Login

POST /v1/auth/login

Authenticate user

> Body Parameters

```json
{
  "login": "string",
  "password": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[LoginRequest](#schemaloginrequest)| no |none|

> Response Examples

> 200 Response

```json
{
  "token": "string",
  "user": {
    "id": "string",
    "name": "string",
    "email": "string",
    "username": "string",
    "role": {
      "code": 0,
      "description": "string"
    },
    "birth_date": "string",
    "document_initials": "string",
    "phone_number_initials": "string",
    "company_id": "string"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|User logged in successfully|[LoginResponse](#schemaloginresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdactivate"></a>

## POST Activate user

POST /v1/auth/activate/{token}

Activate user account

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|token|path|string| yes |none|

> Response Examples

> 204 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|User activated successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid token|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

# TicketsIFSP/Event

<a id="opIdcreate"></a>

## POST Create event

POST /v1/event

Create a new event

> Body Parameters

```json
{
  "company_id": "string",
  "name": "string",
  "description": "string",
  "init_date": "2019-08-24",
  "end_date": "2019-08-24",
  "configuration": {
    "property1": "string",
    "property2": "string"
  }
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateEventRequest](#schemacreateeventrequest)| no |none|

> Response Examples

> 201 Response

```json
{}
```

> 400 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Event created successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdgetTicketSaleByEventId"></a>

## GET Get ticket sale by event id

GET /v1/event/{id}/ticketSale

Get ticket sale by event id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Ticket Sale list|[PaginationTicketSaleResponse](#schemapaginationticketsaleresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdcreateTicketSale"></a>

## POST Create ticket sale

POST /v1/event/{id}/ticketSale

Create a new ticket sale

> Body Parameters

```json
{
  "name": "string",
  "description": "string",
  "price": 0,
  "entries": 0,
  "active": true
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|body|body|[CreateTicketSaleRequest](#schemacreateticketsalerequest)| no |none|

> Response Examples

> 201 Response

```json
{}
```

> 400 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Ticket Sale created successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdgetThumbnail"></a>

## GET Obter thumbnail

GET /v1/event/{id}/thumbnail

Obter thumbnail do evento.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Thumbnail found|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid id|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Unauthorized|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Thumbnail not found|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIduploadThumbnail"></a>

## POST Upload thumbnail

POST /v1/event/{id}/thumbnail

Upload thumbnail do evento.

> Body Parameters

```yaml
file: ""

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|fileName|query|string| yes |none|
|body|body|object| no |none|
|» file|body|string(binary)| yes |none|

> Response Examples

> 202 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|202|[Accepted](https://tools.ietf.org/html/rfc7231#section-6.3.3)|Thumbnail uploaded|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid id|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Unauthorized|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Event not found|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIddeleteThumbnail"></a>

## DELETE Deletar thumbnail

DELETE /v1/event/{id}/thumbnail

Deletar thumbnail do evento.

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 204 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|Thumbnail deleted|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid id|[APIErrorResponse](#schemaapierrorresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Unauthorized|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Thumbnail not found|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdsearch"></a>

## POST Search events

POST /v1/event/search

Search events by name, date, location, or category

> Body Parameters

```json
{
  "filters": [
    {
      "filter_key": "string",
      "value": {},
      "operation": "eq",
      "data_option": "all"
    }
  ],
  "sorts": [
    {
      "sort": "string",
      "direction": "asc"
    }
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|perPage|query|integer(int32)| no |none|
|body|body|[AdvancedSearchRequest](#schemaadvancedsearchrequest)| no |none|

> Response Examples

> 200 Response

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "name": "string",
      "description": "string",
      "company_id": "string",
      "init_date": "2019-08-24",
      "end_date": "2019-08-24",
      "status": "SCHEDULED",
      "address": {
        "street": "string",
        "number": "string",
        "complement": "string",
        "neighborhood": "string",
        "city": "string",
        "state": "string",
        "country": "string",
        "zip_code": "string"
      },
      "configuration": {
        "property1": "string",
        "property2": "string"
      }
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Events retrieved successfully|[PaginationSearchEventResponse](#schemapaginationsearcheventresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdget_1"></a>

## GET Get event by id

GET /v1/event/{id}

Get event information by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "company_id": "string",
  "init_date": "2019-08-24",
  "end_date": "2019-08-24",
  "status": "SCHEDULED",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  },
  "configuration": {
    "property1": "string",
    "property2": "string"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Event found successfully|[EventResponse](#schemaeventresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Event not found|[APIErrorResponse](#schemaapierrorresponse)|

# TicketsIFSP/Enrollment

<a id="opIdcreate_1"></a>

## POST Create enrollment

POST /v1/enrollment

Create a new enrollment

> Body Parameters

```json
{
  "event_id": "string",
  "name": "string",
  "email": "string",
  "document": "string",
  "birth_date": "2019-08-24",
  "ticket_sale_id": "string",
  "ticket_id": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateEnrollmentRequest](#schemacreateenrollmentrequest)| no |none|

> Response Examples

> Enrollment created successfully

```
"3266a228-d496-4841-b31c-195d1a3e9ee5"
```

> 400 Response

```json
{
  "message": "string",
  "status": 0,
  "errors": [
    {
      "message": "string"
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Enrollment created successfully|string|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdwebhook"></a>

## POST Payment

POST /v1/enrollment/webhook

Receive payment webhook

> Body Parameters

```json
{
  "data": {
    "id": "string"
  },
  "date_created": "2019-08-24T14:15:22Z",
  "action": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreatePaymentRequest](#schemacreatepaymentrequest)| no |none|

> Response Examples

> 201 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Webhook received successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdcreateUpsertEnrollment"></a>

## POST Create or update enrollment

POST /v1/enrollment/upsert

Create or update an enrollment

> Body Parameters

```json
{
  "event_id": "string",
  "name": "string",
  "email": "string",
  "document": "string",
  "birth_date": "2019-08-24",
  "ticket_sale_id": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateUpsertEnrollmentRequest](#schemacreateupsertenrollmentrequest)| no |none|

> Response Examples

> 201 Response

> 400 Response

```json
{
  "message": "string",
  "status": 0,
  "errors": [
    {
      "message": "string"
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Upsert enrollment created successfully|string|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdfindByUser"></a>

## GET List enrollments

GET /v1/enrollment/list

List enrollments by user

> Response Examples

> 200 Response

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "user_id": "string",
      "event_id": "string",
      "created_at": "2019-08-24T14:15:22Z",
      "status": "WAITING_CONFIRMATION",
      "updated_at": "2019-08-24T14:15:22Z"
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Enrollments retrieved successfully|[PaginationEnrollmentResponse](#schemapaginationenrollmentresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|

# TicketsIFSP/Ticket

<a id="opIdcheck"></a>

## PATCH Check ticket

PATCH /v1/ticket/{id}/check

Check ticket by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 202 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|202|[Accepted](https://tools.ietf.org/html/rfc7231#section-6.3.3)|Ticket checked successfully|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid request|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Ticket not found|[APIErrorResponse](#schemaapierrorresponse)|

### Responses Data Schema

<a id="opIdget"></a>

## GET Get ticket by id

GET /v1/ticket/{id}

Get ticket information by id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "id": "string",
  "user_id": "string",
  "document": "string",
  "event_id": "string",
  "description": "string",
  "valid_in": "2019-08-24",
  "expired_in": "2019-08-24",
  "status": "AVAILABLE",
  "code": "string",
  "last_time_consumed": "2019-08-24T14:15:22Z"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Ticket found successfully|[TicketResponse](#schematicketresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Ticket not found|[APIErrorResponse](#schemaapierrorresponse)|

<a id="opIdsimpleSearch"></a>

## GET Search tickets by user

GET /v1/ticket/search/user/{id}

Search tickets by user id

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|page|query|integer(int32)| no |none|
|perPage|query|integer(int32)| no |none|
|terms|query|string| no |none|

> Response Examples

> 200 Response

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "user_id": "string",
      "document": "string",
      "event_id": "string",
      "description": "string",
      "valid_in": "2019-08-24",
      "expired_in": "2019-08-24",
      "status": "AVAILABLE",
      "code": "string",
      "last_time_consumed": "2019-08-24T14:15:22Z"
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Tickets retrieved successfully|[PaginationTicketResponse](#schemapaginationticketresponse)|
|401|[Unauthorized](https://tools.ietf.org/html/rfc7235#section-3.1)|Invalid credentials|[APIErrorResponse](#schemaapierrorresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Access denied|[APIErrorResponse](#schemaapierrorresponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Tickets not found|[APIErrorResponse](#schemaapierrorresponse)|

# TicketsIFSP/Cep

<a id="opIdget_3"></a>

## GET Get address by cep

GET /v1/cep/{cep}

Get address information by brazilian zip code

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|cep|path|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "cep": "string",
  "street": "string",
  "neighborhood": "string",
  "city": "string",
  "state": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Cep found|[ZipCodeResponse](#schemazipcoderesponse)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Cep not found|[APIErrorResponse](#schemaapierrorresponse)|

# Data Schema

<h2 id="tocS_AddressResponse">AddressResponse</h2>

<a id="schemaaddressresponse"></a>
<a id="schema_AddressResponse"></a>
<a id="tocSaddressresponse"></a>
<a id="tocsaddressresponse"></a>

```json
{
  "street": "string",
  "number": "string",
  "complement": "string",
  "neighborhood": "string",
  "city": "string",
  "state": "string",
  "country": "string",
  "zip_code": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|street|string|false|none||none|
|number|string|false|none||none|
|complement|string|false|none||none|
|neighborhood|string|false|none||none|
|city|string|false|none||none|
|state|string|false|none||none|
|country|string|false|none||none|
|zip_code|string|false|none||none|

<h2 id="tocS_CompanyResponse">CompanyResponse</h2>

<a id="schemacompanyresponse"></a>
<a id="schema_CompanyResponse"></a>
<a id="tocScompanyresponse"></a>
<a id="tocscompanyresponse"></a>

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|cnpj|string|false|none||none|
|address|[AddressResponse](#schemaaddressresponse)|false|none||none|

<h2 id="tocS_APIError">APIError</h2>

<a id="schemaapierror"></a>
<a id="schema_APIError"></a>
<a id="tocSapierror"></a>
<a id="tocsapierror"></a>

```json
{
  "message": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|message|string|false|none||none|

<h2 id="tocS_APIErrorResponse">APIErrorResponse</h2>

<a id="schemaapierrorresponse"></a>
<a id="schema_APIErrorResponse"></a>
<a id="tocSapierrorresponse"></a>
<a id="tocsapierrorresponse"></a>

```json
{
  "message": "string",
  "status": 0,
  "errors": [
    {
      "message": "string"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|message|string|false|none||none|
|status|integer(int32)|false|none||none|
|errors|[[APIError](#schemaapierror)]|false|none||none|

<h2 id="tocS_UpdateCompanyRequest">UpdateCompanyRequest</h2>

<a id="schemaupdatecompanyrequest"></a>
<a id="schema_UpdateCompanyRequest"></a>
<a id="tocSupdatecompanyrequest"></a>
<a id="tocsupdatecompanyrequest"></a>

```json
{
  "name": "string",
  "description": "string",
  "cnpj": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|description|string|false|none||none|
|cnpj|string|false|none||none|

<h2 id="tocS_RoleResponse">RoleResponse</h2>

<a id="schemaroleresponse"></a>
<a id="schema_RoleResponse"></a>
<a id="tocSroleresponse"></a>
<a id="tocsroleresponse"></a>

```json
{
  "code": 0,
  "description": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|code|integer(int32)|false|none||none|
|description|string|false|none||none|

<h2 id="tocS_UserResponse">UserResponse</h2>

<a id="schemauserresponse"></a>
<a id="schema_UserResponse"></a>
<a id="tocSuserresponse"></a>
<a id="tocsuserresponse"></a>

```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "username": "string",
  "role": {
    "code": 0,
    "description": "string"
  },
  "birth_date": "string",
  "document_initials": "string",
  "phone_number_initials": "string",
  "company_id": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|name|string|false|none||none|
|email|string|false|none||none|
|username|string|false|none||none|
|role|[RoleResponse](#schemaroleresponse)|false|none||none|
|birth_date|string|false|none||none|
|document_initials|string|false|none||none|
|phone_number_initials|string|false|none||none|
|company_id|string|false|none||none|

<h2 id="tocS_UpdateUserRequest">UpdateUserRequest</h2>

<a id="schemaupdateuserrequest"></a>
<a id="schema_UpdateUserRequest"></a>
<a id="tocSupdateuserrequest"></a>
<a id="tocsupdateuserrequest"></a>

```json
{
  "name": "string",
  "bio": "string",
  "birth_date": "string",
  "document": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|bio|string|false|none||none|
|birth_date|string|false|none||none|
|document|string|false|none||none|

<h2 id="tocS_CreateEventRequest">CreateEventRequest</h2>

<a id="schemacreateeventrequest"></a>
<a id="schema_CreateEventRequest"></a>
<a id="tocScreateeventrequest"></a>
<a id="tocscreateeventrequest"></a>

```json
{
  "company_id": "string",
  "name": "string",
  "description": "string",
  "init_date": "2019-08-24",
  "end_date": "2019-08-24",
  "configuration": {
    "property1": "string",
    "property2": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|company_id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|init_date|string(date)|false|none||none|
|end_date|string(date)|false|none||none|
|configuration|object|false|none||none|
|» **additionalProperties**|string|false|none||none|

<h2 id="tocS_CreateTicketSaleRequest">CreateTicketSaleRequest</h2>

<a id="schemacreateticketsalerequest"></a>
<a id="schema_CreateTicketSaleRequest"></a>
<a id="tocScreateticketsalerequest"></a>
<a id="tocscreateticketsalerequest"></a>

```json
{
  "name": "string",
  "description": "string",
  "price": 0,
  "entries": 0,
  "active": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|description|string|false|none||none|
|price|number|false|none||none|
|entries|integer(int32)|false|none||none|
|active|boolean|false|none||none|

<h2 id="tocS_AdvancedSearchRequest">AdvancedSearchRequest</h2>

<a id="schemaadvancedsearchrequest"></a>
<a id="schema_AdvancedSearchRequest"></a>
<a id="tocSadvancedsearchrequest"></a>
<a id="tocsadvancedsearchrequest"></a>

```json
{
  "filters": [
    {
      "filter_key": "string",
      "value": {},
      "operation": "eq",
      "data_option": "all"
    }
  ],
  "sorts": [
    {
      "sort": "string",
      "direction": "asc"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|filters|[[SearchFilterRequest](#schemasearchfilterrequest)]|false|none||The filters to be used in the search|
|sorts|[[SortSearchRequest](#schemasortsearchrequest)]|false|none||The sorts to be used in the search|

<h2 id="tocS_SortSearchRequest">SortSearchRequest</h2>

<a id="schemasortsearchrequest"></a>
<a id="schema_SortSearchRequest"></a>
<a id="tocSsortsearchrequest"></a>
<a id="tocssortsearchrequest"></a>

```json
{
  "sort": "string",
  "direction": "asc"
}

```

The sorts to be used in the search

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|sort|string|false|none||The field to sort the results by|
|direction|string|false|none||The direction to sort the results by|

#### Enum

|Name|Value|
|---|---|
|direction|asc|
|direction|desc|

<h2 id="tocS_SearchFilterRequest">SearchFilterRequest</h2>

<a id="schemasearchfilterrequest"></a>
<a id="schema_SearchFilterRequest"></a>
<a id="tocSsearchfilterrequest"></a>
<a id="tocssearchfilterrequest"></a>

```json
{
  "filter_key": "string",
  "value": {},
  "operation": "eq",
  "data_option": "all"
}

```

The filters to be used in the search

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|filter_key|string|false|none||The key to search for|
|value|object|false|none||The value to search for|
|operation|string|false|none||The comparison used to search for the term|
|data_option|string|false|none||The option used to combine the search criteria|

#### Enum

|Name|Value|
|---|---|
|operation|eq|
|operation|ne|
|operation|gt|
|operation|ge|
|operation|lt|
|operation|le|
|operation|cn|
|operation|ic|
|operation|bw|
|operation|ib|
|operation|ew|
|operation|ie|
|operation|nc|
|operation|bn|
|operation|en|
|operation|nu|
|operation|nn|
|data_option|all|
|data_option|any|

<h2 id="tocS_PaginationSearchEventResponse">PaginationSearchEventResponse</h2>

<a id="schemapaginationsearcheventresponse"></a>
<a id="schema_PaginationSearchEventResponse"></a>
<a id="tocSpaginationsearcheventresponse"></a>
<a id="tocspaginationsearcheventresponse"></a>

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "name": "string",
      "description": "string",
      "company_id": "string",
      "init_date": "2019-08-24",
      "end_date": "2019-08-24",
      "status": "SCHEDULED",
      "address": {
        "street": "string",
        "number": "string",
        "complement": "string",
        "neighborhood": "string",
        "city": "string",
        "state": "string",
        "country": "string",
        "zip_code": "string"
      },
      "configuration": {
        "property1": "string",
        "property2": "string"
      }
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|currentPage|integer(int32)|false|none||none|
|perPage|integer(int32)|false|none||none|
|total|integer(int64)|false|none||none|
|items|[[SearchEventResponse](#schemasearcheventresponse)]|false|none||none|

<h2 id="tocS_SearchEventResponse">SearchEventResponse</h2>

<a id="schemasearcheventresponse"></a>
<a id="schema_SearchEventResponse"></a>
<a id="tocSsearcheventresponse"></a>
<a id="tocssearcheventresponse"></a>

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "company_id": "string",
  "init_date": "2019-08-24",
  "end_date": "2019-08-24",
  "status": "SCHEDULED",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  },
  "configuration": {
    "property1": "string",
    "property2": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|company_id|string|false|none||none|
|init_date|string(date)|false|none||none|
|end_date|string(date)|false|none||none|
|status|string|false|none||none|
|address|[AddressResponse](#schemaaddressresponse)|false|none||none|
|configuration|object|false|none||none|
|» **additionalProperties**|string|false|none||none|

#### Enum

|Name|Value|
|---|---|
|status|SCHEDULED|
|status|PUBLISHED|
|status|OPENED|
|status|IN_PROGRESS|
|status|CANCELED|
|status|FINISHED|

<h2 id="tocS_CreateEnrollmentRequest">CreateEnrollmentRequest</h2>

<a id="schemacreateenrollmentrequest"></a>
<a id="schema_CreateEnrollmentRequest"></a>
<a id="tocScreateenrollmentrequest"></a>
<a id="tocscreateenrollmentrequest"></a>

```json
{
  "event_id": "string",
  "name": "string",
  "email": "string",
  "document": "string",
  "birth_date": "2019-08-24",
  "ticket_sale_id": "string",
  "ticket_id": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|event_id|string|false|none||none|
|name|string|false|none||none|
|email|string|false|none||none|
|document|string|false|none||none|
|birth_date|string(date)|false|none||none|
|ticket_sale_id|string|false|none||none|
|ticket_id|string|false|none||none|

<h2 id="tocS_CreatePaymentRequest">CreatePaymentRequest</h2>

<a id="schemacreatepaymentrequest"></a>
<a id="schema_CreatePaymentRequest"></a>
<a id="tocScreatepaymentrequest"></a>
<a id="tocscreatepaymentrequest"></a>

```json
{
  "data": {
    "id": "string"
  },
  "date_created": "2019-08-24T14:15:22Z",
  "action": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|data|[DataModel](#schemadatamodel)|false|none||none|
|date_created|string(date-time)|false|none||none|
|action|string|false|none||none|

<h2 id="tocS_DataModel">DataModel</h2>

<a id="schemadatamodel"></a>
<a id="schema_DataModel"></a>
<a id="tocSdatamodel"></a>
<a id="tocsdatamodel"></a>

```json
{
  "id": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|

<h2 id="tocS_CreateUpsertEnrollmentRequest">CreateUpsertEnrollmentRequest</h2>

<a id="schemacreateupsertenrollmentrequest"></a>
<a id="schema_CreateUpsertEnrollmentRequest"></a>
<a id="tocScreateupsertenrollmentrequest"></a>
<a id="tocscreateupsertenrollmentrequest"></a>

```json
{
  "event_id": "string",
  "name": "string",
  "email": "string",
  "document": "string",
  "birth_date": "2019-08-24",
  "ticket_sale_id": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|event_id|string|false|none||none|
|name|string|false|none||none|
|email|string|false|none||none|
|document|string|false|none||none|
|birth_date|string(date)|false|none||none|
|ticket_sale_id|string|false|none||none|

<h2 id="tocS_CreateAddressRequest">CreateAddressRequest</h2>

<a id="schemacreateaddressrequest"></a>
<a id="schema_CreateAddressRequest"></a>
<a id="tocScreateaddressrequest"></a>
<a id="tocscreateaddressrequest"></a>

```json
{
  "street": "string",
  "number": "string",
  "complement": "string",
  "neighborhood": "string",
  "city": "string",
  "state": "string",
  "zip_code": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|street|string|false|none||none|
|number|string|false|none||none|
|complement|string|false|none||none|
|neighborhood|string|false|none||none|
|city|string|false|none||none|
|state|string|false|none||none|
|zip_code|string|false|none||none|

<h2 id="tocS_CreateCompanyRequest">CreateCompanyRequest</h2>

<a id="schemacreatecompanyrequest"></a>
<a id="schema_CreateCompanyRequest"></a>
<a id="tocScreatecompanyrequest"></a>
<a id="tocscreatecompanyrequest"></a>

```json
{
  "owner_id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "zip_code": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|owner_id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|cnpj|string|false|none||none|
|address|[CreateAddressRequest](#schemacreateaddressrequest)|false|none||none|

<h2 id="tocS_PaginationSearchCompanyResponse">PaginationSearchCompanyResponse</h2>

<a id="schemapaginationsearchcompanyresponse"></a>
<a id="schema_PaginationSearchCompanyResponse"></a>
<a id="tocSpaginationsearchcompanyresponse"></a>
<a id="tocspaginationsearchcompanyresponse"></a>

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "name": "string",
      "description": "string",
      "cnpj": "string",
      "address": {
        "street": "string",
        "number": "string",
        "complement": "string",
        "neighborhood": "string",
        "city": "string",
        "state": "string",
        "country": "string",
        "zip_code": "string"
      }
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|currentPage|integer(int32)|false|none||none|
|perPage|integer(int32)|false|none||none|
|total|integer(int64)|false|none||none|
|items|[[SearchCompanyResponse](#schemasearchcompanyresponse)]|false|none||none|

<h2 id="tocS_SearchCompanyResponse">SearchCompanyResponse</h2>

<a id="schemasearchcompanyresponse"></a>
<a id="schema_SearchCompanyResponse"></a>
<a id="tocSsearchcompanyresponse"></a>
<a id="tocssearchcompanyresponse"></a>

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "cnpj": "string",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|cnpj|string|false|none||none|
|address|[AddressResponse](#schemaaddressresponse)|false|none||none|

<h2 id="tocS_RegisterRequest">RegisterRequest</h2>

<a id="schemaregisterrequest"></a>
<a id="schema_RegisterRequest"></a>
<a id="tocSregisterrequest"></a>
<a id="tocsregisterrequest"></a>

```json
{
  "name": "string",
  "email": "string",
  "username": "string",
  "password": "string",
  "birth_date": "string",
  "document": "string",
  "phone_number": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|email|string|false|none||none|
|username|string|false|none||none|
|password|string|false|none||none|
|birth_date|string|false|none||none|
|document|string|false|none||none|
|phone_number|string|false|none||none|

<h2 id="tocS_RecoveryRequest">RecoveryRequest</h2>

<a id="schemarecoveryrequest"></a>
<a id="schema_RecoveryRequest"></a>
<a id="tocSrecoveryrequest"></a>
<a id="tocsrecoveryrequest"></a>

```json
{
  "token": "string",
  "password": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|token|string|false|none||none|
|password|string|false|none||none|

<h2 id="tocS_LoginResponse">LoginResponse</h2>

<a id="schemaloginresponse"></a>
<a id="schema_LoginResponse"></a>
<a id="tocSloginresponse"></a>
<a id="tocsloginresponse"></a>

```json
{
  "token": "string",
  "user": {
    "id": "string",
    "name": "string",
    "email": "string",
    "username": "string",
    "role": {
      "code": 0,
      "description": "string"
    },
    "birth_date": "string",
    "document_initials": "string",
    "phone_number_initials": "string",
    "company_id": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|token|string|false|none||none|
|user|[UserResponse](#schemauserresponse)|false|none||none|

<h2 id="tocS_LoginRequest">LoginRequest</h2>

<a id="schemaloginrequest"></a>
<a id="schema_LoginRequest"></a>
<a id="tocSloginrequest"></a>
<a id="tocsloginrequest"></a>

```json
{
  "login": "string",
  "password": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|login|string|false|none||none|
|password|string|false|none||none|

<h2 id="tocS_TicketResponse">TicketResponse</h2>

<a id="schematicketresponse"></a>
<a id="schema_TicketResponse"></a>
<a id="tocSticketresponse"></a>
<a id="tocsticketresponse"></a>

```json
{
  "id": "string",
  "user_id": "string",
  "document": "string",
  "event_id": "string",
  "description": "string",
  "valid_in": "2019-08-24",
  "expired_in": "2019-08-24",
  "status": "AVAILABLE",
  "code": "string",
  "last_time_consumed": "2019-08-24T14:15:22Z"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|user_id|string|false|none||none|
|document|string|false|none||none|
|event_id|string|false|none||none|
|description|string|false|none||none|
|valid_in|string(date)|false|none||none|
|expired_in|string(date)|false|none||none|
|status|string|false|none||none|
|code|string|false|none||none|
|last_time_consumed|string(date-time)|false|none||none|

#### Enum

|Name|Value|
|---|---|
|status|AVAILABLE|
|status|CONSUMED|
|status|EXPIRED|
|status|CANCELED|

<h2 id="tocS_EventResponse">EventResponse</h2>

<a id="schemaeventresponse"></a>
<a id="schema_EventResponse"></a>
<a id="tocSeventresponse"></a>
<a id="tocseventresponse"></a>

```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "company_id": "string",
  "init_date": "2019-08-24",
  "end_date": "2019-08-24",
  "status": "SCHEDULED",
  "address": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "country": "string",
    "zip_code": "string"
  },
  "configuration": {
    "property1": "string",
    "property2": "string"
  }
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|company_id|string|false|none||none|
|init_date|string(date)|false|none||none|
|end_date|string(date)|false|none||none|
|status|string|false|none||none|
|address|[AddressResponse](#schemaaddressresponse)|false|none||none|
|configuration|object|false|none||none|
|» **additionalProperties**|string|false|none||none|

#### Enum

|Name|Value|
|---|---|
|status|SCHEDULED|
|status|PUBLISHED|
|status|OPENED|
|status|IN_PROGRESS|
|status|CANCELED|
|status|FINISHED|

<h2 id="tocS_PaginationTicketSaleResponse">PaginationTicketSaleResponse</h2>

<a id="schemapaginationticketsaleresponse"></a>
<a id="schema_PaginationTicketSaleResponse"></a>
<a id="tocSpaginationticketsaleresponse"></a>
<a id="tocspaginationticketsaleresponse"></a>

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "event_id": "string",
      "name": "string",
      "description": "string",
      "price": 0,
      "entries": 0,
      "active": true
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|currentPage|integer(int32)|false|none||none|
|perPage|integer(int32)|false|none||none|
|total|integer(int64)|false|none||none|
|items|[[TicketSaleResponse](#schematicketsaleresponse)]|false|none||none|

<h2 id="tocS_TicketSaleResponse">TicketSaleResponse</h2>

<a id="schematicketsaleresponse"></a>
<a id="schema_TicketSaleResponse"></a>
<a id="tocSticketsaleresponse"></a>
<a id="tocsticketsaleresponse"></a>

```json
{
  "id": "string",
  "event_id": "string",
  "name": "string",
  "description": "string",
  "price": 0,
  "entries": 0,
  "active": true
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|event_id|string|false|none||none|
|name|string|false|none||none|
|description|string|false|none||none|
|price|number|false|none||none|
|entries|integer(int32)|false|none||none|
|active|boolean|false|none||none|

<h2 id="tocS_PaginationTicketResponse">PaginationTicketResponse</h2>

<a id="schemapaginationticketresponse"></a>
<a id="schema_PaginationTicketResponse"></a>
<a id="tocSpaginationticketresponse"></a>
<a id="tocspaginationticketresponse"></a>

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "user_id": "string",
      "document": "string",
      "event_id": "string",
      "description": "string",
      "valid_in": "2019-08-24",
      "expired_in": "2019-08-24",
      "status": "AVAILABLE",
      "code": "string",
      "last_time_consumed": "2019-08-24T14:15:22Z"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|currentPage|integer(int32)|false|none||none|
|perPage|integer(int32)|false|none||none|
|total|integer(int64)|false|none||none|
|items|[[TicketResponse](#schematicketresponse)]|false|none||none|

<h2 id="tocS_EnrollmentResponse">EnrollmentResponse</h2>

<a id="schemaenrollmentresponse"></a>
<a id="schema_EnrollmentResponse"></a>
<a id="tocSenrollmentresponse"></a>
<a id="tocsenrollmentresponse"></a>

```json
{
  "id": "string",
  "user_id": "string",
  "event_id": "string",
  "created_at": "2019-08-24T14:15:22Z",
  "status": "WAITING_CONFIRMATION",
  "updated_at": "2019-08-24T14:15:22Z"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|string|false|none||none|
|user_id|string|false|none||none|
|event_id|string|false|none||none|
|created_at|string(date-time)|false|none||none|
|status|string|false|none||none|
|updated_at|string(date-time)|false|none||none|

#### Enum

|Name|Value|
|---|---|
|status|WAITING_CONFIRMATION|
|status|CONFIRMED|
|status|DENIED|

<h2 id="tocS_PaginationEnrollmentResponse">PaginationEnrollmentResponse</h2>

<a id="schemapaginationenrollmentresponse"></a>
<a id="schema_PaginationEnrollmentResponse"></a>
<a id="tocSpaginationenrollmentresponse"></a>
<a id="tocspaginationenrollmentresponse"></a>

```json
{
  "currentPage": 0,
  "perPage": 0,
  "total": 0,
  "items": [
    {
      "id": "string",
      "user_id": "string",
      "event_id": "string",
      "created_at": "2019-08-24T14:15:22Z",
      "status": "WAITING_CONFIRMATION",
      "updated_at": "2019-08-24T14:15:22Z"
    }
  ]
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|currentPage|integer(int32)|false|none||none|
|perPage|integer(int32)|false|none||none|
|total|integer(int64)|false|none||none|
|items|[[EnrollmentResponse](#schemaenrollmentresponse)]|false|none||none|

<h2 id="tocS_ZipCodeResponse">ZipCodeResponse</h2>

<a id="schemazipcoderesponse"></a>
<a id="schema_ZipCodeResponse"></a>
<a id="tocSzipcoderesponse"></a>
<a id="tocszipcoderesponse"></a>

```json
{
  "cep": "string",
  "street": "string",
  "neighborhood": "string",
  "city": "string",
  "state": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|cep|string|false|none||none|
|street|string|false|none||none|
|neighborhood|string|false|none||none|
|city|string|false|none||none|
|state|string|false|none||none|

