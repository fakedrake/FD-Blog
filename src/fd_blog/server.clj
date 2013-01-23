(ns fd-blog.server
  (:require [noir.server :as server]
            [fd-blog.views.post])
  (:gen-class))

(server/load-views-ns 'fd-blog.views)

(def base-handler
  (server/gen-handler
    {:mode :prod,
     :ns 'fd-blog
     :session-cookie-attrs {:max-age 1800000}}))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'fd-blog})))

(defn fix-base-url [handler]
  (fn [request]
    (with-redefs [noir.options/resolve-url
                  (fn [url]
                    ;prepend context to the relative URLs
                    (if (.contains url "://")
                      url (str (:context request) url)))]
      (handler request))))

(def handler (-> base-handler fix-base-url))
