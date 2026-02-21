# Coronabot

> [!WARNING]
> This project is no longer maintained.

This Spring Boot application retrieves statistics about Covid-19 and sends a report everyday by
email.

# How to use

An additional, external `coronabot.properties` config file is needed for the application to run.
Here are the required properties with example values:

```yaml
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=john.doe@example.com
spring.mail.password=mypassword

coronabot.mail.from=coronabot@example.com
coronabot.mail.to=mom@example.com, dad@example.com
coronabot.mail.subject=COVID-19 report

coronabot.report.country=france
coronabot.report.sync-cron=0 55 23 * * ?
coronabot.report.mail-cron=0 0 6 * * ?
```

This configuration can be loaded using Spring's `spring.config.additional-location` - see below.

On startup, the application will fetch the COVID statistics for the current day. You can see the
data by accessing `localhost:8080/corona`.

## Using a Jar

1. Download a fat Jar from the [releases section][releases] of the repo, or build from source with
   Maven.

2. Run the JAR (you need Java 11):

   ```bash
   java -jar [jar-file] --spring.config.additional-location=[/path/to/coronabot.properties]
   ```

## Using Docker

Coming soon.

[releases]:
https://github.com/alecigne/covid19-mail-sender/releases
