
(ns topix-trainer.core
  (:import (java.io File))
  (:use cheshire.core)
  (:require [topix.init :as init]
            [topix.data :as data]
            [getweets.core :as getweets]))

(defn- train-with
  "Loading training data from the specified file"
  [topic file]
  (let [data (parse-string (slurp file) true)]
    (doseq [text data]
      (data/analyse topic text))))

(defn- train
  []
  (init/mongo)
  (data/reset)
  (let [dir (File. "data/tweets")]
    (doseq [topic (.list dir)]
      (doseq [file (.list (File. (format "%s/%s" dir topic)))]
        (println "Loading: " file)
        (train-with topic 
                    (format "%s/%s/%s" dir topic file))))
    (data/commit)))

(defn- fetch-tweets
  [topic account]
  (let [dir (File. (format "data/tweets/%s" topic))
        path (format "%s/%s.json" dir account)
        data (->> (getweets/fetch account)
                  (map #(:text %)))]
    (println "Writing tweets to:" path)
    (if (not (.exists dir))
        (.mkdirs dir))
    (spit path (generate-string data))))

(defn- usage
  []
  (prn "Usage: lein run train|fetch-tweets"))

(defn- dispatch
  [action args]
  (condp = action
    "train" (train)
    "fetch-tweets" (apply fetch-tweets args)
    (usage)))

;; Main

(defn -main [& [action & args]]
  (dispatch action args))

