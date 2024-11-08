# Milestone 2 Test Automation

A comprehensive test automation framework for testing the SauceDemo application across multiple platforms:
- REST API Testing
- Web Application Testing 
- Mobile Application Testing

## Technologies Used

- Java
- Maven
- Cucumber
- TestNG
- REST Assured (API Testing)
- Selenium WebDriver (Web Testing)
- Appium (Mobile Testing)
- Jenkins (CI/CD)
- Docker

## Project Structure

```
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       ├── java/
│       │   ├── runner/
│       │   └── steps/
│       │       ├── api/
│       │       ├── app/
│       │       └── web/
│       └── resources/
│           └── features/
│               ├── api/
│               ├── app/
│               └── web/
```

## Features

### API Testing
- Authentication
- Booking CRUD Operations
- Error Handling
- Data Validation

### Web Testing
- Login Functionality
- Product Navigation
- Shopping Cart
- Checkout Process

### Mobile App Testing
- Login Authentication
- Product Catalog
- Shopping Cart Management
- Checkout Flow

## Setup Instructions

1. Clone the repository
2. Install dependencies:
```bash
mvn clean install
```

3. Configure environment:
   - Set up Java JDK 17
   - Install Maven
   - Configure Android SDK (for mobile testing)
   - Set up Appium Server (for mobile testing)

4. Set up Docker environment:
```bash
docker-compose up -d
```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Suites
```bash
# API Tests
mvn test -Dtest=RunApiTest

# Web Tests
mvn test -Dtest=RunWebTest

# Mobile App Tests
mvn test -Dtest=RunAppTest
```

## CI/CD Integration

The project includes Jenkins pipeline configuration for automated testing. The pipeline:
- Checks out the code
- Cleans the workspace
- Runs all test suites
- Generates and publishes test reports
- Cleans up resources

## Test Reports

After test execution, reports can be found in:
- API Test Report: `target/cucumber-reports/api/index.html`
- Web Test Report: `target/cucumber-reports/web/index.html`
- App Test Report: `target/cucumber-reports/app/index.html`


## License

This project is licensed under the MIT License - see the LICENSE file for details.