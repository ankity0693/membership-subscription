# FirstClub Membership System

A membership platform.

## Features

- User management
- Membership plans (Monthly, Quarterly, Yearly)
- Membership tiers (Silver, Gold, Platinum)
- Tier-based benefits
- Tier-based rules
- Membership subscriptions
- Order placement and confirmation
- Automatic benefit application during checkout
- Swagger/OpenAPI documentation
- In-memory storage (no database)

---

## Junit for functionality test
MoveTierBasedOnCriteria

## Technology Stack

- Java 21
- Spring Boot
- Spring Web MVC
- Lombok
- Swagger (springdoc-openapi)
- Maven

---

## Design Patterns Used

| Pattern | Usage |
|---------|------|
| Strategy | Different benefit implementations (Discount, Free Delivery, etc.) |
| Factory | Resolving appropriate benefit strategy |
| Observer | Order notifications after order placement |
| Builder | DTO and Entity creation |
| Template Method | Generic CRUD operations |
| Service Layer | Business logic separation |

---

## Project Structure

```
controller/
service/
dao/
model/
dto/
mapper/
observer/
strategy/
factory/
rule/
utils/
common/
```

---

## Running the Application

```bash
mvn clean spring-boot:run
```

Application runs on

```
http://localhost:8080
```

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Specification

```
http://localhost:8080/v3/api-docs
```

---

# REST APIs

## Users

### Create User

```
POST /api/v1/users
```

Request

```json
{
  "name": "John",
  "emailId": "john@test.com"
}
```

---

## Plans

### Create Membership Plan

```
POST /api/v1/plans?planType=MONTHLY&price=999
```

### Create Tier

```
POST /api/v1/plans/tiers?tierType=SILVER
```

### Get Tiers

```
GET /api/v1/plans/tiers
```

---

## Membership

### Get Membership Plans

```
GET /api/v1/memberships/plans
```

Returns all plans along with tier information and configured benefits.

---

### Subscribe

```
POST /api/v1/memberships/subscribe
```

Request

```json
{
  "userId": 1,
  "planId": 1,
  "tierId": 2
}
```

---

### Get User Subscription

```
GET /api/v1/memberships/users/{userId}
```

---

### Cancel Subscription

```
DELETE /api/v1/memberships/subscriptions/{subscriptionId}
```

---

### Get User Benefits

```
GET /api/v1/memberships/users/{userId}/benefits?useDuring=CHECKOUT
```

---

## Benefits

### Add Benefit

```
POST /api/v1/benefits
```

Example

```json
{
  "tierId": 2,
  "type": "DISCOUNT",
  "conditions": {
    "PERCENTAGE": "10"
  },
  "usedDuring": "CHECKOUT",
  "isActive": true
}
```

---

### Get Active Benefits

```
GET /api/v1/benefits
```

---

### Get Benefits for Tier

```
GET /api/v1/benefits/tier/{tierId}
```

---

## Cohorts

### Assign User to Cohort

```
POST /api/v1/cohorts/users/{userId}/{cohortId}
```

---

### Get User Cohorts

```
GET /api/v1/cohorts/users/{userId}
```

---

## Orders

### Place Order

```
POST /api/v1/orders
```

Example

```json
{
  "userId": 1,
  "items": [
    {
      "itemId": 100,
      "quantity": 2,
      "unitPrice": 250
    }
  ]
}
```

Benefits applicable for the user's membership tier are automatically applied before the order is stored.

---

### Confirm Order

```
PATCH /api/v1/orders/{orderId}/confirm
```

---

# Membership Flow

```
Create User
      |
Create Plans & Tiers
      │
Configure Tier Benefits and Rules
      │
Subscribe User
      │
Place Order
      │
Rule Engine Fetches Benefits
      │
Benefit Strategies Apply Discounts
      │
Order Saved
      │
Observer Publishes Order Event and Tier are upgraded and downgraded
```

---

# Supported Membership Benefits

- Free Delivery
- Percentage Discount
- Priority Support
- Early Access
- Exclusive Coupons

Benefits are filtered based on where they are applicable:

- CHECKOUT
- PRODUCT_SEARCH

---

# Assumptions

- Uses in-memory storage; no database persistence.
- IDs are generated in memory.
- Only one active subscription is allowed per user.
- Benefits are applied based on the user's active membership tier.
- Membership benefits are extensible through the Strategy pattern.

---

# Future Enhancements

- Coupon engine
- Scheduled subscription expiry
- Tier upgrade/downgrade workflows
- More exception handling and each flow

---

# Author

Ankit Kumar -> ankity0693@gmail.com