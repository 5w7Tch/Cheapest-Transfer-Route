# Cheapest Transfer Route

## Deliverable Task: 

A logistics company is optimizing its package delivery network. Packages
need to be transferred between multiple cities, and each transfer has an
associated cost. Your task is to help the company find the best
combination of transfers while ensuring that the total package weight
stays within a given limit.
---

## Features
- Input: List of available transfers with weight and cost, and a maximum weight limit.
- Output: Optimal set of transfers with total cost and weight.
- tested with unit and integration tests.

---

## Building the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/5w7Tch/Cheapest-Transfer-Route.git
   ```

2. Build the application using Maven:
   ```bash
   mvn clean install
   ```

---

## Running the Application

1. Start the application:
   ```bash
   mvn spring-boot:run
   ```

2. The application will be available at `http://localhost:8080`.
   But app has no front end so you can't visit any pages
    
---

## API Endpoints

### Calculate Cheapest Route
**Endpoint:** `/api/transfers/calculate`  
**Method:** `POST`

---

## Running Tests

To execute all unit and integration tests run this command:
```bash
mvn test
```

---

## Example for CURL Commands

### Calculate Cheapest Route
Since no front end exists use this command to test on desireble input
```bash
curl -X POST \
  http://localhost:8080/api/transfers/calculate \
  -H 'Content-Type: application/json' \
  -d '{
    "maxWeight": 15,
    "availableTransfers": [
      { "weight": 5, "cost": 10 },
      { "weight": 10, "cost": 20 },
      { "weight": 3, "cost": 5 },
      { "weight": 8, "cost": 15 }
    ]
  }'
```



