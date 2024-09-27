# Bati-Cuisine

## Description

Bati-Cuisine is a Java application designed for professionals in the kitchen construction and renovation industry. It calculates the total cost of work, taking into account the materials used and labor costs, with the latter being billed by the hour.

The program includes advanced features such as client management, generation of personalized quotes, and an overview of the financial and logistical aspects of renovation projects.

## Features

1. Project Management
   - Add clients associated with projects
   - Add and manage components (materials, labor)
   - Associate quotes with projects for cost estimation before work begins

2. Component Management
   - Materials: Manage material costs
   - Labor: Calculate costs based on hourly rate, hours worked, and worker productivity

3. Client Management
   - Record basic client information
   - Differentiate between professional and individual clients
   - Calculate and apply specific discounts based on client type

4. Quote Creation
   - Generate quotes before work begins
   - Include issue date and validity date
   - Indicate whether the quote has been accepted by the client

5. Cost Calculation
   - Integrate component costs (materials, labor) into the total project cost calculation
   - Apply a profit margin to obtain the final project cost
   - Take into account applicable taxes (VAT) and discounts

6. Display Details and Results
   - Show complete project details (client, site, components, equipment, total cost)
   - Display information about a client, quote, or construction site
   - Generate a detailed summary of the total cost

## Technical Requirements

- Java 8+
- PostgreSQL database

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/KHALID-ZENNOUHI/Bati-Cuisine.git
   ```

2. Navigate to the project directory:
   ```
   cd bati-cuisine
   ```

3. Set up the PostgreSQL database:
   - Create a new database named `bati_cuisine`
   - Update the `application.properties` file with your database credentials


4. Run the application:
   ```
   java -jar bati-cuisine.jar
   ```

## Usage

Follow the on-screen prompts to:
1. Create a new project
2. Add clients
3. Add materials and labor
4. Generate quotes
5. Calculate total costs

## Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit your changes: `git commit -m 'Add some AmazingFeature'`
4. Push to the branch: `git push origin feature/AmazingFeature`
5. Open a pull request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Contact

Your Name - khalidzennouhi08@gmail.com

Project Link: https://github.com/KHALID-ZENNOUHI/Bati-Cuisine
