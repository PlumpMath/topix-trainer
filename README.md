
# topix-trainer

This is a training tool for Topix.  It allows pulling down tweets that can
be used for training for various topics.

## Usage

If you're setting up Topix and just need some training data just use train.

```
lein run train
```

You'll need to have whatever configuration for Topix available in the environment
(like TOPIX_MONGO_DB if you've changed it)

## Fetching Tweets

To fetch tweets you just specify the topic and the account, like this:

```
lein run fetch-tweets TOPIC ACCOUNT
```

The data will then be pull down into *data/* for use next time you run train.

## License

Distributed under the Eclipse Public License, the same as Clojure.

