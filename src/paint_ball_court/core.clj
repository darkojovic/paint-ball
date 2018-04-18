(ns paint-ball-court.core
  (:use paint-ball-court.handler.paint-ball-handler
        ring.server.standalone
        [ring.middleware file-info file])
  (:gen-class))

(defonce server (atom nil))

(defn start []
  (let [port 8081]
    (reset! server
            (serve
              (-> #'app
                  (wrap-file "resources")
                  (wrap-file-info))
              {:port         port
               :init         init
               :auto-reload? true
               :destroy      destroy
               :join         true}))
    (println (str "Site is now on at http://localhost:" port))))

(defn stop []
  (.stop @server)
  (reset! server nil))

(defn -main
  "App entry point"
  [& args]
  (start))
