@Test
Feature: To validate details of the movie generated and received in kafka message

  Scenario: Should validate details of the movie
    Given user should generate a kafka message with movie name as "Batman" and genre as "Sci-Fi"
    When user triggers the kafka message for topic "movies"
    Then user consumes kafka message from topic "movies" and consumer group "CG.movies" and validates



