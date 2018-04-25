(ns paint-ball-court.client.http-client
  (:require [clj-http.client :as client]))

(def api-key "5b0494116878b481782a825866caa39f")
(def belgrade-id "792680")

(defn get-current-weather []
  (cheshire.core/parse-string
   (:body
     (client/get
      (str "http://api.openweathermap.org/data/2.5/weather?id=" belgrade-id "&units=metric&appid=" api-key)
      {:accept :json}))))