# 🛫 Travel in Time ⏳

## Project Description
The aim of this project is to develop an Android application that delivers daily notifications to users, 
highlighting significant historical events that occurred on the current date. This will be achieved by utilizing the 
**Wikipedia API** to retrieve and display relevant information.

## Explanation of the Solved Task

### 1. Introduction
The purpose of this app, "Travel In Time," is to provide users with historical information about significant events, holidays, births, and deaths that occurred in the present day. By accessing data from Wikipedia, the app allows users to explore and learn about notable occurrences on any given day, enhancing their knowledge of history and important dates.

### 2. Project Structure
- **MainActivity**
  - responsible for displaying historical information about significant events, holidays, births, and deaths that occurred on a specific date
  - it sets up the user interface, handles button clicks to fetch data from the Wikipedia API, and displays the data in a RecyclerView
  - the class also manages the app's toolbar, handles back button presses, and adjusts the layout for edge-to-edge display

- **WikiDataModel**
  - represents individual data items within the lists in *OnThisDayResponse*
  - it includes fields for text, year, and a list of pages, each of which contains a thumbnail and an extract

- **OnThisDayResponse**
  - the overall structure of the JSON response from the Wikipedia API
  - it contains lists of *WikiDataModel* objects for different categories: events, births, deaths, and holidays

- **CardsRecViewAdapter**
  - is a custom adapter for a RecyclerView that displays a list of historical events, births, deaths, and holidays
  - it uses the *WikiDataModel* class to populate the data in each card view
 
- **WikiApiService**
  - is a interface that defines the endpoints for the Wikipedia API using Retrofit
 
### 3. Wikipedia API
The Wikipedia API provides access to various types of data from Wikipedia, including historical events, births, deaths, and holidays on specific dates. It allows developers to fetch this data in a structured format, typically JSON, which can then be used in applications to display relevant information to users. The API endpoints are designed to be accessed via HTTP requests, and libraries like Retrofit can be used to simplify the process of making these requests and handling the responses in Android applications. Additionally, if the API requires an API key for authentication, it should be included in the request headers or as a query parameter, depending on the API's requirements.

### 4. Next to do 
- implement push notification feature

### 5. Conclusion
The application fetches historical data from the **Wikipedia API** and displays it in a user-friendly format using a **RecyclerView**. Key components include the *WikiApiService* interface for API calls, *OnThisDayResponse* and *WikiDataModel* classes for data representation, and *CardsRecViewAdapter* for displaying the data.
