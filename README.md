# Trade Reporting Engine

## Introduction

I approached this task with a straightforward implementation, focusing on simplicity. The solution includes a basic REST Controller as the entry point for loading and retrieving data.

The brief was unclear on whether the data should be loaded dynamically by the user or preloaded. To keep things simple, I placed the files in the resources folder and read them all at once, rather than loading them through the API.

For event storage, I used a JPA repository and extended it with an EventSpecification class to facilitate filtering. I implemented filters only for Seller Party and Premium Currency, as specified in the brief, but additional filters could be easily added. Since the brief outlined a specific filtering requirement, I designed the EventService with a single method tailored to that criteria. I assumed this filtering logic was part of the business rules, so I embedded it directly in the service rather than creating a generic function with dynamic parameters, which could add unnecessary complexity given the unknown scope of the filtering criteria.

For testing, I aimed to cover all key areas, but I primarily conducted surface-level testing using the provided data. In a real-world scenario, I would incorporate more edge cases and a broader range of test scenarios, similar to what I did for the anagram function. However, due to time constraints, I focused on testing with the provided files.
## Instruction:

1. Start up the spring application locally.
2. Check what port it is connected to in the logs, I will use 8080 as my example
3. Load the data by hitting the GET API at http://localhost:8080/loadEvents
4. Get the filtered events back by hitting the Get API at http://localhost:8080/filteredEvents


