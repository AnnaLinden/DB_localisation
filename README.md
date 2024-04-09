# Database Localization Project

## Overview

This project demonstrates a simple JavaFX application that allows for data entry and retrieval with localization support. It showcases how to handle internationalization in Java applications, with specific focus on supporting English, Japanese, and Farsi languages. Additionally, the project integrates with a MySQL/MariaDB database to store and retrieve localized data.

## Features

- UI Localization: Dynamically switch UI language between English, Japanese, and Farsi.
- Database Operations: Insert and retrieve data from a database with support for UTF-8 characters, ensuring proper handling of international text.
- Error Handling: Demonstrates how to manage database errors, including character encoding issues.

## Prerequisites

- Java JDK 17 or newer
- MySQL or MariaDB server
- Maven (for dependency management)

## Setup

1. **Database Configuration**:
    - Ensure your MySQL/MariaDB instance is running.
    - Create a database named `localise_db`.
    - Adjust the database character set to `utf8mb4` to support international characters:
      ```sql
      CREATE DATABASE localise_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
      ```
    - Update the database connection settings in the application to match your database credentials.

2. **Project Dependencies**:
    - Use Maven to install the necessary dependencies:
      ```bash
      mvn clean install
      ```

3. **Running the Application**:
    - Execute the main class `com.example.db_localisation.DB_Localisation` to launch the application.

## Usage

- Upon launching the application, you will be presented with a simple form.
- You can choose the language of the UI from the dropdown menu at the top.
- Fill in the form with the first name, last name, and email, then click "Save" to insert the data into the database.
- The application will display a confirmation message once the data is successfully saved.
