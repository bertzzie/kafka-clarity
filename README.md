# Kafka Clarity

This is an example project that reads a Dota2 replay file using [Clarity](https://github.com/skadistats/clarity), 
which then reads all hero positions in game and send it to a Kafka server.

The project is created as a complement for [JVM Meetup #5]() and [this article about Kafka]().

## Requirements

Since Github has a file limit, I cannot upload the replay file directly to here. 
To be able to run the application successfully, make sure you have 3 replay files
in `app-producer/src/main/resources/replay/*`. The files are:

1. `3368387319_2081121450.dem` [download](http://replay111.valve.net/570/3368387319_2081121450.dem.bz2)
1. `3372622939_490339365.dem` [download](http://replay111.valve.net/570/3372622939_490339365.dem.bz2)
1. `3372676225_558108515.dem` [download](http://replay112.valve.net/570/3372676225_558108515.dem.bz2)

Download and extract them (make sure the extension is `.dem`) and it should run perfectly.

Also make sure you have:

- Gradle Installed
- Java 8+
- Apache Kafka 0.11+ 

The default connection is called to `localhost:9092`, you can change it in `app-producer/src/main/resources/application.properties`
if needed. The key is `producer.bootstrap.servers`.

## How to run

To run the consumer:

```bash
   $ ./gradlew executeConsumer
```

To run the producer:

```bash
   $ ./gradlew executeProducer -Pmatch=2
```

You can change `-Pmatch` to 0, 1, or 2. It will parse the 3 replays above according to index.

## Notes

Feel free to send me feedback or PR if you think there's things that need improvements!

## Todo

- [x] Demonstrate how to send data to Kafka (via Producer)
- [x] Demonstrate how to consume data from Kafka
- [x] Demonstrate how to use Kafka Streams to process data
- [ ] Write blog post about this
- [ ] Update meetup video link when available
